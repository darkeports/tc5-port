/*    */ package thaumcraft.client.renderers.tile;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockStaticLiquid;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.fluids.FluidTank;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.client.lib.UtilsFX;
/*    */ import thaumcraft.common.tiles.crafting.TileCrucible;
/*    */ 
/*    */ public class TileCrucibleRenderer extends net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
/*    */ {
/*    */   public void renderEntityAt(TileCrucible cr, double x, double y, double z, float fq)
/*    */   {
/* 19 */     if (cr.tank.getFluidAmount() > 0) renderFluid(cr, x, y, z);
/*    */   }
/*    */   
/*    */   public void renderFluid(TileCrucible cr, double x, double y, double z) {
/* 23 */     GL11.glPushMatrix();
/* 24 */     GL11.glTranslated(x, y + cr.getFluidHeight(), z + 1.0D);
/* 25 */     GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
/* 26 */     if (cr.tank.getFluidAmount() > 0) {
/* 27 */       TextureAtlasSprite icon = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(net.minecraft.init.Blocks.water.getDefaultState());
/* 28 */       cr.getClass();float recolor = cr.aspects.visSize() / 100.0F;
/* 29 */       if (recolor > 0.0F) recolor = 0.5F + recolor / 2.0F;
/* 30 */       UtilsFX.renderQuadFromIcon(icon, 1.0F, 1.0F - recolor / 3.0F, 1.0F - recolor, 1.0F - recolor / 2.0F, BlocksTC.crucible.getMixedBrightnessForBlock(cr.getWorld(), cr.getPos()), 771, 1.0F);
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 35 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   public void renderTileEntityAt(TileEntity te, double d, double d1, double d2, float f, int q)
/*    */   {
/* 40 */     renderEntityAt((TileCrucible)te, d, d1, d2, f);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileCrucibleRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */