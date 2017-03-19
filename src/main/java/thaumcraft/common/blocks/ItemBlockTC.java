/*    */ package thaumcraft.common.blocks;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemBlockTC
/*    */   extends ItemBlock
/*    */ {
/*    */   public ItemBlockTC(Block block)
/*    */   {
/* 12 */     super(block);
/*    */     
/* 14 */     setMaxDamage(0);
/* 15 */     setHasSubtypes(true);
/*    */   }
/*    */   
/*    */ 
/*    */   public int getMetadata(int metadata)
/*    */   {
/* 21 */     return metadata;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getUnlocalizedName(ItemStack stack)
/*    */   {
/* 27 */     BlockTC block = (BlockTC)this.block;
/*    */     
/* 29 */     if (block.hasProperties())
/*    */     {
/* 31 */       return super.getUnlocalizedName() + "." + block.getStateName(block.getStateFromMeta(stack.getMetadata()), false);
/*    */     }
/*    */     
/* 34 */     return super.getUnlocalizedName();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\ItemBlockTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */