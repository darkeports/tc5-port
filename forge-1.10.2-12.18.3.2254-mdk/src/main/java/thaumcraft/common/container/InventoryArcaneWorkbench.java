/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import thaumcraft.api.crafting.IArcaneWorkbench;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ 
/*     */ public class InventoryArcaneWorkbench extends InventoryCrafting implements ISidedInventory, IArcaneWorkbench
/*     */ {
/*     */   public ItemStack[] stackList;
/*     */   public Container eventHandler;
/*     */   private final int inventoryWidth;
/*     */   private final int inventoryHeight;
/*     */   
/*     */   public InventoryArcaneWorkbench(Container p_i1807_1_, int width, int height)
/*     */   {
/*  20 */     super(p_i1807_1_, width, height);
/*  21 */     int k = width * height;
/*  22 */     this.stackList = new ItemStack[k + 2];
/*  23 */     this.eventHandler = p_i1807_1_;
/*  24 */     this.inventoryWidth = width;
/*  25 */     this.inventoryHeight = height;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/*  31 */     return 9;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack getStackInSlot(int index)
/*     */   {
/*  37 */     return index >= 11 ? null : this.stackList[index];
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack getStackInRowAndColumn(int row, int column)
/*     */   {
/*  43 */     return (row >= 0) && (row < this.inventoryWidth) && (column >= 0) && (column <= this.inventoryHeight) ? getStackInSlot(row + column * this.inventoryWidth) : null;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/*  49 */     return "container.arcaneworkbench";
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack removeStackFromSlot(int index)
/*     */   {
/*  55 */     if (this.stackList[index] != null)
/*     */     {
/*  57 */       ItemStack itemstack = this.stackList[index];
/*  58 */       this.stackList[index] = null;
/*  59 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/*  63 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ItemStack decrStackSize(int index, int count)
/*     */   {
/*  70 */     if (this.stackList[index] != null)
/*     */     {
/*     */ 
/*     */ 
/*  74 */       if (this.stackList[index].stackSize <= count)
/*     */       {
/*  76 */         ItemStack itemstack = this.stackList[index];
/*  77 */         this.stackList[index] = null;
/*  78 */         try { this.eventHandler.onCraftMatrixChanged(this); } catch (Exception e) {}
/*  79 */         return itemstack;
/*     */       }
/*     */       
/*     */ 
/*  83 */       ItemStack itemstack = this.stackList[index].splitStack(count);
/*     */       
/*  85 */       if (this.stackList[index].stackSize == 0)
/*     */       {
/*  87 */         this.stackList[index] = null;
/*     */       }
/*     */       try {
/*  90 */         this.eventHandler.onCraftMatrixChanged(this); } catch (Exception e) {}
/*  91 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  96 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setInventorySlotContents(int index, ItemStack stack)
/*     */   {
/* 103 */     this.stackList[index] = stack;
/* 104 */     try { this.eventHandler.onCraftMatrixChanged(this);
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */   public void clear() {
/* 110 */     for (int i = 0; i < this.stackList.length; i++)
/*     */     {
/* 112 */       this.stackList[i] = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 119 */     return this.inventoryHeight;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 125 */     return this.inventoryWidth;
/*     */   }
/*     */   
/*     */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/*     */   {
/* 130 */     if ((i == 10) && (itemstack != null)) {
/* 131 */       if (!(itemstack.getItem() instanceof IWand)) return false;
/* 132 */       IWand wand = (IWand)itemstack.getItem();
/* 133 */       return !wand.isStaff(itemstack);
/*     */     }
/* 135 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canInsertItem(int i, ItemStack itemstack, EnumFacing direction)
/*     */   {
/* 140 */     if ((i != 10) || (itemstack == null) || (!(itemstack.getItem() instanceof IWand))) return false;
/* 141 */     IWand wand = (IWand)itemstack.getItem();
/* 142 */     return !wand.isStaff(itemstack);
/*     */   }
/*     */   
/*     */   public boolean canExtractItem(int i, ItemStack itemstack, EnumFacing direction)
/*     */   {
/* 147 */     return i == 10;
/*     */   }
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side)
/*     */   {
/* 152 */     return new int[] { 10 };
/*     */   }
/*     */   
/*     */   public void setInventorySlotContentsSoftly(int index, ItemStack stack)
/*     */   {
/* 157 */     this.stackList[index] = stack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\InventoryArcaneWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */