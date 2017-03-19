/*    */ package thaumcraft.common.container;
/*    */ 
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotLimitedByItemstack extends Slot
/*    */ {
/*  9 */   ItemStack limitItem = null;
/*    */   
/*    */   public SlotLimitedByItemstack(ItemStack item, IInventory par2IInventory, int par3, int par4, int par5) {
/* 12 */     super(par2IInventory, par3, par4, par5);
/* 13 */     this.limitItem = item;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isItemValid(ItemStack par1ItemStack)
/*    */   {
/* 19 */     return par1ItemStack.isItemEqual(this.limitItem);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\SlotLimitedByItemstack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */