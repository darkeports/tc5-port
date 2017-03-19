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
/*     */ public class ModelLeaderArmor
/*     */   extends ModelCustomArmor
/*     */ {
/*     */   ModelRenderer Helmet;
/*     */   ModelRenderer CollarF;
/*     */   ModelRenderer CollarB;
/*     */   ModelRenderer CollarR;
/*     */   ModelRenderer CollarL;
/*     */   ModelRenderer BeltR;
/*     */   ModelRenderer Mbelt;
/*     */   ModelRenderer MbeltL;
/*     */   ModelRenderer MbeltR;
/*     */   ModelRenderer BeltL;
/*     */   ModelRenderer CloakTL;
/*     */   ModelRenderer Cloak3;
/*     */   ModelRenderer CloakTR;
/*     */   ModelRenderer Cloak1;
/*     */   ModelRenderer Cloak2;
/*     */   ModelRenderer Chestplate;
/*     */   ModelRenderer ChestOrnament;
/*     */   ModelRenderer ChestClothL;
/*     */   ModelRenderer ChestClothR;
/*     */   ModelRenderer Backplate;
/*     */   ModelRenderer GauntletstrapR1;
/*     */   ModelRenderer GauntletstrapR2;
/*     */   ModelRenderer ShoulderR;
/*     */   ModelRenderer ShoulderR1;
/*     */   ModelRenderer ShoulderR2;
/*     */   ModelRenderer ShoulderR5;
/*     */   ModelRenderer ShoulderR3;
/*     */   ModelRenderer ShoulderR4;
/*     */   ModelRenderer GauntletR2;
/*     */   ModelRenderer GauntletR;
/*     */   ModelRenderer GauntletL2;
/*     */   ModelRenderer GauntletstrapL1;
/*     */   ModelRenderer GauntletstrapL2;
/*     */   ModelRenderer ShoulderL;
/*     */   ModelRenderer ShoulderL1;
/*     */   ModelRenderer ShoulderL2;
/*     */   ModelRenderer ShoulderL3;
/*     */   ModelRenderer ShoulderL5;
/*     */   ModelRenderer ShoulderL4;
/*     */   ModelRenderer GauntletL;
/*     */   ModelRenderer LegClothR;
/*     */   ModelRenderer BackpanelR2;
/*     */   ModelRenderer BackpanelR3;
/*     */   ModelRenderer BackpanelR4;
/*     */   ModelRenderer LegClothL;
/*     */   ModelRenderer BackpanelL4;
/*     */   ModelRenderer BackpanelL2;
/*     */   ModelRenderer BackpanelL3;
/*     */   ModelRenderer BackpanelL1;
/*     */   ModelRenderer BackpanelR1;
/*     */   
/*     */   public ModelLeaderArmor(float f)
/*     */   {
/*  72 */     super(f, 0, 128, 64);
/*  73 */     this.textureWidth = 128;
/*  74 */     this.textureHeight = 64;
/*     */     
/*  76 */     this.Helmet = new ModelRenderer(this, 41, 8);
/*  77 */     this.Helmet.addBox(-4.5F, -9.0F, -4.5F, 9, 9, 9);
/*  78 */     this.Helmet.setTextureSize(128, 64);
/*  79 */     setRotation(this.Helmet, 0.0F, 0.0F, 0.0F);
/*     */     
/*  81 */     this.CollarF = new ModelRenderer(this, 17, 31);
/*  82 */     this.CollarF.addBox(-4.5F, -1.5F, -3.0F, 9, 4, 1);
/*  83 */     this.CollarF.setRotationPoint(0.0F, 0.0F, -2.5F);
/*  84 */     setRotation(this.CollarF, 0.2268928F, 0.0F, 0.0F);
/*  85 */     this.CollarB = new ModelRenderer(this, 17, 26);
/*  86 */     this.CollarB.addBox(-4.5F, -1.5F, 7.0F, 9, 4, 1);
/*  87 */     this.CollarB.setRotationPoint(0.0F, 0.0F, -2.5F);
/*  88 */     setRotation(this.CollarB, 0.2268928F, 0.0F, 0.0F);
/*  89 */     this.CollarR = new ModelRenderer(this, 17, 11);
/*  90 */     this.CollarR.addBox(-5.5F, -1.5F, -3.0F, 1, 4, 11);
/*  91 */     this.CollarR.setRotationPoint(0.0F, 0.0F, -2.5F);
/*  92 */     setRotation(this.CollarR, 0.2268928F, 0.0F, 0.0F);
/*  93 */     this.CollarL = new ModelRenderer(this, 17, 11);
/*  94 */     this.CollarL.addBox(4.5F, -1.5F, -3.0F, 1, 4, 11);
/*  95 */     this.CollarL.setRotationPoint(0.0F, 0.0F, -2.5F);
/*  96 */     setRotation(this.CollarL, 0.2268928F, 0.0F, 0.0F);
/*     */     
/*  98 */     this.BeltR = new ModelRenderer(this, 76, 44);
/*  99 */     this.BeltR.addBox(-5.0F, 4.0F, -3.0F, 1, 3, 6);
/* 100 */     this.Mbelt = new ModelRenderer(this, 56, 55);
/* 101 */     this.Mbelt.addBox(-4.0F, 8.0F, -3.0F, 8, 4, 1);
/* 102 */     this.MbeltL = new ModelRenderer(this, 76, 44);
/* 103 */     this.MbeltL.addBox(4.0F, 8.0F, -3.0F, 1, 3, 6);
/* 104 */     this.MbeltR = new ModelRenderer(this, 76, 44);
/* 105 */     this.MbeltR.addBox(-5.0F, 8.0F, -3.0F, 1, 3, 6);
/* 106 */     this.BeltL = new ModelRenderer(this, 76, 44);
/* 107 */     this.BeltL.addBox(4.0F, 4.0F, -3.0F, 1, 3, 6);
/*     */     
/* 109 */     this.CloakTL = new ModelRenderer(this, 0, 43);
/* 110 */     this.CloakTL.addBox(2.5F, 1.0F, -1.0F, 2, 1, 3);
/* 111 */     this.CloakTL.setRotationPoint(0.0F, 0.0F, 3.0F);
/* 112 */     setRotation(this.CloakTL, 0.1396263F, 0.0F, 0.0F);
/* 113 */     this.Cloak3 = new ModelRenderer(this, 0, 59);
/* 114 */     this.Cloak3.addBox(-4.5F, 17.0F, -3.7F, 9, 4, 1);
/* 115 */     this.Cloak3.setRotationPoint(0.0F, 0.0F, 3.0F);
/* 116 */     setRotation(this.Cloak3, 0.4465716F, 0.0F, 0.0F);
/* 117 */     this.CloakTR = new ModelRenderer(this, 0, 43);
/* 118 */     this.CloakTR.addBox(-4.5F, 1.0F, -1.0F, 2, 1, 3);
/* 119 */     this.CloakTR.setRotationPoint(0.0F, 0.0F, 3.0F);
/* 120 */     setRotation(this.CloakTR, 0.1396263F, 0.0F, 0.0F);
/* 121 */     this.Cloak1 = new ModelRenderer(this, 0, 47);
/* 122 */     this.Cloak1.addBox(-4.5F, 2.0F, 1.0F, 9, 12, 1);
/* 123 */     this.Cloak1.setRotationPoint(0.0F, 0.0F, 3.0F);
/* 124 */     setRotation(this.Cloak1, 0.1396263F, 0.0F, 0.0F);
/* 125 */     this.Cloak2 = new ModelRenderer(this, 0, 59);
/* 126 */     this.Cloak2.addBox(-4.5F, 14.0F, -1.3F, 9, 4, 1);
/* 127 */     this.Cloak2.setRotationPoint(0.0F, 0.0F, 3.0F);
/* 128 */     setRotation(this.Cloak2, 0.3069452F, 0.0F, 0.0F);
/*     */     
/* 130 */     this.Chestplate = new ModelRenderer(this, 56, 45);
/* 131 */     this.Chestplate.addBox(-4.0F, 1.0F, -3.8F, 8, 7, 2);
/* 132 */     this.ChestOrnament = new ModelRenderer(this, 76, 53);
/* 133 */     this.ChestOrnament.addBox(-2.5F, 3.0F, -4.8F, 5, 5, 1);
/* 134 */     this.ChestClothL = new ModelRenderer(this, 20, 47);
/* 135 */     this.ChestClothL.mirror = true;
/* 136 */     this.ChestClothL.addBox(1.5F, 1.2F, -4.5F, 3, 9, 1);
/* 137 */     setRotation(this.ChestClothL, 0.0663225F, 0.0F, 0.0F);
/* 138 */     this.ChestClothR = new ModelRenderer(this, 20, 47);
/* 139 */     this.ChestClothR.addBox(-4.5F, 1.2F, -4.5F, 3, 9, 1);
/* 140 */     setRotation(this.ChestClothR, 0.0663225F, 0.0F, 0.0F);
/* 141 */     this.Backplate = new ModelRenderer(this, 36, 45);
/* 142 */     this.Backplate.addBox(-4.0F, 1.0F, 2.0F, 8, 11, 2);
/*     */     
/* 144 */     this.GauntletR = new ModelRenderer(this, 100, 26);
/* 145 */     this.GauntletR.addBox(-3.5F, 3.5F, -2.5F, 2, 6, 5);
/* 146 */     this.GauntletL = new ModelRenderer(this, 114, 26);
/* 147 */     this.GauntletL.addBox(1.5F, 3.5F, -2.5F, 2, 6, 5);
/* 148 */     this.GauntletstrapL1 = new ModelRenderer(this, 84, 31);
/* 149 */     this.GauntletstrapL1.mirror = true;
/* 150 */     this.GauntletstrapL1.addBox(-1.5F, 3.5F, -2.5F, 3, 1, 5);
/* 151 */     this.GauntletstrapL2 = new ModelRenderer(this, 84, 31);
/* 152 */     this.GauntletstrapL2.mirror = true;
/* 153 */     this.GauntletstrapL2.addBox(-1.5F, 6.5F, -2.5F, 3, 1, 5);
/* 154 */     this.GauntletstrapR1 = new ModelRenderer(this, 84, 31);
/* 155 */     this.GauntletstrapR1.addBox(-1.5F, 3.5F, -2.5F, 3, 1, 5);
/* 156 */     this.GauntletstrapR2 = new ModelRenderer(this, 84, 31);
/* 157 */     this.GauntletstrapR2.addBox(-1.5F, 6.5F, -2.5F, 3, 1, 5);
/* 158 */     this.GauntletR2 = new ModelRenderer(this, 102, 37);
/* 159 */     this.GauntletR2.addBox(-5.0F, 3.5F, -2.0F, 1, 5, 4);
/* 160 */     setRotation(this.GauntletR2, 0.0F, 0.0F, -0.1675516F);
/* 161 */     this.GauntletL2 = new ModelRenderer(this, 102, 37);
/* 162 */     this.GauntletL2.addBox(4.0F, 3.5F, -2.0F, 1, 5, 4);
/* 163 */     setRotation(this.GauntletL2, 0.0F, 0.0F, 0.1675516F);
/*     */     
/* 165 */     this.ShoulderR = new ModelRenderer(this, 56, 35);
/* 166 */     this.ShoulderR.addBox(-3.5F, -2.5F, -2.5F, 5, 5, 5);
/* 167 */     this.ShoulderR1 = new ModelRenderer(this, 0, 0);
/* 168 */     this.ShoulderR1.addBox(-4.3F, -1.5F, -3.0F, 3, 5, 6);
/* 169 */     setRotation(this.ShoulderR1, 0.0F, 0.0F, 0.7853982F);
/* 170 */     this.ShoulderR2 = new ModelRenderer(this, 0, 19);
/* 171 */     this.ShoulderR2.addBox(-3.3F, 3.5F, -2.5F, 1, 1, 5);
/* 172 */     setRotation(this.ShoulderR2, 0.0F, 0.0F, 0.7853982F);
/* 173 */     this.ShoulderR5 = new ModelRenderer(this, 18, 4);
/* 174 */     this.ShoulderR5.addBox(-2.3F, -1.5F, 3.0F, 1, 6, 1);
/* 175 */     setRotation(this.ShoulderR5, 0.0F, 0.0F, 0.7853982F);
/* 176 */     this.ShoulderR3 = new ModelRenderer(this, 0, 11);
/* 177 */     this.ShoulderR3.addBox(-2.3F, 3.5F, -3.0F, 1, 2, 6);
/* 178 */     setRotation(this.ShoulderR3, 0.0F, 0.0F, 0.7853982F);
/* 179 */     this.ShoulderR4 = new ModelRenderer(this, 18, 4);
/* 180 */     this.ShoulderR4.addBox(-2.3F, -1.5F, -4.0F, 1, 6, 1);
/* 181 */     setRotation(this.ShoulderR4, 0.0F, 0.0F, 0.7853982F);
/*     */     
/* 183 */     this.ShoulderL = new ModelRenderer(this, 56, 35);
/* 184 */     this.ShoulderL.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5);
/* 185 */     this.ShoulderL1 = new ModelRenderer(this, 0, 0);
/* 186 */     this.ShoulderL1.addBox(1.3F, -1.5F, -3.0F, 3, 5, 6);
/* 187 */     setRotation(this.ShoulderL1, 0.0F, 0.0F, -0.7853982F);
/* 188 */     this.ShoulderL2 = new ModelRenderer(this, 0, 19);
/* 189 */     this.ShoulderL2.mirror = true;
/* 190 */     this.ShoulderL2.addBox(2.3F, 3.5F, -2.5F, 1, 1, 5);
/* 191 */     setRotation(this.ShoulderL2, 0.0F, 0.0F, -0.7853982F);
/* 192 */     this.ShoulderL3 = new ModelRenderer(this, 0, 11);
/* 193 */     this.ShoulderL3.addBox(1.3F, 3.5F, -3.0F, 1, 2, 6);
/* 194 */     setRotation(this.ShoulderL3, 0.0F, 0.0F, -0.7853982F);
/* 195 */     this.ShoulderL5 = new ModelRenderer(this, 18, 4);
/* 196 */     this.ShoulderL5.addBox(1.3F, -1.5F, 3.0F, 1, 6, 1);
/* 197 */     this.ShoulderL5.setTextureSize(128, 64);
/* 198 */     setRotation(this.ShoulderL5, 0.0F, 0.0F, -0.7853982F);
/* 199 */     this.ShoulderL4 = new ModelRenderer(this, 18, 4);
/* 200 */     this.ShoulderL4.addBox(1.3F, -1.5F, -4.0F, 1, 6, 1);
/* 201 */     setRotation(this.ShoulderL4, 0.0F, 0.0F, -0.7853982F);
/*     */     
/* 203 */     this.LegClothR = new ModelRenderer(this, 20, 55);
/* 204 */     this.LegClothR.addBox(0.0F, 0.0F, 0.0F, 3, 8, 1);
/* 205 */     this.LegClothR.setRotationPoint(-4.5F, 10.4F, -3.9F);
/* 206 */     setRotation(this.LegClothR, -0.0349066F, 0.0F, 0.0F);
/* 207 */     this.LegClothL = new ModelRenderer(this, 20, 55);
/* 208 */     this.LegClothL.mirror = true;
/* 209 */     this.LegClothL.addBox(0.0F, 0.0F, 0.0F, 3, 8, 1);
/* 210 */     this.LegClothL.setRotationPoint(1.5F, 10.4F, -3.9F);
/* 211 */     setRotation(this.LegClothL, -0.0349066F, 0.0F, 0.0F);
/*     */     
/* 213 */     this.BackpanelR1 = new ModelRenderer(this, 0, 25);
/* 214 */     this.BackpanelR1.addBox(-3.0F, -0.5F, 2.5F, 5, 7, 1);
/* 215 */     setRotation(this.BackpanelR1, 0.0698132F, 0.0F, 0.0F);
/* 216 */     this.BackpanelR2 = new ModelRenderer(this, 96, 14);
/* 217 */     this.BackpanelR2.addBox(-3.0F, -0.5F, -2.5F, 5, 3, 5);
/* 218 */     setRotation(this.BackpanelR2, 0.0F, 0.0F, 0.1396263F);
/* 219 */     this.BackpanelR3 = new ModelRenderer(this, 116, 13);
/* 220 */     this.BackpanelR3.addBox(-3.0F, 2.5F, -2.5F, 1, 4, 5);
/* 221 */     setRotation(this.BackpanelR3, 0.0F, 0.0F, 0.1396263F);
/* 222 */     this.BackpanelR4 = new ModelRenderer(this, 0, 25);
/* 223 */     this.BackpanelR4.mirror = true;
/* 224 */     this.BackpanelR4.addBox(-3.0F, -0.5F, -3.5F, 5, 7, 1);
/* 225 */     setRotation(this.BackpanelR4, -0.0349066F, 0.0F, 0.0F);
/*     */     
/* 227 */     this.BackpanelL1 = new ModelRenderer(this, 0, 25);
/* 228 */     this.BackpanelL1.addBox(-2.0F, -0.5F, 2.5F, 5, 7, 1);
/* 229 */     setRotation(this.BackpanelL1, 0.0698132F, 0.0F, 0.0F);
/* 230 */     this.BackpanelL4 = new ModelRenderer(this, 0, 25);
/* 231 */     this.BackpanelL4.addBox(-2.0F, -0.5F, -3.5F, 5, 7, 1);
/* 232 */     setRotation(this.BackpanelL4, -0.0349066F, 0.0F, 0.0F);
/* 233 */     this.BackpanelL2 = new ModelRenderer(this, 96, 14);
/* 234 */     this.BackpanelL2.addBox(-2.0F, -0.5F, -2.5F, 5, 3, 5);
/* 235 */     setRotation(this.BackpanelL2, 0.0F, 0.0F, -0.1396263F);
/* 236 */     this.BackpanelL3 = new ModelRenderer(this, 116, 13);
/* 237 */     this.BackpanelL3.addBox(2.0F, 2.5F, -2.5F, 1, 4, 5);
/* 238 */     setRotation(this.BackpanelL3, 0.0F, 0.0F, -0.1396263F);
/*     */     
/* 240 */     this.bipedHeadwear.cubeList.clear();
/* 241 */     this.bipedHead.cubeList.clear();
/* 242 */     this.bipedHead.addChild(this.Helmet);
/*     */     
/* 244 */     this.bipedBody.cubeList.clear();
/* 245 */     this.bipedRightLeg.cubeList.clear();
/* 246 */     this.bipedLeftLeg.cubeList.clear();
/*     */     
/* 248 */     this.bipedBody.addChild(this.Mbelt);
/* 249 */     this.bipedBody.addChild(this.MbeltL);
/* 250 */     this.bipedBody.addChild(this.MbeltR);
/*     */     
/* 252 */     if (f >= 1.0F)
/*     */     {
/*     */ 
/* 255 */       this.bipedBody.addChild(this.BeltL);
/* 256 */       this.bipedBody.addChild(this.BeltR);
/* 257 */       this.bipedBody.addChild(this.Chestplate);
/* 258 */       this.bipedBody.addChild(this.ChestOrnament);
/* 259 */       this.bipedBody.addChild(this.ChestClothR);
/* 260 */       this.bipedBody.addChild(this.ChestClothL);
/* 261 */       this.bipedBody.addChild(this.LegClothR);
/* 262 */       this.bipedBody.addChild(this.LegClothL);
/* 263 */       this.bipedBody.addChild(this.Backplate);
/* 264 */       this.bipedBody.addChild(this.CollarB);
/* 265 */       this.bipedBody.addChild(this.CollarR);
/* 266 */       this.bipedBody.addChild(this.CollarL);
/* 267 */       this.bipedBody.addChild(this.CollarF);
/* 268 */       this.bipedBody.addChild(this.Cloak1);
/* 269 */       this.bipedBody.addChild(this.Cloak2);
/* 270 */       this.bipedBody.addChild(this.Cloak3);
/* 271 */       this.bipedBody.addChild(this.CloakTL);
/* 272 */       this.bipedBody.addChild(this.CloakTR);
/*     */     }
/*     */     
/* 275 */     this.bipedRightArm.cubeList.clear();
/* 276 */     this.bipedRightArm.addChild(this.ShoulderR);
/* 277 */     this.bipedRightArm.addChild(this.ShoulderR1);
/* 278 */     this.bipedRightArm.addChild(this.ShoulderR2);
/* 279 */     this.bipedRightArm.addChild(this.ShoulderR3);
/* 280 */     this.bipedRightArm.addChild(this.ShoulderR4);
/* 281 */     this.bipedRightArm.addChild(this.ShoulderR5);
/* 282 */     this.bipedRightArm.addChild(this.GauntletR);
/* 283 */     this.bipedRightArm.addChild(this.GauntletR2);
/* 284 */     this.bipedRightArm.addChild(this.GauntletstrapR1);
/* 285 */     this.bipedRightArm.addChild(this.GauntletstrapR2);
/*     */     
/* 287 */     this.bipedLeftArm.cubeList.clear();
/* 288 */     this.bipedLeftArm.addChild(this.ShoulderL);
/* 289 */     this.bipedLeftArm.addChild(this.ShoulderL1);
/* 290 */     this.bipedLeftArm.addChild(this.ShoulderL2);
/* 291 */     this.bipedLeftArm.addChild(this.ShoulderL3);
/* 292 */     this.bipedLeftArm.addChild(this.ShoulderL4);
/* 293 */     this.bipedLeftArm.addChild(this.ShoulderL5);
/* 294 */     this.bipedLeftArm.addChild(this.GauntletL);
/* 295 */     this.bipedLeftArm.addChild(this.GauntletL2);
/* 296 */     this.bipedLeftArm.addChild(this.GauntletstrapL1);
/* 297 */     this.bipedLeftArm.addChild(this.GauntletstrapL2);
/*     */     
/* 299 */     this.bipedRightLeg.addChild(this.BackpanelR1);
/* 300 */     this.bipedRightLeg.addChild(this.BackpanelR2);
/* 301 */     this.bipedRightLeg.addChild(this.BackpanelR3);
/* 302 */     this.bipedRightLeg.addChild(this.BackpanelR4);
/*     */     
/* 304 */     this.bipedLeftLeg.addChild(this.BackpanelL1);
/* 305 */     this.bipedLeftLeg.addChild(this.BackpanelL2);
/* 306 */     this.bipedLeftLeg.addChild(this.BackpanelL3);
/* 307 */     this.bipedLeftLeg.addChild(this.BackpanelL4);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 314 */     setRotationAngles(f, f1, f2, f3, f4, f5, entity);
/*     */     
/* 316 */     float a = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
/* 317 */     float b = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
/* 318 */     float c = Math.min(a, b);
/*     */     
/* 320 */     this.LegClothR.rotateAngleX = (a - 0.1047198F);
/* 321 */     this.LegClothL.rotateAngleX = (b - 0.1047198F);
/*     */     
/* 323 */     this.Cloak1.rotateAngleX = (-c / 2.0F + 0.1396263F);
/* 324 */     this.Cloak2.rotateAngleX = (-c / 2.0F + 0.3069452F);
/* 325 */     this.Cloak3.rotateAngleX = (-c / 2.0F + 0.4465716F);
/*     */     
/* 327 */     if (this.isChild) {
/* 328 */       float f6 = 2.0F;
/* 329 */       GL11.glPushMatrix();
/* 330 */       GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
/* 331 */       GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
/* 332 */       this.bipedHead.render(f5);
/* 333 */       GL11.glPopMatrix();
/* 334 */       GL11.glPushMatrix();
/* 335 */       GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
/* 336 */       GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
/* 337 */       this.bipedBody.render(f5);
/* 338 */       this.bipedRightArm.render(f5);
/* 339 */       this.bipedLeftArm.render(f5);
/* 340 */       this.bipedRightLeg.render(f5);
/* 341 */       this.bipedLeftLeg.render(f5);
/* 342 */       this.bipedHeadwear.render(f5);
/* 343 */       GL11.glPopMatrix();
/*     */     } else {
/* 345 */       GL11.glPushMatrix();
/* 346 */       GL11.glScalef(1.01F, 1.01F, 1.01F);
/* 347 */       this.bipedHead.render(f5);
/* 348 */       GL11.glPopMatrix();
/* 349 */       this.bipedBody.render(f5);
/* 350 */       this.bipedRightArm.render(f5);
/* 351 */       this.bipedLeftArm.render(f5);
/* 352 */       this.bipedRightLeg.render(f5);
/* 353 */       this.bipedLeftLeg.render(f5);
/* 354 */       this.bipedHeadwear.render(f5);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setRotation(ModelRenderer model, float x, float y, float z) {
/* 359 */     model.rotateAngleX = x;
/* 360 */     model.rotateAngleY = y;
/* 361 */     model.rotateAngleZ = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\gear\ModelLeaderArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */