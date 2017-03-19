/*     */ package thaumcraft.client.renderers.models.entity;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import thaumcraft.common.entities.monster.EntityEldritchCrab;
/*     */ 
/*     */ 
/*     */ public class ModelEldritchCrab
/*     */   extends ModelBase
/*     */ {
/*     */   ModelRenderer TailHelm;
/*     */   ModelRenderer TailBare;
/*     */   ModelRenderer RFLeg1;
/*     */   ModelRenderer RClaw1;
/*     */   ModelRenderer Head1;
/*     */   ModelRenderer RClaw0;
/*     */   ModelRenderer RClaw2;
/*     */   ModelRenderer LClaw2;
/*     */   ModelRenderer LClaw1;
/*     */   ModelRenderer RArm;
/*     */   ModelRenderer Torso;
/*     */   ModelRenderer RRLeg1;
/*     */   ModelRenderer Head0;
/*     */   ModelRenderer LRLeg1;
/*     */   ModelRenderer LFLeg1;
/*     */   ModelRenderer RRLeg0;
/*     */   ModelRenderer RFLeg0;
/*     */   ModelRenderer LFLeg0;
/*     */   ModelRenderer LRLeg0;
/*     */   ModelRenderer LClaw0;
/*     */   ModelRenderer LArm;
/*     */   
/*     */   public ModelEldritchCrab()
/*     */   {
/*  37 */     this.textureWidth = 128;
/*  38 */     this.textureHeight = 64;
/*     */     
/*  40 */     this.TailHelm = new ModelRenderer(this, 0, 0);
/*  41 */     this.TailHelm.addBox(-4.5F, -4.5F, -0.4F, 9, 9, 9);
/*  42 */     this.TailHelm.setRotationPoint(0.0F, 18.0F, 0.0F);
/*  43 */     setRotation(this.TailHelm, 0.1047198F, 0.0F, 0.0F);
/*     */     
/*  45 */     this.TailBare = new ModelRenderer(this, 64, 0);
/*  46 */     this.TailBare.addBox(-4.0F, -4.0F, -0.4F, 8, 8, 8);
/*  47 */     this.TailBare.setRotationPoint(0.0F, 18.0F, 0.0F);
/*  48 */     setRotation(this.TailBare, 0.1047198F, 0.0F, 0.0F);
/*     */     
/*  50 */     this.RClaw1 = new ModelRenderer(this, 0, 47);
/*  51 */     this.RClaw1.addBox(-2.0F, -1.0F, -5.066667F, 4, 3, 5);
/*  52 */     this.RClaw1.setRotationPoint(-6.0F, 15.5F, -10.0F);
/*     */     
/*  54 */     this.Head1 = new ModelRenderer(this, 0, 38);
/*  55 */     this.Head1.addBox(-2.0F, -1.5F, -9.066667F, 4, 4, 1);
/*  56 */     this.Head1.setRotationPoint(0.0F, 18.0F, 0.0F);
/*     */     
/*  58 */     this.RClaw0 = new ModelRenderer(this, 0, 55);
/*  59 */     this.RClaw0.addBox(-2.0F, -2.5F, -3.066667F, 4, 5, 3);
/*  60 */     this.RClaw0.setRotationPoint(-6.0F, 17.0F, -7.0F);
/*     */     
/*  62 */     this.RClaw2 = new ModelRenderer(this, 14, 54);
/*  63 */     this.RClaw2.addBox(-1.5F, -1.0F, -4.066667F, 3, 2, 5);
/*  64 */     this.RClaw2.setRotationPoint(-6.0F, 18.5F, -10.0F);
/*  65 */     setRotation(this.RClaw2, 0.3141593F, 0.0F, 0.0F);
/*     */     
/*  67 */     this.RArm = new ModelRenderer(this, 44, 4);
/*  68 */     this.RArm.addBox(-1.0F, -1.0F, -5.066667F, 2, 2, 6);
/*  69 */     this.RArm.setRotationPoint(-3.0F, 17.0F, -4.0F);
/*  70 */     setRotation(this.RArm, 0.0F, 0.7504916F, 0.0F);
/*     */     
/*  72 */     this.LClaw2 = new ModelRenderer(this, 14, 54);
/*  73 */     this.LClaw2.addBox(-1.5F, -1.0F, -4.066667F, 3, 2, 5);
/*  74 */     this.LClaw2.setRotationPoint(6.0F, 18.5F, -10.0F);
/*  75 */     setRotation(this.LClaw2, 0.3141593F, 0.0F, 0.0F);
/*     */     
/*  77 */     this.LClaw1 = new ModelRenderer(this, 0, 47);
/*  78 */     this.LClaw1.mirror = true;
/*  79 */     this.LClaw1.addBox(-2.0F, -1.0F, -5.066667F, 4, 3, 5);
/*  80 */     this.LClaw1.setRotationPoint(6.0F, 15.5F, -10.0F);
/*     */     
/*  82 */     this.LClaw0 = new ModelRenderer(this, 0, 55);
/*  83 */     this.LClaw0.mirror = true;
/*  84 */     this.LClaw0.addBox(-2.0F, -2.5F, -3.066667F, 4, 5, 3);
/*  85 */     this.LClaw0.setRotationPoint(6.0F, 17.0F, -7.0F);
/*     */     
/*  87 */     this.LArm = new ModelRenderer(this, 44, 4);
/*  88 */     this.LArm.addBox(-1.0F, -1.0F, -4.066667F, 2, 2, 6);
/*  89 */     this.LArm.setRotationPoint(4.0F, 17.0F, -5.0F);
/*  90 */     setRotation(this.LArm, 0.0F, -0.7504916F, 0.0F);
/*     */     
/*  92 */     this.Torso = new ModelRenderer(this, 0, 18);
/*  93 */     this.Torso.addBox(-3.5F, -3.5F, -6.066667F, 7, 7, 6);
/*  94 */     this.Torso.setRotationPoint(0.0F, 18.0F, 0.0F);
/*  95 */     setRotation(this.Torso, 0.0523599F, 0.0F, 0.0F);
/*     */     
/*  97 */     this.Head0 = new ModelRenderer(this, 0, 31);
/*  98 */     this.Head0.addBox(-2.5F, -2.0F, -8.066667F, 5, 5, 2);
/*  99 */     this.Head0.setRotationPoint(0.0F, 18.0F, 0.0F);
/*     */     
/* 101 */     this.RRLeg1 = new ModelRenderer(this, 36, 4);
/* 102 */     this.RRLeg1.addBox(-4.5F, 1.0F, -0.9F, 2, 5, 2);
/* 103 */     this.RRLeg1.setRotationPoint(-4.0F, 20.0F, -1.5F);
/*     */     
/* 105 */     this.RFLeg1 = new ModelRenderer(this, 36, 4);
/* 106 */     this.RFLeg1.addBox(-5.0F, 1.0F, -1.066667F, 2, 5, 2);
/* 107 */     this.RFLeg1.setRotationPoint(-4.0F, 20.0F, -3.5F);
/*     */     
/* 109 */     this.LRLeg1 = new ModelRenderer(this, 36, 4);
/* 110 */     this.LRLeg1.addBox(2.5F, 1.0F, -0.9F, 2, 5, 2);
/* 111 */     this.LRLeg1.setRotationPoint(4.0F, 20.0F, -1.5F);
/*     */     
/* 113 */     this.LFLeg1 = new ModelRenderer(this, 36, 4);
/* 114 */     this.LFLeg1.addBox(3.0F, 1.0F, -1.066667F, 2, 5, 2);
/* 115 */     this.LFLeg1.setRotationPoint(4.0F, 20.0F, -3.5F);
/*     */     
/* 117 */     this.RRLeg0 = new ModelRenderer(this, 36, 0);
/* 118 */     this.RRLeg0.addBox(-4.5F, -1.0F, -0.9F, 6, 2, 2);
/* 119 */     this.RRLeg0.setRotationPoint(-4.0F, 20.0F, -1.5F);
/*     */     
/* 121 */     this.RFLeg0 = new ModelRenderer(this, 36, 0);
/* 122 */     this.RFLeg0.addBox(-5.0F, -1.0F, -1.066667F, 6, 2, 2);
/* 123 */     this.RFLeg0.setRotationPoint(-4.0F, 20.0F, -3.5F);
/*     */     
/* 125 */     this.LFLeg0 = new ModelRenderer(this, 36, 0);
/* 126 */     this.LFLeg0.addBox(-1.0F, -1.0F, -1.066667F, 6, 2, 2);
/* 127 */     this.LFLeg0.setRotationPoint(4.0F, 20.0F, -3.5F);
/*     */     
/* 129 */     this.LRLeg0 = new ModelRenderer(this, 36, 0);
/* 130 */     this.LRLeg0.addBox(-1.5F, -1.0F, -0.9F, 6, 2, 2);
/* 131 */     this.LRLeg0.setRotationPoint(4.0F, 20.0F, -1.5F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 139 */     super.render(entity, f, f1, f2, f3, f4, f5);
/* 140 */     setRotationAngles(f, f1, f2, f3, f4, f5, entity);
/*     */     
/* 142 */     if (((entity instanceof EntityEldritchCrab)) && (((EntityEldritchCrab)entity).hasHelm())) {
/* 143 */       this.TailHelm.render(f5);
/*     */     } else {
/* 145 */       this.TailBare.render(f5);
/*     */     }
/*     */     
/* 148 */     this.RFLeg1.render(f5);
/* 149 */     this.RClaw1.render(f5);
/* 150 */     this.Head1.render(f5);
/* 151 */     this.RClaw0.render(f5);
/* 152 */     this.RClaw2.render(f5);
/* 153 */     this.LClaw2.render(f5);
/* 154 */     this.LClaw1.render(f5);
/* 155 */     this.RArm.render(f5);
/* 156 */     this.Torso.render(f5);
/* 157 */     this.RRLeg1.render(f5);
/* 158 */     this.Head0.render(f5);
/* 159 */     this.LRLeg1.render(f5);
/* 160 */     this.LFLeg1.render(f5);
/* 161 */     this.RRLeg0.render(f5);
/* 162 */     this.RFLeg0.render(f5);
/* 163 */     this.LFLeg0.render(f5);
/* 164 */     this.LRLeg0.render(f5);
/* 165 */     this.LClaw0.render(f5);
/* 166 */     this.LArm.render(f5);
/*     */   }
/*     */   
/*     */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*     */   {
/* 171 */     model.rotateAngleX = x;
/* 172 */     model.rotateAngleY = y;
/* 173 */     model.rotateAngleZ = z;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity)
/*     */   {
/* 179 */     setRotation(this.RRLeg1, 0.0F, 0.2094395F, 0.4363323F);
/* 180 */     setRotation(this.RFLeg1, 0.0F, -0.2094395F, 0.4363323F);
/* 181 */     setRotation(this.LRLeg1, 0.0F, -0.2094395F, -0.4363323F);
/* 182 */     setRotation(this.LFLeg1, 0.0F, 0.2094395F, -0.4363323F);
/* 183 */     setRotation(this.RRLeg0, 0.0F, 0.2094395F, 0.4363323F);
/* 184 */     setRotation(this.RFLeg0, 0.0F, -0.2094395F, 0.4363323F);
/* 185 */     setRotation(this.LFLeg0, 0.0F, 0.2094395F, -0.4363323F);
/* 186 */     setRotation(this.LRLeg0, 0.0F, -0.2094395F, -0.4363323F);
/*     */     
/* 188 */     float f9 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * par2;
/* 189 */     float f10 = -(MathHelper.cos(par1 * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * par2;
/*     */     
/* 191 */     this.RRLeg1.rotateAngleY += f9;this.RRLeg0.rotateAngleY += f9;
/* 192 */     this.LRLeg1.rotateAngleY += -f9;this.LRLeg0.rotateAngleY += -f9;
/* 193 */     this.RFLeg1.rotateAngleY += f10;this.RFLeg0.rotateAngleY += f10;
/* 194 */     this.LFLeg1.rotateAngleY += -f10;this.LFLeg0.rotateAngleY += -f10;
/* 195 */     this.RRLeg1.rotateAngleZ += f9;this.RRLeg0.rotateAngleZ += f9;
/* 196 */     this.LRLeg1.rotateAngleZ += -f9;this.LRLeg0.rotateAngleZ += -f9;
/* 197 */     this.RFLeg1.rotateAngleZ += f10;this.RFLeg0.rotateAngleZ += f10;
/* 198 */     this.LFLeg1.rotateAngleZ += -f10;this.LFLeg0.rotateAngleZ += -f10;
/*     */     
/* 200 */     this.TailBare.rotateAngleY = (this.TailHelm.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.125F);
/* 201 */     this.TailBare.rotateAngleZ = (this.TailHelm.rotateAngleZ = MathHelper.cos(par1 * 0.6662F) * par2 * 0.125F);
/*     */     
/* 203 */     this.RClaw2.rotateAngleX = (0.3141593F - MathHelper.sin(entity.ticksExisted / 4.0F) * 0.25F);
/* 204 */     this.LClaw2.rotateAngleX = (0.3141593F + MathHelper.sin(entity.ticksExisted / 4.1F) * 0.25F);
/* 205 */     this.RClaw1.rotateAngleX = (MathHelper.sin(entity.ticksExisted / 4.0F) * 0.125F);
/* 206 */     this.LClaw1.rotateAngleX = (-MathHelper.sin(entity.ticksExisted / 4.1F) * 0.125F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\entity\ModelEldritchCrab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */