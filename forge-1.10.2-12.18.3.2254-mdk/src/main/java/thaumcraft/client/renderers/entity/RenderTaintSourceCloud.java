/*    */ package thaumcraft.client.renderers.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderTaintSourceCloud
/*    */   extends Render
/*    */ {
/*    */   public RenderTaintSourceCloud(RenderManager rm)
/*    */   {
/* 34 */     super(rm);
/* 35 */     this.shadowSize = 0.0F;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 42 */   public static final ResourceLocation texture = new ResourceLocation("textures/environment/rain.png");
/*    */   
/*    */   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {}
/*    */   
/* 46 */   protected ResourceLocation getEntityTexture(Entity entity) { return texture; }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\RenderTaintSourceCloud.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */