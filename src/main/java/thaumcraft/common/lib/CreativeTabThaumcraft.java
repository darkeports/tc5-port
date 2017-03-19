/*    */ package thaumcraft.common.lib;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ 
/*    */ public final class CreativeTabThaumcraft extends CreativeTabs
/*    */ {
/*    */   public CreativeTabThaumcraft(int par1, String par2Str)
/*    */   {
/* 13 */     super(par1, par2Str);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public Item getTabIconItem()
/*    */   {
/* 24 */     return ItemsTC.wand;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\CreativeTabThaumcraft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */