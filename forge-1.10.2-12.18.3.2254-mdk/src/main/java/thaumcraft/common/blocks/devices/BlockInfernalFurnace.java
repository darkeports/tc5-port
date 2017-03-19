/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockStaticLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.blocks.IBlockFacingHorizontal;
/*     */ import thaumcraft.common.blocks.misc.BlockPlaceholder;
/*     */ import thaumcraft.common.blocks.misc.BlockPlaceholder.PlaceholderType;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.tiles.devices.TileInfernalFurnace;
/*     */ 
/*     */ public class BlockInfernalFurnace extends BlockTCDevice implements IBlockFacingHorizontal
/*     */ {
/*     */   public BlockInfernalFurnace()
/*     */   {
/*  33 */     super(Material.rock, TileInfernalFurnace.class);
/*  34 */     setStepSound(Block.soundTypeStone);
/*  35 */     setLightLevel(0.9F);
/*  36 */     IBlockState bs = this.blockState.getBaseState();
/*  37 */     bs.withProperty(IBlockFacingHorizontal.FACING, EnumFacing.NORTH);
/*  38 */     setDefaultState(bs);
/*     */     
/*  40 */     setCreativeTab(null);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/*  46 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*  47 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
/*     */   
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public EnumWorldBlockLayer getBlockLayer()
/*     */   {
/*  57 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  63 */     IBlockState bs = getDefaultState();
/*  64 */     bs = bs.withProperty(IBlockFacingHorizontal.FACING, placer.getHorizontalFacing().getOpposite());
/*  65 */     return bs;
/*     */   }
/*     */   
/*  68 */   public static boolean ignore = false;
/*     */   
/*     */   public static void destroyFurnace(World worldIn, BlockPos pos, IBlockState state, BlockPos startpos) {
/*  71 */     if ((ignore) || (worldIn.isRemote)) return;
/*  72 */     ignore = true;
/*  73 */     for (int a = -1; a <= 1; a++) {
/*  74 */       for (int b = -1; b <= 1; b++) {
/*  75 */         for (int c = -1; c <= 1; c++)
/*  76 */           if (pos.add(a, b, c) != startpos) {
/*  77 */             IBlockState bs = worldIn.getBlockState(pos.add(a, b, c));
/*  78 */             if ((bs.getBlock() == BlocksTC.placeholder) && (bs.getValue(BlockPlaceholder.VARIANT) == BlockPlaceholder.PlaceholderType.FURNACE_BRICK)) {
/*  79 */               worldIn.setBlockState(pos.add(a, b, c), Blocks.nether_brick.getDefaultState());
/*     */             }
/*  81 */             if ((bs.getBlock() == BlocksTC.placeholder) && (bs.getValue(BlockPlaceholder.VARIANT) == BlockPlaceholder.PlaceholderType.FURNACE_OBSIDIAN))
/*  82 */               worldIn.setBlockState(pos.add(a, b, c), Blocks.obsidian.getDefaultState());
/*     */           }
/*     */       }
/*     */     }
/*  86 */     if (worldIn.isAirBlock(pos.offset(BlockStateUtils.getFacing(state).getOpposite())))
/*  87 */       worldIn.setBlockState(pos.offset(BlockStateUtils.getFacing(state).getOpposite()), Blocks.iron_bars.getDefaultState());
/*  88 */     worldIn.setBlockState(pos, Blocks.lava.getDefaultState());
/*  89 */     ignore = false;
/*     */   }
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/*  94 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 100 */     destroyFurnace(worldIn, pos, state, pos);
/* 101 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
/*     */   {
/* 106 */     if (entity.posX < pos.getX() + 0.3F) entity.motionX += 9.999999747378752E-5D;
/* 107 */     if (entity.posX > pos.getX() + 0.7F) entity.motionX -= 9.999999747378752E-5D;
/* 108 */     if (entity.posZ < pos.getZ() + 0.3F) entity.motionZ += 9.999999747378752E-5D;
/* 109 */     if (entity.posZ > pos.getZ() + 0.7F) entity.motionZ -= 9.999999747378752E-5D;
/* 110 */     if ((entity instanceof EntityItem)) {
/* 111 */       entity.motionY = 0.02500000037252903D;
/* 112 */       if (entity.onGround) {
/* 113 */         TileInfernalFurnace taf = (TileInfernalFurnace)world.getTileEntity(pos);
/* 114 */         if (taf.addItemsToInventory(((EntityItem)entity).getEntityItem())) {
/* 115 */           entity.setDead();
/*     */         }
/*     */       }
/*     */     }
/* 119 */     else if (((entity instanceof EntityLivingBase)) && 
/* 120 */       (!entity.isImmuneToFire()))
/*     */     {
/* 122 */       entity.attackEntityFrom(DamageSource.lava, 3.0F);
/* 123 */       entity.setFire(10);
/*     */     }
/*     */     
/* 126 */     super.onEntityCollidedWithBlock(world, pos, state, entity);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockInfernalFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */