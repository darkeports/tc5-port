/*     */ package thaumcraft.common.blocks.basic;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving.SpawnPlacementType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.internal.WeightedRandomLoot;
/*     */ import thaumcraft.api.items.ItemGenericEssentiaContainer;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ import thaumcraft.common.config.Config;
/*     */ 
/*     */ public class BlockStoneTC
/*     */   extends BlockTC
/*     */ {
/*  37 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", StoneType.class);
/*     */   
/*     */   public BlockStoneTC()
/*     */   {
/*  41 */     super(Material.rock);
/*  42 */     setCreativeTab(Thaumcraft.tabTC);
/*  43 */     setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, StoneType.ARCANE));
/*  44 */     setHardness(2.0F);
/*  45 */     setResistance(10.0F);
/*  46 */     setStepSound(Block.soundTypeStone);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
/*     */   {
/*  53 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  61 */     return meta < StoneType.values().length ? getDefaultState().withProperty(VARIANT, StoneType.values()[meta]) : getDefaultState();
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/*  67 */     int meta = ((StoneType)state.getValue(VARIANT)).ordinal();
/*     */     
/*  69 */     return meta;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/*  75 */     return new BlockState(this, new IProperty[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/*  81 */     return new IProperty[] { VARIANT };
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/*  87 */     StoneType type = (StoneType)state.getValue(VARIANT);
/*     */     
/*  89 */     return type.getName() + (fullName ? "_stone" : "");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canCreatureSpawn(IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type)
/*     */   {
/*  97 */     IBlockState state = world.getBlockState(pos);
/*  98 */     if (state.getBlock() != this) return super.canCreatureSpawn(world, pos, type);
/*  99 */     return ((StoneType)state.getValue(VARIANT) == StoneType.ANCIENT_ROCK) || ((StoneType)state.getValue(VARIANT) == StoneType.ANCIENT_DOORWAY) || ((StoneType)state.getValue(VARIANT) == StoneType.GLYPHED) || ((StoneType)state.getValue(VARIANT) == StoneType.BEDROCK) || ((StoneType)state.getValue(VARIANT) == StoneType.ANCIENT_TILE) ? false : super.canCreatureSpawn(world, pos, type);
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
/*     */   public int getLightValue(IBlockAccess world, BlockPos pos)
/*     */   {
/* 112 */     IBlockState state = world.getBlockState(pos);
/* 113 */     if (state.getBlock() != this) return super.getLightValue(world, pos);
/* 114 */     return (StoneType)state.getValue(VARIANT) == StoneType.CRUST_GLOW ? 9 : super.getLightValue(world, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   public float getBlockHardness(World worldIn, BlockPos pos)
/*     */   {
/* 120 */     IBlockState state = worldIn.getBlockState(pos);
/* 121 */     if (state.getBlock() != this) return super.getBlockHardness(worldIn, pos);
/* 122 */     return (StoneType)state.getValue(VARIANT) == StoneType.BEDROCK ? -1.0F : (StoneType)state.getValue(VARIANT) == StoneType.ANCIENT_DOORWAY ? -1.0F : (StoneType)state.getValue(VARIANT) == StoneType.ELDRITCH ? 15.0F : (StoneType)state.getValue(VARIANT) == StoneType.CRUST_GLOW ? 1.0F : (StoneType)state.getValue(VARIANT) == StoneType.POROUS ? 0.5F : (StoneType)state.getValue(VARIANT) == StoneType.CRUST ? 1.0F : (StoneType)state.getValue(VARIANT) == StoneType.ANCIENT_ROCK ? 6.0F : super.getBlockHardness(worldIn, pos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
/*     */   {
/* 134 */     IBlockState state = world.getBlockState(pos);
/* 135 */     if (state.getBlock() != this) return super.getExplosionResistance(world, pos, exploder, explosion);
/* 136 */     return (StoneType)state.getValue(VARIANT) == StoneType.ELDRITCH ? 1000.0F : (StoneType)state.getValue(VARIANT) == StoneType.CRUST_GLOW ? 5.0F : (StoneType)state.getValue(VARIANT) == StoneType.POROUS ? 5.0F : (StoneType)state.getValue(VARIANT) == StoneType.CRUST ? 5.0F : (StoneType)state.getValue(VARIANT) == StoneType.ANCIENT_ROCK ? 20.0F : super.getExplosionResistance(world, pos, exploder, explosion);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 144 */   private static int cc = 0;
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/* 148 */     if ((state.getBlock() == this) && ((StoneType)state.getValue(VARIANT) == StoneType.BEDROCK) && 
/* 149 */       (isBlockExposed(worldIn, pos)) && 
/* 150 */       (cc++ < 10)) {
/* 151 */       worldIn.setBlockState(pos, BlocksTC.vacuum.getDefaultState(), 2);
/* 152 */       worldIn.scheduleUpdate(pos, BlocksTC.vacuum, 1);
/*     */     }
/*     */     
/*     */ 
/* 156 */     cc = 0;
/*     */   }
/*     */   
/*     */   private boolean isBlockExposed(World world, BlockPos pos) {
/* 160 */     for (EnumFacing face : ) {
/* 161 */       if (world.isAirBlock(pos.offset(face))) {
/* 162 */         return true;
/*     */       }
/*     */     }
/* 165 */     return false;
/*     */   }
/*     */   
/* 168 */   static Random r = new Random(System.currentTimeMillis());
/* 169 */   static ArrayList<WeightedRandomLoot> pdrops = null;
/*     */   
/*     */ 
/*     */   public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
/*     */   {
/* 174 */     if ((state.getBlock() == this) && ((StoneType)state.getValue(VARIANT) == StoneType.POROUS)) {
/* 175 */       List<ItemStack> ret = new ArrayList();
/* 176 */       int rr = r.nextInt(15) + fortune;
/* 177 */       if (rr > 13) {
/* 178 */         if ((pdrops == null) || (pdrops.size() <= 0)) createDrops();
/* 179 */         ItemStack s = ((WeightedRandomLoot)WeightedRandom.getRandomItem(r, pdrops)).item.copy();
/* 180 */         ret.add(s);
/*     */       } else {
/* 182 */         ret.add(new ItemStack(Blocks.cobblestone));
/*     */       }
/* 184 */       return ret;
/*     */     }
/* 186 */     return super.getDrops(world, pos, state, fortune);
/*     */   }
/*     */   
/*     */   private void createDrops() {
/* 190 */     pdrops = new ArrayList();
/* 191 */     for (Aspect aspect : Aspect.getCompoundAspects()) {
/* 192 */       ItemStack is = new ItemStack(ItemsTC.crystalEssence);
/* 193 */       ((ItemGenericEssentiaContainer)ItemsTC.crystalEssence).setAspects(is, new AspectList().add(aspect, 1));
/* 194 */       pdrops.add(new WeightedRandomLoot(is, 1));
/*     */     }
/* 196 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.shard, 1, 0), 20));
/* 197 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.shard, 1, 1), 20));
/* 198 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.shard, 1, 2), 20));
/* 199 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.shard, 1, 3), 20));
/* 200 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.shard, 1, 4), 20));
/* 201 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.shard, 1, 5), 20));
/* 202 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.shard, 1, 6), 100));
/* 203 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.shard, 1, 7), 5));
/* 204 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.amber), 20));
/* 205 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 0), 20));
/* 206 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 1), 10));
/* 207 */     pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 6), 10));
/* 208 */     if (Config.foundCopperIngot) pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 2), 10));
/* 209 */     if (Config.foundTinIngot) pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 3), 10));
/* 210 */     if (Config.foundSilverIngot) pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 4), 8));
/* 211 */     if (Config.foundLeadIngot) pdrops.add(new WeightedRandomLoot(new ItemStack(ItemsTC.clusters, 1, 5), 10));
/* 212 */     pdrops.add(new WeightedRandomLoot(new ItemStack(Items.diamond), 6));
/* 213 */     pdrops.add(new WeightedRandomLoot(new ItemStack(Items.emerald), 10));
/* 214 */     pdrops.add(new WeightedRandomLoot(new ItemStack(Items.redstone), 15));
/* 215 */     pdrops.add(new WeightedRandomLoot(new ItemStack(Items.prismarine_crystals), 3));
/* 216 */     pdrops.add(new WeightedRandomLoot(new ItemStack(Items.prismarine_shard), 3));
/* 217 */     pdrops.add(new WeightedRandomLoot(new ItemStack(Items.clay_ball), 30));
/* 218 */     pdrops.add(new WeightedRandomLoot(new ItemStack(Items.quartz), 15));
/*     */   }
/*     */   
/*     */   public static enum StoneType
/*     */     implements IStringSerializable
/*     */   {
/* 224 */     ARCANE, 
/* 225 */     ARCANE_BRICK, 
/* 226 */     ANCIENT, 
/* 227 */     ANCIENT_ROCK, 
/* 228 */     ELDRITCH, 
/* 229 */     ANCIENT_TILE, 
/* 230 */     CRUST, 
/* 231 */     CRUST_GLOW, 
/* 232 */     MATRIX_SPEED, 
/* 233 */     MATRIX_COST, 
/* 234 */     ANCIENT_DOORWAY, 
/* 235 */     GLYPHED, 
/* 236 */     BEDROCK, 
/* 237 */     POROUS;
/*     */     
/*     */     private StoneType() {}
/*     */     
/*     */     public String getName()
/*     */     {
/* 243 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 249 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockStoneTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */