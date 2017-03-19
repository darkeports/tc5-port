/*    */ package thaumcraft.client.renderers.entity.mob;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import thaumcraft.client.renderers.models.entity.ModelTaintSheep1;
/*    */ import thaumcraft.common.entities.monster.tainted.EntityTaintSheep;
/*    */ 
/*    */ public class LayerTaintSheepWool implements LayerRenderer
/*    */ {
/* 14 */   private static final ResourceLocation TEXTURE = new ResourceLocation("thaumcraft", "textures/models/creature/sheep_fur.png");
/*    */   private final RenderTaintSheep sheepRenderer;
/* 16 */   private final ModelTaintSheep1 sheepModel = new ModelTaintSheep1();
/*    */   
/*    */   public LayerTaintSheepWool(RenderTaintSheep p_i46112_1_)
/*    */   {
/* 20 */     this.sheepRenderer = p_i46112_1_;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityTaintSheep p_177162_1_, float p_177162_2_, float p_177162_3_, float p_177162_4_, float p_177162_5_, float p_177162_6_, float p_177162_7_, float p_177162_8_)
/*    */   {
/* 25 */     if ((!p_177162_1_.getSheared()) && (!p_177162_1_.isInvisible()))
/*    */     {
/* 27 */       this.sheepRenderer.bindTexture(TEXTURE);
/*    */       
/* 29 */       float[] afloat = EntitySheep.func_175513_a(EnumDyeColor.PURPLE);
/* 30 */       GlStateManager.color(afloat[0], afloat[1], afloat[2]);
/*    */       
/* 32 */       this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
/* 33 */       this.sheepModel.setLivingAnimations(p_177162_1_, p_177162_2_, p_177162_3_, p_177162_4_);
/* 34 */       this.sheepModel.render(p_177162_1_, p_177162_2_, p_177162_3_, p_177162_5_, p_177162_6_, p_177162_7_, p_177162_8_);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean shouldCombineTextures()
/*    */   {
/* 41 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
/*    */   {
/* 47 */     doRenderLayer((EntityTaintSheep)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\mob\LayerTaintSheepWool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */