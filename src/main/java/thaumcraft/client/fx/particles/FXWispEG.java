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
/*     */ public class FXWispEG extends Particle
/*     */ {
/*  14 */   Entity target = null;
/*  15 */   double rx = 0.0D;
/*  16 */   double ry = 0.0D;
/*  17 */   double rz = 0.0D;
/*     */   
/*     */   public FXWispEG(World worldObj, double posX, double posY, double posZ, Entity target2)
/*     */   {
/*  21 */     super(worldObj, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
/*     */     
/*  23 */     this.target = target2;
/*     */     
/*  25 */     this.motionX = (this.rand.nextGaussian() * 0.03D);
/*  26 */     this.motionY = -0.05D;
/*  27 */     this.motionZ = (this.rand.nextGaussian() * 0.03D);
/*     */     
/*  29 */     this.particleScale *= 0.4F;
/*  30 */     this.particleMaxAge = ((int)(40.0D / (Math.random() * 0.3D + 0.7D)));
/*     */     
/*  32 */     this.canCollide = true;
/*  33 */     setSize(0.01F, 0.01F);
/*  34 */     Entity renderentity = FMLClientHandler.instance().getClient().getRenderViewEntity();
/*  35 */     int visibleDistance = 50;
/*  36 */     if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics)
/*  37 */       visibleDistance = 25;
/*  38 */     if (renderentity.getDistance(posX, posY, posZ) > visibleDistance)
/*  39 */       this.particleMaxAge = 0;
/*  40 */     this.prevPosX = posX;
/*  41 */     this.prevPosY = posY;
/*  42 */     this.prevPosZ = posZ;
/*  43 */     this.blendmode = 771;
/*  44 */     this.particleRed = (this.rand.nextFloat() * 0.05F);
/*  45 */     this.particleGreen = (this.rand.nextFloat() * 0.05F);
/*  46 */     this.particleBlue = (this.rand.nextFloat() * 0.05F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  52 */     Entity e = Minecraft.getMinecraft().getRenderViewEntity();
/*  53 */     float agescale = 1.0F - this.particleAge / this.particleMaxAge;
/*  54 */     float d6 = 1024.0F;
/*  55 */     float base = (float)(1.0D - Math.min(d6, getDistanceSq(e.posX, e.posY, e.posZ)) / d6);
/*     */     
/*  57 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F * base);
/*     */     
/*  59 */     float f10 = 0.5F * this.particleScale;
/*  60 */     float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/*  61 */     float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/*  62 */     float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*     */     
/*  64 */     float var8 = this.particleAge % 13 / 16.0F;
/*  65 */     float var9 = var8 + 0.0624375F;
/*  66 */     float var10 = 0.1875F;
/*  67 */     float var11 = var10 + 0.0624375F;
/*     */     
/*  69 */     int i = 240;
/*  70 */     int j = i >> 16 & 0xFFFF;
/*  71 */     int k = i & 0xFFFF;
/*     */     
/*  73 */     wr.pos(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10).tex(var9, var11).color(this.particleRed, this.particleGreen, this.particleBlue, 0.2F * agescale * base).lightmap(j, k).endVertex();
/*  74 */     wr.pos(f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10).tex(var9, var10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.2F * agescale * base).lightmap(j, k).endVertex();
/*  75 */     wr.pos(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10).tex(var8, var10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.2F * agescale * base).lightmap(j, k).endVertex();
/*  76 */     wr.pos(f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10).tex(var8, var11).color(this.particleRed, this.particleGreen, this.particleBlue, 0.2F * agescale * base).lightmap(j, k).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */   public int getFXLayer()
/*     */   {
/*  82 */     return this.blendmode == 1 ? 0 : 1;
/*     */   }
/*     */   
/*     */   public void onUpdate()
/*     */   {
/*  87 */     this.prevPosX = this.posX;
/*  88 */     this.prevPosY = this.posY;
/*  89 */     this.prevPosZ = this.posZ;
/*     */     
/*  91 */     if ((this.target != null) && (!this.onGround)) {
/*  92 */       this.posX += this.target.motionX;
/*  93 */       this.posZ += this.target.motionZ;
/*     */     }
/*     */     
/*     */ 
/*  97 */     pushOutOfBlocks(this.posX, this.posY, this.posZ);
/*  98 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/* 100 */     this.motionX *= 0.9800000190734863D;
/* 101 */     this.motionY *= 0.9800000190734863D;
/* 102 */     this.motionZ *= 0.9800000190734863D;
/* 103 */     if (this.onGround) {
/* 104 */       this.motionX *= 0.8500000190734863D;
/* 105 */       this.motionZ *= 0.8500000190734863D;
/*     */     }
/*     */     
/*     */ 
/* 109 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/* 111 */       setExpired();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGravity(float value)
/*     */   {
/* 211 */     this.particleGravity = value;
/*     */   }
/*     */   
/* 214 */   public int blendmode = 1;
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXWispEG.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */