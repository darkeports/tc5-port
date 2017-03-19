/*     */ package thaumcraft.client.renderers.entity.projectile;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.fx.ParticleEngine;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.common.entities.projectile.EntityGrapple;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderGrapple
/*     */   extends Render
/*     */ {
/*     */   public RenderGrapple(RenderManager rm)
/*     */   {
/*  28 */     super(rm);
/*  29 */     this.shadowSize = 0.0F;
/*     */   }
/*     */   
/*  32 */   ResourceLocation beam = new ResourceLocation("thaumcraft", "textures/misc/particles.png");
/*     */   
/*     */   public void renderEntityAt(Entity entity, double x, double y, double z, float fq, float pticks) {
/*  35 */     Tessellator tessellator = Tessellator.getInstance();
/*  36 */     boolean sticky = ((EntityGrapple)entity).sticky;
/*  37 */     GL11.glPushMatrix();
/*  38 */     GL11.glTranslated(x, y, z);
/*  39 */     GL11.glEnable(3042);
/*  40 */     GL11.glBlendFunc(770, 1);
/*  41 */     GL11.glDisable(2884);
/*  42 */     bindTexture(ParticleEngine.particleTexture);
/*     */     
/*  44 */     float f2 = (1 + entity.ticksExisted % 6) / 8.0F;
/*  45 */     float f3 = f2 + 0.125F;
/*  46 */     float f4 = 0.875F;
/*  47 */     float f5 = f4 + 0.125F;
/*     */     
/*  49 */     float f7 = 0.5F;
/*     */     
/*  51 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
/*     */     
/*  53 */     GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  54 */     GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*  55 */     float bob = MathHelper.sin(entity.ticksExisted / 5.0F) * 0.2F + 0.2F;
/*  56 */     GL11.glScalef(1.0F + bob, 1.0F + bob, 1.0F + bob);
/*  57 */     tessellator.getWorldRenderer().begin(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
/*  58 */     int i = 220;
/*  59 */     int j = i >> 16 & 0xFFFF;
/*  60 */     int k = i & 0xFFFF;
/*  61 */     tessellator.getWorldRenderer().pos(-f7, -f7, 0.0D).tex(f2, f5).color(sticky ? 0.0F : 1.0F, 1.0F, sticky ? 0.2F : 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  62 */     tessellator.getWorldRenderer().pos(f7, -f7, 0.0D).tex(f3, f5).color(sticky ? 0.0F : 1.0F, 1.0F, sticky ? 0.2F : 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  63 */     tessellator.getWorldRenderer().pos(f7, f7, 0.0D).tex(f3, f4).color(sticky ? 0.0F : 1.0F, 1.0F, sticky ? 0.2F : 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  64 */     tessellator.getWorldRenderer().pos(-f7, f7, 0.0D).tex(f2, f4).color(sticky ? 0.0F : 1.0F, 1.0F, sticky ? 0.2F : 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  65 */     tessellator.draw();
/*  66 */     GL11.glEnable(2884);
/*  67 */     GL11.glBlendFunc(770, 771);
/*  68 */     GL11.glDisable(3042);
/*  69 */     GL11.glDisable(32826);
/*  70 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  72 */     GL11.glPopMatrix();
/*     */     
/*     */ 
/*  75 */     calcPoints(((EntityGrapple)entity).getThrower(), (EntityGrapple)entity, pticks);
/*     */     
/*     */ 
/*  78 */     GL11.glPushMatrix();
/*     */     
/*  80 */     Minecraft.getMinecraft().renderEngine.bindTexture(this.beam);
/*     */     
/*  82 */     GL11.glDepthMask(false);
/*  83 */     GL11.glEnable(3042);
/*  84 */     GL11.glBlendFunc(770, 1);
/*     */     
/*  86 */     GL11.glDisable(2884);
/*     */     
/*  88 */     for (int c = 0; c < this.points.size(); c++) {
/*  89 */       int pp = (c + entity.ticksExisted) % 13;
/*  90 */       float alpha = Math.min(1.0F, 1.0F - c / (this.points.size() / 4.0F));
/*  91 */       Vec3 vc = (Vec3)this.points.get(c);
/*  92 */       GL11.glPushMatrix();
/*  93 */       GL11.glTranslated(x + vc.xCoord, y + vc.yCoord, z + vc.zCoord);
/*  94 */       UtilsFX.renderBillboardQuad(0.07500000298023224D, 16, 16, 32 + pp);
/*  95 */       GL11.glPopMatrix();
/*     */     }
/*     */     
/*  98 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  99 */     GL11.glEnable(2884);
/* 100 */     GL11.glBlendFunc(770, 771);
/* 101 */     GL11.glDisable(3042);
/* 102 */     GL11.glDepthMask(true);
/* 103 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/* 106 */   public ArrayList<Vec3> points = new ArrayList();
/* 107 */   public float length = 1.0F;
/*     */   
/*     */   private void calcPoints(EntityLivingBase thrower, EntityGrapple grapple, float pt) {
/* 110 */     if ((thrower == null) || (grapple == null)) return;
/* 111 */     double tx = thrower.lastTickPosX + (thrower.posX - thrower.lastTickPosX) * pt;
/* 112 */     double ty = thrower.lastTickPosY + (thrower.posY - thrower.lastTickPosY) * pt;
/* 113 */     double tz = thrower.lastTickPosZ + (thrower.posZ - thrower.lastTickPosZ) * pt;
/* 114 */     double gx = grapple.lastTickPosX + (grapple.posX - grapple.lastTickPosX) * pt;
/* 115 */     double gy = grapple.lastTickPosY + (grapple.posY - grapple.lastTickPosY) * pt;
/* 116 */     double gz = grapple.lastTickPosZ + (grapple.posZ - grapple.lastTickPosZ) * pt;
/* 117 */     this.points.clear();
/* 118 */     Vec3 vs = new Vec3(0.0D, 0.0D, 0.0D);
/* 119 */     Vec3 ve = new Vec3(tx - gx, ty - gy + thrower.height / 2.0F, tz - gz);
/* 120 */     this.length = ((float)(ve.lengthVector() * 5.0D));
/* 121 */     int steps = (int)this.length;
/* 122 */     this.points.add(vs);
/* 123 */     for (int a = 1; a < steps - 1; a++) {
/* 124 */       float dist = a * (this.length / steps);
/* 125 */       double dx = (tx - gx) / steps * a + MathHelper.sin(dist / 10.0F) * grapple.ampl;
/* 126 */       double dy = (ty - gy + thrower.height / 2.0F) / steps * a + MathHelper.sin(dist / 8.0F) * grapple.ampl;
/* 127 */       double dz = (tz - gz) / steps * a + MathHelper.sin(dist / 6.0F) * grapple.ampl;
/* 128 */       Vec3 vp = new Vec3(dx, dy, dz);
/* 129 */       this.points.add(vp);
/*     */     }
/* 131 */     this.points.add(ve);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
/*     */   {
/* 139 */     renderEntityAt(entity, d, d1, d2, f, f1);
/*     */   }
/*     */   
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 145 */     return TextureMap.locationBlocksTexture;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\projectile\RenderGrapple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */