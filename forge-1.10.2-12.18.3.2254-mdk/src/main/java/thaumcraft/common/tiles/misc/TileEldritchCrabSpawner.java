/*     */ package thaumcraft.common.tiles.misc;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.monster.EntityEldritchCrab;
/*     */ 
/*     */ public class TileEldritchCrabSpawner extends TileThaumcraft implements ITickable
/*     */ {
/*  20 */   public int count = 150;
/*  21 */   public int ticks = 0;
/*     */   
/*     */   public void update()
/*     */   {
/*  25 */     if (this.ticks == 0) this.ticks = this.worldObj.rand.nextInt(500);
/*  26 */     this.ticks += 1;
/*  27 */     if (!this.worldObj.isRemote) {
/*  28 */       this.count -= 1;
/*  29 */       if (this.count < 0) {
/*  30 */         this.count = (50 + this.worldObj.rand.nextInt(50));
/*     */       } else {
/*  32 */         if ((this.count == 15) && (isActivated()) && (!maxEntitiesReached())) {
/*  33 */           this.worldObj.addBlockEvent(this.pos, getBlockType(), 1, 0);
/*  34 */           this.worldObj.playSoundEffect(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, "random.fizz", 0.5F, 1.0F);
/*     */         }
/*  36 */         if ((this.count <= 0) && (isActivated()) && (!maxEntitiesReached())) {
/*  37 */           this.count = (150 + this.worldObj.rand.nextInt(100));
/*  38 */           spawnCrab();
/*  39 */           this.worldObj.playSoundEffect(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, "thaumcraft:gore", 0.5F, 1.0F);
/*     */         }
/*     */       }
/*     */     }
/*  43 */     else if (this.venting > 0) {
/*  44 */       this.venting -= 1;
/*  45 */       for (int a = 0; a < 3; a++) {
/*  46 */         drawVent();
/*     */       }
/*     */     }
/*  49 */     else if (this.worldObj.rand.nextInt(20) == 0) {
/*  50 */       drawVent();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void drawVent()
/*     */   {
/*  57 */     EnumFacing dir = EnumFacing.VALUES[this.facing];
/*  58 */     float fx = 0.15F - this.worldObj.rand.nextFloat() * 0.3F;
/*  59 */     float fz = 0.15F - this.worldObj.rand.nextFloat() * 0.3F;
/*  60 */     float fy = 0.15F - this.worldObj.rand.nextFloat() * 0.3F;
/*  61 */     float fx2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/*  62 */     float fz2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/*  63 */     float fy2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/*  64 */     Thaumcraft.proxy.getFX().drawVentParticles(this.pos.getX() + 0.5F + fx + dir.getFrontOffsetX() / 2.1F, this.pos.getY() + 0.5F + fy + dir.getFrontOffsetY() / 2.1F, this.pos.getZ() + 0.5F + fz + dir.getFrontOffsetZ() / 2.1F, dir.getFrontOffsetX() / 3.0F + fx2, dir.getFrontOffsetY() / 3.0F + fy2, dir.getFrontOffsetZ() / 3.0F + fz2, 10061994, 2.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  73 */   int venting = 0;
/*     */   
/*     */ 
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/*  78 */     if (i == 1)
/*     */     {
/*  80 */       this.venting = 20;
/*  81 */       return true;
/*     */     }
/*     */     
/*  84 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */   
/*     */   private boolean maxEntitiesReached()
/*     */   {
/*  89 */     List ents = this.worldObj.getEntitiesWithinAABB(EntityEldritchCrab.class, AxisAlignedBB.fromBounds(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX() + 1.0D, this.pos.getY() + 1.0D, this.pos.getZ() + 1.0D).expand(32.0D, 32.0D, 32.0D));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  94 */     return ents.size() > 5;
/*     */   }
/*     */   
/*     */   public boolean isActivated()
/*     */   {
/*  99 */     return this.worldObj.getClosestPlayer(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, 16.0D) != null;
/*     */   }
/*     */   
/*     */   private void spawnCrab() {
/* 103 */     EnumFacing dir = EnumFacing.VALUES[this.facing];
/* 104 */     EntityEldritchCrab crab = new EntityEldritchCrab(this.worldObj);
/* 105 */     double x = this.pos.getX() + dir.getFrontOffsetX();
/* 106 */     double y = this.pos.getY() + dir.getFrontOffsetY();
/* 107 */     double z = this.pos.getZ() + dir.getFrontOffsetZ();
/* 108 */     crab.setLocationAndAngles(x + 0.5D, y + 0.5D, z + 0.5D, 0.0F, 0.0F);
/* 109 */     crab.onInitialSpawn(this.worldObj.getDifficultyForLocation(this.pos), (IEntityLivingData)null);
/* 110 */     crab.setHelm(false);
/* 111 */     crab.motionX = (dir.getFrontOffsetX() * 0.2F);
/* 112 */     crab.motionY = (dir.getFrontOffsetY() * 0.2F);
/* 113 */     crab.motionZ = (dir.getFrontOffsetZ() * 0.2F);
/* 114 */     this.worldObj.spawnEntityInWorld(crab);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/* 122 */     return AxisAlignedBB.fromBounds(this.pos.getX() - 1.0D, this.pos.getY() - 1.0D, this.pos.getZ() - 1.0D, this.pos.getX() + 2.0D, this.pos.getY() + 2.0D, this.pos.getZ() + 2.0D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 127 */   byte facing = 0;
/*     */   
/*     */   public byte getVentFacing() {
/* 130 */     return this.facing;
/*     */   }
/*     */   
/*     */   public void setVentFacing(byte face) {
/* 134 */     this.facing = face;
/* 135 */     this.worldObj.markBlockForUpdate(this.pos);
/* 136 */     markDirty();
/*     */   }
/*     */   
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 141 */     this.facing = nbttagcompound.getByte("facing");
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 146 */     nbttagcompound.setByte("facing", this.facing);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\misc\TileEldritchCrabSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */