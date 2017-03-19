/*    */ package thaumcraft.client.gui.plugins;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiScrollButton extends GuiButton
/*    */ {
/*    */   public GuiScrollButton(int buttonId, int x, int y, int width, int height, boolean minus)
/*    */   {
/* 14 */     super(buttonId, x, y, width, height, "");
/* 15 */     this.minus = minus;
/*    */   }
/*    */   
/* 18 */   boolean minus = false;
/*    */   
/* 20 */   static ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
/*    */   
/*    */ 
/*    */   public void drawButton(Minecraft mc, int xx, int yy)
/*    */   {
/* 25 */     if (this.visible)
/*    */     {
/* 27 */       FontRenderer fontrenderer = mc.fontRendererObj;
/* 28 */       mc.getTextureManager().bindTexture(tex);
/* 29 */       GlStateManager.color(0.9F, 0.9F, 0.9F, 0.9F);
/*    */       
/* 31 */       this.hovered = ((xx >= this.xPosition) && (yy >= this.yPosition) && (xx < this.xPosition + this.width) && (yy < this.yPosition + this.height));
/* 32 */       int k = getHoverState(this.hovered);
/* 33 */       if (k == 2) GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 34 */       GlStateManager.enableBlend();
/* 35 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 36 */       GlStateManager.blendFunc(770, 771);
/*    */       
/* 38 */       drawTexturedModalRect(this.xPosition, this.yPosition, this.minus ? 20 : 30, 0, 10, 10);
/*    */       
/* 40 */       mouseDragged(mc, xx, yy);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\plugins\GuiScrollButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */