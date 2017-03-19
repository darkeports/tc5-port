/*    */ package thaumcraft.client.renderers.entity.mob;
/*    */ 
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ import thaumcraft.client.fx.ParticleEngine;
/*    */ import thaumcraft.client.lib.UtilsFX;
/*    */ import thaumcraft.client.renderers.entity.RenderAuraNode;
/*    */ import thaumcraft.common.entities.monster.EntityWisp;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderWisp
/*    */   extends Render
/*    */ {
/*    */   public RenderWisp(RenderManager rm)
/*    */   {
/* 23 */     super(rm);
/* 24 */     this.shadowSize = 0.0F;
/*    */   }
/*    */   
/*    */   public void renderEntityAt(Entity entity, double x, double y, double z, float fq, float pticks)
/*    */   {
/* 29 */     if (((EntityLiving)entity).getHealth() <= 0.0F) { return;
/*    */     }
/* 31 */     int color = 0;
/*    */     
/* 33 */     if (Aspect.getAspect(((EntityWisp)entity).getType()) != null) {
/* 34 */       color = Aspect.getAspect(((EntityWisp)entity).getType()).getColor();
/*    */     }
/*    */     
/* 37 */     GL11.glPushMatrix();
/* 38 */     GL11.glAlphaFunc(516, 0.003921569F);
/* 39 */     GL11.glDepthMask(false);
/*    */     
/* 41 */     GL11.glPushMatrix();
/* 42 */     bindTexture(RenderAuraNode.texture);
/* 43 */     UtilsFX.renderFacingQuad(entity.posX, entity.posY, entity.posZ, 32, 32, 800 + entity.ticksExisted % 16, 0.75F, color, 1.0F, 1, pticks);
/*    */     
/*    */ 
/* 46 */     GL11.glPopMatrix();
/*    */     
/* 48 */     GL11.glPushMatrix();
/* 49 */     bindTexture(ParticleEngine.particleTexture);
/* 50 */     UtilsFX.renderFacingQuad(entity.posX, entity.posY, entity.posZ, 16, 16, 80 + entity.ticksExisted % 16, 0.75F, 16777215, 1.0F, 1, pticks);
/*    */     
/*    */ 
/* 53 */     GL11.glPopMatrix();
/*    */     
/* 55 */     GL11.glDepthMask(true);
/* 56 */     GL11.glAlphaFunc(516, 0.1F);
/* 57 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 62 */     renderEntityAt(entity, d, d1, d2, f, f1);
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(Entity entity)
/*    */   {
/* 67 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\mob\RenderWisp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */