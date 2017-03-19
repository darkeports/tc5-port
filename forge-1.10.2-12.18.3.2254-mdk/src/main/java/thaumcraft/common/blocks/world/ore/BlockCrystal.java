/*     */ package thaumcraft.common.blocks.world.ore;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.property.ExtendedBlockState;
/*     */ import net.minecraftforge.common.property.IExtendedBlockState;
/*     */ import net.minecraftforge.common.property.IUnlistedProperty;
/*     */ import net.minecraftforge.common.property.Properties.PropertyAdapter;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.api.wands.IWandable;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.items.resources.ItemShard.ShardType;
/*     */ import thaumcraft.common.lib.CustomSoundType;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.lib.utils.BlockUtils;
/*     */ 
/*     */ public class BlockCrystal
/*     */   extends Block
/*     */   implements IWandable
/*     */ {
/*  45 */   public static final PropertyInteger SIZE = PropertyInteger.create("size", 0, 3);
/*  46 */   public static final PropertyInteger GENERATION = PropertyInteger.create("gen", 1, 4);
/*  47 */   public static final IUnlistedProperty<Boolean> NORTH = new Properties.PropertyAdapter(PropertyBool.create("north"));
/*  48 */   public static final IUnlistedProperty<Boolean> EAST = new Properties.PropertyAdapter(PropertyBool.create("east"));
/*  49 */   public static final IUnlistedProperty<Boolean> SOUTH = new Properties.PropertyAdapter(PropertyBool.create("south"));
/*  50 */   public static final IUnlistedProperty<Boolean> WEST = new Properties.PropertyAdapter(PropertyBool.create("west"));
/*  51 */   public static final IUnlistedProperty<Boolean> UP = new Properties.PropertyAdapter(PropertyBool.create("up"));
/*  52 */   public static final IUnlistedProperty<Boolean> DOWN = new Properties.PropertyAdapter(PropertyBool.create("down"));
/*  53 */   public static final IUnlistedProperty<Integer> SEED = new Properties.PropertyAdapter(PropertyInteger.create("seed", 0, 7));
/*     */   protected Aspect aspect;
/*     */   
/*     */   public BlockCrystal(Aspect aspect)
/*     */   {
/*  58 */     super(Material.glass);
/*  59 */     this.aspect = aspect;
/*  60 */     setHardness(0.25F);
/*  61 */     setStepSound(new CustomSoundType("crystal", 1.0F, 1.0F));
/*  62 */     setTickRandomly(true);
/*  63 */     setCreativeTab(Thaumcraft.tabTC);
/*  64 */     setDefaultState(this.blockState.getBaseState().withProperty(SIZE, Integer.valueOf(0)).withProperty(GENERATION, Integer.valueOf(1)));
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/*  70 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */   protected boolean canSilkHarvest()
/*     */   {
/*  75 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
/*     */   {
/*  81 */     List<ItemStack> ret = new ArrayList();
/*     */     
/*  83 */     int md = ItemShard.ShardType.getMetaByAspect(this.aspect);
/*     */     
/*  85 */     int count = getGrowth(state) + 1;
/*  86 */     for (int i = 0; i < count; i++)
/*     */     {
/*  88 */       ret.add(new ItemStack(ItemsTC.shard, 1, md));
/*     */     }
/*  90 */     return ret;
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/*  95 */     if ((!worldIn.isRemote) && (rand.nextInt(3 + getGeneration(state)) == 0))
/*     */     {
/*  97 */       int growth = getGrowth(state);
/*  98 */       int generation = getGeneration(state);
/*     */       
/* 100 */       if (AuraHandler.getAuraCurrent(worldIn, pos, this.aspect) < AuraHandler.getAuraBase(worldIn, pos) / 8.0F) {
/* 101 */         if (growth > 0) {
/* 102 */           worldIn.setBlockState(pos, state.withProperty(SIZE, Integer.valueOf(growth - 1)));
/* 103 */           AuraHandler.addRechargeTicket(worldIn, pos, this.aspect, Config.AURABASE / 9);
/*     */         }
/* 105 */         else if (BlockUtils.isBlockTouching(worldIn, pos, this)) {
/* 106 */           worldIn.setBlockToAir(pos);
/* 107 */           AuraHandler.addRechargeTicket(worldIn, pos, this.aspect, Config.AURABASE / 9);
/* 108 */           AuraHandler.addRechargeTicket(worldIn, pos, Aspect.FLUX, 1);
/*     */         }
/*     */         
/*     */       }
/* 112 */       else if (AuraHandler.getAuraCurrent(worldIn, pos, this.aspect) >= AuraHandler.getAuraBase(worldIn, pos) + Config.AURABASE / 16) {
/* 113 */         if ((growth < 3) && (growth < 5 - generation + pos.toLong() % 3L)) {
/* 114 */           if (AuraHandler.drainAura(worldIn, pos, this.aspect, Config.AURABASE / 8))
/*     */           {
/* 116 */             worldIn.setBlockState(pos, state.withProperty(SIZE, Integer.valueOf(growth + 1)));
/*     */           }
/*     */           
/*     */         }
/* 120 */         else if (generation < 4) {
/* 121 */           BlockPos p2 = spreadCrystal(worldIn, pos);
/* 122 */           if ((p2 != null) && (AuraHandler.drainAura(worldIn, pos, this.aspect, Config.AURABASE / 8))) {
/* 123 */             if (rand.nextInt(8) == 0) generation--;
/* 124 */             worldIn.setBlockState(p2, getDefaultState().withProperty(GENERATION, Integer.valueOf(generation + 1)));
/*     */           }
/*     */           
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 131 */       else if ((this.aspect != Aspect.FLUX) && (AuraHandler.getAuraCurrent(worldIn, pos, Aspect.FLUX) > AuraHandler.getAuraCurrent(worldIn, pos, this.aspect)) && (AuraHandler.getAuraCurrent(worldIn, pos, Aspect.FLUX) > AuraHandler.getAuraBase(worldIn, pos) / 2))
/*     */       {
/*     */ 
/* 134 */         if (AuraHandler.drainAura(worldIn, pos, Aspect.FLUX, growth + 1)) {
/* 135 */           worldIn.setBlockState(pos, BlocksTC.crystalTaint.getStateFromMeta(getMetaFromState(state)));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static BlockPos spreadCrystal(World world, BlockPos pos) {
/* 142 */     int xx = pos.getX() + world.rand.nextInt(3) - 1;
/* 143 */     int yy = pos.getY() + world.rand.nextInt(3) - 1;
/* 144 */     int zz = pos.getZ() + world.rand.nextInt(3) - 1;
/*     */     
/* 146 */     BlockPos t = new BlockPos(xx, yy, zz);
/*     */     
/* 148 */     if (t.equals(pos)) { return null;
/*     */     }
/* 150 */     IBlockState bs = world.getBlockState(t);
/* 151 */     Material bm = bs.getBlock().getMaterial();
/*     */     
/* 153 */     if ((!bm.isLiquid()) && ((world.isAirBlock(t)) || (bs.getBlock().isReplaceable(world, t))) && (world.rand.nextInt(16) == 0) && (BlockUtils.isBlockTouching(world, t, Material.rock, true)))
/*     */     {
/* 155 */       return t;
/*     */     }
/* 157 */     return null;
/*     */   }
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/* 162 */     if (!BlockUtils.isBlockTouching(worldIn, pos, Material.rock, true)) {
/* 163 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 164 */       worldIn.setBlockToAir(pos);
/*     */     }
/* 166 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */   }
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing o) {
/* 170 */     return false;
/*     */   }
/*     */   
/*     */   private boolean drawAt(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 174 */     Block b = worldIn.getBlockState(pos).getBlock();
/* 175 */     return (b.getMaterial() == Material.rock) && (b.isSideSolid(worldIn, pos, side.getOpposite()));
/*     */   }
/*     */   
/*     */ 
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, BlockPos pos)
/*     */   {
/* 181 */     IBlockState state = getExtendedState(iblockaccess.getBlockState(pos), iblockaccess, pos);
/* 182 */     if ((state instanceof IExtendedBlockState)) {
/* 183 */       IExtendedBlockState es = (IExtendedBlockState)state;
/* 184 */       int c = 0;
/* 185 */       if (((Boolean)es.getValue(UP)).booleanValue()) c++;
/* 186 */       if (((Boolean)es.getValue(DOWN)).booleanValue()) c++;
/* 187 */       if (((Boolean)es.getValue(EAST)).booleanValue()) c++;
/* 188 */       if (((Boolean)es.getValue(WEST)).booleanValue()) c++;
/* 189 */       if (((Boolean)es.getValue(SOUTH)).booleanValue()) c++;
/* 190 */       if (((Boolean)es.getValue(NORTH)).booleanValue()) c++;
/* 191 */       if (c > 1) {
/* 192 */         setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       } else {
/* 194 */         if (((Boolean)es.getValue(UP)).booleanValue()) {
/* 195 */           setBlockBounds(0.125F, 0.5F, 0.125F, 0.875F, 1.0F, 0.875F);
/*     */         }
/* 197 */         if (((Boolean)es.getValue(DOWN)).booleanValue()) {
/* 198 */           setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 0.5F, 0.875F);
/*     */         }
/* 200 */         if (((Boolean)es.getValue(EAST)).booleanValue()) {
/* 201 */           setBlockBounds(0.5F, 0.125F, 0.125F, 1.0F, 0.875F, 0.875F);
/*     */         }
/* 203 */         if (((Boolean)es.getValue(WEST)).booleanValue()) {
/* 204 */           setBlockBounds(0.0F, 0.125F, 0.125F, 0.5F, 0.875F, 0.875F);
/*     */         }
/* 206 */         if (((Boolean)es.getValue(SOUTH)).booleanValue()) {
/* 207 */           setBlockBounds(0.125F, 0.125F, 0.5F, 0.875F, 0.875F, 1.0F);
/*     */         }
/* 209 */         if (((Boolean)es.getValue(NORTH)).booleanValue()) {
/* 210 */           setBlockBounds(0.125F, 0.125F, 0.0F, 0.875F, 0.875F, 0.5F);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/* 225 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/* 231 */     return false;
/*     */   }
/*     */   
/*     */   public int getLightValue(IBlockAccess world, BlockPos pos)
/*     */   {
/* 236 */     IBlockState st = world.getBlockState(pos);
/* 237 */     return 2 + (st.getBlock().getMetaFromState(st) & 0x3) * 3;
/*     */   }
/*     */   
/*     */   public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 242 */     IBlockState bs = worldIn.getBlockState(pos);
/* 243 */     int i = worldIn.getCombinedLight(pos, bs.getBlock().getLightValue(worldIn, pos));
/* 244 */     int j = 180;
/* 245 */     int k = i & 0xFF;
/* 246 */     int l = j & 0xFF;
/* 247 */     int i1 = i >> 16 & 0xFF;
/* 248 */     int j1 = j >> 16 & 0xFF;
/* 249 */     return (k > l ? k : l) | (i1 > j1 ? i1 : j1) << 16;
/*     */   }
/*     */   
/*     */   public int getRenderColor(IBlockState state)
/*     */   {
/* 254 */     return this.aspect.getColor();
/*     */   }
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
/*     */   {
/* 259 */     return this.aspect.getColor();
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 265 */     IProperty[] listedProperties = { SIZE, GENERATION };
/* 266 */     IUnlistedProperty[] unlistedProperties = { UP, DOWN, NORTH, EAST, WEST, SOUTH, SEED };
/* 267 */     return new ExtendedBlockState(this, listedProperties, unlistedProperties);
/*     */   }
/*     */   
/*     */   public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
/*     */   {
/* 272 */     if ((state instanceof IExtendedBlockState)) {
/* 273 */       IExtendedBlockState retval = (IExtendedBlockState)state;
/* 274 */       return retval.withProperty(UP, Boolean.valueOf(drawAt(world, pos.up(), EnumFacing.UP))).withProperty(DOWN, Boolean.valueOf(drawAt(world, pos.down(), EnumFacing.DOWN))).withProperty(NORTH, Boolean.valueOf(drawAt(world, pos.north(), EnumFacing.NORTH))).withProperty(EAST, Boolean.valueOf(drawAt(world, pos.east(), EnumFacing.EAST))).withProperty(SOUTH, Boolean.valueOf(drawAt(world, pos.south(), EnumFacing.SOUTH))).withProperty(WEST, Boolean.valueOf(drawAt(world, pos.west(), EnumFacing.WEST))).withProperty(SEED, Integer.valueOf(Math.abs(pos.hashCode() % 8)));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 283 */     return state;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 289 */     return state;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 305 */     return getDefaultState().withProperty(SIZE, Integer.valueOf(meta & 0x3)).withProperty(GENERATION, Integer.valueOf(1 + (meta >> 2)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 313 */     int i = 0;
/* 314 */     i |= ((Integer)state.getValue(SIZE)).intValue();
/* 315 */     i |= ((Integer)state.getValue(GENERATION)).intValue() - 1 << 2;
/* 316 */     return i;
/*     */   }
/*     */   
/*     */   public int getGrowth(IBlockState state) {
/* 320 */     return getMetaFromState(state) & 0x3;
/*     */   }
/*     */   
/*     */   public int getGeneration(IBlockState state) {
/* 324 */     return 1 + (getMetaFromState(state) >> 2);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
/*     */   {
/* 331 */     list.add(new ItemStack(itemIn, 1, 0));
/*     */   }
/*     */   
/*     */   public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
/*     */   {
/* 336 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
/*     */   {
/* 341 */     return (BlockUtils.isBlockTouching(worldIn, pos, Material.rock, true)) && (super.canPlaceBlockAt(worldIn, pos));
/*     */   }
/*     */   
/*     */   public boolean onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, BlockPos pos, EnumFacing side)
/*     */   {
/* 346 */     if ((!world.isRemote) && (ResearchHelper.isResearchComplete(player.getName(), "CRYSTALFARMER"))) {
/* 347 */       IBlockState state = world.getBlockState(pos);
/* 348 */       world.playAuxSFX(2001, pos, Block.getStateId(state));
/* 349 */       int growth = getGrowth(state);
/* 350 */       if (growth > 0) {
/* 351 */         world.setBlockState(pos, state.withProperty(SIZE, Integer.valueOf(growth - 1)));
/*     */       } else {
/* 353 */         world.setBlockToAir(pos);
/*     */       }
/* 355 */       int md = ItemShard.ShardType.getMetaByAspect(this.aspect);
/* 356 */       player.dropItem(new ItemStack(ItemsTC.shard, 1, md), false, true);
/* 357 */       return true;
/*     */     }
/* 359 */     return false;
/*     */   }
/*     */   
/*     */   public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}
/*     */   
/*     */   public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\ore\BlockCrystal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */