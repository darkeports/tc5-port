/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumFacing.Axis;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ import thaumcraft.common.blocks.IBlockFacingHorizontal;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ 
/*     */ public class BlockSmelterVent extends BlockTC implements IBlockFacingHorizontal
/*     */ {
/*     */   public BlockSmelterVent()
/*     */   {
/*  26 */     super(net.minecraft.block.material.Material.iron);
/*  27 */     setStepSound(Block.soundTypeMetal);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty(IBlockFacingHorizontal.FACING, EnumFacing.NORTH));
/*  29 */     setHardness(1.0F);
/*  30 */     setResistance(10.0F);
/*     */   }
/*     */   
/*     */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
/*     */   {
/*  35 */     return true;
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  40 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
/*     */   {
/*  48 */     return (super.canPlaceBlockOnSide(worldIn, pos, side)) && (side.getAxis().isHorizontal()) && ((worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock() instanceof BlockSmelter)) && (BlockStateUtils.getFacing(worldIn.getBlockState(pos.offset(side.getOpposite()))) != side);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  56 */     IBlockState bs = getDefaultState();
/*  57 */     if (!facing.getAxis().isHorizontal()) facing = EnumFacing.NORTH;
/*  58 */     bs = bs.withProperty(IBlockFacingHorizontal.FACING, facing.getOpposite());
/*  59 */     return bs;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  65 */     IBlockState bs = getDefaultState();
/*  66 */     bs = bs.withProperty(IBlockFacingHorizontal.FACING, BlockStateUtils.getFacing(meta));
/*  67 */     return bs;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/*  73 */     return 0x0 | ((EnumFacing)state.getValue(IBlockFacingHorizontal.FACING)).getIndex();
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/*  79 */     ArrayList<IProperty> ip = new ArrayList();
/*  80 */     ip.add(IBlockFacingHorizontal.FACING);
/*  81 */     return ip.size() == 0 ? super.createBlockState() : new BlockState(this, (IProperty[])ip.toArray(new IProperty[ip.size()]));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  87 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/*  98 */     EnumFacing facing = BlockStateUtils.getFacing(getMetaFromState(worldIn.getBlockState(pos)));
/*  99 */     switch (facing.ordinal()) {
/* 100 */     case 2:  setBlockBounds(0.125F, 0.125F, 0.0F, 0.875F, 0.875F, 0.5F); break;
/* 101 */     case 3:  setBlockBounds(0.125F, 0.125F, 0.5F, 0.875F, 0.875F, 1.0F); break;
/* 102 */     case 4:  setBlockBounds(0.0F, 0.125F, 0.125F, 0.5F, 0.875F, 0.875F); break;
/* 103 */     case 5:  setBlockBounds(0.5F, 0.125F, 0.125F, 1.0F, 0.875F, 0.875F);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/* 110 */     setBlockBoundsBasedOnState(worldIn, pos);
/* 111 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockSmelterVent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */