/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class FXVent extends Particle
/*     */ {
/*     */   public FXVent(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, int color)
/*     */   {
/*  19 */     super(par1World, par2, par4, par6, par8, par10, par12);
/*  20 */     setSize(0.02F, 0.02F);
/*  21 */     this.particleScale = (this.rand.nextFloat() * 0.1F + 0.05F);
/*  22 */     this.motionX = par8;
/*  23 */     this.motionY = par10;
/*  24 */     this.motionZ = par12;
/*  25 */     this.canCollide = false;
/*     */     
/*  27 */     Color c = new Color(color);
/*  28 */     this.particleRed = (c.getRed() / 255.0F);
/*  29 */     this.particleBlue = (c.getBlue() / 255.0F);
/*  30 */     this.particleGreen = (c.getGreen() / 255.0F);
/*     */     
/*  32 */     setHeading(this.motionX, this.motionY, this.motionZ, 0.125F, 5.0F);
/*     */     
/*  34 */     Entity renderentity = FMLClientHandler.instance().getClient().getRenderViewEntity();
/*  35 */     int visibleDistance = 50;
/*  36 */     if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics) visibleDistance = 25;
/*  37 */     if (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance) { this.particleMaxAge = 0;
/*     */     }
/*  39 */     this.prevPosX = this.posX;
/*  40 */     this.prevPosY = this.posY;
/*  41 */     this.prevPosZ = this.posZ;
/*     */   }
/*     */   
/*  44 */   float psm = 1.0F;
/*     */   
/*  46 */   public void setScale(float f) { this.particleScale *= f;
/*  47 */     this.psm *= f;
/*     */   }
/*     */   
/*     */   public void setHeading(double par1, double par3, double par5, float par7, float par8)
/*     */   {
/*  52 */     float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
/*  53 */     par1 /= f2;
/*  54 */     par3 /= f2;
/*  55 */     par5 /= f2;
/*  56 */     par1 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/*  57 */     par3 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/*  58 */     par5 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/*  59 */     par1 *= par7;
/*  60 */     par3 *= par7;
/*  61 */     par5 *= par7;
/*  62 */     this.motionX = par1;
/*  63 */     this.motionY = par3;
/*  64 */     this.motionZ = par5;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  72 */     this.prevPosX = this.posX;
/*  73 */     this.prevPosY = this.posY;
/*  74 */     this.prevPosZ = this.posZ;
/*  75 */     this.particleAge += 1;
/*  76 */     if (this.particleScale >= this.psm)
/*     */     {
/*  78 */       setExpired();
/*     */     }
/*     */     
/*  81 */     this.motionY += 0.0025D;
/*  82 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  83 */     this.motionX *= 0.8500000190734863D;
/*  84 */     this.motionY *= 0.8500000190734863D;
/*  85 */     this.motionZ *= 0.8500000190734863D;
/*  86 */     if (this.particleScale < this.psm) this.particleScale = ((float)(this.particleScale * 1.15D));
/*  87 */     if (this.particleScale > this.psm) this.particleScale = this.psm;
/*  88 */     if (this.onGround)
/*     */     {
/*  90 */       this.motionX *= 0.699999988079071D;
/*  91 */       this.motionZ *= 0.699999988079071D;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setRGB(float r, float g, float b) {
/*  96 */     this.particleRed = r;
/*  97 */     this.particleGreen = g;
/*  98 */     this.particleBlue = b;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 105 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.33F);
/* 106 */     int part = (int)(1.0F + this.particleScale / this.psm * 4.0F);
/* 107 */     float var8 = part % 16 / 16.0F;
/* 108 */     float var9 = var8 + 0.0624375F;
/* 109 */     float var10 = part / 16 / 16.0F;
/* 110 */     float var11 = var10 + 0.0624375F;
/* 111 */     float var12 = 0.3F * this.particleScale;
/*     */     
/* 113 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/* 114 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/* 115 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/* 116 */     float var16 = 1.0F;
/*     */     
/* 118 */     int i = getBrightnessForRender(f);
/* 119 */     int j = i >> 16 & 0xFFFF;
/* 120 */     int k = i & 0xFFFF;
/*     */     
/* 122 */     float alpha = this.particleAlpha * ((this.psm - this.particleScale) / this.psm);
/* 123 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, alpha).lightmap(j, k).endVertex();
/*     */     
/* 125 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, alpha).lightmap(j, k).endVertex();
/*     */     
/* 127 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, alpha).lightmap(j, k).endVertex();
/*     */     
/* 129 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, alpha).lightmap(j, k).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getFXLayer()
/*     */   {
/* 137 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXVent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */