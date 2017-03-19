/*    */ package thaumcraft.common.entities.ai.combat;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.ai.EntityAIBase;
/*    */ import net.minecraft.entity.ai.EntitySenses;
/*    */ import net.minecraft.pathfinding.PathNavigate;
/*    */ import thaumcraft.common.entities.monster.tainted.EntityTaintCreeper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AICreeperSwell
/*    */   extends EntityAIBase
/*    */ {
/*    */   EntityTaintCreeper swellingCreeper;
/*    */   EntityLivingBase creeperAttackTarget;
/*    */   
/*    */   public AICreeperSwell(EntityTaintCreeper par1EntityCreeper)
/*    */   {
/* 20 */     this.swellingCreeper = par1EntityCreeper;
/* 21 */     setMutexBits(1);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 29 */     EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
/* 30 */     return (this.swellingCreeper.getCreeperState() > 0) || ((entitylivingbase != null) && (this.swellingCreeper.getDistanceSqToEntity(entitylivingbase) < 9.0D));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 38 */     this.swellingCreeper.getNavigator().clearPathEntity();
/* 39 */     this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void resetTask()
/*    */   {
/* 47 */     this.creeperAttackTarget = null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 55 */     if (this.creeperAttackTarget == null)
/*    */     {
/* 57 */       this.swellingCreeper.setCreeperState(-1);
/*    */     }
/* 59 */     else if (this.swellingCreeper.getDistanceSqToEntity(this.creeperAttackTarget) > 49.0D)
/*    */     {
/* 61 */       this.swellingCreeper.setCreeperState(-1);
/*    */     }
/* 63 */     else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget))
/*    */     {
/* 65 */       this.swellingCreeper.setCreeperState(-1);
/*    */     }
/*    */     else
/*    */     {
/* 69 */       this.swellingCreeper.setCreeperState(1);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\ai\combat\AICreeperSwell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */