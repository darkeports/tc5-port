/*     */ package thaumcraft.client.renderers.models.entity;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import thaumcraft.common.entities.monster.EntityPech;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelPech
/*     */   extends ModelBiped
/*     */ {
/*     */   ModelRenderer Jowls;
/*     */   ModelRenderer LowerPack;
/*     */   ModelRenderer UpperPack;
/*     */   
/*     */   public ModelPech()
/*     */   {
/*  24 */     this.textureWidth = 128;
/*  25 */     this.textureHeight = 64;
/*     */     
/*  27 */     this.bipedBody = new ModelRenderer(this, 34, 12);
/*  28 */     this.bipedBody.addBox(-3.0F, 0.0F, 0.0F, 6, 10, 6);
/*  29 */     this.bipedBody.setRotationPoint(0.0F, 9.0F, -3.0F);
/*  30 */     this.bipedBody.setTextureSize(128, 64);
/*  31 */     this.bipedBody.mirror = true;
/*  32 */     setRotation(this.bipedBody, 0.3129957F, 0.0F, 0.0F);
/*     */     
/*  34 */     this.bipedRightLeg = new ModelRenderer(this, 35, 1);
/*  35 */     this.bipedRightLeg.mirror = true;
/*  36 */     this.bipedRightLeg.addBox(-2.9F, 0.0F, 0.0F, 3, 6, 3);
/*  37 */     this.bipedRightLeg.setRotationPoint(0.0F, 18.0F, 0.0F);
/*  38 */     this.bipedRightLeg.setTextureSize(128, 64);
/*  39 */     this.bipedRightLeg.mirror = true;
/*  40 */     setRotation(this.bipedRightLeg, 0.0F, 0.0F, 0.0F);
/*  41 */     this.bipedRightLeg.mirror = false;
/*     */     
/*  43 */     this.bipedLeftLeg = new ModelRenderer(this, 35, 1);
/*  44 */     this.bipedLeftLeg.addBox(-0.1F, 0.0F, 0.0F, 3, 6, 3);
/*  45 */     this.bipedLeftLeg.setRotationPoint(0.0F, 18.0F, 0.0F);
/*  46 */     this.bipedLeftLeg.setTextureSize(128, 64);
/*  47 */     this.bipedLeftLeg.mirror = true;
/*  48 */     setRotation(this.bipedLeftLeg, 0.0F, 0.0F, 0.0F);
/*     */     
/*  50 */     this.bipedHead = new ModelRenderer(this, 2, 11);
/*  51 */     this.bipedHead.addBox(-3.5F, -5.0F, -5.0F, 7, 5, 5);
/*  52 */     this.bipedHead.setRotationPoint(0.0F, 8.0F, 0.0F);
/*  53 */     this.bipedHead.setTextureSize(128, 64);
/*  54 */     this.bipedHead.mirror = true;
/*  55 */     setRotation(this.bipedHead, 0.0F, 0.0F, 0.0F);
/*     */     
/*  57 */     this.Jowls = new ModelRenderer(this, 1, 21);
/*  58 */     this.Jowls.addBox(-4.0F, -1.0F, -6.0F, 8, 3, 5);
/*  59 */     this.Jowls.setRotationPoint(0.0F, 8.0F, 0.0F);
/*  60 */     this.Jowls.setTextureSize(128, 64);
/*  61 */     this.Jowls.mirror = true;
/*  62 */     setRotation(this.Jowls, 0.0F, 0.0F, 0.0F);
/*     */     
/*  64 */     this.LowerPack = new ModelRenderer(this, 0, 0);
/*  65 */     this.LowerPack.addBox(-5.0F, 0.0F, 0.0F, 10, 5, 5);
/*  66 */     this.LowerPack.setRotationPoint(0.0F, 10.0F, 3.5F);
/*  67 */     this.LowerPack.setTextureSize(128, 64);
/*  68 */     this.LowerPack.mirror = true;
/*  69 */     setRotation(this.LowerPack, 0.3013602F, 0.0F, 0.0F);
/*     */     
/*  71 */     this.UpperPack = new ModelRenderer(this, 64, 1);
/*  72 */     this.UpperPack.addBox(-7.5F, -14.0F, 0.0F, 15, 14, 11);
/*  73 */     this.UpperPack.setRotationPoint(0.0F, 10.0F, 3.0F);
/*  74 */     this.UpperPack.setTextureSize(128, 64);
/*  75 */     this.UpperPack.mirror = true;
/*  76 */     setRotation(this.UpperPack, 0.4537856F, 0.0F, 0.0F);
/*     */     
/*     */ 
/*  79 */     this.bipedRightArm = new ModelRenderer(this, 52, 2);
/*  80 */     this.bipedRightArm.mirror = true;
/*  81 */     this.bipedRightArm.addBox(-2.0F, 0.0F, -1.0F, 2, 6, 2);
/*  82 */     this.bipedRightArm.setRotationPoint(-3.0F, 10.0F, -1.0F);
/*  83 */     this.bipedRightArm.setTextureSize(128, 64);
/*  84 */     this.bipedRightArm.mirror = true;
/*  85 */     setRotation(this.bipedRightArm, 0.0F, 0.0F, 0.0F);
/*  86 */     this.bipedRightArm.mirror = false;
/*     */     
/*  88 */     this.bipedLeftArm = new ModelRenderer(this, 52, 2);
/*  89 */     this.bipedLeftArm.addBox(0.0F, 0.0F, -1.0F, 2, 6, 2);
/*  90 */     this.bipedLeftArm.setRotationPoint(3.0F, 10.0F, -1.0F);
/*  91 */     this.bipedLeftArm.setTextureSize(128, 64);
/*  92 */     this.bipedLeftArm.mirror = true;
/*  93 */     setRotation(this.bipedLeftArm, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*     */   {
/*  99 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/* 100 */     this.bipedBody.render(par7);
/* 101 */     this.bipedRightLeg.render(par7);
/* 102 */     this.bipedLeftLeg.render(par7);
/* 103 */     this.bipedHead.render(par7);
/* 104 */     this.Jowls.render(par7);
/* 105 */     this.LowerPack.render(par7);
/* 106 */     this.UpperPack.render(par7);
/* 107 */     this.bipedRightArm.render(par7);
/* 108 */     this.bipedLeftArm.render(par7);
/*     */   }
/*     */   
/*     */ 
/*     */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*     */   {
/* 114 */     model.rotateAngleX = x;
/* 115 */     model.rotateAngleY = y;
/* 116 */     model.rotateAngleZ = z;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity)
/*     */   {
/* 122 */     this.bipedHead.rotateAngleY = (par4 / 57.295776F);
/* 123 */     this.bipedHead.rotateAngleX = (par5 / 57.295776F);
/*     */     
/* 125 */     float mumble = 0.0F;
/* 126 */     if ((entity instanceof EntityPech)) {
/* 127 */       mumble = ((EntityPech)entity).mumble;
/*     */     }
/*     */     
/* 130 */     this.Jowls.rotateAngleY = this.bipedHead.rotateAngleY;
/* 131 */     this.Jowls.rotateAngleX = (this.bipedHead.rotateAngleX + (0.2617994F + MathHelper.cos(par1 * 0.6662F) * par2 * 0.25F) + 0.34906587F * Math.abs(MathHelper.sin(mumble)));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 136 */     this.bipedRightArm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 2.0F * par2 * 0.5F);
/* 137 */     this.bipedLeftArm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F);
/* 138 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 139 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 140 */     this.bipedRightLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.4F * par2);
/* 141 */     this.bipedLeftLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.1415927F) * 1.4F * par2);
/* 142 */     this.bipedRightLeg.rotateAngleY = 0.0F;
/* 143 */     this.bipedLeftLeg.rotateAngleY = 0.0F;
/*     */     
/* 145 */     this.LowerPack.rotateAngleY = (MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.125F);
/* 146 */     this.LowerPack.rotateAngleZ = (MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.125F);
/*     */     
/* 148 */     if (this.isRiding)
/*     */     {
/* 150 */       this.bipedRightArm.rotateAngleX += -0.62831855F;
/* 151 */       this.bipedLeftArm.rotateAngleX += -0.62831855F;
/* 152 */       this.bipedRightLeg.rotateAngleX = -1.2566371F;
/* 153 */       this.bipedLeftLeg.rotateAngleX = -1.2566371F;
/* 154 */       this.bipedRightLeg.rotateAngleY = 0.31415927F;
/* 155 */       this.bipedLeftLeg.rotateAngleY = -0.31415927F;
/*     */     }
/*     */     
/* 158 */     this.bipedRightArm.rotateAngleY = 0.0F;
/* 159 */     this.bipedLeftArm.rotateAngleY = 0.0F;
/*     */     
/*     */ 
/*     */ 
/* 163 */     if (this.swingProgress > -9990.0F)
/*     */     {
/* 165 */       float f6 = this.swingProgress;
/* 166 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 167 */       this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 168 */       this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
/* 169 */       f6 = 1.0F - this.swingProgress;
/* 170 */       f6 *= f6;
/* 171 */       f6 *= f6;
/* 172 */       f6 = 1.0F - f6;
/* 173 */       float f7 = MathHelper.sin(f6 * 3.1415927F);
/* 174 */       float f8 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
/* 175 */       this.bipedRightArm.rotateAngleX = ((float)(this.bipedRightArm.rotateAngleX - (f7 * 1.2D + f8)));
/* 176 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
/* 177 */       this.bipedRightArm.rotateAngleZ = (MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F);
/*     */     }
/*     */     
/* 180 */     if (entity.isSneaking())
/*     */     {
/* 182 */       this.bipedRightArm.rotateAngleX += 0.4F;
/* 183 */       this.bipedLeftArm.rotateAngleX += 0.4F;
/*     */     }
/*     */     
/* 186 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
/* 187 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
/* 188 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
/* 189 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\entity\ModelPech.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */