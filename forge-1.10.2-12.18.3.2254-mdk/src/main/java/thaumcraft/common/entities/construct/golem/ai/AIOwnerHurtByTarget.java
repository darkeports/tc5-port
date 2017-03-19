/*    */ package thaumcraft.common.entities.construct.golem.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.ai.EntityAITarget;
/*    */ import thaumcraft.common.entities.construct.EntityOwnedConstruct;
/*    */ 
/*    */ public class AIOwnerHurtByTarget extends EntityAITarget
/*    */ {
/*    */   EntityOwnedConstruct theDefendingTameable;
/*    */   EntityLivingBase theOwnerAttacker;
/*    */   private int field_142051_e;
/*    */   
/*    */   public AIOwnerHurtByTarget(EntityOwnedConstruct p_i1667_1_)
/*    */   {
/* 15 */     super(p_i1667_1_, false);
/* 16 */     this.theDefendingTameable = p_i1667_1_;
/* 17 */     setMutexBits(1);
/*    */   }
/*    */   
/*    */   public boolean shouldExecute()
/*    */   {
/* 22 */     if (!this.theDefendingTameable.isOwned())
/*    */     {
/* 24 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 28 */     EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwnerEntity();
/*    */     
/* 30 */     if (entitylivingbase == null)
/*    */     {
/* 32 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 36 */     this.theOwnerAttacker = entitylivingbase.getAITarget();
/* 37 */     int i = entitylivingbase.getRevengeTimer();
/* 38 */     return (i != this.field_142051_e) && (isSuitableTarget(this.theOwnerAttacker, false));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 45 */     this.taskOwner.setAttackTarget(this.theOwnerAttacker);
/* 46 */     EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwnerEntity();
/*    */     
/* 48 */     if (entitylivingbase != null)
/*    */     {
/* 50 */       this.field_142051_e = entitylivingbase.getRevengeTimer();
/*    */     }
/*    */     
/* 53 */     super.startExecuting();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\AIOwnerHurtByTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */