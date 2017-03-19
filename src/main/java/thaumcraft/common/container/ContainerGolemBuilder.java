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
/*     */ import thaumcraft.common.tiles.crafting.TileGolemBuilder;
/*     */ 
/*     */ public class ContainerGolemBuilder extends Container
/*     */ {
/*     */   private TileGolemBuilder builder;
/*     */   
/*     */   public ContainerGolemBuilder(InventoryPlayer par1InventoryPlayer, TileGolemBuilder tileEntity)
/*     */   {
/*  20 */     this.builder = tileEntity;
/*  21 */     addSlotToContainer(new SlotOutput(tileEntity, 0, 160, 104));
/*     */     
/*     */ 
/*  24 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  26 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  28 */         addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 24 + j * 18, 142 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  32 */     for (i = 0; i < 9; i++)
/*     */     {
/*  34 */       addSlotToContainer(new Slot(par1InventoryPlayer, i, 24 + i * 18, 200));
/*     */     }
/*     */   }
/*     */   
/*  38 */   public static boolean redo = false;
/*     */   private int lastCost;
/*     */   private int lastMaxCost;
/*     */   
/*  42 */   public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn) { redo = true;
/*  43 */     return super.slotClick(slotId, clickedButton, mode, playerIn);
/*     */   }
/*     */   
/*     */   public void putStackInSlot(int p_75141_1_, ItemStack p_75141_2_)
/*     */   {
/*  48 */     redo = true;
/*  49 */     super.putStackInSlot(p_75141_1_, p_75141_2_);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean enchantItem(EntityPlayer p, int button)
/*     */   {
/*  55 */     if (button == 99) redo = true;
/*  56 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onCraftGuiOpened(ICrafting par1ICrafting)
/*     */   {
/*  62 */     super.onCraftGuiOpened(par1ICrafting);
/*  63 */     par1ICrafting.sendProgressBarUpdate(this, 0, this.builder.cost);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void detectAndSendChanges()
/*     */   {
/*  72 */     super.detectAndSendChanges();
/*     */     
/*  74 */     for (int i = 0; i < this.crafters.size(); i++)
/*     */     {
/*  76 */       ICrafting icrafting = (ICrafting)this.crafters.get(i);
/*     */       
/*  78 */       if (this.lastCost != this.builder.cost)
/*     */       {
/*  80 */         icrafting.sendProgressBarUpdate(this, 0, this.builder.cost);
/*     */       }
/*     */       
/*  83 */       if (this.lastMaxCost != this.builder.maxCost)
/*     */       {
/*  85 */         icrafting.sendProgressBarUpdate(this, 1, this.builder.maxCost);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  90 */     this.lastCost = this.builder.cost;
/*  91 */     this.lastMaxCost = this.builder.maxCost;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void updateProgressBar(int par1, int par2)
/*     */   {
/*  98 */     if (par1 == 0)
/*     */     {
/* 100 */       this.builder.cost = par2;
/*     */     }
/* 102 */     if (par1 == 1)
/*     */     {
/* 104 */       this.builder.maxCost = par2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*     */   {
/* 112 */     return this.builder.isUseableByPlayer(par1EntityPlayer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
/*     */   {
/* 120 */     ItemStack stack = null;
/* 121 */     Slot slotObject = (Slot)this.inventorySlots.get(slot);
/*     */     
/*     */ 
/* 124 */     if ((slotObject != null) && (slotObject.getHasStack())) {
/* 125 */       ItemStack stackInSlot = slotObject.getStack();
/* 126 */       stack = stackInSlot.copy();
/*     */       
/* 128 */       if (slot == 0) {
/* 129 */         if ((!this.builder.isItemValidForSlot(slot, stackInSlot)) || (!mergeItemStack(stackInSlot, 1, this.inventorySlots.size(), true)))
/*     */         {
/*     */ 
/* 132 */           return null;
/*     */         }
/*     */         
/*     */       }
/* 136 */       else if ((!this.builder.isItemValidForSlot(slot, stackInSlot)) || (!mergeItemStack(stackInSlot, 0, 1, false)))
/*     */       {
/* 138 */         return null;
/*     */       }
/*     */       
/* 141 */       if (stackInSlot.stackSize == 0) {
/* 142 */         slotObject.putStack(null);
/*     */       } else {
/* 144 */         slotObject.onSlotChanged();
/*     */       }
/*     */     }
/*     */     
/* 148 */     return stack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerGolemBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */