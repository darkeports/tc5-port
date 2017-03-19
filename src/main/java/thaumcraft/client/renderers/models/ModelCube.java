/*    */ package thaumcraft.client.renderers.models;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ 
/*    */ public class ModelCube
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer cube;
/*    */   
/*    */   public ModelCube()
/*    */   {
/* 14 */     this.textureWidth = 64;
/* 15 */     this.textureHeight = 32;
/*    */     
/* 17 */     this.cube = new ModelRenderer(this, 0, 0);
/* 18 */     this.cube.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
/* 19 */     this.cube.setRotationPoint(8.0F, 8.0F, 8.0F);
/* 20 */     this.cube.setTextureSize(64, 32);
/* 21 */     this.cube.mirror = true;
/*    */   }
/*    */   
/*    */ 
/*    */   public ModelCube(int shift)
/*    */   {
/* 27 */     this.textureWidth = 64;
/* 28 */     this.textureHeight = 64;
/*    */     
/* 30 */     this.cube = new ModelRenderer(this, 0, shift);
/* 31 */     this.cube.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
/* 32 */     this.cube.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 33 */     this.cube.setTextureSize(64, 64);
/* 34 */     this.cube.mirror = true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void render()
/*    */   {
/* 41 */     this.cube.render(0.0625F);
/*    */   }
/*    */   
/*    */   public void setRotation(ModelRenderer model, float x, float y, float z)
/*    */   {
/* 46 */     model.rotateAngleX = x;
/* 47 */     model.rotateAngleY = y;
/* 48 */     model.rotateAngleZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\ModelCube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */