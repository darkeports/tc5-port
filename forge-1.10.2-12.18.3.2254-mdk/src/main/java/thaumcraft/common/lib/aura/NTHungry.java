/*    */ package thaumcraft.common.lib.aura;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class NTHungry extends NTNormal
/*    */ {
/*    */   public NTHungry(int id)
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
/* 19 */     int str = calculateStrength(node);
/* 20 */     float f = thaumcraft.api.aura.AuraHelper.getAura(node.worldObj, node.getPosition(), aspect) / thaumcraft.api.aura.AuraHelper.getAuraBase(node.worldObj, node.getPosition());
/* 21 */     if ((aspect != null) && (node.worldObj.rand.nextFloat() < f) && (AuraHandler.drainAura(node.worldObj, node.getPosition(), aspect, str)) && 
/* 22 */       (node.worldObj.rand.nextInt(1 + node.getNodeSize() * 2) == 0)) {
/* 23 */       node.setNodeSize(node.getNodeSize() + 1);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   int calculateStrength(EntityAuraNode node)
/*    */   {
/* 30 */     return (int)Math.max(1.0F, super.calculateStrength(node) * 0.1F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\aura\NTHungry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */