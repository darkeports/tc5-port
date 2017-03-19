/*    */ package thaumcraft.common.lib.aura;
/*    */ 
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ 
/*    */ public class NTUnstable extends NTNormal
/*    */ {
/*    */   public NTUnstable(int id) {
/*  8 */     super(id);
/*    */   }
/*    */   
/*    */   void performPeriodicEvent(EntityAuraNode node)
/*    */   {
/* 13 */     super.performPeriodicEvent(node);
/*    */     
/* 15 */     if (node.worldObj.rand.nextInt(33) == 0) {
/* 16 */       int s = Aspect.getPrimalAspects().size();
/* 17 */       node.setAspectTag(((Aspect)Aspect.getPrimalAspects().get(node.worldObj.rand.nextInt(s))).getTag());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\aura\NTUnstable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */