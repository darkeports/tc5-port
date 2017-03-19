/*    */ package thaumcraft.client.renderers.models.block;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelBellows
/*    */   extends ModelBase
/*    */ {
/*    */   public ModelRenderer BottomPlank;
/*    */   public ModelRenderer MiddlePlank;
/*    */   public ModelRenderer TopPlank;
/*    */   public ModelRenderer Bag;
/*    */   public ModelRenderer Nozzle;
/*    */   
/*    */   public ModelBellows()
/*    */   {
/* 19 */     this.textureWidth = 128;
/* 20 */     this.textureHeight = 64;
/*    */     
/* 22 */     this.BottomPlank = new ModelRenderer(this, 0, 0);
/* 23 */     this.BottomPlank.addBox(-6.0F, 0.0F, -6.0F, 12, 2, 12);
/* 24 */     this.BottomPlank.setRotationPoint(0.0F, 22.0F, 0.0F);
/* 25 */     this.BottomPlank.setTextureSize(128, 64);
/* 26 */     this.BottomPlank.mirror = true;
/* 27 */     setRotation(this.BottomPlank, 0.0F, 0.0F, 0.0F);
/*    */     
/* 29 */     this.MiddlePlank = new ModelRenderer(this, 0, 0);
/* 30 */     this.MiddlePlank.addBox(-6.0F, -1.0F, -6.0F, 12, 2, 12);
/* 31 */     this.MiddlePlank.setRotationPoint(0.0F, 16.0F, 0.0F);
/* 32 */     this.MiddlePlank.setTextureSize(128, 64);
/* 33 */     this.MiddlePlank.mirror = true;
/* 34 */     setRotation(this.MiddlePlank, 0.0F, 0.0F, 0.0F);
/*    */     
/* 36 */     this.TopPlank = new ModelRenderer(this, 0, 0);
/* 37 */     this.TopPlank.addBox(-6.0F, 0.0F, -6.0F, 12, 2, 12);
/* 38 */     this.TopPlank.setRotationPoint(0.0F, 8.0F, 0.0F);
/* 39 */     this.TopPlank.setTextureSize(128, 64);
/* 40 */     this.TopPlank.mirror = true;
/* 41 */     setRotation(this.TopPlank, 0.0F, 0.0F, 0.0F);
/*    */     
/* 43 */     this.Bag = new ModelRenderer(this, 48, 0);
/* 44 */     this.Bag.addBox(-10.0F, -12.03333F, -10.0F, 20, 24, 20);
/* 45 */     this.Bag.setRotationPoint(0.0F, 16.0F, 0.0F);
/* 46 */     this.Bag.setTextureSize(64, 32);
/* 47 */     this.Bag.mirror = true;
/* 48 */     setRotation(this.Bag, 0.0F, 0.0F, 0.0F);
/*    */     
/* 50 */     this.Nozzle = new ModelRenderer(this, 0, 36);
/* 51 */     this.Nozzle.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 2);
/* 52 */     this.Nozzle.setRotationPoint(0.0F, 16.0F, 6.0F);
/* 53 */     this.Nozzle.setTextureSize(128, 64);
/* 54 */     this.Nozzle.mirror = true;
/* 55 */     setRotation(this.Nozzle, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public void render()
/*    */   {
/* 60 */     this.MiddlePlank.render(0.0625F);
/* 61 */     this.Nozzle.render(0.0625F);
/*    */   }
/*    */   
/*    */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*    */   {
/* 66 */     model.rotateAngleX = x;
/* 67 */     model.rotateAngleY = y;
/* 68 */     model.rotateAngleZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\block\ModelBellows.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */