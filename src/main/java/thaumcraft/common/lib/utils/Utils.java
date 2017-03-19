/*     */ package thaumcraft.common.lib.utils;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import io.netty.buffer.ByteBufOutputStream;
/*     */ import io.netty.handler.codec.EncoderException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemDye;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTSizeTracker;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.biome.WorldChunkManager;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.internal.WeightedRandomLoot;
/*     */ import thaumcraft.api.internal.WorldCoordinates;
/*     */ import thaumcraft.api.items.ItemGenericEssentiaContainer;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.misc.PacketBiomeChange;
/*     */ 
/*     */ public class Utils
/*     */ {
/*     */   public static boolean isChunkLoaded(World world, int x, int z)
/*     */   {
/*  57 */     int xx = x / 16;
/*  58 */     int zz = z / 16;
/*  59 */     return world.getChunkProvider().chunkExists(xx, zz);
/*     */   }
/*     */   
/*     */   public static boolean useBonemealAtLoc(World world, EntityPlayer player, BlockPos pos)
/*     */   {
/*  64 */     ItemStack is = new ItemStack(Items.dye, 1, 15);
/*  65 */     ((ItemDye)Items.dye);return ItemDye.applyBonemeal(is, world, pos, player);
/*     */   }
/*     */   
/*     */   public static boolean hasColor(byte[] colors) {
/*  69 */     for (byte col : colors)
/*  70 */       if (col >= 0)
/*  71 */         return true;
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isEETransmutionItem(Item item) {
/*     */     try {
/*  77 */       String itemClass = "com.pahimar.ee3.item.ITransmutationStone";
/*  78 */       Class ee = Class.forName(itemClass);
/*  79 */       if (ee.isAssignableFrom(item.getClass())) {
/*  80 */         return true;
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {}
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   public static void copyFile(File sourceFile, File destFile) throws IOException {
/*  88 */     if (!destFile.exists()) {
/*  89 */       destFile.createNewFile();
/*     */     }
/*     */     
/*  92 */     FileChannel source = null;
/*  93 */     FileChannel destination = null;
/*     */     try
/*     */     {
/*  96 */       source = new FileInputStream(sourceFile).getChannel();
/*  97 */       destination = new FileOutputStream(destFile).getChannel();
/*  98 */       destination.transferFrom(source, 0L, source.size());
/*     */     } finally {
/* 100 */       if (source != null) {
/* 101 */         source.close();
/*     */       }
/* 103 */       if (destination != null) {
/* 104 */         destination.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 109 */   public static HashMap<List<Object>, ItemStack> specialMiningResult = new HashMap();
/* 110 */   public static HashMap<List<Object>, Float> specialMiningChance = new HashMap();
/*     */   
/*     */   public static void addSpecialMiningResult(ItemStack in, ItemStack out, float chance) {
/* 113 */     specialMiningResult.put(Arrays.asList(new Object[] { in.getItem(), Integer.valueOf(in.getItemDamage()) }), out);
/* 114 */     specialMiningChance.put(Arrays.asList(new Object[] { in.getItem(), Integer.valueOf(in.getItemDamage()) }), Float.valueOf(chance));
/*     */   }
/*     */   
/*     */   public static ItemStack findSpecialMiningResult(ItemStack is, float chance, Random rand) {
/* 118 */     ItemStack dropped = is.copy();
/* 119 */     float r = rand.nextFloat();
/* 120 */     List ik = Arrays.asList(new Object[] { is.getItem(), Integer.valueOf(is.getItemDamage()) });
/* 121 */     if ((specialMiningResult.containsKey(ik)) && (r <= chance * ((Float)specialMiningChance.get(ik)).floatValue())) {
/* 122 */       dropped = ((ItemStack)specialMiningResult.get(ik)).copy();
/* 123 */       dropped.stackSize *= is.stackSize;
/*     */     }
/* 125 */     return dropped;
/*     */   }
/*     */   
/*     */   public static float clamp_float(float par0, float par1, float par2) {
/* 129 */     return par0 > par2 ? par2 : par0 < par1 ? par1 : par0;
/*     */   }
/*     */   
/*     */   public static void setBiomeAt(World world, BlockPos pos, BiomeGenBase biome) {
/* 133 */     setBiomeAt(world, pos, biome, true);
/*     */   }
/*     */   
/*     */   public static void setBiomeAt(World world, BlockPos pos, BiomeGenBase biome, boolean sync) {
/* 137 */     if (biome == null)
/* 138 */       return;
/* 139 */     Chunk chunk = world.getChunkFromBlockCoords(pos);
/* 140 */     byte[] array = chunk.getBiomeArray();
/* 141 */     array[((pos.getZ() & 0xF) << 4 | pos.getX() & 0xF)] = ((byte)(biome.biomeID & 0xFF));
/* 142 */     chunk.setBiomeArray(array);
/* 143 */     if ((sync) && (!world.isRemote)) {
/* 144 */       PacketHandler.INSTANCE.sendToAllAround(new PacketBiomeChange(pos.getX(), pos.getZ(), (short)biome.biomeID), new NetworkRegistry.TargetPoint(world.provider.getDimensionId(), pos.getX(), world.getHeight(pos).getY(), pos.getZ(), 32.0D));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean resetBiomeAt(World world, BlockPos pos)
/*     */   {
/* 151 */     return resetBiomeAt(world, pos, true);
/*     */   }
/*     */   
/*     */   public static boolean resetBiomeAt(World world, BlockPos pos, boolean sync) {
/* 155 */     BiomeGenBase[] biomesForGeneration = null;
/* 156 */     biomesForGeneration = world.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, pos.getX(), pos.getZ(), 1, 1);
/* 157 */     if ((biomesForGeneration != null) && (biomesForGeneration[0] != null)) {
/* 158 */       BiomeGenBase biome = biomesForGeneration[0];
/* 159 */       if (biome.biomeID != world.getBiomeGenForCoords(pos).biomeID) {
/* 160 */         setBiomeAt(world, pos, biome, sync);
/* 161 */         return true;
/*     */       }
/*     */     }
/* 164 */     return false;
/*     */   }
/*     */   
/* 167 */   public static final String[] colorNames = { "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
/*     */   
/*     */ 
/*     */ 
/* 171 */   public static final int[] colors = { 15790320, 15435844, 12801229, 6719955, 14602026, 4312372, 14188952, 4408131, 10526880, 2651799, 8073150, 2437522, 5320730, 3887386, 11743532, 1973019 };
/*     */   
/*     */ 
/*     */ 
/* 175 */   public static ArrayList<List> oreDictLogs = new ArrayList();
/*     */   
/* 177 */   public static boolean isWoodLog(IBlockAccess world, BlockPos pos) { Block bi = world.getBlockState(pos).getBlock();
/* 178 */     if ((bi.isWood(world, pos)) || (bi.canSustainLeaves(world, pos))) return true;
/* 179 */     if (oreDictLogs.contains(Arrays.asList(new Object[] { bi, Integer.valueOf(bi.getMetaFromState(world.getBlockState(pos))) })))
/* 180 */       return true;
/* 181 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isOreBlock(World world, BlockPos pos)
/*     */   {
/* 186 */     IBlockState bi = world.getBlockState(pos);
/* 187 */     if ((bi.getBlock() != Blocks.air) && (bi.getBlock() != Blocks.bedrock)) {
/* 188 */       int md = bi.getBlock().getDamageValue(world, pos);
/* 189 */       ItemStack is = new ItemStack(bi.getBlock(), 1, md);
/* 190 */       if ((is == null) || (is.getItem() == null)) return false;
/* 191 */       int[] od = OreDictionary.getOreIDs(is);
/* 192 */       if ((od != null) && (od.length > 0)) {
/* 193 */         for (int id : od) {
/* 194 */           if ((OreDictionary.getOreName(id) != null) && (OreDictionary.getOreName(id).toUpperCase().contains("ORE")))
/*     */           {
/* 196 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 201 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public static int setNibble(int data, int nibble, int nibbleIndex)
/*     */   {
/* 207 */     int shift = nibbleIndex * 4;
/* 208 */     return data & (15 << shift ^ 0xFFFFFFFF) | nibble << shift;
/*     */   }
/*     */   
/*     */   public static int getNibble(int data, int nibbleIndex) {
/* 212 */     return data >> (nibbleIndex << 2) & 0xF;
/*     */   }
/*     */   
/*     */   public static boolean getBit(int value, int bit) {
/* 216 */     return (value & 1 << bit) != 0;
/*     */   }
/*     */   
/*     */   public static int setBit(int value, int bit) {
/* 220 */     return value | 1 << bit;
/*     */   }
/*     */   
/*     */   public static int clearBit(int value, int bit) {
/* 224 */     return value & (1 << bit ^ 0xFFFFFFFF);
/*     */   }
/*     */   
/*     */   public static int toggleBit(int value, int bit) {
/* 228 */     return value ^ 1 << bit;
/*     */   }
/*     */   
/*     */   public static byte pack(boolean[] vals) {
/* 232 */     byte result = 0;
/* 233 */     for (boolean bit : vals)
/* 234 */       result = (byte)(result << 1 | (bit ? 1 : 0) & 0x1);
/* 235 */     return result;
/*     */   }
/*     */   
/*     */   public static boolean[] unpack(byte val) {
/* 239 */     boolean[] result = new boolean[8];
/* 240 */     for (int i = 0; i < 8; i++) {
/* 241 */       result[i] = ((byte)(val >> 7 - i & 0x1) == 1 ? 1 : false);
/*     */     }
/* 243 */     return result;
/*     */   }
/*     */   
/*     */   public static final byte[] intToByteArray(int value) {
/* 247 */     return new byte[] { (byte)(value >>> 24), (byte)(value >>> 16), (byte)(value >>> 8), (byte)value };
/*     */   }
/*     */   
/*     */   public static int byteArraytoInt(byte[] bytes)
/*     */   {
/* 252 */     return bytes[0] << 24 | bytes[1] << 16 | bytes[2] << 8 | bytes[3];
/*     */   }
/*     */   
/*     */   public static final byte[] shortToByteArray(short value) {
/* 256 */     return new byte[] { (byte)(value >>> 8), (byte)value };
/*     */   }
/*     */   
/*     */   public static short byteArraytoShort(byte[] bytes)
/*     */   {
/* 261 */     return (short)(bytes[0] << 8 | bytes[1]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 283 */   public static HashMap<WorldCoordinates, Long> effectBuffer = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T, E> void setPrivateFinalValue(Class<? super T> classToAccess, T instance, E value, String... fieldNames)
/*     */   {
/* 303 */     Field field = net.minecraftforge.fml.relauncher.ReflectionHelper.findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 308 */       Field modifiersField = Field.class.getDeclaredField("modifiers");
/* 309 */       modifiersField.setAccessible(true);
/* 310 */       modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
/*     */       
/*     */ 
/* 313 */       field.set(instance, value);
/*     */     } catch (Exception e) {
/* 315 */       e.printStackTrace();
/*     */     }
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
/*     */   public static boolean isLyingInCone(double[] x, double[] t, double[] b, float aperture)
/*     */   {
/* 329 */     double halfAperture = aperture / 2.0F;
/*     */     
/*     */ 
/* 332 */     double[] apexToXVect = dif(t, x);
/*     */     
/*     */ 
/* 335 */     double[] axisVect = dif(t, b);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 342 */     boolean isInInfiniteCone = dotProd(apexToXVect, axisVect) / magn(apexToXVect) / magn(axisVect) > Math.cos(halfAperture);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 348 */     if (!isInInfiniteCone) {
/* 349 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 354 */     boolean isUnderRoundCap = dotProd(apexToXVect, axisVect) / magn(axisVect) < magn(axisVect);
/*     */     
/* 356 */     return isUnderRoundCap;
/*     */   }
/*     */   
/*     */   public static double dotProd(double[] a, double[] b) {
/* 360 */     return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
/*     */   }
/*     */   
/*     */   public static double[] dif(double[] a, double[] b) {
/* 364 */     return new double[] { a[0] - b[0], a[1] - b[1], a[2] - b[2] };
/*     */   }
/*     */   
/*     */   public static double magn(double[] a) {
/* 368 */     return Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static Vec3 calculateVelocity(Vec3 from, Vec3 to, double heightGain, double gravity)
/*     */   {
/* 375 */     double endGain = to.yCoord - from.yCoord;
/* 376 */     double horizDist = Math.sqrt(distanceSquared2d(from, to));
/*     */     
/*     */ 
/* 379 */     double gain = heightGain;
/* 380 */     double maxGain = gain > endGain + gain ? gain : endGain + gain;
/*     */     
/*     */ 
/* 383 */     double a = -horizDist * horizDist / (4.0D * maxGain);
/* 384 */     double b = horizDist;
/* 385 */     double c = -endGain;
/*     */     
/* 387 */     double slope = -b / (2.0D * a) - Math.sqrt(b * b - 4.0D * a * c) / (2.0D * a);
/*     */     
/*     */ 
/* 390 */     double vy = Math.sqrt(maxGain * gravity);
/*     */     
/*     */ 
/* 393 */     double vh = vy / slope;
/*     */     
/*     */ 
/* 396 */     double dx = to.xCoord - from.xCoord;
/* 397 */     double dz = to.zCoord - from.zCoord;
/* 398 */     double mag = Math.sqrt(dx * dx + dz * dz);
/* 399 */     double dirx = dx / mag;
/* 400 */     double dirz = dz / mag;
/*     */     
/*     */ 
/* 403 */     double vx = vh * dirx;
/* 404 */     double vz = vh * dirz;
/*     */     
/* 406 */     return new Vec3(vx, vy, vz);
/*     */   }
/*     */   
/*     */   public static double distanceSquared2d(Vec3 from, Vec3 to)
/*     */   {
/* 411 */     double dx = to.xCoord - from.xCoord;
/* 412 */     double dz = to.zCoord - from.zCoord;
/* 413 */     return dx * dx + dz * dz;
/*     */   }
/*     */   
/*     */   public static double distanceSquared3d(Vec3 from, Vec3 to) {
/* 417 */     double dx = to.xCoord - from.xCoord;
/* 418 */     double dy = to.yCoord - from.yCoord;
/* 419 */     double dz = to.zCoord - from.zCoord;
/* 420 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */   
/*     */   public static ItemStack generateLoot(int rarity, Random rand) {
/* 424 */     ItemStack is = null;
/* 425 */     if ((rarity > 0) && (rand.nextFloat() < 0.025F * rarity)) {
/* 426 */       is = genGear(rarity, rand);
/* 427 */       if (is == null) is = generateLoot(rarity, rand);
/*     */     } else {
/* 429 */       switch (rarity) {
/* 430 */       default:  is = ((WeightedRandomLoot)WeightedRandom.getRandomItem(rand, WeightedRandomLoot.lootBagCommon)).item; break;
/* 431 */       case 1:  is = ((WeightedRandomLoot)WeightedRandom.getRandomItem(rand, WeightedRandomLoot.lootBagUncommon)).item; break;
/* 432 */       case 2:  is = ((WeightedRandomLoot)WeightedRandom.getRandomItem(rand, WeightedRandomLoot.lootBagRare)).item;
/*     */       }
/*     */       
/*     */     }
/* 436 */     if (is.getItem() == Items.book) {
/* 437 */       EnchantmentHelper.addRandomEnchantment(rand, is, (int)(5.0F + rarity * 0.75F * rand.nextInt(18)));
/*     */     }
/*     */     
/* 440 */     return is.copy();
/*     */   }
/*     */   
/*     */   private static ItemStack genGear(int rarity, Random rand) {
/* 444 */     ItemStack is = null;
/*     */     
/* 446 */     int quality = rand.nextInt(2);
/* 447 */     if (rand.nextFloat() < 0.2F) quality++;
/* 448 */     if (rand.nextFloat() < 0.15F) quality++;
/* 449 */     if (rand.nextFloat() < 0.1F) quality++;
/* 450 */     if (rand.nextFloat() < 0.095F) quality++;
/* 451 */     if (rand.nextFloat() < 0.095F) { quality++;
/*     */     }
/* 453 */     Item item = getGearItemForSlot(rand.nextInt(5), quality);
/* 454 */     if (item != null) {
/* 455 */       is = new ItemStack(item, 1, rand.nextInt(1 + item.getMaxDamage() / 6));
/*     */     } else {
/* 457 */       return null;
/*     */     }
/*     */     
/* 460 */     if (rand.nextInt(4) < rarity) {
/* 461 */       EnchantmentHelper.addRandomEnchantment(rand, is, (int)(5.0F + rarity * 0.75F * rand.nextInt(18)));
/*     */     }
/* 463 */     return is.copy();
/*     */   }
/*     */   
/*     */   private static Item getGearItemForSlot(int slot, int quality)
/*     */   {
/* 468 */     switch (slot)
/*     */     {
/*     */     case 4: 
/* 471 */       if (quality == 0) return Items.leather_helmet;
/* 472 */       if (quality == 1) return Items.golden_helmet;
/* 473 */       if (quality == 2) return Items.chainmail_helmet;
/* 474 */       if (quality == 3) return Items.iron_helmet;
/* 475 */       if (quality == 4) return ItemsTC.thaumiumHelm;
/* 476 */       if (quality == 5) return Items.diamond_helmet;
/* 477 */       if (quality == 6) return ItemsTC.voidHelm;
/*     */     case 3: 
/* 479 */       if (quality == 0) return Items.leather_chestplate;
/* 480 */       if (quality == 1) return Items.golden_chestplate;
/* 481 */       if (quality == 2) return Items.chainmail_chestplate;
/* 482 */       if (quality == 3) return Items.iron_chestplate;
/* 483 */       if (quality == 4) return ItemsTC.thaumiumChest;
/* 484 */       if (quality == 5) return Items.diamond_chestplate;
/* 485 */       if (quality == 6) return ItemsTC.voidChest;
/*     */     case 2: 
/* 487 */       if (quality == 0) return Items.leather_leggings;
/* 488 */       if (quality == 1) return Items.golden_leggings;
/* 489 */       if (quality == 2) return Items.chainmail_leggings;
/* 490 */       if (quality == 3) return Items.iron_leggings;
/* 491 */       if (quality == 4) return ItemsTC.thaumiumLegs;
/* 492 */       if (quality == 5) return Items.diamond_leggings;
/* 493 */       if (quality == 6) return ItemsTC.voidLegs;
/*     */     case 1: 
/* 495 */       if (quality == 0) return Items.leather_boots;
/* 496 */       if (quality == 1) return Items.golden_boots;
/* 497 */       if (quality == 2) return Items.chainmail_boots;
/* 498 */       if (quality == 3) return Items.iron_boots;
/* 499 */       if (quality == 4) return ItemsTC.thaumiumBoots;
/* 500 */       if (quality == 5) return Items.diamond_boots;
/* 501 */       if (quality == 6) return ItemsTC.voidBoots;
/*     */     case 0: 
/* 503 */       if (quality == 0) return Items.iron_axe;
/* 504 */       if (quality == 1) return Items.iron_sword;
/* 505 */       if (quality == 2) return Items.golden_axe;
/* 506 */       if (quality == 3) return Items.golden_sword;
/* 507 */       if (quality == 4) return ItemsTC.thaumiumSword;
/* 508 */       if (quality == 5) return Items.diamond_sword;
/* 509 */       if (quality == 6) return ItemsTC.voidSword;
/*     */       break; }
/* 511 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void writeItemStackToBuffer(ByteBuf bb, ItemStack stack)
/*     */   {
/* 518 */     if (stack == null)
/*     */     {
/* 520 */       bb.writeShort(-1);
/*     */     }
/*     */     else
/*     */     {
/* 524 */       bb.writeShort(Item.getIdFromItem(stack.getItem()));
/* 525 */       bb.writeByte(stack.stackSize);
/* 526 */       bb.writeShort(stack.getMetadata());
/* 527 */       NBTTagCompound nbttagcompound = null;
/*     */       
/* 529 */       if ((stack.getItem().isDamageable()) || (stack.getItem().getShareTag()))
/*     */       {
/* 531 */         nbttagcompound = stack.getTagCompound();
/*     */       }
/*     */       
/* 534 */       writeNBTTagCompoundToBuffer(bb, nbttagcompound);
/*     */     }
/*     */   }
/*     */   
/*     */   public static ItemStack readItemStackFromBuffer(ByteBuf bb)
/*     */   {
/* 540 */     ItemStack itemstack = null;
/* 541 */     short short1 = bb.readShort();
/*     */     
/* 543 */     if (short1 >= 0)
/*     */     {
/* 545 */       byte b0 = bb.readByte();
/* 546 */       short short2 = bb.readShort();
/* 547 */       itemstack = new ItemStack(Item.getItemById(short1), b0, short2);
/*     */       try {
/* 549 */         itemstack.setTagCompound(readNBTTagCompoundFromBuffer(bb));
/*     */       }
/*     */       catch (IOException e) {}
/*     */     }
/* 553 */     return itemstack;
/*     */   }
/*     */   
/*     */   public static void writeNBTTagCompoundToBuffer(ByteBuf bb, NBTTagCompound nbt)
/*     */   {
/* 558 */     if (nbt == null)
/*     */     {
/* 560 */       bb.writeByte(0);
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 566 */         CompressedStreamTools.write(nbt, new ByteBufOutputStream(bb));
/*     */       }
/*     */       catch (IOException ioexception)
/*     */       {
/* 570 */         throw new EncoderException(ioexception);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static NBTTagCompound readNBTTagCompoundFromBuffer(ByteBuf bb) throws IOException
/*     */   {
/* 577 */     int i = bb.readerIndex();
/* 578 */     byte b0 = bb.readByte();
/*     */     
/* 580 */     if (b0 == 0)
/*     */     {
/* 582 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 586 */     bb.readerIndex(i);
/* 587 */     return CompressedStreamTools.read(new ByteBufInputStream(bb), new NBTSizeTracker(2097152L));
/*     */   }
/*     */   
/*     */   public static ItemStack makeShard(Aspect aspect)
/*     */   {
/* 592 */     return makeShard(aspect, 1);
/*     */   }
/*     */   
/*     */   public static ItemStack makeShard(Aspect aspect, int n) {
/* 596 */     ItemStack is = new ItemStack(ItemsTC.crystalEssence, n, 0);
/* 597 */     ((ItemGenericEssentiaContainer)ItemsTC.crystalEssence).setAspects(is, new AspectList().add(aspect, 1));
/* 598 */     return is;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\utils\Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */