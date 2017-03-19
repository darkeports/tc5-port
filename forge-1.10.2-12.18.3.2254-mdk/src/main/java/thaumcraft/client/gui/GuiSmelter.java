/*    */ package thaumcraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.common.container.ContainerSmelter;
/*    */ import thaumcraft.common.tiles.crafting.TileSmelter;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiSmelter
/*    */   extends GuiContainer
/*    */ {
/*    */   private TileSmelter furnaceInventory;
/*    */   
/*    */   public GuiSmelter(InventoryPlayer par1InventoryPlayer, TileSmelter par2TileEntityFurnace)
/*    */   {
/* 22 */     super(new ContainerSmelter(par1InventoryPlayer, par2TileEntityFurnace));
/* 23 */     this.furnaceInventory = par2TileEntityFurnace;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 33 */   ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_smelter.png");
/*    */   
/*    */ 
/*    */   protected void drawGuiContainerForegroundLayer(int par1, int par2) {}
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/*    */   {
/* 40 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 41 */     this.mc.renderEngine.bindTexture(this.tex);
/* 42 */     int k = (this.width - this.xSize) / 2;
/* 43 */     int l = (this.height - this.ySize) / 2;
/*    */     
/* 45 */     GL11.glEnable(3042);
/* 46 */     drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
/*    */     
/*    */ 
/* 49 */     if (this.furnaceInventory.getBurnTimeRemainingScaled(20) > 0)
/*    */     {
/* 51 */       int i1 = this.furnaceInventory.getBurnTimeRemainingScaled(20);
/* 52 */       drawTexturedModalRect(k + 80, l + 26 + 20 - i1, 176, 20 - i1, 16, i1);
/*    */     }
/*    */     
/* 55 */     int i1 = this.furnaceInventory.getCookProgressScaled(46);
/* 56 */     drawTexturedModalRect(k + 106, l + 13 + 46 - i1, 216, 46 - i1, 9, i1);
/*    */     
/* 58 */     i1 = this.furnaceInventory.getVisScaled(48);
/* 59 */     drawTexturedModalRect(k + 61, l + 12 + 48 - i1, 200, 48 - i1, 8, i1);
/*    */     
/*    */ 
/*    */ 
/* 63 */     drawTexturedModalRect(k + 60, l + 8, 232, 0, 10, 55);
/* 64 */     GL11.glDisable(3042);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiSmelter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */