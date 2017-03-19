/*     */ package thaumcraft.client.renderers.entity.projectile;
/*     */ 
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.client.fx.ParticleEngine;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.common.entities.projectile.EntityPrimalArrow;
/*     */ import thaumcraft.common.items.resources.ItemShard.ShardType;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RenderPrimalArrow extends Render
/*     */ {
/*     */   public RenderPrimalArrow(RenderManager renderManager)
/*     */   {
/*  25 */     super(renderManager);
/*     */   }
/*     */   
/*  28 */   private static final ResourceLocation arrowTextures = new ResourceLocation("textures/entity/arrow.png");
/*  29 */   int size1 = 0;
/*  30 */   int size2 = 0;
/*     */   
/*     */ 
/*     */   public void renderArrow(EntityPrimalArrow arrow, double x, double y, double z, float ns, float prt)
/*     */   {
/*  35 */     bindEntityTexture(arrow);
/*  36 */     GL11.glPushMatrix();
/*     */     
/*  38 */     GL11.glEnable(3042);
/*  39 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  41 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*  42 */     GL11.glRotatef(arrow.prevRotationYaw + (arrow.rotationYaw - arrow.prevRotationYaw) * prt - 90.0F, 0.0F, 1.0F, 0.0F);
/*  43 */     GL11.glRotatef(arrow.prevRotationPitch + (arrow.rotationPitch - arrow.prevRotationPitch) * prt, 0.0F, 0.0F, 1.0F);
/*  44 */     Tessellator tessellator = Tessellator.getInstance();
/*  45 */     byte b0 = 0;
/*  46 */     float f2 = 0.0F;
/*  47 */     float f3 = 0.5F;
/*  48 */     float f4 = (0 + b0 * 10) / 32.0F;
/*  49 */     float f5 = (5 + b0 * 10) / 32.0F;
/*  50 */     float f6 = 0.0F;
/*  51 */     float f7 = 0.15625F;
/*  52 */     float f8 = (5 + b0 * 10) / 32.0F;
/*  53 */     float f9 = (10 + b0 * 10) / 32.0F;
/*  54 */     float f10 = arrow.type < 8 ? 0.05625F : 0.033F;
/*  55 */     GL11.glEnable(32826);
/*  56 */     float f11 = arrow.arrowShake - prt;
/*     */     
/*  58 */     if (f11 > 0.0F)
/*     */     {
/*  60 */       float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
/*  61 */       GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
/*     */     }
/*     */     
/*  64 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, (100.0F - arrow.ticksInGround) / (arrow.type < 8 ? 100.0F : 20.0F));
/*     */     
/*  66 */     GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
/*  67 */     GL11.glScalef(f10, f10, f10);
/*  68 */     GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
/*  69 */     GL11.glNormal3f(f10, 0.0F, 0.0F);
/*  70 */     tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX);
/*  71 */     tessellator.getWorldRenderer().pos(-7.0D, -2.0D, -2.0D).tex(f6, f8).endVertex();
/*  72 */     tessellator.getWorldRenderer().pos(-7.0D, -2.0D, 2.0D).tex(f7, f8).endVertex();
/*  73 */     tessellator.getWorldRenderer().pos(-7.0D, 2.0D, 2.0D).tex(f7, f9).endVertex();
/*  74 */     tessellator.getWorldRenderer().pos(-7.0D, 2.0D, -2.0D).tex(f6, f9).endVertex();
/*  75 */     tessellator.draw();
/*  76 */     GL11.glNormal3f(-f10, 0.0F, 0.0F);
/*  77 */     tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX);
/*  78 */     tessellator.getWorldRenderer().pos(-7.0D, 2.0D, -2.0D).tex(f6, f8).endVertex();
/*  79 */     tessellator.getWorldRenderer().pos(-7.0D, 2.0D, 2.0D).tex(f7, f8).endVertex();
/*  80 */     tessellator.getWorldRenderer().pos(-7.0D, -2.0D, 2.0D).tex(f7, f9).endVertex();
/*  81 */     tessellator.getWorldRenderer().pos(-7.0D, -2.0D, -2.0D).tex(f6, f9).endVertex();
/*  82 */     tessellator.draw();
/*     */     
/*  84 */     for (int i = 0; i < 4; i++)
/*     */     {
/*  86 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  87 */       GL11.glNormal3f(0.0F, 0.0F, f10);
/*  88 */       tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX);
/*  89 */       tessellator.getWorldRenderer().pos(-8.0D, -2.0D, 0.0D).tex(f2, f4).endVertex();
/*  90 */       tessellator.getWorldRenderer().pos(8.0D, -2.0D, 0.0D).tex(f3, f4).endVertex();
/*  91 */       tessellator.getWorldRenderer().pos(8.0D, 2.0D, 0.0D).tex(f3, f5).endVertex();
/*  92 */       tessellator.getWorldRenderer().pos(-8.0D, 2.0D, 0.0D).tex(f2, f5).endVertex();
/*  93 */       tessellator.draw();
/*     */     }
/*     */     
/*  96 */     GL11.glDisable(32826);
/*     */     
/*  98 */     GL11.glDisable(3042);
/*  99 */     GL11.glPopMatrix();
/*     */     
/* 101 */     GL11.glPushMatrix();
/*     */     
/* 103 */     bindTexture(ParticleEngine.particleTexture);
/*     */     
/* 105 */     if (arrow.type < 8) {
/* 106 */       int i = 72 + arrow.ticksExisted % 8;
/* 107 */       UtilsFX.renderFacingQuad(arrow.posX, arrow.posY, arrow.posZ, 16, 16, i, 0.25F, ItemShard.ShardType.byMetadata(arrow.type).getAspect().getColor(), (100.0F - arrow.ticksInGround) / 100.0F, arrow.type < 5 ? 1 : 771, prt);
/*     */     }
/*     */     
/*     */ 
/* 111 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */ 
/*     */   protected ResourceLocation getArrowTextures(EntityPrimalArrow par1EntityArrow)
/*     */   {
/* 117 */     return arrowTextures;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/*     */   {
/* 126 */     return getArrowTextures((EntityPrimalArrow)par1Entity);
/*     */   }
/*     */   
/*     */ 
/*     */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/*     */   {
/* 132 */     renderArrow((EntityPrimalArrow)par1Entity, par2, par4, par6, par8, par9);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\projectile\RenderPrimalArrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */