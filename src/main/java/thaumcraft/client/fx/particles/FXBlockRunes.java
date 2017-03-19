/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class FXBlockRunes extends Particle
/*     */ {
/*     */   public FXBlockRunes(net.minecraft.world.World world, double d, double d1, double d2, float f1, float f2, float f3, int m)
/*     */   {
/*  15 */     super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
/*  16 */     if (f1 == 0.0F) { f1 = 1.0F;
/*     */     }
/*  18 */     this.rotation = (this.rand.nextInt(4) * 90);
/*     */     
/*  20 */     this.particleRed = f1;
/*  21 */     this.particleGreen = f2;
/*  22 */     this.particleBlue = f3;
/*  23 */     this.particleGravity = 0.0F;
/*  24 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/*  25 */     this.particleMaxAge = (3 * m);
/*  26 */     this.canCollide = true;
/*  27 */     setSize(0.01F, 0.01F);
/*  28 */     this.prevPosX = this.posX;
/*  29 */     this.prevPosY = this.posY;
/*  30 */     this.prevPosZ = this.posZ;
/*  31 */     this.canCollide = false;
/*  32 */     this.runeIndex = ((int)(Math.random() * 16.0D + 224.0D));
/*  33 */     this.ofx = (this.rand.nextFloat() * 0.2D);
/*  34 */     this.ofy = (-0.3D + this.rand.nextFloat() * 0.6D);
/*  35 */     this.particleScale = ((float)(1.0D + this.rand.nextGaussian() * 0.10000000149011612D));
/*  36 */     this.particleAlpha = 0.0F;
/*     */   }
/*     */   
/*  39 */   double ofx = 0.0D;
/*  40 */   double ofy = 0.0D;
/*  41 */   float rotation = 0.0F;
/*  42 */   int runeIndex = 0;
/*     */   
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity p_180434_2_, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  47 */     Tessellator.getInstance().draw();
/*  48 */     GL11.glPushMatrix();
/*     */     
/*  50 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, this.particleAlpha / 2.0F);
/*     */     
/*  52 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/*  53 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/*  54 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*     */     
/*  56 */     GL11.glTranslated(var13, var14, var15);
/*  57 */     GL11.glRotatef(this.rotation, 0.0F, 1.0F, 0.0F);
/*  58 */     GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  59 */     GL11.glTranslated(this.ofx, this.ofy, -0.51D);
/*     */     
/*  61 */     float var8 = this.runeIndex % 16 / 16.0F;
/*  62 */     float var9 = var8 + 0.0624375F;
/*  63 */     float var10 = 0.375F;
/*  64 */     float var11 = var10 + 0.0624375F;
/*  65 */     float var12 = 0.3F * this.particleScale;
/*     */     
/*  67 */     float var16 = 1.0F;
/*     */     
/*  69 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */     
/*  71 */     int i = 240;
/*  72 */     int j = i >> 16 & 0xFFFF;
/*  73 */     int k = i & 0xFFFF;
/*     */     
/*  75 */     wr.pos(-0.5D * var12, 0.5D * var12, 0.0D).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/*  76 */     wr.pos(0.5D * var12, 0.5D * var12, 0.0D).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/*  77 */     wr.pos(0.5D * var12, -0.5D * var12, 0.0D).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/*  78 */     wr.pos(-0.5D * var12, -0.5D * var12, 0.0D).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/*  79 */     Tessellator.getInstance().draw();
/*     */     
/*  81 */     GL11.glPopMatrix();
/*  82 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  89 */     this.prevPosX = this.posX;
/*  90 */     this.prevPosY = this.posY;
/*  91 */     this.prevPosZ = this.posZ;
/*  92 */     float threshold = this.particleMaxAge / 5.0F;
/*     */     
/*  94 */     if (this.particleAge <= threshold) {
/*  95 */       this.particleAlpha = (this.particleAge / threshold);
/*     */     } else {
/*  97 */       this.particleAlpha = ((this.particleMaxAge - this.particleAge) / this.particleMaxAge);
/*     */     }
/*     */     
/* 100 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 101 */       setExpired();
/*     */     }
/*     */     
/* 104 */     this.motionY -= 0.04D * this.particleGravity;
/*     */     
/* 106 */     this.posX += this.motionX;
/* 107 */     this.posY += this.motionY;
/* 108 */     this.posZ += this.motionZ;
/*     */   }
/*     */   
/*     */   public void setGravity(float value)
/*     */   {
/* 113 */     this.particleGravity = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXBlockRunes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */