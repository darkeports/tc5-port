/*    */ package thaumcraft.common.lib.world.dim;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.tiles.misc.TileEldritchLock;
/*    */ 
/*    */ public class GenBossRoom extends GenCommon
/*    */ {
/*    */   static void generateRoom(World world, Random random, int cx, int cz, int y, Cell cell)
/*    */   {
/* 14 */     int x = cx * 16;
/* 15 */     int z = cz * 16;
/*    */     
/* 17 */     switch (cell.feature) {
/* 18 */     case 2:  Gen2x2.generateUpperLeft(world, random, cx, cz, 50, cell); break;
/* 19 */     case 3:  Gen2x2.generateUpperRight(world, random, cx, cz, 50, cell); break;
/* 20 */     case 4:  Gen2x2.generateLowerLeft(world, random, cx, cz, 50, cell); break;
/* 21 */     case 5:  Gen2x2.generateLowerRight(world, random, cx, cz, 50, cell);
/*    */     }
/*    */     
/* 24 */     for (int a = 0; a < 7; a++) for (int b = 0; b < 7; b++) {
/* 25 */         int xx = 0;
/* 26 */         int zz = 0;
/* 27 */         EnumFacing dir = EnumFacing.UP;
/* 28 */         if (cell.north) { xx = x + 5 + a;zz = z + 3;dir = EnumFacing.NORTH; }
/* 29 */         if (cell.south) { xx = x + 5 + a;zz = z + 13;dir = EnumFacing.SOUTH; }
/* 30 */         if (cell.east) { xx = x + 13;zz = z + 5 + a;dir = EnumFacing.EAST; }
/* 31 */         if (cell.west) { xx = x + 3;zz = z + 5 + a;dir = EnumFacing.WEST; }
/* 32 */         BlockPos pos = new BlockPos(xx, y + 2 + b, zz);
/* 33 */         switch (PAT_DOORWAY[a][b]) {
/*    */         case 1: 
/* 35 */           placeBlock(world, xx, y + 2 + b, zz, 16, cell);
/* 36 */           TileEntity t = world.getTileEntity(pos);
/* 37 */           if ((t != null) && ((t instanceof TileEldritchLock)))
/* 38 */             ((TileEldritchLock)t).setLockFacing((byte)dir.ordinal());
/*    */           break;
/*    */         case 2: 
/* 41 */           placeBlock(world, xx, y + 2 + b, zz, 15, cell); break;
/* 42 */         case 9:  placeBlock(world, xx, y + 2 + b, zz, 17, cell);
/*    */         }
/*    */       }
/*    */   }
/*    */   
/* 47 */   static final int[][] PAT_DOORWAY = { { 0, 2, 2, 2, 2, 2, 0 }, { 2, 2, 9, 9, 9, 2, 2 }, { 2, 9, 9, 9, 9, 9, 2 }, { 2, 9, 9, 1, 9, 9, 2 }, { 2, 9, 9, 9, 9, 9, 2 }, { 2, 2, 9, 9, 9, 2, 2 }, { 0, 2, 2, 2, 2, 2, 0 } };
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\dim\GenBossRoom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */