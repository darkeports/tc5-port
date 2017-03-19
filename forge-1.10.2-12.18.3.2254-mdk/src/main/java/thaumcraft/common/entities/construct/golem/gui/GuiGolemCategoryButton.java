/*    */ package thaumcraft.common.entities.construct.golem.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ class GuiGolemCategoryButton extends net.minecraft.client.gui.GuiButton
/*    */ {
/*    */   int icon;
/*    */   boolean active;
/*    */   
/*    */   public GuiGolemCategoryButton(int buttonId, int x, int y, int width, int height, String buttonText, int i, boolean act)
/*    */   {
/* 15 */     super(buttonId, x, y, width, height, buttonText);
/* 16 */     this.icon = i;
/* 17 */     this.active = act;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 23 */   static ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
/*    */   
/*    */ 
/*    */   public void drawButton(Minecraft mc, int xx, int yy)
/*    */   {
/* 28 */     if (this.visible)
/*    */     {
/* 30 */       FontRenderer fontrenderer = mc.fontRendererObj;
/* 31 */       mc.getTextureManager().bindTexture(tex);
/* 32 */       GlStateManager.color(0.9F, 0.9F, 0.9F, 0.9F);
/* 33 */       this.hovered = ((xx >= this.xPosition - 8) && (yy >= this.yPosition - 8) && (xx < this.xPosition - 8 + this.width) && (yy < this.yPosition - 8 + this.height));
/* 34 */       int k = getHoverState(this.hovered);
/*    */       
/* 36 */       GlStateManager.enableBlend();
/* 37 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 38 */       GlStateManager.blendFunc(770, 771);
/*    */       
/* 40 */       if (this.active) {
/* 41 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */       }
/* 43 */       else if (k != 2) {
/* 44 */         GlStateManager.color(0.7F, 0.7F, 0.7F, 0.7F);
/*    */       }
/*    */       
/* 47 */       drawTexturedModalRect(this.xPosition - 8, this.yPosition - 8, this.icon * 16, 120, 16, 16);
/*    */       
/* 49 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */       
/* 51 */       if (k == 2) {
/* 52 */         this.zLevel += 90.0F;
/* 53 */         String s = net.minecraft.util.StatCollector.translateToLocal(this.displayString);
/* 54 */         drawString(fontrenderer, s, this.xPosition - 10 - fontrenderer.getStringWidth(s), this.yPosition - 4, 16777215);
/* 55 */         this.zLevel -= 90.0F;
/*    */       }
/*    */       
/* 58 */       mouseDragged(mc, xx, yy);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
/*    */   {
/* 66 */     return (this.enabled) && (this.visible) && (mouseX >= this.xPosition - 8) && (mouseY >= this.yPosition - 8) && (mouseX < this.xPosition - 8 + this.width) && (mouseY < this.yPosition - 8 + this.height);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\gui\GuiGolemCategoryButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */