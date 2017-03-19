/*     */ package thaumcraft.common.entities.monster.boss;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.entities.IEldritchMob;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.world.BlockLoot;
/*     */ import thaumcraft.common.entities.monster.mods.ChampionModifier;
/*     */ import thaumcraft.common.entities.projectile.EntityGolemOrb;
/*     */ 
/*     */ public class EntityEldritchGolem extends EntityThaumcraftBoss implements IEldritchMob, IRangedAttackMob
/*     */ {
/*     */   public EntityEldritchGolem(World p_i1745_1_)
/*     */   {
/*  43 */     super(p_i1745_1_);
/*  44 */     this.tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
/*  45 */     this.tasks.addTask(3, new thaumcraft.common.entities.ai.combat.AIAttackOnCollide(this, EntityLivingBase.class, 1.1D, false));
/*  46 */     this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.8D));
/*  47 */     this.tasks.addTask(7, new EntityAIWander(this, 0.8D));
/*  48 */     this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  49 */     this.tasks.addTask(8, new net.minecraft.entity.ai.EntityAILookIdle(this));
/*  50 */     this.targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, true, new Class[0]));
/*  51 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  52 */     setSize(1.75F, 3.5F);
/*  53 */     this.isImmuneToFire = true;
/*     */   }
/*     */   
/*     */   public void generateName()
/*     */   {
/*  58 */     int t = (int)getEntityAttribute(thaumcraft.common.lib.utils.EntityUtils.CHAMPION_MOD).getAttributeValue();
/*  59 */     if (t >= 0) {
/*  60 */       setCustomNameTag(String.format(net.minecraft.util.StatCollector.translateToLocal("entity.Thaumcraft.EldritchGolem.name.custom"), new Object[] { ChampionModifier.mods[t].getModNameLocalized() }));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  69 */     super.entityInit();
/*  70 */     getDataWatcher().addObject(12, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public boolean isHeadless() {
/*  74 */     return getDataWatcher().getWatchableObjectByte(12) == 1;
/*     */   }
/*     */   
/*     */   public void setHeadless(boolean par1)
/*     */   {
/*  79 */     this.dataWatcher.updateObject(12, Byte.valueOf((byte)(par1 ? 1 : 0)));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/*  85 */     super.writeEntityToNBT(nbt);
/*  86 */     nbt.setBoolean("headless", isHeadless());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/*  95 */     super.readEntityFromNBT(nbt);
/*  96 */     setHeadless(nbt.getBoolean("headless"));
/*  97 */     if (isHeadless()) {
/*  98 */       makeHeadless();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/* 107 */     return isHeadless() ? 3.33F : 3.0F;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTotalArmorValue()
/*     */   {
/* 113 */     return super.getTotalArmorValue() + 6;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/* 119 */     super.applyEntityAttributes();
/* 120 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
/* 121 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
/* 122 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 128 */     return "mob.irongolem.hit";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 134 */     return "mob.irongolem.death";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
/*     */   {
/* 141 */     playSound("mob.irongolem.walk", 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance diff, IEntityLivingData data)
/*     */   {
/* 147 */     this.spawnTimer = 100;
/* 148 */     return super.onInitialSpawn(diff, data);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onLivingUpdate()
/*     */   {
/* 154 */     super.onLivingUpdate();
/*     */     
/* 156 */     if (this.attackTimer > 0)
/*     */     {
/* 158 */       this.attackTimer -= 1;
/*     */     }
/*     */     
/* 161 */     if ((this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D) && (this.rand.nextInt(5) == 0))
/*     */     {
/*     */ 
/* 164 */       IBlockState bs = this.worldObj.getBlockState(getPosition());
/*     */       
/* 166 */       if (bs.getBlock().getMaterial() != Material.air)
/*     */       {
/* 168 */         this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, getEntityBoundingBox().minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(bs) });
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 176 */       if ((!this.worldObj.isRemote) && 
/* 177 */         ((bs.getBlock() instanceof BlockLoot))) {
/* 178 */         this.worldObj.destroyBlock(getPosition(), true);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 183 */     if (!this.worldObj.isRemote) {
/* 184 */       IBlockState bs = this.worldObj.getBlockState(getPosition());
/* 185 */       float h = bs.getBlock().getBlockHardness(this.worldObj, getPosition());
/* 186 */       if ((h >= 0.0F) && (h <= 0.15F)) {
/* 187 */         this.worldObj.destroyBlock(getPosition(), true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource source, float damage)
/*     */   {
/* 195 */     if ((!this.worldObj.isRemote) && (damage > getHealth()) && (!isHeadless())) {
/* 196 */       setHeadless(true);
/* 197 */       this.spawnTimer = 100;
/* 198 */       double xx = MathHelper.cos(this.rotationYaw % 360.0F / 180.0F * 3.1415927F) * 0.75F;
/* 199 */       double zz = MathHelper.sin(this.rotationYaw % 360.0F / 180.0F * 3.1415927F) * 0.75F;
/* 200 */       this.worldObj.createExplosion(this, this.posX + xx, this.posY + getEyeHeight(), this.posZ + zz, 2.0F, false);
/* 201 */       makeHeadless();
/* 202 */       return false;
/*     */     }
/* 204 */     return super.attackEntityFrom(source, damage);
/*     */   }
/*     */   
/*     */   void makeHeadless() {
/* 208 */     this.tasks.addTask(2, new thaumcraft.common.entities.ai.combat.AILongRangeAttack(this, 3.0D, 1.0D, 5, 5, 24.0F));
/*     */   }
/*     */   
/* 211 */   int beamCharge = 0;
/* 212 */   boolean chargingBeam = false;
/*     */   
/*     */ 
/*     */   public boolean attackEntityAsMob(Entity target)
/*     */   {
/* 217 */     if (this.attackTimer > 0) { return false;
/*     */     }
/* 219 */     this.attackTimer = 10;
/* 220 */     this.worldObj.setEntityState(this, (byte)4);
/* 221 */     boolean flag = target.attackEntityFrom(DamageSource.causeMobDamage(this), (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 0.75F);
/*     */     
/*     */ 
/* 224 */     if (flag)
/*     */     {
/* 226 */       target.motionY += 0.2000000059604645D;
/* 227 */       if (isHeadless()) {
/* 228 */         target.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * 1.5F, 0.1D, MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * 1.5F);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 234 */     return flag;
/*     */   }
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float f)
/*     */   {
/* 239 */     if ((canEntityBeSeen(entitylivingbase)) && (!this.chargingBeam) && (this.beamCharge > 0)) {
/* 240 */       this.beamCharge -= 15 + this.rand.nextInt(5);
/* 241 */       getLookHelper().setLookPosition(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY + entitylivingbase.height / 2.0F, entitylivingbase.posZ, 30.0F, 30.0F);
/*     */       
/*     */ 
/*     */ 
/* 245 */       Vec3 v = getLook(1.0F);
/* 246 */       EntityGolemOrb blast = new EntityGolemOrb(this.worldObj, this, entitylivingbase, false);
/* 247 */       blast.posX += v.xCoord;
/* 248 */       blast.posZ += v.zCoord;
/* 249 */       blast.setPosition(blast.posX, blast.posY, blast.posZ);
/*     */       
/* 251 */       double d0 = entitylivingbase.posX + entitylivingbase.motionX - this.posX;
/* 252 */       double d1 = entitylivingbase.posY - this.posY - entitylivingbase.height / 2.0F;
/* 253 */       double d2 = entitylivingbase.posZ + entitylivingbase.motionZ - this.posZ;
/*     */       
/* 255 */       blast.setThrowableHeading(d0, d1, d2, 0.66F, 5.0F);
/*     */       
/* 257 */       playSound("thaumcraft:egattack", 1.0F, 1.0F + this.rand.nextFloat() * 0.1F);
/* 258 */       this.worldObj.spawnEntityInWorld(blast);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void handleStatusUpdate(byte p_70103_1_)
/*     */   {
/* 267 */     if (p_70103_1_ == 4)
/*     */     {
/* 269 */       this.attackTimer = 10;
/* 270 */       playSound("mob.irongolem.throw", 1.0F, 1.0F);
/*     */ 
/*     */     }
/* 273 */     else if (p_70103_1_ == 18)
/*     */     {
/* 275 */       this.spawnTimer = 150;
/*     */ 
/*     */     }
/* 278 */     else if (p_70103_1_ == 19)
/*     */     {
/* 280 */       if (this.arcing == 0) {
/* 281 */         float radius = 2.0F + this.rand.nextFloat() * 2.0F;
/* 282 */         double radians = Math.toRadians(this.rand.nextInt(360));
/* 283 */         double deltaX = radius * Math.cos(radians);
/* 284 */         double deltaZ = radius * Math.sin(radians);
/*     */         
/* 286 */         int bx = MathHelper.floor_double(this.posX + deltaX);
/* 287 */         int by = MathHelper.floor_double(this.posY);
/* 288 */         int bz = MathHelper.floor_double(this.posZ + deltaZ);
/* 289 */         BlockPos bp = new BlockPos(bx, by, bz);
/* 290 */         int c = 0;
/* 291 */         while ((c < 5) && (this.worldObj.isAirBlock(bp))) {
/* 292 */           c++;
/* 293 */           by--;
/*     */         }
/* 295 */         if ((this.worldObj.isAirBlock(bp.up())) && (!this.worldObj.isAirBlock(bp))) {
/* 296 */           this.ax = bx;
/* 297 */           this.ay = by;
/* 298 */           this.az = bz;
/* 299 */           this.arcing = (8 + this.rand.nextInt(5));
/* 300 */           this.worldObj.playSound(this.posX, this.posY, this.posZ, "thaumcraft:jacobs", 0.8F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F, false);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 306 */       super.handleStatusUpdate(p_70103_1_);
/*     */     }
/*     */   }
/*     */   
/* 310 */   int arcing = 0;
/* 311 */   int ax = 0;
/* 312 */   int ay = 0;
/* 313 */   int az = 0;
/*     */   private int attackTimer;
/*     */   
/*     */   public void onUpdate() {
/* 317 */     if (getSpawnTimer() == 150) this.worldObj.setEntityState(this, (byte)18);
/* 318 */     if (getSpawnTimer() > 0) {
/* 319 */       heal(2.0F);
/*     */     }
/* 321 */     super.onUpdate();
/* 322 */     if (this.worldObj.isRemote) {
/* 323 */       if (isHeadless()) {
/* 324 */         this.rotationPitch = 0.0F;
/* 325 */         float f1 = MathHelper.cos(-this.renderYawOffset * 0.017453292F - 3.1415927F);
/* 326 */         float f2 = MathHelper.sin(-this.renderYawOffset * 0.017453292F - 3.1415927F);
/* 327 */         float f3 = -MathHelper.cos(-this.rotationPitch * 0.017453292F);
/* 328 */         float f4 = MathHelper.sin(-this.rotationPitch * 0.017453292F);
/* 329 */         Vec3 v = new Vec3(f2 * f3, f4, f1 * f3);
/*     */         
/* 331 */         if (this.rand.nextInt(20) == 0) {
/* 332 */           float a = (this.rand.nextFloat() - this.rand.nextFloat()) / 2.0F;
/* 333 */           float b = (this.rand.nextFloat() - this.rand.nextFloat()) / 2.0F;
/*     */           
/* 335 */           Thaumcraft.proxy.getFX().spark((float)(this.posX + v.xCoord + a), (float)this.posY + getEyeHeight() - 0.25F, (float)(this.posZ + v.zCoord + b), 0.3F, 0.65F + this.rand.nextFloat() * 0.1F, 1.0F, 1.0F, 0.8F);
/*     */         }
/*     */         
/*     */ 
/* 339 */         Thaumcraft.proxy.getFX().drawVentParticles((float)this.posX + v.xCoord * 0.66D, (float)this.posY + getEyeHeight() - 0.75F, (float)this.posZ + v.zCoord * 0.66D, 0.0D, 0.001D, 0.0D, 5592405, 4.0F);
/*     */         
/*     */ 
/*     */ 
/* 343 */         if (this.arcing > 0) {
/* 344 */           Thaumcraft.proxy.getFX().arcLightning(this.posX, this.posY + this.height / 2.0F, this.posZ, this.ax + 0.5D, this.ay + 1, this.az + 0.5D, 0.65F + this.rand.nextFloat() * 0.1F, 1.0F, 1.0F, 1.0F - this.arcing / 10.0F);
/*     */           
/* 346 */           this.arcing -= 1;
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 351 */       if ((isHeadless()) && (this.beamCharge <= 0)) {
/* 352 */         this.chargingBeam = true;
/*     */       }
/* 354 */       if ((isHeadless()) && (this.chargingBeam)) {
/* 355 */         this.beamCharge += 1;
/* 356 */         this.worldObj.setEntityState(this, (byte)19);
/* 357 */         if (this.beamCharge == 150) this.chargingBeam = false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getAttackTimer()
/*     */   {
/* 365 */     return this.attackTimer;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\boss\EntityEldritchGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */