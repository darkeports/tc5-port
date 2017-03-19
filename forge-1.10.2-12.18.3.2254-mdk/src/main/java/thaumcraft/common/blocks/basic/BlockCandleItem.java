/*    */ package thaumcraft.common.blocks.basic;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.common.blocks.ItemBlockTC;
/*    */ 
/*    */ 
/*    */ public class BlockCandleItem
/*    */   extends ItemBlockTC
/*    */ {
/*    */   public BlockCandleItem(Block block)
/*    */   {
/* 13 */     super(block);
/*    */   }
/*    */   
/*    */   public int getColorFromItemStack(ItemStack stack, int renderPass)
/*    */   {
/* 18 */     Block block = Block.getBlockFromItem(stack.getItem());
/* 19 */     if (block != null) {
/* 20 */       return block.getRenderColor(block.getStateFromMeta(stack.getItemDamage()));
/*    */     }
/* 22 */     return super.getColorFromItemStack(stack, renderPass);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockCandleItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */