/*     */ package thaumcraft.common.lib.events;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.potions.PotionVisExhaust;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.monster.EntityEldritchGuardian;
/*     */ import thaumcraft.common.entities.monster.EntityMindSpider;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultistPortalLesser;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.misc.PacketMiscEvent;
/*     */ import thaumcraft.common.lib.network.playerdata.PacketSyncWarp;
/*     */ import thaumcraft.common.lib.potions.PotionDeathGaze;
/*     */ import thaumcraft.common.lib.potions.PotionInfectiousVisExhaust;
/*     */ import thaumcraft.common.lib.potions.PotionSunScorned;
/*     */ import thaumcraft.common.lib.potions.PotionThaumarhia;
/*     */ import thaumcraft.common.lib.potions.PotionUnnaturalHunger;
/*     */ import thaumcraft.common.lib.research.PlayerKnowledge;
/*     */ 
/*     */ public class WarpEvents
/*     */ {
/*     */   public static void checkWarpEvent(EntityPlayer player)
/*     */   {
/*  45 */     int warp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTotal(player.getName());
/*  46 */     int actualwarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(player.getName()) + Thaumcraft.proxy.getPlayerKnowledge().getWarpSticky(player.getName());
/*     */     
/*     */ 
/*  49 */     int gearWarp = getWarpFromGear(player);
/*  50 */     warp += gearWarp;
/*     */     
/*  52 */     int warpCounter = Thaumcraft.proxy.getPlayerKnowledge().getWarpCounter(player.getName());
/*     */     
/*  54 */     int r = player.worldObj.rand.nextInt(100);
/*     */     
/*  56 */     if ((warpCounter > 0) && (warp > 0) && (r <= Math.sqrt(warpCounter)))
/*     */     {
/*  58 */       warp = Math.min(100, (warp + warp + warpCounter) / 3);
/*     */       
/*  60 */       warpCounter = (int)(warpCounter - Math.max(5.0D, Math.sqrt(warpCounter) * 2.0D - gearWarp * 2));
/*     */       
/*  62 */       Thaumcraft.proxy.getPlayerKnowledge().setWarpCounter(player.getName(), warpCounter);
/*     */       
/*  64 */       int eff = player.worldObj.rand.nextInt(warp);
/*     */       
/*     */ 
/*  67 */       ItemStack helm = player.inventory.armorInventory[3];
/*  68 */       if ((helm != null) && ((helm.getItem() instanceof thaumcraft.common.items.armor.ItemFortressArmor)) && 
/*  69 */         (helm.hasTagCompound()) && (helm.getTagCompound().hasKey("mask")) && (helm.getTagCompound().getInteger("mask") == 0))
/*     */       {
/*     */ 
/*  72 */         eff -= 2 + player.worldObj.rand.nextInt(4);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  77 */       PacketHandler.INSTANCE.sendTo(new PacketMiscEvent((short)0), (EntityPlayerMP)player);
/*     */       
/*     */ 
/*     */ 
/*  81 */       if (eff > 0)
/*     */       {
/*     */ 
/*     */ 
/*  85 */         if (eff <= 4) {
/*  86 */           if (!Config.nostress) { player.worldObj.playSoundAtEntity(player, "creeper.primed", 1.0F, 0.5F);
/*     */           }
/*     */         }
/*  89 */         else if (eff <= 8) {
/*  90 */           if (!Config.nostress) { player.worldObj.playSoundEffect(player.posX + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 10.0F, player.posY + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 10.0F, player.posZ + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 10.0F, "random.explode", 4.0F, (1.0F + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*  97 */         else if (eff <= 12) {
/*  98 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.11")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 103 */         else if (eff <= 16) {
/* 104 */           PotionEffect pe = new PotionEffect(PotionVisExhaust.instance.getId(), 5000, Math.min(3, warp / 15), true, true);
/* 105 */           pe.getCurativeItems().clear();
/*     */           try {
/* 107 */             player.addPotionEffect(pe);
/*     */           } catch (Exception e) {
/* 109 */             e.printStackTrace();
/*     */           }
/*     */           
/* 112 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.1")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 117 */         else if (eff <= 20) {
/* 118 */           PotionEffect pe = new PotionEffect(PotionThaumarhia.instance.getId(), Math.min(32000, 10 * warp), 0, true, true);
/* 119 */           pe.getCurativeItems().clear();
/*     */           try {
/* 121 */             player.addPotionEffect(pe);
/*     */           } catch (Exception e) {
/* 123 */             e.printStackTrace();
/*     */           }
/*     */           
/* 126 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.15")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 131 */         else if (eff <= 24) {
/* 132 */           PotionEffect pe = new PotionEffect(PotionUnnaturalHunger.instance.getId(), 5000, Math.min(3, warp / 15), true, true);
/* 133 */           pe.getCurativeItems().clear();
/* 134 */           pe.addCurativeItem(new ItemStack(Items.rotten_flesh));
/* 135 */           pe.addCurativeItem(new ItemStack(ItemsTC.brain));
/*     */           try {
/* 137 */             player.addPotionEffect(pe);
/*     */           } catch (Exception e) {
/* 139 */             e.printStackTrace();
/*     */           }
/*     */           
/* 142 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.2")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 147 */         else if (eff <= 28) {
/* 148 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.12")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 153 */         else if (eff <= 32) {
/* 154 */           spawnMist(player, warp, 1);
/*     */ 
/*     */         }
/* 157 */         else if (eff <= 36) {
/*     */           try {
/* 159 */             player.addPotionEffect(new PotionEffect(thaumcraft.common.lib.potions.PotionBlurredVision.instance.getId(), Math.min(32000, 10 * warp), 0, true, true));
/*     */           } catch (Exception e) {
/* 161 */             e.printStackTrace();
/*     */           }
/*     */           
/*     */         }
/* 165 */         else if (eff <= 40) {
/* 166 */           PotionEffect pe = new PotionEffect(PotionSunScorned.instance.getId(), 5000, Math.min(3, warp / 15), true, true);
/* 167 */           pe.getCurativeItems().clear();
/*     */           try {
/* 169 */             player.addPotionEffect(pe);
/*     */           } catch (Exception e) {
/* 171 */             e.printStackTrace();
/*     */           }
/*     */           
/* 174 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.5")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 179 */         else if (eff <= 44) {
/*     */           try {
/* 181 */             player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 1200, Math.min(3, warp / 15), true, true));
/*     */           } catch (Exception e) {
/* 183 */             e.printStackTrace();
/*     */           }
/* 185 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.9")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 190 */         else if (eff <= 48) {
/* 191 */           PotionEffect pe = new PotionEffect(PotionInfectiousVisExhaust.instance.getId(), 6000, Math.min(3, warp / 15));
/* 192 */           pe.getCurativeItems().clear();
/*     */           try {
/* 194 */             player.addPotionEffect(pe);
/*     */           } catch (Exception e) {
/* 196 */             e.printStackTrace();
/*     */           }
/*     */           
/* 199 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.1")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 204 */         else if (eff <= 52) {
/* 205 */           player.addPotionEffect(new PotionEffect(Potion.nightVision.id, Math.min(40 * warp, 6000), 0, true, true));
/* 206 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.10")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 211 */         else if (eff <= 56) {
/* 212 */           PotionEffect pe = new PotionEffect(PotionDeathGaze.instance.getId(), 6000, Math.min(3, warp / 15), true, true);
/* 213 */           pe.getCurativeItems().clear();
/*     */           try {
/* 215 */             player.addPotionEffect(pe);
/*     */           }
/*     */           catch (Exception e) {
/* 218 */             e.printStackTrace();
/*     */           }
/*     */           
/* 221 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.4")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 226 */         else if (eff <= 60) {
/* 227 */           suddenlySpiders(player, warp, false);
/*     */ 
/*     */         }
/* 230 */         else if (eff <= 64) {
/* 231 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.13")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 236 */         else if (eff <= 68) {
/* 237 */           spawnMist(player, warp, warp / 30);
/*     */ 
/*     */         }
/* 240 */         else if (eff <= 72) {
/*     */           try {
/* 242 */             player.addPotionEffect(new PotionEffect(Potion.blindness.id, Math.min(32000, 5 * warp), 0, true, true));
/*     */           } catch (Exception e) {
/* 244 */             e.printStackTrace();
/*     */           }
/*     */           
/*     */         }
/* 248 */         else if (eff == 76) {
/* 249 */           if (Thaumcraft.proxy.getPlayerKnowledge().getWarpSticky(player.getName()) > 0) {
/* 250 */             Thaumcraft.proxy.getPlayerKnowledge().addWarpSticky(player.getName(), -1);
/* 251 */             PacketHandler.INSTANCE.sendTo(new PacketSyncWarp(player, (byte)1), (EntityPlayerMP)player);
/* 252 */             PacketHandler.INSTANCE.sendTo(new thaumcraft.common.lib.network.playerdata.PacketWarpMessage(player, (byte)1, -1), (EntityPlayerMP)player);
/*     */           }
/* 254 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.14")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 259 */         else if (eff <= 80) {
/* 260 */           PotionEffect pe = new PotionEffect(PotionUnnaturalHunger.instance.getId(), 6000, Math.min(3, warp / 15), true, true);
/* 261 */           pe.getCurativeItems().clear();
/* 262 */           pe.addCurativeItem(new ItemStack(Items.rotten_flesh));
/* 263 */           pe.addCurativeItem(new ItemStack(ItemsTC.brain));
/*     */           try {
/* 265 */             player.addPotionEffect(pe);
/*     */           } catch (Exception e) {
/* 267 */             e.printStackTrace();
/*     */           }
/*     */           
/* 270 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.2")));
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 275 */         else if (eff <= 88) {
/* 276 */           spawnPortal(player);
/*     */ 
/*     */         }
/* 279 */         else if (eff <= 92) {
/* 280 */           suddenlySpiders(player, warp, true);
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 285 */           spawnMist(player, warp, warp / 15);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 290 */       if (actualwarp > 10)
/*     */       {
/* 292 */         if ((!ResearchHelper.isResearchComplete(player.getName(), "BATHSALTS")) && (!ResearchHelper.isResearchComplete(player.getName(), "@BATHSALTS")))
/*     */         {
/* 294 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.8")));
/* 295 */           ResearchHelper.completeResearch(player, "@BATHSALTS");
/*     */         }
/*     */       }
/* 298 */       if (actualwarp > 25)
/*     */       {
/* 300 */         if (!ResearchHelper.isResearchComplete(player.getName(), "ELDRITCHMINOR")) {
/* 301 */           ResearchHelper.completeResearch(player, "ELDRITCHMINOR");
/*     */         }
/*     */       }
/*     */       
/* 305 */       if (actualwarp > 50)
/*     */       {
/* 307 */         if (!ResearchHelper.isResearchComplete(player.getName(), "ELDRITCHMAJOR")) {
/* 308 */           ResearchHelper.completeResearch(player, "ELDRITCHMAJOR");
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 313 */     Thaumcraft.proxy.getPlayerKnowledge().addWarpTemp(player.getName(), -1);
/* 314 */     PacketHandler.INSTANCE.sendTo(new PacketSyncWarp(player, (byte)2), (EntityPlayerMP)player);
/*     */   }
/*     */   
/*     */   private static void spawnMist(EntityPlayer player, int warp, int guardian) {
/* 318 */     PacketHandler.INSTANCE.sendTo(new PacketMiscEvent((short)1), (EntityPlayerMP)player);
/*     */     
/*     */ 
/*     */ 
/* 322 */     if (guardian > 0) {
/* 323 */       guardian = Math.min(8, guardian);
/* 324 */       for (int a = 0; a < guardian; a++) {
/* 325 */         spawnGuardian(player);
/*     */       }
/*     */     }
/* 328 */     player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.6")));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static void spawnPortal(EntityPlayer player)
/*     */   {
/* 335 */     EntityCultistPortalLesser eg = new EntityCultistPortalLesser(player.worldObj);
/* 336 */     int i = MathHelper.floor_double(player.posX);
/* 337 */     int j = MathHelper.floor_double(player.posY);
/* 338 */     int k = MathHelper.floor_double(player.posZ);
/* 339 */     for (int l = 0; l < 50; l++)
/*     */     {
/* 341 */       int i1 = i + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
/* 342 */       int j1 = j + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
/* 343 */       int k1 = k + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
/*     */       
/* 345 */       if (World.doesBlockHaveSolidTopSurface(player.worldObj, new BlockPos(i1, j1 - 1, k1)))
/*     */       {
/* 347 */         eg.setPosition(i1 + 0.5D, j1 + 1.0D, k1 + 0.5D);
/*     */         
/* 349 */         if ((player.worldObj.checkNoEntityCollision(eg.getEntityBoundingBox())) && (player.worldObj.getCollidingBoundingBoxes(eg, eg.getEntityBoundingBox()).isEmpty()) && (!player.worldObj.isAnyLiquid(eg.getEntityBoundingBox())))
/*     */         {
/*     */ 
/*     */ 
/* 353 */           eg.onInitialSpawn(player.worldObj.getDifficultyForLocation(new BlockPos(eg)), (net.minecraft.entity.IEntityLivingData)null);
/* 354 */           player.worldObj.spawnEntityInWorld(eg);
/* 355 */           player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.16")));
/*     */           
/*     */ 
/* 358 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void spawnGuardian(EntityPlayer player) {
/* 365 */     EntityEldritchGuardian eg = new EntityEldritchGuardian(player.worldObj);
/* 366 */     int i = MathHelper.floor_double(player.posX);
/* 367 */     int j = MathHelper.floor_double(player.posY);
/* 368 */     int k = MathHelper.floor_double(player.posZ);
/* 369 */     for (int l = 0; l < 50; l++)
/*     */     {
/* 371 */       int i1 = i + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
/* 372 */       int j1 = j + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
/* 373 */       int k1 = k + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
/*     */       
/* 375 */       if (World.doesBlockHaveSolidTopSurface(player.worldObj, new BlockPos(i1, j1 - 1, k1)))
/*     */       {
/* 377 */         eg.setPosition(i1, j1, k1);
/*     */         
/* 379 */         if ((player.worldObj.checkNoEntityCollision(eg.getEntityBoundingBox())) && (player.worldObj.getCollidingBoundingBoxes(eg, eg.getEntityBoundingBox()).isEmpty()) && (!player.worldObj.isAnyLiquid(eg.getEntityBoundingBox())))
/*     */         {
/*     */ 
/*     */ 
/* 383 */           eg.setAttackTarget(player);
/* 384 */           player.worldObj.spawnEntityInWorld(eg);
/* 385 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void suddenlySpiders(EntityPlayer player, int warp, boolean real)
/*     */   {
/* 393 */     int spawns = Math.min(50, warp);
/*     */     
/* 395 */     for (int a = 0; a < spawns; a++)
/*     */     {
/* 397 */       EntityMindSpider spider = new EntityMindSpider(player.worldObj);
/* 398 */       int i = MathHelper.floor_double(player.posX);
/* 399 */       int j = MathHelper.floor_double(player.posY);
/* 400 */       int k = MathHelper.floor_double(player.posZ);
/* 401 */       boolean success = false;
/* 402 */       for (int l = 0; l < 50; l++)
/*     */       {
/* 404 */         int i1 = i + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
/* 405 */         int j1 = j + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
/* 406 */         int k1 = k + MathHelper.getRandomIntegerInRange(player.worldObj.rand, 7, 24) * MathHelper.getRandomIntegerInRange(player.worldObj.rand, -1, 1);
/*     */         
/* 408 */         if (World.doesBlockHaveSolidTopSurface(player.worldObj, new BlockPos(i1, j1 - 1, k1)))
/*     */         {
/* 410 */           spider.setPosition(i1, j1, k1);
/*     */           
/* 412 */           if ((player.worldObj.checkNoEntityCollision(spider.getEntityBoundingBox())) && (player.worldObj.getCollidingBoundingBoxes(spider, spider.getEntityBoundingBox()).isEmpty()) && (!player.worldObj.isAnyLiquid(spider.getEntityBoundingBox())))
/*     */           {
/*     */ 
/*     */ 
/* 416 */             success = true;
/* 417 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 421 */       if (success)
/*     */       {
/* 423 */         spider.setAttackTarget(player);
/* 424 */         if (!real) {
/* 425 */           spider.setViewer(player.getName());
/* 426 */           spider.setHarmless(true);
/*     */         }
/*     */         
/* 429 */         player.worldObj.spawnEntityInWorld(spider);
/*     */       }
/*     */     }
/*     */     
/* 433 */     player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("warp.text.7")));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void checkDeathGaze(EntityPlayer player)
/*     */   {
/* 440 */     PotionEffect pe = player.getActivePotionEffect(PotionDeathGaze.instance);
/* 441 */     if (pe == null) return;
/* 442 */     int level = pe.getAmplifier();
/* 443 */     int range = Math.min(8 + level * 3, 24);
/* 444 */     List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(range, range, range));
/*     */     
/*     */ 
/* 447 */     for (int i = 0; i < list.size(); i++)
/*     */     {
/* 449 */       Entity entity = (Entity)list.get(i);
/* 450 */       if ((entity.canBeCollidedWith()) && ((entity instanceof EntityLivingBase)) && (((EntityLivingBase)entity).isEntityAlive()))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 457 */         if (thaumcraft.common.lib.utils.EntityUtils.isVisibleTo(0.75F, player, entity, range))
/*     */         {
/* 459 */           if ((entity != null) && (player.canEntityBeSeen(entity)) && 
/* 460 */             ((!(entity instanceof EntityPlayer)) || (MinecraftServer.getServer().isPVPEnabled())) && 
/* 461 */             (!((EntityLivingBase)entity).isPotionActive(Potion.wither.getId()))) {
/* 462 */             ((EntityLivingBase)entity).setRevengeTarget(player);
/* 463 */             ((EntityLivingBase)entity).setLastAttacker(player);
/* 464 */             if ((entity instanceof EntityCreature)) {
/* 465 */               ((EntityCreature)entity).setAttackTarget(player);
/*     */             }
/* 467 */             ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.wither.getId(), 80));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static int getWarpFromGear(EntityPlayer player)
/*     */   {
/* 477 */     int w = PlayerEvents.getFinalWarp(player.getCurrentEquippedItem(), player);
/*     */     
/* 479 */     for (int a = 0; a < 4; a++) {
/* 480 */       w += PlayerEvents.getFinalWarp(player.inventory.armorItemInSlot(a), player);
/*     */     }
/*     */     
/* 483 */     IInventory baubles = baubles.api.BaublesApi.getBaubles(player);
/* 484 */     for (int a = 0; a < 4; a++) {
/* 485 */       w += PlayerEvents.getFinalWarp(baubles.getStackInSlot(a), player);
/*     */     }
/*     */     
/* 488 */     return w;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\events\WarpEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */