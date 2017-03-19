/*     */ package thaumcraft.common.entities.monster;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.ReflectionHelper;
/*     */ import thaumcraft.api.entities.ITaintedMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class EntityThaumicSlime
/*     */   extends EntitySlime implements ITaintedMob
/*     */ {
/*     */   public EntityThaumicSlime(World par1World)
/*     */   {
/*  31 */     super(par1World);
/*  32 */     int i = 1 << 1 + this.rand.nextInt(3);
/*  33 */     setSlimeSize(i);
/*     */   }
/*     */   
/*  36 */   int launched = 10;
/*     */   
/*     */   public EntityThaumicSlime(World par1World, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving)
/*     */   {
/*  40 */     super(par1World);
/*  41 */     setSlimeSize(1);
/*  42 */     this.posY = ((par2EntityLiving.getEntityBoundingBox().minY + par2EntityLiving.getEntityBoundingBox().maxY) / 2.0D);
/*  43 */     double var6 = par3EntityLiving.posX - par2EntityLiving.posX;
/*  44 */     double var8 = par3EntityLiving.getEntityBoundingBox().minY + par3EntityLiving.height / 3.0F - this.posY;
/*  45 */     double var10 = par3EntityLiving.posZ - par2EntityLiving.posZ;
/*  46 */     double var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);
/*     */     
/*  48 */     if (var12 >= 1.0E-7D)
/*     */     {
/*  50 */       float var14 = (float)(Math.atan2(var10, var6) * 180.0D / 3.141592653589793D) - 90.0F;
/*  51 */       float var15 = (float)-(Math.atan2(var8, var12) * 180.0D / 3.141592653589793D);
/*  52 */       double var16 = var6 / var12;
/*  53 */       double var18 = var10 / var12;
/*  54 */       setLocationAndAngles(par2EntityLiving.posX + var16, this.posY, par2EntityLiving.posZ + var18, var14, var15);
/*  55 */       float var20 = (float)var12 * 0.2F;
/*  56 */       setThrowableHeading(var6, var8 + var20, var10, 1.5F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
/*     */   {
/*  62 */     float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
/*  63 */     par1 /= var9;
/*  64 */     par3 /= var9;
/*  65 */     par5 /= var9;
/*  66 */     par1 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
/*  67 */     par3 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
/*  68 */     par5 += this.rand.nextGaussian() * 0.007499999832361937D * par8;
/*  69 */     par1 *= par7;
/*  70 */     par3 *= par7;
/*  71 */     par5 *= par7;
/*  72 */     this.motionX = par1;
/*  73 */     this.motionY = par3;
/*  74 */     this.motionZ = par5;
/*  75 */     float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
/*  76 */     this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / 3.141592653589793D));
/*  77 */     this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(par3, var10) * 180.0D / 3.141592653589793D));
/*     */   }
/*     */   
/*     */ 
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
/*     */   {
/*  83 */     int i = this.rand.nextInt(3);
/*     */     
/*  85 */     if ((i < 2) && (this.rand.nextFloat() < 0.5F * p_180482_1_.getClampedAdditionalDifficulty()))
/*     */     {
/*  87 */       i++;
/*     */     }
/*     */     
/*  90 */     int j = 1 << i;
/*  91 */     setSlimeSize(j);
/*  92 */     return super.onInitialSpawn(p_180482_1_, p_180482_2_);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSlimeSize(int par1)
/*     */   {
/*  98 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
/*  99 */     setSize(0.51000005F * par1, 0.51000005F * par1);
/* 100 */     setPosition(this.posX, this.posY, this.posZ);
/* 101 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(par1 * par1);
/* 102 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2F + 0.1F * par1);
/* 103 */     setHealth(getMaxHealth());
/* 104 */     this.experienceValue = (par1 + 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 111 */     super.writeEntityToNBT(par1NBTTagCompound);
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 117 */     super.readEntityFromNBT(par1NBTTagCompound);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 127 */     boolean wasOnGround = ((Boolean)ReflectionHelper.getPrivateValue(EntitySlime.class, this, new String[] { "wasOnGround", "field_175452_bi", "bk" })).booleanValue();
/*     */     
/* 129 */     int i = getSlimeSize();
/*     */     
/* 131 */     if ((this.onGround) && (!wasOnGround)) {
/* 132 */       Utils.setPrivateFinalValue(EntitySlime.class, this, Boolean.valueOf(true), new String[] { "wasOnGround", "field_175452_bi", "bk" });
/* 133 */       float sa = this.squishAmount;
/* 134 */       super.onUpdate();
/* 135 */       this.squishAmount = sa;
/*     */       
/* 137 */       if (this.worldObj.isRemote) {
/* 138 */         for (int j = 0; j < i * 2; j++)
/*     */         {
/* 140 */           Thaumcraft.proxy.getFX().slimeJumpFX(this, i);
/*     */         }
/*     */       }
/* 143 */       if (makesSoundOnLand())
/*     */       {
/* 145 */         playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
/*     */       }
/*     */       
/* 148 */       this.squishAmount = -0.5F;
/*     */       
/* 150 */       Utils.setPrivateFinalValue(EntitySlime.class, this, Boolean.valueOf(this.onGround), new String[] { "wasOnGround", "field_175452_bi", "bk" });
/*     */       
/* 152 */       alterSquishAmount();
/*     */     }
/*     */     else {
/* 155 */       super.onUpdate();
/*     */     }
/*     */     
/* 158 */     if (this.worldObj.isRemote)
/*     */     {
/* 160 */       if (this.launched > 0) {
/* 161 */         this.launched -= 1;
/* 162 */         for (int j = 0; j < i * (this.launched + 1); j++)
/*     */         {
/* 164 */           Thaumcraft.proxy.getFX().slimeJumpFX(this, i);
/*     */         }
/*     */       }
/*     */       
/* 168 */       float ff = getSlimeSize();
/* 169 */       setSize(0.6F * ff, 0.6F * ff);
/* 170 */       setSize(0.51000005F * ff, 0.51000005F * ff);
/* 171 */     } else if (!this.isDead)
/*     */     {
/* 173 */       EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 16.0D);
/*     */       
/* 175 */       if (entityplayer != null)
/*     */       {
/* 177 */         if (this.spitCounter > 0) this.spitCounter -= 1;
/* 178 */         faceEntity(entityplayer, 10.0F, 20.0F);
/* 179 */         if ((getDistanceToEntity(entityplayer) > 4.0F) && (this.spitCounter <= 0) && (getSlimeSize() > 2)) {
/* 180 */           this.spitCounter = 101;
/*     */           
/* 182 */           if (!this.worldObj.isRemote) {
/* 183 */             EntityThaumicSlime flyslime = new EntityThaumicSlime(this.worldObj, this, entityplayer);
/* 184 */             this.worldObj.spawnEntityInWorld(flyslime);
/*     */           }
/* 186 */           this.worldObj.playSoundAtEntity(this, "thaumcraft:gore", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
/* 187 */           setSlimeSize(getSlimeSize() - 1);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 193 */   int spitCounter = 100;
/*     */   
/*     */ 
/*     */ 
/*     */   protected EntityThaumicSlime createInstance()
/*     */   {
/* 199 */     return new EntityThaumicSlime(this.worldObj);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDead()
/*     */   {
/* 208 */     int i = getSlimeSize();
/*     */     
/* 210 */     if ((!this.worldObj.isRemote) && (i > 1) && (getHealth() <= 0.0F))
/*     */     {
/* 212 */       for (int k = 0; k < i; k++)
/*     */       {
/* 214 */         float f = (k % 2 - 0.5F) * i / 4.0F;
/* 215 */         float f1 = (k / 2 - 0.5F) * i / 4.0F;
/* 216 */         EntityThaumicSlime entityslime = createInstance();
/* 217 */         entityslime.setSlimeSize(1);
/* 218 */         entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5D, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
/* 219 */         this.worldObj.spawnEntityInWorld(entityslime);
/*     */       }
/*     */     }
/*     */     
/* 223 */     this.isDead = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getCanSpawnHere()
/*     */   {
/* 252 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int getAttackStrength()
/*     */   {
/* 260 */     return getSlimeSize() + 1;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canDamagePlayer()
/*     */   {
/* 266 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void func_175451_e(EntityLivingBase p_175451_1_)
/*     */   {
/* 272 */     int i = getSlimeSize();
/* 273 */     if (this.launched > 0) i += 2;
/* 274 */     if ((canEntityBeSeen(p_175451_1_)) && (getDistanceSqToEntity(p_175451_1_) < 0.6D * i * 0.6D * i) && (p_175451_1_.attackEntityFrom(DamageSource.causeMobDamage(this), getAttackStrength())))
/*     */     {
/* 276 */       playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/* 277 */       applyEnchantments(this, p_175451_1_);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 284 */     return getSlimeSize() > 1 ? ItemsTC.tainted : Item.getItemById(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\EntityThaumicSlime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */