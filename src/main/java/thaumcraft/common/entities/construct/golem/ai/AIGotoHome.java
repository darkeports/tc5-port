/*    */ package thaumcraft.common.entities.construct.golem.ai;
/*    */ 
/*    */ import net.minecraft.entity.ai.RandomPositionGenerator;
/*    */ import net.minecraft.pathfinding.PathNavigate;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.Vec3;
/*    */ import thaumcraft.common.entities.construct.golem.EntityThaumcraftGolem;
/*    */ 
/*    */ public class AIGotoHome extends net.minecraft.entity.ai.EntityAIBase
/*    */ {
/*    */   protected EntityThaumcraftGolem golem;
/*    */   private double movePosX;
/*    */   private double movePosY;
/*    */   private double movePosZ;
/* 15 */   protected int idleCounter = 10;
/*    */   
/*    */   public AIGotoHome(EntityThaumcraftGolem g)
/*    */   {
/* 19 */     this.golem = g;
/* 20 */     setMutexBits(5);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 26 */     if (this.idleCounter > 0)
/*    */     {
/* 28 */       this.idleCounter -= 1;
/* 29 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 33 */     this.idleCounter = 50;
/* 34 */     double dd = this.golem.getDistanceSqToCenter(this.golem.getHomePosition());
/* 35 */     if (dd < 9.0D) return false;
/* 36 */     if (dd > 256.0D) {
/* 37 */       Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.golem, 16, 7, new Vec3(this.golem.getHomePosition().getX(), this.golem.getHomePosition().getY(), this.golem.getHomePosition().getZ()));
/*    */       
/*    */ 
/* 40 */       if (vec3 == null)
/*    */       {
/* 42 */         return false;
/*    */       }
/*    */       
/*    */ 
/* 46 */       this.movePosX = vec3.xCoord;
/* 47 */       this.movePosY = vec3.yCoord;
/* 48 */       this.movePosZ = vec3.zCoord;
/* 49 */       return true;
/*    */     }
/*    */     
/* 52 */     this.movePosX = this.golem.getHomePosition().getX();
/* 53 */     this.movePosY = this.golem.getHomePosition().getY();
/* 54 */     this.movePosZ = this.golem.getHomePosition().getZ();
/* 55 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 63 */     this.golem.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.golem.getGolemMoveSpeed());
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 69 */     return (this.golem.getTask() != null) && (!this.golem.getNavigator().noPath()) && (this.golem.getDistanceSqToCenter(this.golem.getHomePosition()) > 3.0D);
/*    */   }
/*    */   
/*    */ 
/*    */   public void resetTask()
/*    */   {
/* 75 */     this.idleCounter = 50;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\AIGotoHome.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */