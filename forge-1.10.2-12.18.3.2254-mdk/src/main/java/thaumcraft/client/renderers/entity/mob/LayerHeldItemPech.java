/*    */ package thaumcraft.client.renderers.entity.mob;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.ItemRenderer;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraft.item.ItemBow;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.wands.IWand;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class LayerHeldItemPech implements net.minecraft.client.renderer.entity.layers.LayerRenderer
/*    */ {
/*    */   private final RendererLivingEntity field_177206_a;
/*    */   
/*    */   public LayerHeldItemPech(RendererLivingEntity p_i46115_1_)
/*    */   {
/* 28 */     this.field_177206_a = p_i46115_1_;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
/*    */   {
/* 33 */     ItemStack itemstack = p_177141_1_.getHeldItem();
/*    */     
/* 35 */     if (itemstack != null)
/*    */     {
/* 37 */       GlStateManager.pushMatrix();
/*    */       
/* 39 */       if (this.field_177206_a.getMainModel().isChild)
/*    */       {
/* 41 */         float f7 = 0.5F;
/* 42 */         GlStateManager.translate(0.0F, 0.625F, 0.0F);
/* 43 */         GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
/* 44 */         GlStateManager.scale(f7, f7, f7);
/*    */       }
/*    */       
/* 47 */       ((ModelBiped)this.field_177206_a.getMainModel()).postRenderArm(0.0625F);
/* 48 */       GlStateManager.translate(-0.0625F, 0.32F, 0.0625F);
/*    */       
/* 50 */       if ((itemstack.getItem() instanceof IWand)) {
/* 51 */         GlStateManager.translate(0.0D, -0.1D, 0.0D);
/*    */       }
/*    */       
/* 54 */       if ((itemstack.getItem() instanceof ItemBow)) {
/* 55 */         GlStateManager.translate(-0.07500000298023224D, -0.1D, 0.0D);
/*    */       }
/*    */       
/* 58 */       if (((p_177141_1_ instanceof EntityPlayer)) && (((EntityPlayer)p_177141_1_).fishEntity != null))
/*    */       {
/* 60 */         itemstack = new ItemStack(Items.fishing_rod, 0);
/*    */       }
/*    */       
/* 63 */       Item item = itemstack.getItem();
/* 64 */       Minecraft minecraft = Minecraft.getMinecraft();
/*    */       
/* 66 */       if (((item instanceof ItemBlock)) && (Block.getBlockFromItem(item).getRenderType() == 2))
/*    */       {
/* 68 */         GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
/* 69 */         GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
/* 70 */         GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 71 */         float f8 = 0.375F;
/* 72 */         GlStateManager.scale(-f8, -f8, f8);
/*    */       }
/*    */       
/* 75 */       minecraft.getItemRenderer().renderItem(p_177141_1_, itemstack, net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.THIRD_PERSON);
/* 76 */       GlStateManager.popMatrix();
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures()
/*    */   {
/* 82 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\mob\LayerHeldItemPech.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */