/*    */ package thaumcraft.common.blocks.world.plants;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class BlockLeavesTCItem extends ItemBlock
/*    */ {
/*    */   private final BlockLeavesTC leaves;
/*    */   
/*    */   public BlockLeavesTCItem(Block block)
/*    */   {
/* 14 */     super(block);
/* 15 */     this.leaves = ((BlockLeavesTC)block);
/* 16 */     setMaxDamage(0);
/* 17 */     setHasSubtypes(true);
/*    */   }
/*    */   
/*    */   public int getMetadata(int damage)
/*    */   {
/* 22 */     return damage | 0x4;
/*    */   }
/*    */   
/*    */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public int getColorFromItemStack(ItemStack stack, int renderPass)
/*    */   {
/* 28 */     return this.leaves.getRenderColor(this.leaves.getStateFromMeta(stack.getMetadata()));
/*    */   }
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack)
/*    */   {
/* 33 */     return super.getUnlocalizedName() + "." + this.leaves.getWoodTCType(stack.getMetadata()).getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\plants\BlockLeavesTCItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */