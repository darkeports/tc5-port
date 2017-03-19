/*     */ package thaumcraft.common.entities.construct.golem.ai;
/*     */ 
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.entity.ai.EntitySenses;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public class AIArrowAttack
/*     */   extends EntityAIArrowAttack
/*     */ {
/*     */   private final EntityLiving entityHost;
/*     */   private final IRangedAttackMob rangedAttackEntityHost;
/*     */   private int rangedAttackTime;
/*     */   private double entityMoveSpeed;
/*     */   private int field_75318_f;
/*     */   private int field_96561_g;
/*     */   private int maxRangedAttackTime;
/*     */   private float field_96562_i;
/*     */   private float maxAttackDistance;
/*     */   
/*     */   public AIArrowAttack(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn)
/*     */   {
/*  29 */     super(attacker, movespeed, p_i1650_4_, maxAttackTime, maxAttackDistanceIn);
/*  30 */     this.rangedAttackTime = -1;
/*     */     
/*  32 */     if (!(attacker instanceof EntityLivingBase))
/*     */     {
/*  34 */       throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
/*     */     }
/*     */     
/*     */ 
/*  38 */     this.rangedAttackEntityHost = attacker;
/*  39 */     this.entityHost = ((EntityLiving)attacker);
/*  40 */     this.entityMoveSpeed = movespeed;
/*  41 */     this.field_96561_g = p_i1650_4_;
/*  42 */     this.maxRangedAttackTime = maxAttackTime;
/*  43 */     this.field_96562_i = maxAttackDistanceIn;
/*  44 */     this.maxAttackDistance = (maxAttackDistanceIn * maxAttackDistanceIn);
/*  45 */     setMutexBits(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  53 */     if (this.entityHost.getAttackTarget() == null)
/*     */     {
/*  55 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  59 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean continueExecuting()
/*     */   {
/*  66 */     return (shouldExecute()) || (!this.entityHost.getNavigator().noPath());
/*     */   }
/*     */   
/*     */ 
/*     */   public void resetTask()
/*     */   {
/*  72 */     this.field_75318_f = 0;
/*  73 */     this.rangedAttackTime = -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateTask()
/*     */   {
/*  79 */     if (this.entityHost.getAttackTarget() == null) return;
/*  80 */     double d0 = this.entityHost.getDistanceSq(this.entityHost.getAttackTarget().posX, this.entityHost.getAttackTarget().getEntityBoundingBox().minY, this.entityHost.getAttackTarget().posZ);
/*  81 */     boolean flag = this.entityHost.getEntitySenses().canSee(this.entityHost.getAttackTarget());
/*     */     
/*  83 */     if (flag)
/*     */     {
/*  85 */       this.field_75318_f += 1;
/*     */     }
/*     */     else
/*     */     {
/*  89 */       this.field_75318_f = 0;
/*     */     }
/*     */     
/*  92 */     if ((d0 <= this.maxAttackDistance) && (this.field_75318_f >= 20))
/*     */     {
/*  94 */       this.entityHost.getNavigator().clearPathEntity();
/*     */     }
/*     */     else
/*     */     {
/*  98 */       this.entityHost.getNavigator().tryMoveToEntityLiving(this.entityHost.getAttackTarget(), this.entityMoveSpeed);
/*     */     }
/*     */     
/* 101 */     this.entityHost.getLookHelper().setLookPositionWithEntity(this.entityHost.getAttackTarget(), 10.0F, 30.0F);
/*     */     
/* 103 */     if (--this.rangedAttackTime == 0)
/*     */     {
/* 105 */       if ((d0 > this.maxAttackDistance) || (!flag))
/*     */       {
/* 107 */         return;
/*     */       }
/*     */       
/* 110 */       float f = MathHelper.sqrt_double(d0) / this.field_96562_i;
/* 111 */       float lvt_5_1_ = MathHelper.clamp_float(f, 0.1F, 1.0F);
/* 112 */       this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.entityHost.getAttackTarget(), lvt_5_1_);
/* 113 */       this.rangedAttackTime = MathHelper.floor_float(f * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
/*     */     }
/* 115 */     else if (this.rangedAttackTime < 0)
/*     */     {
/* 117 */       float f2 = MathHelper.sqrt_double(d0) / this.field_96562_i;
/* 118 */       this.rangedAttackTime = MathHelper.floor_float(f2 * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\AIArrowAttack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */