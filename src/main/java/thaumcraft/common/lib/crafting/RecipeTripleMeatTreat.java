/*    */ package thaumcraft.common.lib.crafting;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.ForgeHooks;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ 
/*    */ public class RecipeTripleMeatTreat implements IRecipe
/*    */ {
/*    */   public boolean matches(InventoryCrafting inv, World worldIn)
/*    */   {
/* 16 */     boolean sugar = false;
/* 17 */     ArrayList<Integer> meats = new ArrayList();
/* 18 */     for (int a = 0; a < 3; a++)
/* 19 */       for (int b = 0; b < 3; b++)
/* 20 */         if (inv.getStackInRowAndColumn(a, b) != null) {
/* 21 */           ItemStack stack = inv.getStackInRowAndColumn(a, b).copy();
/* 22 */           if ((stack.getItem() == Items.sugar) && (sugar)) return false;
/* 23 */           if ((stack.getItem() == Items.sugar) && (!sugar)) {
/* 24 */             sugar = true;
/*    */ 
/*    */           }
/* 27 */           else if (stack.getItem() == ItemsTC.chunks) {
/* 28 */             if ((meats.contains(Integer.valueOf(stack.getItemDamage()))) || (meats.size() >= 3)) return false;
/* 29 */             meats.add(Integer.valueOf(stack.getItemDamage()));
/*    */           }
/*    */           else {
/* 32 */             return false;
/*    */           }
/*    */         }
/* 35 */     return (sugar) && (meats.size() == 3);
/*    */   }
/*    */   
/*    */   public ItemStack getCraftingResult(InventoryCrafting inv)
/*    */   {
/* 40 */     return new ItemStack(ItemsTC.tripleMeatTreat);
/*    */   }
/*    */   
/*    */   public int getRecipeSize()
/*    */   {
/* 45 */     return 9;
/*    */   }
/*    */   
/*    */   public ItemStack getRecipeOutput()
/*    */   {
/* 50 */     return new ItemStack(ItemsTC.tripleMeatTreat);
/*    */   }
/*    */   
/*    */   public ItemStack[] getRemainingItems(InventoryCrafting inv)
/*    */   {
/* 55 */     ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*    */     
/* 57 */     for (int i = 0; i < aitemstack.length; i++)
/*    */     {
/* 59 */       ItemStack itemstack = inv.getStackInSlot(i);
/* 60 */       aitemstack[i] = ForgeHooks.getContainerItem(itemstack);
/*    */     }
/*    */     
/* 63 */     return aitemstack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\crafting\RecipeTripleMeatTreat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */