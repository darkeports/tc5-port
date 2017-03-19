/*    */ package thaumcraft.common.container;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.api.wands.IWand;
/*    */ 
/*    */ public class SlotLimitedByWand
/*    */   extends Slot
/*    */ {
/* 12 */   int limit = 64;
/*    */   
/*    */   public SlotLimitedByWand(IInventory par2IInventory, int par3, int par4, int par5)
/*    */   {
/* 16 */     super(par2IInventory, par3, par4, par5);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isItemValid(ItemStack stack)
/*    */   {
/* 23 */     return (stack != null) && (stack.getItem() != null) && ((stack.getItem() instanceof IWand)) && (!((IWand)stack.getItem()).isStaff(stack));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getSlotStackLimit()
/*    */   {
/* 31 */     return this.limit;
/*    */   }
/*    */   
/*    */ 
/*    */   public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
/*    */   {
/* 37 */     super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\SlotLimitedByWand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */