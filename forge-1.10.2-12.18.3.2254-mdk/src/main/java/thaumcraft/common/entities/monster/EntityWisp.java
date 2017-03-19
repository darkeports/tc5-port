/*     */ package thaumcraft.common.entities.monster;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.EntityFlying;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.IMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.items.ItemGenericEssentiaContainer;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXWispZap;
/*     */ 
/*     */ public class EntityWisp extends EntityFlying implements IMob
/*     */ {
/*     */   public int courseChangeCooldown;
/*     */   public double waypointX;
/*     */   public double waypointY;
/*     */   public double waypointZ;
/*     */   private int aggroCooldown;
/*     */   public int prevAttackCounter;
/*     */   public int attackCounter;
/*     */   private BlockPos currentFlightTarget;
/*     */   
/*     */   public EntityWisp(World world)
/*     */   {
/*  47 */     super(world);
/*  48 */     this.courseChangeCooldown = 0;
/*  49 */     this.aggroCooldown = 0;
/*  50 */     this.prevAttackCounter = 0;
/*  51 */     this.attackCounter = 0;
/*  52 */     setSize(0.9F, 0.9F);
/*  53 */     this.experienceValue = 5;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  59 */     super.applyEntityAttributes();
/*  60 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(22.0D);
/*  61 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
/*  62 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
/*     */   }
/*     */   
/*     */   protected boolean canTriggerWalking()
/*     */   {
/*  67 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int decreaseAirSupply(int par1)
/*     */   {
/*  73 */     return par1;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource damagesource, float i)
/*     */   {
/*  79 */     if ((damagesource.getSourceOfDamage() instanceof EntityLivingBase)) {
/*  80 */       setAttackTarget((EntityLivingBase)damagesource.getSourceOfDamage());
/*  81 */       this.aggroCooldown = 200;
/*     */     }
/*  83 */     if ((damagesource.getEntity() instanceof EntityLivingBase)) {
/*  84 */       setAttackTarget((EntityLivingBase)damagesource.getEntity());
/*  85 */       this.aggroCooldown = 200;
/*     */     }
/*  87 */     return super.attackEntityFrom(damagesource, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  94 */     super.entityInit();
/*  95 */     this.dataWatcher.addObject(22, String.valueOf(""));
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDeath(DamageSource par1DamageSource)
/*     */   {
/* 101 */     super.onDeath(par1DamageSource);
/* 102 */     if (this.worldObj.isRemote) {
/* 103 */       Thaumcraft.proxy.getFX().burst(this.posX, this.posY + 0.44999998807907104D, this.posZ, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 110 */     super.onUpdate();
/* 111 */     if ((this.worldObj.isRemote) && 
/* 112 */       (this.ticksExisted <= 1)) {
/* 113 */       Thaumcraft.proxy.getFX().burst(this.posX, this.posY + 0.44999998807907104D, this.posZ, 1.0F);
/*     */     }
/*     */     
/* 116 */     if ((this.worldObj.isRemote) && (this.worldObj.rand.nextBoolean()) && (Aspect.getAspect(getType()) != null)) {
/* 117 */       Color color = new Color(Aspect.getAspect(getType()).getColor());
/* 118 */       Thaumcraft.proxy.getFX().wispFX(this.posX + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.7F, this.posY + 0.44999998807907104D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.7F, this.posZ + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.7F, 0.1F, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 126 */     this.motionY *= 0.6000000238418579D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getType()
/*     */   {
/* 133 */     return this.dataWatcher.getWatchableObjectString(22);
/*     */   }
/*     */   
/*     */   public void setType(String t) {
/* 137 */     this.dataWatcher.updateObject(22, String.valueOf(t));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onLivingUpdate()
/*     */   {
/* 144 */     super.onLivingUpdate();
/*     */     
/* 146 */     if (isServerWorld())
/*     */     {
/* 148 */       if ((!this.worldObj.isRemote) && (Aspect.getAspect(getType()) == null)) {
/* 149 */         if (this.worldObj.rand.nextInt(10) != 0) {
/* 150 */           ArrayList<Aspect> as = Aspect.getPrimalAspects();
/* 151 */           setType(((Aspect)as.get(this.worldObj.rand.nextInt(as.size()))).getTag());
/*     */         } else {
/* 153 */           ArrayList<Aspect> as = Aspect.getCompoundAspects();
/* 154 */           setType(((Aspect)as.get(this.worldObj.rand.nextInt(as.size()))).getTag());
/*     */         }
/*     */       }
/*     */       
/* 158 */       if ((!this.worldObj.isRemote) && (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL))
/*     */       {
/* 160 */         setDead();
/*     */       }
/*     */       
/* 163 */       this.prevAttackCounter = this.attackCounter;
/*     */       
/* 165 */       double attackrange = 16.0D;
/*     */       
/*     */ 
/* 168 */       if ((getAttackTarget() == null) || (!canEntityBeSeen(getAttackTarget())))
/*     */       {
/* 170 */         if ((this.currentFlightTarget != null) && ((!this.worldObj.isAirBlock(this.currentFlightTarget)) || (this.currentFlightTarget.getY() < 1) || (this.currentFlightTarget.getY() > this.worldObj.getPrecipitationHeight(this.currentFlightTarget).up(8).getY())))
/*     */         {
/*     */ 
/*     */ 
/* 174 */           this.currentFlightTarget = null;
/*     */         }
/*     */         
/* 177 */         if ((this.currentFlightTarget == null) || (this.rand.nextInt(30) == 0) || (getDistanceSqToCenter(this.currentFlightTarget) < 4.0D))
/*     */         {
/*     */ 
/* 180 */           this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 186 */         double var1 = this.currentFlightTarget.getX() + 0.5D - this.posX;
/* 187 */         double var3 = this.currentFlightTarget.getY() + 0.1D - this.posY;
/* 188 */         double var5 = this.currentFlightTarget.getZ() + 0.5D - this.posZ;
/* 189 */         this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.10000000149011612D;
/* 190 */         this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
/* 191 */         this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.10000000149011612D;
/* 192 */         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
/* 193 */         float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
/* 194 */         this.moveForward = 0.15F;
/* 195 */         this.rotationYaw += var8;
/*     */       }
/* 197 */       else if ((getDistanceSqToEntity(getAttackTarget()) > attackrange * attackrange / 2.0D) && (canEntityBeSeen(getAttackTarget())))
/*     */       {
/*     */ 
/* 200 */         double var1 = getAttackTarget().posX - this.posX;
/* 201 */         double var3 = getAttackTarget().posY + getAttackTarget().getEyeHeight() * 0.66F - this.posY;
/* 202 */         double var5 = getAttackTarget().posZ - this.posZ;
/* 203 */         this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.10000000149011612D;
/* 204 */         this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
/* 205 */         this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.10000000149011612D;
/* 206 */         float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
/* 207 */         float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
/* 208 */         this.moveForward = 0.5F;
/* 209 */         this.rotationYaw += var8;
/*     */       }
/*     */       
/* 212 */       if (((getAttackTarget() instanceof EntityPlayer)) && (((EntityPlayer)getAttackTarget()).capabilities.disableDamage))
/*     */       {
/* 214 */         setAttackTarget(null);
/*     */       }
/*     */       
/* 217 */       if ((getAttackTarget() != null) && (getAttackTarget().isDead))
/*     */       {
/* 219 */         setAttackTarget(null);
/*     */       }
/*     */       
/* 222 */       this.aggroCooldown -= 1;
/*     */       
/*     */ 
/* 225 */       if ((this.worldObj.rand.nextInt(1000) == 0) && ((getAttackTarget() == null) || (this.aggroCooldown-- <= 0)))
/*     */       {
/* 227 */         setAttackTarget(this.worldObj.getClosestPlayerToEntity(this, 16.0D));
/* 228 */         if (getAttackTarget() != null)
/*     */         {
/* 230 */           this.aggroCooldown = 50;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 235 */       if ((!this.isDead) && (getAttackTarget() != null) && (getAttackTarget().getDistanceSqToEntity(this) < attackrange * attackrange))
/*     */       {
/* 237 */         double d5 = getAttackTarget().posX - this.posX;
/* 238 */         double d6 = getAttackTarget().getEntityBoundingBox().minY + getAttackTarget().height / 2.0F - (this.posY + this.height / 2.0F);
/* 239 */         double d7 = getAttackTarget().posZ - this.posZ;
/* 240 */         this.renderYawOffset = (this.rotationYaw = -(float)Math.atan2(d5, d7) * 180.0F / 3.141593F);
/* 241 */         if (canEntityBeSeen(getAttackTarget()))
/*     */         {
/* 243 */           this.attackCounter += 1;
/* 244 */           if (this.attackCounter == 20)
/*     */           {
/* 246 */             this.worldObj.playSoundAtEntity(this, "thaumcraft:zap", 1.0F, 1.1F);
/*     */             
/* 248 */             thaumcraft.common.lib.network.PacketHandler.INSTANCE.sendToAllAround(new PacketFXWispZap(getEntityId(), getAttackTarget().getEntityId()), new net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 32.0D));
/*     */             
/*     */ 
/*     */ 
/* 252 */             float damage = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
/*     */             
/* 254 */             if ((Math.abs(getAttackTarget().motionX) > 0.10000000149011612D) || (Math.abs(getAttackTarget().motionY) > 0.10000000149011612D) || (Math.abs(getAttackTarget().motionZ) > 0.10000000149011612D))
/*     */             {
/*     */ 
/* 257 */               if (this.worldObj.rand.nextFloat() < 0.4F) {
/* 258 */                 getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), damage);
/*     */               }
/*     */             }
/* 261 */             else if (this.worldObj.rand.nextFloat() < 0.66F) {
/* 262 */               getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), damage + 1.0F);
/*     */             }
/* 264 */             this.attackCounter = (-20 + this.worldObj.rand.nextInt(20));
/*     */           }
/*     */         }
/* 267 */         else if (this.attackCounter > 0)
/*     */         {
/* 269 */           this.attackCounter -= 1;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 280 */     return "thaumcraft:wisplive";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 286 */     return "random.fizz";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 292 */     return "thaumcraft:wispdead";
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 298 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 304 */     if (Aspect.getAspect(getType()) != null) {
/* 305 */       ItemStack ess = new ItemStack(ItemsTC.wispyEssence);
/* 306 */       AspectList al = new AspectList();
/* 307 */       ((ItemGenericEssentiaContainer)ess.getItem()).setAspects(ess, new AspectList().add(Aspect.getAspect(getType()), 2));
/*     */       
/* 309 */       entityDropItem(ess, 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected float getSoundVolume()
/*     */   {
/* 317 */     return 0.25F;
/*     */   }
/*     */   
/*     */   protected boolean canDespawn()
/*     */   {
/* 322 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getCanSpawnHere()
/*     */   {
/* 328 */     int count = 0;
/*     */     try {
/* 330 */       List l = this.worldObj.getEntitiesWithinAABB(EntityWisp.class, getEntityBoundingBox().expand(16.0D, 16.0D, 16.0D));
/*     */       
/* 332 */       if (l != null) count = l.size();
/*     */     }
/*     */     catch (Exception e) {}
/* 335 */     return (count < 8) && (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) && (isValidLightLevel()) && (super.getCanSpawnHere());
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean isValidLightLevel()
/*     */   {
/* 341 */     BlockPos blockpos = new BlockPos(this.posX, getEntityBoundingBox().minY, this.posZ);
/*     */     
/*     */ 
/* 344 */     if (this.worldObj.getLightFor(net.minecraft.world.EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32))
/*     */     {
/* 346 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 350 */     int i = this.worldObj.getLightFromNeighbors(blockpos);
/*     */     
/* 352 */     if (this.worldObj.isThundering())
/*     */     {
/* 354 */       int j = this.worldObj.getSkylightSubtracted();
/* 355 */       this.worldObj.setSkylightSubtracted(10);
/* 356 */       i = this.worldObj.getLightFromNeighbors(blockpos);
/* 357 */       this.worldObj.setSkylightSubtracted(j);
/*     */     }
/*     */     
/* 360 */     return i <= this.rand.nextInt(8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 367 */     super.writeEntityToNBT(nbttagcompound);
/* 368 */     nbttagcompound.setString("Type", getType());
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 374 */     super.readEntityFromNBT(nbttagcompound);
/* 375 */     setType(nbttagcompound.getString("Type"));
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMaxSpawnedInChunk()
/*     */   {
/* 381 */     return 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\EntityWisp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */