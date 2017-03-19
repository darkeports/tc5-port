/*     */ package thaumcraft.common.tiles.misc;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.monster.boss.EntityCultistPortalGreater;
/*     */ import thaumcraft.common.entities.monster.boss.EntityEldritchGolem;
/*     */ import thaumcraft.common.entities.monster.boss.EntityEldritchWarden;
/*     */ import thaumcraft.common.entities.monster.boss.EntityTaintacleGiant;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintacle;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXBlockMist;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ import thaumcraft.common.lib.world.biomes.BiomeHandler;
/*     */ import thaumcraft.common.lib.world.dim.Cell;
/*     */ import thaumcraft.common.lib.world.dim.CellLoc;
/*     */ import thaumcraft.common.lib.world.dim.GenCommon;
/*     */ import thaumcraft.common.lib.world.dim.MapBossData;
/*     */ import thaumcraft.common.lib.world.dim.MazeHandler;
/*     */ 
/*     */ public class TileEldritchLock extends TileThaumcraft implements ITickable
/*     */ {
/*  42 */   public int count = -1;
/*     */   
/*     */ 
/*     */ 
/*     */   public void update()
/*     */   {
/*  48 */     if (this.count != -1) {
/*  49 */       this.count += 1;
/*  50 */       if (this.count % 5 == 0) {
/*  51 */         this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:pump", 1.0F, 1.0F);
/*     */       }
/*  53 */       if (this.count > 100)
/*     */       {
/*  55 */         doBossSpawn();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void doBossSpawn()
/*     */   {
/*  63 */     this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:ice", 1.0F, 1.0F);
/*  64 */     if (!this.worldObj.isRemote)
/*     */     {
/*  66 */       int cx = this.pos.getX() >> 4;
/*  67 */       int cz = this.pos.getZ() >> 4;
/*  68 */       int centerx = this.pos.getX() >> 4;
/*  69 */       int centerz = this.pos.getZ() >> 4;
/*  70 */       int exit = 0;
/*  71 */       for (int a = -2; a <= 2; a++) {
/*  72 */         for (int b = -2; b <= 2; b++) {
/*  73 */           Cell c = MazeHandler.getFromHashMap(new CellLoc(cx + a, cz + b));
/*  74 */           if ((c != null) && (c.feature == 2)) {
/*  75 */             centerx = cx + a;
/*  76 */             centerz = cz + b;
/*     */           }
/*  78 */           if ((c != null) && (c.feature >= 2) && (c.feature <= 5) && ((c.north) || (c.south) || (c.east) || (c.west))) {
/*  79 */             exit = c.feature;
/*     */           }
/*     */         }
/*     */       }
/*  83 */       MapBossData mbd = (MapBossData)this.worldObj.loadItemData(MapBossData.class, "BossMapData");
/*  84 */       if (mbd == null) {
/*  85 */         mbd = new MapBossData("BossMapData");
/*  86 */         mbd.bossCount = 0;
/*  87 */         mbd.markDirty();
/*  88 */         this.worldObj.setItemData("BossMapData", mbd);
/*     */       }
/*     */       
/*  91 */       mbd.bossCount += 1;
/*  92 */       if (this.worldObj.rand.nextFloat() < 0.25F) mbd.bossCount += 1;
/*  93 */       mbd.markDirty();
/*     */       
/*  95 */       switch (mbd.bossCount % 4) {
/*  96 */       case 0:  spawnGolemBossRoom(centerx, centerz, exit); break;
/*  97 */       case 1:  spawnWardenBossRoom(centerx, centerz, exit); break;
/*  98 */       case 2:  spawnCultistBossRoom(centerx, centerz, exit); break;
/*  99 */       case 3:  spawnTaintBossRoom(centerx, centerz, exit);
/*     */       }
/*     */       
/* 102 */       for (int a = -2; a <= 2; a++) {
/* 103 */         for (int b = -2; b <= 2; b++)
/* 104 */           for (int c = -2; c <= 2; c++)
/* 105 */             if (this.worldObj.getBlockState(this.pos.add(a, b, c)) == BlocksTC.eldritch.getStateFromMeta(5)) {
/* 106 */               thaumcraft.common.lib.network.PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockMist(this.pos.add(a, b, c), 4194368), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), this.pos.getX() + a, this.pos.getY() + b, this.pos.getZ() + c, 32.0D));
/*     */               
/*     */ 
/* 109 */               this.worldObj.setBlockToAir(this.pos.add(a, b, c));
/*     */             }
/*     */       }
/* 112 */       this.worldObj.setBlockToAir(this.pos);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 122 */   int[][] ped = { { 2, 2, 2 }, { 0, -1, 1 }, { 3, 3, 3 } };
/*     */   
/*     */   private void spawnWardenBossRoom(int cx, int cz, int exit) {
/* 125 */     for (int i = 0; i < this.worldObj.playerEntities.size(); i++)
/*     */     {
/* 127 */       EntityPlayer ep = (EntityPlayer)this.worldObj.playerEntities.get(i);
/* 128 */       if (ep.getDistanceSq(this.pos) < 300.0D) {
/* 129 */         ep.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.boss.warden")));
/*     */       }
/*     */     }
/* 132 */     int x = cx * 16 + 16;
/* 133 */     int y = 50;
/* 134 */     int z = cz * 16 + 16;
/* 135 */     int x2 = x;
/* 136 */     int z2 = z;
/* 137 */     switch (exit) {
/* 138 */     case 2:  x2 += 8;z2 += 8; break;
/* 139 */     case 3:  x2 -= 8;z2 += 8; break;
/* 140 */     case 4:  x2 += 8;z2 -= 8; break;
/* 141 */     case 5:  x2 -= 8;z2 -= 8;
/*     */     }
/*     */     
/* 144 */     GenCommon.genObelisk(this.worldObj, new BlockPos(x2, y + 4, z));
/* 145 */     GenCommon.genObelisk(this.worldObj, new BlockPos(x, y + 4, z2));
/* 146 */     this.worldObj.setBlockState(new BlockPos(x2, y + 2, z), BlocksTC.eldritch.getStateFromMeta(1));
/* 147 */     this.worldObj.setBlockState(new BlockPos(x, y + 2, z2), BlocksTC.eldritch.getStateFromMeta(1));
/*     */     
/* 149 */     for (int a = -1; a <= 1; a++) {
/* 150 */       for (int b = -1; b <= 1; b++) {
/* 151 */         if ((a != 0) && (b != 0) && (this.worldObj.rand.nextFloat() < 0.9F)) {
/* 152 */           float rr = this.worldObj.rand.nextFloat();
/* 153 */           int md = rr < 0.3F ? 1 : rr < 0.1F ? 2 : 0;
/* 154 */           this.worldObj.setBlockState(new BlockPos(x2 + a, y + 2, z + b), BlocksTC.lootUrn.getStateFromMeta(md));
/*     */         }
/* 156 */         if ((a != 0) && (b != 0) && (this.worldObj.rand.nextFloat() < 0.9F)) {
/* 157 */           float rr = this.worldObj.rand.nextFloat();
/* 158 */           int md = rr < 0.3F ? 1 : rr < 0.1F ? 2 : 0;
/* 159 */           this.worldObj.setBlockState(new BlockPos(x + a, y + 2, z2 + b), BlocksTC.lootUrn.getStateFromMeta(md));
/*     */         }
/*     */       }
/*     */     }
/* 163 */     this.worldObj.setBlockState(new BlockPos(x - 2, y + 2, z - 2), BlocksTC.pedestal.getStateFromMeta(2));
/* 164 */     this.worldObj.setBlockState(new BlockPos(x - 2, y + 2, z + 2), BlocksTC.pedestal.getStateFromMeta(2));
/* 165 */     this.worldObj.setBlockState(new BlockPos(x + 2, y + 2, z + 2), BlocksTC.pedestal.getStateFromMeta(2));
/* 166 */     this.worldObj.setBlockState(new BlockPos(x + 2, y + 2, z - 2), BlocksTC.pedestal.getStateFromMeta(2));
/*     */     
/*     */ 
/* 169 */     for (int a = 0; a < 3; a++) {
/* 170 */       for (int b = 0; b < 3; b++) {
/* 171 */         if (this.ped[a][b] < 0) {
/* 172 */           this.worldObj.setBlockState(new BlockPos(x2 - 1 + b, y + 2, z2 - 1 + a), BlocksTC.stone.getStateFromMeta(7));
/*     */         } else {
/* 174 */           this.worldObj.setBlockState(new BlockPos(x2 - 1 + b, y + 2, z2 - 1 + a), BlocksTC.stairsAncient.getStateFromMeta(this.ped[a][b]));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 180 */     EntityEldritchWarden boss = new EntityEldritchWarden(this.worldObj);
/* 181 */     double d0 = this.pos.getX() - (x2 + 0.5D);
/* 182 */     double d1 = this.pos.getY() - (y + 3 + boss.getEyeHeight());
/* 183 */     double d2 = this.pos.getZ() - (z2 + 0.5D);
/* 184 */     double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
/* 185 */     float f = (float)(Math.atan2(d2, d0) * 180.0D / 3.141592653589793D) - 90.0F;
/* 186 */     float f1 = (float)-(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D);
/* 187 */     boss.setLocationAndAngles(x2 + 0.5D, y + 3, z2 + 0.5D, f, f1);
/* 188 */     boss.onInitialSpawn(this.worldObj.getDifficultyForLocation(this.pos), (IEntityLivingData)null);
/* 189 */     boss.setHomePosAndDistance(new BlockPos(x, y + 2, z), 32);
/* 190 */     this.worldObj.spawnEntityInWorld(boss);
/*     */   }
/*     */   
/*     */   private void spawnGolemBossRoom(int cx, int cz, int exit)
/*     */   {
/* 195 */     for (int i = 0; i < this.worldObj.playerEntities.size(); i++)
/*     */     {
/* 197 */       EntityPlayer ep = (EntityPlayer)this.worldObj.playerEntities.get(i);
/* 198 */       if (ep.getDistanceSq(this.pos) < 300.0D) {
/* 199 */         ep.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.boss.golem")));
/*     */       }
/*     */     }
/* 202 */     int x = cx * 16 + 16;
/* 203 */     int y = 50;
/* 204 */     int z = cz * 16 + 16;
/* 205 */     int x2 = 0;
/* 206 */     int z2 = 0;
/* 207 */     switch (exit) {
/* 208 */     case 2:  x2 = 8;z2 = 8; break;
/* 209 */     case 3:  x2 = -8;z2 = 8; break;
/* 210 */     case 4:  x2 = 8;z2 = -8; break;
/* 211 */     case 5:  x2 = -8;z2 = -8;
/*     */     }
/*     */     
/* 214 */     GenCommon.genObelisk(this.worldObj, new BlockPos(x + x2, y + 4, z + z2));
/* 215 */     GenCommon.genObelisk(this.worldObj, new BlockPos(x - x2, y + 4, z + z2));
/* 216 */     GenCommon.genObelisk(this.worldObj, new BlockPos(x + x2, y + 4, z - z2));
/* 217 */     this.worldObj.setBlockState(new BlockPos(x + x2, y + 2, z + z2), BlocksTC.eldritch.getStateFromMeta(1));
/* 218 */     this.worldObj.setBlockState(new BlockPos(x - x2, y + 2, z + z2), BlocksTC.eldritch.getStateFromMeta(1));
/* 219 */     this.worldObj.setBlockState(new BlockPos(x + x2, y + 2, z - z2), BlocksTC.eldritch.getStateFromMeta(1));
/*     */     
/* 221 */     for (int a = 0; a < 3; a++) {
/* 222 */       for (int b = 0; b < 3; b++) {
/* 223 */         if (this.ped[a][b] < 0) {
/* 224 */           this.worldObj.setBlockState(new BlockPos(x - 1 + b, y + 2, z - 1 + a), BlocksTC.stone.getStateFromMeta(7));
/*     */         } else {
/* 226 */           this.worldObj.setBlockState(new BlockPos(x - 1 + b, y + 2, z - 1 + a), BlocksTC.stairsAncient.getStateFromMeta(this.ped[a][b]));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 231 */     for (int a = -10; a <= 10; a++) {
/* 232 */       for (int b = -10; b <= 10; b++) {
/* 233 */         if (((a < -2) && (b < -2)) || ((a > 2) && (b > 2)) || ((a < -2) && (b > 2)) || ((a > 2) && (b < -2) && (this.worldObj.rand.nextFloat() < 0.15F) && (this.worldObj.isAirBlock(new BlockPos(x + a, y + 2, z + b)))))
/*     */         {
/* 235 */           float rr = this.worldObj.rand.nextFloat();
/* 236 */           int md = rr < 0.2F ? 1 : rr < 0.05F ? 2 : 0;
/* 237 */           this.worldObj.setBlockState(new BlockPos(x + a, y + 2, z + b), this.worldObj.rand.nextFloat() < 0.3F ? BlocksTC.lootCrate.getStateFromMeta(md) : BlocksTC.lootUrn.getStateFromMeta(md));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 242 */     EntityEldritchGolem boss = new EntityEldritchGolem(this.worldObj);
/* 243 */     double d0 = this.pos.getX() - (x + 0.5D);
/* 244 */     double d1 = this.pos.getY() - (y + 3 + boss.getEyeHeight());
/* 245 */     double d2 = this.pos.getZ() - (z + 0.5D);
/* 246 */     double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
/* 247 */     float f = (float)(Math.atan2(d2, d0) * 180.0D / 3.141592653589793D) - 90.0F;
/* 248 */     float f1 = (float)-(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D);
/* 249 */     boss.setLocationAndAngles(x + 0.5D, y + 3, z + 0.5D, f, f1);
/* 250 */     boss.onInitialSpawn(this.worldObj.getDifficultyForLocation(this.pos), (IEntityLivingData)null);
/* 251 */     this.worldObj.spawnEntityInWorld(boss);
/*     */   }
/*     */   
/*     */   private void spawnCultistBossRoom(int cx, int cz, int exit)
/*     */   {
/* 256 */     for (int i = 0; i < this.worldObj.playerEntities.size(); i++)
/*     */     {
/* 258 */       EntityPlayer ep = (EntityPlayer)this.worldObj.playerEntities.get(i);
/* 259 */       if (ep.getDistanceSq(this.pos) < 300.0D) {
/* 260 */         ep.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.boss.crimson")));
/*     */       }
/*     */     }
/*     */     
/* 264 */     int x = cx * 16 + 16;
/* 265 */     int y = 50;
/* 266 */     int z = cz * 16 + 16;
/*     */     
/* 268 */     for (int a = -4; a <= 4; a++) {
/* 269 */       for (int b = -4; b <= 4; b++) {
/* 270 */         if (((Math.abs(a) != 2) && (Math.abs(b) != 2)) || ((!this.worldObj.rand.nextBoolean()) && (
/* 271 */           ((Math.abs(a) != 3) && (Math.abs(b) != 3)) || ((this.worldObj.rand.nextFloat() <= 0.33F) && (
/* 272 */           ((Math.abs(a) != 4) && (Math.abs(b) != 4)) || (this.worldObj.rand.nextFloat() <= 0.25F))))))
/* 273 */           this.worldObj.setBlockState(new BlockPos(x + b, y + 1, z + a), BlocksTC.stone.getStateFromMeta(10));
/*     */       }
/*     */     }
/* 276 */     for (int a = 0; a < 5; a++) {
/* 277 */       for (int b = 0; b < 5; b++)
/* 278 */         if ((a == 0) || (a == 4) || (b == 0) || (b == 4)) {
/* 279 */           this.worldObj.setBlockState(new BlockPos(x - 8 + b * 4, y + 2, z - 8 + a * 4), BlocksTC.stone.getStateFromMeta(2));
/* 280 */           this.worldObj.setBlockState(new BlockPos(x - 8 + b * 4, y + 3, z - 8 + a * 4), BlocksTC.stone.getStateFromMeta(11));
/* 281 */           this.worldObj.setBlockState(new BlockPos(x - 8 + b * 4, y + 4, z - 8 + a * 4), BlocksTC.slabStone.getStateFromMeta(2));
/*     */           
/* 283 */           this.worldObj.setBlockState(new BlockPos(x - 8 + b * 4, y + 10, z - 8 + a * 4), BlocksTC.stone.getStateFromMeta(2));
/* 284 */           this.worldObj.setBlockState(new BlockPos(x - 8 + b * 4, y + 9, z - 8 + a * 4), BlocksTC.stone.getStateFromMeta(11));
/* 285 */           this.worldObj.setBlockState(new BlockPos(x - 8 + b * 4, y + 8, z - 8 + a * 4), BlocksTC.slabStone.getStateFromMeta(10));
/*     */         }
/*     */     }
/* 288 */     EntityCultistPortalGreater boss = new EntityCultistPortalGreater(this.worldObj);
/* 289 */     boss.setLocationAndAngles(x + 0.5D, y + 2, z + 0.5D, 0.0F, 0.0F);
/* 290 */     this.worldObj.spawnEntityInWorld(boss);
/*     */   }
/*     */   
/*     */   private void spawnTaintBossRoom(int cx, int cz, int exit)
/*     */   {
/* 295 */     for (int i = 0; i < this.worldObj.playerEntities.size(); i++)
/*     */     {
/* 297 */       EntityPlayer ep = (EntityPlayer)this.worldObj.playerEntities.get(i);
/* 298 */       if (ep.getDistanceSq(this.pos) < 300.0D) {
/* 299 */         ep.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.boss.taint")));
/*     */       }
/*     */     }
/*     */     
/* 303 */     int x = cx * 16 + 16;
/* 304 */     int y = 50;
/* 305 */     int z = cz * 16 + 16;
/*     */     
/* 307 */     AuraHelper.addAura(getWorld(), new BlockPos(x, y, z), thaumcraft.api.aspects.Aspect.FLUX, Config.AURABASE * 4);
/*     */     
/* 309 */     for (int a = -12; a <= 12; a++) {
/* 310 */       for (int b = -12; b <= 12; b++) {
/* 311 */         Utils.setBiomeAt(this.worldObj, new BlockPos(x + b, 0, z + a), BiomeHandler.biomeTaint);
/*     */         
/* 313 */         for (int c = 0; c < 9; c++) {
/* 314 */           if ((this.worldObj.isAirBlock(new BlockPos(x + b, y + 2 + c, z + a))) && (thaumcraft.common.lib.utils.BlockUtils.isAdjacentToSolidBlock(this.worldObj, new BlockPos(x + b, y + 2 + c, z + a))))
/*     */           {
/* 316 */             if (this.worldObj.rand.nextInt(3) != 0)
/* 317 */               this.worldObj.setBlockState(new BlockPos(x + b, y + 2 + c, z + a), BlocksTC.taintFibre.getDefaultState());
/*     */           }
/*     */         }
/* 320 */         if (this.worldObj.rand.nextFloat() < 0.15D) {
/* 321 */           this.worldObj.setBlockState(new BlockPos(x + b, y + 2, z + a), BlocksTC.taintBlock.getStateFromMeta(1));
/* 322 */           if (this.worldObj.rand.nextFloat() < 0.2D) {
/* 323 */             this.worldObj.setBlockState(new BlockPos(x + b, y + 3, z + a), BlocksTC.taintBlock.getStateFromMeta(1));
/*     */           }
/*     */         }
/* 326 */         if (((Math.abs(a) != 4) && (Math.abs(b) != 4)) || ((!this.worldObj.rand.nextBoolean()) && (
/* 327 */           ((Math.abs(a) < 5) && (Math.abs(b) < 5)) || ((this.worldObj.rand.nextFloat() <= 0.33F) && (
/* 328 */           ((Math.abs(a) < 7) && (Math.abs(b) < 7)) || (this.worldObj.rand.nextFloat() <= 0.25F)))))) {
/* 329 */           this.worldObj.setBlockState(new BlockPos(x + b, y + 1, z + a), BlocksTC.taintBlock.getStateFromMeta(0));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 334 */     EntityTaintacle boss1 = this.worldObj.getDifficulty() != EnumDifficulty.HARD ? new EntityTaintacle(this.worldObj) : new EntityTaintacleGiant(this.worldObj);
/* 335 */     boss1.setLocationAndAngles(x + 0.5D, y + 3, z + 0.5D, 0.0F, 0.0F);
/* 336 */     EntityUtils.makeChampion(boss1, true);
/* 337 */     this.worldObj.spawnEntityInWorld(boss1);
/*     */     
/* 339 */     EntityTaintacle boss2 = this.worldObj.rand.nextBoolean() ? new EntityTaintacle(this.worldObj) : new EntityTaintacleGiant(this.worldObj);
/* 340 */     boss2.setLocationAndAngles(x + 3.5D, y + 3, z + 3.5D, 0.0F, 0.0F);
/* 341 */     EntityUtils.makeChampion(boss2, true);
/* 342 */     this.worldObj.spawnEntityInWorld(boss2);
/*     */     
/* 344 */     EntityTaintacle boss3 = (boss2 instanceof EntityTaintacleGiant) ? new EntityTaintacle(this.worldObj) : new EntityTaintacleGiant(this.worldObj);
/* 345 */     boss3.setLocationAndAngles(x - 2.5D, y + 3, z + 3.5D, 0.0F, 0.0F);
/* 346 */     EntityUtils.makeChampion(boss3, true);
/* 347 */     this.worldObj.spawnEntityInWorld(boss3);
/*     */     
/* 349 */     EntityTaintacle boss4 = this.worldObj.rand.nextBoolean() ? new EntityTaintacle(this.worldObj) : new EntityTaintacleGiant(this.worldObj);
/* 350 */     boss4.setLocationAndAngles(x + 3.5D, y + 3, z - 2.5D, 0.0F, 0.0F);
/* 351 */     EntityUtils.makeChampion(boss4, true);
/* 352 */     this.worldObj.spawnEntityInWorld(boss4);
/*     */     
/* 354 */     EntityTaintacle boss5 = (boss4 instanceof EntityTaintacleGiant) ? new EntityTaintacle(this.worldObj) : new EntityTaintacleGiant(this.worldObj);
/* 355 */     boss5.setLocationAndAngles(x - 2.5D, y + 3, z - 2.5D, 0.0F, 0.0F);
/* 356 */     EntityUtils.makeChampion(boss5, true);
/* 357 */     this.worldObj.spawnEntityInWorld(boss5);
/*     */   }
/*     */   
/*     */ 
/*     */   public double getMaxRenderDistanceSquared()
/*     */   {
/* 363 */     return 9216.0D;
/*     */   }
/*     */   
/*     */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/* 369 */     return AxisAlignedBB.fromBounds(getPos().getX() - 2.25D, getPos().getY() - 2.25D, getPos().getZ() - 2.25D, getPos().getX() + 3.25D, getPos().getY() + 3.25D, getPos().getZ() + 3.25D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 374 */   byte facing = 0;
/*     */   
/*     */   public byte getLockFacing() {
/* 377 */     return this.facing;
/*     */   }
/*     */   
/*     */   public void setLockFacing(byte face) {
/* 381 */     this.facing = face;
/* 382 */     this.worldObj.markBlockForUpdate(this.pos);
/* 383 */     markDirty();
/*     */   }
/*     */   
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 388 */     this.facing = nbttagcompound.getByte("facing");
/* 389 */     this.count = nbttagcompound.getShort("count");
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 394 */     nbttagcompound.setByte("facing", this.facing);
/* 395 */     nbttagcompound.setShort("count", (short)this.count);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\misc\TileEldritchLock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */