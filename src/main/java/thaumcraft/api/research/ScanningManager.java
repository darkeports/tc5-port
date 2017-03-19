/*    */ package thaumcraft.api.research;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ import net.minecraft.util.text.translation.I18n;
/*    */ 
/*    */ 
/*    */ public class ScanningManager
/*    */ {
/* 11 */   static ArrayList<IScanThing> things = new ArrayList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void addScannableThing(IScanThing obj)
/*    */   {
/* 20 */     things.add(obj);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void scanTheThing(EntityPlayer player, Object object)
/*    */   {
/* 29 */     boolean found = false;
/* 30 */     for (IScanThing thing : things) {
/* 31 */       if ((thing.checkThing(player, object)) && 
/* 32 */         (ResearchHelper.completeResearch(player, thing.getResearchKey()))) {
/* 33 */         found = true;
/*    */       }
/*    */     }
/*    */     
/* 37 */     if (!found) {
/* 38 */       player.addChatMessage(new TextComponentString("§5§o" + I18n.translateToLocal("tc.unknownobject")));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static boolean isThingStillScannable(EntityPlayer player, Object object)
/*    */   {
/* 48 */     for (IScanThing thing : things) {
/* 49 */       if ((thing.checkThing(player, object)) && 
/* 50 */         (!ResearchHelper.isResearchComplete(player.getName(), thing.getResearchKey()))) {
/* 51 */         return true;
/*    */       }
/*    */     }
/*    */     
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\research\ScanningManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */