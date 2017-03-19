/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class FXBubble extends Particle
/*     */ {
/*  14 */   public int particle = 16;
/*     */   
/*     */   public FXBubble(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, int age)
/*     */   {
/*  18 */     super(par1World, par2, par4, par6, par8, par10, par12);
/*  19 */     this.particleRed = 1.0F;
/*  20 */     this.particleGreen = 0.0F;
/*  21 */     this.particleBlue = 0.5F;
/*  22 */     setSize(0.02F, 0.02F);
/*  23 */     this.canCollide = false;
/*  24 */     this.particleScale *= (this.rand.nextFloat() * 0.3F + 0.2F);
/*  25 */     this.motionX = (par8 * 0.20000000298023224D + (float)(Math.random() * 2.0D - 1.0D) * 0.02F);
/*  26 */     this.motionY = (par10 * 0.20000000298023224D + (float)Math.random() * 0.02F);
/*  27 */     this.motionZ = (par12 * 0.20000000298023224D + (float)(Math.random() * 2.0D - 1.0D) * 0.02F);
/*  28 */     this.particleMaxAge = ((int)(age + 2 + 8.0D / (Math.random() * 0.8D + 0.2D)));
/*     */     
/*  30 */     Entity renderentity = Minecraft.getMinecraft().getRenderViewEntity();
/*  31 */     int visibleDistance = 50;
/*  32 */     if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics) visibleDistance = 25;
/*  33 */     if (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance) { this.particleMaxAge = 0;
/*     */     }
/*  35 */     this.prevPosX = this.posX;
/*  36 */     this.prevPosY = this.posY;
/*  37 */     this.prevPosZ = this.posZ;
/*     */   }
/*     */   
/*     */   public void setFroth() {
/*  41 */     this.particleScale *= 0.75F;
/*  42 */     this.particleMaxAge = (4 + this.rand.nextInt(3));
/*  43 */     this.bubblespeed = -0.001D;
/*  44 */     this.motionX /= 5.0D;
/*  45 */     this.motionY /= 10.0D;
/*  46 */     this.motionZ /= 5.0D;
/*     */   }
/*     */   
/*     */   public void setFroth2() {
/*  50 */     this.particleScale *= 0.75F;
/*  51 */     this.particleMaxAge = (12 + this.rand.nextInt(12));
/*  52 */     this.bubblespeed = -0.005D;
/*  53 */     this.motionX /= 5.0D;
/*  54 */     this.motionY /= 10.0D;
/*  55 */     this.motionZ /= 5.0D;
/*     */   }
/*     */   
/*  58 */   public double bubblespeed = 0.002D;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  66 */     this.prevPosX = this.posX;
/*  67 */     this.prevPosY = this.posY;
/*  68 */     this.prevPosZ = this.posZ;
/*  69 */     this.motionY += this.bubblespeed;
/*  70 */     if (this.bubblespeed > 0.0D) {
/*  71 */       this.motionX += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.01F;
/*  72 */       this.motionZ += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.01F;
/*     */     }
/*     */     
/*  75 */     this.posX += this.motionX;
/*  76 */     this.posY += this.motionY;
/*  77 */     this.posZ += this.motionZ;
/*     */     
/*  79 */     this.motionX *= 0.8500000238418579D;
/*  80 */     this.motionY *= 0.8500000238418579D;
/*  81 */     this.motionZ *= 0.8500000238418579D;
/*     */     
/*  83 */     if (this.particleMaxAge-- <= 0)
/*     */     {
/*  85 */       setExpired();
/*     */ 
/*     */     }
/*  88 */     else if (this.particleMaxAge <= 2) {
/*  89 */       this.particle += 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setRGB(float r, float g, float b) {
/*  94 */     this.particleRed = r;
/*  95 */     this.particleGreen = g;
/*  96 */     this.particleBlue = b;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 103 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, this.particleAlpha);
/*     */     
/* 105 */     float var8 = this.particle % 16 / 16.0F;
/* 106 */     float var9 = var8 + 0.0624375F;
/* 107 */     float var10 = this.particle / 16 / 16.0F;
/* 108 */     float var11 = var10 + 0.0624375F;
/* 109 */     float var12 = 0.1F * this.particleScale;
/* 110 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/* 111 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/* 112 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/* 113 */     float var16 = 1.0F;
/*     */     
/* 115 */     int i = 240;
/* 116 */     int j = i >> 16 & 0xFFFF;
/* 117 */     int k = i & 0xFFFF;
/*     */     
/* 119 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha).lightmap(j, k).endVertex();
/*     */     
/* 121 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha).lightmap(j, k).endVertex();
/*     */     
/* 123 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha).lightmap(j, k).endVertex();
/*     */     
/* 125 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha).lightmap(j, k).endVertex();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXBubble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */