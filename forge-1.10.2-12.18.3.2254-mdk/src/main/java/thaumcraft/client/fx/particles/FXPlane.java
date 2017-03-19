/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class FXPlane extends Particle
/*     */ {
/*     */   float angle;
/*     */   float angleYaw;
/*     */   float anglePitch;
/*     */   
/*     */   public FXPlane(net.minecraft.world.World world, double d, double d1, double d2, double m, double m1, double m2, int life)
/*     */   {
/*  19 */     super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */     
/*  21 */     this.particleRed = 1.0F;
/*  22 */     this.particleGreen = 1.0F;
/*  23 */     this.particleBlue = 1.0F;
/*  24 */     this.particleGravity = 0.0F;
/*  25 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/*  26 */     this.particleMaxAge = life;
/*  27 */     this.canCollide = true;
/*  28 */     setSize(0.01F, 0.01F);
/*  29 */     this.prevPosX = this.posX;
/*  30 */     this.prevPosY = this.posY;
/*  31 */     this.prevPosZ = this.posZ;
/*  32 */     this.canCollide = false;
/*  33 */     this.particleScale = 1.0F;
/*  34 */     this.particleAlpha = 0.0F;
/*     */     
/*     */ 
/*  37 */     double dx = m - this.posX;
/*  38 */     double dy = m1 - this.posY;
/*  39 */     double dz = m2 - this.posZ;
/*     */     
/*  41 */     this.motionX = (dx / this.particleMaxAge);
/*  42 */     this.motionY = (dy / this.particleMaxAge);
/*  43 */     this.motionZ = (dz / this.particleMaxAge);
/*     */     
/*  45 */     this.particleTextureIndexX = 6;
/*  46 */     this.particleTextureIndexY = 10;
/*     */     
/*  48 */     double d3 = MathHelper.sqrt_double(dx * dx + dz * dz);
/*  49 */     this.angleYaw = 0.0F;
/*  50 */     this.anglePitch = 0.0F;
/*  51 */     if (d3 >= 1.0E-7D)
/*     */     {
/*  53 */       this.angleYaw = ((float)(MathHelper.atan2(dz, dx) * 180.0D / 3.141592653589793D) - 90.0F);
/*  54 */       this.anglePitch = ((float)-(MathHelper.atan2(dy, d3) * 180.0D / 3.141592653589793D));
/*     */     }
/*     */     
/*  57 */     this.angle = ((float)(this.rand.nextGaussian() * 20.0D));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getFXLayer()
/*     */   {
/*  68 */     return 2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity p_180434_2_, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  75 */     Tessellator.getInstance().draw();
/*  76 */     GL11.glPushMatrix();
/*     */     
/*  78 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, this.particleAlpha / 2.0F);
/*     */     
/*  80 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/*  81 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/*  82 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*     */     
/*  84 */     GL11.glTranslated(var13, var14, var15);
/*  85 */     GL11.glRotatef(-this.angleYaw + 90.0F, 0.0F, 1.0F, 0.0F);
/*  86 */     GL11.glRotatef(this.anglePitch + 90.0F, 0.0F, 0.0F, 1.0F);
/*  87 */     GL11.glRotatef(this.angle, 0.0F, 1.0F, 0.0F);
/*     */     
/*     */ 
/*  90 */     this.particleTextureIndexX = Math.round((this.particleAge + f) / this.particleMaxAge * 8.0F);
/*     */     
/*  92 */     float var8 = this.particleTextureIndexX / 16.0F;
/*  93 */     float var9 = var8 + 0.0625F;
/*  94 */     float var10 = this.particleTextureIndexY / 16.0F;
/*  95 */     float var11 = var10 + 0.0625F;
/*     */     
/*  97 */     float var12 = this.particleScale * (0.5F + (this.particleAge + f) / this.particleMaxAge);
/*     */     
/*  99 */     float var16 = 1.0F;
/*     */     
/*     */ 
/*     */ 
/* 103 */     int i = 240;
/* 104 */     int j = i >> 16 & 0xFFFF;
/* 105 */     int k = i & 0xFFFF;
/*     */     
/* 107 */     GL11.glDisable(2884);
/*     */     
/* 109 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/* 110 */     wr.pos(-0.5D * var12, 0.5D * var12, 0.0D).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/* 111 */     wr.pos(0.5D * var12, 0.5D * var12, 0.0D).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/* 112 */     wr.pos(0.5D * var12, -0.5D * var12, 0.0D).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/* 113 */     wr.pos(-0.5D * var12, -0.5D * var12, 0.0D).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/* 114 */     Tessellator.getInstance().draw();
/*     */     
/* 116 */     GL11.glEnable(2884);
/*     */     
/* 118 */     GL11.glPopMatrix();
/* 119 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 126 */     this.prevPosX = this.posX;
/* 127 */     this.prevPosY = this.posY;
/* 128 */     this.prevPosZ = this.posZ;
/* 129 */     float threshold = this.particleMaxAge / 5.0F;
/*     */     
/* 131 */     if (this.particleAge <= threshold) {
/* 132 */       this.particleAlpha = (this.particleAge / threshold);
/*     */     } else {
/* 134 */       this.particleAlpha = ((this.particleMaxAge - this.particleAge) / this.particleMaxAge);
/*     */     }
/*     */     
/* 137 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 138 */       setExpired();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 143 */     this.posX += this.motionX;
/* 144 */     this.posY += this.motionY;
/* 145 */     this.posZ += this.motionZ;
/*     */   }
/*     */   
/*     */   public void setGravity(float value)
/*     */   {
/* 150 */     this.particleGravity = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXPlane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */