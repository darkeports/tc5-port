/*    */ package thaumcraft.client.gui;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.common.entities.monster.EntityPech;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiPech extends GuiContainer
/*    */ {
/*    */   EntityPech pech;
/*    */   
/*    */   public GuiPech(InventoryPlayer par1InventoryPlayer, World world, EntityPech pech)
/*    */   {
/* 25 */     super(new thaumcraft.common.container.ContainerPech(par1InventoryPlayer, world, pech));
/* 26 */     this.xSize = 175;
/* 27 */     this.ySize = 232;
/* 28 */     this.pech = pech;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 37 */   ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_pech.png");
/*    */   
/*    */ 
/*    */   protected void drawGuiContainerForegroundLayer(int par1, int par2) {}
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/*    */   {
/* 44 */     this.mc.renderEngine.bindTexture(this.tex);
/* 45 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 46 */     int var5 = (this.width - this.xSize) / 2;
/* 47 */     int var6 = (this.height - this.ySize) / 2;
/* 48 */     GL11.glEnable(3042);
/* 49 */     drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
/*    */     
/*    */ 
/* 52 */     if ((this.pech.isValued(this.inventorySlots.getSlot(0).getStack())) && (this.inventorySlots.getSlot(0).getStack() != null) && (this.inventorySlots.getSlot(1).getStack() == null) && (this.inventorySlots.getSlot(2).getStack() == null) && (this.inventorySlots.getSlot(3).getStack() == null) && (this.inventorySlots.getSlot(4).getStack() == null))
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 59 */       drawTexturedModalRect(var5 + 67, var6 + 24, 176, 0, 25, 25);
/*    */     }
/*    */     
/* 62 */     GL11.glDisable(3042);
/*    */   }
/*    */   
/*    */   protected void mouseClicked(int mx, int my, int par3) throws java.io.IOException
/*    */   {
/* 67 */     super.mouseClicked(mx, my, par3);
/*    */     
/* 69 */     int gx = (this.width - this.xSize) / 2;
/* 70 */     int gy = (this.height - this.ySize) / 2;
/*    */     
/*    */ 
/* 73 */     int var7 = mx - (gx + 67);
/* 74 */     int var8 = my - (gy + 24);
/*    */     
/* 76 */     if ((var7 >= 0) && (var8 >= 0) && (var7 < 25) && (var8 < 25) && (this.pech.isValued(this.inventorySlots.getSlot(0).getStack())) && (this.inventorySlots.getSlot(0).getStack() != null) && (this.inventorySlots.getSlot(1).getStack() == null) && (this.inventorySlots.getSlot(2).getStack() == null) && (this.inventorySlots.getSlot(3).getStack() == null) && (this.inventorySlots.getSlot(4).getStack() == null))
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 85 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 0);
/*    */       
/* 87 */       playButton();
/* 88 */       return;
/*    */     }
/*    */   }
/*    */   
/*    */   private void playButton() {
/* 93 */     this.mc.getRenderViewEntity().worldObj.playSound(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ, "thaumcraft:pech_dice", 0.5F, 0.95F + this.mc.getRenderViewEntity().worldObj.rand.nextFloat() * 0.1F, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiPech.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */