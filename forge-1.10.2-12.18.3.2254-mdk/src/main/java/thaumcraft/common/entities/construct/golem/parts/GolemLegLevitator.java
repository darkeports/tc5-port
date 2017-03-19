/*    */ package thaumcraft.common.entities.construct.golem.parts;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.golems.IGolemAPI;
/*    */ 
/*    */ public class GolemLegLevitator implements thaumcraft.api.golems.parts.GolemLeg.ILegFunction
/*    */ {
/*    */   public void onUpdateTick(IGolemAPI golem)
/*    */   {
/* 11 */     if ((golem.getGolemWorld().isRemote) && ((!golem.getGolemEntity().onGround) || (golem.getGolemEntity().ticksExisted % 5 == 0))) {
/* 12 */       thaumcraft.common.Thaumcraft.proxy.getFX().drawGolemFlyParticles(golem.getGolemEntity().posX, golem.getGolemEntity().posY + 0.1D, golem.getGolemEntity().posZ, golem.getGolemWorld().rand.nextGaussian() / 100.0D, -0.1D, golem.getGolemWorld().rand.nextGaussian() / 100.0D);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\parts\GolemLegLevitator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */