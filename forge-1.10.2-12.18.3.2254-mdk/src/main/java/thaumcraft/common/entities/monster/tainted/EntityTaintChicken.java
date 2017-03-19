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
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ public class EntityTaintChicken extends EntityMob implements ITaintedMob
/*     */ {
/*  33 */   public boolean field_753_a = false;
/*  34 */   public float field_752_b = 0.0F;
/*  35 */   public float destPos = 0.0F;
/*     */   public float field_757_d;
/*     */   public float field_756_e;
/*  38 */   public float field_755_h = 1.0F;
/*     */   
/*     */   public EntityTaintChicken(World par1World)
/*     */   {
/*  42 */     super(par1World);
/*  43 */     setSize(0.5F, 0.8F);
/*  44 */     this.tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
/*  45 */     this.tasks.addTask(2, new AIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  46 */     this.tasks.addTask(2, new net.minecraft.entity.ai.EntityAILeapAtTarget(this, 0.3F));
/*  47 */     this.tasks.addTask(3, new AIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
/*  48 */     this.tasks.addTask(3, new AIAttackOnCollide(this, EntityAnimal.class, 1.0D, true));
/*  49 */     this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
/*  50 */     this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
/*  51 */     this.tasks.addTask(5, new net.minecraft.entity.ai.EntityAILookIdle(this));
/*  52 */     this.targetTasks.addTask(0, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/*  53 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  54 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
/*  55 */     this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityAnimal.class, false));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/*  61 */     return !ITaintedMob.class.isAssignableFrom(clazz);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/*  67 */     return ((otherEntity instanceof ITaintedMob)) || (isOnTeam(otherEntity.getTeam()));
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  73 */     super.applyEntityAttributes();
/*  74 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*  75 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
/*  76 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getTotalArmorValue()
/*     */   {
/*  83 */     return 2;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onLivingUpdate()
/*     */   {
/*  89 */     super.onLivingUpdate();
/*  90 */     this.field_756_e = this.field_752_b;
/*  91 */     this.field_757_d = this.destPos;
/*  92 */     this.destPos = ((float)(this.destPos + (this.onGround ? -1 : 4) * 0.3D));
/*     */     
/*  94 */     if (this.destPos < 0.0F)
/*     */     {
/*  96 */       this.destPos = 0.0F;
/*     */     }
/*     */     
/*  99 */     if (this.destPos > 1.0F)
/*     */     {
/* 101 */       this.destPos = 1.0F;
/*     */     }
/*     */     
/* 104 */     if ((!this.onGround) && (this.field_755_h < 1.0F))
/*     */     {
/* 106 */       this.field_755_h = 1.0F;
/*     */     }
/*     */     
/* 109 */     this.field_755_h = ((float)(this.field_755_h * 0.9D));
/*     */     
/* 111 */     if ((!this.onGround) && (this.motionY < 0.0D))
/*     */     {
/* 113 */       this.motionY *= 0.9D;
/*     */     }
/*     */     
/* 116 */     this.field_752_b += this.field_755_h * 2.0F;
/*     */     
/* 118 */     if ((this.worldObj.isRemote) && (this.ticksExisted < 5)) {
/* 119 */       for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(10); a++) {
/* 120 */         Thaumcraft.proxy.getFX().splooshFX(this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 131 */     super.writeEntityToNBT(par1NBTTagCompound);
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 137 */     super.readEntityFromNBT(par1NBTTagCompound);
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 143 */     return "mob.chicken.say";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 149 */     return "mob.chicken.hurt";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 155 */     return "mob.chicken.hurt";
/*     */   }
/*     */   
/*     */ 
/*     */   protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
/*     */   {
/* 161 */     playSound("mob.chicken.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected float getSoundPitch()
/*     */   {
/* 166 */     return 0.7F;
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 172 */     return ItemsTC.tainted;
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 177 */     if (this.worldObj.rand.nextInt(4) == 0) {
/* 178 */       entityDropItem(new ItemStack(ItemsTC.tainted, 1, 0), this.height / 2.0F);
/*     */     } else {
/* 180 */       entityDropItem(new ItemStack(ItemsTC.tainted, 1, 1), this.height / 2.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean attackEntityAsMob(Entity victim)
/*     */   {
/* 186 */     if (super.attackEntityAsMob(victim))
/*     */     {
/* 188 */       if ((victim instanceof EntityLivingBase))
/*     */       {
/* 190 */         byte b0 = 0;
/*     */         
/* 192 */         if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL)
/*     */         {
/* 194 */           b0 = 3;
/*     */         }
/* 196 */         else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
/*     */         {
/* 198 */           b0 = 6;
/*     */         }
/*     */         
/* 201 */         if ((b0 > 0) && (this.rand.nextInt(b0 + 1) > 2))
/*     */         {
/* 203 */           ((EntityLivingBase)victim).addPotionEffect(new net.minecraft.potion.PotionEffect(PotionFluxTaint.instance.getId(), b0 * 20, 0));
/*     */         }
/*     */       }
/*     */       
/* 207 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 211 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\tainted\EntityTaintChicken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */