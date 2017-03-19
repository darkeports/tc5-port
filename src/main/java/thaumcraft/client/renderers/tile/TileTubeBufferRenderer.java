/*    */ package thaumcraft.client.renderers.tile;
/*    */ 
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.api.ThaumcraftApiHelper;
/*    */ import thaumcraft.client.renderers.models.block.ModelTubeValve;
/*    */ import thaumcraft.common.tiles.essentia.TileTubeBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileTubeBufferRenderer
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/*    */   private ModelTubeValve model;
/* 20 */   private static final ResourceLocation TEX_VALVE = new ResourceLocation("thaumcraft", "textures/models/valve.png");
/*    */   
/*    */ 
/*    */   public TileTubeBufferRenderer()
/*    */   {
/* 25 */     this.model = new ModelTubeValve();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void renderEntityAt(TileTubeBuffer buffer, double x, double y, double z, float fq)
/*    */   {
/* 32 */     bindTexture(TEX_VALVE);
/*    */     
/* 34 */     if (buffer.getWorld() != null) {
/* 35 */       for (EnumFacing dir : EnumFacing.VALUES) {
/* 36 */         if ((buffer.chokedSides[dir.ordinal()] != 0) && (buffer.openSides[dir.ordinal()] != 0) && (ThaumcraftApiHelper.getConnectableTile(buffer.getWorld(), buffer.getPos(), dir) != null))
/*    */         {
/*    */ 
/*    */ 
/* 40 */           GL11.glPushMatrix();
/* 41 */           GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
/*    */           
/* 43 */           if (dir.getOpposite().getFrontOffsetY() == 0) {
/* 44 */             GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*    */           } else {
/* 46 */             GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
/* 47 */             GL11.glRotatef(90.0F, dir.getOpposite().getFrontOffsetY(), 0.0F, 0.0F);
/*    */           }
/* 49 */           GL11.glRotatef(90.0F, dir.getOpposite().getFrontOffsetX(), dir.getOpposite().getFrontOffsetY(), dir.getOpposite().getFrontOffsetZ());
/*    */           
/* 51 */           GL11.glPushMatrix();
/* 52 */           if (buffer.chokedSides[dir.ordinal()] == 2) {
/* 53 */             GL11.glColor3f(1.0F, 0.3F, 0.3F);
/*    */           } else {
/* 55 */             GL11.glColor3f(0.3F, 0.3F, 1.0F);
/*    */           }
/*    */           
/* 58 */           GL11.glScaled(1.2D, 1.0D, 1.2D);
/*    */           
/* 60 */           GL11.glTranslated(0.0D, -0.5D, 0.0D);
/* 61 */           this.model.renderRod();
/* 62 */           GL11.glPopMatrix();
/* 63 */           GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 64 */           GL11.glPopMatrix();
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f, int q)
/*    */   {
/* 72 */     renderEntityAt((TileTubeBuffer)tileentity, d, d1, d2, f);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileTubeBufferRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */