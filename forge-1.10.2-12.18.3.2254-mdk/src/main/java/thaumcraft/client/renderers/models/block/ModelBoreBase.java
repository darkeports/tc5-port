/*     */ package thaumcraft.client.renderers.models.block;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ 
/*     */ 
/*     */ public class ModelBoreBase
/*     */   extends ModelBase
/*     */ {
/*     */   ModelRenderer Base1;
/*     */   ModelRenderer Base2;
/*     */   ModelRenderer PillarMid;
/*     */   ModelRenderer Pillar2;
/*     */   ModelRenderer Pillar3;
/*     */   ModelRenderer Pillar4;
/*     */   ModelRenderer Pillar1;
/*     */   ModelRenderer Nozzle1;
/*     */   ModelRenderer Nozzle2;
/*     */   
/*     */   public ModelBoreBase()
/*     */   {
/*  22 */     this.textureWidth = 128;
/*  23 */     this.textureHeight = 64;
/*     */     
/*  25 */     this.Base1 = new ModelRenderer(this, 64, 24);
/*  26 */     this.Base1.addBox(-8.0F, 0.0F, -8.0F, 16, 2, 16);
/*  27 */     this.Base1.setRotationPoint(0.0F, 0.0F, 0.0F);
/*  28 */     this.Base1.setTextureSize(128, 64);
/*  29 */     this.Base1.mirror = true;
/*  30 */     setRotation(this.Base1, 0.0F, 0.0F, 0.0F);
/*  31 */     this.Base2 = new ModelRenderer(this, 64, 24);
/*  32 */     this.Base2.addBox(-8.0F, 0.0F, -8.0F, 16, 2, 16);
/*  33 */     this.Base2.setRotationPoint(0.0F, 14.0F, 0.0F);
/*  34 */     this.Base2.setTextureSize(128, 64);
/*  35 */     this.Base2.mirror = true;
/*  36 */     setRotation(this.Base2, 0.0F, 0.0F, 0.0F);
/*  37 */     this.PillarMid = new ModelRenderer(this, 84, 42);
/*  38 */     this.PillarMid.addBox(-2.5F, 0.0F, -2.5F, 5, 12, 5);
/*  39 */     this.PillarMid.setRotationPoint(0.0F, 2.0F, 0.0F);
/*  40 */     this.PillarMid.setTextureSize(128, 64);
/*  41 */     this.PillarMid.mirror = true;
/*  42 */     setRotation(this.PillarMid, 0.0F, 0.0F, 0.0F);
/*  43 */     this.Pillar2 = new ModelRenderer(this, 64, 42);
/*  44 */     this.Pillar2.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
/*  45 */     this.Pillar2.setRotationPoint(-5.0F, 2.0F, -5.0F);
/*  46 */     this.Pillar2.setTextureSize(128, 64);
/*  47 */     this.Pillar2.mirror = true;
/*  48 */     setRotation(this.Pillar2, 0.0F, 0.0F, 0.0F);
/*  49 */     this.Pillar3 = new ModelRenderer(this, 64, 42);
/*  50 */     this.Pillar3.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
/*  51 */     this.Pillar3.setRotationPoint(-5.0F, 2.0F, 5.0F);
/*  52 */     this.Pillar3.setTextureSize(128, 64);
/*  53 */     this.Pillar3.mirror = true;
/*  54 */     setRotation(this.Pillar3, 0.0F, 0.0F, 0.0F);
/*  55 */     this.Pillar4 = new ModelRenderer(this, 64, 42);
/*  56 */     this.Pillar4.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
/*  57 */     this.Pillar4.setRotationPoint(5.0F, 2.0F, 5.0F);
/*  58 */     this.Pillar4.setTextureSize(128, 64);
/*  59 */     this.Pillar4.mirror = true;
/*  60 */     setRotation(this.Pillar4, 0.0F, 0.0F, 0.0F);
/*  61 */     this.Pillar1 = new ModelRenderer(this, 64, 42);
/*  62 */     this.Pillar1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
/*  63 */     this.Pillar1.setRotationPoint(5.0F, 2.0F, -5.0F);
/*  64 */     this.Pillar1.setTextureSize(128, 64);
/*  65 */     this.Pillar1.mirror = true;
/*  66 */     setRotation(this.Pillar1, 0.0F, 0.0F, 0.0F);
/*  67 */     this.Nozzle1 = new ModelRenderer(this, 106, 42);
/*  68 */     this.Nozzle1.addBox(2.5F, -2.0F, -2.0F, 5, 4, 4);
/*  69 */     this.Nozzle1.setRotationPoint(0.0F, 8.0F, 0.0F);
/*  70 */     this.Nozzle1.setTextureSize(128, 64);
/*  71 */     this.Nozzle1.mirror = true;
/*  72 */     setRotation(this.Nozzle1, 0.0F, 0.0F, 0.0F);
/*  73 */     this.Nozzle2 = new ModelRenderer(this, 106, 51);
/*  74 */     this.Nozzle2.addBox(7.0F, -2.5F, -2.5F, 1, 5, 5);
/*  75 */     this.Nozzle2.setRotationPoint(0.0F, 8.0F, 0.0F);
/*  76 */     this.Nozzle2.setTextureSize(128, 64);
/*  77 */     this.Nozzle2.mirror = true;
/*  78 */     setRotation(this.Nozzle2, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void render()
/*     */   {
/*  84 */     float f5 = 0.0625F;
/*  85 */     this.Base1.render(f5);
/*  86 */     this.Base2.render(f5);
/*  87 */     this.PillarMid.render(f5);
/*  88 */     this.Pillar2.render(f5);
/*  89 */     this.Pillar3.render(f5);
/*  90 */     this.Pillar4.render(f5);
/*  91 */     this.Pillar1.render(f5);
/*     */   }
/*     */   
/*     */   public void renderNozzle()
/*     */   {
/*  96 */     float f5 = 0.0625F;
/*  97 */     this.Nozzle1.render(f5);
/*  98 */     this.Nozzle2.render(f5);
/*     */   }
/*     */   
/*     */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*     */   {
/* 103 */     model.rotateAngleX = x;
/* 104 */     model.rotateAngleY = y;
/* 105 */     model.rotateAngleZ = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\block\ModelBoreBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */