/*    */ package thaumcraft.common.container;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.api.wands.ItemFocusBasic;
/*    */ import thaumcraft.common.entities.construct.EntityTurretFocus;
/*    */ 
/*    */ 
/*    */ public class SlotTurretFocus
/*    */   extends SlotMobEquipment
/*    */ {
/*    */   public SlotTurretFocus(EntityTurretFocus entity, int par3, int par4, int par5)
/*    */   {
/* 13 */     super(entity, par3, par4, par5);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isItemValid(ItemStack stack)
/*    */   {
/* 20 */     return (stack != null) && (stack.getItem() != null) && ((stack.getItem() instanceof ItemFocusBasic)) && (((ItemFocusBasic)stack.getItem()).canBePlacedInTurret());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onSlotChanged()
/*    */   {
/* 27 */     ((EntityTurretFocus)this.entity).updateFocus();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\SlotTurretFocus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */