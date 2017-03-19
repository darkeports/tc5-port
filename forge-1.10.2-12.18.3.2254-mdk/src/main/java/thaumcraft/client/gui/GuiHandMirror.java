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
/*    */ import thaumcraft.common.container.ContainerHandMirror;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiHandMirror
/*    */   extends GuiContainer
/*    */ {
/* 18 */   int ci = 0;
/*    */   
/*    */   public GuiHandMirror(InventoryPlayer par1InventoryPlayer, World world, int x, int y, int z)
/*    */   {
/* 22 */     super(new ContainerHandMirror(par1InventoryPlayer, world, x, y, z));
/* 23 */     this.ci = par1InventoryPlayer.currentItem;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void drawGuiContainerForegroundLayer() {}
/*    */   
/*    */ 
/*    */ 
/*    */   protected boolean checkHotbarKeys(int par1)
/*    */   {
/* 35 */     return false;
/*    */   }
/*    */   
/* 38 */   ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_handmirror.png");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/*    */   {
/* 45 */     this.mc.renderEngine.bindTexture(this.tex);
/* 46 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 47 */     int var5 = (this.width - this.xSize) / 2;
/* 48 */     int var6 = (this.height - this.ySize) / 2;
/* 49 */     drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
/* 50 */     GL11.glEnable(3042);
/* 51 */     GL11.glBlendFunc(770, 771);
/* 52 */     drawTexturedModalRect(var5 + 8 + this.ci * 18, var6 + 142, 240, 0, 16, 16);
/* 53 */     GL11.glDisable(3042);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiHandMirror.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */