/*     */ package thaumcraft.common.entities.monster.boss;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.DataWatcher;
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
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.living.EnderTeleportEvent;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.entities.IEldritchMob;
/*     */ import thaumcraft.api.internal.EnumWarpType;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.monster.EntityEldritchGuardian;
/*     */ import thaumcraft.common.entities.monster.mods.ChampionModifier;
/*     */ import thaumcraft.common.entities.projectile.EntityEldritchOrb;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXBlockArc;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXSonic;
/*     */ 
/*     */ public class EntityEldritchWarden extends EntityThaumcraftBoss implements IRangedAttackMob, IEldritchMob
/*     */ {
/*     */   public EntityEldritchWarden(World p_i1745_1_)
/*     */   {
/*  52 */     super(p_i1745_1_);
/*     */     
/*  54 */     this.tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
/*  55 */     this.tasks.addTask(2, new thaumcraft.common.entities.ai.combat.AILongRangeAttack(this, 3.0D, 1.0D, 20, 40, 24.0F));
/*  56 */     this.tasks.addTask(3, new thaumcraft.common.entities.ai.combat.AIAttackOnCollide(this, EntityLivingBase.class, 1.1D, false));
/*  57 */     this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.8D));
/*  58 */     this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
/*  59 */     this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  60 */     this.tasks.addTask(8, new net.minecraft.entity.ai.EntityAILookIdle(this));
/*  61 */     this.targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/*  62 */     this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  63 */     this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, thaumcraft.common.entities.monster.cult.EntityCultist.class, true));
/*  64 */     setSize(1.5F, 3.5F);
/*     */   }
/*     */   
/*     */   public void generateName()
/*     */   {
/*  69 */     int t = (int)getEntityAttribute(thaumcraft.common.lib.utils.EntityUtils.CHAMPION_MOD).getAttributeValue();
/*  70 */     if (t >= 0) {
/*  71 */       setCustomNameTag(String.format(net.minecraft.util.StatCollector.translateToLocal("entity.Thaumcraft.EldritchWarden.name.custom"), new Object[] { getTitle(), ChampionModifier.mods[t].getModNameLocalized() }));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private String getTitle()
/*     */   {
/*  78 */     return this.titles[getDataWatcher().getWatchableObjectByte(16)];
/*     */   }
/*     */   
/*     */   private void setTitle(int title) {
/*  82 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)title));
/*     */   }
/*     */   
/*  85 */   String[] titles = { "Aphoom-Zhah", "Basatan", "Chaugnar Faugn", "Mnomquah", "Nyogtha", "Oorn", "Shaikorth", "Rhan-Tegoth", "Rhogog", "Shudde M'ell", "Vulthoom", "Yag-Kosha", "Yibb-Tstll", "Zathog", "Zushakon" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  93 */     super.applyEntityAttributes();
/*  94 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D);
/*  95 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.33D);
/*  96 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/* 102 */     super.entityInit();
/* 103 */     getDataWatcher().addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/* 109 */     super.writeEntityToNBT(nbt);
/* 110 */     nbt.setByte("title", getDataWatcher().getWatchableObjectByte(16));
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/* 116 */     super.readEntityFromNBT(nbt);
/* 117 */     setTitle(nbt.getByte("title"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getTotalArmorValue()
/*     */   {
/* 126 */     return super.getTotalArmorValue() + 4;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void updateAITasks()
/*     */   {
/* 132 */     if (this.fieldFrenzyCounter == 0) {
/* 133 */       super.updateAITasks();
/*     */     }
/*     */     
/* 136 */     if ((this.hurtResistantTime <= 0) && (this.ticksExisted % 25 == 0)) {
/* 137 */       int bh = (int)(getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() * 0.66D);
/* 138 */       if (getAbsorptionAmount() < bh) {
/* 139 */         setAbsorptionAmount(getAbsorptionAmount() + 1.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 145 */     if (getSpawnTimer() == 150) this.worldObj.setEntityState(this, (byte)18);
/* 146 */     super.onUpdate();
/* 147 */     if (this.worldObj.isRemote) {
/* 148 */       if (this.armLiftL > 0.0F) this.armLiftL -= 0.05F;
/* 149 */       if (this.armLiftR > 0.0F) this.armLiftR -= 0.05F;
/* 150 */       float x = (float)(this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 151 */       float z = (float)(this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 152 */       Thaumcraft.proxy.getFX().wispFXEG(x, (float)(this.posY + 0.25D * this.height), z, this);
/*     */       
/* 154 */       if (this.spawnTimer > 0) {
/* 155 */         float he = Math.max(1.0F, this.height * ((150 - this.spawnTimer) / 150.0F));
/* 156 */         for (int a = 0; a < 33; a++) {
/* 157 */           Thaumcraft.proxy.getFX().smokeSpiral(this.posX, getEntityBoundingBox().minY + he / 2.0F, this.posZ, he, this.rand.nextInt(360), MathHelper.floor_double(getEntityBoundingBox().minY) - 1, 2232623);
/*     */         }
/*     */       }
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
/*     */ 
/*     */   public void onLivingUpdate()
/*     */   {
/* 174 */     super.onLivingUpdate();
/* 175 */     int i = MathHelper.floor_double(this.posX);
/* 176 */     int j = MathHelper.floor_double(this.posY);
/* 177 */     int k = MathHelper.floor_double(this.posZ);
/*     */     
/* 179 */     for (int l = 0; l < 4; l++)
/*     */     {
/* 181 */       i = MathHelper.floor_double(this.posX + (l % 2 * 2 - 1) * 0.25F);
/* 182 */       j = MathHelper.floor_double(this.posY);
/* 183 */       k = MathHelper.floor_double(this.posZ + (l / 2 % 2 * 2 - 1) * 0.25F);
/* 184 */       BlockPos bp = new BlockPos(i, j, k);
/* 185 */       if (this.worldObj.isAirBlock(bp))
/*     */       {
/* 187 */         this.worldObj.setBlockState(bp, BlocksTC.effect.getStateFromMeta(1));
/*     */       }
/*     */     }
/*     */     
/* 191 */     if ((!this.worldObj.isRemote) && (this.fieldFrenzyCounter > 0)) {
/* 192 */       if (this.fieldFrenzyCounter == 150) {
/* 193 */         teleportHome();
/*     */       }
/* 195 */       performFieldFrenzy();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 200 */   boolean fieldFrenzy = false;
/* 201 */   int fieldFrenzyCounter = 0;
/*     */   
/*     */   private void performFieldFrenzy()
/*     */   {
/* 205 */     if ((this.fieldFrenzyCounter < 121) && (this.fieldFrenzyCounter % 10 == 0)) {
/* 206 */       this.worldObj.setEntityState(this, (byte)17);
/* 207 */       double radius = (150 - this.fieldFrenzyCounter) / 8.0D;
/* 208 */       int d = 1 + this.fieldFrenzyCounter / 8;
/* 209 */       int i = MathHelper.floor_double(this.posX);
/* 210 */       int j = MathHelper.floor_double(this.posY);
/* 211 */       int k = MathHelper.floor_double(this.posZ);
/* 212 */       for (int q = 0; q < 180 / d; q++)
/*     */       {
/* 214 */         double radians = Math.toRadians(q * 2 * d);
/* 215 */         int deltaX = (int)(radius * Math.cos(radians));
/* 216 */         int deltaZ = (int)(radius * Math.sin(radians));
/* 217 */         BlockPos bp = new BlockPos(i + deltaX, j, k + deltaZ);
/* 218 */         if ((this.worldObj.isAirBlock(bp)) && (this.worldObj.isBlockNormalCube(bp.down(), false)))
/*     */         {
/* 220 */           this.worldObj.setBlockState(bp, BlocksTC.effect.getStateFromMeta(1));
/* 221 */           this.worldObj.scheduleUpdate(bp, BlocksTC.effect, 250 + this.rand.nextInt(150));
/*     */           
/* 223 */           if (this.rand.nextFloat() < 0.3F) {
/* 224 */             PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockArc(bp, this, 0.5F + this.rand.nextFloat() * 0.2F, 0.0F, 0.5F + this.rand.nextFloat() * 0.2F), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 32.0D));
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 229 */       this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "thaumcraft:zap", 1.0F, 0.9F + this.rand.nextFloat() * 0.1F);
/*     */     }
/*     */     
/* 232 */     this.fieldFrenzyCounter -= 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void teleportHome()
/*     */   {
/* 239 */     EnderTeleportEvent event = new EnderTeleportEvent(this, getHomePosition().getX(), getHomePosition().getY(), getHomePosition().getZ(), 0.0F);
/* 240 */     if (MinecraftForge.EVENT_BUS.post(event)) {
/* 241 */       return;
/*     */     }
/* 243 */     double d3 = this.posX;
/* 244 */     double d4 = this.posY;
/* 245 */     double d5 = this.posZ;
/* 246 */     this.posX = event.targetX;
/* 247 */     this.posY = event.targetY;
/* 248 */     this.posZ = event.targetZ;
/* 249 */     boolean flag = false;
/* 250 */     int i = MathHelper.floor_double(this.posX);
/* 251 */     int j = MathHelper.floor_double(this.posY);
/* 252 */     int k = MathHelper.floor_double(this.posZ);
/* 253 */     BlockPos bp = new BlockPos(i, j, k);
/* 254 */     if (this.worldObj.isBlockLoaded(bp))
/*     */     {
/* 256 */       bp = new BlockPos(i, j, k);
/* 257 */       boolean flag1 = false;
/* 258 */       int tries = 20;
/* 259 */       while ((!flag1) && (tries > 0))
/*     */       {
/* 261 */         Block block = this.worldObj.getBlockState(bp.down()).getBlock();
/* 262 */         Block block2 = this.worldObj.getBlockState(bp).getBlock();
/* 263 */         if ((block.getMaterial().blocksMovement()) && (!block2.getMaterial().blocksMovement()))
/*     */         {
/* 265 */           flag1 = true;
/*     */         }
/*     */         else
/*     */         {
/* 269 */           i = MathHelper.floor_double(this.posX) + this.rand.nextInt(8) - this.rand.nextInt(8);
/* 270 */           k = MathHelper.floor_double(this.posZ) + this.rand.nextInt(8) - this.rand.nextInt(8);
/* 271 */           tries--;
/*     */         }
/*     */       }
/*     */       
/* 275 */       if (flag1)
/*     */       {
/* 277 */         setPosition(i + 0.5D, j + 0.1D, k + 0.5D);
/*     */         
/* 279 */         if (this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty())
/*     */         {
/* 281 */           flag = true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 286 */     if (!flag)
/*     */     {
/* 288 */       setPosition(d3, d4, d5);
/* 289 */       return;
/*     */     }
/*     */     
/*     */ 
/* 293 */     short short1 = 128;
/*     */     
/* 295 */     for (int l = 0; l < short1; l++)
/*     */     {
/* 297 */       double d6 = l / (short1 - 1.0D);
/* 298 */       float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 299 */       float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 300 */       float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 301 */       double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
/* 302 */       double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * this.height;
/* 303 */       double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
/* 304 */       this.worldObj.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, d7, d8, d9, f, f1, f2, new int[0]);
/*     */     }
/*     */     
/* 307 */     this.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
/* 308 */     playSound("mob.endermen.portal", 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEntityInvulnerable(DamageSource ds)
/*     */   {
/* 317 */     return (this.fieldFrenzyCounter > 0) || (super.isEntityInvulnerable(ds));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource source, float damage)
/*     */   {
/* 323 */     if ((isEntityInvulnerable(source)) || (source == DamageSource.drown) || (source == DamageSource.wither))
/*     */     {
/* 325 */       return false;
/*     */     }
/*     */     
/* 328 */     boolean aef = super.attackEntityFrom(source, damage);
/*     */     
/* 330 */     if ((!this.worldObj.isRemote) && (aef) && (!this.fieldFrenzy) && (getAbsorptionAmount() <= 0.0F)) {
/* 331 */       this.fieldFrenzy = true;
/* 332 */       this.fieldFrenzyCounter = 150;
/*     */     }
/*     */     
/* 335 */     return aef;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance diff, IEntityLivingData data)
/*     */   {
/* 342 */     this.spawnTimer = 150;
/* 343 */     setTitle(this.rand.nextInt(this.titles.length));
/* 344 */     setAbsorptionAmount((float)(getAbsorptionAmount() + getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() * 0.66D));
/* 345 */     return super.onInitialSpawn(diff, data);
/*     */   }
/*     */   
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/* 351 */     return 3.1F;
/*     */   }
/*     */   
/* 354 */   boolean lastBlast = false;
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float f)
/*     */   {
/* 358 */     if (this.rand.nextFloat() > 0.2F) {
/* 359 */       EntityEldritchOrb blast = new EntityEldritchOrb(this.worldObj, this);
/* 360 */       this.lastBlast = (!this.lastBlast);
/*     */       
/* 362 */       this.worldObj.setEntityState(this, (byte)(this.lastBlast ? 16 : 15));
/*     */       
/* 364 */       int rr = this.lastBlast ? 90 : 180;
/* 365 */       double xx = MathHelper.cos((this.rotationYaw + rr) % 360.0F / 180.0F * 3.1415927F) * 0.5F;
/* 366 */       double yy = 0.13D;
/* 367 */       double zz = MathHelper.sin((this.rotationYaw + rr) % 360.0F / 180.0F * 3.1415927F) * 0.5F;
/* 368 */       blast.setPosition(blast.posX - xx, blast.posY - yy, blast.posZ - zz);
/*     */       
/* 370 */       double d0 = entitylivingbase.posX + entitylivingbase.motionX - this.posX;
/* 371 */       double d1 = entitylivingbase.posY - this.posY - entitylivingbase.height / 2.0F;
/* 372 */       double d2 = entitylivingbase.posZ + entitylivingbase.motionZ - this.posZ;
/*     */       
/* 374 */       blast.setThrowableHeading(d0, d1, d2, 1.0F, 2.0F);
/*     */       
/* 376 */       playSound("thaumcraft:egattack", 2.0F, 1.0F + this.rand.nextFloat() * 0.1F);
/* 377 */       this.worldObj.spawnEntityInWorld(blast);
/*     */     }
/* 379 */     else if (canEntityBeSeen(entitylivingbase)) {
/* 380 */       PacketHandler.INSTANCE.sendToAllAround(new PacketFXSonic(getEntityId()), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 32.0D));
/*     */       
/*     */ 
/* 383 */       entitylivingbase.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * 1.5F, 0.1D, MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * 1.5F);
/*     */       
/*     */       try
/*     */       {
/* 387 */         entitylivingbase.addPotionEffect(new PotionEffect(Potion.wither.id, 400, 0));
/* 388 */         entitylivingbase.addPotionEffect(new PotionEffect(Potion.weakness.id, 400, 0));
/*     */       }
/*     */       catch (Exception e) {}
/* 391 */       if ((entitylivingbase instanceof EntityPlayer)) {
/* 392 */         ResearchHelper.addWarpToPlayer((EntityPlayer)entitylivingbase, 3 + this.worldObj.rand.nextInt(3), EnumWarpType.TEMPORARY);
/*     */       }
/*     */       
/* 395 */       playSound("thaumcraft:egscreech", 4.0F, 1.0F + this.rand.nextFloat() * 0.1F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 400 */   public float armLiftL = 0.0F;
/* 401 */   public float armLiftR = 0.0F;
/*     */   
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public void handleStatusUpdate(byte p_70103_1_)
/*     */   {
/* 407 */     if (p_70103_1_ == 15)
/*     */     {
/* 409 */       this.armLiftL = 0.5F;
/*     */ 
/*     */     }
/* 412 */     else if (p_70103_1_ == 16)
/*     */     {
/* 414 */       this.armLiftR = 0.5F;
/*     */ 
/*     */     }
/* 417 */     else if (p_70103_1_ == 17)
/*     */     {
/* 419 */       this.armLiftL = 0.9F;
/* 420 */       this.armLiftR = 0.9F;
/*     */ 
/*     */     }
/* 423 */     else if (p_70103_1_ == 18)
/*     */     {
/* 425 */       this.spawnTimer = 150;
/*     */     }
/*     */     else
/*     */     {
/* 429 */       super.handleStatusUpdate(p_70103_1_);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/* 437 */     if (clazz == EntityEldritchGuardian.class)
/* 438 */       return false;
/* 439 */     return super.canAttackClass(clazz);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 448 */     return "thaumcraft:egidle";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 458 */     return "thaumcraft:egdeath";
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTalkInterval()
/*     */   {
/* 464 */     return 500;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\boss\EntityEldritchWarden.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */