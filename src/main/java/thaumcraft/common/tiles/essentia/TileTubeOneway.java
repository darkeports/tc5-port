/*    */ package thaumcraft.common.tiles.essentia;
/*    */ 
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileTubeOneway
/*    */   extends TileTube
/*    */ {
/*    */   void calculateSuction(Aspect filter, boolean restrict, boolean directional)
/*    */   {
/* 12 */     super.calculateSuction(filter, restrict, true);
/*    */   }
/*    */   
/*    */   void equalizeWithNeighbours(boolean directional)
/*    */   {
/* 17 */     super.equalizeWithNeighbours(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\essentia\TileTubeOneway.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */