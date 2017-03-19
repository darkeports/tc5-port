/*    */ package thaumcraft.common.lib.enchantment;
/*    */ 
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ import net.minecraft.enchantment.EnumEnchantmentType;
/*    */ import net.minecraft.item.ItemBook;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import thaumcraft.api.items.IRepairable;
/*    */ 
/*    */ public class EnchantmentRepair extends Enchantment
/*    */ {
/*    */   public EnchantmentRepair(int par1, int par2)
/*    */   {
/* 14 */     super(par1, new ResourceLocation("repair"), par2, EnumEnchantmentType.ALL);
/* 15 */     setName("repair");
/*    */   }
/*    */   
/*    */ 
/*    */   public int getMinEnchantability(int par1)
/*    */   {
/* 21 */     return 20 + (par1 - 1) * 10;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getMaxEnchantability(int par1)
/*    */   {
/* 30 */     return super.getMinEnchantability(par1) + 50;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getMaxLevel()
/*    */   {
/* 39 */     return 2;
/*    */   }
/*    */   
/*    */   public boolean canApply(ItemStack stack)
/*    */   {
/* 44 */     if ((stack.isItemStackDamageable()) && (((stack.getItem() instanceof IRepairable)) || ((stack.getItem() instanceof ItemBook))))
/* 45 */       return true;
/* 46 */     return false;
/*    */   }
/*    */   
/*    */   public boolean canApplyAtEnchantingTable(ItemStack stack)
/*    */   {
/* 51 */     return canApply(stack);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean canApplyTogether(Enchantment par1Enchantment)
/*    */   {
/* 57 */     return (super.canApplyTogether(par1Enchantment)) && (par1Enchantment.effectId != Enchantment.unbreaking.effectId);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\enchantment\EnchantmentRepair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */