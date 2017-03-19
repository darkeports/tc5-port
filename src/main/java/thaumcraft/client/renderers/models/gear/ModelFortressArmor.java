/*     */ package thaumcraft.client.renderers.models.gear;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.common.items.armor.ItemFortressArmor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelFortressArmor
/*     */   extends ModelCustomArmor
/*     */ {
/*     */   ModelRenderer OrnamentL;
/*     */   ModelRenderer OrnamentL2;
/*     */   ModelRenderer OrnamentR;
/*     */   ModelRenderer OrnamentR2;
/*     */   ModelRenderer Helmet;
/*     */   ModelRenderer HelmetR;
/*     */   ModelRenderer HelmetL;
/*     */   ModelRenderer HelmetB;
/*     */   ModelRenderer capsthingy;
/*     */   ModelRenderer flapR;
/*     */   ModelRenderer flapL;
/*     */   ModelRenderer Gemornament;
/*     */   ModelRenderer Gem;
/*     */   ModelRenderer[] Mask;
/*     */   ModelRenderer Goggles;
/*     */   ModelRenderer BeltR;
/*     */   ModelRenderer Mbelt;
/*     */   ModelRenderer MbeltL;
/*     */   ModelRenderer MbeltR;
/*     */   ModelRenderer BeltL;
/*     */   ModelRenderer Chestplate;
/*     */   ModelRenderer Scroll;
/*     */   ModelRenderer Backplate;
/*     */   ModelRenderer Book;
/*     */   ModelRenderer ShoulderR;
/*     */   ModelRenderer GauntletR;
/*     */   ModelRenderer GauntletstrapR1;
/*     */   ModelRenderer GauntletstrapR2;
/*     */   ModelRenderer ShoulderplateRtop;
/*     */   ModelRenderer ShoulderplateR1;
/*     */   ModelRenderer ShoulderplateR2;
/*     */   ModelRenderer ShoulderplateR3;
/*     */   ModelRenderer ShoulderL;
/*     */   ModelRenderer GauntletL;
/*     */   ModelRenderer Gauntletstrapl1;
/*     */   ModelRenderer GauntletstrapL2;
/*     */   ModelRenderer ShoulderplateLtop;
/*     */   ModelRenderer ShoulderplateL1;
/*     */   ModelRenderer ShoulderplateL2;
/*     */   ModelRenderer ShoulderplateL3;
/*     */   ModelRenderer LegpanelR1;
/*     */   ModelRenderer LegpanelR2;
/*     */   ModelRenderer LegpanelR3;
/*     */   ModelRenderer LegpanelR4;
/*     */   ModelRenderer LegpanelR5;
/*     */   ModelRenderer LegpanelR6;
/*     */   ModelRenderer SidepanelR1;
/*     */   ModelRenderer SidepanelR2;
/*     */   ModelRenderer SidepanelR3;
/*     */   ModelRenderer BackpanelR1;
/*     */   ModelRenderer BackpanelR2;
/*     */   ModelRenderer BackpanelR3;
/*     */   ModelRenderer BackpanelL3;
/*     */   ModelRenderer LegpanelL1;
/*     */   ModelRenderer LegpanelL2;
/*     */   ModelRenderer LegpanelL3;
/*     */   ModelRenderer LegpanelL4;
/*     */   ModelRenderer LegpanelL5;
/*     */   ModelRenderer LegpanelL6;
/*     */   ModelRenderer SidepanelL1;
/*     */   ModelRenderer SidepanelL2;
/*     */   ModelRenderer SidepanelL3;
/*     */   ModelRenderer BackpanelL1;
/*     */   ModelRenderer BackpanelL2;
/*     */   
/*     */   public ModelFortressArmor(float f)
/*     */   {
/*  89 */     super(f, 0, 128, 64);
/*  90 */     this.textureWidth = 128;
/*  91 */     this.textureHeight = 64;
/*     */     
/*  93 */     this.Mask = new ModelRenderer[3];
/*  94 */     for (int a = 0; a < 3; a++) {
/*  95 */       this.Mask[a] = new ModelRenderer(this, 52 + a * 24, 2);
/*  96 */       this.Mask[a].addBox(-4.5F, -5.0F, -4.6F, 9, 5, 1);
/*  97 */       this.Mask[a].setRotationPoint(0.0F, 0.0F, 0.0F);
/*  98 */       this.Mask[a].setTextureSize(128, 64);
/*  99 */       setRotation(this.Mask[a], 0.0F, 0.0F, 0.0F);
/*     */     }
/*     */     
/* 102 */     this.Goggles = new ModelRenderer(this, 100, 18);
/* 103 */     this.Goggles.addBox(-4.5F, -5.0F, -4.25F, 9, 5, 1);
/* 104 */     this.Goggles.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 105 */     this.Goggles.setTextureSize(128, 64);
/* 106 */     setRotation(this.Goggles, 0.0F, 0.0F, 0.0F);
/*     */     
/* 108 */     this.OrnamentL = new ModelRenderer(this, 78, 8);
/* 109 */     this.OrnamentL.mirror = true;
/* 110 */     this.OrnamentL.addBox(1.5F, -9.0F, -6.5F, 2, 2, 1);
/* 111 */     this.OrnamentL.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 112 */     this.OrnamentL.setTextureSize(128, 64);
/*     */     
/* 114 */     setRotation(this.OrnamentL, -0.1396263F, 0.0F, 0.0F);
/* 115 */     this.OrnamentL2 = new ModelRenderer(this, 78, 8);
/* 116 */     this.OrnamentL2.mirror = true;
/* 117 */     this.OrnamentL2.addBox(3.5F, -10.0F, -6.5F, 1, 2, 1);
/* 118 */     this.OrnamentL2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 119 */     this.OrnamentL2.setTextureSize(128, 64);
/*     */     
/* 121 */     setRotation(this.OrnamentL2, -0.1396263F, 0.0F, 0.0F);
/* 122 */     this.OrnamentR = new ModelRenderer(this, 78, 8);
/* 123 */     this.OrnamentR.addBox(-3.5F, -9.0F, -6.5F, 2, 2, 1);
/* 124 */     this.OrnamentR.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 125 */     this.OrnamentR.setTextureSize(128, 64);
/* 126 */     setRotation(this.OrnamentR, -0.1396263F, 0.0F, 0.0F);
/* 127 */     this.OrnamentR2 = new ModelRenderer(this, 78, 8);
/* 128 */     this.OrnamentR2.addBox(-4.5F, -10.0F, -6.5F, 1, 2, 1);
/* 129 */     this.OrnamentR2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 130 */     this.OrnamentR2.setTextureSize(128, 64);
/* 131 */     setRotation(this.OrnamentR2, -0.1396263F, 0.0F, 0.0F);
/* 132 */     this.Helmet = new ModelRenderer(this, 41, 8);
/* 133 */     this.Helmet.addBox(-4.5F, -9.0F, -4.5F, 9, 4, 9);
/* 134 */     this.Helmet.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 135 */     this.Helmet.setTextureSize(128, 64);
/* 136 */     setRotation(this.Helmet, 0.0F, 0.0F, 0.0F);
/* 137 */     this.HelmetR = new ModelRenderer(this, 21, 13);
/* 138 */     this.HelmetR.addBox(-6.5F, -3.0F, -4.5F, 1, 5, 9);
/* 139 */     this.HelmetR.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 140 */     this.HelmetR.setTextureSize(128, 64);
/* 141 */     setRotation(this.HelmetR, 0.0F, 0.0F, 0.5235988F);
/* 142 */     this.HelmetL = new ModelRenderer(this, 21, 13);
/* 143 */     this.HelmetL.mirror = true;
/* 144 */     this.HelmetL.addBox(5.5F, -3.0F, -4.5F, 1, 5, 9);
/* 145 */     this.HelmetL.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 146 */     this.HelmetL.setTextureSize(128, 64);
/*     */     
/* 148 */     setRotation(this.HelmetL, 0.0F, 0.0F, -0.5235988F);
/* 149 */     this.HelmetB = new ModelRenderer(this, 41, 21);
/* 150 */     this.HelmetB.addBox(-4.5F, -3.0F, 5.5F, 9, 5, 1);
/* 151 */     this.HelmetB.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 152 */     this.HelmetB.setTextureSize(128, 64);
/* 153 */     setRotation(this.HelmetB, 0.5235988F, 0.0F, 0.0F);
/* 154 */     this.capsthingy = new ModelRenderer(this, 21, 0);
/* 155 */     this.capsthingy.addBox(-4.5F, -6.0F, -6.5F, 9, 1, 2);
/* 156 */     this.capsthingy.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 157 */     this.capsthingy.setTextureSize(128, 64);
/* 158 */     setRotation(this.capsthingy, 0.0F, 0.0F, 0.0F);
/*     */     
/* 160 */     this.flapR = new ModelRenderer(this, 59, 10);
/* 161 */     this.flapR.addBox(-10.0F, -2.0F, -1.0F, 3, 3, 1);
/* 162 */     this.flapR.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 163 */     this.flapR.setTextureSize(128, 64);
/* 164 */     setRotation(this.flapR, 0.0F, -0.5235988F, 0.5235988F);
/*     */     
/* 166 */     this.flapL = new ModelRenderer(this, 59, 10);
/* 167 */     this.flapL.mirror = true;
/* 168 */     this.flapL.addBox(7.0F, -2.0F, -1.0F, 3, 3, 1);
/* 169 */     this.flapL.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 170 */     this.flapL.setTextureSize(128, 64);
/* 171 */     setRotation(this.flapL, 0.0F, 0.5235988F, -0.5235988F);
/*     */     
/* 173 */     this.Gemornament = new ModelRenderer(this, 68, 11);
/* 174 */     this.Gemornament.addBox(-1.5F, -9.0F, -7.0F, 3, 3, 2);
/* 175 */     this.Gemornament.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 176 */     this.Gemornament.setTextureSize(128, 64);
/* 177 */     setRotation(this.Gemornament, -0.1396263F, 0.0F, 0.0F);
/* 178 */     this.Gem = new ModelRenderer(this, 72, 8);
/* 179 */     this.Gem.addBox(-1.0F, -8.5F, -7.5F, 2, 2, 1);
/* 180 */     this.Gem.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 181 */     this.Gem.setTextureSize(128, 64);
/* 182 */     setRotation(this.Gem, -0.1396263F, 0.0F, 0.0F);
/* 183 */     this.BeltR = new ModelRenderer(this, 76, 44);
/* 184 */     this.BeltR.addBox(-5.0F, 4.0F, -3.0F, 1, 3, 6);
/* 185 */     this.BeltR.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 186 */     this.BeltR.setTextureSize(128, 64);
/* 187 */     setRotation(this.BeltR, 0.0F, 0.0F, 0.0F);
/* 188 */     this.Mbelt = new ModelRenderer(this, 56, 55);
/* 189 */     this.Mbelt.addBox(-4.0F, 8.0F, -3.0F, 8, 4, 1);
/* 190 */     this.Mbelt.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 191 */     this.Mbelt.setTextureSize(128, 64);
/* 192 */     setRotation(this.Mbelt, 0.0F, 0.0F, 0.0F);
/* 193 */     this.MbeltL = new ModelRenderer(this, 76, 44);
/* 194 */     this.MbeltL.addBox(4.0F, 8.0F, -3.0F, 1, 3, 6);
/* 195 */     this.MbeltL.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 196 */     this.MbeltL.setTextureSize(128, 64);
/* 197 */     setRotation(this.MbeltL, 0.0F, 0.0F, 0.0F);
/* 198 */     this.MbeltR = new ModelRenderer(this, 76, 44);
/* 199 */     this.MbeltR.addBox(-5.0F, 8.0F, -3.0F, 1, 3, 6);
/* 200 */     this.MbeltR.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 201 */     this.MbeltR.setTextureSize(128, 64);
/* 202 */     setRotation(this.MbeltR, 0.0F, 0.0F, 0.0F);
/* 203 */     this.BeltL = new ModelRenderer(this, 76, 44);
/* 204 */     this.BeltL.addBox(4.0F, 4.0F, -3.0F, 1, 3, 6);
/* 205 */     this.BeltL.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 206 */     this.BeltL.setTextureSize(128, 64);
/* 207 */     setRotation(this.BeltL, 0.0F, 0.0F, 0.0F);
/* 208 */     this.Chestplate = new ModelRenderer(this, 56, 45);
/* 209 */     this.Chestplate.addBox(-4.0F, 1.0F, -4.0F, 8, 7, 2);
/* 210 */     this.Chestplate.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 211 */     this.Chestplate.setTextureSize(128, 64);
/* 212 */     setRotation(this.Chestplate, 0.0F, 0.0F, 0.0F);
/* 213 */     this.Scroll = new ModelRenderer(this, 34, 27);
/* 214 */     this.Scroll.addBox(-2.0F, 9.5F, 4.0F, 8, 3, 3);
/* 215 */     this.Scroll.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 216 */     this.Scroll.setTextureSize(128, 64);
/* 217 */     setRotation(this.Scroll, 0.0F, 0.0F, 0.1919862F);
/* 218 */     this.Backplate = new ModelRenderer(this, 36, 45);
/* 219 */     this.Backplate.addBox(-4.0F, 1.0F, 2.0F, 8, 11, 2);
/* 220 */     this.Backplate.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 221 */     this.Backplate.setTextureSize(128, 64);
/* 222 */     setRotation(this.Backplate, 0.0F, 0.0F, 0.0F);
/* 223 */     this.Book = new ModelRenderer(this, 100, 8);
/* 224 */     this.Book.addBox(1.0F, -0.3F, 4.0F, 5, 7, 2);
/* 225 */     this.Book.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 226 */     this.Book.setTextureSize(128, 64);
/* 227 */     setRotation(this.Book, 0.0F, 0.0F, 0.7679449F);
/* 228 */     this.ShoulderR = new ModelRenderer(this, 56, 35);
/* 229 */     this.ShoulderR.addBox(-3.5F, -2.5F, -2.5F, 5, 5, 5);
/* 230 */     this.ShoulderR.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 231 */     this.ShoulderR.setTextureSize(128, 64);
/* 232 */     setRotation(this.ShoulderR, 0.0F, 0.0F, 0.0F);
/* 233 */     this.GauntletR = new ModelRenderer(this, 100, 26);
/* 234 */     this.GauntletR.addBox(-3.5F, 3.5F, -2.5F, 2, 6, 5);
/* 235 */     this.GauntletR.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 236 */     this.GauntletR.setTextureSize(128, 64);
/* 237 */     setRotation(this.GauntletR, 0.0F, 0.0F, 0.0F);
/* 238 */     this.GauntletstrapR1 = new ModelRenderer(this, 84, 31);
/* 239 */     this.GauntletstrapR1.addBox(-1.5F, 3.5F, -2.5F, 3, 1, 5);
/* 240 */     this.GauntletstrapR1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 241 */     this.GauntletstrapR1.setTextureSize(128, 64);
/* 242 */     setRotation(this.GauntletstrapR1, 0.0F, 0.0F, 0.0F);
/* 243 */     this.GauntletstrapR2 = new ModelRenderer(this, 84, 31);
/* 244 */     this.GauntletstrapR2.addBox(-1.5F, 6.5F, -2.5F, 3, 1, 5);
/* 245 */     this.GauntletstrapR2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 246 */     this.GauntletstrapR2.setTextureSize(128, 64);
/* 247 */     setRotation(this.GauntletstrapR2, 0.0F, 0.0F, 0.0F);
/* 248 */     this.ShoulderplateRtop = new ModelRenderer(this, 110, 37);
/* 249 */     this.ShoulderplateRtop.addBox(-5.5F, -2.5F, -3.5F, 2, 1, 7);
/* 250 */     this.ShoulderplateRtop.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 251 */     this.ShoulderplateRtop.setTextureSize(128, 64);
/* 252 */     setRotation(this.ShoulderplateRtop, 0.0F, 0.0F, 0.4363323F);
/* 253 */     this.ShoulderplateR1 = new ModelRenderer(this, 110, 45);
/* 254 */     this.ShoulderplateR1.addBox(-4.5F, -1.5F, -3.5F, 1, 4, 7);
/* 255 */     this.ShoulderplateR1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 256 */     this.ShoulderplateR1.setTextureSize(128, 64);
/* 257 */     setRotation(this.ShoulderplateR1, 0.0F, 0.0F, 0.4363323F);
/* 258 */     this.ShoulderplateR2 = new ModelRenderer(this, 94, 45);
/* 259 */     this.ShoulderplateR2.addBox(-3.5F, 1.5F, -3.5F, 1, 3, 7);
/* 260 */     this.ShoulderplateR2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 261 */     this.ShoulderplateR2.setTextureSize(128, 64);
/* 262 */     setRotation(this.ShoulderplateR2, 0.0F, 0.0F, 0.4363323F);
/* 263 */     this.ShoulderplateR3 = new ModelRenderer(this, 94, 45);
/* 264 */     this.ShoulderplateR3.addBox(-2.5F, 3.5F, -3.5F, 1, 3, 7);
/* 265 */     this.ShoulderplateR3.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 266 */     this.ShoulderplateR3.setTextureSize(128, 64);
/* 267 */     setRotation(this.ShoulderplateR3, 0.0F, 0.0F, 0.4363323F);
/*     */     
/* 269 */     this.ShoulderL = new ModelRenderer(this, 56, 35);
/* 270 */     this.ShoulderL.mirror = true;
/* 271 */     this.ShoulderL.addBox(-1.5F, -2.5F, -2.5F, 5, 5, 5);
/* 272 */     this.ShoulderL.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 273 */     this.ShoulderL.setTextureSize(128, 64);
/* 274 */     setRotation(this.ShoulderL, 0.0F, 0.0F, 0.0F);
/*     */     
/* 276 */     this.GauntletL = new ModelRenderer(this, 114, 26);
/* 277 */     this.GauntletL.addBox(1.5F, 3.5F, -2.5F, 2, 6, 5);
/* 278 */     this.GauntletL.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 279 */     this.GauntletL.setTextureSize(128, 64);
/* 280 */     setRotation(this.GauntletL, 0.0F, 0.0F, 0.0F);
/*     */     
/* 282 */     this.Gauntletstrapl1 = new ModelRenderer(this, 84, 31);
/* 283 */     this.Gauntletstrapl1.mirror = true;
/* 284 */     this.Gauntletstrapl1.addBox(-1.5F, 3.5F, -2.5F, 3, 1, 5);
/* 285 */     this.Gauntletstrapl1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 286 */     this.Gauntletstrapl1.setTextureSize(128, 64);
/* 287 */     setRotation(this.Gauntletstrapl1, 0.0F, 0.0F, 0.0F);
/*     */     
/* 289 */     this.GauntletstrapL2 = new ModelRenderer(this, 84, 31);
/* 290 */     this.GauntletstrapL2.mirror = true;
/* 291 */     this.GauntletstrapL2.addBox(-1.5F, 6.5F, -2.5F, 3, 1, 5);
/* 292 */     this.GauntletstrapL2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 293 */     this.GauntletstrapL2.setTextureSize(128, 64);
/* 294 */     setRotation(this.GauntletstrapL2, 0.0F, 0.0F, 0.0F);
/*     */     
/* 296 */     this.ShoulderplateLtop = new ModelRenderer(this, 110, 37);
/* 297 */     this.ShoulderplateLtop.mirror = true;
/* 298 */     this.ShoulderplateLtop.addBox(3.5F, -2.5F, -3.5F, 2, 1, 7);
/* 299 */     this.ShoulderplateLtop.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 300 */     this.ShoulderplateLtop.setTextureSize(128, 64);
/* 301 */     setRotation(this.ShoulderplateLtop, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 303 */     this.ShoulderplateL1 = new ModelRenderer(this, 110, 45);
/* 304 */     this.ShoulderplateL1.mirror = true;
/* 305 */     this.ShoulderplateL1.addBox(3.5F, -1.5F, -3.5F, 1, 4, 7);
/* 306 */     this.ShoulderplateL1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 307 */     this.ShoulderplateL1.setTextureSize(128, 64);
/* 308 */     setRotation(this.ShoulderplateL1, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 310 */     this.ShoulderplateL2 = new ModelRenderer(this, 94, 45);
/* 311 */     this.ShoulderplateL2.mirror = true;
/* 312 */     this.ShoulderplateL2.addBox(2.5F, 1.5F, -3.5F, 1, 3, 7);
/* 313 */     this.ShoulderplateL2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 314 */     this.ShoulderplateL2.setTextureSize(128, 64);
/* 315 */     setRotation(this.ShoulderplateL2, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 317 */     this.ShoulderplateL3 = new ModelRenderer(this, 94, 45);
/* 318 */     this.ShoulderplateL3.mirror = true;
/* 319 */     this.ShoulderplateL3.addBox(1.5F, 3.5F, -3.5F, 1, 3, 7);
/* 320 */     this.ShoulderplateL3.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 321 */     this.ShoulderplateL3.setTextureSize(128, 64);
/* 322 */     setRotation(this.ShoulderplateL3, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 324 */     this.LegpanelR1 = new ModelRenderer(this, 0, 51);
/* 325 */     this.LegpanelR1.addBox(-1.0F, 0.5F, -3.5F, 3, 4, 1);
/* 326 */     this.LegpanelR1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 327 */     this.LegpanelR1.setTextureSize(128, 64);
/* 328 */     setRotation(this.LegpanelR1, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 330 */     this.LegpanelR2 = new ModelRenderer(this, 8, 51);
/* 331 */     this.LegpanelR2.addBox(-1.0F, 3.5F, -2.5F, 3, 4, 1);
/* 332 */     this.LegpanelR2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 333 */     this.LegpanelR2.setTextureSize(128, 64);
/* 334 */     setRotation(this.LegpanelR2, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 336 */     this.LegpanelR3 = new ModelRenderer(this, 0, 56);
/* 337 */     this.LegpanelR3.addBox(-1.0F, 6.5F, -1.5F, 3, 3, 1);
/* 338 */     this.LegpanelR3.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 339 */     this.LegpanelR3.setTextureSize(128, 64);
/* 340 */     setRotation(this.LegpanelR3, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 342 */     this.LegpanelR4 = new ModelRenderer(this, 0, 43);
/* 343 */     this.LegpanelR4.addBox(-3.0F, 0.5F, -3.5F, 2, 3, 1);
/* 344 */     this.LegpanelR4.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 345 */     this.LegpanelR4.setTextureSize(128, 64);
/* 346 */     setRotation(this.LegpanelR4, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 348 */     this.LegpanelR5 = new ModelRenderer(this, 0, 47);
/* 349 */     this.LegpanelR5.addBox(-3.0F, 2.5F, -2.5F, 2, 3, 1);
/* 350 */     this.LegpanelR5.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 351 */     this.LegpanelR5.setTextureSize(128, 64);
/* 352 */     setRotation(this.LegpanelR5, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 354 */     this.LegpanelR6 = new ModelRenderer(this, 6, 43);
/* 355 */     this.LegpanelR6.addBox(-3.0F, 4.5F, -1.5F, 2, 3, 1);
/* 356 */     this.LegpanelR6.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 357 */     this.LegpanelR6.setTextureSize(128, 64);
/* 358 */     setRotation(this.LegpanelR6, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 360 */     this.SidepanelR1 = new ModelRenderer(this, 0, 22);
/* 361 */     this.SidepanelR1.addBox(-2.5F, 0.5F, -2.5F, 1, 4, 5);
/* 362 */     this.SidepanelR1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 363 */     this.SidepanelR1.setTextureSize(128, 64);
/* 364 */     setRotation(this.SidepanelR1, 0.0F, 0.0F, 0.4363323F);
/*     */     
/* 366 */     this.SidepanelR2 = new ModelRenderer(this, 0, 31);
/* 367 */     this.SidepanelR2.addBox(-1.5F, 3.5F, -2.5F, 1, 3, 5);
/* 368 */     this.SidepanelR2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 369 */     this.SidepanelR2.setTextureSize(128, 64);
/* 370 */     setRotation(this.SidepanelR2, 0.0F, 0.0F, 0.4363323F);
/*     */     
/* 372 */     this.SidepanelR3 = new ModelRenderer(this, 12, 31);
/* 373 */     this.SidepanelR3.addBox(-0.5F, 5.5F, -2.5F, 1, 3, 5);
/* 374 */     this.SidepanelR3.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 375 */     this.SidepanelR3.setTextureSize(128, 64);
/* 376 */     setRotation(this.SidepanelR3, 0.0F, 0.0F, 0.4363323F);
/*     */     
/* 378 */     this.BackpanelR1 = new ModelRenderer(this, 0, 18);
/* 379 */     this.BackpanelR1.addBox(-3.0F, 0.5F, 2.5F, 5, 3, 1);
/* 380 */     this.BackpanelR1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 381 */     this.BackpanelR1.setTextureSize(128, 64);
/* 382 */     setRotation(this.BackpanelR1, 0.4363323F, 0.0F, 0.0F);
/*     */     
/* 384 */     this.BackpanelR2 = new ModelRenderer(this, 0, 18);
/* 385 */     this.BackpanelR2.addBox(-3.0F, 2.5F, 1.5F, 5, 3, 1);
/* 386 */     this.BackpanelR2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 387 */     this.BackpanelR2.setTextureSize(128, 64);
/* 388 */     setRotation(this.BackpanelR2, 0.4363323F, 0.0F, 0.0F);
/*     */     
/* 390 */     this.BackpanelR3 = new ModelRenderer(this, 0, 18);
/* 391 */     this.BackpanelR3.addBox(-3.0F, 4.5F, 0.5F, 5, 3, 1);
/* 392 */     this.BackpanelR3.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 393 */     this.BackpanelR3.setTextureSize(128, 64);
/* 394 */     setRotation(this.BackpanelR3, 0.4363323F, 0.0F, 0.0F);
/*     */     
/* 396 */     this.BackpanelL3 = new ModelRenderer(this, 0, 18);
/* 397 */     this.BackpanelL3.mirror = true;
/* 398 */     this.BackpanelL3.addBox(-2.0F, 4.5F, 0.5F, 5, 3, 1);
/* 399 */     this.BackpanelL3.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 400 */     this.BackpanelL3.setTextureSize(128, 64);
/* 401 */     setRotation(this.BackpanelL3, 0.4363323F, 0.0F, 0.0F);
/*     */     
/* 403 */     this.LegpanelL1 = new ModelRenderer(this, 0, 51);
/* 404 */     this.LegpanelL1.mirror = true;
/* 405 */     this.LegpanelL1.addBox(-2.0F, 0.5F, -3.5F, 3, 4, 1);
/* 406 */     this.LegpanelL1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 407 */     this.LegpanelL1.setTextureSize(128, 64);
/* 408 */     setRotation(this.LegpanelL1, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 410 */     this.LegpanelL2 = new ModelRenderer(this, 8, 51);
/* 411 */     this.LegpanelL2.mirror = true;
/* 412 */     this.LegpanelL2.addBox(-2.0F, 3.5F, -2.5F, 3, 4, 1);
/* 413 */     this.LegpanelL2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 414 */     this.LegpanelL2.setTextureSize(128, 64);
/* 415 */     setRotation(this.LegpanelL2, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 417 */     this.LegpanelL3 = new ModelRenderer(this, 0, 56);
/* 418 */     this.LegpanelL3.mirror = true;
/* 419 */     this.LegpanelL3.addBox(-2.0F, 6.5F, -1.5F, 3, 3, 1);
/* 420 */     this.LegpanelL3.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 421 */     this.LegpanelL3.setTextureSize(128, 64);
/* 422 */     setRotation(this.LegpanelL3, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 424 */     this.LegpanelL4 = new ModelRenderer(this, 0, 43);
/* 425 */     this.LegpanelL4.mirror = true;
/* 426 */     this.LegpanelL4.addBox(1.0F, 0.5F, -3.5F, 2, 3, 1);
/* 427 */     this.LegpanelL4.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 428 */     this.LegpanelL4.setTextureSize(128, 64);
/* 429 */     setRotation(this.LegpanelL4, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 431 */     this.LegpanelL5 = new ModelRenderer(this, 0, 47);
/* 432 */     this.LegpanelL5.mirror = true;
/* 433 */     this.LegpanelL5.addBox(1.0F, 2.5F, -2.5F, 2, 3, 1);
/* 434 */     this.LegpanelL5.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 435 */     this.LegpanelL5.setTextureSize(128, 64);
/* 436 */     setRotation(this.LegpanelL5, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 438 */     this.LegpanelL6 = new ModelRenderer(this, 6, 43);
/* 439 */     this.LegpanelL6.mirror = true;
/* 440 */     this.LegpanelL6.addBox(1.0F, 4.5F, -1.5F, 2, 3, 1);
/* 441 */     this.LegpanelL6.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 442 */     this.LegpanelL6.setTextureSize(128, 64);
/* 443 */     setRotation(this.LegpanelL6, -0.4363323F, 0.0F, 0.0F);
/*     */     
/* 445 */     this.SidepanelL1 = new ModelRenderer(this, 0, 22);
/* 446 */     this.SidepanelL1.mirror = true;
/* 447 */     this.SidepanelL1.addBox(1.5F, 0.5F, -2.5F, 1, 4, 5);
/* 448 */     this.SidepanelL1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 449 */     this.SidepanelL1.setTextureSize(128, 64);
/* 450 */     setRotation(this.SidepanelL1, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 452 */     this.SidepanelL2 = new ModelRenderer(this, 0, 31);
/* 453 */     this.SidepanelL2.mirror = true;
/* 454 */     this.SidepanelL2.addBox(0.5F, 3.5F, -2.5F, 1, 3, 5);
/* 455 */     this.SidepanelL2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 456 */     this.SidepanelL2.setTextureSize(128, 64);
/* 457 */     setRotation(this.SidepanelL2, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 459 */     this.SidepanelL3 = new ModelRenderer(this, 12, 31);
/* 460 */     this.SidepanelL3.mirror = true;
/* 461 */     this.SidepanelL3.addBox(-0.5F, 5.5F, -2.5F, 1, 3, 5);
/* 462 */     this.SidepanelL3.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 463 */     this.SidepanelL3.setTextureSize(128, 64);
/* 464 */     setRotation(this.SidepanelL3, 0.0F, 0.0F, -0.4363323F);
/*     */     
/* 466 */     this.BackpanelL1 = new ModelRenderer(this, 0, 18);
/* 467 */     this.BackpanelL1.mirror = true;
/* 468 */     this.BackpanelL1.addBox(-2.0F, 0.5F, 2.5F, 5, 3, 1);
/* 469 */     this.BackpanelL1.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 470 */     this.BackpanelL1.setTextureSize(128, 64);
/* 471 */     setRotation(this.BackpanelL1, 0.4363323F, 0.0F, 0.0F);
/*     */     
/* 473 */     this.BackpanelL2 = new ModelRenderer(this, 0, 18);
/* 474 */     this.BackpanelL2.mirror = true;
/* 475 */     this.BackpanelL2.addBox(-2.0F, 2.5F, 1.5F, 5, 3, 1);
/* 476 */     this.BackpanelL2.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 477 */     this.BackpanelL2.setTextureSize(128, 64);
/* 478 */     setRotation(this.BackpanelL2, 0.4363323F, 0.0F, 0.0F);
/*     */     
/* 480 */     this.bipedHeadwear.cubeList.clear();
/* 481 */     this.bipedHead.cubeList.clear();
/* 482 */     this.bipedHead.addChild(this.OrnamentL);
/* 483 */     this.bipedHead.addChild(this.OrnamentL2);
/* 484 */     this.bipedHead.addChild(this.OrnamentR);
/* 485 */     this.bipedHead.addChild(this.OrnamentR2);
/* 486 */     this.bipedHead.addChild(this.Helmet);
/* 487 */     this.bipedHead.addChild(this.HelmetR);
/* 488 */     this.bipedHead.addChild(this.HelmetL);
/* 489 */     this.bipedHead.addChild(this.HelmetB);
/* 490 */     this.bipedHead.addChild(this.capsthingy);
/* 491 */     this.bipedHead.addChild(this.flapR);
/* 492 */     this.bipedHead.addChild(this.flapL);
/* 493 */     this.bipedHead.addChild(this.Gemornament);
/* 494 */     this.bipedHead.addChild(this.Gem);
/* 495 */     this.bipedHead.addChild(this.Goggles);
/* 496 */     this.bipedHead.addChild(this.Mask[0]);
/* 497 */     this.bipedHead.addChild(this.Mask[1]);
/* 498 */     this.bipedHead.addChild(this.Mask[2]);
/*     */     
/* 500 */     this.bipedBody.cubeList.clear();
/* 501 */     if (f < 1.0F) {
/* 502 */       this.bipedBody.addChild(this.Mbelt);
/* 503 */       this.bipedBody.addChild(this.MbeltL);
/* 504 */       this.bipedBody.addChild(this.MbeltR);
/*     */     } else {
/* 506 */       this.bipedBody.addChild(this.BeltR);
/* 507 */       this.bipedBody.addChild(this.BeltL);
/* 508 */       this.bipedBody.addChild(this.Chestplate);
/* 509 */       this.bipedBody.addChild(this.Scroll);
/* 510 */       this.bipedBody.addChild(this.Backplate);
/* 511 */       this.bipedBody.addChild(this.Book);
/*     */     }
/*     */     
/* 514 */     this.bipedRightArm.cubeList.clear();
/* 515 */     this.bipedRightArm.addChild(this.ShoulderR);
/* 516 */     this.bipedRightArm.addChild(this.GauntletR);
/* 517 */     this.bipedRightArm.addChild(this.GauntletstrapR1);
/* 518 */     this.bipedRightArm.addChild(this.GauntletstrapR2);
/* 519 */     this.bipedRightArm.addChild(this.ShoulderplateRtop);
/* 520 */     this.bipedRightArm.addChild(this.ShoulderplateR1);
/* 521 */     this.bipedRightArm.addChild(this.ShoulderplateR2);
/* 522 */     this.bipedRightArm.addChild(this.ShoulderplateR3);
/*     */     
/* 524 */     this.bipedLeftArm.cubeList.clear();
/* 525 */     this.bipedLeftArm.addChild(this.ShoulderL);
/* 526 */     this.bipedLeftArm.addChild(this.GauntletL);
/* 527 */     this.bipedLeftArm.addChild(this.Gauntletstrapl1);
/* 528 */     this.bipedLeftArm.addChild(this.GauntletstrapL2);
/* 529 */     this.bipedLeftArm.addChild(this.ShoulderplateLtop);
/* 530 */     this.bipedLeftArm.addChild(this.ShoulderplateL1);
/* 531 */     this.bipedLeftArm.addChild(this.ShoulderplateL2);
/* 532 */     this.bipedLeftArm.addChild(this.ShoulderplateL3);
/*     */     
/* 534 */     this.bipedRightLeg.cubeList.clear();
/* 535 */     this.bipedRightLeg.addChild(this.LegpanelR1);
/* 536 */     this.bipedRightLeg.addChild(this.LegpanelR2);
/* 537 */     this.bipedRightLeg.addChild(this.LegpanelR3);
/* 538 */     this.bipedRightLeg.addChild(this.LegpanelR4);
/* 539 */     this.bipedRightLeg.addChild(this.LegpanelR5);
/* 540 */     this.bipedRightLeg.addChild(this.LegpanelR6);
/* 541 */     this.bipedRightLeg.addChild(this.SidepanelR1);
/* 542 */     this.bipedRightLeg.addChild(this.SidepanelR2);
/* 543 */     this.bipedRightLeg.addChild(this.SidepanelR3);
/* 544 */     this.bipedRightLeg.addChild(this.BackpanelR1);
/* 545 */     this.bipedRightLeg.addChild(this.BackpanelR2);
/* 546 */     this.bipedRightLeg.addChild(this.BackpanelR3);
/*     */     
/* 548 */     this.bipedLeftLeg.cubeList.clear();
/* 549 */     this.bipedLeftLeg.addChild(this.BackpanelL3);
/* 550 */     this.bipedLeftLeg.addChild(this.LegpanelL1);
/* 551 */     this.bipedLeftLeg.addChild(this.LegpanelL2);
/* 552 */     this.bipedLeftLeg.addChild(this.LegpanelL3);
/* 553 */     this.bipedLeftLeg.addChild(this.LegpanelL4);
/* 554 */     this.bipedLeftLeg.addChild(this.LegpanelL5);
/* 555 */     this.bipedLeftLeg.addChild(this.LegpanelL6);
/* 556 */     this.bipedLeftLeg.addChild(this.SidepanelL1);
/* 557 */     this.bipedLeftLeg.addChild(this.SidepanelL2);
/* 558 */     this.bipedLeftLeg.addChild(this.SidepanelL3);
/* 559 */     this.bipedLeftLeg.addChild(this.BackpanelL1);
/* 560 */     this.bipedLeftLeg.addChild(this.BackpanelL2);
/*     */   }
/*     */   
/*     */ 
/* 564 */   private static HashMap<Integer, Integer> hasSet = new HashMap();
/* 565 */   private static HashMap<Integer, Integer> hasMask = new HashMap();
/* 566 */   private static HashMap<Integer, Boolean> hasGoggles = new HashMap();
/*     */   
/*     */   private void checkSet(Entity entity) {
/* 569 */     if (((entity instanceof EntityLivingBase)) && (entity.ticksExisted % 20 == 0)) {
/* 570 */       int set = 0;
/* 571 */       for (int a = 1; a < 4; a++) {
/* 572 */         ItemStack piece = ((EntityLivingBase)entity).getEquipmentInSlot(a + 1);
/* 573 */         if ((piece != null) && ((piece.getItem() instanceof ItemFortressArmor))) {
/* 574 */           set++;
/* 575 */           if (a == 3) {
/* 576 */             if ((piece.hasTagCompound()) && (piece.getTagCompound().hasKey("mask"))) {
/* 577 */               hasMask.put(Integer.valueOf(entity.getEntityId()), Integer.valueOf(piece.getTagCompound().getInteger("mask")));
/*     */             } else {
/* 579 */               hasMask.remove(Integer.valueOf(entity.getEntityId()));
/*     */             }
/* 581 */             if ((piece.hasTagCompound()) && (piece.getTagCompound().hasKey("goggles"))) {
/* 582 */               hasGoggles.put(Integer.valueOf(entity.getEntityId()), Boolean.valueOf(true));
/*     */             } else {
/* 584 */               hasGoggles.remove(Integer.valueOf(entity.getEntityId()));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 591 */       if (set > 0) {
/* 592 */         hasSet.put(Integer.valueOf(entity.getEntityId()), Integer.valueOf(set));
/*     */       } else {
/* 594 */         hasSet.remove(Integer.valueOf(entity.getEntityId()));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 605 */     checkSet(entity);
/* 606 */     int set = hasSet.containsKey(Integer.valueOf(entity.getEntityId())) ? ((Integer)hasSet.get(Integer.valueOf(entity.getEntityId()))).intValue() : -1;
/* 607 */     int mask = hasMask.containsKey(Integer.valueOf(entity.getEntityId())) ? ((Integer)hasMask.get(Integer.valueOf(entity.getEntityId()))).intValue() : -1;
/*     */     
/* 609 */     this.Goggles.isHidden = (!hasGoggles.containsKey(Integer.valueOf(entity.getEntityId())));
/*     */     
/*     */ 
/* 612 */     for (int a = 0; a < 3; a++) {
/* 613 */       if (mask == a) {
/* 614 */         this.Mask[a].isHidden = false;
/*     */       } else {
/* 616 */         this.Mask[a].isHidden = true;
/*     */       }
/*     */     }
/*     */     
/* 620 */     this.Scroll.isHidden = (set < 3);
/* 621 */     this.Book.isHidden = (set < 2);
/* 622 */     this.OrnamentL.isHidden = (set < 3);
/* 623 */     this.OrnamentL2.isHidden = (set < 3);
/* 624 */     this.OrnamentR.isHidden = (set < 3);
/* 625 */     this.OrnamentR2.isHidden = (set < 3);
/* 626 */     this.Gemornament.isHidden = (set < 3);
/* 627 */     this.Gem.isHidden = (set < 3);
/* 628 */     this.flapL.isHidden = (set < 2);
/* 629 */     this.flapR.isHidden = (set < 2);
/* 630 */     this.ShoulderplateLtop.isHidden = (set < 2);
/* 631 */     this.ShoulderplateL1.isHidden = (set < 2);
/* 632 */     this.ShoulderplateL2.isHidden = (set < 3);
/* 633 */     this.ShoulderplateL3.isHidden = (set < 3);
/* 634 */     this.ShoulderplateRtop.isHidden = (set < 2);
/* 635 */     this.ShoulderplateR1.isHidden = (set < 2);
/* 636 */     this.ShoulderplateR2.isHidden = (set < 3);
/* 637 */     this.ShoulderplateR3.isHidden = (set < 3);
/* 638 */     this.SidepanelR2.isHidden = (set < 2);
/* 639 */     this.SidepanelL2.isHidden = (set < 2);
/* 640 */     this.SidepanelR3.isHidden = (set < 3);
/* 641 */     this.SidepanelL3.isHidden = (set < 3);
/*     */     
/* 643 */     setRotationAngles(f, f1, f2, f3, f4, f5, entity);
/*     */     
/*     */ 
/* 646 */     if (this.isChild)
/*     */     {
/* 648 */       float f6 = 2.0F;
/* 649 */       GL11.glPushMatrix();
/* 650 */       GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
/* 651 */       GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
/* 652 */       this.bipedHead.render(f5);
/* 653 */       GL11.glPopMatrix();
/* 654 */       GL11.glPushMatrix();
/* 655 */       GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
/* 656 */       GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
/* 657 */       this.bipedBody.render(f5);
/* 658 */       this.bipedRightArm.render(f5);
/* 659 */       this.bipedLeftArm.render(f5);
/* 660 */       this.bipedRightLeg.render(f5);
/* 661 */       this.bipedLeftLeg.render(f5);
/*     */       
/* 663 */       this.bipedHeadwear.render(f5);
/*     */       
/* 665 */       GL11.glPopMatrix();
/*     */     }
/*     */     else
/*     */     {
/* 669 */       GL11.glPushMatrix();
/* 670 */       GL11.glScalef(1.01F, 1.01F, 1.01F);
/* 671 */       this.bipedHead.render(f5);
/* 672 */       GL11.glPopMatrix();
/* 673 */       this.bipedBody.render(f5);
/* 674 */       this.bipedRightArm.render(f5);
/* 675 */       this.bipedLeftArm.render(f5);
/* 676 */       this.bipedRightLeg.render(f5);
/* 677 */       this.bipedLeftLeg.render(f5);
/* 678 */       this.bipedHeadwear.render(f5);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*     */   {
/* 684 */     model.rotateAngleX = x;
/* 685 */     model.rotateAngleY = y;
/* 686 */     model.rotateAngleZ = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\gear\ModelFortressArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */