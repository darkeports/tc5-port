/*    */ package thaumcraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.api.wands.FocusUpgradeType;
/*    */ import thaumcraft.api.wands.ItemFocusBasic;
/*    */ import thaumcraft.common.container.ContainerArcaneBore;
/*    */ import thaumcraft.common.items.wands.foci.ItemFocusExcavation;
/*    */ import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
/*    */ import thaumcraft.common.tiles.devices.TileArcaneBore;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiArcaneBore extends GuiContainer
/*    */ {
/*    */   private TileArcaneBore bore;
/*    */   
/*    */   public GuiArcaneBore(InventoryPlayer par1InventoryPlayer, TileArcaneBore e)
/*    */   {
/* 28 */     super(new ContainerArcaneBore(par1InventoryPlayer, e));
/* 29 */     this.bore = e;
/* 30 */     this.xSize = 176;
/* 31 */     this.ySize = 141;
/*    */   }
/*    */   
/*    */ 
/* 35 */   ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_arcanebore.png");
/*    */   
/*    */ 
/*    */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/*    */   {
/* 40 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 41 */     this.mc.renderEngine.bindTexture(this.tex);
/* 42 */     int var5 = (this.width - this.xSize) / 2;
/* 43 */     int var6 = (this.height - this.ySize) / 2;
/* 44 */     drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
/*    */     
/* 46 */     if ((this.bore.getStackInSlot(1) != null) && (this.bore.getStackInSlot(1).getItemDamage() + 1 >= this.bore.getStackInSlot(1).getMaxDamage())) {
/* 47 */       drawTexturedModalRect(var5 + 74, var6 + 18, 184, 0, 16, 16);
/*    */     }
/*    */     
/* 50 */     GL11.glPushMatrix();
/* 51 */     GL11.glTranslatef(var5 + 112, var6 + 8, 505.0F);
/* 52 */     GL11.glScalef(0.5F, 0.5F, 0.0F);
/* 53 */     String text = "Width: " + (1 + (this.bore.area + this.bore.maxRadius) * 2);
/* 54 */     this.fontRendererObj.drawStringWithShadow(text, 0.0F, 0.0F, 16777215);
/*    */     
/* 56 */     text = "Speed: +" + this.bore.speed;
/* 57 */     this.fontRendererObj.drawStringWithShadow(text, 0.0F, 10.0F, 16777215);
/* 58 */     text = "Other properties:";
/* 59 */     this.fontRendererObj.drawStringWithShadow(text, 0.0F, 24.0F, 16777215);
/* 60 */     int base = 0;
/*    */     
/* 62 */     int refining = this.bore.getStackInSlot(1) != null ? EnumInfusionEnchantment.getInfusionEnchantmentLevel(this.bore.getStackInSlot(1), EnumInfusionEnchantment.REFINING) : 0;
/*    */     
/* 64 */     if ((this.bore.getStackInSlot(0) != null) && ((this.bore.getStackInSlot(0).getItem() instanceof ItemFocusBasic)) && (((ItemFocusBasic)this.bore.getStackInSlot(0).getItem()).isUpgradedWith(this.bore.getStackInSlot(0), ItemFocusExcavation.dowsing)))
/*    */     {
/* 66 */       refining++;
/*    */     }
/* 68 */     if (refining > 0) {
/* 69 */       text = "Refining " + refining;
/* 70 */       this.fontRendererObj.drawStringWithShadow(text, 4.0F, 34 + base, 12632256);
/* 71 */       base += 9;
/*    */     }
/* 73 */     if (this.bore.fortune > 0) {
/* 74 */       text = "Fortune " + this.bore.fortune;
/* 75 */       this.fontRendererObj.drawStringWithShadow(text, 4.0F, 34 + base, 15648330);
/* 76 */       base += 9;
/*    */     }
/*    */     
/* 79 */     if (((this.bore.getStackInSlot(1) != null) && (EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, this.bore.getStackInSlot(1)) > 0)) || ((this.bore.getStackInSlot(0) != null) && ((this.bore.getStackInSlot(0).getItem() instanceof ItemFocusBasic)) && (((ItemFocusBasic)this.bore.getStackInSlot(0).getItem()).isUpgradedWith(this.bore.getStackInSlot(0), FocusUpgradeType.silktouch))))
/*    */     {
/*    */ 
/* 82 */       text = "Silk Touch";
/* 83 */       this.fontRendererObj.drawStringWithShadow(text, 4.0F, 34 + base, 8421631);
/* 84 */       base += 9;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 89 */     GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 90 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiArcaneBore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */