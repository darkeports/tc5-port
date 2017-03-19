/*     */ package thaumcraft.common.entities.monster.cult;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ public class EntityCultistPortalLesser extends EntityMob
/*     */ {
/*     */   public EntityCultistPortalLesser(World par1World)
/*     */   {
/*  26 */     super(par1World);
/*  27 */     this.isImmuneToFire = true;
/*  28 */     this.experienceValue = 10;
/*  29 */     setSize(1.5F, 3.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTotalArmorValue()
/*     */   {
/*  35 */     return 4;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  41 */     super.entityInit();
/*  42 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/*  47 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x4) != 0;
/*     */   }
/*     */   
/*     */   public void setActive(boolean tamed)
/*     */   {
/*  52 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/*  54 */     if (tamed)
/*     */     {
/*  56 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x4)));
/*     */     }
/*     */     else
/*     */     {
/*  60 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFB)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  68 */     super.applyEntityAttributes();
/*  69 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
/*  70 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.0D);
/*  71 */     getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
/*     */   }
/*     */   
/*     */   protected boolean canDespawn()
/*     */   {
/*  76 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/*  85 */     super.writeEntityToNBT(nbt);
/*  86 */     nbt.setByte("flags", this.dataWatcher.getWatchableObjectByte(16));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/*  95 */     super.readEntityFromNBT(nbt);
/*  96 */     this.dataWatcher.updateObject(16, Byte.valueOf(nbt.getByte("flags")));
/*     */   }
/*     */   
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canBePushed()
/*     */   {
/* 106 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void moveEntity(double par1, double par3, double par5) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onLivingUpdate() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isInRangeToRenderDist(double par1)
/*     */   {
/* 123 */     return par1 < 4096.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getBrightnessForRender(float par1)
/*     */   {
/* 131 */     return 15728880;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getBrightness(float par1)
/*     */   {
/* 140 */     return 1.0F;
/*     */   }
/*     */   
/* 143 */   int stagecounter = 100;
/* 144 */   public int activeCounter = 0;
/*     */   
/*     */   public void onUpdate()
/*     */   {
/* 148 */     super.onUpdate();
/* 149 */     if (isActive()) {
/* 150 */       this.activeCounter += 1;
/*     */     }
/* 152 */     if (!this.worldObj.isRemote) {
/* 153 */       if (!isActive()) {
/* 154 */         if (this.ticksExisted % 10 == 0) {
/* 155 */           EntityPlayer p = this.worldObj.getClosestPlayerToEntity(this, 32.0D);
/* 156 */           if (p != null) {
/* 157 */             setActive(true);
/* 158 */             playSound("thaumcraft:craftstart", 1.0F, 1.0F);
/*     */           }
/*     */         }
/*     */       }
/* 162 */       else if (this.stagecounter-- <= 0) {
/* 163 */         EntityPlayer p = this.worldObj.getClosestPlayerToEntity(this, 32.0D);
/* 164 */         if ((p != null) && (canEntityBeSeen(p))) {
/* 165 */           int count = this.worldObj.getDifficulty() == EnumDifficulty.NORMAL ? 4 : this.worldObj.getDifficulty() == EnumDifficulty.HARD ? 6 : 2;
/*     */           try {
/* 167 */             List l = this.worldObj.getEntitiesWithinAABB(EntityCultist.class, getEntityBoundingBox().expand(32.0D, 32.0D, 32.0D));
/* 168 */             if (l != null) count -= l.size();
/*     */           } catch (Exception e) {}
/* 170 */           if (count > 0) {
/* 171 */             this.worldObj.setEntityState(this, (byte)16);
/* 172 */             spawnMinions();
/*     */           }
/*     */         }
/* 175 */         this.stagecounter = (50 + this.rand.nextInt(50));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 180 */     if (this.pulse > 0) this.pulse -= 1;
/*     */   }
/*     */   
/*     */   int getTiming() {
/* 184 */     List<net.minecraft.entity.Entity> l = EntityUtils.getEntitiesInRange(this.worldObj, this.posX, this.posY, this.posZ, this, EntityCultist.class, 32.0D);
/* 185 */     return l.size() * 20;
/*     */   }
/*     */   
/*     */   void spawnMinions()
/*     */   {
/* 190 */     EntityCultist cultist = null;
/* 191 */     if (this.rand.nextFloat() > 0.33D) {
/* 192 */       cultist = new EntityCultistKnight(this.worldObj);
/*     */     } else {
/* 194 */       cultist = new EntityCultistCleric(this.worldObj);
/*     */     }
/* 196 */     cultist.setPosition(this.posX + this.rand.nextFloat() - this.rand.nextFloat(), this.posY + 0.25D, this.posZ + this.rand.nextFloat() - this.rand.nextFloat());
/*     */     
/*     */ 
/*     */ 
/* 200 */     cultist.onInitialSpawn(this.worldObj.getDifficultyForLocation(new net.minecraft.util.BlockPos(cultist.getPosition())), (IEntityLivingData)null);
/* 201 */     this.worldObj.spawnEntityInWorld(cultist);
/* 202 */     cultist.spawnExplosionParticle();
/* 203 */     cultist.playSound("thaumcraft:wandfail", 1.0F, 1.0F);
/* 204 */     attackEntityFrom(DamageSource.outOfWorld, 5 + this.rand.nextInt(5));
/*     */   }
/*     */   
/*     */   protected boolean isValidLightLevel()
/*     */   {
/* 209 */     return true;
/*     */   }
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer p)
/*     */   {
/* 214 */     if ((getDistanceSqToEntity(p) < 3.0D) && (p.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this), 4.0F)))
/*     */     {
/* 216 */       playSound("thaumcraft:zap", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected float getSoundVolume()
/*     */   {
/* 223 */     return 0.75F;
/*     */   }
/*     */   
/*     */   public int getTalkInterval()
/*     */   {
/* 228 */     return 540;
/*     */   }
/*     */   
/*     */   protected String getLivingSound()
/*     */   {
/* 233 */     return "thaumcraft:monolith";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 239 */     return "thaumcraft:zap";
/*     */   }
/*     */   
/*     */   protected String getDeathSound()
/*     */   {
/* 244 */     return "thaumcraft:shock";
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 250 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 258 */   public int pulse = 0;
/*     */   
/*     */   protected void dropFewItems(boolean flag, int fortune) {}
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void handleStatusUpdate(byte msg) {
/* 264 */     if (msg == 16)
/*     */     {
/* 266 */       this.pulse = 10;
/*     */     }
/*     */     else
/*     */     {
/* 270 */       super.handleStatusUpdate(msg);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void addPotionEffect(PotionEffect p_70690_1_) {}
/*     */   
/*     */ 
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */   
/*     */ 
/*     */   public void onDeath(DamageSource p_70645_1_)
/*     */   {
/* 283 */     if (!this.worldObj.isRemote) {
/* 284 */       this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.5F, false, false);
/*     */     }
/*     */     
/* 287 */     super.onDeath(p_70645_1_);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\cult\EntityCultistPortalLesser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */