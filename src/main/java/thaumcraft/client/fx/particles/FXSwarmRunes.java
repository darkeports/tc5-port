/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.util.Random;
		  import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class FXSwarmRunes extends Particle
/*     */ {
/*     */   private Entity target;
/*  14 */   private float turnSpeed = 10.0F;
/*  15 */   private float speed = 0.2F;
/*     */   
/*     */   public FXSwarmRunes(World par1World, double x, double y, double z, Entity target, float r, float g, float b)
/*     */   {
/*  19 */     super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
/*  20 */     this.particleRed = r;
/*  21 */     this.particleGreen = g;
/*  22 */     this.particleBlue = b;
/*  23 */     this.particleScale = (this.rand.nextFloat() * 0.5F + 1.0F);
/*     */     
/*  25 */     this.target = target;
/*     */     
/*  27 */     float f3 = 0.2F;
/*  28 */     this.motionX = ((this.rand.nextFloat() - this.rand.nextFloat()) * f3);
/*  29 */     this.motionY = ((this.rand.nextFloat() - this.rand.nextFloat()) * f3);
/*  30 */     this.motionZ = ((this.rand.nextFloat() - this.rand.nextFloat()) * f3);
/*     */     
/*  32 */     this.particleGravity = 0.1F;
/*  33 */     this.canCollide = true;
/*     */   }
/*     */   
/*     */ 
/*     */   public FXSwarmRunes(World par1World, double x, double y, double z, Entity target, float r, float g, float b, float sp, float ts, float pg)
/*     */   {
/*  39 */     this(par1World, x, y, z, target, r, g, b);
/*  40 */     this.speed = sp;
/*     */     
/*  42 */     this.particleGravity = pg;
/*  43 */     this.particle = this.rand.nextInt(16);
/*     */   }
/*     */   
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  49 */     float bob = MathHelper.sin(this.particleAge / 3.0F) * 0.25F + 1.0F;
/*     */     
/*  51 */     org.lwjgl.opengl.GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
/*     */     
/*  53 */     float var8 = this.particle / 16.0F;
/*  54 */     float var9 = var8 + 0.0624375F;
/*  55 */     float var10 = 0.375F;
/*  56 */     float var11 = var10 + 0.0624375F;
/*  57 */     float var12 = 0.07F * this.particleScale * bob;
/*     */     
/*  59 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/*     */     
/*  61 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/*     */     
/*  63 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*     */     
/*  65 */     float var16 = 1.0F;
/*     */     
/*  67 */     float trans = (50.0F - this.deathtimer) / 50.0F * 0.66F;
/*     */     
/*  69 */     int i = 240;
/*  70 */     int j = i >> 16 & 0xFFFF;
/*  71 */     int k = i & 0xFFFF;
/*  72 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, trans).lightmap(j, k).endVertex();
/*     */     
/*  74 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, trans).lightmap(j, k).endVertex();
/*     */     
/*  76 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, trans).lightmap(j, k).endVertex();
/*     */     
/*  78 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, trans).lightmap(j, k).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFXLayer()
/*     */   {
/*  85 */     return 0;
/*     */   }
/*     */   
/*  88 */   int deathtimer = 0;
/*     */   
/*     */   public void onUpdate() {
/*  91 */     this.prevPosX = this.posX;
/*  92 */     this.prevPosY = this.posY;
/*  93 */     this.prevPosZ = this.posZ;
/*     */     
/*  95 */     this.particleAge += 1;
/*     */     
/*     */ 
/*     */ 
/*  99 */     if ((this.particleAge > 200) || (this.target == null) || (this.target.isDead) || (((this.target instanceof EntityLivingBase)) && (((EntityLivingBase)this.target).deathTime > 0))) {
/* 100 */       this.deathtimer += 1;
/* 101 */       this.motionX *= 0.9D;
/* 102 */       this.motionZ *= 0.9D;
/* 103 */       this.motionY -= this.particleGravity / 2.0F;
/* 104 */       if (this.deathtimer > 50) {
/* 105 */         setExpired();
/*     */       }
/*     */     } else {
/* 108 */       this.motionY += this.particleGravity;
/*     */     }
/*     */     
/* 111 */     pushOutOfBlocks(this.posX, this.posY, this.posZ);
/* 112 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/* 114 */     this.motionX *= 0.985D;
/* 115 */     this.motionY *= 0.985D;
/* 116 */     this.motionZ *= 0.985D;
/*     */     
/* 118 */     if ((this.particleAge < 200) && (this.target != null) && (!this.target.isDead) && ((!(this.target instanceof EntityLivingBase)) || (((EntityLivingBase)this.target).deathTime <= 0))) {
/* 119 */       boolean hurt = false;
/*     */       
/* 121 */       if ((this.target instanceof EntityLivingBase))
/* 122 */         hurt = ((EntityLivingBase)this.target).hurtTime > 0;
/* 123 */       if ((getDistanceSqToEntity(this.target) > this.target.width * this.target.width) && (!hurt)) {
/* 124 */         faceEntity(this.target, this.turnSpeed / 2.0F + this.rand.nextInt((int)(this.turnSpeed / 2.0F)), this.turnSpeed / 2.0F + this.rand.nextInt((int)(this.turnSpeed / 2.0F)));
/*     */       }
/*     */       else
/*     */       {
/* 128 */         if ((hurt) && (getDistanceSqToEntity(this.target) < this.target.width * this.target.width)) this.particleAge += 100;
/* 129 */         faceEntity(this.target, -(this.turnSpeed / 2.0F + this.rand.nextInt((int)(this.turnSpeed / 2.0F))), -(this.turnSpeed / 2.0F + this.rand.nextInt((int)(this.turnSpeed / 2.0F))));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 135 */       this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/*     */       
/*     */ 
/* 138 */       this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/*     */       
/*     */ 
/* 141 */       this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F));
/*     */       
/* 143 */       setHeading(this.motionX, this.motionY, this.motionZ, this.speed, 15.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void faceEntity(Entity par1Entity, float par2, float par3)
/*     */   {
/* 150 */     double d0 = par1Entity.posX - this.posX;
/* 151 */     double d1 = par1Entity.posZ - this.posZ;
/* 152 */     double d2 = (par1Entity.getEntityBoundingBox().minY + par1Entity.getEntityBoundingBox().maxY) / 2.0D - (getEntityBoundingBox().minY + getEntityBoundingBox().maxY) / 2.0D;
/*     */     
/*     */ 
/* 155 */     double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
/* 156 */     float f2 = (float)(Math.atan2(d1, d0) * 180.0D / 3.141592653589793D) - 90.0F;
/* 157 */     float f3 = (float)-(Math.atan2(d2, d3) * 180.0D / 3.141592653589793D);
/* 158 */     this.rotationPitch = updateRotation(this.rotationPitch, f3, par3);
/* 159 */     this.rotationYaw = updateRotation(this.rotationYaw, f2, par2);
/*     */   }
/*     */   
/*     */   private float updateRotation(float par1, float par2, float par3) {
/* 163 */     float f3 = MathHelper.wrapAngleTo180_float(par2 - par1);
/*     */     
/* 165 */     if (f3 > par3) {
/* 166 */       f3 = par3;
/*     */     }
/*     */     
/* 169 */     if (f3 < -par3) {
/* 170 */       f3 = -par3;
/*     */     }
/*     */     
/* 173 */     return par1 + f3;
/*     */   }
/*     */   
/*     */   public void setHeading(double par1, double par3, double par5, float par7, float par8)
/*     */   {
/* 178 */     float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
/*     */     
/* 180 */     par1 /= f2;
/* 181 */     par3 /= f2;
/* 182 */     par5 /= f2;
/* 183 */     par1 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/*     */     
/*     */ 
/* 186 */     par3 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/*     */     
/*     */ 
/* 189 */     par5 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/*     */     
/*     */ 
/* 192 */     par1 *= par7;
/* 193 */     par3 *= par7;
/* 194 */     par5 *= par7;
/* 195 */     this.motionX = par1;
/* 196 */     this.motionY = par3;
/* 197 */     this.motionZ = par5;
/*     */   }
/*     */   
/* 200 */   public int particle = 0;
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXSwarmRunes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */