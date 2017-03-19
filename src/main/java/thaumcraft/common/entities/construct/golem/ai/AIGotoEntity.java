/*    */ package thaumcraft.common.entities.construct.golem.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.ai.EntityLookHelper;
/*    */ import net.minecraft.pathfinding.PathEntity;
/*    */ import net.minecraft.pathfinding.PathNavigate;
/*    */ import net.minecraft.pathfinding.PathPoint;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.golems.tasks.Task;
/*    */ import thaumcraft.common.config.Config;
/*    */ import thaumcraft.common.entities.construct.golem.EntityThaumcraftGolem;
/*    */ 
/*    */ public class AIGotoEntity extends AIGoto
/*    */ {
/*    */   public AIGotoEntity(EntityThaumcraftGolem g)
/*    */   {
/* 18 */     super(g, (byte)1);
/*    */   }
/*    */   
/*    */   public void updateTask()
/*    */   {
/* 23 */     super.updateTask();
/* 24 */     if (this.golem.getLookHelper() != null) {
/* 25 */       this.golem.getLookHelper().setLookPositionWithEntity(this.golem.getTask().getEntity(), 10.0F, this.golem.getVerticalFaceSpeed());
/*    */     }
/*    */   }
/*    */   
/*    */   protected void moveTo() {
/* 30 */     this.golem.getNavigator().tryMoveToEntityLiving(this.golem.getTask().getEntity(), this.golem.getGolemMoveSpeed());
/*    */   }
/*    */   
/*    */ 
/*    */   protected boolean findDestination()
/*    */   {
/* 36 */     java.util.ArrayList<Task> list = thaumcraft.common.entities.construct.golem.tasks.TaskHandler.getEntityTasksSorted(this.golem.worldObj.provider.getDimensionId(), this.golem.getUniqueID(), this.golem);
/*    */     
/* 38 */     for (Task ticket : list) {
/* 39 */       if ((areGolemTagsValidForTask(ticket)) && (ticket.canGolemPerformTask(this.golem)) && (this.golem.isWithinHomeDistanceFromPosition(ticket.getEntity().getPosition())) && (isValidDestination(this.golem.worldObj, ticket.getEntity().getPosition())) && (canEasilyReach(ticket.getEntity())))
/*    */       {
/*    */ 
/*    */ 
/*    */ 
/* 44 */         this.golem.setTask(ticket);
/* 45 */         this.golem.getTask().setReserved(true);
/* 46 */         this.minDist = (3.5D + this.golem.getTask().getEntity().width / 2.0F * (this.golem.getTask().getEntity().width / 2.0F));
/* 47 */         if (Config.showGolemEmotes) this.golem.worldObj.setEntityState(this.golem, (byte)5);
/* 48 */         return true;
/*    */       }
/*    */     }
/*    */     
/* 52 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private boolean canEasilyReach(Entity e)
/*    */   {
/* 59 */     if (this.golem.getDistanceSqToEntity(e) < this.minDist) { return true;
/*    */     }
/* 61 */     PathEntity pathentity = this.golem.getNavigator().getPathToEntityLiving(e);
/*    */     
/* 63 */     if (pathentity == null)
/*    */     {
/* 65 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 69 */     PathPoint pathpoint = pathentity.getFinalPathPoint();
/*    */     
/* 71 */     if (pathpoint == null)
/*    */     {
/* 73 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 77 */     int i = pathpoint.xCoord - MathHelper.floor_double(e.posX);
/* 78 */     int j = pathpoint.zCoord - MathHelper.floor_double(e.posZ);
/* 79 */     return i * i + j * j < this.minDist;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\AIGotoEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */