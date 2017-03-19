/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.common.tiles.crafting.TileFocalManipulator;
/*     */ 
/*     */ public class ContainerFocalManipulator extends Container
/*     */ {
/*     */   private TileFocalManipulator table;
/*     */   private int lastBreakTime;
/*     */   
/*     */   public ContainerFocalManipulator(InventoryPlayer par1InventoryPlayer, TileFocalManipulator tileEntity)
/*     */   {
/*  19 */     this.table = tileEntity;
/*  20 */     addSlotToContainer(new SlotLimitedByClass(ItemFocusBasic.class, tileEntity, 0, 88, 60));
/*     */     
/*     */ 
/*  23 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  25 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  27 */         addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 16 + j * 18, 151 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  31 */     for (i = 0; i < 9; i++)
/*     */     {
/*  33 */       addSlotToContainer(new Slot(par1InventoryPlayer, i, 16 + i * 18, 209));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean enchantItem(EntityPlayer p, int button)
/*     */   {
/*  41 */     if ((button >= 0) && 
/*  42 */       (!this.table.startCraft(button, p))) {
/*  43 */       this.table.getWorld().playSoundEffect(this.table.getPos().getX(), this.table.getPos().getY(), this.table.getPos().getZ(), "thaumcraft:craftfail", 0.33F, 1.0F);
/*     */     }
/*     */     
/*     */ 
/*  47 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*     */   {
/*  54 */     return this.table.isUseableByPlayer(par1EntityPlayer);
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*     */   {
/*  60 */     ItemStack itemstack = null;
/*  61 */     Slot slot = (Slot)this.inventorySlots.get(par2);
/*     */     
/*  63 */     if ((slot != null) && (slot.getHasStack()))
/*     */     {
/*  65 */       ItemStack itemstack1 = slot.getStack();
/*  66 */       itemstack = itemstack1.copy();
/*     */       
/*  68 */       if (par2 != 0)
/*     */       {
/*  70 */         if ((itemstack1.getItem() instanceof ItemFocusBasic))
/*     */         {
/*  72 */           if (!mergeItemStack(itemstack1, 0, 1, false))
/*     */           {
/*  74 */             return null;
/*     */           }
/*     */         }
/*  77 */         else if ((par2 >= 1) && (par2 < 28))
/*     */         {
/*  79 */           if (!mergeItemStack(itemstack1, 28, 37, false))
/*     */           {
/*  81 */             return null;
/*     */           }
/*     */         }
/*  84 */         else if ((par2 >= 28) && (par2 < 37) && (!mergeItemStack(itemstack1, 1, 28, false)))
/*     */         {
/*  86 */           return null;
/*     */         }
/*     */       }
/*  89 */       else if (!mergeItemStack(itemstack1, 1, 37, false))
/*     */       {
/*  91 */         return null;
/*     */       }
/*     */       
/*  94 */       if (itemstack1.stackSize == 0)
/*     */       {
/*  96 */         slot.putStack((ItemStack)null);
/*     */       }
/*     */       else
/*     */       {
/* 100 */         slot.onSlotChanged();
/*     */       }
/*     */       
/* 103 */       if (itemstack1.stackSize == itemstack.stackSize)
/*     */       {
/* 105 */         return null;
/*     */       }
/*     */       
/* 108 */       slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
/*     */     }
/*     */     
/* 111 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerFocalManipulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */