/*    */ package thaumcraft.common.items.consumables;
/*    */ 
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ItemBathSalts
/*    */   extends Item
/*    */ {
/*    */   public ItemBathSalts()
/*    */   {
/* 13 */     setCreativeTab(Thaumcraft.tabTC);
/* 14 */     setHasSubtypes(false);
/*    */   }
/*    */   
/*    */ 
/*    */   public int getEntityLifespan(ItemStack itemStack, World world)
/*    */   {
/* 20 */     return 200;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemBathSalts.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */