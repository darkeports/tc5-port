/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.blocks.IBlockEnabled;
/*     */ import thaumcraft.common.blocks.IBlockFacingHorizontal;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.tiles.crafting.TileSmelter;
/*     */ 
/*     */ public class BlockSmelter extends BlockTCDevice implements IBlockEnabled, IBlockFacingHorizontal
/*     */ {
/*     */   public BlockSmelter()
/*     */   {
/*  32 */     super(Material.iron, TileSmelter.class);
/*  33 */     setStepSound(Block.soundTypeMetal);
/*     */     
/*  35 */     IBlockState bs = this.blockState.getBaseState();
/*  36 */     bs.withProperty(IBlockFacingHorizontal.FACING, EnumFacing.NORTH);
/*  37 */     bs.withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(false));
/*  38 */     setDefaultState(bs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  48 */     IBlockState bs = getDefaultState();
/*  49 */     bs = bs.withProperty(IBlockFacingHorizontal.FACING, placer.getHorizontalFacing().getOpposite());
/*  50 */     bs = bs.withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(false));
/*  51 */     return bs;
/*     */   }
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/*  56 */     TileEntity te = worldIn.getTileEntity(pos);
/*  57 */     if ((te != null) && ((te instanceof TileSmelter))) {
/*  58 */       ((TileSmelter)te).checkNeighbours();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/*  64 */     if ((!world.isRemote) && (!playerIn.isSneaking())) {
/*  65 */       playerIn.openGui(Thaumcraft.instance, 9, world, pos.getX(), pos.getY(), pos.getZ());
/*     */     }
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   public int getLightValue(IBlockAccess world, BlockPos pos)
/*     */   {
/*  72 */     return BlockStateUtils.isEnabled(world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos))) ? 13 : super.getLightValue(world, pos);
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride()
/*     */   {
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  82 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getComparatorInputOverride(World world, BlockPos pos)
/*     */   {
/*  88 */     TileEntity te = world.getTileEntity(pos);
/*  89 */     if ((te != null) && ((te instanceof IInventory))) {
/*  90 */       return Container.calcRedstoneFromInventory((IInventory)te);
/*     */     }
/*  92 */     return 0;
/*     */   }
/*     */   
/*     */   public static void setFurnaceState(World world, BlockPos pos, boolean state) {
/*  96 */     if (state == BlockStateUtils.isEnabled(world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)))) return;
/*  97 */     TileEntity tileentity = world.getTileEntity(pos);
/*  98 */     keepInventory = true;
/*  99 */     world.setBlockState(pos, world.getBlockState(pos).withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(state)), 3);
/* 100 */     world.setBlockState(pos, world.getBlockState(pos).withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(state)), 3);
/* 101 */     if (tileentity != null)
/*     */     {
/* 103 */       tileentity.validate();
/* 104 */       world.setTileEntity(pos, tileentity);
/*     */     }
/* 106 */     keepInventory = false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 112 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 114 */     if (((tileentity instanceof TileSmelter)) && (!worldIn.isRemote) && (((TileSmelter)tileentity).vis > 0))
/*     */     {
/* 116 */       int ess = ((TileSmelter)tileentity).vis;
/* 117 */       AuraHelper.pollute(worldIn, pos, ess, true);
/*     */     }
/*     */     
/* 120 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void randomDisplayTick(World w, BlockPos pos, IBlockState state, Random r)
/*     */   {
/* 126 */     if (BlockStateUtils.isEnabled(state)) {
/* 127 */       float f = pos.getX() + 0.5F;
/* 128 */       float f1 = pos.getY() + 0.2F + r.nextFloat() * 5.0F / 16.0F;
/* 129 */       float f2 = pos.getZ() + 0.5F;
/* 130 */       float f3 = 0.52F;
/* 131 */       float f4 = r.nextFloat() * 0.5F - 0.25F;
/*     */       
/* 133 */       if (BlockStateUtils.getFacing(state) == EnumFacing.WEST) {
/* 134 */         w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D, new int[0]);
/* 135 */         w.spawnParticle(EnumParticleTypes.FLAME, f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */       
/* 138 */       if (BlockStateUtils.getFacing(state) == EnumFacing.EAST) {
/* 139 */         w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D, new int[0]);
/* 140 */         w.spawnParticle(EnumParticleTypes.FLAME, f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */       
/* 143 */       if (BlockStateUtils.getFacing(state) == EnumFacing.NORTH) {
/* 144 */         w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D, new int[0]);
/* 145 */         w.spawnParticle(EnumParticleTypes.FLAME, f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */       
/* 148 */       if (BlockStateUtils.getFacing(state) == EnumFacing.SOUTH) {
/* 149 */         w.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D, new int[0]);
/* 150 */         w.spawnParticle(EnumParticleTypes.FLAME, f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockSmelter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */