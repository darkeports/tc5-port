/*    */ package thaumcraft.client.renderers.models.block;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ 
/*    */ public class ModelBrain
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer Shape1;
/*    */   ModelRenderer Shape2;
/*    */   ModelRenderer Shape3;
/*    */   
/*    */   public ModelBrain()
/*    */   {
/* 16 */     this.textureWidth = 128;
/* 17 */     this.textureHeight = 64;
/*    */     
/* 19 */     this.Shape1 = new ModelRenderer(this, 0, 0);
/* 20 */     this.Shape1.addBox(0.0F, 0.0F, 0.0F, 12, 10, 16);
/* 21 */     this.Shape1.setRotationPoint(-6.0F, 8.0F, -8.0F);
/* 22 */     this.Shape1.setTextureSize(128, 64);
/* 23 */     this.Shape1.mirror = true;
/* 24 */     setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
/* 25 */     this.Shape2 = new ModelRenderer(this, 64, 0);
/* 26 */     this.Shape2.addBox(0.0F, 0.0F, 0.0F, 8, 3, 7);
/* 27 */     this.Shape2.setRotationPoint(-4.0F, 18.0F, 0.0F);
/* 28 */     this.Shape2.setTextureSize(128, 64);
/* 29 */     this.Shape2.mirror = true;
/* 30 */     setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
/* 31 */     this.Shape3 = new ModelRenderer(this, 0, 32);
/* 32 */     this.Shape3.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2);
/* 33 */     this.Shape3.setRotationPoint(-1.0F, 18.0F, -2.0F);
/* 34 */     this.Shape3.setTextureSize(128, 64);
/* 35 */     this.Shape3.mirror = true;
/* 36 */     setRotation(this.Shape3, 0.4089647F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */ 
/*    */   public void render()
/*    */   {
/* 42 */     this.Shape1.render(0.0625F);
/* 43 */     this.Shape2.render(0.0625F);
/* 44 */     this.Shape3.render(0.0625F);
/*    */   }
/*    */   
/*    */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*    */   {
/* 49 */     model.rotateAngleX = x;
/* 50 */     model.rotateAngleY = y;
/* 51 */     model.rotateAngleZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\block\ModelBrain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */