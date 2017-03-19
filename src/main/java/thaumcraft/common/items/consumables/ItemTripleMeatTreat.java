/*    */ package thaumcraft.common.items.consumables;
/*    */ 
/*    */ import net.minecraft.item.ItemFood;
/*    */ import net.minecraft.potion.Potion;
/*    */ 
/*    */ public class ItemTripleMeatTreat
/*    */   extends ItemFood
/*    */ {
/*    */   public ItemTripleMeatTreat()
/*    */   {
/* 11 */     super(6, 0.8F, true);
/* 12 */     setAlwaysEdible();
/* 13 */     setPotionEffect(Potion.regeneration.id, 5, 0, 0.66F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemTripleMeatTreat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */