/*    */ package thaumcraft.common.blocks.basic;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.common.blocks.ItemBlockTC;
/*    */ 
/*    */ public class BlockNitorItem
/*    */   extends ItemBlockTC
/*    */ {
/*    */   public BlockNitorItem(Block block)
/*    */   {
/* 12 */     super(block);
/*    */   }
/*    */   
/*    */   public int getColorFromItemStack(ItemStack stack, int renderPass)
/*    */   {
/* 17 */     Block block = Block.getBlockFromItem(stack.getItem());
/* 18 */     if ((block != null) && (renderPass == 0)) {
/* 19 */       return block.getRenderColor(block.getStateFromMeta(stack.getItemDamage()));
/*    */     }
/* 21 */     return super.getColorFromItemStack(stack, renderPass);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockNitorItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */