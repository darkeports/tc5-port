/*    */ package thaumcraft.client.fx.particles;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.particle.Particle;
/*    */ import net.minecraft.client.renderer.VertexBuffer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class FXSmokeSpiral extends Particle
/*    */ {
/* 13 */   private float radius = 1.0F;
/* 14 */   private int start = 0;
/* 15 */   private int miny = 0;
/*    */   
/*    */   public FXSmokeSpiral(World world, double d, double d1, double d2, float radius, int start, int miny)
/*    */   {
/* 19 */     super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
/*    */     
/* 21 */     this.particleGravity = -0.01F;
/* 22 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/* 23 */     this.particleScale *= 1.0F;
/* 24 */     this.particleMaxAge = (20 + world.rand.nextInt(10));
/* 25 */     this.canCollide = true;
/* 26 */     setSize(0.01F, 0.01F);
/* 27 */     this.prevPosX = this.posX;
/* 28 */     this.prevPosY = this.posY;
/* 29 */     this.prevPosZ = this.posZ;
/* 30 */     this.radius = radius;
/* 31 */     this.start = start;
/* 32 */     this.miny = miny;
/*    */   }
/*    */   
/*    */ 
/*    */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*    */   {
/* 38 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.66F * this.particleAlpha);
/*    */     
/* 40 */     int particle = (int)(1.0F + this.particleAge / this.particleMaxAge * 4.0F);
/*    */     
/* 42 */     float r1 = this.start + 720.0F * ((this.particleAge + f) / this.particleMaxAge);
/* 43 */     float r2 = 90.0F - 180.0F * ((this.particleAge + f) / this.particleMaxAge);
/*    */     
/* 45 */     float mX = -MathHelper.sin(r1 / 180.0F * 3.1415927F) * MathHelper.cos(r2 / 180.0F * 3.1415927F);
/*    */     
/* 47 */     float mZ = MathHelper.cos(r1 / 180.0F * 3.1415927F) * MathHelper.cos(r2 / 180.0F * 3.1415927F);
/*    */     
/* 49 */     float mY = -MathHelper.sin(r2 / 180.0F * 3.1415927F);
/* 50 */     mX *= this.radius;
/* 51 */     mY *= this.radius;
/* 52 */     mZ *= this.radius;
/*    */     
/* 54 */     float var8 = particle % 16 / 16.0F;
/* 55 */     float var9 = var8 + 0.0624375F;
/* 56 */     float var10 = particle / 16 / 16.0F;
/* 57 */     float var11 = var10 + 0.0624375F;
/* 58 */     float var12 = 0.15F * this.particleScale;
/* 59 */     float var13 = (float)(this.posX + mX - interpPosX);
/* 60 */     float var14 = (float)(Math.max(this.posY + mY, this.miny + 0.1F) - interpPosY);
/* 61 */     float var15 = (float)(this.posZ + mZ - interpPosZ);
/* 62 */     float var16 = 1.0F;
/* 63 */     int i = getBrightnessForRender(f);
/* 64 */     int j = i >> 16 & 0xFFFF;
/* 65 */     int k = i & 0xFFFF;
/* 66 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.66F * this.particleAlpha).lightmap(j, k).endVertex();
/*    */     
/* 68 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.66F * this.particleAlpha).lightmap(j, k).endVertex();
/*    */     
/* 70 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.66F * this.particleAlpha).lightmap(j, k).endVertex();
/*    */     
/* 72 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.66F * this.particleAlpha).lightmap(j, k).endVertex();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getFXLayer()
/*    */   {
/* 79 */     return 1;
/*    */   }
/*    */   
/*    */   public void onUpdate()
/*    */   {
/* 84 */     setAlphaF((this.particleMaxAge - this.particleAge) / this.particleMaxAge);
/* 85 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 87 */       setExpired();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXSmokeSpiral.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */