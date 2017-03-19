/*     */ package thaumcraft.client.renderers.tile;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.models.AdvancedModelLoader;
/*     */ import thaumcraft.client.lib.models.IModelCustom;
/*     */ import thaumcraft.common.config.Config;
/*     */ 
/*     */ public class TileEldritchObeliskRenderer extends TileEntitySpecialRenderer
/*     */ {
/*     */   FloatBuffer fBuffer;
/*     */   private boolean inrange;
/*     */   private IModelCustom model;
/*  32 */   private static final ResourceLocation CAP = new ResourceLocation("thaumcraft", "models/obj/obelisk_cap.obj");
/*     */   
/*     */   public TileEldritchObeliskRenderer()
/*     */   {
/*  36 */     this.fBuffer = GLAllocation.createDirectFloatBuffer(16);
/*  37 */     this.model = AdvancedModelLoader.loadModel(CAP);
/*     */   }
/*     */   
/*  40 */   private static ResourceLocation t1 = new ResourceLocation("thaumcraft", "textures/misc/tunnel.png");
/*  41 */   private static ResourceLocation t2 = new ResourceLocation("thaumcraft", "textures/misc/particlefield.png");
/*  42 */   private static ResourceLocation t3 = new ResourceLocation("thaumcraft", "textures/misc/particlefield32.png");
/*  43 */   private static ResourceLocation t4 = new ResourceLocation("thaumcraft", "textures/models/obelisk_side.png");
/*  44 */   private static ResourceLocation t5 = new ResourceLocation("thaumcraft", "textures/models/obelisk_cap.png");
/*  45 */   private static ResourceLocation t6 = new ResourceLocation("thaumcraft", "textures/models/obelisk_side_2.png");
/*  46 */   private static ResourceLocation t7 = new ResourceLocation("thaumcraft", "textures/models/obelisk_cap_2.png");
/*     */   
/*     */ 
/*     */   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f, int qq)
/*     */   {
/*  51 */     this.inrange = (Minecraft.getMinecraft().getRenderViewEntity().getDistanceSqToCenter(te.getPos()) < 512.0D);
/*     */     
/*  53 */     float bob = 0.0F;
/*  54 */     float count = Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + f;
/*  55 */     bob = MathHelper.sin(count / 10.0F) * 0.1F + 0.1F;
/*     */     
/*  57 */     GL11.glPushMatrix();
/*  58 */     GL11.glDisable(2912);
/*  59 */     drawPlaneZNeg(x, y + 1.0D + bob, z, f, 3);
/*  60 */     drawPlaneZPos(x, y + 1.0D + bob, z, f, 3);
/*  61 */     drawPlaneXNeg(x, y + 1.0D + bob, z, f, 3);
/*  62 */     drawPlaneXPos(x, y + 1.0D + bob, z, f, 3);
/*  63 */     GL11.glEnable(2912);
/*  64 */     GL11.glPopMatrix();
/*     */     
/*  66 */     GL11.glPushMatrix();
/*     */     
/*  68 */     GL11.glEnable(2896);
/*     */     
/*  70 */     GL11.glEnable(32826);
/*  71 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  73 */     GL11.glEnable(3042);
/*  74 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  76 */     ResourceLocation tempTex1 = t4;
/*  77 */     ResourceLocation tempTex2 = t5;
/*  78 */     if (te.getWorld() != null) {
/*  79 */       int j = te.getBlockType().getMixedBrightnessForBlock(te.getWorld(), te.getPos());
/*  80 */       int k = j % 65536;
/*  81 */       int l = j / 65536;
/*  82 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/*     */       
/*  84 */       if (te.getWorld().provider.getDimensionId() == Config.dimensionOuterId) {
/*  85 */         tempTex1 = t6;
/*  86 */         tempTex2 = t7;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  91 */     GL11.glPushMatrix();
/*  92 */     bindTexture(tempTex1);
/*  93 */     GL11.glTranslated(x + 0.5D, y + 1.0D + bob, z + 0.5D);
/*  94 */     for (int a = 0; a < 4; a++) {
/*  95 */       GL11.glPushMatrix();
/*  96 */       GL11.glRotatef(a * 90, 0.0F, 1.0F, 0.0F);
/*  97 */       GL11.glTranslated(0.0D, 0.0D, -0.5D);
/*  98 */       renderSide(3);
/*  99 */       GL11.glPopMatrix();
/*     */     }
/* 101 */     GL11.glPopMatrix();
/*     */     
/* 103 */     GL11.glPushMatrix();
/*     */     
/* 105 */     GL11.glTranslated(x + 0.5D, y + 1.0D + bob, z + 0.5D);
/* 106 */     GL11.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
/* 107 */     bindTexture(tempTex2);
/* 108 */     this.model.renderPart("Cap");
/* 109 */     GL11.glPopMatrix();
/* 110 */     GL11.glPushMatrix();
/* 111 */     GL11.glTranslated(x + 0.5D, y + 4.0D + bob, z + 0.5D);
/* 112 */     GL11.glRotated(90.0D, -1.0D, 0.0D, 0.0D);
/* 113 */     this.model.renderPart("Cap");
/* 114 */     GL11.glPopMatrix();
/* 115 */     GL11.glDisable(3042);
/* 116 */     GL11.glDisable(32826);
/* 117 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */ 
/*     */   public void drawPlaneZPos(double x, double y, double z, float f, int height)
/*     */   {
/* 123 */     float px = (float)TileEntityRendererDispatcher.staticPlayerX;
/* 124 */     float py = (float)TileEntityRendererDispatcher.staticPlayerY;
/* 125 */     float pz = (float)TileEntityRendererDispatcher.staticPlayerZ;
/* 126 */     GL11.glDisable(2896);
/* 127 */     Random random = new Random(31100L);
/* 128 */     float offset = 0.99F;
/* 129 */     if (this.inrange) {
/* 130 */       for (int i = 0; i < 16; i++)
/*     */       {
/* 132 */         GL11.glPushMatrix();
/* 133 */         float f5 = 16 - i;
/* 134 */         float f6 = 0.0625F;
/* 135 */         float f7 = 1.0F / (f5 + 1.0F);
/* 136 */         if (i == 0)
/*     */         {
/* 138 */           bindTexture(t1);
/* 139 */           f7 = 0.1F;
/* 140 */           f5 = 65.0F;
/* 141 */           f6 = 0.125F;
/* 142 */           GL11.glEnable(3042);
/* 143 */           GL11.glBlendFunc(770, 771);
/*     */         }
/* 145 */         if (i == 1)
/*     */         {
/* 147 */           bindTexture(t2);
/* 148 */           GL11.glEnable(3042);
/* 149 */           GL11.glBlendFunc(1, 1);
/* 150 */           f6 = 0.5F;
/*     */         }
/*     */         
/* 153 */         float f8 = (float)-(z + offset);
/* 154 */         float f9 = f8 + (float)ActiveRenderInfo.getPosition().zCoord;
/* 155 */         float f10 = f8 + f5 + (float)ActiveRenderInfo.getPosition().zCoord;
/* 156 */         float f11 = f9 / f10;
/* 157 */         f11 = (float)(z + offset) + f11;
/* 158 */         GL11.glTranslatef(px, py, f11);
/* 159 */         GL11.glTexGeni(8192, 9472, 9217);
/* 160 */         GL11.glTexGeni(8193, 9472, 9217);
/* 161 */         GL11.glTexGeni(8194, 9472, 9217);
/* 162 */         GL11.glTexGeni(8195, 9472, 9216);
/* 163 */         GL11.glTexGen(8192, 9473, calcFloatBuffer(1.0F, 0.0F, 0.0F, 0.0F));
/* 164 */         GL11.glTexGen(8193, 9473, calcFloatBuffer(0.0F, 1.0F, 0.0F, 0.0F));
/* 165 */         GL11.glTexGen(8194, 9473, calcFloatBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/*     */         
/* 167 */         GL11.glTexGen(8195, 9474, calcFloatBuffer(0.0F, 0.0F, 1.0F, 0.0F));
/* 168 */         GL11.glEnable(3168);
/* 169 */         GL11.glEnable(3169);
/* 170 */         GL11.glEnable(3170);
/* 171 */         GL11.glEnable(3171);
/* 172 */         GL11.glPopMatrix();
/* 173 */         GL11.glMatrixMode(5890);
/* 174 */         GL11.glPushMatrix();
/* 175 */         GL11.glLoadIdentity();
/* 176 */         GL11.glTranslatef(0.0F, (float)(System.currentTimeMillis() % 700000L) / 250000.0F, 0.0F);
/* 177 */         GL11.glScalef(f6, f6, f6);
/* 178 */         GL11.glTranslatef(0.5F, 0.5F, 0.0F);
/* 179 */         GL11.glRotatef((i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
/* 180 */         GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
/* 181 */         GL11.glTranslatef(-px, -py, -pz);
/*     */         
/*     */ 
/* 184 */         GL11.glTranslatef((float)ActiveRenderInfo.getPosition().xCoord * f5 / f9, (float)ActiveRenderInfo.getPosition().yCoord * f5 / f9, -pz);
/*     */         
/*     */ 
/* 187 */         Tessellator tessellator = Tessellator.getInstance();
/* 188 */         tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 189 */         f11 = random.nextFloat() * 0.5F + 0.1F;
/* 190 */         float f12 = random.nextFloat() * 0.5F + 0.4F;
/* 191 */         float f13 = random.nextFloat() * 0.5F + 0.5F;
/* 192 */         if (i == 0)
/*     */         {
/* 194 */           f11 = f12 = f13 = 1.0F;
/*     */         }
/*     */         
/*     */ 
/* 198 */         tessellator.getWorldRenderer().pos(x, y + height, z + offset).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 199 */         tessellator.getWorldRenderer().pos(x, y, z + offset).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 200 */         tessellator.getWorldRenderer().pos(x + 1.0D, y, z + offset).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 201 */         tessellator.getWorldRenderer().pos(x + 1.0D, y + height, z + offset).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/*     */         
/* 203 */         tessellator.draw();
/* 204 */         GL11.glPopMatrix();
/* 205 */         GL11.glMatrixMode(5888);
/*     */       }
/*     */     } else {
/* 208 */       GL11.glPushMatrix();
/* 209 */       bindTexture(t3);
/* 210 */       Tessellator tessellator = Tessellator.getInstance();
/* 211 */       tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*     */       
/*     */ 
/* 214 */       tessellator.getWorldRenderer().pos(x, y + height, z + offset).tex(1.0D, 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 215 */       tessellator.getWorldRenderer().pos(x, y, z + offset).tex(1.0D, 0.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 216 */       tessellator.getWorldRenderer().pos(x + 1.0D, y, z + offset).tex(0.0D, 0.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 217 */       tessellator.getWorldRenderer().pos(x + 1.0D, y + height, z + offset).tex(0.0D, 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/*     */       
/* 219 */       tessellator.draw();
/* 220 */       GL11.glPopMatrix();
/*     */     }
/*     */     
/* 223 */     GL11.glDisable(3042);
/* 224 */     GL11.glDisable(3168);
/* 225 */     GL11.glDisable(3169);
/* 226 */     GL11.glDisable(3170);
/* 227 */     GL11.glDisable(3171);
/* 228 */     GL11.glEnable(2896);
/*     */   }
/*     */   
/*     */   public void drawPlaneZNeg(double x, double y, double z, float f, int height)
/*     */   {
/* 233 */     float px = (float)TileEntityRendererDispatcher.staticPlayerX;
/* 234 */     float py = (float)TileEntityRendererDispatcher.staticPlayerY;
/* 235 */     float pz = (float)TileEntityRendererDispatcher.staticPlayerZ;
/* 236 */     GL11.glDisable(2896);
/* 237 */     Random random = new Random(31100L);
/* 238 */     float offset = 0.01F;
/* 239 */     if (this.inrange) {
/* 240 */       for (int i = 0; i < 16; i++)
/*     */       {
/* 242 */         GL11.glPushMatrix();
/* 243 */         float f5 = 16 - i;
/* 244 */         float f6 = 0.0625F;
/* 245 */         float f7 = 1.0F / (f5 + 1.0F);
/* 246 */         if (i == 0)
/*     */         {
/* 248 */           bindTexture(t1);
/* 249 */           f7 = 0.1F;
/* 250 */           f5 = 65.0F;
/* 251 */           f6 = 0.125F;
/* 252 */           GL11.glEnable(3042);
/* 253 */           GL11.glBlendFunc(770, 771);
/*     */         }
/* 255 */         if (i == 1)
/*     */         {
/* 257 */           bindTexture(t2);
/* 258 */           GL11.glEnable(3042);
/* 259 */           GL11.glBlendFunc(1, 1);
/* 260 */           f6 = 0.5F;
/*     */         }
/*     */         
/* 263 */         float f8 = (float)(z + offset);
/* 264 */         float f9 = f8 - (float)ActiveRenderInfo.getPosition().zCoord;
/* 265 */         float f10 = f8 + f5 - (float)ActiveRenderInfo.getPosition().zCoord;
/* 266 */         float f11 = f9 / f10;
/* 267 */         f11 = (float)(z + offset) + f11;
/* 268 */         GL11.glTranslatef(px, py, f11);
/* 269 */         GL11.glTexGeni(8192, 9472, 9217);
/* 270 */         GL11.glTexGeni(8193, 9472, 9217);
/* 271 */         GL11.glTexGeni(8194, 9472, 9217);
/* 272 */         GL11.glTexGeni(8195, 9472, 9216);
/* 273 */         GL11.glTexGen(8192, 9473, calcFloatBuffer(1.0F, 0.0F, 0.0F, 0.0F));
/* 274 */         GL11.glTexGen(8193, 9473, calcFloatBuffer(0.0F, 1.0F, 0.0F, 0.0F));
/* 275 */         GL11.glTexGen(8194, 9473, calcFloatBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/*     */         
/* 277 */         GL11.glTexGen(8195, 9474, calcFloatBuffer(0.0F, 0.0F, 1.0F, 0.0F));
/* 278 */         GL11.glEnable(3168);
/* 279 */         GL11.glEnable(3169);
/* 280 */         GL11.glEnable(3170);
/* 281 */         GL11.glEnable(3171);
/* 282 */         GL11.glPopMatrix();
/* 283 */         GL11.glMatrixMode(5890);
/* 284 */         GL11.glPushMatrix();
/* 285 */         GL11.glLoadIdentity();
/* 286 */         GL11.glTranslatef(0.0F, (float)(System.currentTimeMillis() % 700000L) / 250000.0F, 0.0F);
/* 287 */         GL11.glScalef(f6, f6, f6);
/* 288 */         GL11.glTranslatef(0.5F, 0.5F, 0.0F);
/* 289 */         GL11.glRotatef((i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
/* 290 */         GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
/* 291 */         GL11.glTranslatef(-px, -py, -pz);
/*     */         
/*     */ 
/* 294 */         GL11.glTranslatef((float)ActiveRenderInfo.getPosition().xCoord * f5 / f9, (float)ActiveRenderInfo.getPosition().yCoord * f5 / f9, -pz);
/*     */         
/* 296 */         Tessellator tessellator = Tessellator.getInstance();
/* 297 */         tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 298 */         f11 = random.nextFloat() * 0.5F + 0.1F;
/* 299 */         float f12 = random.nextFloat() * 0.5F + 0.4F;
/* 300 */         float f13 = random.nextFloat() * 0.5F + 0.5F;
/* 301 */         if (i == 0)
/*     */         {
/* 303 */           f11 = f12 = f13 = 1.0F;
/*     */         }
/*     */         
/*     */ 
/* 307 */         tessellator.getWorldRenderer().pos(x, y, z + offset).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 308 */         tessellator.getWorldRenderer().pos(x, y + height, z + offset).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 309 */         tessellator.getWorldRenderer().pos(x + 1.0D, y + height, z + offset).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 310 */         tessellator.getWorldRenderer().pos(x + 1.0D, y, z + offset).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/*     */         
/* 312 */         tessellator.draw();
/* 313 */         GL11.glPopMatrix();
/* 314 */         GL11.glMatrixMode(5888);
/*     */       }
/* 316 */     } else { GL11.glPushMatrix();
/* 317 */       bindTexture(t3);
/* 318 */       Tessellator tessellator = Tessellator.getInstance();
/* 319 */       tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*     */       
/*     */ 
/* 322 */       tessellator.getWorldRenderer().pos(x, y, z + offset).tex(1.0D, 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 323 */       tessellator.getWorldRenderer().pos(x, y + height, z + offset).tex(1.0D, 0.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 324 */       tessellator.getWorldRenderer().pos(x + 1.0D, y + height, z + offset).tex(0.0D, 0.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 325 */       tessellator.getWorldRenderer().pos(x + 1.0D, y, z + offset).tex(0.0D, 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/*     */       
/* 327 */       tessellator.draw();
/* 328 */       GL11.glPopMatrix();
/*     */     }
/*     */     
/* 331 */     GL11.glDisable(3042);
/* 332 */     GL11.glDisable(3168);
/* 333 */     GL11.glDisable(3169);
/* 334 */     GL11.glDisable(3170);
/* 335 */     GL11.glDisable(3171);
/* 336 */     GL11.glEnable(2896);
/*     */   }
/*     */   
/*     */   public void drawPlaneXPos(double x, double y, double z, float f, int height)
/*     */   {
/* 341 */     float px = (float)TileEntityRendererDispatcher.staticPlayerX;
/* 342 */     float py = (float)TileEntityRendererDispatcher.staticPlayerY;
/* 343 */     float pz = (float)TileEntityRendererDispatcher.staticPlayerZ;
/* 344 */     GL11.glDisable(2896);
/* 345 */     Random random = new Random(31100L);
/* 346 */     float offset = 0.99F;
/* 347 */     if (this.inrange) {
/* 348 */       for (int i = 0; i < 16; i++)
/*     */       {
/* 350 */         GL11.glPushMatrix();
/* 351 */         float f5 = 16 - i;
/* 352 */         float f6 = 0.0625F;
/* 353 */         float f7 = 1.0F / (f5 + 1.0F);
/* 354 */         if (i == 0)
/*     */         {
/* 356 */           bindTexture(t1);
/* 357 */           f7 = 0.1F;
/* 358 */           f5 = 65.0F;
/* 359 */           f6 = 0.125F;
/* 360 */           GL11.glEnable(3042);
/* 361 */           GL11.glBlendFunc(770, 771);
/*     */         }
/* 363 */         if (i == 1)
/*     */         {
/* 365 */           bindTexture(t2);
/* 366 */           GL11.glEnable(3042);
/* 367 */           GL11.glBlendFunc(1, 1);
/* 368 */           f6 = 0.5F;
/*     */         }
/*     */         
/* 371 */         float f8 = (float)-(x + offset);
/* 372 */         float f9 = f8 + (float)ActiveRenderInfo.getPosition().xCoord;
/* 373 */         float f10 = f8 + f5 + (float)ActiveRenderInfo.getPosition().xCoord;
/* 374 */         float f11 = f9 / f10;
/* 375 */         f11 = (float)(x + offset) + f11;
/* 376 */         GL11.glTranslatef(f11, py, pz);
/* 377 */         GL11.glTexGeni(8192, 9472, 9217);
/* 378 */         GL11.glTexGeni(8193, 9472, 9217);
/* 379 */         GL11.glTexGeni(8194, 9472, 9217);
/* 380 */         GL11.glTexGeni(8195, 9472, 9216);
/*     */         
/*     */ 
/* 383 */         GL11.glTexGen(8193, 9473, calcFloatBuffer(0.0F, 1.0F, 0.0F, 0.0F));
/* 384 */         GL11.glTexGen(8192, 9473, calcFloatBuffer(0.0F, 0.0F, 1.0F, 0.0F));
/* 385 */         GL11.glTexGen(8194, 9473, calcFloatBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/* 386 */         GL11.glTexGen(8195, 9474, calcFloatBuffer(1.0F, 0.0F, 0.0F, 0.0F));
/* 387 */         GL11.glEnable(3168);
/* 388 */         GL11.glEnable(3169);
/* 389 */         GL11.glEnable(3170);
/* 390 */         GL11.glEnable(3171);
/* 391 */         GL11.glPopMatrix();
/* 392 */         GL11.glMatrixMode(5890);
/* 393 */         GL11.glPushMatrix();
/* 394 */         GL11.glLoadIdentity();
/* 395 */         GL11.glTranslatef(0.0F, (float)(System.currentTimeMillis() % 700000L) / 250000.0F, 0.0F);
/* 396 */         GL11.glScalef(f6, f6, f6);
/* 397 */         GL11.glTranslatef(0.5F, 0.5F, 0.0F);
/* 398 */         GL11.glRotatef((i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
/* 399 */         GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
/* 400 */         GL11.glTranslatef(-pz, -py, -px);
/*     */         
/*     */ 
/* 403 */         GL11.glTranslatef((float)ActiveRenderInfo.getPosition().zCoord * f5 / f9, (float)ActiveRenderInfo.getPosition().yCoord * f5 / f9, -px);
/*     */         
/*     */ 
/* 406 */         Tessellator tessellator = Tessellator.getInstance();
/* 407 */         tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 408 */         f11 = random.nextFloat() * 0.5F + 0.1F;
/* 409 */         float f12 = random.nextFloat() * 0.5F + 0.4F;
/* 410 */         float f13 = random.nextFloat() * 0.5F + 0.5F;
/* 411 */         if (i == 0)
/*     */         {
/* 413 */           f11 = f12 = f13 = 1.0F;
/*     */         }
/*     */         
/*     */ 
/* 417 */         tessellator.getWorldRenderer().pos(x + offset, y + height, z).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 418 */         tessellator.getWorldRenderer().pos(x + offset, y + height, z + 1.0D).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 419 */         tessellator.getWorldRenderer().pos(x + offset, y, z + 1.0D).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 420 */         tessellator.getWorldRenderer().pos(x + offset, y, z).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/*     */         
/* 422 */         tessellator.draw();
/* 423 */         GL11.glPopMatrix();
/* 424 */         GL11.glMatrixMode(5888);
/*     */       }
/* 426 */     } else { GL11.glPushMatrix();
/* 427 */       bindTexture(t3);
/* 428 */       Tessellator tessellator = Tessellator.getInstance();
/* 429 */       tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*     */       
/*     */ 
/* 432 */       tessellator.getWorldRenderer().pos(x + offset, y + height, z).tex(1.0D, 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 433 */       tessellator.getWorldRenderer().pos(x + offset, y + height, z + 1.0D).tex(1.0D, 0.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 434 */       tessellator.getWorldRenderer().pos(x + offset, y, z + 1.0D).tex(0.0D, 0.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 435 */       tessellator.getWorldRenderer().pos(x + offset, y, z).tex(0.0D, 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/*     */       
/* 437 */       tessellator.draw();
/* 438 */       GL11.glPopMatrix();
/*     */     }
/*     */     
/* 441 */     GL11.glDisable(3042);
/* 442 */     GL11.glDisable(3168);
/* 443 */     GL11.glDisable(3169);
/* 444 */     GL11.glDisable(3170);
/* 445 */     GL11.glDisable(3171);
/* 446 */     GL11.glEnable(2896);
/*     */   }
/*     */   
/*     */   public void drawPlaneXNeg(double x, double y, double z, float f, int height)
/*     */   {
/* 451 */     float px = (float)TileEntityRendererDispatcher.staticPlayerX;
/* 452 */     float py = (float)TileEntityRendererDispatcher.staticPlayerY;
/* 453 */     float pz = (float)TileEntityRendererDispatcher.staticPlayerZ;
/* 454 */     GL11.glDisable(2896);
/* 455 */     Random random = new Random(31100L);
/* 456 */     float offset = 0.01F;
/* 457 */     if (this.inrange) {
/* 458 */       for (int i = 0; i < 16; i++)
/*     */       {
/* 460 */         GL11.glPushMatrix();
/* 461 */         float f5 = 16 - i;
/* 462 */         float f6 = 0.0625F;
/* 463 */         float f7 = 1.0F / (f5 + 1.0F);
/* 464 */         if (i == 0)
/*     */         {
/* 466 */           bindTexture(t1);
/* 467 */           f7 = 0.1F;
/* 468 */           f5 = 65.0F;
/* 469 */           f6 = 0.125F;
/* 470 */           GL11.glEnable(3042);
/* 471 */           GL11.glBlendFunc(770, 771);
/*     */         }
/* 473 */         if (i == 1)
/*     */         {
/* 475 */           bindTexture(t2);
/* 476 */           GL11.glEnable(3042);
/* 477 */           GL11.glBlendFunc(1, 1);
/* 478 */           f6 = 0.5F;
/*     */         }
/*     */         
/* 481 */         float f8 = (float)(x + offset);
/* 482 */         float f9 = f8 - (float)ActiveRenderInfo.getPosition().xCoord;
/* 483 */         float f10 = f8 + f5 - (float)ActiveRenderInfo.getPosition().xCoord;
/* 484 */         float f11 = f9 / f10;
/* 485 */         f11 = (float)(x + offset) + f11;
/* 486 */         GL11.glTranslatef(f11, py, pz);
/* 487 */         GL11.glTexGeni(8192, 9472, 9217);
/* 488 */         GL11.glTexGeni(8193, 9472, 9217);
/* 489 */         GL11.glTexGeni(8194, 9472, 9217);
/* 490 */         GL11.glTexGeni(8195, 9472, 9216);
/*     */         
/*     */ 
/* 493 */         GL11.glTexGen(8193, 9473, calcFloatBuffer(0.0F, 1.0F, 0.0F, 0.0F));
/* 494 */         GL11.glTexGen(8192, 9473, calcFloatBuffer(0.0F, 0.0F, 1.0F, 0.0F));
/* 495 */         GL11.glTexGen(8194, 9473, calcFloatBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/* 496 */         GL11.glTexGen(8195, 9474, calcFloatBuffer(1.0F, 0.0F, 0.0F, 0.0F));
/* 497 */         GL11.glEnable(3168);
/* 498 */         GL11.glEnable(3169);
/* 499 */         GL11.glEnable(3170);
/* 500 */         GL11.glEnable(3171);
/* 501 */         GL11.glPopMatrix();
/* 502 */         GL11.glMatrixMode(5890);
/* 503 */         GL11.glPushMatrix();
/* 504 */         GL11.glLoadIdentity();
/* 505 */         GL11.glTranslatef(0.0F, (float)(System.currentTimeMillis() % 700000L) / 250000.0F, 0.0F);
/* 506 */         GL11.glScalef(f6, f6, f6);
/* 507 */         GL11.glTranslatef(0.5F, 0.5F, 0.0F);
/* 508 */         GL11.glRotatef((i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
/* 509 */         GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
/* 510 */         GL11.glTranslatef(-pz, -py, -px);
/*     */         
/*     */ 
/* 513 */         GL11.glTranslatef((float)ActiveRenderInfo.getPosition().zCoord * f5 / f9, (float)ActiveRenderInfo.getPosition().yCoord * f5 / f9, -px);
/*     */         
/*     */ 
/* 516 */         Tessellator tessellator = Tessellator.getInstance();
/* 517 */         tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 518 */         f11 = random.nextFloat() * 0.5F + 0.1F;
/* 519 */         float f12 = random.nextFloat() * 0.5F + 0.4F;
/* 520 */         float f13 = random.nextFloat() * 0.5F + 0.5F;
/* 521 */         if (i == 0)
/*     */         {
/* 523 */           f11 = f12 = f13 = 1.0F;
/*     */         }
/*     */         
/*     */ 
/* 527 */         tessellator.getWorldRenderer().pos(x + offset, y, z).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 528 */         tessellator.getWorldRenderer().pos(x + offset, y, z + 1.0D).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 529 */         tessellator.getWorldRenderer().pos(x + offset, y + height, z + 1.0D).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/* 530 */         tessellator.getWorldRenderer().pos(x + offset, y + height, z).color(f11 * f7, f12 * f7, f13 * f7, 1.0F).endVertex();
/*     */         
/* 532 */         tessellator.draw();
/* 533 */         GL11.glPopMatrix();
/* 534 */         GL11.glMatrixMode(5888);
/*     */       }
/* 536 */     } else { GL11.glPushMatrix();
/* 537 */       bindTexture(t3);
/* 538 */       Tessellator tessellator = Tessellator.getInstance();
/* 539 */       tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*     */       
/*     */ 
/* 542 */       tessellator.getWorldRenderer().pos(x + offset, y, z).tex(1.0D, 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 543 */       tessellator.getWorldRenderer().pos(x + offset, y, z + 1.0D).tex(1.0D, 0.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 544 */       tessellator.getWorldRenderer().pos(x + offset, y + height, z + 1.0D).tex(0.0D, 0.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/* 545 */       tessellator.getWorldRenderer().pos(x + offset, y + height, z).tex(0.0D, 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).endVertex();
/*     */       
/* 547 */       tessellator.draw();
/* 548 */       GL11.glPopMatrix();
/*     */     }
/*     */     
/* 551 */     GL11.glDisable(3042);
/* 552 */     GL11.glDisable(3168);
/* 553 */     GL11.glDisable(3169);
/* 554 */     GL11.glDisable(3170);
/* 555 */     GL11.glDisable(3171);
/* 556 */     GL11.glEnable(2896);
/*     */   }
/*     */   
/*     */   private FloatBuffer calcFloatBuffer(float f, float f1, float f2, float f3)
/*     */   {
/* 561 */     this.fBuffer.clear();
/* 562 */     this.fBuffer.put(f).put(f1).put(f2).put(f3);
/* 563 */     this.fBuffer.flip();
/* 564 */     return this.fBuffer;
/*     */   }
/*     */   
/*     */ 
/*     */   public void renderSide(int h)
/*     */   {
/* 570 */     Tessellator tessellator = Tessellator.getInstance();
/* 571 */     GL11.glEnable(32826);
/* 572 */     GL11.glEnable(3042);
/* 573 */     GL11.glBlendFunc(770, 771);
/* 574 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 575 */     tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
/* 576 */     tessellator.getWorldRenderer().pos(-0.5D, h, 0.0D).tex(0.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 577 */     tessellator.getWorldRenderer().pos(0.5D, h, 0.0D).tex(1.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 578 */     tessellator.getWorldRenderer().pos(0.5D, 0.0D, 0.0D).tex(1.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 579 */     tessellator.getWorldRenderer().pos(-0.5D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 580 */     tessellator.draw();
/* 581 */     GL11.glDisable(3042);
/* 582 */     GL11.glDisable(32826);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileEldritchObeliskRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */