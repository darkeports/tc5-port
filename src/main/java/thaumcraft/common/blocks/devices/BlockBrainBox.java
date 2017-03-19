/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ import thaumcraft.common.blocks.IBlockEnabled;
/*     */ import thaumcraft.common.blocks.IBlockFacing;
/*     */ import thaumcraft.common.blocks.IBlockFacingHorizontal;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ 
/*     */ public class BlockBrainBox extends BlockTC implements IBlockFacingHorizontal, IBlockEnabled
/*     */ {
/*     */   public BlockBrainBox()
/*     */   {
/*  25 */     super(Material.iron);
/*  26 */     setStepSound(Block.soundTypeMetal);
/*  27 */     IBlockState bs = this.blockState.getBaseState();
/*  28 */     bs.withProperty(IBlockFacing.FACING, EnumFacing.UP);
/*  29 */     setDefaultState(bs);
/*  30 */     setHardness(1.0F);
/*  31 */     setResistance(10.0F);
/*     */   }
/*     */   
/*     */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
/*     */   {
/*  36 */     return true;
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  41 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  46 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  52 */     return false;
/*     */   }
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/*  57 */     if (worldIn.getBlockState(pos.offset(BlockStateUtils.getFacing(state))).getBlock() != BlocksTC.thaumatorium) {
/*  58 */       dropBlockAsItem(worldIn, pos, BlocksTC.brainBox.getDefaultState(), 0);
/*  59 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
/*     */   {
/*  65 */     if (worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock() != BlocksTC.thaumatorium) return false;
/*  66 */     if (worldIn.getBlockState(pos.offset(side.getOpposite())).getValue(FACING) == side) return false;
/*  67 */     return super.canPlaceBlockOnSide(worldIn, pos, side);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  73 */     IBlockState bs = getDefaultState();
/*  74 */     bs = bs.withProperty(IBlockFacing.FACING, facing.getOpposite());
/*  75 */     return bs;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  81 */     IBlockState bs = getDefaultState();
/*  82 */     bs = bs.withProperty(IBlockFacing.FACING, BlockStateUtils.getFacing(meta));
/*  83 */     return bs;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/*  89 */     byte b0 = 0;
/*  90 */     int i = b0 | ((EnumFacing)state.getValue(IBlockFacing.FACING)).getIndex();
/*  91 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/*  97 */     return new BlockState(this, new IProperty[] { IBlockFacing.FACING });
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
/*     */   {
/* 102 */     setBlockBounds(0.1875F, 0.1875F, 0.1875F, 0.8125F, 0.8125F, 0.8125F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockBrainBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */