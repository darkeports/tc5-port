/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.common.lib.utils.TCVec3;
/*     */ 
/*     */ public class FXGeneric extends Particle
/*     */ {
/*     */   public FXGeneric(World world, double x, double y, double z, double xx, double yy, double zz)
/*     */   {
/*  21 */     super(world, x, y, z, xx, yy, zz);
/*  22 */     setSize(0.1F, 0.1F);
/*  23 */     setPosition(x, y, z);
/*  24 */     this.prevPosX = this.posX;
/*  25 */     this.prevPosY = this.posY;
/*  26 */     this.prevPosZ = this.posZ;
/*  27 */     
/*  28 */     
/*  29 */     
/*  30 */     this.particleTextureJitterX = 0.0F;
/*  31 */     this.particleTextureJitterY = 0.0F;
/*  32 */     this.motionX = xx;
/*  33 */     this.motionY = yy;
/*  34 */     this.motionZ = zz;
/*  35 */     this.canCollide = false;
/*     */   }
/*     */   
/*     */ 
/*  39 */   int layer = 0;
/*     */   
/*     */   public void setLayer(int layer)
/*     */   {
/*  43 */     this.layer = layer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setRBGColorF(float particleRedIn, float particleGreenIn, float particleBlueIn)
/*     */   {
/*  50 */     super.setRBGColorF(particleRedIn, particleGreenIn, particleBlueIn);
/*  51 */     this.dr = particleRedIn;
/*  52 */     this.dg = particleGreenIn;
/*  53 */     this.db = particleBlueIn;
/*     */   }
/*     */   
/*     */   public void setRBGColorF(float particleRedIn, float particleGreenIn, float particleBlueIn, float r2, float g2, float b2) {
/*  57 */     super.setRBGColorF(particleRedIn, particleGreenIn, particleBlueIn);
/*  58 */     this.dr = r2;
/*  59 */     this.dg = g2;
/*  60 */     this.db = b2;
/*     */   }
/*     */   
/*  63 */   float dr = 0.0F;
/*  64 */   float dg = 0.0F;
/*  65 */   float db = 0.0F;
/*     */   
/*     */ 
/*     */   public int getFXLayer()
/*     */   {
/*  70 */     return this.layer;
/*     */   }
/*     */   
/*  73 */   boolean loop = false;
/*     */   
/*     */   public void setLoop(boolean loop) {
/*  76 */     this.loop = loop;
/*     */   }
/*     */   
/*  79 */   protected float particleScaleMod = 0.0F;
/*     */   
/*     */   public void setScale(float scale) {
/*  82 */     this.particleScale = scale;
/*     */   }
/*     */   
/*     */   public void setScale(float scale, float end) {
/*  86 */     this.particleScale = scale;
/*  87 */     this.particleScaleMod = ((scale - end) / this.particleMaxAge);
/*     */   }
/*     */   
/*     */   public void setRotationSpeed(float rot) {
/*  91 */     this.rotationSpeed = rot;
/*     */   }
/*     */   
/*     */   public void setRotationSpeed(float start, float rot) {
/*  95 */     this.rotation = start;
/*  96 */     this.rotationSpeed = rot;
/*     */   }
/*     */   
/*  99 */   float rotationSpeed = 0.0F;
/* 100 */   float rotation = 0.0F;
/*     */   
/* 102 */   int delay = 0;
/*     */   
/*     */   public void setMaxAge(int max, int delay) {
/* 105 */     this.particleMaxAge = max;
/* 106 */     this.particleMaxAge += delay;
/* 107 */     this.delay = delay;
/*     */   }
/*     */   
/* 110 */   int startParticle = 0;
/* 111 */   int numParticles = 1;
/* 112 */   int particleInc = 1;
/*     */   
/*     */   public void setParticles(int startParticle, int numParticles, int particleInc) {
/* 115 */     this.startParticle = startParticle;
/* 116 */     this.numParticles = numParticles;
/* 117 */     this.particleInc = particleInc;
/* 118 */     setParticleTextureIndex(startParticle);
/*     */   }
/*     */   
/* 121 */   float particleAlphaMod = 0.0F;
/*     */   
/*     */   public void setAlphaF(float a1)
/*     */   {
/* 125 */     super.setAlphaF(a1);
/* 126 */     this.particleAlphaMod = 0.0F;
/*     */   }
/*     */   
/*     */   public void setAlphaF(float a1, float a2) {
/* 130 */     super.setAlphaF(a1);
/* 131 */     this.particleAlphaMod = ((a1 - a2) / this.particleMaxAge);
/*     */   }
/*     */   
/* 134 */   double slowDown = 0.9800000190734863D;
/*     */   
/*     */   public void setSlowDown(double slowDown) {
/* 137 */     this.slowDown = slowDown;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 143 */     this.prevPosX = this.posX;
/* 144 */     this.prevPosY = this.posY;
/* 145 */     this.prevPosZ = this.posZ;
/*     */     
/* 147 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/* 149 */       setExpired();
/*     */     }
/*     */     
/* 152 */     this.rotation += this.rotationSpeed;
/*     */     
/* 154 */     this.motionY -= 0.04D * this.particleGravity;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 160 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/* 162 */     this.motionX *= this.slowDown;
/* 163 */     this.motionY *= this.slowDown;
/* 164 */     this.motionZ *= this.slowDown;
/*     */     
/*     */ 
/* 167 */     this.motionX += this.windX;
/* 168 */     this.motionZ += this.windZ;
/*     */     //TODO:Change from is collied to something that actually checks if its on the ground
/* 170 */     if ((this.isCollided) && (this.slowDown != 1.0D))
/*     */     {
/* 172 */       this.motionX *= 0.699999988079071D;
/* 173 */       this.motionZ *= 0.699999988079071D;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 178 */   boolean angled = false;
/*     */   
/* 180 */   public void setAngles(float yaw, float pitch) { this.angleYaw = yaw;
/* 181 */     this.anglePitch = pitch;
/* 182 */     this.angled = true;
/*     */   }
/*     */   
/*     */   float angleYaw;
/*     */   float anglePitch;
/*     */   public void setGravity(float g)
/*     */   {
/* 189 */     this.particleGravity = g;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setParticleTextureIndex(int p_70536_1_)
/*     */   {
/* 195 */     this.particleTextureIndexX = (p_70536_1_ % this.gridSize);
/* 196 */     this.particleTextureIndexY = (p_70536_1_ / this.gridSize);
/*     */   }
/*     */   
/* 199 */   int gridSize = 16;
/*     */   double windX;
/*     */   
/* 202 */   public void setGridSize(int gridSize) { this.gridSize = gridSize; }
/*     */   
/*     */ 
/*     */   double windZ;
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 208 */     if (this.loop) {
/* 209 */       setParticleTextureIndex(this.startParticle + this.particleAge / this.particleInc % this.numParticles);
/*     */     } else {
/* 211 */       float fs = this.particleAge / this.particleMaxAge;
/* 212 */       setParticleTextureIndex((int)(this.startParticle + Math.min(this.numParticles * fs, this.numParticles - 1)));
/*     */     }
/*     */     
/* 215 */     if (this.particleAge < this.delay) return;
/* 216 */     this.particleAlpha -= this.particleAlphaMod;
/* 217 */     float t = this.particleAlpha;
/* 218 */     if ((this.particleAge <= 1) || (this.particleAge >= this.particleMaxAge - 1)) this.particleAlpha = (t / 2.0F);
/* 219 */     if (this.particleAlpha < 0.0F) this.particleAlpha = 0.0F;
/* 220 */     if (this.particleAlpha > 1.0F) { this.particleAlpha = 1.0F;
/*     */     }
/* 222 */     this.particleScale -= this.particleScaleMod;
/* 223 */     if (this.particleScale < 0.0F) { this.particleScale = 0.0F;
/*     */     }
/* 225 */     if (this.depthIgnore) GlStateManager.disableDepth();
/* 226 */     draw(wr, entity, f, f1, f2, f3, f4, f5);
/* 227 */     if (this.depthIgnore) { GlStateManager.enableDepth();
/*     */     }
/* 229 */     this.particleAlpha = t;
/*     */   }
/*     */   
/*     */   public void draw(VertexBuffer wr, Entity p_180434_2_, float pticks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
/*     */   {
/* 234 */     float f6 = this.particleTextureIndexX / this.gridSize;
/* 235 */     float f7 = f6 + 1.0F / this.gridSize;
/* 236 */     float f8 = this.particleTextureIndexY / this.gridSize;
/* 237 */     float f9 = f8 + 1.0F / this.gridSize;
/* 238 */     float f10 = 0.1F * this.particleScale;
/*     */     
/* 240 */     if (this.particleTexture != null)
/*     */     {
/* 242 */       f6 = this.particleTexture.getMinU();
/* 243 */       f7 = this.particleTexture.getMaxU();
/* 244 */       f8 = this.particleTexture.getMinV();
/* 245 */       f9 = this.particleTexture.getMaxV();
/*     */     }
/*     */     
/* 248 */     Tessellator.getInstance().draw();
/* 249 */     GL11.glPushMatrix();
/*     */     
/* 251 */     float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * pticks - interpPosX);
/* 252 */     float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * pticks - interpPosY);
/* 253 */     float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * pticks - interpPosZ);
/* 254 */     GL11.glTranslated(f11, f12, f13);
/*     */     
/* 256 */     GL11.glPushMatrix();
/*     */     
/* 258 */     float fs = MathHelper.clamp_float((this.particleAge + pticks) / this.particleMaxAge, 0.0F, 1.0F);
/* 259 */     float pr = this.particleRed + (this.dr - this.particleRed) * fs;
/* 260 */     float pg = this.particleGreen + (this.dg - this.particleGreen) * fs;
/* 261 */     float pb = this.particleBlue + (this.db - this.particleBlue) * fs;
/*     */     
/* 263 */     if (this.angled) {
/* 264 */       GL11.glRotatef(-this.angleYaw + 90.0F, 0.0F, 1.0F, 0.0F);
/* 265 */       GL11.glRotatef(this.anglePitch + 90.0F, 1.0F, 0.0F, 0.0F);
/*     */     } else {
/* 267 */       UtilsFX.rotateToPlayer();
/*     */     }
/*     */     
/* 270 */     GL11.glRotatef(this.rotation + pticks * this.rotationSpeed, 0.0F, 0.0F, 1.0F);
/* 271 */     int i = getBrightnessForRender(pticks);
/* 272 */     int j = i >> 16 & 0xFFFF;
/* 273 */     int k = i & 0xFFFF;
/* 274 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/* 275 */     wr.pos(-f10, -f10, 0.0D).tex(f7, f9).color(pr, pg, pb, this.particleAlpha).lightmap(j, k).endVertex();
/* 276 */     wr.pos(-f10, f10, 0.0D).tex(f7, f8).color(pr, pg, pb, this.particleAlpha).lightmap(j, k).endVertex();
/* 277 */     wr.pos(f10, f10, 0.0D).tex(f6, f8).color(pr, pg, pb, this.particleAlpha).lightmap(j, k).endVertex();
/* 278 */     wr.pos(f10, -f10, 0.0D).tex(f6, f9).color(pr, pg, pb, this.particleAlpha).lightmap(j, k).endVertex();
/* 279 */     Tessellator.getInstance().draw();
/*     */     
/* 281 */     GL11.glPopMatrix();
/*     */     
/* 283 */     GL11.glPopMatrix();
/* 284 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setWind(double d)
/*     */   {
/* 290 */     int m = this.worldObj.getMoonPhase();
/* 291 */     TCVec3 vsource = TCVec3.createVectorHelper(0.0D, 0.0D, 0.0D);
/* 292 */     TCVec3 vtar = TCVec3.createVectorHelper(0.1D, 0.0D, 0.0D);
/* 293 */     vtar.rotateAroundY(m * (40 + this.worldObj.rand.nextInt(10)) / 180.0F * 3.1415927F);
/* 294 */     TCVec3 vres = vsource.addVector(vtar.xCoord, vtar.yCoord, vtar.zCoord);
/* 295 */     this.windX = (vres.xCoord * d);
/* 296 */     this.windZ = (vres.zCoord * d);
/*     */   }
/*     */   
/* 299 */   boolean depthIgnore = false;
/*     */   
/*     */   public void setDepthIgnore(boolean b) {
/* 302 */     this.depthIgnore = b;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXGeneric.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */