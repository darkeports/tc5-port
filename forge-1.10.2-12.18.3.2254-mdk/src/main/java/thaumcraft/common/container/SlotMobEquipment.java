/*    */ package thaumcraft.common.container;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotMobEquipment
/*    */   extends Slot
/*    */ {
/*    */   EntityLiving entity;
/*    */   
/*    */   public SlotMobEquipment(EntityLiving entity, int par3, int par4, int par5)
/*    */   {
/* 15 */     super(null, par3, par4, par5);
/* 16 */     this.entity = entity;
/*    */   }
/*    */   
/*    */ 
/*    */   public ItemStack getStack()
/*    */   {
/* 22 */     return this.entity.getEquipmentInSlot(getSlotIndex());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void putStack(ItemStack stack)
/*    */   {
/* 29 */     this.entity.setCurrentItemOrArmor(getSlotIndex(), stack);
/*    */     
/* 31 */     if ((stack != null) && (stack.stackSize > getSlotStackLimit()))
/*    */     {
/* 33 */       stack.stackSize = getSlotStackLimit();
/*    */     }
/* 35 */     onSlotChanged();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onSlotChanged() {}
/*    */   
/*    */ 
/*    */ 
/*    */   public int getSlotStackLimit()
/*    */   {
/* 47 */     return 64;
/*    */   }
/*    */   
/*    */ 
/*    */   public ItemStack decrStackSize(int amount)
/*    */   {
/* 53 */     if (getStack() != null)
/*    */     {
/*    */ 
/*    */ 
/* 57 */       if (getStack().stackSize <= amount)
/*    */       {
/* 59 */         ItemStack itemstack = getStack();
/* 60 */         putStack(null);
/* 61 */         return itemstack;
/*    */       }
/*    */       
/*    */ 
/* 65 */       ItemStack itemstack = getStack().splitStack(amount);
/* 66 */       if (getStack().stackSize == 0)
/*    */       {
/* 68 */         putStack(null);
/*    */       }
/* 70 */       return itemstack;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 75 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isHere(IInventory inv, int slotIn)
/*    */   {
/* 83 */     return slotIn == getSlotIndex();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\SlotMobEquipment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */