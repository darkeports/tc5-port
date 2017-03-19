/*    */ package thaumcraft.client.renderers.entity.construct;
/*    */ 
/*    */ import net.minecraft.client.renderer.entity.RenderLiving;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import thaumcraft.client.renderers.models.entity.ModelNodeMagnet;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderNodeMagnet
/*    */   extends RenderLiving
/*    */ {
/*    */   public RenderNodeMagnet(RenderManager rm)
/*    */   {
/* 16 */     super(rm, new ModelNodeMagnet(), 0.5F);
/*    */   }
/*    */   
/*    */ 
/* 20 */   private static final ResourceLocation rl = new ResourceLocation("thaumcraft", "textures/models/nodemagnet.png");
/*    */   
/*    */   protected ResourceLocation getEntityTexture(Entity entity)
/*    */   {
/* 24 */     return rl;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\construct\RenderNodeMagnet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */