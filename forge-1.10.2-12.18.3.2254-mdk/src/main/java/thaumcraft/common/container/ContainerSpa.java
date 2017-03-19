/*    */ package thaumcraft.common.container;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.common.items.consumables.ItemBathSalts;
/*    */ import thaumcraft.common.tiles.devices.TileSpa;
/*    */ 
/*    */ public class ContainerSpa extends Container
/*    */ {
/*    */   private TileSpa spa;
/*    */   private int lastBreakTime;
/*    */   
/*    */   public ContainerSpa(InventoryPlayer par1InventoryPlayer, TileSpa tileEntity)
/*    */   {
/* 19 */     this.spa = tileEntity;
/* 20 */     addSlotToContainer(new SlotLimitedByClass(ItemBathSalts.class, tileEntity, 0, 65, 31));
/*    */     
/*    */ 
/* 23 */     for (int i = 0; i < 3; i++)
/*    */     {
/* 25 */       for (int j = 0; j < 9; j++)
/*    */       {
/* 27 */         addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*    */       }
/*    */     }
/*    */     
/* 31 */     for (i = 0; i < 9; i++)
/*    */     {
/* 33 */       addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean enchantItem(EntityPlayer p, int button)
/*    */   {
/* 42 */     if (button == 1) {
/* 43 */       this.spa.toggleMix();
/*    */     }
/* 45 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*    */   {
/* 52 */     return this.spa.isUseableByPlayer(par1EntityPlayer);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
/*    */   {
/* 60 */     ItemStack stack = null;
/* 61 */     Slot slotObject = (Slot)this.inventorySlots.get(slot);
/*    */     
/*    */ 
/* 64 */     if ((slotObject != null) && (slotObject.getHasStack())) {
/* 65 */       ItemStack stackInSlot = slotObject.getStack();
/* 66 */       stack = stackInSlot.copy();
/*    */       
/* 68 */       if (slot == 0) {
/* 69 */         if ((!this.spa.isItemValidForSlot(slot, stackInSlot)) || (!mergeItemStack(stackInSlot, 1, this.inventorySlots.size(), true)))
/*    */         {
/*    */ 
/* 72 */           return null;
/*    */         }
/*    */         
/*    */       }
/* 76 */       else if ((!this.spa.isItemValidForSlot(slot, stackInSlot)) || (!mergeItemStack(stackInSlot, 0, 1, false)))
/*    */       {
/* 78 */         return null;
/*    */       }
/*    */       
/* 81 */       if (stackInSlot.stackSize == 0) {
/* 82 */         slotObject.putStack(null);
/*    */       } else {
/* 84 */         slotObject.onSlotChanged();
/*    */       }
/*    */     }
/*    */     
/* 88 */     return stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerSpa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */