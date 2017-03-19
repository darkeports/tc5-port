/*    */ package thaumcraft.common.entities.construct.golem.parts;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.IRangedAttackMob;
/*    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.golems.IGolemAPI;
/*    */ import thaumcraft.api.golems.parts.GolemArm.IArmFunction;
/*    */ import thaumcraft.common.entities.construct.golem.ai.AIArrowAttack;
/*    */ import thaumcraft.common.entities.projectile.EntityGolemDart;
/*    */ 
/*    */ public class GolemArmDart implements GolemArm.IArmFunction
/*    */ {
/*    */   public void onMeleeAttack(IGolemAPI golem, Entity target) {}
/*    */   
/*    */   public void onRangedAttack(IGolemAPI golem, EntityLivingBase target, float range)
/*    */   {
/* 20 */     EntityGolemDart entityarrow = new EntityGolemDart(golem.getGolemWorld(), golem.getGolemEntity(), 1.6F);
/* 21 */     float dmg = (float)golem.getGolemEntity().getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.attackDamage).getAttributeValue() / 3.0F;
/* 22 */     entityarrow.setDamage(dmg + range + golem.getGolemWorld().rand.nextGaussian() * 0.25D);
/* 23 */     double d0 = target.posX - golem.getGolemEntity().posX;
/* 24 */     double d1 = target.getEntityBoundingBox().minY + target.getEyeHeight() + range * range - entityarrow.posY;
/* 25 */     double d2 = target.posZ - golem.getGolemEntity().posZ;
/* 26 */     entityarrow.setThrowableHeading(d0, d1, d2, 1.6F, 3.0F);
/*    */     
/* 28 */     golem.getGolemWorld().spawnEntityInWorld(entityarrow);
/* 29 */     golem.getGolemEntity().playSound("random.bow", 1.0F, 1.0F / (golem.getGolemWorld().rand.nextFloat() * 0.4F + 0.8F));
/*    */   }
/*    */   
/*    */ 
/*    */   public net.minecraft.entity.ai.EntityAIArrowAttack getRangedAttackAI(IRangedAttackMob golem)
/*    */   {
/* 35 */     return new AIArrowAttack(golem, 1.0D, 20, 25, 16.0F);
/*    */   }
/*    */   
/*    */   public void onUpdateTick(IGolemAPI golem) {}
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\parts\GolemArmDart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */