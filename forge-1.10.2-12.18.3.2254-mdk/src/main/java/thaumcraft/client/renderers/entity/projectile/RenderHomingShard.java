/*     */ package thaumcraft.client.renderers.entity.projectile;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.fx.ParticleEngine;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.client.lib.UtilsFX.Vector;
/*     */ import thaumcraft.common.entities.projectile.EntityHomingShard;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderHomingShard
/*     */   extends Render
/*     */ {
/*     */   public RenderHomingShard(RenderManager rm)
/*     */   {
/*  28 */     super(rm);
/*  29 */     this.shadowSize = 0.0F;
/*     */   }
/*     */   
/*  32 */   private Random random = new Random();
/*     */   
/*     */   public void renderEntityAt(EntityHomingShard entity, double x, double y, double z, float fq, float pticks) {
/*  35 */     Tessellator tessellator = Tessellator.getInstance();
/*     */     
/*  37 */     GL11.glPushMatrix();
/*  38 */     GL11.glDepthMask(false);
/*  39 */     GL11.glEnable(3042);
/*  40 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  42 */     bindTexture(ParticleEngine.particleTexture);
/*     */     
/*  44 */     float f2 = (8 + entity.ticksExisted % 8) / 16.0F;
/*  45 */     float f3 = f2 + 0.0625F;
/*  46 */     float f4 = 0.25F;
/*  47 */     float f5 = f4 + 0.0625F;
/*     */     
/*  49 */     float f6 = 1.0F;
/*  50 */     float f7 = 0.5F;
/*  51 */     float f8 = 0.5F;
/*     */     
/*  53 */     GL11.glColor4f(0.405F, 0.075F, 0.525F, 1.0F);
/*     */     
/*  55 */     GL11.glPushMatrix();
/*  56 */     GL11.glTranslated(x, y, z);
/*  57 */     GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  58 */     GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*  59 */     GL11.glScalef(0.4F + 0.1F * entity.getStrength(), 0.4F + 0.1F * entity.getStrength(), 0.4F + 0.1F * entity.getStrength());
/*  60 */     tessellator.getWorldRenderer().begin(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
/*  61 */     int i = 240;
/*  62 */     int j = i >> 16 & 0xFFFF;
/*  63 */     int k = i & 0xFFFF;
/*  64 */     tessellator.getWorldRenderer().pos(-f7, -f8, 0.0D).tex(f2, f5).color(0.405F, 0.075F, 0.525F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  65 */     tessellator.getWorldRenderer().pos(f6 - f7, -f8, 0.0D).tex(f3, f5).color(0.405F, 0.075F, 0.525F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  66 */     tessellator.getWorldRenderer().pos(f6 - f7, 1.0F - f8, 0.0D).tex(f3, f4).color(0.405F, 0.075F, 0.525F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  67 */     tessellator.getWorldRenderer().pos(-f7, 1.0F - f8, 0.0D).tex(f2, f4).color(0.405F, 0.075F, 0.525F, 1.0F).lightmap(j, k).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  68 */     tessellator.draw();
/*  69 */     GL11.glPopMatrix();
/*     */     
/*  71 */     GL11.glPushMatrix();
/*  72 */     GL11.glBlendFunc(770, 1);
/*  73 */     bindTexture(beamTexture);
/*  74 */     Minecraft mc = Minecraft.getMinecraft();
/*  75 */     EntityPlayerSP p = mc.thePlayer;
/*  76 */     double doubleX = p.lastTickPosX + (p.posX - p.lastTickPosX) * pticks;
/*  77 */     double doubleY = p.lastTickPosY + (p.posY - p.lastTickPosY) * pticks;
/*  78 */     double doubleZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * pticks;
/*  79 */     UtilsFX.Vector player = new UtilsFX.Vector((float)doubleX, (float)doubleY, (float)doubleZ);
/*     */     
/*  81 */     double dX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * pticks;
/*  82 */     double dY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * pticks;
/*  83 */     double dZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * pticks;
/*  84 */     UtilsFX.Vector start = new UtilsFX.Vector((float)dX, (float)dY, (float)dZ);
/*     */     
/*  86 */     if (entity.vl.size() == 0) {
/*  87 */       entity.vl.add(start);
/*     */     }
/*     */     
/*  90 */     GL11.glTranslated(-doubleX, -doubleY, -doubleZ);
/*  91 */     UtilsFX.Vector vs = new UtilsFX.Vector((float)dX, (float)dY, (float)dZ);
/*     */     
/*  93 */     tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
/*  94 */     int c = entity.vl.size();
/*  95 */     for (UtilsFX.Vector nv : entity.vl) {
/*  96 */       UtilsFX.drawBeam(vs, nv, player, 0.25F * (c / entity.vl.size()), 240, 0.405F, 0.075F, 0.525F, 1.0F);
/*  97 */       vs = nv;
/*  98 */       c--;
/*     */     }
/*     */     
/* 101 */     tessellator.draw();
/*     */     
/* 103 */     GL11.glPopMatrix();
/*     */     
/* 105 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 106 */     GL11.glBlendFunc(770, 771);
/* 107 */     GL11.glDisable(3042);
/* 108 */     GL11.glDisable(32826);
/*     */     
/* 110 */     GL11.glDepthMask(true);
/* 111 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 116 */   private static final ResourceLocation beamTexture = new ResourceLocation("thaumcraft", "textures/misc/beaml.png");
/*     */   
/*     */   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
/*     */   {
/* 120 */     renderEntityAt((EntityHomingShard)entity, d, d1, d2, f, f1);
/*     */   }
/*     */   
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 125 */     return TextureMap.locationBlocksTexture;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\projectile\RenderHomingShard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */