/*    */ package thaumcraft.common.lib.world.dim;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockStaticLiquid;
/*    */ import net.minecraft.entity.IEntityLivingData;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.common.entities.monster.EntityEldritchGuardian;
/*    */ import thaumcraft.common.entities.monster.cult.EntityCultistPortalLesser;
/*    */ 
/*    */ public class GenPassageRoom extends GenCommon
/*    */ {
/*    */   static void generateRoom(World world, Random random, int cx, int cz, int y, Cell cell)
/*    */   {
/* 20 */     int x = cx * 16;
/* 21 */     int z = cz * 16;
/*    */     
/* 23 */     if (random.nextBoolean()) {
/* 24 */       GenCommon.generateChamberA(world, random, cx, cz, y, cell);
/*    */     } else {
/* 26 */       GenCommon.generateChamberB(world, random, cx, cz, y, cell);
/*    */     }
/*    */     
/* 29 */     GenCommon.generateConnections(world, random, cx, cz, y, cell, 3, true);
/*    */     
/* 31 */     int feature = random.nextInt(8);
/*    */     
/* 33 */     switch (feature) {
/*    */     default: 
/*    */       break;
/* 36 */     case 0:  for (int qq = 0; qq < 16; qq++) {
/* 37 */         world.setBlockState(new BlockPos(x + 8 + MathHelper.getRandomIntegerInRange(world.rand, -3, 3), y + 2 + qq / 2, z + 8 + MathHelper.getRandomIntegerInRange(world.rand, -3, 3)), BlocksTC.taintDust.getDefaultState().withProperty(net.minecraftforge.fluids.BlockFluidBase.LEVEL, Integer.valueOf(3)));
/*    */       }
/*    */       
/*    */ 
/*    */ 
/* 42 */       break;
/* 43 */     case 1:  generateMiniBossGuardians(world, random, x, y, z); break;
/* 44 */     case 2:  genObelisk(world, new BlockPos(x + 8, y + 4, z + 8)); break;
/*    */     case 3: case 4: case 5: 
/* 46 */       int q = random.nextInt(4);
/* 47 */       for (int qq = -1; qq <= 1; qq++) { for (int ww = -1; ww <= 1; ww++) {
/* 48 */           decoCommon.remove(new BlockPos(x + 8 + qq, y + 1, z + 8 + ww));
/* 49 */           crabSpawner.remove(new BlockPos(x + 8 + qq, y + 1, z + 8 + ww));
/* 50 */           decoUrn.remove(new BlockPos(x + 8 + qq, y + 1, z + 8 + ww));
/* 51 */           world.setBlockState(new BlockPos(x + 8 + qq, y + 1, z + 8 + ww), q == 2 ? BlocksTC.purifyingFluid.getDefaultState() : q == 1 ? BlocksTC.liquidDeath.getDefaultState() : q == 0 ? net.minecraft.init.Blocks.lava.getDefaultState() : net.minecraft.init.Blocks.water.getDefaultState());
/*    */         }
/*    */       }
/*    */       
/*    */ 
/*    */ 
/* 57 */       break;
/* 58 */     case 6:  generateMiniBossPortal(world, random, x, y, z);
/*    */     }
/*    */   }
/*    */   
/*    */   private static void generateMiniBossPortal(World world, Random random, int x, int y, int z) {
/* 63 */     EntityCultistPortalLesser eg = new EntityCultistPortalLesser(world);
/* 64 */     eg.setPosition(x + 8.5D, y + 2, z + 8.5D);
/* 65 */     eg.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(eg)), (IEntityLivingData)null);
/* 66 */     world.spawnEntityInWorld(eg);
/*    */   }
/*    */   
/*    */   private static void generateMiniBossGuardians(World world, Random random, int x, int y, int z)
/*    */   {
/* 71 */     int zz = 1 + (world.getDifficulty() == EnumDifficulty.NORMAL ? 1 : world.getDifficulty() == EnumDifficulty.HARD ? 2 : 0);
/* 72 */     if (random.nextFloat() < zz / 10.0F) {
/* 73 */       genObelisk(world, new BlockPos(x + 8, y + 4, z + 8));
/*    */     }
/* 75 */     world.setBlockState(new BlockPos(x + 8, y + 2, z + 8), BlocksTC.lootUrn.getStateFromMeta(2));
/* 76 */     for (int qq = 0; qq < zz; qq++)
/*    */     {
/* 78 */       EntityEldritchGuardian eg = new EntityEldritchGuardian(world);
/* 79 */       double i1 = x + 8.5D + MathHelper.getRandomIntegerInRange(world.rand, 1, 3) * MathHelper.getRandomIntegerInRange(world.rand, -1, 1);
/* 80 */       double j1 = y + 3;
/* 81 */       double k1 = z + 8.5D + MathHelper.getRandomIntegerInRange(world.rand, 1, 3) * MathHelper.getRandomIntegerInRange(world.rand, -1, 1);
/*    */       
/* 83 */       eg.setPosition(i1, j1, k1);
/* 84 */       eg.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(eg)), (IEntityLivingData)null);
/* 85 */       eg.setHomePosAndDistance(new BlockPos(x + 8, y + 2, z + 8), 16);
/* 86 */       world.spawnEntityInWorld(eg);
/* 87 */       if (qq == 0) thaumcraft.common.lib.utils.EntityUtils.makeChampion(eg, true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\dim\GenPassageRoom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */