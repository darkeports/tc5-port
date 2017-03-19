/*     */ package thaumcraft.common.entities.construct.golem;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EntityTracker;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagLong;
/*     */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ import thaumcraft.api.golems.EnumGolemTrait;
/*     */ import thaumcraft.api.golems.IGolemProperties;
/*     */ import thaumcraft.api.golems.parts.GolemAddon;
/*     */ import thaumcraft.api.golems.parts.GolemArm;
/*     */ import thaumcraft.api.golems.parts.GolemArm.IArmFunction;
/*     */ import thaumcraft.api.golems.parts.GolemHead;
/*     */ import thaumcraft.api.golems.parts.GolemHead.IHeadFunction;
/*     */ import thaumcraft.api.golems.parts.GolemLeg;
/*     */ import thaumcraft.api.golems.parts.GolemMaterial;
/*     */ import thaumcraft.api.golems.tasks.Task;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.construct.EntityOwnedConstruct;
/*     */ import thaumcraft.common.entities.construct.golem.ai.AIFollowOwner;
/*     */ import thaumcraft.common.entities.construct.golem.ai.AIGotoBlock;
/*     */ import thaumcraft.common.entities.construct.golem.ai.AIGotoEntity;
/*     */ import thaumcraft.common.entities.construct.golem.ai.AIGotoHome;
/*     */ import thaumcraft.common.entities.construct.golem.ai.AIOwnerHurtByTarget;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class EntityThaumcraftGolem extends EntityOwnedConstruct implements thaumcraft.api.golems.IGolemAPI, IRangedAttackMob
/*     */ {
/*     */   public EntityThaumcraftGolem(World worldIn)
/*     */   {
/*  65 */     super(worldIn);
/*  66 */     setSize(0.4F, 0.9F);
/*  67 */     this.targetTasks.taskEntries.clear();
/*  68 */     this.tasks.addTask(2, new AIGotoEntity(this));
/*  69 */     this.tasks.addTask(3, new AIGotoBlock(this));
/*  70 */     this.tasks.addTask(4, new AIGotoHome(this));
/*  71 */     this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  72 */     this.tasks.addTask(6, new EntityAILookIdle(this));
/*     */     
/*  74 */     this.experienceValue = 5;
/*     */   }
/*     */   
/*  77 */   int rankXp = 0;
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  82 */     super.entityInit();
/*  83 */     this.dataWatcher.addObject(18, Integer.valueOf(0));
/*  84 */     this.dataWatcher.addObject(19, Integer.valueOf(0));
/*  85 */     this.dataWatcher.addObject(20, new Byte((byte)0));
/*  86 */     this.dataWatcher.addObject(21, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public IGolemProperties getProperties() {
/*  90 */     ByteBuffer bb = ByteBuffer.allocate(8);
/*  91 */     bb.putInt(this.dataWatcher.getWatchableObjectInt(18));
/*  92 */     bb.putInt(this.dataWatcher.getWatchableObjectInt(19));
/*  93 */     return GolemProperties.fromLong(bb.getLong(0));
/*     */   }
/*     */   
/*     */   public void setProperties(IGolemProperties prop) {
/*  97 */     ByteBuffer bb = ByteBuffer.allocate(8);
/*  98 */     bb.putLong(prop.toLong());
/*  99 */     bb.rewind();
/* 100 */     this.dataWatcher.updateObject(18, Integer.valueOf(bb.getInt()));
/* 101 */     this.dataWatcher.updateObject(19, Integer.valueOf(bb.getInt()));
/*     */   }
/*     */   
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/* 107 */     return 0.7F;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/* 113 */     super.applyEntityAttributes();
/*     */     
/* 115 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
/* 116 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/* 117 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
/* 118 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.0D);
/* 119 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void updateEntityAttributes()
/*     */   {
/* 126 */     int mh = 10 + getProperties().getMaterial().healthMod;
/* 127 */     if (getProperties().hasTrait(EnumGolemTrait.FRAGILE)) mh = (int)(mh * 0.75D);
/* 128 */     mh += getProperties().getRank();
/* 129 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(mh);
/*     */     
/*     */ 
/* 132 */     this.stepHeight = (getProperties().hasTrait(EnumGolemTrait.WHEELED) ? 0.5F : 0.6F);
/*     */     
/* 134 */     setHomePosAndDistance(getHomePosition() == BlockPos.ORIGIN ? getPosition() : getHomePosition(), getProperties().hasTrait(EnumGolemTrait.SCOUT) ? 32 : 16);
/*     */     
/* 136 */     this.navigator = getNewNavigator(this.worldObj);
/*     */     
/* 138 */     if ((getNavigator() instanceof PathNavigateGround)) {
/* 139 */       ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*     */     }
/*     */     
/*     */ 
/* 143 */     if (getProperties().hasTrait(EnumGolemTrait.FIGHTER)) {
/* 144 */       double da = getProperties().getMaterial().damage;
/* 145 */       if (getProperties().hasTrait(EnumGolemTrait.BRUTAL)) da = Math.max(da * 1.5D, da + 1.0D);
/* 146 */       da += getProperties().getRank() * 0.25D;
/* 147 */       getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(da);
/*     */     }
/*     */     else {
/* 150 */       getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.0D);
/*     */     }
/* 152 */     createAI();
/*     */   }
/*     */   
/*     */   private void createAI()
/*     */   {
/* 157 */     this.tasks.taskEntries.clear();
/* 158 */     this.targetTasks.taskEntries.clear();
/*     */     
/* 160 */     if (isFollowingOwner()) {
/* 161 */       this.tasks.addTask(4, new AIFollowOwner(this, 1.0D, 10.0F, 2.0F));
/*     */     } else {
/* 163 */       this.tasks.addTask(3, new AIGotoEntity(this));
/* 164 */       this.tasks.addTask(4, new AIGotoBlock(this));
/* 165 */       this.tasks.addTask(5, new AIGotoHome(this));
/*     */     }
/*     */     
/* 168 */     this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/* 169 */     this.tasks.addTask(9, new EntityAILookIdle(this));
/*     */     
/* 171 */     if (getProperties().hasTrait(EnumGolemTrait.FIGHTER))
/*     */     {
/* 173 */       if ((getNavigator() instanceof PathNavigateGround)) {
/* 174 */         this.tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
/*     */       }
/*     */       
/* 177 */       if (getProperties().hasTrait(EnumGolemTrait.RANGED)) {
/* 178 */         EntityAIArrowAttack aa = null;
/* 179 */         if (getProperties().getArms().function != null) {
/* 180 */           aa = getProperties().getArms().function.getRangedAttackAI(this);
/*     */         }
/* 182 */         if (aa != null) {
/* 183 */           this.tasks.addTask(1, aa);
/*     */         }
/*     */       }
/* 186 */       this.tasks.addTask(2, new thaumcraft.common.entities.ai.combat.AIAttackOnCollide(this, EntityLivingBase.class, 1.15D, false));
/*     */       
/* 188 */       if (isFollowingOwner()) {
/* 189 */         this.targetTasks.addTask(1, new AIOwnerHurtByTarget(this));
/* 190 */         this.targetTasks.addTask(2, new thaumcraft.common.entities.construct.golem.ai.AIOwnerHurtTarget(this));
/*     */       }
/*     */       
/* 193 */       this.targetTasks.addTask(3, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isOnLadder()
/*     */   {
/* 201 */     return isBesideClimbableBlock();
/*     */   }
/*     */   
/*     */ 
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance diff, IEntityLivingData ld)
/*     */   {
/* 207 */     setHomePosAndDistance(getPosition(), 16);
/* 208 */     updateEntityAttributes();
/* 209 */     return ld;
/*     */   }
/*     */   
/*     */   public int getTotalArmorValue()
/*     */   {
/* 214 */     int armor = getProperties().getMaterial().armor;
/* 215 */     if (getProperties().hasTrait(EnumGolemTrait.ARMORED)) armor = (int)Math.max(armor * 1.5D, armor + 1);
/* 216 */     if (getProperties().hasTrait(EnumGolemTrait.FRAGILE)) armor = (int)(armor * 0.75D);
/* 217 */     return armor;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onLivingUpdate()
/*     */   {
/* 223 */     updateArmSwingProgress();
/* 224 */     super.onLivingUpdate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 236 */     super.onUpdate();
/*     */     
/* 238 */     if (!this.worldObj.isRemote) {
/* 239 */       if ((this.task != null) && (this.task.isSuspended())) { this.task = null;
/*     */       }
/* 241 */       if ((getAttackTarget() != null) && (getAttackTarget().isDead)) { setAttackTarget(null);
/*     */       }
/* 243 */       if ((getAttackTarget() != null) && (getProperties().hasTrait(EnumGolemTrait.RANGED)) && (getDistanceSqToEntity(getAttackTarget()) > 1024.0D)) {
/* 244 */         setAttackTarget(null);
/*     */       }
/* 246 */       if ((!MinecraftServer.getServer().isPVPEnabled()) && 
/* 247 */         (getAttackTarget() != null) && ((getAttackTarget() instanceof EntityPlayer))) { setAttackTarget(null);
/*     */       }
/*     */       
/* 250 */       if (this.ticksExisted % (getProperties().hasTrait(EnumGolemTrait.REPAIR) ? 40 : 100) == 0) { heal(1.0F);
/*     */       }
/* 252 */       if (getProperties().hasTrait(EnumGolemTrait.CLIMBER)) {
/* 253 */         setBesideClimbableBlock((!this.isSwingInProgress) && (this.isCollidedHorizontally));
/*     */       }
/*     */     }
/* 256 */     if (getProperties().getHead().function != null)
/* 257 */       getProperties().getHead().function.onUpdateTick(this);
/* 258 */     if (getProperties().getArms().function != null)
/* 259 */       getProperties().getArms().function.onUpdateTick(this);
/* 260 */     if (getProperties().getLegs().function != null)
/* 261 */       getProperties().getLegs().function.onUpdateTick(this);
/* 262 */     if (getProperties().getAddon().function != null) {
/* 263 */       getProperties().getAddon().function.onUpdateTick(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public void handleStatusUpdate(byte par1)
/*     */   {
/* 273 */     if (par1 == 5)
/*     */     {
/* 275 */       Thaumcraft.proxy.getFX().drawGenericParticles(this.posX, this.posY + this.height + 0.1D, this.posZ, 0.0D, 0.0D, 0.0D, 1.0F, 1.0F, 1.0F, 0.5F, false, '°' + (this.rand.nextBoolean() ? 0 : 3), 3, 1, 6, 0, 2.0F, 0.0F, 1);
/*     */ 
/*     */ 
/*     */     }
/* 279 */     else if (par1 == 6)
/*     */     {
/* 281 */       Thaumcraft.proxy.getFX().drawGenericParticles(this.posX, this.posY + this.height + 0.1D, this.posZ, 0.0D, 0.025D, 0.0D, 0.1F, 1.0F, 1.0F, 0.5F, false, 15, 1, 1, 10, 0, 2.0F, 0.0F, 1);
/*     */ 
/*     */ 
/*     */     }
/* 285 */     else if (par1 == 7)
/*     */     {
/* 287 */       Thaumcraft.proxy.getFX().drawGenericParticles(this.posX, this.posY + this.height + 0.1D, this.posZ, 0.0D, 0.05D, 0.0D, 1.0F, 1.0F, 1.0F, 0.5F, false, 160, 10, 1, 10, 0, 2.0F, 0.0F, 1);
/*     */ 
/*     */ 
/*     */     }
/* 291 */     else if (par1 == 8)
/*     */     {
/* 293 */       Thaumcraft.proxy.getFX().drawGenericParticles(this.posX, this.posY + this.height + 0.1D, this.posZ, 0.0D, 0.01D, 0.0D, 1.0F, 1.0F, 0.1F, 0.5F, false, 14, 1, 1, 20, 0, 2.0F, 0.0F, 1);
/*     */ 
/*     */ 
/*     */     }
/* 297 */     else if (par1 == 9)
/*     */     {
/* 299 */       for (int a = 0; a < 5; a++) {
/* 300 */         Thaumcraft.proxy.getFX().drawGenericParticles(this.posX, this.posY + this.height, this.posZ, this.rand.nextGaussian() * 0.009999999776482582D, this.rand.nextFloat() * 0.02D, this.rand.nextGaussian() * 0.009999999776482582D, 1.0F, 1.0F, 1.0F, 0.5F, false, 13, 1, 1, 20 + this.rand.nextInt(20), 0, 0.3F + this.rand.nextFloat() * 0.4F, 0.0F, 1);
/*     */ 
/*     */       }
/*     */       
/*     */     }
/*     */     else
/*     */     {
/* 307 */       super.handleStatusUpdate(par1);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getGolemMoveSpeed() {
/* 312 */     return 1.0F + getProperties().getRank() * 0.025F + (getProperties().hasTrait(EnumGolemTrait.LIGHT) ? 0.2F : 0.0F) + (getProperties().hasTrait(EnumGolemTrait.HEAVY) ? -0.175F : 0.0F) + (getProperties().hasTrait(EnumGolemTrait.FLYER) ? -0.33F : 0.0F) + (getProperties().hasTrait(EnumGolemTrait.WHEELED) ? 0.25F : 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected net.minecraft.pathfinding.PathNavigate getNewNavigator(World worldIn)
/*     */   {
/* 322 */     return getProperties().hasTrait(EnumGolemTrait.CLIMBER) ? new net.minecraft.pathfinding.PathNavigateClimber(this, worldIn) : getProperties().hasTrait(EnumGolemTrait.FLYER) ? new thaumcraft.common.entities.construct.golem.ai.PathNavigateGolemAir(this, worldIn) : new thaumcraft.common.entities.construct.golem.ai.PathNavigateGolemGround(this, worldIn);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void moveEntityWithHeading(float f1, float f2)
/*     */   {
/* 330 */     if (isServerWorld())
/*     */     {
/* 332 */       if (getProperties().hasTrait(EnumGolemTrait.FLYER))
/*     */       {
/* 334 */         moveFlying(f1, f2, 0.1F);
/* 335 */         moveEntity(this.motionX, this.motionY, this.motionZ);
/* 336 */         this.motionX *= 0.8999999761581421D;
/* 337 */         this.motionY *= 0.8999999761581421D;
/* 338 */         this.motionZ *= 0.8999999761581421D;
/*     */         
/* 340 */         if (getAttackTarget() == null)
/*     */         {
/* 342 */           this.motionY -= 0.005D;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 347 */         super.moveEntityWithHeading(f1, f2);
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/* 352 */       super.moveEntityWithHeading(f1, f2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/* 365 */     return (getProperties().hasTrait(EnumGolemTrait.HEAVY)) && (!getProperties().hasTrait(EnumGolemTrait.FLYER));
/*     */   }
/*     */   
/*     */   public void fall(float distance, float damageMultiplier)
/*     */   {
/* 370 */     if ((!getProperties().hasTrait(EnumGolemTrait.FLYER)) && (!getProperties().hasTrait(EnumGolemTrait.CLIMBER))) {
/* 371 */       super.fall(distance, damageMultiplier);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/* 380 */     super.readEntityFromNBT(nbt);
/* 381 */     setProperties(GolemProperties.fromLong(nbt.getLong("props")));
/*     */     
/* 383 */     setHomePosAndDistance(BlockPos.fromLong(nbt.getLong("homepos")), 16);
/*     */     
/* 385 */     this.dataWatcher.updateObject(20, Byte.valueOf(nbt.getByte("gflags")));
/*     */     
/* 387 */     this.rankXp = nbt.getInteger("rankXP");
/*     */     
/* 389 */     setGolemColor(nbt.getByte("color"));
/*     */     
/* 391 */     updateEntityAttributes();
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/* 397 */     super.writeEntityToNBT(nbt);
/*     */     
/* 399 */     nbt.setLong("props", getProperties().toLong());
/*     */     
/* 401 */     nbt.setLong("homepos", getHomePosition().toLong());
/*     */     
/* 403 */     nbt.setByte("gflags", this.dataWatcher.getWatchableObjectByte(20));
/*     */     
/* 405 */     nbt.setInteger("rankXP", this.rankXp);
/*     */     
/* 407 */     nbt.setByte("color", getGolemColor());
/*     */   }
/*     */   
/*     */ 
/*     */   protected void damageEntity(DamageSource ds, float damage)
/*     */   {
/* 413 */     if ((ds.isFireDamage()) && (getProperties().hasTrait(EnumGolemTrait.FIREPROOF))) {
/* 414 */       return;
/*     */     }
/* 416 */     if ((ds.isExplosion()) && (getProperties().hasTrait(EnumGolemTrait.BLASTPROOF))) {
/* 417 */       damage = Math.min(getMaxHealth() / 2.0F, damage * 0.3F);
/*     */     }
/* 419 */     if (ds == DamageSource.cactus) return;
/* 420 */     if ((ds == DamageSource.inWall) || (ds == DamageSource.outOfWorld)) {
/* 421 */       setLocationAndAngles(getHomePosition().getX() + 0.5D, getHomePosition().getY() + 0.5D, getHomePosition().getZ() + 0.5D, 0.0F, 0.0F);
/*     */     }
/* 423 */     super.damageEntity(ds, damage);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean interact(EntityPlayer player)
/*     */   {
/* 429 */     if ((player.isSneaking()) || ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof net.minecraft.item.ItemNameTag))))
/* 430 */       return false;
/* 431 */     if ((!this.worldObj.isRemote) && (isOwner(player)) && (!this.isDead)) {
/* 432 */       if ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof IWand))) {
/* 433 */         playSound("thaumcraft:zap", 1.0F, 1.0F);
/* 434 */         if (this.task != null) this.task.setReserved(false);
/* 435 */         dropCarried();
/* 436 */         ItemStack placer = new ItemStack(ItemsTC.golemPlacer);
/* 437 */         placer.setTagInfo("props", new NBTTagLong(getProperties().toLong()));
/* 438 */         placer.setTagInfo("xp", new net.minecraft.nbt.NBTTagInt(this.rankXp));
/* 439 */         entityDropItem(placer, 0.5F);
/* 440 */         setDead();
/* 441 */         player.swingItem();
/*     */       }
/* 443 */       else if ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof ItemGolemBell)) && (thaumcraft.api.research.ResearchHelper.isResearchComplete(player.getName(), "GOLEMDIRECT")))
/*     */       {
/* 445 */         if (this.task != null) this.task.setReserved(false);
/* 446 */         playSound("thaumcraft:scan", 1.0F, 1.0F);
/* 447 */         setFollowingOwner(!isFollowingOwner());
/* 448 */         if (isFollowingOwner()) {
/* 449 */           player.addChatMessage(new ChatComponentTranslation("golem.follow", new Object[] { "" }));
/* 450 */           if (Config.showGolemEmotes) this.worldObj.setEntityState(this, (byte)5);
/*     */         } else {
/* 452 */           player.addChatMessage(new ChatComponentTranslation("golem.stay", new Object[] { "" }));
/* 453 */           if (Config.showGolemEmotes) this.worldObj.setEntityState(this, (byte)8);
/* 454 */           setHomePosAndDistance(getPosition(), 16);
/*     */         }
/* 456 */         updateEntityAttributes();
/*     */         
/* 458 */         player.swingItem();
/*     */       }
/* 460 */       else if (player.getHeldItem() != null) {
/* 461 */         int[] ids = OreDictionary.getOreIDs(player.getHeldItem());
/* 462 */         if ((ids != null) && (ids.length > 0)) {
/* 463 */           for (int id : ids) {
/* 464 */             String s = OreDictionary.getOreName(id);
/* 465 */             if (s.startsWith("dye")) {
/* 466 */               for (int a = 0; a < thaumcraft.common.config.ConfigAspects.dyes.length; a++) {
/* 467 */                 if (s.equals(thaumcraft.common.config.ConfigAspects.dyes[a])) {
/* 468 */                   playSound("thaumcraft:zap", 1.0F, 1.0F);
/* 469 */                   setGolemColor((byte)(16 - a));
/* 470 */                   player.getHeldItem().stackSize -= 1;
/* 471 */                   player.swingItem();
/* 472 */                   return true;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 479 */       return true;
/*     */     }
/*     */     
/* 482 */     return super.interact(player);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDeath(DamageSource cause)
/*     */   {
/* 488 */     if (this.task != null) this.task.setReserved(false);
/* 489 */     super.onDeath(cause);
/*     */     
/* 491 */     if (!this.worldObj.isRemote)
/*     */     {
/* 493 */       dropCarried();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void dropCarried() {
/* 498 */     for (ItemStack s : getCarrying()) {
/* 499 */       if (s != null) entityDropItem(s, 0.25F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
/*     */   {
/* 505 */     float b = p_70628_2_ * 0.15F;
/* 506 */     for (ItemStack stack : getProperties().generateComponents()) {
/* 507 */       ItemStack s = stack.copy();
/* 508 */       if (this.rand.nextFloat() < 0.3F + b) {
/* 509 */         if (s.stackSize > 0) s.stackSize -= this.rand.nextInt(s.stackSize);
/* 510 */         entityDropItem(s, 0.25F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isBesideClimbableBlock()
/*     */   {
/* 518 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(20), 0);
/*     */   }
/*     */   
/*     */   public void setBesideClimbableBlock(boolean p_70839_1_)
/*     */   {
/* 523 */     byte b0 = this.dataWatcher.getWatchableObjectByte(20);
/*     */     
/* 525 */     if (p_70839_1_)
/*     */     {
/* 527 */       b0 = (byte)Utils.setBit(b0, 0);
/*     */     }
/*     */     else
/*     */     {
/* 531 */       b0 = (byte)Utils.clearBit(b0, 0);
/*     */     }
/*     */     
/* 534 */     this.dataWatcher.updateObject(20, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public boolean isFollowingOwner()
/*     */   {
/* 539 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(20), 1);
/*     */   }
/*     */   
/*     */   public void setFollowingOwner(boolean par1)
/*     */   {
/* 544 */     byte var2 = this.dataWatcher.getWatchableObjectByte(20);
/*     */     
/* 546 */     if (par1)
/*     */     {
/* 548 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.setBit(var2, 1)));
/*     */     }
/*     */     else
/*     */     {
/* 552 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.clearBit(var2, 1)));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setAttackTarget(EntityLivingBase entitylivingbaseIn)
/*     */   {
/* 558 */     super.setAttackTarget(entitylivingbaseIn);
/* 559 */     setInCombat(getAttackTarget() != null);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isInCombat()
/*     */   {
/* 565 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(20), 3);
/*     */   }
/*     */   
/*     */   public void setInCombat(boolean par1)
/*     */   {
/* 570 */     byte var2 = this.dataWatcher.getWatchableObjectByte(20);
/*     */     
/* 572 */     if (par1)
/*     */     {
/* 574 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.setBit(var2, 3)));
/*     */     }
/*     */     else
/*     */     {
/* 578 */       this.dataWatcher.updateObject(20, Byte.valueOf((byte)Utils.clearBit(var2, 3)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean attackEntityAsMob(Entity ent)
/*     */   {
/* 586 */     float dmg = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
/* 587 */     int kb = 0;
/*     */     
/* 589 */     if ((ent instanceof EntityLivingBase))
/*     */     {
/* 591 */       dmg += EnchantmentHelper.func_152377_a(getHeldItem(), ((EntityLivingBase)ent).getCreatureAttribute());
/* 592 */       kb += EnchantmentHelper.getKnockbackModifier(this);
/*     */     }
/*     */     
/* 595 */     boolean flag = ent.attackEntityFrom(DamageSource.causeMobDamage(this), dmg);
/*     */     
/* 597 */     if (flag)
/*     */     {
/* 599 */       if (((ent instanceof EntityLivingBase)) && (getProperties().hasTrait(EnumGolemTrait.DEFT))) {
/* 600 */         thaumcraft.common.lib.utils.EntityUtils.setRecentlyHit((EntityLivingBase)ent, 100);
/*     */       }
/* 602 */       if (kb > 0)
/*     */       {
/* 604 */         ent.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * kb * 0.5F, 0.1D, MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * kb * 0.5F);
/* 605 */         this.motionX *= 0.6D;
/* 606 */         this.motionZ *= 0.6D;
/*     */       }
/*     */       
/* 609 */       int j = EnchantmentHelper.getFireAspectModifier(this);
/*     */       
/* 611 */       if (j > 0)
/*     */       {
/* 613 */         ent.setFire(j * 4);
/*     */       }
/*     */       
/* 616 */       applyEnchantments(this, ent);
/*     */       
/* 618 */       if (getProperties().getArms().function != null) {
/* 619 */         getProperties().getArms().function.onMeleeAttack(this, ent);
/*     */       }
/*     */       
/* 622 */       if (((ent instanceof EntityLiving)) && (!((EntityLiving)ent).isEntityAlive())) {
/* 623 */         addRankXp(8);
/*     */       }
/*     */     }
/*     */     
/* 627 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 633 */   protected Task task = null;
/* 634 */   protected int taskID = Integer.MAX_VALUE;
/*     */   public static final int XPM = 1000;
/*     */   
/* 637 */   public Task getTask() { if ((this.task == null) && (this.taskID != Integer.MAX_VALUE)) {
/* 638 */       this.task = thaumcraft.common.entities.construct.golem.tasks.TaskHandler.getTask(this.worldObj.provider.getDimensionId(), this.taskID);
/* 639 */       this.taskID = Integer.MAX_VALUE;
/*     */     }
/* 641 */     return this.task;
/*     */   }
/*     */   
/*     */   public void setTask(Task task) {
/* 645 */     this.task = task;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addRankXp(int xp)
/*     */   {
/* 654 */     if ((!getProperties().hasTrait(EnumGolemTrait.SMART)) || (this.worldObj.isRemote)) return;
/* 655 */     int rank = getProperties().getRank();
/* 656 */     if (rank < 10) {
/* 657 */       this.rankXp += xp;
/* 658 */       int xn = (rank + 1) * (rank + 1) * 1000;
/* 659 */       if (this.rankXp >= xn) {
/* 660 */         this.rankXp -= xn;
/* 661 */         IGolemProperties props = getProperties();
/* 662 */         props.setRank(rank + 1);
/* 663 */         setProperties(props);
/* 664 */         if (Config.showGolemEmotes) {
/* 665 */           this.worldObj.setEntityState(this, (byte)9);
/* 666 */           this.worldObj.playSoundAtEntity(this, "random.levelup", 0.25F, 1.0F);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack holdItem(ItemStack stack)
/*     */   {
/* 674 */     if ((stack == null) || (stack.stackSize <= 0)) return null;
/* 675 */     for (int a = 0; a < (getProperties().hasTrait(EnumGolemTrait.HAULER) ? 2 : 1); a++) {
/* 676 */       if (getInventory()[a] == null) {
/* 677 */         setCurrentItemOrArmor(a, stack);
/* 678 */         return null;
/*     */       }
/* 680 */       if ((getInventory()[a].stackSize < getInventory()[a].getMaxStackSize()) && (ItemStack.areItemsEqual(getInventory()[a], stack)) && (ItemStack.areItemStackTagsEqual(getInventory()[a], stack)))
/*     */       {
/*     */ 
/* 683 */         int d = Math.min(stack.stackSize, getInventory()[a].getMaxStackSize() - getInventory()[a].stackSize);
/* 684 */         stack.stackSize -= d;
/* 685 */         getInventory()[a].stackSize += d;
/* 686 */         if (stack.stackSize <= 0) { stack = null;
/*     */         }
/*     */       }
/*     */     }
/* 690 */     return stack;
/*     */   }
/*     */   
/*     */   public ItemStack dropItem(ItemStack stack)
/*     */   {
/* 695 */     ItemStack out = null;
/* 696 */     for (int a = 0; a < (getProperties().hasTrait(EnumGolemTrait.HAULER) ? 2 : 1); a++)
/* 697 */       if (getInventory()[a] != null) {
/* 698 */         if (stack == null) {
/* 699 */           out = getInventory()[a].copy();
/*     */           
/* 701 */           setCurrentItemOrArmor(a, null);
/*     */         }
/* 703 */         else if ((ItemStack.areItemsEqual(getInventory()[a], stack)) && (ItemStack.areItemStackTagsEqual(getInventory()[a], stack)))
/*     */         {
/* 705 */           out = getInventory()[a].copy();
/* 706 */           out.stackSize = Math.min(stack.stackSize, out.stackSize);
/* 707 */           getInventory()[a].stackSize -= stack.stackSize;
/* 708 */           if (getInventory()[a].stackSize <= 0) {
/* 709 */             setCurrentItemOrArmor(a, null);
/*     */           }
/*     */         }
/* 712 */         if (out != null) break;
/*     */       }
/* 714 */     if ((getProperties().hasTrait(EnumGolemTrait.HAULER)) && (getInventory()[0] == null) && (getInventory()[1] != null))
/*     */     {
/* 716 */       setCurrentItemOrArmor(0, getInventory()[1].copy());
/* 717 */       setCurrentItemOrArmor(1, null);
/*     */     }
/*     */     
/* 720 */     return out;
/*     */   }
/*     */   
/*     */   public boolean canCarry(ItemStack stack, boolean partial)
/*     */   {
/* 725 */     for (int a = 0; a < (getProperties().hasTrait(EnumGolemTrait.HAULER) ? 2 : 1); a++) {
/* 726 */       if (getInventory()[a] == null) return true;
/* 727 */       if ((getInventory()[a].stackSize < getInventory()[a].getMaxStackSize()) && (ItemStack.areItemsEqual(getInventory()[a], stack)) && (ItemStack.areItemStackTagsEqual(getInventory()[a], stack)))
/*     */       {
/*     */ 
/* 730 */         return true; }
/*     */     }
/* 732 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isCarrying(ItemStack stack)
/*     */   {
/* 737 */     if (stack == null) return false;
/* 738 */     for (int a = 0; a < (getProperties().hasTrait(EnumGolemTrait.HAULER) ? 2 : 1); a++) {
/* 739 */       if ((getInventory()[a] != null) && (getInventory()[a].stackSize > 0) && 
/* 740 */         (ItemStack.areItemsEqual(getInventory()[a], stack)) && (ItemStack.areItemStackTagsEqual(getInventory()[a], stack)))
/* 741 */         return true;
/*     */     }
/* 743 */     return false;
/*     */   }
/*     */   
/*     */   public ItemStack[] getCarrying()
/*     */   {
/* 748 */     if (getProperties().hasTrait(EnumGolemTrait.HAULER))
/* 749 */       return new ItemStack[] { getInventory()[0], getInventory()[1] };
/* 750 */     return new ItemStack[] { getInventory()[0] };
/*     */   }
/*     */   
/*     */   public EntityLivingBase getGolemEntity()
/*     */   {
/* 755 */     return this;
/*     */   }
/*     */   
/*     */   public World getGolemWorld()
/*     */   {
/* 760 */     return getEntityWorld();
/*     */   }
/*     */   
/*     */ 
/*     */   public void swingArm()
/*     */   {
/* 766 */     if ((!this.isSwingInProgress) || (this.swingProgressInt >= 3) || (this.swingProgressInt < 0))
/*     */     {
/* 768 */       this.swingProgressInt = -1;
/* 769 */       this.isSwingInProgress = true;
/*     */       
/* 771 */       if ((this.worldObj instanceof WorldServer))
/*     */       {
/* 773 */         ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S0BPacketAnimation(this, 0));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float range)
/*     */   {
/* 781 */     if (getProperties().getArms().function != null) {
/* 782 */       getProperties().getArms().function.onRangedAttack(this, target, range);
/*     */     }
/*     */   }
/*     */   
/*     */   public byte getGolemColor()
/*     */   {
/* 788 */     return this.dataWatcher.getWatchableObjectByte(21);
/*     */   }
/*     */   
/*     */   public void setGolemColor(byte b) {
/* 792 */     this.dataWatcher.updateObject(21, Byte.valueOf(b));
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\EntityThaumcraftGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */