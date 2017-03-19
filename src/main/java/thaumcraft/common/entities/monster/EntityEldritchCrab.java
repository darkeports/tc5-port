/*     */ package thaumcraft.common.entities.monster;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.monster.EntitySpider.GroupData;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.entities.IEldritchMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultist;
/*     */ 
/*     */ public class EntityEldritchCrab extends EntityMob implements IEldritchMob
/*     */ {
/*     */   public EntityEldritchCrab(World par1World)
/*     */   {
/*  39 */     super(par1World);
/*  40 */     setSize(0.8F, 0.6F);
/*  41 */     this.experienceValue = 6;
/*  42 */     ((PathNavigateGround)getNavigator()).setBreakDoors(true);
/*  43 */     this.tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
/*  44 */     this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.63F));
/*  45 */     this.tasks.addTask(3, new thaumcraft.common.entities.ai.combat.AIAttackOnCollide(this, EntityLivingBase.class, 1.0D, false));
/*  46 */     this.tasks.addTask(7, new EntityAIWander(this, 0.8D));
/*  47 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/*  48 */     this.targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, true, new Class[0]));
/*  49 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, net.minecraft.entity.player.EntityPlayer.class, true));
/*  50 */     this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityCultist.class, true));
/*     */   }
/*     */   
/*     */   public double getYOffset()
/*     */   {
/*  55 */     return isRiding() ? 0.5D : 0.0D;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  61 */     super.applyEntityAttributes();
/*  62 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
/*  63 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
/*  64 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(hasHelm() ? 0.275D : 0.3D);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  70 */     super.entityInit();
/*  71 */     this.dataWatcher.addObject(22, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public boolean canPickUpLoot()
/*     */   {
/*  76 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTotalArmorValue()
/*     */   {
/*  82 */     return hasHelm() ? 5 : 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance diff, IEntityLivingData data)
/*     */   {
/*  88 */     if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
/*  89 */       setHelm(true);
/*     */     } else {
/*  91 */       setHelm(this.rand.nextFloat() < 0.33F);
/*     */     }
/*     */     
/*  94 */     if (data == null)
/*     */     {
/*  96 */       data = new EntitySpider.GroupData();
/*     */       
/*  98 */       if ((this.worldObj.getDifficulty() == EnumDifficulty.HARD) && (this.worldObj.rand.nextFloat() < 0.1F * diff.getClampedAdditionalDifficulty()))
/*     */       {
/* 100 */         ((EntitySpider.GroupData)data).func_111104_a(this.worldObj.rand);
/*     */       }
/*     */     }
/*     */     
/* 104 */     if ((data instanceof EntitySpider.GroupData))
/*     */     {
/* 106 */       int i = ((EntitySpider.GroupData)data).potionEffectId;
/*     */       
/* 108 */       if ((i > 0) && (Potion.potionTypes[i] != null))
/*     */       {
/* 110 */         addPotionEffect(new PotionEffect(i, Integer.MAX_VALUE));
/*     */       }
/*     */     }
/*     */     
/* 114 */     return super.onInitialSpawn(diff, data);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasHelm()
/*     */   {
/* 120 */     return (this.dataWatcher.getWatchableObjectByte(22) & 0x1) != 0;
/*     */   }
/*     */   
/*     */   public void setHelm(boolean par1)
/*     */   {
/* 125 */     byte var2 = this.dataWatcher.getWatchableObjectByte(22);
/*     */     
/* 127 */     if (par1)
/*     */     {
/* 129 */       this.dataWatcher.updateObject(22, Byte.valueOf((byte)(var2 | 0x1)));
/*     */     }
/*     */     else
/*     */     {
/* 133 */       this.dataWatcher.updateObject(22, Byte.valueOf((byte)(var2 & 0xFFFFFFFE)));
/*     */     }
/*     */   }
/*     */   
/* 137 */   private int attackTime = 0;
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 142 */     super.onUpdate();
/*     */     
/* 144 */     this.attackTime -= 1;
/*     */     
/* 146 */     if (this.ticksExisted < 20) { this.fallDistance = 0.0F;
/*     */     }
/* 148 */     if ((this.ridingEntity == null) && (getAITarget() != null) && (getAITarget().riddenByEntity == null) && (!this.onGround) && (!hasHelm()) && (!getAITarget().isDead) && (this.posY - getAITarget().posY >= getAITarget().height / 2.0F) && (getDistanceSqToEntity(getAITarget()) < 4.0D))
/*     */     {
/*     */ 
/*     */ 
/* 152 */       mountEntity(getAITarget());
/*     */     }
/*     */     
/* 155 */     if ((!this.worldObj.isRemote) && (this.ridingEntity != null) && 
/* 156 */       (this.attackTime <= 0)) {
/* 157 */       this.attackTime = (10 + this.rand.nextInt(10));
/* 158 */       attackEntityAsMob(this.ridingEntity);
/* 159 */       if ((this.ridingEntity != null) && (this.rand.nextFloat() < 0.2D)) { dismountEntity(this.ridingEntity);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 167 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
/*     */   {
/* 173 */     super.dropFewItems(p_70628_1_, p_70628_2_);
/*     */     
/* 175 */     if ((p_70628_1_) && ((this.rand.nextInt(3) == 0) || (this.rand.nextInt(1 + p_70628_2_) > 0)))
/*     */     {
/* 177 */       dropItem(Items.ender_pearl, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean attackEntityAsMob(Entity p_70652_1_)
/*     */   {
/* 183 */     if (super.attackEntityAsMob(p_70652_1_)) {
/* 184 */       playSound("thaumcraft:crabclaw", 1.0F, 0.9F + this.worldObj.rand.nextFloat() * 0.2F);
/* 185 */       return true; }
/* 186 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource source, float damage)
/*     */   {
/* 192 */     boolean b = super.attackEntityFrom(source, damage);
/*     */     
/* 194 */     if ((hasHelm()) && (getHealth() / getMaxHealth() <= 0.5F)) {
/* 195 */       setHelm(false);
/* 196 */       renderBrokenItemStack(new ItemStack(ItemsTC.crimsonPlateChest));
/* 197 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
/*     */     }
/*     */     
/* 200 */     return b;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 207 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 208 */     this.dataWatcher.updateObject(22, Byte.valueOf(par1NBTTagCompound.getByte("Flags")));
/* 209 */     if (!hasHelm()) {
/* 210 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 220 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 221 */     par1NBTTagCompound.setByte("Flags", this.dataWatcher.getWatchableObjectByte(22));
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTalkInterval()
/*     */   {
/* 227 */     return 160;
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 233 */     return "thaumcraft:crabtalk";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 239 */     return "game.hostile.hurt";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 245 */     return "thaumcraft:crabdeath";
/*     */   }
/*     */   
/*     */ 
/*     */   protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
/*     */   {
/* 251 */     playSound("mob.spider.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumCreatureAttribute getCreatureAttribute()
/*     */   {
/* 257 */     return EnumCreatureAttribute.ARTHROPOD;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isPotionApplicable(PotionEffect p_70687_1_)
/*     */   {
/* 263 */     return p_70687_1_.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(p_70687_1_);
/*     */   }
/*     */   
/*     */   public boolean isOnSameTeam(EntityLivingBase el)
/*     */   {
/* 268 */     return el instanceof EntityEldritchCrab;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\EntityEldritchCrab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */