/*     */ package thaumcraft.common.entities.construct.golem.ai;
/*     */ 
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.ai.RandomPositionGenerator;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import thaumcraft.api.golems.GolemHelper;
/*     */ import thaumcraft.api.golems.IGolemProperties;
/*     */ import thaumcraft.api.golems.seals.ISeal;
/*     */ import thaumcraft.api.golems.seals.ISealEntity;
/*     */ import thaumcraft.api.golems.tasks.Task;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.construct.golem.EntityThaumcraftGolem;
/*     */ import thaumcraft.common.entities.construct.golem.tasks.TaskHandler;
/*     */ 
/*     */ public abstract class AIGoto extends net.minecraft.entity.ai.EntityAIBase
/*     */ {
/*     */   protected EntityThaumcraftGolem golem;
/*  22 */   protected int taskCounter = -1;
/*  23 */   protected byte type = 0;
/*     */   protected int cooldown;
/*  25 */   protected double minDist = 4.0D;
/*     */   
/*     */   private BlockPos prevRamble;
/*     */   protected BlockPos targetBlock;
/*     */   
/*     */   public AIGoto(EntityThaumcraftGolem g, byte type)
/*     */   {
/*  32 */     this.golem = g;
/*  33 */     this.type = type;
/*  34 */     setMutexBits(5);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  40 */     if (this.cooldown > 0)
/*     */     {
/*  42 */       this.cooldown -= 1;
/*  43 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  47 */     this.cooldown = 5;
/*     */     
/*  49 */     if ((this.golem.getTask() != null) && (!this.golem.getTask().isSuspended())) {
/*  50 */       return false;
/*     */     }
/*     */     
/*  53 */     this.targetBlock = null;
/*  54 */     boolean start = findDestination();
/*     */     
/*  56 */     if ((start) && (this.golem.getTask() != null) && 
/*  57 */       (this.golem.getTask().getSealPos() != null)) {
/*  58 */       ISealEntity se = GolemHelper.getSealEntity(this.golem.worldObj.provider.getDimensionId(), this.golem.getTask().getSealPos());
/*  59 */       if (se != null) {
/*  60 */         se.getSeal().onTaskStarted(this.golem.worldObj, this.golem, this.golem.getTask());
/*     */       }
/*     */     }
/*     */     
/*  64 */     return start;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/*  72 */     moveTo();
/*  73 */     this.taskCounter = 0;
/*     */   }
/*     */   
/*     */ 
/*     */   protected abstract void moveTo();
/*     */   
/*     */   public boolean continueExecuting()
/*     */   {
/*  81 */     return (this.taskCounter >= 0) && (this.taskCounter <= 1000) && (this.golem.getTask() != null) && (!this.golem.getTask().isSuspended()) && (isValidDestination(this.golem.worldObj, this.golem.getTask().getPos()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateTask()
/*     */   {
/*  90 */     if (this.golem.getTask() == null) { return;
/*     */     }
/*  92 */     if (this.pause-- <= 0)
/*     */     {
/*  94 */       double dist = this.golem.getTask().getType() == 0 ? this.golem.getDistanceSqToCenter(this.targetBlock == null ? this.golem.getTask().getPos() : this.targetBlock) : this.golem.getDistanceSqToEntity(this.golem.getTask().getEntity());
/*     */       
/*     */ 
/*     */ 
/*  98 */       if (dist > this.minDist)
/*     */       {
/* 100 */         this.golem.getTask().setCompletion(false);
/* 101 */         this.taskCounter += 1;
/*     */         
/* 103 */         if (this.taskCounter % 40 == 0)
/*     */         {
/* 105 */           if ((this.prevRamble != null) && (this.prevRamble.equals(this.golem.getPosition()))) {
/* 106 */             Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.golem, 6, 4, new Vec3(this.golem.getTask().getPos().getX(), this.golem.getTask().getPos().getY(), this.golem.getTask().getPos().getZ()));
/* 107 */             if (vec3 != null) {
/* 108 */               this.golem.getNavigator().tryMoveToXYZ(vec3.xCoord + 0.5D, vec3.yCoord + 0.5D, vec3.zCoord + 0.5D, this.golem.getGolemMoveSpeed());
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 113 */             moveTo();
/*     */           }
/* 115 */           this.prevRamble = this.golem.getPosition();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 120 */         TaskHandler.completeTask(this.golem.getTask(), this.golem);
/* 121 */         if ((this.golem.getTask() != null) && (this.golem.getTask().isCompleted())) {
/* 122 */           if (this.taskCounter >= 0) {
/* 123 */             this.taskCounter = 0;
/*     */           }
/* 125 */           this.pause = 0;
/*     */         } else {
/* 127 */           this.pause = 10;
/* 128 */           this.taskCounter += 1;
/*     */         }
/* 130 */         this.taskCounter -= 1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 135 */   int pause = 0;
/*     */   
/*     */   public void resetTask()
/*     */   {
/* 139 */     if (this.golem.getTask() != null) {
/* 140 */       if ((!this.golem.getTask().isCompleted()) && (this.golem.getTask().isReserved()) && 
/* 141 */         (Config.showGolemEmotes)) { this.golem.worldObj.setEntityState(this.golem, (byte)6);
/*     */       }
/* 143 */       if ((this.golem.getTask().isCompleted()) && (!this.golem.getTask().isSuspended())) {
/* 144 */         this.golem.getTask().setSuspended(true);
/*     */       }
/* 146 */       this.golem.getTask().setReserved(false);
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract boolean findDestination();
/*     */   
/*     */   protected boolean isValidDestination(World world, BlockPos pos) {
/* 153 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean areGolemTagsValidForTask(Task ticket) {
/* 157 */     ISealEntity se = thaumcraft.common.entities.construct.golem.seals.SealHandler.getSealEntity(this.golem.worldObj.provider.getDimensionId(), ticket.getSealPos());
/* 158 */     if ((se != null) && (se.getSeal() != null)) {
/* 159 */       if ((se.isLocked()) && (!this.golem.getOwnerId().equals(se.getOwner()))) return false;
/* 160 */       if ((se.getSeal().getRequiredTags() != null) && (!this.golem.getProperties().getTraits().containsAll(java.util.Arrays.asList(se.getSeal().getRequiredTags()))))
/* 161 */         return false;
/* 162 */       if (se.getSeal().getForbiddenTags() != null) {
/* 163 */         for (thaumcraft.api.golems.EnumGolemTrait tag : se.getSeal().getForbiddenTags()) {
/* 164 */           if (this.golem.getProperties().getTraits().contains(tag)) return false;
/*     */         }
/*     */       }
/*     */     } else {
/* 168 */       return true;
/*     */     }
/* 170 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\AIGoto.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */