/*    */ package thaumcraft.common.lib.aura;
/*    */ 
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ 
/*    */ public class NTTaint extends NTNormal
/*    */ {
/*    */   public NTTaint(int id) {
/*  8 */     super(id);
/*    */   }
/*    */   
/*    */   void performPeriodicEvent(EntityAuraNode node)
/*    */   {
/* 13 */     super.performPeriodicEvent(node);
/* 14 */     float f = AuraHandler.getAuraCurrent(node.worldObj, node.getPosition(), Aspect.FLUX) / AuraHandler.getAuraBase(node.worldObj, node.getPosition());
/* 15 */     if (node.worldObj.rand.nextFloat() > f * 0.8F) {
/* 16 */       AuraHandler.addNodeRechargeTicket(node, Aspect.FLUX, (int)Math.max(1.0D, super.calculateStrength(node) * 0.2D));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\aura\NTTaint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */