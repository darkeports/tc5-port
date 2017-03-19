/*    */ package thaumcraft.client.fx.particles;
/*    */ 
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class FXGenericP2P extends FXGeneric
/*    */ {
/*    */   private double targetX;
/*    */   private double targetY;
/*    */   private double targetZ;
/*    */   
/*    */   public FXGenericP2P(World world, double x, double y, double z, double xx, double yy, double zz)
/*    */   {
/* 14 */     super(world, x, y, z, 0.0D, 0.0D, 0.0D);
/* 15 */     setSize(0.1F, 0.1F);
/* 16 */     this.prevPosX = this.posX;
/* 17 */     this.prevPosY = this.posY;
/* 18 */     this.prevPosZ = this.posZ;
/*    */     
/* 20 */     this.targetX = xx;
/* 21 */     this.targetY = yy;
/* 22 */     this.targetZ = zz;
/*    */     
/* 24 */     double dx = xx - this.posX;
/* 25 */     double dy = yy - this.posY;
/* 26 */     double dz = zz - this.posZ;
/* 27 */     int base = (int)(MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz) * 3.0F);
/* 28 */     if (base < 1) base = 1;
/* 29 */     this.particleMaxAge = (base / 2 + this.rand.nextInt(base));
/*    */     
/* 31 */     
/* 32 */     
/* 33 */     
/* 34 */     this.particleTextureJitterX = 0.0F;
/* 35 */     this.particleTextureJitterY = 0.0F;
/* 36 */     float f3 = 0.01F;
/* 37 */     this.motionX = ((float)this.worldObj.rand.nextGaussian() * f3);
/* 38 */     this.motionY = ((float)this.worldObj.rand.nextGaussian() * f3);
/* 39 */     this.motionZ = ((float)this.worldObj.rand.nextGaussian() * f3);
/*    */     
/* 41 */     this.particleGravity = 0.2F;
/* 42 */     this.canCollide = true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onUpdate()
/*    */   {
/* 50 */     this.prevPosX = this.posX;
/* 51 */     this.prevPosY = this.posY;
/* 52 */     this.prevPosZ = this.posZ;
/*    */     
/*    */ 
/*    */ 
/* 56 */     if ((this.particleAge++ >= this.particleMaxAge) || ((MathHelper.floor_double(this.posX) == MathHelper.floor_double(this.targetX)) && (MathHelper.floor_double(this.posY) == MathHelper.floor_double(this.targetY)) && (MathHelper.floor_double(this.posZ) == MathHelper.floor_double(this.targetZ))))
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/* 61 */       setExpired();
/* 62 */       return;
/*    */     }
/*    */     
/*    */ 
/*    */ 	 //TODO:???
/* 67 */     if (this.canCollide) pushOutOfBlocks(this.posX, this.posY, this.posZ);
/* 68 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*    */     
/* 70 */     this.motionX *= 0.985D;
/* 71 */     this.motionY *= 0.985D;
/* 72 */     this.motionZ *= 0.985D;
/*    */     
/* 74 */     double dx = this.targetX - this.posX;
/* 75 */     double dy = this.targetY - this.posY;
/* 76 */     double dz = this.targetZ - this.posZ;
/* 77 */     double d13 = 0.3D;
/* 78 */     double d11 = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
/*    */     
/* 80 */     if (d11 < 4.0D) {
/* 81 */       this.particleScale *= 0.9F;
/* 82 */       d13 = 0.6D;
/*    */     }
/*    */     
/* 85 */     dx /= d11;
/* 86 */     dy /= d11;
/* 87 */     dz /= d11;
/*    */     
/* 89 */     this.motionX += dx * d13;
/* 90 */     this.motionY += dy * d13;
/* 91 */     this.motionZ += dz * d13;
/*    */     
/* 93 */     this.motionX = MathHelper.clamp_float((float)this.motionX, -0.35F, 0.35F);
/* 94 */     this.motionY = MathHelper.clamp_float((float)this.motionY, -0.35F, 0.35F);
/* 95 */     this.motionZ = MathHelper.clamp_float((float)this.motionZ, -0.35F, 0.35F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXGenericP2P.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */