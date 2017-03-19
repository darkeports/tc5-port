/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryFocusPouch
/*     */   implements IInventory
/*     */ {
/*     */   public ItemStack[] stackList;
/*     */   private Container eventHandler;
/*     */   
/*     */   public InventoryFocusPouch(Container par1Container)
/*     */   {
/*  22 */     this.stackList = new ItemStack[18];
/*  23 */     this.eventHandler = par1Container;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/*  32 */     return this.stackList.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getStackInSlot(int par1)
/*     */   {
/*  41 */     return par1 >= getSizeInventory() ? null : this.stackList[par1];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack removeStackFromSlot(int par1)
/*     */   {
/*  51 */     if (this.stackList[par1] != null)
/*     */     {
/*  53 */       ItemStack var2 = this.stackList[par1];
/*  54 */       this.stackList[par1] = null;
/*  55 */       return var2;
/*     */     }
/*     */     
/*     */ 
/*  59 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack decrStackSize(int par1, int par2)
/*     */   {
/*  70 */     if (this.stackList[par1] != null)
/*     */     {
/*     */ 
/*     */ 
/*  74 */       if (this.stackList[par1].stackSize <= par2)
/*     */       {
/*  76 */         ItemStack var3 = this.stackList[par1];
/*  77 */         this.stackList[par1] = null;
/*  78 */         this.eventHandler.onCraftMatrixChanged(this);
/*  79 */         return var3;
/*     */       }
/*     */       
/*     */ 
/*  83 */       ItemStack var3 = this.stackList[par1].splitStack(par2);
/*     */       
/*  85 */       if (this.stackList[par1].stackSize == 0)
/*     */       {
/*  87 */         this.stackList[par1] = null;
/*     */       }
/*     */       
/*  90 */       this.eventHandler.onCraftMatrixChanged(this);
/*  91 */       return var3;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  96 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*     */   {
/* 106 */     this.stackList[par1] = par2ItemStack;
/* 107 */     this.eventHandler.onCraftMatrixChanged(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getInventoryStackLimit()
/*     */   {
/* 117 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*     */   {
/* 126 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/*     */   {
/* 132 */     return (itemstack != null) && ((itemstack.getItem() instanceof ItemFocusBasic));
/*     */   }
/*     */   
/*     */ 
/*     */   public void markDirty() {}
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/* 141 */     return "container.focuspouch";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName()
/*     */   {
/* 146 */     return false;
/*     */   }
/*     */   
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 151 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void openInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */   
/*     */   public int getField(int id)
/*     */   {
/* 162 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount()
/*     */   {
/* 170 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\InventoryFocusPouch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */