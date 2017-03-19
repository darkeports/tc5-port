/*     */ package thaumcraft.common.entities.construct;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter;
/*     */ import net.minecraft.entity.ai.EntityAITarget;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.IMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.projectile.EntityPrimalArrow;
/*     */ 
/*     */ public class EntityTurretCrossbow extends EntityOwnedConstruct implements IRangedAttackMob
/*     */ {
/*     */   public EntityTurretCrossbow(World worldIn)
/*     */   {
/*  53 */     super(worldIn);
/*  54 */     setSize(0.95F, 1.25F);
/*  55 */     this.tasks.addTask(1, new EntityAIArrowAttack(this, 0.0D, 20, 60, 24.0F));
/*  56 */     this.tasks.addTask(2, new EntityAIWatchTarget(this));
/*  57 */     this.targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/*  58 */     this.targetTasks.addTask(2, new EntityAINearestValidTarget(this, EntityLiving.class, 5, true, false, IMob.mobSelector));
/*  59 */     this.stepHeight = 0.0F;
/*     */   }
/*     */   
/*     */   public EntityTurretCrossbow(World worldIn, BlockPos pos) {
/*  63 */     this(worldIn);
/*  64 */     setPositionAndRotation(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float range)
/*     */   {
/*  70 */     if ((getHeldItem() != null) && (getHeldItem().stackSize > 0)) {
/*  71 */       if (getHeldItem().getItem() == ItemsTC.primalArrows) {
/*  72 */         EntityPrimalArrow entityarrow = new EntityPrimalArrow(this.worldObj, this, 1.6F, getHeldItem().getItemDamage());
/*  73 */         entityarrow.setDamage(3.0D + range * 2.0F + this.rand.nextGaussian() * 0.25D);
/*  74 */         entityarrow.setKnockbackStrength(getHeldItem().getItemDamage() == 3 ? 1 : 0);
/*  75 */         Vec3 vec3d = getLook(1.0F);
/*  76 */         entityarrow.posX -= vec3d.xCoord * 0.8999999761581421D;
/*  77 */         entityarrow.posY -= vec3d.yCoord * 0.8999999761581421D;
/*  78 */         entityarrow.posZ -= vec3d.zCoord * 0.8999999761581421D;
/*  79 */         double d0 = target.posX - this.posX;
/*  80 */         double d1 = target.getEntityBoundingBox().minY + target.getEyeHeight() + range * range * 4.0F - entityarrow.posY;
/*  81 */         double d2 = target.posZ - this.posZ;
/*  82 */         entityarrow.setThrowableHeading(d0, d1, d2, 1.6F, 1.0F);
/*  83 */         this.worldObj.spawnEntityInWorld(entityarrow);
/*     */       } else {
/*  85 */         EntityArrow entityarrow = new EntityArrow(this.worldObj, this, target, 1.6F, 3.0F);
/*  86 */         entityarrow.setDamage(2.5D + range * 2.0F + this.rand.nextGaussian() * 0.25D);
/*  87 */         Vec3 vec3d = getLook(1.0F);
/*  88 */         entityarrow.posX -= vec3d.xCoord * 0.8999999761581421D;
/*  89 */         entityarrow.posY -= vec3d.yCoord * 0.8999999761581421D;
/*  90 */         entityarrow.posZ -= vec3d.zCoord * 0.8999999761581421D;
/*  91 */         entityarrow.canBePickedUp = 1;
/*  92 */         double d0 = target.posX - this.posX;
/*  93 */         double d1 = target.getEntityBoundingBox().minY + target.getEyeHeight() + range * range * 4.0F - entityarrow.posY;
/*  94 */         double d2 = target.posZ - this.posZ;
/*  95 */         entityarrow.setThrowableHeading(d0, d1, d2, 1.6F, 2.0F);
/*  96 */         this.worldObj.spawnEntityInWorld(entityarrow);
/*     */       }
/*  98 */       this.worldObj.setEntityState(this, (byte)16);
/*  99 */       playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
/* 100 */       getHeldItem().stackSize -= 1;
/* 101 */       if (getHeldItem().stackSize <= 0) {
/* 102 */         setCurrentItemOrArmor(0, null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void handleStatusUpdate(byte par1)
/*     */   {
/* 112 */     if (par1 == 16)
/*     */     {
/* 114 */       if (!this.isSwingInProgress) {
/* 115 */         this.swingProgressInt = -1;
/* 116 */         this.isSwingInProgress = true;
/*     */       }
/*     */       
/*     */     }
/* 120 */     else if (par1 == 17)
/*     */     {
/* 122 */       if (!this.isLoadInProgress) {
/* 123 */         this.loadProgressInt = -1;
/* 124 */         this.isLoadInProgress = true;
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/* 129 */       super.handleStatusUpdate(par1);
/*     */     }
/*     */   }
/*     */   
/* 133 */   int loadProgressInt = 0;
/* 134 */   boolean isLoadInProgress = false;
/* 135 */   float loadProgress = 0.0F;
/* 136 */   float prevLoadProgress = 0.0F;
/* 137 */   public float loadProgressForRender = 0.0F;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getLoadProgress(float pt)
/*     */   {
/* 142 */     float f1 = this.loadProgress - this.prevLoadProgress;
/*     */     
/* 144 */     if (f1 < 0.0F)
/*     */     {
/* 146 */       f1 += 1.0F;
/*     */     }
/*     */     
/* 149 */     return this.prevLoadProgress + f1 * pt;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void updateArmSwingProgress()
/*     */   {
/* 155 */     if (this.isSwingInProgress)
/*     */     {
/* 157 */       this.swingProgressInt += 1;
/*     */       
/* 159 */       if (this.swingProgressInt >= 6)
/*     */       {
/* 161 */         this.swingProgressInt = 0;
/* 162 */         this.isSwingInProgress = false;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 167 */       this.swingProgressInt = 0;
/*     */     }
/* 169 */     this.swingProgress = (this.swingProgressInt / 6.0F);
/*     */     
/* 171 */     if (this.isLoadInProgress)
/*     */     {
/* 173 */       this.loadProgressInt += 1;
/*     */       
/* 175 */       if (this.loadProgressInt >= 10)
/*     */       {
/* 177 */         this.loadProgressInt = 0;
/* 178 */         this.isLoadInProgress = false;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 183 */       this.loadProgressInt = 0;
/*     */     }
/* 185 */     this.loadProgress = (this.loadProgressInt / 10.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onEntityUpdate()
/*     */   {
/* 191 */     this.prevLoadProgress = this.loadProgress;
/* 192 */     if ((!this.worldObj.isRemote) && ((getHeldItem() == null) || (getHeldItem().stackSize <= 0))) {
/* 193 */       BlockPos p = getPosition().down();
/* 194 */       TileEntity t = this.worldObj.getTileEntity(p);
/* 195 */       if ((t != null) && ((t instanceof TileEntityDispenser)) && (BlockDispenser.getFacing(t.getBlockMetadata()) == EnumFacing.UP)) {
/* 196 */         TileEntityDispenser d = (TileEntityDispenser)t;
/* 197 */         for (int a = 0; a < d.getSizeInventory(); a++) {
/* 198 */           if ((d.getStackInSlot(a) != null) && ((d.getStackInSlot(a).getItem() == Items.arrow) || (d.getStackInSlot(a).getItem() == ItemsTC.primalArrows)))
/*     */           {
/* 200 */             setCurrentItemOrArmor(0, d.decrStackSize(a, d.getStackInSlot(a).stackSize));
/* 201 */             playSound("thaumcraft:cameraticks", 1.0F, 1.0F);
/* 202 */             this.worldObj.setEntityState(this, (byte)17);
/* 203 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 208 */     if (isRiding()) this.posY += 0.3D;
/* 209 */     super.onEntityUpdate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/* 216 */     return isOnTeam(otherEntity.getTeam());
/*     */   }
/*     */   
/*     */ 
/*     */   public Team getTeam()
/*     */   {
/* 222 */     if (isOwned())
/*     */     {
/* 224 */       EntityLivingBase entitylivingbase = getOwnerEntity();
/*     */       
/* 226 */       if (entitylivingbase != null)
/*     */       {
/* 228 */         return entitylivingbase.getTeam();
/*     */       }
/*     */     }
/*     */     
/* 232 */     return super.getTeam();
/*     */   }
/*     */   
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/* 238 */     return this.height * 0.66F;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/* 244 */     super.applyEntityAttributes();
/* 245 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
/* 246 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0D);
/*     */   }
/*     */   
/*     */   public int getTotalArmorValue()
/*     */   {
/* 251 */     return 2;
/*     */   }
/*     */   
/* 254 */   boolean attackedLastTick = false;
/* 255 */   int attackCount = 0;
/*     */   
/*     */   public void onUpdate()
/*     */   {
/* 259 */     super.onUpdate();
/*     */     
/* 261 */     if ((getAttackTarget() != null) && (isOnSameTeam(getAttackTarget()))) { setAttackTarget(null);
/*     */     }
/* 263 */     if (!this.worldObj.isRemote) {
/* 264 */       this.rotationYaw = this.rotationYawHead;
/*     */       
/* 266 */       if (this.ticksExisted % 80 == 0) heal(1.0F);
/*     */     }
/*     */     else {
/* 269 */       updateArmSwingProgress();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canBePushed()
/*     */   {
/* 281 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 287 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/* 295 */     super.readEntityFromNBT(nbt);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/* 301 */     super.writeEntityToNBT(nbt);
/*     */   }
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount)
/*     */   {
/* 306 */     this.rotationYaw = ((float)(this.rotationYaw + getRNG().nextGaussian() * 45.0D));
/* 307 */     this.rotationPitch = ((float)(this.rotationPitch + getRNG().nextGaussian() * 20.0D));
/* 308 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */   
/*     */   public void knockBack(Entity p_70653_1_, float p_70653_2_, double p_70653_3_, double p_70653_5_)
/*     */   {
/* 313 */     super.knockBack(p_70653_1_, p_70653_2_, p_70653_3_ / 10.0D, p_70653_5_ / 10.0D);
/* 314 */     if (this.motionY > 0.1D) this.motionY = 0.1D;
/*     */   }
/*     */   
/*     */   public boolean interact(EntityPlayer player)
/*     */   {
/* 319 */     if ((!this.worldObj.isRemote) && (isOwner(player)) && (!this.isDead)) {
/* 320 */       if ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof IWand))) {
/* 321 */         playSound("thaumcraft:zap", 1.0F, 1.0F);
/* 322 */         dropAmmo();
/* 323 */         entityDropItem(new ItemStack(ItemsTC.turretPlacer, 1, 0), 0.5F);
/* 324 */         setDead();
/* 325 */         player.swingItem();
/*     */       } else {
/* 327 */         player.openGui(Thaumcraft.instance, 16, this.worldObj, getEntityId(), 0, 0);
/*     */       }
/* 329 */       return true;
/*     */     }
/*     */     
/* 332 */     return super.interact(player);
/*     */   }
/*     */   
/*     */   public void moveEntity(double x, double y, double z)
/*     */   {
/* 337 */     super.moveEntity(x / 5.0D, y, z / 5.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDeath(DamageSource cause)
/*     */   {
/* 343 */     super.onDeath(cause);
/*     */     
/* 345 */     if (!this.worldObj.isRemote)
/*     */     {
/* 347 */       dropAmmo();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void dropAmmo() {
/* 352 */     if (getHeldItem() != null) {
/* 353 */       entityDropItem(getHeldItem(), 0.5F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
/*     */   {
/* 359 */     float b = p_70628_2_ * 0.15F;
/* 360 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(ItemsTC.mind), 0.5F);
/* 361 */     if (this.rand.nextFloat() < 0.5F + b) entityDropItem(new ItemStack(ItemsTC.gear), 0.5F);
/* 362 */     if (this.rand.nextFloat() < 0.5F + b) entityDropItem(new ItemStack(BlocksTC.plank), 0.5F);
/* 363 */     if (this.rand.nextFloat() < 0.5F + b) entityDropItem(new ItemStack(BlocksTC.plank), 0.5F);
/*     */   }
/*     */   
/*     */   protected MovingObjectPosition getMovingObjectPosition()
/*     */   {
/* 368 */     float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch);
/* 369 */     float f1 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw);
/* 370 */     double d0 = this.prevPosX + (this.posX - this.prevPosX);
/* 371 */     double d1 = this.prevPosY + (this.posY - this.prevPosY) + getEyeHeight();
/* 372 */     double d2 = this.prevPosZ + (this.posZ - this.prevPosZ);
/* 373 */     Vec3 vec3 = new Vec3(d0, d1, d2);
/* 374 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/* 375 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/* 376 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/* 377 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/* 378 */     float f6 = f3 * f4;
/* 379 */     float f7 = f2 * f4;
/* 380 */     double d3 = 5.0D;
/* 381 */     Vec3 vec31 = vec3.addVector(f6 * d3, f5 * d3, f7 * d3);
/* 382 */     return this.worldObj.rayTraceBlocks(vec3, vec31, true, false, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getVerticalFaceSpeed()
/*     */   {
/* 388 */     return 20;
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
/* 401 */       this.theWatcher = p_i1631_1_;
/* 402 */       setMutexBits(2);
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean shouldExecute()
/*     */     {
/* 408 */       if (this.theWatcher.getAttackTarget() != null)
/*     */       {
/* 410 */         this.closestEntity = this.theWatcher.getAttackTarget();
/*     */       }
/*     */       
/* 413 */       return this.closestEntity != null;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean continueExecuting()
/*     */     {
/* 419 */       float d = (float)getTargetDistance();
/* 420 */       return this.closestEntity.isEntityAlive();
/*     */     }
/*     */     
/*     */ 
/*     */     public void startExecuting()
/*     */     {
/* 426 */       this.lookTime = (40 + this.theWatcher.getRNG().nextInt(40));
/*     */     }
/*     */     
/*     */ 
/*     */     public void resetTask()
/*     */     {
/* 432 */       this.closestEntity = null;
/*     */     }
/*     */     
/*     */ 
/*     */     public void updateTask()
/*     */     {
/* 438 */       this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, this.theWatcher.getVerticalFaceSpeed());
/* 439 */       this.lookTime -= 1;
/*     */     }
/*     */     
/*     */     protected double getTargetDistance()
/*     */     {
/* 444 */       IAttributeInstance iattributeinstance = this.theWatcher.getEntityAttribute(SharedMonsterAttributes.followRange);
/* 445 */       return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
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
/*     */     
/*     */     public EntityAINearestValidTarget(EntityCreature p_i45878_1_, Class p_i45878_2_, boolean p_i45878_3_)
/*     */     {
/* 459 */       this(p_i45878_1_, p_i45878_2_, p_i45878_3_, false);
/*     */     }
/*     */     
/*     */     public EntityAINearestValidTarget(EntityCreature p_i45879_1_, Class p_i45879_2_, boolean p_i45879_3_, boolean p_i45879_4_)
/*     */     {
/* 464 */       this(p_i45879_1_, p_i45879_2_, 10, p_i45879_3_, p_i45879_4_, (Predicate)null);
/*     */     }
/*     */     
/*     */     public EntityAINearestValidTarget(EntityCreature p_i45880_1_, Class p_i45880_2_, int p_i45880_3_, boolean p_i45880_4_, boolean p_i45880_5_, final Predicate tselector)
/*     */     {
/* 469 */       super(p_i45880_4_, p_i45880_5_);
/* 470 */       this.targetClass = p_i45880_2_;
/* 471 */       this.targetChance = p_i45880_3_;
/* 472 */       this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(p_i45880_1_);
/* 473 */       setMutexBits(1);
/* 474 */       this.targetEntitySelector = new Predicate()
/*     */       {
/*     */         private static final String __OBFID = "CL_00001621";
/*     */         
/*     */         public boolean applySelection(EntityLivingBase entity) {
/* 479 */           if ((tselector != null) && (!tselector.apply(entity)))
/*     */           {
/* 481 */             return false;
/*     */           }
/*     */           
/*     */ 
/* 485 */           if ((entity instanceof EntityPlayer))
/*     */           {
/* 487 */             double d0 = EntityTurretCrossbow.EntityAINearestValidTarget.this.getTargetDistance();
/*     */             
/* 489 */             if (entity.isSneaking())
/*     */             {
/* 491 */               d0 *= 0.800000011920929D;
/*     */             }
/*     */             
/* 494 */             if (entity.isInvisible())
/*     */             {
/* 496 */               float f = ((EntityPlayer)entity).getArmorVisibility();
/*     */               
/* 498 */               if (f < 0.1F)
/*     */               {
/* 500 */                 f = 0.1F;
/*     */               }
/*     */               
/* 503 */               d0 *= 0.7F * f;
/*     */             }
/*     */             
/* 506 */             if (entity.getDistanceToEntity(EntityTurretCrossbow.EntityAINearestValidTarget.this.taskOwner) > d0)
/*     */             {
/* 508 */               return false;
/*     */             }
/*     */           }
/*     */           
/* 512 */           return EntityTurretCrossbow.EntityAINearestValidTarget.this.isSuitableTarget(entity, false);
/*     */         }
/*     */         
/*     */         public boolean apply(Object p_apply_1_)
/*     */         {
/* 517 */           return applySelection((EntityLivingBase)p_apply_1_);
/*     */         }
/*     */       };
/*     */     }
/*     */     
/*     */ 
/*     */     protected boolean isSuitableTarget(EntityLivingBase p_75296_1_, boolean p_75296_2_)
/*     */     {
/* 525 */       if (!isSuitableTarget(this.taskOwner, p_75296_1_, p_75296_2_, this.shouldCheckSight))
/*     */       {
/* 527 */         return false;
/*     */       }
/* 529 */       if (!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos(p_75296_1_)))
/*     */       {
/* 531 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 535 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public boolean shouldExecute()
/*     */     {
/* 542 */       if ((this.targetChance > 0) && (this.taskOwner.getRNG().nextInt(this.targetChance) != 0))
/*     */       {
/* 544 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 548 */       double d0 = getTargetDistance();
/* 549 */       List list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(d0, 4.0D, d0), Predicates.and(this.targetEntitySelector, EntitySelectors.NOT_SPECTATING));
/* 550 */       Collections.sort(list, this.theNearestAttackableTargetSorter);
/*     */       
/* 552 */       if (list.isEmpty())
/*     */       {
/* 554 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 558 */       this.targetEntity = ((EntityLivingBase)list.get(0));
/* 559 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void startExecuting()
/*     */     {
/* 567 */       this.taskOwner.setAttackTarget(this.targetEntity);
/* 568 */       super.startExecuting();
/*     */     }
/*     */     
/*     */     public class Sorter implements Comparator
/*     */     {
/*     */       private final Entity theEntity;
/*     */       private static final String __OBFID = "CL_00001622";
/*     */       
/*     */       public Sorter(Entity p_i1662_1_)
/*     */       {
/* 578 */         this.theEntity = p_i1662_1_;
/*     */       }
/*     */       
/*     */       public int compare(Entity p_compare_1_, Entity p_compare_2_)
/*     */       {
/* 583 */         double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
/* 584 */         double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
/* 585 */         return d0 > d1 ? 1 : d0 < d1 ? -1 : 0;
/*     */       }
/*     */       
/*     */       public int compare(Object p_compare_1_, Object p_compare_2_)
/*     */       {
/* 590 */         return compare((Entity)p_compare_1_, (Entity)p_compare_2_);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\EntityTurretCrossbow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */