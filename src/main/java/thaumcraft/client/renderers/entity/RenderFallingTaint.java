/*    */ package thaumcraft.client.renderers.entity;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockModelRenderer;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityFallingBlock;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.common.entities.EntityFallingTaint;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderFallingTaint extends Render
/*    */ {
/*    */   public RenderFallingTaint(RenderManager p_i46177_1_)
/*    */   {
/* 29 */     super(p_i46177_1_);
/* 30 */     this.shadowSize = 0.5F;
/*    */   }
/*    */   
/*    */   public void doRender(EntityFallingTaint p_180557_1_, double p_180557_2_, double p_180557_4_, double p_180557_6_, float p_180557_8_, float p_180557_9_)
/*    */   {
/* 35 */     if (p_180557_1_.getBlock() != null)
/*    */     {
/* 37 */       bindTexture(TextureMap.locationBlocksTexture);
/* 38 */       IBlockState iblockstate = p_180557_1_.getBlock();
/* 39 */       Block block = iblockstate.getBlock();
/* 40 */       BlockPos blockpos = new BlockPos(p_180557_1_);
/* 41 */       World world = p_180557_1_.getWorld();
/*    */       
/* 43 */       if ((iblockstate != world.getBlockState(blockpos)) && (block.getRenderType() != -1))
/*    */       {
/* 45 */         if (block.getRenderType() == 3)
/*    */         {
/* 47 */           GlStateManager.pushMatrix();
/* 48 */           GlStateManager.translate((float)p_180557_2_, (float)p_180557_4_, (float)p_180557_6_);
/* 49 */           GlStateManager.disableLighting();
/* 50 */           Tessellator tessellator = Tessellator.getInstance();
/* 51 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 52 */           worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/*    */           
/* 54 */           int i = blockpos.getX();
/* 55 */           int j = blockpos.getY();
/* 56 */           int k = blockpos.getZ();
/* 57 */           worldrenderer.setTranslation(-i - 0.5F, -j, -k - 0.5F);
/* 58 */           BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 59 */           net.minecraft.client.resources.model.IBakedModel ibakedmodel = blockrendererdispatcher.getModelFromBlockState(iblockstate, world, (BlockPos)null);
/* 60 */           blockrendererdispatcher.getBlockModelRenderer().renderModel(world, ibakedmodel, iblockstate, blockpos, worldrenderer, false);
/* 61 */           worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 62 */           tessellator.draw();
/* 63 */           GlStateManager.enableLighting();
/* 64 */           GlStateManager.popMatrix();
/* 65 */           super.doRender(p_180557_1_, p_180557_2_, p_180557_4_, p_180557_6_, p_180557_8_, p_180557_9_);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityFallingBlock entity)
/*    */   {
/* 73 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */   
/*    */ 
/*    */   protected ResourceLocation getEntityTexture(Entity entity)
/*    */   {
/* 79 */     return getEntityTexture((EntityFallingBlock)entity);
/*    */   }
/*    */   
/*    */ 
/*    */   public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
/*    */   {
/* 85 */     doRender((EntityFallingTaint)entity, x, y, z, p_76986_8_, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\RenderFallingTaint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */