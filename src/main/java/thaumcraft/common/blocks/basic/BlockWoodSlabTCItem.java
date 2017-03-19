/*    */ package thaumcraft.common.blocks.basic;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockSlab;
/*    */ import net.minecraft.item.ItemSlab;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class BlockWoodSlabTCItem extends ItemSlab
/*    */ {
/*    */   public BlockWoodSlabTCItem(Block par1)
/*    */   {
/* 12 */     super(par1, (BlockSlab)thaumcraft.api.blocks.BlocksTC.slabWood, (BlockSlab)thaumcraft.api.blocks.BlocksTC.doubleSlabWood);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getUnlocalizedName(ItemStack stack)
/*    */   {
/* 19 */     return super.getUnlocalizedName() + "." + ((BlockWoodSlabTC)this.block).getStateName(this.block.getStateFromMeta(stack.getMetadata()), false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockWoodSlabTCItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */