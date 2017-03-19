/*    */ package thaumcraft.client.renderers.tile;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockModelRenderer;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.client.resources.model.IBakedModel;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.client.lib.models.AdvancedModelLoader;
/*    */ import thaumcraft.client.lib.models.IModelCustom;
/*    */ import thaumcraft.common.tiles.misc.TileEldritchCrabSpawner;
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class TileEldritchCrabSpawnerRenderer
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/*    */   private IModelCustom model;
/* 33 */   private static final ResourceLocation VENT = new ResourceLocation("thaumcraft", "models/obj/crabvent.obj");
/* 34 */   private static final ResourceLocation T1 = new ResourceLocation("thaumcraft", "textures/models/crabvent.png");
/*    */   
/* 36 */   public TileEldritchCrabSpawnerRenderer() { this.model = AdvancedModelLoader.loadModel(VENT); }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void renderTileEntityAt(TileEldritchCrabSpawner tile, double par2, double par4, double par6, float par8)
/*    */   {
/* 45 */     bindTexture(TextureMap.locationBlocksTexture);
/* 46 */     IBlockState iblockstate = BlocksTC.stone.getStateFromMeta(6);
/* 47 */     Block block = iblockstate.getBlock();
/*    */     
/* 49 */     GlStateManager.pushMatrix();
/* 50 */     GlStateManager.translate(par2, par4, par6);
/* 51 */     GlStateManager.disableLighting();
/* 52 */     Tessellator tessellator = Tessellator.getInstance();
/* 53 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 54 */     worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/*    */     
/* 56 */     int i = tile.getPos().getX();
/* 57 */     int j = tile.getPos().getY();
/* 58 */     int k = tile.getPos().getZ();
/* 59 */     worldrenderer.setTranslation(-i, -j, -k);
/* 60 */     BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 61 */     IBakedModel ibakedmodel = blockrendererdispatcher.getModelFromBlockState(iblockstate, tile.getWorld(), (BlockPos)null);
/* 62 */     blockrendererdispatcher.getBlockModelRenderer().renderModel(tile.getWorld(), ibakedmodel, iblockstate, tile.getPos(), worldrenderer, false);
/* 63 */     worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 64 */     tessellator.draw();
/* 65 */     GlStateManager.enableLighting();
/* 66 */     GlStateManager.popMatrix();
/*    */     
/*    */ 
/* 69 */     GL11.glPushMatrix();
/* 70 */     translateFromOrientation(par2, par4, par6, tile.getVentFacing());
/* 71 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 72 */     bindTexture(T1);
/* 73 */     this.model.renderAll();
/* 74 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */ 
/*    */   private void translateFromOrientation(double x, double y, double z, int orientation)
/*    */   {
/* 80 */     GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
/* 81 */     if (orientation == 0) {
/* 82 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 83 */     } else if (orientation == 1) {
/* 84 */       GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 85 */     } else if (orientation == 2) {
/* 86 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 87 */     } else if (orientation != 3) {
/* 88 */       if (orientation == 4) {
/* 89 */         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/* 90 */       } else if (orientation == 5) {
/* 91 */         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8, int q) {
/* 97 */     renderTileEntityAt((TileEldritchCrabSpawner)par1TileEntity, par2, par4, par6, par8);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileEldritchCrabSpawnerRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */