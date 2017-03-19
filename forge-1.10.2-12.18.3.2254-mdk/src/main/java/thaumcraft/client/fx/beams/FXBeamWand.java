/*     */ package thaumcraft.client.fx.beams;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ 
/*     */ public class FXBeamWand extends Particle
/*     */ {
/*  23 */   public int particle = 16;
/*  24 */   EntityLivingBase sourceEntity = null;
/*  25 */   private double offset = 0.0D;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FXBeamWand(World par1World, EntityLivingBase p, double tx, double ty, double tz, float red, float green, float blue, int age)
/*     */   {
/*  33 */     super(par1World, p.posX, p.posY, p.posZ, 0.0D, 0.0D, 0.0D);
/*     */     
/*     */ 
/*  36 */     this.offset = (p.height / 2.0F + 0.25D);
/*     */     
/*  38 */     this.particleRed = red;
/*  39 */     this.particleGreen = green;
/*  40 */     this.particleBlue = blue;
/*  41 */     this.sourceEntity = p;
/*  42 */     setSize(0.02F, 0.02F);
/*  43 */     this.canCollide = false;
/*  44 */     this.motionX = 0.0D;
/*  45 */     this.motionY = 0.0D;
/*  46 */     this.motionZ = 0.0D;
/*  47 */     this.tX = tx;
/*  48 */     this.tY = ty;
/*  49 */     this.tZ = tz;
/*  50 */     float xd = (float)(p.posX - this.tX);
/*  51 */     float yd = (float)(p.posY + this.offset - this.tY);
/*  52 */     float zd = (float)(p.posZ - this.tZ);
/*  53 */     this.length = MathHelper.sqrt_float(xd * xd + yd * yd + zd * zd);
/*  54 */     double var7 = MathHelper.sqrt_double(xd * xd + zd * zd);
/*  55 */     this.rotYaw = ((float)(Math.atan2(xd, zd) * 180.0D / 3.141592653589793D));
/*  56 */     this.rotPitch = ((float)(Math.atan2(yd, var7) * 180.0D / 3.141592653589793D));
/*  57 */     this.prevYaw = this.rotYaw;
/*  58 */     this.prevPitch = this.rotPitch;
/*     */     
/*  60 */     this.particleMaxAge = age;
/*     */     
/*  62 */     Entity renderentity = FMLClientHandler.instance().getClient().getRenderViewEntity();
/*  63 */     int visibleDistance = 50;
/*  64 */     if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics) visibleDistance = 25;
/*  65 */     if (renderentity.getDistance(p.posX, p.posY, p.posZ) > visibleDistance) { this.particleMaxAge = 0;
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateBeam(double x, double y, double z)
/*     */   {
/* 109 */     this.tX = x;
/* 110 */     this.tY = y;
/* 111 */     this.tZ = z;
/* 112 */     while (this.particleMaxAge - this.particleAge < 4) { this.particleMaxAge += 1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 122 */     this.prevPosX = this.sourceEntity.posX;
/* 123 */     this.prevPosY = (this.sourceEntity.posY + this.offset);
/* 124 */     this.prevPosZ = this.sourceEntity.posZ;
/* 125 */     this.ptX = this.tX;
/* 126 */     this.ptY = this.tY;
/* 127 */     this.ptZ = this.tZ;
/*     */     
/* 129 */     this.prevYaw = this.rotYaw;
/* 130 */     this.prevPitch = this.rotPitch;
/*     */     
/* 132 */     float xd = (float)(this.sourceEntity.posX - this.tX);
/* 133 */     float yd = (float)(this.sourceEntity.posY + this.offset - this.tY);
/* 134 */     float zd = (float)(this.sourceEntity.posZ - this.tZ);
/*     */     
/* 136 */     this.length = MathHelper.sqrt_float(xd * xd + yd * yd + zd * zd);
/*     */     
/* 138 */     double var7 = MathHelper.sqrt_double(xd * xd + zd * zd);
/*     */     
/* 140 */     this.rotYaw = ((float)(Math.atan2(xd, zd) * 180.0D / 3.141592653589793D));
/*     */     
/*     */ 
/*     */ 
/* 144 */     for (this.rotPitch = ((float)(Math.atan2(yd, var7) * 180.0D / 3.141592653589793D)); this.rotPitch - this.prevPitch < -180.0F; this.prevPitch -= 360.0F) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 149 */     while (this.rotPitch - this.prevPitch >= 180.0F)
/*     */     {
/* 151 */       this.prevPitch += 360.0F;
/*     */     }
/*     */     
/* 154 */     while (this.rotYaw - this.prevYaw < -180.0F)
/*     */     {
/* 156 */       this.prevYaw -= 360.0F;
/*     */     }
/*     */     
/* 159 */     while (this.rotYaw - this.prevYaw >= 180.0F)
/*     */     {
/* 161 */       this.prevYaw += 360.0F;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 166 */     if (this.impact > 0) { this.impact -= 1;
/*     */     }
/* 168 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/* 170 */       setExpired();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setRGB(float r, float g, float b) {
/* 175 */     this.particleRed = r;
/* 176 */     this.particleGreen = g;
/* 177 */     this.particleBlue = b;
/*     */   }
/*     */   
/* 180 */   private float length = 0.0F;
/* 181 */   private float rotYaw = 0.0F;
/* 182 */   private float rotPitch = 0.0F;
/* 183 */   private float prevYaw = 0.0F;
/* 184 */   private float prevPitch = 0.0F;
/* 185 */   private Entity targetEntity = null;
/* 186 */   private double tX = 0.0D;
/* 187 */   private double tY = 0.0D;
/* 188 */   private double tZ = 0.0D;
/* 189 */   private double ptX = 0.0D;
/* 190 */   private double ptY = 0.0D;
/* 191 */   private double ptZ = 0.0D;
/*     */   
/* 193 */   private int type = 0;
/*     */   
/* 195 */   public void setType(int type) { this.type = type; }
/*     */   
/*     */ 
/* 198 */   private float endMod = 1.0F;
/*     */   
/* 200 */   public void setEndMod(float endMod) { this.endMod = endMod; }
/*     */   
/*     */ 
/* 203 */   private boolean reverse = false;
/*     */   
/* 205 */   public void setReverse(boolean reverse) { this.reverse = reverse; }
/*     */   
/*     */ 
/* 208 */   private boolean pulse = true;
/*     */   
/* 210 */   public void setPulse(boolean pulse) { this.pulse = pulse; }
/*     */   
/*     */ 
/* 213 */   private int rotationspeed = 5;
/*     */   
/* 215 */   public void setRotationspeed(int rotationspeed) { this.rotationspeed = rotationspeed; }
/*     */   
/*     */ 
/* 218 */   private float prevSize = 0.0F;
/*     */   
/*     */   public int impact;
/* 221 */   ResourceLocation beam = new ResourceLocation("thaumcraft", "textures/misc/beam.png");
/* 222 */   ResourceLocation beam1 = new ResourceLocation("thaumcraft", "textures/misc/beam1.png");
/* 223 */   ResourceLocation beam2 = new ResourceLocation("thaumcraft", "textures/misc/beam2.png");
/* 224 */   ResourceLocation beam3 = new ResourceLocation("thaumcraft", "textures/misc/beam3.png");
/*     */   
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity p_180434_2_, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 229 */     Tessellator.getInstance().draw();
/*     */     
/* 231 */     GL11.glPushMatrix();
/* 232 */     float var9 = 1.0F;
/* 233 */     float slide = Minecraft.getMinecraft().thePlayer.ticksExisted;
/* 234 */     float rot = (float)(this.worldObj.provider.getWorldTime() % (360 / this.rotationspeed) * this.rotationspeed) + this.rotationspeed * f;
/*     */     
/* 236 */     float size = 1.0F;
/* 237 */     if (this.pulse) {
/* 238 */       size = Math.min(this.particleAge / 4.0F, 1.0F);
/* 239 */       size = (float)(this.prevSize + (size - this.prevSize) * f);
/*     */     }
/*     */     
/* 242 */     float op = 0.4F;
/* 243 */     if ((this.pulse) && (this.particleMaxAge - this.particleAge <= 4)) {
/* 244 */       op = 0.4F - (4 - (this.particleMaxAge - this.particleAge)) * 0.1F;
/*     */     }
/* 246 */     switch (this.type) {
/* 247 */     default:  Minecraft.getMinecraft().renderEngine.bindTexture(this.beam); break;
/* 248 */     case 1:  Minecraft.getMinecraft().renderEngine.bindTexture(this.beam1); break;
/* 249 */     case 2:  Minecraft.getMinecraft().renderEngine.bindTexture(this.beam2); break;
/* 250 */     case 3:  Minecraft.getMinecraft().renderEngine.bindTexture(this.beam3);
/*     */     }
/*     */     
/* 253 */     GL11.glTexParameterf(3553, 10242, 10497.0F);
/* 254 */     GL11.glTexParameterf(3553, 10243, 10497.0F);
/*     */     
/* 256 */     GL11.glDisable(2884);
/*     */     
/* 258 */     float var11 = slide + f;
/* 259 */     if (this.reverse) var11 *= -1.0F;
/* 260 */     float var12 = -var11 * 0.2F - MathHelper.floor_float(-var11 * 0.1F);
/*     */     
/* 262 */     GL11.glEnable(3042);
/* 263 */     GL11.glBlendFunc(770, 1);
/* 264 */     GL11.glDepthMask(false);
/*     */     
/* 266 */     double prex = this.sourceEntity.prevPosX;
/* 267 */     double prey = this.sourceEntity.prevPosY + this.offset;
/* 268 */     double prez = this.sourceEntity.prevPosZ;
/* 269 */     double px = this.sourceEntity.posX;
/* 270 */     double py = this.sourceEntity.posY + this.offset;
/* 271 */     double pz = this.sourceEntity.posZ;
/*     */     
/* 273 */     prex -= MathHelper.cos(this.sourceEntity.prevRotationYaw / 180.0F * 3.141593F) * 0.066F;
/* 274 */     prey -= 0.06D;
/* 275 */     prez -= MathHelper.sin(this.sourceEntity.prevRotationYaw / 180.0F * 3.141593F) * 0.04F;
/* 276 */     Vec3d vec3d = this.sourceEntity.getLook(1.0F);
/* 277 */     prex += vec3d.xCoord * 0.3D;
/* 278 */     prey += vec3d.yCoord * 0.3D;
/* 279 */     prez += vec3d.zCoord * 0.3D;
/*     */     
/* 281 */     px -= MathHelper.cos(this.sourceEntity.rotationYaw / 180.0F * 3.141593F) * 0.066F;
/* 282 */     py -= 0.06D;
/* 283 */     pz -= MathHelper.sin(this.sourceEntity.rotationYaw / 180.0F * 3.141593F) * 0.04F;
/* 284 */     vec3d = this.sourceEntity.getLook(1.0F);
/* 285 */     px += vec3d.xCoord * 0.3D;
/* 286 */     py += vec3d.yCoord * 0.3D;
/* 287 */     pz += vec3d.zCoord * 0.3D;
/*     */     
/* 289 */     float xx = (float)(prex + (px - prex) * f - interpPosX);
/* 290 */     float yy = (float)(prey + (py - prey) * f - interpPosY);
/* 291 */     float zz = (float)(prez + (pz - prez) * f - interpPosZ);
/* 292 */     GL11.glTranslated(xx, yy, zz);
/*     */     
/* 294 */     float ry = (float)(this.prevYaw + (this.rotYaw - this.prevYaw) * f);
/* 295 */     float rp = (float)(this.prevPitch + (this.rotPitch - this.prevPitch) * f);
/* 296 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 297 */     GL11.glRotatef(180.0F + ry, 0.0F, 0.0F, -1.0F);
/* 298 */     GL11.glRotatef(rp, 1.0F, 0.0F, 0.0F);
/*     */     
/* 300 */     double var44 = -0.15D * size;
/* 301 */     double var17 = 0.15D * size;
/* 302 */     double var44b = -0.15D * size * this.endMod;
/* 303 */     double var17b = 0.15D * size * this.endMod;
/* 304 */     int i = 200;
/* 305 */     int j = i >> 16 & 0xFFFF;
/* 306 */     int k = i & 0xFFFF;
/*     */     
/* 308 */     GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
/* 309 */     for (int t = 0; t < 3; t++)
/*     */     {
/* 311 */       double var29 = this.length * size * var9;
/* 312 */       double var31 = 0.0D;
/* 313 */       double var33 = 1.0D;
/* 314 */       double var35 = -1.0F + var12 + t / 3.0F;
/* 315 */       double var37 = this.length * size * var9 + var35;
/*     */       
/* 317 */       GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
/* 318 */       wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/* 319 */       wr.pos(var44b, var29, 0.0D).tex(var33, var37).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
/* 320 */       wr.pos(var44, 0.0D, 0.0D).tex(var33, var35).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
/* 321 */       wr.pos(var17, 0.0D, 0.0D).tex(var31, var35).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
/* 322 */       wr.pos(var17b, var29, 0.0D).tex(var31, var37).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
/* 323 */       Tessellator.getInstance().draw();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 328 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 329 */     GL11.glDepthMask(true);
/* 330 */     GL11.glBlendFunc(770, 771);
/* 331 */     GL11.glDisable(3042);
/* 332 */     GL11.glEnable(2884);
/*     */     
/* 334 */     GL11.glPopMatrix();
/*     */     
/*     */ 
/* 337 */     if (this.impact > 0) { renderImpact(Tessellator.getInstance(), f, f1, f2, f3, f4, f5);
/*     */     }
/* 339 */     Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getMCParticleTexture());
/* 340 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/* 341 */     this.prevSize = size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderImpact(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 348 */     GL11.glPushMatrix();
/* 349 */     GL11.glDepthMask(false);
/* 350 */     GL11.glEnable(3042);
/* 351 */     GL11.glBlendFunc(770, 1);
/*     */     
/* 353 */     Minecraft.getMinecraft().renderEngine.bindTexture(thaumcraft.client.fx.ParticleEngine.particleTexture);
/*     */     
/* 355 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.66F);
/* 356 */     int part = this.particleAge % 16;
/*     */     
/* 358 */     float var8 = part / 16.0F;
/* 359 */     float var9 = var8 + 0.0624375F;
/* 360 */     float var10 = 0.3125F;
/* 361 */     float var11 = var10 + 0.0624375F;
/* 362 */     float var12 = this.endMod / 2.0F / (6 - this.impact);
/*     */     
/* 364 */     float var13 = (float)(this.ptX + (this.tX - this.ptX) * f - interpPosX);
/* 365 */     float var14 = (float)(this.ptY + (this.tY - this.ptY) * f - interpPosY);
/* 366 */     float var15 = (float)(this.ptZ + (this.tZ - this.ptZ) * f - interpPosZ);
/* 367 */     float var16 = 1.0F;
/*     */     
/* 369 */     int i = 200;
/* 370 */     int j = i >> 16 & 0xFFFF;
/* 371 */     int k = i & 0xFFFF;
/*     */     
/* 373 */     tessellator.getBuffer().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/* 374 */     tessellator.getBuffer().pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
/* 375 */     tessellator.getBuffer().pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
/* 376 */     tessellator.getBuffer().pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
/* 377 */     tessellator.getBuffer().pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
/*     */     
/* 379 */     tessellator.draw();
/* 380 */     GL11.glBlendFunc(770, 771);
/* 381 */     GL11.glDisable(3042);
/* 382 */     GL11.glDepthMask(true);
/* 383 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\beams\FXBeamWand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */