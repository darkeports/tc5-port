/*     */ package thaumcraft.client.renderers.models.gear;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelKnightArmor
/*     */   extends ModelCustomArmor
/*     */ {
/*     */   ModelRenderer Frontcloth1;
/*     */   ModelRenderer Helmet;
/*     */   ModelRenderer BeltR;
/*     */   ModelRenderer Mbelt;
/*     */   ModelRenderer MbeltL;
/*     */   ModelRenderer MbeltR;
/*     */   ModelRenderer BeltL;
/*     */   ModelRenderer Chestplate;
/*     */   ModelRenderer CloakAtL;
/*     */   ModelRenderer Backplate;
/*     */   ModelRenderer Cloak3;
/*     */   ModelRenderer CloakAtR;
/*     */   ModelRenderer Tabbard;
/*     */   ModelRenderer Cloak1;
/*     */   ModelRenderer Cloak2;
/*     */   ModelRenderer ShoulderR1;
/*     */   ModelRenderer GauntletR;
/*     */   ModelRenderer GauntletstrapR1;
/*     */   ModelRenderer GauntletstrapR2;
/*     */   ModelRenderer ShoulderR;
/*     */   ModelRenderer ShoulderR0;
/*     */   ModelRenderer ShoulderR2;
/*     */   ModelRenderer ShoulderL1;
/*     */   ModelRenderer GauntletL;
/*     */   ModelRenderer GauntletstrapL1;
/*     */   ModelRenderer GauntletstrapL2;
/*     */   ModelRenderer ShoulderL;
/*     */   ModelRenderer ShoulderL0;
/*     */   ModelRenderer ShoulderL2;
/*     */   ModelRenderer SidepanelR3;
/*     */   ModelRenderer SidepanelR2;
/*     */   ModelRenderer SidepanelL2;
/*     */   ModelRenderer SidepanelR0;
/*     */   ModelRenderer SidepanelL0;
/*     */   ModelRenderer SidepanelR1;
/*     */   ModelRenderer SidepanelL3;
/*     */   ModelRenderer Frontcloth2;
/*     */   ModelRenderer SidepanelL1;
/*     */   
/*     */   public ModelKnightArmor(float f)
/*     */   {
/*  65 */     super(f, 0, 128, 64);
/*  66 */     this.textureWidth = 128;
/*  67 */     this.textureHeight = 64;
/*     */     
/*     */ 
/*     */ 
/*  71 */     this.Helmet = new ModelRenderer(this, 41, 8);
/*  72 */     this.Helmet.addBox(-4.5F, -9.0F, -4.5F, 9, 9, 9);
/*  73 */     this.Helmet.setTextureSize(128, 64);
/*  74 */     setRotation(this.Helmet, 0.0F, 0.0F, 0.0F);
/*     */     
/*  76 */     this.BeltR = new ModelRenderer(this, 76, 44);
/*  77 */     this.BeltR.addBox(-5.0F, 4.0F, -3.0F, 1, 3, 6);
/*  78 */     this.BeltR.setTextureSize(128, 64);
/*  79 */     setRotation(this.BeltR, 0.0F, 0.0F, 0.0F);
/*     */     
/*  81 */     this.Mbelt = new ModelRenderer(this, 56, 55);
/*  82 */     this.Mbelt.addBox(-4.0F, 8.0F, -3.0F, 8, 4, 1);
/*  83 */     this.Mbelt.setTextureSize(128, 64);
/*  84 */     setRotation(this.Mbelt, 0.0F, 0.0F, 0.0F);
/*     */     
/*  86 */     this.MbeltL = new ModelRenderer(this, 76, 44);
/*  87 */     this.MbeltL.addBox(4.0F, 8.0F, -3.0F, 1, 3, 6);
/*  88 */     this.MbeltL.setTextureSize(128, 64);
/*  89 */     setRotation(this.MbeltL, 0.0F, 0.0F, 0.0F);
/*     */     
/*  91 */     this.MbeltR = new ModelRenderer(this, 76, 44);
/*  92 */     this.MbeltR.addBox(-5.0F, 8.0F, -3.0F, 1, 3, 6);
/*  93 */     this.MbeltR.setTextureSize(128, 64);
/*  94 */     setRotation(this.MbeltR, 0.0F, 0.0F, 0.0F);
/*     */     
/*  96 */     this.BeltL = new ModelRenderer(this, 76, 44);
/*  97 */     this.BeltL.addBox(4.0F, 4.0F, -3.0F, 1, 3, 6);
/*  98 */     this.BeltL.setTextureSize(128, 64);
/*  99 */     setRotation(this.BeltL, 0.0F, 0.0F, 0.0F);
/*     */     
/* 101 */     this.Tabbard = new ModelRenderer(this, 114, 52);
/* 102 */     this.Tabbard.addBox(-3.0F, 1.2F, -3.5F, 6, 10, 1);
/* 103 */     this.Tabbard.setTextureSize(128, 64);
/* 104 */     setRotation(this.Tabbard, 0.0F, 0.0F, 0.0F);
/*     */     
/* 106 */     this.CloakAtL = new ModelRenderer(this, 0, 43);
/* 107 */     this.CloakAtL.addBox(2.5F, 1.0F, 2.0F, 2, 1, 3);
/* 108 */     this.CloakAtL.setTextureSize(128, 64);
/* 109 */     setRotation(this.CloakAtL, 0.1396263F, 0.0F, 0.0F);
/*     */     
/* 111 */     this.Backplate = new ModelRenderer(this, 36, 45);
/* 112 */     this.Backplate.addBox(-4.0F, 1.0F, 2.0F, 8, 11, 2);
/* 113 */     this.Backplate.setTextureSize(128, 64);
/* 114 */     setRotation(this.Backplate, 0.0F, 0.0F, 0.0F);
/*     */     
/* 116 */     this.Cloak1 = new ModelRenderer(this, 0, 47);
/* 117 */     this.Cloak1.addBox(0.0F, 0.0F, 0.0F, 9, 12, 1);
/* 118 */     this.Cloak1.setRotationPoint(-4.5F, 1.3F, 4.2F);
/* 119 */     this.Cloak1.setTextureSize(128, 64);
/* 120 */     setRotation(this.Cloak1, 0.1396263F, 0.0F, 0.0F);
/* 121 */     this.Cloak2 = new ModelRenderer(this, 0, 59);
/* 122 */     this.Cloak2.addBox(0.0F, 11.7F, -2.0F, 9, 4, 1);
/* 123 */     this.Cloak2.setRotationPoint(-4.5F, 1.3F, 4.2F);
/* 124 */     this.Cloak2.setTextureSize(128, 64);
/* 125 */     setRotation(this.Cloak2, 0.3069452F, 0.0F, 0.0F);
/* 126 */     this.Cloak3 = new ModelRenderer(this, 0, 59);
/* 127 */     this.Cloak3.addBox(0.0F, 15.2F, -4.2F, 9, 4, 1);
/* 128 */     this.Cloak3.setRotationPoint(-4.5F, 1.3F, 4.2F);
/* 129 */     this.Cloak3.setTextureSize(128, 64);
/* 130 */     setRotation(this.Cloak3, 0.4465716F, 0.0F, 0.0F);
/*     */     
/* 132 */     this.CloakAtR = new ModelRenderer(this, 0, 43);
/* 133 */     this.CloakAtR.addBox(-4.5F, 1.0F, 2.0F, 2, 1, 3);
/* 134 */     this.CloakAtR.setTextureSize(128, 64);
/* 135 */     setRotation(this.CloakAtR, 0.1396263F, 0.0F, 0.0F);
/*     */     
/* 137 */     this.Chestplate = new ModelRenderer(this, 56, 45);
/* 138 */     this.Chestplate.addBox(-4.0F, 1.0F, -3.0F, 8, 7, 1);
/* 139 */     this.Chestplate.setTextureSize(128, 64);
/* 140 */     setRotation(this.Chestplate, 0.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 146 */     this.ShoulderR1 = new ModelRenderer(this, 0, 19);
/* 147 */     this.ShoulderR1.addBox(-3.3F, 3.5F, -2.5F, 1, 1, 5);
/*     */     
/* 149 */     this.ShoulderR1.setTextureSize(128, 64);
/* 150 */     setRotation(this.ShoulderR1, 0.0F, 0.0F, 0.7853982F);
/*     */     
/* 152 */     this.GauntletR = new ModelRenderer(this, 100, 26);
/* 153 */     this.GauntletR.addBox(-3.5F, 3.5F, -2.5F, 2, 6, 5);
/*     */     
/* 155 */     this.GauntletR.setTextureSize(128, 64);
/* 156 */     this.GauntletR.mirror = true;
/* 157 */     setRotation(this.GauntletR, 0.0F, 0.0F, 0.0F);
/*     */     
/* 159 */     this.GauntletstrapR1 = new ModelRenderer(this, 84, 31);
/* 160 */     this.GauntletstrapR1.addBox(-1.5F, 3.5F, -2.5F, 3, 1, 5);
/*     */     
/* 162 */     this.GauntletstrapR1.setTextureSize(128, 64);
/* 163 */     this.GauntletstrapR1.mirror = true;
/* 164 */     setRotation(this.GauntletstrapR1, 0.0F, 0.0F, 0.0F);
/*     */     
/* 166 */     this.GauntletstrapR2 = new ModelRenderer(this, 84, 31);
/* 167 */     this.GauntletstrapR2.addBox(-1.5F, 6.5F, -2.5F, 3, 1, 5);
/*     */     
/* 169 */     this.GauntletstrapR2.setTextureSize(128, 64);
/* 170 */     this.GauntletstrapR2.mirror = true;
/* 171 */     setRotation(this.GauntletstrapR2, 0.0F, 0.0F, 0.0F);
/*     */     
/* 173 */     this.ShoulderR = new ModelRenderer(this, 56, 35);
/* 174 */     this.ShoulderR.addBox(-3.5F, -2.5F, -2.5F, 5, 5, 5);
/*     */     
/* 176 */     this.ShoulderR.setTextureSize(128, 64);
/* 177 */     this.ShoulderR.mirror = true;
/* 178 */     setRotation(this.ShoulderR, 0.0F, 0.0F, 0.0F);
/*     */     
/* 180 */     this.ShoulderR0 = new ModelRenderer(this, 0, 0);
/* 181 */     this.ShoulderR0.addBox(-4.3F, -1.5F, -3.0F, 3, 5, 6);
/*     */     
/* 183 */     this.ShoulderR0.setTextureSize(128, 64);
/* 184 */     this.ShoulderR0.mirror = true;
/* 185 */     setRotation(this.ShoulderR0, 0.0F, 0.0F, 0.7853982F);
/*     */     
/* 187 */     this.ShoulderR2 = new ModelRenderer(this, 0, 11);
/* 188 */     this.ShoulderR2.addBox(-2.3F, 3.5F, -3.0F, 1, 2, 6);
/*     */     
/* 190 */     this.ShoulderR2.setTextureSize(128, 64);
/* 191 */     this.ShoulderR2.mirror = true;
/* 192 */     setRotation(this.ShoulderR2, 0.0F, 0.0F, 0.7853982F);
/*     */     
/* 194 */     this.ShoulderL1 = new ModelRenderer(this, 0, 19);
/* 195 */     this.ShoulderL1.mirror = true;
/* 196 */     this.ShoulderL1.addBox(2.3F, 3.5F, -2.5F, 1, 1, 5);
/*     */     
/* 198 */     this.ShoulderL1.setTextureSize(128, 64);
/*     */     
/* 200 */     setRotation(this.ShoulderL1, 0.0F, 0.0F, -0.7853982F);
/*     */     
/* 202 */     this.GauntletL = new ModelRenderer(this, 114, 26);
/* 203 */     this.GauntletL.addBox(1.5F, 3.5F, -2.5F, 2, 6, 5);
/*     */     
/* 205 */     this.GauntletL.setTextureSize(128, 64);
/* 206 */     setRotation(this.GauntletL, 0.0F, 0.0F, 0.0F);
/*     */     
/* 208 */     this.GauntletstrapL1 = new ModelRenderer(this, 84, 31);
/* 209 */     this.GauntletstrapL1.mirror = true;
/* 210 */     this.GauntletstrapL1.addBox(-1.5F, 3.5F, -2.5F, 3, 1, 5);
/*     */     
/* 212 */     this.GauntletstrapL1.setTextureSize(128, 64);
/* 213 */     setRotation(this.GauntletstrapL1, 0.0F, 0.0F, 0.0F);
/*     */     
/* 215 */     this.GauntletstrapL2 = new ModelRenderer(this, 84, 31);
/* 216 */     this.GauntletstrapL2.mirror = true;
/* 217 */     this.GauntletstrapL2.addBox(-1.5F, 6.5F, -2.5F, 3, 1, 5);
/*     */     
/* 219 */     this.GauntletstrapL2.setTextureSize(128, 64);
/* 220 */     setRotation(this.GauntletstrapL2, 0.0F, 0.0F, 0.0F);
/*     */     
/* 222 */     this.ShoulderL = new ModelRenderer(this, 56, 35);
/* 223 */     this.ShoulderL.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5);
/*     */     
/* 225 */     this.ShoulderL.setTextureSize(128, 64);
/* 226 */     setRotation(this.ShoulderL, 0.0F, 0.0F, 0.0F);
/*     */     
/* 228 */     this.ShoulderL0 = new ModelRenderer(this, 0, 0);
/* 229 */     this.ShoulderL0.addBox(1.3F, -1.5F, -3.0F, 3, 5, 6);
/*     */     
/* 231 */     this.ShoulderL0.setTextureSize(128, 64);
/* 232 */     setRotation(this.ShoulderL0, 0.0F, 0.0F, -0.7853982F);
/*     */     
/* 234 */     this.ShoulderL2 = new ModelRenderer(this, 0, 11);
/* 235 */     this.ShoulderL2.addBox(1.3F, 3.5F, -3.0F, 1, 2, 6);
/*     */     
/* 237 */     this.ShoulderL2.setTextureSize(128, 64);
/* 238 */     setRotation(this.ShoulderL2, 0.0F, 0.0F, -0.7853982F);
/*     */     
/* 240 */     this.SidepanelR3 = new ModelRenderer(this, 116, 13);
/* 241 */     this.SidepanelR3.addBox(-3.0F, 2.5F, -2.5F, 1, 4, 5);
/*     */     
/* 243 */     this.SidepanelR3.setTextureSize(128, 64);
/* 244 */     setRotation(this.SidepanelR3, 0.0F, 0.0F, 0.1396263F);
/*     */     
/*     */ 
/* 247 */     this.SidepanelR2 = new ModelRenderer(this, 114, 5);
/* 248 */     this.SidepanelR2.mirror = true;
/* 249 */     this.SidepanelR2.addBox(-2.0F, 2.5F, -2.5F, 2, 3, 5);
/*     */     
/* 251 */     this.SidepanelR2.setTextureSize(128, 64);
/* 252 */     setRotation(this.SidepanelR2, 0.0F, 0.0F, 0.1396263F);
/*     */     
/* 254 */     this.SidepanelL2 = new ModelRenderer(this, 114, 5);
/* 255 */     this.SidepanelL2.addBox(0.0F, 2.5F, -2.5F, 2, 3, 5);
/*     */     
/* 257 */     this.SidepanelL2.setTextureSize(128, 64);
/* 258 */     setRotation(this.SidepanelL2, 0.0F, 0.0F, -0.1396263F);
/*     */     
/* 260 */     this.SidepanelR0 = new ModelRenderer(this, 96, 14);
/* 261 */     this.SidepanelR0.addBox(-3.0F, -0.5F, -2.5F, 5, 3, 5);
/*     */     
/* 263 */     this.SidepanelR0.setTextureSize(128, 64);
/* 264 */     setRotation(this.SidepanelR0, 0.0F, 0.0F, 0.1396263F);
/*     */     
/* 266 */     this.SidepanelL0 = new ModelRenderer(this, 96, 14);
/* 267 */     this.SidepanelL0.addBox(-2.0F, -0.5F, -2.5F, 5, 3, 5);
/*     */     
/* 269 */     this.SidepanelL0.setTextureSize(128, 64);
/* 270 */     setRotation(this.SidepanelL0, 0.0F, 0.0F, -0.1396263F);
/*     */     
/* 272 */     this.SidepanelR1 = new ModelRenderer(this, 96, 7);
/* 273 */     this.SidepanelR1.mirror = true;
/* 274 */     this.SidepanelR1.addBox(0.0F, 2.5F, -2.5F, 2, 2, 5);
/*     */     
/* 276 */     this.SidepanelR1.setTextureSize(128, 64);
/* 277 */     setRotation(this.SidepanelR1, 0.0F, 0.0F, 0.1396263F);
/*     */     
/* 279 */     this.SidepanelL3 = new ModelRenderer(this, 116, 13);
/* 280 */     this.SidepanelL3.addBox(2.0F, 2.5F, -2.5F, 1, 4, 5);
/*     */     
/* 282 */     this.SidepanelL3.setTextureSize(128, 64);
/* 283 */     setRotation(this.SidepanelL3, 0.0F, 0.0F, -0.1396263F);
/*     */     
/* 285 */     this.SidepanelL1 = new ModelRenderer(this, 96, 7);
/* 286 */     this.SidepanelL1.addBox(-2.0F, 2.5F, -2.5F, 2, 2, 5);
/*     */     
/* 288 */     this.SidepanelL1.setTextureSize(128, 64);
/* 289 */     setRotation(this.SidepanelL1, 0.0F, 0.0F, -0.1396263F);
/*     */     
/* 291 */     this.Frontcloth1 = new ModelRenderer(this, 120, 39);
/* 292 */     this.Frontcloth1.addBox(0.0F, 0.0F, 0.0F, 6, 8, 1);
/* 293 */     this.Frontcloth1.setRotationPoint(-3.0F, 11.0F, -3.5F);
/* 294 */     this.Frontcloth1.setTextureSize(128, 64);
/* 295 */     setRotation(this.Frontcloth1, -0.1047198F, 0.0F, 0.0F);
/*     */     
/* 297 */     this.Frontcloth2 = new ModelRenderer(this, 100, 37);
/* 298 */     this.Frontcloth2.addBox(0.0F, 7.5F, 1.8F, 6, 3, 1);
/* 299 */     this.Frontcloth2.setRotationPoint(-3.0F, 11.0F, -3.5F);
/* 300 */     this.Frontcloth2.setTextureSize(128, 64);
/* 301 */     setRotation(this.Frontcloth2, -0.3316126F, 0.0F, 0.0F);
/*     */     
/* 303 */     this.bipedHeadwear.cubeList.clear();
/* 304 */     this.bipedHead.cubeList.clear();
/* 305 */     this.bipedHead.addChild(this.Helmet);
/*     */     
/* 307 */     this.bipedBody.cubeList.clear();
/* 308 */     this.bipedRightLeg.cubeList.clear();
/* 309 */     this.bipedLeftLeg.cubeList.clear();
/*     */     
/* 311 */     this.bipedBody.addChild(this.Mbelt);
/* 312 */     this.bipedBody.addChild(this.MbeltL);
/* 313 */     this.bipedBody.addChild(this.MbeltR);
/*     */     
/* 315 */     if (f >= 1.0F)
/*     */     {
/*     */ 
/* 318 */       this.bipedBody.addChild(this.Chestplate);
/* 319 */       this.bipedBody.addChild(this.Frontcloth1);
/* 320 */       this.bipedBody.addChild(this.Frontcloth2);
/* 321 */       this.bipedBody.addChild(this.Tabbard);
/* 322 */       this.bipedBody.addChild(this.Backplate);
/* 323 */       this.bipedBody.addChild(this.Cloak1);
/* 324 */       this.bipedBody.addChild(this.Cloak2);
/* 325 */       this.bipedBody.addChild(this.Cloak3);
/* 326 */       this.bipedBody.addChild(this.CloakAtL);
/* 327 */       this.bipedBody.addChild(this.CloakAtR);
/*     */     }
/*     */     
/* 330 */     this.bipedRightArm.cubeList.clear();
/* 331 */     this.bipedRightArm.addChild(this.ShoulderR);
/* 332 */     this.bipedRightArm.addChild(this.ShoulderR0);
/* 333 */     this.bipedRightArm.addChild(this.ShoulderR1);
/* 334 */     this.bipedRightArm.addChild(this.ShoulderR2);
/* 335 */     this.bipedRightArm.addChild(this.GauntletR);
/* 336 */     this.bipedRightArm.addChild(this.GauntletstrapR1);
/* 337 */     this.bipedRightArm.addChild(this.GauntletstrapR2);
/*     */     
/* 339 */     this.bipedLeftArm.cubeList.clear();
/* 340 */     this.bipedLeftArm.addChild(this.ShoulderL);
/* 341 */     this.bipedLeftArm.addChild(this.ShoulderL0);
/* 342 */     this.bipedLeftArm.addChild(this.ShoulderL1);
/* 343 */     this.bipedLeftArm.addChild(this.ShoulderL2);
/* 344 */     this.bipedLeftArm.addChild(this.GauntletL);
/* 345 */     this.bipedLeftArm.addChild(this.GauntletstrapL1);
/* 346 */     this.bipedLeftArm.addChild(this.GauntletstrapL2);
/*     */     
/* 348 */     this.bipedRightLeg.addChild(this.SidepanelR0);
/* 349 */     this.bipedRightLeg.addChild(this.SidepanelR1);
/* 350 */     this.bipedRightLeg.addChild(this.SidepanelR2);
/* 351 */     this.bipedRightLeg.addChild(this.SidepanelR3);
/*     */     
/* 353 */     this.bipedLeftLeg.addChild(this.SidepanelL0);
/* 354 */     this.bipedLeftLeg.addChild(this.SidepanelL1);
/* 355 */     this.bipedLeftLeg.addChild(this.SidepanelL2);
/* 356 */     this.bipedLeftLeg.addChild(this.SidepanelL3);
/*     */   }
/*     */   
/*     */ 
/*     */   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 362 */     setRotationAngles(f, f1, f2, f3, f4, f5, entity);
/*     */     
/* 364 */     float a = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
/* 365 */     float b = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
/* 366 */     float c = Math.min(a, b);
/*     */     
/* 368 */     this.Frontcloth1.rotateAngleX = (c - 0.1047198F);
/* 369 */     this.Frontcloth2.rotateAngleX = (c - 0.3316126F);
/*     */     
/* 371 */     this.Cloak1.rotateAngleX = (-c / 2.0F + 0.1396263F);
/* 372 */     this.Cloak2.rotateAngleX = (-c / 2.0F + 0.3069452F);
/* 373 */     this.Cloak3.rotateAngleX = (-c / 2.0F + 0.4465716F);
/*     */     
/* 375 */     if (this.isChild) {
/* 376 */       float f6 = 2.0F;
/* 377 */       GL11.glPushMatrix();
/* 378 */       GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
/* 379 */       GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
/* 380 */       this.bipedHead.render(f5);
/* 381 */       GL11.glPopMatrix();
/* 382 */       GL11.glPushMatrix();
/* 383 */       GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
/* 384 */       GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
/* 385 */       this.bipedBody.render(f5);
/* 386 */       this.bipedRightArm.render(f5);
/* 387 */       this.bipedLeftArm.render(f5);
/* 388 */       this.bipedRightLeg.render(f5);
/* 389 */       this.bipedLeftLeg.render(f5);
/* 390 */       this.bipedHeadwear.render(f5);
/* 391 */       GL11.glPopMatrix();
/*     */     } else {
/* 393 */       GL11.glPushMatrix();
/* 394 */       GL11.glScalef(1.01F, 1.01F, 1.01F);
/* 395 */       this.bipedHead.render(f5);
/* 396 */       GL11.glPopMatrix();
/* 397 */       this.bipedBody.render(f5);
/* 398 */       this.bipedRightArm.render(f5);
/* 399 */       this.bipedLeftArm.render(f5);
/* 400 */       this.bipedRightLeg.render(f5);
/* 401 */       this.bipedLeftLeg.render(f5);
/* 402 */       this.bipedHeadwear.render(f5);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setRotation(ModelRenderer model, float x, float y, float z) {
/* 407 */     model.rotateAngleX = x;
/* 408 */     model.rotateAngleY = y;
/* 409 */     model.rotateAngleZ = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\gear\ModelKnightArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */