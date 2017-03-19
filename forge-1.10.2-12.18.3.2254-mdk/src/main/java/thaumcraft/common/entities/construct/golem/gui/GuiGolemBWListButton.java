/*    */ package thaumcraft.common.entities.construct.golem.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import thaumcraft.api.golems.seals.ISealConfigFilter;
/*    */ 
/*    */ public class GuiGolemBWListButton extends GuiButton
/*    */ {
/*    */   ISealConfigFilter filter;
/*    */   
/*    */   public GuiGolemBWListButton(int buttonId, int x, int y, int width, int height, ISealConfigFilter filter)
/*    */   {
/* 17 */     super(buttonId, x, y, width, height, "");
/* 18 */     this.filter = filter;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 23 */   static ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
/*    */   
/*    */   public void drawButton(Minecraft mc, int xx, int yy)
/*    */   {
/* 27 */     if (this.visible)
/*    */     {
/* 29 */       FontRenderer fontrenderer = mc.fontRendererObj;
/* 30 */       mc.getTextureManager().bindTexture(tex);
/* 31 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 32 */       this.hovered = ((xx >= this.xPosition) && (yy >= this.yPosition) && (xx < this.xPosition + this.width) && (yy < this.yPosition + this.height));
/*    */       
/* 34 */       int k = getHoverState(this.hovered);
/* 35 */       if (k == 2) {
/* 36 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */       } else {
/* 38 */         GlStateManager.color(0.9F, 0.9F, 0.9F, 0.9F);
/*    */       }
/* 40 */       GlStateManager.enableBlend();
/* 41 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 42 */       GlStateManager.blendFunc(770, 771);
/*    */       
/* 44 */       if (this.filter.isBlacklist()) {
/* 45 */         drawTexturedModalRect(this.xPosition, this.yPosition, 0, 136, 16, 16);
/*    */       } else {
/* 47 */         drawTexturedModalRect(this.xPosition, this.yPosition, 16, 136, 16, 16);
/*    */       }
/*    */       
/*    */ 
/* 51 */       if (k == 2) {
/* 52 */         drawCenteredString(fontrenderer, StatCollector.translateToLocal(this.filter.isBlacklist() ? "button.bl" : "button.wl"), this.xPosition + 8, this.yPosition + 17, 16777215);
/*    */       }
/*    */       
/* 55 */       mouseDragged(mc, xx, yy);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\gui\GuiGolemBWListButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */