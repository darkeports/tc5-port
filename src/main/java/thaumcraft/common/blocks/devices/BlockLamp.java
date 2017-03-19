/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.blocks.IBlockEnabled;
/*     */ import thaumcraft.common.blocks.IBlockFacing;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.tiles.devices.TileLampArcane;
/*     */ 
/*     */ public class BlockLamp extends BlockTCDevice implements IBlockFacing, IBlockEnabled
/*     */ {
/*     */   public BlockLamp(Class tc)
/*     */   {
/*  26 */     super(Material.iron, tc);
/*  27 */     setStepSound(Block.soundTypeMetal);
/*  28 */     setHardness(1.0F);
/*  29 */     IBlockState bs = this.blockState.getBaseState();
/*  30 */     bs.withProperty(IBlockFacing.FACING, EnumFacing.DOWN);
/*  31 */     bs.withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(true));
/*  32 */     setDefaultState(bs);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  38 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  44 */     return false;
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  49 */     return 0;
/*     */   }
/*     */   
/*     */   public int getLightValue(IBlockAccess world, BlockPos pos)
/*     */   {
/*  54 */     return BlockStateUtils.isEnabled(world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos))) ? 15 : super.getLightValue(world, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  60 */     IBlockState bs = getDefaultState();
/*  61 */     bs = bs.withProperty(IBlockFacing.FACING, facing.getOpposite());
/*  62 */     bs = bs.withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(false));
/*  63 */     return bs;
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/*  68 */     TileEntity te = worldIn.getTileEntity(pos);
/*  69 */     if ((te != null) && ((te instanceof TileLampArcane))) {
/*  70 */       ((TileLampArcane)te).removeLights();
/*     */     }
/*  72 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/*  77 */     if (worldIn.isAirBlock(pos.offset(BlockStateUtils.getFacing(state)))) {
/*  78 */       dropBlockAsItem(worldIn, pos, getDefaultState(), 0);
/*  79 */       worldIn.setBlockToAir(pos);
/*  80 */       return;
/*     */     }
/*     */     
/*  83 */     TileEntity te = worldIn.getTileEntity(pos);
/*  84 */     if ((te != null) && ((te instanceof TileLampArcane)) && 
/*  85 */       (BlockStateUtils.isEnabled(state)) && (worldIn.isBlockPowered(pos))) {
/*  86 */       ((TileLampArcane)te).removeLights();
/*     */     }
/*     */     
/*     */ 
/*  90 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/*  95 */     setBlockBounds(0.25F, 0.125F, 0.25F, 0.75F, 0.875F, 0.75F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/* 101 */     setBlockBoundsBasedOnState(worldIn, pos);
/* 102 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockLamp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */