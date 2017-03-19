/*    */ package thaumcraft.client.renderers.models.block;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelCentrifuge
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer Crossbar;
/*    */   ModelRenderer Dingus1;
/*    */   ModelRenderer Dingus2;
/*    */   ModelRenderer Core;
/*    */   ModelRenderer Top;
/*    */   ModelRenderer Bottom;
/*    */   
/*    */   public ModelCentrifuge()
/*    */   {
/* 20 */     this.textureWidth = 64;
/* 21 */     this.textureHeight = 32;
/*    */     
/* 23 */     this.Crossbar = new ModelRenderer(this, 16, 0);
/* 24 */     this.Crossbar.addBox(-4.0F, -1.0F, -1.0F, 8, 2, 2);
/* 25 */     this.Crossbar.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 26 */     this.Crossbar.setTextureSize(64, 32);
/* 27 */     this.Crossbar.mirror = true;
/* 28 */     setRotation(this.Crossbar, 0.0F, 0.0F, 0.0F);
/* 29 */     this.Dingus1 = new ModelRenderer(this, 0, 16);
/* 30 */     this.Dingus1.addBox(4.0F, -3.0F, -2.0F, 4, 6, 4);
/* 31 */     this.Dingus1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 32 */     this.Dingus1.setTextureSize(64, 32);
/* 33 */     this.Dingus1.mirror = true;
/* 34 */     setRotation(this.Dingus1, 0.0F, 0.0F, 0.0F);
/* 35 */     this.Dingus2 = new ModelRenderer(this, 0, 16);
/* 36 */     this.Dingus2.addBox(-8.0F, -3.0F, -2.0F, 4, 6, 4);
/* 37 */     this.Dingus2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 38 */     this.Dingus2.setTextureSize(64, 32);
/* 39 */     this.Dingus2.mirror = true;
/* 40 */     setRotation(this.Dingus2, 0.0F, 0.0F, 0.0F);
/* 41 */     this.Core = new ModelRenderer(this, 0, 0);
/* 42 */     this.Core.addBox(-1.5F, -4.0F, -1.5F, 3, 8, 3);
/* 43 */     this.Core.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 44 */     this.Core.setTextureSize(64, 32);
/* 45 */     this.Core.mirror = true;
/* 46 */     setRotation(this.Core, 0.0F, 0.0F, 0.0F);
/* 47 */     this.Top = new ModelRenderer(this, 20, 16);
/* 48 */     this.Top.addBox(-4.0F, -8.0F, -4.0F, 8, 4, 8);
/* 49 */     this.Top.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 50 */     this.Top.setTextureSize(64, 32);
/* 51 */     this.Top.mirror = true;
/* 52 */     setRotation(this.Top, 0.0F, 0.0F, 0.0F);
/* 53 */     this.Bottom = new ModelRenderer(this, 20, 16);
/* 54 */     this.Bottom.addBox(-4.0F, 4.0F, -4.0F, 8, 4, 8);
/* 55 */     this.Bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 56 */     this.Bottom.setTextureSize(64, 32);
/* 57 */     this.Bottom.mirror = true;
/* 58 */     setRotation(this.Bottom, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */ 
/*    */   public void renderBoxes()
/*    */   {
/* 64 */     this.Top.render(0.0625F);
/* 65 */     this.Bottom.render(0.0625F);
/*    */   }
/*    */   
/*    */   public void renderSpinnyBit()
/*    */   {
/* 70 */     this.Crossbar.render(0.0625F);
/* 71 */     this.Dingus1.render(0.0625F);
/* 72 */     this.Dingus2.render(0.0625F);
/* 73 */     this.Core.render(0.0625F);
/*    */   }
/*    */   
/*    */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*    */   {
/* 78 */     model.rotateAngleX = x;
/* 79 */     model.rotateAngleY = y;
/* 80 */     model.rotateAngleZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\block\ModelCentrifuge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */