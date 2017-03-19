/*    */ package thaumcraft.client.renderers.tile;
/*    */ 
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.api.ThaumcraftApiHelper;
/*    */ import thaumcraft.client.renderers.models.block.ModelTubeValve;
/*    */ import thaumcraft.common.tiles.essentia.TileTubeOneway;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileTubeOnewayRenderer
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/*    */   private ModelTubeValve model;
/* 21 */   private static final ResourceLocation TEX_VALVE = new ResourceLocation("thaumcraft", "textures/models/valve.png");
/*    */   
/*    */ 
/*    */   public TileTubeOnewayRenderer()
/*    */   {
/* 26 */     this.model = new ModelTubeValve();
/*    */   }
/*    */   
/* 29 */   EnumFacing fd = null;
/*    */   
/*    */ 
/*    */   public void renderEntityAt(TileTubeOneway valve, double x, double y, double z, float fq)
/*    */   {
/* 34 */     bindTexture(TEX_VALVE);
/* 35 */     if ((valve.getWorld() != null) && (ThaumcraftApiHelper.getConnectableTile(valve.getWorld(), valve.getPos(), valve.facing.getOpposite()) == null)) {
/* 36 */       return;
/*    */     }
/*    */     
/*    */ 
/* 40 */     GL11.glPushMatrix();
/* 41 */     this.fd = valve.facing;
/* 42 */     GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
/*    */     
/* 44 */     if (this.fd.getFrontOffsetY() == 0) {
/* 45 */       GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*    */     } else {
/* 47 */       GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
/* 48 */       GL11.glRotatef(90.0F, this.fd.getFrontOffsetY(), 0.0F, 0.0F);
/*    */     }
/* 50 */     GL11.glRotatef(90.0F, this.fd.getFrontOffsetX(), this.fd.getFrontOffsetY(), this.fd.getFrontOffsetZ());
/*    */     
/*    */ 
/* 53 */     GL11.glPushMatrix();
/* 54 */     GL11.glColor3f(0.45F, 0.5F, 1.0F);
/* 55 */     GL11.glScaled(1.25D, 1.0D, 1.25D);
/* 56 */     GL11.glTranslated(0.0D, -0.4000000059604645D, 0.0D);
/* 57 */     this.model.renderRod();
/* 58 */     GL11.glPopMatrix();
/* 59 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 60 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f, int q)
/*    */   {
/* 67 */     renderEntityAt((TileTubeOneway)tileentity, d, d1, d2, f);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileTubeOnewayRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */