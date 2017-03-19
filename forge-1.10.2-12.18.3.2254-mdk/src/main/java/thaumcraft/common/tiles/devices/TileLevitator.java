/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.codechicken.libold.raytracer.IndexedCuboid6;
import thaumcraft.codechicken.libold.vec.Cuboid6;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ 
/*     */ public class TileLevitator extends thaumcraft.api.blocks.TileThaumcraft implements ITickable
/*     */ {
/*  29 */   private int[] ranges = { 4, 8, 16, 32 };
/*  30 */   private int range = 1;
/*  31 */   private int rangeActual = 0;
/*  32 */   private int counter = 0;
/*  33 */   private int vis = 0;
/*     */   
/*     */   public void update()
/*     */   {
/*  37 */     EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata());
/*  38 */     if (this.rangeActual > this.ranges[this.range]) {
/*  39 */       this.rangeActual = 0;
/*     */     }
/*  41 */     int p = this.counter % this.ranges[this.range];
/*  42 */     if (this.worldObj.getBlockState(this.pos.offset(facing, 1 + p)).getBlock().isOpaqueCube()) {
/*  43 */       if (1 + p < this.rangeActual) {
/*  44 */         this.rangeActual = (1 + p);
/*     */       }
/*  46 */       this.counter = -1;
/*  47 */     } else if (1 + p > this.rangeActual) {
/*  48 */       this.rangeActual = (1 + p);
/*     */     }
/*     */     
/*  51 */     this.counter += 1;
/*     */     
/*  53 */     if ((!this.worldObj.isRemote) && (this.vis < 10)) {
/*  54 */       this.vis += AuraHandler.drainAuraAvailable(this.worldObj, getPos(), thaumcraft.api.aspects.Aspect.AIR, 1) * 1200;
/*  55 */       markDirty();
/*  56 */       this.worldObj.markBlockForUpdate(getPos());
/*     */     }
/*     */     
/*  59 */     if ((this.rangeActual > 0) && (this.vis > 0) && (BlockStateUtils.isEnabled(getBlockMetadata())))
/*     */     {
/*  61 */       List<Entity> targets = this.worldObj.getEntitiesWithinAABB(Entity.class, net.minecraft.util.AxisAlignedBB.fromBounds(this.pos.getX() - (facing.getFrontOffsetX() < 0 ? this.rangeActual : 0), this.pos.getY() - (facing.getFrontOffsetY() < 0 ? this.rangeActual : 0), this.pos.getZ() - (facing.getFrontOffsetZ() < 0 ? this.rangeActual : 0), this.pos.getX() + 1 + (facing.getFrontOffsetX() > 0 ? this.rangeActual : 0), this.pos.getY() + 1 + (facing.getFrontOffsetY() > 0 ? this.rangeActual : 0), this.pos.getZ() + 1 + (facing.getFrontOffsetZ() > 0 ? this.rangeActual : 0)));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  69 */       boolean lifted = false;
/*  70 */       if (targets.size() > 0)
/*  71 */         for (Entity e : targets)
/*  72 */           if (((e instanceof EntityItem)) || (e.canBePushed()) || ((e instanceof EntityHorse))) {
/*  73 */             lifted = true;
/*  74 */             drawFXAt(e);
/*  75 */             if ((e.isSneaking()) && (facing == EnumFacing.UP)) {
/*  76 */               if (e.motionY < 0.0D) e.motionY *= 0.8999999761581421D;
/*     */             } else {
/*  78 */               e.motionX += 0.1F * facing.getFrontOffsetX();
/*  79 */               e.motionY += 0.1F * facing.getFrontOffsetY();
/*  80 */               e.motionZ += 0.1F * facing.getFrontOffsetZ();
/*  81 */               if ((facing.getAxis() != net.minecraft.util.EnumFacing.Axis.Y) && (!e.onGround)) {
/*  82 */                 if (e.motionY < 0.0D) e.motionY *= 0.8999999761581421D;
/*  83 */                 e.motionY += 0.07999999821186066D;
/*     */               }
/*  85 */               if (e.motionX > 0.3499999940395355D) e.motionX = 0.3499999940395355D;
/*  86 */               if (e.motionY > 0.3499999940395355D) e.motionY = 0.3499999940395355D;
/*  87 */               if (e.motionZ > 0.3499999940395355D) e.motionZ = 0.3499999940395355D;
/*  88 */               if (e.motionX < -0.3499999940395355D) e.motionX = -0.3499999940395355D;
/*  89 */               if (e.motionY < -0.3499999940395355D) e.motionY = -0.3499999940395355D;
/*  90 */               if (e.motionZ < -0.3499999940395355D) e.motionZ = -0.3499999940395355D;
/*     */             }
/*  92 */             e.fallDistance = 0.0F;
/*  93 */             this.vis -= getCost();
/*  94 */             if (this.vis <= 0) break;
/*     */           }
/*  96 */       drawFX(facing);
/*     */       
/*  98 */       if ((lifted) && (!this.worldObj.isRemote) && (this.counter % 20 == 0)) {
/*  99 */         markDirty();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawFX(EnumFacing facing) {
/* 105 */     if ((this.worldObj.isRemote) && (this.worldObj.rand.nextFloat() < 0.2F)) {
/* 106 */       float x = this.pos.offset(facing).getX() + (facing.getFrontOffsetX() != 0 ? 0.0F : 0.25F + this.worldObj.rand.nextFloat() * 0.5F);
/* 107 */       float y = this.pos.offset(facing).getY() + (facing.getFrontOffsetY() != 0 ? 0.0F : 0.25F + this.worldObj.rand.nextFloat() * 0.5F);
/* 108 */       float z = this.pos.offset(facing).getZ() + (facing.getFrontOffsetZ() != 0 ? 0.0F : 0.25F + this.worldObj.rand.nextFloat() * 0.5F);
/* 109 */       Thaumcraft.proxy.getFX().drawLevitatorParticles(x, y, z, facing.getFrontOffsetX() / 100.0D, facing.getFrontOffsetY() / 100.0D, facing.getFrontOffsetZ() / 100.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawFXAt(Entity e)
/*     */   {
/* 115 */     if ((this.worldObj.isRemote) && (this.worldObj.rand.nextFloat() < 0.2F)) {
/* 116 */       float x = (float)(e.posX + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * e.width);
/* 117 */       float y = (float)(e.posY + this.worldObj.rand.nextFloat() * e.height);
/* 118 */       float z = (float)(e.posZ + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * e.width);
/* 119 */       Thaumcraft.proxy.getFX().drawLevitatorParticles(x, y, z, (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.01D, (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.01D, (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.01D);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbt)
/*     */   {
/* 129 */     this.range = nbt.getByte("range");
/* 130 */     this.vis = nbt.getInteger("vis");
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbt)
/*     */   {
/* 136 */     nbt.setByte("range", (byte)this.range);
/* 137 */     nbt.setInteger("vis", this.vis);
/*     */   }
/*     */   
/*     */   public int getCost() {
/* 141 */     return this.ranges[this.range] * 2;
/*     */   }
/*     */   
/*     */   public void increaseRange(EntityPlayer playerIn) {
/* 145 */     this.rangeActual = 0;
/* 146 */     if (!this.worldObj.isRemote) {
/* 147 */       this.range += 1;
/* 148 */       if (this.range >= this.ranges.length) this.range = 0;
/* 149 */       markDirty();
/* 150 */       this.worldObj.markBlockForUpdate(getPos());
/* 151 */       playerIn.addChatMessage(new ChatComponentText(String.format(net.minecraft.util.StatCollector.translateToLocal("tc.levitator"), new Object[] { Integer.valueOf(this.ranges[this.range]), Integer.valueOf(getCost()) })));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MovingObjectPosition rayTrace(World world, Vec3 vec3d, Vec3 vec3d1, MovingObjectPosition fullblock)
/*     */   {
/* 161 */     return fullblock;
/*     */   }
/*     */   
/*     */ 
/*     */   public void addTraceableCuboids(List<IndexedCuboid6> cuboids)
/*     */   {
/* 167 */     EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata());
/* 168 */     cuboids.add(new IndexedCuboid6(Integer.valueOf(0), getCuboidByFacing(facing)));
/*     */   }
/*     */   
/*     */   public Cuboid6 getCuboidByFacing(EnumFacing facing) {
/* 172 */     switch (facing) {
/* 173 */     default:  return new Cuboid6(getPos().getX() + 0.375D, getPos().getY() + 0.0625D, getPos().getZ() + 0.375D, getPos().getX() + 0.625D, getPos().getY() + 0.125D, getPos().getZ() + 0.625D);
/*     */     case DOWN: 
/* 175 */       return new Cuboid6(getPos().getX() + 0.375D, getPos().getY() + 0.875D, getPos().getZ() + 0.375D, getPos().getX() + 0.625D, getPos().getY() + 0.9375D, getPos().getZ() + 0.625D);
/*     */     case EAST: 
/* 177 */       return new Cuboid6(getPos().getX() + 0.0625D, getPos().getY() + 0.375D, getPos().getZ() + 0.375D, getPos().getX() + 0.125D, getPos().getY() + 0.625D, getPos().getZ() + 0.625D);
/*     */     case WEST: 
/* 179 */       return new Cuboid6(getPos().getX() + 0.875D, getPos().getY() + 0.375D, getPos().getZ() + 0.375D, getPos().getX() + 0.9375D, getPos().getY() + 0.625D, getPos().getZ() + 0.625D);
/*     */     case SOUTH: 
/* 181 */       return new Cuboid6(getPos().getX() + 0.375D, getPos().getY() + 0.375D, getPos().getZ() + 0.0625D, getPos().getX() + 0.625D, getPos().getY() + 0.625D, getPos().getZ() + 0.125D);
/*     */     }
/* 183 */     return new Cuboid6(getPos().getX() + 0.375D, getPos().getY() + 0.375D, getPos().getZ() + 0.875D, getPos().getX() + 0.625D, getPos().getY() + 0.625D, getPos().getZ() + 0.9375D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileLevitator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */