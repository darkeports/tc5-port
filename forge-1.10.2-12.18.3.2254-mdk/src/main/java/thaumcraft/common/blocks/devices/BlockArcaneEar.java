/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.blocks.IBlockEnabled;
/*     */ import thaumcraft.common.blocks.IBlockFacing;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.tiles.devices.TileArcaneEar;
/*     */ 
/*     */ public class BlockArcaneEar extends BlockTCDevice implements IBlockFacing, IBlockEnabled
/*     */ {
/*     */   public BlockArcaneEar()
/*     */   {
/*  26 */     super(net.minecraft.block.material.Material.wood, TileArcaneEar.class);
/*  27 */     setStepSound(Block.soundTypeWood);
/*  28 */     setHardness(1.0F);
/*     */     
/*  30 */     IBlockState bs = this.blockState.getBaseState();
/*  31 */     bs.withProperty(IBlockFacing.FACING, EnumFacing.UP);
/*  32 */     bs.withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(false));
/*  33 */     setDefaultState(bs);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  39 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  45 */     return false;
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  50 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  56 */     IBlockState bs = getDefaultState();
/*  57 */     bs = bs.withProperty(IBlockFacing.FACING, facing);
/*  58 */     bs = bs.withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(false));
/*  59 */     return bs;
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/*  64 */     TileArcaneEar tile = (TileArcaneEar)worldIn.getTileEntity(pos);
/*  65 */     if (tile != null)
/*     */     {
/*  67 */       tile.updateTone();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/*  73 */     TileArcaneEar tile = (TileArcaneEar)worldIn.getTileEntity(pos);
/*  74 */     if (tile != null)
/*     */     {
/*  76 */       tile.updateTone();
/*     */     }
/*  78 */     if (!worldIn.getBlockState(pos.offset(BlockStateUtils.getFacing(state).getOpposite())).getBlock().isSideSolid(worldIn, pos.offset(BlockStateUtils.getFacing(state).getOpposite()), BlockStateUtils.getFacing(state)))
/*     */     {
/*  80 */       dropBlockAsItem(worldIn, pos, getDefaultState(), 0);
/*  81 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/*  87 */     if (world.isRemote) return true;
/*  88 */     TileArcaneEar tile = (TileArcaneEar)world.getTileEntity(pos);
/*  89 */     if (tile != null)
/*     */     {
/*  91 */       tile.changePitch();
/*  92 */       tile.triggerNote(world, pos, true);
/*     */     }
/*  94 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canProvidePower() {
/*  98 */     return true;
/*     */   }
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
/*     */   {
/* 103 */     return BlockStateUtils.isEnabled(state.getBlock().getMetaFromState(state)) ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
/*     */   {
/* 108 */     return BlockStateUtils.isEnabled(state.getBlock().getMetaFromState(state)) ? 15 : 0;
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
/*     */   {
/* 113 */     return worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock().isSideSolid(worldIn, pos.offset(side.getOpposite()), side);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 120 */     EnumFacing facing = BlockStateUtils.getFacing(getMetaFromState(worldIn.getBlockState(pos)));
/* 121 */     switch (facing.ordinal()) {
/* 122 */     case 0:  setBlockBounds(0.125F, 0.625F, 0.125F, 0.875F, 1.0F, 0.875F); break;
/* 123 */     case 1:  setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.375F, 0.875F); break;
/* 124 */     case 2:  setBlockBounds(0.125F, 0.125F, 0.625F, 0.875F, 0.875F, 1.0F); break;
/* 125 */     case 3:  setBlockBounds(0.125F, 0.125F, 0.0F, 0.875F, 0.875F, 0.375F); break;
/* 126 */     case 4:  setBlockBounds(0.625F, 0.125F, 0.125F, 1.0F, 0.875F, 0.875F); break;
/* 127 */     case 5:  setBlockBounds(0.0F, 0.125F, 0.125F, 0.375F, 0.875F, 0.875F);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/* 134 */     setBlockBoundsBasedOnState(worldIn, pos);
/* 135 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int par5, int par6)
/*     */   {
/* 141 */     float var7 = (float)Math.pow(2.0D, (par6 - 12) / 12.0D);
/* 142 */     if (par5 <= 4) {
/* 143 */       if (par5 >= 0) {
/* 144 */         String var8 = "harp";
/*     */         
/* 146 */         if (par5 == 1)
/*     */         {
/* 148 */           var8 = "bd";
/*     */         }
/*     */         
/* 151 */         if (par5 == 2)
/*     */         {
/* 153 */           var8 = "snare";
/*     */         }
/*     */         
/* 156 */         if (par5 == 3)
/*     */         {
/* 158 */           var8 = "hat";
/*     */         }
/*     */         
/* 161 */         if (par5 == 4)
/*     */         {
/* 163 */           var8 = "bassattack";
/*     */         }
/*     */         
/* 166 */         worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "note." + var8, 3.0F, var7);
/*     */       }
/* 168 */       worldIn.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, par6 / 24.0D, 0.0D, 0.0D, new int[0]);
/* 169 */       return true;
/*     */     }
/* 171 */     return super.onBlockEventReceived(worldIn, pos, state, par5, par6);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockArcaneEar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */