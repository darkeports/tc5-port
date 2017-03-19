/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.blocks.BlockTCDevice;
/*    */ import thaumcraft.common.blocks.IBlockFacing;
/*    */ import thaumcraft.common.tiles.essentia.TileCrystallizer;
/*    */ 
/*    */ public class BlockCrystallizer extends BlockTCDevice implements IBlockFacing
/*    */ {
/*    */   public BlockCrystallizer()
/*    */   {
/* 18 */     super(Material.wood, TileCrystallizer.class);
/* 19 */     setStepSound(Block.soundTypeWood);
/*    */   }
/*    */   
/*    */   public int damageDropped(IBlockState state)
/*    */   {
/* 24 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 30 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 36 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getRenderType()
/*    */   {
/* 42 */     return -1;
/*    */   }
/*    */   
/*    */ 
/*    */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*    */   {
/* 48 */     IBlockState bs = getDefaultState();
/* 49 */     bs = bs.withProperty(IBlockFacing.FACING, facing.getOpposite());
/* 50 */     return bs;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockCrystallizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */