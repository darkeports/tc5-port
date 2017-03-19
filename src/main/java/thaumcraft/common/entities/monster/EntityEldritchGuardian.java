/*     */ package thaumcraft.common.entities.monster;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.entities.IEldritchMob;
/*     */ import thaumcraft.api.internal.EnumWarpType;
/*     */ import thaumcraft.api.items.ItemGenericEssentiaContainer;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.ai.combat.AILongRangeAttack;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultist;
/*     */ import thaumcraft.common.entities.projectile.EntityEldritchOrb;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXSonic;
/*     */ import thaumcraft.common.lib.network.misc.PacketMiscEvent;
/*     */ 
/*     */ public class EntityEldritchGuardian extends EntityMob implements IRangedAttackMob, IEldritchMob
/*     */ {
/*     */   public EntityEldritchGuardian(World p_i1745_1_)
/*     */   {
/*  59 */     super(p_i1745_1_);
/*  60 */     ((PathNavigateGround)getNavigator()).setBreakDoors(true);
/*  61 */     this.tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
/*  62 */     this.tasks.addTask(2, new AILongRangeAttack(this, 8.0D, 1.0D, 20, 40, 24.0F));
/*  63 */     this.tasks.addTask(3, new thaumcraft.common.entities.ai.combat.AIAttackOnCollide(this, EntityLivingBase.class, 1.0D, false));
/*  64 */     this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.8D));
/*  65 */     this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
/*  66 */     this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  67 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/*  68 */     this.targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/*  69 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  70 */     this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityCultist.class, true));
/*  71 */     setSize(0.8F, 2.25F);
/*  72 */     this.experienceValue = 20;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  78 */     super.applyEntityAttributes();
/*  79 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
/*  80 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
/*  81 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
/*  82 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  88 */     super.entityInit();
/*  89 */     getDataWatcher().addObject(12, Byte.valueOf((byte)0));
/*  90 */     getDataWatcher().addObject(13, Byte.valueOf((byte)0));
/*  91 */     getDataWatcher().addObject(14, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getTotalArmorValue()
/*     */   {
/* 100 */     return 4;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canPickUpLoot()
/*     */   {
/* 106 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource source, float damage)
/*     */   {
/* 115 */     if (source.isMagicDamage()) {
/* 116 */       damage /= 2.0F;
/*     */     }
/* 118 */     return super.attackEntityFrom(source, damage);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 128 */     super.onUpdate();
/*     */     
/* 130 */     if (this.worldObj.isRemote) {
/* 131 */       if (this.armLiftL > 0.0F) this.armLiftL -= 0.05F;
/* 132 */       if (this.armLiftR > 0.0F) this.armLiftR -= 0.05F;
/* 133 */       float x = (float)(this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 134 */       float z = (float)(this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 135 */       Thaumcraft.proxy.getFX().wispFXEG(x, (float)(this.posY + 0.22D * this.height), z, this);
/*     */ 
/*     */     }
/* 138 */     else if (this.worldObj.provider.getDimensionId() != Config.dimensionOuterId)
/*     */     {
/* 140 */       if (((this.ticksExisted == 0) || (this.ticksExisted % 100 == 0)) && (this.worldObj.getDifficulty() != EnumDifficulty.EASY))
/*     */       {
/* 142 */         double d6 = this.worldObj.getDifficulty() == EnumDifficulty.HARD ? 576.0D : 256.0D;
/* 143 */         for (int i = 0; i < this.worldObj.playerEntities.size(); i++)
/*     */         {
/* 145 */           EntityPlayer entityplayer1 = (EntityPlayer)this.worldObj.playerEntities.get(i);
/*     */           
/* 147 */           if (entityplayer1.isEntityAlive())
/*     */           {
/* 149 */             double d5 = entityplayer1.getDistanceSq(this.posX, this.posY, this.posZ);
/*     */             
/* 151 */             if (d5 < d6)
/*     */             {
/* 153 */               PacketHandler.INSTANCE.sendTo(new PacketMiscEvent((short)2), (net.minecraft.entity.player.EntityPlayerMP)entityplayer1);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean attackEntityAsMob(Entity p_70652_1_)
/*     */   {
/* 168 */     boolean flag = super.attackEntityAsMob(p_70652_1_);
/*     */     
/* 170 */     if (flag)
/*     */     {
/* 172 */       int i = this.worldObj.getDifficulty().getDifficultyId();
/*     */       
/* 174 */       if ((getHeldItem() == null) && (isBurning()) && (this.rand.nextFloat() < i * 0.3F))
/*     */       {
/* 176 */         p_70652_1_.setFire(2 * i);
/*     */       }
/*     */     }
/*     */     
/* 180 */     return flag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 189 */     return "thaumcraft:egidle";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 199 */     return "thaumcraft:egdeath";
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTalkInterval()
/*     */   {
/* 205 */     return 500;
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 211 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addRandomDrop()
/*     */   {
/* 219 */     super.addRandomDrop();
/*     */   }
/*     */   
/*     */ 
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 225 */     if (this.rand.nextBoolean()) {
/* 226 */       ItemStack ess = new ItemStack(ItemsTC.wispyEssence);
/* 227 */       AspectList al = new AspectList();
/* 228 */       ((ItemGenericEssentiaContainer)ess.getItem()).setAspects(ess, new AspectList().add(Aspect.UNDEAD, 2));
/* 229 */       entityDropItem(ess, 1.0F);
/*     */     }
/*     */     
/* 232 */     if (this.rand.nextBoolean()) {
/* 233 */       ItemStack ess = new ItemStack(ItemsTC.wispyEssence);
/* 234 */       AspectList al = new AspectList();
/* 235 */       ((ItemGenericEssentiaContainer)ess.getItem()).setAspects(ess, new AspectList().add(Aspect.ELDRITCH, 2));
/* 236 */       entityDropItem(ess, 1.0F);
/*     */     }
/*     */     
/* 239 */     if (this.rand.nextInt(10 + (this.worldObj.provider.getDimensionId() == Config.dimensionOuterId ? 10 : 0)) <= i / 2.0F) {
/* 240 */       dropItem(ItemsTC.eldritchEye, 1);
/*     */     }
/*     */     
/* 243 */     super.dropFewItems(flag, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumCreatureAttribute getCreatureAttribute()
/*     */   {
/* 252 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/* 261 */     super.writeEntityToNBT(nbt);
/* 262 */     if ((getHomePosition() != null) && (getMaximumHomeDistance() > 0.0F)) {
/* 263 */       nbt.setInteger("HomeD", (int)getMaximumHomeDistance());
/* 264 */       nbt.setInteger("HomeX", getHomePosition().getX());
/* 265 */       nbt.setInteger("HomeY", getHomePosition().getY());
/* 266 */       nbt.setInteger("HomeZ", getHomePosition().getZ());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/* 277 */     super.readEntityFromNBT(nbt);
/* 278 */     if (nbt.hasKey("HomeD")) {
/* 279 */       setHomePosAndDistance(new BlockPos(nbt.getInteger("HomeX"), nbt.getInteger("HomeY"), nbt.getInteger("HomeZ")), nbt.getInteger("HomeD"));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance diff, IEntityLivingData data)
/*     */   {
/* 287 */     IEntityLivingData dd = super.onInitialSpawn(diff, data);
/* 288 */     float f = diff.getClampedAdditionalDifficulty();
/* 289 */     if (this.worldObj.provider.getDimensionId() == Config.dimensionOuterId) {
/* 290 */       int bh = (int)getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() / 2;
/* 291 */       setAbsorptionAmount(getAbsorptionAmount() + bh);
/*     */     }
/* 293 */     return dd;
/*     */   }
/*     */   
/*     */   protected void updateAITasks()
/*     */   {
/* 298 */     super.updateAITasks();
/* 299 */     if ((this.worldObj.provider.getDimensionId() == Config.dimensionOuterId) && (this.hurtResistantTime <= 0) && (this.ticksExisted % 25 == 0)) {
/* 300 */       int bh = (int)getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() / 2;
/* 301 */       if (getAbsorptionAmount() < bh)
/* 302 */         setAbsorptionAmount(getAbsorptionAmount() + 1.0F);
/*     */     }
/*     */   }
/*     */   
/* 306 */   public float armLiftL = 0.0F;
/* 307 */   public float armLiftR = 0.0F;
/*     */   
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public void handleStatusUpdate(byte p_70103_1_)
/*     */   {
/* 313 */     if (p_70103_1_ == 15)
/*     */     {
/* 315 */       this.armLiftL = 0.5F;
/*     */ 
/*     */     }
/* 318 */     else if (p_70103_1_ == 16)
/*     */     {
/* 320 */       this.armLiftR = 0.5F;
/*     */     }
/* 322 */     else if (p_70103_1_ == 17)
/*     */     {
/* 324 */       this.armLiftL = 0.9F;
/* 325 */       this.armLiftR = 0.9F;
/*     */     }
/*     */     else
/*     */     {
/* 329 */       super.handleStatusUpdate(p_70103_1_);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean canDespawn()
/*     */   {
/* 339 */     return !hasHome();
/*     */   }
/*     */   
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/* 345 */     return 2.1F;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getCanSpawnHere()
/*     */   {
/* 351 */     List ents = this.worldObj.getEntitiesWithinAABB(EntityEldritchGuardian.class, AxisAlignedBB.fromBounds(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(32.0D, 16.0D, 32.0D));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 357 */     return ents.size() > 0 ? false : super.getCanSpawnHere();
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean isValidLightLevel()
/*     */   {
/* 363 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   protected float getSoundVolume()
/*     */   {
/* 369 */     return 1.5F;
/*     */   }
/*     */   
/* 372 */   boolean lastBlast = false;
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float f)
/*     */   {
/* 376 */     if (this.rand.nextFloat() > 0.1F) {
/* 377 */       EntityEldritchOrb blast = new EntityEldritchOrb(this.worldObj, this);
/* 378 */       this.lastBlast = (!this.lastBlast);
/*     */       
/* 380 */       this.worldObj.setEntityState(this, (byte)(this.lastBlast ? 16 : 15));
/*     */       
/* 382 */       int rr = this.lastBlast ? 90 : 180;
/* 383 */       double xx = MathHelper.cos((this.rotationYaw + rr) % 360.0F / 180.0F * 3.1415927F) * 0.5F;
/* 384 */       double yy = 0.057777777D * this.height;
/* 385 */       double zz = MathHelper.sin((this.rotationYaw + rr) % 360.0F / 180.0F * 3.1415927F) * 0.5F;
/* 386 */       blast.setPosition(blast.posX - xx, blast.posY, blast.posZ - zz);
/*     */       
/* 388 */       Vec3 v = getLookVec();
/* 389 */       blast.setThrowableHeading(v.xCoord, v.yCoord, v.zCoord, 1.0F, 2.0F);
/*     */       
/* 391 */       playSound("thaumcraft:egattack", 2.0F, 1.0F + this.rand.nextFloat() * 0.1F);
/* 392 */       this.worldObj.spawnEntityInWorld(blast);
/*     */     }
/* 394 */     else if (canEntityBeSeen(entitylivingbase)) {
/* 395 */       PacketHandler.INSTANCE.sendToAllAround(new PacketFXSonic(getEntityId()), new net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 32.0D));
/*     */       try {
/* 397 */         entitylivingbase.addPotionEffect(new PotionEffect(Potion.wither.id, 400, 0));
/*     */       }
/*     */       catch (Exception e) {}
/* 400 */       if ((entitylivingbase instanceof EntityPlayer)) {
/* 401 */         ResearchHelper.addWarpToPlayer((EntityPlayer)entitylivingbase, 1 + this.worldObj.rand.nextInt(3), EnumWarpType.TEMPORARY);
/*     */       }
/*     */       
/* 404 */       playSound("thaumcraft:egscreech", 3.0F, 1.0F + this.rand.nextFloat() * 0.1F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase el)
/*     */   {
/* 411 */     return el instanceof IEldritchMob;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\EntityEldritchGuardian.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */