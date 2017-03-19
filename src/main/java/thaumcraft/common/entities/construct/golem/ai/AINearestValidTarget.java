/*     */ package thaumcraft.common.entities.construct.golem.ai;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter;
/*     */ import net.minecraft.entity.ai.EntityAITarget;
/*     */ import net.minecraft.entity.ai.EntitySenses;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class AINearestValidTarget extends EntityAITarget
/*     */ {
/*     */   protected final Class targetClass;
/*     */   private final int targetChance;
/*     */   protected final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
/*     */   protected Predicate targetEntitySelector;
/*     */   protected EntityLivingBase targetEntity;
/*     */   private int targetUnseenTicks;
/*     */   
/*     */   public AINearestValidTarget(EntityCreature p_i45878_1_, Class p_i45878_2_, boolean p_i45878_3_)
/*     */   {
/*  34 */     this(p_i45878_1_, p_i45878_2_, p_i45878_3_, false);
/*     */   }
/*     */   
/*     */   public AINearestValidTarget(EntityCreature p_i45879_1_, Class p_i45879_2_, boolean p_i45879_3_, boolean p_i45879_4_)
/*     */   {
/*  39 */     this(p_i45879_1_, p_i45879_2_, 10, p_i45879_3_, p_i45879_4_, (Predicate)null);
/*     */   }
/*     */   
/*     */   public AINearestValidTarget(EntityCreature p_i45880_1_, Class p_i45880_2_, int p_i45880_3_, boolean p_i45880_4_, boolean p_i45880_5_, final Predicate tselector)
/*     */   {
/*  44 */     super(p_i45880_1_, p_i45880_4_, p_i45880_5_);
/*  45 */     this.targetClass = p_i45880_2_;
/*  46 */     this.targetChance = p_i45880_3_;
/*  47 */     this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(p_i45880_1_);
/*  48 */     setMutexBits(1);
/*  49 */     this.targetEntitySelector = new Predicate()
/*     */     {
/*     */       private static final String __OBFID = "CL_00001621";
/*     */       
/*     */       public boolean applySelection(EntityLivingBase entity) {
/*  54 */         if ((tselector != null) && (!tselector.apply(entity)))
/*     */         {
/*  56 */           return false;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*  61 */         if ((entity instanceof EntityPlayer))
/*     */         {
/*  63 */           double d0 = AINearestValidTarget.this.getTargetDistance();
/*     */           
/*  65 */           if (entity.isSneaking())
/*     */           {
/*  67 */             d0 *= 0.800000011920929D;
/*     */           }
/*     */           
/*  70 */           if (entity.isInvisible())
/*     */           {
/*  72 */             float f = ((EntityPlayer)entity).getArmorVisibility();
/*     */             
/*  74 */             if (f < 0.1F)
/*     */             {
/*  76 */               f = 0.1F;
/*     */             }
/*     */             
/*  79 */             d0 *= 0.7F * f;
/*     */           }
/*     */           
/*  82 */           if (entity.getDistanceToEntity(AINearestValidTarget.this.taskOwner) > d0)
/*     */           {
/*  84 */             return false;
/*     */           }
/*     */         }
/*     */         
/*  88 */         return AINearestValidTarget.this.isSuitableTarget(entity, false);
/*     */       }
/*     */       
/*     */       public boolean apply(Object p_apply_1_)
/*     */       {
/*  93 */         return applySelection((EntityLivingBase)p_apply_1_);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean continueExecuting()
/*     */   {
/* 101 */     EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();
/* 102 */     if (entitylivingbase == null)
/*     */     {
/* 104 */       return false;
/*     */     }
/* 106 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/* 108 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 112 */     Team team = this.taskOwner.getTeam();
/* 113 */     Team team1 = entitylivingbase.getTeam();
/*     */     
/* 115 */     if ((team != null) && (team1 == team) && (!((ITargets)this.taskOwner).getTargetFriendly()))
/*     */     {
/* 117 */       return false;
/*     */     }
/*     */     
/* 120 */     if ((team != null) && (team1 != team) && (((ITargets)this.taskOwner).getTargetFriendly()))
/*     */     {
/* 122 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 126 */     double d0 = getTargetDistance();
/*     */     
/* 128 */     if (this.taskOwner.getDistanceSqToEntity(entitylivingbase) > d0 * d0)
/*     */     {
/* 130 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 134 */     if (this.shouldCheckSight)
/*     */     {
/* 136 */       if (this.taskOwner.getEntitySenses().canSee(entitylivingbase))
/*     */       {
/* 138 */         this.targetUnseenTicks = 0;
/*     */       }
/* 140 */       else if (++this.targetUnseenTicks > 60)
/*     */       {
/* 142 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 146 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isSuitableTarget(EntityLivingBase p_75296_1_, boolean p_75296_2_)
/*     */   {
/* 155 */     if (!isGoodTarget(this.taskOwner, p_75296_1_, p_75296_2_, this.shouldCheckSight))
/*     */     {
/* 157 */       return false;
/*     */     }
/* 159 */     if (!this.taskOwner.isWithinHomeDistanceFromPosition(new net.minecraft.util.BlockPos(p_75296_1_)))
/*     */     {
/* 161 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 165 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean isGoodTarget(EntityLiving attacker, EntityLivingBase posTar, boolean p_179445_2_, boolean checkSight)
/*     */   {
/* 172 */     if (posTar == null)
/*     */     {
/* 174 */       return false;
/*     */     }
/* 176 */     if (posTar == attacker)
/*     */     {
/* 178 */       return false;
/*     */     }
/* 180 */     if (!posTar.isEntityAlive())
/*     */     {
/* 182 */       return false;
/*     */     }
/* 184 */     if (!attacker.canAttackClass(posTar.getClass()))
/*     */     {
/* 186 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 190 */     Team team = attacker.getTeam();
/* 191 */     Team team1 = posTar.getTeam();
/* 192 */     if ((team != null) && (team1 == team) && (!((ITargets)attacker).getTargetFriendly()))
/*     */     {
/* 194 */       return false;
/*     */     }
/*     */     
/* 197 */     if ((team != null) && (team1 != team) && (((ITargets)attacker).getTargetFriendly()))
/*     */     {
/* 199 */       return false;
/*     */     }
/*     */     
/* 202 */     if (((attacker instanceof IEntityOwnable)) && (org.apache.commons.lang3.StringUtils.isNotEmpty(((IEntityOwnable)attacker).getOwnerId())))
/*     */     {
/* 204 */       if (((posTar instanceof IEntityOwnable)) && (((IEntityOwnable)attacker).getOwnerId().equals(((IEntityOwnable)posTar).getOwnerId())) && (!((ITargets)attacker).getTargetFriendly()))
/*     */       {
/*     */ 
/*     */ 
/* 208 */         return false;
/*     */       }
/*     */       
/* 211 */       if ((!(posTar instanceof IEntityOwnable)) && (!(posTar instanceof EntityPlayer)) && (((ITargets)attacker).getTargetFriendly())) {
/* 212 */         return false;
/*     */       }
/*     */       
/* 215 */       if (((posTar instanceof IEntityOwnable)) && (!((IEntityOwnable)attacker).getOwnerId().equals(((IEntityOwnable)posTar).getOwnerId())) && (((ITargets)attacker).getTargetFriendly()))
/*     */       {
/*     */ 
/*     */ 
/* 219 */         return false;
/*     */       }
/*     */       
/* 222 */       if ((posTar == ((IEntityOwnable)attacker).getOwner()) && (!((ITargets)attacker).getTargetFriendly()))
/*     */       {
/* 224 */         return false;
/*     */       }
/*     */       
/*     */     }
/* 228 */     else if (((posTar instanceof EntityPlayer)) && (!p_179445_2_) && (((EntityPlayer)posTar).capabilities.disableDamage) && (!((ITargets)attacker).getTargetFriendly()))
/*     */     {
/*     */ 
/* 231 */       return false;
/*     */     }
/*     */     
/* 234 */     return (!checkSight) || (attacker.getEntitySenses().canSee(posTar));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/* 241 */     if ((this.targetChance > 0) && (this.taskOwner.getRNG().nextInt(this.targetChance) != 0))
/*     */     {
/* 243 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 247 */     double d0 = getTargetDistance();
/* 248 */     List list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(d0, 4.0D, d0), Predicates.and(this.targetEntitySelector, EntitySelectors.NOT_SPECTATING));
/*     */     
/* 250 */     java.util.Collections.sort(list, this.theNearestAttackableTargetSorter);
/*     */     
/* 252 */     if (list.isEmpty())
/*     */     {
/* 254 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 258 */     this.targetEntity = ((EntityLivingBase)list.get(0));
/* 259 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/* 269 */     this.taskOwner.setAttackTarget(this.targetEntity);
/* 270 */     this.targetUnseenTicks = 0;
/* 271 */     super.startExecuting();
/*     */   }
/*     */   
/*     */   public class Sorter implements Comparator
/*     */   {
/*     */     private final Entity theEntity;
/*     */     private static final String __OBFID = "CL_00001622";
/*     */     
/*     */     public Sorter(Entity p_i1662_1_)
/*     */     {
/* 281 */       this.theEntity = p_i1662_1_;
/*     */     }
/*     */     
/*     */     public int compare(Entity p_compare_1_, Entity p_compare_2_)
/*     */     {
/* 286 */       double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
/* 287 */       double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
/* 288 */       return d0 > d1 ? 1 : d0 < d1 ? -1 : 0;
/*     */     }
/*     */     
/*     */     public int compare(Object p_compare_1_, Object p_compare_2_)
/*     */     {
/* 293 */       return compare((Entity)p_compare_1_, (Entity)p_compare_2_);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\AINearestValidTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */