/*    */ package thaumcraft.common.blocks.basic;
/*    */ 
/*    */ import net.minecraft.block.BlockStairs;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BlockStairsTC extends BlockStairs
/*    */ {
/*    */   public BlockStairsTC(IBlockState modelState)
/*    */   {
/* 14 */     super(modelState);
/* 15 */     setCreativeTab(thaumcraft.common.Thaumcraft.tabTC);
/* 16 */     setLightOpacity(0);
/*    */   }
/*    */   
/*    */   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
/*    */   {
/* 21 */     if (getMaterial() == Material.wood) return 20;
/* 22 */     return super.getFlammability(world, pos, face);
/*    */   }
/*    */   
/*    */   public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
/*    */   {
/* 27 */     if (getMaterial() == Material.wood) return 5;
/* 28 */     return super.getFireSpreadSpeed(world, pos, face);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockStairsTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */