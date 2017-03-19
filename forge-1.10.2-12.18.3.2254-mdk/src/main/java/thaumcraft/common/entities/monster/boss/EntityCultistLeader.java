/*     */ package thaumcraft.common.entities.monster.boss;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.ai.combat.AILongRangeAttack;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultist;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultistCleric;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultistKnight;
/*     */ import thaumcraft.common.entities.monster.mods.ChampionModifier;
/*     */ import thaumcraft.common.entities.projectile.EntityGolemOrb;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ public class EntityCultistLeader extends EntityThaumcraftBoss implements IRangedAttackMob
/*     */ {
/*     */   public EntityCultistLeader(World p_i1745_1_)
/*     */   {
/*  47 */     super(p_i1745_1_);
/*  48 */     setSize(0.75F, 2.25F);
/*  49 */     this.tasks.addTask(0, new EntityAISwimming(this));
/*  50 */     this.tasks.addTask(2, new AILongRangeAttack(this, 16.0D, 1.0D, 30, 40, 24.0F));
/*  51 */     this.tasks.addTask(3, new thaumcraft.common.entities.ai.combat.AIAttackOnCollide(this, EntityLivingBase.class, 1.1D, false));
/*  52 */     this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.8D));
/*  53 */     this.tasks.addTask(7, new EntityAIWander(this, 0.8D));
/*  54 */     this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  55 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/*  56 */     this.targetTasks.addTask(1, new thaumcraft.common.entities.ai.combat.AICultistHurtByTarget(this, true));
/*  57 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  58 */     this.experienceValue = 40;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  64 */     super.applyEntityAttributes();
/*  65 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.32D);
/*  66 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150.0D);
/*  67 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  74 */     super.entityInit();
/*  75 */     getDataWatcher().addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void generateName() {
/*  79 */     int t = (int)getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue();
/*  80 */     if (t >= 0) {
/*  81 */       setCustomNameTag(String.format(net.minecraft.util.StatCollector.translateToLocal("entity.Thaumcraft.CultistLeader.name.custom"), new Object[] { getTitle(), ChampionModifier.mods[t].getModNameLocalized() }));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private String getTitle()
/*     */   {
/*  89 */     return this.titles[getDataWatcher().getWatchableObjectByte(16)];
/*     */   }
/*     */   
/*     */   private void setTitle(int title) {
/*  93 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)title));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/*  99 */     super.writeEntityToNBT(nbt);
/* 100 */     nbt.setByte("title", getDataWatcher().getWatchableObjectByte(16));
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/* 106 */     super.readEntityFromNBT(nbt);
/* 107 */     setTitle(nbt.getByte("title"));
/*     */   }
/*     */   
/* 110 */   String[] titles = { "Alberic", "Anselm", "Bastian", "Beturian", "Chabier", "Chorache", "Chuse", "Dodorol", "Ebardo", "Ferrando", "Fertus", "Guillen", "Larpe", "Obano", "Zelipe" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addRandomDrop()
/*     */   {
/* 118 */     setCurrentItemOrArmor(4, new ItemStack(ItemsTC.crimsonPraetorHelm));
/* 119 */     setCurrentItemOrArmor(3, new ItemStack(ItemsTC.crimsonPraetorChest));
/* 120 */     setCurrentItemOrArmor(2, new ItemStack(ItemsTC.crimsonPraetorLegs));
/* 121 */     setCurrentItemOrArmor(1, new ItemStack(ItemsTC.crimsonBoots));
/* 122 */     if (this.worldObj.getDifficulty() == EnumDifficulty.EASY) {
/* 123 */       setCurrentItemOrArmor(0, new ItemStack(ItemsTC.voidSword));
/*     */     } else {
/* 125 */       setCurrentItemOrArmor(0, new ItemStack(ItemsTC.crimsonBlade));
/*     */     }
/*     */   }
/*     */   
/*     */   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance diff)
/*     */   {
/* 131 */     float f = diff.getClampedAdditionalDifficulty();
/* 132 */     if ((getHeldItem() != null) && (this.rand.nextFloat() < 0.5F * f))
/*     */     {
/* 134 */       EnchantmentHelper.addRandomEnchantment(this.rand, getHeldItem(), (int)(7.0F + f * this.rand.nextInt(22)));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isOnSameTeam(EntityLivingBase el)
/*     */   {
/* 140 */     return ((el instanceof EntityCultist)) || ((el instanceof EntityCultistLeader));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/* 146 */     if ((clazz == EntityCultistCleric.class) || (clazz == EntityCultistLeader.class) || (clazz == EntityCultistKnight.class))
/*     */     {
/*     */ 
/* 149 */       return false; }
/* 150 */     return super.canAttackClass(clazz);
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 156 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 161 */     entityDropItem(new ItemStack(ItemsTC.lootBag, 1, 2), 1.5F);
/*     */   }
/*     */   
/*     */ 
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance diff, IEntityLivingData data)
/*     */   {
/* 167 */     addRandomDrop();
/* 168 */     setEnchantmentBasedOnDifficulty(diff);
/* 169 */     setTitle(this.rand.nextInt(this.titles.length));
/* 170 */     return super.onInitialSpawn(diff, data);
/*     */   }
/*     */   
/*     */   protected void updateAITasks()
/*     */   {
/* 175 */     super.updateAITasks();
/* 176 */     List<Entity> list = EntityUtils.getEntitiesInRange(this.worldObj, this.posX, this.posY, this.posZ, this, EntityCultist.class, 8.0D);
/* 177 */     for (Entity e : list) {
/*     */       try {
/* 179 */         if (((e instanceof EntityCultist)) && (!((EntityCultist)e).isPotionActive(Potion.regeneration.id))) {
/* 180 */           ((EntityCultist)e).addPotionEffect(new PotionEffect(Potion.regeneration.id, 60, 1));
/*     */         }
/*     */       }
/*     */       catch (Exception e1) {}
/*     */     }
/*     */   }
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float f) {
/* 188 */     if (canEntityBeSeen(entitylivingbase)) {
/* 189 */       swingItem();
/* 190 */       getLookHelper().setLookPosition(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY + entitylivingbase.height / 2.0F, entitylivingbase.posZ, 30.0F, 30.0F);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 195 */       EntityGolemOrb blast = new EntityGolemOrb(this.worldObj, this, entitylivingbase, true);
/* 196 */       blast.posX += blast.motionX / 2.0D;
/* 197 */       blast.posZ += blast.motionZ / 2.0D;
/* 198 */       blast.setPosition(blast.posX, blast.posY, blast.posZ);
/*     */       
/* 200 */       double d0 = entitylivingbase.posX - this.posX;
/* 201 */       double d1 = entitylivingbase.getEntityBoundingBox().minY + entitylivingbase.height / 2.0F - (this.posY + this.height / 2.0F);
/* 202 */       double d2 = entitylivingbase.posZ - this.posZ;
/*     */       
/* 204 */       blast.setThrowableHeading(d0, d1 + 2.0D, d2, 0.66F, 3.0F);
/*     */       
/* 206 */       playSound("thaumcraft:egattack", 1.0F, 1.0F + this.rand.nextFloat() * 0.1F);
/* 207 */       this.worldObj.spawnEntityInWorld(blast);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void spawnExplosionParticle()
/*     */   {
/* 214 */     if (this.worldObj.isRemote) {
/* 215 */       for (int i = 0; i < 20; i++)
/*     */       {
/* 217 */         double d0 = this.rand.nextGaussian() * 0.05D;
/* 218 */         double d1 = this.rand.nextGaussian() * 0.05D;
/* 219 */         double d2 = this.rand.nextGaussian() * 0.05D;
/* 220 */         double d3 = 2.0D;
/*     */         
/* 222 */         Thaumcraft.proxy.getFX().cultistSpawn(this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width + d0 * d3, this.posY + this.rand.nextFloat() * this.height + d1 * d3, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width + d2 * d3, d0, d1, d2);
/*     */       }
/*     */       
/*     */     }
/*     */     else
/*     */     {
/* 228 */       this.worldObj.setEntityState(this, (byte)20);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\boss\EntityCultistLeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */