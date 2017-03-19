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
/*    */ import thaumcraft.common.container.ContainerTurretBasic;
/*    */ import thaumcraft.common.entities.construct.EntityTurretCrossbow;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiTurretBasic
/*    */   extends GuiContainer
/*    */ {
/*    */   EntityTurretCrossbow turret;
/*    */   
/*    */   public GuiTurretBasic(InventoryPlayer par1InventoryPlayer, World world, EntityTurretCrossbow t)
/*    */   {
/* 23 */     super(new ContainerTurretBasic(par1InventoryPlayer, world, t));
/* 24 */     this.xSize = 175;
/* 25 */     this.ySize = 232;
/* 26 */     this.turret = t;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 33 */   ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_turret_basic.png");
/*    */   
/*    */ 
/*    */   protected void drawGuiContainerForegroundLayer(int par1, int par2) {}
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/*    */   {
/* 40 */     this.mc.renderEngine.bindTexture(this.tex);
/* 41 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 42 */     int k = (this.width - this.xSize) / 2;
/* 43 */     int l = (this.height - this.ySize) / 2;
/* 44 */     GL11.glEnable(3042);
/* 45 */     drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
/*    */     
/* 47 */     int h = (int)(39.0F * (this.turret.getHealth() / this.turret.getMaxHealth()));
/* 48 */     drawTexturedModalRect(k + 68, l + 59, 192, 48, h, 6);
/*    */     
/* 50 */     GL11.glDisable(3042);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiTurretBasic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */