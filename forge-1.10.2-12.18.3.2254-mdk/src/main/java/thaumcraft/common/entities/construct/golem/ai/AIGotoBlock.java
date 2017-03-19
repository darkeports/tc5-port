/*     */ package thaumcraft.common.entities.construct.golem.ai;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.golems.tasks.Task;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.construct.golem.EntityThaumcraftGolem;
/*     */ 
/*     */ public class AIGotoBlock extends AIGoto
/*     */ {
/*     */   public AIGotoBlock(EntityThaumcraftGolem g)
/*     */   {
/*  20 */     super(g, (byte)0);
/*     */   }
/*     */   
/*     */   public void updateTask()
/*     */   {
/*  25 */     super.updateTask();
/*  26 */     if (this.golem.getLookHelper() != null) {
/*  27 */       this.golem.getLookHelper().setLookPosition(this.golem.getTask().getPos().getX() + 0.5D, this.golem.getTask().getPos().getY() + 0.5D, this.golem.getTask().getPos().getZ() + 0.5D, 10.0F, this.golem.getVerticalFaceSpeed());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void moveTo()
/*     */   {
/*  34 */     if (this.targetBlock != null) {
/*  35 */       this.golem.getNavigator().tryMoveToXYZ(this.targetBlock.getX() + 0.5D, this.targetBlock.getY() + 0.5D, this.targetBlock.getZ() + 0.5D, this.golem.getGolemMoveSpeed());
/*     */     } else {
/*  37 */       this.golem.getNavigator().tryMoveToXYZ(this.golem.getTask().getPos().getX() + 0.5D, this.golem.getTask().getPos().getY() + 0.5D, this.golem.getTask().getPos().getZ() + 0.5D, this.golem.getGolemMoveSpeed());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean findDestination()
/*     */   {
/*  44 */     java.util.ArrayList<Task> list = thaumcraft.common.entities.construct.golem.tasks.TaskHandler.getBlockTasksSorted(this.golem.worldObj.provider.getDimensionId(), this.golem.getUniqueID(), this.golem);
/*     */     
/*  46 */     for (Task ticket : list) {
/*  47 */       if ((areGolemTagsValidForTask(ticket)) && (ticket.canGolemPerformTask(this.golem)) && (this.golem.isWithinHomeDistanceFromPosition(ticket.getPos())) && (isValidDestination(this.golem.worldObj, ticket.getPos())) && (canEasilyReach(ticket.getPos())))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*  52 */         this.targetBlock = getAdjacentSpace(ticket.getPos());
/*  53 */         this.golem.setTask(ticket);
/*  54 */         this.golem.getTask().setReserved(true);
/*  55 */         if (Config.showGolemEmotes) { this.golem.worldObj.setEntityState(this.golem, (byte)5);
/*     */         }
/*  57 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  61 */     return false;
/*     */   }
/*     */   
/*     */   private BlockPos getAdjacentSpace(BlockPos pos) {
/*  65 */     double d = Double.MAX_VALUE;
/*  66 */     BlockPos closest = null;
/*  67 */     for (EnumFacing face : EnumFacing.HORIZONTALS) {
/*  68 */       Block block = this.golem.worldObj.getBlockState(pos.offset(face)).getBlock();
/*  69 */       if (!block.getMaterial().blocksMovement()) {
/*  70 */         double dist = pos.offset(face).distanceSqToCenter(this.golem.posX, this.golem.posY, this.golem.posZ);
/*  71 */         if (dist < d) {
/*  72 */           closest = pos.offset(face);
/*  73 */           d = dist;
/*     */         }
/*     */       }
/*     */     }
/*  77 */     return closest;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean canEasilyReach(BlockPos pos)
/*     */   {
/*  83 */     if (this.golem.getDistanceSqToCenter(pos) < this.minDist) { return true;
/*     */     }
/*  85 */     PathEntity pathentity = this.golem.getNavigator().getPathToXYZ(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
/*     */     
/*  87 */     if (pathentity == null)
/*     */     {
/*  89 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  93 */     PathPoint pathpoint = pathentity.getFinalPathPoint();
/*     */     
/*  95 */     if (pathpoint == null)
/*     */     {
/*  97 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 101 */     int i = pathpoint.xCoord - MathHelper.floor_double(pos.getX());
/* 102 */     int j = pathpoint.zCoord - MathHelper.floor_double(pos.getZ());
/* 103 */     int k = pathpoint.yCoord - MathHelper.floor_double(pos.getY());
/* 104 */     return i * i + j * j + k * k < 2.25D;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\AIGotoBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */