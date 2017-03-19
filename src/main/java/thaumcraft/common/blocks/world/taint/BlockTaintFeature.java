/*     */ package thaumcraft.common.blocks.world.taint;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumFacing.Axis;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.BlockFluidBase;
/*     */ import thaumcraft.api.ThaumcraftMaterials;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ import thaumcraft.common.blocks.IBlockFacing;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintCrawler;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class BlockTaintFeature
/*     */   extends BlockTC
/*     */   implements ITaintBlock
/*     */ {
/*     */   public BlockTaintFeature()
/*     */   {
/*  41 */     super(ThaumcraftMaterials.MATERIAL_TAINT);
/*  42 */     setHardness(0.1F);
/*  43 */     setLightLevel(0.625F);
/*  44 */     IBlockState bs = this.blockState.getBaseState();
/*  45 */     bs.withProperty(IBlockFacing.FACING, EnumFacing.UP);
/*  46 */     setDefaultState(bs);
/*  47 */     setTickRandomly(true);
/*  48 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */   protected boolean canSilkHarvest()
/*     */   {
/*  53 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/*  59 */     if (!worldIn.isRemote)
/*     */     {
/*  61 */       if (worldIn.rand.nextFloat() < 0.333F) {
/*  62 */         Entity e = new EntityTaintCrawler(worldIn);
/*  63 */         e.setLocationAndAngles(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.rand.nextInt(360), 0.0F);
/*  64 */         worldIn.spawnEntityInWorld(e);
/*     */       } else {
/*  66 */         AuraHelper.pollute(worldIn, pos, 1, true);
/*     */       }
/*     */     }
/*  69 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public void die(World world, BlockPos pos, IBlockState blockState)
/*     */   {
/*  74 */     world.setBlockState(pos, BlocksTC.taintDust.getDefaultState().withProperty(BlockFluidBase.LEVEL, Integer.valueOf(world.rand.nextInt(2))));
/*  75 */     Utils.resetBiomeAt(world, pos, world.rand.nextInt(25) == 0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
/*     */   {
/*  81 */     if (!world.isRemote)
/*     */     {
/*     */ 
/*  84 */       if ((AuraHelper.getAura(world, pos, Aspect.FLUX) < Config.AURABASE / 25) && (random.nextInt(10) == 0)) {
/*  85 */         die(world, pos, state);
/*  86 */         return;
/*     */       }
/*  88 */       if ((AuraHelper.getAura(world, pos, Aspect.FLUX) <= Config.AURABASE / 5) && (random.nextInt(200) == 0)) {
/*  89 */         AuraHelper.pollute(world, pos, 1, true);
/*  90 */         return;
/*     */       }
/*  92 */       BlockTaintFibre.spreadFibres(world, pos);
/*     */       
/*  94 */       if ((world.getBlockState(pos.down()).getBlock() == BlocksTC.taintLog) && (world.getBlockState(pos.down()).getValue(BlockTaintLog.AXIS) == EnumFacing.Axis.Y) && (world.rand.nextInt(100) == 0))
/*     */       {
/*     */ 
/*  97 */         if (world.rand.nextFloat() < Config.taintSpreadRate * 5.0F) AuraHelper.drainAura(world, pos, Aspect.FLUX, 1);
/*  98 */         world.setBlockState(pos, BlocksTC.taintBlock.getStateFromMeta(2));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/* 106 */     return ItemsTC.tainted;
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/* 111 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
/*     */   {
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 121 */     return 200;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/* 127 */     if ((!worldIn.isRemote) && (!worldIn.getBlockState(pos.offset(BlockStateUtils.getFacing(state).getOpposite())).getBlock().isBlockSolid(worldIn, pos.offset(BlockStateUtils.getFacing(state).getOpposite()), BlockStateUtils.getFacing(state))))
/*     */     {
/* 129 */       spawnAsEntity(worldIn, pos, new ItemStack(ItemsTC.tainted));
/* 130 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/* 137 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/* 143 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/* 149 */     IBlockState bs = getDefaultState();
/* 150 */     bs = bs.withProperty(IBlockFacing.FACING, facing);
/* 151 */     return bs;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 158 */     IBlockState bs = getDefaultState();
/* 159 */     bs = bs.withProperty(IBlockFacing.FACING, BlockStateUtils.getFacing(meta));
/* 160 */     return bs;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 166 */     byte b0 = 0;
/* 167 */     int i = b0 | ((EnumFacing)state.getValue(IBlockFacing.FACING)).getIndex();
/* 168 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 174 */     ArrayList<IProperty> ip = new ArrayList();
/* 175 */     ip.add(IBlockFacing.FACING);
/* 176 */     return new BlockState(this, (IProperty[])ip.toArray(new IProperty[ip.size()]));
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 181 */     EnumFacing facing = BlockStateUtils.getFacing(getMetaFromState(worldIn.getBlockState(pos)));
/* 182 */     switch (facing.ordinal()) {
/* 183 */     case 0:  setBlockBounds(0.125F, 0.625F, 0.125F, 0.875F, 1.0F, 0.875F); break;
/* 184 */     case 1:  setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.375F, 0.875F); break;
/* 185 */     case 2:  setBlockBounds(0.125F, 0.125F, 0.625F, 0.875F, 0.875F, 1.0F); break;
/* 186 */     case 3:  setBlockBounds(0.125F, 0.125F, 0.0F, 0.875F, 0.875F, 0.375F); break;
/* 187 */     case 4:  setBlockBounds(0.625F, 0.125F, 0.125F, 1.0F, 0.875F, 0.875F); break;
/* 188 */     case 5:  setBlockBounds(0.0F, 0.125F, 0.125F, 0.375F, 0.875F, 0.875F);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/* 195 */     setBlockBoundsBasedOnState(worldIn, pos);
/* 196 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\taint\BlockTaintFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */