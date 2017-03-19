/*     */ package thaumcraft.client.renderers.models.entity;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.common.entities.monster.EntityEldritchGuardian;
/*     */ import thaumcraft.common.entities.monster.boss.EntityEldritchWarden;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelEldritchGuardian
/*     */   extends ModelBase
/*     */ {
/*     */   ModelRenderer BeltR;
/*     */   ModelRenderer Mbelt;
/*     */   ModelRenderer MbeltL;
/*     */   ModelRenderer MbeltR;
/*     */   ModelRenderer BeltL;
/*     */   ModelRenderer Hood4;
/*     */   ModelRenderer Cloak3;
/*     */   ModelRenderer Chestplate;
/*     */   ModelRenderer HoodEye;
/*     */   ModelRenderer Hood1;
/*     */   ModelRenderer Hood2;
/*     */   ModelRenderer Hood3;
/*     */   ModelRenderer Backplate;
/*     */   ModelRenderer Cloak1;
/*     */   ModelRenderer Cloak2;
/*     */   ModelRenderer ShoulderplateTopR;
/*     */   ModelRenderer ShoulderplateR1;
/*     */   ModelRenderer ShoulderplateR2;
/*     */   ModelRenderer ShoulderplateR3;
/*     */   ModelRenderer ShoulderR;
/*     */   ModelRenderer ArmR3;
/*     */   ModelRenderer ArmL1;
/*     */   ModelRenderer ArmL3;
/*     */   ModelRenderer ArmR1;
/*     */   ModelRenderer ArmR2;
/*     */   ModelRenderer ArmL2;
/*     */   ModelRenderer ShoulderL;
/*     */   ModelRenderer ShoulderplateLtop;
/*     */   ModelRenderer ShoulderplateL1;
/*     */   ModelRenderer ShoulderplateL2;
/*     */   ModelRenderer ShoulderplateL3;
/*     */   ModelRenderer LegpanelR4;
/*     */   ModelRenderer LegpanelR5;
/*     */   ModelRenderer LegpanelR6;
/*     */   ModelRenderer SidepanelR1;
/*     */   ModelRenderer BackpanelR1;
/*     */   ModelRenderer BackpanelR2;
/*     */   ModelRenderer BackpanelR3;
/*     */   ModelRenderer BackpanelL3;
/*     */   ModelRenderer LegpanelL4;
/*     */   ModelRenderer LegpanelL5;
/*     */   ModelRenderer LegpanelL6;
/*     */   ModelRenderer SidepanelL1;
/*     */   ModelRenderer SidepanelR4;
/*     */   ModelRenderer BackpanelL1;
/*     */   ModelRenderer BackpanelL2;
/*     */   ModelRenderer LegpanelC1;
/*     */   ModelRenderer LegpanelC2;
/*     */   ModelRenderer LegpanelC3;
/*     */   ModelRenderer SidepanelR3;
/*     */   ModelRenderer SidepanelL4;
/*     */   ModelRenderer SidepanelL3;
/*     */   ModelRenderer SidepanelR2;
/*     */   ModelRenderer SidepanelL2;
/*     */   private float partialTicks;
/*     */   
/*     */   public ModelEldritchGuardian()
/*     */   {
/*  80 */     this.textureWidth = 128;
/*  81 */     this.textureHeight = 64;
/*     */     
/*  83 */     this.BeltR = new ModelRenderer(this, 76, 44);
/*  84 */     this.BeltR.addBox(-5.0F, 4.0F, -3.0F, 1, 3, 6);
/*  85 */     this.BeltR.setRotationPoint(0.0F, -6.0F, 0.0F);
/*  86 */     this.BeltR.setTextureSize(128, 64);
/*  87 */     this.BeltR.mirror = true;
/*  88 */     setRotation(this.BeltR, 0.0F, 0.0F, 0.0F);
/*  89 */     this.Mbelt = new ModelRenderer(this, 56, 55);
/*  90 */     this.Mbelt.addBox(-4.0F, 8.0F, -3.0F, 8, 4, 1);
/*  91 */     this.Mbelt.setRotationPoint(0.0F, -6.0F, 0.0F);
/*  92 */     this.Mbelt.setTextureSize(128, 64);
/*  93 */     this.Mbelt.mirror = true;
/*  94 */     setRotation(this.Mbelt, 0.0F, 0.0F, 0.0F);
/*  95 */     this.MbeltL = new ModelRenderer(this, 76, 44);
/*  96 */     this.MbeltL.addBox(4.0F, 8.0F, -3.0F, 1, 3, 6);
/*  97 */     this.MbeltL.setRotationPoint(0.0F, -6.0F, 0.0F);
/*  98 */     this.MbeltL.setTextureSize(128, 64);
/*  99 */     this.MbeltL.mirror = true;
/* 100 */     setRotation(this.MbeltL, 0.0F, 0.0F, 0.0F);
/* 101 */     this.MbeltR = new ModelRenderer(this, 76, 44);
/* 102 */     this.MbeltR.addBox(-5.0F, 8.0F, -3.0F, 1, 3, 6);
/* 103 */     this.MbeltR.setRotationPoint(0.0F, -6.0F, 0.0F);
/* 104 */     this.MbeltR.setTextureSize(128, 64);
/* 105 */     this.MbeltR.mirror = true;
/* 106 */     setRotation(this.MbeltR, 0.0F, 0.0F, 0.0F);
/* 107 */     this.BeltL = new ModelRenderer(this, 76, 44);
/* 108 */     this.BeltL.addBox(4.0F, 4.0F, -3.0F, 1, 3, 6);
/* 109 */     this.BeltL.setRotationPoint(0.0F, -6.0F, 0.0F);
/* 110 */     this.BeltL.setTextureSize(128, 64);
/* 111 */     this.BeltL.mirror = true;
/* 112 */     setRotation(this.BeltL, 0.0F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 115 */     this.Chestplate = new ModelRenderer(this, 56, 45);
/* 116 */     this.Chestplate.addBox(-4.0F, 1.0F, -4.0F, 8, 7, 2);
/* 117 */     this.Chestplate.setRotationPoint(0.0F, -6.0F, 0.0F);
/* 118 */     this.Chestplate.setTextureSize(128, 64);
/* 119 */     this.Chestplate.mirror = true;
/* 120 */     setRotation(this.Chestplate, 0.0F, 0.0F, 0.0F);
/*     */     
/* 122 */     this.HoodEye = new ModelRenderer(this, 0, 0);
/* 123 */     this.HoodEye.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
/* 124 */     this.HoodEye.setRotationPoint(0.0F, -6.0F, 0.0F);
/* 125 */     this.HoodEye.setTextureSize(128, 64);
/* 126 */     this.HoodEye.mirror = true;
/* 127 */     setRotation(this.HoodEye, 0.0F, 0.0F, 0.0F);
/*     */     
/* 129 */     this.Hood1 = new ModelRenderer(this, 40, 12);
/* 130 */     this.Hood1.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
/* 131 */     this.Hood1.setRotationPoint(0.0F, -6.0F, 0.0F);
/* 132 */     this.Hood1.setTextureSize(128, 64);
/* 133 */     this.Hood1.mirror = true;
/* 134 */     setRotation(this.Hood1, 0.0F, 0.0F, 0.0F);
/* 135 */     this.Hood2 = new ModelRenderer(this, 36, 28);
/* 136 */     this.Hood2.addBox(-3.5F, -8.7F, 2.0F, 7, 7, 3);
/* 137 */     this.Hood2.setTextureSize(128, 64);
/* 138 */     this.Hood2.mirror = true;
/* 139 */     setRotation(this.Hood2, -0.2268928F, 0.0F, 0.0F);
/* 140 */     this.Hood3 = new ModelRenderer(this, 22, 19);
/* 141 */     this.Hood3.addBox(-3.0F, -9.0F, 2.5F, 6, 6, 3);
/* 142 */     this.Hood3.setTextureSize(128, 64);
/* 143 */     this.Hood3.mirror = true;
/* 144 */     setRotation(this.Hood3, -0.3490659F, 0.0F, 0.0F);
/* 145 */     this.Hood4 = new ModelRenderer(this, 40, 4);
/* 146 */     this.Hood4.addBox(-2.5F, -9.7F, 3.5F, 5, 5, 3);
/* 147 */     this.Hood4.setTextureSize(128, 64);
/* 148 */     this.Hood4.mirror = true;
/* 149 */     setRotation(this.Hood4, -0.5759587F, 0.0F, 0.0F);
/* 150 */     this.Hood1.addChild(this.Hood2);
/* 151 */     this.Hood1.addChild(this.Hood3);
/* 152 */     this.Hood1.addChild(this.Hood4);
/*     */     
/*     */ 
/* 155 */     this.Backplate = new ModelRenderer(this, 36, 45);
/* 156 */     this.Backplate.addBox(-4.0F, 1.0F, 2.0F, 8, 11, 2);
/* 157 */     this.Backplate.setRotationPoint(0.0F, -6.0F, 0.0F);
/* 158 */     this.Backplate.setTextureSize(128, 64);
/* 159 */     this.Backplate.mirror = true;
/* 160 */     setRotation(this.Backplate, 0.0F, 0.0F, 0.0F);
/*     */     
/* 162 */     this.ShoulderplateTopR = new ModelRenderer(this, 110, 37);
/* 163 */     this.ShoulderplateTopR.addBox(-5.5F, -2.5F, -3.5F, 2, 1, 7);
/* 164 */     this.ShoulderplateTopR.setRotationPoint(-5.0F, -4.0F, 0.0F);
/* 165 */     this.ShoulderplateTopR.setTextureSize(128, 64);
/* 166 */     this.ShoulderplateTopR.mirror = true;
/* 167 */     setRotation(this.ShoulderplateTopR, -0.3665191F, 0.3141593F, 0.4363323F);
/* 168 */     this.ShoulderplateR1 = new ModelRenderer(this, 110, 45);
/* 169 */     this.ShoulderplateR1.addBox(3.5F, -1.5F, -3.5F, 1, 4, 7);
/* 170 */     this.ShoulderplateR1.setRotationPoint(5.0F, -4.0F, 0.0F);
/* 171 */     this.ShoulderplateR1.setTextureSize(128, 64);
/* 172 */     this.ShoulderplateR1.mirror = true;
/* 173 */     setRotation(this.ShoulderplateR1, -0.3665191F, -0.3141593F, -0.4363323F);
/* 174 */     this.ShoulderplateR2 = new ModelRenderer(this, 94, 45);
/* 175 */     this.ShoulderplateR2.addBox(-3.5F, 1.5F, -3.5F, 1, 3, 7);
/* 176 */     this.ShoulderplateR2.setRotationPoint(-5.0F, -4.0F, 0.0F);
/* 177 */     this.ShoulderplateR2.setTextureSize(128, 64);
/* 178 */     this.ShoulderplateR2.mirror = true;
/* 179 */     setRotation(this.ShoulderplateR2, -0.3665191F, 0.3141593F, 0.4363323F);
/* 180 */     this.ShoulderplateR3 = new ModelRenderer(this, 94, 45);
/* 181 */     this.ShoulderplateR3.addBox(-2.5F, 3.5F, -3.5F, 1, 3, 7);
/* 182 */     this.ShoulderplateR3.setRotationPoint(-5.0F, -4.0F, 0.0F);
/* 183 */     this.ShoulderplateR3.setTextureSize(128, 64);
/* 184 */     this.ShoulderplateR3.mirror = true;
/* 185 */     setRotation(this.ShoulderplateR3, -0.3665191F, 0.3141593F, 0.4363323F);
/*     */     
/* 187 */     this.ShoulderR = new ModelRenderer(this, 56, 35);
/* 188 */     this.ShoulderR.addBox(-3.5F, -2.5F, -2.5F, 5, 5, 5);
/* 189 */     this.ShoulderR.setRotationPoint(-5.0F, -4.0F, 0.0F);
/* 190 */     this.ShoulderR.setTextureSize(128, 64);
/* 191 */     this.ShoulderR.mirror = true;
/* 192 */     setRotation(this.ShoulderR, -0.3665191F, 0.122173F, 0.0349066F);
/*     */     
/* 194 */     this.ArmL1 = new ModelRenderer(this, 72, 8);
/* 195 */     this.ArmL1.addBox(-1.0F, 2.5F, -1.5F, 4, 10, 5);
/* 196 */     this.ArmL1.setRotationPoint(5.0F, -4.0F, 0.0F);
/* 197 */     this.ArmL1.setTextureSize(128, 64);
/* 198 */     this.ArmL1.mirror = true;
/* 199 */     setRotation(this.ArmL1, -0.9599311F, -0.1047198F, -0.1919862F);
/* 200 */     this.ArmL2 = new ModelRenderer(this, 76, 28);
/* 201 */     this.ArmL2.addBox(-1.0F, 9.5F, 3.5F, 4, 3, 3);
/* 202 */     this.ArmL2.setTextureSize(128, 64);
/* 203 */     this.ArmL2.mirror = true;
/* 204 */     this.ArmL3 = new ModelRenderer(this, 76, 23);
/* 205 */     this.ArmL3.addBox(-1.0F, 6.5F, 3.5F, 4, 3, 2);
/* 206 */     this.ArmL3.setTextureSize(128, 64);
/* 207 */     this.ArmL3.mirror = true;
/* 208 */     this.ArmL1.addChild(this.ArmL2);
/* 209 */     this.ArmL1.addChild(this.ArmL3);
/*     */     
/* 211 */     this.ArmR1 = new ModelRenderer(this, 72, 8);
/* 212 */     this.ArmR1.addBox(-3.0F, 2.5F, -1.5F, 4, 10, 5);
/* 213 */     this.ArmR1.setRotationPoint(-5.0F, -4.0F, 0.0F);
/* 214 */     this.ArmR1.setTextureSize(128, 64);
/* 215 */     this.ArmR1.mirror = true;
/* 216 */     setRotation(this.ArmR1, -0.9599311F, 0.1047198F, 0.1919862F);
/* 217 */     this.ArmR2 = new ModelRenderer(this, 76, 28);
/* 218 */     this.ArmR2.addBox(-3.0F, 9.5F, 3.5F, 4, 3, 3);
/* 219 */     this.ArmR2.setTextureSize(128, 64);
/* 220 */     this.ArmR2.mirror = true;
/* 221 */     this.ArmR3 = new ModelRenderer(this, 76, 23);
/* 222 */     this.ArmR3.addBox(-3.0F, 6.5F, 3.5F, 4, 3, 2);
/* 223 */     this.ArmR3.setTextureSize(128, 64);
/* 224 */     this.ArmR3.mirror = true;
/* 225 */     this.ArmR1.addChild(this.ArmR2);
/* 226 */     this.ArmR1.addChild(this.ArmR3);
/*     */     
/* 228 */     this.ShoulderL = new ModelRenderer(this, 56, 35);
/* 229 */     this.ShoulderL.mirror = true;
/* 230 */     this.ShoulderL.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5);
/* 231 */     this.ShoulderL.setRotationPoint(5.0F, -4.0F, 0.0F);
/* 232 */     this.ShoulderL.setTextureSize(128, 64);
/*     */     
/* 234 */     setRotation(this.ShoulderL, -0.3665191F, -0.122173F, -0.0349066F);
/*     */     
/* 236 */     this.ShoulderplateLtop = new ModelRenderer(this, 110, 37);
/* 237 */     this.ShoulderplateLtop.addBox(3.5F, -2.5F, -3.5F, 2, 1, 7);
/* 238 */     this.ShoulderplateLtop.setRotationPoint(5.0F, -4.0F, 0.0F);
/* 239 */     this.ShoulderplateLtop.setTextureSize(128, 64);
/* 240 */     this.ShoulderplateLtop.mirror = true;
/* 241 */     setRotation(this.ShoulderplateLtop, -0.3665191F, -0.3141593F, -0.4363323F);
/* 242 */     this.ShoulderplateL1 = new ModelRenderer(this, 110, 45);
/* 243 */     this.ShoulderplateL1.addBox(-4.5F, -1.5F, -3.5F, 1, 4, 7);
/* 244 */     this.ShoulderplateL1.setRotationPoint(-5.0F, -4.0F, 0.0F);
/* 245 */     this.ShoulderplateL1.setTextureSize(128, 64);
/* 246 */     this.ShoulderplateL1.mirror = true;
/* 247 */     setRotation(this.ShoulderplateL1, -0.3665191F, 0.3141593F, 0.4363323F);
/* 248 */     this.ShoulderplateLtop.mirror = false;
/*     */     
/* 250 */     this.ShoulderplateL2 = new ModelRenderer(this, 94, 45);
/* 251 */     this.ShoulderplateL2.addBox(2.5F, 1.5F, -3.5F, 1, 3, 7);
/* 252 */     this.ShoulderplateL2.setRotationPoint(5.0F, -4.0F, 0.0F);
/* 253 */     this.ShoulderplateL2.setTextureSize(128, 64);
/* 254 */     this.ShoulderplateL2.mirror = true;
/* 255 */     setRotation(this.ShoulderplateL2, -0.3665191F, -0.3141593F, -0.4363323F);
/* 256 */     this.ShoulderplateL2.mirror = false;
/*     */     
/* 258 */     this.ShoulderplateL3 = new ModelRenderer(this, 94, 45);
/* 259 */     this.ShoulderplateL3.addBox(1.5F, 3.5F, -3.5F, 1, 3, 7);
/* 260 */     this.ShoulderplateL3.setRotationPoint(5.0F, -4.0F, 0.0F);
/* 261 */     this.ShoulderplateL3.setTextureSize(128, 64);
/* 262 */     this.ShoulderplateL3.mirror = true;
/* 263 */     setRotation(this.ShoulderplateL3, -0.3665191F, -0.3141593F, -0.4363323F);
/* 264 */     this.ShoulderplateL3.mirror = false;
/* 265 */     this.LegpanelR4 = new ModelRenderer(this, 0, 43);
/* 266 */     this.LegpanelR4.addBox(-3.0F, 0.5F, -3.5F, 2, 3, 1);
/* 267 */     this.LegpanelR4.setRotationPoint(-2.0F, 6.0F, 0.0F);
/* 268 */     this.LegpanelR4.setTextureSize(128, 64);
/* 269 */     this.LegpanelR4.mirror = true;
/* 270 */     setRotation(this.LegpanelR4, -0.4363323F, 0.0F, 0.0F);
/* 271 */     this.LegpanelR5 = new ModelRenderer(this, 0, 47);
/* 272 */     this.LegpanelR5.addBox(-3.0F, 2.5F, -2.5F, 2, 3, 1);
/* 273 */     this.LegpanelR5.setRotationPoint(-2.0F, 6.0F, 0.0F);
/* 274 */     this.LegpanelR5.setTextureSize(128, 64);
/* 275 */     this.LegpanelR5.mirror = true;
/* 276 */     setRotation(this.LegpanelR5, -0.4363323F, 0.0F, 0.0F);
/* 277 */     this.LegpanelR6 = new ModelRenderer(this, 6, 43);
/* 278 */     this.LegpanelR6.addBox(-3.0F, 4.5F, -1.5F, 2, 3, 1);
/* 279 */     this.LegpanelR6.setRotationPoint(-2.0F, 6.0F, 0.0F);
/* 280 */     this.LegpanelR6.setTextureSize(128, 64);
/* 281 */     this.LegpanelR6.mirror = true;
/* 282 */     setRotation(this.LegpanelR6, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 284 */     this.BackpanelR1 = new ModelRenderer(this, 0, 18);
/* 285 */     this.BackpanelR1.addBox(-3.0F, 0.5F, 2.5F, 5, 3, 1);
/* 286 */     this.BackpanelR1.setRotationPoint(-2.0F, 6.0F, 0.0F);
/* 287 */     this.BackpanelR1.setTextureSize(128, 64);
/* 288 */     this.BackpanelR1.mirror = true;
/* 289 */     setRotation(this.BackpanelR1, 0.4363323F, 0.0F, 0.0F);
/* 290 */     this.BackpanelR2 = new ModelRenderer(this, 0, 18);
/* 291 */     this.BackpanelR2.addBox(-3.0F, 2.5F, 1.5F, 5, 3, 1);
/* 292 */     this.BackpanelR2.setRotationPoint(-2.0F, 6.0F, 0.0F);
/* 293 */     this.BackpanelR2.setTextureSize(128, 64);
/* 294 */     this.BackpanelR2.mirror = true;
/* 295 */     setRotation(this.BackpanelR2, 0.4363323F, 0.0F, 0.0F);
/* 296 */     this.BackpanelR3 = new ModelRenderer(this, 0, 18);
/* 297 */     this.BackpanelR3.addBox(-3.0F, 4.5F, 0.5F, 5, 3, 1);
/* 298 */     this.BackpanelR3.setRotationPoint(-2.0F, 6.0F, 0.0F);
/* 299 */     this.BackpanelR3.setTextureSize(128, 64);
/* 300 */     this.BackpanelR3.mirror = true;
/* 301 */     setRotation(this.BackpanelR3, 0.4363323F, 0.0F, 0.0F);
/* 302 */     this.BackpanelL3 = new ModelRenderer(this, 0, 18);
/* 303 */     this.BackpanelL3.addBox(-2.0F, 4.5F, 0.5F, 5, 3, 1);
/* 304 */     this.BackpanelL3.setRotationPoint(2.0F, 6.0F, 0.0F);
/* 305 */     this.BackpanelL3.setTextureSize(128, 64);
/* 306 */     this.BackpanelL3.mirror = true;
/* 307 */     setRotation(this.BackpanelL3, 0.4363323F, 0.0F, 0.0F);
/*     */     
/* 309 */     this.LegpanelL4 = new ModelRenderer(this, 0, 43);
/* 310 */     this.LegpanelL4.addBox(1.0F, 0.5F, -3.5F, 2, 3, 1);
/* 311 */     this.LegpanelL4.setRotationPoint(2.0F, 6.0F, 0.0F);
/* 312 */     this.LegpanelL4.setTextureSize(128, 64);
/* 313 */     this.LegpanelL4.mirror = true;
/* 314 */     setRotation(this.LegpanelL4, -0.4363323F, 0.0F, 0.0F);
/* 315 */     this.LegpanelL4.mirror = false;
/*     */     
/* 317 */     this.LegpanelL5 = new ModelRenderer(this, 0, 47);
/* 318 */     this.LegpanelL5.addBox(1.0F, 2.5F, -2.5F, 2, 3, 1);
/* 319 */     this.LegpanelL5.setRotationPoint(2.0F, 6.0F, 0.0F);
/* 320 */     this.LegpanelL5.setTextureSize(128, 64);
/* 321 */     this.LegpanelL5.mirror = true;
/* 322 */     setRotation(this.LegpanelL5, -0.4363323F, 0.0F, 0.0F);
/* 323 */     this.LegpanelL5.mirror = false;
/*     */     
/* 325 */     this.LegpanelL6 = new ModelRenderer(this, 6, 43);
/* 326 */     this.LegpanelL6.addBox(1.0F, 4.5F, -1.5F, 2, 3, 1);
/* 327 */     this.LegpanelL6.setRotationPoint(2.0F, 6.0F, 0.0F);
/* 328 */     this.LegpanelL6.setTextureSize(128, 64);
/* 329 */     this.LegpanelL6.mirror = true;
/* 330 */     setRotation(this.LegpanelL6, -0.4363323F, 0.0F, 0.0F);
/* 331 */     this.LegpanelL6.mirror = false;
/*     */     
/* 333 */     this.BackpanelL1 = new ModelRenderer(this, 0, 18);
/* 334 */     this.BackpanelL1.addBox(-2.0F, 0.5F, 2.5F, 5, 3, 1);
/* 335 */     this.BackpanelL1.setRotationPoint(2.0F, 6.0F, 0.0F);
/* 336 */     this.BackpanelL1.setTextureSize(128, 64);
/* 337 */     this.BackpanelL1.mirror = true;
/* 338 */     setRotation(this.BackpanelL1, 0.4363323F, 0.0F, 0.0F);
/* 339 */     this.BackpanelL2 = new ModelRenderer(this, 0, 18);
/* 340 */     this.BackpanelL2.addBox(-2.0F, 2.5F, 1.5F, 5, 3, 1);
/* 341 */     this.BackpanelL2.setRotationPoint(2.0F, 6.0F, 0.0F);
/* 342 */     this.BackpanelL2.setTextureSize(128, 64);
/* 343 */     this.BackpanelL2.mirror = true;
/* 344 */     setRotation(this.BackpanelL2, 0.4363323F, 0.0F, 0.0F);
/*     */     
/* 346 */     this.SidepanelL1 = new ModelRenderer(this, 0, 22);
/* 347 */     this.SidepanelL1.addBox(1.5F, 0.5F, -2.5F, 1, 4, 5);
/* 348 */     this.SidepanelL1.setRotationPoint(2.0F, 6.0F, 0.0F);
/* 349 */     this.SidepanelL1.setTextureSize(128, 64);
/* 350 */     this.SidepanelL1.mirror = true;
/* 351 */     setRotation(this.SidepanelL1, 0.0F, 0.0F, -0.4363323F);
/* 352 */     this.SidepanelR1 = new ModelRenderer(this, 0, 22);
/* 353 */     this.SidepanelR1.addBox(-2.5F, 0.5F, -2.5F, 1, 4, 5);
/* 354 */     this.SidepanelR1.setRotationPoint(-2.0F, 6.0F, 0.0F);
/* 355 */     this.SidepanelR1.setTextureSize(128, 64);
/* 356 */     this.SidepanelR1.mirror = true;
/* 357 */     setRotation(this.SidepanelR1, 0.0F, 0.0F, 0.4363323F);
/*     */     
/*     */ 
/*     */ 
/* 361 */     this.SidepanelR2 = new ModelRenderer(this, 0, 54);
/* 362 */     this.SidepanelR2.addBox(0.0F, 0.0F, -0.5F, 1, 5, 5);
/* 363 */     this.SidepanelR2.setRotationPoint(-4.5F, 9.5F, -2.0F);
/* 364 */     this.SidepanelR2.setTextureSize(128, 64);
/* 365 */     this.SidepanelR2.mirror = true;
/* 366 */     setRotation(this.SidepanelR2, 0.0F, 0.0F, 0.122173F);
/* 367 */     this.SidepanelR3 = new ModelRenderer(this, 0, 35);
/* 368 */     this.SidepanelR3.addBox(0.0F, 0.0F, -0.5F, 1, 3, 5);
/* 369 */     this.SidepanelR3.setRotationPoint(0.0F, 5.0F, 0.0F);
/* 370 */     this.SidepanelR3.setTextureSize(128, 64);
/* 371 */     this.SidepanelR3.mirror = true;
/* 372 */     setRotation(this.SidepanelR3, 0.0F, 0.0F, 0.296706F);
/* 373 */     this.SidepanelR4 = new ModelRenderer(this, 24, 35);
/* 374 */     this.SidepanelR4.addBox(0.0F, 0.0F, -0.5F, 1, 3, 5);
/* 375 */     this.SidepanelR4.setRotationPoint(0.0F, 3.0F, 0.0F);
/* 376 */     this.SidepanelR4.setTextureSize(128, 64);
/* 377 */     this.SidepanelR4.mirror = true;
/* 378 */     setRotation(this.SidepanelR4, 0.0F, 0.0F, 0.5235988F);
/*     */     
/*     */ 
/* 381 */     this.SidepanelL2 = new ModelRenderer(this, 0, 54);
/* 382 */     this.SidepanelL2.addBox(0.0F, 0.0F, -0.5F, 1, 5, 5);
/* 383 */     this.SidepanelL2.setRotationPoint(4.5F, 9.5F, -2.0F);
/* 384 */     this.SidepanelL2.setTextureSize(128, 64);
/* 385 */     this.SidepanelL2.mirror = true;
/* 386 */     setRotation(this.SidepanelL2, 0.0F, 0.0F, -0.122173F);
/* 387 */     this.SidepanelL3 = new ModelRenderer(this, 0, 35);
/* 388 */     this.SidepanelL3.addBox(0.0F, 0.0F, -0.5F, 1, 3, 5);
/* 389 */     this.SidepanelL3.setRotationPoint(0.0F, 5.0F, 0.0F);
/* 390 */     this.SidepanelL3.setTextureSize(128, 64);
/* 391 */     this.SidepanelL3.mirror = true;
/* 392 */     setRotation(this.SidepanelL3, 0.0F, 0.0F, -0.296706F);
/* 393 */     this.SidepanelL4 = new ModelRenderer(this, 24, 35);
/* 394 */     this.SidepanelL4.addBox(0.0F, 0.0F, -0.5F, 1, 3, 5);
/* 395 */     this.SidepanelL4.setRotationPoint(0.0F, 3.0F, 0.0F);
/* 396 */     this.SidepanelL4.setTextureSize(128, 64);
/* 397 */     this.SidepanelL4.mirror = true;
/* 398 */     setRotation(this.SidepanelL4, 0.0F, 0.0F, -0.5235988F);
/*     */     
/*     */ 
/* 401 */     this.LegpanelC1 = new ModelRenderer(this, 16, 45);
/* 402 */     this.LegpanelC1.addBox(-3.0F, 0.0F, -0.5F, 6, 8, 1);
/* 403 */     this.LegpanelC1.setRotationPoint(0.0F, 5.5F, -3.0F);
/* 404 */     this.LegpanelC1.setTextureSize(128, 64);
/* 405 */     this.LegpanelC1.mirror = true;
/* 406 */     setRotation(this.LegpanelC1, 0.0F, 0.0F, 0.0F);
/* 407 */     this.LegpanelC2 = new ModelRenderer(this, 16, 54);
/* 408 */     this.LegpanelC2.addBox(-3.0F, 0.0F, -0.5F, 6, 4, 1);
/* 409 */     this.LegpanelC2.setRotationPoint(0.0F, 8.0F, 0.0F);
/* 410 */     this.LegpanelC2.setTextureSize(128, 64);
/* 411 */     this.LegpanelC2.mirror = true;
/* 412 */     setRotation(this.LegpanelC2, 0.0F, 0.0F, 0.0F);
/* 413 */     this.LegpanelC3 = new ModelRenderer(this, 32, 59);
/* 414 */     this.LegpanelC3.addBox(-3.0F, 0.0F, -0.5F, 6, 4, 1);
/* 415 */     this.LegpanelC3.setRotationPoint(0.0F, 4.0F, 0.0F);
/* 416 */     this.LegpanelC3.setTextureSize(128, 64);
/* 417 */     this.LegpanelC3.mirror = true;
/* 418 */     setRotation(this.LegpanelC3, 0.0F, 0.0F, 0.0F);
/*     */     
/* 420 */     this.Cloak1 = new ModelRenderer(this, 106, 0);
/* 421 */     this.Cloak1.addBox(0.0F, 0.0F, -0.5F, 10, 18, 1);
/* 422 */     this.Cloak1.setRotationPoint(-5.0F, -6.0F, 4.0F);
/* 423 */     this.Cloak1.setTextureSize(128, 64);
/* 424 */     this.Cloak1.mirror = true;
/* 425 */     setRotation(this.Cloak1, 0.0F, 0.0F, 0.0F);
/* 426 */     this.Cloak2 = new ModelRenderer(this, 106, 19);
/* 427 */     this.Cloak2.addBox(0.0F, 0.0F, -0.5F, 10, 4, 1);
/* 428 */     this.Cloak2.setRotationPoint(0.0F, 18.0F, 0.0F);
/* 429 */     this.Cloak2.setTextureSize(128, 64);
/* 430 */     this.Cloak2.mirror = true;
/* 431 */     setRotation(this.Cloak2, 0.0F, 0.0F, 0.0F);
/* 432 */     this.Cloak3 = new ModelRenderer(this, 106, 24);
/* 433 */     this.Cloak3.addBox(0.0F, 0.0F, -0.5F, 10, 4, 1);
/* 434 */     this.Cloak3.setRotationPoint(0.0F, 4.0F, 0.0F);
/* 435 */     this.Cloak3.setTextureSize(128, 64);
/* 436 */     this.Cloak3.mirror = true;
/* 437 */     setRotation(this.Cloak3, 0.0F, 0.0F, 0.0F);
/*     */     
/* 439 */     this.LegpanelC1.addChild(this.LegpanelC2);
/* 440 */     this.LegpanelC2.addChild(this.LegpanelC3);
/* 441 */     this.SidepanelL2.addChild(this.SidepanelL3);
/* 442 */     this.SidepanelL3.addChild(this.SidepanelL4);
/* 443 */     this.SidepanelR2.addChild(this.SidepanelR3);
/* 444 */     this.SidepanelR3.addChild(this.SidepanelR4);
/* 445 */     this.Cloak1.addChild(this.Cloak2);
/* 446 */     this.Cloak2.addChild(this.Cloak3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_)
/*     */   {
/* 456 */     this.partialTicks = p_78086_4_;
/*     */   }
/*     */   
/*     */ 
/*     */   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 462 */     super.render(entity, f, f1, f2, f3, f4, f5);
/* 463 */     setRotationAngles(f, f1, f2, f3, f4, f5, entity);
/*     */     
/* 465 */     this.BeltR.render(f5);
/* 466 */     this.Mbelt.render(f5);
/* 467 */     this.MbeltL.render(f5);
/* 468 */     this.MbeltR.render(f5);
/* 469 */     this.BeltL.render(f5);
/*     */     
/* 471 */     this.Chestplate.render(f5);
/* 472 */     this.Hood1.render(f5);
/*     */     
/* 474 */     this.Backplate.render(f5);
/*     */     
/* 476 */     this.ShoulderplateTopR.render(f5);
/* 477 */     this.ShoulderplateR1.render(f5);
/* 478 */     this.ShoulderplateR2.render(f5);
/* 479 */     this.ShoulderplateR3.render(f5);
/*     */     
/* 481 */     this.ShoulderR.render(f5);
/*     */     
/* 483 */     this.ArmL1.render(f5);
/* 484 */     this.ArmR1.render(f5);
/*     */     
/* 486 */     this.ShoulderL.render(f5);
/* 487 */     this.ShoulderplateLtop.render(f5);
/* 488 */     this.ShoulderplateL1.render(f5);
/* 489 */     this.ShoulderplateL2.render(f5);
/* 490 */     this.ShoulderplateL3.render(f5);
/* 491 */     this.LegpanelR4.render(f5);
/* 492 */     this.LegpanelR5.render(f5);
/* 493 */     this.LegpanelR6.render(f5);
/*     */     
/* 495 */     this.BackpanelR1.render(f5);
/* 496 */     this.BackpanelR2.render(f5);
/* 497 */     this.BackpanelR3.render(f5);
/* 498 */     this.BackpanelL3.render(f5);
/* 499 */     this.LegpanelL4.render(f5);
/* 500 */     this.LegpanelL5.render(f5);
/* 501 */     this.LegpanelL6.render(f5);
/*     */     
/* 503 */     this.BackpanelL1.render(f5);
/* 504 */     this.BackpanelL2.render(f5);
/*     */     
/*     */ 
/* 507 */     this.Cloak1.render(f5);
/*     */     
/* 509 */     this.SidepanelR1.render(f5);
/* 510 */     this.SidepanelL1.render(f5);
/*     */     
/* 512 */     this.SidepanelL2.render(f5);
/* 513 */     this.SidepanelR2.render(f5);
/*     */     
/* 515 */     this.LegpanelC1.render(f5);
/*     */     
/* 517 */     if ((entity instanceof EntityEldritchWarden)) {
/* 518 */       GL11.glPushMatrix();
/* 519 */       GL11.glEnable(3042);
/* 520 */       GL11.glBlendFunc(770, 1);
/* 521 */       GL11.glScaled(1.01D, 1.01D, 1.01D);
/* 522 */       int j = (int)(195.0F + MathHelper.sin(entity.ticksExisted / 3.0F) * 15.0F + 15.0F);
/* 523 */       int k = j % 65536;
/* 524 */       int l = j / 65536;
/* 525 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/* 526 */       this.HoodEye.render(f5);
/* 527 */       GL11.glDisable(3042);
/* 528 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity)
/*     */   {
/* 537 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
/*     */     
/* 539 */     this.Hood1.rotateAngleY = (par4 / 57.295776F);
/* 540 */     this.Hood1.rotateAngleX = (par5 / 57.295776F);
/* 541 */     this.HoodEye.rotateAngleX = this.Hood1.rotateAngleX;
/* 542 */     this.HoodEye.rotateAngleY = this.Hood1.rotateAngleY;
/* 543 */     float alr = 0.0F;
/* 544 */     float all = 0.0F;
/* 545 */     if ((entity instanceof EntityEldritchGuardian)) {
/* 546 */       alr = ((EntityEldritchGuardian)entity).armLiftR;
/* 547 */       all = ((EntityEldritchGuardian)entity).armLiftL;
/*     */     }
/* 549 */     if ((entity instanceof EntityEldritchWarden)) {
/* 550 */       alr = ((EntityEldritchWarden)entity).armLiftR;
/* 551 */       all = ((EntityEldritchWarden)entity).armLiftL;
/*     */     }
/* 553 */     this.ArmL1.rotateAngleX = (-1.0F - all + MathHelper.sin((entity.ticksExisted + 20 + this.partialTicks) / 10.0F) * 0.08F);
/* 554 */     this.ArmR1.rotateAngleX = (-1.0F - alr + MathHelper.sin((entity.ticksExisted + this.partialTicks) / 10.0F) * 0.08F);
/*     */     
/* 556 */     this.LegpanelC1.rotateAngleX = (-0.15F + MathHelper.sin((entity.ticksExisted + this.partialTicks) / 8.0F) * 0.12F);
/* 557 */     this.LegpanelC2.rotateAngleX = (MathHelper.sin((entity.ticksExisted + this.partialTicks - 5.0F) / 8.0F) * 0.13F);
/* 558 */     this.LegpanelC3.rotateAngleX = (MathHelper.sin((entity.ticksExisted + this.partialTicks - 10.0F) / 8.0F) * 0.14F);
/*     */     
/* 560 */     this.Cloak1.rotateAngleX = (0.2F + MathHelper.sin((entity.ticksExisted + this.partialTicks) / 7.0F) * 0.08F);
/* 561 */     this.Cloak2.rotateAngleX = (MathHelper.sin((entity.ticksExisted + this.partialTicks - 5.0F) / 7.0F) * 0.1F);
/* 562 */     this.Cloak3.rotateAngleX = (MathHelper.sin((entity.ticksExisted + this.partialTicks - 10.0F) / 7.0F) * 0.12F);
/*     */     
/* 564 */     this.SidepanelL2.rotateAngleZ = (-0.2F + MathHelper.sin((entity.ticksExisted + 10 + this.partialTicks) / 8.0F) * 0.12F);
/* 565 */     this.SidepanelL3.rotateAngleZ = (MathHelper.sin((entity.ticksExisted + this.partialTicks + 5.0F) / 8.0F) * 0.13F);
/* 566 */     this.SidepanelL4.rotateAngleZ = (MathHelper.sin((entity.ticksExisted + this.partialTicks) / 8.0F) * 0.14F);
/*     */     
/* 568 */     this.SidepanelR2.rotateAngleZ = (0.2F + MathHelper.sin((entity.ticksExisted - 5 + this.partialTicks) / 8.0F) * 0.12F);
/* 569 */     this.SidepanelR3.rotateAngleZ = (MathHelper.sin((entity.ticksExisted + this.partialTicks - 10.0F) / 8.0F) * 0.13F);
/* 570 */     this.SidepanelR4.rotateAngleZ = (MathHelper.sin((entity.ticksExisted + this.partialTicks - 15.0F) / 8.0F) * 0.14F);
/*     */   }
/*     */   
/*     */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*     */   {
/* 575 */     model.rotateAngleX = x;
/* 576 */     model.rotateAngleY = y;
/* 577 */     model.rotateAngleZ = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\entity\ModelEldritchGuardian.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */