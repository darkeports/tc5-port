/*    */ package thaumcraft.client.renderers.tile;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.client.lib.UtilsFX;
/*    */ import thaumcraft.client.lib.ender.ShaderCallback;
/*    */ import thaumcraft.client.lib.ender.ShaderHelper;
/*    */ 
/*    */ public class TileHoleRenderer extends net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
/*    */ {
/*    */   private final ShaderCallback shaderCallback;
/*    */   
/*    */   public TileHoleRenderer()
/*    */   {
/* 23 */     this.shaderCallback = new ShaderCallback()
/*    */     {
/*    */       public void call(int shader) {
/* 26 */         Minecraft mc = Minecraft.getMinecraft();
/*    */         
/* 28 */         int x = ARBShaderObjects.glGetUniformLocationARB(shader, "yaw");
/* 29 */         ARBShaderObjects.glUniform1fARB(x, (float)(mc.thePlayer.rotationYaw * 2.0F * 3.141592653589793D / 360.0D));
/*    */         
/* 31 */         int z = ARBShaderObjects.glGetUniformLocationARB(shader, "pitch");
/* 32 */         ARBShaderObjects.glUniform1fARB(z, -(float)(mc.thePlayer.rotationPitch * 2.0F * 3.141592653589793D / 360.0D));
/*    */       }
/*    */     };
/*    */   }
/*    */   
/*    */ 
/* 38 */   private static final ResourceLocation starsTexture = new ResourceLocation("textures/entity/end_portal.png");
/*    */   
/*    */   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f, int p_180535_9_)
/*    */   {
/* 42 */     GL11.glPushMatrix();
/* 43 */     bindTexture(starsTexture);
/* 44 */     ShaderHelper.useShader(ShaderHelper.endShader, this.shaderCallback);
/* 45 */     GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
/* 46 */     for (EnumFacing face : EnumFacing.values()) {
/* 47 */       IBlockState bs = te.getWorld().getBlockState(te.getPos().offset(face));
/* 48 */       if ((bs.getBlock().isOpaqueCube()) && (bs.getBlock() != BlocksTC.hole)) {
/* 49 */         GL11.glPushMatrix();
/* 50 */         GL11.glRotatef(90.0F, -face.getFrontOffsetY(), face.getFrontOffsetX(), -face.getFrontOffsetZ());
/* 51 */         if (face.getFrontOffsetZ() < 0) {
/* 52 */           GL11.glTranslated(0.0D, 0.0D, -0.49900001287460327D);
/* 53 */           GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*    */         } else {
/* 55 */           GL11.glTranslated(0.0D, 0.0D, 0.49900001287460327D);
/*    */         }
/* 57 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
/* 58 */         UtilsFX.renderQuadCentered();
/* 59 */         GL11.glPopMatrix();
/*    */       }
/*    */     }
/* 62 */     ShaderHelper.releaseShader();
/* 63 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileHoleRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */