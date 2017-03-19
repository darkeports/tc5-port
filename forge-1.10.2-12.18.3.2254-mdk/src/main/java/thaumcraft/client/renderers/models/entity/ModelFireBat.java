/*     */ package thaumcraft.client.renderers.models.entity;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.common.entities.monster.EntityFireBat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class ModelFireBat
/*     */   extends ModelBase
/*     */ {
/*     */   private ModelRenderer batHead;
/*     */   private ModelRenderer batBody;
/*     */   private ModelRenderer batRightWing;
/*     */   private ModelRenderer batLeftWing;
/*     */   private ModelRenderer batOuterRightWing;
/*     */   private ModelRenderer batOuterLeftWing;
/*     */   
/*     */   public ModelFireBat()
/*     */   {
/*  33 */     this.textureWidth = 64;
/*  34 */     this.textureHeight = 64;
/*  35 */     this.batHead = new ModelRenderer(this, 0, 0);
/*  36 */     this.batHead.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
/*  37 */     ModelRenderer var1 = new ModelRenderer(this, 24, 0);
/*  38 */     var1.addBox(-4.0F, -6.0F, -2.0F, 3, 4, 1);
/*  39 */     this.batHead.addChild(var1);
/*  40 */     ModelRenderer var2 = new ModelRenderer(this, 24, 0);
/*  41 */     var2.mirror = true;
/*  42 */     var2.addBox(1.0F, -6.0F, -2.0F, 3, 4, 1);
/*  43 */     this.batHead.addChild(var2);
/*  44 */     this.batBody = new ModelRenderer(this, 0, 16);
/*  45 */     this.batBody.addBox(-3.0F, 4.0F, -3.0F, 6, 12, 6);
/*  46 */     this.batBody.setTextureOffset(0, 34).addBox(-5.0F, 16.0F, 0.0F, 10, 6, 1);
/*  47 */     this.batRightWing = new ModelRenderer(this, 42, 0);
/*  48 */     this.batRightWing.addBox(-12.0F, 1.0F, 1.5F, 10, 16, 1);
/*  49 */     this.batOuterRightWing = new ModelRenderer(this, 24, 16);
/*  50 */     this.batOuterRightWing.setRotationPoint(-12.0F, 1.0F, 1.5F);
/*  51 */     this.batOuterRightWing.addBox(-8.0F, 1.0F, 0.0F, 8, 12, 1);
/*  52 */     this.batLeftWing = new ModelRenderer(this, 42, 0);
/*  53 */     this.batLeftWing.mirror = true;
/*  54 */     this.batLeftWing.addBox(2.0F, 1.0F, 1.5F, 10, 16, 1);
/*  55 */     this.batOuterLeftWing = new ModelRenderer(this, 24, 16);
/*  56 */     this.batOuterLeftWing.mirror = true;
/*  57 */     this.batOuterLeftWing.setRotationPoint(12.0F, 1.0F, 1.5F);
/*  58 */     this.batOuterLeftWing.addBox(0.0F, 1.0F, 0.0F, 8, 12, 1);
/*  59 */     this.batBody.addChild(this.batRightWing);
/*  60 */     this.batBody.addChild(this.batLeftWing);
/*  61 */     this.batRightWing.addChild(this.batOuterRightWing);
/*  62 */     this.batLeftWing.addChild(this.batOuterLeftWing);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBatSize()
/*     */   {
/*  71 */     return 36;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*     */   {
/*  79 */     EntityFireBat var8 = (EntityFireBat)par1Entity;
/*     */     
/*  81 */     if (var8.getIsBatHanging())
/*     */     {
/*  83 */       this.batHead.rotateAngleX = (par6 / 57.295776F);
/*  84 */       this.batHead.rotateAngleY = (3.1415927F - par5 / 57.295776F);
/*  85 */       this.batHead.rotateAngleZ = 3.1415927F;
/*  86 */       this.batHead.setRotationPoint(0.0F, -2.0F, 0.0F);
/*  87 */       this.batRightWing.setRotationPoint(-3.0F, 0.0F, 3.0F);
/*  88 */       this.batLeftWing.setRotationPoint(3.0F, 0.0F, 3.0F);
/*  89 */       this.batBody.rotateAngleX = 3.1415927F;
/*  90 */       this.batRightWing.rotateAngleX = -0.15707964F;
/*  91 */       this.batRightWing.rotateAngleY = -1.2566371F;
/*  92 */       this.batOuterRightWing.rotateAngleY = -1.7278761F;
/*  93 */       this.batLeftWing.rotateAngleX = this.batRightWing.rotateAngleX;
/*  94 */       this.batLeftWing.rotateAngleY = (-this.batRightWing.rotateAngleY);
/*  95 */       this.batOuterLeftWing.rotateAngleY = (-this.batOuterRightWing.rotateAngleY);
/*     */     }
/*     */     else
/*     */     {
/*  99 */       this.batHead.rotateAngleX = (par6 / 57.295776F);
/* 100 */       this.batHead.rotateAngleY = (par5 / 57.295776F);
/* 101 */       this.batHead.rotateAngleZ = 0.0F;
/* 102 */       this.batHead.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 103 */       this.batRightWing.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 104 */       this.batLeftWing.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 105 */       this.batBody.rotateAngleX = (0.7853982F + MathHelper.cos(par4 * 0.1F) * 0.15F);
/* 106 */       this.batBody.rotateAngleY = 0.0F;
/* 107 */       this.batRightWing.rotateAngleY = (MathHelper.cos(par4 * 1.3F) * 3.1415927F * 0.25F);
/* 108 */       this.batLeftWing.rotateAngleY = (-this.batRightWing.rotateAngleY);
/* 109 */       this.batOuterRightWing.rotateAngleY = (this.batRightWing.rotateAngleY * 0.5F);
/* 110 */       this.batOuterLeftWing.rotateAngleY = (-this.batRightWing.rotateAngleY * 0.5F);
/*     */     }
/*     */     
/* 113 */     this.batHead.render(par7);
/* 114 */     this.batBody.render(par7);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\entity\ModelFireBat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */