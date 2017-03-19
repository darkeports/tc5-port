/*      */ package thaumcraft.client.lib;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.gui.Gui;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.gui.inventory.GuiContainer;
/*      */ import net.minecraft.client.model.PositionTextureVertex;
/*      */ import net.minecraft.client.particle.EffectRenderer;
/*      */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraftforge.fml.relauncher.ReflectionHelper;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import thaumcraft.api.aspects.Aspect;
/*      */ import thaumcraft.client.fx.ParticleEngine;
/*      */ import thaumcraft.common.config.Config;
/*      */ 
/*      */ 
/*      */ public class UtilsFX
/*      */ {
/*   44 */   public static final VertexFormat VERTEXFORMAT_POS_TEX_CO_LM_NO = new VertexFormat().addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   53 */   public static final String[] colorNames = { "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
/*      */   
/*      */ 
/*      */ 
/*   57 */   public static final String[] colorCodes = { "§f", "§6", "§d", "§9", "§e", "§a", "§d", "§8", "§7", "§b", "§5", "§9", "§4", "§2", "§c", "§8" };
/*      */   
/*      */ 
/*      */ 
/*   61 */   public static final int[] colors = { 15790320, 15435844, 12801229, 6719955, 14602026, 4312372, 14188952, 4408131, 10526880, 2651799, 8073150, 2437522, 5320730, 3887386, 11743532, 1973019 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void renderFacingQuad(double px, double py, double pz, int gridX, int gridY, int frame, float scale, int color, float alpha, int blend, float partialTicks)
/*      */   {
/*   99 */     if ((Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer)) {
/*  100 */       Tessellator tessellator = Tessellator.getInstance();
/*  101 */       WorldRenderer wr = tessellator.getWorldRenderer();
/*  102 */       float arX = ActiveRenderInfo.getRotationX();
/*  103 */       float arZ = ActiveRenderInfo.getRotationZ();
/*  104 */       float arYZ = ActiveRenderInfo.getRotationYZ();
/*  105 */       float arXY = ActiveRenderInfo.getRotationXY();
/*  106 */       float arXZ = ActiveRenderInfo.getRotationXZ();
/*      */       
/*  108 */       EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
/*  109 */       double iPX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
/*  110 */       double iPY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
/*  111 */       double iPZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
/*      */       
/*  113 */       GlStateManager.pushMatrix();
/*  114 */       GlStateManager.enableBlend();
/*  115 */       GlStateManager.blendFunc(770, blend);
/*  116 */       GlStateManager.alphaFunc(516, 0.003921569F);
/*  117 */       GlStateManager.depthMask(false);
/*      */       
/*  119 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */       
/*  121 */       GL11.glTranslated(-iPX, -iPY, -iPZ);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  126 */       Vec3 v1 = new Vec3(-arX * scale - arYZ * scale, -arXZ * scale, -arZ * scale - arXY * scale);
/*  127 */       Vec3 v2 = new Vec3(-arX * scale + arYZ * scale, arXZ * scale, -arZ * scale + arXY * scale);
/*  128 */       Vec3 v3 = new Vec3(arX * scale + arYZ * scale, arXZ * scale, arZ * scale + arXY * scale);
/*  129 */       Vec3 v4 = new Vec3(arX * scale - arYZ * scale, -arXZ * scale, arZ * scale - arXY * scale);
/*      */       
/*  131 */       int xm = frame % gridX;
/*  132 */       int ym = frame / gridY;
/*      */       
/*  134 */       float f1 = xm / gridX;
/*  135 */       float f2 = f1 + 1.0F / gridX;
/*  136 */       float f3 = ym / gridY;
/*  137 */       float f4 = f3 + 1.0F / gridY;
/*      */       
/*  139 */       TexturedQuadTC quad = new TexturedQuadTC(new PositionTextureVertex[] { new PositionTextureVertex((float)px + (float)v1.xCoord, (float)py + (float)v1.yCoord, (float)pz + (float)v1.zCoord, f2, f4), new PositionTextureVertex((float)px + (float)v2.xCoord, (float)py + (float)v2.yCoord, (float)pz + (float)v2.zCoord, f2, f3), new PositionTextureVertex((float)px + (float)v3.xCoord, (float)py + (float)v3.yCoord, (float)pz + (float)v3.zCoord, f1, f3), new PositionTextureVertex((float)px + (float)v4.xCoord, (float)py + (float)v4.yCoord, (float)pz + (float)v4.zCoord, f1, f4) });
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  144 */       quad.draw(tessellator.getWorldRenderer(), 1.0F, 220, color, alpha);
/*      */       
/*  146 */       GlStateManager.depthMask(true);
/*  147 */       GlStateManager.blendFunc(770, 771);
/*  148 */       GlStateManager.disableBlend();
/*  149 */       GlStateManager.alphaFunc(516, 0.1F);
/*      */       
/*  151 */       GlStateManager.popMatrix();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void drawTexturedQuad(int par1, int par2, int par3, int par4, int par5, int par6, double zLevel)
/*      */   {
/*  279 */     float var7 = 0.00390625F;
/*  280 */     float var8 = 0.00390625F;
/*  281 */     Tessellator var9 = Tessellator.getInstance();
/*  282 */     var9.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX);
/*  283 */     var9.getWorldRenderer().pos(par1 + 0, par2 + par6, zLevel).tex((par3 + 0) * var7, (par4 + par6) * var8).endVertex();
/*  284 */     var9.getWorldRenderer().pos(par1 + par5, par2 + par6, zLevel).tex((par3 + par5) * var7, (par4 + par6) * var8).endVertex();
/*  285 */     var9.getWorldRenderer().pos(par1 + par5, par2 + 0, zLevel).tex((par3 + par5) * var7, (par4 + 0) * var8).endVertex();
/*  286 */     var9.getWorldRenderer().pos(par1 + 0, par2 + 0, zLevel).tex((par3 + 0) * var7, (par4 + 0) * var8).endVertex();
/*  287 */     var9.draw();
/*      */   }
/*      */   
/*      */   public static void drawTexturedQuadFull(int par1, int par2, double zLevel)
/*      */   {
/*  292 */     Tessellator var9 = Tessellator.getInstance();
/*  293 */     var9.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX);
/*  294 */     var9.getWorldRenderer().pos(par1 + 0, par2 + 16, zLevel).tex(0.0D, 1.0D).endVertex();
/*  295 */     var9.getWorldRenderer().pos(par1 + 16, par2 + 16, zLevel).tex(1.0D, 1.0D).endVertex();
/*  296 */     var9.getWorldRenderer().pos(par1 + 16, par2 + 0, zLevel).tex(1.0D, 0.0D).endVertex();
/*  297 */     var9.getWorldRenderer().pos(par1 + 0, par2 + 0, zLevel).tex(0.0D, 0.0D).endVertex();
/*  298 */     var9.draw();
/*      */   }
/*      */   
/*      */   public static void drawItemInGUI(int x, int y, ItemStack stack, GuiScreen gui)
/*      */   {
/*  303 */     boolean blendon = GL11.glIsEnabled(3042);
/*  304 */     GlStateManager.pushMatrix();
/*  305 */     GlStateManager.enableRescaleNormal();
/*  306 */     GlStateManager.enableBlend();
/*  307 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  308 */     RenderHelper.enableGUIStandardItemLighting();
/*  309 */     gui.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
/*  310 */     gui.mc.getRenderItem().renderItemOverlayIntoGUI(gui.mc.fontRendererObj, stack, x, y, "");
/*  311 */     RenderHelper.disableStandardItemLighting();
/*  312 */     GlStateManager.disableRescaleNormal();
/*  313 */     if (!blendon) GlStateManager.disableBlend();
/*  314 */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */   public static void renderItemInGUI(int x, int y, int z, ItemStack stack) {
/*  318 */     Minecraft mc = Minecraft.getMinecraft();
/*      */     try {
/*  320 */       GlStateManager.pushMatrix();
/*  321 */       RenderHelper.enableGUIStandardItemLighting();
/*  322 */       GlStateManager.disableLighting();
/*  323 */       GlStateManager.enableRescaleNormal();
/*  324 */       GlStateManager.enableColorMaterial();
/*  325 */       GlStateManager.enableLighting();
/*  326 */       mc.getRenderItem().zLevel = z;
/*  327 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
/*  328 */       mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
/*  329 */       mc.getRenderItem().zLevel = 0.0F;
/*  330 */       GlStateManager.disableLighting();
/*  331 */       GlStateManager.popMatrix();
/*  332 */       GlStateManager.enableLighting();
/*  333 */       GlStateManager.enableDepth();
/*  334 */       RenderHelper.enableStandardItemLighting();
/*      */     }
/*      */     catch (Exception e) {}
/*      */   }
/*      */   
/*      */   public static void renderQuadCentered(ResourceLocation texture, float scale, float red, float green, float blue, int brightness, int blend, float opacity)
/*      */   {
/*  341 */     Minecraft.getMinecraft().renderEngine.bindTexture(texture);
/*  342 */     renderQuadCentered(1, 1, 0, scale, red, green, blue, brightness, blend, opacity);
/*      */   }
/*      */   
/*      */   public static void renderQuadCentered(ResourceLocation texture, int gridX, int gridY, int frame, float scale, float red, float green, float blue, int brightness, int blend, float opacity)
/*      */   {
/*  347 */     Minecraft.getMinecraft().renderEngine.bindTexture(texture);
/*  348 */     renderQuadCentered(gridX, gridY, frame, scale, red, green, blue, brightness, blend, opacity);
/*      */   }
/*      */   
/*      */   public static void renderQuadCentered() {
/*  352 */     renderQuadCentered(1, 1, 0, 1.0F, 1.0F, 1.0F, 1.0F, 200, 771, 1.0F);
/*      */   }
/*      */   
/*      */   public static void renderQuadCentered(int gridX, int gridY, int frame, float scale, float red, float green, float blue, int brightness, int blend, float opacity)
/*      */   {
/*  357 */     boolean blendon = GL11.glIsEnabled(3042);
/*  358 */     Tessellator tessellator = Tessellator.getInstance();
/*      */     
/*  360 */     GlStateManager.enableBlend();
/*  361 */     GlStateManager.blendFunc(770, blend);
/*      */     
/*  363 */     int xm = frame % gridX;
/*  364 */     int ym = frame / gridY;
/*      */     
/*  366 */     float f1 = xm / gridX;
/*  367 */     float f2 = f1 + 1.0F / gridX;
/*  368 */     float f3 = ym / gridY;
/*  369 */     float f4 = f3 + 1.0F / gridY;
/*      */     
/*      */ 
/*  372 */     Color c = new Color(red, green, blue);
/*      */     
/*  374 */     TexturedQuadTC quad = new TexturedQuadTC(new PositionTextureVertex[] { new PositionTextureVertex(-0.5F, 0.5F, 0.0F, f2, f4), new PositionTextureVertex(0.5F, 0.5F, 0.0F, f2, f3), new PositionTextureVertex(0.5F, -0.5F, 0.0F, f1, f3), new PositionTextureVertex(-0.5F, -0.5F, 0.0F, f1, f4) });
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  380 */     quad.draw(tessellator.getWorldRenderer(), scale, brightness, c.getRGB(), opacity);
/*      */     
/*  382 */     if (!blendon) { GlStateManager.disableBlend();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void renderQuadFromIcon(TextureAtlasSprite icon, float scale, float red, float green, float blue, int brightness, int blend, float opacity)
/*      */   {
/*  388 */     Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/*      */     
/*  390 */     Tessellator tessellator = Tessellator.getInstance();
/*      */     
/*  392 */     float f1 = icon.getMaxU();
/*  393 */     float f2 = icon.getMinV();
/*  394 */     float f3 = icon.getMinU();
/*  395 */     float f4 = icon.getMaxV();
/*      */     
/*  397 */     GL11.glScalef(scale, scale, scale);
/*  398 */     GL11.glEnable(3042);
/*  399 */     GL11.glBlendFunc(770, blend);
/*      */     
/*  401 */     GL11.glColor4f(red, green, blue, opacity);
/*      */     
/*  403 */     if (brightness > -1) {
/*  404 */       tessellator.getWorldRenderer().begin(7, VERTEXFORMAT_POS_TEX_CO_LM_NO);
/*      */     } else {
/*  406 */       tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
/*      */     }
/*  408 */     int j = brightness >> 16 & 0xFFFF;
/*  409 */     int k = brightness & 0xFFFF;
/*      */     
/*  411 */     tessellator.getWorldRenderer().pos(0.0D, 0.0D, 0.0D).tex(f1, f4).color(red, green, blue, opacity);
/*  412 */     if (brightness > -1) tessellator.getWorldRenderer().lightmap(j, k);
/*  413 */     tessellator.getWorldRenderer().normal(0.0F, 0.0F, 1.0F);
/*  414 */     tessellator.getWorldRenderer().endVertex();
/*      */     
/*  416 */     tessellator.getWorldRenderer().pos(1.0D, 0.0D, 0.0D).tex(f3, f4).color(red, green, blue, opacity);
/*  417 */     if (brightness > -1) tessellator.getWorldRenderer().lightmap(j, k);
/*  418 */     tessellator.getWorldRenderer().normal(0.0F, 0.0F, 1.0F);
/*  419 */     tessellator.getWorldRenderer().endVertex();
/*      */     
/*  421 */     tessellator.getWorldRenderer().pos(1.0D, 1.0D, 0.0D).tex(f3, f2).color(red, green, blue, opacity);
/*  422 */     if (brightness > -1) tessellator.getWorldRenderer().lightmap(j, k);
/*  423 */     tessellator.getWorldRenderer().normal(0.0F, 0.0F, 1.0F);
/*  424 */     tessellator.getWorldRenderer().endVertex();
/*      */     
/*  426 */     tessellator.getWorldRenderer().pos(0.0D, 1.0D, 0.0D).tex(f1, f2).color(red, green, blue, opacity);
/*  427 */     if (brightness > -1) tessellator.getWorldRenderer().lightmap(j, k);
/*  428 */     tessellator.getWorldRenderer().normal(0.0F, 0.0F, 1.0F);
/*  429 */     tessellator.getWorldRenderer().endVertex();
/*      */     
/*  431 */     tessellator.draw();
/*      */     
/*  433 */     GL11.glDisable(3042);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void drawTag(int x, int y, Aspect aspect, float amount, int bonus, double z, int blend, float alpha)
/*      */   {
/*  440 */     drawTag(x, y, aspect, amount, bonus, z, blend, alpha, false);
/*      */   }
/*      */   
/*      */   public static void drawTag(int x, int y, Aspect aspect, float amt, int bonus, double z) {
/*  444 */     drawTag(x, y, aspect, amt, bonus, z, 771, 1.0F, false);
/*      */   }
/*      */   
/*      */   public static void drawTag(int x, int y, Aspect aspect) {
/*  448 */     drawTag(x, y, aspect, 0.0F, 0, 0.0D, 771, 1.0F, true);
/*      */   }
/*      */   
/*  451 */   static DecimalFormat myFormatter = new DecimalFormat("#######.##");
/*      */   
/*      */   public static void drawTag(int x, int y, Aspect aspect, float amount, int bonus, double z, int blend, float alpha, boolean bw) {
/*  454 */     drawTag(x, y, aspect, amount, bonus, z, blend, alpha, bw);
/*      */   }
/*      */   
/*      */   public static void drawTag(double x, double y, Aspect aspect, float amount, int bonus, double z, int blend, float alpha, boolean bw) {
/*  458 */     if (aspect == null) return;
/*  459 */     boolean blendon = GL11.glIsEnabled(3042);
/*  460 */     Minecraft mc = Minecraft.getMinecraft();
/*  461 */     boolean isLightingEnabled = GL11.glIsEnabled(2896);
/*      */     
/*      */ 
/*  464 */     Color color = new Color(aspect.getColor());
/*  465 */     GL11.glPushMatrix();
/*  466 */     GL11.glDisable(2896);
/*  467 */     GL11.glAlphaFunc(516, 0.003921569F);
/*  468 */     GL11.glEnable(3042);
/*  469 */     GL11.glBlendFunc(770, blend);
/*      */     
/*  471 */     GL11.glPushMatrix();
/*      */     
/*  473 */     mc.renderEngine.bindTexture(aspect.getImage());
/*  474 */     if (!bw) {
/*  475 */       GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha);
/*      */     } else {
/*  477 */       GL11.glColor4f(0.1F, 0.1F, 0.1F, alpha * 0.8F);
/*      */     }
/*      */     
/*  480 */     Tessellator var9 = Tessellator.getInstance();
/*      */     
/*  482 */     var9.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*  483 */     if (!bw) {
/*  484 */       var9.getWorldRenderer().pos(x + 0.0D, y + 16.0D, z).tex(0.0D, 1.0D).color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha).endVertex();
/*  485 */       var9.getWorldRenderer().pos(x + 16.0D, y + 16.0D, z).tex(1.0D, 1.0D).color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha).endVertex();
/*  486 */       var9.getWorldRenderer().pos(x + 16.0D, y + 0.0D, z).tex(1.0D, 0.0D).color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha).endVertex();
/*  487 */       var9.getWorldRenderer().pos(x + 0.0D, y + 0.0D, z).tex(0.0D, 0.0D).color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha).endVertex();
/*      */     } else {
/*  489 */       var9.getWorldRenderer().pos(x + 0.0D, y + 16.0D, z).tex(0.0D, 1.0D).color(0.1F, 0.1F, 0.1F, alpha * 0.8F).endVertex();
/*  490 */       var9.getWorldRenderer().pos(x + 16.0D, y + 16.0D, z).tex(1.0D, 1.0D).color(0.1F, 0.1F, 0.1F, alpha * 0.8F).endVertex();
/*  491 */       var9.getWorldRenderer().pos(x + 16.0D, y + 0.0D, z).tex(1.0D, 0.0D).color(0.1F, 0.1F, 0.1F, alpha * 0.8F).endVertex();
/*  492 */       var9.getWorldRenderer().pos(x + 0.0D, y + 0.0D, z).tex(0.0D, 0.0D).color(0.1F, 0.1F, 0.1F, alpha * 0.8F).endVertex();
/*      */     }
/*  494 */     var9.draw();
/*      */     
/*  496 */     GL11.glPopMatrix();
/*      */     
/*  498 */     if (amount > 0.0F) {
/*  499 */       GL11.glPushMatrix();
/*  500 */       float q = 0.5F;
/*  501 */       if (!Config.largeTagText) {
/*  502 */         GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  503 */         q = 1.0F;
/*      */       }
/*  505 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  506 */       String am = myFormatter.format(amount);
/*  507 */       int sw = mc.fontRendererObj.getStringWidth(am);
/*      */       
/*  509 */       mc.fontRendererObj.drawStringWithShadow(am, (32 - sw + (int)x * 2) * q, (32 - mc.fontRendererObj.FONT_HEIGHT + (int)y * 2) * q, 16777215);
/*  510 */       GL11.glPopMatrix();
/*      */     }
/*      */     
/*  513 */     if (bonus > 0) {
/*  514 */       GL11.glPushMatrix();
/*  515 */       mc.renderEngine.bindTexture(ParticleEngine.particleTexture);
/*  516 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  517 */       int px = 16 * (mc.thePlayer.ticksExisted % 16);
/*  518 */       drawTexturedQuad((int)x - 4, (int)y - 4, px, 80, 16, 16, z);
/*  519 */       if (bonus > 1) {
/*  520 */         float q = 0.5F;
/*  521 */         if (!Config.largeTagText) {
/*  522 */           GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  523 */           q = 1.0F;
/*      */         }
/*  525 */         String am = "" + bonus;
/*  526 */         int sw = mc.fontRendererObj.getStringWidth(am) / 2;
/*  527 */         GL11.glTranslated(0.0D, 0.0D, -1.0D);
/*  528 */         mc.fontRendererObj.drawStringWithShadow(am, (8 - sw + (int)x * 2) * q, (15 - mc.fontRendererObj.FONT_HEIGHT + (int)y * 2) * q, 16777215);
/*      */       }
/*  530 */       GL11.glPopMatrix();
/*      */     }
/*      */     
/*  533 */     if (!blendon) {
/*  534 */       GL11.glDisable(3042);
/*      */     }
/*  536 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  537 */     GL11.glAlphaFunc(516, 0.1F);
/*  538 */     if (isLightingEnabled) {
/*  539 */       GL11.glEnable(2896);
/*      */     } else {
/*  541 */       GL11.glDisable(2896);
/*      */     }
/*  543 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void drawCustomTooltip(GuiScreen gui, FontRenderer fr, List textList, int x, int y, int subTipColor)
/*      */   {
/*  578 */     if (!textList.isEmpty())
/*      */     {
/*  580 */       Minecraft mc = Minecraft.getMinecraft();
/*  581 */       ScaledResolution scaledresolution = new ScaledResolution(mc);
/*  582 */       int sf = scaledresolution.getScaleFactor();
/*  583 */       GlStateManager.disableRescaleNormal();
/*  584 */       RenderHelper.disableStandardItemLighting();
/*  585 */       GlStateManager.disableLighting();
/*  586 */       GlStateManager.disableDepth();
/*  587 */       int max = 240;
/*  588 */       int mx = Mouse.getEventX();
/*  589 */       boolean flip = false;
/*  590 */       if ((max + 24) * sf + mx > mc.displayWidth) {
/*  591 */         max = (mc.displayWidth - mx) / sf - 24;
/*  592 */         if (max < 120) {
/*  593 */           max = 240;
/*  594 */           flip = true;
/*      */         }
/*      */       }
/*      */       
/*  598 */       int widestLineWidth = 0;
/*  599 */       Iterator textLineEntry = textList.iterator();
/*  600 */       boolean b = false;
/*  601 */       while (textLineEntry.hasNext())
/*      */       {
/*  603 */         String textLine = (String)textLineEntry.next();
/*  604 */         if (fr.getStringWidth(textLine) > max) {
/*  605 */           b = true;
/*  606 */           break;
/*      */         }
/*      */       }
/*      */       
/*  610 */       if (b) {
/*  611 */         List tl = new ArrayList();
/*  612 */         for (Object o : textList) {
/*  613 */           textLine = (String)o;
/*  614 */           List tl2 = fr.listFormattedStringToWidth(textLine, max);
/*  615 */           for (Object o2 : tl2) {
/*  616 */             String textLine2 = ((String)o2).trim();
/*  617 */             if (textLine.startsWith("@@")) textLine2 = "@@" + textLine2;
/*  618 */             tl.add(textLine2);
/*      */           } }
/*      */         String textLine;
/*  621 */         textList = tl;
/*      */       }
/*      */       
/*  624 */       Iterator textLines = textList.iterator();
/*      */       
/*  626 */       int totalHeight = -2;
/*      */       
/*  628 */       while (textLines.hasNext())
/*      */       {
/*  630 */         String textLine = (String)textLines.next();
/*  631 */         int lineWidth = fr.getStringWidth(textLine);
/*  632 */         if ((textLine.startsWith("@@")) && (!fr.getUnicodeFlag()))
/*      */         {
/*  634 */           lineWidth /= 2;
/*      */         }
/*  636 */         if (lineWidth > widestLineWidth)
/*      */         {
/*  638 */           widestLineWidth = lineWidth;
/*      */         }
/*  640 */         totalHeight += ((textLine.startsWith("@@")) && (!fr.getUnicodeFlag()) ? 7 : 10);
/*      */       }
/*      */       
/*  643 */       int sX = x + 12;
/*  644 */       int sY = y - 12;
/*      */       
/*  646 */       if (textList.size() > 1)
/*      */       {
/*  648 */         totalHeight += 2;
/*      */       }
/*      */       
/*  651 */       if (flip) { sX -= widestLineWidth + 24;
/*      */       }
/*  653 */       Minecraft.getMinecraft().getRenderItem().zLevel = 300.0F;
/*  654 */       int var10 = -267386864;
/*  655 */       drawGradientRect(sX - 3, sY - 4, sX + widestLineWidth + 3, sY - 3, var10, var10);
/*  656 */       drawGradientRect(sX - 3, sY + totalHeight + 3, sX + widestLineWidth + 3, sY + totalHeight + 4, var10, var10);
/*  657 */       drawGradientRect(sX - 3, sY - 3, sX + widestLineWidth + 3, sY + totalHeight + 3, var10, var10);
/*  658 */       drawGradientRect(sX - 4, sY - 3, sX - 3, sY + totalHeight + 3, var10, var10);
/*  659 */       drawGradientRect(sX + widestLineWidth + 3, sY - 3, sX + widestLineWidth + 4, sY + totalHeight + 3, var10, var10);
/*  660 */       int var11 = 1347420415;
/*  661 */       int var12 = (var11 & 0xFEFEFE) >> 1 | var11 & 0xFF000000;
/*  662 */       drawGradientRect(sX - 3, sY - 3 + 1, sX - 3 + 1, sY + totalHeight + 3 - 1, var11, var12);
/*  663 */       drawGradientRect(sX + widestLineWidth + 2, sY - 3 + 1, sX + widestLineWidth + 3, sY + totalHeight + 3 - 1, var11, var12);
/*  664 */       drawGradientRect(sX - 3, sY - 3, sX + widestLineWidth + 3, sY - 3 + 1, var11, var11);
/*  665 */       drawGradientRect(sX - 3, sY + totalHeight + 2, sX + widestLineWidth + 3, sY + totalHeight + 3, var12, var12);
/*      */       
/*  667 */       for (int i = 0; i < textList.size(); i++)
/*      */       {
/*  669 */         GL11.glPushMatrix();
/*  670 */         GL11.glTranslatef(sX, sY, 0.0F);
/*      */         
/*  672 */         String tl = (String)textList.get(i);
/*  673 */         boolean shift = false;
/*      */         
/*  675 */         GL11.glPushMatrix();
/*  676 */         if ((tl.startsWith("@@")) && (!fr.getUnicodeFlag())) {
/*  677 */           sY += 7;
/*  678 */           GL11.glScalef(0.5F, 0.5F, 1.0F);
/*  679 */           shift = true;
/*      */         } else {
/*  681 */           sY += 10;
/*      */         }
/*  683 */         tl = tl.replaceAll("@@", "");
/*      */         
/*  685 */         if (subTipColor != -99) {
/*  686 */           if (i == 0)
/*      */           {
/*  688 */             tl = "§" + Integer.toHexString(subTipColor) + tl;
/*      */           }
/*      */           else
/*      */           {
/*  692 */             tl = "§7" + tl;
/*      */           }
/*      */         }
/*  695 */         GL11.glTranslated(0.0D, 0.0D, 301.0D);
/*  696 */         fr.drawStringWithShadow(tl, 0.0F, shift ? 3.0F : 0.0F, -1);
/*  697 */         GL11.glPopMatrix();
/*      */         
/*  699 */         if (i == 0)
/*      */         {
/*  701 */           sY += 2;
/*      */         }
/*      */         
/*  704 */         GL11.glPopMatrix();
/*      */       }
/*      */       
/*  707 */       Minecraft.getMinecraft().getRenderItem().zLevel = 0.0F;
/*  708 */       GlStateManager.enableLighting();
/*  709 */       GlStateManager.enableDepth();
/*  710 */       RenderHelper.enableStandardItemLighting();
/*  711 */       GlStateManager.enableRescaleNormal();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
/*      */   {
/*  720 */     boolean blendon = GL11.glIsEnabled(3042);
/*  721 */     float var7 = (par5 >> 24 & 0xFF) / 255.0F;
/*  722 */     float var8 = (par5 >> 16 & 0xFF) / 255.0F;
/*  723 */     float var9 = (par5 >> 8 & 0xFF) / 255.0F;
/*  724 */     float var10 = (par5 & 0xFF) / 255.0F;
/*  725 */     float var11 = (par6 >> 24 & 0xFF) / 255.0F;
/*  726 */     float var12 = (par6 >> 16 & 0xFF) / 255.0F;
/*  727 */     float var13 = (par6 >> 8 & 0xFF) / 255.0F;
/*  728 */     float var14 = (par6 & 0xFF) / 255.0F;
/*  729 */     GL11.glDisable(3553);
/*  730 */     GL11.glEnable(3042);
/*  731 */     GL11.glDisable(3008);
/*  732 */     GL11.glBlendFunc(770, 771);
/*  733 */     GL11.glShadeModel(7425);
/*  734 */     Tessellator var15 = Tessellator.getInstance();
/*  735 */     var15.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  736 */     var15.getWorldRenderer().pos(par3, par2, 300.0D).color(var8, var9, var10, var7).endVertex();
/*  737 */     var15.getWorldRenderer().pos(par1, par2, 300.0D).color(var8, var9, var10, var7).endVertex();
/*  738 */     var15.getWorldRenderer().pos(par1, par4, 300.0D).color(var12, var13, var14, var11).endVertex();
/*  739 */     var15.getWorldRenderer().pos(par3, par4, 300.0D).color(var12, var13, var14, var11).endVertex();
/*  740 */     var15.draw();
/*  741 */     GL11.glShadeModel(7424);
/*  742 */     if (!blendon) GL11.glDisable(3042);
/*  743 */     GL11.glEnable(3008);
/*  744 */     GL11.glEnable(3553);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getGuiXSize(GuiContainer gui)
/*      */   {
/*      */     try
/*      */     {
/*  983 */       return ((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, new String[] { "xSize", "f", "field_146999_f" })).intValue();
/*      */     }
/*      */     catch (Exception e) {}
/*  986 */     return 0;
/*      */   }
/*      */   
/*      */   public static int getGuiYSize(GuiContainer gui)
/*      */   {
/*      */     try {
/*  992 */       return ((Integer)ReflectionHelper.getPrivateValue(GuiContainer.class, gui, new String[] { "ySize", "g", "field_147000_g" })).intValue();
/*      */     }
/*      */     catch (Exception e) {}
/*  995 */     return 0;
/*      */   }
/*      */   
/*      */   public static float getGuiZLevel(Gui gui)
/*      */   {
/*      */     try {
/* 1001 */       return ((Float)ReflectionHelper.getPrivateValue(Gui.class, gui, new String[] { "zLevel", "e", "field_73735_i" })).floatValue();
/*      */     }
/*      */     catch (Exception e) {}
/* 1004 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public static ResourceLocation getMCParticleTexture()
/*      */   {
/*      */     try
/*      */     {
/* 1011 */       return (ResourceLocation)ReflectionHelper.getPrivateValue(EffectRenderer.class, null, new String[] { "particleTextures", "field_110737_b", "b" });
/*      */     } catch (Exception e) {}
/* 1013 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void renderBillboardQuad(double scale)
/*      */   {
/* 1066 */     GL11.glPushMatrix();
/* 1067 */     rotateToPlayer();
/* 1068 */     Tessellator tessellator = Tessellator.getInstance();
/* 1069 */     tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/* 1070 */     tessellator.getWorldRenderer().pos(-scale, -scale, 0.0D).tex(0.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1071 */     tessellator.getWorldRenderer().pos(-scale, scale, 0.0D).tex(0.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1072 */     tessellator.getWorldRenderer().pos(scale, scale, 0.0D).tex(1.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1073 */     tessellator.getWorldRenderer().pos(scale, -scale, 0.0D).tex(1.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1074 */     tessellator.draw();
/* 1075 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public static void renderBillboardQuad(double scale, int gridX, int gridY, int frame) {
/* 1079 */     GL11.glPushMatrix();
/* 1080 */     rotateToPlayer();
/*      */     
/* 1082 */     int xm = frame % gridX;
/* 1083 */     int ym = frame / gridY;
/*      */     
/* 1085 */     float f1 = xm / gridX;
/* 1086 */     float f2 = f1 + 1.0F / gridX;
/* 1087 */     float f3 = ym / gridY;
/* 1088 */     float f4 = f3 + 1.0F / gridY;
/*      */     
/* 1090 */     Tessellator tessellator = Tessellator.getInstance();
/* 1091 */     tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/* 1092 */     tessellator.getWorldRenderer().pos(-scale, -scale, 0.0D).tex(f2, f4).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1093 */     tessellator.getWorldRenderer().pos(-scale, scale, 0.0D).tex(f2, f3).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1094 */     tessellator.getWorldRenderer().pos(scale, scale, 0.0D).tex(f1, f3).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1095 */     tessellator.getWorldRenderer().pos(scale, -scale, 0.0D).tex(f1, f4).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1096 */     tessellator.draw();
/* 1097 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public static void renderBillboardQuadWithRotation(float rot, double scale) {
/* 1101 */     GL11.glPushMatrix();
/*      */     
/* 1103 */     rotateToPlayer();
/*      */     
/* 1105 */     GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
/*      */     
/* 1107 */     Tessellator tessellator = Tessellator.getInstance();
/* 1108 */     tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1109 */     tessellator.getWorldRenderer().pos(-scale, -scale, 0.0D).tex(0.0D, 0.0D).endVertex();
/* 1110 */     tessellator.getWorldRenderer().pos(-scale, scale, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 1111 */     tessellator.getWorldRenderer().pos(scale, scale, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 1112 */     tessellator.getWorldRenderer().pos(scale, -scale, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 1113 */     tessellator.draw();
/*      */     
/* 1115 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public static void rotateToPlayer() {
/* 1119 */     GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
/* 1120 */     GL11.glRotatef(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
/*      */   }
/*      */   
/*      */   public static boolean renderItemStack(Minecraft mc, ItemStack itm, int x, int y, String txt) {
/* 1124 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 1125 */     RenderItem itemRender = mc.getRenderItem();
/* 1126 */     boolean isLightingEnabled = GL11.glIsEnabled(2896);
/*      */     
/* 1128 */     boolean rc = false;
/*      */     
/* 1130 */     if ((itm != null) && (itm.getItem() != null)) {
/* 1131 */       rc = true;
/* 1132 */       boolean isRescaleNormalEnabled = GL11.glIsEnabled(32826);
/* 1133 */       GL11.glPushMatrix();
/* 1134 */       GL11.glTranslatef(0.0F, 0.0F, 32.0F);
/* 1135 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1136 */       GL11.glEnable(32826);
/* 1137 */       GL11.glEnable(2896);
/* 1138 */       short short1 = 240;
/* 1139 */       short short2 = 240;
/* 1140 */       RenderHelper.enableGUIStandardItemLighting();
/* 1141 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, short1 / 1.0F, short2 / 1.0F);
/* 1142 */       itemRender.renderItemAndEffectIntoGUI(itm, x, y);
/* 1143 */       itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, itm, x, y, txt);
/* 1144 */       GL11.glPopMatrix();
/* 1145 */       if (isRescaleNormalEnabled) {
/* 1146 */         GL11.glEnable(32826);
/*      */       } else {
/* 1148 */         GL11.glDisable(32826);
/*      */       }
/*      */     }
/*      */     
/* 1152 */     if (isLightingEnabled) {
/* 1153 */       GL11.glEnable(2896);
/*      */     } else {
/* 1155 */       GL11.glDisable(2896);
/*      */     }
/*      */     
/* 1158 */     return rc;
/*      */   }
/*      */   
/*      */   public static boolean renderItemStackShaded(Minecraft mc, ItemStack itm, int x, int y, String txt, float shade) {
/* 1162 */     GL11.glColor4f(shade, shade, shade, shade);
/* 1163 */     RenderItem itemRender = mc.getRenderItem();
/* 1164 */     boolean isLightingEnabled = GL11.glIsEnabled(2896);
/*      */     
/* 1166 */     boolean rc = false;
/*      */     
/* 1168 */     if ((itm != null) && (itm.getItem() != null)) {
/* 1169 */       rc = true;
/* 1170 */       boolean isRescaleNormalEnabled = GL11.glIsEnabled(32826);
/* 1171 */       GL11.glPushMatrix();
/* 1172 */       GL11.glTranslatef(0.0F, 0.0F, 32.0F);
/* 1173 */       GL11.glColor4f(shade, shade, shade, shade);
/* 1174 */       GL11.glEnable(32826);
/* 1175 */       GL11.glEnable(2896);
/* 1176 */       short short1 = 240;
/* 1177 */       short short2 = 240;
/* 1178 */       RenderHelper.enableGUIStandardItemLighting();
/* 1179 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, short1 / 1.0F, short2 / 1.0F);
/* 1180 */       itemRender.renderItemAndEffectIntoGUI(itm, x, y);
/* 1181 */       itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, itm, x, y, txt);
/* 1182 */       GL11.glPopMatrix();
/* 1183 */       if (isRescaleNormalEnabled) {
/* 1184 */         GL11.glEnable(32826);
/*      */       } else {
/* 1186 */         GL11.glDisable(32826);
/*      */       }
/*      */     }
/*      */     
/* 1190 */     if (isLightingEnabled) {
/* 1191 */       GL11.glEnable(2896);
/*      */     } else {
/* 1193 */       GL11.glDisable(2896);
/*      */     }
/* 1195 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1196 */     return rc;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void drawBeam(Vector S, Vector E, Vector P, float width, int bright)
/*      */   {
/* 1222 */     drawBeam(S, E, P, width, bright, 1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public static void drawBeam(Vector S, Vector E, Vector P, float width, int bright, float r, float g, float b, float a)
/*      */   {
/* 1227 */     Vector PS = Sub(S, P);
/* 1228 */     Vector SE = Sub(E, S);
/*      */     
/* 1230 */     Vector normal = Cross(PS, SE);
/* 1231 */     normal = normal.normalize();
/*      */     
/* 1233 */     Vector half = Mul(normal, width);
/* 1234 */     Vector p1 = Add(S, half);
/* 1235 */     Vector p2 = Sub(S, half);
/* 1236 */     Vector p3 = Add(E, half);
/* 1237 */     Vector p4 = Sub(E, half);
/*      */     
/* 1239 */     drawQuad(Tessellator.getInstance(), p1, p3, p4, p2, bright, r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawQuad(Tessellator tessellator, Vector p1, Vector p2, Vector p3, Vector p4, int bright, float r, float g, float b, float a) {
/* 1243 */     int j = bright >> 16 & 0xFFFF;
/* 1244 */     int k = bright & 0xFFFF;
/* 1245 */     tessellator.getWorldRenderer().pos(p1.getX(), p1.getY(), p1.getZ()).tex(0.0D, 0.0D).lightmap(j, k).color(r, g, b, a).endVertex();
/* 1246 */     tessellator.getWorldRenderer().pos(p2.getX(), p2.getY(), p2.getZ()).tex(1.0D, 0.0D).lightmap(j, k).color(r, g, b, a).endVertex();
/* 1247 */     tessellator.getWorldRenderer().pos(p3.getX(), p3.getY(), p3.getZ()).tex(1.0D, 1.0D).lightmap(j, k).color(r, g, b, a).endVertex();
/* 1248 */     tessellator.getWorldRenderer().pos(p4.getX(), p4.getY(), p4.getZ()).tex(0.0D, 1.0D).lightmap(j, k).color(r, g, b, a).endVertex();
/*      */   }
/*      */   
/*      */   public static class Vector {
/*      */     public final float x;
/*      */     public final float y;
/*      */     public final float z;
/*      */     
/*      */     public Vector(float x, float y, float z) {
/* 1257 */       this.x = x;
/* 1258 */       this.y = y;
/* 1259 */       this.z = z;
/*      */     }
/*      */     
/*      */     public float getX() {
/* 1263 */       return this.x;
/*      */     }
/*      */     
/*      */     public float getY() {
/* 1267 */       return this.y;
/*      */     }
/*      */     
/*      */     public float getZ() {
/* 1271 */       return this.z;
/*      */     }
/*      */     
/*      */     public float norm() {
/* 1275 */       return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*      */     }
/*      */     
/*      */     public Vector normalize() {
/* 1279 */       float n = norm();
/* 1280 */       return new Vector(this.x / n, this.y / n, this.z / n);
/*      */     }
/*      */   }
/*      */   
/*      */   private static Vector Cross(Vector a, Vector b) {
/* 1285 */     float x = a.y * b.z - a.z * b.y;
/* 1286 */     float y = a.z * b.x - a.x * b.z;
/* 1287 */     float z = a.x * b.y - a.y * b.x;
/* 1288 */     return new Vector(x, y, z);
/*      */   }
/*      */   
/*      */   public static Vector Sub(Vector a, Vector b) {
/* 1292 */     return new Vector(a.x - b.x, a.y - b.y, a.z - b.z);
/*      */   }
/*      */   
/* 1295 */   private static Vector Add(Vector a, Vector b) { return new Vector(a.x + b.x, a.y + b.y, a.z + b.z); }
/*      */   
/*      */   private static Vector Mul(Vector a, float f) {
/* 1298 */     return new Vector(a.x * f, a.y * f, a.z * f);
/*      */   }
/*      */   
/*      */   public static void renderItemIn2D(String sprite, float thickness)
/*      */   {
/* 1303 */     renderItemIn2D(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(sprite), thickness);
/*      */   }
/*      */   
/*      */   public static void renderItemIn2D(TextureAtlasSprite icon, float thickness)
/*      */   {
/* 1308 */     GL11.glPushMatrix();
/* 1309 */     float f1 = icon.getMaxU();
/* 1310 */     float f2 = icon.getMinV();
/* 1311 */     float f3 = icon.getMinU();
/* 1312 */     float f4 = icon.getMaxV();
/* 1313 */     Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/*      */     
/* 1315 */     Tessellator tessellator = Tessellator.getInstance();
/* 1316 */     tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/* 1317 */     tessellator.getWorldRenderer().pos(0.0D, 0.0D, 0.0D).tex(f1, f4).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 1318 */     tessellator.getWorldRenderer().pos(1.0D, 0.0D, 0.0D).tex(f3, f4).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 1319 */     tessellator.getWorldRenderer().pos(1.0D, 1.0D, 0.0D).tex(f3, f2).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 1320 */     tessellator.getWorldRenderer().pos(0.0D, 1.0D, 0.0D).tex(f1, f2).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 1321 */     tessellator.draw();
/* 1322 */     tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/* 1323 */     tessellator.getWorldRenderer().pos(0.0D, 1.0D, 0.0F - thickness).tex(f1, f2).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 1324 */     tessellator.getWorldRenderer().pos(1.0D, 1.0D, 0.0F - thickness).tex(f3, f2).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 1325 */     tessellator.getWorldRenderer().pos(1.0D, 0.0D, 0.0F - thickness).tex(f3, f4).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 1326 */     tessellator.getWorldRenderer().pos(0.0D, 0.0D, 0.0F - thickness).tex(f1, f4).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 1327 */     tessellator.draw();
/*      */     
/* 1329 */     if (thickness > 0.0F) {
/* 1330 */       float f5 = 0.5F * (f1 - f3) / icon.getIconWidth();
/* 1331 */       float f6 = 0.5F * (f4 - f2) / icon.getIconHeight();
/* 1332 */       tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1337 */       for (int k = 0; k < icon.getIconWidth(); k++)
/*      */       {
/* 1339 */         float f7 = k / icon.getIconWidth();
/* 1340 */         float f8 = f1 + (f3 - f1) * f7 - f5;
/* 1341 */         tessellator.getWorldRenderer().pos(f7, 0.0D, 0.0F - thickness).tex(f8, f4).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 1342 */         tessellator.getWorldRenderer().pos(f7, 0.0D, 0.0D).tex(f8, f4).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 1343 */         tessellator.getWorldRenderer().pos(f7, 1.0D, 0.0D).tex(f8, f2).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 1344 */         tessellator.getWorldRenderer().pos(f7, 1.0D, 0.0F - thickness).tex(f8, f2).normal(-1.0F, 0.0F, 0.0F).endVertex();
/*      */       }
/*      */       
/* 1347 */       tessellator.draw();
/* 1348 */       tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*      */       
/*      */ 
/* 1351 */       for (k = 0; k < icon.getIconWidth(); k++)
/*      */       {
/* 1353 */         float f7 = k / icon.getIconWidth();
/* 1354 */         float f8 = f1 + (f3 - f1) * f7 - f5;
/* 1355 */         float f9 = f7 + 1.0F / icon.getIconWidth();
/* 1356 */         tessellator.getWorldRenderer().pos(f9, 1.0D, 0.0F - thickness).tex(f8, f2).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 1357 */         tessellator.getWorldRenderer().pos(f9, 1.0D, 0.0D).tex(f8, f2).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 1358 */         tessellator.getWorldRenderer().pos(f9, 0.0D, 0.0D).tex(f8, f4).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 1359 */         tessellator.getWorldRenderer().pos(f9, 0.0D, 0.0F - thickness).tex(f8, f4).normal(1.0F, 0.0F, 0.0F).endVertex();
/*      */       }
/*      */       
/* 1362 */       tessellator.draw();
/* 1363 */       tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*      */       
/* 1365 */       for (k = 0; k < icon.getIconHeight(); k++)
/*      */       {
/* 1367 */         float f7 = k / icon.getIconHeight();
/* 1368 */         float f8 = f4 + (f2 - f4) * f7 - f6;
/* 1369 */         float f9 = f7 + 1.0F / icon.getIconHeight();
/* 1370 */         tessellator.getWorldRenderer().pos(0.0D, f9, 0.0D).tex(f1, f8).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1371 */         tessellator.getWorldRenderer().pos(1.0D, f9, 0.0D).tex(f3, f8).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1372 */         tessellator.getWorldRenderer().pos(1.0D, f9, 0.0F - thickness).tex(f3, f8).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1373 */         tessellator.getWorldRenderer().pos(0.0D, f9, 0.0F - thickness).tex(f1, f8).normal(0.0F, 1.0F, 0.0F).endVertex();
/*      */       }
/*      */       
/* 1376 */       tessellator.draw();
/* 1377 */       tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*      */       
/* 1379 */       for (k = 0; k < icon.getIconHeight(); k++)
/*      */       {
/* 1381 */         float f7 = k / icon.getIconHeight();
/* 1382 */         float f8 = f4 + (f2 - f4) * f7 - f6;
/* 1383 */         tessellator.getWorldRenderer().pos(1.0D, f7, 0.0D).tex(f3, f8).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 1384 */         tessellator.getWorldRenderer().pos(0.0D, f7, 0.0D).tex(f1, f8).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 1385 */         tessellator.getWorldRenderer().pos(0.0D, f7, 0.0F - thickness).tex(f1, f8).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 1386 */         tessellator.getWorldRenderer().pos(1.0D, f7, 0.0F - thickness).tex(f3, f8).normal(0.0F, -1.0F, 0.0F).endVertex();
/*      */       }
/*      */       
/* 1389 */       tessellator.draw();
/*      */     }
/* 1391 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/* 1394 */   public static float sysPartialTicks = 0.0F;
/*      */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\lib\UtilsFX.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */