/*     */ package thaumcraft.common.entities.monster.tainted;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.entities.ITaintedMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
/*     */ 
/*     */ public class EntityTaintRabbit extends EntityRabbit implements ITaintedMob
/*     */ {
/*  28 */   private int field_175540_bm = 0;
/*  29 */   private int field_175535_bn = 0;
/*  30 */   private boolean field_175536_bo = false;
/*  31 */   private boolean field_175537_bp = false;
/*  32 */   private int field_175538_bq = 0;
/*     */   private int carrotTicks;
/*     */   private EntityPlayer field_175543_bt;
/*     */   
/*     */   public EntityTaintRabbit(World worldIn) {
/*  37 */     super(worldIn);
/*  38 */     this.tasks.taskEntries.clear();
/*     */     
/*  40 */     this.tasks.addTask(1, new net.minecraft.entity.ai.EntityAISwimming(this));
/*  41 */     this.tasks.addTask(2, new AIAttackOnCollide(this, EntityPlayer.class, 1.4D, false));
/*  42 */     this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
/*  43 */     this.tasks.addTask(8, new AIAttackOnCollide(this, EntityAnimal.class, 1.4D, false));
/*  44 */     this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
/*  45 */     this.targetTasks.addTask(0, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/*  46 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  47 */     this.targetTasks.addTask(8, new EntityAINearestAttackableTarget(this, EntityAnimal.class, false));
/*  48 */     setMovementSpeed(0.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/*  54 */     return !ITaintedMob.class.isAssignableFrom(clazz);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/*  60 */     return ((otherEntity instanceof ITaintedMob)) || (isOnTeam(otherEntity.getTeam()));
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTotalArmorValue()
/*     */   {
/*  66 */     return getRabbitType() == 99 ? 8 : 3;
/*     */   }
/*     */   
/*     */ 
/*     */   public EntityRabbit createChild(EntityAgeable ageable)
/*     */   {
/*  72 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
/*     */   {
/*  78 */     int j = this.rand.nextInt(2) + this.rand.nextInt(1 + p_70628_2_);
/*     */     
/*     */ 
/*  81 */     for (int k = 0; k < j; k++)
/*     */     {
/*  83 */       dropItem(ItemsTC.tainted, 1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isBreedingItem(ItemStack stack)
/*     */   {
/*  90 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  96 */     super.applyEntityAttributes();
/*  97 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
/*  98 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
/*  99 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
/* 100 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityAsMob(Entity p_70652_1_)
/*     */   {
/* 106 */     float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
/* 107 */     playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*     */     
/* 109 */     if (getRabbitType() == 99)
/*     */     {
/* 111 */       f *= 2.0F;
/*     */     }
/*     */     
/* 114 */     int i = 0;
/*     */     
/* 116 */     if ((p_70652_1_ instanceof EntityLivingBase))
/*     */     {
/* 118 */       f += EnchantmentHelper.func_152377_a(getHeldItem(), ((EntityLivingBase)p_70652_1_).getCreatureAttribute());
/* 119 */       i += EnchantmentHelper.getKnockbackModifier(this);
/*     */     }
/*     */     
/* 122 */     boolean flag = p_70652_1_.attackEntityFrom(net.minecraft.util.DamageSource.causeMobDamage(this), f);
/*     */     
/* 124 */     if (flag)
/*     */     {
/*     */ 
/* 127 */       if ((p_70652_1_ instanceof EntityLivingBase))
/*     */       {
/* 129 */         byte b0 = 0;
/*     */         
/* 131 */         if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL)
/*     */         {
/* 133 */           b0 = 3;
/*     */         }
/* 135 */         else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
/*     */         {
/* 137 */           b0 = 6;
/*     */         }
/*     */         
/* 140 */         if ((b0 > 0) && (this.rand.nextInt(b0 + 1) > 2))
/*     */         {
/* 142 */           ((EntityLivingBase)p_70652_1_).addPotionEffect(new net.minecraft.potion.PotionEffect(PotionFluxTaint.instance.getId(), b0 * 20, 0));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 147 */       if (i > 0)
/*     */       {
/* 149 */         p_70652_1_.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F, 0.1D, MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F);
/* 150 */         this.motionX *= 0.6D;
/* 151 */         this.motionZ *= 0.6D;
/*     */       }
/*     */       
/* 154 */       int j = EnchantmentHelper.getFireAspectModifier(this);
/*     */       
/* 156 */       if (j > 0)
/*     */       {
/* 158 */         p_70652_1_.setFire(j * 4);
/*     */       }
/*     */       
/* 161 */       applyEnchantments(this, p_70652_1_);
/*     */     }
/*     */     
/* 164 */     return flag;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\tainted\EntityTaintRabbit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */