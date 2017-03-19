/*    */ package thaumcraft.common.container;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemPickaxe;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.common.items.wands.foci.ItemFocusExcavation;
/*    */ import thaumcraft.common.tiles.devices.TileArcaneBore;
/*    */ 
/*    */ public class ContainerArcaneBore extends Container
/*    */ {
/*    */   private TileArcaneBore tileEntity;
/*    */   
/*    */   public ContainerArcaneBore(InventoryPlayer iinventory, TileArcaneBore e)
/*    */   {
/* 19 */     this.tileEntity = e;
/* 20 */     addSlotToContainer(new SlotLimitedByClass(ItemFocusExcavation.class, e, 0, 26, 18));
/* 21 */     addSlotToContainer(new SlotLimitedByClass(ItemPickaxe.class, e, 1, 74, 18));
/* 22 */     bindPlayerInventory(iinventory);
/*    */   }
/*    */   
/*    */   protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
/* 26 */     for (int i = 0; i < 3; i++) {
/* 27 */       for (int j = 0; j < 9; j++) {
/* 28 */         addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 59 + i * 18));
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 33 */     for (int i = 0; i < 9; i++) {
/* 34 */       addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 117));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*    */   {
/* 42 */     return this.tileEntity.getWorld().getTileEntity(this.tileEntity.getPos()) == this.tileEntity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
/*    */   {
/* 50 */     ItemStack stack = null;
/* 51 */     Slot slotObject = (Slot)this.inventorySlots.get(slot);
/*    */     
/*    */ 
/* 54 */     if ((slotObject != null) && (slotObject.getHasStack())) {
/* 55 */       ItemStack stackInSlot = slotObject.getStack();
/* 56 */       stack = stackInSlot.copy();
/*    */       
/*    */ 
/* 59 */       if (slot <= 1) {
/* 60 */         if (!mergeItemStack(stackInSlot, 2, this.inventorySlots.size(), true)) {
/* 61 */           return null;
/*    */         }
/*    */       }
/* 64 */       else if (slot > 1) {
/* 65 */         if ((stackInSlot.getItem() instanceof ItemFocusExcavation)) {
/* 66 */           if (!mergeItemStack(stackInSlot, 0, 1, false)) {
/* 67 */             return null;
/*    */           }
/*    */           
/*    */         }
/* 71 */         else if (((stackInSlot.getItem() instanceof ItemPickaxe)) && 
/* 72 */           (!mergeItemStack(stackInSlot, 1, 2, false))) {
/* 73 */           return null;
/*    */ 
/*    */ 
/*    */         }
/*    */         
/*    */ 
/*    */ 
/*    */ 
/*    */       }
/* 82 */       else if (!mergeItemStack(stackInSlot, 2, 38, false))
/*    */       {
/* 84 */         return null;
/*    */       }
/*    */       
/* 87 */       if (stackInSlot.stackSize == 0) {
/* 88 */         slotObject.putStack(null);
/*    */       } else {
/* 90 */         slotObject.onSlotChanged();
/*    */       }
/*    */       
/* 93 */       if (stackInSlot.stackSize == stack.stackSize)
/*    */       {
/* 95 */         return null;
/*    */       }
/*    */     }
/*    */     
/* 99 */     return stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerArcaneBore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */