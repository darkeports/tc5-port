/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.items.wands.ItemFocusPouch;
/*     */ 
/*     */ public class ContainerFocusPouch extends Container
/*     */ {
/*     */   private World worldObj;
/*     */   private int posX;
/*     */   private int posY;
/*     */   private int posZ;
/*     */   private int blockSlot;
/*  20 */   public IInventory input = new InventoryFocusPouch(this);
/*  21 */   ItemStack pouch = null;
/*  22 */   EntityPlayer player = null;
/*     */   
/*     */ 
/*     */   public ContainerFocusPouch(InventoryPlayer iinventory, World par2World, int par3, int par4, int par5)
/*     */   {
/*  27 */     this.worldObj = par2World;
/*  28 */     this.posX = par3;
/*  29 */     this.posY = par4;
/*  30 */     this.posZ = par5;
/*  31 */     this.player = iinventory.player;
/*  32 */     this.pouch = iinventory.getCurrentItem();
/*  33 */     this.blockSlot = (iinventory.currentItem + 45);
/*     */     
/*  35 */     for (int a = 0; a < 18; a++) {
/*  36 */       addSlotToContainer(new SlotLimitedByClass(thaumcraft.api.wands.ItemFocusBasic.class, this.input, a, 37 + a % 6 * 18, 51 + a / 6 * 18));
/*     */     }
/*  38 */     bindPlayerInventory(iinventory);
/*     */     
/*  40 */     if (!par2World.isRemote) {
/*     */       try {
/*  42 */         ((InventoryFocusPouch)this.input).stackList = ((ItemFocusPouch)this.pouch.getItem()).getInventory(this.pouch);
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*  46 */     onCraftMatrixChanged(this.input);
/*     */   }
/*     */   
/*     */   protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
/*  50 */     for (int i = 0; i < 3; i++) {
/*  51 */       for (int j = 0; j < 9; j++) {
/*  52 */         addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 151 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  57 */     for (int i = 0; i < 9; i++) {
/*  58 */       addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 209));
/*     */     }
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
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
/*     */   {
/*  86 */     if (slot == this.blockSlot) return null;
/*  87 */     ItemStack stack = null;
/*  88 */     Slot slotObject = (Slot)this.inventorySlots.get(slot);
/*     */     
/*     */ 
/*  91 */     if ((slotObject != null) && (slotObject.getHasStack())) {
/*  92 */       ItemStack stackInSlot = slotObject.getStack();
/*  93 */       stack = stackInSlot.copy();
/*     */       
/*  95 */       if (slot < 18) {
/*  96 */         if ((!this.input.isItemValidForSlot(slot, stackInSlot)) || (!mergeItemStack(stackInSlot, 18, this.inventorySlots.size(), true)))
/*     */         {
/*     */ 
/*  99 */           return null;
/*     */         }
/*     */       }
/* 102 */       else if ((!this.input.isItemValidForSlot(slot, stackInSlot)) || (!mergeItemStack(stackInSlot, 0, 18, false)))
/*     */       {
/* 104 */         return null;
/*     */       }
/*     */       
/* 107 */       if (stackInSlot.stackSize == 0) {
/* 108 */         slotObject.putStack(null);
/*     */       } else {
/* 110 */         slotObject.onSlotChanged();
/*     */       }
/*     */     }
/*     */     
/* 114 */     return stack;
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer var1)
/*     */   {
/* 119 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
/*     */   {
/* 125 */     if (par1 == this.blockSlot) return null;
/* 126 */     return super.slotClick(par1, par2, par3, par4EntityPlayer);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/*     */   {
/* 132 */     super.onContainerClosed(par1EntityPlayer);
/* 133 */     if (!this.worldObj.isRemote)
/*     */     {
/* 135 */       ((ItemFocusPouch)this.pouch.getItem()).setInventory(this.pouch, ((InventoryFocusPouch)this.input).stackList);
/*     */       
/* 137 */       if (this.player == null) return;
/* 138 */       if ((this.player.getHeldItem() != null) && (this.player.getHeldItem().isItemEqual(this.pouch)))
/* 139 */         this.player.setCurrentItemOrArmor(0, this.pouch);
/* 140 */       this.player.inventory.markDirty();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerFocusPouch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */