/*    */ package thaumcraft.client.renderers.entity.mob;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.RenderLiving;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderTaintSheep extends RenderLiving
/*    */ {
/* 11 */   private static final ResourceLocation rl = new ResourceLocation("thaumcraft", "textures/models/creature/sheep.png");
/*    */   
/*    */ 
/*    */   public RenderTaintSheep(RenderManager p_i46145_1_, ModelBase p_i46145_2_, float p_i46145_3_)
/*    */   {
/* 16 */     super(p_i46145_1_, p_i46145_2_, p_i46145_3_);
/* 17 */     addLayer(new LayerTaintSheepWool(this));
/*    */   }
/*    */   
/*    */ 
/*    */   protected ResourceLocation getEntityTexture(Entity entity)
/*    */   {
/* 23 */     return rl;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\mob\RenderTaintSheep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */