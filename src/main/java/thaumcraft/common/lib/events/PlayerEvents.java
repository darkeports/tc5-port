/*     */ package thaumcraft.common.lib.events;
/*     */ 
/*     */ import baubles.api.BaublesApi;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.event.entity.item.ItemTossEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
/*     */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerEvent.LoadFromFile;
/*     */ import net.minecraftforge.event.entity.player.PlayerEvent.SaveToFile;
/*     */ import net.minecraftforge.event.entity.player.PlayerUseItemEvent.Finish;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectHelper;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IEssentiaContainerItem;
/*     */ import thaumcraft.api.items.IRechargable;
/*     */ import thaumcraft.api.items.IRepairable;
/*     */ import thaumcraft.api.items.IRepairableExtended;
/*     */ import thaumcraft.api.items.IRunicArmor;
/*     */ import thaumcraft.api.items.IVisDiscountGear;
/*     */ import thaumcraft.api.items.IWarpingGear;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.research.ResearchCategories;
/*     */ import thaumcraft.api.research.ResearchCategoryList;
/*     */ import thaumcraft.api.research.ResearchItem;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.items.armor.Hover;
/*     */ import thaumcraft.common.items.armor.ItemThaumostaticHarness;
/*     */ import thaumcraft.common.items.baubles.ItemAmuletRunic;
/*     */ import thaumcraft.common.items.baubles.ItemGirdleRunic;
/*     */ import thaumcraft.common.items.baubles.ItemRingRunic;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
/*     */ import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
/*     */ import thaumcraft.common.lib.potions.PotionDeathGaze;
/*     */ import thaumcraft.common.lib.potions.PotionUnnaturalHunger;
/*     */ import thaumcraft.common.lib.potions.PotionWarpWard;
/*     */ import thaumcraft.common.lib.research.PlayerKnowledge;
/*     */ import thaumcraft.common.lib.research.ResearchManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerEvents
/*     */ {
/*     */   @SubscribeEvent
/*     */   public void livingTick(LivingEvent.LivingUpdateEvent event)
/*     */   {
/*  85 */     if ((event.entity instanceof EntityPlayer)) {
/*  86 */       EntityPlayer player = (EntityPlayer)event.entity;
/*     */       
/*  88 */       handleMisc(player);
/*     */       
/*  90 */       handleSpeedMods(player);
/*     */       
/*  92 */       if (!player.worldObj.isRemote) {
/*  93 */         handleRunicArmor(player);
/*     */         
/*  95 */         handleRepair(player);
/*     */         
/*  97 */         handleWarp(player);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void handleMisc(EntityPlayer player)
/*     */   {
/* 107 */     if ((Hover.getHover(player.getEntityId())) && ((player.inventory.armorItemInSlot(2) == null) || (!(player.inventory.armorItemInSlot(2).getItem() instanceof ItemThaumostaticHarness))))
/*     */     {
/* 109 */       Hover.setHover(player.getEntityId(), false);
/* 110 */       player.capabilities.isFlying = false;
/*     */     }
/*     */     
/*     */ 
/* 114 */     if ((player.worldObj.provider.getDimensionId() == Config.dimensionOuterId) && (player.ticksExisted % 20 == 0) && (!player.isSpectator()) && (!player.capabilities.isCreativeMode) && ((player.capabilities.isFlying) || (Hover.getHover(player.getEntityId()))))
/*     */     {
/*     */ 
/* 117 */       player.capabilities.isFlying = false;
/* 118 */       Hover.setHover(player.getEntityId(), false);
/* 119 */       player.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + "" + EnumChatFormatting.GRAY + StatCollector.translateToLocal("tc.break.fly")));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SubscribeEvent
/*     */   public void tooltipEvent(ItemTooltipEvent event)
/*     */   {
/* 128 */     int charge = getFinalCharge(event.itemStack);
/* 129 */     if (charge > 0) {
/* 130 */       event.toolTip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.runic.charge") + " +" + charge);
/*     */     }
/* 132 */     int warp = getFinalWarp(event.itemStack, event.entityPlayer);
/* 133 */     if (warp > 0) {
/* 134 */       event.toolTip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("item.warping") + " " + warp);
/*     */     }
/* 136 */     AspectList al = getFinalDiscount(event.itemStack, event.entityPlayer);
/* 137 */     if (al != null) {
/* 138 */       if (al.getAmount(Aspect.ENERGY) > 0) {
/* 139 */         event.toolTip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + al.getAmount(Aspect.ENERGY) + "%");
/*     */       }
/*     */       
/* 142 */       for (Aspect a : al.getAspectsSortedByName()) {
/* 143 */         if (a != Aspect.ENERGY) {
/* 144 */           event.toolTip.add(EnumChatFormatting.DARK_PURPLE + a.getName() + " " + StatCollector.translateToLocal("tc.discount") + ": " + al.getAmount(a) + "%");
/*     */         }
/*     */       }
/*     */     }
/* 148 */     if (event.itemStack != null)
/*     */     {
/* 150 */       if ((event.itemStack.getItem() instanceof IRechargable)) {
/* 151 */         int c = Math.round(100.0F * ((IRechargable)event.itemStack.getItem()).getChargeLevel(event.itemStack));
/* 152 */         if (c >= 0) {
/* 153 */           event.toolTip.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("tc.charge") + " " + c + "%");
/*     */         }
/*     */       }
/* 156 */       if ((event.itemStack.getItem() instanceof IEssentiaContainerItem)) {
/* 157 */         AspectList aspects = ((IEssentiaContainerItem)event.itemStack.getItem()).getAspects(event.itemStack);
/* 158 */         if ((aspects != null) && (aspects.size() > 0)) {
/* 159 */           for (Aspect tag : aspects.getAspectsSortedByName()) {
/* 160 */             event.toolTip.add(tag.getName() + " x" + aspects.getAmount(tag));
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 165 */       NBTTagList nbttaglist = EnumInfusionEnchantment.getInfusionEnchantmentTagList(event.itemStack);
/* 166 */       if (nbttaglist != null)
/*     */       {
/* 168 */         for (int j = 0; j < nbttaglist.tagCount(); j++)
/*     */         {
/* 170 */           int k = nbttaglist.getCompoundTagAt(j).getShort("id");
/* 171 */           int l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
/* 172 */           if ((k >= 0) && (k < EnumInfusionEnchantment.values().length)) {
/* 173 */             String s = EnumChatFormatting.GOLD + StatCollector.translateToLocal(new StringBuilder().append("enchantment.infusion.").append(EnumInfusionEnchantment.values()[k].toString()).toString());
/* 174 */             if (EnumInfusionEnchantment.values()[k].maxLevel > 1) {
/* 175 */               s = s + " " + StatCollector.translateToLocal(new StringBuilder().append("enchantment.level.").append(l).toString());
/*     */             }
/* 177 */             event.toolTip.add(1, s);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 186 */   static HashMap<Integer, Long> nextCycle = new HashMap();
/* 187 */   static HashMap<Integer, Integer> lastCharge = new HashMap();
/* 188 */   static HashMap<Integer, Integer> lastMaxCharge = new HashMap();
/* 189 */   static HashMap<Integer, Integer[]> runicInfo = new HashMap();
/* 190 */   static HashMap<String, Long> upgradeCooldown = new HashMap();
/* 191 */   static HashMap<Integer, Boolean> dirtyList = new HashMap();
/*     */   
/*     */   public static void markRunicDirty(Entity entity) {
/* 194 */     dirtyList.put(Integer.valueOf(entity.getEntityId()), Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   private void handleRunicArmor(EntityPlayer player)
/*     */   {
/* 199 */     if ((player.ticksExisted % 100 == 0) || (dirtyList.containsKey(Integer.valueOf(player.getEntityId())))) {
/* 200 */       dirtyList.remove(Integer.valueOf(player.getEntityId()));
/* 201 */       int max = 0;
/* 202 */       int charged = 0;
/* 203 */       int kinetic = 0;
/* 204 */       int healing = 0;
/* 205 */       int emergency = 0;
/*     */       
/* 207 */       for (int a = 0; a < 4; a++) {
/* 208 */         if ((player.inventory.armorItemInSlot(a) != null) && ((player.inventory.armorItemInSlot(a).getItem() instanceof IRunicArmor)))
/*     */         {
/* 210 */           int amount = getFinalCharge(player.inventory.armorItemInSlot(a));
/* 211 */           max += amount;
/*     */         }
/*     */       }
/*     */       
/* 215 */       IInventory baubles = BaublesApi.getBaubles(player);
/* 216 */       for (int a = 0; a < 4; a++) {
/* 217 */         if ((baubles.getStackInSlot(a) != null) && ((baubles.getStackInSlot(a).getItem() instanceof IRunicArmor)))
/*     */         {
/* 219 */           int amount = getFinalCharge(baubles.getStackInSlot(a));
/* 220 */           if ((baubles.getStackInSlot(a).getItem() instanceof ItemRingRunic)) {
/* 221 */             switch (baubles.getStackInSlot(a).getItemDamage()) {
/* 222 */             case 2:  charged++; break;
/* 223 */             case 3:  healing++;
/*     */             }
/*     */             
/* 226 */           } else if (((baubles.getStackInSlot(a).getItem() instanceof ItemAmuletRunic)) && (baubles.getStackInSlot(a).getItemDamage() == 1))
/*     */           {
/* 228 */             emergency++;
/*     */           }
/* 230 */           else if (((baubles.getStackInSlot(a).getItem() instanceof ItemGirdleRunic)) && (baubles.getStackInSlot(a).getItemDamage() == 1))
/*     */           {
/* 232 */             kinetic++;
/*     */           }
/* 234 */           max += amount;
/*     */         }
/*     */       }
/*     */       
/* 238 */       if (lastMaxCharge.containsKey(Integer.valueOf(player.getEntityId()))) {
/* 239 */         int charge = ((Integer)lastMaxCharge.get(Integer.valueOf(player.getEntityId()))).intValue();
/* 240 */         if (charge > max) {
/* 241 */           player.setAbsorptionAmount(player.getAbsorptionAmount() - (charge - max));
/*     */         }
/* 243 */         if (max <= 0) {
/* 244 */           lastMaxCharge.remove(Integer.valueOf(player.getEntityId()));
/*     */         }
/*     */       }
/*     */       
/* 248 */       if (max > 0) {
/* 249 */         runicInfo.put(Integer.valueOf(player.getEntityId()), new Integer[] { Integer.valueOf(max), Integer.valueOf(charged), Integer.valueOf(kinetic), Integer.valueOf(healing), Integer.valueOf(emergency) });
/* 250 */         lastMaxCharge.put(Integer.valueOf(player.getEntityId()), Integer.valueOf(max));
/*     */       } else {
/* 252 */         runicInfo.remove(Integer.valueOf(player.getEntityId()));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 258 */     if (runicInfo.containsKey(Integer.valueOf(player.getEntityId()))) {
/* 259 */       if (!nextCycle.containsKey(Integer.valueOf(player.getEntityId()))) {
/* 260 */         nextCycle.put(Integer.valueOf(player.getEntityId()), Long.valueOf(0L));
/*     */       }
/* 262 */       long time = System.currentTimeMillis();
/*     */       
/* 264 */       int charge = (int)player.getAbsorptionAmount();
/* 265 */       if ((charge == 0) && (lastCharge.containsKey(Integer.valueOf(player.getEntityId()))) && (((Integer)lastCharge.get(Integer.valueOf(player.getEntityId()))).intValue() > 0))
/*     */       {
/*     */ 
/* 268 */         nextCycle.put(Integer.valueOf(player.getEntityId()), Long.valueOf(time + Config.shieldWait));
/* 269 */         lastCharge.put(Integer.valueOf(player.getEntityId()), Integer.valueOf(0));
/*     */       }
/*     */       
/* 272 */       if ((charge < ((Integer[])runicInfo.get(Integer.valueOf(player.getEntityId())))[0].intValue()) && (((Long)nextCycle.get(Integer.valueOf(player.getEntityId()))).longValue() < time) && (!AuraHandler.shouldPreserveAura(player.worldObj, player, player.getPosition(), Aspect.AIR)) && (!AuraHandler.shouldPreserveAura(player.worldObj, player, player.getPosition(), Aspect.EARTH)) && (AuraHandler.drainAura(player.worldObj, new BlockPos(player), Aspect.AIR, Config.shieldCost, false)) && (AuraHandler.drainAura(player.worldObj, new BlockPos(player), Aspect.EARTH, Config.shieldCost, false)))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 279 */         AuraHandler.drainAura(player.worldObj, new BlockPos(player), Aspect.AIR, Config.shieldCost);
/* 280 */         AuraHandler.drainAura(player.worldObj, new BlockPos(player), Aspect.EARTH, Config.shieldCost);
/*     */         
/* 282 */         long interval = Config.shieldRecharge - ((Integer[])runicInfo.get(Integer.valueOf(player.getEntityId())))[1].intValue() * 500;
/* 283 */         nextCycle.put(Integer.valueOf(player.getEntityId()), Long.valueOf(time + interval));
/*     */         
/* 285 */         player.setAbsorptionAmount(charge + 1);
/* 286 */         lastCharge.put(Integer.valueOf(player.getEntityId()), Integer.valueOf(charge + 1));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getFinalCharge(ItemStack stack)
/*     */   {
/* 293 */     if (!(stack.getItem() instanceof IRunicArmor)) return 0;
/* 294 */     IRunicArmor armor = (IRunicArmor)stack.getItem();
/* 295 */     int base = armor.getRunicCharge(stack);
/* 296 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("RS.HARDEN"))) {
/* 297 */       base += stack.getTagCompound().getByte("RS.HARDEN");
/*     */     }
/* 299 */     return base;
/*     */   }
/*     */   
/*     */   public static int getFinalWarp(ItemStack stack, EntityPlayer player) {
/* 303 */     if (stack == null) return 0;
/* 304 */     int warp = 0;
/* 305 */     if ((stack.getItem() instanceof IWarpingGear)) {
/* 306 */       IWarpingGear armor = (IWarpingGear)stack.getItem();
/* 307 */       warp += armor.getWarp(stack, player);
/*     */     }
/* 309 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("TC.WARP"))) {
/* 310 */       warp += stack.getTagCompound().getByte("TC.WARP");
/*     */     }
/* 312 */     return warp;
/*     */   }
/*     */   
/*     */   public static AspectList getFinalDiscount(ItemStack stack, EntityPlayer player) {
/* 316 */     if ((stack == null) || (!(stack.getItem() instanceof IVisDiscountGear))) return null;
/* 317 */     IVisDiscountGear gear = (IVisDiscountGear)stack.getItem();
/* 318 */     AspectList out = new AspectList();
/* 319 */     int gen = gear.getVisDiscount(stack, player, null);
/* 320 */     if (gen > 0) out.add(Aspect.ENERGY, gen);
/* 321 */     Aspect a; int d; for (Iterator i$ = Aspect.getPrimalAspects().iterator(); i$.hasNext(); 
/*     */         
/* 323 */         out.add(a, d))
/*     */     {
/* 321 */       a = (Aspect)i$.next();
/* 322 */       d = gear.getVisDiscount(stack, player, a);
/* 323 */       if ((d <= 0) || ((d == gen) && (gen != 0))) {}
/*     */     }
/* 325 */     return out.size() > 0 ? out : null;
/*     */   }
/*     */   
/*     */   public static int getHardening(ItemStack stack) {
/* 329 */     if (!(stack.getItem() instanceof IRunicArmor)) return 0;
/* 330 */     int base = 0;
/* 331 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("RS.HARDEN"))) {
/* 332 */       base += stack.getTagCompound().getByte("RS.HARDEN");
/*     */     }
/* 334 */     return base;
/*     */   }
/*     */   
/*     */   private void handleSpeedMods(EntityPlayer player) {
/* 338 */     updateSpeed(player);
/*     */     
/* 340 */     if ((player.worldObj.isRemote) && ((player.isSneaking()) || (player.inventory.armorItemInSlot(0) == null) || (player.inventory.armorItemInSlot(0).getItem() != ItemsTC.travellerBoots)) && (this.prevStep.containsKey(Integer.valueOf(player.getEntityId()))))
/*     */     {
/*     */ 
/*     */ 
/* 344 */       player.stepHeight = ((Float)this.prevStep.get(Integer.valueOf(player.getEntityId()))).floatValue();
/* 345 */       this.prevStep.remove(Integer.valueOf(player.getEntityId()));
/*     */     }
/*     */   }
/*     */   
/* 349 */   public HashMap<Integer, Float> prevStep = new HashMap();
/*     */   
/*     */   private void updateSpeed(EntityPlayer player)
/*     */   {
/*     */     try {
/* 354 */       if ((!player.capabilities.isFlying) && (player.inventory.armorItemInSlot(0) != null) && (player.moveForward > 0.0F))
/*     */       {
/*     */ 
/*     */ 
/* 358 */         int haste = EnchantmentHelper.getEnchantmentLevel(Config.enchHaste.effectId, player.inventory.armorItemInSlot(0));
/* 359 */         if (haste > 0) {
/* 360 */           float bonus = haste * 0.015F;
/* 361 */           if (player.isAirBorne) bonus /= 2.0F;
/* 362 */           if (player.isInWater()) bonus /= 2.0F;
/* 363 */           player.moveFlying(0.0F, 1.0F, bonus);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void playerJumps(LivingEvent.LivingJumpEvent event)
/*     */   {
/* 373 */     if (((event.entity instanceof EntityPlayer)) && (((EntityPlayer)event.entity).inventory.armorItemInSlot(0) != null) && (((EntityPlayer)event.entity).inventory.armorItemInSlot(0).getItem() == ItemsTC.travellerBoots))
/*     */     {
/*     */ 
/* 376 */       event.entityLiving.motionY += 0.2750000059604645D; }
/*     */   }
/*     */   
/*     */   private void handleRepair(EntityPlayer player) {
/* 380 */     if (player.ticksExisted % 40 == 0)
/*     */     {
/*     */ 
/* 383 */       for (int a = 0; a < InventoryPlayer.getHotbarSize(); a++) {
/* 384 */         if (player.inventory.mainInventory[a] != null) {
/* 385 */           ItemStack is = player.inventory.mainInventory[a];
/*     */           
/*     */ 
/* 388 */           if ((is.getItemDamage() > 0) && ((is.getItem() instanceof IRepairable)) && (!player.capabilities.isCreativeMode))
/*     */           {
/* 390 */             doRepair(is, player);
/*     */           }
/*     */         }
/*     */       }
/* 394 */       for (int a = 0; a < 4; a++) {
/* 395 */         if (player.inventory.armorItemInSlot(a) != null) {
/* 396 */           ItemStack is = player.inventory.armorItemInSlot(a);
/*     */           
/*     */ 
/* 399 */           if ((is.getItemDamage() > 0) && ((is.getItem() instanceof IRepairable)) && (!player.capabilities.isCreativeMode))
/*     */           {
/* 401 */             doRepair(is, player);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void doRepair(ItemStack is, EntityPlayer player) {
/* 409 */     int level = EnchantmentHelper.getEnchantmentLevel(Config.enchRepair.effectId, is);
/* 410 */     if (level <= 0) return;
/* 411 */     if (level > 2) {
/* 412 */       level = 2;
/*     */     }
/*     */     
/* 415 */     AspectList cost = ThaumcraftCraftingManager.getObjectTags(is);
/* 416 */     if ((cost == null) || (cost.size() == 0)) return;
/* 417 */     cost = AspectHelper.reduceToPrimals(cost);
/* 418 */     AspectList finalCost = new AspectList();
/* 419 */     finalCost.merge(Aspect.EARTH, level);
/* 420 */     for (Aspect a : cost.getAspects()) {
/* 421 */       if (a != null) {
/* 422 */         finalCost.merge(a, level);
/*     */       }
/*     */     }
/* 425 */     Aspect[] aa = finalCost.getAspects();
/* 426 */     Aspect as = aa[player.getRNG().nextInt(aa.length)];
/* 427 */     if ((is.getItem() instanceof IRepairableExtended)) {
/* 428 */       if ((((IRepairableExtended)is.getItem()).doRepair(is, player, level)) && 
/* 429 */         (!AuraHandler.shouldPreserveAura(player.worldObj, player, new BlockPos(player), as)) && (AuraHandler.drainAura(player.worldObj, new BlockPos(player), as, level)))
/*     */       {
/* 431 */         is.damageItem(-level, player);
/*     */       }
/*     */       
/*     */     }
/* 435 */     else if ((!AuraHandler.shouldPreserveAura(player.worldObj, player, new BlockPos(player), as)) && (AuraHandler.drainAura(player.worldObj, new BlockPos(player), as, level)))
/*     */     {
/* 437 */       is.damageItem(-level, player);
/*     */     }
/*     */   }
/*     */   
/*     */   private void handleWarp(EntityPlayer player)
/*     */   {
/* 443 */     if ((!Config.wuss) && (player.ticksExisted > 0) && (player.ticksExisted % 2000 == 0) && (!player.isPotionActive(PotionWarpWard.instance)))
/*     */     {
/* 445 */       WarpEvents.checkWarpEvent(player);
/*     */     }
/*     */     
/*     */ 
/* 449 */     if ((player.ticksExisted % 10 == 0) && (player.isPotionActive(PotionDeathGaze.instance))) {
/* 450 */       WarpEvents.checkDeathGaze(player);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @SubscribeEvent
/*     */   public void droppedItem(ItemTossEvent event)
/*     */   {
/* 458 */     NBTTagCompound itemData = event.entityItem.getEntityData();
/* 459 */     itemData.setString("thrower", event.player.getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @SubscribeEvent
/*     */   public void playerLoad(PlayerEvent.LoadFromFile event)
/*     */   {
/* 468 */     ResearchManager.loadingBlocked = true;
/* 469 */     EntityPlayer p = event.entityPlayer;
/* 470 */     Thaumcraft.proxy.getPlayerKnowledge().wipePlayerKnowledge(p.getName());
/*     */     
/* 472 */     File file1 = getPlayerFile("thaum", event.playerDirectory, p.getName());
/*     */     
/* 474 */     ResearchManager.loadPlayerData(p.getName(), file1, getPlayerFile("thaumback", event.playerDirectory, p.getName()));
/*     */     
/*     */ 
/*     */ 
/* 478 */     Collection<ResearchCategoryList> rc = ResearchCategories.researchCategories.values();
/* 479 */     for (ResearchCategoryList cat : rc) {
/* 480 */       Collection<ResearchItem> res = cat.research.values();
/* 481 */       for (ResearchItem ri : res) {
/* 482 */         if (ri.isAutoUnlock()) {
/* 483 */           Thaumcraft.proxy.getResearchManager().completeResearch(p, ri.key, (byte)0);
/*     */         }
/*     */       }
/*     */     }
/* 487 */     ResearchManager.loadingBlocked = false;
/*     */   }
/*     */   
/*     */   public File getPlayerFile(String suffix, File playerDirectory, String playername)
/*     */   {
/* 492 */     if ("dat".equals(suffix)) throw new IllegalArgumentException("The suffix 'dat' is reserved");
/* 493 */     return new File(playerDirectory, "_" + playername + "." + suffix);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void playerSave(PlayerEvent.SaveToFile event) {
/* 498 */     ResearchManager.loadingBlocked = true;
/* 499 */     EntityPlayer p = event.entityPlayer;
/* 500 */     ResearchManager.savePlayerData(p, getPlayerFile("thaum", event.playerDirectory, p.getName()), getPlayerFile("thaumback", event.playerDirectory, p.getName()));
/*     */     
/*     */ 
/* 503 */     ResearchManager.loadingBlocked = false;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void finishedUsingItem(PlayerUseItemEvent.Finish event)
/*     */   {
/* 509 */     if ((!event.entity.worldObj.isRemote) && 
/* 510 */       (event.entityPlayer.isPotionActive(PotionUnnaturalHunger.instance))) {
/* 511 */       if ((event.item.isItemEqual(new ItemStack(Items.rotten_flesh))) || (event.item.isItemEqual(new ItemStack(ItemsTC.brain))))
/*     */       {
/* 513 */         PotionEffect pe = event.entityPlayer.getActivePotionEffect(PotionUnnaturalHunger.instance);
/*     */         
/* 515 */         int amp = pe.getAmplifier() - 1;
/* 516 */         int duration = pe.getDuration() - 600;
/*     */         
/* 518 */         event.entityPlayer.removePotionEffect(PotionUnnaturalHunger.instance.getId());
/*     */         
/* 520 */         if ((duration > 0) && (amp >= 0)) {
/* 521 */           pe = new PotionEffect(PotionUnnaturalHunger.instance.getId(), duration, amp, true, false);
/* 522 */           pe.getCurativeItems().clear();
/* 523 */           pe.addCurativeItem(new ItemStack(Items.rotten_flesh));
/* 524 */           event.entityPlayer.addPotionEffect(pe);
/*     */         }
/*     */         
/* 527 */         event.entityPlayer.addChatMessage(new ChatComponentText("§2§o" + StatCollector.translateToLocal("warp.text.hunger.2")));
/*     */ 
/*     */ 
/*     */       }
/* 531 */       else if ((event.item.getItem() instanceof ItemFood)) {
/* 532 */         event.entityPlayer.addChatMessage(new ChatComponentText("§4§o" + StatCollector.translateToLocal("warp.text.hunger.1")));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\events\PlayerEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */