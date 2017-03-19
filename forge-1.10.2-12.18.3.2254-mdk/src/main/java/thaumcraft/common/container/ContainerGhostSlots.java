/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerGhostSlots extends Container
/*     */ {
/*     */   public boolean canInteractWith(EntityPlayer entityplayer)
/*     */   {
/*  14 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack slotClick(int slotClicked, int button, int mod, EntityPlayer player)
/*     */   {
/*  20 */     ItemStack itemstack = null;
/*  21 */     InventoryPlayer inventoryplayer = player.inventory;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  29 */     if (((mod == 0) || (mod == 1)) && ((button == 0) || (button == 1)))
/*     */     {
/*  31 */       if (slotClicked == 64537)
/*     */       {
/*  33 */         if ((inventoryplayer.getItemStack() != null) && (slotClicked == 64537))
/*     */         {
/*  35 */           if (button == 0)
/*     */           {
/*  37 */             player.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), false);
/*  38 */             inventoryplayer.setItemStack((ItemStack)null);
/*     */           }
/*     */           
/*  41 */           if (button == 1)
/*     */           {
/*  43 */             player.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack().splitStack(1), false);
/*     */             
/*  45 */             if (inventoryplayer.getItemStack().stackSize == 0)
/*     */             {
/*  47 */               inventoryplayer.setItemStack((ItemStack)null);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*  52 */       else if (mod == 1)
/*     */       {
/*  54 */         if (slotClicked < 0)
/*     */         {
/*  56 */           return null;
/*     */         }
/*  58 */         Slot slot2 = (Slot)this.inventorySlots.get(slotClicked);
/*  59 */         if ((slot2 != null) && (slot2.getStack() != null) && ((slot2 instanceof SlotGhost))) {
/*  60 */           if (button == 0) {
/*  61 */             slot2.putStack((ItemStack)null);
/*     */           }
/*  63 */           else if (button == 1) {
/*  64 */             ItemStack slotStack = slot2.getStack();
/*  65 */             slotStack.stackSize += 16;
/*  66 */             if (slotStack.stackSize > slot2.getSlotStackLimit()) {
/*  67 */               slotStack.stackSize = slot2.getSlotStackLimit();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/*  73 */         if (slotClicked < 0)
/*     */         {
/*  75 */           return null;
/*     */         }
/*     */         
/*  78 */         Slot slot2 = (Slot)this.inventorySlots.get(slotClicked);
/*     */         
/*  80 */         if (slot2 != null)
/*     */         {
/*  82 */           ItemStack slotStack = slot2.getStack();
/*  83 */           ItemStack playerStack = inventoryplayer.getItemStack();
/*     */           
/*  85 */           if (slotStack != null)
/*     */           {
/*  87 */             itemstack = slotStack.copy();
/*     */           }
/*     */           
/*  90 */           if (slotStack == null)
/*     */           {
/*  92 */             if ((playerStack != null) && (slot2.isItemValid(playerStack)))
/*     */             {
/*  94 */               int k1 = button == 0 ? playerStack.stackSize : 1;
/*     */               
/*  96 */               if (k1 > slot2.getSlotStackLimit())
/*     */               {
/*  98 */                 k1 = slot2.getSlotStackLimit();
/*     */               }
/*     */               
/* 101 */               if (playerStack.stackSize >= k1)
/*     */               {
/* 103 */                 if ((slot2 instanceof SlotGhost)) {
/* 104 */                   ItemStack ic = playerStack.copy();
/* 105 */                   ic.stackSize = k1;
/* 106 */                   slot2.putStack(ic);
/*     */                 } else {
/* 108 */                   slot2.putStack(playerStack.splitStack(k1));
/*     */                 }
/*     */               }
/*     */               
/* 112 */               if ((!(slot2 instanceof SlotGhost)) && (playerStack.stackSize == 0))
/*     */               {
/* 114 */                 inventoryplayer.setItemStack((ItemStack)null);
/*     */               }
/*     */             }
/*     */           }
/* 118 */           else if ((slot2.canTakeStack(player)) || ((slot2 instanceof SlotGhost)))
/*     */           {
/* 120 */             if (playerStack == null)
/*     */             {
/* 122 */               if ((slot2 instanceof SlotGhost)) {
/* 123 */                 int k1 = button == 0 ? 1 : -1;
/* 124 */                 if (slotStack.stackSize - k1 <= slot2.getSlotStackLimit())
/* 125 */                   slot2.decrStackSize(k1);
/* 126 */                 if (slotStack.stackSize == 0)
/*     */                 {
/* 128 */                   slot2.putStack((ItemStack)null);
/*     */                 }
/*     */               } else {
/* 131 */                 int k1 = button == 0 ? slotStack.stackSize : (slotStack.stackSize + 1) / 2;
/* 132 */                 ItemStack itemstack3 = slot2.decrStackSize(k1);
/* 133 */                 inventoryplayer.setItemStack(itemstack3);
/*     */                 
/* 135 */                 if (slotStack.stackSize == 0)
/*     */                 {
/* 137 */                   slot2.putStack((ItemStack)null);
/*     */                 }
/*     */                 
/* 140 */                 slot2.onPickupFromSlot(player, inventoryplayer.getItemStack());
/*     */               }
/*     */             }
/* 143 */             else if (slot2.isItemValid(playerStack))
/*     */             {
/* 145 */               if ((slotStack.getItem() == playerStack.getItem()) && (slotStack.getItemDamage() == playerStack.getItemDamage()) && (ItemStack.areItemStackTagsEqual(slotStack, playerStack)))
/*     */               {
/* 147 */                 int k1 = button == 0 ? playerStack.stackSize : 1;
/*     */                 
/* 149 */                 if (k1 > slot2.getSlotStackLimit() - slotStack.stackSize)
/*     */                 {
/* 151 */                   k1 = slot2.getSlotStackLimit() - slotStack.stackSize;
/*     */                 }
/*     */                 
/* 154 */                 if (k1 > playerStack.getMaxStackSize() - slotStack.stackSize)
/*     */                 {
/* 156 */                   k1 = playerStack.getMaxStackSize() - slotStack.stackSize;
/*     */                 }
/* 158 */                 if (!(slot2 instanceof SlotGhost)) {
/* 159 */                   playerStack.splitStack(k1);
/*     */                   
/* 161 */                   if (playerStack.stackSize == 0)
/*     */                   {
/* 163 */                     inventoryplayer.setItemStack((ItemStack)null);
/*     */                   }
/*     */                 }
/* 166 */                 slotStack.stackSize += k1;
/*     */               }
/* 168 */               else if (playerStack.stackSize <= slot2.getSlotStackLimit())
/*     */               {
/* 170 */                 slot2.putStack(playerStack.copy());
/* 171 */                 if (!(slot2 instanceof SlotGhost)) inventoryplayer.setItemStack(slotStack);
/*     */               }
/*     */             }
/* 174 */             else if ((slotStack.getItem() == playerStack.getItem()) && (playerStack.getMaxStackSize() > 1) && ((!slotStack.getHasSubtypes()) || (slotStack.getItemDamage() == playerStack.getItemDamage())) && (ItemStack.areItemStackTagsEqual(slotStack, playerStack)))
/*     */             {
/* 176 */               int k1 = slotStack.stackSize;
/*     */               
/* 178 */               if ((k1 > 0) && (k1 + playerStack.stackSize <= playerStack.getMaxStackSize()))
/*     */               {
/* 180 */                 if (!(slot2 instanceof SlotGhost)) playerStack.stackSize += k1;
/* 181 */                 slotStack = slot2.decrStackSize(k1);
/*     */                 
/* 183 */                 if (slotStack.stackSize == 0)
/*     */                 {
/* 185 */                   slot2.putStack((ItemStack)null);
/*     */                 }
/*     */                 
/* 188 */                 if (!(slot2 instanceof SlotGhost)) { slot2.onPickupFromSlot(player, inventoryplayer.getItemStack());
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 193 */           slot2.onSlotChanged();
/*     */         }
/*     */       }
/*     */     }
/* 197 */     else if ((mod == 2) && (button >= 0) && (button < 9))
/*     */     {
/* 199 */       Slot slot2 = (Slot)this.inventorySlots.get(slotClicked);
/*     */       
/* 201 */       if (slot2.canTakeStack(player))
/*     */       {
/* 203 */         ItemStack slotStack = inventoryplayer.getStackInSlot(button);
/* 204 */         boolean flag = (slotStack == null) || ((slot2.inventory == inventoryplayer) && (slot2.isItemValid(slotStack)));
/* 205 */         int k1 = -1;
/*     */         
/* 207 */         if (!flag)
/*     */         {
/* 209 */           k1 = inventoryplayer.getFirstEmptyStack();
/* 210 */           flag |= k1 > -1;
/*     */         }
/*     */         
/* 213 */         if ((slot2.getHasStack()) && (flag))
/*     */         {
/* 215 */           ItemStack itemstack3 = slot2.getStack();
/* 216 */           if (!(slot2 instanceof SlotGhost)) { inventoryplayer.setInventorySlotContents(button, itemstack3.copy());
/*     */           }
/* 218 */           if (((slot2.inventory != inventoryplayer) || (!slot2.isItemValid(slotStack))) && (slotStack != null))
/*     */           {
/* 220 */             if (k1 > -1)
/*     */             {
/* 222 */               if (!(slot2 instanceof SlotGhost)) inventoryplayer.addItemStackToInventory(slotStack);
/* 223 */               slot2.decrStackSize(itemstack3.stackSize);
/* 224 */               slot2.putStack((ItemStack)null);
/* 225 */               if (!(slot2 instanceof SlotGhost)) slot2.onPickupFromSlot(player, itemstack3);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 230 */             slot2.decrStackSize(itemstack3.stackSize);
/* 231 */             slot2.putStack(slotStack);
/* 232 */             if (!(slot2 instanceof SlotGhost)) slot2.onPickupFromSlot(player, itemstack3);
/*     */           }
/*     */         }
/* 235 */         else if ((!slot2.getHasStack()) && (slotStack != null) && (slot2.isItemValid(slotStack)))
/*     */         {
/* 237 */           if (!(slot2 instanceof SlotGhost)) inventoryplayer.setInventorySlotContents(button, (ItemStack)null);
/* 238 */           slot2.putStack(slotStack);
/*     */         }
/*     */       }
/*     */     }
/* 242 */     else if ((mod == 3) && (player.capabilities.isCreativeMode) && (inventoryplayer.getItemStack() == null) && (slotClicked >= 0))
/*     */     {
/* 244 */       Slot slot2 = (Slot)this.inventorySlots.get(slotClicked);
/*     */       
/* 246 */       if ((slot2 != null) && (slot2.getHasStack()))
/*     */       {
/* 248 */         ItemStack slotStack = slot2.getStack().copy();
/* 249 */         slotStack.stackSize = slotStack.getMaxStackSize();
/* 250 */         if (!(slot2 instanceof SlotGhost)) inventoryplayer.setItemStack(slotStack);
/*     */       }
/*     */     }
/* 253 */     else if (mod == 3) {
/* 254 */       Slot slot2 = (Slot)this.inventorySlots.get(slotClicked);
/* 255 */       if ((slot2 != null) && ((slot2 instanceof SlotGhost)) && (button == 3)) {
/* 256 */         slot2.putStack((ItemStack)null);
/*     */       }
/*     */     }
/* 259 */     else if ((mod == 4) && (inventoryplayer.getItemStack() == null) && (slotClicked >= 0))
/*     */     {
/* 261 */       Slot slot2 = (Slot)this.inventorySlots.get(slotClicked);
/*     */       
/* 263 */       if ((slot2 != null) && (slot2.getHasStack()) && ((slot2.canTakeStack(player)) || ((slot2 instanceof SlotGhost))))
/*     */       {
/*     */ 
/* 266 */         ItemStack slotStack = slot2.decrStackSize(button == 0 ? 1 : slot2.getStack().stackSize);
/* 267 */         if (!(slot2 instanceof SlotGhost)) {
/* 268 */           slot2.onPickupFromSlot(player, slotStack);
/* 269 */           player.dropPlayerItemWithRandomChoice(slotStack, false);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/* 274 */     else if ((mod == 6) && (slotClicked >= 0))
/*     */     {
/* 276 */       Slot slot2 = (Slot)this.inventorySlots.get(slotClicked);
/* 277 */       ItemStack slotStack = inventoryplayer.getItemStack();
/*     */       
/* 279 */       if ((slotStack != null) && ((slot2 == null) || (!slot2.getHasStack()) || (!slot2.canTakeStack(player))))
/*     */       {
/* 281 */         int l = button == 0 ? 0 : this.inventorySlots.size() - 1;
/* 282 */         int k1 = button == 0 ? 1 : -1;
/*     */         
/* 284 */         for (int l1 = 0; l1 < 2; l1++)
/*     */         {
/* 286 */           for (int i2 = l; (i2 >= 0) && (i2 < this.inventorySlots.size()) && (slotStack.stackSize < slotStack.getMaxStackSize()); i2 += k1)
/*     */           {
/* 288 */             Slot slot3 = (Slot)this.inventorySlots.get(i2);
/*     */             
/* 290 */             if ((!(slot3 instanceof SlotGhost)) && (!(slot3 instanceof SlotGhostFluid)) && (slot3.getHasStack()) && (canAddItemToSlot(slot3, slotStack, true)) && (slot3.canTakeStack(player)) && (canMergeSlot(slotStack, slot3)) && ((l1 != 0) || (slot3.getStack().stackSize != slot3.getStack().getMaxStackSize())))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 296 */               int j2 = Math.min(slotStack.getMaxStackSize() - slotStack.stackSize, slot3.getStack().stackSize);
/* 297 */               ItemStack itemstack5 = slot3.decrStackSize(j2);
/* 298 */               if (!(slot2 instanceof SlotGhost)) { slotStack.stackSize += j2;
/*     */               }
/* 300 */               if (itemstack5.stackSize <= 0)
/*     */               {
/* 302 */                 slot3.putStack((ItemStack)null);
/*     */               }
/*     */               
/* 305 */               if (!(slot2 instanceof SlotGhost)) { slot3.onPickupFromSlot(player, itemstack5);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 311 */       detectAndSendChanges();
/*     */     }
/*     */     
/*     */ 
/* 315 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerGhostSlots.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */