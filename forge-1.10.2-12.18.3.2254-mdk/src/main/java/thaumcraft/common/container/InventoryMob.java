/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryMob
/*     */   implements IInventory
/*     */ {
/*     */   public ItemStack[] inventory;
/*     */   public Entity ent;
/*     */   public boolean inventoryChanged;
/*     */   public int slotCount;
/*  21 */   public int stacklimit = 64;
/*     */   
/*     */   public InventoryMob(Entity entity, int slots) {
/*  24 */     this.slotCount = slots;
/*  25 */     this.inventory = new ItemStack[this.slotCount];
/*  26 */     this.inventoryChanged = false;
/*  27 */     this.ent = entity;
/*     */   }
/*     */   
/*     */   public InventoryMob(Entity entity, int slots, int lim) {
/*  31 */     this.slotCount = slots;
/*  32 */     this.inventory = new ItemStack[this.slotCount];
/*  33 */     this.inventoryChanged = false;
/*  34 */     this.stacklimit = lim;
/*  35 */     this.ent = entity;
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
/*     */   public ItemStack decrStackSize(int i, int j)
/*     */   {
/* 125 */     ItemStack[] aitemstack = this.inventory;
/* 126 */     if (aitemstack[i] != null) {
/* 127 */       if (aitemstack[i].stackSize <= j) {
/* 128 */         ItemStack itemstack = aitemstack[i];
/* 129 */         aitemstack[i] = null;
/* 130 */         return itemstack;
/*     */       }
/*     */       
/* 133 */       ItemStack itemstack1 = aitemstack[i].splitStack(j);
/* 134 */       if (aitemstack[i].stackSize == 0) {
/* 135 */         aitemstack[i] = null;
/*     */       }
/* 137 */       return itemstack1;
/*     */     }
/*     */     
/* 140 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setInventorySlotContents(int i, ItemStack itemstack)
/*     */   {
/* 146 */     ItemStack[] aitemstack = this.inventory;
/* 147 */     aitemstack[i] = itemstack;
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
/*     */   public int getSizeInventory()
/*     */   {
/* 180 */     return this.inventory.length + 1;
/*     */   }
/*     */   
/*     */   public ItemStack getStackInSlot(int i)
/*     */   {
/* 185 */     ItemStack[] aitemstack = this.inventory;
/* 186 */     return aitemstack[i];
/*     */   }
/*     */   
/*     */   public int getInventoryStackLimit()
/*     */   {
/* 191 */     return this.stacklimit;
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
/*     */   public boolean isUseableByPlayer(EntityPlayer entityplayer)
/*     */   {
/* 222 */     return false;
/*     */   }
/*     */   
/*     */   public ItemStack removeStackFromSlot(int var1)
/*     */   {
/* 227 */     return null;
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
/*     */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/*     */   {
/* 302 */     return true;
/*     */   }
/*     */   
/*     */   public void markDirty()
/*     */   {
/* 307 */     this.inventoryChanged = true;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/* 313 */     return "Inventory";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName()
/*     */   {
/* 318 */     return false;
/*     */   }
/*     */   
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 323 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void openInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getField(int id)
/*     */   {
/* 342 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount()
/*     */   {
/* 350 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\InventoryMob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */