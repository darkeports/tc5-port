/*    */ package thaumcraft.common.blocks.world.plants;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ 
/*    */ public class BlockSaplingTCItem extends ItemBlock
/*    */ {
/*    */   public BlockSaplingTCItem(Block par1)
/*    */   {
/* 10 */     super(par1);
/* 11 */     setHasSubtypes(true);
/*    */   }
/*    */   
/*    */ 
/*    */   public int getMetadata(int metadata)
/*    */   {
/* 17 */     return metadata;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getUnlocalizedName(net.minecraft.item.ItemStack stack)
/*    */   {
/* 23 */     return super.getUnlocalizedName() + "." + ((BlockSaplingTC)this.block).getStateName(this.block.getStateFromMeta(stack.getMetadata()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\plants\BlockSaplingTCItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */