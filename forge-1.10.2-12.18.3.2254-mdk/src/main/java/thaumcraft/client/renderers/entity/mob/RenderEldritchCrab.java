/*    */ package thaumcraft.client.renderers.entity.mob;
/*    */ 
/*    */ import net.minecraft.client.renderer.entity.RenderLiving;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.client.renderers.models.entity.ModelEldritchCrab;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderEldritchCrab extends RenderLiving
/*    */ {
/* 15 */   private static final ResourceLocation skin = new ResourceLocation("thaumcraft", "textures/models/creature/crab.png");
/*    */   
/*    */   public RenderEldritchCrab(RenderManager renderManager)
/*    */   {
/* 19 */     super(renderManager, new ModelEldritchCrab(), 1.0F);
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(Entity entity)
/*    */   {
/* 24 */     return skin;
/*    */   }
/*    */   
/*    */   public void renderCrab(EntityLiving crab, double par2, double par4, double par6, float par8, float par9)
/*    */   {
/* 29 */     super.doRender(crab, par2, par4, par6, par8, par9);
/*    */   }
/*    */   
/*    */ 
/*    */   public void doRender(EntityLiving par1Entity, double par2, double par4, double par6, float par8, float par9)
/*    */   {
/* 35 */     renderCrab(par1Entity, par2, par4, par6, par8, par9);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\mob\RenderEldritchCrab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */