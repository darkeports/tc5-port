/*     */ package thaumcraft.common.config;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionHelper;
/*     */ import thaumcraft.api.ThaumcraftApi;
/*     */ import thaumcraft.api.ThaumcraftApi.EntityTagsNBT;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ 
/*     */ public class ConfigAspects
/*     */ {
/*     */   public static void init()
/*     */   {
/*  23 */     registerItemAspects();
/*  24 */     registerEntityAspects();
/*     */   }
/*     */   
/*  27 */   private static final int[] ALLMETA = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
/*  28 */   public static String[] dyes = { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void registerEntityAspects()
/*     */   {
/*  35 */     ThaumcraftApi.registerEntityTag("Zombie", new AspectList().add(Aspect.UNDEAD, 2).add(Aspect.MAN, 1).add(Aspect.EARTH, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  36 */     ThaumcraftApi.registerEntityTag("Giant", new AspectList().add(Aspect.UNDEAD, 4).add(Aspect.MAN, 3).add(Aspect.EARTH, 3), new ThaumcraftApi.EntityTagsNBT[0]);
/*  37 */     ThaumcraftApi.registerEntityTag("Skeleton", new AspectList().add(Aspect.UNDEAD, 3).add(Aspect.MAN, 1).add(Aspect.EARTH, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  38 */     ThaumcraftApi.registerEntityTag("Skeleton", new AspectList().add(Aspect.UNDEAD, 4).add(Aspect.MAN, 1).add(Aspect.FIRE, 2), new ThaumcraftApi.EntityTagsNBT[] { new ThaumcraftApi.EntityTagsNBT("SkeletonType", Byte.valueOf(1)) });
/*  39 */     ThaumcraftApi.registerEntityTag("Creeper", new AspectList().add(Aspect.PLANT, 2).add(Aspect.FIRE, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  40 */     ThaumcraftApi.registerEntityTag("Creeper", new AspectList().add(Aspect.PLANT, 3).add(Aspect.FIRE, 3).add(Aspect.ENERGY, 3), new ThaumcraftApi.EntityTagsNBT[] { new ThaumcraftApi.EntityTagsNBT("powered", Byte.valueOf(1)) });
/*  41 */     ThaumcraftApi.registerEntityTag("EntityHorse", new AspectList().add(Aspect.BEAST, 4).add(Aspect.EARTH, 1).add(Aspect.AIR, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  42 */     ThaumcraftApi.registerEntityTag("Pig", new AspectList().add(Aspect.BEAST, 2).add(Aspect.EARTH, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  43 */     ThaumcraftApi.registerEntityTag("XPOrb", new AspectList().add(Aspect.MIND, 5), new ThaumcraftApi.EntityTagsNBT[0]);
/*  44 */     ThaumcraftApi.registerEntityTag("Sheep", new AspectList().add(Aspect.BEAST, 2).add(Aspect.EARTH, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  45 */     ThaumcraftApi.registerEntityTag("Cow", new AspectList().add(Aspect.BEAST, 3).add(Aspect.EARTH, 3), new ThaumcraftApi.EntityTagsNBT[0]);
/*  46 */     ThaumcraftApi.registerEntityTag("MushroomCow", new AspectList().add(Aspect.BEAST, 3).add(Aspect.PLANT, 1).add(Aspect.EARTH, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  47 */     ThaumcraftApi.registerEntityTag("SnowMan", new AspectList().add(Aspect.COLD, 3).add(Aspect.WATER, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  48 */     ThaumcraftApi.registerEntityTag("Ozelot", new AspectList().add(Aspect.BEAST, 3).add(Aspect.ENTROPY, 3), new ThaumcraftApi.EntityTagsNBT[0]);
/*  49 */     ThaumcraftApi.registerEntityTag("Chicken", new AspectList().add(Aspect.BEAST, 2).add(Aspect.FLIGHT, 2).add(Aspect.AIR, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  50 */     ThaumcraftApi.registerEntityTag("Squid", new AspectList().add(Aspect.BEAST, 2).add(Aspect.WATER, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  51 */     ThaumcraftApi.registerEntityTag("Wolf", new AspectList().add(Aspect.BEAST, 3).add(Aspect.EARTH, 3), new ThaumcraftApi.EntityTagsNBT[0]);
/*  52 */     ThaumcraftApi.registerEntityTag("Bat", new AspectList().add(Aspect.BEAST, 1).add(Aspect.FLIGHT, 1).add(Aspect.AIR, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  53 */     ThaumcraftApi.registerEntityTag("Boat", new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.WATER, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  54 */     ThaumcraftApi.registerEntityTag("Spider", new AspectList().add(Aspect.BEAST, 3).add(Aspect.ENTROPY, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  55 */     ThaumcraftApi.registerEntityTag("Slime", new AspectList().add(Aspect.LIFE, 2).add(Aspect.WATER, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  56 */     ThaumcraftApi.registerEntityTag("Ghast", new AspectList().add(Aspect.UNDEAD, 3).add(Aspect.FIRE, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  57 */     ThaumcraftApi.registerEntityTag("PigZombie", new AspectList().add(Aspect.UNDEAD, 4).add(Aspect.FIRE, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  58 */     ThaumcraftApi.registerEntityTag("Enderman", new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.MOTION, 2).add(Aspect.AIR, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  59 */     ThaumcraftApi.registerEntityTag("CaveSpider", new AspectList().add(Aspect.BEAST, 2).add(Aspect.DEATH, 2).add(Aspect.EARTH, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  60 */     ThaumcraftApi.registerEntityTag("Silverfish", new AspectList().add(Aspect.BEAST, 1).add(Aspect.EARTH, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  61 */     ThaumcraftApi.registerEntityTag("Blaze", new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.FIRE, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  62 */     ThaumcraftApi.registerEntityTag("LavaSlime", new AspectList().add(Aspect.WATER, 3).add(Aspect.FIRE, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  63 */     ThaumcraftApi.registerEntityTag("EnderDragon", new AspectList().add(Aspect.ELDRITCH, 20).add(Aspect.BEAST, 20).add(Aspect.ENTROPY, 20), new ThaumcraftApi.EntityTagsNBT[0]);
/*  64 */     ThaumcraftApi.registerEntityTag("WitherBoss", new AspectList().add(Aspect.UNDEAD, 20).add(Aspect.ENTROPY, 20).add(Aspect.FIRE, 15), new ThaumcraftApi.EntityTagsNBT[0]);
/*  65 */     ThaumcraftApi.registerEntityTag("Witch", new AspectList().add(Aspect.MAN, 3).add(Aspect.AURA, 2).add(Aspect.FIRE, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  66 */     ThaumcraftApi.registerEntityTag("Villager", new AspectList().add(Aspect.MAN, 3).add(Aspect.AIR, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  67 */     ThaumcraftApi.registerEntityTag("VillagerGolem", new AspectList().add(Aspect.METAL, 4).add(Aspect.EARTH, 3), new ThaumcraftApi.EntityTagsNBT[0]);
/*  68 */     ThaumcraftApi.registerEntityTag("MinecartRideable", new AspectList().add(Aspect.MECHANISM, 3).add(Aspect.AIR, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  69 */     ThaumcraftApi.registerEntityTag("MinecartChest", new AspectList().add(Aspect.MECHANISM, 3).add(Aspect.AIR, 1).add(Aspect.VOID, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  70 */     ThaumcraftApi.registerEntityTag("MinecartFurnace", new AspectList().add(Aspect.MECHANISM, 3).add(Aspect.AIR, 1).add(Aspect.FIRE, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  71 */     ThaumcraftApi.registerEntityTag("MinecartTNT", new AspectList().add(Aspect.MECHANISM, 3).add(Aspect.AIR, 1).add(Aspect.FIRE, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  72 */     ThaumcraftApi.registerEntityTag("MinecartHopper", new AspectList().add(Aspect.MECHANISM, 3).add(Aspect.AIR, 1).add(Aspect.EXCHANGE, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  73 */     ThaumcraftApi.registerEntityTag("MinecartSpawner", new AspectList().add(Aspect.MECHANISM, 3).add(Aspect.AIR, 1).add(Aspect.AURA, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  74 */     ThaumcraftApi.registerEntityTag("EnderCrystal", new AspectList().add(Aspect.ELDRITCH, 3).add(Aspect.AURA, 3).add(Aspect.LIFE, 3), new ThaumcraftApi.EntityTagsNBT[0]);
/*  75 */     ThaumcraftApi.registerEntityTag("ItemFrame", new AspectList().add(Aspect.SENSES, 3).add(Aspect.CRAFT, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  76 */     ThaumcraftApi.registerEntityTag("Painting", new AspectList().add(Aspect.SENSES, 5).add(Aspect.CRAFT, 3), new ThaumcraftApi.EntityTagsNBT[0]);
/*  77 */     ThaumcraftApi.registerEntityTag("Guardian", new AspectList().add(Aspect.BEAST, 2).add(Aspect.ELDRITCH, 1).add(Aspect.WATER, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  78 */     ThaumcraftApi.registerEntityTag("Rabbit", new AspectList().add(Aspect.BEAST, 1).add(Aspect.EARTH, 1).add(Aspect.MOTION, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  79 */     ThaumcraftApi.registerEntityTag("Endermite", new AspectList().add(Aspect.BEAST, 1).add(Aspect.ELDRITCH, 1).add(Aspect.MOTION, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*     */     
/*     */ 
/*  82 */     ThaumcraftApi.registerEntityTag("Thaumcraft.AuraNode", new AspectList().add(Aspect.AURA, 10), new ThaumcraftApi.EntityTagsNBT[0]);
/*  83 */     ThaumcraftApi.registerEntityTag("Thaumcraft.PrimalOrb", new AspectList().add(Aspect.AIR, 5).add(Aspect.ENTROPY, 10).add(Aspect.AURA, 10).add(Aspect.ENERGY, 10), new ThaumcraftApi.EntityTagsNBT[0]);
/*  84 */     ThaumcraftApi.registerEntityTag("Thaumcraft.Firebat", new AspectList().add(Aspect.BEAST, 2).add(Aspect.FLIGHT, 1).add(Aspect.FIRE, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  85 */     ThaumcraftApi.registerEntityTag("Thaumcraft.Pech", new AspectList().add(Aspect.MAN, 2).add(Aspect.AURA, 2).add(Aspect.EXCHANGE, 2).add(Aspect.DESIRE, 2), new ThaumcraftApi.EntityTagsNBT[] { new ThaumcraftApi.EntityTagsNBT("PechType", Byte.valueOf(0)) });
/*  86 */     ThaumcraftApi.registerEntityTag("Thaumcraft.Pech", new AspectList().add(Aspect.MAN, 2).add(Aspect.AURA, 2).add(Aspect.EXCHANGE, 2).add(Aspect.AVERSION, 2), new ThaumcraftApi.EntityTagsNBT[] { new ThaumcraftApi.EntityTagsNBT("PechType", Byte.valueOf(1)) });
/*  87 */     ThaumcraftApi.registerEntityTag("Thaumcraft.Pech", new AspectList().add(Aspect.MAN, 2).add(Aspect.AURA, 4).add(Aspect.EXCHANGE, 2), new ThaumcraftApi.EntityTagsNBT[] { new ThaumcraftApi.EntityTagsNBT("PechType", Byte.valueOf(2)) });
/*  88 */     ThaumcraftApi.registerEntityTag("Thaumcraft.ThaumSlime", new AspectList().add(Aspect.LIFE, 2).add(Aspect.WATER, 2).add(Aspect.FLUX, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  89 */     ThaumcraftApi.registerEntityTag("Thaumcraft.BrainyZombie", new AspectList().add(Aspect.UNDEAD, 3).add(Aspect.MAN, 1).add(Aspect.MIND, 1).add(Aspect.EARTH, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  90 */     ThaumcraftApi.registerEntityTag("Thaumcraft.GiantBrainyZombie", new AspectList().add(Aspect.UNDEAD, 4).add(Aspect.MAN, 2).add(Aspect.MIND, 1).add(Aspect.EARTH, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  91 */     ThaumcraftApi.registerEntityTag("Thaumcraft.Taintacle", new AspectList().add(Aspect.FLUX, 3).add(Aspect.WATER, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  92 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintacleTiny", new AspectList().add(Aspect.FLUX, 1).add(Aspect.WATER, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  93 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintSpider", new AspectList().add(Aspect.FLUX, 1).add(Aspect.EARTH, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  94 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintSpore", new AspectList().add(Aspect.FLUX, 2).add(Aspect.AIR, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  95 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintSwarmer", new AspectList().add(Aspect.FLUX, 2).add(Aspect.AIR, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  96 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintSwarm", new AspectList().add(Aspect.FLUX, 3).add(Aspect.AIR, 3), new ThaumcraftApi.EntityTagsNBT[0]);
/*  97 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintedPig", new AspectList().add(Aspect.FLUX, 2).add(Aspect.EARTH, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*  98 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintedRabbit", new AspectList().add(Aspect.FLUX, 2).add(Aspect.EARTH, 1).add(Aspect.MOTION, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*  99 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintedSheep", new AspectList().add(Aspect.FLUX, 2).add(Aspect.EARTH, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/* 100 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintedCow", new AspectList().add(Aspect.FLUX, 3).add(Aspect.EARTH, 3), new ThaumcraftApi.EntityTagsNBT[0]);
/* 101 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintedChicken", new AspectList().add(Aspect.FLUX, 2).add(Aspect.FLIGHT, 2).add(Aspect.AIR, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/* 102 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintedRabbit", new AspectList().add(Aspect.FLUX, 2).add(Aspect.MOTION, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/* 103 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintedVillager", new AspectList().add(Aspect.FLUX, 3).add(Aspect.AIR, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/* 104 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintedCreeper", new AspectList().add(Aspect.FLUX, 2).add(Aspect.FIRE, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/* 105 */     ThaumcraftApi.registerEntityTag("Thaumcraft.MindSpider", new AspectList().add(Aspect.FLUX, 2).add(Aspect.FIRE, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*     */     
/* 107 */     ThaumcraftApi.registerEntityTag("Thaumcraft.EldritchGuardian", new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.DEATH, 2).add(Aspect.UNDEAD, 4), new ThaumcraftApi.EntityTagsNBT[0]);
/* 108 */     ThaumcraftApi.registerEntityTag("Thaumcraft.EldritchOrb", new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.DEATH, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/* 109 */     ThaumcraftApi.registerEntityTag("Thaumcraft.CultistKnight", new AspectList().add(Aspect.ELDRITCH, 1).add(Aspect.MAN, 2).add(Aspect.AVERSION, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/* 110 */     ThaumcraftApi.registerEntityTag("Thaumcraft.CultistCleric", new AspectList().add(Aspect.ELDRITCH, 1).add(Aspect.MAN, 2).add(Aspect.AVERSION, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/* 111 */     ThaumcraftApi.registerEntityTag("Thaumcraft.EldritchCrab", new AspectList().add(Aspect.ELDRITCH, 1).add(Aspect.BEAST, 1).add(Aspect.TRAP, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/* 112 */     ThaumcraftApi.registerEntityTag("Thaumcraft.InhabitedZombie", new AspectList().add(Aspect.ELDRITCH, 1).add(Aspect.UNDEAD, 1).add(Aspect.MAN, 1), new ThaumcraftApi.EntityTagsNBT[0]);
/*     */     
/*     */ 
/* 115 */     ThaumcraftApi.registerEntityTag("Thaumcraft.EldritchWarden", new AspectList().add(Aspect.ELDRITCH, 8).add(Aspect.DEATH, 4).add(Aspect.UNDEAD, 4), new ThaumcraftApi.EntityTagsNBT[0]);
/* 116 */     ThaumcraftApi.registerEntityTag("Thaumcraft.EldritchGolem", new AspectList().add(Aspect.ELDRITCH, 8).add(Aspect.ENERGY, 4).add(Aspect.MECHANISM, 4), new ThaumcraftApi.EntityTagsNBT[0]);
/* 117 */     ThaumcraftApi.registerEntityTag("Thaumcraft.CultistLeader", new AspectList().add(Aspect.ELDRITCH, 8).add(Aspect.AVERSION, 4).add(Aspect.MAN, 4), new ThaumcraftApi.EntityTagsNBT[0]);
/* 118 */     ThaumcraftApi.registerEntityTag("Thaumcraft.TaintacleGiant", new AspectList().add(Aspect.ELDRITCH, 8).add(Aspect.WATER, 4).add(Aspect.FLUX, 4), new ThaumcraftApi.EntityTagsNBT[0]);
/*     */     
/* 120 */     for (Aspect tag : Aspect.aspects.values()) {
/* 121 */       ThaumcraftApi.registerEntityTag("Thaumcraft.Wisp", new AspectList().add(tag, 2).add(Aspect.AURA, 1).add(Aspect.AIR, 1), new ThaumcraftApi.EntityTagsNBT[] { new ThaumcraftApi.EntityTagsNBT("Type", tag.getTag()) });
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 126 */     ThaumcraftApi.registerEntityTag("Thaumcraft.Golem", new AspectList().add(Aspect.AIR, 2).add(Aspect.EARTH, 2).add(Aspect.MOTION, 2), new ThaumcraftApi.EntityTagsNBT[0]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void registerItemAspects()
/*     */   {
/* 136 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.stone, 1, 32767), new AspectList().add(Aspect.EARTH, 2));
/* 137 */     ThaumcraftApi.registerObjectTag("cobblestone", new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENTROPY, 1));
/* 138 */     ThaumcraftApi.registerObjectTag("logWood", new AspectList().add(Aspect.PLANT, 4));
/* 139 */     ThaumcraftApi.registerObjectTag("plankWood", new AspectList().add(Aspect.PLANT, 1));
/* 140 */     ThaumcraftApi.registerObjectTag("slabWood", new AspectList().add(Aspect.PLANT, 1));
/* 141 */     ThaumcraftApi.registerObjectTag("stairWood", new AspectList().add(Aspect.PLANT, 1));
/* 142 */     ThaumcraftApi.registerObjectTag("stickWood", new AspectList().add(Aspect.PLANT, 1));
/* 143 */     ThaumcraftApi.registerObjectTag("treeSapling", new AspectList().add(Aspect.PLANT, 3));
/* 144 */     ThaumcraftApi.registerObjectTag("treeLeaves", new AspectList().add(Aspect.PLANT, 1));
/*     */     
/* 146 */     String[] dyes = { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };
/*     */     
/* 148 */     for (int i = 0; i < 16; i++)
/*     */     {
/* 150 */       ThaumcraftApi.registerObjectTag(dyes[i], new AspectList().add(Aspect.SENSES, 1));
/*     */     }
/*     */     
/*     */ 
/* 154 */     ThaumcraftApi.registerObjectTag("oreLapis", new AspectList().add(Aspect.EARTH, 1).add(Aspect.SENSES, 3));
/* 155 */     ThaumcraftApi.registerObjectTag("oreDiamond", new AspectList().add(Aspect.EARTH, 1).add(Aspect.DESIRE, 3).add(Aspect.CRYSTAL, 3));
/* 156 */     ThaumcraftApi.registerObjectTag("gemDiamond", new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.DESIRE, 4));
/* 157 */     ThaumcraftApi.registerObjectTag("oreRedstone", new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENERGY, 3));
/* 158 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.lit_redstone_ore), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENERGY, 3));
/* 159 */     ThaumcraftApi.registerObjectTag("oreEmerald", new AspectList().add(Aspect.EARTH, 1).add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 3));
/* 160 */     ThaumcraftApi.registerObjectTag("gemEmerald", new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.DESIRE, 5));
/* 161 */     ThaumcraftApi.registerObjectTag("oreQuartz", new AspectList().add(Aspect.EARTH, 1).add(Aspect.CRYSTAL, 3));
/* 162 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.quartz), new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.ENERGY, 1));
/* 163 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.gold_nugget), new AspectList().add(Aspect.METAL, 1));
/* 164 */     ThaumcraftApi.registerObjectTag("nuggetIron", new AspectList().add(Aspect.METAL, 1));
/* 165 */     ThaumcraftApi.registerObjectTag("oreIron", new AspectList().add(Aspect.EARTH, 1).add(Aspect.METAL, 3));
/* 166 */     ThaumcraftApi.registerObjectTag("dustIron", new AspectList().add(Aspect.METAL, 3).add(Aspect.ENTROPY, 1));
/* 167 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.iron_ingot), new AspectList().add(Aspect.METAL, 4));
/* 168 */     ThaumcraftApi.registerObjectTag("oreGold", new AspectList().add(Aspect.EARTH, 1).add(Aspect.METAL, 2).add(Aspect.DESIRE, 1));
/* 169 */     ThaumcraftApi.registerObjectTag("dustGold", new AspectList().add(Aspect.METAL, 2).add(Aspect.ENTROPY, 1).add(Aspect.DESIRE, 1));
/* 170 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.gold_ingot), new AspectList().add(Aspect.METAL, 3).add(Aspect.DESIRE, 2));
/* 171 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.coal_ore), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENERGY, 2).add(Aspect.FIRE, 1));
/* 172 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.coal, 1, 32767), new AspectList().add(Aspect.ENERGY, 2).add(Aspect.FIRE, 2));
/* 173 */     ThaumcraftApi.registerObjectTag("dustRedstone", new AspectList().add(Aspect.ENERGY, 2));
/* 174 */     ThaumcraftApi.registerObjectTag("dustGlowstone", new AspectList().add(Aspect.SENSES, 1).add(Aspect.LIGHT, 2));
/* 175 */     ThaumcraftApi.registerObjectTag("glowstone", new AspectList().add(Aspect.SENSES, 3).add(Aspect.LIGHT, 10));
/*     */     
/* 177 */     ThaumcraftApi.registerObjectTag("nuggetCopper", new AspectList().add(Aspect.METAL, 1));
/* 178 */     ThaumcraftApi.registerObjectTag("ingotCopper", new AspectList().add(Aspect.METAL, 3).add(Aspect.EXCHANGE, 1));
/* 179 */     ThaumcraftApi.registerObjectTag("dustCopper", new AspectList().add(Aspect.METAL, 2).add(Aspect.ENTROPY, 1).add(Aspect.EXCHANGE, 1));
/* 180 */     ThaumcraftApi.registerObjectTag("oreCopper", new AspectList().add(Aspect.METAL, 2).add(Aspect.EARTH, 1).add(Aspect.EXCHANGE, 1));
/* 181 */     ThaumcraftApi.registerObjectTag("clusterCopper", new AspectList().add(Aspect.ORDER, 1).add(Aspect.METAL, 5).add(Aspect.EARTH, 1).add(Aspect.EXCHANGE, 2));
/*     */     
/* 183 */     ThaumcraftApi.registerObjectTag("nuggetTin", new AspectList().add(Aspect.METAL, 1));
/* 184 */     ThaumcraftApi.registerObjectTag("ingotTin", new AspectList().add(Aspect.METAL, 3).add(Aspect.CRYSTAL, 1));
/* 185 */     ThaumcraftApi.registerObjectTag("dustTin", new AspectList().add(Aspect.METAL, 2).add(Aspect.ENTROPY, 1).add(Aspect.CRYSTAL, 1));
/* 186 */     ThaumcraftApi.registerObjectTag("oreTin", new AspectList().add(Aspect.METAL, 3).add(Aspect.ENTROPY, 1).add(Aspect.CRYSTAL, 1));
/* 187 */     ThaumcraftApi.registerObjectTag("clusterTin", new AspectList().add(Aspect.ORDER, 1).add(Aspect.METAL, 5).add(Aspect.EARTH, 1).add(Aspect.CRYSTAL, 2));
/*     */     
/* 189 */     ThaumcraftApi.registerObjectTag("nuggetSilver", new AspectList().add(Aspect.METAL, 1));
/* 190 */     ThaumcraftApi.registerObjectTag("ingotSilver", new AspectList().add(Aspect.METAL, 3).add(Aspect.DESIRE, 1));
/* 191 */     ThaumcraftApi.registerObjectTag("dustSilver", new AspectList().add(Aspect.METAL, 2).add(Aspect.ENTROPY, 1).add(Aspect.DESIRE, 1));
/* 192 */     ThaumcraftApi.registerObjectTag("oreSilver", new AspectList().add(Aspect.METAL, 3).add(Aspect.ENTROPY, 1).add(Aspect.DESIRE, 1));
/* 193 */     ThaumcraftApi.registerObjectTag("clusterSilver", new AspectList().add(Aspect.ORDER, 1).add(Aspect.METAL, 5).add(Aspect.EARTH, 1).add(Aspect.DESIRE, 2));
/*     */     
/* 195 */     ThaumcraftApi.registerObjectTag("nuggetLead", new AspectList().add(Aspect.METAL, 1));
/* 196 */     ThaumcraftApi.registerObjectTag("ingotLead", new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 1));
/* 197 */     ThaumcraftApi.registerObjectTag("dustLead", new AspectList().add(Aspect.METAL, 2).add(Aspect.ENTROPY, 1).add(Aspect.ORDER, 1));
/* 198 */     ThaumcraftApi.registerObjectTag("oreLead", new AspectList().add(Aspect.METAL, 3).add(Aspect.ENTROPY, 1).add(Aspect.ORDER, 1));
/* 199 */     ThaumcraftApi.registerObjectTag("clusterLead", new AspectList().add(Aspect.ORDER, 1).add(Aspect.METAL, 5).add(Aspect.EARTH, 1).add(Aspect.ORDER, 2));
/*     */     
/*     */ 
/* 202 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.bedrock), new AspectList().add(Aspect.VOID, 16).add(Aspect.ENTROPY, 16).add(Aspect.EARTH, 16).add(Aspect.DARKNESS, 16));
/* 203 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.dirt, 1, 32767), new AspectList().add(Aspect.EARTH, 2));
/* 204 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.dirt, 1, 2), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT, 1));
/* 205 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.farmland, 1, 32767), new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER, 1));
/* 206 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.sand, 1, 32767), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENTROPY, 1));
/* 207 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.grass), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT, 1));
/* 208 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.clay_ball, 1, 32767), new AspectList().add(Aspect.WATER, 1).add(Aspect.EARTH, 1));
/* 209 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.end_stone), new AspectList().add(Aspect.EARTH, 1).add(Aspect.DARKNESS, 1));
/* 210 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.gravel), new AspectList().add(Aspect.EARTH, 1).add(Aspect.EARTH, 1));
/* 211 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.mycelium), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT, 1));
/* 212 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.clay, 1, 32767), new AspectList().add(Aspect.EARTH, 3).add(Aspect.WATER, 3));
/* 213 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.hardened_clay, 1, 32767), new AspectList().add(Aspect.EARTH, 4).add(Aspect.FIRE, 1));
/* 214 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.stained_hardened_clay, 1, 32767), new AspectList().add(Aspect.EARTH, 3).add(Aspect.FIRE, 1).add(Aspect.SENSES, 1));
/* 215 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.brick, 1, 32767), new AspectList().add(Aspect.EARTH, 1).add(Aspect.FIRE, 1));
/* 216 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.netherbrick, 1, 32767), new AspectList().add(Aspect.FIRE, 1));
/* 217 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.soul_sand, 1, 32767), new AspectList().add(Aspect.EARTH, 1).add(Aspect.TRAP, 1).add(Aspect.SOUL, 1));
/* 218 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.netherrack, 1, 32767), new AspectList().add(Aspect.EARTH, 1).add(Aspect.EARTH, 1).add(Aspect.FIRE, 1));
/* 219 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.nether_brick), new AspectList().add(Aspect.EARTH, 2).add(Aspect.FIRE, 1));
/* 220 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.glass, 1, 32767), new AspectList().add(Aspect.CRYSTAL, 1));
/* 221 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.stained_glass, 1, 32767), new AspectList().add(Aspect.CRYSTAL, 1));
/* 222 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.mossy_cobblestone, 1, 32767), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT, 1));
/* 223 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.obsidian, 1, 32767), new AspectList().add(Aspect.EARTH, 2).add(Aspect.FIRE, 2).add(Aspect.DARKNESS, 1));
/* 224 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.monster_egg, 1, 32767), new AspectList().add(Aspect.EARTH, 2).add(Aspect.BEAST, 1).add(Aspect.TRAP, 1));
/* 225 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.stonebrick, 1, 32767), new AspectList().add(Aspect.EARTH, 2));
/* 226 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.stonebrick, 1, 1), new AspectList(new ItemStack(Blocks.stonebrick)).remove(Aspect.EARTH, 1).add(Aspect.PLANT, 1));
/* 227 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.stonebrick, 1, 2), new AspectList(new ItemStack(Blocks.stonebrick)).remove(Aspect.EARTH, 1).add(Aspect.ENTROPY, 1));
/* 228 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.stonebrick, 1, 3), new AspectList(new ItemStack(Blocks.stonebrick)).remove(Aspect.EARTH, 1).add(Aspect.ORDER, 1));
/*     */     
/* 230 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.sandstone, 1, 1), new AspectList(new ItemStack(Blocks.sandstone)).remove(Aspect.EARTH, 1).add(Aspect.ORDER, 1));
/* 231 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.sandstone, 1, 2), new AspectList(new ItemStack(Blocks.sandstone)).remove(Aspect.EARTH, 1).add(Aspect.ORDER, 1));
/*     */     
/*     */ 
/*     */ 
/* 235 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.tallgrass, 1, 32767), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AIR, 1));
/* 236 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.double_plant, 1, 32767), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AIR, 1));
/* 237 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.waterlily, 1, 32767), new AspectList().add(Aspect.PLANT, 2).add(Aspect.WATER, 1));
/* 238 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.deadbush, 1, 32767), new AspectList().add(Aspect.PLANT, 1).add(Aspect.ENTROPY, 1));
/* 239 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.vine, 1, 32767), new AspectList().add(Aspect.PLANT, 1));
/* 240 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.wheat_seeds, 1, 32767), new AspectList().add(Aspect.PLANT, 1));
/* 241 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.melon_seeds, 1, 32767), new AspectList().add(Aspect.PLANT, 1));
/* 242 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.pumpkin_seeds, 1, 32767), new AspectList().add(Aspect.PLANT, 1));
/* 243 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.melon, 1, 32767), new AspectList().add(Aspect.LIFE, 1));
/* 244 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.nether_wart), new AspectList().add(Aspect.PLANT, 1).add(Aspect.FIRE, 1));
/*     */     
/* 246 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.red_flower, 1, 32767), new AspectList().add(Aspect.PLANT, 1).add(Aspect.LIFE, 1).add(Aspect.SENSES, 1));
/* 247 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.yellow_flower, 1, 32767), new AspectList().add(Aspect.PLANT, 1).add(Aspect.LIFE, 1).add(Aspect.SENSES, 1));
/* 248 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.cactus), new AspectList().add(Aspect.PLANT, 3).add(Aspect.WATER, 1).add(Aspect.ENTROPY, 1));
/* 249 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.brown_mushroom), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DARKNESS, 1).add(Aspect.EARTH, 1));
/* 250 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.red_mushroom), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DARKNESS, 1).add(Aspect.FIRE, 1));
/* 251 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.brown_mushroom_block, 1, 32767), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 1).add(Aspect.EARTH, 1));
/* 252 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.red_mushroom_block, 1, 32767), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 1).add(Aspect.FIRE, 1));
/* 253 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.reeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.WATER, 1).add(Aspect.AIR, 1));
/* 254 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.wheat), new AspectList().add(Aspect.PLANT, 2).add(Aspect.LIFE, 1));
/* 255 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.apple), new AspectList().add(Aspect.PLANT, 2).add(Aspect.LIFE, 1));
/* 256 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.carrot), new AspectList().add(Aspect.PLANT, 1).add(Aspect.LIFE, 1).add(Aspect.SENSES, 1));
/* 257 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.potato), new AspectList().add(Aspect.PLANT, 1).add(Aspect.LIFE, 1).add(Aspect.EARTH, 1));
/* 258 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.baked_potato), new AspectList().add(Aspect.PLANT, 1).add(Aspect.LIFE, 2));
/* 259 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.poisonous_potato), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DEATH, 1));
/* 260 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.pumpkin), new AspectList().add(Aspect.PLANT, 2));
/* 261 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.lit_pumpkin, 1, 1), new AspectList().add(Aspect.PLANT, 2).add(Aspect.LIGHT, 1));
/* 262 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.melon_block, 1, 32767), new AspectList().add(Aspect.PLANT, 2).remove(Aspect.LIFE, 4));
/* 263 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.sponge, 1, 0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.TRAP, 1).add(Aspect.VOID, 1));
/* 264 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.sponge, 1, 1), new AspectList().add(Aspect.EARTH, 1).add(Aspect.TRAP, 1).add(Aspect.VOID, 1).add(Aspect.WATER, 1));
/* 265 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.sugar), new AspectList().add(Aspect.DESIRE, 1));
/* 266 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.cake), new AspectList().add(Aspect.DESIRE, 2).add(Aspect.PLANT, 1).add(Aspect.LIFE, 4));
/* 267 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.cake), new AspectList().add(Aspect.DESIRE, 2).add(Aspect.PLANT, 1).add(Aspect.LIFE, 4));
/* 268 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.pumpkin_pie), new AspectList().add(Aspect.DESIRE, 1).add(Aspect.PLANT, 1).add(Aspect.LIFE, 2));
/*     */     
/*     */ 
/* 271 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.experience_bottle), new AspectList().add(Aspect.MIND, 8));
/* 272 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.name_tag, 1, 32767), new AspectList().add(Aspect.MIND, 2).add(Aspect.BEAST, 2));
/* 273 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.iron_horse_armor, 1, 32767), new AspectList().add(Aspect.PROTECT, 2).add(Aspect.BEAST, 2));
/* 274 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.golden_horse_armor, 1, 32767), new AspectList().add(Aspect.PROTECT, 4).add(Aspect.BEAST, 2));
/* 275 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.diamond_horse_armor, 1, 32767), new AspectList().add(Aspect.PROTECT, 6).add(Aspect.BEAST, 2));
/* 276 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.fire, 1, 32767), new AspectList().add(Aspect.FIRE, 4));
/*     */     
/* 278 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.water, 1, 32767), new AspectList().add(Aspect.WATER, 4));
/* 279 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.lava, 1, 32767), new AspectList().add(Aspect.FIRE, 3).add(Aspect.EARTH, 1));
/* 280 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.ice, 1, 32767), new AspectList().add(Aspect.COLD, 4));
/* 281 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.packed_ice, 1, 32767), new AspectList().add(Aspect.COLD, 3).add(Aspect.EARTH, 1));
/* 282 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.snowball, 1, 32767), new AspectList().add(Aspect.COLD, 1));
/* 283 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.cookie, 1, 32767), new AspectList().add(Aspect.LIFE, 1));
/* 284 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.potionitem), new AspectList().add(Aspect.WATER, 1).add(Aspect.CRYSTAL, 1));
/* 285 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.torch, 1, 32767), new AspectList().add(Aspect.LIGHT, 1));
/* 286 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.web, 1, 32767), new AspectList().add(Aspect.TRAP, 2).add(Aspect.BEAST, 1));
/* 287 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.flint, 1, 32767), new AspectList().add(Aspect.EARTH, 1).add(Aspect.TOOL, 1));
/* 288 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.string, 1, 32767), new AspectList().add(Aspect.BEAST, 1).add(Aspect.BEAST, 1));
/* 289 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.slime_ball), new AspectList().add(Aspect.WATER, 1).add(Aspect.LIFE, 1));
/* 290 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.leather, 1, 32767), new AspectList().add(Aspect.BEAST, 2).add(Aspect.PROTECT, 1));
/* 291 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.rotten_flesh, 1, 32767), new AspectList().add(Aspect.MAN, 1).add(Aspect.LIFE, 2));
/* 292 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.feather, 1, 32767), new AspectList().add(Aspect.FLIGHT, 2).add(Aspect.AIR, 1));
/* 293 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.bone, 1, 32767), new AspectList().add(Aspect.DEATH, 2).add(Aspect.LIFE, 1));
/* 294 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.egg, 1, 32767), new AspectList().add(Aspect.LIFE, 1).add(Aspect.BEAST, 1));
/* 295 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.spider_eye, 1, 32767), new AspectList().add(Aspect.SENSES, 2).add(Aspect.BEAST, 2).add(Aspect.DEATH, 1));
/* 296 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.gunpowder, 1, 32767), new AspectList().add(Aspect.FIRE, 4).add(Aspect.ENTROPY, 4));
/* 297 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.wool, 1, 32767), new AspectList().add(Aspect.BEAST, 2).add(Aspect.CRAFT, 1));
/* 298 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.fish, 1, 32767), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.WATER, 1));
/* 299 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.cooked_fish, 1, 32767), new AspectList().add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1).add(Aspect.LIFE, 2));
/* 300 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.chicken, 1, 32767), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.AIR, 1));
/* 301 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.cooked_chicken, 1, 32767), new AspectList().add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1).add(Aspect.LIFE, 2));
/* 302 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.porkchop, 1, 32767), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.EARTH, 1));
/* 303 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.cooked_porkchop, 1, 32767), new AspectList().add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1).add(Aspect.LIFE, 2));
/* 304 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.beef, 1, 32767), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.EARTH, 1));
/* 305 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.cooked_beef, 1, 32767), new AspectList().add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1).add(Aspect.LIFE, 2));
/* 306 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.mutton, 1, 32767), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.EARTH, 1));
/* 307 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.cooked_mutton, 1, 32767), new AspectList().add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1).add(Aspect.LIFE, 2));
/* 308 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.rabbit, 1, 32767), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.EARTH, 1));
/* 309 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.cooked_rabbit, 1, 32767), new AspectList().add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1).add(Aspect.LIFE, 2));
/* 310 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.rabbit_hide, 1, 32767), new AspectList().add(Aspect.BEAST, 1).add(Aspect.PROTECT, 1));
/* 311 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.rabbit_foot, 1, 32767), new AspectList().add(Aspect.BEAST, 1).add(Aspect.PROTECT, 1).add(Aspect.MOTION, 2));
/*     */     
/* 313 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.blaze_rod, 1, 32767), new AspectList().add(Aspect.FIRE, 4).add(Aspect.ENERGY, 2));
/* 314 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.saddle, 1, 32767), new AspectList().add(Aspect.BEAST, 2).add(Aspect.MOTION, 4));
/* 315 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.ender_pearl, 1, 32767), new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.MOTION, 4));
/* 316 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.ghast_tear, 1, 32767), new AspectList().add(Aspect.WATER, 1).add(Aspect.UNDEAD, 4).add(Aspect.SOUL, 4));
/* 317 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.skull, 1, 0), new AspectList().add(Aspect.DEATH, 4).add(Aspect.SOUL, 4).add(Aspect.UNDEAD, 4));
/* 318 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.skull, 1, 1), new AspectList().add(Aspect.DEATH, 4).add(Aspect.SOUL, 4).add(Aspect.UNDEAD, 4));
/* 319 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.skull, 1, 2), new AspectList().add(Aspect.DEATH, 4).add(Aspect.SOUL, 4).add(Aspect.MAN, 4));
/* 320 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.skull, 1, 3), new AspectList().add(Aspect.DEATH, 4).add(Aspect.SOUL, 4).add(Aspect.MAN, 4));
/* 321 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.skull, 1, 4), new AspectList().add(Aspect.DEATH, 4).add(Aspect.SOUL, 4).add(Aspect.ENTROPY, 2).add(Aspect.FIRE, 2));
/*     */     
/* 323 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_11), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.DESIRE, 4));
/* 324 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_13), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.WATER, 4).add(Aspect.DESIRE, 4));
/* 325 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_cat), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.BEAST, 4).add(Aspect.DESIRE, 4));
/* 326 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_chirp), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.EARTH, 4).add(Aspect.DESIRE, 4));
/* 327 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_far), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.ELDRITCH, 4).add(Aspect.DESIRE, 4));
/* 328 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_mall), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.MAN, 4).add(Aspect.DESIRE, 4));
/* 329 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_mellohi), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.CRAFT, 4).add(Aspect.DESIRE, 4));
/* 330 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_stal), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.DARKNESS, 4).add(Aspect.DESIRE, 4));
/* 331 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_strad), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.ENERGY, 4).add(Aspect.DESIRE, 4));
/* 332 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_ward), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.LIFE, 4).add(Aspect.DESIRE, 4));
/* 333 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_blocks), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.TOOL, 4).add(Aspect.DESIRE, 4));
/* 334 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.record_wait), new AspectList().add(Aspect.SENSES, 4).add(Aspect.AIR, 4).add(Aspect.TRAP, 4).add(Aspect.DESIRE, 4));
/*     */     
/* 336 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.nether_star), new AspectList().add(Aspect.ELDRITCH, 8).add(Aspect.ENERGY, 8).add(Aspect.ORDER, 8).add(Aspect.LIGHT, 8));
/*     */     
/* 338 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.chainmail_helmet, 1, 32767), new AspectList().add(Aspect.METAL, 8));
/* 339 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.chainmail_chestplate, 1, 32767), new AspectList().add(Aspect.METAL, 12));
/* 340 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.chainmail_leggings, 1, 32767), new AspectList().add(Aspect.METAL, 11));
/* 341 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.chainmail_boots, 1, 32767), new AspectList().add(Aspect.METAL, 7));
/*     */     
/* 343 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.paper), new AspectList().add(Aspect.MIND, 1));
/* 344 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.book), new AspectList().add(Aspect.MIND, 3));
/* 345 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.enchanted_book), new AspectList(new ItemStack(Items.book)));
/* 346 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.bookshelf), new AspectList().add(Aspect.MIND, 8));
/* 347 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.dragon_egg), new AspectList().add(Aspect.ELDRITCH, 8).add(Aspect.BEAST, 8).add(Aspect.DARKNESS, 8));
/* 348 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.portal, 1, 32767), new AspectList().add(Aspect.FIRE, 4).add(Aspect.MOTION, 4));
/* 349 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.end_portal, 1, 32767), new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.MOTION, 4));
/* 350 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.end_portal_frame, 1, 32767), new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.ENERGY, 2).add(Aspect.MOTION, 4));
/* 351 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.mob_spawner, 1, 32767), new AspectList().add(Aspect.BEAST, 4).add(Aspect.MOTION, 4).add(Aspect.UNDEAD, 4).add(Aspect.ENERGY, 4));
/* 352 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.prismarine_shard), new AspectList().add(Aspect.WATER, 1).add(Aspect.EARTH, 1));
/* 353 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.prismarine_crystals), new AspectList().add(Aspect.WATER, 1).add(Aspect.CRYSTAL, 1).add(Aspect.LIGHT, 1));
/*     */     
/*     */ 
/* 356 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.chest, 1, 32767), new AspectList().add(Aspect.VOID, 4));
/* 357 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.trapped_chest, 1, 32767), new AspectList().add(Aspect.TRAP, 2));
/*     */     
/* 359 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.ender_eye), new AspectList().add(Aspect.SENSES, 4));
/* 360 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.arrow), new AspectList().add(Aspect.AVERSION, 1));
/* 361 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.glass_bottle), new AspectList().add(Aspect.VOID, 1));
/*     */     
/* 363 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.golden_apple, 1, 0), new AspectList().add(Aspect.ENERGY, 2).add(Aspect.LIFE, 4));
/* 364 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.golden_apple, 1, 1), new AspectList().add(Aspect.ENERGY, 4).add(Aspect.LIFE, 8));
/* 365 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.bowl), new AspectList().add(Aspect.VOID, 1));
/* 366 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.mushroom_stew), new AspectList().add(Aspect.LIFE, 4));
/* 367 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.minecart), new AspectList().add(Aspect.MOTION, 5));
/* 368 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.iron_door), new AspectList().add(Aspect.TRAP, 1).add(Aspect.MOTION, 1));
/* 369 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.acacia_door), new AspectList().add(Aspect.TRAP, 1).add(Aspect.MOTION, 1));
/* 370 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.dark_oak_door), new AspectList().add(Aspect.TRAP, 1).add(Aspect.MOTION, 1));
/* 371 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.jungle_door), new AspectList().add(Aspect.TRAP, 1).add(Aspect.MOTION, 1));
/* 372 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.oak_door), new AspectList().add(Aspect.TRAP, 1).add(Aspect.MOTION, 1));
/* 373 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.spruce_door), new AspectList().add(Aspect.TRAP, 1).add(Aspect.MOTION, 1));
/* 374 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.birch_door), new AspectList().add(Aspect.TRAP, 1).add(Aspect.MOTION, 1));
/*     */     
/* 376 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.boat), new AspectList().add(Aspect.WATER, 4).add(Aspect.MOTION, 4));
/* 377 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.flint_and_steel, 1, 32767), new AspectList().add(Aspect.FIRE, 4));
/* 378 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.fishing_rod, 1, 32767), new AspectList().add(Aspect.WATER, 1).add(Aspect.TOOL, 1));
/*     */     
/* 380 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.bucket), new AspectList().add(Aspect.METAL, 8).add(Aspect.VOID, 1));
/* 381 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.water_bucket), new AspectList(new ItemStack(Items.bucket)).add(Aspect.WATER, 4));
/* 382 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.lava_bucket), new AspectList(new ItemStack(Items.bucket)).add(Aspect.FIRE, 4).add(Aspect.EARTH, 1));
/* 383 */     ThaumcraftApi.registerObjectTag(new ItemStack(Items.milk_bucket), new AspectList(new ItemStack(Items.bucket)).add(Aspect.LIFE, 2).add(Aspect.BEAST, 2).add(Aspect.WATER, 2));
/*     */     
/* 385 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.brewing_stand), new AspectList().add(Aspect.CRAFT, 2).add(Aspect.WATER, 2));
/* 386 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.stone_button), new AspectList().add(Aspect.MECHANISM, 1));
/* 387 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.rail, 1, 32767), new AspectList().add(Aspect.METAL, 1).add(Aspect.MOTION, 1));
/* 388 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.detector_rail, 1, 32767), new AspectList().add(Aspect.MECHANISM, 1).add(Aspect.SENSES, 1));
/* 389 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.golden_rail, 1, 32767), new AspectList().add(Aspect.MECHANISM, 1).add(Aspect.ENERGY, 1));
/* 390 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.activator_rail, 1, 32767), new AspectList().add(Aspect.MECHANISM, 2));
/* 391 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.acacia_fence_gate, 1, 32767), new AspectList().add(Aspect.MOTION, 1).add(Aspect.MECHANISM, 1));
/* 392 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.dark_oak_fence_gate, 1, 32767), new AspectList().add(Aspect.MOTION, 1).add(Aspect.MECHANISM, 1));
/* 393 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.jungle_fence_gate, 1, 32767), new AspectList().add(Aspect.MOTION, 1).add(Aspect.MECHANISM, 1));
/* 394 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.oak_fence_gate, 1, 32767), new AspectList().add(Aspect.MOTION, 1).add(Aspect.MECHANISM, 1));
/* 395 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.spruce_fence_gate, 1, 32767), new AspectList().add(Aspect.MOTION, 1).add(Aspect.MECHANISM, 1));
/* 396 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.birch_fence_gate, 1, 32767), new AspectList().add(Aspect.MOTION, 1).add(Aspect.MECHANISM, 1));
/* 397 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.wooden_pressure_plate, 1, 32767), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MECHANISM, 1).add(Aspect.SENSES, 1));
/* 398 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.stone_pressure_plate, 1, 32767), new AspectList().add(Aspect.EARTH, 2).add(Aspect.MECHANISM, 1).add(Aspect.SENSES, 1));
/* 399 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.light_weighted_pressure_plate, 1, 32767), new AspectList().add(Aspect.MECHANISM, 1).add(Aspect.SENSES, 1));
/* 400 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.heavy_weighted_pressure_plate, 1, 32767), new AspectList().add(Aspect.MECHANISM, 1).add(Aspect.SENSES, 1));
/* 401 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.lever, 1, 32767), new AspectList().add(Aspect.MECHANISM, 1));
/* 402 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.redstone_torch, 1, 32767), new AspectList().add(Aspect.ENERGY, 1));
/* 403 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.piston, 1, 32767), new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.MOTION, 4));
/* 404 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.sticky_piston, 1, 32767), new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.MOTION, 4));
/* 405 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.jukebox), new AspectList().add(Aspect.SENSES, 4).add(Aspect.MECHANISM, 2).add(Aspect.AIR, 4));
/* 406 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.noteblock), new AspectList().add(Aspect.SENSES, 4).add(Aspect.MECHANISM, 1).add(Aspect.AIR, 4));
/* 407 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.trapdoor, 1, 32767), new AspectList().add(Aspect.MOTION, 1));
/* 408 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.lit_furnace, 1, 32767), new AspectList().add(Aspect.FIRE, 4));
/* 409 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.furnace, 1, 32767), new AspectList().add(Aspect.FIRE, 2));
/* 410 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.enchanting_table), new AspectList().add(Aspect.ENERGY, 8).add(Aspect.CRAFT, 4));
/* 411 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.crafting_table), new AspectList().add(Aspect.CRAFT, 4));
/* 412 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.clock), new AspectList().add(Aspect.MECHANISM, 2));
/*     */     
/* 414 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.anvil, 1, 32767), new AspectList().add(Aspect.METAL, 64).add(Aspect.CRAFT, 2).add(Aspect.TOOL, 2));
/*     */     
/*     */ 
/* 417 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.beacon), new AspectList().add(Aspect.AURA, 2).add(Aspect.ENERGY, 2).add(Aspect.EXCHANGE, 2));
/* 418 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.wooden_button, 1, 32767), new AspectList().add(Aspect.MECHANISM, 1));
/* 419 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.carrot_on_a_stick, 1, 32767), new AspectList().add(Aspect.MOTION, 1).add(Aspect.DESIRE, 2));
/*     */     
/* 421 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.flower_pot), new AspectList().add(Aspect.VOID, 1).add(Aspect.PLANT, 1));
/* 422 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Items.golden_carrot), new AspectList().add(Aspect.SENSES, 2));
/* 423 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.ender_chest, 1, 32767), new AspectList().merge(Aspect.EXCHANGE, 2).merge(Aspect.MOTION, 2).merge(Aspect.VOID, 4));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 428 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.hopper, 1, 32767), new AspectList().merge(Aspect.MECHANISM, 1).merge(Aspect.EXCHANGE, 1).merge(Aspect.VOID, 1));
/* 429 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.dropper, 1, 32767), new AspectList().merge(Aspect.MECHANISM, 1).merge(Aspect.EXCHANGE, 1).merge(Aspect.VOID, 1));
/* 430 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.dispenser, 1, 32767), new AspectList().merge(Aspect.MECHANISM, 1).merge(Aspect.EXCHANGE, 1).merge(Aspect.VOID, 1));
/* 431 */     ThaumcraftApi.registerObjectTag(new ItemStack(Blocks.tripwire_hook, 1, 32767), new AspectList().add(Aspect.SENSES, 1).add(Aspect.MECHANISM, 1).add(Aspect.TRAP, 1));
/*     */     
/* 433 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(Blocks.daylight_detector, 1, 32767), new AspectList().merge(Aspect.SENSES, 2).merge(Aspect.LIGHT, 3).merge(Aspect.MECHANISM, 1));
/*     */     
/*     */ 
/* 436 */     Map lhm = new LinkedHashMap();
/* 437 */     for (int var4 = 1; var4 <= 32767; var4++)
/*     */     {
/* 439 */       List var5 = PotionHelper.getPotionEffects(var4, false);
/* 440 */       if ((var5 != null) && (!var5.isEmpty()))
/*     */       {
/* 442 */         lhm.put(var5, Integer.valueOf(var4));
/*     */       }
/*     */     }
/* 445 */     Iterator var6 = lhm.values().iterator();
/* 446 */     while (var6.hasNext())
/*     */     {
/* 448 */       int var7 = ((Integer)var6.next()).intValue();
/* 449 */       ThaumcraftApi.registerObjectTag(new ItemStack(Items.potionitem, 1, var7), new AspectList(new ItemStack(Items.potionitem)));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 454 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.grassAmbient), new AspectList().add(Aspect.EARTH, 1).add(Aspect.PLANT, 1).add(Aspect.LIGHT, 1));
/* 455 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.golemPlacer, 1, 32767), new AspectList().add(Aspect.MOTION, 3).add(Aspect.MAN, 3).add(Aspect.MECHANISM, 3));
/* 456 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(BlocksTC.tableWood), new AspectList().add(Aspect.TOOL, 1));
/* 457 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(BlocksTC.tableStone), new AspectList().add(Aspect.TOOL, 1));
/* 458 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.arcaneWorkbench), new AspectList(new ItemStack(BlocksTC.tableWood)).add(Aspect.CRAFT, 4));
/* 459 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.researchTable), new AspectList(new ItemStack(BlocksTC.tableWood)).add(Aspect.MIND, 4));
/* 460 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.tripleMeatTreat), new AspectList().add(Aspect.LIFE, 3).add(Aspect.DESIRE, 1));
/* 461 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.bucketPure), new AspectList().add(Aspect.MIND, 5).add(Aspect.ORDER, 5).add(Aspect.METAL, 8));
/*     */     
/* 463 */     ThaumcraftApi.registerObjectTag("clusterIron", new AspectList().add(Aspect.ORDER, 1).add(Aspect.METAL, 6).add(Aspect.EARTH, 1));
/* 464 */     ThaumcraftApi.registerObjectTag("clusterGold", new AspectList().add(Aspect.ORDER, 1).add(Aspect.METAL, 4).add(Aspect.EARTH, 1).add(Aspect.DESIRE, 2));
/* 465 */     ThaumcraftApi.registerObjectTag("clusterCinnabar", new AspectList().add(Aspect.ORDER, 1).add(Aspect.METAL, 4).add(Aspect.EARTH, 1).add(Aspect.EXCHANGE, 4).add(Aspect.DEATH, 1));
/* 466 */     ThaumcraftApi.registerObjectTag("nuggetThaumium", new AspectList().add(Aspect.METAL, 1));
/* 467 */     ThaumcraftApi.registerObjectTag("nuggetVoid", new AspectList().add(Aspect.METAL, 1));
/* 468 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.nuggets, 1, 5), new AspectList().add(Aspect.METAL, 1));
/* 469 */     ThaumcraftApi.registerObjectTag("nuggetBrass", new AspectList().add(Aspect.METAL, 1));
/* 470 */     ThaumcraftApi.registerObjectTag("oreCinnabar", new AspectList().add(Aspect.EARTH, 1).add(Aspect.METAL, 2).add(Aspect.EXCHANGE, 2).add(Aspect.DEATH, 1));
/* 471 */     ThaumcraftApi.registerObjectTag("oreAmber", new AspectList().add(Aspect.EARTH, 1).add(Aspect.TRAP, 3).add(Aspect.CRYSTAL, 2));
/*     */     
/* 473 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(BlocksTC.nitor, 1, 32767), new AspectList(new ItemStack(BlocksTC.nitor, 1, 4)));
/*     */     
/* 475 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.crystalAir, 1, 32767), new AspectList().add(Aspect.AIR, 4).add(Aspect.CRYSTAL, 2));
/* 476 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.crystalFire, 1, 32767), new AspectList().add(Aspect.FIRE, 4).add(Aspect.CRYSTAL, 2));
/* 477 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.crystalWater, 1, 32767), new AspectList().add(Aspect.WATER, 4).add(Aspect.CRYSTAL, 2));
/* 478 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.crystalEarth, 1, 32767), new AspectList().add(Aspect.EARTH, 4).add(Aspect.CRYSTAL, 2));
/* 479 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.crystalOrder, 1, 32767), new AspectList().add(Aspect.ORDER, 4).add(Aspect.CRYSTAL, 2));
/* 480 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.crystalEntropy, 1, 32767), new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.CRYSTAL, 2));
/* 481 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.crystalTaint, 1, 32767), new AspectList().add(Aspect.FLUX, 4).add(Aspect.CRYSTAL, 2));
/*     */     
/* 483 */     ThaumcraftApi.registerObjectTag("quicksilver", new AspectList().add(Aspect.METAL, 3).add(Aspect.DEATH, 1).add(Aspect.EXCHANGE, 2));
/* 484 */     ThaumcraftApi.registerObjectTag("gemAmber", new AspectList().add(Aspect.TRAP, 2).add(Aspect.CRYSTAL, 2));
/*     */     
/* 486 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.taintFibre), new AspectList().add(Aspect.PLANT, 1).add(Aspect.FLUX, 3));
/* 487 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.taintBlock, 1, 0), new AspectList().add(Aspect.LIFE, 1).add(Aspect.FLUX, 1));
/* 488 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.taintBlock, 1, 1), new AspectList().add(Aspect.EARTH, 1).add(Aspect.FLUX, 1));
/* 489 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.taintBlock, 1, 2), new AspectList().add(Aspect.AURA, 1).add(Aspect.WATER, 1).add(Aspect.FLUX, 2));
/* 490 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.taintBlock, 1, 3), new AspectList().add(Aspect.EARTH, 2).add(Aspect.FLUX, 1));
/* 491 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.taintFeature, 1, 0), new AspectList().add(Aspect.AURA, 1).add(Aspect.BEAST, 1).add(Aspect.FLUX, 2));
/* 492 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.taintLog, 1, 0), new AspectList().add(Aspect.PLANT, 1).add(Aspect.FLUX, 1));
/*     */     
/* 494 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.log, 1, 0), new AspectList().add(Aspect.PLANT, 3).add(Aspect.LIFE, 1));
/* 495 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.log, 1, 1), new AspectList().add(Aspect.PLANT, 3).add(Aspect.AURA, 1));
/* 496 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.leaf, 1, 0), new AspectList().add(Aspect.PLANT, 1));
/* 497 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.leaf, 1, 1), new AspectList().add(Aspect.PLANT, 1));
/* 498 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.sapling, 1, 0), new AspectList().add(Aspect.PLANT, 2).add(Aspect.LIFE, 2));
/* 499 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.sapling, 1, 1), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AURA, 2));
/* 500 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.shimmerleaf), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AURA, 2).add(Aspect.ENERGY, 1));
/* 501 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.cinderpearl), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AURA, 1).add(Aspect.FIRE, 2));
/* 502 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.vishroom), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 1).add(Aspect.AURA, 1).add(Aspect.ENTROPY, 1));
/*     */     
/* 504 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 0), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENERGY, 1));
/* 505 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 1), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ENERGY, 1));
/* 506 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 2), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ELDRITCH, 1));
/* 507 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 3), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ELDRITCH, 1));
/* 508 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 4), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ELDRITCH, 1));
/* 509 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 5), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ELDRITCH, 1));
/* 510 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 6), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ELDRITCH, 1).add(Aspect.LIFE, 1));
/* 511 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 7), new AspectList().add(Aspect.EARTH, 1).add(Aspect.ELDRITCH, 1).add(Aspect.LIFE, 1).add(Aspect.LIGHT, 1));
/* 512 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 10), new AspectList().add(Aspect.METAL, 1).add(Aspect.ELDRITCH, 1).add(Aspect.TRAP, 1));
/* 513 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 11), new AspectList().add(Aspect.METAL, 1).add(Aspect.ELDRITCH, 1).add(Aspect.MIND, 1));
/* 514 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 12), new AspectList().add(Aspect.VOID, 32).add(Aspect.ENTROPY, 16).add(Aspect.DARKNESS, 16));
/* 515 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.stone, 1, 13), new AspectList().add(Aspect.EARTH, 1).add(Aspect.VOID, 1));
/*     */     
/* 517 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.brain), new AspectList().add(Aspect.LIFE, 2).add(Aspect.MIND, 4).add(Aspect.UNDEAD, 2));
/*     */     
/* 519 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.knowledgeFragment), new AspectList().add(Aspect.MIND, 8));
/* 520 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.tainted, 1, 0), new AspectList().add(Aspect.FLUX, 2).add(Aspect.WATER, 1).add(Aspect.AURA, 1));
/* 521 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.tainted, 1, 1), new AspectList().add(Aspect.FLUX, 2).add(Aspect.DESIRE, 1).add(Aspect.AURA, 1));
/*     */     
/* 523 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.coin), new AspectList().add(Aspect.DESIRE, 1));
/* 524 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.lootBag, 1, 0), new AspectList().add(Aspect.DESIRE, 4));
/* 525 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.lootBag, 1, 1), new AspectList().add(Aspect.DESIRE, 8));
/* 526 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.lootBag, 1, 2), new AspectList().add(Aspect.DESIRE, 16));
/* 527 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.lootUrn, 1, 0), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.EARTH, 2));
/* 528 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.lootUrn, 1, 1), new AspectList().add(Aspect.DESIRE, 8).add(Aspect.EARTH, 2));
/* 529 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.lootUrn, 1, 2), new AspectList().add(Aspect.DESIRE, 16).add(Aspect.EARTH, 2));
/* 530 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.lootCrate, 1, 0), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.PLANT, 2));
/* 531 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.lootCrate, 1, 1), new AspectList().add(Aspect.DESIRE, 8).add(Aspect.PLANT, 2));
/* 532 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.lootCrate, 1, 2), new AspectList().add(Aspect.DESIRE, 16).add(Aspect.PLANT, 2));
/*     */     
/* 534 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.chunks, 1, 32767), new AspectList().add(Aspect.LIFE, 1));
/* 535 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(ItemsTC.tripleMeatTreat, 1, 32767), new AspectList().add(Aspect.LIFE, 1).remove(Aspect.LIFE, 1));
/*     */     
/* 537 */     ThaumcraftApi.registerObjectTag("shardAir", new AspectList().add(Aspect.AIR, 2).add(Aspect.CRYSTAL, 1));
/* 538 */     ThaumcraftApi.registerObjectTag("shardFire", new AspectList().add(Aspect.FIRE, 2).add(Aspect.CRYSTAL, 1));
/* 539 */     ThaumcraftApi.registerObjectTag("shardWater", new AspectList().add(Aspect.WATER, 2).add(Aspect.CRYSTAL, 1));
/* 540 */     ThaumcraftApi.registerObjectTag("shardEarth", new AspectList().add(Aspect.EARTH, 2).add(Aspect.CRYSTAL, 1));
/* 541 */     ThaumcraftApi.registerObjectTag("shardOrder", new AspectList().add(Aspect.ORDER, 2).add(Aspect.CRYSTAL, 1));
/* 542 */     ThaumcraftApi.registerObjectTag("shardEntropy", new AspectList().add(Aspect.ENTROPY, 2).add(Aspect.CRYSTAL, 1));
/* 543 */     ThaumcraftApi.registerObjectTag("shardTainted", new AspectList().add(Aspect.FLUX, 2).add(Aspect.CRYSTAL, 1));
/* 544 */     ThaumcraftApi.registerObjectTag("shardBalanced", new AspectList().add(Aspect.AIR, 2).add(Aspect.FIRE, 2).add(Aspect.WATER, 2).add(Aspect.EARTH, 2).add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2).add(Aspect.CRYSTAL, 1));
/* 545 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.salisMundus), new AspectList(new ItemStack(ItemsTC.shard, 1, 6)).add(Aspect.ENERGY, 2).remove(Aspect.CRYSTAL));
/*     */     
/* 547 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.crucible), new AspectList(new ItemStack(Items.cauldron, 1, 32767)).add(Aspect.CRAFT, 4).add(Aspect.WATER, 4));
/* 548 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.candle, 1, 32767), new AspectList().add(Aspect.LIGHT, 1).add(Aspect.LIFE, 1));
/*     */     
/* 550 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.thaumonomicon, 1, 32767), new AspectList(new ItemStack(Blocks.bookshelf)).merge(Aspect.MIND, 4));
/*     */     
/* 552 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.pedestal, 1, 0), new AspectList().add(Aspect.EARTH, 2).add(Aspect.AIR, 5));
/* 553 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.pedestal, 1, 1), new AspectList().add(Aspect.EARTH, 2).add(Aspect.ELDRITCH, 5));
/* 554 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.pedestal, 1, 2), new AspectList().add(Aspect.EARTH, 2).add(Aspect.ELDRITCH, 5));
/*     */     
/*     */ 
/* 557 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.phial, 1, 0), new AspectList().add(Aspect.VOID, 1));
/* 558 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.phial, 1, 1), new AspectList());
/* 559 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.wispyEssence, 1, 0), new AspectList().add(Aspect.AURA, 1));
/* 560 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crystalEssence, 1, 0), new AspectList());
/*     */     
/* 562 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(ItemsTC.primalArrows, 1, 0), new AspectList().add(Aspect.AVERSION, 1).add(Aspect.AIR, 1));
/* 563 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(ItemsTC.primalArrows, 1, 1), new AspectList().add(Aspect.AVERSION, 1).add(Aspect.FIRE, 1));
/* 564 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(ItemsTC.primalArrows, 1, 2), new AspectList().add(Aspect.AVERSION, 1).add(Aspect.WATER, 1));
/* 565 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(ItemsTC.primalArrows, 1, 3), new AspectList().add(Aspect.AVERSION, 1).add(Aspect.EARTH, 1));
/* 566 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(ItemsTC.primalArrows, 1, 4), new AspectList().add(Aspect.AVERSION, 1).add(Aspect.ORDER, 1));
/* 567 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(ItemsTC.primalArrows, 1, 5), new AspectList().add(Aspect.AVERSION, 1).add(Aspect.ENTROPY, 1));
/* 568 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(ItemsTC.goggles, 1, 32767), new AspectList().add(Aspect.SENSES, 4));
/* 569 */     ThaumcraftApi.registerComplexObjectTag(new ItemStack(BlocksTC.arcaneEar), new AspectList().add(Aspect.SENSES, 4));
/*     */     
/*     */ 
/* 572 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.ringRunic, 1, 0), new AspectList().add(Aspect.METAL, 1).add(Aspect.PROTECT, 3).add(Aspect.ENERGY, 3));
/* 573 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.amuletVis, 1, 0), new AspectList().add(Aspect.AURA, 2).add(Aspect.METAL, 1).add(Aspect.ENERGY, 5));
/* 574 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.baubles, 1, 3), new AspectList().add(Aspect.AURA, 2).add(Aspect.METAL, 1).add(Aspect.AIR, 5));
/* 575 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.baubles, 1, 4), new AspectList().add(Aspect.AURA, 2).add(Aspect.METAL, 1).add(Aspect.EARTH, 5));
/* 576 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.baubles, 1, 5), new AspectList().add(Aspect.AURA, 2).add(Aspect.METAL, 1).add(Aspect.FIRE, 5));
/* 577 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.baubles, 1, 6), new AspectList().add(Aspect.AURA, 2).add(Aspect.METAL, 1).add(Aspect.WATER, 5));
/* 578 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.baubles, 1, 7), new AspectList().add(Aspect.AURA, 2).add(Aspect.METAL, 1).add(Aspect.ORDER, 5));
/* 579 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.baubles, 1, 8), new AspectList().add(Aspect.AURA, 2).add(Aspect.METAL, 1).add(Aspect.ENTROPY, 5));
/* 580 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.focusPech), new AspectList().add(Aspect.ENERGY, 5).add(Aspect.ENTROPY, 5).add(Aspect.ELDRITCH, 5).add(Aspect.AVERSION, 5));
/* 581 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.focusShard), new AspectList().add(Aspect.ENERGY, 5).add(Aspect.FLUX, 5).add(Aspect.ELDRITCH, 5).add(Aspect.AVERSION, 5));
/* 582 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonPlateChest, 1, 32767), new AspectList().add(Aspect.METAL, 5).add(Aspect.ELDRITCH, 1));
/* 583 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonPraetorChest, 1, 32767), new AspectList().add(Aspect.METAL, 5).add(Aspect.ELDRITCH, 2));
/* 584 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonRobeChest, 1, 32767), new AspectList().add(Aspect.METAL, 3).add(Aspect.BEAST, 1).add(Aspect.ELDRITCH, 1));
/* 585 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonPlateLegs, 1, 32767), new AspectList().add(Aspect.METAL, 5).add(Aspect.ELDRITCH, 1));
/* 586 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonPraetorLegs, 1, 32767), new AspectList().add(Aspect.METAL, 5).add(Aspect.ELDRITCH, 2));
/* 587 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonRobeLegs, 1, 32767), new AspectList().add(Aspect.METAL, 3).add(Aspect.BEAST, 1).add(Aspect.ELDRITCH, 1));
/* 588 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonPlateHelm, 1, 32767), new AspectList().add(Aspect.METAL, 5).add(Aspect.ELDRITCH, 1));
/* 589 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonPraetorHelm, 1, 32767), new AspectList().add(Aspect.METAL, 5).add(Aspect.ELDRITCH, 2));
/* 590 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonRobeHelm, 1, 32767), new AspectList().add(Aspect.METAL, 3).add(Aspect.BEAST, 1).add(Aspect.ELDRITCH, 1));
/* 591 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonBoots, 1, 32767), new AspectList().add(Aspect.METAL, 4).add(Aspect.ELDRITCH, 1));
/* 592 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonBlade, 1, 32767), new AspectList().add(Aspect.METAL, 4).add(Aspect.ELDRITCH, 1).add(Aspect.DEATH, 1));
/* 593 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.banner, 1, 1), new AspectList().add(Aspect.ELDRITCH, 1).add(Aspect.PLANT, 2).add(Aspect.BEAST, 1));
/*     */     
/*     */ 
/* 596 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.eldritchEye), new AspectList().add(Aspect.ELDRITCH, 5).add(Aspect.AURA, 3).add(Aspect.SENSES, 3).add(Aspect.SOUL, 3));
/* 597 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.crimsonRites), new AspectList().add(Aspect.MIND, 5).add(Aspect.ELDRITCH, 3).add(Aspect.SOUL, 3));
/* 598 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.runedTablet), new AspectList().add(Aspect.TRAP, 4).add(Aspect.MIND, 4).add(Aspect.ENERGY, 2));
/* 599 */     ThaumcraftApi.registerObjectTag(new ItemStack(ItemsTC.primordialPearl), new AspectList().add(Aspect.AIR, 16).add(Aspect.EARTH, 16).add(Aspect.FIRE, 16).add(Aspect.WATER, 16).add(Aspect.ORDER, 16).add(Aspect.ENTROPY, 16).add(Aspect.FLUX, 16));
/* 600 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.eldritch, 1, 0), new AspectList().add(Aspect.VOID, 4).add(Aspect.ELDRITCH, 4));
/* 601 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.eldritch, 1, 1), new AspectList().add(Aspect.VOID, 4).add(Aspect.ELDRITCH, 4));
/* 602 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.eldritch, 1, 2), new AspectList().add(Aspect.VOID, 4).add(Aspect.ELDRITCH, 4));
/* 603 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.eldritch, 1, 3), new AspectList().add(Aspect.VOID, 4).add(Aspect.ELDRITCH, 4));
/* 604 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.eldritch, 1, 4), new AspectList().add(Aspect.VOID, 4).add(Aspect.ELDRITCH, 4).add(Aspect.MECHANISM, 2));
/* 605 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.eldritch, 1, 5), new AspectList().add(Aspect.VOID, 4).add(Aspect.ELDRITCH, 4));
/* 606 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.eldritch, 1, 6), new AspectList().add(Aspect.VOID, 4).add(Aspect.ELDRITCH, 4).add(Aspect.MOTION, 4));
/* 607 */     ThaumcraftApi.registerObjectTag(new ItemStack(BlocksTC.eldritch, 1, 7), new AspectList().add(Aspect.VOID, 4).add(Aspect.ELDRITCH, 4).add(Aspect.BEAST, 4));
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\config\ConfigAspects.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */