/*    */ package thaumcraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.client.gui.plugins.GuiToggleButton;
/*    */ import thaumcraft.common.container.ContainerTurretAdvanced;
/*    */ import thaumcraft.common.entities.construct.EntityTurretCrossbowAdvanced;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiTurretAdvanced extends GuiContainer
/*    */ {
/*    */   EntityTurretCrossbowAdvanced turret;
/*    */   
/*    */   public GuiTurretAdvanced(InventoryPlayer par1InventoryPlayer, World world, EntityTurretCrossbowAdvanced t)
/*    */   {
/* 27 */     super(new ContainerTurretAdvanced(par1InventoryPlayer, world, t));
/* 28 */     this.xSize = 175;
/* 29 */     this.ySize = 232;
/* 30 */     this.turret = t;
/*    */   }
/*    */   
/*    */   public void initGui()
/*    */   {
/* 35 */     super.initGui();
/*    */     
/* 37 */     this.buttonList.add(new GuiToggleButton(1, this.guiLeft + 90, this.guiTop + 13, 8, 8, "button.turretfocus.1", new Runnable() {
/* 38 */       public void run() { GuiToggleButton.toggled = GuiTurretAdvanced.this.turret.getTargetAnimal(); } }));
/* 39 */     this.buttonList.add(new GuiToggleButton(2, this.guiLeft + 90, this.guiTop + 27, 8, 8, "button.turretfocus.2", new Runnable() {
/* 40 */       public void run() { GuiToggleButton.toggled = GuiTurretAdvanced.this.turret.getTargetMob(); } }));
/* 41 */     this.buttonList.add(new GuiToggleButton(3, this.guiLeft + 90, this.guiTop + 41, 8, 8, "button.turretfocus.3", new Runnable() {
/* 42 */       public void run() { GuiToggleButton.toggled = GuiTurretAdvanced.this.turret.getTargetPlayer(); } }));
/* 43 */     this.buttonList.add(new GuiToggleButton(4, this.guiLeft + 90, this.guiTop + 55, 8, 8, "button.turretfocus.4", new Runnable() {
/* 44 */       public void run() { GuiToggleButton.toggled = GuiTurretAdvanced.this.turret.getTargetFriendly(); }
/*    */     }));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 51 */   ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_turret_advanced.png");
/*    */   
/*    */ 
/*    */   protected void drawGuiContainerForegroundLayer(int par1, int par2) {}
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/*    */   {
/* 58 */     this.mc.renderEngine.bindTexture(this.tex);
/* 59 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 60 */     int k = (this.width - this.xSize) / 2;
/* 61 */     int l = (this.height - this.ySize) / 2;
/* 62 */     GL11.glEnable(3042);
/* 63 */     drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
/*    */     
/* 65 */     int h = (int)(39.0F * (this.turret.getHealth() / this.turret.getMaxHealth()));
/* 66 */     drawTexturedModalRect(k + 30, l + 59, 192, 48, h, 6);
/*    */     
/* 68 */     GL11.glDisable(3042);
/*    */   }
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException
/*    */   {
/* 73 */     if (button.id == 1)
/*    */     {
/* 75 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 1);
/*    */ 
/*    */     }
/* 78 */     else if (button.id == 2)
/*    */     {
/* 80 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 2);
/*    */ 
/*    */     }
/* 83 */     else if (button.id == 3)
/*    */     {
/* 85 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 3);
/*    */ 
/*    */     }
/* 88 */     else if (button.id == 4)
/*    */     {
/* 90 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 4);
/*    */     }
/*    */     else
/*    */     {
/* 94 */       super.actionPerformed(button);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiTurretAdvanced.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */