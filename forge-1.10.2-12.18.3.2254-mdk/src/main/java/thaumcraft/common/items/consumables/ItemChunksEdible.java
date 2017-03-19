/*    */ package thaumcraft.common.items.consumables;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemFood;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.common.items.IVariantItems;
/*    */ 
/*    */ 
/*    */ public class ItemChunksEdible
/*    */   extends ItemFood
/*    */   implements IVariantItems
/*    */ {
/*    */   public final int itemUseDuration;
/*    */   
/*    */   public ItemChunksEdible()
/*    */   {
/* 21 */     super(1, 0.3F, false);
/* 22 */     this.itemUseDuration = 10;
/* 23 */     setMaxStackSize(64);
/* 24 */     setHasSubtypes(true);
/* 25 */     setMaxDamage(0);
/*    */   }
/*    */   
/* 28 */   private String[] variants = { "beef", "chicken", "pork", "fish", "rabbit", "mutton" };
/*    */   
/*    */ 
/*    */   public int getMaxItemUseDuration(ItemStack par1ItemStack)
/*    */   {
/* 33 */     return this.itemUseDuration;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*    */   {
/* 39 */     for (int a = 0; a < this.variants.length; a++) {
/* 40 */       par3List.add(new ItemStack(this, 1, a));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/*    */   {
/* 47 */     return super.getUnlocalizedName() + "." + this.variants[par1ItemStack.getItemDamage()];
/*    */   }
/*    */   
/*    */   public String[] getVariantNames()
/*    */   {
/* 52 */     return this.variants;
/*    */   }
/*    */   
/*    */   public int[] getVariantMeta()
/*    */   {
/* 57 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemChunksEdible.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */