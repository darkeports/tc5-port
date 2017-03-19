/*     */ package thaumcraft.common.entities.monster.boss;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.boss.IBossDisplayData;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.entities.IEldritchMob;
/*     */ import thaumcraft.api.entities.ITaintedMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintacle;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ public class EntityTaintacleGiant extends EntityTaintacle implements ITaintedMob, IBossDisplayData, IEldritchMob
/*     */ {
/*     */   public EntityTaintacleGiant(World par1World)
/*     */   {
/*  30 */     super(par1World);
/*  31 */     setSize(1.1F, 6.0F);
/*  32 */     this.experienceValue = 20;
/*     */   }
/*     */   
/*     */   public double getReach()
/*     */   {
/*  37 */     return 2.5D + this.rand.nextFloat();
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  42 */     super.applyEntityAttributes();
/*  43 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(175.0D);
/*  44 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(9.0D);
/*     */   }
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance diff, IEntityLivingData data)
/*     */   {
/*  49 */     EntityUtils.makeChampion(this, true);
/*  50 */     return data;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  56 */     super.onUpdate();
/*     */     
/*  58 */     if (getAnger() > 0) setAnger(getAnger() - 1);
/*  59 */     if ((this.worldObj.isRemote) && (this.rand.nextInt(15) == 0) && (getAnger() > 0)) {
/*  60 */       double d0 = this.rand.nextGaussian() * 0.02D;
/*  61 */       double d1 = this.rand.nextGaussian() * 0.02D;
/*  62 */       double d2 = this.rand.nextGaussian() * 0.02D;
/*  63 */       this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, this.posX + this.rand.nextFloat() * this.width - this.width / 2.0D, getEntityBoundingBox().minY + this.height + this.rand.nextFloat() * 0.5D, this.posZ + this.rand.nextFloat() * this.width - this.width / 2.0D, d0, d1, d2, new int[0]);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  70 */     if ((!this.worldObj.isRemote) && 
/*  71 */       (this.ticksExisted % 30 == 0))
/*     */     {
/*  73 */       heal(1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  82 */     super.entityInit();
/*  83 */     getDataWatcher().addObject(14, new Short((short)0));
/*     */   }
/*     */   
/*     */   public int getAnger()
/*     */   {
/*  88 */     return this.dataWatcher.getWatchableObjectShort(14);
/*     */   }
/*     */   
/*     */   public void setAnger(int par1)
/*     */   {
/*  93 */     this.dataWatcher.updateObject(14, Short.valueOf((short)par1));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getCanSpawnHere()
/*     */   {
/*  99 */     return false;
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 104 */     ArrayList<Entity> ents = EntityUtils.getEntitiesInRange(this.worldObj, this.posX, this.posY, this.posZ, this, EntityTaintacleGiant.class, 48.0D);
/* 105 */     if ((ents == null) || (ents.size() <= 0)) {
/* 106 */       EntityUtils.entityDropSpecialItem(this, new net.minecraft.item.ItemStack(ItemsTC.primordialPearl, 1 + this.rand.nextInt(2)), this.height / 2.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean canDespawn()
/*     */   {
/* 112 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBreatheUnderwater()
/*     */   {
/* 118 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   protected int decreaseAirSupply(int air)
/*     */   {
/* 124 */     return air;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource source, float damage)
/*     */   {
/* 130 */     if (!this.worldObj.isRemote)
/*     */     {
/* 132 */       if (damage > 35.0F) {
/* 133 */         if (getAnger() == 0) {
/*     */           try {
/* 135 */             addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, (int)(damage / 15.0F)));
/* 136 */             addPotionEffect(new PotionEffect(Potion.damageBoost.id, 200, (int)(damage / 40.0F)));
/* 137 */             addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 200, (int)(damage / 40.0F)));
/* 138 */             setAnger(200);
/*     */           } catch (Exception e) {}
/* 140 */           if ((source.getEntity() != null) && ((source.getEntity() instanceof EntityPlayer))) {
/* 141 */             ((EntityPlayer)source.getEntity()).addChatMessage(new ChatComponentText(getName() + " " + net.minecraft.util.StatCollector.translateToLocal("tc.boss.enrage")));
/*     */           }
/*     */         }
/* 144 */         damage = 35.0F;
/*     */       }
/*     */     }
/*     */     
/* 148 */     return super.attackEntityFrom(source, damage);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\boss\EntityTaintacleGiant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */