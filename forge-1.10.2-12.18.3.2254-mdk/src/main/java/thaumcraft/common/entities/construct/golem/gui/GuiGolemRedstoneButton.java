/*    */ package thaumcraft.common.entities.construct.golem.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import thaumcraft.api.golems.seals.ISealEntity;
/*    */ 
/*    */ public class GuiGolemRedstoneButton extends GuiButton
/*    */ {
/*    */   ISealEntity seal;
/*    */   
/*    */   public GuiGolemRedstoneButton(int buttonId, int x, int y, int width, int height, ISealEntity seal)
/*    */   {
/* 18 */     super(buttonId, x, y, width, height, "");
/* 19 */     this.seal = seal;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 24 */   static ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
/*    */   
/*    */   public void drawButton(Minecraft mc, int xx, int yy)
/*    */   {
/* 28 */     if (this.visible)
/*    */     {
/* 30 */       FontRenderer fontrenderer = mc.fontRendererObj;
/* 31 */       mc.getTextureManager().bindTexture(tex);
/* 32 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 33 */       this.hovered = ((xx >= this.xPosition) && (yy >= this.yPosition) && (xx < this.xPosition + this.width) && (yy < this.yPosition + this.height));
/*    */       
/* 35 */       int k = getHoverState(this.hovered);
/* 36 */       if (k == 2) {
/* 37 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */       } else {
/* 39 */         GlStateManager.color(0.9F, 0.9F, 0.9F, 0.9F);
/*    */       }
/* 41 */       GlStateManager.enableBlend();
/* 42 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 43 */       GlStateManager.blendFunc(770, 771);
/*    */       
/* 45 */       if (this.seal.isRedstoneSensitive()) {
/* 46 */         drawTexturedModalRect(this.xPosition, this.yPosition, 64, 136, 16, 16);
/*    */       } else {
/* 48 */         drawTexturedModalRect(this.xPosition, this.yPosition, 80, 136, 16, 16);
/*    */       }
/*    */       
/* 51 */       if (k == 2) {
/* 52 */         this.zLevel += 90.0F;
/* 53 */         String s = this.seal.isRedstoneSensitive() ? StatCollector.translateToLocal("golem.prop.redon") : StatCollector.translateToLocal("golem.prop.redoff");
/* 54 */         drawString(fontrenderer, s, this.xPosition - 2 - fontrenderer.getStringWidth(s), this.yPosition + 4, 16777215);
/* 55 */         this.zLevel -= 90.0F;
/*    */       }
/* 57 */       mouseDragged(mc, xx, yy);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\gui\GuiGolemRedstoneButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */