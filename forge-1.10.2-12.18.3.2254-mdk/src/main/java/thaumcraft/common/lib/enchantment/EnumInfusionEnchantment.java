/*     */ package thaumcraft.common.lib.enchantment;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
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
/*     */ public enum EnumInfusionEnchantment
/*     */ {
/*  23 */   COLLECTOR(ImmutableSet.of("axe", "pickaxe", "shovel", "weapon"), 1, "INFUSIONENCHANTMENT"), 
/*  24 */   DESTRUCTIVE(ImmutableSet.of("axe", "pickaxe", "shovel"), 1, "INFUSIONENCHANTMENT"), 
/*  25 */   BURROWING(ImmutableSet.of("axe", "pickaxe"), 1, "INFUSIONENCHANTMENT"), 
/*  26 */   SOUNDING(ImmutableSet.of("pickaxe"), 4, "INFUSIONENCHANTMENT"), 
/*  27 */   REFINING(ImmutableSet.of("pickaxe"), 4, "INFUSIONENCHANTMENT"), 
/*  28 */   ARCING(ImmutableSet.of("weapon"), 4, "INFUSIONENCHANTMENT"), 
/*  29 */   ESSENCE(ImmutableSet.of("weapon"), 5, "INFUSIONENCHANTMENT"), 
/*  30 */   VISBATTERY(ImmutableSet.of("chargable"), 3, "?"), 
/*  31 */   VISCHARGE(ImmutableSet.of("chargable"), 1, "?"), 
/*  32 */   SWIFT(ImmutableSet.of("boots"), 4, "IEARMOR"), 
/*  33 */   AGILE(ImmutableSet.of("legs"), 1, "IEARMOR"), 
/*  34 */   INFESTED(ImmutableSet.of("chest"), 1, "IETAINT");
/*     */   
/*     */   public Set<String> toolClasses;
/*     */   public int maxLevel;
/*     */   public String research;
/*     */   
/*     */   private EnumInfusionEnchantment(Set<String> toolClasses, int ml, String research) {
/*  41 */     this.toolClasses = toolClasses;
/*  42 */     this.maxLevel = ml;
/*  43 */     this.research = research;
/*     */   }
/*     */   
/*     */   public static NBTTagList getInfusionEnchantmentTagList(ItemStack stack)
/*     */   {
/*  48 */     return (stack == null) || (stack.getTagCompound() == null) ? null : stack.getTagCompound().getTagList("infench", 10);
/*     */   }
/*     */   
/*     */   public static List<EnumInfusionEnchantment> getInfusionEnchantments(ItemStack stack) {
/*  52 */     NBTTagList nbttaglist = getInfusionEnchantmentTagList(stack);
/*  53 */     List<EnumInfusionEnchantment> list = new ArrayList();
/*  54 */     if (nbttaglist != null)
/*     */     {
/*  56 */       for (int j = 0; j < nbttaglist.tagCount(); j++)
/*     */       {
/*  58 */         int k = nbttaglist.getCompoundTagAt(j).getShort("id");
/*  59 */         int l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
/*     */         
/*  61 */         if ((k >= 0) && (k < values().length))
/*     */         {
/*  63 */           list.add(values()[k]);
/*     */         }
/*     */       }
/*     */     }
/*  67 */     return list;
/*     */   }
/*     */   
/*     */   public static int getInfusionEnchantmentLevel(ItemStack stack, EnumInfusionEnchantment enchantment) {
/*  71 */     NBTTagList nbttaglist = getInfusionEnchantmentTagList(stack);
/*  72 */     List<EnumInfusionEnchantment> list = new ArrayList();
/*  73 */     if (nbttaglist != null)
/*     */     {
/*  75 */       for (int j = 0; j < nbttaglist.tagCount(); j++)
/*     */       {
/*  77 */         int k = nbttaglist.getCompoundTagAt(j).getShort("id");
/*  78 */         int l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
/*     */         
/*  80 */         if ((k >= 0) && (k < values().length) && (values()[k] == enchantment))
/*     */         {
/*  82 */           return l;
/*     */         }
/*     */       }
/*     */     }
/*  86 */     return 0;
/*     */   }
/*     */   
/*     */   public static void addInfusionEnchantment(ItemStack stack, EnumInfusionEnchantment ie, int level) {
/*  90 */     if ((stack == null) || (level > ie.maxLevel)) return;
/*  91 */     NBTTagList nbttaglist = getInfusionEnchantmentTagList(stack);
/*  92 */     if (nbttaglist != null)
/*     */     {
/*  94 */       for (int j = 0; j < nbttaglist.tagCount(); j++)
/*     */       {
/*  96 */         int k = nbttaglist.getCompoundTagAt(j).getShort("id");
/*  97 */         int l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
/*  98 */         if (k == ie.ordinal()) {
/*  99 */           if (level <= l) {
/* 100 */             return;
/*     */           }
/* 102 */           nbttaglist.getCompoundTagAt(j).setShort("lvl", (short)level);
/* 103 */           stack.setTagInfo("infench", nbttaglist);
/* 104 */           return;
/*     */         }
/*     */         
/*     */       }
/*     */     } else {
/* 109 */       nbttaglist = new NBTTagList();
/*     */     }
/* 111 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 112 */     nbttagcompound.setShort("id", (short)ie.ordinal());
/* 113 */     nbttagcompound.setShort("lvl", (short)level);
/* 114 */     nbttaglist.appendTag(nbttagcompound);
/*     */     
/* 116 */     if (nbttaglist.tagCount() > 0)
/*     */     {
/* 118 */       stack.setTagInfo("infench", nbttaglist);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\enchantment\EnumInfusionEnchantment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */