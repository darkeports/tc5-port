/*     */ package thaumcraft.common.lib.world.dim;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ 
/*     */ 
/*     */ public class MazeHandler
/*     */ {
/*  17 */   public static ConcurrentHashMap<CellLoc, Short> labyrinth = new ConcurrentHashMap();
/*     */   
/*     */   public static synchronized void putToHashMap(CellLoc key, Cell cell) {
/*  20 */     labyrinth.put(key, Short.valueOf(cell.pack()));
/*     */   }
/*     */   
/*     */   public static synchronized void putToHashMapRaw(CellLoc key, short cell) {
/*  24 */     labyrinth.put(key, Short.valueOf(cell));
/*     */   }
/*     */   
/*     */   public static synchronized Cell getFromHashMap(CellLoc key) {
/*  28 */     return labyrinth.containsKey(key) ? new Cell(((Short)labyrinth.get(key)).shortValue()) : null;
/*     */   }
/*     */   
/*     */   public static synchronized void removeFromHashMap(CellLoc key) {
/*  32 */     labyrinth.remove(key);
/*     */   }
/*     */   
/*     */   public static synchronized short getFromHashMapRaw(CellLoc key) {
/*  36 */     return labyrinth.containsKey(key) ? ((Short)labyrinth.get(key)).shortValue() : 0;
/*     */   }
/*     */   
/*     */   public static synchronized void clearHashMap() {
/*  40 */     labyrinth.clear();
/*     */   }
/*     */   
/*     */   private static void readNBT(NBTTagCompound nbt) {
/*  44 */     NBTTagList tagList = nbt.getTagList("cells", 10);
/*  45 */     for (int a = 0; a < tagList.tagCount(); a++) {
/*  46 */       NBTTagCompound cell = tagList.getCompoundTagAt(a);
/*  47 */       int x = cell.getInteger("x");
/*  48 */       int z = cell.getInteger("z");
/*  49 */       short v = cell.getShort("cell");
/*  50 */       putToHashMapRaw(new CellLoc(x, z), v);
/*     */     }
/*     */   }
/*     */   
/*     */   private static NBTTagCompound writeNBT() {
/*  55 */     NBTTagCompound nbt = new NBTTagCompound();
/*  56 */     NBTTagList tagList = new NBTTagList();
/*  57 */     for (CellLoc loc : labyrinth.keySet()) {
/*  58 */       short v = getFromHashMapRaw(loc);
/*  59 */       if (v > 0) {
/*  60 */         NBTTagCompound cell = new NBTTagCompound();
/*  61 */         cell.setInteger("x", loc.x);
/*  62 */         cell.setInteger("z", loc.z);
/*  63 */         cell.setShort("cell", v);
/*  64 */         tagList.appendTag(cell);
/*     */       }
/*     */     }
/*     */     
/*  68 */     nbt.setTag("cells", tagList);
/*  69 */     return nbt;
/*     */   }
/*     */   
/*     */   public static void loadMaze(World world) {
/*  73 */     clearHashMap();
/*     */     
/*  75 */     File file1 = new File(world.getSaveHandler().getWorldDirectory(), "labyrinth.dat");
/*     */     
/*     */     NBTTagCompound nbttagcompound;
/*     */     NBTTagCompound nbttagcompound1;
/*  79 */     if (file1.exists())
/*     */     {
/*     */       try
/*     */       {
/*  83 */         nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/*  84 */         nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/*  85 */         readNBT(nbttagcompound1);
/*  86 */         return;
/*     */       }
/*     */       catch (Exception exception1)
/*     */       {
/*  90 */         exception1.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*  94 */     file1 = new File(world.getSaveHandler().getWorldDirectory(), "labyrinth.dat_old");
/*     */     
/*  96 */     if (file1.exists())
/*     */     {
/*     */       try
/*     */       {
/* 100 */         nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/* 101 */         nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 102 */         readNBT(nbttagcompound1);
/* 103 */         return;
/*     */       }
/*     */       catch (Exception exception)
/*     */       {
/* 107 */         exception.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void saveMaze(World world)
/*     */   {
/* 114 */     NBTTagCompound nbttagcompound = writeNBT();
/* 115 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 116 */     nbttagcompound1.setTag("Data", nbttagcompound);
/*     */     
/*     */     try
/*     */     {
/* 120 */       File file1 = new File(world.getSaveHandler().getWorldDirectory(), "labyrinth.dat_new");
/* 121 */       File file2 = new File(world.getSaveHandler().getWorldDirectory(), "labyrinth.dat_old");
/* 122 */       File file3 = new File(world.getSaveHandler().getWorldDirectory(), "labyrinth.dat");
/* 123 */       CompressedStreamTools.writeCompressed(nbttagcompound1, new FileOutputStream(file1));
/*     */       
/* 125 */       if (file2.exists())
/*     */       {
/* 127 */         file2.delete();
/*     */       }
/*     */       
/* 130 */       file3.renameTo(file2);
/*     */       
/* 132 */       if (file3.exists())
/*     */       {
/* 134 */         file3.delete();
/*     */       }
/*     */       
/* 137 */       file1.renameTo(file3);
/*     */       
/* 139 */       if (file1.exists())
/*     */       {
/* 141 */         file1.delete();
/*     */       }
/*     */     }
/*     */     catch (Exception exception)
/*     */     {
/* 146 */       exception.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean mazesInRange(int chunkX, int chunkZ, int w, int h)
/*     */   {
/* 152 */     for (int x = -w; x <= w; x++) {
/* 153 */       for (int z = -h; z <= h; z++) {
/* 154 */         if (getFromHashMap(new CellLoc(chunkX + x, chunkZ + z)) != null)
/* 155 */           return true;
/*     */       }
/*     */     }
/* 158 */     return false;
/*     */   }
/*     */   
/*     */   public static void generateEldritch(World world, Random random, int cx, int cz)
/*     */   {
/* 163 */     CellLoc loc = new CellLoc(cx, cz);
/* 164 */     Cell cell = getFromHashMap(loc);
/* 165 */     if (cell != null) {
/* 166 */       switch (cell.feature) {
/*     */       case 1: 
/* 168 */         GenPortal.generatePortal(world, random, cx, cz, 50, cell); break;
/*     */       case 2: case 3: case 4: case 5: 
/* 170 */         GenBossRoom.generateRoom(world, random, cx, cz, 50, cell); break;
/*     */       case 6: 
/* 172 */         GenKeyRoom.generateRoom(world, random, cx, cz, 50, cell); break;
/*     */       case 7: 
/* 174 */         GenNestRoom.generateRoom(world, random, cx, cz, 50, cell); break;
/*     */       case 8: 
/* 176 */         GenLibraryRoom.generateRoom(world, random, cx, cz, 50, cell); break;
/*     */       case 9: 
/*     */       case 10: 
/* 179 */         GenTurretRoom.generateRoom(world, random, cx, cz, 50, cell); break;
/*     */       case 11: 
/* 181 */         GenPassageRoom.generateRoom(world, random, cx, cz, 50, cell); break;
/*     */       case 12: case 13: case 14: case 15: 
/*     */       case 16: case 17: case 18: case 19: 
/*     */       case 20: case 21: case 22: 
/*     */       case 23: case 24: default: 
/* 186 */         generatePassage(world, random, cx, cz, 50, cell);
/*     */       }
/* 188 */       GenCommon.processDecorations(world);
/*     */     }
/*     */   }
/*     */   
/*     */   protected static void generatePassage(World world, Random random, int cx, int cz, int y, Cell cell)
/*     */   {
/* 194 */     switch (random.nextInt(1)) {
/* 195 */     case 0:  GenPassage.generateDefaultPassage(world, random, cx, cz, y, cell);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\dim\MazeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */