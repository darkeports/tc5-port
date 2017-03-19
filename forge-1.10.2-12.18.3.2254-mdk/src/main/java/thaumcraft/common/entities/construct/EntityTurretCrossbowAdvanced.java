/*     */ package thaumcraft.common.entities.construct;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter;
/*     */ import net.minecraft.entity.ai.EntityAITarget;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.entity.ai.EntitySenses;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.IMob;
/*     */ import net.minecraft.entity.passive.IAnimals;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class EntityTurretCrossbowAdvanced extends EntityTurretCrossbow
/*     */ {
/*     */   public EntityTurretCrossbowAdvanced(World worldIn)
/*     */   {
/*  46 */     super(worldIn);
/*  47 */     setSize(0.95F, 1.5F);
/*  48 */     this.tasks.taskEntries.clear();
/*  49 */     this.targetTasks.taskEntries.clear();
/*     */     
/*  51 */     this.tasks.addTask(1, new EntityAIArrowAttack(this, 0.0D, 20, 40, 24.0F));
/*  52 */     this.tasks.addTask(2, new EntityAIWatchTarget(this));
/*  53 */     this.targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/*  54 */     this.targetTasks.addTask(2, new EntityAINearestValidTarget(this, EntityLivingBase.class, 5, true, false, (Predicate)null));
/*  55 */     setTargetMob(true);
/*  56 */     this.stepHeight = 0.0F;
/*     */   }
/*     */   
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/*  62 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public EntityTurretCrossbowAdvanced(World worldIn, BlockPos pos) {
/*  66 */     this(worldIn);
/*  67 */     setPositionAndRotation(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  73 */     super.entityInit();
/*  74 */     this.dataWatcher.addObject(20, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/*  80 */     if ((IAnimals.class.isAssignableFrom(clazz)) && (!IMob.class.isAssignableFrom(clazz)) && (getTargetAnimal())) return true;
/*  81 */     if ((IMob.class.isAssignableFrom(clazz)) && (getTargetMob())) return true;
/*  82 */     if ((EntityPlayer.class.isAssignableFrom(clazz)) && (getTargetPlayer())) {
/*  83 */       if ((!this.worldObj.isRemote) && (!MinecraftServer.getServer().isPVPEnabled()) && (!getTargetFriendly())) {
/*  84 */         setTargetPlayer(false);
/*  85 */         return false;
/*     */       }
/*  87 */       return true;
/*     */     }
/*     */     
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   public boolean getTargetAnimal()
/*     */   {
/*  95 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(20), 0);
/*     */   }
/*     */   
/*     */   public void setTargetAnimal(boolean par1)
/*     */   {
/* 100 */     byte var2 = this.dataWatcher.getWatchableObjectByte(20);
/* 101 */     if (par1) this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.setBit(var2, 0))); else
/* 102 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.clearBit(var2, 0)));
/* 103 */     setAttackTarget(null);
/*     */   }
/*     */   
/*     */   public boolean getTargetMob()
/*     */   {
/* 108 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(20), 1);
/*     */   }
/*     */   
/*     */   public void setTargetMob(boolean par1)
/*     */   {
/* 113 */     byte var2 = this.dataWatcher.getWatchableObjectByte(20);
/* 114 */     if (par1) this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.setBit(var2, 1))); else
/* 115 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.clearBit(var2, 1)));
/* 116 */     setAttackTarget(null);
/*     */   }
/*     */   
/*     */   public boolean getTargetPlayer()
/*     */   {
/* 121 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(20), 2);
/*     */   }
/*     */   
/*     */   public void setTargetPlayer(boolean par1)
/*     */   {
/* 126 */     byte var2 = this.dataWatcher.getWatchableObjectByte(20);
/* 127 */     if (par1) this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.setBit(var2, 2))); else
/* 128 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.clearBit(var2, 2)));
/* 129 */     setAttackTarget(null);
/*     */   }
/*     */   
/*     */   public boolean getTargetFriendly()
/*     */   {
/* 134 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(20), 3);
/*     */   }
/*     */   
/*     */   public void setTargetFriendly(boolean par1)
/*     */   {
/* 139 */     byte var2 = this.dataWatcher.getWatchableObjectByte(20);
/* 140 */     if (par1) this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.setBit(var2, 3))); else
/* 141 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.clearBit(var2, 3)));
/* 142 */     setAttackTarget(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Team getTeam()
/*     */   {
/* 149 */     if (isOwned())
/*     */     {
/* 151 */       EntityLivingBase entitylivingbase = getOwnerEntity();
/*     */       
/* 153 */       if (entitylivingbase != null)
/*     */       {
/* 155 */         return entitylivingbase.getTeam();
/*     */       }
/*     */     }
/*     */     
/* 159 */     return super.getTeam();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/* 166 */     super.applyEntityAttributes();
/* 167 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
/*     */   }
/*     */   
/*     */   public int getTotalArmorValue()
/*     */   {
/* 172 */     return 8;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 179 */     super.onUpdate();
/* 180 */     if ((!this.worldObj.isRemote) && (!MinecraftServer.getServer().isPVPEnabled()) && 
/* 181 */       (getAttackTarget() != null) && ((getAttackTarget() instanceof EntityPlayer)) && (getAttackTarget() != getOwnerEntity())) {
/* 182 */       setAttackTarget(null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/* 190 */     super.readEntityFromNBT(nbt);
/* 191 */     this.dataWatcher.updateObject(20, Byte.valueOf(nbt.getByte("targets")));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/* 197 */     super.writeEntityToNBT(nbt);
/* 198 */     nbt.setByte("targets", this.dataWatcher.getWatchableObjectByte(20));
/*     */   }
/*     */   
/*     */ 
/*     */   public void knockBack(Entity p_70653_1_, float p_70653_2_, double p_70653_3_, double p_70653_5_)
/*     */   {
/* 204 */     super.knockBack(p_70653_1_, p_70653_2_, p_70653_3_ / 3.0D, p_70653_5_ / 3.0D);
/*     */   }
/*     */   
/*     */   public boolean interact(EntityPlayer player)
/*     */   {
/* 209 */     if ((!this.worldObj.isRemote) && (isOwner(player)) && (!this.isDead)) {
/* 210 */       if ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof IWand))) {
/* 211 */         playSound("thaumcraft:zap", 1.0F, 1.0F);
/* 212 */         dropAmmo();
/* 213 */         entityDropItem(new ItemStack(ItemsTC.turretPlacer, 1, 3), 0.5F);
/* 214 */         setDead();
/* 215 */         player.swingItem();
/*     */       } else {
/* 217 */         player.openGui(Thaumcraft.instance, 17, this.worldObj, getEntityId(), 0, 0);
/*     */       }
/* 219 */       return true;
/*     */     }
/*     */     
/* 222 */     return super.interact(player);
/*     */   }
/*     */   
/*     */   public void moveEntity(double x, double y, double z)
/*     */   {
/* 227 */     super.moveEntity(x / 2.0D, y, z / 2.0D);
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
/*     */   {
/* 232 */     float b = p_70628_2_ * 0.15F;
/* 233 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(ItemsTC.mind, 1, 1), 0.5F);
/* 234 */     if (this.rand.nextFloat() < 0.5F + b) entityDropItem(new ItemStack(ItemsTC.gear), 0.5F);
/* 235 */     if (this.rand.nextFloat() < 0.5F + b) entityDropItem(new ItemStack(BlocksTC.plank), 0.5F);
/* 236 */     if (this.rand.nextFloat() < 0.5F + b) entityDropItem(new ItemStack(BlocksTC.plank), 0.5F);
/* 237 */     if (this.rand.nextFloat() < 0.3F + b) entityDropItem(new ItemStack(ItemsTC.plate, 1, 0), 0.5F);
/* 238 */     if (this.rand.nextFloat() < 0.4F + b) entityDropItem(new ItemStack(ItemsTC.plate, 1, 1), 0.5F);
/* 239 */     if (this.rand.nextFloat() < 0.4F + b) { entityDropItem(new ItemStack(ItemsTC.plate, 1, 1), 0.5F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected class EntityAIWatchTarget
/*     */     extends EntityAIBase
/*     */   {
/*     */     protected EntityLiving theWatcher;
/*     */     protected Entity closestEntity;
/*     */     private int lookTime;
/*     */     
/*     */     public EntityAIWatchTarget(EntityLiving p_i1631_1_)
/*     */     {
/* 253 */       this.theWatcher = p_i1631_1_;
/* 254 */       setMutexBits(2);
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean shouldExecute()
/*     */     {
/* 260 */       if (this.theWatcher.getAttackTarget() != null)
/*     */       {
/* 262 */         this.closestEntity = this.theWatcher.getAttackTarget();
/*     */       }
/*     */       
/* 265 */       return this.closestEntity != null;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean continueExecuting()
/*     */     {
/* 271 */       float d = (float)getTargetDistance();
/* 272 */       return this.closestEntity.isEntityAlive();
/*     */     }
/*     */     
/*     */ 
/*     */     public void startExecuting()
/*     */     {
/* 278 */       this.lookTime = (40 + this.theWatcher.getRNG().nextInt(40));
/*     */     }
/*     */     
/*     */ 
/*     */     public void resetTask()
/*     */     {
/* 284 */       this.closestEntity = null;
/*     */     }
/*     */     
/*     */ 
/*     */     public void updateTask()
/*     */     {
/* 290 */       this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, this.theWatcher.getVerticalFaceSpeed());
/* 291 */       this.lookTime -= 1;
/*     */     }
/*     */     
/*     */     protected double getTargetDistance()
/*     */     {
/* 296 */       IAttributeInstance iattributeinstance = this.theWatcher.getEntityAttribute(SharedMonsterAttributes.followRange);
/* 297 */       return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
/*     */     }
/*     */   }
/*     */   
/*     */   protected class EntityAINearestValidTarget extends EntityAITarget
/*     */   {
/*     */     protected final Class targetClass;
/*     */     private final int targetChance;
/*     */     protected final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
/*     */     protected Predicate targetEntitySelector;
/*     */     protected EntityLivingBase targetEntity;
/*     */     private int targetUnseenTicks;
/*     */     
/*     */     public EntityAINearestValidTarget(EntityCreature p_i45878_1_, Class p_i45878_2_, boolean p_i45878_3_) {
/* 311 */       this(p_i45878_1_, p_i45878_2_, p_i45878_3_, false);
/*     */     }
/*     */     
/*     */     public EntityAINearestValidTarget(EntityCreature p_i45879_1_, Class p_i45879_2_, boolean p_i45879_3_, boolean p_i45879_4_)
/*     */     {
/* 316 */       this(p_i45879_1_, p_i45879_2_, 10, p_i45879_3_, p_i45879_4_, (Predicate)null);
/*     */     }
/*     */     
/*     */     public EntityAINearestValidTarget(EntityCreature p_i45880_1_, Class p_i45880_2_, int p_i45880_3_, boolean p_i45880_4_, boolean p_i45880_5_, final Predicate tselector)
/*     */     {
/* 321 */       super(p_i45880_4_, p_i45880_5_);
/* 322 */       this.targetClass = p_i45880_2_;
/* 323 */       this.targetChance = p_i45880_3_;
/* 324 */       this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(p_i45880_1_);
/* 325 */       setMutexBits(1);
/* 326 */       this.targetEntitySelector = new Predicate()
/*     */       {
/*     */         private static final String __OBFID = "CL_00001621";
/*     */         
/*     */         public boolean applySelection(EntityLivingBase entity) {
/* 331 */           if ((tselector != null) && (!tselector.apply(entity)))
/*     */           {
/* 333 */             return false;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 338 */           if ((entity instanceof EntityPlayer))
/*     */           {
/* 340 */             double d0 = EntityTurretCrossbowAdvanced.EntityAINearestValidTarget.this.getTargetDistance();
/*     */             
/* 342 */             if (entity.isSneaking())
/*     */             {
/* 344 */               d0 *= 0.800000011920929D;
/*     */             }
/*     */             
/* 347 */             if (entity.isInvisible())
/*     */             {
/* 349 */               float f = ((EntityPlayer)entity).getArmorVisibility();
/*     */               
/* 351 */               if (f < 0.1F)
/*     */               {
/* 353 */                 f = 0.1F;
/*     */               }
/*     */               
/* 356 */               d0 *= 0.7F * f;
/*     */             }
/*     */             
/* 359 */             if (entity.getDistanceToEntity(EntityTurretCrossbowAdvanced.EntityAINearestValidTarget.this.taskOwner) > d0)
/*     */             {
/* 361 */               return false;
/*     */             }
/*     */           }
/*     */           
/* 365 */           return EntityTurretCrossbowAdvanced.EntityAINearestValidTarget.this.isSuitableTarget(entity, false);
/*     */         }
/*     */         
/*     */         public boolean apply(Object p_apply_1_)
/*     */         {
/* 370 */           return applySelection((EntityLivingBase)p_apply_1_);
/*     */         }
/*     */       };
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean continueExecuting()
/*     */     {
/* 378 */       EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();
/* 379 */       if (entitylivingbase == null)
/*     */       {
/* 381 */         return false;
/*     */       }
/* 383 */       if (!entitylivingbase.isEntityAlive())
/*     */       {
/* 385 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 389 */       Team team = this.taskOwner.getTeam();
/* 390 */       Team team1 = entitylivingbase.getTeam();
/*     */       
/* 392 */       if ((team != null) && (team1 == team) && (!((EntityTurretCrossbowAdvanced)this.taskOwner).getTargetFriendly()))
/*     */       {
/* 394 */         return false;
/*     */       }
/*     */       
/* 397 */       if ((team != null) && (team1 != team) && (((EntityTurretCrossbowAdvanced)this.taskOwner).getTargetFriendly()))
/*     */       {
/* 399 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 403 */       double d0 = getTargetDistance();
/*     */       
/* 405 */       if (this.taskOwner.getDistanceSqToEntity(entitylivingbase) > d0 * d0)
/*     */       {
/* 407 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 411 */       if (this.shouldCheckSight)
/*     */       {
/* 413 */         if (this.taskOwner.getEntitySenses().canSee(entitylivingbase))
/*     */         {
/* 415 */           this.targetUnseenTicks = 0;
/*     */         }
/* 417 */         else if (++this.targetUnseenTicks > 60)
/*     */         {
/* 419 */           return false;
/*     */         }
/*     */       }
/*     */       
/* 423 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected boolean isSuitableTarget(EntityLivingBase p_75296_1_, boolean p_75296_2_)
/*     */     {
/* 432 */       if (!isGoodTarget(this.taskOwner, p_75296_1_, p_75296_2_, this.shouldCheckSight))
/*     */       {
/* 434 */         return false;
/*     */       }
/* 436 */       if (!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos(p_75296_1_)))
/*     */       {
/* 438 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 442 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private boolean isGoodTarget(EntityLiving attacker, EntityLivingBase posTar, boolean p_179445_2_, boolean checkSight)
/*     */     {
/* 449 */       if (posTar == null)
/*     */       {
/* 451 */         return false;
/*     */       }
/* 453 */       if (posTar == attacker)
/*     */       {
/* 455 */         return false;
/*     */       }
/* 457 */       if (!posTar.isEntityAlive())
/*     */       {
/* 459 */         return false;
/*     */       }
/* 461 */       if (!attacker.canAttackClass(posTar.getClass()))
/*     */       {
/* 463 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 467 */       Team team = attacker.getTeam();
/* 468 */       Team team1 = posTar.getTeam();
/* 469 */       if ((team != null) && (team1 == team) && (!((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()))
/*     */       {
/* 471 */         return false;
/*     */       }
/*     */       
/* 474 */       if ((team != null) && (team1 != team) && (((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()))
/*     */       {
/* 476 */         return false;
/*     */       }
/*     */       
/* 479 */       if (((attacker instanceof IEntityOwnable)) && (org.apache.commons.lang3.StringUtils.isNotEmpty(((IEntityOwnable)attacker).getOwnerId())))
/*     */       {
/* 481 */         if (((posTar instanceof IEntityOwnable)) && (((IEntityOwnable)attacker).getOwnerId().equals(((IEntityOwnable)posTar).getOwnerId())) && (!((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()))
/*     */         {
/*     */ 
/*     */ 
/* 485 */           return false;
/*     */         }
/*     */         
/* 488 */         if ((!(posTar instanceof IEntityOwnable)) && (!(posTar instanceof EntityPlayer)) && (((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly())) {
/* 489 */           return false;
/*     */         }
/*     */         
/* 492 */         if (((posTar instanceof IEntityOwnable)) && (!((IEntityOwnable)attacker).getOwnerId().equals(((IEntityOwnable)posTar).getOwnerId())) && (((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()))
/*     */         {
/*     */ 
/*     */ 
/* 496 */           return false;
/*     */         }
/*     */         
/* 499 */         if ((posTar == ((IEntityOwnable)attacker).getOwner()) && (!((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()))
/*     */         {
/* 501 */           return false;
/*     */         }
/*     */         
/*     */       }
/* 505 */       else if (((posTar instanceof EntityPlayer)) && (!p_179445_2_) && (((EntityPlayer)posTar).capabilities.disableDamage) && (!((EntityTurretCrossbowAdvanced)attacker).getTargetFriendly()))
/*     */       {
/*     */ 
/* 508 */         return false;
/*     */       }
/*     */       
/* 511 */       return (!checkSight) || (attacker.getEntitySenses().canSee(posTar));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public boolean shouldExecute()
/*     */     {
/* 518 */       if ((this.targetChance > 0) && (this.taskOwner.getRNG().nextInt(this.targetChance) != 0))
/*     */       {
/* 520 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 524 */       double d0 = getTargetDistance();
/* 525 */       List list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(d0, 4.0D, d0), Predicates.and(this.targetEntitySelector, EntitySelectors.NOT_SPECTATING));
/* 526 */       Collections.sort(list, this.theNearestAttackableTargetSorter);
/*     */       
/* 528 */       if (list.isEmpty())
/*     */       {
/* 530 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 534 */       this.targetEntity = ((EntityLivingBase)list.get(0));
/* 535 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void startExecuting()
/*     */     {
/* 545 */       this.taskOwner.setAttackTarget(this.targetEntity);
/* 546 */       this.targetUnseenTicks = 0;
/* 547 */       super.startExecuting();
/*     */     }
/*     */     
/*     */     public class Sorter implements Comparator
/*     */     {
/*     */       private final Entity theEntity;
/*     */       private static final String __OBFID = "CL_00001622";
/*     */       
/*     */       public Sorter(Entity p_i1662_1_)
/*     */       {
/* 557 */         this.theEntity = p_i1662_1_;
/*     */       }
/*     */       
/*     */       public int compare(Entity p_compare_1_, Entity p_compare_2_)
/*     */       {
/* 562 */         double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
/* 563 */         double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
/* 564 */         return d0 > d1 ? 1 : d0 < d1 ? -1 : 0;
/*     */       }
/*     */       
/*     */       public int compare(Object p_compare_1_, Object p_compare_2_)
/*     */       {
/* 569 */         return compare((Entity)p_compare_1_, (Entity)p_compare_2_);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\EntityTurretCrossbowAdvanced.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */