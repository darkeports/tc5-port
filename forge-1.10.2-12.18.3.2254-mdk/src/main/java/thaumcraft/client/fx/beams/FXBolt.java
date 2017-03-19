/*     */ package thaumcraft.client.fx.beams;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
import thaumcraft.codechicken.libold.vec.Vector3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FXBolt
/*     */   extends Particle
/*     */ {
/*     */   public FXBolt(World par1World, double x, double y, double z, double tx, double ty, double tz, float red, float green, float blue)
/*     */   {
/*  30 */     super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
/*     */     
/*  32 */     this.particleRed = red;
/*  33 */     this.particleGreen = green;
/*  34 */     this.particleBlue = blue;
/*     */     
/*  36 */     setSize(0.02F, 0.02F);
/*  37 */     this.canCollide = false;
/*  38 */     this.motionX = 0.0D;
/*  39 */     this.motionY = 0.0D;
/*  40 */     this.motionZ = 0.0D;
/*  41 */     this.tX = (tx - x);
/*  42 */     this.tY = (ty - y);
/*  43 */     this.tZ = (tz - z);
/*     */     
/*     */ 
/*  46 */     this.particleMaxAge = 3;
/*     */     
/*     */ 
/*  49 */     Vec3d vs = new Vec3d(0.0D, 0.0D, 0.0D);
/*  50 */     Vec3d ve = new Vec3d(this.tX, this.tY, this.tZ);
/*     */     
/*  52 */     this.length = ((float)(ve.lengthVector() * 3.141592653589793D));
/*     */     
/*  54 */     int steps = (int)this.length;
/*     */     
/*  56 */     this.points.add(vs);
/*  57 */     this.pointsWidth.add(Float.valueOf(1.0F));
/*  58 */     this.dr = ((float)(this.rand.nextInt(50) * 3.141592653589793D));
/*     */     
/*  60 */     float ampl = 0.1F;
/*     */     
/*  62 */     for (int a = 1; a < steps - 1; a++) {
/*  63 */       float dist = a * (this.length / steps) + this.dr;
/*  64 */       double dx = this.tX / steps * a + MathHelper.sin(dist / 4.0F) * ampl;
/*  65 */       double dy = this.tY / steps * a + MathHelper.sin(dist / 3.0F) * ampl;
/*  66 */       double dz = this.tZ / steps * a + MathHelper.sin(dist / 2.0F) * ampl;
/*  67 */       dx += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
/*  68 */       dy += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
/*  69 */       dz += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
/*     */       
/*  71 */       Vec3d vp = new Vec3d(dx, dy, dz);
/*  72 */       this.points.add(vp);
/*  73 */       this.pointsWidth.add(Float.valueOf(1.0F));
/*     */     }
/*  75 */     this.pointsWidth.add(Float.valueOf(1.0F));
/*  76 */     this.points.add(ve);
/*     */     
/*  78 */     this.seed = this.rand.nextInt(1000);
/*     */   }
/*     */   
/*  81 */   ArrayList<Vec3d> points = new ArrayList();
/*  82 */   ArrayList<Float> pointsWidth = new ArrayList();
/*  83 */   float dr = 0.0F;
/*  84 */   long seed = 0L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  92 */     this.prevPosX = this.posX;
/*  93 */     this.prevPosY = this.posY;
/*  94 */     this.prevPosZ = this.posZ;
/*     */     
/*  96 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/*  98 */       setExpired();
/*     */     }
/*     */     
/* 101 */     Random rr = new Random(this.seed);
/*     */     
/* 103 */     this.points.clear();
/* 104 */     this.pointsWidth.clear();
/* 105 */     Vec3d vs = new Vec3d(0.0D, 0.0D, 0.0D);
/* 106 */     Vec3d ve = new Vec3d(this.tX, this.tY, this.tZ);
/* 107 */     int steps = (int)this.length;
/* 108 */     this.points.add(vs);
/* 109 */     this.pointsWidth.add(Float.valueOf(1.0F));
/* 110 */     float ampl = 0.15F * this.particleAge;
/* 111 */     for (int a = 1; a < steps - 1; a++) {
/* 112 */       float dist = a * (this.length / steps) + this.dr;
/* 113 */       double dx = this.tX / steps * a + MathHelper.sin(dist / 4.0F) * ampl;
/* 114 */       double dy = this.tY / steps * a + MathHelper.sin(dist / 3.0F) * ampl;
/* 115 */       double dz = this.tZ / steps * a + MathHelper.sin(dist / 2.0F) * ampl;
/* 116 */       dx += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
/* 117 */       dy += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
/* 118 */       dz += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
/* 119 */       Vec3d vp = new Vec3d(dx, dy, dz);
/* 120 */       this.points.add(vp);
/* 121 */       this.pointsWidth.add(Float.valueOf(rr.nextInt(4) == 0 ? 1.0F - this.particleAge * 0.25F : 1.0F));
/*     */     }
/* 123 */     this.pointsWidth.add(Float.valueOf(1.0F));
/* 124 */     this.points.add(ve);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setRGB(float r, float g, float b)
/*     */   {
/* 130 */     this.particleRed = r;
/* 131 */     this.particleGreen = g;
/* 132 */     this.particleBlue = b;
/*     */   }
/*     */   
/* 135 */   private Entity targetEntity = null;
/* 136 */   private double tX = 0.0D;
/* 137 */   private double tY = 0.0D;
/* 138 */   private double tZ = 0.0D;
/*     */   
/* 140 */   ResourceLocation beam = new ResourceLocation("thaumcraft", "textures/misc/beaml.png");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float cosyaw, float cospitch, float sinyaw, float cossinpitch, float f5)
/*     */   {
/* 147 */     Tessellator.getInstance().draw();
/*     */     
/* 149 */     GL11.glPushMatrix();
/* 150 */     double ePX = this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX;
/* 151 */     double ePY = this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY;
/* 152 */     double ePZ = this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ;
/* 153 */     GL11.glTranslated(ePX, ePY, ePZ);
/*     */     
/*     */ 
/* 156 */     Minecraft.getMinecraft().renderEngine.bindTexture(this.beam);
/*     */     
/* 158 */     GL11.glDepthMask(false);
/* 159 */     GL11.glEnable(3042);
/* 160 */     GL11.glBlendFunc(770, 1);
/*     */     
/* 162 */     GL11.glDisable(2884);
/*     */     
/* 164 */     int i = 220;
/* 165 */     int j = i >> 16 & 0xFFFF;
/* 166 */     int k = i & 0xFFFF;
/*     */     
/* 168 */     wr.begin(5, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/* 169 */     float f9 = 0.0F;
/* 170 */     float f10 = 1.0F;
/* 171 */     for (int c = 0; c < this.points.size(); c++) {
/* 172 */       float size = 0.15F * ((Float)this.pointsWidth.get(c)).floatValue();
/*     */       
/* 174 */       float f13 = c / this.length;
/* 175 */       Vec3d vc = (Vec3d)this.points.get(c);
/* 176 */       Vec3d vp = c == 0 ? (Vec3d)this.points.get(c) : (Vec3d)this.points.get(c - 1);
/* 177 */       Vec3d vn = c == this.points.size() - 1 ? (Vec3d)this.points.get(c) : (Vec3d)this.points.get(c + 1);
/* 178 */       Vec3d v1 = vp.subtract(vc);
/* 179 */       Vec3d v2 = vc.subtract(vn);
/* 180 */       Vec3d v = v1.add(v2).normalize();
/* 181 */       v = v.rotatePitch(1.5707964F);
/* 182 */       Vector3 vf = new Vector3(v).multiply(size);
/* 183 */       wr.pos(vc.xCoord + vf.x, vc.yCoord + vf.y, vc.zCoord + vf.z).tex(f13, f10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.8F / Math.max(1, this.particleAge)).lightmap(j, k).endVertex();
/* 184 */       wr.pos(vc.xCoord - vf.x, vc.yCoord - vf.y, vc.zCoord - vf.z).tex(f13, f9).color(this.particleRed, this.particleGreen, this.particleBlue, 0.8F / Math.max(1, this.particleAge)).lightmap(j, k).endVertex();
/*     */     }
/*     */     
/* 187 */     Tessellator.getInstance().draw();
/*     */     
/* 189 */     wr.begin(5, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */     
/* 191 */     for (int c = 0; c < this.points.size(); c++) {
/* 192 */       float size = 0.15F * ((Float)this.pointsWidth.get(c)).floatValue();
/* 193 */       float f13 = c / this.length;
/* 194 */       Vec3d vc = (Vec3d)this.points.get(c);
/* 195 */       Vec3d vp = c == 0 ? (Vec3d)this.points.get(c) : (Vec3d)this.points.get(c - 1);
/* 196 */       Vec3d vn = c == this.points.size() - 1 ? (Vec3d)this.points.get(c) : (Vec3d)this.points.get(c + 1);
/* 197 */       Vec3d v1 = vp.subtract(vc);
/* 198 */       Vec3d v2 = vc.subtract(vn);
/* 199 */       Vec3d v = v1.add(v2).normalize();
/* 200 */       v = v.rotateYaw(1.5707964F);
/* 201 */       Vector3 vf = new Vector3(v).multiply(size);
/* 202 */       wr.pos(vc.xCoord + vf.x, vc.yCoord + vf.y, vc.zCoord + vf.z).tex(f13, f10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.8F / Math.max(1, this.particleAge)).lightmap(j, k).endVertex();
/* 203 */       wr.pos(vc.xCoord - vf.x, vc.yCoord - vf.y, vc.zCoord - vf.z).tex(f13, f9).color(this.particleRed, this.particleGreen, this.particleBlue, 0.8F / Math.max(1, this.particleAge)).lightmap(j, k).endVertex();
/*     */     }
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
/* 240 */     Tessellator.getInstance().draw();
/*     */     
/* 242 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 243 */     GL11.glEnable(2884);
/* 244 */     GL11.glBlendFunc(770, 771);
/* 245 */     GL11.glDisable(3042);
/* 246 */     GL11.glDepthMask(true);
/*     */     
/* 248 */     GL11.glPopMatrix();
/*     */     
/* 250 */     Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getMCParticleTexture());
/*     */     
/* 252 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */   
/* 255 */   public float length = 1.0F;
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\beams\FXBolt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */