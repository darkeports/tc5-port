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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelRobe
/*     */   extends ModelCustomArmor
/*     */ {
/*     */   ModelRenderer Hood1;
/*     */   ModelRenderer Hood2;
/*     */   ModelRenderer Hood3;
/*     */   ModelRenderer Hood4;
/*     */   ModelRenderer Chestthing;
/*     */   ModelRenderer Mbelt;
/*     */   ModelRenderer MbeltB;
/*     */   ModelRenderer ClothchestL;
/*     */   ModelRenderer ClothchestR;
/*     */   ModelRenderer Book;
/*     */   ModelRenderer Scroll;
/*     */   ModelRenderer BeltR;
/*     */   ModelRenderer Backplate;
/*     */   ModelRenderer MbeltL;
/*     */   ModelRenderer MbeltR;
/*     */   ModelRenderer BeltL;
/*     */   ModelRenderer Chestplate;
/*     */   ModelRenderer ShoulderplateR1;
/*     */   ModelRenderer ShoulderplateR2;
/*     */   ModelRenderer ShoulderplateR3;
/*     */   ModelRenderer ShoulderplateTopR;
/*     */   ModelRenderer RArm1;
/*     */   ModelRenderer RArm2;
/*     */   ModelRenderer RArm3;
/*     */   ModelRenderer LArm1;
/*     */   ModelRenderer LArm2;
/*     */   ModelRenderer LArm3;
/*     */   ModelRenderer ShoulderplateL1;
/*     */   ModelRenderer ShoulderplateL2;
/*     */   ModelRenderer ShoulderplateL3;
/*     */   ModelRenderer ShoulderplateTopL;
/*     */   ModelRenderer ShoulderL;
/*     */   ModelRenderer ShoulderR;
/*     */   ModelRenderer ClothBackR3;
/*     */   ModelRenderer FrontclothR2;
/*     */   ModelRenderer FrontclothR1;
/*     */   ModelRenderer SideclothR2;
/*     */   ModelRenderer SideclothR1;
/*     */   ModelRenderer SideclothR3;
/*     */   ModelRenderer ClothBackR1;
/*     */   ModelRenderer ClothBackR2;
/*     */   ModelRenderer SidepanelR1;
/*     */   ModelRenderer LegpanelR6;
/*     */   ModelRenderer LegpanelR5;
/*     */   ModelRenderer LegpanelR4;
/*     */   ModelRenderer FrontclothL2;
/*     */   ModelRenderer ClothBackL3;
/*     */   ModelRenderer ClothBackL1;
/*     */   ModelRenderer FrontclothL1;
/*     */   ModelRenderer SideclothL2;
/*     */   ModelRenderer SideclothL3;
/*     */   ModelRenderer Focipouch;
/*     */   ModelRenderer SideclothL1;
/*     */   ModelRenderer ClothBackL2;
/*     */   ModelRenderer LegpanelL4;
/*     */   ModelRenderer LegpanelL5;
/*     */   ModelRenderer LegpanelL6;
/*     */   ModelRenderer SidepanelL1;
/*     */   
/*     */   public ModelRobe(float f)
/*     */   {
/*  88 */     super(f, 0, 128, 64);
/*  89 */     this.textureWidth = 128;
/*  90 */     this.textureHeight = 64;
/*     */     
/*     */ 
/*  93 */     this.Hood1 = new ModelRenderer(this, 16, 7);
/*  94 */     this.Hood1.addBox(-4.5F, -9.0F, -4.6F, 9, 9, 9);
/*  95 */     this.Hood1.setTextureSize(128, 64);
/*  96 */     setRotation(this.Hood1, 0.0F, 0.0F, 0.0F);
/*     */     
/*  98 */     this.Hood2 = new ModelRenderer(this, 52, 13);
/*  99 */     this.Hood2.addBox(-4.0F, -9.7F, 2.0F, 8, 9, 3);
/* 100 */     this.Hood2.setTextureSize(128, 64);
/* 101 */     setRotation(this.Hood2, -0.2268928F, 0.0F, 0.0F);
/*     */     
/* 103 */     this.Hood3 = new ModelRenderer(this, 52, 14);
/* 104 */     this.Hood3.addBox(-3.5F, -10.0F, 3.5F, 7, 8, 3);
/* 105 */     this.Hood3.setTextureSize(128, 64);
/* 106 */     setRotation(this.Hood3, -0.3490659F, 0.0F, 0.0F);
/*     */     
/* 108 */     this.Hood4 = new ModelRenderer(this, 53, 15);
/* 109 */     this.Hood4.addBox(-3.0F, -10.7F, 3.5F, 6, 7, 3);
/* 110 */     this.Hood4.setTextureSize(128, 64);
/* 111 */     setRotation(this.Hood4, -0.5759587F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 114 */     this.Chestthing = new ModelRenderer(this, 56, 50);
/* 115 */     this.Chestthing.addBox(-2.5F, 1.0F, -4.0F, 5, 7, 1);
/* 116 */     this.Chestthing.setTextureSize(128, 64);
/* 117 */     setRotation(this.Chestthing, 0.0F, 0.0F, 0.0F);
/*     */     
/* 119 */     this.Mbelt = new ModelRenderer(this, 16, 55);
/* 120 */     this.Mbelt.addBox(-4.0F, 7.0F, -3.0F, 8, 5, 1);
/* 121 */     this.Mbelt.setTextureSize(128, 64);
/* 122 */     setRotation(this.Mbelt, 0.0F, 0.0F, 0.0F);
/*     */     
/* 124 */     this.MbeltB = new ModelRenderer(this, 16, 55);
/* 125 */     this.MbeltB.addBox(-4.0F, 7.0F, -4.0F, 8, 5, 1);
/* 126 */     this.MbeltB.setTextureSize(128, 64);
/* 127 */     setRotation(this.MbeltB, 0.0F, 3.141593F, 0.0F);
/*     */     
/* 129 */     this.ClothchestL = new ModelRenderer(this, 108, 38);
/* 130 */     this.ClothchestL.mirror = true;
/* 131 */     this.ClothchestL.addBox(2.1F, 0.5F, -3.5F, 2, 8, 1);
/* 132 */     this.ClothchestL.setTextureSize(128, 64);
/* 133 */     setRotation(this.ClothchestL, 0.0F, 0.0F, 0.0F);
/*     */     
/* 135 */     this.ClothchestR = new ModelRenderer(this, 108, 38);
/* 136 */     this.ClothchestR.addBox(-4.1F, 0.5F, -3.5F, 2, 8, 1);
/* 137 */     this.ClothchestR.setTextureSize(128, 64);
/* 138 */     setRotation(this.ClothchestR, 0.0F, 0.0F, 0.0F);
/*     */     
/* 140 */     this.Book = new ModelRenderer(this, 81, 16);
/* 141 */     this.Book.addBox(1.0F, 0.0F, 4.0F, 5, 7, 2);
/* 142 */     this.Book.setTextureSize(128, 64);
/* 143 */     setRotation(this.Book, 0.0F, 0.0F, 0.7679449F);
/*     */     
/* 145 */     this.Scroll = new ModelRenderer(this, 78, 25);
/* 146 */     this.Scroll.addBox(-2.0F, 9.5F, 4.0F, 8, 3, 3);
/* 147 */     this.Scroll.setTextureSize(128, 64);
/* 148 */     setRotation(this.Scroll, 0.0F, 0.0F, 0.1919862F);
/*     */     
/* 150 */     this.BeltR = new ModelRenderer(this, 16, 36);
/* 151 */     this.BeltR.addBox(-5.0F, 4.0F, -3.0F, 1, 3, 6);
/* 152 */     this.BeltR.setTextureSize(128, 64);
/* 153 */     setRotation(this.BeltR, 0.0F, 0.0F, 0.0F);
/*     */     
/* 155 */     this.Backplate = new ModelRenderer(this, 36, 45);
/* 156 */     this.Backplate.addBox(-4.0F, 1.0F, 1.9F, 8, 11, 2);
/* 157 */     this.Backplate.setTextureSize(128, 64);
/* 158 */     setRotation(this.Backplate, 0.0F, 0.0F, 0.0F);
/*     */     
/* 160 */     this.MbeltL = new ModelRenderer(this, 16, 36);
/* 161 */     this.MbeltL.addBox(4.0F, 8.0F, -3.0F, 1, 3, 6);
/* 162 */     this.MbeltL.setTextureSize(128, 64);
/* 163 */     setRotation(this.MbeltL, 0.0F, 0.0F, 0.0F);
/*     */     
/* 165 */     this.MbeltR = new ModelRenderer(this, 16, 36);
/* 166 */     this.MbeltR.addBox(-5.0F, 8.0F, -3.0F, 1, 3, 6);
/* 167 */     this.MbeltR.setTextureSize(128, 64);
/* 168 */     setRotation(this.MbeltR, 0.0F, 0.0F, 0.0F);
/*     */     
/* 170 */     this.BeltL = new ModelRenderer(this, 16, 36);
/* 171 */     this.BeltL.addBox(4.0F, 4.0F, -3.0F, 1, 3, 6);
/* 172 */     this.BeltL.setTextureSize(128, 64);
/* 173 */     setRotation(this.BeltL, 0.0F, 0.0F, 0.0F);
/*     */     
/* 175 */     this.Chestplate = new ModelRenderer(this, 16, 25);
/* 176 */     this.Chestplate.addBox(-4.0F, 1.0F, -3.0F, 8, 6, 1);
/* 177 */     this.Chestplate.setTextureSize(128, 64);
/* 178 */     setRotation(this.Chestplate, 0.0F, 0.0F, 0.0F);
/*     */     
/* 180 */     this.ShoulderplateR1 = new ModelRenderer(this, 56, 33);
/* 181 */     this.ShoulderplateR1.addBox(-4.5F, -1.5F, -3.5F, 1, 4, 7);
/*     */     
/* 183 */     this.ShoulderplateR1.setTextureSize(128, 64);
/* 184 */     setRotation(this.ShoulderplateR1, 0.0F, 0.0F, 0.4363323F);
/*     */     
/* 186 */     this.ShoulderplateR2 = new ModelRenderer(this, 40, 33);
/* 187 */     this.ShoulderplateR2.addBox(-3.5F, 1.5F, -3.5F, 1, 3, 7);
/*     */     
/* 189 */     this.ShoulderplateR2.setTextureSize(128, 64);
/* 190 */     setRotation(this.ShoulderplateR2, 0.0F, 0.0F, 0.4363323F);
/*     */     
/* 192 */     this.ShoulderplateR3 = new ModelRenderer(this, 40, 33);
/* 193 */     this.ShoulderplateR3.addBox(-2.5F, 3.5F, -3.5F, 1, 3, 7);
/*     */     
/* 195 */     this.ShoulderplateR3.setTextureSize(128, 64);
/* 196 */     setRotation(this.ShoulderplateR3, 0.0F, 0.0F, 0.4363323F);
/*     */     
/* 198 */     this.ShoulderplateTopR = new ModelRenderer(this, 56, 25);
/* 199 */     this.ShoulderplateTopR.addBox(-5.5F, -2.5F, -3.5F, 2, 1, 7);
/*     */     
/* 201 */     this.ShoulderplateTopR.setTextureSize(128, 64);
/* 202 */     setRotation(this.ShoulderplateTopR, 0.0F, 0.0F, 0.4363323F);
/*     */     
/* 204 */     this.RArm1 = new ModelRenderer(this, 88, 39);
/* 205 */     this.RArm1.addBox(-3.5F, 2.5F, -2.5F, 5, 7, 5);
/*     */     
/* 207 */     this.RArm1.setTextureSize(128, 64);
/* 208 */     setRotation(this.RArm1, 0.0F, 0.0F, 0.0F);
/*     */     
/* 210 */     this.RArm2 = new ModelRenderer(this, 76, 32);
/* 211 */     this.RArm2.addBox(-3.0F, 5.5F, 2.5F, 4, 4, 2);
/*     */     
/* 213 */     this.RArm2.setTextureSize(128, 64);
/* 214 */     setRotation(this.RArm2, 0.0F, 0.0F, 0.0F);
/*     */     
/* 216 */     this.RArm3 = new ModelRenderer(this, 88, 32);
/* 217 */     this.RArm3.addBox(-2.5F, 3.5F, 2.5F, 3, 2, 1);
/*     */     
/* 219 */     this.RArm3.setTextureSize(128, 64);
/* 220 */     setRotation(this.RArm3, 0.0F, 0.0F, 0.0F);
/*     */     
/* 222 */     this.ShoulderplateL1 = new ModelRenderer(this, 56, 33);
/* 223 */     this.ShoulderplateL1.addBox(3.5F, -1.5F, -3.5F, 1, 4, 7);
/*     */     
/* 225 */     this.ShoulderplateL1.setTextureSize(128, 64);
/* 226 */     setRotation(this.ShoulderplateL1, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 228 */     this.ShoulderplateL2 = new ModelRenderer(this, 40, 33);
/* 229 */     this.ShoulderplateL2.addBox(2.5F, 1.5F, -3.5F, 1, 3, 7);
/*     */     
/* 231 */     this.ShoulderplateL2.setTextureSize(128, 64);
/* 232 */     setRotation(this.ShoulderplateL2, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 234 */     this.ShoulderplateL3 = new ModelRenderer(this, 40, 33);
/* 235 */     this.ShoulderplateL3.addBox(1.5F, 3.5F, -3.5F, 1, 3, 7);
/*     */     
/* 237 */     this.ShoulderplateL3.setTextureSize(128, 64);
/* 238 */     setRotation(this.ShoulderplateL3, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 240 */     this.ShoulderplateTopL = new ModelRenderer(this, 56, 25);
/* 241 */     this.ShoulderplateTopL.addBox(3.5F, -2.5F, -3.5F, 2, 1, 7);
/*     */     
/* 243 */     this.ShoulderplateTopL.setTextureSize(128, 64);
/* 244 */     setRotation(this.ShoulderplateTopL, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 246 */     this.ShoulderR = new ModelRenderer(this, 16, 45);
/* 247 */     this.ShoulderR.mirror = true;
/* 248 */     this.ShoulderR.addBox(-3.5F, -2.5F, -2.5F, 5, 5, 5);
/*     */     
/* 250 */     this.ShoulderR.setTextureSize(128, 64);
/* 251 */     setRotation(this.ShoulderR, 0.0F, 0.0F, 0.0F);
/*     */     
/* 253 */     this.LArm1 = new ModelRenderer(this, 88, 39);
/* 254 */     this.LArm1.mirror = true;
/* 255 */     this.LArm1.addBox(-1.5F, 2.5F, -2.5F, 5, 7, 5);
/*     */     
/* 257 */     this.LArm1.setTextureSize(128, 64);
/* 258 */     setRotation(this.LArm1, 0.0F, 0.0F, 0.0F);
/*     */     
/* 260 */     this.LArm2 = new ModelRenderer(this, 76, 32);
/* 261 */     this.LArm2.addBox(-1.0F, 5.5F, 2.5F, 4, 4, 2);
/*     */     
/* 263 */     this.LArm2.setTextureSize(128, 64);
/* 264 */     setRotation(this.LArm2, 0.0F, 0.0F, 0.0F);
/*     */     
/* 266 */     this.LArm3 = new ModelRenderer(this, 88, 32);
/* 267 */     this.LArm3.addBox(-0.5F, 3.5F, 2.5F, 3, 2, 1);
/*     */     
/* 269 */     this.LArm3.setTextureSize(128, 64);
/* 270 */     setRotation(this.LArm3, 0.0F, 0.0F, 0.0F);
/*     */     
/* 272 */     this.ShoulderL = new ModelRenderer(this, 16, 45);
/* 273 */     this.ShoulderL.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5);
/*     */     
/* 275 */     this.ShoulderL.setTextureSize(128, 64);
/* 276 */     this.ShoulderL.mirror = true;
/* 277 */     setRotation(this.ShoulderL, 0.0F, 0.0F, 0.0F);
/*     */     
/* 279 */     this.FrontclothR1 = new ModelRenderer(this, 108, 38);
/* 280 */     this.FrontclothR1.addBox(0.0F, 0.0F, 0.0F, 3, 8, 1);
/* 281 */     this.FrontclothR1.setRotationPoint(-3.0F, 11.0F, -2.9F);
/* 282 */     this.FrontclothR1.setTextureSize(128, 64);
/* 283 */     setRotation(this.FrontclothR1, -0.1047198F, 0.0F, 0.0F);
/*     */     
/* 285 */     this.FrontclothR2 = new ModelRenderer(this, 108, 47);
/* 286 */     this.FrontclothR2.addBox(0.0F, 7.5F, 1.7F, 3, 3, 1);
/* 287 */     this.FrontclothR2.setRotationPoint(-3.0F, 11.0F, -2.9F);
/* 288 */     this.FrontclothR2.setTextureSize(128, 64);
/* 289 */     setRotation(this.FrontclothR2, -0.3316126F, 0.0F, 0.0F);
/*     */     
/* 291 */     this.FrontclothL1 = new ModelRenderer(this, 108, 38);
/* 292 */     this.FrontclothL1.mirror = true;
/* 293 */     this.FrontclothL1.addBox(0.0F, 0.0F, 0.0F, 3, 8, 1);
/* 294 */     this.FrontclothL1.setRotationPoint(0.0F, 11.0F, -2.9F);
/* 295 */     this.FrontclothL1.setTextureSize(128, 64);
/* 296 */     setRotation(this.FrontclothL1, -0.1047198F, 0.0F, 0.0F);
/*     */     
/* 298 */     this.FrontclothL2 = new ModelRenderer(this, 108, 47);
/* 299 */     this.FrontclothL2.mirror = true;
/* 300 */     this.FrontclothL2.addBox(0.0F, 7.5F, 1.7F, 3, 3, 1);
/* 301 */     this.FrontclothL2.setRotationPoint(0.0F, 11.0F, -2.9F);
/* 302 */     this.FrontclothL2.setTextureSize(128, 64);
/* 303 */     setRotation(this.FrontclothL2, -0.3316126F, 0.0F, 0.0F);
/*     */     
/*     */ 
/*     */ 
/* 307 */     this.ClothBackR1 = new ModelRenderer(this, 118, 16);
/* 308 */     this.ClothBackR1.mirror = true;
/* 309 */     this.ClothBackR1.addBox(0.0F, 0.0F, 0.0F, 4, 8, 1);
/* 310 */     this.ClothBackR1.setRotationPoint(-4.0F, 11.5F, 2.9F);
/* 311 */     this.ClothBackR1.setTextureSize(128, 64);
/* 312 */     setRotation(this.ClothBackR1, 0.1047198F, 0.0F, 0.0F);
/*     */     
/* 314 */     this.ClothBackR2 = new ModelRenderer(this, 123, 9);
/* 315 */     this.ClothBackR2.addBox(0.0F, 7.8F, -0.9F, 1, 2, 1);
/* 316 */     this.ClothBackR2.setRotationPoint(-4.0F, 11.5F, 2.9F);
/* 317 */     this.ClothBackR2.setTextureSize(128, 64);
/* 318 */     setRotation(this.ClothBackR2, 0.2268928F, 0.0F, 0.0F);
/*     */     
/* 320 */     this.ClothBackR3 = new ModelRenderer(this, 120, 12);
/* 321 */     this.ClothBackR3.mirror = true;
/* 322 */     this.ClothBackR3.addBox(1.0F, 7.8F, -0.9F, 3, 3, 1);
/* 323 */     this.ClothBackR3.setRotationPoint(-4.0F, 11.5F, 2.9F);
/* 324 */     this.ClothBackR3.setTextureSize(128, 64);
/* 325 */     setRotation(this.ClothBackR3, 0.2268928F, 0.0F, 0.0F);
/*     */     
/*     */ 
/* 328 */     this.ClothBackL1 = new ModelRenderer(this, 118, 16);
/* 329 */     this.ClothBackL1.addBox(0.0F, 0.0F, 0.0F, 4, 8, 1);
/* 330 */     this.ClothBackL1.setRotationPoint(0.0F, 11.5F, 2.9F);
/* 331 */     this.ClothBackL1.setTextureSize(128, 64);
/* 332 */     setRotation(this.ClothBackL1, 0.1047198F, 0.0F, 0.0F);
/*     */     
/* 334 */     this.ClothBackL2 = new ModelRenderer(this, 123, 9);
/* 335 */     this.ClothBackL2.mirror = true;
/* 336 */     this.ClothBackL2.addBox(3.0F, 7.8F, -0.9F, 1, 2, 1);
/* 337 */     this.ClothBackL2.setRotationPoint(0.0F, 11.5F, 2.9F);
/* 338 */     this.ClothBackL2.setTextureSize(128, 64);
/* 339 */     setRotation(this.ClothBackL2, 0.2268928F, 0.0F, 0.0F);
/*     */     
/* 341 */     this.ClothBackL3 = new ModelRenderer(this, 120, 12);
/* 342 */     this.ClothBackL3.addBox(0.0F, 7.8F, -0.9F, 3, 3, 1);
/* 343 */     this.ClothBackL3.setRotationPoint(0.0F, 11.5F, 2.9F);
/* 344 */     this.ClothBackL3.setTextureSize(128, 64);
/* 345 */     setRotation(this.ClothBackL3, 0.2268928F, 0.0F, 0.0F);
/*     */     
/*     */ 
/*     */ 
/* 349 */     this.SideclothL2 = new ModelRenderer(this, 116, 34);
/* 350 */     this.SideclothL2.addBox(0.5F, 5.5F, -2.5F, 1, 3, 5);
/*     */     
/* 352 */     this.SideclothL2.setTextureSize(128, 64);
/* 353 */     setRotation(this.SideclothL2, 0.0F, 0.0F, -0.296706F);
/*     */     
/* 355 */     this.SideclothR1 = new ModelRenderer(this, 116, 42);
/* 356 */     this.SideclothR1.addBox(-2.5F, 0.5F, -2.5F, 1, 5, 5);
/*     */     
/* 358 */     this.SideclothR1.setTextureSize(128, 64);
/* 359 */     setRotation(this.SideclothR1, 0.0F, 0.0F, 0.122173F);
/*     */     
/* 361 */     this.SideclothR2 = new ModelRenderer(this, 116, 34);
/* 362 */     this.SideclothR2.addBox(-1.5F, 5.5F, -2.5F, 1, 3, 5);
/*     */     
/* 364 */     this.SideclothR2.setTextureSize(128, 64);
/* 365 */     setRotation(this.SideclothR2, 0.0F, 0.0F, 0.296706F);
/*     */     
/* 367 */     this.SideclothR3 = new ModelRenderer(this, 116, 1);
/* 368 */     this.SideclothR3.addBox(0.4F, 8.4F, -2.5F, 1, 3, 5);
/*     */     
/* 370 */     this.SideclothR3.setTextureSize(128, 64);
/* 371 */     setRotation(this.SideclothR3, 0.0F, 0.0F, 0.5235988F);
/*     */     
/*     */ 
/*     */ 
/* 375 */     this.SidepanelR1 = new ModelRenderer(this, 116, 25);
/* 376 */     this.SidepanelR1.addBox(-2.5F, 0.5F, -2.5F, 1, 4, 5);
/*     */     
/* 378 */     this.SidepanelR1.setTextureSize(128, 64);
/* 379 */     this.SidepanelR1.mirror = true;
/* 380 */     setRotation(this.SidepanelR1, 0.0F, 0.0F, 0.4363323F);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 386 */     this.LegpanelR6 = new ModelRenderer(this, 82, 38);
/* 387 */     this.LegpanelR6.addBox(-3.0F, 4.5F, -1.5F, 2, 3, 1);
/*     */     
/* 389 */     this.LegpanelR6.setTextureSize(128, 64);
/* 390 */     setRotation(this.LegpanelR6, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 392 */     this.LegpanelR5 = new ModelRenderer(this, 76, 42);
/* 393 */     this.LegpanelR5.addBox(-3.0F, 2.5F, -2.5F, 2, 3, 1);
/*     */     
/* 395 */     this.LegpanelR5.setTextureSize(128, 64);
/* 396 */     setRotation(this.LegpanelR5, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 398 */     this.LegpanelR4 = new ModelRenderer(this, 76, 38);
/* 399 */     this.LegpanelR4.addBox(-3.0F, 0.5F, -3.5F, 2, 3, 1);
/*     */     
/* 401 */     this.LegpanelR4.setTextureSize(128, 64);
/* 402 */     setRotation(this.LegpanelR4, -0.4363323F, 0.0F, 0.0F);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 412 */     this.SideclothL3 = new ModelRenderer(this, 116, 1);
/* 413 */     this.SideclothL3.addBox(-1.4F, 8.4F, -2.5F, 1, 3, 5);
/*     */     
/* 415 */     this.SideclothL3.setTextureSize(128, 64);
/* 416 */     setRotation(this.SideclothL3, 0.0F, 0.0F, -0.5235988F);
/*     */     
/* 418 */     this.Focipouch = new ModelRenderer(this, 100, 20);
/* 419 */     this.Focipouch.addBox(3.5F, 0.5F, -2.5F, 3, 6, 5);
/*     */     
/* 421 */     this.Focipouch.setTextureSize(128, 64);
/* 422 */     setRotation(this.Focipouch, 0.0F, 0.0F, -0.122173F);
/*     */     
/* 424 */     this.SideclothL1 = new ModelRenderer(this, 116, 42);
/* 425 */     this.SideclothL1.addBox(1.5F, 0.5F, -2.5F, 1, 5, 5);
/*     */     
/* 427 */     this.SideclothL1.setTextureSize(128, 64);
/* 428 */     setRotation(this.SideclothL1, 0.0F, 0.0F, -0.122173F);
/*     */     
/*     */ 
/*     */ 
/* 432 */     this.LegpanelL4 = new ModelRenderer(this, 76, 38);
/* 433 */     this.LegpanelL4.mirror = true;
/* 434 */     this.LegpanelL4.addBox(1.0F, 0.5F, -3.5F, 2, 3, 1);
/*     */     
/* 436 */     this.LegpanelL4.setTextureSize(128, 64);
/* 437 */     setRotation(this.LegpanelL4, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 439 */     this.LegpanelL5 = new ModelRenderer(this, 76, 42);
/* 440 */     this.LegpanelL5.mirror = true;
/* 441 */     this.LegpanelL5.addBox(1.0F, 2.5F, -2.5F, 2, 3, 1);
/*     */     
/* 443 */     this.LegpanelL5.setTextureSize(128, 64);
/* 444 */     setRotation(this.LegpanelL5, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 446 */     this.LegpanelL6 = new ModelRenderer(this, 82, 38);
/* 447 */     this.LegpanelL6.mirror = true;
/* 448 */     this.LegpanelL6.addBox(1.0F, 4.5F, -1.5F, 2, 3, 1);
/*     */     
/* 450 */     this.LegpanelL6.setTextureSize(128, 64);
/* 451 */     setRotation(this.LegpanelL6, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 453 */     this.SidepanelL1 = new ModelRenderer(this, 116, 25);
/* 454 */     this.SidepanelL1.addBox(1.5F, 0.5F, -2.5F, 1, 4, 5);
/*     */     
/* 456 */     this.SidepanelL1.setTextureSize(128, 64);
/* 457 */     setRotation(this.SidepanelL1, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 459 */     this.bipedHeadwear.cubeList.clear();
/* 460 */     this.bipedHead.cubeList.clear();
/* 461 */     this.bipedHead.addChild(this.Hood1);
/* 462 */     this.bipedHead.addChild(this.Hood2);
/* 463 */     this.bipedHead.addChild(this.Hood3);
/* 464 */     this.bipedHead.addChild(this.Hood4);
/*     */     
/* 466 */     this.bipedBody.cubeList.clear();
/* 467 */     this.bipedRightLeg.cubeList.clear();
/* 468 */     this.bipedLeftLeg.cubeList.clear();
/* 469 */     this.bipedBody.addChild(this.Mbelt);
/* 470 */     this.bipedBody.addChild(this.MbeltB);
/* 471 */     this.bipedBody.addChild(this.MbeltL);
/* 472 */     this.bipedBody.addChild(this.MbeltR);
/* 473 */     if (f < 1.0F) {
/* 474 */       this.bipedLeftLeg.addChild(this.Focipouch);
/* 475 */       this.bipedBody.addChild(this.FrontclothR1);
/* 476 */       this.bipedBody.addChild(this.FrontclothR2);
/* 477 */       this.bipedBody.addChild(this.FrontclothL1);
/* 478 */       this.bipedBody.addChild(this.FrontclothL2);
/*     */       
/* 480 */       this.bipedBody.addChild(this.ClothBackR1);
/* 481 */       this.bipedBody.addChild(this.ClothBackR2);
/* 482 */       this.bipedBody.addChild(this.ClothBackR3);
/*     */       
/* 484 */       this.bipedBody.addChild(this.ClothBackL1);
/* 485 */       this.bipedBody.addChild(this.ClothBackL2);
/* 486 */       this.bipedBody.addChild(this.ClothBackL3);
/*     */     } else {
/* 488 */       this.bipedBody.addChild(this.Chestplate);
/* 489 */       this.bipedBody.addChild(this.Chestthing);
/* 490 */       this.bipedBody.addChild(this.Scroll);
/* 491 */       this.bipedBody.addChild(this.Backplate);
/* 492 */       this.bipedBody.addChild(this.Book);
/* 493 */       this.bipedBody.addChild(this.ClothchestL);
/* 494 */       this.bipedBody.addChild(this.ClothchestR);
/*     */     }
/*     */     
/* 497 */     this.bipedRightArm.cubeList.clear();
/* 498 */     this.bipedRightArm.addChild(this.ShoulderR);
/* 499 */     this.bipedRightArm.addChild(this.RArm1);
/* 500 */     this.bipedRightArm.addChild(this.RArm2);
/* 501 */     this.bipedRightArm.addChild(this.RArm3);
/* 502 */     this.bipedRightArm.addChild(this.ShoulderplateTopR);
/* 503 */     this.bipedRightArm.addChild(this.ShoulderplateR1);
/* 504 */     this.bipedRightArm.addChild(this.ShoulderplateR2);
/* 505 */     this.bipedRightArm.addChild(this.ShoulderplateR3);
/*     */     
/* 507 */     this.bipedLeftArm.cubeList.clear();
/* 508 */     this.bipedLeftArm.addChild(this.ShoulderL);
/* 509 */     this.bipedLeftArm.addChild(this.LArm1);
/* 510 */     this.bipedLeftArm.addChild(this.LArm2);
/* 511 */     this.bipedLeftArm.addChild(this.LArm3);
/* 512 */     this.bipedLeftArm.addChild(this.ShoulderplateTopL);
/* 513 */     this.bipedLeftArm.addChild(this.ShoulderplateL1);
/* 514 */     this.bipedLeftArm.addChild(this.ShoulderplateL2);
/* 515 */     this.bipedLeftArm.addChild(this.ShoulderplateL3);
/*     */     
/*     */ 
/*     */ 
/* 519 */     this.bipedRightLeg.addChild(this.LegpanelR4);
/* 520 */     this.bipedRightLeg.addChild(this.LegpanelR5);
/* 521 */     this.bipedRightLeg.addChild(this.LegpanelR6);
/* 522 */     this.bipedRightLeg.addChild(this.SidepanelR1);
/*     */     
/* 524 */     this.bipedRightLeg.addChild(this.SideclothR1);
/* 525 */     this.bipedRightLeg.addChild(this.SideclothR2);
/* 526 */     this.bipedRightLeg.addChild(this.SideclothR3);
/*     */     
/*     */ 
/*     */ 
/* 530 */     this.bipedLeftLeg.addChild(this.LegpanelL4);
/* 531 */     this.bipedLeftLeg.addChild(this.LegpanelL5);
/* 532 */     this.bipedLeftLeg.addChild(this.LegpanelL6);
/* 533 */     this.bipedLeftLeg.addChild(this.SidepanelL1);
/*     */     
/* 535 */     this.bipedLeftLeg.addChild(this.SideclothL1);
/* 536 */     this.bipedLeftLeg.addChild(this.SideclothL2);
/* 537 */     this.bipedLeftLeg.addChild(this.SideclothL3);
/*     */   }
/*     */   
/*     */ 
/*     */   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 543 */     setRotationAngles(f, f1, f2, f3, f4, f5, entity);
/*     */     
/* 545 */     float a = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
/* 546 */     float b = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1;
/* 547 */     float c = Math.min(a, b);
/*     */     
/* 549 */     this.FrontclothR1.rotateAngleX = (this.FrontclothL1.rotateAngleX = c - 0.1047198F);
/* 550 */     this.FrontclothR2.rotateAngleX = (this.FrontclothL2.rotateAngleX = c - 0.3316126F);
/*     */     
/* 552 */     this.ClothBackR1.rotateAngleX = (this.ClothBackL1.rotateAngleX = -c + 0.1047198F);
/* 553 */     this.ClothBackR2.rotateAngleX = (this.ClothBackL2.rotateAngleX = this.ClothBackR3.rotateAngleX = this.ClothBackL3.rotateAngleX = -c + 0.2268928F);
/*     */     
/*     */ 
/* 556 */     if (this.isChild) {
/* 557 */       float f6 = 2.0F;
/* 558 */       GL11.glPushMatrix();
/* 559 */       GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
/* 560 */       GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
/*     */       
/* 562 */       this.bipedHead.render(f5);
/*     */       
/* 564 */       GL11.glPopMatrix();
/* 565 */       GL11.glPushMatrix();
/* 566 */       GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
/* 567 */       GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
/* 568 */       this.bipedBody.render(f5);
/* 569 */       this.bipedRightArm.render(f5);
/* 570 */       this.bipedLeftArm.render(f5);
/* 571 */       this.bipedRightLeg.render(f5);
/* 572 */       this.bipedLeftLeg.render(f5);
/*     */       
/* 574 */       this.bipedHeadwear.render(f5);
/*     */       
/* 576 */       GL11.glPopMatrix();
/*     */     } else {
/* 578 */       GL11.glPushMatrix();
/* 579 */       GL11.glScalef(1.01F, 1.01F, 1.01F);
/*     */       
/* 581 */       this.bipedHead.render(f5);
/* 582 */       GL11.glPopMatrix();
/* 583 */       this.bipedBody.render(f5);
/* 584 */       this.bipedRightArm.render(f5);
/* 585 */       this.bipedLeftArm.render(f5);
/* 586 */       this.bipedRightLeg.render(f5);
/* 587 */       this.bipedLeftLeg.render(f5);
/* 588 */       this.bipedHeadwear.render(f5);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setRotation(ModelRenderer model, float x, float y, float z) {
/* 593 */     model.rotateAngleX = x;
/* 594 */     model.rotateAngleY = y;
/* 595 */     model.rotateAngleZ = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\gear\ModelRobe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */