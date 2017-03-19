/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
/*     */ import thaumcraft.common.tiles.crafting.TileSmelter;
/*     */ 
/*     */ public class ContainerSmelter extends Container
/*     */ {
/*     */   private TileSmelter furnace;
/*     */   private int lastCookTime;
/*     */   private int lastBurnTime;
/*     */   private int lastItemBurnTime;
/*     */   private int lastVis;
/*     */   private int lastSmelt;
/*     */   private int lastFlux;
/*     */   
/*     */   public ContainerSmelter(InventoryPlayer par1InventoryPlayer, TileSmelter tileEntity)
/*     */   {
/*  28 */     this.furnace = tileEntity;
/*  29 */     addSlotToContainer(new SlotLimitedHasAspects(tileEntity, 0, 80, 8));
/*  30 */     addSlotToContainer(new Slot(tileEntity, 1, 80, 48));
/*     */     
/*     */ 
/*  33 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  35 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  37 */         addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  41 */     for (i = 0; i < 9; i++)
/*     */     {
/*  43 */       addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onCraftGuiOpened(ICrafting par1ICrafting)
/*     */   {
/*  51 */     super.onCraftGuiOpened(par1ICrafting);
/*  52 */     par1ICrafting.func_175173_a(this, this.furnace);
/*  53 */     par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
/*  54 */     par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
/*  55 */     par1ICrafting.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
/*  56 */     par1ICrafting.sendProgressBarUpdate(this, 3, this.furnace.vis);
/*  57 */     par1ICrafting.sendProgressBarUpdate(this, 4, this.furnace.smeltTime);
/*     */   }
/*     */   
/*     */ 
/*     */   public void detectAndSendChanges()
/*     */   {
/*  63 */     super.detectAndSendChanges();
/*     */     
/*  65 */     for (int i = 0; i < this.crafters.size(); i++)
/*     */     {
/*  67 */       ICrafting icrafting = (ICrafting)this.crafters.get(i);
/*     */       
/*  69 */       if (this.lastCookTime != this.furnace.furnaceCookTime)
/*     */       {
/*  71 */         icrafting.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
/*     */       }
/*     */       
/*  74 */       if (this.lastBurnTime != this.furnace.furnaceBurnTime)
/*     */       {
/*  76 */         icrafting.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
/*     */       }
/*     */       
/*  79 */       if (this.lastItemBurnTime != this.furnace.currentItemBurnTime)
/*     */       {
/*  81 */         icrafting.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
/*     */       }
/*     */       
/*  84 */       if (this.lastVis != this.furnace.vis)
/*     */       {
/*  86 */         icrafting.sendProgressBarUpdate(this, 3, this.furnace.vis);
/*     */       }
/*     */       
/*  89 */       if (this.lastSmelt != this.furnace.smeltTime)
/*     */       {
/*  91 */         icrafting.sendProgressBarUpdate(this, 4, this.furnace.smeltTime);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  97 */     this.lastCookTime = this.furnace.furnaceCookTime;
/*  98 */     this.lastBurnTime = this.furnace.furnaceBurnTime;
/*  99 */     this.lastItemBurnTime = this.furnace.currentItemBurnTime;
/* 100 */     this.lastVis = this.furnace.vis;
/* 101 */     this.lastSmelt = this.furnace.smeltTime;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void updateProgressBar(int par1, int par2)
/*     */   {
/* 108 */     if (par1 == 0)
/*     */     {
/* 110 */       this.furnace.furnaceCookTime = par2;
/*     */     }
/*     */     
/* 113 */     if (par1 == 1)
/*     */     {
/* 115 */       this.furnace.furnaceBurnTime = par2;
/*     */     }
/*     */     
/* 118 */     if (par1 == 2)
/*     */     {
/* 120 */       this.furnace.currentItemBurnTime = par2;
/*     */     }
/*     */     
/* 123 */     if (par1 == 3)
/*     */     {
/* 125 */       this.furnace.vis = par2;
/*     */     }
/*     */     
/* 128 */     if (par1 == 4)
/*     */     {
/* 130 */       this.furnace.smeltTime = par2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*     */   {
/* 138 */     return this.furnace.isUseableByPlayer(par1EntityPlayer);
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*     */   {
/* 144 */     ItemStack itemstack = null;
/* 145 */     Slot slot = (Slot)this.inventorySlots.get(par2);
/*     */     
/* 147 */     if ((slot != null) && (slot.getHasStack()))
/*     */     {
/* 149 */       ItemStack itemstack1 = slot.getStack();
/* 150 */       itemstack = itemstack1.copy();
/*     */       
/* 152 */       if ((par2 != 1) && (par2 != 0))
/*     */       {
/* 154 */         AspectList al = ThaumcraftCraftingManager.getObjectTags(itemstack1);
/*     */         
/* 156 */         if (TileSmelter.isItemFuel(itemstack1))
/*     */         {
/* 158 */           if (!mergeItemStack(itemstack1, 1, 2, false))
/*     */           {
/* 160 */             if (!mergeItemStack(itemstack1, 0, 1, false))
/*     */             {
/* 162 */               return null;
/*     */             }
/*     */           }
/*     */         }
/* 166 */         else if ((al != null) && (al.size() > 0))
/*     */         {
/* 168 */           if (!mergeItemStack(itemstack1, 0, 1, false))
/*     */           {
/* 170 */             return null;
/*     */           }
/*     */         }
/* 173 */         else if ((par2 >= 2) && (par2 < 29))
/*     */         {
/* 175 */           if (!mergeItemStack(itemstack1, 29, 38, false))
/*     */           {
/* 177 */             return null;
/*     */           }
/*     */         }
/* 180 */         else if ((par2 >= 29) && (par2 < 38) && (!mergeItemStack(itemstack1, 2, 29, false)))
/*     */         {
/* 182 */           return null;
/*     */         }
/*     */       }
/* 185 */       else if (!mergeItemStack(itemstack1, 2, 38, false))
/*     */       {
/* 187 */         return null;
/*     */       }
/*     */       
/* 190 */       if (itemstack1.stackSize == 0)
/*     */       {
/* 192 */         slot.putStack((ItemStack)null);
/*     */       }
/*     */       else
/*     */       {
/* 196 */         slot.onSlotChanged();
/*     */       }
/*     */       
/* 199 */       if (itemstack1.stackSize == itemstack.stackSize)
/*     */       {
/* 201 */         return null;
/*     */       }
/*     */       
/* 204 */       slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
/*     */     }
/*     */     
/* 207 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerSmelter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */