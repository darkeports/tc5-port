/*     */ package thaumcraft.client.gui.plugins;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ 
/*     */ public class GuiHoverButton extends GuiButton
/*     */ {
/*     */   String description;
/*     */   GuiScreen screen;
/*     */   int color;
/*     */   
/*     */   public GuiHoverButton(GuiScreen screen, int buttonId, int x, int y, int width, int height, String buttonText, String description, Object tex)
/*     */   {
/*  24 */     super(buttonId, x, y, width, height, buttonText);
/*  25 */     this.description = description;
/*  26 */     this.tex = tex;
/*  27 */     this.screen = screen;
/*  28 */     this.color = 16777215;
/*     */   }
/*     */   
/*     */   public GuiHoverButton(GuiScreen screen, int buttonId, int x, int y, int width, int height, String buttonText, String description, Object tex, int color) {
/*  32 */     super(buttonId, x, y, width, height, buttonText);
/*  33 */     this.description = description;
/*  34 */     this.tex = tex;
/*  35 */     this.screen = screen;
/*  36 */     this.color = color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  42 */   Object tex = null;
/*     */   
/*     */ 
/*     */   public void drawButton(Minecraft mc, int xx, int yy)
/*     */   {
/*  47 */     if (this.visible)
/*     */     {
/*  49 */       FontRenderer fontrenderer = mc.fontRendererObj;
/*  50 */       Color c = new Color(this.color);
/*  51 */       GlStateManager.color(0.9F * (c.getRed() / 255.0F), 0.9F * (c.getGreen() / 255.0F), 0.9F * (c.getBlue() / 255.0F), 0.9F);
/*  52 */       this.hovered = ((xx >= this.xPosition - this.width / 2) && (yy >= this.yPosition - this.height / 2) && (xx < this.xPosition - this.width / 2 + this.width) && (yy < this.yPosition - this.height / 2 + this.height));
/*  53 */       int k = getHoverState(this.hovered);
/*     */       
/*  55 */       GlStateManager.enableBlend();
/*  56 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  57 */       GlStateManager.blendFunc(770, 771);
/*     */       
/*  59 */       if (k == 2) {
/*  60 */         GlStateManager.color(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, 1.0F);
/*     */       }
/*     */       
/*  63 */       if ((this.tex instanceof Aspect)) {
/*  64 */         mc.getTextureManager().bindTexture(((Aspect)this.tex).getImage());
/*  65 */         Color c2 = new Color(((Aspect)this.tex).getColor());
/*  66 */         if (k != 2) {
/*  67 */           GlStateManager.color(c2.getRed() / 290.0F, c2.getGreen() / 290.0F, c2.getBlue() / 290.0F, 0.9F);
/*     */         } else
/*  69 */           GlStateManager.color(c2.getRed() / 255.0F, c2.getGreen() / 255.0F, c2.getBlue() / 255.0F, 1.0F);
/*  70 */         drawModalRectWithCustomSizedTexture(this.xPosition - this.width / 2, this.yPosition - this.height / 2, 0.0F, 0.0F, 16, 16, 16.0F, 16.0F);
/*     */       }
/*     */       
/*  73 */       if ((this.tex instanceof ResourceLocation)) {
/*  74 */         mc.getTextureManager().bindTexture((ResourceLocation)this.tex);
/*  75 */         drawModalRectWithCustomSizedTexture(this.xPosition - this.width / 2, this.yPosition - this.height / 2, 0.0F, 0.0F, 16, 16, 16.0F, 16.0F);
/*     */       }
/*     */       
/*  78 */       if ((this.tex instanceof TextureAtlasSprite)) {
/*  79 */         drawTexturedModalRect(this.xPosition - this.width / 2, this.yPosition - this.height / 2, (TextureAtlasSprite)this.tex, 16, 16);
/*     */       }
/*     */       
/*  82 */       if ((this.tex instanceof ItemStack)) {
/*  83 */         this.zLevel -= 90.0F;
/*  84 */         UtilsFX.renderItemStackShaded(mc, (ItemStack)this.tex, this.xPosition - this.width / 2, this.yPosition - this.height / 2, null, k == 2 ? 1.0F : 0.9F);
/*  85 */         this.zLevel += 90.0F;
/*     */       }
/*     */       
/*  88 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  89 */       mouseDragged(mc, xx, yy);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawButtonForegroundLayer(int xx, int yy)
/*     */   {
/*  97 */     FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
/*  98 */     this.zLevel += 90.0F;
/*  99 */     ArrayList<String> text = new ArrayList();
/* 100 */     text.add(this.displayString);
/* 101 */     int m = 8;
/* 102 */     if (this.description != null) {
/* 103 */       m = 0;
/* 104 */       text.add("§o§9" + this.description);
/*     */     }
/* 106 */     UtilsFX.drawCustomTooltip(this.screen, fontrenderer, text, xx + 4, yy + m, -99);
/* 107 */     this.zLevel -= 90.0F;
/* 108 */     net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
/* 109 */     GlStateManager.disableLighting();
/* 110 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
/*     */   {
/* 118 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\plugins\GuiHoverButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */