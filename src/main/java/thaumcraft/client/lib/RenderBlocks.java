/*     */ package thaumcraft.client.lib;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */ public class RenderBlocks
/*     */ {
/*     */   public IBlockAccess blockAccess;
/*     */   public boolean flipTexture;
/*     */   public boolean field_152631_f;
/*     */   public boolean renderAllFaces;
/*  18 */   public boolean useInventoryTint = true;
/*  19 */   public boolean renderFromInside = false;
/*     */   
/*     */   public double renderMinX;
/*     */   
/*     */   public double renderMaxX;
/*     */   
/*     */   public double renderMinY;
/*     */   
/*     */   public double renderMaxY;
/*     */   
/*     */   public double renderMinZ;
/*     */   
/*     */   public double renderMaxZ;
/*     */   
/*     */   public boolean lockBlockBounds;
/*     */   
/*     */   public boolean partialRenderBounds;
/*     */   
/*     */   public final Minecraft minecraftRB;
/*     */   
/*     */   public int uvRotateEast;
/*     */   
/*     */   public int uvRotateWest;
/*     */   
/*     */   public int uvRotateSouth;
/*     */   
/*     */   public int uvRotateNorth;
/*     */   
/*     */   public int uvRotateTop;
/*     */   
/*     */   public int uvRotateBottom;
/*     */   
/*     */   public float aoLightValueScratchXYZNNN;
/*     */   
/*     */   public float aoLightValueScratchXYNN;
/*     */   
/*     */   public float aoLightValueScratchXYZNNP;
/*     */   
/*     */   public float aoLightValueScratchYZNN;
/*     */   
/*     */   public float aoLightValueScratchYZNP;
/*     */   
/*     */   public float aoLightValueScratchXYZPNN;
/*     */   
/*     */   public float aoLightValueScratchXYPN;
/*     */   
/*     */   public float aoLightValueScratchXYZPNP;
/*     */   
/*     */   public float aoLightValueScratchXYZNPN;
/*     */   
/*     */   public float aoLightValueScratchXYNP;
/*     */   
/*     */   public float aoLightValueScratchXYZNPP;
/*     */   
/*     */   public float aoLightValueScratchYZPN;
/*     */   
/*     */   public float aoLightValueScratchXYZPPN;
/*     */   
/*     */   public float aoLightValueScratchXYPP;
/*     */   
/*     */   public float aoLightValueScratchYZPP;
/*     */   
/*     */   public float aoLightValueScratchXYZPPP;
/*     */   
/*     */   public float aoLightValueScratchXZNN;
/*     */   
/*     */   public float aoLightValueScratchXZPN;
/*     */   
/*     */   public float aoLightValueScratchXZNP;
/*     */   
/*     */   public float aoLightValueScratchXZPP;
/*     */   
/*     */   public int aoBrightnessXYZNNN;
/*     */   
/*     */   public int aoBrightnessXYNN;
/*     */   
/*     */   public int aoBrightnessXYZNNP;
/*     */   
/*     */   public int aoBrightnessYZNN;
/*     */   
/*     */   public int aoBrightnessYZNP;
/*     */   
/*     */   public int aoBrightnessXYZPNN;
/*     */   
/*     */   public int aoBrightnessXYPN;
/*     */   public int aoBrightnessXYZPNP;
/*     */   public int aoBrightnessXYZNPN;
/*     */   public int aoBrightnessXYNP;
/*     */   public int aoBrightnessXYZNPP;
/*     */   public int aoBrightnessYZPN;
/*     */   public int aoBrightnessXYZPPN;
/*     */   public int aoBrightnessXYPP;
/*     */   public int aoBrightnessYZPP;
/*     */   public int aoBrightnessXYZPPP;
/*     */   public int aoBrightnessXZNN;
/*     */   public int aoBrightnessXZPN;
/*     */   public int aoBrightnessXZNP;
/*     */   public int aoBrightnessXZPP;
/*     */   public int brightnessTopLeft;
/*     */   public int brightnessBottomLeft;
/*     */   public int brightnessBottomRight;
/*     */   public int brightnessTopRight;
/*     */   public float colorRedTopLeft;
/*     */   public float colorRedBottomLeft;
/*     */   public float colorRedBottomRight;
/*     */   public float colorRedTopRight;
/*     */   public float colorGreenTopLeft;
/*     */   public float colorGreenBottomLeft;
/*     */   public float colorGreenBottomRight;
/*     */   public float colorGreenTopRight;
/*     */   public float colorBlueTopLeft;
/*     */   public float colorBlueBottomLeft;
/*     */   public float colorBlueBottomRight;
/*     */   public float colorBlueTopRight;
/*     */   private static final String __OBFID = "CL_00000940";
/*     */   private static RenderBlocks instance;
/*     */   
/*     */   public RenderBlocks(IBlockAccess p_i1251_1_)
/*     */   {
/* 138 */     this.blockAccess = p_i1251_1_;
/* 139 */     this.field_152631_f = false;
/* 140 */     this.flipTexture = false;
/* 141 */     this.minecraftRB = Minecraft.getMinecraft();
/*     */   }
/*     */   
/*     */   public RenderBlocks()
/*     */   {
/* 146 */     this.minecraftRB = Minecraft.getMinecraft();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setRenderBounds(double p_147782_1_, double p_147782_3_, double p_147782_5_, double p_147782_7_, double p_147782_9_, double p_147782_11_)
/*     */   {
/* 152 */     if (!this.lockBlockBounds)
/*     */     {
/* 154 */       this.renderMinX = p_147782_1_;
/* 155 */       this.renderMaxX = p_147782_7_;
/* 156 */       this.renderMinY = p_147782_3_;
/* 157 */       this.renderMaxY = p_147782_9_;
/* 158 */       this.renderMinZ = p_147782_5_;
/* 159 */       this.renderMaxZ = p_147782_11_;
/* 160 */       this.partialRenderBounds = ((this.minecraftRB.gameSettings.ambientOcclusion >= 2) && ((this.renderMinX > 0.0D) || (this.renderMaxX < 1.0D) || (this.renderMinY > 0.0D) || (this.renderMaxY < 1.0D) || (this.renderMinZ > 0.0D) || (this.renderMaxZ < 1.0D)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRenderBoundsFromBlock(Block p_147775_1_)
/*     */   {
/* 169 */     if (!this.lockBlockBounds)
/*     */     {
/* 171 */       this.renderMinX = p_147775_1_.getBlockBoundsMinX();
/* 172 */       this.renderMaxX = p_147775_1_.getBlockBoundsMaxX();
/* 173 */       this.renderMinY = p_147775_1_.getBlockBoundsMinY();
/* 174 */       this.renderMaxY = p_147775_1_.getBlockBoundsMaxY();
/* 175 */       this.renderMinZ = p_147775_1_.getBlockBoundsMinZ();
/* 176 */       this.renderMaxZ = p_147775_1_.getBlockBoundsMaxZ();
/* 177 */       this.partialRenderBounds = ((this.minecraftRB.gameSettings.ambientOcclusion >= 2) && ((this.renderMinX > 0.0D) || (this.renderMaxX < 1.0D) || (this.renderMinY > 0.0D) || (this.renderMaxY < 1.0D) || (this.renderMinZ > 0.0D) || (this.renderMaxZ < 1.0D)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void overrideBlockBounds(double p_147770_1_, double p_147770_3_, double p_147770_5_, double p_147770_7_, double p_147770_9_, double p_147770_11_)
/*     */   {
/* 187 */     this.renderMinX = p_147770_1_;
/* 188 */     this.renderMaxX = p_147770_7_;
/* 189 */     this.renderMinY = p_147770_3_;
/* 190 */     this.renderMaxY = p_147770_9_;
/* 191 */     this.renderMinZ = p_147770_5_;
/* 192 */     this.renderMaxZ = p_147770_11_;
/* 193 */     this.lockBlockBounds = true;
/* 194 */     this.partialRenderBounds = ((this.minecraftRB.gameSettings.ambientOcclusion >= 2) && ((this.renderMinX > 0.0D) || (this.renderMaxX < 1.0D) || (this.renderMinY > 0.0D) || (this.renderMaxY < 1.0D) || (this.renderMinZ > 0.0D) || (this.renderMaxZ < 1.0D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderFaceYNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, TextureAtlasSprite p_147768_8_, float red, float green, float blue, int bright)
/*     */   {
/* 203 */     Tessellator tessellator = Tessellator.getInstance();
/*     */     
/* 205 */     double d3 = p_147768_8_.getInterpolatedU(this.renderMinX * 16.0D);
/* 206 */     double d4 = p_147768_8_.getInterpolatedU(this.renderMaxX * 16.0D);
/* 207 */     double d5 = p_147768_8_.getInterpolatedV(this.renderMinZ * 16.0D);
/* 208 */     double d6 = p_147768_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
/*     */     
/* 210 */     if ((this.renderMinX < 0.0D) || (this.renderMaxX > 1.0D))
/*     */     {
/* 212 */       d3 = p_147768_8_.getMinU();
/* 213 */       d4 = p_147768_8_.getMaxU();
/*     */     }
/*     */     
/* 216 */     if ((this.renderMinZ < 0.0D) || (this.renderMaxZ > 1.0D))
/*     */     {
/* 218 */       d5 = p_147768_8_.getMinV();
/* 219 */       d6 = p_147768_8_.getMaxV();
/*     */     }
/*     */     
/* 222 */     double d7 = d4;
/* 223 */     double d8 = d3;
/* 224 */     double d9 = d5;
/* 225 */     double d10 = d6;
/*     */     
/* 227 */     if (this.uvRotateBottom == 2)
/*     */     {
/* 229 */       d3 = p_147768_8_.getInterpolatedU(this.renderMinZ * 16.0D);
/* 230 */       d5 = p_147768_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
/* 231 */       d4 = p_147768_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
/* 232 */       d6 = p_147768_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
/* 233 */       d9 = d5;
/* 234 */       d10 = d6;
/* 235 */       d7 = d3;
/* 236 */       d8 = d4;
/* 237 */       d5 = d6;
/* 238 */       d6 = d9;
/*     */     }
/* 240 */     else if (this.uvRotateBottom == 1)
/*     */     {
/* 242 */       d3 = p_147768_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
/* 243 */       d5 = p_147768_8_.getInterpolatedV(this.renderMinX * 16.0D);
/* 244 */       d4 = p_147768_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
/* 245 */       d6 = p_147768_8_.getInterpolatedV(this.renderMaxX * 16.0D);
/* 246 */       d7 = d4;
/* 247 */       d8 = d3;
/* 248 */       d3 = d4;
/* 249 */       d4 = d8;
/* 250 */       d9 = d6;
/* 251 */       d10 = d5;
/*     */     }
/* 253 */     else if (this.uvRotateBottom == 3)
/*     */     {
/* 255 */       d3 = p_147768_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
/* 256 */       d4 = p_147768_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
/* 257 */       d5 = p_147768_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
/* 258 */       d6 = p_147768_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
/* 259 */       d7 = d4;
/* 260 */       d8 = d3;
/* 261 */       d9 = d5;
/* 262 */       d10 = d6;
/*     */     }
/*     */     
/* 265 */     double d11 = p_147768_2_ + this.renderMinX;
/* 266 */     double d12 = p_147768_2_ + this.renderMaxX;
/* 267 */     double d13 = p_147768_4_ + this.renderMinY;
/* 268 */     double d14 = p_147768_6_ + this.renderMinZ;
/* 269 */     double d15 = p_147768_6_ + this.renderMaxZ;
/*     */     
/* 271 */     if (this.renderFromInside)
/*     */     {
/* 273 */       d11 = p_147768_2_ + this.renderMaxX;
/* 274 */       d12 = p_147768_2_ + this.renderMinX;
/*     */     }
/*     */     
/* 277 */     int i = bright;
/* 278 */     int j = i >> 16 & 0xFFFF;
/* 279 */     int k = i & 0xFFFF;
/*     */     
/* 281 */     tessellator.getWorldRenderer().pos(d11, d13, d15).tex(d8, d10).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 282 */     tessellator.getWorldRenderer().pos(d11, d13, d14).tex(d3, d5).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 283 */     tessellator.getWorldRenderer().pos(d12, d13, d14).tex(d7, d9).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 284 */     tessellator.getWorldRenderer().pos(d12, d13, d15).tex(d4, d6).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderFaceYPos(Block p_147806_1_, double p_147806_2_, double p_147806_4_, double p_147806_6_, TextureAtlasSprite p_147806_8_, float red, float green, float blue, int bright)
/*     */   {
/* 292 */     Tessellator tessellator = Tessellator.getInstance();
/*     */     
/*     */ 
/* 295 */     double d3 = p_147806_8_.getInterpolatedU(this.renderMinX * 16.0D);
/* 296 */     double d4 = p_147806_8_.getInterpolatedU(this.renderMaxX * 16.0D);
/* 297 */     double d5 = p_147806_8_.getInterpolatedV(this.renderMinZ * 16.0D);
/* 298 */     double d6 = p_147806_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
/*     */     
/* 300 */     if ((this.renderMinX < 0.0D) || (this.renderMaxX > 1.0D))
/*     */     {
/* 302 */       d3 = p_147806_8_.getMinU();
/* 303 */       d4 = p_147806_8_.getMaxU();
/*     */     }
/*     */     
/* 306 */     if ((this.renderMinZ < 0.0D) || (this.renderMaxZ > 1.0D))
/*     */     {
/* 308 */       d5 = p_147806_8_.getMinV();
/* 309 */       d6 = p_147806_8_.getMaxV();
/*     */     }
/*     */     
/* 312 */     double d7 = d4;
/* 313 */     double d8 = d3;
/* 314 */     double d9 = d5;
/* 315 */     double d10 = d6;
/*     */     
/* 317 */     if (this.uvRotateTop == 1)
/*     */     {
/* 319 */       d3 = p_147806_8_.getInterpolatedU(this.renderMinZ * 16.0D);
/* 320 */       d5 = p_147806_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
/* 321 */       d4 = p_147806_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
/* 322 */       d6 = p_147806_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
/* 323 */       d9 = d5;
/* 324 */       d10 = d6;
/* 325 */       d7 = d3;
/* 326 */       d8 = d4;
/* 327 */       d5 = d6;
/* 328 */       d6 = d9;
/*     */     }
/* 330 */     else if (this.uvRotateTop == 2)
/*     */     {
/* 332 */       d3 = p_147806_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
/* 333 */       d5 = p_147806_8_.getInterpolatedV(this.renderMinX * 16.0D);
/* 334 */       d4 = p_147806_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
/* 335 */       d6 = p_147806_8_.getInterpolatedV(this.renderMaxX * 16.0D);
/* 336 */       d7 = d4;
/* 337 */       d8 = d3;
/* 338 */       d3 = d4;
/* 339 */       d4 = d8;
/* 340 */       d9 = d6;
/* 341 */       d10 = d5;
/*     */     }
/* 343 */     else if (this.uvRotateTop == 3)
/*     */     {
/* 345 */       d3 = p_147806_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
/* 346 */       d4 = p_147806_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
/* 347 */       d5 = p_147806_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
/* 348 */       d6 = p_147806_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
/* 349 */       d7 = d4;
/* 350 */       d8 = d3;
/* 351 */       d9 = d5;
/* 352 */       d10 = d6;
/*     */     }
/*     */     
/* 355 */     double d11 = p_147806_2_ + this.renderMinX;
/* 356 */     double d12 = p_147806_2_ + this.renderMaxX;
/* 357 */     double d13 = p_147806_4_ + this.renderMaxY;
/* 358 */     double d14 = p_147806_6_ + this.renderMinZ;
/* 359 */     double d15 = p_147806_6_ + this.renderMaxZ;
/*     */     
/* 361 */     if (this.renderFromInside)
/*     */     {
/* 363 */       d11 = p_147806_2_ + this.renderMaxX;
/* 364 */       d12 = p_147806_2_ + this.renderMinX;
/*     */     }
/*     */     
/* 367 */     int i = bright;
/* 368 */     int j = i >> 16 & 0xFFFF;
/* 369 */     int k = i & 0xFFFF;
/* 370 */     tessellator.getWorldRenderer().pos(d12, d13, d15).tex(d4, d6).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 371 */     tessellator.getWorldRenderer().pos(d12, d13, d14).tex(d7, d9).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 372 */     tessellator.getWorldRenderer().pos(d11, d13, d14).tex(d3, d5).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 373 */     tessellator.getWorldRenderer().pos(d11, d13, d15).tex(d8, d10).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderFaceZNeg(Block p_147761_1_, double p_147761_2_, double p_147761_4_, double p_147761_6_, TextureAtlasSprite p_147761_8_, float red, float green, float blue, int bright)
/*     */   {
/* 381 */     Tessellator tessellator = Tessellator.getInstance();
/*     */     
/*     */ 
/* 384 */     double d3 = p_147761_8_.getInterpolatedU(this.renderMinX * 16.0D);
/* 385 */     double d4 = p_147761_8_.getInterpolatedU(this.renderMaxX * 16.0D);
/*     */     
/* 387 */     if (this.field_152631_f)
/*     */     {
/* 389 */       d4 = p_147761_8_.getInterpolatedU((1.0D - this.renderMinX) * 16.0D);
/* 390 */       d3 = p_147761_8_.getInterpolatedU((1.0D - this.renderMaxX) * 16.0D);
/*     */     }
/*     */     
/* 393 */     double d5 = p_147761_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
/* 394 */     double d6 = p_147761_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
/*     */     
/*     */ 
/* 397 */     if (this.flipTexture)
/*     */     {
/* 399 */       double d7 = d3;
/* 400 */       d3 = d4;
/* 401 */       d4 = d7;
/*     */     }
/*     */     
/* 404 */     if ((this.renderMinX < 0.0D) || (this.renderMaxX > 1.0D))
/*     */     {
/* 406 */       d3 = p_147761_8_.getMinU();
/* 407 */       d4 = p_147761_8_.getMaxU();
/*     */     }
/*     */     
/* 410 */     if ((this.renderMinY < 0.0D) || (this.renderMaxY > 1.0D))
/*     */     {
/* 412 */       d5 = p_147761_8_.getMinV();
/* 413 */       d6 = p_147761_8_.getMaxV();
/*     */     }
/*     */     
/* 416 */     double d7 = d4;
/* 417 */     double d8 = d3;
/* 418 */     double d9 = d5;
/* 419 */     double d10 = d6;
/*     */     
/* 421 */     if (this.uvRotateEast == 2)
/*     */     {
/* 423 */       d3 = p_147761_8_.getInterpolatedU(this.renderMinY * 16.0D);
/* 424 */       d4 = p_147761_8_.getInterpolatedU(this.renderMaxY * 16.0D);
/* 425 */       d5 = p_147761_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
/* 426 */       d6 = p_147761_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
/* 427 */       d9 = d5;
/* 428 */       d10 = d6;
/* 429 */       d7 = d3;
/* 430 */       d8 = d4;
/* 431 */       d5 = d6;
/* 432 */       d6 = d9;
/*     */     }
/* 434 */     else if (this.uvRotateEast == 1)
/*     */     {
/* 436 */       d3 = p_147761_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
/* 437 */       d4 = p_147761_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
/* 438 */       d5 = p_147761_8_.getInterpolatedV(this.renderMaxX * 16.0D);
/* 439 */       d6 = p_147761_8_.getInterpolatedV(this.renderMinX * 16.0D);
/* 440 */       d7 = d4;
/* 441 */       d8 = d3;
/* 442 */       d3 = d4;
/* 443 */       d4 = d8;
/* 444 */       d9 = d6;
/* 445 */       d10 = d5;
/*     */     }
/* 447 */     else if (this.uvRotateEast == 3)
/*     */     {
/* 449 */       d3 = p_147761_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
/* 450 */       d4 = p_147761_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
/* 451 */       d5 = p_147761_8_.getInterpolatedV(this.renderMaxY * 16.0D);
/* 452 */       d6 = p_147761_8_.getInterpolatedV(this.renderMinY * 16.0D);
/* 453 */       d7 = d4;
/* 454 */       d8 = d3;
/* 455 */       d9 = d5;
/* 456 */       d10 = d6;
/*     */     }
/*     */     
/* 459 */     double d11 = p_147761_2_ + this.renderMinX;
/* 460 */     double d12 = p_147761_2_ + this.renderMaxX;
/* 461 */     double d13 = p_147761_4_ + this.renderMinY;
/* 462 */     double d14 = p_147761_4_ + this.renderMaxY;
/* 463 */     double d15 = p_147761_6_ + this.renderMinZ;
/*     */     
/* 465 */     if (this.renderFromInside)
/*     */     {
/* 467 */       d11 = p_147761_2_ + this.renderMaxX;
/* 468 */       d12 = p_147761_2_ + this.renderMinX;
/*     */     }
/*     */     
/* 471 */     int i = bright;
/* 472 */     int j = i >> 16 & 0xFFFF;
/* 473 */     int k = i & 0xFFFF;
/* 474 */     tessellator.getWorldRenderer().pos(d11, d14, d15).tex(d7, d9).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 475 */     tessellator.getWorldRenderer().pos(d12, d14, d15).tex(d3, d5).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 476 */     tessellator.getWorldRenderer().pos(d12, d13, d15).tex(d8, d10).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 477 */     tessellator.getWorldRenderer().pos(d11, d13, d15).tex(d4, d6).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderFaceZPos(Block p_147734_1_, double p_147734_2_, double p_147734_4_, double p_147734_6_, TextureAtlasSprite p_147734_8_, float red, float green, float blue, int bright)
/*     */   {
/* 485 */     Tessellator tessellator = Tessellator.getInstance();
/*     */     
/* 487 */     double d3 = p_147734_8_.getInterpolatedU(this.renderMinX * 16.0D);
/* 488 */     double d4 = p_147734_8_.getInterpolatedU(this.renderMaxX * 16.0D);
/* 489 */     double d5 = p_147734_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
/* 490 */     double d6 = p_147734_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
/*     */     
/*     */ 
/* 493 */     if (this.flipTexture)
/*     */     {
/* 495 */       double d7 = d3;
/* 496 */       d3 = d4;
/* 497 */       d4 = d7;
/*     */     }
/*     */     
/* 500 */     if ((this.renderMinX < 0.0D) || (this.renderMaxX > 1.0D))
/*     */     {
/* 502 */       d3 = p_147734_8_.getMinU();
/* 503 */       d4 = p_147734_8_.getMaxU();
/*     */     }
/*     */     
/* 506 */     if ((this.renderMinY < 0.0D) || (this.renderMaxY > 1.0D))
/*     */     {
/* 508 */       d5 = p_147734_8_.getMinV();
/* 509 */       d6 = p_147734_8_.getMaxV();
/*     */     }
/*     */     
/* 512 */     double d7 = d4;
/* 513 */     double d8 = d3;
/* 514 */     double d9 = d5;
/* 515 */     double d10 = d6;
/*     */     
/* 517 */     if (this.uvRotateWest == 1)
/*     */     {
/* 519 */       d3 = p_147734_8_.getInterpolatedU(this.renderMinY * 16.0D);
/* 520 */       d6 = p_147734_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
/* 521 */       d4 = p_147734_8_.getInterpolatedU(this.renderMaxY * 16.0D);
/* 522 */       d5 = p_147734_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
/* 523 */       d9 = d5;
/* 524 */       d10 = d6;
/* 525 */       d7 = d3;
/* 526 */       d8 = d4;
/* 527 */       d5 = d6;
/* 528 */       d6 = d9;
/*     */     }
/* 530 */     else if (this.uvRotateWest == 2)
/*     */     {
/* 532 */       d3 = p_147734_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
/* 533 */       d5 = p_147734_8_.getInterpolatedV(this.renderMinX * 16.0D);
/* 534 */       d4 = p_147734_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
/* 535 */       d6 = p_147734_8_.getInterpolatedV(this.renderMaxX * 16.0D);
/* 536 */       d7 = d4;
/* 537 */       d8 = d3;
/* 538 */       d3 = d4;
/* 539 */       d4 = d8;
/* 540 */       d9 = d6;
/* 541 */       d10 = d5;
/*     */     }
/* 543 */     else if (this.uvRotateWest == 3)
/*     */     {
/* 545 */       d3 = p_147734_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
/* 546 */       d4 = p_147734_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
/* 547 */       d5 = p_147734_8_.getInterpolatedV(this.renderMaxY * 16.0D);
/* 548 */       d6 = p_147734_8_.getInterpolatedV(this.renderMinY * 16.0D);
/* 549 */       d7 = d4;
/* 550 */       d8 = d3;
/* 551 */       d9 = d5;
/* 552 */       d10 = d6;
/*     */     }
/*     */     
/* 555 */     double d11 = p_147734_2_ + this.renderMinX;
/* 556 */     double d12 = p_147734_2_ + this.renderMaxX;
/* 557 */     double d13 = p_147734_4_ + this.renderMinY;
/* 558 */     double d14 = p_147734_4_ + this.renderMaxY;
/* 559 */     double d15 = p_147734_6_ + this.renderMaxZ;
/*     */     
/* 561 */     if (this.renderFromInside)
/*     */     {
/* 563 */       d11 = p_147734_2_ + this.renderMaxX;
/* 564 */       d12 = p_147734_2_ + this.renderMinX;
/*     */     }
/* 566 */     int i = bright;
/* 567 */     int j = i >> 16 & 0xFFFF;
/* 568 */     int k = i & 0xFFFF;
/* 569 */     tessellator.getWorldRenderer().pos(d11, d14, d15).tex(d3, d5).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 570 */     tessellator.getWorldRenderer().pos(d11, d13, d15).tex(d8, d10).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 571 */     tessellator.getWorldRenderer().pos(d12, d13, d15).tex(d4, d6).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 572 */     tessellator.getWorldRenderer().pos(d12, d14, d15).tex(d7, d9).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderFaceXNeg(Block p_147798_1_, double p_147798_2_, double p_147798_4_, double p_147798_6_, TextureAtlasSprite p_147798_8_, float red, float green, float blue, int bright)
/*     */   {
/* 580 */     Tessellator tessellator = Tessellator.getInstance();
/*     */     
/* 582 */     double d3 = p_147798_8_.getInterpolatedU(this.renderMinZ * 16.0D);
/* 583 */     double d4 = p_147798_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
/* 584 */     double d5 = p_147798_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
/* 585 */     double d6 = p_147798_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
/*     */     
/*     */ 
/* 588 */     if (this.flipTexture)
/*     */     {
/* 590 */       double d7 = d3;
/* 591 */       d3 = d4;
/* 592 */       d4 = d7;
/*     */     }
/*     */     
/* 595 */     if ((this.renderMinZ < 0.0D) || (this.renderMaxZ > 1.0D))
/*     */     {
/* 597 */       d3 = p_147798_8_.getMinU();
/* 598 */       d4 = p_147798_8_.getMaxU();
/*     */     }
/*     */     
/* 601 */     if ((this.renderMinY < 0.0D) || (this.renderMaxY > 1.0D))
/*     */     {
/* 603 */       d5 = p_147798_8_.getMinV();
/* 604 */       d6 = p_147798_8_.getMaxV();
/*     */     }
/*     */     
/* 607 */     double d7 = d4;
/* 608 */     double d8 = d3;
/* 609 */     double d9 = d5;
/* 610 */     double d10 = d6;
/*     */     
/* 612 */     if (this.uvRotateNorth == 1)
/*     */     {
/* 614 */       d3 = p_147798_8_.getInterpolatedU(this.renderMinY * 16.0D);
/* 615 */       d5 = p_147798_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
/* 616 */       d4 = p_147798_8_.getInterpolatedU(this.renderMaxY * 16.0D);
/* 617 */       d6 = p_147798_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
/* 618 */       d9 = d5;
/* 619 */       d10 = d6;
/* 620 */       d7 = d3;
/* 621 */       d8 = d4;
/* 622 */       d5 = d6;
/* 623 */       d6 = d9;
/*     */     }
/* 625 */     else if (this.uvRotateNorth == 2)
/*     */     {
/* 627 */       d3 = p_147798_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
/* 628 */       d5 = p_147798_8_.getInterpolatedV(this.renderMinZ * 16.0D);
/* 629 */       d4 = p_147798_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
/* 630 */       d6 = p_147798_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
/* 631 */       d7 = d4;
/* 632 */       d8 = d3;
/* 633 */       d3 = d4;
/* 634 */       d4 = d8;
/* 635 */       d9 = d6;
/* 636 */       d10 = d5;
/*     */     }
/* 638 */     else if (this.uvRotateNorth == 3)
/*     */     {
/* 640 */       d3 = p_147798_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
/* 641 */       d4 = p_147798_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
/* 642 */       d5 = p_147798_8_.getInterpolatedV(this.renderMaxY * 16.0D);
/* 643 */       d6 = p_147798_8_.getInterpolatedV(this.renderMinY * 16.0D);
/* 644 */       d7 = d4;
/* 645 */       d8 = d3;
/* 646 */       d9 = d5;
/* 647 */       d10 = d6;
/*     */     }
/*     */     
/* 650 */     double d11 = p_147798_2_ + this.renderMinX;
/* 651 */     double d12 = p_147798_4_ + this.renderMinY;
/* 652 */     double d13 = p_147798_4_ + this.renderMaxY;
/* 653 */     double d14 = p_147798_6_ + this.renderMinZ;
/* 654 */     double d15 = p_147798_6_ + this.renderMaxZ;
/*     */     
/* 656 */     if (this.renderFromInside)
/*     */     {
/* 658 */       d14 = p_147798_6_ + this.renderMaxZ;
/* 659 */       d15 = p_147798_6_ + this.renderMinZ;
/*     */     }
/* 661 */     int i = bright;
/* 662 */     int j = i >> 16 & 0xFFFF;
/* 663 */     int k = i & 0xFFFF;
/* 664 */     tessellator.getWorldRenderer().pos(d11, d13, d15).tex(d7, d9).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 665 */     tessellator.getWorldRenderer().pos(d11, d13, d14).tex(d3, d5).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 666 */     tessellator.getWorldRenderer().pos(d11, d12, d14).tex(d8, d10).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 667 */     tessellator.getWorldRenderer().pos(d11, d12, d15).tex(d4, d6).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderFaceXPos(Block p_147764_1_, double p_147764_2_, double p_147764_4_, double p_147764_6_, TextureAtlasSprite p_147764_8_, float red, float green, float blue, int bright)
/*     */   {
/* 676 */     Tessellator tessellator = Tessellator.getInstance();
/*     */     
/*     */ 
/* 679 */     double d3 = p_147764_8_.getInterpolatedU(this.renderMinZ * 16.0D);
/* 680 */     double d4 = p_147764_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
/*     */     
/* 682 */     if (this.field_152631_f)
/*     */     {
/* 684 */       d4 = p_147764_8_.getInterpolatedU((1.0D - this.renderMinZ) * 16.0D);
/* 685 */       d3 = p_147764_8_.getInterpolatedU((1.0D - this.renderMaxZ) * 16.0D);
/*     */     }
/*     */     
/* 688 */     double d5 = p_147764_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
/* 689 */     double d6 = p_147764_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
/*     */     
/*     */ 
/* 692 */     if (this.flipTexture)
/*     */     {
/* 694 */       double d7 = d3;
/* 695 */       d3 = d4;
/* 696 */       d4 = d7;
/*     */     }
/*     */     
/* 699 */     if ((this.renderMinZ < 0.0D) || (this.renderMaxZ > 1.0D))
/*     */     {
/* 701 */       d3 = p_147764_8_.getMinU();
/* 702 */       d4 = p_147764_8_.getMaxU();
/*     */     }
/*     */     
/* 705 */     if ((this.renderMinY < 0.0D) || (this.renderMaxY > 1.0D))
/*     */     {
/* 707 */       d5 = p_147764_8_.getMinV();
/* 708 */       d6 = p_147764_8_.getMaxV();
/*     */     }
/*     */     
/* 711 */     double d7 = d4;
/* 712 */     double d8 = d3;
/* 713 */     double d9 = d5;
/* 714 */     double d10 = d6;
/*     */     
/* 716 */     if (this.uvRotateSouth == 2)
/*     */     {
/* 718 */       d3 = p_147764_8_.getInterpolatedU(this.renderMinY * 16.0D);
/* 719 */       d5 = p_147764_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
/* 720 */       d4 = p_147764_8_.getInterpolatedU(this.renderMaxY * 16.0D);
/* 721 */       d6 = p_147764_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
/* 722 */       d9 = d5;
/* 723 */       d10 = d6;
/* 724 */       d7 = d3;
/* 725 */       d8 = d4;
/* 726 */       d5 = d6;
/* 727 */       d6 = d9;
/*     */     }
/* 729 */     else if (this.uvRotateSouth == 1)
/*     */     {
/* 731 */       d3 = p_147764_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
/* 732 */       d5 = p_147764_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
/* 733 */       d4 = p_147764_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
/* 734 */       d6 = p_147764_8_.getInterpolatedV(this.renderMinZ * 16.0D);
/* 735 */       d7 = d4;
/* 736 */       d8 = d3;
/* 737 */       d3 = d4;
/* 738 */       d4 = d8;
/* 739 */       d9 = d6;
/* 740 */       d10 = d5;
/*     */     }
/* 742 */     else if (this.uvRotateSouth == 3)
/*     */     {
/* 744 */       d3 = p_147764_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
/* 745 */       d4 = p_147764_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
/* 746 */       d5 = p_147764_8_.getInterpolatedV(this.renderMaxY * 16.0D);
/* 747 */       d6 = p_147764_8_.getInterpolatedV(this.renderMinY * 16.0D);
/* 748 */       d7 = d4;
/* 749 */       d8 = d3;
/* 750 */       d9 = d5;
/* 751 */       d10 = d6;
/*     */     }
/*     */     
/* 754 */     double d11 = p_147764_2_ + this.renderMaxX;
/* 755 */     double d12 = p_147764_4_ + this.renderMinY;
/* 756 */     double d13 = p_147764_4_ + this.renderMaxY;
/* 757 */     double d14 = p_147764_6_ + this.renderMinZ;
/* 758 */     double d15 = p_147764_6_ + this.renderMaxZ;
/*     */     
/* 760 */     if (this.renderFromInside)
/*     */     {
/* 762 */       d14 = p_147764_6_ + this.renderMaxZ;
/* 763 */       d15 = p_147764_6_ + this.renderMinZ;
/*     */     }
/* 765 */     int i = bright;
/* 766 */     int j = i >> 16 & 0xFFFF;
/* 767 */     int k = i & 0xFFFF;
/* 768 */     tessellator.getWorldRenderer().pos(d11, d12, d15).tex(d8, d10).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 769 */     tessellator.getWorldRenderer().pos(d11, d12, d14).tex(d4, d6).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 770 */     tessellator.getWorldRenderer().pos(d11, d13, d14).tex(d7, d9).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/* 771 */     tessellator.getWorldRenderer().pos(d11, d13, d15).tex(d3, d5).lightmap(j, k).color(red, green, blue, 1.0F).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static RenderBlocks getInstance()
/*     */   {
/* 783 */     if (instance == null) instance = new RenderBlocks();
/* 784 */     return instance;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\lib\RenderBlocks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */