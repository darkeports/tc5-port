/*    */ package thaumcraft.client.gui.plugins;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import thaumcraft.client.gui.GuiTurretFocus;
/*    */ 
/*    */ public class GuiToggleButton extends GuiButton
/*    */ {
/*    */   Runnable runnable;
/*    */   public static boolean toggled;
/*    */   
/*    */   public GuiToggleButton(int buttonId, int x, int y, int width, int height, String buttonText, Runnable runnable)
/*    */   {
/* 16 */     super(buttonId, x, y, width, height, buttonText);
/* 17 */     this.runnable = runnable;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void drawButton(Minecraft mc, int xx, int yy)
/*    */   {
/* 25 */     if (this.visible)
/*    */     {
/* 27 */       this.runnable.run();
/*    */       
/* 29 */       FontRenderer fontrenderer = mc.fontRendererObj;
/* 30 */       mc.getTextureManager().bindTexture(GuiTurretFocus.tex);
/* 31 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 32 */       this.hovered = ((xx >= this.xPosition) && (yy >= this.yPosition) && (xx < this.xPosition + this.width) && (yy < this.yPosition + this.height));
/*    */       
/*    */ 
/* 35 */       GlStateManager.enableBlend();
/* 36 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 37 */       GlStateManager.blendFunc(770, 771);
/*    */       
/* 39 */       drawTexturedModalRect(this.xPosition, this.yPosition, 192, 16, 8, 8);
/*    */       
/* 41 */       if (toggled) {
/* 42 */         drawTexturedModalRect(this.xPosition, this.yPosition, 192, 24, 8, 8);
/*    */       }
/*    */       
/* 45 */       drawString(fontrenderer, net.minecraft.util.StatCollector.translateToLocal(this.displayString), this.xPosition + 12, this.yPosition, 16777215);
/*    */       
/* 47 */       mouseDragged(mc, xx, yy);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\plugins\GuiToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */