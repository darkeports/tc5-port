/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.crafting.CrucibleRecipe;
/*     */ import thaumcraft.common.tiles.devices.TileThaumatorium;
/*     */ 
/*     */ public class ContainerThaumatorium extends Container
/*     */ {
/*     */   private TileThaumatorium thaumatorium;
/*  19 */   private EntityPlayer player = null;
/*     */   
/*     */   public ContainerThaumatorium(InventoryPlayer par1InventoryPlayer, TileThaumatorium tileEntity)
/*     */   {
/*  23 */     this.player = par1InventoryPlayer.player;
/*  24 */     this.thaumatorium = tileEntity;
/*  25 */     this.thaumatorium.eventHandler = this;
/*  26 */     addSlotToContainer(new Slot(tileEntity, 0, 48, 16));
/*     */     
/*     */ 
/*  29 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  31 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  33 */         addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  37 */     for (i = 0; i < 9; i++)
/*     */     {
/*  39 */       addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
/*     */     }
/*     */     
/*  42 */     onCraftMatrixChanged(this.thaumatorium);
/*     */   }
/*     */   
/*  45 */   public ArrayList<CrucibleRecipe> recipes = new ArrayList();
/*     */   
/*     */ 
/*     */   public void onCraftMatrixChanged(IInventory par1iInventory)
/*     */   {
/*  50 */     super.onCraftMatrixChanged(par1iInventory);
/*  51 */     updateRecipes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/*     */   {
/*  58 */     super.onContainerClosed(par1EntityPlayer);
/*     */     
/*  60 */     if (!this.thaumatorium.getWorld().isRemote)
/*     */     {
/*  62 */       this.thaumatorium.eventHandler = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateRecipes()
/*     */   {
/*  68 */     this.recipes.clear();
/*  69 */     Iterator i$; if ((this.thaumatorium.inputStack != null) || (this.thaumatorium.recipeHash != null)) {
/*  70 */       for (i$ = thaumcraft.api.ThaumcraftApi.getCraftingRecipes().iterator(); i$.hasNext();) { r = i$.next();
/*  71 */         if ((r instanceof CrucibleRecipe)) {
/*  72 */           if ((thaumcraft.api.research.ResearchHelper.isResearchComplete(this.player.getName(), ((CrucibleRecipe)r).research)) && (((CrucibleRecipe)r).catalystMatches(this.thaumatorium.inputStack)))
/*     */           {
/*  74 */             this.recipes.add((CrucibleRecipe)r);
/*     */ 
/*     */           }
/*  77 */           else if ((this.thaumatorium.recipeHash != null) && (this.thaumatorium.recipeHash.size() > 0)) {
/*  78 */             for (Integer hash : this.thaumatorium.recipeHash) {
/*  79 */               if (((CrucibleRecipe)r).hash == hash.intValue()) {
/*  80 */                 this.recipes.add((CrucibleRecipe)r);
/*  81 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     Object r;
/*     */   }
/*     */   
/*     */   public boolean enchantItem(EntityPlayer par1EntityPlayer, int button)
/*     */   {
/*  94 */     if ((this.recipes.size() > 0) && 
/*  95 */       (button >= 0) && (button < this.recipes.size())) {
/*  96 */       boolean found = false;
/*  97 */       for (int a = 0; a < this.thaumatorium.recipeHash.size(); a++) {
/*  98 */         if (((CrucibleRecipe)this.recipes.get(button)).hash == ((Integer)this.thaumatorium.recipeHash.get(a)).intValue()) {
/*  99 */           found = true;
/* 100 */           this.thaumatorium.recipeEssentia.remove(a);
/* 101 */           this.thaumatorium.recipePlayer.remove(a);
/* 102 */           this.thaumatorium.recipeHash.remove(a);
/* 103 */           this.thaumatorium.currentCraft = -1;
/* 104 */           break;
/*     */         }
/*     */       }
/* 107 */       if (!found) {
/* 108 */         this.thaumatorium.recipeEssentia.add(((CrucibleRecipe)this.recipes.get(button)).aspects.copy());
/* 109 */         this.thaumatorium.recipePlayer.add(par1EntityPlayer.getName());
/* 110 */         this.thaumatorium.recipeHash.add(Integer.valueOf(((CrucibleRecipe)this.recipes.get(button)).hash));
/*     */       }
/* 112 */       this.thaumatorium.markDirty();
/* 113 */       this.thaumatorium.getWorld().markBlockForUpdate(this.thaumatorium.getPos());
/* 114 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 118 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*     */   {
/* 124 */     return this.thaumatorium.isUseableByPlayer(par1EntityPlayer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*     */   {
/* 133 */     ItemStack itemstack = null;
/* 134 */     Slot slot = (Slot)this.inventorySlots.get(par2);
/*     */     
/* 136 */     if ((slot != null) && (slot.getHasStack()))
/*     */     {
/* 138 */       ItemStack itemstack1 = slot.getStack();
/* 139 */       itemstack = itemstack1.copy();
/*     */       
/* 141 */       if (par2 != 0)
/*     */       {
/* 143 */         if (!mergeItemStack(itemstack1, 0, 1, false))
/*     */         {
/* 145 */           return null;
/*     */         }
/*     */       }
/* 148 */       else if ((par2 >= 1) && (par2 < 28))
/*     */       {
/* 150 */         if (!mergeItemStack(itemstack1, 28, 37, false))
/*     */         {
/* 152 */           return null;
/*     */         }
/*     */       } else {
/* 155 */         if ((par2 >= 28) && (par2 < 37) && (!mergeItemStack(itemstack1, 1, 28, false)))
/*     */         {
/* 157 */           return null;
/*     */         }
/* 159 */         if (!mergeItemStack(itemstack1, 1, 37, false))
/*     */         {
/* 161 */           return null;
/*     */         }
/*     */       }
/* 164 */       if (itemstack1.stackSize == 0)
/*     */       {
/* 166 */         slot.putStack((ItemStack)null);
/*     */       }
/*     */       else
/*     */       {
/* 170 */         slot.onSlotChanged();
/*     */       }
/*     */       
/* 173 */       if (itemstack1.stackSize == itemstack.stackSize)
/*     */       {
/* 175 */         return null;
/*     */       }
/*     */       
/* 178 */       slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
/*     */     }
/*     */     
/* 181 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerThaumatorium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */