/*    */ package thaumcraft.common.items.tools;
/*    */ 
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ItemSanityChecker
/*    */   extends Item
/*    */ {
/*    */   public ItemSanityChecker()
/*    */   {
/* 13 */     setMaxStackSize(1);
/* 14 */     setHasSubtypes(false);
/* 15 */     setMaxDamage(0);
/* 16 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public EnumRarity getRarity(ItemStack itemstack)
/*    */   {
/* 24 */     return EnumRarity.UNCOMMON;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemSanityChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */