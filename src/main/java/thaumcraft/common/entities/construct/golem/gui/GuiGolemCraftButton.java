/*    */ package thaumcraft.common.entities.construct.golem.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiGolemCraftButton
/*    */   extends GuiButton
/*    */ {
/*    */   public GuiGolemCraftButton(int buttonId, int x, int y)
/*    */   {
/* 15 */     super(buttonId, x, y, 24, 16, "");
/*    */   }
/*    */   
/* 18 */   static ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_golembuilder.png");
/*    */   
/*    */ 
/*    */   public void drawButton(Minecraft mc, int xx, int yy)
/*    */   {
/* 23 */     if (this.visible)
/*    */     {
/* 25 */       FontRenderer fontrenderer = mc.fontRendererObj;
/* 26 */       mc.getTextureManager().bindTexture(tex);
/* 27 */       GlStateManager.color(0.9F, 0.9F, 0.9F, 0.9F);
/* 28 */       this.hovered = ((xx >= this.xPosition) && (yy >= this.yPosition) && (xx < this.xPosition + this.width) && (yy < this.yPosition + this.height));
/* 29 */       int k = getHoverState(this.hovered);
/*    */       
/* 31 */       GlStateManager.enableBlend();
/* 32 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 33 */       GlStateManager.blendFunc(770, 771);
/*    */       
/* 35 */       if ((this.enabled) && (k == 2)) {
/* 36 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */       }
/*    */       
/* 39 */       drawTexturedModalRect(this.xPosition, this.yPosition, 216, 64, 24, 16);
/*    */       
/* 41 */       if (!this.enabled) {
/* 42 */         drawTexturedModalRect(this.xPosition, this.yPosition, 216, 40, 24, 16);
/*    */       }
/*    */       
/* 45 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */       
/* 47 */       mouseDragged(mc, xx, yy);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\gui\GuiGolemCraftButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */