/*     */ package thaumcraft.client.renderers.entity.projectile;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.fx.ParticleEngine;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderEldritchOrb
/*     */   extends Render
/*     */ {
/*     */   public RenderEldritchOrb(RenderManager renderManager)
/*     */   {
/*  22 */     super(renderManager);
/*  23 */     this.shadowSize = 0.0F;
/*     */   }
/*     */   
/*  26 */   private Random random = new Random();
/*     */   
/*     */   public void renderEntityAt(Entity entity, double x, double y, double z, float fq, float pticks)
/*     */   {
/*  30 */     Tessellator tessellator = Tessellator.getInstance();
/*     */     
/*  32 */     this.random.setSeed(187L);
/*     */     
/*  34 */     GL11.glPushMatrix();
/*     */     
/*  36 */     RenderHelper.disableStandardItemLighting();
/*  37 */     float f1 = entity.ticksExisted / 80.0F;
/*  38 */     float f3 = 0.9F;
/*  39 */     float f2 = 0.0F;
/*     */     
/*  41 */     Random random = new Random(entity.getEntityId());
/*  42 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*  43 */     GL11.glDisable(3553);
/*  44 */     GL11.glShadeModel(7425);
/*  45 */     GL11.glEnable(3042);
/*  46 */     GL11.glBlendFunc(770, 1);
/*  47 */     GL11.glDisable(3008);
/*  48 */     GL11.glEnable(2884);
/*  49 */     GL11.glDepthMask(false);
/*  50 */     GL11.glPushMatrix();
/*  51 */     for (int i = 0; i < 12; i++) {
/*  52 */       GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
/*  53 */       GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
/*  54 */       GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
/*  55 */       GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
/*  56 */       GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
/*  57 */       GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 360.0F, 0.0F, 0.0F, 1.0F);
/*     */       
/*  59 */       tessellator.getWorldRenderer().begin(6, DefaultVertexFormats.POSITION_COLOR);
/*  60 */       float fa = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
/*  61 */       float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
/*  62 */       fa /= 30.0F / (Math.min(entity.ticksExisted, 10) / 10.0F);
/*  63 */       f4 /= 30.0F / (Math.min(entity.ticksExisted, 10) / 10.0F);
/*  64 */       tessellator.getWorldRenderer().pos(0.0D, 0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F - f2).endVertex();
/*  65 */       tessellator.getWorldRenderer().pos(-0.866D * f4, fa, -0.5F * f4).color(64.0F, 64.0F, 64.0F, 255.0F * (1.0F - f2)).endVertex();
/*  66 */       tessellator.getWorldRenderer().pos(0.866D * f4, fa, -0.5F * f4).color(64.0F, 64.0F, 64.0F, 255.0F * (1.0F - f2)).endVertex();
/*  67 */       tessellator.getWorldRenderer().pos(0.0D, fa, 1.0F * f4).color(64.0F, 64.0F, 64.0F, 255.0F * (1.0F - f2)).endVertex();
/*  68 */       tessellator.getWorldRenderer().pos(-0.866D * f4, fa, -0.5F * f4).color(64.0F, 64.0F, 64.0F, 255.0F * (1.0F - f2)).endVertex();
/*  69 */       tessellator.draw();
/*     */     }
/*     */     
/*  72 */     GL11.glPopMatrix();
/*  73 */     GL11.glDepthMask(true);
/*  74 */     GL11.glDisable(2884);
/*  75 */     GL11.glDisable(3042);
/*  76 */     GL11.glShadeModel(7424);
/*  77 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  78 */     GL11.glEnable(3553);
/*  79 */     GL11.glEnable(3008);
/*  80 */     RenderHelper.enableStandardItemLighting();
/*     */     
/*  82 */     GL11.glPopMatrix();
/*     */     
/*  84 */     GL11.glPushMatrix();
/*  85 */     GL11.glTranslated(x, y, z);
/*  86 */     GL11.glEnable(3042);
/*  87 */     GL11.glBlendFunc(770, 771);
/*  88 */     bindTexture(ParticleEngine.particleTexture);
/*  89 */     f2 = entity.ticksExisted % 13 / 16.0F;
/*  90 */     f3 = f2 + 0.0624375F;
/*  91 */     float f4 = 0.1875F;
/*  92 */     float f5 = f4 + 0.0624375F;
/*  93 */     float f6 = 1.0F;
/*  94 */     float f7 = 0.5F;
/*  95 */     float f8 = 0.5F;
/*  96 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  97 */     GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  98 */     GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*  99 */     GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 100 */     tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/* 101 */     tessellator.getWorldRenderer();
/* 102 */     tessellator.getWorldRenderer().pos(0.0F - f7, 0.0F - f8, 0.0D).tex(f2, f5).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 103 */     tessellator.getWorldRenderer().pos(f6 - f7, 0.0F - f8, 0.0D).tex(f3, f5).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 104 */     tessellator.getWorldRenderer().pos(f6 - f7, 1.0F - f8, 0.0D).tex(f3, f4).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 105 */     tessellator.getWorldRenderer().pos(0.0F - f7, 1.0F - f8, 0.0D).tex(f2, f4).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 106 */     tessellator.draw();
/* 107 */     GL11.glDisable(3042);
/* 108 */     GL11.glDisable(32826);
/* 109 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 110 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */ 
/*     */   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
/*     */   {
/* 116 */     renderEntityAt(entity, d, d1, d2, f, f1);
/*     */   }
/*     */   
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 122 */     return ParticleEngine.particleTexture;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\projectile\RenderEldritchOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */