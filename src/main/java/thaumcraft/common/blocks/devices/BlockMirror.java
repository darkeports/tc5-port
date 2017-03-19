/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.blocks.IBlockFacing;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.tiles.devices.TileMirror;
/*     */ import thaumcraft.common.tiles.devices.TileMirrorEssentia;
/*     */ 
/*     */ public class BlockMirror extends BlockTCDevice implements IBlockFacing
/*     */ {
/*     */   public BlockMirror(Class cls)
/*     */   {
/*  32 */     super(net.minecraft.block.material.Material.iron, cls);
/*  33 */     setStepSound(new thaumcraft.common.lib.CustomSoundType("jar", 1.0F, 1.0F));
/*  34 */     setHardness(0.1F);
/*  35 */     setHarvestLevel(null, 0);
/*  36 */     IBlockState bs = this.blockState.getBaseState();
/*  37 */     bs.withProperty(IBlockFacing.FACING, EnumFacing.UP);
/*  38 */     setDefaultState(bs);
/*     */   }
/*     */   
/*     */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
/*     */   {
/*  43 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  51 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  57 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  63 */     return false;
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  68 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  74 */     IBlockState bs = getDefaultState();
/*  75 */     bs = bs.withProperty(IBlockFacing.FACING, facing);
/*  76 */     return bs;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/*  84 */     EnumFacing d = BlockStateUtils.getFacing(state);
/*  85 */     if (!worldIn.getBlockState(pos.offset(d.getOpposite())).getBlock().isSideSolid(worldIn, pos.offset(d.getOpposite()), d)) {
/*  86 */       dropBlockAsItem(worldIn, pos, getDefaultState(), 0);
/*  87 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/*  93 */     EnumFacing facing = BlockStateUtils.getFacing(getMetaFromState(worldIn.getBlockState(pos)));
/*  94 */     switch (facing.ordinal()) {
/*  95 */     case 0:  setBlockBounds(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F); break;
/*  96 */     case 1:  setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F); break;
/*  97 */     case 2:  setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0F); break;
/*  98 */     case 3:  setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F); break;
/*  99 */     case 4:  setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F); break;
/* 100 */     case 5:  setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/* 107 */     setBlockBoundsBasedOnState(worldIn, pos);
/* 108 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
/*     */   {
/* 119 */     return worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock().isSideSolid(worldIn, pos.offset(side.getOpposite()), side);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/* 126 */     if (world.isRemote) return true;
/* 127 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
/*     */   {
/* 134 */     TileEntity te = worldIn.getTileEntity(pos);
/* 135 */     if (((te instanceof TileMirror)) || ((te instanceof TileMirrorEssentia)))
/*     */     {
/* 137 */       dropMirror(worldIn, pos, state, te);
/*     */     }
/*     */     else
/*     */     {
/* 141 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
/*     */   {
/* 148 */     if (((te instanceof TileMirror)) || ((te instanceof TileMirrorEssentia)))
/*     */     {
/* 150 */       dropMirror(worldIn, pos, state, te);
/*     */     }
/*     */     else
/*     */     {
/* 154 */       super.harvestBlock(worldIn, player, pos, state, (TileEntity)null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void dropMirror(World world, BlockPos pos, IBlockState state, TileEntity te)
/*     */   {
/* 160 */     if (this.tileClass == TileMirror.class) {
/* 161 */       TileMirror tm = (TileMirror)te;
/* 162 */       ItemStack drop = new ItemStack(this, 1, 0);
/* 163 */       if ((tm != null) && ((tm instanceof TileMirror))) {
/* 164 */         if (tm.linked) {
/* 165 */           drop.setItemDamage(1);
/* 166 */           drop.setTagInfo("linkX", new NBTTagInt(tm.linkX));
/* 167 */           drop.setTagInfo("linkY", new NBTTagInt(tm.linkY));
/* 168 */           drop.setTagInfo("linkZ", new NBTTagInt(tm.linkZ));
/* 169 */           drop.setTagInfo("linkDim", new NBTTagInt(tm.linkDim));
/* 170 */           drop.setTagInfo("dimname", new NBTTagString(DimensionManager.getProvider(tm.getWorld().provider.getDimensionId()).getDimensionName()));
/* 171 */           tm.invalidateLink();
/*     */         }
/* 173 */         spawnAsEntity(world, pos, drop);
/*     */       }
/*     */     } else {
/* 176 */       TileMirrorEssentia tm = (TileMirrorEssentia)te;
/* 177 */       ItemStack drop = new ItemStack(this, 1, 0);
/* 178 */       if ((tm != null) && ((tm instanceof TileMirrorEssentia))) {
/* 179 */         if (tm.linked) {
/* 180 */           drop.setItemDamage(1);
/* 181 */           drop.setTagInfo("linkX", new NBTTagInt(tm.linkX));
/* 182 */           drop.setTagInfo("linkY", new NBTTagInt(tm.linkY));
/* 183 */           drop.setTagInfo("linkZ", new NBTTagInt(tm.linkZ));
/* 184 */           drop.setTagInfo("linkDim", new NBTTagInt(tm.linkDim));
/* 185 */           drop.setTagInfo("dimname", new NBTTagString(DimensionManager.getProvider(tm.getWorld().provider.getDimensionId()).getDimensionName()));
/* 186 */           tm.invalidateLink();
/*     */         }
/* 188 */         spawnAsEntity(world, pos, drop);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
/*     */   {
/* 196 */     if ((!world.isRemote) && (this.tileClass == TileMirror.class) && ((entity instanceof EntityItem)) && (!entity.isDead) && (((EntityItem)entity).timeUntilPortal == 0))
/*     */     {
/* 198 */       TileMirror taf = (TileMirror)world.getTileEntity(pos);
/* 199 */       if (taf != null) {
/* 200 */         taf.transport((EntityItem)entity);
/*     */       }
/*     */     }
/* 203 */     super.onEntityCollidedWithBlock(world, pos, state, entity);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockMirror.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */