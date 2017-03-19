/*    */ package thaumcraft.common.entities.construct.golem.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import thaumcraft.api.golems.seals.ISealConfigToggles.SealToggle;
/*    */ 
/*    */ public class GuiGolemPropButton extends GuiButton
/*    */ {
/*    */   ISealConfigToggles.SealToggle prop;
/*    */   
/*    */   public GuiGolemPropButton(int buttonId, int x, int y, int width, int height, String buttonText, ISealConfigToggles.SealToggle prop)
/*    */   {
/* 17 */     super(buttonId, x, y, width, height, buttonText);
/* 18 */     this.prop = prop;
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
/* 34 */       GlStateManager.enableBlend();
/* 35 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 36 */       GlStateManager.blendFunc(770, 771);
/*    */       
/* 38 */       drawTexturedModalRect(this.xPosition - 2, this.yPosition - 2, 2, 18, 12, 12);
/*    */       
/* 40 */       if (this.prop.getValue()) {
/* 41 */         drawTexturedModalRect(this.xPosition - 2, this.yPosition - 2, 18, 18, 12, 12);
/*    */       }
/*    */       
/* 44 */       drawString(fontrenderer, StatCollector.translateToLocal(this.displayString), this.xPosition + 12, this.yPosition, 16777215);
/*    */       
/* 46 */       mouseDragged(mc, xx, yy);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\gui\GuiGolemPropButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */