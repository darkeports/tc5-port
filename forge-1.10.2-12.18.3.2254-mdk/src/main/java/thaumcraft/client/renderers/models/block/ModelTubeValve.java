/*    */ package thaumcraft.client.renderers.models.block;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ 
/*    */ public class ModelTubeValve
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer ValveRod;
/*    */   ModelRenderer ValveRing;
/*    */   
/*    */   public ModelTubeValve()
/*    */   {
/* 15 */     this.textureWidth = 64;
/* 16 */     this.textureHeight = 32;
/*    */     
/* 18 */     this.ValveRod = new ModelRenderer(this, 0, 10);
/* 19 */     this.ValveRod.addBox(-1.0F, 2.0F, -1.0F, 2, 2, 2);
/* 20 */     this.ValveRod.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 21 */     this.ValveRod.setTextureSize(64, 32);
/* 22 */     this.ValveRod.mirror = true;
/* 23 */     setRotation(this.ValveRod, 0.0F, 0.0F, 0.0F);
/*    */     
/* 25 */     this.ValveRing = new ModelRenderer(this, 0, 0);
/* 26 */     this.ValveRing.addBox(-2.0F, 4.0F, -2.0F, 4, 1, 4);
/* 27 */     this.ValveRing.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 28 */     this.ValveRing.setTextureSize(64, 32);
/* 29 */     this.ValveRing.mirror = true;
/* 30 */     setRotation(this.ValveRing, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public void renderRod()
/*    */   {
/* 35 */     this.ValveRod.render(0.0625F);
/*    */   }
/*    */   
/*    */   public void renderRing()
/*    */   {
/* 40 */     this.ValveRing.render(0.0625F);
/*    */   }
/*    */   
/*    */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*    */   {
/* 45 */     model.rotateAngleX = x;
/* 46 */     model.rotateAngleY = y;
/* 47 */     model.rotateAngleZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\block\ModelTubeValve.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */