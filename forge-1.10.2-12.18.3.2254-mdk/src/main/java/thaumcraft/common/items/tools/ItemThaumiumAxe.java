/*    */ package thaumcraft.common.items.tools;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.item.Item.ToolMaterial;
/*    */ import net.minecraft.item.ItemAxe;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.api.items.IRepairable;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ItemThaumiumAxe extends ItemAxe implements IRepairable
/*    */ {
/*    */   public ItemThaumiumAxe(Item.ToolMaterial enumtoolmaterial)
/*    */   {
/* 16 */     super(enumtoolmaterial);
/* 17 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */   public Set<String> getToolClasses(ItemStack stack)
/*    */   {
/* 22 */     return ImmutableSet.of("axe");
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*    */   {
/* 28 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemThaumiumAxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */