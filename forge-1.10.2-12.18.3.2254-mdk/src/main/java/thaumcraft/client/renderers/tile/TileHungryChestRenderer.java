/*     */ package thaumcraft.client.renderers.tile;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.model.ModelChest;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.common.tiles.devices.TileHungryChest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class TileHungryChestRenderer
/*     */   extends TileEntitySpecialRenderer
/*     */ {
/*  22 */   private ModelChest chestModel = new ModelChest();
/*  23 */   private static final ResourceLocation textureNormal = new ResourceLocation("thaumcraft", "textures/models/chesthungry.png");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderTileEntityChestAt(TileHungryChest chest, double par2, double par4, double par6, float par8, int bp)
/*     */   {
/*  30 */     int var9 = 0;
/*     */     
/*  32 */     if (!chest.hasWorldObj())
/*     */     {
/*  34 */       var9 = 0;
/*     */     }
/*     */     else
/*     */     {
/*  38 */       Block var10 = chest.getBlockType();
/*  39 */       var9 = chest.getBlockMetadata();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  44 */     ModelChest var14 = this.chestModel;
/*     */     
/*  46 */     if (bp >= 0)
/*     */     {
/*  48 */       bindTexture(DESTROY_STAGES[bp]);
/*  49 */       GlStateManager.matrixMode(5890);
/*  50 */       GlStateManager.pushMatrix();
/*  51 */       GlStateManager.scale(4.0F, 4.0F, 1.0F);
/*  52 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  53 */       GlStateManager.matrixMode(5888);
/*     */     } else {
/*  55 */       bindTexture(textureNormal);
/*     */     }
/*  57 */     GL11.glPushMatrix();
/*  58 */     GL11.glEnable(32826);
/*  59 */     if (bp < 0)
/*     */     {
/*  61 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     
/*  64 */     GL11.glTranslatef((float)par2, (float)par4 + 1.0F, (float)par6 + 1.0F);
/*  65 */     GL11.glScalef(1.0F, -1.0F, -1.0F);
/*  66 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*  67 */     short var11 = 0;
/*     */     
/*  69 */     if (var9 == 2)
/*     */     {
/*  71 */       var11 = 180;
/*     */     }
/*     */     
/*  74 */     if (var9 == 3)
/*     */     {
/*  76 */       var11 = 0;
/*     */     }
/*     */     
/*  79 */     if (var9 == 4)
/*     */     {
/*  81 */       var11 = 90;
/*     */     }
/*     */     
/*  84 */     if (var9 == 5)
/*     */     {
/*  86 */       var11 = -90;
/*     */     }
/*     */     
/*  89 */     GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
/*  90 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*  91 */     float var12 = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * par8;
/*     */     
/*     */ 
/*  94 */     var12 = 1.0F - var12;
/*  95 */     var12 = 1.0F - var12 * var12 * var12;
/*  96 */     var14.chestLid.rotateAngleX = (-(var12 * 3.1415927F / 2.0F));
/*  97 */     var14.renderAll();
/*  98 */     GL11.glDisable(32826);
/*  99 */     GL11.glPopMatrix();
/* 100 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 102 */     if (bp >= 0)
/*     */     {
/* 104 */       GlStateManager.matrixMode(5890);
/* 105 */       GlStateManager.popMatrix();
/* 106 */       GlStateManager.matrixMode(5888);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8, int q)
/*     */   {
/* 114 */     renderTileEntityChestAt((TileHungryChest)par1TileEntity, par2, par4, par6, par8, q);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileHungryChestRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */