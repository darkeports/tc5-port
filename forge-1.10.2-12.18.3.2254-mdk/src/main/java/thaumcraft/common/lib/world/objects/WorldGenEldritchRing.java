/*     */ package thaumcraft.common.lib.world.objects;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.lib.utils.TCVec3;
/*     */ import thaumcraft.common.lib.world.dim.GenCommon;
/*     */ import thaumcraft.common.lib.world.dim.MazeHandler;
/*     */ import thaumcraft.common.tiles.misc.TileBanner;
/*     */ import thaumcraft.common.tiles.misc.TileEldritchAltar;
/*     */ 
/*     */ public class WorldGenEldritchRing extends WorldGenerator
/*     */ {
/*     */   public int chunkX;
/*     */   public int chunkZ;
/*     */   public int width;
/*     */   
/*     */   protected Block[] GetValidSpawnBlocks()
/*     */   {
/*  28 */     return new Block[] { Blocks.stone, Blocks.sand, Blocks.packed_ice, Blocks.grass, Blocks.gravel, Blocks.dirt };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean locationIsValidSpawn(World world, BlockPos pos)
/*     */   {
/*  40 */     int distanceToAir = 0;
/*  41 */     Block checkID = world.getBlockState(pos).getBlock();
/*     */     
/*  43 */     while ((checkID != Blocks.air) && (!world.isAirBlock(pos))) {
/*  44 */       distanceToAir++;
/*  45 */       checkID = world.getBlockState(pos.up(distanceToAir)).getBlock();
/*     */     }
/*     */     
/*  48 */     if (distanceToAir > 2) {
/*  49 */       return false;
/*     */     }
/*     */     
/*  52 */     int j = distanceToAir - 1;
/*     */     
/*  54 */     Block blockID = world.getBlockState(pos.up(j)).getBlock();
/*  55 */     Block blockIDAbove = world.getBlockState(pos.up(j + 1)).getBlock();
/*  56 */     Block blockIDBelow = world.getBlockState(pos.up(j - 1)).getBlock();
/*  57 */     for (Block x : GetValidSpawnBlocks()) {
/*  58 */       if (blockIDAbove != Blocks.air) {
/*  59 */         return false;
/*     */       }
/*  61 */       if (blockID == x)
/*  62 */         return true;
/*  63 */       if (((blockID == Blocks.snow_layer) || (blockID == Blocks.tallgrass)) && (blockIDBelow == x)) {
/*  64 */         return true;
/*     */       }
/*     */     }
/*  67 */     return false;
/*     */   }
/*     */   
/*  70 */   public int height = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean generate(World world, Random rand, BlockPos pos)
/*     */   {
/*  76 */     if ((!locationIsValidSpawn(world, pos.add(-3, 0, -3))) || (!locationIsValidSpawn(world, pos)) || (!locationIsValidSpawn(world, pos.add(3, 0, 0))) || (!locationIsValidSpawn(world, pos.add(3, 0, 3))) || (!locationIsValidSpawn(world, pos.add(0, 0, 3))) || (MazeHandler.mazesInRange(this.chunkX, this.chunkZ, this.width, this.height)))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  84 */       return false;
/*     */     }
/*     */     
/*  87 */     IBlockState replaceBlock = world.getBiomeGenForCoords(pos).topBlock;
/*  88 */     int i = pos.getX();
/*  89 */     int k = pos.getZ();
/*     */     
/*  91 */     for (int x = i - 3; x <= i + 3; x++) {
/*  92 */       for (int z = k - 3; z <= k + 3; z++) {
/*  93 */         BlockPos p = new BlockPos(x, pos.getY(), z);
/*  94 */         for (int q = -3; q < 5; q++) {
/*  95 */           Block bb = world.getBlockState(p.up(q)).getBlock();
/*  96 */           TCVec3 v1 = TCVec3.createVectorHelper(pos);
/*  97 */           TCVec3 v2 = TCVec3.createVectorHelper(p.up(q));
/*  98 */           if ((q <= 0) && (v1.squareDistanceTo(v2) < 15.0D)) {
/*  99 */             if (rand.nextInt(3) == 0) {
/* 100 */               world.setBlockState(p.up(q), BlocksTC.stone.getStateFromMeta(4));
/*     */             } else {
/* 102 */               world.setBlockState(p.up(q), Blocks.obsidian.getDefaultState());
/*     */             }
/* 104 */           } else if (q > 0) {
/* 105 */             world.setBlockToAir(p.up(q));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 110 */     for (int x = i - 3; x <= i + 3; x++) {
/* 111 */       for (int z = k - 3; z <= k + 3; z++) {
/* 112 */         if (((x != i - 3) && (x != i + 3)) || ((z != k - 3) && (z != k + 3))) {
/* 113 */           BlockPos p = new BlockPos(x, pos.getY(), z);
/*     */           
/* 115 */           if ((x == i) && (z == k))
/*     */           {
/* 117 */             world.setBlockState(p.up(), BlocksTC.eldritch.getDefaultState());
/* 118 */             world.setBlockState(p, BlocksTC.stone.getStateFromMeta(4));
/* 119 */             int r = rand.nextInt(10);
/* 120 */             TileEntity te = world.getTileEntity(p.up());
/* 121 */             if ((te != null) && ((te instanceof TileEldritchAltar))) {
/* 122 */               switch (r) {
/*     */               case 1: case 2: case 3: case 4: 
/* 124 */                 ((TileEldritchAltar)te).setSpawner(true);
/* 125 */                 ((TileEldritchAltar)te).setSpawnType((byte)0);
/* 126 */                 for (EnumFacing dir : EnumFacing.HORIZONTALS)
/*     */                 {
/* 128 */                   BlockPos pp = p.add(-dir.getFrontOffsetX() * 3, 1, dir.getFrontOffsetZ() * 3);
/* 129 */                   world.setBlockState(pp, BlocksTC.banner.getStateFromMeta(1));
/* 130 */                   te = world.getTileEntity(pp);
/* 131 */                   if ((te != null) && ((te instanceof TileBanner)))
/*     */                   {
/* 133 */                     int face = 0;
/*     */                     
/* 135 */                     switch (dir.ordinal()) {
/* 136 */                     case 2:  face = 8; break;
/* 137 */                     case 3:  face = 0; break;
/* 138 */                     case 4:  face = 12; break;
/* 139 */                     case 5:  face = 4; break;
/*     */                     }
/* 141 */                     ((TileBanner)te).setBannerFacing((byte)face);
/*     */                   }
/*     */                 }
/* 144 */                 break;
/*     */               case 6: case 7: 
/* 146 */                 ((TileEldritchAltar)te).setSpawner(true);
/* 147 */                 ((TileEldritchAltar)te).setSpawnType((byte)1);
/*     */               }
/*     */               
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 156 */             GenCommon.genObelisk(world, p.up(3));
/*     */ 
/*     */ 
/*     */           }
/* 160 */           else if (((x != i - 3) && (x != i + 3)) || (((Math.abs((z - k) % 2) == 1) || (((z == k - 3) || (z == k + 3)) && (Math.abs((x - i) % 2) == 1))) && (Math.abs(x - i) != Math.abs(z - k)))) {
/* 161 */             world.setBlockState(p.up(), BlocksTC.eldritch.getStateFromMeta(1));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 167 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\objects\WorldGenEldritchRing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */