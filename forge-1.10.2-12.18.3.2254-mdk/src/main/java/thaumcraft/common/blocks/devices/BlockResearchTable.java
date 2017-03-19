/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.blocks.BlockTCDevice;
/*    */ import thaumcraft.common.blocks.IBlockFacingHorizontal;
/*    */ import thaumcraft.common.tiles.crafting.TileResearchTable;
/*    */ 
/*    */ public class BlockResearchTable extends BlockTCDevice implements IBlockFacingHorizontal
/*    */ {
/*    */   public BlockResearchTable()
/*    */   {
/* 21 */     super(Material.wood, TileResearchTable.class);
/* 22 */     setStepSound(Block.soundTypeWood);
/*    */   }
/*    */   
/*    */   public int damageDropped(IBlockState state)
/*    */   {
/* 27 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 33 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 39 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
/*    */   {
/* 44 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*    */   {
/* 52 */     if (world.isRemote) { return true;
/*    */     }
/* 54 */     player.openGui(Thaumcraft.instance, 10, world, pos.getX(), pos.getY(), pos.getZ());
/*    */     
/* 56 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*    */   {
/* 62 */     IBlockState bs = getDefaultState();
/* 63 */     bs = bs.withProperty(IBlockFacingHorizontal.FACING, placer.getHorizontalFacing());
/* 64 */     return bs;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockResearchTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */