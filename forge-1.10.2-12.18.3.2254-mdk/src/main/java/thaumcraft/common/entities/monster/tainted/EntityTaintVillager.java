/*     */ package thaumcraft.common.entities.monster.tainted;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAIOpenDoor;
/*     */ import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest2;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.village.Village;
/*     */ import net.minecraft.village.VillageCollection;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.entities.ITaintedMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class EntityTaintVillager extends EntityMob implements ITaintedMob
/*     */ {
/*     */   private int randomTickDivider;
/*     */   Village villageObj;
/*     */   
/*     */   public EntityTaintVillager(World par1World)
/*     */   {
/*  42 */     this(par1World, 0);
/*     */   }
/*     */   
/*     */   public EntityTaintVillager(World par1World, int par2)
/*     */   {
/*  47 */     super(par1World);
/*  48 */     this.randomTickDivider = 0;
/*  49 */     this.villageObj = null;
/*  50 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  51 */     this.tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
/*  52 */     this.tasks.addTask(2, new net.minecraft.entity.ai.EntityAIMoveIndoors(this));
/*  53 */     this.tasks.addTask(2, new thaumcraft.common.entities.ai.combat.AIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  54 */     this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
/*  55 */     this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
/*  56 */     this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
/*  57 */     this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
/*  58 */     this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
/*  59 */     this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityVillager.class, 5.0F, 0.02F));
/*  60 */     this.tasks.addTask(9, new EntityAIWander(this, 1.0D));
/*  61 */     this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLivingBase.class, 8.0F));
/*  62 */     this.targetTasks.addTask(0, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/*  63 */     this.targetTasks.addTask(2, new net.minecraft.entity.ai.EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  69 */     super.applyEntityAttributes();
/*  70 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
/*  71 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
/*  72 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/*  78 */     return !ITaintedMob.class.isAssignableFrom(clazz);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/*  84 */     return ((otherEntity instanceof ITaintedMob)) || (isOnTeam(otherEntity.getTeam()));
/*     */   }
/*     */   
/*     */   public void onLivingUpdate()
/*     */   {
/*  89 */     super.onLivingUpdate();
/*  90 */     if ((this.worldObj.isRemote) && (this.ticksExisted < 5)) {
/*  91 */       for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(10); a++) {
/*  92 */         Thaumcraft.proxy.getFX().splooshFX(this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void updateAITick()
/*     */   {
/* 101 */     if (--this.randomTickDivider <= 0)
/*     */     {
/* 103 */       BlockPos blockpos = new BlockPos(this);
/* 104 */       this.worldObj.villageCollectionObj.addToVillagerPositionList(blockpos);
/* 105 */       this.randomTickDivider = (70 + this.rand.nextInt(50));
/* 106 */       this.villageObj = this.worldObj.villageCollectionObj.getNearestVillage(blockpos, 32);
/*     */       
/* 108 */       if (this.villageObj == null)
/*     */       {
/* 110 */         detachHome();
/*     */       }
/*     */       else
/*     */       {
/* 114 */         BlockPos blockpos1 = this.villageObj.getCenter();
/* 115 */         setHomePosAndDistance(blockpos1, (int)(this.villageObj.getVillageRadius() * 1.0F));
/*     */       }
/*     */     }
/*     */     
/* 119 */     super.updateAITick();
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/* 125 */     super.entityInit();
/* 126 */     this.dataWatcher.addObject(16, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 137 */     super.writeEntityToNBT(par1NBTTagCompound);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 146 */     super.readEntityFromNBT(par1NBTTagCompound);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 153 */     return ItemsTC.tainted;
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 158 */     if (this.worldObj.rand.nextInt(3) == 0) {
/* 159 */       entityDropItem(new ItemStack(ItemsTC.tainted, 1, 0), this.height / 2.0F);
/*     */     } else {
/* 161 */       entityDropItem(new ItemStack(ItemsTC.tainted, 1, 1), this.height / 2.0F);
/*     */     }
/* 163 */     if (this.worldObj.rand.nextInt(8) < 1 + i) { entityDropItem(new ItemStack(ItemsTC.coin), 1.5F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 173 */     return "mob.villager.idle";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 182 */     return "mob.villager.hit";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 191 */     return "mob.villager.death";
/*     */   }
/*     */   
/*     */   protected float getSoundPitch()
/*     */   {
/* 196 */     return 0.7F;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setRevengeTarget(EntityLivingBase par1EntityLiving)
/*     */   {
/* 202 */     super.setRevengeTarget(par1EntityLiving);
/*     */     
/* 204 */     if ((this.villageObj != null) && (par1EntityLiving != null))
/*     */     {
/* 206 */       this.villageObj.addOrRenewAgressor(par1EntityLiving);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityAsMob(Entity victim)
/*     */   {
/* 213 */     if (super.attackEntityAsMob(victim))
/*     */     {
/* 215 */       if ((victim instanceof EntityLivingBase))
/*     */       {
/* 217 */         byte b0 = 0;
/*     */         
/* 219 */         if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL)
/*     */         {
/* 221 */           b0 = 3;
/*     */         }
/* 223 */         else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
/*     */         {
/* 225 */           b0 = 6;
/*     */         }
/*     */         
/* 228 */         if ((b0 > 0) && (this.rand.nextInt(b0 + 1) > 2))
/*     */         {
/* 230 */           ((EntityLivingBase)victim).addPotionEffect(new net.minecraft.potion.PotionEffect(PotionFluxTaint.instance.getId(), b0 * 20, 0));
/*     */         }
/*     */       }
/*     */       
/* 234 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 238 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\tainted\EntityTaintVillager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */