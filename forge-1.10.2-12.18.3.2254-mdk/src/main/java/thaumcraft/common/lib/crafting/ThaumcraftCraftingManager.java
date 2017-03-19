/*     */ package thaumcraft.common.lib.crafting;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.Item.ToolMaterial;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemBow;
/*     */ import net.minecraft.item.ItemEnchantedBook;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.ShapedRecipes;
/*     */ import net.minecraft.item.crafting.ShapelessRecipes;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*     */ import net.minecraftforge.oredict.ShapelessOreRecipe;
/*     */ import thaumcraft.api.ThaumcraftApi;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectHelper;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IEssentiaContainerItem;
/*     */ import thaumcraft.api.crafting.CrucibleRecipe;
/*     */ import thaumcraft.api.crafting.IArcaneRecipe;
/*     */ import thaumcraft.api.crafting.InfusionRecipe;
/*     */ import thaumcraft.api.crafting.ShapedArcaneRecipe;
/*     */ import thaumcraft.api.crafting.ShapelessArcaneRecipe;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.api.wands.WandCap;
/*     */ import thaumcraft.api.wands.WandRod;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class ThaumcraftCraftingManager
/*     */ {
/*     */   public static ShapedRecipes createFakeRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj)
/*     */   {
/*  56 */     String var3 = "";
/*  57 */     int var4 = 0;
/*  58 */     int var5 = 0;
/*  59 */     int var6 = 0;
/*     */     
/*     */ 
/*  62 */     if ((par2ArrayOfObj[var4] instanceof String[]))
/*     */     {
/*  64 */       String[] var7 = (String[])(String[])par2ArrayOfObj[(var4++)];
/*  65 */       String[] var8 = var7;
/*  66 */       int var9 = var7.length;
/*     */       
/*  68 */       for (int var10 = 0; var10 < var9; var10++)
/*     */       {
/*  70 */         String var11 = var8[var10];
/*  71 */         var6++;
/*  72 */         var5 = var11.length();
/*  73 */         var3 = var3 + var11;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  78 */       while ((par2ArrayOfObj[var4] instanceof String))
/*     */       {
/*  80 */         String var13 = (String)par2ArrayOfObj[(var4++)];
/*  81 */         var6++;
/*  82 */         var5 = var13.length();
/*  83 */         var3 = var3 + var13;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  89 */     for (HashMap var14 = new HashMap(); var4 < par2ArrayOfObj.length; var4 += 2)
/*     */     {
/*  91 */       Character var16 = (Character)par2ArrayOfObj[var4];
/*  92 */       ItemStack var17 = null;
/*     */       
/*  94 */       if ((par2ArrayOfObj[(var4 + 1)] instanceof Item))
/*     */       {
/*  96 */         var17 = new ItemStack((Item)par2ArrayOfObj[(var4 + 1)]);
/*     */       }
/*  98 */       else if ((par2ArrayOfObj[(var4 + 1)] instanceof Block))
/*     */       {
/* 100 */         var17 = new ItemStack((Block)par2ArrayOfObj[(var4 + 1)]);
/*     */       }
/* 102 */       else if ((par2ArrayOfObj[(var4 + 1)] instanceof ItemStack))
/*     */       {
/* 104 */         var17 = (ItemStack)par2ArrayOfObj[(var4 + 1)];
/*     */       }
/*     */       
/* 107 */       var14.put(var16, var17);
/*     */     }
/*     */     
/* 110 */     ItemStack[] var15 = new ItemStack[var5 * var6];
/*     */     
/* 112 */     for (int var9 = 0; var9 < var5 * var6; var9++)
/*     */     {
/* 114 */       char var18 = var3.charAt(var9);
/*     */       
/* 116 */       if (var14.containsKey(Character.valueOf(var18)))
/*     */       {
/* 118 */         var15[var9] = ((ItemStack)var14.get(Character.valueOf(var18))).copy();
/*     */       }
/*     */       else
/*     */       {
/* 122 */         var15[var9] = null;
/*     */       }
/*     */     }
/*     */     
/* 126 */     return new ShapedRecipes(var5, var6, var15, par1ItemStack);
/*     */   }
/*     */   
/*     */   public static CrucibleRecipe findMatchingCrucibleRecipe(String username, AspectList aspects, ItemStack lastDrop) {
/* 130 */     int highest = 0;
/* 131 */     int index = -1;
/* 132 */     for (int a = 0; a < ThaumcraftApi.getCraftingRecipes().size(); a++) {
/* 133 */       if ((ThaumcraftApi.getCraftingRecipes().get(a) instanceof CrucibleRecipe)) {
/* 134 */         CrucibleRecipe recipe = (CrucibleRecipe)ThaumcraftApi.getCraftingRecipes().get(a);
/* 135 */         ItemStack temp = lastDrop.copy();
/* 136 */         temp.stackSize = 1;
/* 137 */         if ((ResearchHelper.isResearchComplete(username, recipe.research)) && (recipe.matches(aspects, temp)))
/*     */         {
/* 139 */           int result = recipe.aspects.size();
/* 140 */           if (result > highest) {
/* 141 */             highest = result;
/* 142 */             index = a;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 148 */     if (index < 0) return null;
/* 149 */     AspectList output = new AspectList();
/*     */     
/* 151 */     return (CrucibleRecipe)ThaumcraftApi.getCraftingRecipes().get(index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ItemStack findMatchingArcaneRecipe(InventoryCrafting awb, EntityPlayer player)
/*     */   {
/* 159 */     int var2 = 0;
/* 160 */     ItemStack var3 = null;
/* 161 */     ItemStack var4 = null;
/*     */     
/* 163 */     for (int var5 = 0; var5 < 9; var5++)
/*     */     {
/* 165 */       ItemStack var6 = awb.getStackInSlot(var5);
/*     */       
/* 167 */       if (var6 != null)
/*     */       {
/* 169 */         if (var2 == 0)
/*     */         {
/* 171 */           var3 = var6;
/*     */         }
/*     */         
/* 174 */         if (var2 == 1)
/*     */         {
/* 176 */           var4 = var6;
/*     */         }
/*     */         
/* 179 */         var2++;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 184 */     IArcaneRecipe var13 = null;
/* 185 */     for (Object var11 : ThaumcraftApi.getCraftingRecipes()) {
/* 186 */       if (((var11 instanceof IArcaneRecipe)) && (((IArcaneRecipe)var11).matches(awb, player.worldObj, player)))
/*     */       {
/* 188 */         var13 = (IArcaneRecipe)var11;
/* 189 */         break;
/*     */       }
/*     */     }
/*     */     
/* 193 */     return var13 == null ? null : var13.getCraftingResult(awb);
/*     */   }
/*     */   
/*     */ 
/*     */   public static AspectList findMatchingArcaneRecipeAspects(InventoryCrafting awb, EntityPlayer player)
/*     */   {
/* 199 */     int var2 = 0;
/* 200 */     ItemStack var3 = null;
/* 201 */     ItemStack var4 = null;
/*     */     
/* 203 */     for (int var5 = 0; var5 < 9; var5++)
/*     */     {
/* 205 */       ItemStack var6 = awb.getStackInSlot(var5);
/*     */       
/* 207 */       if (var6 != null)
/*     */       {
/* 209 */         if (var2 == 0)
/*     */         {
/* 211 */           var3 = var6;
/*     */         }
/*     */         
/* 214 */         if (var2 == 1)
/*     */         {
/* 216 */           var4 = var6;
/*     */         }
/*     */         
/* 219 */         var2++;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 224 */     IArcaneRecipe var13 = null;
/* 225 */     for (Object var11 : ThaumcraftApi.getCraftingRecipes()) {
/* 226 */       if (((var11 instanceof IArcaneRecipe)) && (((IArcaneRecipe)var11).matches(awb, player.worldObj, player)))
/*     */       {
/* 228 */         var13 = (IArcaneRecipe)var11;
/*     */         
/* 230 */         break;
/*     */       }
/*     */     }
/*     */     
/* 234 */     return var13.getAspects() != null ? var13.getAspects() : var13 == null ? new AspectList() : var13.getAspects(awb);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static InfusionRecipe findMatchingInfusionRecipe(ArrayList<ItemStack> items, ItemStack input, EntityPlayer player)
/*     */   {
/* 241 */     InfusionRecipe var13 = null;
/* 242 */     for (Object var11 : ThaumcraftApi.getCraftingRecipes()) {
/* 243 */       if (((var11 instanceof InfusionRecipe)) && (((InfusionRecipe)var11).matches(items, input, player.worldObj, player)))
/*     */       {
/* 245 */         var13 = (InfusionRecipe)var11;
/* 246 */         break;
/*     */       }
/*     */     }
/*     */     
/* 250 */     return var13;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AspectList getObjectTags(ItemStack itemstack)
/*     */   {
/* 258 */     return getObjectTags(itemstack, null);
/*     */   }
/*     */   
/*     */   public static AspectList getObjectTags(ItemStack itemstack, ArrayList<List> history)
/*     */   {
/*     */     Item item;
/*     */     int meta;
/*     */     try {
/* 266 */       item = itemstack.getItem();
/* 267 */       meta = itemstack.getItemDamage();
/*     */     } catch (Exception e) {
/* 269 */       return null;
/*     */     }
/*     */     
/* 272 */     AspectList tmp = (AspectList)ThaumcraftApi.objectTags.get(Arrays.asList(new Object[] { item, Integer.valueOf(meta) }));
/*     */     
/* 274 */     if (tmp == null) {
/* 275 */       Collection<List> col = ThaumcraftApi.objectTags.keySet();
/* 276 */       for (List l : col) {
/* 277 */         if (((Item)l.get(0) == item) && ((l.get(1) instanceof int[]))) {
/* 278 */           int[] range = (int[])l.get(1);
/* 279 */           Arrays.sort(range);
/* 280 */           if (Arrays.binarySearch(range, meta) >= 0) {
/* 281 */             tmp = (AspectList)ThaumcraftApi.objectTags.get(Arrays.asList(new Object[] { item, range }));
/* 282 */             return tmp;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 287 */       tmp = (AspectList)ThaumcraftApi.objectTags.get(Arrays.asList(new Object[] { item, Integer.valueOf(32767) }));
/* 288 */       if ((tmp == null) && 
/* 289 */         (tmp == null)) {
/* 290 */         if ((meta == 32767) && (tmp == null)) {
/* 291 */           int index = 0;
/*     */           do {
/* 293 */             tmp = (AspectList)ThaumcraftApi.objectTags.get(Arrays.asList(new Object[] { item, Integer.valueOf(index) }));
/* 294 */             index++;
/* 295 */           } while ((index < 16) && (tmp == null));
/*     */         }
/* 297 */         if (tmp == null) { tmp = generateTags(item, meta, history);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 304 */     if ((itemstack.getItem() instanceof IWand)) {
/* 305 */       IWand wand = (IWand)itemstack.getItem();
/* 306 */       if (tmp == null) tmp = new AspectList();
/* 307 */       tmp.merge(Aspect.ENERGY, (wand.getRod(itemstack).getCraftCost() + wand.getCap(itemstack).getCraftCost()) / 2);
/*     */       
/* 309 */       tmp.merge(Aspect.TOOL, (wand.getRod(itemstack).getCraftCost() + wand.getCap(itemstack).getCraftCost()) / 3);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 315 */     if ((item != null) && (item == Items.potionitem)) {
/* 316 */       if (tmp == null) tmp = new AspectList();
/* 317 */       tmp.merge(Aspect.WATER, 1);
/*     */       
/* 319 */       ItemPotion ip = (ItemPotion)item;
/* 320 */       List effects = ip.getEffects(itemstack.getItemDamage());
/* 321 */       if (effects != null)
/*     */       {
/* 323 */         if (ItemPotion.isSplash(itemstack.getItemDamage())) tmp.merge(Aspect.ENTROPY, 2);
/* 324 */         Iterator var5 = effects.iterator();
/* 325 */         while (var5.hasNext())
/*     */         {
/* 327 */           PotionEffect var6 = (PotionEffect)var5.next();
/* 328 */           tmp.merge(Aspect.ENERGY, (var6.getAmplifier() + 1) * 2);
/* 329 */           if (var6.getPotionID() == Potion.blindness.id) { tmp.merge(Aspect.DARKNESS, (var6.getAmplifier() + 1) * 3);
/* 330 */           } else if (var6.getPotionID() == Potion.confusion.id) { tmp.merge(Aspect.ELDRITCH, (var6.getAmplifier() + 1) * 3);
/* 331 */           } else if (var6.getPotionID() == Potion.damageBoost.id) { tmp.merge(Aspect.AVERSION, (var6.getAmplifier() + 1) * 3);
/* 332 */           } else if (var6.getPotionID() == Potion.digSlowdown.id) { tmp.merge(Aspect.TRAP, (var6.getAmplifier() + 1) * 3);
/* 333 */           } else if (var6.getPotionID() == Potion.digSpeed.id) { tmp.merge(Aspect.TOOL, (var6.getAmplifier() + 1) * 3);
/* 334 */           } else if (var6.getPotionID() == Potion.fireResistance.id) {
/* 335 */             tmp.merge(Aspect.PROTECT, var6.getAmplifier() + 1);
/* 336 */             tmp.merge(Aspect.FIRE, (var6.getAmplifier() + 1) * 2);
/*     */           }
/* 338 */           else if (var6.getPotionID() == Potion.harm.id) { tmp.merge(Aspect.DEATH, (var6.getAmplifier() + 1) * 3);
/* 339 */           } else if (var6.getPotionID() == Potion.heal.id) { tmp.merge(Aspect.LIFE, (var6.getAmplifier() + 1) * 3);
/* 340 */           } else if (var6.getPotionID() == Potion.hunger.id) { tmp.merge(Aspect.DEATH, (var6.getAmplifier() + 1) * 3);
/* 341 */           } else if (var6.getPotionID() == Potion.invisibility.id) { tmp.merge(Aspect.SENSES, (var6.getAmplifier() + 1) * 3);
/* 342 */           } else if (var6.getPotionID() == Potion.jump.id) { tmp.merge(Aspect.FLIGHT, (var6.getAmplifier() + 1) * 3);
/* 343 */           } else if (var6.getPotionID() == Potion.moveSlowdown.id) { tmp.merge(Aspect.TRAP, (var6.getAmplifier() + 1) * 3);
/* 344 */           } else if (var6.getPotionID() == Potion.moveSpeed.id) { tmp.merge(Aspect.MOTION, (var6.getAmplifier() + 1) * 3);
/* 345 */           } else if (var6.getPotionID() == Potion.nightVision.id) { tmp.merge(Aspect.SENSES, (var6.getAmplifier() + 1) * 3);
/* 346 */           } else if (var6.getPotionID() == Potion.poison.id) { tmp.merge(Aspect.DEATH, (var6.getAmplifier() + 1) * 3);
/* 347 */           } else if (var6.getPotionID() == Potion.regeneration.id) { tmp.merge(Aspect.LIFE, (var6.getAmplifier() + 1) * 3);
/* 348 */           } else if (var6.getPotionID() == Potion.resistance.id) { tmp.merge(Aspect.PROTECT, (var6.getAmplifier() + 1) * 3);
/* 349 */           } else if (var6.getPotionID() == Potion.waterBreathing.id) { tmp.merge(Aspect.AIR, (var6.getAmplifier() + 1) * 3);
/* 350 */           } else if (var6.getPotionID() == Potion.weakness.id) { tmp.merge(Aspect.DEATH, (var6.getAmplifier() + 1) * 3);
/* 351 */           } else if (var6.getPotionID() == Potion.absorption.id) {
/* 352 */             tmp.merge(Aspect.PROTECT, var6.getAmplifier() + 1);
/* 353 */             tmp.merge(Aspect.LIFE, (var6.getAmplifier() + 1) * 2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 360 */     return capAspects(getBonusTags(itemstack, tmp), 64);
/*     */   }
/*     */   
/*     */   private static AspectList capAspects(AspectList sourcetags, int amount)
/*     */   {
/* 365 */     if (sourcetags == null) return sourcetags;
/* 366 */     AspectList out = new AspectList();
/* 367 */     for (Aspect aspect : sourcetags.getAspects()) {
/* 368 */       if (aspect != null)
/* 369 */         out.merge(aspect, Math.min(amount, sourcetags.getAmount(aspect)));
/*     */     }
/* 371 */     return out;
/*     */   }
/*     */   
/*     */   private static AspectList getBonusTags(ItemStack itemstack, AspectList sourcetags) {
/* 375 */     AspectList tmp = new AspectList();
/*     */     
/* 377 */     Item item = itemstack.getItem();
/* 378 */     if ((item != null) && ((item instanceof IEssentiaContainerItem)) && (!((IEssentiaContainerItem)item).ignoreContainedAspects())) {
/* 379 */       tmp = ((IEssentiaContainerItem)item).getAspects(itemstack);
/* 380 */       if ((tmp != null) && (tmp.size() > 0)) {
/* 381 */         for (Aspect tag : tmp.copy().getAspects()) {
/* 382 */           if (tmp.getAmount(tag) <= 0) tmp.remove(tag);
/*     */         }
/*     */       }
/*     */     }
/* 386 */     if (tmp == null) { tmp = new AspectList();
/*     */     }
/* 388 */     if (sourcetags != null) {
/* 389 */       for (Aspect tag : sourcetags.getAspects()) {
/* 390 */         if (tag != null) {
/* 391 */           tmp.add(tag, sourcetags.getAmount(tag));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 396 */     int id = Item.getIdFromItem(itemstack.getItem());
/* 397 */     int meta = itemstack.getItemDamage();
/*     */     
/* 399 */     if ((item != null) && (
/* 400 */       (tmp != null) || (item == Items.potionitem)))
/*     */     {
/* 402 */       if ((item instanceof ItemArmor)) {
/* 403 */         tmp.merge(Aspect.PROTECT, ((ItemArmor)item).damageReduceAmount);
/*     */       }
/* 405 */       else if (((item instanceof ItemSword)) && (((ItemSword)item).getDamageVsEntity() + 1.0F > 0.0F))
/*     */       {
/* 407 */         tmp.merge(Aspect.AVERSION, (int)(((ItemSword)item).getDamageVsEntity() + 1.0F));
/*     */ 
/*     */       }
/* 410 */       else if ((item instanceof ItemBow)) {
/* 411 */         tmp.merge(Aspect.AVERSION, 3).merge(Aspect.FLIGHT, 1);
/*     */ 
/*     */       }
/* 414 */       else if ((item instanceof ItemTool)) {
/* 415 */         String mat = ((ItemTool)item).getToolMaterialName();
/* 416 */         for (Item.ToolMaterial tm : Item.ToolMaterial.values()) {
/* 417 */           if (tm.toString().equals(mat)) {
/* 418 */             tmp.merge(Aspect.TOOL, tm.getHarvestLevel() + 1);
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 423 */       else if (((item instanceof net.minecraft.item.ItemShears)) || ((item instanceof ItemHoe))) {
/* 424 */         if (item.getMaxDamage() <= Item.ToolMaterial.WOOD.getMaxUses()) {
/* 425 */           tmp.merge(Aspect.TOOL, 1);
/*     */         }
/* 427 */         else if ((item.getMaxDamage() <= Item.ToolMaterial.STONE.getMaxUses()) || (item.getMaxDamage() <= Item.ToolMaterial.GOLD.getMaxUses()))
/*     */         {
/* 429 */           tmp.merge(Aspect.TOOL, 2);
/*     */         }
/* 431 */         else if (item.getMaxDamage() <= Item.ToolMaterial.IRON.getMaxUses()) {
/* 432 */           tmp.merge(Aspect.TOOL, 3);
/*     */         } else {
/* 434 */           tmp.merge(Aspect.TOOL, 4);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 439 */       NBTTagList ench = itemstack.getEnchantmentTagList();
/* 440 */       if ((item instanceof ItemEnchantedBook)) {
/* 441 */         ench = ((ItemEnchantedBook)item).getEnchantments(itemstack);
/*     */       }
/* 443 */       if (ench != null)
/*     */       {
/* 445 */         int var5 = 0;
/* 446 */         for (int var3 = 0; var3 < ench.tagCount(); var3++)
/*     */         {
/* 448 */           short eid = ench.getCompoundTagAt(var3).getShort("id");
/* 449 */           short lvl = ench.getCompoundTagAt(var3).getShort("lvl");
/* 450 */           if (eid == Enchantment.aquaAffinity.effectId) { tmp.merge(Aspect.WATER, lvl);
/* 451 */           } else if (eid == Enchantment.baneOfArthropods.effectId) { tmp.merge(Aspect.BEAST, lvl);
/* 452 */           } else if (eid == Enchantment.blastProtection.effectId) { tmp.merge(Aspect.PROTECT, lvl);
/* 453 */           } else if (eid == Enchantment.efficiency.effectId) { tmp.merge(Aspect.TOOL, lvl);
/* 454 */           } else if (eid == Enchantment.featherFalling.effectId) { tmp.merge(Aspect.FLIGHT, lvl);
/* 455 */           } else if (eid == Enchantment.fireAspect.effectId) { tmp.merge(Aspect.FIRE, lvl);
/* 456 */           } else if (eid == Enchantment.fireProtection.effectId) { tmp.merge(Aspect.PROTECT, lvl);
/* 457 */           } else if (eid == Enchantment.flame.effectId) { tmp.merge(Aspect.FIRE, lvl);
/* 458 */           } else if (eid == Enchantment.fortune.effectId) { tmp.merge(Aspect.DESIRE, lvl);
/* 459 */           } else if (eid == Enchantment.infinity.effectId) { tmp.merge(Aspect.CRAFT, lvl);
/* 460 */           } else if (eid == Enchantment.knockback.effectId) { tmp.merge(Aspect.AIR, lvl);
/* 461 */           } else if (eid == Enchantment.looting.effectId) { tmp.merge(Aspect.DESIRE, lvl);
/* 462 */           } else if (eid == Enchantment.power.effectId) { tmp.merge(Aspect.AVERSION, lvl);
/* 463 */           } else if (eid == Enchantment.projectileProtection.effectId) { tmp.merge(Aspect.PROTECT, lvl);
/* 464 */           } else if (eid == Enchantment.protection.effectId) { tmp.merge(Aspect.PROTECT, lvl);
/* 465 */           } else if (eid == Enchantment.punch.effectId) { tmp.merge(Aspect.AIR, lvl);
/* 466 */           } else if (eid == Enchantment.respiration.effectId) { tmp.merge(Aspect.AIR, lvl);
/* 467 */           } else if (eid == Enchantment.sharpness.effectId) { tmp.merge(Aspect.AVERSION, lvl);
/* 468 */           } else if (eid == Enchantment.silkTouch.effectId) { tmp.merge(Aspect.EXCHANGE, lvl);
/* 469 */           } else if (eid == Enchantment.thorns.effectId) { tmp.merge(Aspect.AVERSION, lvl);
/* 470 */           } else if (eid == Enchantment.smite.effectId) { tmp.merge(Aspect.ENTROPY, lvl);
/* 471 */           } else if (eid == Enchantment.unbreaking.effectId) { tmp.merge(Aspect.EARTH, lvl);
/* 472 */           } else if (eid == Enchantment.depthStrider.effectId) { tmp.merge(Aspect.WATER, lvl);
/* 473 */           } else if (eid == Enchantment.luckOfTheSea.effectId) { tmp.merge(Aspect.DESIRE, lvl);
/* 474 */           } else if (eid == Enchantment.lure.effectId) { tmp.merge(Aspect.BEAST, lvl);
/*     */ 
/*     */           }
/* 477 */           else if (eid == Config.enchHaste.effectId) { tmp.merge(Aspect.MOTION, lvl);
/* 478 */           } else if (eid == Config.enchRepair.effectId) {
/* 479 */             tmp.merge(Aspect.TOOL, lvl);
/*     */           }
/*     */           
/* 482 */           var5 += lvl;
/*     */         }
/* 484 */         if (var5 > 0) { tmp.merge(Aspect.ENERGY, var5);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 489 */     return AspectHelper.cullTags(tmp);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static AspectList generateTags(Item item, int meta)
/*     */   {
/* 496 */     AspectList temp = generateTags(item, meta, new ArrayList());
/* 497 */     return temp;
/*     */   }
/*     */   
/*     */   public static AspectList generateTags(Item item, int meta, ArrayList<List> history)
/*     */   {
/* 502 */     if (history == null) { history = new ArrayList();
/*     */     }
/* 504 */     int tmeta = meta;
/*     */     try {
/* 506 */       tmeta = (new ItemStack(item, 1, meta).getItem().isDamageable()) || (!new ItemStack(item, 1, meta).getItem().getHasSubtypes()) ? 32767 : meta;
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*     */ 
/* 511 */     if (ThaumcraftApi.exists(item, tmeta)) {
/* 512 */       return getObjectTags(new ItemStack(item, 1, tmeta), history);
/*     */     }
/*     */     
/* 515 */     if (history.contains(Arrays.asList(new Object[] { item, Integer.valueOf(tmeta) }))) {
/* 516 */       return null;
/*     */     }
/*     */     
/* 519 */     history.add(Arrays.asList(new Object[] { item, Integer.valueOf(tmeta) }));
/*     */     
/*     */     AspectList ret;
/*     */     
/* 523 */     if (history.size() < 100)
/* 524 */       ret = generateTagsFromRecipes(item, tmeta == 32767 ? 0 : meta, history); else {
/* 525 */       return null;
/*     */     }
/* 527 */     AspectList ret = capAspects(ret, 64);
/*     */     
/*     */ 
/* 530 */     ThaumcraftApi.registerObjectTag(new ItemStack(item, 1, tmeta), ret);
/*     */     
/* 532 */     return ret;
/*     */   }
/*     */   
/*     */   private static AspectList generateTagsFromCrucibleRecipes(Item item, int meta, ArrayList<List> history)
/*     */   {
/* 537 */     CrucibleRecipe cr = ThaumcraftApi.getCrucibleRecipe(new ItemStack(item, 1, meta));
/*     */     
/* 539 */     if (cr != null) {
/* 540 */       AspectList ot = cr.aspects.copy();
/* 541 */       int ss = cr.getRecipeOutput().stackSize;
/* 542 */       ItemStack cat = null;
/* 543 */       if ((cr.catalyst instanceof ItemStack)) {
/* 544 */         cat = (ItemStack)cr.catalyst;
/* 545 */       } else if (((cr.catalyst instanceof List)) && (((List)cr.catalyst).size() > 0)) {
/* 546 */         cat = (ItemStack)((List)cr.catalyst).get(0);
/*     */       }
/* 548 */       if (cat == null) return null;
/* 549 */       AspectList ot2 = getObjectTags(cat, history);
/*     */       
/* 551 */       AspectList out = new AspectList();
/*     */       
/* 553 */       if ((ot2 != null) && (ot2.size() > 0)) {
/* 554 */         for (Aspect tt : ot2.getAspects()) {
/* 555 */           out.add(tt, ot2.getAmount(tt));
/*     */         }
/*     */       }
/* 558 */       for (Aspect tt : ot.getAspects()) {
/* 559 */         int amt = (int)(Math.sqrt(ot.getAmount(tt)) / ss);
/* 560 */         out.add(tt, amt);
/*     */       }
/*     */       
/* 563 */       for (Aspect as : out.getAspects()) {
/* 564 */         if (out.getAmount(as) <= 0) out.remove(as);
/*     */       }
/* 566 */       return out;
/*     */     }
/* 568 */     return null;
/*     */   }
/*     */   
/*     */   private static AspectList generateTagsFromArcaneRecipes(Item item, int meta, ArrayList<List> history) {
/* 572 */     AspectList ret = null;
/* 573 */     int value = 0;
/*     */     
/*     */ 
/* 576 */     List recipeList = ThaumcraftApi.getCraftingRecipes();
/*     */     label883:
/* 578 */     for (int q = 0; q < recipeList.size(); q++)
/* 579 */       if ((recipeList.get(q) instanceof IArcaneRecipe)) {
/* 580 */         IArcaneRecipe recipe = (IArcaneRecipe)recipeList.get(q);
/* 581 */         if ((recipe.getRecipeOutput() != null) && (recipe.getRecipeOutput().getItem() != null)) {
/* 582 */           int idR = recipe.getRecipeOutput().getItemDamage() == 32767 ? 0 : recipe.getRecipeOutput().getItemDamage();
/* 583 */           int idS = meta < 0 ? 0 : meta;
/* 584 */           if ((recipe.getRecipeOutput().getItem() == item) && (idR == idS)) {
/* 585 */             ArrayList<ItemStack> ingredients = new ArrayList();
/* 586 */             AspectList ph = new AspectList();
/* 587 */             int cval = 0;
/*     */             try {
/* 589 */               if ((recipeList.get(q) instanceof ShapedArcaneRecipe)) {
/* 590 */                 int width = ((ShapedArcaneRecipe)recipeList.get(q)).width;
/* 591 */                 int height = ((ShapedArcaneRecipe)recipeList.get(q)).height;
/* 592 */                 Object[] items = ((ShapedArcaneRecipe)recipeList.get(q)).getInput();
/*     */                 
/* 594 */                 for (int i = 0; (i < width) && (i < 3); i++) {
/* 595 */                   for (int j = 0; (j < height) && (j < 3); j++) {
/* 596 */                     if (items[(i + j * width)] != null)
/*     */                     {
/* 598 */                       if ((items[(i + j * width)] instanceof List)) {
/* 599 */                         for (ItemStack it : (List)items[(i + j * width)]) {
/* 600 */                           if (Utils.isEETransmutionItem(it.getItem())) break label883;
/* 601 */                           AspectList obj = getObjectTags(it, history);
/* 602 */                           if ((obj != null) && (obj.size() > 0)) {
/* 603 */                             ItemStack is = it.copy();
/* 604 */                             is.stackSize = 1;
/* 605 */                             ingredients.add(is);
/* 606 */                             break;
/*     */                           }
/*     */                         }
/*     */                       } else {
/* 610 */                         ItemStack it = (ItemStack)items[(i + j * width)];
/* 611 */                         if (Utils.isEETransmutionItem(it.getItem())) break label883;
/* 612 */                         ItemStack is = it.copy();
/* 613 */                         is.stackSize = 1;
/* 614 */                         ingredients.add(is);
/*     */                       } }
/*     */                   }
/*     */                 }
/* 618 */               } else if ((recipeList.get(q) instanceof ShapelessArcaneRecipe)) {
/* 619 */                 ArrayList items = ((ShapelessArcaneRecipe)recipeList.get(q)).getInput();
/*     */                 
/* 621 */                 for (int i = 0; (i < items.size()) && (i < 9); i++) {
/* 622 */                   if (items.get(i) != null)
/*     */                   {
/* 624 */                     if ((items.get(i) instanceof List)) {
/* 625 */                       for (ItemStack it : (List)items.get(i)) {
/* 626 */                         if (Utils.isEETransmutionItem(it.getItem())) break label883;
/* 627 */                         AspectList obj = getObjectTags(it, history);
/*     */                         
/* 629 */                         if ((obj != null) && (obj.size() > 0)) {
/* 630 */                           ItemStack is = it.copy();
/* 631 */                           is.stackSize = 1;
/* 632 */                           ingredients.add(is);
/* 633 */                           break;
/*     */                         }
/*     */                       }
/*     */                     } else {
/* 637 */                       ItemStack it = (ItemStack)items.get(i);
/* 638 */                       if (Utils.isEETransmutionItem(it.getItem())) break label883;
/* 639 */                       ItemStack is = it.copy();
/* 640 */                       is.stackSize = 1;
/* 641 */                       ingredients.add(is);
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/* 646 */               ph = getAspectsFromIngredients(ingredients, recipe.getRecipeOutput(), history);
/* 647 */               if (recipe.getAspects() != null) {
/* 648 */                 for (Aspect a : recipe.getAspects().getAspects()) {
/* 649 */                   ph.add(a, (int)(Math.sqrt(recipe.getAspects().getAmount(a)) / recipe.getRecipeOutput().stackSize));
/*     */                 }
/*     */               }
/* 652 */               for (Aspect as : ph.copy().getAspects()) {
/* 653 */                 if (ph.getAmount(as) <= 0) ph.remove(as);
/*     */               }
/* 655 */               if (cval >= value) {
/* 656 */                 ret = ph;
/* 657 */                 value = cval;
/*     */               }
/*     */             } catch (Exception e) {
/* 660 */               e.printStackTrace();
/*     */             }
/*     */           } } }
/* 663 */     return ret;
/*     */   }
/*     */   
/*     */   private static ItemStack stackFromRecipeObject(Object obj) {
/* 667 */     ItemStack out = null;
/* 668 */     if ((obj instanceof ItemStack)) return (ItemStack)obj;
/* 669 */     int l; if ((obj instanceof String)) {
/* 670 */       String s = (String)obj;
/* 671 */       l = Integer.MAX_VALUE;
/* 672 */       for (ItemStack stack : net.minecraftforge.oredict.OreDictionary.getOres(s)) {
/* 673 */         if (out == null) out = stack.copy();
/* 674 */         AspectList al = AspectHelper.getObjectAspects(stack);
/* 675 */         int q = al.visSize();
/* 676 */         if ((q > 0) && (q < l)) {
/* 677 */           l = q;
/* 678 */           out = stack.copy();
/*     */         }
/*     */       }
/*     */     }
/* 682 */     return out;
/*     */   }
/*     */   
/*     */   private static AspectList generateTagsFromInfusionRecipes(Item item, int meta, ArrayList<List> history)
/*     */   {
/* 687 */     InfusionRecipe cr = ThaumcraftApi.getInfusionRecipe(new ItemStack(item, 1, meta));
/*     */     
/* 689 */     if (cr != null) {
/* 690 */       AspectList ot = cr.getAspects().copy();
/* 691 */       ArrayList<ItemStack> ingredients = new ArrayList();
/* 692 */       ItemStack is = stackFromRecipeObject(cr.getRecipeInput());
/* 693 */       is.stackSize = 1;
/* 694 */       ingredients.add(is);
/* 695 */       for (Object cat : cr.getComponents()) {
/* 696 */         ItemStack is2 = stackFromRecipeObject(cat);
/* 697 */         is2.stackSize = 1;
/* 698 */         ingredients.add(is2);
/*     */       }
/*     */       
/* 701 */       AspectList out = new AspectList();
/*     */       
/* 703 */       AspectList ot2 = getAspectsFromIngredients(ingredients, (ItemStack)cr.getRecipeOutput(), history);
/*     */       
/* 705 */       for (Aspect tt : ot2.getAspects()) {
/* 706 */         out.add(tt, ot2.getAmount(tt));
/*     */       }
/*     */       
/* 709 */       for (Aspect tt : ot.getAspects()) {
/* 710 */         int amt = (int)(Math.sqrt(ot.getAmount(tt)) / ((ItemStack)cr.getRecipeOutput()).stackSize);
/* 711 */         out.add(tt, amt);
/*     */       }
/*     */       
/* 714 */       for (Aspect as : out.getAspects()) {
/* 715 */         if (out.getAmount(as) <= 0) { out.remove(as);
/*     */         }
/*     */       }
/* 718 */       return out;
/*     */     }
/* 720 */     return null;
/*     */   }
/*     */   
/*     */   private static AspectList generateTagsFromCraftingRecipes(Item item, int meta, ArrayList<List> history) {
/* 724 */     AspectList ret = null;
/* 725 */     int value = Integer.MAX_VALUE;
/*     */     
/*     */ 
/* 728 */     List recipeList = CraftingManager.getInstance().getRecipeList();
/*     */     label1070:
/* 730 */     for (int q = 0; q < recipeList.size(); q++) {
/* 731 */       IRecipe recipe = (IRecipe)recipeList.get(q);
/* 732 */       if ((recipe != null) && (recipe.getRecipeOutput() != null) && (Item.getIdFromItem(recipe.getRecipeOutput().getItem()) > 0) && (recipe.getRecipeOutput().getItem() != null))
/*     */       {
/* 734 */         int idR = recipe.getRecipeOutput().getItemDamage() == 32767 ? 0 : recipe.getRecipeOutput().getItemDamage();
/* 735 */         int idS = meta == 32767 ? 0 : meta;
/*     */         
/* 737 */         if ((recipe.getRecipeOutput().getItem() == item) && (idR == idS)) {
/* 738 */           ArrayList<ItemStack> ingredients = new ArrayList();
/* 739 */           AspectList ph = new AspectList();
/* 740 */           int cval = 0;
/*     */           try {
/* 742 */             if ((recipeList.get(q) instanceof ShapedRecipes))
/*     */             {
/* 744 */               int width = ((ShapedRecipes)recipeList.get(q)).recipeWidth;
/* 745 */               int height = ((ShapedRecipes)recipeList.get(q)).recipeHeight;
/* 746 */               ItemStack[] items = ((ShapedRecipes)recipeList.get(q)).recipeItems;
/*     */               
/* 748 */               for (int i = 0; (i < width) && (i < 3); i++) {
/* 749 */                 for (int j = 0; (j < height) && (j < 3); j++)
/* 750 */                   if (items[(i + j * width)] != null)
/*     */                   {
/* 752 */                     if (Utils.isEETransmutionItem(items[(i + j * width)].getItem())) break label1070;
/* 753 */                     ItemStack is = items[(i + j * width)].copy();
/* 754 */                     is.stackSize = 1;
/* 755 */                     ingredients.add(is);
/*     */                   }
/*     */               }
/* 758 */             } else if ((recipeList.get(q) instanceof ShapelessRecipes)) {
/* 759 */               List<ItemStack> items = ((ShapelessRecipes)recipeList.get(q)).recipeItems;
/* 760 */               for (int i = 0; (i < items.size()) && (i < 9); i++)
/* 761 */                 if (items.get(i) != null)
/*     */                 {
/* 763 */                   if (Utils.isEETransmutionItem(((ItemStack)items.get(i)).getItem())) break label1070;
/* 764 */                   ItemStack is = ((ItemStack)items.get(i)).copy();
/* 765 */                   is.stackSize = 1;
/* 766 */                   ingredients.add(is);
/*     */                 }
/* 768 */             } else if ((recipeList.get(q) instanceof ShapedOreRecipe))
/*     */             {
/* 770 */               int size = ((ShapedOreRecipe)recipeList.get(q)).getRecipeSize();
/*     */               
/* 772 */               Object[] items = ((ShapedOreRecipe)recipeList.get(q)).getInput();
/*     */               
/* 774 */               for (int i = 0; (i < size) && (i < 9); i++) {
/* 775 */                 if (items[i] != null)
/*     */                 {
/* 777 */                   if ((items[i] instanceof List)) {
/* 778 */                     for (ItemStack it : (List)items[i]) {
/* 779 */                       if (Utils.isEETransmutionItem(it.getItem())) break label1070;
/* 780 */                       AspectList obj = getObjectTags(it, history);
/* 781 */                       if ((obj != null) && (obj.size() > 0)) {
/* 782 */                         ItemStack is = it.copy();
/* 783 */                         is.stackSize = 1;
/* 784 */                         ingredients.add(is);
/* 785 */                         break;
/*     */                       }
/*     */                     }
/*     */                   } else {
/* 789 */                     ItemStack it = (ItemStack)items[i];
/* 790 */                     if (Utils.isEETransmutionItem(it.getItem())) break label1070;
/* 791 */                     ItemStack is = it.copy();
/* 792 */                     is.stackSize = 1;
/* 793 */                     ingredients.add(is);
/*     */                   }
/*     */                 }
/*     */               }
/* 797 */             } else if ((recipeList.get(q) instanceof ShapelessOreRecipe)) {
/* 798 */               ArrayList items = ((ShapelessOreRecipe)recipeList.get(q)).getInput();
/*     */               
/* 800 */               for (int i = 0; (i < items.size()) && (i < 9); i++) {
/* 801 */                 if (items.get(i) != null)
/*     */                 {
/* 803 */                   if ((items.get(i) instanceof List)) {
/* 804 */                     for (ItemStack it : (List)items.get(i)) {
/* 805 */                       if (Utils.isEETransmutionItem(it.getItem())) break label1070;
/* 806 */                       AspectList obj = getObjectTags(it, history);
/*     */                       
/* 808 */                       if ((obj != null) && (obj.size() > 0)) {
/* 809 */                         ItemStack is = it.copy();
/* 810 */                         is.stackSize = 1;
/* 811 */                         ingredients.add(is);
/* 812 */                         break;
/*     */                       }
/*     */                     }
/*     */                   } else {
/* 816 */                     ItemStack it = (ItemStack)items.get(i);
/* 817 */                     if (Utils.isEETransmutionItem(it.getItem())) break label1070;
/* 818 */                     ItemStack is = it.copy();
/* 819 */                     is.stackSize = 1;
/* 820 */                     ingredients.add(is);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/* 825 */             ph = getAspectsFromIngredients(ingredients, recipe.getRecipeOutput(), history);
/* 826 */             for (Aspect as : ph.copy().getAspects()) {
/* 827 */               if (ph.getAmount(as) <= 0) ph.remove(as);
/*     */             }
/* 829 */             if ((ph.visSize() < value) && (ph.visSize() > 0)) {
/* 830 */               ret = ph;
/* 831 */               value = ph.visSize();
/*     */             }
/*     */           } catch (Exception e) {
/* 834 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       } }
/* 838 */     return ret;
/*     */   }
/*     */   
/*     */   private static AspectList getAspectsFromIngredients(ArrayList<ItemStack> ingredients, ItemStack recipeOut, ArrayList<List> history)
/*     */   {
/* 843 */     AspectList out = new AspectList();
/* 844 */     AspectList mid = new AspectList();
/* 845 */     for (ItemStack is : ingredients) {
/* 846 */       AspectList obj = getObjectTags(is, history);
/*     */       
/* 848 */       if (is.getItem().getContainerItem() != null) {
/* 849 */         if (is.getItem().getContainerItem() != is.getItem())
/*     */         {
/*     */ 
/* 852 */           AspectList objC = getObjectTags(new ItemStack(is.getItem(), 1, 32767), history);
/* 853 */           for (Aspect as : objC.getAspects()) {
/* 854 */             out.reduce(as, objC.getAmount(as));
/*     */           }
/*     */         }
/*     */       }
/* 858 */       else if (obj != null) {
/* 859 */         for (Aspect as : obj.getAspects()) {
/* 860 */           if (as != null) {
/* 861 */             mid.add(as, obj.getAmount(as));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 866 */     for (Aspect as : mid.getAspects()) {
/* 867 */       if (as != null) {
/* 868 */         out.add(as, (int)(mid.getAmount(as) * 0.75F / recipeOut.stackSize));
/*     */       }
/*     */     }
/* 871 */     for (Aspect as : out.getAspects()) {
/* 872 */       if (out.getAmount(as) <= 0) out.remove(as);
/*     */     }
/* 874 */     return out;
/*     */   }
/*     */   
/*     */   private static AspectList generateTagsFromRecipes(Item item, int meta, ArrayList<List> history)
/*     */   {
/* 879 */     AspectList ret = null;
/* 880 */     int value = 0;
/*     */     
/*     */ 
/* 883 */     ret = generateTagsFromCrucibleRecipes(item, meta, history);
/* 884 */     if (ret != null) { return ret;
/*     */     }
/*     */     
/* 887 */     ret = generateTagsFromArcaneRecipes(item, meta, history);
/* 888 */     if (ret != null) { return ret;
/*     */     }
/*     */     
/* 891 */     ret = generateTagsFromInfusionRecipes(item, meta, history);
/* 892 */     if (ret != null) { return ret;
/*     */     }
/*     */     
/* 895 */     ret = generateTagsFromCraftingRecipes(item, meta, history);
/* 896 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\crafting\ThaumcraftCraftingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */