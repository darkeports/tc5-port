/*     */ package thaumcraft.common.entities.monster.cult;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAIOpenDoor;
/*     */ import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.entities.ai.combat.AILongRangeAttack;
/*     */ import thaumcraft.common.entities.ai.misc.AIAltarFocus;
/*     */ import thaumcraft.common.entities.projectile.EntityGolemOrb;
/*     */ 
/*     */ public class EntityCultistCleric extends EntityCultist implements IRangedAttackMob, IEntityAdditionalSpawnData
/*     */ {
/*     */   public EntityCultistCleric(World p_i1745_1_)
/*     */   {
/*  40 */     super(p_i1745_1_);
/*  41 */     this.tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
/*  42 */     this.tasks.addTask(1, new AIAltarFocus(this));
/*  43 */     this.tasks.addTask(2, new AILongRangeAttack(this, 2.0D, 1.0D, 20, 40, 24.0F));
/*  44 */     this.tasks.addTask(3, new thaumcraft.common.entities.ai.combat.AIAttackOnCollide(this, EntityLivingBase.class, 1.0D, false));
/*  45 */     this.tasks.addTask(4, new EntityAIRestrictOpenDoor(this));
/*  46 */     this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
/*  47 */     this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.8D));
/*  48 */     this.tasks.addTask(7, new EntityAIWander(this, 0.8D));
/*  49 */     this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  50 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/*  51 */     this.targetTasks.addTask(1, new thaumcraft.common.entities.ai.combat.AICultistHurtByTarget(this, true));
/*  52 */     this.targetTasks.addTask(2, new net.minecraft.entity.ai.EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  59 */     super.applyEntityAttributes();
/*  60 */     getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.maxHealth).setBaseValue(24.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void func_180481_a(DifficultyInstance diff)
/*     */   {
/*  66 */     setCurrentItemOrArmor(4, new ItemStack(ItemsTC.crimsonRobeHelm));
/*  67 */     setCurrentItemOrArmor(3, new ItemStack(ItemsTC.crimsonRobeChest));
/*  68 */     setCurrentItemOrArmor(2, new ItemStack(ItemsTC.crimsonRobeLegs));
/*  69 */     if (this.rand.nextFloat() < (this.worldObj.getDifficulty() == EnumDifficulty.HARD ? 0.3F : 0.1F))
/*     */     {
/*  71 */       setCurrentItemOrArmor(1, new ItemStack(ItemsTC.crimsonBoots));
/*     */     }
/*     */   }
/*     */   
/*  75 */   public int rage = 0;
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float f)
/*     */   {
/*  79 */     double d0 = entitylivingbase.posX - this.posX;
/*  80 */     double d1 = entitylivingbase.getEntityBoundingBox().minY + entitylivingbase.height / 2.0F - (this.posY + this.height / 2.0F);
/*  81 */     double d2 = entitylivingbase.posZ - this.posZ;
/*  82 */     swingItem();
/*  83 */     if (this.rand.nextFloat() > 0.66F) {
/*  84 */       EntityGolemOrb blast = new EntityGolemOrb(this.worldObj, this, entitylivingbase, true);
/*  85 */       blast.posX += blast.motionX / 2.0D;
/*  86 */       blast.posZ += blast.motionZ / 2.0D;
/*  87 */       blast.setPosition(blast.posX, blast.posY, blast.posZ);
/*  88 */       blast.setThrowableHeading(d0, d1 + 2.0D, d2, 0.66F, 3.0F);
/*  89 */       playSound("thaumcraft:egattack", 1.0F, 1.0F + this.rand.nextFloat() * 0.1F);
/*  90 */       this.worldObj.spawnEntityInWorld(blast);
/*     */     } else {
/*  92 */       float f1 = MathHelper.sqrt_float(f) * 0.5F;
/*  93 */       this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, getPosition(), 0);
/*     */       
/*  95 */       for (int i = 0; i < 3; i++)
/*     */       {
/*  97 */         EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.worldObj, this, d0 + this.rand.nextGaussian() * f1, d1, d2 + this.rand.nextGaussian() * f1);
/*  98 */         entitysmallfireball.posY = (this.posY + this.height / 2.0F + 0.5D);
/*  99 */         this.worldObj.spawnEntityInWorld(entitysmallfireball);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canDespawn()
/*     */   {
/* 107 */     return !getIsRitualist();
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/* 113 */     super.entityInit();
/* 114 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public boolean getIsRitualist()
/*     */   {
/* 119 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
/*     */   }
/*     */   
/*     */   public void setIsRitualist(boolean par1)
/*     */   {
/* 124 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 126 */     if (par1)
/*     */     {
/* 128 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x1)));
/*     */     }
/*     */     else
/*     */     {
/* 132 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFE)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
/*     */   {
/* 139 */     if (isEntityInvulnerable(p_70097_1_))
/*     */     {
/* 141 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 145 */     setIsRitualist(false);
/* 146 */     return super.attackEntityFrom(p_70097_1_, p_70097_2_);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 156 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 157 */     this.dataWatcher.updateObject(16, Byte.valueOf(par1NBTTagCompound.getByte("Flags")));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 163 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 164 */     par1NBTTagCompound.setByte("Flags", this.dataWatcher.getWatchableObjectByte(16));
/*     */   }
/*     */   
/*     */   public void writeSpawnData(ByteBuf data)
/*     */   {
/* 169 */     data.writeInt(getHomePosition().getX());
/* 170 */     data.writeInt(getHomePosition().getY());
/* 171 */     data.writeInt(getHomePosition().getZ());
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf data)
/*     */   {
/* 176 */     setHomePosAndDistance(new BlockPos(data.readInt(), data.readInt(), data.readInt()), 8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 183 */     super.onUpdate();
/* 184 */     if ((this.worldObj.isRemote) && (getIsRitualist())) {
/* 185 */       double d0 = getHomePosition().getX() + 0.5D - this.posX;
/* 186 */       double d1 = getHomePosition().getY() + 1.5D - (this.posY + getEyeHeight());
/* 187 */       double d2 = getHomePosition().getZ() + 0.5D - this.posZ;
/* 188 */       double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
/* 189 */       float f = (float)(Math.atan2(d2, d0) * 180.0D / 3.141592653589793D) - 90.0F;
/* 190 */       float f1 = (float)-(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D);
/* 191 */       this.rotationPitch = updateRotation(this.rotationPitch, f1, 10.0F);
/* 192 */       this.rotationYawHead = updateRotation(this.rotationYawHead, f, getVerticalFaceSpeed());
/*     */     }
/* 194 */     if ((!this.worldObj.isRemote) && (getIsRitualist()) && 
/* 195 */       (this.rage >= 5)) { setIsRitualist(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private float updateRotation(float p_75652_1_, float p_75652_2_, float p_75652_3_)
/*     */   {
/* 201 */     float f3 = MathHelper.wrapAngleTo180_float(p_75652_2_ - p_75652_1_);
/*     */     
/* 203 */     if (f3 > p_75652_3_)
/*     */     {
/* 205 */       f3 = p_75652_3_;
/*     */     }
/*     */     
/* 208 */     if (f3 < -p_75652_3_)
/*     */     {
/* 210 */       f3 = -p_75652_3_;
/*     */     }
/*     */     
/* 213 */     return p_75652_1_ + f3;
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 219 */     return "thaumcraft:chant";
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTalkInterval()
/*     */   {
/* 225 */     return 500;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void handleStatusUpdate(byte par1)
/*     */   {
/* 233 */     if (par1 == 19)
/*     */     {
/* 235 */       for (int i = 0; i < 3; i++)
/*     */       {
/* 237 */         double d0 = this.rand.nextGaussian() * 0.02D;
/* 238 */         double d1 = this.rand.nextGaussian() * 0.02D;
/* 239 */         double d2 = this.rand.nextGaussian() * 0.02D;
/* 240 */         this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2, new int[0]);
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 248 */       super.handleStatusUpdate(par1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\cult\EntityCultistCleric.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */