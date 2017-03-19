/*     */ package thaumcraft.client.renderers.entity;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.items.tools.ItemThaumometer;
/*     */ import thaumcraft.common.lib.aura.EntityAuraNode;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RenderAuraNode
/*     */   extends Render
/*     */ {
/*     */   public RenderAuraNode(RenderManager rm)
/*     */   {
/*  30 */     super(rm);
/*  31 */     this.shadowSize = 0.0F;
/*     */   }
/*     */   
/*  34 */   int size1 = 4;
/*  35 */   int size2 = 0;
/*     */   
/*     */ 
/*     */   private void doRender(EntityAuraNode entity, double x, double y, double z, float fq, float pticks)
/*     */   {
/*  40 */     if (entity.isDead) return;
/*  41 */     double vr = 8000.0D;
/*  42 */     long t = System.currentTimeMillis();
/*  43 */     boolean canSee = EntityUtils.hasRevealer(Minecraft.getMinecraft().getRenderViewEntity());
/*  44 */     if (!canSee) {
/*  45 */       canSee = (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) && ((Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof ItemThaumometer)) && (EntityUtils.isVisibleTo(0.8F, Minecraft.getMinecraft().getRenderViewEntity(), entity, 16.0F));
/*     */       
/*     */ 
/*  48 */       vr = 300.0D;
/*     */     }
/*     */     
/*  51 */     if (!canSee) { return;
/*     */     }
/*  53 */     double d = entity.getDistanceSqToEntity(Minecraft.getMinecraft().getRenderViewEntity());
/*  54 */     if (d > vr) { return;
/*     */     }
/*  56 */     float alpha = 1.0F - (float)Math.min(1.0D, d / (vr * 0.8999999761581421D));
/*     */     
/*  58 */     int color = 8947848;
/*  59 */     int blend = 1;
/*  60 */     int type = 1;
/*  61 */     float size = 0.15F + entity.getNodeSize() / (Config.AURABASE * 1.5F);
/*  62 */     if (entity.getAspect() != null) {
/*  63 */       color = entity.getAspect().getColor();
/*  64 */       blend = entity.getAspect().getBlend();
/*  65 */       type = 1 + entity.getNodeType();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  71 */     GlStateManager.pushMatrix();
/*  72 */     bindTexture(texture);
/*  73 */     GlStateManager.disableDepth();
/*     */     
/*  75 */     UtilsFX.renderFacingQuad(entity.posX, entity.posY, entity.posZ, 32, 32, entity.ticksExisted % 32, size, color, 0.75F * alpha, blend, pticks);
/*     */     
/*     */ 
/*     */ 
/*  79 */     float s = 1.0F - MathHelper.sin((entity.ticksExisted + pticks) / 8.0F) / 5.0F;
/*     */     
/*  81 */     UtilsFX.renderFacingQuad(entity.posX, entity.posY, entity.posZ, 32, 32, 800 + entity.ticksExisted % 16, s * size * 0.7F, color, 0.9F * alpha, blend, pticks);
/*     */     
/*     */ 
/*     */ 
/*  85 */     UtilsFX.renderFacingQuad(entity.posX, entity.posY, entity.posZ, 32, 32, 32 * type + entity.ticksExisted % 32, size / 3.0F, 16777215, 1.0F * alpha, type == 2 ? 771 : 1, pticks);
/*     */     
/*     */ 
/*     */ 
/*  89 */     GlStateManager.enableDepth();
/*     */     
/*  91 */     GlStateManager.popMatrix();
/*     */     
/*  93 */     if (d < 30.0D) {
/*  94 */       float sc = 1.0F - (float)Math.min(1.0D, d / 25.0D);
/*  95 */       GlStateManager.pushMatrix();
/*  96 */       GlStateManager.translate(x, y, z);
/*  97 */       GlStateManager.scale(0.025D * sc, 0.025D * sc, 0.025D * sc);
/*  98 */       UtilsFX.rotateToPlayer();
/*  99 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 100 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 105 */       UtilsFX.drawTag(-8, -32, entity.getAspect(), entity.getNodeSize(), 0, 0.0D);
/* 106 */       GlStateManager.scale(0.5D, 0.5D, 0.5D);
/* 107 */       String text = StatCollector.translateToLocal("nodetype." + entity.getNodeType());
/* 108 */       int sw = Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
/* 109 */       Minecraft.getMinecraft().fontRendererObj.drawString(text, -sw / 2.0F, -72.0F, 16777215, false);
/* 110 */       GlStateManager.popMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
/*     */   {
/* 118 */     doRender((EntityAuraNode)entity, d, d1, d2, f, f1);
/*     */   }
/*     */   
/* 121 */   public static final ResourceLocation texture = new ResourceLocation("thaumcraft", "textures/misc/nodes.png");
/*     */   
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 125 */     return texture;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\RenderAuraNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */