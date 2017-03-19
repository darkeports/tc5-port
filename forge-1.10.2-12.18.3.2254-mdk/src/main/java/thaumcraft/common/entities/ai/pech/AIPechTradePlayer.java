/*    */ package thaumcraft.common.entities.ai.pech;
/*    */ 
/*    */ import net.minecraft.entity.ai.EntityAIBase;
/*    */ import net.minecraft.pathfinding.PathNavigate;
/*    */ import thaumcraft.common.entities.monster.EntityPech;
/*    */ 
/*    */ public class AIPechTradePlayer
/*    */   extends EntityAIBase
/*    */ {
/*    */   private EntityPech villager;
/*    */   
/*    */   public AIPechTradePlayer(EntityPech par1EntityVillager)
/*    */   {
/* 14 */     this.villager = par1EntityVillager;
/* 15 */     setMutexBits(5);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 23 */     if (!this.villager.isEntityAlive())
/*    */     {
/* 25 */       return false;
/*    */     }
/* 27 */     if (this.villager.isInWater())
/*    */     {
/* 29 */       return false;
/*    */     }
/* 31 */     if (!this.villager.isTamed())
/*    */     {
/* 33 */       return false;
/*    */     }
/* 35 */     if (!this.villager.onGround)
/*    */     {
/* 37 */       return false;
/*    */     }
/* 39 */     if (this.villager.velocityChanged)
/*    */     {
/* 41 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 45 */     return this.villager.trading;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 54 */     this.villager.getNavigator().clearPathEntity();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void resetTask()
/*    */   {
/* 62 */     this.villager.trading = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\ai\pech\AIPechTradePlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */