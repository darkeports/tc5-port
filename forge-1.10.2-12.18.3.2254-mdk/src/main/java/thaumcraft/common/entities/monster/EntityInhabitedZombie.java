/*     */ package thaumcraft.common.entities.monster;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.GameRules;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.entities.IEldritchMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultist;
/*     */ 
/*     */ public class EntityInhabitedZombie extends EntityZombie implements IEldritchMob
/*     */ {
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  30 */     super.applyEntityAttributes();
/*  31 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
/*  32 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
/*  33 */     getEntityAttribute(reinforcementChance).setBaseValue(0.0D);
/*     */   }
/*     */   
/*     */   public EntityInhabitedZombie(World world)
/*     */   {
/*  38 */     super(world);
/*  39 */     this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
/*  40 */     this.targetTasks.addTask(3, new net.minecraft.entity.ai.EntityAINearestAttackableTarget(this, EntityCultist.class, true));
/*     */   }
/*     */   
/*     */ 
/*     */   public void onKillEntity(EntityLivingBase par1EntityLivingBase) {}
/*     */   
/*     */ 
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance diff, IEntityLivingData data)
/*     */   {
/*  49 */     float d = this.worldObj.getDifficulty() == EnumDifficulty.HARD ? 0.9F : 0.6F;
/*  50 */     setCurrentItemOrArmor(4, new ItemStack(ItemsTC.crimsonPlateHelm));
/*  51 */     if (this.rand.nextFloat() <= d) setCurrentItemOrArmor(3, new ItemStack(ItemsTC.crimsonPlateChest));
/*  52 */     if (this.rand.nextFloat() <= d) setCurrentItemOrArmor(2, new ItemStack(ItemsTC.crimsonPlateLegs));
/*  53 */     return super.onInitialSpawn(diff, data);
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/*  59 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {}
/*     */   
/*     */ 
/*     */   protected void onDeathUpdate()
/*     */   {
/*  68 */     if (!this.worldObj.isRemote) {
/*  69 */       EntityEldritchCrab crab = new EntityEldritchCrab(this.worldObj);
/*  70 */       crab.setPositionAndRotation(this.posX, this.posY + getEyeHeight(), this.posZ, this.rotationYaw, this.rotationPitch);
/*  71 */       crab.setHelm(true);
/*  72 */       this.worldObj.spawnEntityInWorld(crab);
/*     */       
/*  74 */       if (((this.recentlyHit > 0) || (isPlayer())) && (canDropLoot()) && (this.worldObj.getGameRules().getBoolean("doMobLoot")))
/*     */       {
/*  76 */         int i = getExperiencePoints(this.attackingPlayer);
/*     */         
/*  78 */         while (i > 0)
/*     */         {
/*  80 */           int j = EntityXPOrb.getXPSplit(i);
/*  81 */           i -= j;
/*  82 */           this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  87 */     for (int i = 0; i < 20; i++)
/*     */     {
/*  89 */       double d2 = this.rand.nextGaussian() * 0.02D;
/*  90 */       double d0 = this.rand.nextGaussian() * 0.02D;
/*  91 */       double d1 = this.rand.nextGaussian() * 0.02D;
/*  92 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d2, d0, d1, new int[0]);
/*     */     }
/*     */     
/*  95 */     setDead();
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDeath(DamageSource p_70645_1_) {}
/*     */   
/*     */   protected String getLivingSound()
/*     */   {
/* 103 */     return "thaumcraft:crabtalk";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 109 */     return "game.hostile.hurt";
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getCanSpawnHere()
/*     */   {
/* 115 */     List ents = this.worldObj.getEntitiesWithinAABB(EntityInhabitedZombie.class, AxisAlignedBB.fromBounds(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(32.0D, 16.0D, 32.0D));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 121 */     return ents.size() > 0 ? false : super.getCanSpawnHere();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\EntityInhabitedZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */