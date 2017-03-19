/*    */ package thaumcraft.common.lib.aura;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class NTNormal extends NodeType
/*    */ {
/*    */   public NTNormal(int id)
/*    */   {
/*  9 */     super(id);
/*    */   }
/*    */   
/*    */ 
/*    */   void performTickEvent(EntityAuraNode node) {}
/*    */   
/*    */   void performPeriodicEvent(EntityAuraNode node)
/*    */   {
/* 17 */     if (node.worldObj.isRemote) return;
/* 18 */     thaumcraft.api.aspects.Aspect aspect = node.getAspect();
/* 19 */     if (aspect != null) {
/* 20 */       AuraHandler.addNodeRechargeTicket(node, aspect.isPrimal() ? aspect : thaumcraft.api.aspects.AspectHelper.getRandomPrimal(node.worldObj.rand, aspect), calculateStrength(node));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   int calculateStrength(EntityAuraNode node)
/*    */   {
/* 27 */     int m = node.worldObj.provider.getMoonPhase(node.worldObj.getWorldInfo().getWorldTime());
/* 28 */     float b = 1.0F + (Math.abs(m - 4) - 2) / 5.0F;
/* 29 */     return (int)Math.max(1.0D, Math.sqrt(node.getNodeSize() / 3.0F) * b);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\aura\NTNormal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */