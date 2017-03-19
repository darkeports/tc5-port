/*     */ package thaumcraft.common.entities.monster.tainted;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.entities.ITaintedMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
/*     */ 
/*     */ public class EntityTaintPig extends EntityMob implements ITaintedMob
/*     */ {
/*     */   public EntityTaintPig(World par1World)
/*     */   {
/*  35 */     super(par1World);
/*  36 */     setSize(0.9F, 0.9F);
/*  37 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  38 */     this.tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
/*  39 */     this.tasks.addTask(2, new AIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  40 */     this.tasks.addTask(3, new AIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
/*  41 */     this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
/*  42 */     this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
/*  43 */     this.tasks.addTask(7, new net.minecraft.entity.ai.EntityAILookIdle(this));
/*  44 */     this.tasks.addTask(8, new AIAttackOnCollide(this, EntityAnimal.class, 1.0D, false));
/*  45 */     this.targetTasks.addTask(0, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/*  46 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  47 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
/*  48 */     this.targetTasks.addTask(8, new EntityAINearestAttackableTarget(this, EntityAnimal.class, false));
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  54 */     super.applyEntityAttributes();
/*  55 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
/*  56 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
/*  57 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.275D);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/*  63 */     return !ITaintedMob.class.isAssignableFrom(clazz);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/*  69 */     return ((otherEntity instanceof ITaintedMob)) || (isOnTeam(otherEntity.getTeam()));
/*     */   }
/*     */   
/*     */   public void onLivingUpdate()
/*     */   {
/*  74 */     super.onLivingUpdate();
/*  75 */     if ((this.worldObj.isRemote) && (this.ticksExisted < 5)) {
/*  76 */       for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(10); a++) {
/*  77 */         Thaumcraft.proxy.getFX().splooshFX(this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int getTotalArmorValue()
/*     */   {
/*  84 */     return 2;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  90 */     super.entityInit();
/*  91 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/*  97 */     super.writeEntityToNBT(par1NBTTagCompound);
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 103 */     super.readEntityFromNBT(par1NBTTagCompound);
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 109 */     return "mob.pig.say";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 115 */     return "mob.pig.say";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 121 */     return "mob.pig.death";
/*     */   }
/*     */   
/*     */ 
/*     */   protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
/*     */   {
/* 127 */     playSound("mob.pig.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   protected float getSoundPitch()
/*     */   {
/* 133 */     return 0.7F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected net.minecraft.item.Item getDropItem()
/*     */   {
/* 141 */     return ItemsTC.tainted;
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 146 */     if (this.worldObj.rand.nextInt(3) == 0) {
/* 147 */       if (this.worldObj.rand.nextBoolean()) {
/* 148 */         entityDropItem(new ItemStack(ItemsTC.tainted, 1, 0), this.height / 2.0F);
/*     */       } else {
/* 150 */         entityDropItem(new ItemStack(ItemsTC.tainted, 1, 1), this.height / 2.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean attackEntityAsMob(Entity victim) {
/* 156 */     if (super.attackEntityAsMob(victim))
/*     */     {
/* 158 */       if ((victim instanceof EntityLivingBase))
/*     */       {
/* 160 */         byte b0 = 0;
/*     */         
/* 162 */         if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL)
/*     */         {
/* 164 */           b0 = 3;
/*     */         }
/* 166 */         else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
/*     */         {
/* 168 */           b0 = 6;
/*     */         }
/*     */         
/* 171 */         if ((b0 > 0) && (this.rand.nextInt(b0 + 1) > 2))
/*     */         {
/* 173 */           ((EntityLivingBase)victim).addPotionEffect(new net.minecraft.potion.PotionEffect(PotionFluxTaint.instance.getId(), b0 * 20, 0));
/*     */         }
/*     */       }
/*     */       
/* 177 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 181 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\tainted\EntityTaintPig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */