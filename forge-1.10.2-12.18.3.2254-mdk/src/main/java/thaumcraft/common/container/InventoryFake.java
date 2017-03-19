/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public class InventoryFake implements IInventory
/*     */ {
/*     */   private ItemStack[] stackList;
/*     */   
/*     */   public InventoryFake(ItemStack[] stackList)
/*     */   {
/*  15 */     this.stackList = stackList;
/*     */   }
/*     */   
/*     */   public InventoryFake(ArrayList<ItemStack> stackList) {
/*  19 */     this.stackList = ((ItemStack[])stackList.toArray(new ItemStack[0]));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/*  27 */     return this.stackList.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getStackInSlot(int par1)
/*     */   {
/*  35 */     return par1 >= getSizeInventory() ? null : this.stackList[par1];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack removeStackFromSlot(int par1)
/*     */   {
/*  45 */     if (this.stackList[par1] != null) {
/*  46 */       ItemStack var2 = this.stackList[par1];
/*  47 */       this.stackList[par1] = null;
/*  48 */       return var2;
/*     */     }
/*  50 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack decrStackSize(int par1, int par2)
/*     */   {
/*  60 */     if (this.stackList[par1] != null)
/*     */     {
/*     */ 
/*  63 */       if (this.stackList[par1].stackSize <= par2) {
/*  64 */         ItemStack var3 = this.stackList[par1];
/*  65 */         this.stackList[par1] = null;
/*  66 */         return var3;
/*     */       }
/*  68 */       ItemStack var3 = this.stackList[par1].splitStack(par2);
/*     */       
/*  70 */       if (this.stackList[par1].stackSize == 0) {
/*  71 */         this.stackList[par1] = null;
/*     */       }
/*     */       
/*  74 */       return var3;
/*     */     }
/*     */     
/*  77 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*     */   {
/*  87 */     this.stackList[par1] = par2ItemStack;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getInventoryStackLimit()
/*     */   {
/*  96 */     return 64;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*     */   {
/* 105 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/*     */   {
/* 110 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void markDirty() {}
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/* 119 */     return "container.fake";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName()
/*     */   {
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 129 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void openInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */   public int getField(int id)
/*     */   {
/* 141 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFieldCount()
/*     */   {
/* 153 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\InventoryFake.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */