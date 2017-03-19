/*    */ package thaumcraft.common.items.consumables;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ 
/*    */ public class ItemRunedTablet
/*    */   extends Item
/*    */ {
/*    */   public ItemRunedTablet()
/*    */   {
/* 18 */     setMaxStackSize(1);
/* 19 */     setHasSubtypes(true);
/* 20 */     setMaxDamage(0);
/* 21 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */   public EnumRarity getRarity(ItemStack stack)
/*    */   {
/* 26 */     return EnumRarity.RARE;
/*    */   }
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*    */   {
/* 31 */     super.addInformation(stack, player, list, par4);
/* 32 */     list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("item.runed_tablet.text"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemRunedTablet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */