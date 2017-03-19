/*     */ package thaumcraft.common.blocks.world.taint;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraftforge.common.IPlantable;
/*     */ import net.minecraftforge.common.property.ExtendedBlockState;
/*     */ import net.minecraftforge.common.property.IExtendedBlockState;
/*     */ import net.minecraftforge.common.property.IUnlistedProperty;
/*     */ import net.minecraftforge.common.property.Properties.PropertyAdapter;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.ThaumcraftMaterials;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.IBlockFacing;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.lib.CustomSoundType;
/*     */ import thaumcraft.common.lib.utils.BlockUtils;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ import thaumcraft.common.lib.world.biomes.BiomeHandler;
/*     */ 
/*     */ public class BlockTaintFibre
/*     */   extends Block implements ITaintBlock
/*     */ {
/*  51 */   public static final IUnlistedProperty<Boolean> NORTH = new Properties.PropertyAdapter(PropertyBool.create("north"));
/*  52 */   public static final IUnlistedProperty<Boolean> EAST = new Properties.PropertyAdapter(PropertyBool.create("east"));
/*  53 */   public static final IUnlistedProperty<Boolean> SOUTH = new Properties.PropertyAdapter(PropertyBool.create("south"));
/*  54 */   public static final IUnlistedProperty<Boolean> WEST = new Properties.PropertyAdapter(PropertyBool.create("west"));
/*  55 */   public static final IUnlistedProperty<Boolean> UP = new Properties.PropertyAdapter(PropertyBool.create("up"));
/*  56 */   public static final IUnlistedProperty<Boolean> DOWN = new Properties.PropertyAdapter(PropertyBool.create("down"));
/*  57 */   public static final IUnlistedProperty<Integer> GROWTH = new Properties.PropertyAdapter(PropertyInteger.create("growth", 0, 4));
/*     */   
/*     */   public BlockTaintFibre()
/*     */   {
/*  61 */     super(ThaumcraftMaterials.MATERIAL_TAINT);
/*  62 */     setHardness(1.0F);
/*  63 */     setStepSound(new CustomSoundType("gore", 0.5F, 0.8F));
/*  64 */     setTickRandomly(true);
/*  65 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
/*  69 */     return 3;
/*     */   }
/*     */   
/*  72 */   public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) { return 3; }
/*     */   
/*     */   public MapColor getMapColor(IBlockState state)
/*     */   {
/*  76 */     return MapColor.purpleColor;
/*     */   }
/*     */   
/*     */   public void die(World world, BlockPos pos, IBlockState blockState)
/*     */   {
/*  81 */     world.setBlockToAir(pos);
/*     */   }
/*     */   
/*     */   protected boolean canSilkHarvest()
/*     */   {
/*  86 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/*  92 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
/*     */   {
/*  98 */     state = getExtendedState(state, worldIn, pos);
/*  99 */     if ((state instanceof IExtendedBlockState)) {
/* 100 */       if ((worldIn.rand.nextInt(5) <= fortune) && ((((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 1) || (((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 2) || (((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 4)))
/*     */       {
/*     */ 
/*     */ 
/* 104 */         spawnAsEntity(worldIn, pos, new ItemStack(ItemsTC.tainted, 1, 1));
/*     */       }
/* 106 */       else if (((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 3) {
/* 107 */         if (worldIn.rand.nextInt(5) <= fortune) {
/* 108 */           spawnAsEntity(worldIn, pos, new ItemStack(ItemsTC.tainted, 1, 0));
/*     */         }
/* 110 */         AuraHelper.pollute(worldIn, pos, 3 + worldIn.rand.nextInt(3), true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
/*     */   {
/* 117 */     if (!world.isRemote)
/*     */     {
/*     */ 
/* 120 */       state = getExtendedState(state, world, pos);
/* 121 */       if ((state instanceof IExtendedBlockState)) {
/* 122 */         if ((((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 0) && (isOnlyAdjacentToTaint(world, pos))) {
/* 123 */           die(world, pos, state);
/*     */         }
/* 125 */         else if (AuraHelper.getAura(world, pos, Aspect.FLUX) < Config.AURABASE / 25) {
/* 126 */           die(world, pos, state);
/* 127 */           Utils.resetBiomeAt(world, pos, world.rand.nextInt(25) == 0);
/*     */         } else {
/* 129 */           spreadFibres(world, pos);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void spreadFibres(World world, BlockPos pos) {
/* 136 */     if (Config.wuss) { return;
/*     */     }
/* 138 */     if (AuraHelper.getAura(world, pos, Aspect.FLUX) > Config.AURABASE / 5)
/*     */     {
/* 140 */       if (world.getBiomeGenForCoords(pos).biomeID != Config.biomeTaintID) {
/* 141 */         Utils.setBiomeAt(world, pos, BiomeHandler.biomeTaint, world.rand.nextInt(10) == 0);
/*     */       }
/* 143 */       int xx = pos.getX() + world.rand.nextInt(3) - 1;
/* 144 */       int yy = pos.getY() + world.rand.nextInt(3) - 1;
/* 145 */       int zz = pos.getZ() + world.rand.nextInt(3) - 1;
/*     */       
/* 147 */       BlockPos t = new BlockPos(xx, yy, zz);
/*     */       
/* 149 */       if (t.equals(pos)) { return;
/*     */       }
/* 151 */       IBlockState bs = world.getBlockState(t);
/* 152 */       Material bm = bs.getBlock().getMaterial();
/* 153 */       float bh = bs.getBlock().getBlockHardness(world, t);
/* 154 */       if ((bh < 0.0F) || (bh > 10.0F)) { return;
/*     */       }
/* 156 */       if ((!bs.getBlock().isLeaves(world, t)) && (!bm.isLiquid()) && ((world.isAirBlock(t)) || (bs.getBlock().isReplaceable(world, t)) || ((bs.getBlock() instanceof BlockFlower)) || ((bs.getBlock() instanceof IPlantable))) && (BlockUtils.isAdjacentToSolidBlock(world, t)) && (!isOnlyAdjacentToTaint(world, t)))
/*     */       {
/*     */ 
/*     */ 
/* 160 */         if (world.rand.nextFloat() < Config.taintSpreadRate) AuraHelper.drainAura(world, t, Aspect.FLUX, 1);
/* 161 */         world.setBlockState(t, BlocksTC.taintFibre.getDefaultState());
/* 162 */         world.addBlockEvent(t, BlocksTC.taintFibre, 1, 0);
/* 163 */         if (world.getBiomeGenForCoords(t).biomeID != Config.biomeTaintID)
/* 164 */           Utils.setBiomeAt(world, t, BiomeHandler.biomeTaint);
/* 165 */         return;
/*     */       }
/*     */       
/* 168 */       if (bs.getBlock().isLeaves(world, t)) {
/* 169 */         EnumFacing face = null;
/* 170 */         if ((world.rand.nextFloat() < 0.6D) && ((face = BlockUtils.getFaceBlockTouching(world, t, BlocksTC.taintLog)) != null)) {
/* 171 */           world.setBlockState(t, BlocksTC.taintFeature.getDefaultState().withProperty(IBlockFacing.FACING, face.getOpposite()));
/* 172 */           if (world.rand.nextFloat() < Config.taintSpreadRate * 3.0F) AuraHelper.drainAura(world, t, Aspect.FLUX, 1);
/*     */         } else {
/* 174 */           if (world.rand.nextFloat() < Config.taintSpreadRate) AuraHelper.drainAura(world, t, Aspect.FLUX, 1);
/* 175 */           world.setBlockState(t, BlocksTC.taintFibre.getDefaultState());
/* 176 */           world.addBlockEvent(t, BlocksTC.taintFibre, 1, 0);
/*     */         }
/* 178 */         return;
/*     */       }
/*     */       
/*     */ 
/* 182 */       if (isHemmedByTaint(world, t)) {
/* 183 */         if ((Utils.isWoodLog(world, t)) && (bs.getBlock().getMaterial() != ThaumcraftMaterials.MATERIAL_TAINT)) {
/* 184 */           world.setBlockState(t, BlocksTC.taintLog.getDefaultState().withProperty(BlockTaintLog.VARIANT, BlockTaintLog.LogType.values()[0]).withProperty(BlockTaintLog.AXIS, BlockUtils.getBlockAxis(world, t)));
/*     */           
/*     */ 
/* 187 */           if (world.rand.nextFloat() < Config.taintSpreadRate * 2.0F) AuraHelper.drainAura(world, t, Aspect.FLUX, 1);
/* 188 */           return;
/*     */         }
/*     */         
/* 191 */         if ((bs.getBlock() == Blocks.red_mushroom_block) || (bs.getBlock() == Blocks.brown_mushroom_block) || (bm == Material.gourd) || (bm == Material.cactus) || (bm == Material.coral) || (bm == Material.sponge) || (bm == Material.wood))
/*     */         {
/* 193 */           world.setBlockState(t, BlocksTC.taintBlock.getStateFromMeta(0));
/* 194 */           world.addBlockEvent(t, BlocksTC.taintBlock, 1, 0);
/* 195 */           if (world.rand.nextFloat() < Config.taintSpreadRate * 2.0F) AuraHelper.drainAura(world, t, Aspect.FLUX, 1);
/* 196 */           return;
/*     */         }
/*     */         
/* 199 */         if ((bm == Material.sand) || (bm == Material.ground) || (bm == Material.grass) || (bm == Material.clay)) {
/* 200 */           world.setBlockState(t, BlocksTC.taintBlock.getStateFromMeta(1));
/* 201 */           world.addBlockEvent(t, BlocksTC.taintBlock, 1, 0);
/* 202 */           if (world.rand.nextFloat() < Config.taintSpreadRate * 2.0F) AuraHelper.drainAura(world, t, Aspect.FLUX, 1);
/* 203 */           return;
/*     */         }
/*     */         
/* 206 */         if (bm == Material.rock) {
/* 207 */           world.setBlockState(t, BlocksTC.taintBlock.getStateFromMeta(3));
/* 208 */           world.addBlockEvent(t, BlocksTC.taintBlock, 1, 0);
/* 209 */           if (world.rand.nextFloat() < Config.taintSpreadRate * 4.0F) AuraHelper.drainAura(world, t, Aspect.FLUX, 1);
/* 210 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/* 221 */     state = getExtendedState(state, worldIn, pos);
/* 222 */     if (((state instanceof IExtendedBlockState)) && 
/* 223 */       (((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 0) && (isOnlyAdjacentToTaint(worldIn, pos))) {
/* 224 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */     
/* 227 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */   }
/*     */   
/*     */   public static int getAdjacentTaint(IBlockAccess world, BlockPos pos) {
/* 231 */     int count = 0;
/* 232 */     for (EnumFacing dir : EnumFacing.VALUES) {
/* 233 */       if (world.getBlockState(pos.offset(dir)).getBlock().getMaterial() != ThaumcraftMaterials.MATERIAL_TAINT) count++;
/*     */     }
/* 235 */     return count;
/*     */   }
/*     */   
/*     */   public static boolean isOnlyAdjacentToTaint(World world, BlockPos pos) {
/* 239 */     for (EnumFacing dir : EnumFacing.VALUES) {
/* 240 */       if ((!world.isAirBlock(pos.offset(dir))) && (world.getBlockState(pos.offset(dir)).getBlock().getMaterial() != ThaumcraftMaterials.MATERIAL_TAINT) && (world.getBlockState(pos.offset(dir)).getBlock().isBlockSolid(world, pos.offset(dir), dir.getOpposite())))
/*     */       {
/* 242 */         return false; }
/*     */     }
/* 244 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isHemmedByTaint(World world, BlockPos pos) {
/* 248 */     int c = 0;
/* 249 */     for (EnumFacing dir : EnumFacing.VALUES) {
/* 250 */       Block block = world.getBlockState(pos.offset(dir)).getBlock();
/* 251 */       if (block.getMaterial() == ThaumcraftMaterials.MATERIAL_TAINT) { c++;
/* 252 */       } else if (world.isAirBlock(pos.offset(dir))) { c--;
/* 253 */       } else if ((!block.getMaterial().isLiquid()) && (!block.isBlockSolid(world, pos.offset(dir), dir.getOpposite()))) c--;
/*     */     }
/* 255 */     return c > 0;
/*     */   }
/*     */   
/*     */   public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity)
/*     */   {
/* 260 */     if ((!world.isRemote) && ((entity instanceof EntityLivingBase)) && (!((EntityLivingBase)entity).isEntityUndead()) && 
/* 261 */       (world.rand.nextInt(750) == 0)) {
/* 262 */       ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(PotionFluxTaint.instance.getId(), 200, 0, false, true));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
/*     */   {
/* 270 */     if (eventID == 1)
/*     */     {
/* 272 */       if (worldIn.isRemote) {
/* 273 */         worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:roots", 0.1F, 0.9F + worldIn.rand.nextFloat() * 0.2F, false);
/*     */       }
/* 275 */       return true;
/*     */     }
/* 277 */     return super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumWorldBlockLayer getBlockLayer()
/*     */   {
/* 284 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing o) {
/* 288 */     return false;
/*     */   }
/*     */   
/*     */   private boolean drawAt(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 292 */     Block b = worldIn.getBlockState(pos).getBlock();
/* 293 */     return (b != BlocksTC.taintFibre) && (b != BlocksTC.taintFeature) && (b.isBlockSolid(worldIn, pos, side.getOpposite()));
/*     */   }
/*     */   
/*     */ 
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, BlockPos pos)
/*     */   {
/* 299 */     IBlockState state = getExtendedState(iblockaccess.getBlockState(pos), iblockaccess, pos);
/* 300 */     if ((state instanceof IExtendedBlockState)) {
/* 301 */       int c = 0;
/* 302 */       if (((Boolean)((IExtendedBlockState)state).getValue(UP)).booleanValue()) c++;
/* 303 */       if (((Boolean)((IExtendedBlockState)state).getValue(DOWN)).booleanValue()) c++;
/* 304 */       if (((Boolean)((IExtendedBlockState)state).getValue(EAST)).booleanValue()) c++;
/* 305 */       if (((Boolean)((IExtendedBlockState)state).getValue(WEST)).booleanValue()) c++;
/* 306 */       if (((Boolean)((IExtendedBlockState)state).getValue(SOUTH)).booleanValue()) c++;
/* 307 */       if (((Boolean)((IExtendedBlockState)state).getValue(NORTH)).booleanValue()) c++;
/* 308 */       if (c > 1) {
/* 309 */         setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       }
/* 311 */       else if ((((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 1) || (((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 2))
/*     */       {
/* 313 */         setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.8F, 0.8F);
/*     */       }
/* 315 */       else if (((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 3) {
/* 316 */         setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.375F, 0.8F);
/*     */       }
/* 318 */       else if (((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 4) {
/* 319 */         setBlockBounds(0.2F, 0.2F, 0.2F, 0.8F, 1.0F, 0.8F);
/*     */       } else {
/* 321 */         if (((Boolean)((IExtendedBlockState)state).getValue(UP)).booleanValue()) {
/* 322 */           setBlockBounds(0.0F, 0.95F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/* 324 */         if (((Boolean)((IExtendedBlockState)state).getValue(DOWN)).booleanValue()) {
/* 325 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.05F, 1.0F);
/*     */         }
/* 327 */         if (((Boolean)((IExtendedBlockState)state).getValue(EAST)).booleanValue()) {
/* 328 */           setBlockBounds(0.95F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/* 330 */         if (((Boolean)((IExtendedBlockState)state).getValue(WEST)).booleanValue()) {
/* 331 */           setBlockBounds(0.0F, 0.0F, 0.0F, 0.05F, 1.0F, 1.0F);
/*     */         }
/* 333 */         if (((Boolean)((IExtendedBlockState)state).getValue(SOUTH)).booleanValue()) {
/* 334 */           setBlockBounds(0.0F, 0.0F, 0.95F, 1.0F, 1.0F, 1.0F);
/*     */         }
/* 336 */         if (((Boolean)((IExtendedBlockState)state).getValue(NORTH)).booleanValue()) {
/* 337 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.05F);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/* 347 */     if (drawAt(worldIn, pos.up(), EnumFacing.UP)) {
/* 348 */       setBlockBounds(0.0F, 0.95F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 349 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     }
/* 351 */     if (drawAt(worldIn, pos.down(), EnumFacing.DOWN)) {
/* 352 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.05F, 1.0F);
/* 353 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     }
/*     */     
/* 356 */     if (drawAt(worldIn, pos.east(), EnumFacing.EAST)) {
/* 357 */       setBlockBounds(0.95F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 358 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     }
/* 360 */     if (drawAt(worldIn, pos.west(), EnumFacing.WEST)) {
/* 361 */       setBlockBounds(0.0F, 0.0F, 0.0F, 0.05F, 1.0F, 1.0F);
/* 362 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     }
/*     */     
/* 365 */     if (drawAt(worldIn, pos.south(), EnumFacing.SOUTH)) {
/* 366 */       setBlockBounds(0.0F, 0.0F, 0.95F, 1.0F, 1.0F, 1.0F);
/* 367 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     }
/* 369 */     if (drawAt(worldIn, pos.north(), EnumFacing.NORTH)) {
/* 370 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.05F);
/* 371 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos)
/*     */   {
/* 380 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 385 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/* 391 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/* 397 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 403 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getLightValue(IBlockAccess world, BlockPos pos)
/*     */   {
/* 410 */     IBlockState state = getExtendedState(world.getBlockState(pos), world, pos);
/* 411 */     if ((state instanceof IExtendedBlockState)) {
/* 412 */       return (((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 2) || (((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 4) ? 6 : ((Integer)((IExtendedBlockState)state).getValue(GROWTH)).intValue() == 3 ? 12 : super.getLightValue(world, pos);
/*     */     }
/*     */     
/*     */ 
/* 416 */     return super.getLightValue(world, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 422 */     return state;
/*     */   }
/*     */   
/*     */   public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
/*     */   {
/* 427 */     if ((state instanceof IExtendedBlockState)) {
/* 428 */       IExtendedBlockState retval = (IExtendedBlockState)state;
/* 429 */       boolean d = drawAt(world, pos.down(), EnumFacing.DOWN);
/* 430 */       boolean u = drawAt(world, pos.up(), EnumFacing.UP);
/* 431 */       int growth = 0;
/* 432 */       Random r = new Random(pos.toLong());
/* 433 */       int q = r.nextInt(50);
/* 434 */       if (d) {
/* 435 */         if (q < 4) { growth = 1;
/* 436 */         } else if ((q == 4) || (q == 5)) { growth = 2;
/* 437 */         } else if (q == 6) growth = 3;
/*     */       }
/* 439 */       if ((u) && 
/* 440 */         (q > 47)) { growth = 4;
/*     */       }
/* 442 */       return retval.withProperty(UP, Boolean.valueOf(drawAt(world, pos.up(), EnumFacing.UP))).withProperty(DOWN, Boolean.valueOf(drawAt(world, pos.down(), EnumFacing.DOWN))).withProperty(NORTH, Boolean.valueOf(drawAt(world, pos.north(), EnumFacing.NORTH))).withProperty(EAST, Boolean.valueOf(drawAt(world, pos.east(), EnumFacing.EAST))).withProperty(SOUTH, Boolean.valueOf(drawAt(world, pos.south(), EnumFacing.SOUTH))).withProperty(WEST, Boolean.valueOf(drawAt(world, pos.west(), EnumFacing.WEST))).withProperty(GROWTH, Integer.valueOf(growth));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 451 */     return state;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 457 */     IProperty[] listedProperties = new IProperty[0];
/* 458 */     IUnlistedProperty[] unlistedProperties = { UP, DOWN, NORTH, EAST, WEST, SOUTH, GROWTH };
/* 459 */     return new ExtendedBlockState(this, listedProperties, unlistedProperties);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\taint\BlockTaintFibre.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */