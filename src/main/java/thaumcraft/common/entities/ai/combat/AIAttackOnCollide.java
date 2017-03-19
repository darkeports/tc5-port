/*     */ package thaumcraft.common.entities.ai.combat;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.entity.ai.EntitySenses;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.entities.IEntityReach;
/*     */ 
/*     */ public class AIAttackOnCollide extends EntityAIBase
/*     */ {
/*     */   World worldObj;
/*     */   EntityCreature attacker;
/*     */   int attackTick;
/*     */   double speedTowardsTarget;
/*     */   boolean longMemory;
/*     */   PathEntity entityPathEntity;
/*     */   Class classTarget;
/*     */   private int field_75445_i;
/*     */   private double field_151497_i;
/*     */   private double field_151495_j;
/*     */   private double field_151496_k;
/*     */   private static final String __OBFID = "CL_00001595";
/*     */   private int failedPathFindingPenalty;
/*     */   
/*     */   public AIAttackOnCollide(EntityCreature p_i1635_1_, Class p_i1635_2_, double p_i1635_3_, boolean p_i1635_5_)
/*     */   {
/*  35 */     this(p_i1635_1_, p_i1635_3_, p_i1635_5_);
/*  36 */     this.classTarget = p_i1635_2_;
/*     */   }
/*     */   
/*     */   public AIAttackOnCollide(EntityCreature p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_)
/*     */   {
/*  41 */     this.attacker = p_i1636_1_;
/*  42 */     this.worldObj = p_i1636_1_.worldObj;
/*  43 */     this.speedTowardsTarget = p_i1636_2_;
/*  44 */     this.longMemory = p_i1636_4_;
/*  45 */     setMutexBits(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  53 */     EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
/*     */     
/*  55 */     if (entitylivingbase == null)
/*     */     {
/*  57 */       return false;
/*     */     }
/*  59 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/*  61 */       return false;
/*     */     }
/*  63 */     if ((this.classTarget != null) && (!this.classTarget.isAssignableFrom(entitylivingbase.getClass())))
/*     */     {
/*  65 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  69 */     if (--this.field_75445_i <= 0)
/*     */     {
/*  71 */       this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
/*  72 */       this.field_75445_i = (4 + this.attacker.getRNG().nextInt(7));
/*  73 */       return this.entityPathEntity != null;
/*     */     }
/*     */     
/*     */ 
/*  77 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean continueExecuting()
/*     */   {
/*  87 */     EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
/*  88 */     return !this.longMemory ? false : !this.attacker.getNavigator().noPath() ? true : !entitylivingbase.isEntityAlive() ? false : entitylivingbase == null ? false : this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/*  98 */     this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
/*  99 */     this.field_75445_i = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resetTask()
/*     */   {
/* 107 */     this.attacker.getNavigator().clearPathEntity();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateTask()
/*     */   {
/* 115 */     EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
/* 116 */     this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
/* 117 */     double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
/* 118 */     double d1 = this.attacker.width * 2.0F * this.attacker.width * 2.0F + entitylivingbase.width;
/* 119 */     if ((this.attacker instanceof IEntityReach)) {
/* 120 */       d1 += ((IEntityReach)this.attacker).getReach() * ((IEntityReach)this.attacker).getReach();
/*     */     }
/* 122 */     this.field_75445_i -= 1;
/* 123 */     if (this.attackTick > 0) { this.attackTick -= 1;
/*     */     }
/* 125 */     if (((this.longMemory) || (this.attacker.getEntitySenses().canSee(entitylivingbase))) && (this.field_75445_i <= 0) && (((this.field_151497_i == 0.0D) && (this.field_151495_j == 0.0D) && (this.field_151496_k == 0.0D)) || (entitylivingbase.getDistanceSq(this.field_151497_i, this.field_151495_j, this.field_151496_k) >= 1.0D) || (this.attacker.getRNG().nextFloat() < 0.05F)))
/*     */     {
/* 127 */       this.field_151497_i = entitylivingbase.posX;
/* 128 */       this.field_151495_j = entitylivingbase.getEntityBoundingBox().minY;
/* 129 */       this.field_151496_k = entitylivingbase.posZ;
/* 130 */       this.field_75445_i = (this.failedPathFindingPenalty + 4 + this.attacker.getRNG().nextInt(7));
/*     */       
/* 132 */       if (this.attacker.getNavigator().getPath() != null)
/*     */       {
/* 134 */         PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
/* 135 */         if ((finalPathPoint != null) && (entitylivingbase.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1.0D))
/*     */         {
/* 137 */           this.failedPathFindingPenalty = 0;
/*     */         }
/*     */         else
/*     */         {
/* 141 */           this.failedPathFindingPenalty += 10;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 146 */         this.failedPathFindingPenalty += 10;
/*     */       }
/*     */       
/* 149 */       if (d0 > 1024.0D)
/*     */       {
/* 151 */         this.field_75445_i += 10;
/*     */       }
/* 153 */       else if (d0 > 256.0D)
/*     */       {
/* 155 */         this.field_75445_i += 5;
/*     */       }
/*     */       
/* 158 */       if (!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget))
/*     */       {
/* 160 */         this.field_75445_i += 15;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 166 */     if ((d0 <= d1) && (this.attackTick <= 0))
/*     */     {
/* 168 */       this.attackTick = 10;
/*     */       
/* 170 */       if (this.attacker.getHeldItem() != null)
/*     */       {
/* 172 */         this.attacker.swingItem();
/*     */       }
/*     */       
/* 175 */       this.attacker.attackEntityAsMob(entitylivingbase);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\ai\combat\AIAttackOnCollide.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */