/*    */ package thaumcraft.client.renderers.models.block;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ 
/*    */ public class ModelBanner
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer B1;
/*    */   ModelRenderer B2;
/*    */   ModelRenderer Beam;
/*    */   public ModelRenderer Banner;
/*    */   ModelRenderer Pole;
/*    */   
/*    */   public ModelBanner()
/*    */   {
/* 18 */     this.textureWidth = 128;
/* 19 */     this.textureHeight = 64;
/*    */     
/* 21 */     this.B1 = new ModelRenderer(this, 0, 29);
/* 22 */     this.B1.addBox(-5.0F, -7.5F, -1.5F, 2, 3, 3);
/* 23 */     this.B1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 24 */     this.B1.setTextureSize(128, 64);
/* 25 */     this.B1.mirror = true;
/* 26 */     setRotation(this.B1, 0.0F, 0.0F, 0.0F);
/* 27 */     this.B2 = new ModelRenderer(this, 0, 29);
/* 28 */     this.B2.addBox(3.0F, -7.5F, -1.5F, 2, 3, 3);
/* 29 */     this.B2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 30 */     this.B2.setTextureSize(128, 64);
/* 31 */     this.B2.mirror = true;
/* 32 */     setRotation(this.B2, 0.0F, 0.0F, 0.0F);
/* 33 */     this.Beam = new ModelRenderer(this, 30, 0);
/* 34 */     this.Beam.addBox(-7.0F, -7.0F, -1.0F, 14, 2, 2);
/* 35 */     this.Beam.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 36 */     this.Beam.setTextureSize(128, 64);
/* 37 */     this.Beam.mirror = true;
/* 38 */     setRotation(this.Beam, 0.0F, 0.0F, 0.0F);
/* 39 */     this.Banner = new ModelRenderer(this, 0, 0);
/* 40 */     this.Banner.addBox(-7.0F, 0.0F, -0.5F, 14, 28, 1);
/* 41 */     this.Banner.setRotationPoint(0.0F, -5.0F, 0.0F);
/* 42 */     this.Banner.setTextureSize(128, 64);
/* 43 */     this.Banner.mirror = true;
/* 44 */     setRotation(this.Banner, 0.0F, 0.0F, 0.0F);
/* 45 */     this.Pole = new ModelRenderer(this, 62, 0);
/* 46 */     this.Pole.addBox(0.0F, 0.0F, -1.0F, 2, 31, 2);
/* 47 */     this.Pole.setRotationPoint(-1.0F, -7.0F, -2.0F);
/* 48 */     this.Pole.setTextureSize(128, 64);
/* 49 */     this.Pole.mirror = true;
/* 50 */     setRotation(this.Pole, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public void renderPole()
/*    */   {
/* 55 */     this.Pole.render(0.0625F);
/*    */   }
/*    */   
/*    */   public void renderBeam()
/*    */   {
/* 60 */     this.Beam.render(0.0625F);
/*    */   }
/*    */   
/*    */   public void renderTabs()
/*    */   {
/* 65 */     this.B1.render(0.0625F);
/* 66 */     this.B2.render(0.0625F);
/*    */   }
/*    */   
/*    */   public void renderBanner() {
/* 70 */     this.Banner.render(0.0625F);
/*    */   }
/*    */   
/*    */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*    */   {
/* 75 */     model.rotateAngleX = x;
/* 76 */     model.rotateAngleY = y;
/* 77 */     model.rotateAngleZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\block\ModelBanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */