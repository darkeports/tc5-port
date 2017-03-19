/*    */ package thaumcraft.client.fx.particles;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.particle.Particle;
/*    */ import net.minecraft.client.renderer.VertexBuffer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class FXSpark extends Particle
/*    */ {
/*    */   public FXSpark(World world, double d, double d1, double d2, float f)
/*    */   {
/* 13 */     super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
/*    */     
/* 15 */     this.particleRed = 1.0F;
/* 16 */     this.particleGreen = 1.0F;
/* 17 */     this.particleBlue = 1.0F;
/* 18 */     this.particleGravity = 0.0F;
/* 19 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/* 20 */     this.particleScale = f;
/* 21 */     this.particleMaxAge = (5 + world.rand.nextInt(5));
/* 22 */     this.canCollide = true;
/* 23 */     setSize(0.01F, 0.01F);
/* 24 */     this.particle = (world.rand.nextInt(3) * 8);
/* 25 */     this.flip = world.rand.nextBoolean();
/*    */   }
/*    */   
/* 28 */   int particle = 0;
/* 29 */   boolean flip = false;
/*    */   
/*    */ 
/*    */ 
/*    */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*    */   {
/* 35 */     org.lwjgl.opengl.GL11.glColor4f(1.0F, 1.0F, 1.0F, this.particleAlpha);
/* 36 */     int part = this.particle + (int)(this.particleAge / this.particleMaxAge * 7.0F);
/*    */     
/* 38 */     float var8 = part % 8 / 8.0F;
/* 39 */     float var9 = var8 + 0.125F;
/* 40 */     float var10 = part / 8 / 8.0F;
/* 41 */     float var11 = var10 + 0.125F;
/* 42 */     float var12 = this.particleScale;
/* 43 */     if (this.flip) {
/* 44 */       float t = var8;
/* 45 */       var8 = var9;
/* 46 */       var9 = t;
/*    */     }
/* 48 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/* 49 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/* 50 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*    */     
/* 52 */     int i = getBrightnessForRender(f);
/* 53 */     int j = i >> 16 & 0xFFFF;
/* 54 */     int k = i & 0xFFFF;
/*    */     
/* 56 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*    */     
/* 58 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*    */     
/* 60 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*    */     
/* 62 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 67 */   public int layer = 2;
/*    */   
/*    */   public int getFXLayer() {
/* 70 */     return this.layer;
/*    */   }
/*    */   
/*    */ 
/*    */   public void onUpdate()
/*    */   {
/* 76 */     this.prevPosX = this.posX;
/* 77 */     this.prevPosY = this.posY;
/* 78 */     this.prevPosZ = this.posZ;
/*    */     
/* 80 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 81 */       setExpired();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXSpark.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */