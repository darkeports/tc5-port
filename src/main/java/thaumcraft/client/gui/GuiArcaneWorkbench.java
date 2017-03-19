/*     */ package thaumcraft.client.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.common.container.InventoryArcaneWorkbench;
/*     */ import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
/*     */ import thaumcraft.common.tiles.crafting.TileArcaneWorkbench;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiArcaneWorkbench extends GuiContainer
/*     */ {
/*     */   private TileArcaneWorkbench tileEntity;
/*     */   private InventoryPlayer ip;
/*  28 */   private int[][] aspectLocs = { { 72, 21 }, { 24, 43 }, { 24, 102 }, { 72, 124 }, { 120, 102 }, { 120, 43 } };
/*     */   
/*     */   public GuiArcaneWorkbench(InventoryPlayer par1InventoryPlayer, TileArcaneWorkbench e)
/*     */   {
/*  32 */     super(new thaumcraft.common.container.ContainerArcaneWorkbench(par1InventoryPlayer, e));
/*  33 */     this.tileEntity = e;
/*  34 */     this.ip = par1InventoryPlayer;
/*  35 */     this.ySize = 234;
/*  36 */     this.xSize = 190;
/*     */   }
/*     */   
/*     */ 
/*  40 */   ArrayList<Aspect> primals = Aspect.getPrimalAspects();
/*     */   
/*  42 */   ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_arcaneworkbench.png");
/*     */   
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/*     */   {
/*  47 */     this.mc.renderEngine.bindTexture(this.tex);
/*  48 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  49 */     GL11.glEnable(3042);
/*  50 */     int var5 = (this.width - this.xSize) / 2;
/*  51 */     int var6 = (this.height - this.ySize) / 2;
/*  52 */     drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
/*  53 */     GL11.glDisable(3042);
/*     */     
/*  55 */     IWand wand = null;
/*  56 */     if ((this.tileEntity.inventory.getStackInSlot(10) != null) && ((this.tileEntity.inventory.getStackInSlot(10).getItem() instanceof IWand))) {
/*  57 */       wand = (IWand)this.tileEntity.inventory.getStackInSlot(10).getItem();
/*     */     }
/*     */     
/*  60 */     AspectList cost = null;
/*     */     int count;
/*  62 */     if (ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.tileEntity.inventory, this.ip.player) != null) {
/*  63 */       cost = ThaumcraftCraftingManager.findMatchingArcaneRecipeAspects(this.tileEntity.inventory, this.ip.player);
/*  64 */       count = 0;
/*  65 */       for (Aspect primal : this.primals) {
/*  66 */         float amt = cost.getAmount(primal);
/*  67 */         if (cost.getAmount(primal) > 0) {
/*  68 */           float alpha = 0.5F + (MathHelper.sin((this.ip.player.ticksExisted + count * 10) / 2.0F) * 0.2F - 0.2F);
/*  69 */           if (wand != null) {
/*  70 */             amt *= wand.getConsumptionModifier(this.tileEntity.inventory.getStackInSlot(10), this.ip.player, primal, true);
/*  71 */             if (amt <= wand.getVis(this.tileEntity.inventory.getStackInSlot(10), primal)) {
/*  72 */               alpha = 1.0F;
/*     */             }
/*     */           }
/*     */           
/*  76 */           UtilsFX.drawTag(var5 + this.aspectLocs[count][0] - 8, var6 + this.aspectLocs[count][1] - 8, primal, amt, 0, this.zLevel, 771, alpha, false);
/*     */         }
/*     */         
/*  79 */         count++;
/*  80 */         if (count > 5)
/*     */           break;
/*     */       }
/*     */     }
/*  84 */     if ((wand != null) && 
/*  85 */       (cost != null) && (!wand.consumeAllVis(this.tileEntity.inventory.getStackInSlot(10), this.ip.player, cost, false, true))) {
/*  86 */       GL11.glPushMatrix();
/*  87 */       float var40 = 0.33F;
/*  88 */       GL11.glColor4f(var40, var40, var40, 0.66F);
/*     */       
/*  90 */       GL11.glEnable(2896);
/*  91 */       GL11.glEnable(2884);
/*  92 */       GL11.glEnable(3042);
/*     */       
/*  94 */       this.itemRender.renderItemAndEffectIntoGUI(ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.tileEntity.inventory, this.ip.player), var5 + 160, var6 + 64);
/*     */       
/*  96 */       this.itemRender.renderItemOverlayIntoGUI(this.mc.fontRendererObj, ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.tileEntity.inventory, this.ip.player), var5 + 160, var6 + 64, "");
/*     */       
/*     */ 
/*     */ 
/* 100 */       GL11.glDisable(3042);
/* 101 */       GL11.glDisable(2896);
/* 102 */       GL11.glPopMatrix();
/*     */       
/* 104 */       GL11.glPushMatrix();
/* 105 */       GL11.glTranslatef(var5 + 168, var6 + 46, 0.0F);
/* 106 */       GL11.glScalef(0.5F, 0.5F, 0.0F);
/* 107 */       String text = "Insufficient vis";
/*     */       
/* 109 */       int ll = this.fontRendererObj.getStringWidth(text) / 2;
/* 110 */       this.fontRendererObj.drawString(text, -ll, 0, 15625838);
/* 111 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 112 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiArcaneWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */