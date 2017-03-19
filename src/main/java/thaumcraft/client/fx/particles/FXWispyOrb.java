/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class FXWispyOrb
/*     */   extends Particle
/*     */ {
/*  14 */   public int particle = 128;
/*  15 */   float as = 0.0F;
/*     */   
/*     */   public FXWispyOrb(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, int age)
/*     */   {
/*  19 */     super(par1World, par2, par4, par6, par8, par10, par12);
/*  20 */     this.particleRed = (0.25F + this.rand.nextFloat() * 0.75F);
/*  21 */     this.particleGreen = (0.25F + this.rand.nextFloat() * 0.75F);
/*  22 */     this.particleBlue = (0.25F + this.rand.nextFloat() * 0.75F);
/*  23 */     setSize(0.02F, 0.02F);
/*  24 */     this.canCollide = false;
/*  25 */     this.particleScale *= (this.rand.nextFloat() * 0.5F + 0.3F);
/*  26 */     this.motionX = (par8 * 0.20000000298023224D + (float)(Math.random() * 2.0D - 1.0D) * 0.02F);
/*  27 */     this.motionY = (par10 * 0.20000000298023224D + (float)Math.random() * 0.02F);
/*  28 */     this.motionZ = (par12 * 0.20000000298023224D + (float)(Math.random() * 2.0D - 1.0D) * 0.02F);
/*  29 */     this.particleMaxAge = ((int)(age + age / 2 * this.rand.nextFloat()));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  36 */     this.prevPosX = this.posX;
/*  37 */     this.prevPosY = this.posY;
/*  38 */     this.prevPosZ = this.posZ;
/*     */   }
/*     */   
/*  41 */   public double bubblespeed = 0.002D;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  49 */     this.prevPosX = this.posX;
/*  50 */     this.prevPosY = this.posY;
/*  51 */     this.prevPosZ = this.posZ;
/*  52 */     this.motionY += this.bubblespeed;
/*  53 */     if (this.bubblespeed > 0.0D) {
/*  54 */       this.motionX += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.01F;
/*  55 */       this.motionZ += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.01F;
/*     */     }
/*     */     
/*  58 */     this.posX += this.motionX;
/*  59 */     this.posY += this.motionY;
/*  60 */     this.posZ += this.motionZ;
/*     */     
/*  62 */     this.motionX *= 0.8500000238418579D;
/*  63 */     this.motionY *= 0.8500000238418579D;
/*  64 */     this.motionZ *= 0.8500000238418579D;
/*     */     
/*  66 */     if (this.particleMaxAge-- <= 0)
/*     */     {
/*  68 */       setExpired();
/*     */     }
/*     */     
/*  71 */     this.particle += 1;
/*     */     
/*  73 */     this.particleAlpha -= this.as;
/*     */     
/*  75 */     if (this.particle >= 144) this.particle = 128;
/*     */   }
/*     */   
/*     */   public void setRGB(float r, float g, float b) {
/*  79 */     this.particleRed = r;
/*  80 */     this.particleGreen = g;
/*  81 */     this.particleBlue = b;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAlphaF(float alpha)
/*     */   {
/*  88 */     super.setAlphaF(alpha);
/*  89 */     this.as = (this.particleAlpha / this.particleMaxAge);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  96 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, this.particleAlpha);
/*     */     
/*  98 */     float var8 = this.particle % 16 / 16.0F;
/*  99 */     float var9 = var8 + 0.0624375F;
/* 100 */     float var10 = this.particle / 16 / 16.0F;
/* 101 */     float var11 = var10 + 0.0624375F;
/* 102 */     float var12 = 0.1F * this.particleScale;
/* 103 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/* 104 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/* 105 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/* 106 */     float var16 = 1.0F;
/*     */     
/* 108 */     int i = 240;
/* 109 */     int j = i >> 16 & 0xFFFF;
/* 110 */     int k = i & 0xFFFF;
/*     */     
/* 112 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha).lightmap(j, k).endVertex();
/*     */     
/* 114 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha).lightmap(j, k).endVertex();
/*     */     
/* 116 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha).lightmap(j, k).endVertex();
/*     */     
/* 118 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha).lightmap(j, k).endVertex();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXWispyOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */