/*     */ package thaumcraft.common.entities;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ 
/*     */ public class EntityFallingTaint extends Entity implements net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
/*     */ {
/*     */   public IBlockState fallTile;
/*     */   BlockPos oldPos;
/*     */   public int fallTime;
/*     */   private int fallHurtMax;
/*     */   private float fallHurtAmount;
/*     */   
/*     */   public IBlockState getBlock()
/*     */   {
/*  26 */     return this.fallTile;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EntityFallingTaint(World par1World)
/*     */   {
/*  42 */     super(par1World);
/*  43 */     this.fallTime = 0;
/*  44 */     this.fallHurtMax = 40;
/*  45 */     this.fallHurtAmount = 2.0F;
/*     */   }
/*     */   
/*     */   public EntityFallingTaint(World par1World, double par2, double par4, double par6, IBlockState par8, BlockPos o)
/*     */   {
/*  50 */     super(par1World);
/*  51 */     this.fallTime = 0;
/*  52 */     this.fallHurtMax = 40;
/*  53 */     this.fallHurtAmount = 2.0F;
/*  54 */     this.fallTile = par8;
/*  55 */     this.preventEntitySpawning = true;
/*  56 */     setSize(0.98F, 0.98F);
/*  57 */     setPosition(par2, par4, par6);
/*  58 */     this.motionX = 0.0D;
/*  59 */     this.motionY = 0.0D;
/*  60 */     this.motionZ = 0.0D;
/*  61 */     this.prevPosX = par2;
/*  62 */     this.prevPosY = par4;
/*  63 */     this.prevPosZ = par6;
/*  64 */     this.oldPos = o;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/*  74 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void entityInit() {}
/*     */   
/*     */ 
/*     */   public void writeSpawnData(ByteBuf data)
/*     */   {
/*  84 */     data.writeInt(Block.getIdFromBlock(this.fallTile.getBlock()));
/*  85 */     data.writeByte(this.fallTile.getBlock().getMetaFromState(this.fallTile));
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf data)
/*     */   {
/*     */     try {
/*  91 */       this.fallTile = Block.getBlockById(data.readInt()).getStateFromMeta(data.readByte());
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 102 */     return !this.isDead;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 111 */     if ((this.fallTile == null) || (this.fallTile == Blocks.air))
/*     */     {
/* 113 */       setDead();
/*     */     }
/*     */     else
/*     */     {
/* 117 */       this.prevPosX = this.posX;
/* 118 */       this.prevPosY = this.posY;
/* 119 */       this.prevPosZ = this.posZ;
/* 120 */       this.fallTime += 1;
/* 121 */       this.motionY -= 0.03999999910593033D;
/* 122 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 123 */       this.motionX *= 0.9800000190734863D;
/* 124 */       this.motionY *= 0.9800000190734863D;
/* 125 */       this.motionZ *= 0.9800000190734863D;
/*     */       
/* 127 */       BlockPos bp = new BlockPos(this);
/*     */       
/* 129 */       if (!this.worldObj.isRemote)
/*     */       {
/*     */ 
/*     */ 
/* 133 */         if (this.fallTime == 1)
/*     */         {
/*     */ 
/* 136 */           if (this.worldObj.getBlockState(this.oldPos) != this.fallTile)
/*     */           {
/* 138 */             setDead();
/* 139 */             return;
/*     */           }
/*     */           
/* 142 */           this.worldObj.setBlockToAir(this.oldPos);
/*     */         }
/*     */         
/* 145 */         if ((this.onGround) || (this.worldObj.getBlockState(bp.down()) == BlocksTC.fluxGoo))
/*     */         {
/* 147 */           this.motionX *= 0.699999988079071D;
/* 148 */           this.motionZ *= 0.699999988079071D;
/* 149 */           this.motionY *= -0.5D;
/*     */           
/* 151 */           if ((this.worldObj.getBlockState(bp).getBlock() != Blocks.piston) && (this.worldObj.getBlockState(bp).getBlock() != Blocks.piston_extension) && (this.worldObj.getBlockState(bp).getBlock() != Blocks.piston_head))
/*     */           {
/*     */ 
/*     */ 
/* 155 */             this.worldObj.playSoundAtEntity(this, "thaumcraft:gore", 0.5F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
/* 156 */             setDead();
/*     */             
/* 158 */             if ((!canPlace(bp)) || (thaumcraft.common.blocks.world.taint.BlockTaint.canFallBelow(this.worldObj, bp.down())) || (!this.worldObj.setBlockState(bp, this.fallTile))) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 171 */         else if (((this.fallTime > 100) && (!this.worldObj.isRemote) && ((bp.getY() < 1) || (bp.getY() > 256))) || (this.fallTime > 600))
/*     */         {
/*     */ 
/* 174 */           setDead();
/*     */         }
/*     */       }
/* 177 */       else if ((this.onGround) || (this.fallTime == 1))
/*     */       {
/* 179 */         for (int j = 0; j < 10; j++)
/*     */         {
/* 181 */           thaumcraft.common.Thaumcraft.proxy.getFX().taintLandFX(this);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean canPlace(BlockPos pos)
/*     */   {
/* 190 */     return (this.worldObj.getBlockState(pos).getBlock() == BlocksTC.taintFibre) || (this.worldObj.getBlockState(pos).getBlock() == BlocksTC.fluxGoo) || (this.worldObj.canBlockBePlaced(this.fallTile.getBlock(), pos, true, net.minecraft.util.EnumFacing.UP, (Entity)null, (net.minecraft.item.ItemStack)null));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 206 */     Block block = this.fallTile != null ? this.fallTile.getBlock() : Blocks.air;
/* 207 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(block);
/* 208 */     par1NBTTagCompound.setString("Block", resourcelocation == null ? "" : resourcelocation.toString());
/* 209 */     par1NBTTagCompound.setByte("Data", (byte)block.getMetaFromState(this.fallTile));
/*     */     
/* 211 */     par1NBTTagCompound.setByte("Time", (byte)this.fallTime);
/* 212 */     par1NBTTagCompound.setFloat("FallHurtAmount", this.fallHurtAmount);
/* 213 */     par1NBTTagCompound.setInteger("FallHurtMax", this.fallHurtMax);
/* 214 */     par1NBTTagCompound.setLong("Old", this.oldPos.toLong());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 223 */     int i = par1NBTTagCompound.getByte("Data") & 0xFF;
/*     */     
/* 225 */     if (par1NBTTagCompound.hasKey("Block", 8))
/*     */     {
/* 227 */       this.fallTile = Block.getBlockFromName(par1NBTTagCompound.getString("Block")).getStateFromMeta(i);
/*     */     }
/* 229 */     else if (par1NBTTagCompound.hasKey("TileID", 99))
/*     */     {
/* 231 */       this.fallTile = Block.getBlockById(par1NBTTagCompound.getInteger("TileID")).getStateFromMeta(i);
/*     */     }
/*     */     else
/*     */     {
/* 235 */       this.fallTile = Block.getBlockById(par1NBTTagCompound.getByte("Tile") & 0xFF).getStateFromMeta(i);
/*     */     }
/*     */     
/* 238 */     this.fallTime = (par1NBTTagCompound.getByte("Time") & 0xFF);
/* 239 */     this.oldPos = BlockPos.fromLong(par1NBTTagCompound.getLong("Old"));
/*     */     
/* 241 */     if (par1NBTTagCompound.hasKey("HurtEntities"))
/*     */     {
/* 243 */       this.fallHurtAmount = par1NBTTagCompound.getFloat("FallHurtAmount");
/* 244 */       this.fallHurtMax = par1NBTTagCompound.getInteger("FallHurtMax");
/*     */     }
/*     */     
/*     */ 
/* 248 */     if (this.fallTile == null)
/*     */     {
/* 250 */       this.fallTile = Blocks.sand.getDefaultState();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void addEntityCrashInfo(CrashReportCategory par1CrashReportCategory)
/*     */   {
/* 257 */     super.addEntityCrashInfo(par1CrashReportCategory);
/* 258 */     par1CrashReportCategory.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(this.fallTile.getBlock())));
/* 259 */     par1CrashReportCategory.addCrashSection("Immitating block data", Integer.valueOf(this.fallTile.getBlock().getMetaFromState(this.fallTile)));
/*     */   }
/*     */   
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public World getWorld()
/*     */   {
/* 266 */     return this.worldObj;
/*     */   }
/*     */   
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public boolean canRenderOnFire()
/*     */   {
/* 273 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\EntityFallingTaint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */