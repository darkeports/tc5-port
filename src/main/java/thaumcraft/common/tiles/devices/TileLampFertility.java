/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.ThaumcraftApiHelper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.IEssentiaTransport;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.common.blocks.IBlockEnabled;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ 
/*     */ public class TileLampFertility
/*     */   extends TileThaumcraft implements IEssentiaTransport, ITickable
/*     */ {
/*  29 */   public int charges = 0;
/*     */   
/*     */ 
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
/*     */   {
/*  34 */     super.onDataPacket(net, pkt);
/*  35 */     if ((this.worldObj != null) && 
/*  36 */       (this.worldObj.isRemote)) {
/*  37 */       this.worldObj.checkLightFor(EnumSkyBlock.BLOCK, getPos());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*  42 */   int count = 0;
/*     */   
/*     */   public void update()
/*     */   {
/*  46 */     if (!this.worldObj.isRemote) {
/*  47 */       if (this.charges < 4) {
/*  48 */         if (drawEssentia()) {
/*  49 */           this.charges += 1;
/*  50 */           markDirty();
/*  51 */           this.worldObj.markBlockForUpdate(getPos());
/*     */         }
/*  53 */         if (this.charges <= 1) {
/*  54 */           if (BlockStateUtils.isEnabled(getBlockMetadata()))
/*     */           {
/*  56 */             this.worldObj.setBlockState(this.pos, this.worldObj.getBlockState(getPos()).withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(false)), 3);
/*     */           }
/*     */         }
/*  59 */         else if ((!gettingPower()) && (!BlockStateUtils.isEnabled(getBlockMetadata())))
/*     */         {
/*  61 */           this.worldObj.setBlockState(this.pos, this.worldObj.getBlockState(getPos()).withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(true)), 3);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  66 */       if ((!gettingPower()) && (this.charges > 1) && (this.count++ % 300 == 0)) {
/*  67 */         updateAnimals();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void updateAnimals()
/*     */   {
/*  76 */     int distance = 7;
/*     */     
/*  78 */     List<EntityAnimal> var5 = this.worldObj.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.fromBounds(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX() + 1, this.pos.getY() + 1, this.pos.getZ() + 1).expand(distance, distance, distance));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  84 */     Iterator var2 = var5.iterator();
/*     */     
/*  86 */     while (var2.hasNext())
/*     */     {
/*  88 */       EntityAnimal var3 = (EntityAnimal)var2.next();
/*     */       
/*     */ 
/*  91 */       EntityLivingBase var4 = var3;
/*     */       
/*  93 */       if ((var3.getGrowingAge() == 0) && (!var3.isInLove()))
/*     */       {
/*  95 */         ArrayList<EntityAnimal> sa = new ArrayList();
/*  96 */         Iterator varq = var5.iterator();
/*  97 */         while (varq.hasNext())
/*     */         {
/*  99 */           EntityLivingBase var7 = (EntityLivingBase)varq.next();
/* 100 */           if (var7.getClass().equals(var4.getClass())) {
/* 101 */             sa.add((EntityAnimal)var7);
/*     */           }
/*     */         }
/*     */         
/* 105 */         if ((sa == null) || (sa.size() <= 7)) {
/* 106 */           Iterator var22 = sa.iterator();
/* 107 */           EntityAnimal partner = null;
/* 108 */           while (var22.hasNext())
/*     */           {
/*     */ 
/* 111 */             EntityAnimal var33 = (EntityAnimal)var22.next();
/* 112 */             if ((var33.getGrowingAge() == 0) && (!var33.isInLove())) {
/* 113 */               if (partner == null) {
/* 114 */                 partner = var33;
/*     */               } else {
/* 116 */                 this.charges -= 2;
/* 117 */                 var33.setInLove(null);
/* 118 */                 partner.setInLove(null);
/* 119 */                 return;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound) {
/* 129 */     this.charges = nbttagcompound.getInteger("charges");
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 135 */     nbttagcompound.setInteger("charges", this.charges);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 140 */   int drawDelay = 0;
/*     */   
/*     */   boolean drawEssentia() {
/* 143 */     if (++this.drawDelay % 5 != 0) return false;
/* 144 */     TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, getPos(), BlockStateUtils.getFacing(getBlockMetadata()));
/* 145 */     if (te != null) {
/* 146 */       IEssentiaTransport ic = (IEssentiaTransport)te;
/* 147 */       if (!ic.canOutputTo(BlockStateUtils.getFacing(getBlockMetadata()).getOpposite())) return false;
/* 148 */       if ((ic.getSuctionAmount(BlockStateUtils.getFacing(getBlockMetadata()).getOpposite()) < getSuctionAmount(BlockStateUtils.getFacing(getBlockMetadata()))) && (ic.takeEssentia(Aspect.LIFE, 1, BlockStateUtils.getFacing(getBlockMetadata()).getOpposite()) == 1))
/*     */       {
/*     */ 
/* 151 */         return true;
/*     */       }
/*     */     }
/* 154 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isConnectable(EnumFacing face)
/*     */   {
/* 159 */     return face == BlockStateUtils.getFacing(getBlockMetadata());
/*     */   }
/*     */   
/*     */   public boolean canInputFrom(EnumFacing face)
/*     */   {
/* 164 */     return face == BlockStateUtils.getFacing(getBlockMetadata());
/*     */   }
/*     */   
/*     */   public boolean canOutputTo(EnumFacing face)
/*     */   {
/* 169 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSuction(Aspect aspect, int amount) {}
/*     */   
/*     */ 
/*     */   public int getMinimumSuction()
/*     */   {
/* 178 */     return 0;
/*     */   }
/*     */   
/*     */   public Aspect getSuctionType(EnumFacing face)
/*     */   {
/* 183 */     return Aspect.LIFE;
/*     */   }
/*     */   
/*     */   public int getSuctionAmount(EnumFacing face)
/*     */   {
/* 188 */     return face == BlockStateUtils.getFacing(getBlockMetadata()) ? 128 - this.charges * 10 : 0;
/*     */   }
/*     */   
/*     */   public Aspect getEssentiaType(EnumFacing loc)
/*     */   {
/* 193 */     return null;
/*     */   }
/*     */   
/*     */   public int getEssentiaAmount(EnumFacing loc)
/*     */   {
/* 198 */     return 0;
/*     */   }
/*     */   
/*     */   public int takeEssentia(Aspect aspect, int amount, EnumFacing facing)
/*     */   {
/* 203 */     return 0;
/*     */   }
/*     */   
/*     */   public int addEssentia(Aspect aspect, int amount, EnumFacing facing)
/*     */   {
/* 208 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileLampFertility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */