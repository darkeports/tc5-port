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
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class FXArc
/*     */   extends Particle
/*     */ {
/*  22 */   public int particle = 16;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FXArc(World par1World, double x, double y, double z, double tx, double ty, double tz, float red, float green, float blue, double hg)
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
/*     */ 
/*  47 */     this.particleMaxAge = 1;
/*     */     
/*  49 */     double xx = 0.0D;
/*  50 */     double yy = 0.0D;
/*  51 */     double zz = 0.0D;
/*     */     
/*  53 */     double gravity = 0.115D;
/*  54 */     double noise = 0.25D;
/*     */     
/*  56 */     Vec3d vs = new Vec3d(xx, yy, zz);
/*  57 */     Vec3d ve = new Vec3d(this.tX, this.tY, this.tZ);
/*  58 */     Vec3d vc = new Vec3d(xx, yy, zz);
/*     */     
/*  60 */     this.length = ((float)ve.lengthVector());
/*  61 */     Vec3d vv = Utils.calculateVelocity(vs, ve, hg, gravity);
/*  62 */     double l = Utils.distanceSquared3d(new Vec3d(0.0D, 0.0D, 0.0D), vv);
/*     */     
/*  64 */     this.points.add(vs);
/*     */     
/*  66 */     int c = 0;
/*  67 */     while ((Utils.distanceSquared3d(ve, vc) > l) && (c < 50))
/*     */     {
/*  69 */       Vec3d vt = vc.addVector(vv.xCoord, vv.yCoord, vv.zCoord);
/*  70 */       vc = new Vec3d(vt.xCoord, vt.yCoord, vt.zCoord);
/*  71 */       vt = vt.addVector((this.rand.nextDouble() - this.rand.nextDouble()) * noise, (this.rand.nextDouble() - this.rand.nextDouble()) * noise, (this.rand.nextDouble() - this.rand.nextDouble()) * noise);
/*     */       
/*     */ 
/*     */ 
/*  75 */       this.points.add(vt);
/*  76 */       vv = vv.subtract(0.0D, gravity / 1.9D, 0.0D);
/*  77 */       c++;
/*     */     }
/*     */     
/*  80 */     this.points.add(ve);
/*     */   }
/*     */   
/*  83 */   ArrayList<Vec3d> points = new ArrayList();
/*     */   
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
/*     */   }
/*     */   
/*     */   public void setRGB(float r, float g, float b)
/*     */   {
/* 104 */     this.particleRed = r;
/* 105 */     this.particleGreen = g;
/* 106 */     this.particleBlue = b;
/*     */   }
/*     */   
/* 109 */   private Entity targetEntity = null;
/* 110 */   private double tX = 0.0D;
/* 111 */   private double tY = 0.0D;
/* 112 */   private double tZ = 0.0D;
/*     */   
/* 114 */   ResourceLocation beam = new ResourceLocation("thaumcraft", "textures/misc/beamh.png");
/*     */   
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 119 */     Tessellator.getInstance().draw();
/*     */     
/* 121 */     GL11.glPushMatrix();
/* 122 */     double ePX = this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX;
/* 123 */     double ePY = this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY;
/* 124 */     double ePZ = this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ;
/* 125 */     GL11.glTranslated(ePX, ePY, ePZ);
/* 126 */     float size = 0.25F;
/*     */     
/* 128 */     Minecraft.getMinecraft().renderEngine.bindTexture(this.beam);
/*     */     
/* 130 */     GL11.glDepthMask(false);
/* 131 */     GL11.glEnable(3042);
/* 132 */     GL11.glBlendFunc(770, 1);
/*     */     
/* 134 */     GL11.glDisable(2884);
/*     */     
/* 136 */     int i = 220;
/* 137 */     int j = i >> 16 & 0xFFFF;
/* 138 */     int k = i & 0xFFFF;
/*     */     
/* 140 */     wr.begin(5, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
/*     */     
/* 142 */     float f9 = 0.0F;
/* 143 */     float f10 = 1.0F;
/* 144 */     for (int c = 0; c < this.points.size(); c++) {
/* 145 */       Vec3d v = (Vec3d)this.points.get(c);
/* 146 */       float f13 = c / this.length;
/* 147 */       double dx = v.xCoord;
/* 148 */       double dy = v.yCoord;
/* 149 */       double dz = v.zCoord;
/* 150 */       wr.pos(dx, dy - size, dz).tex(f13, f10).lightmap(j, k).color(this.particleRed, this.particleGreen, this.particleBlue, 0.8F).endVertex();
/* 151 */       wr.pos(dx, dy + size, dz).tex(f13, f9).lightmap(j, k).color(this.particleRed, this.particleGreen, this.particleBlue, 0.8F).endVertex();
/*     */     }
/*     */     
/* 154 */     Tessellator.getInstance().draw();
/*     */     
/* 156 */     wr.begin(5, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
/*     */     
/* 158 */     for (int c = 0; c < this.points.size(); c++) {
/* 159 */       Vec3d v = (Vec3d)this.points.get(c);
/* 160 */       float f13 = c / this.length;
/* 161 */       double dx = v.xCoord;
/* 162 */       double dy = v.yCoord;
/* 163 */       double dz = v.zCoord;
/* 164 */       wr.pos(dx - size, dy, dz - size).tex(f13, f10).lightmap(j, k).color(this.particleRed, this.particleGreen, this.particleBlue, 0.8F).endVertex();
/* 165 */       wr.pos(dx + size, dy, dz + size).tex(f13, f9).lightmap(j, k).color(this.particleRed, this.particleGreen, this.particleBlue, 0.8F).endVertex();
/*     */     }
/*     */     
/* 168 */     Tessellator.getInstance().draw();
/*     */     
/* 170 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 171 */     GL11.glEnable(2884);
/* 172 */     GL11.glBlendFunc(770, 771);
/* 173 */     GL11.glDisable(3042);
/* 174 */     GL11.glDepthMask(true);
/*     */     
/* 176 */     GL11.glPopMatrix();
/*     */     
/* 178 */     Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getMCParticleTexture());
/*     */     
/* 180 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */   
/* 183 */   public int blendmode = 1;
/* 184 */   public float length = 1.0F;
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\beams\FXArc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */