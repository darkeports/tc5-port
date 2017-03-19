/*     */ package thaumcraft.client.renderers.tile;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.client.lib.models.AdvancedModelLoader;
/*     */ import thaumcraft.client.lib.models.IModelCustom;
/*     */ import thaumcraft.common.tiles.devices.TileNodeStabilizer;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class TileNodeStabilizerRenderer extends TileEntitySpecialRenderer
/*     */ {
/*     */   private IModelCustom model;
/*  24 */   private static final ResourceLocation MODEL = new ResourceLocation("thaumcraft", "models/obj/node_stabilizer.obj");
/*  25 */   private static final ResourceLocation TEX = new ResourceLocation("thaumcraft", "textures/models/node_stabilizer.png");
/*  26 */   private static final ResourceLocation OVER = new ResourceLocation("thaumcraft", "textures/models/node_stabilizer_over.png");
/*  27 */   private static final ResourceLocation BUBBLE = new ResourceLocation("thaumcraft", "textures/misc/node_bubble.png");
/*     */   
/*  29 */   public TileNodeStabilizerRenderer() { this.model = AdvancedModelLoader.loadModel(MODEL); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderTileEntityAt(TileNodeStabilizer tile, double par2, double par4, double par6, float par8)
/*     */   {
/*  38 */     int bright = 20;
/*  39 */     if (tile.getWorld() != null) {
/*  40 */       bright = tile.getBlockType().getMixedBrightnessForBlock(tile.getWorld(), tile.getPos());
/*     */     }
/*  42 */     GL11.glPushMatrix();
/*  43 */     GL11.glTranslatef((float)par2 + 0.5F, (float)par4, (float)par6 + 0.5F);
/*     */     
/*  45 */     GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
/*  46 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  48 */     bindTexture(TEX);
/*     */     
/*  50 */     this.model.renderPart("lock");
/*  51 */     for (int a = 0; a < 4; a++) {
/*  52 */       GL11.glPushMatrix();
/*  53 */       if (tile.getWorld() != null) {
/*  54 */         int j = bright;
/*  55 */         int k = j % 65536;
/*  56 */         int l = j / 65536;
/*  57 */         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/*     */       }
/*     */       
/*  60 */       GL11.glRotatef(90 * a, 0.0F, 0.0F, 1.0F);
/*  61 */       GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/*  62 */       GL11.glTranslatef(0.0F, 0.0F, tile.count / 100.0F);
/*  63 */       bindTexture(TEX);
/*  64 */       this.model.renderPart("piston");
/*     */       
/*  66 */       if (tile.getWorld() != null) {
/*  67 */         float scale = MathHelper.sin((Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + a * 5) / 3.0F) * 0.1F + 0.9F;
/*  68 */         int j = 50 + (int)(170.0F * (tile.count / 37.0F * scale));
/*  69 */         int k = j % 65536;
/*  70 */         int l = j / 65536;
/*  71 */         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/*     */       }
/*     */       
/*  74 */       bindTexture(OVER);
/*  75 */       this.model.renderPart("piston");
/*  76 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  77 */       GL11.glPopMatrix();
/*     */     }
/*     */     
/*  80 */     GL11.glPopMatrix();
/*     */     
/*  82 */     if (tile.count > 0) {
/*  83 */       GL11.glPushMatrix();
/*  84 */       GL11.glAlphaFunc(516, 0.003921569F);
/*  85 */       GL11.glEnable(3042);
/*  86 */       GL11.glBlendFunc(770, 1);
/*  87 */       GL11.glDepthMask(false);
/*  88 */       float alpha = MathHelper.sin(Minecraft.getMinecraft().getRenderViewEntity().ticksExisted / 8.0F) * 0.1F + 0.5F;
/*  89 */       bindTexture(BUBBLE);
/*  90 */       UtilsFX.renderFacingQuad(tile.getPos().getX() + 0.5D, tile.getPos().getY() + 1.5D, tile.getPos().getZ() + 0.5D, 1, 1, 0, 0.9F, 16777215, tile.count / 37.0F * alpha, 771, par8);
/*     */       
/*  92 */       GL11.glDepthMask(true);
/*  93 */       GL11.glDisable(3042);
/*  94 */       GL11.glAlphaFunc(516, 0.1F);
/*  95 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8, int q)
/*     */   {
/* 103 */     renderTileEntityAt((TileNodeStabilizer)par1TileEntity, par2, par4, par6, par8);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileNodeStabilizerRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */