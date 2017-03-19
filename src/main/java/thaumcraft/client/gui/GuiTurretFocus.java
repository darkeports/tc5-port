/*     */ package thaumcraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.client.gui.plugins.GuiToggleButton;
/*     */ import thaumcraft.common.container.ContainerTurretFocus;
/*     */ import thaumcraft.common.entities.construct.EntityTurretFocus;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiTurretFocus extends GuiContainer
/*     */ {
/*     */   EntityTurretFocus turret;
/*     */   
/*     */   public GuiTurretFocus(InventoryPlayer par1InventoryPlayer, World world, EntityTurretFocus t)
/*     */   {
/*  29 */     super(new ContainerTurretFocus(par1InventoryPlayer, world, t));
/*  30 */     this.xSize = 175;
/*  31 */     this.ySize = 232;
/*  32 */     this.turret = t;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void initGui()
/*     */   {
/*  39 */     super.initGui();
/*     */     
/*  41 */     this.buttonList.add(new GuiToggleButton(1, this.guiLeft + 90, this.guiTop + 13, 8, 8, "button.turretfocus.1", new Runnable() {
/*  42 */       public void run() { GuiToggleButton.toggled = GuiTurretFocus.this.turret.getTargetAnimal(); } }));
/*  43 */     this.buttonList.add(new GuiToggleButton(2, this.guiLeft + 90, this.guiTop + 27, 8, 8, "button.turretfocus.2", new Runnable() {
/*  44 */       public void run() { GuiToggleButton.toggled = GuiTurretFocus.this.turret.getTargetMob(); } }));
/*  45 */     this.buttonList.add(new GuiToggleButton(3, this.guiLeft + 90, this.guiTop + 41, 8, 8, "button.turretfocus.3", new Runnable() {
/*  46 */       public void run() { GuiToggleButton.toggled = GuiTurretFocus.this.turret.getTargetPlayer(); } }));
/*  47 */     this.buttonList.add(new GuiToggleButton(4, this.guiLeft + 90, this.guiTop + 55, 8, 8, "button.turretfocus.4", new Runnable() {
/*  48 */       public void run() { GuiToggleButton.toggled = GuiTurretFocus.this.turret.getTargetFriendly(); }
/*     */     }));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  56 */   public static ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_turret_focus.png");
/*     */   
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int par1, int par2) {}
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/*     */   {
/*  63 */     this.mc.renderEngine.bindTexture(tex);
/*  64 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  65 */     int k = (this.width - this.xSize) / 2;
/*  66 */     int l = (this.height - this.ySize) / 2;
/*  67 */     GL11.glEnable(3042);
/*  68 */     drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
/*  69 */     int h = (int)(39.0F * (this.turret.getHealth() / this.turret.getMaxHealth()));
/*  70 */     drawTexturedModalRect(k + 24, l + 59, 192, 48, h, 6);
/*     */     
/*  72 */     if ((this.turret.getHeldItem() != null) && ((this.turret.getHeldItem().getItem() instanceof ItemFocusBasic))) {
/*  73 */       GL11.glPushMatrix();
/*  74 */       GL11.glScaled(0.5D, 0.5D, 0.5D);
/*  75 */       String s = net.minecraft.util.StatCollector.translateToLocal("turretfocus.range");
/*  76 */       s = s.replaceFirst("%t", "" + (int)((ItemFocusBasic)this.turret.getHeldItem().getItem()).getTurretRange(this.turret.getHeldItem()));
/*  77 */       drawCenteredString(this.fontRendererObj, s, (k + 44) * 2, (l + 9) * 2, 16777215);
/*  78 */       GL11.glPopMatrix();
/*     */     }
/*     */     
/*  81 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void actionPerformed(GuiButton button)
/*     */     throws IOException
/*     */   {
/*  89 */     if (button.id == 1)
/*     */     {
/*  91 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 1);
/*     */ 
/*     */     }
/*  94 */     else if (button.id == 2)
/*     */     {
/*  96 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 2);
/*     */ 
/*     */     }
/*  99 */     else if (button.id == 3)
/*     */     {
/* 101 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 3);
/*     */ 
/*     */     }
/* 104 */     else if (button.id == 4)
/*     */     {
/* 106 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 4);
/*     */     }
/*     */     else
/*     */     {
/* 110 */       super.actionPerformed(button);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiTurretFocus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */