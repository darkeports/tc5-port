/*     */ package thaumcraft.common.entities.monster.tainted;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntitySenses;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import thaumcraft.api.ThaumcraftMaterials;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.entities.ITaintedMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ 
/*     */ public class EntityTaintacle extends EntityMob implements ITaintedMob, thaumcraft.common.entities.IEntityReach
/*     */ {
/*  33 */   public float flailIntensity = 1.0F;
/*     */   
/*     */   public EntityTaintacle(World par1World)
/*     */   {
/*  37 */     super(par1World);
/*  38 */     setSize(0.8F, 3.0F);
/*  39 */     this.experienceValue = 10;
/*     */     
/*  41 */     this.tasks.addTask(1, new thaumcraft.common.entities.ai.combat.AIAttackOnCollide(this, EntityLivingBase.class, 1.0D, false));
/*  42 */     this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
/*  43 */     this.tasks.addTask(3, new EntityAILookIdle(this));
/*  44 */     this.targetTasks.addTask(0, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/*  45 */     this.targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/*  51 */     return !ITaintedMob.class.isAssignableFrom(clazz);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/*  57 */     return ((otherEntity instanceof ITaintedMob)) || (isOnTeam(otherEntity.getTeam()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean getCanSpawnHere()
/*     */   {
/*  64 */     boolean onTaint = (this.worldObj.getBlockState(getPosition()).getBlock().getMaterial() == ThaumcraftMaterials.MATERIAL_TAINT) || (this.worldObj.getBlockState(getPosition().down()).getBlock().getMaterial() == ThaumcraftMaterials.MATERIAL_TAINT);
/*     */     
/*     */ 
/*  67 */     return (onTaint) && (this.worldObj.getDifficulty() != net.minecraft.world.EnumDifficulty.PEACEFUL);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  74 */     super.applyEntityAttributes();
/*  75 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
/*  76 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
/*  77 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   public void moveEntity(double par1, double par3, double par5)
/*     */   {
/*  83 */     par1 = 0.0D;
/*  84 */     par5 = 0.0D;
/*  85 */     if (par3 > 0.0D) par3 = 0.0D;
/*  86 */     super.moveEntity(par1, par3, par5);
/*     */   }
/*     */   
/*     */   public void onUpdate()
/*     */   {
/*  91 */     super.onUpdate();
/*     */     
/*  93 */     if ((!this.worldObj.isRemote) && (this.ticksExisted % 20 == 0))
/*     */     {
/*  95 */       boolean onTaint = (this.worldObj.getBlockState(getPosition()).getBlock().getMaterial() == ThaumcraftMaterials.MATERIAL_TAINT) || (this.worldObj.getBlockState(getPosition().down()).getBlock().getMaterial() == ThaumcraftMaterials.MATERIAL_TAINT);
/*     */       
/*  97 */       if (!onTaint) { damageEntity(DamageSource.starve, 1.0F);
/*     */       }
/*  99 */       if ((!(this instanceof EntityTaintacleSmall)) && (this.ticksExisted % 40 == 0) && (getAttackTarget() != null) && (getDistanceSqToEntity(getAttackTarget()) > 16.0D) && (getDistanceSqToEntity(getAttackTarget()) < 256.0D) && (getEntitySenses().canSee(getAttackTarget())))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 105 */         spawnTentacles(getAttackTarget());
/*     */       }
/*     */     }
/*     */     
/* 109 */     if (this.worldObj.isRemote) {
/* 110 */       if (this.flailIntensity > 1.0F) { this.flailIntensity -= 0.01F;
/*     */       }
/* 112 */       if ((this.ticksExisted < this.height * 10.0F) && (this.onGround)) {
/* 113 */         Thaumcraft.proxy.getFX().tentacleAriseFX(this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void spawnTentacles(Entity entity)
/*     */   {
/* 120 */     if ((this.worldObj.getBiomeGenForCoords(entity.getPosition()).biomeID == Config.biomeEldritchID) || (this.worldObj.getBlockState(entity.getPosition()).getBlock().getMaterial() == ThaumcraftMaterials.MATERIAL_TAINT) || (this.worldObj.getBlockState(entity.getPosition().down()).getBlock().getMaterial() == ThaumcraftMaterials.MATERIAL_TAINT))
/*     */     {
/*     */ 
/* 123 */       EntityTaintacleSmall taintlet = new EntityTaintacleSmall(this.worldObj);
/* 124 */       taintlet.setLocationAndAngles(entity.posX + this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat(), entity.posY, entity.posZ + this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat(), 0.0F, 0.0F);
/*     */       
/*     */ 
/*     */ 
/* 128 */       this.worldObj.spawnEntityInWorld(taintlet);
/* 129 */       playSound("thaumcraft:tentacle", getSoundVolume(), getSoundPitch());
/* 130 */       if ((this.worldObj.getBiomeGenForCoords(entity.getPosition()).biomeID == Config.biomeEldritchID) && (this.worldObj.isAirBlock(entity.getPosition())) && (thaumcraft.common.lib.utils.BlockUtils.isAdjacentToSolidBlock(this.worldObj, entity.getPosition())))
/*     */       {
/* 132 */         this.worldObj.setBlockState(entity.getPosition(), BlocksTC.taintFibre.getDefaultState());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void faceEntity(Entity par1Entity, float par2)
/*     */   {
/* 141 */     double d0 = par1Entity.posX - this.posX;
/* 142 */     double d1 = par1Entity.posZ - this.posZ;
/*     */     
/*     */ 
/* 145 */     float f2 = (float)(Math.atan2(d1, d0) * 180.0D / 3.141592653589793D) - 90.0F;
/* 146 */     this.rotationYaw = updateRotation(this.rotationYaw, f2, par2);
/*     */   }
/*     */   
/*     */   protected float updateRotation(float par1, float par2, float par3)
/*     */   {
/* 151 */     float f3 = MathHelper.wrapAngleTo180_float(par2 - par1);
/*     */     
/* 153 */     if (f3 > par3)
/*     */     {
/* 155 */       f3 = par3;
/*     */     }
/*     */     
/* 158 */     if (f3 < -par3)
/*     */     {
/* 160 */       f3 = -par3;
/*     */     }
/*     */     
/* 163 */     return par1 + f3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getTalkInterval()
/*     */   {
/* 170 */     return 200;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 177 */     return "thaumcraft:roots";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected float getSoundPitch()
/*     */   {
/* 184 */     return 1.3F - this.height / 10.0F;
/*     */   }
/*     */   
/*     */   protected float getSoundVolume()
/*     */   {
/* 189 */     return this.height / 8.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 196 */     return "thaumcraft:tentacle";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 203 */     return "thaumcraft:tentacle";
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 209 */     return ItemsTC.tainted;
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 214 */     if (this.worldObj.rand.nextBoolean()) {
/* 215 */       entityDropItem(new ItemStack(ItemsTC.tainted, 1, 0), this.height / 2.0F);
/*     */     } else {
/* 217 */       entityDropItem(new ItemStack(ItemsTC.tainted, 1, 1), this.height / 2.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public void handleStatusUpdate(byte par1)
/*     */   {
/* 224 */     if (par1 == 16)
/*     */     {
/* 226 */       this.flailIntensity = 3.0F;
/*     */     }
/*     */     else
/*     */     {
/* 230 */       super.handleStatusUpdate(par1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityAsMob(Entity p_70652_1_)
/*     */   {
/* 237 */     this.worldObj.setEntityState(this, (byte)16);
/* 238 */     playSound("thaumcraft:tentacle", getSoundVolume(), getSoundPitch());
/* 239 */     return super.attackEntityAsMob(p_70652_1_);
/*     */   }
/*     */   
/*     */ 
/*     */   public double getReach()
/*     */   {
/* 245 */     return 2.0F + this.rand.nextFloat();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\tainted\EntityTaintacle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */