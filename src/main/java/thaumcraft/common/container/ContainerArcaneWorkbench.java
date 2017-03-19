/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
/*     */ import thaumcraft.common.tiles.crafting.TileArcaneWorkbench;
/*     */ 
/*     */ public class ContainerArcaneWorkbench extends Container
/*     */ {
/*     */   private TileArcaneWorkbench tileEntity;
/*     */   private InventoryPlayer ip;
/*     */   
/*     */   public ContainerArcaneWorkbench(InventoryPlayer par1InventoryPlayer, TileArcaneWorkbench e)
/*     */   {
/*  23 */     this.tileEntity = e;
/*  24 */     this.tileEntity.inventory.eventHandler = this;
/*  25 */     this.ip = par1InventoryPlayer;
/*  26 */     addSlotToContainer(new SlotCraftingArcaneWorkbench(par1InventoryPlayer.player, this.tileEntity.inventory, this.tileEntity.inventory, 9, 160, 64));
/*     */     
/*     */ 
/*  29 */     addSlotToContainer(new SlotLimitedByWand(this.tileEntity.inventory, 10, 160, 24));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  34 */     for (int var6 = 0; var6 < 3; var6++)
/*     */     {
/*  36 */       for (int var7 = 0; var7 < 3; var7++)
/*     */       {
/*  38 */         addSlotToContainer(new Slot(this.tileEntity.inventory, var7 + var6 * 3, 40 + var7 * 24, 40 + var6 * 24));
/*     */       }
/*     */     }
/*     */     
/*  42 */     for (var6 = 0; var6 < 3; var6++)
/*     */     {
/*  44 */       for (int var7 = 0; var7 < 9; var7++)
/*     */       {
/*  46 */         addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 16 + var7 * 18, 151 + var6 * 18));
/*     */       }
/*     */     }
/*     */     
/*  50 */     for (var6 = 0; var6 < 9; var6++)
/*     */     {
/*  52 */       addSlotToContainer(new Slot(par1InventoryPlayer, var6, 16 + var6 * 18, 209));
/*     */     }
/*     */     
/*  55 */     onCraftMatrixChanged(this.tileEntity.inventory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCraftMatrixChanged(IInventory par1IInventory)
/*     */   {
/*  64 */     InventoryCrafting ic = new InventoryCrafting(new ContainerDummy(), 3, 3);
/*  65 */     for (int a = 0; a < 9; a++) {
/*  66 */       ic.setInventorySlotContents(a, this.tileEntity.inventory.getStackInSlot(a));
/*     */     }
/*  68 */     this.tileEntity.inventory.setInventorySlotContentsSoftly(9, CraftingManager.getInstance().findMatchingRecipe(ic, this.tileEntity.getWorld()));
/*     */     
/*     */ 
/*  71 */     if ((this.tileEntity.inventory.getStackInSlot(9) == null) && (this.tileEntity.inventory.getStackInSlot(10) != null) && ((this.tileEntity.inventory.getStackInSlot(10).getItem() instanceof IWand)))
/*     */     {
/*     */ 
/*  74 */       IWand wand = (IWand)this.tileEntity.inventory.getStackInSlot(10).getItem();
/*     */       
/*  76 */       if (wand.consumeAllVis(this.tileEntity.inventory.getStackInSlot(10), this.ip.player, ThaumcraftCraftingManager.findMatchingArcaneRecipeAspects(this.tileEntity.inventory, this.ip.player), false, true))
/*     */       {
/*     */ 
/*     */ 
/*  80 */         this.tileEntity.inventory.setInventorySlotContentsSoftly(9, ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.tileEntity.inventory, this.ip.player));
/*     */       }
/*     */     }
/*     */     
/*  84 */     this.tileEntity.markDirty();
/*  85 */     this.tileEntity.getWorld().markBlockForUpdate(this.tileEntity.getPos());
/*  86 */     detectAndSendChanges();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/*     */   {
/*  94 */     super.onContainerClosed(par1EntityPlayer);
/*     */     
/*  96 */     if (!this.tileEntity.getWorld().isRemote)
/*     */     {
/*  98 */       this.tileEntity.inventory.eventHandler = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*     */   {
/* 105 */     return this.tileEntity.getWorld().getTileEntity(this.tileEntity.getPos()) == this.tileEntity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1)
/*     */   {
/* 115 */     ItemStack var2 = null;
/* 116 */     Slot var3 = (Slot)this.inventorySlots.get(par1);
/*     */     
/* 118 */     if ((var3 != null) && (var3.getHasStack()))
/*     */     {
/* 120 */       ItemStack var4 = var3.getStack();
/* 121 */       var2 = var4.copy();
/*     */       
/* 123 */       if (par1 == 0)
/*     */       {
/* 125 */         if (!mergeItemStack(var4, 11, 47, true))
/*     */         {
/* 127 */           return null;
/*     */         }
/*     */         
/* 130 */         var3.onSlotChange(var4, var2);
/*     */       }
/* 132 */       else if ((par1 >= 11) && (par1 < 38))
/*     */       {
/* 134 */         if (((var4.getItem() instanceof IWand)) && (!((IWand)var4.getItem()).isStaff(var4)))
/*     */         {
/* 136 */           if (!mergeItemStack(var4, 1, 2, false)) {
/* 137 */             return null;
/*     */           }
/* 139 */           var3.onSlotChange(var4, var2);
/*     */         }
/* 141 */         else if (!mergeItemStack(var4, 38, 47, false))
/*     */         {
/* 143 */           return null;
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 148 */       else if ((par1 >= 38) && (par1 < 47))
/*     */       {
/* 150 */         if (((var4.getItem() instanceof IWand)) && (!((IWand)var4.getItem()).isStaff(var4)))
/*     */         {
/* 152 */           if (!mergeItemStack(var4, 1, 2, false)) {
/* 153 */             return null;
/*     */           }
/* 155 */           var3.onSlotChange(var4, var2);
/*     */         }
/* 157 */         else if (!mergeItemStack(var4, 11, 38, false))
/*     */         {
/* 159 */           return null;
/*     */         }
/*     */         
/*     */       }
/* 163 */       else if (!mergeItemStack(var4, 11, 47, false))
/*     */       {
/* 165 */         return null;
/*     */       }
/*     */       
/* 168 */       if (var4.stackSize == 0)
/*     */       {
/* 170 */         var3.putStack((ItemStack)null);
/*     */       }
/*     */       else
/*     */       {
/* 174 */         var3.onSlotChanged();
/*     */       }
/*     */       
/* 177 */       if (var4.stackSize == var2.stackSize)
/*     */       {
/* 179 */         return null;
/*     */       }
/*     */       
/* 182 */       var3.onPickupFromSlot(this.ip.player, var4);
/*     */     }
/*     */     
/* 185 */     return var2;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
/*     */   {
/* 191 */     if (par3 == 4) {
/* 192 */       par2 = 1;
/* 193 */       return super.slotClick(par1, par2, par3, par4EntityPlayer);
/*     */     }
/* 195 */     if (((par1 == 0) || (par1 == 1)) && (par2 > 0)) par2 = 0;
/* 196 */     return super.slotClick(par1, par2, par3, par4EntityPlayer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canMergeSlot(ItemStack stack, Slot slot)
/*     */   {
/* 203 */     return (slot.inventory != this.tileEntity) && (super.canMergeSlot(stack, slot));
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerArcaneWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */