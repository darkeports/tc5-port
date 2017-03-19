/*     */ package thaumcraft.common.lib.world.biomes;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockTallGrass.EnumType;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.biome.BiomeGenBase.Height;
/*     */ import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*     */ import net.minecraft.world.gen.feature.WorldGenBlockBlob;
/*     */ import net.minecraft.world.gen.feature.WorldGenTallGrass;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.monster.EntityPech;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ import thaumcraft.common.lib.world.objects.WorldGenBigMagicTree;
/*     */ import thaumcraft.common.lib.world.objects.WorldGenGreatwoodTrees;
/*     */ 
/*     */ public class BiomeGenMagicalForest extends BiomeGenBase
/*     */ {
/*     */   protected WorldGenBigMagicTree bigTree;
/*  37 */   private static final WorldGenBlockBlob blobs = new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0);
/*     */   
/*     */   public BiomeGenMagicalForest(int par1)
/*     */   {
/*  41 */     super(par1);
/*  42 */     this.bigTree = new WorldGenBigMagicTree(false);
/*     */     
/*  44 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 2, 1, 3));
/*  45 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 2, 1, 3));
/*     */     
/*  47 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 3, 1, 1));
/*  48 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 3, 1, 1));
/*     */     
/*  50 */     if (Config.spawnPech)
/*  51 */       this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityPech.class, 10, 1, 2));
/*  52 */     if (Config.spawnWisp) {
/*  53 */       this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(thaumcraft.common.entities.monster.EntityWisp.class, 10, 1, 2));
/*     */     }
/*  55 */     this.theBiomeDecorator.treesPerChunk = 2;
/*  56 */     this.theBiomeDecorator.flowersPerChunk = 10;
/*  57 */     this.theBiomeDecorator.grassPerChunk = 12;
/*  58 */     this.theBiomeDecorator.waterlilyPerChunk = 6;
/*  59 */     this.theBiomeDecorator.mushroomsPerChunk = 6;
/*  60 */     setTemperatureRainfall(0.7F, 0.6F);
/*  61 */     setHeight(new BiomeGenBase.Height(0.2F, 0.2F));
/*  62 */     setBiomeName("Magical Forest");
/*  63 */     setColor(Config.blueBiome ? 6728396 : 6747307);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldGenAbstractTree genBigTreeChance(Random par1Random)
/*     */   {
/*  72 */     return par1Random.nextInt(10) == 0 ? new WorldGenGreatwoodTrees(false, par1Random.nextInt(8) == 0) : par1Random.nextInt(14) == 0 ? new thaumcraft.common.lib.world.objects.WorldGenSilverwoodTrees(false, 8, 5) : this.bigTree;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
/*     */   {
/*  81 */     return par1Random.nextInt(4) == 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getGrassColorAtPos(BlockPos p_180627_1_)
/*     */   {
/*  90 */     return Config.blueBiome ? 6728396 : 5635969;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getFoliageColorAtPos(BlockPos p_180625_1_)
/*     */   {
/*  97 */     return Config.blueBiome ? 7851246 : 6750149;
/*     */   }
/*     */   
/*     */   public int getWaterColorMultiplier()
/*     */   {
/* 102 */     return 30702;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void decorate(World world, Random random, BlockPos pos)
/*     */   {
/* 111 */     for (int a = 0; a < 3; a++) {
/* 112 */       BlockPos pp = new BlockPos(pos);
/* 113 */       pp = pp.add(4 + random.nextInt(8), 0, 4 + random.nextInt(8));
/* 114 */       pp = world.getHeight(pp);
/*     */       
/* 116 */       while ((pp.getY() > 30) && (world.getBlockState(pp).getBlock() != Blocks.grass)) {
/* 117 */         pp = pp.down();
/*     */       }
/* 119 */       Block l1 = world.getBlockState(pp).getBlock();
/* 120 */       if (l1 == Blocks.grass)
/*     */       {
/* 122 */         world.setBlockState(pp, BlocksTC.grassAmbient.getDefaultState(), 2);
/* 123 */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 128 */     int k = random.nextInt(3);
/*     */     
/* 130 */     for (int l = 0; l < k; l++)
/*     */     {
/* 132 */       BlockPos p2 = new BlockPos(pos);
/* 133 */       p2 = p2.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8);
/* 134 */       p2 = world.getHeight(p2);
/* 135 */       blobs.generate(world, random, p2);
/*     */     }
/*     */     
/* 138 */     for (k = 0; k < 4; k++)
/*     */     {
/* 140 */       for (l = 0; l < 4; l++)
/*     */       {
/* 142 */         if (random.nextInt(40) == 0)
/*     */         {
/* 144 */           BlockPos p2 = new BlockPos(pos);
/* 145 */           p2 = p2.add(k * 4 + 1 + 8 + random.nextInt(3), 0, l * 4 + 1 + 8 + random.nextInt(3));
/* 146 */           p2 = world.getHeight(p2);
/* 147 */           WorldGenBigMushroom worldgenbigmushroom = new WorldGenBigMushroom();
/* 148 */           worldgenbigmushroom.generate(world, random, p2);
/*     */         }
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 154 */       super.decorate(world, random, pos);
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*     */ 
/* 159 */     for (int a = 0; a < 8; a++) {
/* 160 */       BlockPos p2 = new BlockPos(pos);
/* 161 */       p2 = p2.add(random.nextInt(16), 0, random.nextInt(16));
/* 162 */       p2 = world.getHeight(p2);
/*     */       
/* 164 */       while ((p2.getY() > 50) && (world.getBlockState(p2).getBlock() != Blocks.grass)) {
/* 165 */         p2 = p2.down();
/*     */       }
/* 167 */       Block l2 = world.getBlockState(p2).getBlock();
/* 168 */       if ((l2 == Blocks.grass) && (world.getBlockState(p2.up()).getBlock().isReplaceable(world, p2.up())) && (isBlockAdjacentToWood(world, p2.up())))
/*     */       {
/*     */ 
/* 171 */         world.setBlockState(p2.up(), BlocksTC.vishroom.getDefaultState(), 2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isBlockAdjacentToWood(IBlockAccess world, BlockPos pos)
/*     */   {
/* 178 */     int count = 0;
/* 179 */     for (int xx = -1; xx <= 1; xx++) {
/* 180 */       for (int yy = -1; yy <= 1; yy++) {
/* 181 */         for (int zz = -1; zz <= 1; zz++)
/* 182 */           if (((xx != 0) || (yy != 0) || (zz != 0)) && 
/* 183 */             (Utils.isWoodLog(world, pos.add(xx, yy, zz)))) return true;
/*     */       }
/*     */     }
/* 186 */     return false;
/*     */   }
/*     */   
/*     */   public BiomeGenBase createMutation()
/*     */   {
/* 191 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public net.minecraft.block.BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos)
/*     */   {
/* 197 */     double d0 = MathHelper.clamp_double((1.0D + GRASS_COLOR_NOISE.func_151601_a(pos.getX() / 48.0D, pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
/* 198 */     return net.minecraft.block.BlockFlower.EnumFlowerType.values()[((int)(d0 * net.minecraft.block.BlockFlower.EnumFlowerType.values().length))];
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\biomes\BiomeGenMagicalForest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */