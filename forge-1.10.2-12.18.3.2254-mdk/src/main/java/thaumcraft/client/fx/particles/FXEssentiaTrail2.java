/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import com.sasmaster.glelwjgl.java.CoreGLE;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.codechicken.libold.vec.Quat;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.devices.BlockEssentiaTransport;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ 
/*     */ public class FXEssentiaTrail2
/*     */   extends Particle
/*     */ {
/*     */   private double targetX;
/*     */   private double targetY;
/*     */   private double targetZ;
/*     */   private double startX;
/*     */   private double startY;
/*     */   private double startZ;
/*  39 */   private int count = 0;
/*  40 */   public int length = 20;
/*  41 */   private String key = "";
/*     */   
/*  43 */   private BlockPos startPos = null;
/*  44 */   private BlockPos endPos = null;
/*     */   
/*  46 */   static HashMap<String, FXEssentiaTrail2> pt = new HashMap();
/*     */   
/*     */ 
/*     */   public FXEssentiaTrail2(World w, double par2, double par4, double par6, double tx, double ty, double tz, int count, int color, float scale, int extend)
/*     */   {
/*  51 */     super(w, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/*     */     
/*  53 */     this.particleScale = ((float)(scale * (1.0D + this.rand.nextGaussian() * 0.15000000596046448D)));
/*  54 */     this.length = Math.max(20, extend);
/*  55 */     this.count = count;
/*  56 */     this.targetX = tx;
/*  57 */     this.targetY = ty;
/*  58 */     this.targetZ = tz;
/*  59 */     BlockPos bp1 = new BlockPos(this.posX, this.posY, this.posZ);
/*  60 */     BlockPos bp2 = new BlockPos(this.targetX, this.targetY, this.targetZ);
/*     */     
/*  62 */     IBlockState bs = w.getBlockState(bp1);
/*     */     
/*  64 */     if ((bs.getBlock() instanceof BlockEssentiaTransport)) {
/*  65 */       EnumFacing f = BlockStateUtils.getFacing(bs);
/*  66 */       this.posX += f.getFrontOffsetX() * 0.05F;
/*  67 */       this.posY += f.getFrontOffsetY() * 0.05F;
/*  68 */       this.posZ += f.getFrontOffsetZ() * 0.05F;
/*     */     }
/*     */     
/*  71 */     double dx = tx - this.posX;
/*  72 */     double dy = ty - this.posY;
/*  73 */     double dz = tz - this.posZ;
/*  74 */     int base = (int)(MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz) * 21.0F);
/*  75 */     if (base < 1)
/*  76 */       base = 1;
/*  77 */     this.particleMaxAge = base;
/*     */     
/*  79 */     String k = bp1.toLong() + "" + bp2.toLong() + "" + color;
/*  80 */     if (pt.containsKey(k)) {
/*  81 */       FXEssentiaTrail2 trail2 = (FXEssentiaTrail2)pt.get(k);
/*  82 */       if ((!trail2.isExpired) && (trail2.vecs.size() < trail2.length)) {
/*  83 */         trail2.length += Math.max(extend, 5);
/*  84 */         trail2.particleMaxAge += Math.max(extend, 5);
/*  85 */         this.particleMaxAge = 0;
/*     */       }
/*     */     }
/*  88 */     if (this.particleMaxAge > 0) {
/*  89 */       pt.put(k, this);
/*  90 */       this.key = k;
/*     */     }
/*     */     
/*  93 */     this.motionX = (MathHelper.sin(count / 4.0F) * 0.015F);
/*  94 */     this.motionY = (MathHelper.sin(count / 3.0F) * 0.015F);
/*  95 */     this.motionZ = (MathHelper.sin(count / 2.0F) * 0.015F);
/*     */     
/*  97 */     Color c = new Color(color);
/*  98 */     this.particleRed = (c.getRed() / 255.0F);
/*  99 */     this.particleGreen = (c.getGreen() / 255.0F);
/* 100 */     this.particleBlue = (c.getBlue() / 255.0F);
/*     */     
/* 102 */     this.particleGravity = 0.2F;
/* 103 */     this.canCollide = true;
/*     */     
/* 105 */     this.vecs.add(new Quat(0.0D, 0.0D, 0.0D, 0.001D));
/* 106 */     this.vecs.add(new Quat(0.0D, 0.0D, 0.0D, 0.001D));
/*     */     
/* 108 */     this.startX = this.posX;
/* 109 */     this.startY = this.posY;
/* 110 */     this.startZ = this.posZ;
/*     */     
/* 112 */     this.startPos = new BlockPos(this.startX, this.startY, this.startZ);
/* 113 */     this.endPos = bp2;
/*     */   }
/*     */   
/*     */ 
/* 117 */   CoreGLE gle = new CoreGLE();
/*     */   
/* 119 */   private static final ResourceLocation TEX0 = new ResourceLocation("thaumcraft", "textures/misc/essentia.png");
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 125 */     Tessellator.getInstance().draw();
/* 126 */     GL11.glPushMatrix();
/* 127 */     double ePX = this.startX - interpPosX;
/* 128 */     double ePY = this.startY - interpPosY;
/* 129 */     double ePZ = this.startZ - interpPosZ;
/* 130 */     GL11.glTranslated(ePX, ePY, ePZ);
/*     */     
/* 132 */     if ((this.points != null) && (this.points.length > 2)) {
/* 133 */       Minecraft.getMinecraft().renderEngine.bindTexture(TEX0);
/*     */       
/* 135 */       this.gle.set_POLYCYL_TESS(8);
/* 136 */       this.gle.set__ROUND_TESS_PIECES(1);
/* 137 */       this.gle.gleSetJoinStyle(1042);
/* 138 */       this.gle.glePolyCone(this.points.length, this.points, this.colours, this.radii, 0.075F, this.growing < 0 ? 0.0F : 0.075F * (this.particleAge - this.growing + f));
/*     */     }
/*     */     
/*     */ 
/* 142 */     GL11.glPopMatrix();
/* 143 */     Minecraft.getMinecraft().renderEngine.bindTexture(ParticleEngine.particleTexture);
/* 144 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */   
/* 147 */   int layer = 1;
/*     */   double[][] points;
/*     */   
/* 150 */   public void setFXLayer(int l) { this.layer = l; }
/*     */   
/*     */ 
/*     */   public int getFXLayer()
/*     */   {
/* 155 */     return this.layer;
/*     */   }
/*     */   
/*     */ 
/*     */   float[][] colours;
/*     */   double[] radii;
/* 161 */   int growing = -1;
/* 162 */   ArrayList<Quat> vecs = new ArrayList();
/*     */   
/*     */ 
/*     */   public BlockPos getPosition()
/*     */   {
/* 167 */     return new BlockPos(this.posX, this.posY, this.posZ);
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 171 */     this.prevPosX = this.posX;
/* 172 */     this.prevPosY = this.posY;
/* 173 */     this.prevPosZ = this.posZ;
/*     */     
/* 175 */     if ((this.particleAge++ >= this.particleMaxAge) || (this.length < 1)) {
/* 176 */       setExpired();
/* 177 */       if ((pt.containsKey(this.key)) && (((FXEssentiaTrail2)pt.get(this.key)).isExpired)) {
/* 178 */         pt.remove(this.key);
/*     */       }
/* 180 */       return;
/*     */     }
/*     */     
/* 183 */     this.motionY += 0.01D * this.particleGravity;
/*     */     
/* 185 */     double ds = this.startPos.distanceSqToCenter(this.posX, this.posY, this.posZ);
/* 186 */     double de = this.endPos.distanceSqToCenter(this.posX, this.posY, this.posZ);
/*     */     
/* 188 */     this.canCollide = !(((ds <= 1.5D) || (de <= 1.5D)));
/*     */     //TODO:???
/* 190 */     if (this.canCollide) {
/* 191 */       pushOutOfBlocks(this.posX, this.posY, this.posZ);
/*     */     }
/*     */     
/* 194 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/* 196 */     this.motionX *= 0.985D;
/* 197 */     this.motionY *= 0.985D;
/* 198 */     this.motionZ *= 0.985D;
/*     */     
/* 200 */     this.motionX = MathHelper.clamp_float((float)this.motionX, -0.05F, 0.05F);
/* 201 */     this.motionY = MathHelper.clamp_float((float)this.motionY, -0.05F, 0.05F);
/* 202 */     this.motionZ = MathHelper.clamp_float((float)this.motionZ, -0.05F, 0.05F);
/*     */     
/* 204 */     double dx = this.targetX - this.posX;
/* 205 */     double dy = this.targetY - this.posY;
/* 206 */     double dz = this.targetZ - this.posZ;
/* 207 */     double d13 = 0.01D;
/* 208 */     double d11 = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
/*     */     
/* 210 */     dx /= d11;
/* 211 */     dy /= d11;
/* 212 */     dz /= d11;
/*     */     
/* 214 */     this.motionX += dx * (d13 / Math.min(1.0D, d11));
/* 215 */     this.motionY += dy * (d13 / Math.min(1.0D, d11));
/* 216 */     this.motionZ += dz * (d13 / Math.min(1.0D, d11));
/*     */     
/* 218 */     float scale = this.particleScale * (0.75F + MathHelper.sin((this.count + this.particleAge) / 2.0F) * 0.25F);
/*     */     
/* 220 */     if (d11 < 1.0D) {
/* 221 */       float f = MathHelper.sin((float)(d11 * 1.5707963267948966D));
/* 222 */       scale *= f;
/* 223 */       this.particleScale *= f;
/*     */     }
/*     */     
/* 226 */     if (this.particleScale > 0.001D) {
/* 227 */       this.vecs.add(new Quat(scale, this.posX - this.startX, this.posY - this.startY, this.posZ - this.startZ));
/*     */     } else {
/* 229 */       if (this.growing < 0) this.growing = this.particleAge;
/* 230 */       this.length -= 1;
/* 231 */       Thaumcraft.proxy.getFX().essentiaDropFx(this.targetX + this.rand.nextGaussian() * 0.07500000298023224D, this.targetY + this.rand.nextGaussian() * 0.07500000298023224D, this.targetZ + this.rand.nextGaussian() * 0.07500000298023224D, this.particleRed, this.particleGreen, this.particleBlue, 0.5F);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 238 */     if (this.vecs.size() > this.length) {
/* 239 */       this.vecs.remove(0);
/*     */     }
/*     */     
/* 242 */     this.points = new double[this.vecs.size()][3];
/* 243 */     this.colours = new float[this.vecs.size()][3];
/* 244 */     this.radii = new double[this.vecs.size()];
/* 245 */     int c = this.vecs.size();
/* 246 */     for (Quat v : this.vecs) {
/* 247 */       c--;
/* 248 */       float variance = 1.0F + MathHelper.sin((c + this.particleAge) / 3.0F) * 0.2F;
/*     */       
/* 250 */       float xx = MathHelper.sin((c + this.particleAge) / 6.0F) * 0.03F;
/* 251 */       float yy = MathHelper.sin((c + this.particleAge) / 7.0F) * 0.03F;
/* 252 */       float zz = MathHelper.sin((c + this.particleAge) / 8.0F) * 0.03F;
/*     */       
/* 254 */       this.points[c][0] = (v.x + xx);
/* 255 */       this.points[c][1] = (v.y + yy);
/* 256 */       this.points[c][2] = (v.z + zz);
/*     */       
/* 258 */       this.radii[c] = (v.s * variance);
/*     */       
/* 260 */       if (c > this.vecs.size() - 10) {
/* 261 */         this.radii[c] *= MathHelper.cos((float)((c - (this.vecs.size() - 12)) / 10.0F * 1.5707963267948966D));
/*     */       }
/*     */       
/* 264 */       if (c == 0) this.radii[c] = 0.0D; else if (c == 1) { this.radii[c] = 0.0D;
/* 265 */       } else if (c == 2) { this.radii[c] = ((this.particleScale * 0.5D + this.radii[c]) / 2.0D);
/* 266 */       } else if (c == 3) { this.radii[c] = ((this.particleScale + this.radii[c]) / 2.0D);
/* 267 */       } else if (c == 4) { this.radii[c] = ((this.particleScale + this.radii[c] * 2.0D) / 3.0D);
/*     */       }
/* 269 */       float v2 = 1.0F - MathHelper.sin((c + this.particleAge) / 2.0F) * 0.1F;
/*     */       
/* 271 */       this.colours[c][0] = (this.particleRed * v2);
/* 272 */       this.colours[c][1] = (this.particleGreen * v2);
/* 273 */       this.colours[c][2] = (this.particleBlue * v2);
/*     */     }
/*     */     
/*     */ 
/* 277 */     if ((this.vecs.size() > 2) && (this.rand.nextBoolean())) {
/* 278 */       int q = this.rand.nextInt(3);
/* 279 */       if (this.rand.nextBoolean()) {
/* 280 */         q = this.vecs.size() - 2;
/*     */       }
/* 282 */       Thaumcraft.proxy.getFX().essentiaDropFx(((Quat)this.vecs.get(q)).x + this.startX, ((Quat)this.vecs.get(q)).y + this.startY, ((Quat)this.vecs.get(q)).z + this.startZ, this.particleRed, this.particleGreen, this.particleBlue, 0.5F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setGravity(float value)
/*     */   {
/* 290 */     this.particleGravity = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXEssentiaTrail2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */