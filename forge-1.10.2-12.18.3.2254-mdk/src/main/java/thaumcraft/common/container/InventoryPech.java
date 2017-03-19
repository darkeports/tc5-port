/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import thaumcraft.common.entities.monster.EntityPech;
/*     */ 
/*     */ public class InventoryPech implements IInventory
/*     */ {
/*     */   private final EntityPech theMerchant;
/*  13 */   private ItemStack[] theInventory = new ItemStack[5];
/*     */   private final EntityPlayer thePlayer;
/*     */   private Container eventHandler;
/*     */   
/*     */   public InventoryPech(EntityPlayer par1EntityPlayer, EntityPech par2IMerchant, Container par1Container)
/*     */   {
/*  19 */     this.thePlayer = par1EntityPlayer;
/*  20 */     this.theMerchant = par2IMerchant;
/*  21 */     this.eventHandler = par1Container;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/*  30 */     return this.theInventory.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getStackInSlot(int par1)
/*     */   {
/*  39 */     return this.theInventory[par1];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack decrStackSize(int par1, int par2)
/*     */   {
/*  49 */     if (this.theInventory[par1] != null)
/*     */     {
/*     */ 
/*     */ 
/*  53 */       if (this.theInventory[par1].stackSize <= par2)
/*     */       {
/*  55 */         ItemStack var3 = this.theInventory[par1];
/*  56 */         this.theInventory[par1] = null;
/*  57 */         this.eventHandler.onCraftMatrixChanged(this);
/*  58 */         return var3;
/*     */       }
/*     */       
/*     */ 
/*  62 */       ItemStack var3 = this.theInventory[par1].splitStack(par2);
/*     */       
/*  64 */       if (this.theInventory[par1].stackSize == 0)
/*     */       {
/*  66 */         this.theInventory[par1] = null;
/*     */       }
/*     */       
/*  69 */       this.eventHandler.onCraftMatrixChanged(this);
/*  70 */       return var3;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  75 */     return null;
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
/*     */   public ItemStack removeStackFromSlot(int par1)
/*     */   {
/*  88 */     if (this.theInventory[par1] != null)
/*     */     {
/*  90 */       ItemStack itemstack = this.theInventory[par1];
/*  91 */       this.theInventory[par1] = null;
/*  92 */       return itemstack;
/*     */     }
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
/* 106 */     this.theInventory[par1] = par2ItemStack;
/*     */     
/* 108 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit()))
/*     */     {
/* 110 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*     */     }
/* 112 */     this.eventHandler.onCraftMatrixChanged(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getInventoryStackLimit()
/*     */   {
/* 122 */     return 64;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*     */   {
/* 131 */     return this.theMerchant.isTamed();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/*     */   {
/* 140 */     return par1 == 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void markDirty()
/*     */   {
/* 146 */     this.eventHandler.onCraftMatrixChanged(this);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/* 152 */     return "entity.Pech.name";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName()
/*     */   {
/* 157 */     return false;
/*     */   }
/*     */   
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 162 */     return null;
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
/* 173 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount()
/*     */   {
/* 181 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\InventoryPech.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */