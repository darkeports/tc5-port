/*     */ package thaumcraft.common.lib.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockCrops;
/*     */ import net.minecraft.block.BlockStem;
/*     */ import net.minecraft.block.IGrowable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropUtils
/*     */ {
/*  22 */   public static ArrayList<String> clickableCrops = new ArrayList();
/*  23 */   public static ArrayList<String> standardCrops = new ArrayList();
/*  24 */   public static ArrayList<String> stackedCrops = new ArrayList();
/*  25 */   public static ArrayList<String> lampBlacklist = new ArrayList();
/*     */   
/*     */   public static void addStandardCrop(ItemStack stack, int grownMeta) {
/*  28 */     Block block = Block.getBlockFromItem(stack.getItem());
/*  29 */     if (block == null) {
/*  30 */       return;
/*     */     }
/*  32 */     addStandardCrop(block, grownMeta);
/*     */   }
/*     */   
/*     */   public static void addStandardCrop(Block block, int grownMeta) {
/*  36 */     if (grownMeta == 32767) {
/*  37 */       for (int a = 0; a < 16; a++) standardCrops.add(block.getUnlocalizedName() + a);
/*     */     } else {
/*  39 */       standardCrops.add(block.getUnlocalizedName() + grownMeta);
/*     */     }
/*  41 */     if (((block instanceof BlockCrops)) && (grownMeta != 7))
/*  42 */       standardCrops.add(block.getUnlocalizedName() + "7");
/*     */   }
/*     */   
/*     */   public static void addClickableCrop(ItemStack stack, int grownMeta) {
/*  46 */     if (Block.getBlockFromItem(stack.getItem()) == null) return;
/*  47 */     if (grownMeta == 32767) {
/*  48 */       for (int a = 0; a < 16; a++) clickableCrops.add(Block.getBlockFromItem(stack.getItem()).getUnlocalizedName() + a);
/*     */     } else {
/*  50 */       clickableCrops.add(Block.getBlockFromItem(stack.getItem()).getUnlocalizedName() + grownMeta);
/*     */     }
/*  52 */     if (((Block.getBlockFromItem(stack.getItem()) instanceof BlockCrops)) && (grownMeta != 7))
/*  53 */       clickableCrops.add(Block.getBlockFromItem(stack.getItem()).getUnlocalizedName() + "7");
/*     */   }
/*     */   
/*     */   public static void addStackedCrop(ItemStack stack, int grownMeta) {
/*  57 */     if (Block.getBlockFromItem(stack.getItem()) == null) return;
/*  58 */     addStackedCrop(Block.getBlockFromItem(stack.getItem()), grownMeta);
/*     */   }
/*     */   
/*     */   public static void addStackedCrop(Block block, int grownMeta) {
/*  62 */     if (grownMeta == 32767) {
/*  63 */       for (int a = 0; a < 16; a++) stackedCrops.add(block.getUnlocalizedName() + a);
/*     */     } else {
/*  65 */       stackedCrops.add(block.getUnlocalizedName() + grownMeta);
/*     */     }
/*  67 */     if (((block instanceof BlockCrops)) && (grownMeta != 7))
/*  68 */       stackedCrops.add(block.getUnlocalizedName() + "7");
/*     */   }
/*     */   
/*     */   public static boolean isGrownCrop(World world, BlockPos pos) {
/*  72 */     if (world.isAirBlock(pos)) return false;
/*  73 */     boolean found = false;
/*  74 */     IBlockState bs = world.getBlockState(pos);
/*  75 */     Block bi = bs.getBlock();
/*  76 */     int md = bi.getMetaFromState(bs);
/*     */     
/*  78 */     if ((standardCrops.contains(bi.getUnlocalizedName() + md)) || (clickableCrops.contains(bi.getUnlocalizedName() + md)) || (stackedCrops.contains(bi.getUnlocalizedName() + md)))
/*     */     {
/*     */ 
/*  81 */       found = true;
/*     */     }
/*     */     
/*  84 */     Block biB = world.getBlockState(pos.down()).getBlock();
/*     */     
/*  86 */     if ((((bi instanceof IGrowable)) && (!((IGrowable)bi).canGrow(world, pos, world.getBlockState(pos), world.isRemote)) && (!(bi instanceof BlockStem))) || (((bi instanceof BlockCrops)) && (md == 7) && (!found)) || (standardCrops.contains(bi.getUnlocalizedName() + md)) || (clickableCrops.contains(bi.getUnlocalizedName() + md)) || ((stackedCrops.contains(bi.getUnlocalizedName() + md)) && (biB == bi)))
/*     */     {
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
/*  99 */       return true;
/*     */     }
/* 101 */     return false;
/*     */   }
/*     */   
/*     */   public static void blacklistLamp(ItemStack stack, int meta) {
/* 105 */     if (Block.getBlockFromItem(stack.getItem()) == null) return;
/* 106 */     if (meta == 32767) {
/* 107 */       for (int a = 0; a < 16; a++) lampBlacklist.add(Block.getBlockFromItem(stack.getItem()).getUnlocalizedName() + a);
/*     */     } else {
/* 109 */       lampBlacklist.add(Block.getBlockFromItem(stack.getItem()).getUnlocalizedName() + meta);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean doesLampGrow(World world, BlockPos pos) {
/* 114 */     Block bi = world.getBlockState(pos).getBlock();
/* 115 */     int md = bi.getMetaFromState(world.getBlockState(pos));
/* 116 */     if (lampBlacklist.contains(bi.getUnlocalizedName() + md)) {
/* 117 */       return false;
/*     */     }
/* 119 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\utils\CropUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */