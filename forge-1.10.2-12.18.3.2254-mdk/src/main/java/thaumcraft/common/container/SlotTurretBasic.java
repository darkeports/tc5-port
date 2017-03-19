/*    */ package thaumcraft.common.container;
/*    */ 
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.common.entities.construct.EntityTurretCrossbow;
/*    */ 
/*    */ 
/*    */ public class SlotTurretBasic
/*    */   extends SlotMobEquipment
/*    */ {
/*    */   public SlotTurretBasic(EntityTurretCrossbow turret, int par3, int par4, int par5)
/*    */   {
/* 14 */     super(turret, par3, par4, par5);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isItemValid(ItemStack stack)
/*    */   {
/* 21 */     return (stack != null) && (stack.getItem() != null) && ((stack.getItem() == Items.arrow) || (stack.getItem() == ItemsTC.primalArrows));
/*    */   }
/*    */   
/*    */   public void onSlotChanged() {}
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\SlotTurretBasic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */