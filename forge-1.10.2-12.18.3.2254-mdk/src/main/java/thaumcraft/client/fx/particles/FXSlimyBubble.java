/*    */ package thaumcraft.client.fx.particles;
/*    */ 
/*    */ import net.minecraft.client.particle.Particle;
/*    */ import net.minecraft.client.renderer.VertexBuffer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class FXSlimyBubble extends Particle
/*    */ {
/*    */   public FXSlimyBubble(World world, double d, double d1, double d2, float f)
/*    */   {
/* 13 */     super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
/*    */     
/* 15 */     this.particleRed = 1.0F;
/* 16 */     this.particleGreen = 1.0F;
/* 17 */     this.particleBlue = 1.0F;
/* 18 */     this.particleGravity = 0.0F;
/* 19 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/* 20 */     this.particleScale = f;
/* 21 */     this.particleMaxAge = (15 + world.rand.nextInt(5));
/* 22 */     this.canCollide = true;
/* 23 */     setSize(0.01F, 0.01F);
/*    */   }
/*    */   
/* 26 */   int particle = 144;
/*    */   
/*    */ 
/*    */ 
/*    */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*    */   {
/* 32 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, this.particleAlpha);
/*    */     
/* 34 */     float var8 = this.particle % 16 / 16.0F;
/* 35 */     float var9 = var8 + 0.0625F;
/* 36 */     float var10 = this.particle / 16 / 16.0F;
/* 37 */     float var11 = var10 + 0.0625F;
/* 38 */     float var12 = this.particleScale;
/* 39 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/* 40 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/* 41 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*    */     
/* 43 */     int i = getBrightnessForRender(f);
/* 44 */     int j = i >> 16 & 0xFFFF;
/* 45 */     int k = i & 0xFFFF;
/*    */     
/* 47 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*    */     
/* 49 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*    */     
/* 51 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*    */     
/* 53 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getFXLayer()
/*    */   {
/* 60 */     return 1;
/*    */   }
/*    */   
/*    */ 
/*    */   public void onUpdate()
/*    */   {
/* 66 */     this.prevPosX = this.posX;
/* 67 */     this.prevPosY = this.posY;
/* 68 */     this.prevPosZ = this.posZ;
/*    */     
/* 70 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 71 */       setExpired();
/*    */     }
/* 73 */     if (this.particleAge - 1 < 6) {
/* 74 */       this.particle = (144 + this.particleAge / 2);
/* 75 */       if (this.particleAge == 5) {
/* 76 */         this.posY += 0.1D;
/*    */       }
/*    */     }
/* 79 */     else if (this.particleAge < this.particleMaxAge - 4) {
/* 80 */       this.motionY += 0.005D;
/* 81 */       this.particle = (147 + this.particleAge % 4 / 2);
/*    */     } else {
/* 83 */       this.motionY /= 2.0D;
/* 84 */       this.particle = (150 - (this.particleMaxAge - this.particleAge) / 2);
/*    */     }
/*    */     
/* 87 */     this.posY += this.motionY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXSlimyBubble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */