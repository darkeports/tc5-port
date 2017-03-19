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
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ 
/*     */ public class FXBeamBore extends Particle
/*     */ {
/*  21 */   public int particle = 16;
/*     */   
/*  23 */   private double offset = 0.0D;
/*     */   
/*  25 */   private double tX = 0.0D;
/*  26 */   private double tY = 0.0D;
/*  27 */   private double tZ = 0.0D;
/*  28 */   private double ptX = 0.0D;
/*  29 */   private double ptY = 0.0D;
/*  30 */   private double ptZ = 0.0D;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FXBeamBore(World par1World, double px, double py, double pz, double tx, double ty, double tz, float red, float green, float blue, int age)
/*     */   {
/*  38 */     super(par1World, px, py, pz, 0.0D, 0.0D, 0.0D);
/*     */     
/*     */ 
/*  41 */     this.particleRed = red;
/*  42 */     this.particleGreen = green;
/*  43 */     this.particleBlue = blue;
/*  44 */     setSize(0.02F, 0.02F);
/*  45 */     this.canCollide = false;
/*  46 */     this.motionX = 0.0D;
/*  47 */     this.motionY = 0.0D;
/*  48 */     this.motionZ = 0.0D;
/*  49 */     this.tX = tx;
/*  50 */     this.tY = ty;
/*  51 */     this.tZ = tz;
/*  52 */     this.prevYaw = this.rotYaw;
/*  53 */     this.prevPitch = this.rotPitch;
/*  54 */     this.particleMaxAge = age;
/*     */     
/*  56 */     Entity renderentity = FMLClientHandler.instance().getClient().getRenderViewEntity();
/*  57 */     int visibleDistance = 64;
/*  58 */     if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics) visibleDistance = 32;
/*  59 */     if ((renderentity != null) && (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance)) this.particleMaxAge = 0;
/*     */   }
/*     */   
/*     */   public void updateBeam(double x, double y, double z)
/*     */   {
/*  64 */     this.tX = x;
/*  65 */     this.tY = y;
/*  66 */     this.tZ = z;
/*  67 */     while (this.particleMaxAge - this.particleAge < 4) { this.particleMaxAge += 1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  77 */     this.prevPosX = this.posX;
/*  78 */     this.prevPosY = (this.posY + this.offset);
/*  79 */     this.prevPosZ = this.posZ;
/*     */     
/*  81 */     this.ptX = this.tX;
/*  82 */     this.ptY = this.tY;
/*  83 */     this.ptZ = this.tZ;
/*     */     
/*  85 */     this.prevYaw = this.rotYaw;
/*  86 */     this.prevPitch = this.rotPitch;
/*     */     
/*  88 */     float xd = (float)(this.posX - this.tX);
/*  89 */     float yd = (float)(this.posY - this.tY);
/*  90 */     float zd = (float)(this.posZ - this.tZ);
/*  91 */     this.length = MathHelper.sqrt_float(xd * xd + yd * yd + zd * zd);
/*  92 */     double var7 = MathHelper.sqrt_double(xd * xd + zd * zd);
/*  93 */     this.rotYaw = ((float)(Math.atan2(xd, zd) * 180.0D / 3.141592653589793D));
/*  94 */     this.rotPitch = ((float)(Math.atan2(yd, var7) * 180.0D / 3.141592653589793D));
/*  95 */     this.prevYaw = this.rotYaw;
/*  96 */     this.prevPitch = this.rotPitch;
/*     */     
/*  98 */     if (this.impact > 0) { this.impact -= 1;
/*     */     }
/* 100 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/* 102 */       setExpired();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setRGB(float r, float g, float b) {
/* 107 */     this.particleRed = r;
/* 108 */     this.particleGreen = g;
/* 109 */     this.particleBlue = b;
/*     */   }
/*     */   
/* 112 */   private float length = 0.0F;
/* 113 */   private float rotYaw = 0.0F;
/* 114 */   private float rotPitch = 0.0F;
/* 115 */   private float prevYaw = 0.0F;
/* 116 */   private float prevPitch = 0.0F;
/* 117 */   private Entity targetEntity = null;
/*     */   
/* 119 */   private int type = 0;
/*     */   
/* 121 */   public void setType(int type) { this.type = type; }
/*     */   
/*     */ 
/* 124 */   private float endMod = 1.0F;
/*     */   
/* 126 */   public void setEndMod(float endMod) { this.endMod = endMod; }
/*     */   
/*     */ 
/* 129 */   private boolean reverse = false;
/*     */   
/* 131 */   public void setReverse(boolean reverse) { this.reverse = reverse; }
/*     */   
/*     */ 
/* 134 */   private boolean pulse = true;
/*     */   
/* 136 */   public void setPulse(boolean pulse) { this.pulse = pulse; }
/*     */   
/*     */ 
/* 139 */   private int rotationspeed = 5;
/*     */   
/* 141 */   public void setRotationspeed(int rotationspeed) { this.rotationspeed = rotationspeed; }
/*     */   
/*     */ 
/* 144 */   private float prevSize = 0.0F;
/*     */   
/*     */   public int impact;
/* 147 */   ResourceLocation beam = new ResourceLocation("thaumcraft", "textures/misc/beam.png");
/* 148 */   ResourceLocation beam1 = new ResourceLocation("thaumcraft", "textures/misc/beam1.png");
/* 149 */   ResourceLocation beam2 = new ResourceLocation("thaumcraft", "textures/misc/beam2.png");
/* 150 */   ResourceLocation beam3 = new ResourceLocation("thaumcraft", "textures/misc/beam3.png");
/*     */   
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity p_180434_2_, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 155 */     Tessellator.getInstance().draw();
/*     */     
/* 157 */     GL11.glPushMatrix();
/* 158 */     float var9 = 1.0F;
/* 159 */     float slide = Minecraft.getMinecraft().thePlayer.ticksExisted;
/* 160 */     float rot = (float)(this.worldObj.provider.getWorldTime() % (360 / this.rotationspeed) * this.rotationspeed) + this.rotationspeed * f;
/*     */     
/* 162 */     float size = 1.0F;
/* 163 */     if (this.pulse) {
/* 164 */       size = Math.min(this.particleAge / 4.0F, 1.0F);
/* 165 */       size = (float)(this.prevSize + (size - this.prevSize) * f);
/*     */     }
/*     */     
/* 168 */     float op = 0.4F;
/* 169 */     if ((this.pulse) && (this.particleMaxAge - this.particleAge <= 4)) {
/* 170 */       op = 0.4F - (4 - (this.particleMaxAge - this.particleAge)) * 0.1F;
/*     */     }
/*     */     
/* 173 */     switch (this.type) {
/* 174 */     default:  Minecraft.getMinecraft().renderEngine.bindTexture(this.beam); break;
/* 175 */     case 1:  Minecraft.getMinecraft().renderEngine.bindTexture(this.beam1); break;
/* 176 */     case 2:  Minecraft.getMinecraft().renderEngine.bindTexture(this.beam2); break;
/* 177 */     case 3:  Minecraft.getMinecraft().renderEngine.bindTexture(this.beam3);
/*     */     }
/*     */     
/* 180 */     GL11.glTexParameterf(3553, 10242, 10497.0F);
/* 181 */     GL11.glTexParameterf(3553, 10243, 10497.0F);
/*     */     
/* 183 */     GL11.glDisable(2884);
/*     */     
/* 185 */     float var11 = slide + f;
/* 186 */     if (this.reverse) var11 *= -1.0F;
/* 187 */     float var12 = -var11 * 0.2F - MathHelper.floor_float(-var11 * 0.1F);
/*     */     
/* 189 */     GL11.glEnable(3042);
/* 190 */     GL11.glBlendFunc(770, 1);
/* 191 */     GL11.glDepthMask(false);
/*     */     
/* 193 */     float xx = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/* 194 */     float yy = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/* 195 */     float zz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/* 196 */     GL11.glTranslated(xx, yy, zz);
/*     */     
/* 198 */     float ry = (float)(this.prevYaw + (this.rotYaw - this.prevYaw) * f);
/* 199 */     float rp = (float)(this.prevPitch + (this.rotPitch - this.prevPitch) * f);
/* 200 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 201 */     GL11.glRotatef(180.0F + ry, 0.0F, 0.0F, -1.0F);
/* 202 */     GL11.glRotatef(rp, 1.0F, 0.0F, 0.0F);
/*     */     
/* 204 */     double var44 = -0.15D * size;
/* 205 */     double var17 = 0.15D * size;
/* 206 */     double var44b = -0.15D * size * this.endMod;
/* 207 */     double var17b = 0.15D * size * this.endMod;
/* 208 */     int i = 200;
/* 209 */     int j = i >> 16 & 0xFFFF;
/* 210 */     int k = i & 0xFFFF;
/* 211 */     GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
/* 212 */     for (int t = 0; t < 3; t++)
/*     */     {
/* 214 */       double var29 = this.length * size * var9;
/* 215 */       double var31 = 0.0D;
/* 216 */       double var33 = 1.0D;
/* 217 */       double var35 = -1.0F + var12 + t / 3.0F;
/* 218 */       double var37 = this.length * size * var9 + var35;
/*     */       
/* 220 */       GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
/* 221 */       wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/* 222 */       wr.pos(var44b, var29, 0.0D).tex(var33, var37).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
/* 223 */       wr.pos(var44, 0.0D, 0.0D).tex(var33, var35).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
/* 224 */       wr.pos(var17, 0.0D, 0.0D).tex(var31, var35).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
/* 225 */       wr.pos(var17b, var29, 0.0D).tex(var31, var37).color(this.particleRed, this.particleGreen, this.particleBlue, op).lightmap(j, k).endVertex();
/* 226 */       Tessellator.getInstance().draw();
/*     */     }
/*     */     
/*     */ 
/* 230 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 231 */     GL11.glDepthMask(true);
/* 232 */     GL11.glBlendFunc(770, 771);
/* 233 */     GL11.glDisable(3042);
/* 234 */     GL11.glEnable(2884);
/* 235 */     GL11.glPopMatrix();
/*     */     
/*     */ 
/* 238 */     if (this.impact > 0) { renderImpact(Tessellator.getInstance(), f, f1, f2, f3, f4, f5);
/*     */     }
/* 240 */     Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getMCParticleTexture());
/* 241 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/* 242 */     this.prevSize = size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderImpact(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 249 */     GL11.glPushMatrix();
/* 250 */     GL11.glDepthMask(false);
/* 251 */     GL11.glEnable(3042);
/* 252 */     GL11.glBlendFunc(770, 1);
/*     */     
/* 254 */     Minecraft.getMinecraft().renderEngine.bindTexture(thaumcraft.client.fx.ParticleEngine.particleTexture);
/*     */     
/* 256 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.66F);
/* 257 */     int part = this.particleAge % 16;
/*     */     
/* 259 */     float var8 = part / 16.0F;
/* 260 */     float var9 = var8 + 0.0624375F;
/* 261 */     float var10 = 0.3125F;
/* 262 */     float var11 = var10 + 0.0624375F;
/* 263 */     float var12 = this.endMod / 2.0F / (6 - this.impact);
/*     */     
/* 265 */     float var13 = (float)(this.ptX + (this.tX - this.ptX) * f - interpPosX);
/* 266 */     float var14 = (float)(this.ptY + (this.tY - this.ptY) * f - interpPosY);
/* 267 */     float var15 = (float)(this.ptZ + (this.tZ - this.ptZ) * f - interpPosZ);
/* 268 */     float var16 = 1.0F;
/* 269 */     int i = 200;
/* 270 */     int j = i >> 16 & 0xFFFF;
/* 271 */     int k = i & 0xFFFF;
/* 272 */     tessellator.getBuffer().begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/* 273 */     tessellator.getBuffer().pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
/* 274 */     tessellator.getBuffer().pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
/* 275 */     tessellator.getBuffer().pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
/* 276 */     tessellator.getBuffer().pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed, this.particleGreen, this.particleBlue, 0.66F).lightmap(j, k).endVertex();
/*     */     
/* 278 */     tessellator.draw();
/* 279 */     GL11.glBlendFunc(770, 771);
/* 280 */     GL11.glDisable(3042);
/* 281 */     GL11.glDepthMask(true);
/* 282 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\beams\FXBeamBore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */