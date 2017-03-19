/*    */ package thaumcraft.common.lib.aura;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class NTPure extends NTNormal
/*    */ {
/*    */   public NTPure(int id)
/*    */   {
/*  9 */     super(id);
/*    */   }
/*    */   
/*    */ 
/*    */   void performPeriodicEvent(EntityAuraNode node)
/*    */   {
/* 15 */     super.performPeriodicEvent(node);
/* 16 */     if ((AuraHandler.drainAura(node.worldObj, new net.minecraft.util.BlockPos(node.posX, node.posY, node.posZ), thaumcraft.api.aspects.Aspect.FLUX, 1)) && (node.worldObj.rand.nextFloat() < 0.025F))
/*    */     {
/* 18 */       node.setNodeSize(node.getNodeSize() - 1);
/* 19 */       if (node.getNodeSize() <= 0) node.setDead();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\aura\NTPure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */