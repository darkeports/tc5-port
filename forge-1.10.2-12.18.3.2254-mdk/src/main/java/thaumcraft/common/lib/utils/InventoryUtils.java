/*     */ package thaumcraft.common.lib.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityHopper;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.world.GameRules;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.registry.GameRegistry;
/*     */ import net.minecraftforge.fml.common.registry.GameRegistry.UniqueIdentifier;
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ import thaumcraft.common.tiles.crafting.TileArcaneWorkbench;
/*     */ 
/*     */ public class InventoryUtils
/*     */ {
/*     */   public static boolean hasRoomFor(ItemStack stack, IInventory inventory, EnumFacing side)
/*     */   {
/*  34 */     if (stack != null) {
/*  35 */       ItemStack is = placeItemStackIntoInventory(stack, inventory, side, false);
/*  36 */       return !ItemStack.areItemStacksEqual(stack, is);
/*     */     }
/*  38 */     return false;
/*     */   }
/*     */   
/*     */   public static ItemStack placeItemStackIntoInventory(ItemStack stack, IInventory inventory, EnumFacing side, boolean doit)
/*     */   {
/*  43 */     ItemStack itemstack = stack.copy();
/*  44 */     ItemStack itemstack1 = insertStack(inventory, itemstack, side, doit);
/*     */     
/*  46 */     if ((itemstack1 == null) || (itemstack1.stackSize == 0))
/*     */     {
/*  48 */       if (doit) inventory.markDirty();
/*  49 */       return null;
/*     */     }
/*  51 */     stack = itemstack1;
/*  52 */     return stack.copy();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ItemStack insertStack(IInventory inventory, ItemStack stack1, EnumFacing side, boolean doit)
/*     */   {
/*  60 */     if (((inventory instanceof ISidedInventory)) && (side != null))
/*     */     {
/*  62 */       ISidedInventory isidedinventory = (ISidedInventory)inventory;
/*  63 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/*     */ 
/*  66 */       if (aint != null) {
/*  67 */         for (int j = 0; (j < aint.length) && (stack1 != null) && (stack1.stackSize > 0); j++)
/*     */         {
/*  69 */           if ((inventory.getStackInSlot(aint[j]) != null) && (inventory.getStackInSlot(aint[j]).isItemEqual(stack1)))
/*  70 */             stack1 = attemptInsertion(inventory, stack1, aint[j], side, doit);
/*  71 */           if ((stack1 == null) || (stack1.stackSize == 0))
/*     */             break;
/*     */         }
/*     */       }
/*  75 */       if ((aint != null) && (stack1 != null) && (stack1.stackSize > 0)) {
/*  76 */         for (int j = 0; (j < aint.length) && (stack1 != null) && (stack1.stackSize > 0); j++)
/*     */         {
/*  78 */           stack1 = attemptInsertion(inventory, stack1, aint[j], side, doit);
/*  79 */           if ((stack1 == null) || (stack1.stackSize == 0))
/*     */             break;
/*     */         }
/*     */       }
/*     */     } else {
/*  84 */       int k = inventory.getSizeInventory();
/*     */       
/*     */ 
/*  87 */       for (int l = 0; (l < k) && (stack1 != null) && (stack1.stackSize > 0); l++)
/*     */       {
/*  89 */         if ((inventory.getStackInSlot(l) != null) && (inventory.getStackInSlot(l).isItemEqual(stack1)))
/*  90 */           stack1 = attemptInsertion(inventory, stack1, l, side, doit);
/*  91 */         if ((stack1 == null) || (stack1.stackSize == 0))
/*     */           break;
/*     */       }
/*  94 */       if ((stack1 != null) && (stack1.stackSize > 0))
/*     */       {
/*  96 */         TileEntityChest dc = null;
/*  97 */         if ((inventory instanceof TileEntity)) {
/*  98 */           dc = getDoubleChest((TileEntity)inventory);
/*  99 */           if (dc != null) {
/* 100 */             int k2 = dc.getSizeInventory();
/*     */             
/* 102 */             for (int l = 0; (l < k2) && (stack1 != null) && (stack1.stackSize > 0); l++)
/*     */             {
/* 104 */               if ((dc.getStackInSlot(l) != null) && (dc.getStackInSlot(l).isItemEqual(stack1)))
/* 105 */                 stack1 = attemptInsertion(dc, stack1, l, side, doit);
/* 106 */               if ((stack1 == null) || (stack1.stackSize == 0))
/*     */                 break;
/*     */             }
/*     */           }
/*     */         }
/* 111 */         if ((stack1 != null) && (stack1.stackSize > 0)) {
/* 112 */           for (int l = 0; (l < k) && (stack1 != null) && (stack1.stackSize > 0); l++)
/*     */           {
/* 114 */             stack1 = attemptInsertion(inventory, stack1, l, side, doit);
/* 115 */             if ((stack1 == null) || (stack1.stackSize == 0))
/*     */               break;
/*     */           }
/* 118 */           if ((stack1 != null) && (stack1.stackSize > 0) && (dc != null)) {
/* 119 */             int k2 = dc.getSizeInventory();
/* 120 */             for (int l = 0; (l < k2) && (stack1 != null) && (stack1.stackSize > 0); l++)
/*     */             {
/* 122 */               stack1 = attemptInsertion(dc, stack1, l, side, doit);
/* 123 */               if ((stack1 == null) || (stack1.stackSize == 0))
/*     */                 break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 130 */     if ((stack1 != null) && (stack1.stackSize == 0))
/*     */     {
/* 132 */       stack1 = null;
/*     */     }
/*     */     
/* 135 */     return stack1;
/*     */   }
/*     */   
/*     */   private static ItemStack attemptInsertion(IInventory inventory, ItemStack stack, int slot, EnumFacing side, boolean doit)
/*     */   {
/* 140 */     ItemStack slotStack = inventory.getStackInSlot(slot);
/* 141 */     if (canInsertItemToInventory(inventory, stack, slot, side))
/*     */     {
/* 143 */       boolean flag = false;
/*     */       
/* 145 */       if (slotStack == null)
/*     */       {
/* 147 */         if (inventory.getInventoryStackLimit() < stack.stackSize) {
/* 148 */           ItemStack in = stack.splitStack(inventory.getInventoryStackLimit());
/* 149 */           if (doit) inventory.setInventorySlotContents(slot, in);
/*     */         }
/*     */         else {
/* 152 */           if (doit) inventory.setInventorySlotContents(slot, stack);
/* 153 */           stack = null;
/*     */         }
/* 155 */         flag = true;
/*     */       }
/* 157 */       else if (areItemStacksEqualStrict(slotStack, stack))
/*     */       {
/* 159 */         int k = Math.min(inventory.getInventoryStackLimit() - slotStack.stackSize, stack.getMaxStackSize() - slotStack.stackSize);
/*     */         
/* 161 */         int l = Math.min(stack.stackSize, k);
/* 162 */         stack.stackSize -= l;
/* 163 */         if (doit) slotStack.stackSize += l;
/* 164 */         flag = l > 0;
/*     */       }
/*     */       
/* 167 */       if ((flag) && (doit))
/*     */       {
/* 169 */         if ((inventory instanceof TileEntityHopper))
/*     */         {
/* 171 */           ((TileEntityHopper)inventory).setTransferCooldown(8);
/* 172 */           inventory.markDirty();
/*     */         }
/*     */         
/* 175 */         inventory.markDirty();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 182 */     return stack;
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
/*     */   public static int countItemsInWorld(World world, BlockPos pos, ItemStack stack, double range, boolean ignoreDamage, boolean ignoreNBT, boolean useOre, boolean useMod)
/*     */   {
/* 262 */     int count = 0;
/* 263 */     ArrayList<Entity> l = EntityUtils.getEntitiesInRange(world, pos, null, EntityItem.class, range);
/* 264 */     for (Entity e : l) {
/* 265 */       EntityItem ei = (EntityItem)e;
/* 266 */       if ((ei.getEntityItem() != null) && (areItemStacksEqual(stack, ei.getEntityItem(), ignoreDamage, ignoreNBT, useOre, useMod))) {
/* 267 */         count += ei.getEntityItem().stackSize;
/*     */       }
/*     */     }
/* 270 */     return count;
/*     */   }
/*     */   
/*     */   public static int inventoryContainsAmount(IInventory inventory, ItemStack stack, EnumFacing side, boolean ignoreDamage, boolean ignoreNBT, boolean useOre, boolean useMod) {
/* 274 */     int count = 0;
/*     */     
/* 276 */     if (((inventory instanceof ISidedInventory)) && (side != null))
/*     */     {
/* 278 */       ISidedInventory isidedinventory = (ISidedInventory)inventory;
/* 279 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/* 281 */       for (int j = 0; (j < aint.length) && (stack != null) && (stack.stackSize > 0); j++)
/*     */       {
/* 283 */         ItemStack ts = attemptExtraction(inventory, stack, aint[j], side, ignoreDamage, ignoreNBT, useOre, useMod, false);
/* 284 */         if (ts != null) count += ts.stackSize;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 289 */       int k = inventory.getSizeInventory();
/*     */       
/* 291 */       for (int l = 0; (l < k) && (stack != null) && (stack.stackSize > 0); l++)
/*     */       {
/* 293 */         ItemStack ts = attemptExtraction(inventory, stack, l, side, ignoreDamage, ignoreNBT, useOre, useMod, false);
/* 294 */         if (ts != null) { count += ts.stackSize;
/*     */         }
/*     */       }
/*     */     }
/* 298 */     return count;
/*     */   }
/*     */   
/*     */   public static boolean inventoryContains(IInventory inventory, ItemStack stack, EnumFacing side, boolean ignoreDamage, boolean ignoreNBT, boolean useOre, boolean useMod) {
/* 302 */     ItemStack s = extractStack(inventory, stack, side, ignoreDamage, ignoreNBT, useOre, useMod, false);
/* 303 */     return (s != null) && (s.stackSize > 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ItemStack extractStack(IInventory inventory, ItemStack stack1, EnumFacing side, boolean ignoreDamage, boolean ignoreNBT, boolean useOre, boolean useMod, boolean doit)
/*     */   {
/* 311 */     ItemStack outStack = null;
/* 312 */     if (((inventory instanceof ISidedInventory)) && (side != null))
/*     */     {
/* 314 */       ISidedInventory isidedinventory = (ISidedInventory)inventory;
/* 315 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/* 317 */       for (int j = 0; (j < aint.length) && (stack1 != null) && (stack1.stackSize > 0) && (outStack == null); j++)
/*     */       {
/* 319 */         outStack = attemptExtraction(inventory, stack1, aint[j], side, ignoreDamage, ignoreNBT, useOre, useMod, doit);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 324 */       int k = inventory.getSizeInventory();
/*     */       
/* 326 */       for (int l = 0; (l < k) && (stack1 != null) && (stack1.stackSize > 0) && (outStack == null); l++)
/*     */       {
/* 328 */         outStack = attemptExtraction(inventory, stack1, l, side, ignoreDamage, ignoreNBT, useOre, useMod, doit);
/*     */       }
/*     */     }
/*     */     
/* 332 */     if ((outStack == null) || (outStack.stackSize == 0))
/*     */     {
/* 334 */       return null;
/*     */     }
/*     */     
/* 337 */     return outStack.copy();
/*     */   }
/*     */   
/*     */   public static ItemStack attemptExtraction(IInventory inventory, ItemStack stack, int slot, EnumFacing side, boolean ignoreDamage, boolean ignoreNBT, boolean useOre, boolean useMod, boolean doit)
/*     */   {
/* 342 */     ItemStack slotStack = inventory.getStackInSlot(slot);
/* 343 */     ItemStack outStack = stack.copy();
/*     */     
/* 345 */     if (canExtractItemFromInventory(inventory, slotStack, slot, side))
/*     */     {
/* 347 */       boolean flag = false;
/*     */       
/* 349 */       if (areItemStacksEqual(slotStack, stack, ignoreDamage, ignoreNBT, useOre, useMod))
/*     */       {
/* 351 */         outStack = slotStack.copy();
/* 352 */         outStack.stackSize = stack.stackSize;
/* 353 */         int k = stack.stackSize - slotStack.stackSize;
/*     */         
/* 355 */         if (k >= 0) {
/* 356 */           outStack.stackSize -= k;
/* 357 */           if (doit) {
/* 358 */             slotStack = null;
/* 359 */             inventory.setInventorySlotContents(slot, null);
/*     */           }
/*     */         }
/* 362 */         else if (doit) {
/* 363 */           slotStack.stackSize -= outStack.stackSize;
/* 364 */           inventory.setInventorySlotContents(slot, slotStack);
/*     */         }
/*     */         
/* 367 */         flag = true;
/*     */       }
/*     */       else
/*     */       {
/* 371 */         return null;
/*     */       }
/*     */       
/* 374 */       if ((flag) && (doit))
/*     */       {
/* 376 */         inventory.markDirty();
/*     */       }
/*     */     } else {
/* 379 */       return null;
/*     */     }
/*     */     
/* 382 */     return outStack;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean canInsertItemToInventory(IInventory inventory, ItemStack stack1, int par2, EnumFacing side)
/*     */   {
/* 390 */     return (stack1 != null) && (inventory.isItemValidForSlot(par2, stack1)) && ((!(inventory instanceof ISidedInventory)) || (((ISidedInventory)inventory).canInsertItem(par2, stack1, side)));
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean canExtractItemFromInventory(IInventory inventory, ItemStack stack1, int par2, EnumFacing side)
/*     */   {
/* 396 */     return (stack1 != null) && ((!(inventory instanceof ISidedInventory)) || (((ISidedInventory)inventory).canExtractItem(par2, stack1, side)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean compareMultipleItems(ItemStack c1, ItemStack[] c2)
/*     */   {
/* 404 */     if ((c1 == null) || (c1.stackSize <= 0)) return false;
/* 405 */     for (ItemStack is : c2) {
/* 406 */       if ((is != null) && (c1.isItemEqual(is)) && (ItemStack.areItemStackTagsEqual(c1, is))) return true;
/*     */     }
/* 408 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean areItemStacksEqualStrict(ItemStack stack0, ItemStack stack1) {
/* 412 */     return areItemStacksEqual(stack0, stack1, false, false, false, false);
/*     */   }
/*     */   
/*     */   public static ItemStack findFirstMatchFromFilter(ItemStack[] filters, boolean blacklist, IInventory inv, EnumFacing face, boolean ignoreDamage, boolean ignoreNBT, boolean useOre, boolean useMod)
/*     */   {
/* 417 */     return findFirstMatchFromFilter(filters, blacklist, inv, face, ignoreDamage, ignoreNBT, useOre, useMod, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public static ItemStack findFirstMatchFromFilter(ItemStack[] filters, boolean blacklist, IInventory inv, EnumFacing face, boolean ignoreDamage, boolean ignoreNBT, boolean useOre, boolean useMod, boolean leaveOne)
/*     */   {
/*     */     label178:
/* 424 */     for (int a = 0; a < inv.getSizeInventory(); a++) {
/* 425 */       ItemStack is = inv.getStackInSlot(a);
/* 426 */       if ((is != null) && (is.stackSize > 0))
/*     */       {
/* 428 */         if ((!leaveOne) || (inventoryContainsAmount(inv, is, face, ignoreDamage, ignoreNBT, useOre, useMod) >= 2))
/*     */         {
/* 430 */           boolean allow = false;
/* 431 */           boolean allEmpty = true;
/*     */           
/* 433 */           for (ItemStack fs : filters) {
/* 434 */             if (fs != null) {
/* 435 */               allEmpty = false;
/*     */               
/* 437 */               boolean r = areItemStacksEqual(fs.copy(), is.copy(), ignoreDamage, ignoreNBT, useOre, useMod);
/* 438 */               if (blacklist) {
/* 439 */                 if (r) break label178;
/* 440 */                 allow = true;
/*     */ 
/*     */ 
/*     */ 
/*     */               }
/* 445 */               else if (r) {
/* 446 */                 return is;
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 451 */           if ((blacklist) && ((allow) || (allEmpty))) return is;
/*     */         } }
/*     */     }
/* 454 */     return null;
/*     */   }
/*     */   
/*     */   public static ItemStack findFirstMatchFromFilter(ItemStack[] filters, boolean blacklist, ItemStack[] itemStacks, boolean value, boolean ignoreDamage, boolean ignoreNBT, boolean useOre)
/*     */   {
/* 459 */     return (ItemStack)findFirstMatchFromFilterTuple(filters, blacklist, itemStacks, value, ignoreDamage, ignoreNBT, useOre).getFirst();
/*     */   }
/*     */   
/*     */ 
/*     */   public static Tuple<ItemStack, ItemStack> findFirstMatchFromFilterTuple(ItemStack[] filters, boolean blacklist, ItemStack[] stacks, boolean ignoreDamage, boolean ignoreNBT, boolean useOre, boolean useMod)
/*     */   {
/*     */     label168:
/* 466 */     for (ItemStack is : stacks) {
/* 467 */       if ((is != null) && (is.stackSize > 0)) {
/* 468 */         boolean allow = false;
/* 469 */         boolean allEmpty = true;
/* 470 */         for (ItemStack fs : filters) {
/* 471 */           if (fs != null) {
/* 472 */             allEmpty = false;
/*     */             
/* 474 */             boolean r = areItemStacksEqual(fs.copy(), is.copy(), ignoreDamage, ignoreNBT, useOre, useMod);
/* 475 */             if (blacklist) {
/* 476 */               if (r) break label168; allow = true;
/*     */             }
/* 478 */             else if (r) {
/* 479 */               return new Tuple(is, fs);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 484 */         if ((blacklist) && ((allow) || (allEmpty))) {
/* 485 */           return new Tuple(is, null);
/*     */         }
/*     */       }
/*     */     }
/* 489 */     return new Tuple(null, null);
/*     */   }
/*     */   
/*     */   public static boolean areItemStacksEqual(ItemStack stack0, ItemStack stack1, boolean ignoreDamage, boolean ignoreNBT, boolean useOre, boolean useMod)
/*     */   {
/* 494 */     if ((stack0 == null) && (stack1 != null)) return false;
/* 495 */     if ((stack0 != null) && (stack1 == null)) return false;
/* 496 */     if ((stack0 == null) && (stack1 == null)) { return true;
/*     */     }
/* 498 */     if (useMod) {
/* 499 */       String m1 = "A";
/* 500 */       String m2 = "B";
/* 501 */       String a = GameRegistry.findUniqueIdentifierFor(stack0.getItem()).modId;
/* 502 */       if (a == null) a = GameRegistry.findUniqueIdentifierFor(Block.getBlockFromItem(stack0.getItem())).modId;
/* 503 */       if (a != null) m1 = a;
/* 504 */       String b = GameRegistry.findUniqueIdentifierFor(stack1.getItem()).modId;
/* 505 */       if (b == null) b = GameRegistry.findUniqueIdentifierFor(Block.getBlockFromItem(stack1.getItem())).modId;
/* 506 */       if (b != null) m2 = b;
/* 507 */       return m1.equals(m2);
/*     */     }
/*     */     
/* 510 */     if (useOre) {
/* 511 */       int[] od = OreDictionary.getOreIDs(stack0);
/* 512 */       for (int i : od) {
/* 513 */         if (thaumcraft.api.ThaumcraftApiHelper.containsMatch(false, new ItemStack[] { stack1 }, OreDictionary.getOres(OreDictionary.getOreName(i))))
/*     */         {
/* 515 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 519 */     boolean t1 = true;
/* 520 */     if (!ignoreNBT) {
/* 521 */       t1 = ItemStack.areItemStackTagsEqual(stack0, stack1);
/*     */     }
/* 523 */     if ((stack0.getItemDamage() == 32767) || (stack1.getItemDamage() == 32767))
/*     */     {
/* 525 */       ignoreDamage = true; }
/* 526 */     boolean t2 = (!ignoreDamage) && (stack0.getItemDamage() != stack1.getItemDamage());
/*     */     
/* 528 */     return t2 ? false : stack0.getItem() != stack1.getItem() ? false : t1;
/*     */   }
/*     */   
/*     */   public static boolean consumeInventoryItems(EntityPlayer player, ItemStack item, boolean nocheck, boolean ore)
/*     */   {
/* 533 */     if ((!nocheck) && (!isPlayerCarryingAmount(player, item, ore))) { return false;
/*     */     }
/* 535 */     int count = item.stackSize;
/* 536 */     for (int var2 = 0; var2 < player.inventory.mainInventory.length; var2++)
/*     */     {
/* 538 */       if ((player.inventory.mainInventory[var2] != null) && (player.inventory.mainInventory[var2].isItemEqual(item)) && (areItemStacksEqual(player.inventory.mainInventory[var2], item, false, false, ore, false)))
/*     */       {
/*     */ 
/*     */ 
/* 542 */         if (player.inventory.mainInventory[var2].stackSize > count) {
/* 543 */           player.inventory.mainInventory[var2].stackSize -= count;
/* 544 */           count = 0;
/*     */         } else {
/* 546 */           count -= player.inventory.mainInventory[var2].stackSize;
/* 547 */           player.inventory.mainInventory[var2] = null;
/*     */         }
/*     */         
/* 550 */         if (count <= 0) { return true;
/*     */         }
/*     */       }
/*     */     }
/* 554 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean consumeInventoryItems(EntityPlayer player, Item item, int md, int amt)
/*     */   {
/* 559 */     if (!isPlayerCarryingAmount(player, new ItemStack(item, amt, md), false)) { return false;
/*     */     }
/* 561 */     int count = amt;
/* 562 */     for (int var2 = 0; var2 < player.inventory.mainInventory.length; var2++)
/*     */     {
/* 564 */       if ((player.inventory.mainInventory[var2] != null) && (player.inventory.mainInventory[var2].getItem() == item) && (player.inventory.mainInventory[var2].getItemDamage() == md))
/*     */       {
/*     */ 
/*     */ 
/* 568 */         if (player.inventory.mainInventory[var2].stackSize > count) {
/* 569 */           player.inventory.mainInventory[var2].stackSize -= count;
/* 570 */           count = 0;
/*     */         } else {
/* 572 */           count -= player.inventory.mainInventory[var2].stackSize;
/* 573 */           player.inventory.mainInventory[var2] = null;
/*     */         }
/*     */         
/* 576 */         if (count <= 0) { return true;
/*     */         }
/*     */       }
/*     */     }
/* 580 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean consumeInventoryItem(EntityPlayer player, Item item, int md)
/*     */   {
/* 585 */     for (int var2 = 0; var2 < player.inventory.mainInventory.length; var2++)
/*     */     {
/* 587 */       if ((player.inventory.mainInventory[var2] != null) && (player.inventory.mainInventory[var2].getItem() == item) && (player.inventory.mainInventory[var2].getItemDamage() == md))
/*     */       {
/*     */ 
/*     */ 
/* 591 */         if (--player.inventory.mainInventory[var2].stackSize <= 0)
/*     */         {
/* 593 */           player.inventory.mainInventory[var2] = null;
/*     */         }
/* 595 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 599 */     return false;
/*     */   }
/*     */   
/*     */   public static void dropItems(World world, BlockPos pos) {
/* 603 */     TileEntity tileEntity = world.getTileEntity(pos);
/* 604 */     if (!(tileEntity instanceof IInventory)) {
/* 605 */       return;
/*     */     }
/*     */     
/* 608 */     IInventory inventory = (IInventory)tileEntity;
/*     */     
/* 610 */     for (int i = 0; i < inventory.getSizeInventory(); i++)
/*     */     {
/* 612 */       ItemStack item = inventory.getStackInSlot(i);
/*     */       
/* 614 */       if ((item != null) && (item.stackSize > 0)) {
/* 615 */         float rx = world.rand.nextFloat() * 0.8F + 0.1F;
/* 616 */         float ry = world.rand.nextFloat() * 0.8F + 0.1F;
/* 617 */         float rz = world.rand.nextFloat() * 0.8F + 0.1F;
/*     */         
/* 619 */         EntityItem entityItem = new EntityItem(world, pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz, item.copy());
/*     */         
/*     */ 
/*     */ 
/* 623 */         float factor = 0.05F;
/* 624 */         entityItem.motionX = (world.rand.nextGaussian() * factor);
/* 625 */         entityItem.motionY = (world.rand.nextGaussian() * factor + 0.20000000298023224D);
/* 626 */         entityItem.motionZ = (world.rand.nextGaussian() * factor);
/* 627 */         world.spawnEntityInWorld(entityItem);
/*     */         
/* 629 */         inventory.setInventorySlotContents(i, null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void dropHarvestsAtPos(World worldIn, BlockPos pos, List<ItemStack> list) {
/* 635 */     dropHarvestsAtPos(worldIn, pos, list, false, 0, null);
/*     */   }
/*     */   
/*     */   public static void dropHarvestsAtPos(World worldIn, BlockPos pos, List<ItemStack> list, boolean followItem, int color, Entity target)
/*     */   {
/* 640 */     for (ItemStack item : list)
/*     */     {
/* 642 */       if ((!worldIn.isRemote) && (worldIn.getGameRules().getBoolean("doTileDrops")) && (!worldIn.restoringBlockSnapshots))
/*     */       {
/*     */ 
/* 645 */         float f = 0.5F;
/* 646 */         double d0 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5D;
/* 647 */         double d1 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5D;
/* 648 */         double d2 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5D;
/* 649 */         EntityItem entityitem = null;
/* 650 */         if (followItem) {
/* 651 */           entityitem = new thaumcraft.common.entities.EntityFollowingItem(worldIn, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, item, target, color);
/*     */         } else {
/* 653 */           entityitem = new EntityItem(worldIn, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, item);
/*     */         }
/* 655 */         entityitem.setDefaultPickupDelay();
/* 656 */         worldIn.spawnEntityInWorld(entityitem);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void dropItemAtPos(World world, ItemStack item, BlockPos pos) {
/* 662 */     if ((!world.isRemote) && (item != null) && (item.stackSize > 0)) {
/* 663 */       EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, item.copy());
/*     */       
/*     */ 
/* 666 */       world.spawnEntityInWorld(entityItem);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void dropItemAtEntity(World world, ItemStack item, Entity entity) {
/* 671 */     if ((!world.isRemote) && (item != null) && (item.stackSize > 0)) {
/* 672 */       EntityItem entityItem = new EntityItem(world, entity.posX, entity.posY + entity.getEyeHeight() / 2.0F, entity.posZ, item.copy());
/*     */       
/*     */ 
/* 675 */       world.spawnEntityInWorld(entityItem);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void dropItemsAtEntity(World world, BlockPos pos, Entity entity) {
/* 680 */     TileEntity tileEntity = world.getTileEntity(pos);
/* 681 */     if ((!(tileEntity instanceof IInventory)) || (world.isRemote)) {
/* 682 */       return;
/*     */     }
/* 684 */     IInventory inventory = (IInventory)tileEntity;
/*     */     
/* 686 */     for (int i = 0; i < inventory.getSizeInventory(); i++)
/* 687 */       if ((!(tileEntity instanceof TileArcaneWorkbench)) || (i != 9)) {
/* 688 */         ItemStack item = inventory.getStackInSlot(i);
/*     */         
/* 690 */         if ((item != null) && (item.stackSize > 0))
/*     */         {
/* 692 */           EntityItem entityItem = new EntityItem(world, entity.posX, entity.posY + entity.getEyeHeight() / 2.0F, entity.posZ, item.copy());
/*     */           
/*     */ 
/*     */ 
/* 696 */           world.spawnEntityInWorld(entityItem);
/* 697 */           inventory.setInventorySlotContents(i, null);
/*     */         }
/*     */       }
/*     */   }
/*     */   
/*     */   public static boolean isPlayerCarryingAmount(EntityPlayer player, ItemStack stack, boolean ore) {
/* 703 */     if (stack == null) return false;
/* 704 */     int count = stack.stackSize;
/* 705 */     for (int var2 = 0; var2 < player.inventory.mainInventory.length; var2++)
/*     */     {
/* 707 */       if ((player.inventory.mainInventory[var2] != null) && (player.inventory.mainInventory[var2].isItemEqual(stack)) && (areItemStacksEqual(player.inventory.mainInventory[var2], stack, false, false, ore, false)))
/*     */       {
/*     */ 
/*     */ 
/* 711 */         count -= player.inventory.mainInventory[var2].stackSize;
/* 712 */         if (count <= 0) return true;
/*     */       }
/*     */     }
/* 715 */     return false;
/*     */   }
/*     */   
/*     */   public static int isPlayerCarrying(EntityPlayer player, ItemStack stack)
/*     */   {
/* 720 */     for (int var2 = 0; var2 < player.inventory.mainInventory.length; var2++)
/*     */     {
/* 722 */       if ((player.inventory.mainInventory[var2] != null) && (player.inventory.mainInventory[var2].isItemEqual(stack)))
/*     */       {
/*     */ 
/* 725 */         return var2;
/*     */       }
/*     */     }
/*     */     
/* 729 */     return -1;
/*     */   }
/*     */   
/*     */   public static ItemStack damageItem(int par1, ItemStack stack, World worldObj) {
/* 733 */     if (stack.isItemStackDamageable())
/*     */     {
/* 735 */       if (par1 > 0)
/*     */       {
/* 737 */         int var3 = net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
/* 738 */         int var4 = 0;
/*     */         
/* 740 */         for (int var5 = 0; (var3 > 0) && (var5 < par1); var5++)
/*     */         {
/* 742 */           if (net.minecraft.enchantment.EnchantmentDurability.negateDamage(stack, var3, worldObj.rand))
/*     */           {
/* 744 */             var4++;
/*     */           }
/*     */         }
/*     */         
/* 748 */         par1 -= var4;
/*     */         
/* 750 */         if (par1 <= 0)
/*     */         {
/* 752 */           return stack;
/*     */         }
/*     */       }
/*     */       
/* 756 */       stack.setItemDamage(stack.getItemDamage() + par1);
/*     */       
/* 758 */       if (stack.getItemDamage() > stack.getMaxDamage())
/*     */       {
/* 760 */         stack.stackSize -= 1;
/* 761 */         if (stack.stackSize < 0)
/*     */         {
/* 763 */           stack.stackSize = 0;
/*     */         }
/*     */         
/* 766 */         stack.setItemDamage(0);
/*     */       }
/*     */     }
/*     */     
/* 770 */     return stack;
/*     */   }
/*     */   
/*     */   public static void dropItemsWithChance(World world, int x, int y, int z, float chance, int fortune, ArrayList<ItemStack> items) {
/* 774 */     for (ItemStack item : items)
/*     */     {
/* 776 */       if ((world.rand.nextFloat() <= chance) && (item.stackSize > 0))
/*     */       {
/* 778 */         if ((!world.isRemote) && (world.getGameRules().getBoolean("doTileDrops")))
/*     */         {
/* 780 */           float var6 = 0.7F;
/* 781 */           double var7 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
/* 782 */           double var9 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
/* 783 */           double var11 = world.rand.nextFloat() * var6 + (1.0F - var6) * 0.5D;
/* 784 */           EntityItem var13 = new EntityItem(world, x + var7, y + var9, z + var11, item);
/* 785 */           var13.setPickupDelay(10);
/* 786 */           world.spawnEntityInWorld(var13);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static TileEntityChest getDoubleChest(TileEntity tile) {
/* 793 */     if ((tile != null) && ((tile instanceof TileEntityChest))) {
/* 794 */       if (((TileEntityChest)tile).adjacentChestXNeg != null) return ((TileEntityChest)tile).adjacentChestXNeg;
/* 795 */       if (((TileEntityChest)tile).adjacentChestXPos != null) return ((TileEntityChest)tile).adjacentChestXPos;
/* 796 */       if (((TileEntityChest)tile).adjacentChestZNeg != null) return ((TileEntityChest)tile).adjacentChestZNeg;
/* 797 */       if (((TileEntityChest)tile).adjacentChestZPos != null) return ((TileEntityChest)tile).adjacentChestZPos;
/*     */     }
/* 799 */     return null;
/*     */   }
/*     */   
/*     */   public static ItemStack cycleItemStack(Object input) {
/* 803 */     return cycleItemStack(input, 0);
/*     */   }
/*     */   
/*     */   public static ItemStack cycleItemStack(Object input, int counter) {
/* 807 */     ItemStack it = null;
/* 808 */     if ((input instanceof ItemStack[])) {
/* 809 */       ItemStack[] q = (ItemStack[])input;
/* 810 */       if ((q != null) && (q.length > 0)) {
/* 811 */         int idx = (int)((counter + System.currentTimeMillis() / 1000L) % q.length);
/* 812 */         it = cycleItemStack(q[idx], counter++);
/*     */       }
/*     */     }
/* 815 */     else if ((input instanceof ItemStack)) {
/* 816 */       it = (ItemStack)input;
/* 817 */       if ((it != null) && (it.getItem() != null) && (it.getItemDamage() == 32767) && (it.getItem().getHasSubtypes())) {
/* 818 */         List<ItemStack> q = new ArrayList();
/* 819 */         it.getItem().getSubItems(it.getItem(), it.getItem().getCreativeTab(), q);
/* 820 */         if ((q != null) && (q.size() > 0)) {
/* 821 */           int md = (int)((counter + System.currentTimeMillis() / 1000L) % q.size());
/* 822 */           ItemStack it2 = new ItemStack(it.getItem(), 1, md);
/* 823 */           it2.setTagCompound(it.getTagCompound());
/* 824 */           it = it2;
/*     */         }
/*     */       }
/* 827 */       else if ((it != null) && (it.getItem() != null) && (it.getItemDamage() == 32767) && (it.isItemStackDamageable())) {
/* 828 */         int md = (int)((counter + System.currentTimeMillis() / 10L) % it.getMaxDamage());
/* 829 */         ItemStack it2 = new ItemStack(it.getItem(), 1, md);
/* 830 */         it2.setTagCompound(it.getTagCompound());
/* 831 */         it = it2;
/*     */       }
/*     */       
/*     */     }
/* 835 */     else if ((input instanceof List)) {
/* 836 */       List<ItemStack> q = (List)input;
/* 837 */       if ((q != null) && (q.size() > 0)) {
/* 838 */         int idx = (int)((counter + System.currentTimeMillis() / 1000L) % q.size());
/* 839 */         it = cycleItemStack(q.get(idx), counter++);
/*     */       }
/*     */     }
/* 842 */     else if ((input instanceof String)) {
/* 843 */       List<ItemStack> q = OreDictionary.getOres((String)input);
/* 844 */       if ((q != null) && (q.size() > 0)) {
/* 845 */         int idx = (int)((counter + System.currentTimeMillis() / 1000L) % q.size());
/* 846 */         it = cycleItemStack(q.get(idx), counter++);
/*     */       }
/*     */     }
/*     */     
/* 850 */     return it;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean consumeAmount(IItemHandler inventory, ItemStack stack, int amount, boolean ignoreDamage, boolean ignoreNBT, boolean useOre, boolean useMod, boolean simulate)
/*     */   {
/* 859 */     for (int a = 0; a < inventory.getSlots(); a++) {
/* 860 */       if (areItemStacksEqual(stack, inventory.getStackInSlot(a), ignoreDamage, ignoreNBT, useOre, useMod)) {
/* 861 */         int s = Math.min(amount, inventory.getStackInSlot(a).stackSize);
/* 862 */         ItemStack es = inventory.extractItem(a, s, simulate);
/* 863 */         if (es != null) amount -= es.stackSize;
/*     */       }
/* 865 */       if (amount <= 0) return true;
/*     */     }
/* 867 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\utils\InventoryUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */