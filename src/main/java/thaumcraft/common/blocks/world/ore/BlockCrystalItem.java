/*    */ package thaumcraft.common.blocks.world.ore;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ 
/*    */ public class BlockCrystalItem extends ItemBlock
/*    */ {
/*    */   public BlockCrystalItem(Block block)
/*    */   {
/* 14 */     super(block);
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int getColorFromItemStack(ItemStack stack, int layer)
/*    */   {
/* 21 */     return ((BlockCrystal)getBlock()).aspect.getColor();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\ore\BlockCrystalItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */