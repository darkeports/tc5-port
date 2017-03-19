/*     */ package thaumcraft.common.lib.world.objects;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSapling;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
/*     */ 
/*     */ public class WorldGenSilverwoodTrees extends WorldGenAbstractTree
/*     */ {
/*     */   private final int minTreeHeight;
/*     */   private final int randomTreeHeight;
/*  21 */   boolean worldgen = false;
/*     */   
/*     */   public WorldGenSilverwoodTrees(boolean doBlockNotify, int minTreeHeight, int randomTreeHeight)
/*     */   {
/*  25 */     super(doBlockNotify);
/*     */     
/*  27 */     this.worldgen = (!doBlockNotify);
/*  28 */     this.minTreeHeight = minTreeHeight;
/*  29 */     this.randomTreeHeight = randomTreeHeight;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean generate(World world, Random random, BlockPos pos)
/*     */   {
/*  37 */     int height = random.nextInt(this.randomTreeHeight) + this.minTreeHeight;
/*     */     
/*  39 */     boolean flag = true;
/*     */     
/*  41 */     int x = pos.getX();
/*  42 */     int y = pos.getY();
/*  43 */     int z = pos.getZ();
/*     */     
/*  45 */     if ((y >= 1) && (y + height + 1 <= 256))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*  50 */       for (int i1 = y; i1 <= y + 1 + height; i1++)
/*     */       {
/*  52 */         byte spread = 1;
/*     */         
/*  54 */         if (i1 == y)
/*     */         {
/*  56 */           spread = 0;
/*     */         }
/*     */         
/*  59 */         if (i1 >= y + 1 + height - 2)
/*     */         {
/*  61 */           spread = 3;
/*     */         }
/*     */         
/*  64 */         for (int j1 = x - spread; (j1 <= x + spread) && (flag); j1++)
/*     */         {
/*  66 */           for (int k1 = z - spread; (k1 <= z + spread) && (flag); k1++)
/*     */           {
/*  68 */             if ((i1 >= 0) && (i1 < 256))
/*     */             {
/*  70 */               Block block = world.getBlockState(new BlockPos(j1, i1, k1)).getBlock();
/*     */               
/*  72 */               if ((!block.isAir(world, new BlockPos(j1, i1, k1))) && (!block.isLeaves(world, new BlockPos(j1, i1, k1))) && (!block.isReplaceable(world, new BlockPos(j1, i1, k1))))
/*     */               {
/*     */ 
/*  75 */                 if (i1 > y)
/*     */                 {
/*  77 */                   flag = false;
/*     */                 }
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/*  83 */               flag = false;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  89 */       if (!flag)
/*     */       {
/*  91 */         return false;
/*     */       }
/*     */       
/*     */ 
/*  95 */       Block block1 = world.getBlockState(new BlockPos(x, y - 1, z)).getBlock();
/*     */       
/*  97 */       boolean isSoil = block1.canSustainPlant(world, new BlockPos(x, y - 1, z), EnumFacing.UP, (BlockSapling)Blocks.sapling);
/*  98 */       if ((isSoil) && (y < 256 - height - 1))
/*     */       {
/* 100 */         block1.onPlantGrow(world, new BlockPos(x, y - 1, z), new BlockPos(x, y, z));
/* 101 */         int start = y + height - 5;
/* 102 */         int end = y + height + 3 + random.nextInt(3);
/*     */         
/*     */ 
/* 105 */         for (int k2 = start; k2 <= end; k2++)
/*     */         {
/* 107 */           int cty = MathHelper.clamp_int(k2, y + height - 3, y + height);
/*     */           
/* 109 */           for (int xx = x - 5; xx <= x + 5; xx++)
/*     */           {
/* 111 */             for (int zz = z - 5; zz <= z + 5; zz++)
/*     */             {
/* 113 */               double d3 = xx - x;
/* 114 */               double d4 = k2 - cty;
/* 115 */               double d5 = zz - z;
/* 116 */               double dist = d3 * d3 + d4 * d4 + d5 * d5;
/*     */               
/* 118 */               if ((dist < 10 + random.nextInt(8)) && (world.getBlockState(new BlockPos(xx, k2, zz)).getBlock().canBeReplacedByLeaves(world, new BlockPos(xx, k2, zz))))
/*     */               {
/*     */ 
/* 121 */                 setBlockAndNotifyAdequately(world, new BlockPos(xx, k2, zz), BlocksTC.leaf.getStateFromMeta(1));
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 130 */         for (k2 = 0; k2 < height; k2++)
/*     */         {
/* 132 */           Block block2 = world.getBlockState(new BlockPos(x, y + k2, z)).getBlock();
/* 133 */           if ((block2.isAir(world, new BlockPos(x, y + k2, z))) || (block2.isLeaves(world, new BlockPos(x, y + k2, z))) || (block2.isReplaceable(world, new BlockPos(x, y + k2, z))))
/*     */           {
/*     */ 
/* 136 */             setBlockAndNotifyAdequately(world, new BlockPos(x, y + k2, z), BlocksTC.log.getStateFromMeta(4));
/* 137 */             setBlockAndNotifyAdequately(world, new BlockPos(x - 1, y + k2, z), BlocksTC.log.getStateFromMeta(4));
/* 138 */             setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + k2, z), BlocksTC.log.getStateFromMeta(4));
/* 139 */             setBlockAndNotifyAdequately(world, new BlockPos(x, y + k2, z - 1), BlocksTC.log.getStateFromMeta(4));
/* 140 */             setBlockAndNotifyAdequately(world, new BlockPos(x, y + k2, z + 1), BlocksTC.log.getStateFromMeta(4));
/*     */           }
/*     */         }
/*     */         
/* 144 */         if (random.nextFloat() < 0.3F) {
/* 145 */           int q = height / 2 - 1 + random.nextInt(3);
/* 146 */           ThaumcraftWorldGenerator.spawnNode(world, new BlockPos(x, y + q, z), 3, 0.33F);
/*     */         }
/*     */         
/* 149 */         setBlockAndNotifyAdequately(world, new BlockPos(x, y + k2, z), BlocksTC.log.getStateFromMeta(4));
/* 150 */         setBlockAndNotifyAdequately(world, new BlockPos(x - 1, y, z - 1), BlocksTC.log.getStateFromMeta(4));
/* 151 */         setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y, z + 1), BlocksTC.log.getStateFromMeta(4));
/* 152 */         setBlockAndNotifyAdequately(world, new BlockPos(x - 1, y, z + 1), BlocksTC.log.getStateFromMeta(4));
/* 153 */         setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y, z - 1), BlocksTC.log.getStateFromMeta(4));
/* 154 */         if (random.nextInt(3) != 0) setBlockAndNotifyAdequately(world, new BlockPos(x - 1, y + 1, z - 1), BlocksTC.log.getStateFromMeta(4));
/* 155 */         if (random.nextInt(3) != 0) setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + 1, z + 1), BlocksTC.log.getStateFromMeta(4));
/* 156 */         if (random.nextInt(3) != 0) setBlockAndNotifyAdequately(world, new BlockPos(x - 1, y + 1, z + 1), BlocksTC.log.getStateFromMeta(4));
/* 157 */         if (random.nextInt(3) != 0) setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + 1, z - 1), BlocksTC.log.getStateFromMeta(4));
/* 158 */         setBlockAndNotifyAdequately(world, new BlockPos(x - 2, y, z), BlocksTC.log.getStateFromMeta(3));
/* 159 */         setBlockAndNotifyAdequately(world, new BlockPos(x + 2, y, z), BlocksTC.log.getStateFromMeta(3));
/* 160 */         setBlockAndNotifyAdequately(world, new BlockPos(x, y, z - 2), BlocksTC.log.getStateFromMeta(5));
/* 161 */         setBlockAndNotifyAdequately(world, new BlockPos(x, y, z + 2), BlocksTC.log.getStateFromMeta(5));
/* 162 */         setBlockAndNotifyAdequately(world, new BlockPos(x - 2, y - 1, z), BlocksTC.log.getStateFromMeta(4));
/* 163 */         setBlockAndNotifyAdequately(world, new BlockPos(x + 2, y - 1, z), BlocksTC.log.getStateFromMeta(4));
/* 164 */         setBlockAndNotifyAdequately(world, new BlockPos(x, y - 1, z - 2), BlocksTC.log.getStateFromMeta(4));
/* 165 */         setBlockAndNotifyAdequately(world, new BlockPos(x, y - 1, z + 2), BlocksTC.log.getStateFromMeta(4));
/* 166 */         setBlockAndNotifyAdequately(world, new BlockPos(x - 1, y + (height - 4), z - 1), BlocksTC.log.getStateFromMeta(4));
/* 167 */         setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + (height - 4), z + 1), BlocksTC.log.getStateFromMeta(4));
/* 168 */         setBlockAndNotifyAdequately(world, new BlockPos(x - 1, y + (height - 4), z + 1), BlocksTC.log.getStateFromMeta(4));
/* 169 */         setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + (height - 4), z - 1), BlocksTC.log.getStateFromMeta(4));
/* 170 */         if (random.nextInt(3) == 0) setBlockAndNotifyAdequately(world, new BlockPos(x - 1, y + (height - 5), z - 1), BlocksTC.log.getStateFromMeta(4));
/* 171 */         if (random.nextInt(3) == 0) setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + (height - 5), z + 1), BlocksTC.log.getStateFromMeta(4));
/* 172 */         if (random.nextInt(3) == 0) setBlockAndNotifyAdequately(world, new BlockPos(x - 1, y + (height - 5), z + 1), BlocksTC.log.getStateFromMeta(4));
/* 173 */         if (random.nextInt(3) == 0) setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + (height - 5), z - 1), BlocksTC.log.getStateFromMeta(4));
/* 174 */         setBlockAndNotifyAdequately(world, new BlockPos(x - 2, y + (height - 4), z), BlocksTC.log.getStateFromMeta(3));
/* 175 */         setBlockAndNotifyAdequately(world, new BlockPos(x + 2, y + (height - 4), z), BlocksTC.log.getStateFromMeta(3));
/* 176 */         setBlockAndNotifyAdequately(world, new BlockPos(x, y + (height - 4), z - 2), BlocksTC.log.getStateFromMeta(5));
/* 177 */         setBlockAndNotifyAdequately(world, new BlockPos(x, y + (height - 4), z + 2), BlocksTC.log.getStateFromMeta(5));
/*     */         
/* 179 */         if (this.worldgen) {
/* 180 */           WorldGenerator flowers = new WorldGenCustomFlowers(BlocksTC.shimmerleaf, 0);
/* 181 */           flowers.generate(world, random, new BlockPos(x, y, z));
/*     */         }
/*     */         
/* 184 */         return true;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 190 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 196 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\objects\WorldGenSilverwoodTrees.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */