/*    */ package thaumcraft.common.entities.ai.combat;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.ai.EntityAITarget;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.entities.monster.cult.EntityCultistCleric;
/*    */ 
/*    */ public class AICultistHurtByTarget extends EntityAITarget
/*    */ {
/*    */   boolean entityCallsForHelp;
/*    */   private int field_142052_b;
/*    */   
/*    */   public AICultistHurtByTarget(EntityCreature p_i1660_1_, boolean p_i1660_2_)
/*    */   {
/* 19 */     super(p_i1660_1_, false);
/* 20 */     this.entityCallsForHelp = p_i1660_2_;
/* 21 */     setMutexBits(1);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 29 */     int i = this.taskOwner.getRevengeTimer();
/* 30 */     return (i != this.field_142052_b) && (isSuitableTarget(this.taskOwner.getAITarget(), false));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 38 */     this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
/* 39 */     this.field_142052_b = this.taskOwner.getRevengeTimer();
/*    */     
/* 41 */     if (this.entityCallsForHelp)
/*    */     {
/* 43 */       double d0 = getTargetDistance() * 2.0D;
/* 44 */       List list = this.taskOwner.worldObj.getEntitiesWithinAABB(thaumcraft.common.entities.monster.cult.EntityCultist.class, AxisAlignedBB.fromBounds(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D).expand(d0, 10.0D, d0));
/*    */       
/*    */ 
/* 47 */       Iterator iterator = list.iterator();
/*    */       
/* 49 */       while (iterator.hasNext())
/*    */       {
/* 51 */         EntityCreature entitycreature = (EntityCreature)iterator.next();
/*    */         
/* 53 */         if ((this.taskOwner != entitycreature) && (entitycreature.getAttackTarget() == null) && (!entitycreature.isOnSameTeam(this.taskOwner.getAITarget())))
/*    */         {
/*    */ 
/* 56 */           if (((entitycreature instanceof EntityCultistCleric)) && (((EntityCultistCleric)entitycreature).getIsRitualist()))
/*    */           {
/* 58 */             ((EntityCultistCleric)entitycreature).rage += 1;
/* 59 */             this.taskOwner.worldObj.setEntityState(entitycreature, (byte)19);
/* 60 */             if (this.taskOwner.worldObj.rand.nextInt(3) == 0) {
/* 61 */               ((EntityCultistCleric)entitycreature).setIsRitualist(false);
/* 62 */               entitycreature.setAttackTarget(this.taskOwner.getAITarget());
/*    */             }
/*    */           } else {
/* 65 */             entitycreature.setAttackTarget(this.taskOwner.getAITarget());
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 70 */     super.startExecuting();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\ai\combat\AICultistHurtByTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */