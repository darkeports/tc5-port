/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumFacing.Axis;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import thaumcraft.common.blocks.BlockTCDevice;
/*    */ import thaumcraft.common.blocks.IBlockFacingHorizontal;
/*    */ import thaumcraft.common.tiles.devices.TileArcaneBoreBase;
/*    */ 
/*    */ public class BlockArcaneBoreBase extends BlockTCDevice implements IBlockFacingHorizontal
/*    */ {
/*    */   public BlockArcaneBoreBase()
/*    */   {
/* 17 */     super(net.minecraft.block.material.Material.wood, TileArcaneBoreBase.class);
/* 18 */     setStepSound(Block.soundTypeWood);
/*    */   }
/*    */   
/*    */ 
/*    */   public int getRenderType()
/*    */   {
/* 24 */     return -1;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 30 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 36 */     return false;
/*    */   }
/*    */   
/*    */   public int damageDropped(IBlockState state)
/*    */   {
/* 41 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
/*    */   {
/* 47 */     return side.getAxis() == EnumFacing.Axis.Y;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockArcaneBoreBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */