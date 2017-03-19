/*    */ package thaumcraft.common.items.tools;
/*    */ 
/*    */ import net.minecraft.item.Item;
/*    */ import thaumcraft.api.items.IScribeTools;
/*    */ 
/*    */ public class ItemScribingTools
/*    */   extends Item implements IScribeTools
/*    */ {
/*    */   public ItemScribingTools()
/*    */   {
/* 11 */     this.maxStackSize = 1;
/* 12 */     this.canRepair = true;
/* 13 */     setMaxDamage(100);
/* 14 */     setHasSubtypes(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemScribingTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */