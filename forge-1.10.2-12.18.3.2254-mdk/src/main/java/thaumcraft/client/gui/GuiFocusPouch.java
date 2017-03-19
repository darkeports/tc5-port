/*    */ package thaumcraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiFocusPouch extends GuiContainer
/*    */ {
/*    */   private int blockSlot;
/*    */   
/*    */   public GuiFocusPouch(InventoryPlayer par1InventoryPlayer, World world, int x, int y, int z)
/*    */   {
/* 20 */     super(new thaumcraft.common.container.ContainerFocusPouch(par1InventoryPlayer, world, x, y, z));
/* 21 */     this.blockSlot = par1InventoryPlayer.currentItem;
/* 22 */     this.xSize = 175;
/* 23 */     this.ySize = 232;
/*    */   }
/*    */   
/* 26 */   ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_focuspouch.png");
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int par1, int par2)
/*    */   {
/* 30 */     this.mc.renderEngine.bindTexture(this.tex);
/* 31 */     float t = this.zLevel;
/* 32 */     this.zLevel = 200.0F;
/* 33 */     GL11.glEnable(3042);
/* 34 */     drawTexturedModalRect(8 + this.blockSlot * 18, 209, 240, 0, 16, 16);
/* 35 */     GL11.glDisable(3042);
/* 36 */     this.zLevel = t;
/*    */   }
/*    */   
/*    */   protected boolean checkHotbarKeys(int par1)
/*    */   {
/* 41 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/*    */   {
/* 50 */     if (this.mc.thePlayer.inventory.mainInventory[this.blockSlot] == null) this.mc.thePlayer.closeScreen();
/* 51 */     this.mc.renderEngine.bindTexture(this.tex);
/* 52 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 53 */     int var5 = (this.width - this.xSize) / 2;
/* 54 */     int var6 = (this.height - this.ySize) / 2;
/* 55 */     GL11.glEnable(3042);
/* 56 */     drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
/* 57 */     GL11.glDisable(3042);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiFocusPouch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */