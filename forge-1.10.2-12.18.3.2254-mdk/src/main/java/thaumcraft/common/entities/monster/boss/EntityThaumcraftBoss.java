/*     */ package thaumcraft.common.entities.monster.boss;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ 
/*     */ public class EntityThaumcraftBoss extends EntityMob implements net.minecraft.entity.boss.IBossDisplayData
/*     */ {
/*     */   public EntityThaumcraftBoss(World world)
/*     */   {
/*  32 */     super(world);
/*  33 */     this.experienceValue = 50;
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/*  39 */     super.readEntityFromNBT(nbt);
/*  40 */     if (nbt.hasKey("HomeD")) {
/*  41 */       setHomePosAndDistance(new BlockPos(nbt.getInteger("HomeX"), nbt.getInteger("HomeY"), nbt.getInteger("HomeZ")), nbt.getInteger("HomeD"));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/*  48 */     super.writeEntityToNBT(nbt);
/*  49 */     if ((getHomePosition() != null) && (getMaximumHomeDistance() > 0.0F)) {
/*  50 */       nbt.setInteger("HomeD", (int)getMaximumHomeDistance());
/*  51 */       nbt.setInteger("HomeX", getHomePosition().getX());
/*  52 */       nbt.setInteger("HomeY", getHomePosition().getY());
/*  53 */       nbt.setInteger("HomeZ", getHomePosition().getZ());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  60 */     super.applyEntityAttributes();
/*  61 */     getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.95D);
/*  62 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  68 */     super.entityInit();
/*  69 */     getDataWatcher().addObject(14, new Short((short)0));
/*     */   }
/*     */   
/*  72 */   HashMap<Integer, Integer> aggro = new HashMap();
/*     */   
/*     */   protected void updateAITasks()
/*     */   {
/*  76 */     if (getSpawnTimer() == 0) {
/*  77 */       super.updateAITasks();
/*     */     }
/*  79 */     if ((getAttackTarget() != null) && (getAttackTarget().isDead)) {
/*  80 */       setAttackTarget(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance diff, IEntityLivingData data) {
/*  85 */     setHomePosAndDistance(getPosition(), 24);
/*  86 */     generateName();
/*  87 */     return data;
/*     */   }
/*     */   
/*     */   public int getAnger()
/*     */   {
/*  92 */     return this.dataWatcher.getWatchableObjectShort(14);
/*     */   }
/*     */   
/*     */   public void setAnger(int par1)
/*     */   {
/*  97 */     this.dataWatcher.updateObject(14, Short.valueOf((short)par1));
/*     */   }
/*     */   
/* 100 */   int spawnTimer = 0;
/*     */   
/*     */   public int getSpawnTimer() {
/* 103 */     return this.spawnTimer;
/*     */   }
/*     */   
/*     */   public void onUpdate()
/*     */   {
/* 108 */     super.onUpdate();
/* 109 */     if (getSpawnTimer() > 0) { this.spawnTimer -= 1;
/*     */     }
/* 111 */     if (getAnger() > 0) setAnger(getAnger() - 1);
/* 112 */     if ((this.worldObj.isRemote) && (this.rand.nextInt(15) == 0) && (getAnger() > 0)) {
/* 113 */       double d0 = this.rand.nextGaussian() * 0.02D;
/* 114 */       double d1 = this.rand.nextGaussian() * 0.02D;
/* 115 */       double d2 = this.rand.nextGaussian() * 0.02D;
/* 116 */       this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, this.posX + this.rand.nextFloat() * this.width - this.width / 2.0D, getEntityBoundingBox().minY + this.height + this.rand.nextFloat() * 0.5D, this.posZ + this.rand.nextFloat() * this.width - this.width / 2.0D, d0, d1, d2, new int[0]);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 123 */     if (!this.worldObj.isRemote) {
/* 124 */       if (this.ticksExisted % 30 == 0)
/*     */       {
/* 126 */         heal(1.0F);
/*     */       }
/*     */       
/* 129 */       if ((getAttackTarget() != null) && (this.ticksExisted % 20 == 0)) {
/* 130 */         ArrayList<Integer> dl = new ArrayList();
/* 131 */         int players = 0;
/* 132 */         int hei = getAttackTarget().getEntityId();
/* 133 */         int ad = this.aggro.containsKey(Integer.valueOf(hei)) ? ((Integer)this.aggro.get(Integer.valueOf(hei))).intValue() : 0;
/* 134 */         int ld = ad;
/* 135 */         Entity newTarget = null;
/* 136 */         for (Integer ei : this.aggro.keySet()) {
/* 137 */           int ca = ((Integer)this.aggro.get(ei)).intValue();
/* 138 */           if ((ca > ad + 25) && (ca > ad * 1.1D) && (ca > ld)) {
/* 139 */             newTarget = this.worldObj.getEntityByID(hei);
/* 140 */             if ((newTarget == null) || (newTarget.isDead) || (getDistanceSqToEntity(newTarget) > 16384.0D)) {
/* 141 */               dl.add(ei);
/*     */             } else {
/* 143 */               hei = ei.intValue();
/* 144 */               ld = ei.intValue();
/* 145 */               if ((newTarget instanceof EntityPlayer)) {
/* 146 */                 players++;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         Integer ei;
/* 152 */         for (Iterator i$ = dl.iterator(); i$.hasNext(); this.aggro.remove(ei)) { ei = (Integer)i$.next();
/*     */         }
/* 154 */         if ((newTarget != null) && (hei != getAttackTarget().getEntityId())) {
/* 155 */           setAttackTarget((EntityLivingBase)newTarget);
/*     */         }
/*     */         
/* 158 */         float om = getMaxHealth();
/*     */         
/* 160 */         IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.maxHealth);
/* 161 */         IAttributeInstance iattributeinstance2 = getEntityAttribute(SharedMonsterAttributes.attackDamage);
/*     */         
/* 163 */         for (int a = 0; a < 5; a++) {
/* 164 */           iattributeinstance2.removeModifier(thaumcraft.common.lib.utils.EntityUtils.DMGBUFF[a]);
/* 165 */           iattributeinstance.removeModifier(thaumcraft.common.lib.utils.EntityUtils.HPBUFF[a]);
/*     */         }
/*     */         
/* 168 */         for (int a = 0; a < Math.min(5, players - 1); a++) {
/* 169 */           iattributeinstance.applyModifier(thaumcraft.common.lib.utils.EntityUtils.HPBUFF[a]);
/* 170 */           iattributeinstance2.applyModifier(thaumcraft.common.lib.utils.EntityUtils.DMGBUFF[a]);
/*     */         }
/*     */         
/* 173 */         double mm = getMaxHealth() / om;
/* 174 */         setHealth((float)(getHealth() * mm));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isEntityInvulnerable(DamageSource ds)
/*     */   {
/* 183 */     return (super.isEntityInvulnerable(ds)) || (getSpawnTimer() > 0);
/*     */   }
/*     */   
/*     */   public boolean canBreatheUnderwater()
/*     */   {
/* 188 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canBePushed()
/*     */   {
/* 193 */     return (super.canBePushed()) && (!isEntityInvulnerable(DamageSource.starve));
/*     */   }
/*     */   
/*     */ 
/*     */   protected int decreaseAirSupply(int air)
/*     */   {
/* 199 */     return air;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setInWeb() {}
/*     */   
/*     */   public boolean canPickUpLoot()
/*     */   {
/* 207 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance diff) {}
/*     */   
/*     */ 
/*     */   protected boolean canDespawn()
/*     */   {
/* 217 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isOnSameTeam(EntityLivingBase el)
/*     */   {
/* 222 */     return el instanceof thaumcraft.api.entities.IEldritchMob;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void dropFewItems(boolean flag, int fortune)
/*     */   {
/* 229 */     thaumcraft.common.lib.utils.EntityUtils.entityDropSpecialItem(this, new ItemStack(ItemsTC.primordialPearl, 1 + this.rand.nextInt(2)), this.height / 2.0F);
/* 230 */     entityDropItem(new ItemStack(ItemsTC.lootBag, 1, 2), 1.5F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource source, float damage)
/*     */   {
/* 237 */     if (!this.worldObj.isRemote) {
/* 238 */       if ((source.getEntity() != null) && ((source.getEntity() instanceof EntityLivingBase))) {
/* 239 */         int target = source.getEntity().getEntityId();
/* 240 */         int ad = (int)damage;
/* 241 */         if (this.aggro.containsKey(Integer.valueOf(target))) {
/* 242 */           ad += ((Integer)this.aggro.get(Integer.valueOf(target))).intValue();
/*     */         }
/* 244 */         this.aggro.put(Integer.valueOf(target), Integer.valueOf(ad));
/*     */       }
/*     */       
/* 247 */       if (damage > 35.0F) {
/* 248 */         if (getAnger() == 0) {
/*     */           try {
/* 250 */             addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, (int)(damage / 15.0F)));
/* 251 */             addPotionEffect(new PotionEffect(Potion.damageBoost.id, 200, (int)(damage / 10.0F)));
/* 252 */             addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 200, (int)(damage / 40.0F)));
/* 253 */             setAnger(200);
/*     */           } catch (Exception e) {}
/* 255 */           if ((source.getEntity() != null) && ((source.getEntity() instanceof EntityPlayer))) {
/* 256 */             ((EntityPlayer)source.getEntity()).addChatMessage(new ChatComponentText(getName() + " " + net.minecraft.util.StatCollector.translateToLocal("tc.boss.enrage")));
/*     */           }
/*     */         }
/* 259 */         damage = 35.0F;
/*     */       }
/*     */     }
/*     */     
/* 263 */     return super.attackEntityFrom(source, damage);
/*     */   }
/*     */   
/*     */   public void generateName() {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\boss\EntityThaumcraftBoss.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */