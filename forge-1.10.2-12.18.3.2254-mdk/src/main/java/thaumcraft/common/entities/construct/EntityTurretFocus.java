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
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter;
/*     */ import net.minecraft.entity.ai.EntityAITarget;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
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
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.ConfigItems;
/*     */ import thaumcraft.common.items.wands.ItemWand;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class EntityTurretFocus extends EntityOwnedConstruct
/*     */ {
/*  59 */   protected AspectList vis = new AspectList();
/*  60 */   protected int maxVis = 100;
/*  61 */   protected int attackTimer = 10;
/*     */   
/*     */   public EntityTurretFocus(World worldIn) {
/*  64 */     super(worldIn);
/*  65 */     setSize(0.9F, 0.9F);
/*  66 */     this.tasks.addTask(2, new EntityAIWatchTarget(this));
/*  67 */     this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F));
/*  68 */     this.tasks.addTask(4, new EntityAILookIdle(this));
/*  69 */     this.targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/*  70 */     this.targetTasks.addTask(2, new EntityAINearestValidTarget(this, EntityLivingBase.class, 5, true, false, (Predicate)null));
/*  71 */     setTargetMob(true);
/*  72 */     this.experienceValue = 6;
/*     */   }
/*     */   
/*     */   public EntityTurretFocus(World worldIn, BlockPos pos, EnumFacing face) {
/*  76 */     this(worldIn);
/*  77 */     setPositionAndRotation(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
/*  78 */     setFacing(face);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  84 */     super.entityInit();
/*  85 */     this.dataWatcher.addObject(18, new ItemStack(ItemsTC.wand));
/*  86 */     this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
/*  87 */     this.dataWatcher.addObject(20, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/*  93 */     return isOnTeam(otherEntity.getTeam());
/*     */   }
/*     */   
/*     */ 
/*     */   public Team getTeam()
/*     */   {
/*  99 */     if (isOwned())
/*     */     {
/* 101 */       EntityLivingBase entitylivingbase = getOwnerEntity();
/*     */       
/* 103 */       if (entitylivingbase != null)
/*     */       {
/* 105 */         return entitylivingbase.getTeam();
/*     */       }
/*     */     }
/*     */     
/* 109 */     return super.getTeam();
/*     */   }
/*     */   
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/* 115 */     return this.height * 0.5F;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/* 121 */     super.applyEntityAttributes();
/* 122 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
/* 123 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
/*     */   }
/*     */   
/*     */   public int getTotalArmorValue()
/*     */   {
/* 128 */     return 4;
/*     */   }
/*     */   
/* 131 */   boolean attackedLastTick = false;
/* 132 */   int attackCount = 0;
/*     */   
/*     */   public void onUpdate()
/*     */   {
/* 136 */     super.onUpdate();
/*     */     
/* 138 */     doUpdateStuff();
/*     */     
/* 140 */     if (!this.worldObj.isRemote) {
/* 141 */       this.rotationYaw = this.rotationYawHead;
/*     */       
/* 143 */       if (this.ticksExisted % 4 == 0) { rechargeVis();
/*     */       }
/* 145 */       if (this.ticksExisted % 40 == 0) { heal(1.0F);
/*     */       }
/* 147 */       if (this.attackTimer > 0) { this.attackTimer -= 1;
/*     */       }
/* 149 */       boolean at = this.attackedLastTick;
/* 150 */       this.attackedLastTick = false;
/* 151 */       if ((getAttackTarget() != null) && (this.attackTimer <= 0)) {
/* 152 */         attackEntityWithFocus();
/*     */       }
/* 154 */       if ((at) && (!this.attackedLastTick)) {
/* 155 */         this.attackCount = 0;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doUpdateStuff() {
/* 161 */     if ((getAttackTarget() != null) && ((getAttackTarget() instanceof EntityTurretFocus)) && (!getTargetFriendly()))
/* 162 */       setAttackTarget(null);
/* 163 */     if ((!this.worldObj.isRemote) && (!MinecraftServer.getServer().isPVPEnabled()) && 
/* 164 */       (getAttackTarget() != null) && ((getAttackTarget() instanceof EntityPlayer)) && (getAttackTarget() != getOwnerEntity())) {
/* 165 */       setAttackTarget(null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onEntityUpdate()
/*     */   {
/* 172 */     if (isRiding()) this.posY += 1.0D;
/* 173 */     super.onEntityUpdate();
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void handleStatusUpdate(byte par1)
/*     */   {
/* 180 */     if ((par1 == 16) && (getHeldItem() != null) && (getHeldItem().getItem() != null) && ((getHeldItem().getItem() instanceof ItemFocusBasic)))
/*     */     {
/* 182 */       ((ItemFocusBasic)getHeldItem().getItem()).onFocusActivation(getWand(), this.worldObj, this, getMovingObjectPosition(), this.attackCount);
/*     */     }
/*     */     else
/*     */     {
/* 186 */       super.handleStatusUpdate(par1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount)
/*     */   {
/* 192 */     this.rotationYaw = ((float)(this.rotationYaw + getRNG().nextGaussian() * 45.0D));
/* 193 */     this.rotationPitch = ((float)(this.rotationPitch + getRNG().nextGaussian() * 20.0D));
/* 194 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */   
/*     */   protected void attackEntityWithFocus()
/*     */   {
/* 199 */     if ((getHeldItem() != null) && (getHeldItem().getItem() != null) && ((getHeldItem().getItem() instanceof ItemFocusBasic)))
/*     */     {
/* 201 */       ItemFocusBasic focus = (ItemFocusBasic)getHeldItem().getItem();
/*     */       
/* 203 */       double dis = getDistanceToEntity(getAttackTarget());
/* 204 */       if ((dis > focus.getTurretRange(getHeldItem())) || (!EntityUtils.isVisibleTo(0.3F, this, getAttackTarget(), focus.getTurretRange(getHeldItem())))) {
/* 205 */         return;
/*     */       }
/*     */       
/* 208 */       AspectList cost = focus.getVisCost(getHeldItem());
/* 209 */       if (consumeVis(cost, false)) {
/* 210 */         float d = (float)(dis / focus.getTurretRange(getHeldItem()));
/* 211 */         this.rotationPitch -= focus.getTurretCorrection(getHeldItem()) * d;
/* 212 */         if (focus.onFocusActivation(getWand(), this.worldObj, this, getMovingObjectPosition(), this.attackCount)) {
/* 213 */           consumeVis(cost, true);
/* 214 */           this.attackTimer = (focus.getActivationCooldown(getHeldItem()) / 50);
/* 215 */           if (!focus.isVisCostPerTick(getHeldItem())) {
/* 216 */             this.attackTimer = ((int)(this.attackTimer * 1.33D));
/*     */           }
/* 218 */           this.rotationPitch += focus.getTurretCorrection(getHeldItem()) * d;
/* 219 */           this.attackedLastTick = true;
/* 220 */           this.attackCount += 1;
/* 221 */           this.worldObj.setEntityState(this, (byte)16);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean consumeVis(AspectList aspects, boolean doit)
/*     */   {
/* 229 */     if (this.worldObj.isRemote) return true;
/* 230 */     if ((aspects == null) || (aspects.size() == 0)) return false;
/* 231 */     for (Aspect aspect : aspects.getAspects()) {
/* 232 */       int cost = aspects.getAmount(aspect);
/* 233 */       if (this.vis.getAmount(aspect) < cost) return false;
/*     */     }
/* 235 */     if (doit)
/* 236 */       for (Aspect aspect : aspects.getAspects()) {
/* 237 */         int cost = aspects.getAmount(aspect);
/* 238 */         this.vis.remove(aspect, cost);
/*     */       }
/* 240 */     return true;
/*     */   }
/*     */   
/*     */   protected void rechargeVis() {
/* 244 */     Aspect low = null;
/* 245 */     int amt = Integer.MAX_VALUE;
/* 246 */     for (Aspect aspect : Aspect.getPrimalAspects()) {
/* 247 */       if ((this.vis.getAmount(aspect) < this.maxVis) && (this.vis.getAmount(aspect) < amt) && (AuraHandler.getAuraCurrent(this.worldObj, getPosition(), aspect) > 0))
/*     */       {
/* 249 */         low = aspect;
/* 250 */         amt = this.vis.getAmount(aspect);
/*     */       }
/*     */     }
/* 253 */     if (low != null) {
/* 254 */       this.vis.add(low, AuraHandler.drainAuraAvailable(this.worldObj, getPosition(), low, 1));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBePushed()
/*     */   {
/* 261 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 267 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/* 275 */     super.readEntityFromNBT(nbt);
/*     */     
/* 277 */     this.vis.readFromNBT(nbt);
/* 278 */     setFacing(EnumFacing.VALUES[nbt.getByte("face")]);
/* 279 */     this.dataWatcher.updateObject(20, Byte.valueOf(nbt.getByte("targets")));
/*     */     
/* 281 */     updateFocus();
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/* 287 */     super.writeEntityToNBT(nbt);
/*     */     
/* 289 */     this.vis.writeToNBT(nbt);
/* 290 */     nbt.setByte("face", (byte)getFacing().ordinal());
/* 291 */     nbt.setByte("targets", this.dataWatcher.getWatchableObjectByte(20));
/*     */   }
/*     */   
/*     */   public EnumFacing getFacing()
/*     */   {
/* 296 */     return EnumFacing.VALUES[this.dataWatcher.getWatchableObjectByte(19)];
/*     */   }
/*     */   
/*     */   public void setFacing(EnumFacing face) {
/* 300 */     this.dataWatcher.updateObject(19, Byte.valueOf((byte)face.ordinal()));
/*     */   }
/*     */   
/*     */ 
/*     */   public void moveEntity(double x, double y, double z) {}
/*     */   
/*     */ 
/*     */   public void moveFlying(float strafe, float forward, float friction) {}
/*     */   
/*     */   public boolean interact(EntityPlayer player)
/*     */   {
/* 311 */     if ((!this.worldObj.isRemote) && (isOwner(player)) && (!this.isDead)) {
/* 312 */       if ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof IWand))) {
/* 313 */         playSound("thaumcraft:zap", 1.0F, 1.0F);
/* 314 */         dropFocus();
/* 315 */         entityDropItem(new ItemStack(ItemsTC.turretPlacer, 1, 1), 0.5F);
/* 316 */         setDead();
/* 317 */         player.swingItem();
/*     */       } else {
/* 319 */         player.openGui(Thaumcraft.instance, 15, this.worldObj, getEntityId(), 0, 0);
/*     */       }
/* 321 */       return true;
/*     */     }
/*     */     
/* 324 */     return super.interact(player);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDeath(DamageSource cause)
/*     */   {
/* 330 */     super.onDeath(cause);
/*     */     
/* 332 */     if (!this.worldObj.isRemote)
/*     */     {
/* 334 */       dropFocus();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void dropFocus() {
/* 339 */     if (getHeldItem() != null) {
/* 340 */       entityDropItem(getHeldItem(), 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
/*     */   {
/* 346 */     float b = p_70628_2_ * 0.15F;
/* 347 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(ItemsTC.mind, 1, 1), 0.5F);
/* 348 */     if (this.rand.nextFloat() < 0.5F + b) entityDropItem(new ItemStack(ItemsTC.gear), 0.5F);
/* 349 */     if (this.rand.nextFloat() < 0.5F + b) entityDropItem(new ItemStack(BlocksTC.plank), 0.5F);
/* 350 */     if (this.rand.nextFloat() < 0.5F + b) entityDropItem(new ItemStack(BlocksTC.plank), 0.5F);
/*     */   }
/*     */   
/*     */   public ItemStack getWand()
/*     */   {
/* 355 */     return this.dataWatcher.getWatchableObjectItemStack(18);
/*     */   }
/*     */   
/*     */   public void setWand(ItemStack ws)
/*     */   {
/* 360 */     this.dataWatcher.updateObject(18, ws);
/*     */   }
/*     */   
/*     */   public void updateFocus() {
/* 364 */     ItemStack wand = new ItemStack(ItemsTC.wand);
/* 365 */     ItemWand ws = (ItemWand)wand.getItem();
/* 366 */     ws.setCap(wand, ConfigItems.WAND_CAP_GOLD);
/* 367 */     ws.setRod(wand, ConfigItems.WAND_ROD_GREATWOOD);
/* 368 */     if (getHeldItem() != null) {
/* 369 */       ws.setFocus(wand, getHeldItem());
/* 370 */       ItemFocusBasic focus = (ItemFocusBasic)getHeldItem().getItem();
/* 371 */       getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(focus.getTurretRange(getHeldItem()));
/*     */     }
/* 373 */     setWand(wand);
/*     */   }
/*     */   
/*     */   protected MovingObjectPosition getMovingObjectPosition()
/*     */   {
/* 378 */     float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch);
/* 379 */     float f1 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw);
/* 380 */     double d0 = this.prevPosX + (this.posX - this.prevPosX);
/* 381 */     double d1 = this.prevPosY + (this.posY - this.prevPosY) + getEyeHeight();
/* 382 */     double d2 = this.prevPosZ + (this.posZ - this.prevPosZ);
/* 383 */     Vec3 vec3 = new Vec3(d0, d1, d2);
/* 384 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/* 385 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/* 386 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/* 387 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/* 388 */     float f6 = f3 * f4;
/* 389 */     float f7 = f2 * f4;
/* 390 */     double d3 = 5.0D;
/* 391 */     Vec3 vec31 = vec3.addVector(f6 * d3, f5 * d3, f7 * d3);
/* 392 */     return this.worldObj.rayTraceBlocks(vec3, vec31, true, false, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getVerticalFaceSpeed()
/*     */   {
/* 398 */     return 20;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/* 404 */     if ((IAnimals.class.isAssignableFrom(clazz)) && (!IMob.class.isAssignableFrom(clazz)) && (getTargetAnimal())) return true;
/* 405 */     if ((IMob.class.isAssignableFrom(clazz)) && (getTargetMob())) return true;
/* 406 */     if ((EntityPlayer.class.isAssignableFrom(clazz)) && (getTargetPlayer())) {
/* 407 */       if ((!this.worldObj.isRemote) && (!MinecraftServer.getServer().isPVPEnabled()) && (!getTargetFriendly())) {
/* 408 */         setTargetPlayer(false);
/* 409 */         return false;
/*     */       }
/* 411 */       return true;
/*     */     }
/*     */     
/* 414 */     return false;
/*     */   }
/*     */   
/*     */   public boolean getTargetAnimal()
/*     */   {
/* 419 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(20), 0);
/*     */   }
/*     */   
/*     */   public void setTargetAnimal(boolean par1)
/*     */   {
/* 424 */     byte var2 = this.dataWatcher.getWatchableObjectByte(20);
/* 425 */     if (par1) this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.setBit(var2, 0))); else
/* 426 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.clearBit(var2, 0)));
/* 427 */     setAttackTarget(null);
/*     */   }
/*     */   
/*     */   public boolean getTargetMob()
/*     */   {
/* 432 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(20), 1);
/*     */   }
/*     */   
/*     */   public void setTargetMob(boolean par1)
/*     */   {
/* 437 */     byte var2 = this.dataWatcher.getWatchableObjectByte(20);
/* 438 */     if (par1) this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.setBit(var2, 1))); else
/* 439 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.clearBit(var2, 1)));
/* 440 */     setAttackTarget(null);
/*     */   }
/*     */   
/*     */   public boolean getTargetPlayer()
/*     */   {
/* 445 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(20), 2);
/*     */   }
/*     */   
/*     */   public void setTargetPlayer(boolean par1)
/*     */   {
/* 450 */     byte var2 = this.dataWatcher.getWatchableObjectByte(20);
/* 451 */     if (par1) this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.setBit(var2, 2))); else
/* 452 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.clearBit(var2, 2)));
/* 453 */     setAttackTarget(null);
/*     */   }
/*     */   
/*     */   public boolean getTargetFriendly()
/*     */   {
/* 458 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(20), 3);
/*     */   }
/*     */   
/*     */   public void setTargetFriendly(boolean par1)
/*     */   {
/* 463 */     byte var2 = this.dataWatcher.getWatchableObjectByte(20);
/* 464 */     if (par1) this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.setBit(var2, 3))); else
/* 465 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.clearBit(var2, 3)));
/* 466 */     setAttackTarget(null);
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
/* 479 */       this.theWatcher = p_i1631_1_;
/* 480 */       setMutexBits(2);
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean shouldExecute()
/*     */     {
/* 486 */       if (this.theWatcher.getAttackTarget() != null)
/*     */       {
/* 488 */         this.closestEntity = this.theWatcher.getAttackTarget();
/*     */       }
/*     */       
/* 491 */       return this.closestEntity != null;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean continueExecuting()
/*     */     {
/* 497 */       float d = (float)getTargetDistance();
/* 498 */       return this.closestEntity.isEntityAlive();
/*     */     }
/*     */     
/*     */ 
/*     */     public void startExecuting()
/*     */     {
/* 504 */       this.lookTime = (40 + this.theWatcher.getRNG().nextInt(40));
/*     */     }
/*     */     
/*     */ 
/*     */     public void resetTask()
/*     */     {
/* 510 */       this.closestEntity = null;
/*     */     }
/*     */     
/*     */ 
/*     */     public void updateTask()
/*     */     {
/* 516 */       this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, this.theWatcher.getVerticalFaceSpeed());
/* 517 */       this.lookTime -= 1;
/*     */     }
/*     */     
/*     */     protected double getTargetDistance()
/*     */     {
/* 522 */       IAttributeInstance iattributeinstance = this.theWatcher.getEntityAttribute(SharedMonsterAttributes.followRange);
/* 523 */       return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
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
/* 537 */       this(p_i45878_1_, p_i45878_2_, p_i45878_3_, false);
/*     */     }
/*     */     
/*     */     public EntityAINearestValidTarget(EntityCreature p_i45879_1_, Class p_i45879_2_, boolean p_i45879_3_, boolean p_i45879_4_)
/*     */     {
/* 542 */       this(p_i45879_1_, p_i45879_2_, 10, p_i45879_3_, p_i45879_4_, (Predicate)null);
/*     */     }
/*     */     
/*     */     public EntityAINearestValidTarget(EntityCreature p_i45880_1_, Class p_i45880_2_, int p_i45880_3_, boolean p_i45880_4_, boolean p_i45880_5_, final Predicate tselector)
/*     */     {
/* 547 */       super(p_i45880_4_, p_i45880_5_);
/* 548 */       this.targetClass = p_i45880_2_;
/* 549 */       this.targetChance = p_i45880_3_;
/* 550 */       this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(p_i45880_1_);
/* 551 */       setMutexBits(1);
/* 552 */       this.targetEntitySelector = new Predicate()
/*     */       {
/*     */         private static final String __OBFID = "CL_00001621";
/*     */         
/*     */         public boolean applySelection(EntityLivingBase entity) {
/* 557 */           if ((tselector != null) && (!tselector.apply(entity)))
/*     */           {
/* 559 */             return false;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 564 */           if ((entity instanceof EntityPlayer))
/*     */           {
/* 566 */             double d0 = EntityTurretFocus.EntityAINearestValidTarget.this.getTargetDistance();
/*     */             
/* 568 */             if (entity.isSneaking())
/*     */             {
/* 570 */               d0 *= 0.800000011920929D;
/*     */             }
/*     */             
/* 573 */             if (entity.isInvisible())
/*     */             {
/* 575 */               float f = ((EntityPlayer)entity).getArmorVisibility();
/*     */               
/* 577 */               if (f < 0.1F)
/*     */               {
/* 579 */                 f = 0.1F;
/*     */               }
/*     */               
/* 582 */               d0 *= 0.7F * f;
/*     */             }
/*     */             
/* 585 */             if (entity.getDistanceToEntity(EntityTurretFocus.EntityAINearestValidTarget.this.taskOwner) > d0)
/*     */             {
/* 587 */               return false;
/*     */             }
/*     */           }
/*     */           
/* 591 */           return EntityTurretFocus.EntityAINearestValidTarget.this.isSuitableTarget(entity, false);
/*     */         }
/*     */         
/*     */         public boolean apply(Object p_apply_1_)
/*     */         {
/* 596 */           return applySelection((EntityLivingBase)p_apply_1_);
/*     */         }
/*     */       };
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean continueExecuting()
/*     */     {
/* 604 */       EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();
/* 605 */       if (entitylivingbase == null)
/*     */       {
/* 607 */         return false;
/*     */       }
/* 609 */       if (!entitylivingbase.isEntityAlive())
/*     */       {
/* 611 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 615 */       Team team = this.taskOwner.getTeam();
/* 616 */       Team team1 = entitylivingbase.getTeam();
/*     */       
/* 618 */       if ((team != null) && (team1 == team) && (!((EntityTurretFocus)this.taskOwner).getTargetFriendly()))
/*     */       {
/* 620 */         return false;
/*     */       }
/*     */       
/* 623 */       if ((team != null) && (team1 != team) && (((EntityTurretFocus)this.taskOwner).getTargetFriendly()))
/*     */       {
/* 625 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 629 */       double d0 = getTargetDistance();
/*     */       
/* 631 */       if (this.taskOwner.getDistanceSqToEntity(entitylivingbase) > d0 * d0)
/*     */       {
/* 633 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 637 */       if (this.shouldCheckSight)
/*     */       {
/* 639 */         if (this.taskOwner.getEntitySenses().canSee(entitylivingbase))
/*     */         {
/* 641 */           this.targetUnseenTicks = 0;
/*     */         }
/* 643 */         else if (++this.targetUnseenTicks > 60)
/*     */         {
/* 645 */           return false;
/*     */         }
/*     */       }
/*     */       
/* 649 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected boolean isSuitableTarget(EntityLivingBase p_75296_1_, boolean p_75296_2_)
/*     */     {
/* 658 */       if (!isGoodTarget(this.taskOwner, p_75296_1_, p_75296_2_, this.shouldCheckSight))
/*     */       {
/* 660 */         return false;
/*     */       }
/* 662 */       if (!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos(p_75296_1_)))
/*     */       {
/* 664 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 668 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private boolean isGoodTarget(EntityLiving attacker, EntityLivingBase posTar, boolean p_179445_2_, boolean checkSight)
/*     */     {
/* 675 */       if (posTar == null)
/*     */       {
/* 677 */         return false;
/*     */       }
/* 679 */       if (posTar == attacker)
/*     */       {
/* 681 */         return false;
/*     */       }
/* 683 */       if (!posTar.isEntityAlive())
/*     */       {
/* 685 */         return false;
/*     */       }
/* 687 */       if (!attacker.canAttackClass(posTar.getClass()))
/*     */       {
/* 689 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 693 */       Team team = attacker.getTeam();
/* 694 */       Team team1 = posTar.getTeam();
/* 695 */       if ((team != null) && (team1 == team) && (!((EntityTurretFocus)attacker).getTargetFriendly()))
/*     */       {
/* 697 */         return false;
/*     */       }
/*     */       
/* 700 */       if ((team != null) && (team1 != team) && (((EntityTurretFocus)attacker).getTargetFriendly()))
/*     */       {
/* 702 */         return false;
/*     */       }
/*     */       
/* 705 */       if (((attacker instanceof IEntityOwnable)) && (org.apache.commons.lang3.StringUtils.isNotEmpty(((IEntityOwnable)attacker).getOwnerId())))
/*     */       {
/* 707 */         if (((posTar instanceof IEntityOwnable)) && (((IEntityOwnable)attacker).getOwnerId().equals(((IEntityOwnable)posTar).getOwnerId())) && (!((EntityTurretFocus)attacker).getTargetFriendly()))
/*     */         {
/*     */ 
/*     */ 
/* 711 */           return false;
/*     */         }
/*     */         
/* 714 */         if ((!(posTar instanceof IEntityOwnable)) && (!(posTar instanceof EntityPlayer)) && (((EntityTurretFocus)attacker).getTargetFriendly())) {
/* 715 */           return false;
/*     */         }
/*     */         
/* 718 */         if (((posTar instanceof IEntityOwnable)) && (!((IEntityOwnable)attacker).getOwnerId().equals(((IEntityOwnable)posTar).getOwnerId())) && (((EntityTurretFocus)attacker).getTargetFriendly()))
/*     */         {
/*     */ 
/*     */ 
/* 722 */           return false;
/*     */         }
/*     */         
/* 725 */         if ((posTar == ((IEntityOwnable)attacker).getOwner()) && (!((EntityTurretFocus)attacker).getTargetFriendly()))
/*     */         {
/* 727 */           return false;
/*     */         }
/*     */         
/*     */       }
/* 731 */       else if (((posTar instanceof EntityPlayer)) && (!p_179445_2_) && (((EntityPlayer)posTar).capabilities.disableDamage) && (!((EntityTurretFocus)attacker).getTargetFriendly()))
/*     */       {
/*     */ 
/* 734 */         return false;
/*     */       }
/*     */       
/* 737 */       return (!checkSight) || (attacker.getEntitySenses().canSee(posTar));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public boolean shouldExecute()
/*     */     {
/* 744 */       if ((this.targetChance > 0) && (this.taskOwner.getRNG().nextInt(this.targetChance) != 0))
/*     */       {
/* 746 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 750 */       double d0 = getTargetDistance();
/* 751 */       List list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(d0, 4.0D, d0), Predicates.and(this.targetEntitySelector, EntitySelectors.NOT_SPECTATING));
/* 752 */       Collections.sort(list, this.theNearestAttackableTargetSorter);
/*     */       
/* 754 */       if (list.isEmpty())
/*     */       {
/* 756 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 760 */       this.targetEntity = ((EntityLivingBase)list.get(0));
/* 761 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void startExecuting()
/*     */     {
/* 771 */       this.taskOwner.setAttackTarget(this.targetEntity);
/* 772 */       this.targetUnseenTicks = 0;
/* 773 */       super.startExecuting();
/*     */     }
/*     */     
/*     */     public class Sorter implements Comparator
/*     */     {
/*     */       private final Entity theEntity;
/*     */       private static final String __OBFID = "CL_00001622";
/*     */       
/*     */       public Sorter(Entity p_i1662_1_)
/*     */       {
/* 783 */         this.theEntity = p_i1662_1_;
/*     */       }
/*     */       
/*     */       public int compare(Entity p_compare_1_, Entity p_compare_2_)
/*     */       {
/* 788 */         double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
/* 789 */         double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
/* 790 */         return d0 > d1 ? 1 : d0 < d1 ? -1 : 0;
/*     */       }
/*     */       
/*     */       public int compare(Object p_compare_1_, Object p_compare_2_)
/*     */       {
/* 795 */         return compare((Entity)p_compare_1_, (Entity)p_compare_2_);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\EntityTurretFocus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */