/*    */ package thaumcraft.common.items.tools;
/*    */ 
/*    */ import net.minecraft.item.Item.ToolMaterial;
/*    */ import net.minecraft.item.ItemHoe;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ItemThaumiumHoe extends ItemHoe implements thaumcraft.api.items.IRepairable
/*    */ {
/*    */   public ItemThaumiumHoe(Item.ToolMaterial enumtoolmaterial)
/*    */   {
/* 12 */     super(enumtoolmaterial);
/* 13 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*    */   {
/* 20 */     return par2ItemStack.isItemEqual(new ItemStack(thaumcraft.api.items.ItemsTC.ingots)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemThaumiumHoe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */