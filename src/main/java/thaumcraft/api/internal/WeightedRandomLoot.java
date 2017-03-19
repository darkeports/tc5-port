/*    */ package thaumcraft.api.internal;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.WeightedRandom.Item;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeightedRandomLoot
/*    */   extends Item
/*    */ {
/*    */   public ItemStack item;
/*    */   
/*    */   public WeightedRandomLoot(ItemStack stack, int weight)
/*    */   {
/* 16 */     super(weight);
/* 17 */     this.item = stack;
/*    */   }
/*    */   
/* 20 */   public static ArrayList<WeightedRandomLoot> lootBagCommon = new ArrayList();
/* 21 */   public static ArrayList<WeightedRandomLoot> lootBagUncommon = new ArrayList();
/* 22 */   public static ArrayList<WeightedRandomLoot> lootBagRare = new ArrayList();
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\internal\WeightedRandomLoot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */