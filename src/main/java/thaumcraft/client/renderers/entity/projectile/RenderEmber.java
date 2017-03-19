/*    */ package thaumcraft.client.renderers.entity.projectile;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.client.fx.ParticleEngine;
/*    */ import thaumcraft.client.lib.UtilsFX;
/*    */ import thaumcraft.common.entities.projectile.EntityEmber;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderEmber
/*    */   extends Render
/*    */ {
/*    */   public RenderEmber(RenderManager rm)
/*    */   {
/* 25 */     super(rm);
/* 26 */     this.shadowSize = 0.0F;
/*    */   }
/*    */   
/* 29 */   private Random random = new Random();
/*    */   
/*    */   public void renderEntityAt(EntityEmber entity, double x, double y, double z, float fq, float pticks)
/*    */   {
/* 33 */     Tessellator tessellator = Tessellator.getInstance();
/*    */     
/* 35 */     GL11.glPushMatrix();
/*    */     
/* 37 */     GL11.glTranslated(x, y, z);
/* 38 */     GL11.glDepthMask(false);
/* 39 */     GlStateManager.enableBlend();
/* 40 */     GlStateManager.blendFunc(770, 1);
/*    */     
/* 42 */     bindTexture(ParticleEngine.particleTexture);
/* 43 */     int p = (int)(8.0F * (entity.ticksExisted / entity.duration));
/* 44 */     float f2 = (7 + p) / 16.0F;
/* 45 */     float f3 = f2 + 0.0625F;
/* 46 */     float f4 = 0.5625F;
/* 47 */     float f5 = f4 + 0.0625F;
/*    */     
/* 49 */     float f6 = 1.0F;
/* 50 */     float f7 = 0.5F;
/* 51 */     float f8 = 0.5F;
/*    */     
/* 53 */     float fc = entity.ticksExisted / entity.duration;
/* 54 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
/*    */     
/* 56 */     float particleScale = 0.25F + fc;
/*    */     
/* 58 */     GL11.glScalef(particleScale, particleScale, particleScale);
/*    */     
/* 60 */     GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 61 */     GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 62 */     tessellator.getWorldRenderer().begin(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
/* 63 */     int i = 220;
/* 64 */     int j = i >> 16 & 0xFFFF;
/* 65 */     int k = i & 0xFFFF;
/* 66 */     tessellator.getWorldRenderer().pos(-f7, -f8, 0.0D).tex(f2, f5).color(1.0F, 1.0F, 1.0F, 0.9F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 67 */     tessellator.getWorldRenderer().pos(f6 - f7, -f8, 0.0D).tex(f3, f5).color(1.0F, 1.0F, 1.0F, 0.9F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 68 */     tessellator.getWorldRenderer().pos(f6 - f7, 1.0F - f8, 0.0D).tex(f3, f4).color(1.0F, 1.0F, 1.0F, 0.9F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 69 */     tessellator.getWorldRenderer().pos(-f7, 1.0F - f8, 0.0D).tex(f2, f4).color(1.0F, 1.0F, 1.0F, 0.9F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 70 */     tessellator.draw();
/* 71 */     GlStateManager.disableBlend();
/* 72 */     GL11.glDepthMask(true);
/* 73 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 74 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 81 */     renderEntityAt((EntityEmber)entity, d, d1, d2, f, f1);
/*    */   }
/*    */   
/*    */ 
/*    */   protected ResourceLocation getEntityTexture(Entity entity)
/*    */   {
/* 87 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\projectile\RenderEmber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */