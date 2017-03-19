/*    */ package thaumcraft.common.entities.ai.combat;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*    */ 
/*    */ public class AILongRangeAttack extends EntityAIArrowAttack
/*    */ {
/*    */   private final EntityLiving wielder;
/* 10 */   double minDistance = 0.0D;
/*    */   
/*    */   public AILongRangeAttack(net.minecraft.entity.IRangedAttackMob par1IRangedAttackMob, double min, double p_i1650_2_, int p_i1650_4_, int p_i1650_5_, float p_i1650_6_)
/*    */   {
/* 14 */     super(par1IRangedAttackMob, p_i1650_2_, p_i1650_4_, p_i1650_5_, p_i1650_6_);
/*    */     
/* 16 */     this.minDistance = min;
/* 17 */     this.wielder = ((EntityLiving)par1IRangedAttackMob);
/*    */   }
/*    */   
/*    */   public boolean shouldExecute()
/*    */   {
/* 22 */     boolean ex = super.shouldExecute();
/* 23 */     if (ex) {
/* 24 */       EntityLivingBase var1 = this.wielder.getAttackTarget();
/* 25 */       if (var1 == null) {
/* 26 */         return false;
/*    */       }
/* 28 */       if (var1.isDead) {
/* 29 */         this.wielder.setAttackTarget(null);
/* 30 */         return false;
/*    */       }
/* 32 */       double ra = this.wielder.getDistanceSq(var1.posX, var1.getEntityBoundingBox().minY, var1.posZ);
/* 33 */       if (ra < this.minDistance * this.minDistance) { return false;
/*    */       }
/*    */     }
/* 36 */     return ex;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\ai\combat\AILongRangeAttack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */