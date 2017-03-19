/*     */ package thaumcraft.client.renderers.models.gear;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Rotations;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ 
/*     */ public class ModelCustomArmor extends ModelBiped
/*     */ {
/*     */   public ModelCustomArmor(float f, int i, int j, int k)
/*     */   {
/*  16 */     super(f, i, j, k);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entity)
/*     */   {
/*  24 */     if ((entity instanceof EntityLivingBase)) {
/*  25 */       this.swingProgress = ((EntityLivingBase)entity).getSwingProgress(UtilsFX.sysPartialTicks);
/*     */     }
/*     */     
/*  28 */     if ((entity instanceof EntityArmorStand))
/*     */     {
/*  30 */       setRotationAnglesStand(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entity);
/*     */     }
/*  32 */     else if (((entity instanceof net.minecraft.entity.monster.EntitySkeleton)) || ((entity instanceof net.minecraft.entity.monster.EntityZombie))) {
/*  33 */       setRotationAnglesZombie(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entity);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/*  39 */       this.bipedHead.rotateAngleY = (p_78087_4_ / 57.295776F);
/*  40 */       this.bipedHead.rotateAngleX = (p_78087_5_ / 57.295776F);
/*  41 */       this.bipedRightArm.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 2.0F * p_78087_2_ * 0.5F);
/*  42 */       this.bipedLeftArm.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 2.0F * p_78087_2_ * 0.5F);
/*  43 */       this.bipedRightArm.rotateAngleZ = 0.0F;
/*  44 */       this.bipedLeftArm.rotateAngleZ = 0.0F;
/*  45 */       this.bipedRightLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_);
/*  46 */       this.bipedLeftLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_);
/*  47 */       this.bipedRightLeg.rotateAngleY = 0.0F;
/*  48 */       this.bipedLeftLeg.rotateAngleY = 0.0F;
/*     */       
/*  50 */       if (this.isRiding)
/*     */       {
/*  52 */         this.bipedRightArm.rotateAngleX += -0.62831855F;
/*  53 */         this.bipedLeftArm.rotateAngleX += -0.62831855F;
/*  54 */         this.bipedRightLeg.rotateAngleX = -1.2566371F;
/*  55 */         this.bipedLeftLeg.rotateAngleX = -1.2566371F;
/*  56 */         this.bipedRightLeg.rotateAngleY = 0.31415927F;
/*  57 */         this.bipedLeftLeg.rotateAngleY = -0.31415927F;
/*     */       }
/*     */       
/*  60 */       if (this.heldItemLeft != 0)
/*     */       {
/*  62 */         this.bipedLeftArm.rotateAngleX = (this.bipedLeftArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemLeft);
/*     */       }
/*     */       
/*  65 */       this.bipedRightArm.rotateAngleY = 0.0F;
/*  66 */       this.bipedRightArm.rotateAngleZ = 0.0F;
/*     */       
/*  68 */       switch (this.heldItemRight)
/*     */       {
/*     */       case 0: 
/*     */       case 2: 
/*     */       default: 
/*     */         break;
/*     */       case 1: 
/*  75 */         this.bipedRightArm.rotateAngleX = (this.bipedRightArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemRight);
/*  76 */         break;
/*     */       case 3: 
/*  78 */         this.bipedRightArm.rotateAngleX = (this.bipedRightArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemRight);
/*  79 */         this.bipedRightArm.rotateAngleY = -0.5235988F;
/*     */       }
/*     */       
/*  82 */       this.bipedLeftArm.rotateAngleY = 0.0F;
/*     */       
/*     */ 
/*     */ 
/*  86 */       if (this.swingProgress > -9990.0F)
/*     */       {
/*  88 */         float f6 = this.swingProgress;
/*  89 */         this.bipedBody.rotateAngleY = (MathHelper.sin(MathHelper.sqrt_float(f6) * 3.1415927F * 2.0F) * 0.2F);
/*  90 */         this.bipedRightArm.rotationPointZ = (MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F);
/*  91 */         this.bipedRightArm.rotationPointX = (-MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F);
/*  92 */         this.bipedLeftArm.rotationPointZ = (-MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F);
/*  93 */         this.bipedLeftArm.rotationPointX = (MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F);
/*  94 */         this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
/*  95 */         this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
/*  96 */         this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
/*  97 */         f6 = 1.0F - this.swingProgress;
/*  98 */         f6 *= f6;
/*  99 */         f6 *= f6;
/* 100 */         f6 = 1.0F - f6;
/* 101 */         float f7 = MathHelper.sin(f6 * 3.1415927F);
/* 102 */         float f8 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
/* 103 */         this.bipedRightArm.rotateAngleX = ((float)(this.bipedRightArm.rotateAngleX - (f7 * 1.2D + f8)));
/* 104 */         this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
/* 105 */         this.bipedRightArm.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
/*     */       }
/*     */       
/* 108 */       if (this.isSneak)
/*     */       {
/* 110 */         this.bipedBody.rotateAngleX = 0.5F;
/* 111 */         this.bipedRightArm.rotateAngleX += 0.4F;
/* 112 */         this.bipedLeftArm.rotateAngleX += 0.4F;
/* 113 */         this.bipedRightLeg.rotationPointZ = 4.0F;
/* 114 */         this.bipedLeftLeg.rotationPointZ = 4.0F;
/* 115 */         this.bipedRightLeg.rotationPointY = 13.0F;
/* 116 */         this.bipedLeftLeg.rotationPointY = 13.0F;
/* 117 */         this.bipedHead.rotationPointY = 4.5F;
/*     */         
/* 119 */         this.bipedBody.rotationPointY = 4.5F;
/* 120 */         this.bipedRightArm.rotationPointY = 5.0F;
/* 121 */         this.bipedLeftArm.rotationPointY = 5.0F;
/*     */       }
/*     */       else
/*     */       {
/* 125 */         this.bipedBody.rotateAngleX = 0.0F;
/* 126 */         this.bipedRightLeg.rotationPointZ = 0.1F;
/* 127 */         this.bipedLeftLeg.rotationPointZ = 0.1F;
/* 128 */         this.bipedRightLeg.rotationPointY = 12.0F;
/* 129 */         this.bipedLeftLeg.rotationPointY = 12.0F;
/* 130 */         this.bipedHead.rotationPointY = 0.0F;
/*     */         
/* 132 */         this.bipedBody.rotationPointY = 0.0F;
/* 133 */         this.bipedRightArm.rotationPointY = 2.0F;
/* 134 */         this.bipedLeftArm.rotationPointY = 2.0F;
/*     */       }
/*     */       
/* 137 */       this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
/* 138 */       this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
/* 139 */       this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
/* 140 */       this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
/*     */       
/* 142 */       if (this.aimedBow)
/*     */       {
/* 144 */         float f6 = 0.0F;
/* 145 */         float f7 = 0.0F;
/* 146 */         this.bipedRightArm.rotateAngleZ = 0.0F;
/* 147 */         this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 148 */         this.bipedRightArm.rotateAngleY = (-(0.1F - f6 * 0.6F) + this.bipedHead.rotateAngleY);
/* 149 */         this.bipedLeftArm.rotateAngleY = (0.1F - f6 * 0.6F + this.bipedHead.rotateAngleY + 0.4F);
/* 150 */         this.bipedRightArm.rotateAngleX = (-1.5707964F + this.bipedHead.rotateAngleX);
/* 151 */         this.bipedLeftArm.rotateAngleX = (-1.5707964F + this.bipedHead.rotateAngleX);
/* 152 */         this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
/* 153 */         this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
/* 154 */         this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
/* 155 */         this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
/* 156 */         this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
/* 157 */         this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
/*     */       }
/*     */       
/* 160 */       copyModelAngles(this.bipedHead, this.bipedHeadwear);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRotationAnglesZombie(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
/*     */   {
/* 171 */     super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
/*     */     
/* 173 */     float f6 = MathHelper.sin(this.swingProgress * 3.1415927F);
/* 174 */     float f7 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
/*     */     
/*     */ 
/* 177 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 178 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 179 */     this.bipedRightArm.rotateAngleY = (-(0.1F - f6 * 0.6F));
/* 180 */     this.bipedLeftArm.rotateAngleY = (0.1F - f6 * 0.6F);
/* 181 */     this.bipedRightArm.rotateAngleX = -1.5707964F;
/* 182 */     this.bipedLeftArm.rotateAngleX = -1.5707964F;
/* 183 */     this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
/* 184 */     this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
/* 185 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
/* 186 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
/* 187 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
/* 188 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setRotationAnglesStand(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
/*     */   {
/* 194 */     if ((p_78087_7_ instanceof EntityArmorStand))
/*     */     {
/* 196 */       EntityArmorStand entityarmorstand = (EntityArmorStand)p_78087_7_;
/* 197 */       this.bipedHead.rotateAngleX = (0.017453292F * entityarmorstand.getHeadRotation().getX());
/* 198 */       this.bipedHead.rotateAngleY = (0.017453292F * entityarmorstand.getHeadRotation().getY());
/* 199 */       this.bipedHead.rotateAngleZ = (0.017453292F * entityarmorstand.getHeadRotation().getZ());
/* 200 */       this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
/* 201 */       this.bipedBody.rotateAngleX = (0.017453292F * entityarmorstand.getBodyRotation().getX());
/* 202 */       this.bipedBody.rotateAngleY = (0.017453292F * entityarmorstand.getBodyRotation().getY());
/* 203 */       this.bipedBody.rotateAngleZ = (0.017453292F * entityarmorstand.getBodyRotation().getZ());
/* 204 */       this.bipedLeftArm.rotateAngleX = (0.017453292F * entityarmorstand.getLeftArmRotation().getX());
/* 205 */       this.bipedLeftArm.rotateAngleY = (0.017453292F * entityarmorstand.getLeftArmRotation().getY());
/* 206 */       this.bipedLeftArm.rotateAngleZ = (0.017453292F * entityarmorstand.getLeftArmRotation().getZ());
/* 207 */       this.bipedRightArm.rotateAngleX = (0.017453292F * entityarmorstand.getRightArmRotation().getX());
/* 208 */       this.bipedRightArm.rotateAngleY = (0.017453292F * entityarmorstand.getRightArmRotation().getY());
/* 209 */       this.bipedRightArm.rotateAngleZ = (0.017453292F * entityarmorstand.getRightArmRotation().getZ());
/* 210 */       this.bipedLeftLeg.rotateAngleX = (0.017453292F * entityarmorstand.getLeftLegRotation().getX());
/* 211 */       this.bipedLeftLeg.rotateAngleY = (0.017453292F * entityarmorstand.getLeftLegRotation().getY());
/* 212 */       this.bipedLeftLeg.rotateAngleZ = (0.017453292F * entityarmorstand.getLeftLegRotation().getZ());
/* 213 */       this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
/* 214 */       this.bipedRightLeg.rotateAngleX = (0.017453292F * entityarmorstand.getRightLegRotation().getX());
/* 215 */       this.bipedRightLeg.rotateAngleY = (0.017453292F * entityarmorstand.getRightLegRotation().getY());
/* 216 */       this.bipedRightLeg.rotateAngleZ = (0.017453292F * entityarmorstand.getRightLegRotation().getZ());
/* 217 */       this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
/* 218 */       copyModelAngles(this.bipedHead, this.bipedHeadwear);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\gear\ModelCustomArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */