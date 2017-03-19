/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumFacing.Axis;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.blocks.BlockTC;
/*    */ import thaumcraft.common.blocks.IBlockFacingHorizontal;
/*    */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*    */ 
/*    */ public class BlockSmelterAux extends BlockTC implements IBlockFacingHorizontal
/*    */ {
/*    */   public BlockSmelterAux()
/*    */   {
/* 23 */     super(net.minecraft.block.material.Material.iron);
/* 24 */     setStepSound(Block.soundTypeMetal);
/* 25 */     setDefaultState(this.blockState.getBaseState().withProperty(IBlockFacingHorizontal.FACING, EnumFacing.NORTH));
/* 26 */     setHardness(1.0F);
/* 27 */     setResistance(10.0F);
/*    */   }
/*    */   
/*    */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
/*    */   {
/* 32 */     return true;
/*    */   }
/*    */   
/*    */   public int damageDropped(IBlockState state)
/*    */   {
/* 37 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
/*    */   
/*    */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
/*    */   {
/* 45 */     return (super.canPlaceBlockOnSide(worldIn, pos, side)) && (side.getAxis().isHorizontal()) && ((worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock() instanceof BlockSmelter)) && (BlockStateUtils.getFacing(worldIn.getBlockState(pos.offset(side.getOpposite()))) != side);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*    */   {
/* 53 */     IBlockState bs = getDefaultState();
/* 54 */     if (!facing.getAxis().isHorizontal()) facing = EnumFacing.NORTH;
/* 55 */     bs = bs.withProperty(IBlockFacingHorizontal.FACING, facing.getOpposite());
/* 56 */     return bs;
/*    */   }
/*    */   
/*    */ 
/*    */   public IBlockState getStateFromMeta(int meta)
/*    */   {
/* 62 */     IBlockState bs = getDefaultState();
/* 63 */     bs = bs.withProperty(IBlockFacingHorizontal.FACING, BlockStateUtils.getFacing(meta));
/* 64 */     return bs;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getMetaFromState(IBlockState state)
/*    */   {
/* 70 */     return 0x0 | ((EnumFacing)state.getValue(IBlockFacingHorizontal.FACING)).getIndex();
/*    */   }
/*    */   
/*    */ 
/*    */   protected BlockState createBlockState()
/*    */   {
/* 76 */     ArrayList<IProperty> ip = new ArrayList();
/* 77 */     ip.add(IBlockFacingHorizontal.FACING);
/* 78 */     return ip.size() == 0 ? super.createBlockState() : new BlockState(this, (IProperty[])ip.toArray(new IProperty[ip.size()]));
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 84 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 90 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockSmelterAux.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */