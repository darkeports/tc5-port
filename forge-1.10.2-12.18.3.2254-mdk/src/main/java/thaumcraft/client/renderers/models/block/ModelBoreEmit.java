/*    */ package thaumcraft.client.renderers.models.block;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ 
/*    */ public class ModelBoreEmit
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer Knob;
/*    */   ModelRenderer Cross1;
/*    */   ModelRenderer Cross3;
/*    */   ModelRenderer Cross2;
/*    */   ModelRenderer Rod;
/*    */   
/*    */   public ModelBoreEmit()
/*    */   {
/* 18 */     this.textureWidth = 128;
/* 19 */     this.textureHeight = 64;
/*    */     
/* 21 */     this.Knob = new ModelRenderer(this, 66, 0);
/* 22 */     this.Knob.addBox(-2.0F, 12.0F, -2.0F, 4, 4, 4);
/* 23 */     this.Knob.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 24 */     this.Knob.setTextureSize(128, 64);
/* 25 */     this.Knob.mirror = true;
/* 26 */     setRotation(this.Knob, 0.0F, 0.0F, 0.0F);
/* 27 */     this.Cross1 = new ModelRenderer(this, 56, 16);
/* 28 */     this.Cross1.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4);
/* 29 */     this.Cross1.setRotationPoint(0.0F, 8.0F, 0.0F);
/* 30 */     this.Cross1.setTextureSize(128, 64);
/* 31 */     this.Cross1.mirror = true;
/* 32 */     setRotation(this.Cross1, 0.0F, 0.0F, 0.0F);
/* 33 */     this.Cross3 = new ModelRenderer(this, 56, 16);
/* 34 */     this.Cross3.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4);
/* 35 */     this.Cross3.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 36 */     this.Cross3.setTextureSize(128, 64);
/* 37 */     this.Cross3.mirror = true;
/* 38 */     setRotation(this.Cross3, 0.0F, 0.0F, 0.0F);
/* 39 */     this.Cross2 = new ModelRenderer(this, 56, 24);
/* 40 */     this.Cross2.addBox(-3.0F, 4.0F, -3.0F, 6, 1, 6);
/* 41 */     this.Cross2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 42 */     this.Cross2.setTextureSize(128, 64);
/* 43 */     this.Cross2.mirror = true;
/* 44 */     setRotation(this.Cross2, 0.0F, 0.0F, 0.0F);
/* 45 */     this.Rod = new ModelRenderer(this, 56, 0);
/* 46 */     this.Rod.addBox(-1.0F, 1.0F, -1.0F, 2, 11, 2);
/* 47 */     this.Rod.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 48 */     this.Rod.setTextureSize(128, 64);
/* 49 */     this.Rod.mirror = true;
/* 50 */     setRotation(this.Rod, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public void render(boolean focus)
/*    */   {
/* 55 */     float f5 = 0.0625F;
/* 56 */     if (focus) this.Knob.render(f5);
/* 57 */     this.Cross1.render(f5);
/* 58 */     this.Cross3.render(f5);
/* 59 */     this.Cross2.render(f5);
/* 60 */     this.Rod.render(f5);
/*    */   }
/*    */   
/*    */ 
/*    */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*    */   {
/* 66 */     model.rotateAngleX = x;
/* 67 */     model.rotateAngleY = y;
/* 68 */     model.rotateAngleZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\block\ModelBoreEmit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */