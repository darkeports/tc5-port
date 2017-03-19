/*    */ package thaumcraft.client.lib;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockModelShapes;
/*    */ import net.minecraft.client.renderer.ItemModelMesher;
/*    */ import net.minecraft.client.renderer.entity.RenderItem;
/*    */ import net.minecraft.client.resources.model.ModelManager;
/*    */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class ModelHelper
/*    */ {
/*    */   public static void registerItem(Item item, int metadata, String itemName)
/*    */   {
/* 33 */     getItemModelMesher().register(item, metadata, new ModelResourceLocation(itemName, "inventory"));
/*    */   }
/*    */   
/*    */   public static void registerBlock(Block block, int metadata, String blockName)
/*    */   {
/* 38 */     registerItem(Item.getItemFromBlock(block), metadata, blockName);
/*    */   }
/*    */   
/*    */   public static void registerBlock(Block block, String blockName)
/*    */   {
/* 43 */     registerBlock(block, 0, blockName);
/*    */   }
/*    */   
/*    */   public static void registerItem(Item item, String itemName)
/*    */   {
/* 48 */     registerItem(item, 0, itemName);
/*    */   }
/*    */   
/*    */   public static ItemModelMesher getItemModelMesher()
/*    */   {
/* 53 */     return Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
/*    */   }
/*    */   
/*    */   public static BlockModelShapes getBlockModelShapes()
/*    */   {
/* 58 */     return getItemModelMesher().getModelManager().getBlockModelShapes();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\lib\ModelHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */