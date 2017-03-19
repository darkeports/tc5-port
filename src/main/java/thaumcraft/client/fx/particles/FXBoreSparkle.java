/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ 
/*     */ public class FXBoreSparkle extends Particle
/*     */ {
/*     */   private double targetX;
/*     */   private double targetY;
/*     */   private double targetZ;
/*     */   
/*     */   public FXBoreSparkle(net.minecraft.world.World par1World, double par2, double par4, double par6, double tx, double ty, double tz)
/*     */   {
/*  19 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/*  20 */     this.particleRed = (this.particleGreen = this.particleBlue = 0.6F);
/*  21 */     this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F);
/*     */     
/*  23 */     this.targetX = tx;
/*  24 */     this.targetY = ty;
/*  25 */     this.targetZ = tz;
/*     */     
/*  27 */     double dx = tx - this.posX;
/*  28 */     double dy = ty - this.posY;
/*  29 */     double dz = tz - this.posZ;
/*  30 */     int base = (int)(MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz) * 3.0F);
/*  31 */     if (base < 1)
/*  32 */       base = 1;
/*  33 */     this.particleMaxAge = (base / 2 + this.rand.nextInt(base));
/*     */     
/*  35 */     float f3 = 0.01F;
/*  36 */     this.motionX = ((float)this.rand.nextGaussian() * f3);
/*  37 */     this.motionY = ((float)this.rand.nextGaussian() * f3);
/*  38 */     this.motionZ = ((float)this.rand.nextGaussian() * f3);
/*     */     
/*  40 */     this.particleRed = 0.2F;
/*  41 */     this.particleGreen = (0.6F + this.rand.nextFloat() * 0.3F);
/*  42 */     this.particleBlue = 0.2F;
/*     */     
/*  44 */     this.particleGravity = 0.2F;
/*  45 */     this.canCollide = true;
/*     */     
/*  47 */     Entity renderentity = FMLClientHandler.instance().getClient().getRenderViewEntity();
/*  48 */     int visibleDistance = 64;
/*  49 */     if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics)
/*  50 */       visibleDistance = 32;
/*  51 */     if (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance) {
/*  52 */       this.particleMaxAge = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
/*  57 */     float bob = MathHelper.sin(this.particleAge / 3.0F) * 0.5F + 1.0F;
/*     */     
/*  59 */     org.lwjgl.opengl.GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
/*  60 */     int part = this.particleAge % 4;
/*     */     
/*  62 */     float var8 = part / 16.0F;
/*  63 */     float var9 = var8 + 0.0624375F;
/*  64 */     float var10 = 0.25F;
/*  65 */     float var11 = var10 + 0.0624375F;
/*  66 */     float var12 = 0.1F * this.particleScale * bob;
/*     */     
/*  68 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/*     */     
/*  70 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/*     */     
/*  72 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*     */     
/*  74 */     float var16 = 1.0F;
/*     */     
/*  76 */     int i = 240;
/*  77 */     int j = i >> 16 & 0xFFFF;
/*  78 */     int k = i & 0xFFFF;
/*     */     
/*  80 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */     
/*  82 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */     
/*  84 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */     
/*  86 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  92 */     this.prevPosX = this.posX;
/*  93 */     this.prevPosY = this.posY;
/*  94 */     this.prevPosZ = this.posZ;
/*     */     
/*  96 */     if ((this.particleAge++ >= this.particleMaxAge) || ((MathHelper.floor_double(this.posX) == MathHelper.floor_double(this.targetX)) && (MathHelper.floor_double(this.posY) == MathHelper.floor_double(this.targetY)) && (MathHelper.floor_double(this.posZ) == MathHelper.floor_double(this.targetZ))))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 102 */       setExpired();
/* 103 */       return;
/*     */     }
/*     */     
/*     */ 	//TODO:???
/* 107 */     if (this.canCollide)
/* 108 */       pushOutOfBlocks(this.posX, this.posY, this.posZ);
/* 109 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/* 111 */     this.motionX *= 0.985D;
/* 112 */     this.motionY *= 0.985D;
/* 113 */     this.motionZ *= 0.985D;
/*     */     
/* 115 */     double dx = this.targetX - this.posX;
/* 116 */     double dy = this.targetY - this.posY;
/* 117 */     double dz = this.targetZ - this.posZ;
/* 118 */     double d13 = 0.3D;
/* 119 */     double d11 = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
/*     */     
/* 121 */     if (d11 < 4.0D) {
/* 122 */       this.particleScale *= 0.9F;
/* 123 */       d13 = 0.6D;
/*     */     }
/*     */     
/* 126 */     dx /= d11;
/* 127 */     dy /= d11;
/* 128 */     dz /= d11;
/*     */     
/* 130 */     this.motionX += dx * d13;
/* 131 */     this.motionY += dy * d13;
/* 132 */     this.motionZ += dz * d13;
/*     */     
/* 134 */     this.motionX = MathHelper.clamp_float((float)this.motionX, -0.35F, 0.35F);
/* 135 */     this.motionY = MathHelper.clamp_float((float)this.motionY, -0.35F, 0.35F);
/* 136 */     this.motionZ = MathHelper.clamp_float((float)this.motionZ, -0.35F, 0.35F);
/*     */   }
/*     */   
/*     */   public void setGravity(float value) {
/* 140 */     this.particleGravity = value;
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
/* 237 */   public int particle = 24;
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXBoreSparkle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */