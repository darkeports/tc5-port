/*    */ package thaumcraft.common.lib.enchantment;
/*    */ 
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ import net.minecraft.enchantment.EnumEnchantmentType;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ import net.minecraft.item.ItemBook;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import thaumcraft.common.items.armor.ItemThaumostaticHarness;
/*    */ 
/*    */ public class EnchantmentHaste extends Enchantment
/*    */ {
/*    */   public EnchantmentHaste(int par1, int par2)
/*    */   {
/* 15 */     super(par1, new ResourceLocation("haste"), par2, EnumEnchantmentType.ARMOR);
/* 16 */     setName("haste");
/*    */   }
/*    */   
/*    */ 
/*    */   public int getMinEnchantability(int par1)
/*    */   {
/* 22 */     return 15 + (par1 - 1) * 9;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getMaxEnchantability(int par1)
/*    */   {
/* 31 */     return super.getMinEnchantability(par1) + 50;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getMaxLevel()
/*    */   {
/* 40 */     return 3;
/*    */   }
/*    */   
/*    */   public boolean canApply(ItemStack is)
/*    */   {
/* 45 */     if ((is != null) && ((((is.getItem() instanceof ItemArmor)) && ((((ItemArmor)is.getItem()).armorType == 3) || ((is.getItem() instanceof ItemThaumostaticHarness)))) || ((is.getItem() instanceof ItemBook))))
/*    */     {
/*    */ 
/* 48 */       return true;
/*    */     }
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public boolean canApplyAtEnchantingTable(ItemStack stack)
/*    */   {
/* 55 */     return canApply(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\enchantment\EnchantmentHaste.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */