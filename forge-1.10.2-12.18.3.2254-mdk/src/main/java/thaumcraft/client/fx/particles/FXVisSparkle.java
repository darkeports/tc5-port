/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class FXVisSparkle extends Particle
/*     */ {
/*     */   private double targetX;
/*     */   private double targetY;
/*     */   private double targetZ;
/*     */   
/*     */   public FXVisSparkle(World par1World, double par2, double par4, double par6, double tx, double ty, double tz)
/*     */   {
/*  18 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/*  19 */     this.particleRed = (this.particleGreen = this.particleBlue = 0.6F);
/*  20 */     this.particleScale = 0.0F;
/*     */     
/*  22 */     this.targetX = tx;
/*  23 */     this.targetY = ty;
/*  24 */     this.targetZ = tz;
/*     */     
/*  26 */     this.particleMaxAge = 1000;
/*     */     
/*  28 */     float f3 = 0.01F;
/*  29 */     this.motionX = ((float)this.rand.nextGaussian() * f3);
/*  30 */     this.motionY = ((float)this.rand.nextGaussian() * f3);
/*  31 */     this.motionZ = ((float)this.rand.nextGaussian() * f3);
/*  32 */     this.sizeMod = (45 + this.rand.nextInt(15));
/*  33 */     this.particleRed = 0.2F;
/*  34 */     this.particleGreen = (0.6F + this.rand.nextFloat() * 0.3F);
/*  35 */     this.particleBlue = 0.2F;
/*     */     
/*  37 */     this.particleGravity = 0.2F;
/*  38 */     this.canCollide = false;
/*     */   }
/*     */   
/*     */ 
/*  42 */   float sizeMod = 0.0F;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity p_180434_2_, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  49 */     float bob = MathHelper.sin(this.particleAge / 3.0F) * 0.3F + 6.0F;
/*     */     
/*  51 */     org.lwjgl.opengl.GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
/*  52 */     int part = this.particleAge % 16;
/*  53 */     float var8 = part / 16.0F;
/*  54 */     float var9 = var8 + 0.0624375F;
/*  55 */     float var10 = 0.5F;
/*  56 */     float var11 = var10 + 0.0624375F;
/*     */     
/*  58 */     float var12 = 0.1F * this.particleScale * bob;
/*     */     
/*  60 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/*     */     
/*  62 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/*     */     
/*  64 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*     */     
/*  66 */     float var16 = 1.0F;
/*     */     
/*  68 */     int i = 240;
/*  69 */     int j = i >> 16 & 0xFFFF;
/*  70 */     int k = i & 0xFFFF;
/*     */     
/*  72 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.5F).lightmap(j, k).endVertex();
/*     */     
/*  74 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.5F).lightmap(j, k).endVertex();
/*     */     
/*  76 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.5F).lightmap(j, k).endVertex();
/*     */     
/*  78 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.5F).lightmap(j, k).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  85 */     this.prevPosX = this.posX;
/*  86 */     this.prevPosY = this.posY;
/*  87 */     this.prevPosZ = this.posZ;
/*     */     
/*  89 */     if (this.particleAge++ >= this.particleMaxAge) {
/*  90 */       setExpired();
/*  91 */       return;
/*     */     }
/*     */     
/*  94 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/*  96 */     this.motionX *= 0.985D;
/*  97 */     this.motionY *= 0.985D;
/*  98 */     this.motionZ *= 0.985D;
/*     */     
/* 100 */     double dx = this.targetX - this.posX;
/* 101 */     double dy = this.targetY - this.posY;
/* 102 */     double dz = this.targetZ - this.posZ;
/* 103 */     double d13 = 0.10000000149011612D;
/* 104 */     double d11 = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
/* 105 */     if (d11 < 2.0D) this.particleScale *= 0.95F;
/* 106 */     if (d11 < 0.2D) this.particleMaxAge = this.particleAge;
/* 107 */     if (this.particleAge < 10) this.particleScale = (this.particleAge / this.sizeMod);
/* 108 */     dx /= d11;
/* 109 */     dy /= d11;
/* 110 */     dz /= d11;
/*     */     
/* 112 */     this.motionX += dx * d13;
/* 113 */     this.motionY += dy * d13;
/* 114 */     this.motionZ += dz * d13;
/*     */     
/* 116 */     this.motionX = MathHelper.clamp_float((float)this.motionX, -0.1F, 0.1F);
/* 117 */     this.motionY = MathHelper.clamp_float((float)this.motionY, -0.1F, 0.1F);
/* 118 */     this.motionZ = MathHelper.clamp_float((float)this.motionZ, -0.1F, 0.1F);
/*     */   }
/*     */   
/*     */   public void setGravity(float value) {
/* 122 */     this.particleGravity = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXVisSparkle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */