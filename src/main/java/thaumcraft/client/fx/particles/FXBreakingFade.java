/*    */ package thaumcraft.client.fx.particles;
/*    */ 
/*    */ import net.minecraft.client.particle.ParticleBreaking;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.VertexBuffer;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class FXBreakingFade
/*    */   extends ParticleBreaking
/*    */ {
/*    */   public FXBreakingFade(World worldIn, double p_i1197_2_, double p_i1197_4_, double p_i1197_6_, double p_i1197_8_, double p_i1197_10_, double p_i1197_12_, Item p_i1197_14_, int p_i1197_15_)
/*    */   {
/* 19 */     super(worldIn, p_i1197_2_, p_i1197_4_, p_i1197_6_, p_i1197_8_, p_i1197_10_, p_i1197_12_, p_i1197_14_, p_i1197_15_);
/*    */   }
/*    */   
/*    */ 
/*    */   public FXBreakingFade(World worldIn, double p_i1196_2_, double p_i1196_4_, double p_i1196_6_, Item p_i1196_8_, int p_i1196_9_)
/*    */   {
/* 25 */     super(worldIn, p_i1196_2_, p_i1196_4_, p_i1196_6_, p_i1196_8_, p_i1196_9_);
/*    */   }
/*    */   
/*    */   public FXBreakingFade(World worldIn, double p_i1195_2_, double p_i1195_4_, double p_i1195_6_, Item p_i1195_8_)
/*    */   {
/* 30 */     super(worldIn, p_i1195_2_, p_i1195_4_, p_i1195_6_, p_i1195_8_);
/*    */   }
/*    */   
/*    */   public void setParticleMaxAge(int particleMaxAge) {
/* 34 */     this.particleMaxAge = particleMaxAge;
/*    */   }
/*    */   
/*    */   public void setParticleGravity(float f) {
/* 38 */     this.particleGravity = f;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getFXLayer()
/*    */   {
/* 44 */     return 1;
/*    */   }
/*    */   
/*    */ 
/*    */   public void renderParticle(VertexBuffer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
/*    */   {
/* 50 */     GlStateManager.depthMask(false);
/* 51 */     float f6 = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/* 52 */     float f7 = f6 + 0.015609375F;
/* 53 */     float f8 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/* 54 */     float f9 = f8 + 0.015609375F;
/* 55 */     float f10 = 0.1F * this.particleScale;
/* 56 */     float fade = 1.0F - this.particleAge / this.particleMaxAge;
/*    */     
/* 58 */     if (this.particleTexture != null)
/*    */     {
/* 60 */       f6 = this.particleTexture.getInterpolatedU(this.particleTextureJitterX / 4.0F * 16.0F);
/* 61 */       f7 = this.particleTexture.getInterpolatedU((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
/* 62 */       f8 = this.particleTexture.getInterpolatedV(this.particleTextureJitterY / 4.0F * 16.0F);
/* 63 */       f9 = this.particleTexture.getInterpolatedV((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
/*    */     }
/* 65 */     int i = getBrightnessForRender(p_180434_3_);
/* 66 */     int j = i >> 16 & 0xFFFF;
/* 67 */     int k = i & 0xFFFF;
/* 68 */     float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * p_180434_3_ - interpPosX);
/* 69 */     float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * p_180434_3_ - interpPosY);
/* 70 */     float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * p_180434_3_ - interpPosZ);
/* 71 */     p_180434_1_.pos(f11 - p_180434_4_ * f10 - p_180434_7_ * f10, f12 - p_180434_5_ * f10, f13 - p_180434_6_ * f10 - p_180434_8_ * f10).tex(f6, f9).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha * fade).lightmap(j, k).endVertex();
/*    */     
/* 73 */     p_180434_1_.pos(f11 - p_180434_4_ * f10 + p_180434_7_ * f10, f12 + p_180434_5_ * f10, f13 - p_180434_6_ * f10 + p_180434_8_ * f10).tex(f6, f8).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha * fade).lightmap(j, k).endVertex();
/*    */     
/* 75 */     p_180434_1_.pos(f11 + p_180434_4_ * f10 + p_180434_7_ * f10, f12 + p_180434_5_ * f10, f13 + p_180434_6_ * f10 + p_180434_8_ * f10).tex(f7, f8).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha * fade).lightmap(j, k).endVertex();
/*    */     
/* 77 */     p_180434_1_.pos(f11 + p_180434_4_ * f10 - p_180434_7_ * f10, f12 - p_180434_5_ * f10, f13 + p_180434_6_ * f10 - p_180434_8_ * f10).tex(f7, f9).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha * fade).lightmap(j, k).endVertex();
/*    */     
/* 79 */     GlStateManager.depthMask(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXBreakingFade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */