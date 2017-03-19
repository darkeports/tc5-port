/*     */ package thaumcraft.common.entities.monster;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class EntityFireBat extends EntityMob
/*     */ {
/*     */   private BlockPos currentFlightTarget;
/*  33 */   public EntityLivingBase owner = null;
/*     */   
/*     */   public EntityFireBat(World par1World)
/*     */   {
/*  37 */     super(par1World);
/*  38 */     setSize(0.5F, 0.9F);
/*  39 */     setIsBatHanging(true);
/*  40 */     this.isImmuneToFire = true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/*  46 */     super.entityInit();
/*  47 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public int getBrightnessForRender(float par1)
/*     */   {
/*  54 */     return 15728880;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getBrightness(float par1)
/*     */   {
/*  63 */     return 1.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected float getSoundVolume()
/*     */   {
/*  72 */     return 0.1F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected float getSoundPitch()
/*     */   {
/*  81 */     return super.getSoundPitch() * 0.95F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/*  90 */     return (getIsBatHanging()) && (this.rand.nextInt(4) != 0) ? null : "mob.bat.idle";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/*  99 */     return "mob.bat.hurt";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 108 */     return "mob.bat.death";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canBePushed()
/*     */   {
/* 117 */     return false;
/*     */   }
/*     */   
/* 120 */   public int damBonus = 0;
/*     */   
/*     */   private int attackTime;
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/* 127 */     super.applyEntityAttributes();
/* 128 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getIsDevil() ? 15.0D : 5.0D);
/* 129 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(getIsSummoned() ? (getIsDevil() ? 3 : 2) + this.damBonus : 1.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getIsBatHanging()
/*     */   {
/* 135 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(16), 0);
/*     */   }
/*     */   
/*     */   public void setIsBatHanging(boolean par1)
/*     */   {
/* 140 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 142 */     if (par1)
/*     */     {
/* 144 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)Utils.setBit(var2, 0)));
/*     */     }
/*     */     else
/*     */     {
/* 148 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)Utils.clearBit(var2, 0)));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getIsSummoned()
/*     */   {
/* 154 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(16), 1);
/*     */   }
/*     */   
/*     */   public void setIsSummoned(boolean par1)
/*     */   {
/* 159 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 161 */     if (par1)
/*     */     {
/* 163 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)Utils.setBit(var2, 1)));
/*     */     }
/*     */     else
/*     */     {
/* 167 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)Utils.clearBit(var2, 1)));
/*     */     }
/*     */     
/* 170 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(par1 ? (getIsDevil() ? 3 : 2) + this.damBonus : 1.0D);
/*     */   }
/*     */   
/*     */   public boolean getIsExplosive()
/*     */   {
/* 175 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(16), 2);
/*     */   }
/*     */   
/*     */   public void setIsExplosive(boolean par1)
/*     */   {
/* 180 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/* 181 */     if (par1)
/*     */     {
/* 183 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)Utils.setBit(var2, 2)));
/*     */     }
/*     */     else
/*     */     {
/* 187 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)Utils.clearBit(var2, 2)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getIsDevil()
/*     */   {
/* 194 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(16), 3);
/*     */   }
/*     */   
/*     */   public void setIsDevil(boolean par1)
/*     */   {
/* 199 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/* 200 */     if (par1)
/*     */     {
/* 202 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)Utils.setBit(var2, 3)));
/*     */     }
/*     */     else
/*     */     {
/* 206 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)Utils.clearBit(var2, 3)));
/*     */     }
/*     */     
/* 209 */     if (par1) {
/* 210 */       getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(getIsSummoned() ? (par1 ? 3 : 2) + this.damBonus : 1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getIsVampire()
/*     */   {
/* 216 */     return Utils.getBit(this.dataWatcher.getWatchableObjectByte(16), 4);
/*     */   }
/*     */   
/*     */   public void setIsVampire(boolean par1)
/*     */   {
/* 221 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/* 222 */     if (par1)
/*     */     {
/* 224 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)Utils.setBit(var2, 4)));
/*     */     }
/*     */     else
/*     */     {
/* 228 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)Utils.clearBit(var2, 4)));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onLivingUpdate()
/*     */   {
/* 234 */     if (isWet())
/*     */     {
/* 236 */       attackEntityFrom(DamageSource.drown, 1.0F);
/*     */     }
/*     */     
/* 239 */     super.onLivingUpdate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 248 */     super.onUpdate();
/*     */     
/* 250 */     if ((this.worldObj.isRemote) && (getIsExplosive())) {
/* 251 */       Thaumcraft.proxy.getFX().drawGenericParticles(this.prevPosX + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F, this.prevPosY + this.height / 2.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F, this.prevPosZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F, 0.0D, 0.0D, 0.0D, 1.0F, 1.0F, 1.0F, 0.8F, false, 151, 9, 1, 7 + this.rand.nextInt(5), 0, 1.0F + this.rand.nextFloat() * 0.5F, 0.0F, 0);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 259 */     if (getIsBatHanging())
/*     */     {
/* 261 */       this.motionX = (this.motionY = this.motionZ = 0.0D);
/* 262 */       this.posY = (MathHelper.floor_double(this.posY) + 1.0D - this.height);
/*     */     }
/*     */     else
/*     */     {
/* 266 */       this.motionY *= 0.6000000238418579D;
/*     */     }
/*     */     
/* 269 */     if ((this.worldObj.isRemote) && (!getIsVampire())) {
/* 270 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.prevPosX + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, this.prevPosY + this.height / 2.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, this.prevPosZ + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 275 */       this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.prevPosX + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, this.prevPosY + this.height / 2.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, this.prevPosZ + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateAITasks()
/*     */   {
/* 289 */     super.updateAITasks();
/*     */     
/* 291 */     if (this.attackTime > 0) { this.attackTime -= 1;
/*     */     }
/* 293 */     BlockPos blockpos = new BlockPos(this);
/* 294 */     BlockPos blockpos1 = blockpos.up();
/*     */     
/* 296 */     if (getIsBatHanging())
/*     */     {
/* 298 */       if (!this.worldObj.getBlockState(blockpos1).getBlock().isNormalCube())
/*     */       {
/* 300 */         setIsBatHanging(false);
/* 301 */         this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, blockpos, 0);
/*     */       }
/*     */       else
/*     */       {
/* 305 */         if (this.rand.nextInt(200) == 0)
/*     */         {
/* 307 */           this.rotationYawHead = this.rand.nextInt(360);
/*     */         }
/*     */         
/* 310 */         if (this.worldObj.getClosestPlayerToEntity(this, 4.0D) != null)
/*     */         {
/* 312 */           setIsBatHanging(false);
/* 313 */           this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, blockpos, 0);
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */     }
/* 319 */     else if (getAttackTarget() == null)
/*     */     {
/* 321 */       if (getIsSummoned()) {
/* 322 */         attackEntityFrom(DamageSource.generic, 2.0F);
/*     */       }
/* 324 */       if ((this.currentFlightTarget != null) && ((!this.worldObj.isAirBlock(this.currentFlightTarget)) || (this.currentFlightTarget.getY() < 1)))
/*     */       {
/*     */ 
/*     */ 
/* 328 */         this.currentFlightTarget = null;
/*     */       }
/*     */       
/* 331 */       if ((this.currentFlightTarget == null) || (this.rand.nextInt(30) == 0) || (getDistanceSqToCenter(this.currentFlightTarget) < 4.0D))
/*     */       {
/*     */ 
/* 334 */         this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 340 */       double var1 = this.currentFlightTarget.getX() + 0.5D - this.posX;
/* 341 */       double var3 = this.currentFlightTarget.getY() + 0.1D - this.posY;
/* 342 */       double var5 = this.currentFlightTarget.getZ() + 0.5D - this.posZ;
/* 343 */       this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.10000000149011612D;
/* 344 */       this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
/* 345 */       this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.10000000149011612D;
/* 346 */       float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
/* 347 */       float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
/* 348 */       this.moveForward = 0.5F;
/* 349 */       this.rotationYaw += var8;
/*     */       
/* 351 */       if ((this.rand.nextInt(100) == 0) && (this.worldObj.getBlockState(blockpos1).getBlock().isNormalCube()))
/*     */       {
/* 353 */         setIsBatHanging(true);
/*     */       }
/*     */     } else {
/* 356 */       double var1 = getAttackTarget().posX - this.posX;
/* 357 */       double var3 = getAttackTarget().posY + getAttackTarget().getEyeHeight() * 0.66F - this.posY;
/* 358 */       double var5 = getAttackTarget().posZ - this.posZ;
/* 359 */       this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.10000000149011612D;
/* 360 */       this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
/* 361 */       this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.10000000149011612D;
/* 362 */       float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
/* 363 */       float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
/* 364 */       this.moveForward = 0.5F;
/* 365 */       this.rotationYaw += var8;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 370 */     if (getAttackTarget() == null)
/*     */     {
/* 372 */       setAttackTarget(findPlayerToAttack());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/* 380 */     else if (getAttackTarget().isEntityAlive())
/*     */     {
/* 382 */       float f = getAttackTarget().getDistanceToEntity(this);
/*     */       
/* 384 */       if (canEntityBeSeen(getAttackTarget()))
/*     */       {
/* 386 */         attackEntity(getAttackTarget(), f);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 391 */       setAttackTarget(null);
/*     */     }
/*     */     
/* 394 */     if (((getAttackTarget() instanceof EntityPlayer)) && (((EntityPlayer)getAttackTarget()).capabilities.disableDamage))
/*     */     {
/* 396 */       setAttackTarget(null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/* 408 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void fall(float par1, float damageMultiplier) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateFallState(double p_180433_1_, boolean p_180433_3_, Block p_180433_4_, BlockPos p_180433_5_) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean doesEntityNotTriggerPressurePlate()
/*     */   {
/* 431 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*     */   {
/* 440 */     if ((isEntityInvulnerable(par1DamageSource)) || (par1DamageSource.isFireDamage()) || (par1DamageSource.isExplosion()))
/*     */     {
/*     */ 
/* 443 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 447 */     if ((!this.worldObj.isRemote) && (getIsBatHanging()))
/*     */     {
/* 449 */       setIsBatHanging(false);
/*     */     }
/*     */     
/* 452 */     return super.attackEntityFrom(par1DamageSource, par2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean attackEntityAsMob(Entity p_70652_1_)
/*     */   {
/* 460 */     return super.attackEntityAsMob(p_70652_1_);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void attackEntity(Entity par1Entity, float par2)
/*     */   {
/* 466 */     if ((this.attackTime <= 0) && (par2 < Math.max(2.5F, par1Entity.width * 1.1F)) && (par1Entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY) && (par1Entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY))
/*     */     {
/*     */ 
/*     */ 
/* 470 */       if (getIsSummoned()) {
/* 471 */         EntityUtils.setRecentlyHit((EntityLivingBase)par1Entity, 100);
/*     */       }
/*     */       
/* 474 */       if (getIsVampire()) {
/* 475 */         if ((this.owner != null) && (!this.owner.isPotionActive(Potion.regeneration.id))) {
/* 476 */           this.owner.addPotionEffect(new PotionEffect(Potion.regeneration.id, 26, 1));
/*     */         }
/* 478 */         heal(1.0F);
/*     */       }
/*     */       
/* 481 */       this.attackTime = 20;
/* 482 */       if (((getIsExplosive()) || (this.worldObj.rand.nextInt(10) == 0)) && (!this.worldObj.isRemote) && (!getIsDevil())) {
/* 483 */         par1Entity.hurtResistantTime = 0;
/* 484 */         this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.5F + (getIsExplosive() ? this.damBonus * 0.33F : 0.0F), false, false);
/* 485 */         setDead();
/*     */       }
/* 487 */       else if ((getIsVampire()) || (this.worldObj.rand.nextBoolean())) {
/* 488 */         double mx = par1Entity.motionX;
/* 489 */         double my = par1Entity.motionY;
/* 490 */         double mz = par1Entity.motionZ;
/* 491 */         attackEntityAsMob(par1Entity);
/* 492 */         par1Entity.isAirBorne = false;
/* 493 */         par1Entity.motionX = mx;
/* 494 */         par1Entity.motionY = my;
/* 495 */         par1Entity.motionZ = mz;
/*     */       } else {
/* 497 */         par1Entity.setFire(getIsSummoned() ? 4 : 2);
/*     */       }
/*     */       
/* 500 */       this.worldObj.playSoundAtEntity(this, "mob.bat.hurt", 0.5F, 0.9F + this.worldObj.rand.nextFloat() * 0.2F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected EntityLivingBase findPlayerToAttack()
/*     */   {
/* 509 */     double var1 = 12.0D;
/* 510 */     return getIsSummoned() ? null : this.worldObj.getClosestPlayerToEntity(this, var1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 519 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 520 */     this.dataWatcher.updateObject(16, Byte.valueOf(par1NBTTagCompound.getByte("BatFlags")));
/* 521 */     this.damBonus = par1NBTTagCompound.getByte("damBonus");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 530 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 531 */     par1NBTTagCompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
/* 532 */     par1NBTTagCompound.setByte("damBonus", (byte)this.damBonus);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getCanSpawnHere()
/*     */   {
/* 541 */     int i = MathHelper.floor_double(this.posX);
/* 542 */     int j = MathHelper.floor_double(getEntityBoundingBox().minY);
/* 543 */     int k = MathHelper.floor_double(this.posZ);
/* 544 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 545 */     int var4 = this.worldObj.getLight(blockpos);
/* 546 */     byte var5 = 7;
/* 547 */     return var4 > this.rand.nextInt(var5) ? false : super.getCanSpawnHere();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 556 */     if (!getIsSummoned()) return Items.gunpowder; return Item.getItemById(0);
/*     */   }
/*     */   
/*     */   protected boolean isValidLightLevel()
/*     */   {
/* 561 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\EntityFireBat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */