/*    */ package thaumcraft.client.lib;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.renderer.ItemModelMesher;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
/*    */ import net.minecraft.client.renderer.entity.RenderItem;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.client.resources.model.IBakedModel;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ 
/*    */ public class CustomRenderItem
/*    */   extends RenderItem
/*    */ {
/*    */   public CustomRenderItem()
/*    */   {
/* 22 */     super(null, Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void registerItems() {}
/*    */   
/*    */ 
/*    */   public void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text)
/*    */   {
/* 32 */     if ((stack != null) && (stack.stackSize <= 0)) text = EnumChatFormatting.GOLD + "*";
/* 33 */     Minecraft.getMinecraft().getRenderItem().renderItemOverlayIntoGUI(fr, stack, xPosition, yPosition, text);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected void registerItem(Item itm, int subType, String identifier) {}
/*    */   
/*    */ 
/*    */ 
/*    */   protected void registerBlock(Block blk, int subType, String identifier) {}
/*    */   
/*    */ 
/*    */ 
/*    */   public void func_175039_a(boolean p_175039_1_)
/*    */   {
/* 48 */     Minecraft.getMinecraft().getRenderItem().func_175039_a(p_175039_1_);
/*    */   }
/*    */   
/*    */   public ItemModelMesher getItemModelMesher()
/*    */   {
/* 53 */     return Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
/*    */   }
/*    */   
/*    */   public void renderItem(ItemStack stack, IBakedModel model)
/*    */   {
/* 58 */     Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
/*    */   }
/*    */   
/*    */   public boolean shouldRenderItemIn3D(ItemStack stack)
/*    */   {
/* 63 */     return Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(stack);
/*    */   }
/*    */   
/*    */   public void func_181564_a(ItemStack p_181564_1_, ItemCameraTransforms.TransformType p_181564_2_)
/*    */   {
/* 68 */     Minecraft.getMinecraft().getRenderItem().func_181564_a(p_181564_1_, p_181564_2_);
/*    */   }
/*    */   
/*    */   public void renderItemModelForEntity(ItemStack stack, EntityLivingBase entityToRenderFor, ItemCameraTransforms.TransformType cameraTransformType)
/*    */   {
/* 73 */     Minecraft.getMinecraft().getRenderItem().renderItemModelForEntity(stack, entityToRenderFor, cameraTransformType);
/*    */   }
/*    */   
/*    */   public void renderItemIntoGUI(ItemStack stack, int x, int y)
/*    */   {
/* 78 */     Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(stack, x, y);
/*    */   }
/*    */   
/*    */   public void renderItemAndEffectIntoGUI(ItemStack stack, int xPosition, int yPosition)
/*    */   {
/* 83 */     Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, xPosition, yPosition);
/*    */   }
/*    */   
/*    */   public void renderItemOverlays(FontRenderer fr, ItemStack stack, int xPosition, int yPosition)
/*    */   {
/* 88 */     Minecraft.getMinecraft().getRenderItem().renderItemOverlays(fr, stack, xPosition, yPosition);
/*    */   }
/*    */   
/*    */   public void onResourceManagerReload(IResourceManager resourceManager)
/*    */   {
/* 93 */     Minecraft.getMinecraft().getRenderItem().onResourceManagerReload(resourceManager);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\lib\CustomRenderItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */