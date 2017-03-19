/*    */ package thaumcraft.client.renderers.entity.projectile;
/*    */ 
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import thaumcraft.common.entities.projectile.EntityPechBlast;
/*    */ 
/*    */ public class RenderPechBlast
/*    */   extends Render
/*    */ {
/*    */   public RenderPechBlast(RenderManager rm)
/*    */   {
/* 15 */     super(rm);
/* 16 */     this.shadowSize = 0.1F;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void renderEntityAt(EntityPechBlast tg, double x, double y, double z, float fq) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 29 */     renderEntityAt((EntityPechBlast)entity, d, d1, d2, f);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected ResourceLocation getEntityTexture(Entity entity)
/*    */   {
/* 36 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\projectile\RenderPechBlast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */