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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderExplosiveOrb
/*    */   extends Render
/*    */ {
/*    */   public RenderExplosiveOrb(RenderManager rm)
/*    */   {
/* 27 */     super(rm);
/* 28 */     this.shadowSize = 0.0F;
/*    */   }
/*    */   
/* 31 */   private Random random = new Random();
/*    */   
/*    */   public void renderEntityAt(Entity entity, double x, double y, double z, float fq, float pticks)
/*    */   {
/* 35 */     Tessellator tessellator = Tessellator.getInstance();
/*    */     
/* 37 */     GL11.glPushMatrix();
/* 38 */     GL11.glTranslated(x, y, z);
/* 39 */     GL11.glDepthMask(false);
/* 40 */     GlStateManager.enableBlend();
/* 41 */     GlStateManager.blendFunc(770, 771);
/*    */     
/* 43 */     bindTexture(ParticleEngine.particleTexture2);
/*    */     
/* 45 */     float f2 = entity.ticksExisted % 4 / 16.0F;
/* 46 */     float f3 = f2 + 0.0625F;
/* 47 */     float f4 = 0.8125F;
/* 48 */     float f5 = f4 + 0.0625F;
/*    */     
/* 50 */     float f6 = 1.0F;
/* 51 */     float f7 = 0.5F;
/* 52 */     float f8 = 0.5F;
/*    */     
/* 54 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
/* 55 */     GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 56 */     GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 57 */     GL11.glScalef(0.7F, 0.7F, 0.7F);
/* 58 */     tessellator.getWorldRenderer().begin(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
/* 59 */     int i = 220;
/* 60 */     int j = i >> 16 & 0xFFFF;
/* 61 */     int k = i & 0xFFFF;
/* 62 */     tessellator.getWorldRenderer().pos(-f7, -f8, 0.0D).tex(f2, f5).color(1.0F, 1.0F, 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 63 */     tessellator.getWorldRenderer().pos(f6 - f7, -f8, 0.0D).tex(f3, f5).color(1.0F, 1.0F, 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 64 */     tessellator.getWorldRenderer().pos(f6 - f7, 1.0F - f8, 0.0D).tex(f3, f4).color(1.0F, 1.0F, 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 65 */     tessellator.getWorldRenderer().pos(-f7, 1.0F - f8, 0.0D).tex(f2, f4).color(1.0F, 1.0F, 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 66 */     tessellator.draw();
/* 67 */     GlStateManager.disableBlend();
/* 68 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 69 */     GL11.glDepthMask(true);
/* 70 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */ 
/*    */   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 76 */     renderEntityAt(entity, d, d1, d2, f, f1);
/*    */   }
/*    */   
/*    */ 
/*    */   protected ResourceLocation getEntityTexture(Entity entity)
/*    */   {
/* 82 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\projectile\RenderExplosiveOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */