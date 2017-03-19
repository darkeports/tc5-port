/*     */ package thaumcraft.common.entities.monster.tainted;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.entities.ITaintedMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ public class EntityTaintSwarm extends EntityMob implements ITaintedMob
/*     */ {
/*     */   private BlockPos currentFlightTarget;
/*     */   
/*     */   public EntityTaintSwarm(World par1World)
/*     */   {
/*  37 */     super(par1World);
/*  38 */     setSize(2.0F, 2.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/*  44 */     return !ITaintedMob.class.isAssignableFrom(clazz);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/*  50 */     return ((otherEntity instanceof ITaintedMob)) || (isOnTeam(otherEntity.getTeam()));
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  56 */     super.entityInit();
/*  57 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public int getBrightnessForRender(float par1)
/*     */   {
/*  64 */     return 15728880;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canDespawn()
/*     */   {
/*  70 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getBrightness(float par1)
/*     */   {
/*  79 */     return 1.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected float getSoundVolume()
/*     */   {
/*  88 */     return 0.1F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/*  98 */     return "thaumcraft:swarm";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 107 */     return "thaumcraft:swarmattack";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 116 */     return "thaumcraft:swarmattack";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canBePushed()
/*     */   {
/* 125 */     return false;
/*     */   }
/*     */   
/* 128 */   public int damBonus = 0;
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/* 133 */     super.applyEntityAttributes();
/* 134 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
/* 135 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2 + this.damBonus);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getIsSummoned()
/*     */   {
/* 141 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x2) != 0;
/*     */   }
/*     */   
/*     */   public void setIsSummoned(boolean par1)
/*     */   {
/* 146 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 148 */     if (par1)
/*     */     {
/* 150 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x2)));
/*     */     }
/*     */     else
/*     */     {
/* 154 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFC)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 161 */   public ArrayList swarm = new ArrayList();
/*     */   private int attackTime;
/*     */   
/*     */   public void onUpdate()
/*     */   {
/* 166 */     super.onUpdate();
/*     */     
/*     */ 
/* 169 */     this.motionY *= 0.6000000238418579D;
/*     */     
/* 171 */     if (this.worldObj.isRemote) {
/* 172 */       for (int a = 0; a < this.swarm.size(); a++) {
/* 173 */         if ((this.swarm.get(a) == null) || (((Entity)this.swarm.get(a)).isDead)) {
/* 174 */           this.swarm.remove(a);
/* 175 */           break;
/*     */         }
/*     */       }
/*     */       
/* 179 */       if (this.swarm.size() < Math.max(Thaumcraft.proxy.getFX().particleCount(25), 10)) {
/* 180 */         this.swarm.add(Thaumcraft.proxy.getFX().swarmParticleFX(this, 0.22F, 15.0F, 0.08F));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void onLivingUpdate()
/*     */   {
/* 187 */     super.onLivingUpdate();
/*     */     
/* 189 */     if (getAttackTarget() == null)
/*     */     {
/* 191 */       if (getIsSummoned()) {
/* 192 */         attackEntityFrom(DamageSource.generic, 5.0F);
/*     */       }
/* 194 */       if ((this.currentFlightTarget != null) && ((!this.worldObj.isAirBlock(this.currentFlightTarget)) || (this.currentFlightTarget.getY() < 1) || (this.currentFlightTarget.getY() > this.worldObj.getPrecipitationHeight(this.currentFlightTarget).up(2).getY()) || (this.worldObj.getBiomeGenForCoords(this.currentFlightTarget).biomeID != Config.biomeTaintID)))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 199 */         this.currentFlightTarget = null;
/*     */       }
/*     */       
/* 202 */       if ((this.currentFlightTarget == null) || (this.rand.nextInt(30) == 0) || (getDistanceSqToCenter(this.currentFlightTarget) < 4.0D))
/*     */       {
/*     */ 
/* 205 */         this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 211 */       double var1 = this.currentFlightTarget.getX() + 0.5D - this.posX;
/* 212 */       double var3 = this.currentFlightTarget.getY() + 0.1D - this.posY;
/* 213 */       double var5 = this.currentFlightTarget.getZ() + 0.5D - this.posZ;
/* 214 */       this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.015000000014901161D;
/* 215 */       this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
/* 216 */       this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.015000000014901161D;
/* 217 */       float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
/* 218 */       float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
/* 219 */       this.moveForward = 0.1F;
/* 220 */       this.rotationYaw += var8;
/*     */     }
/* 222 */     else if (getAttackTarget() != null) {
/* 223 */       double var1 = getAttackTarget().posX - this.posX;
/* 224 */       double var3 = getAttackTarget().posY + getAttackTarget().getEyeHeight() - this.posY;
/* 225 */       double var5 = getAttackTarget().posZ - this.posZ;
/* 226 */       this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.025000000149011613D;
/* 227 */       this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
/* 228 */       this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.02500000001490116D;
/* 229 */       float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
/* 230 */       float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
/* 231 */       this.moveForward = 0.1F;
/* 232 */       this.rotationYaw += var8;
/*     */     }
/*     */     
/* 235 */     if (getAttackTarget() == null)
/*     */     {
/* 237 */       setAttackTarget((EntityLivingBase)findPlayerToAttack());
/*     */     }
/* 239 */     else if (getAttackTarget().isEntityAlive())
/*     */     {
/* 241 */       float f = getAttackTarget().getDistanceToEntity(this);
/*     */       
/* 243 */       if (canEntityBeSeen(getAttackTarget()))
/*     */       {
/* 245 */         attackEntity(getAttackTarget(), f);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 250 */       setAttackTarget(null);
/*     */     }
/*     */     
/* 253 */     if (((getAttackTarget() instanceof EntityPlayer)) && (((EntityPlayer)getAttackTarget()).capabilities.disableDamage))
/*     */     {
/* 255 */       setAttackTarget(null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void updateAITasks()
/*     */   {
/* 265 */     super.updateAITasks();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/* 275 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean doesEntityNotTriggerPressurePlate()
/*     */   {
/* 288 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*     */   {
/* 297 */     if (isEntityInvulnerable(par1DamageSource))
/*     */     {
/* 299 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 303 */     return super.attackEntityFrom(par1DamageSource, par2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void attackEntity(Entity par1Entity, float par2)
/*     */   {
/* 311 */     if ((this.attackTime <= 0) && (par2 < 3.0F) && (par1Entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY) && (par1Entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY))
/*     */     {
/* 313 */       if (getIsSummoned()) {
/* 314 */         EntityUtils.setRecentlyHit((EntityLivingBase)par1Entity, 100);
/*     */       }
/*     */       
/* 317 */       this.attackTime = (10 + this.rand.nextInt(5));
/*     */       
/* 319 */       double mx = par1Entity.motionX;
/* 320 */       double my = par1Entity.motionY;
/* 321 */       double mz = par1Entity.motionZ;
/* 322 */       if ((attackEntityAsMob(par1Entity)) && 
/* 323 */         (!this.worldObj.isRemote) && ((par1Entity instanceof EntityLivingBase))) {
/* 324 */         ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.weakness.id, 100, 0));
/*     */       }
/*     */       
/* 327 */       par1Entity.isAirBorne = false;
/* 328 */       par1Entity.motionX = mx;
/* 329 */       par1Entity.motionY = my;
/* 330 */       par1Entity.motionZ = mz;
/*     */       
/* 332 */       this.worldObj.playSoundAtEntity(this, "thaumcraft:swarmattack", 0.3F, 0.9F + this.worldObj.rand.nextFloat() * 0.2F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected Entity findPlayerToAttack()
/*     */   {
/* 340 */     double var1 = 8.0D;
/* 341 */     return getIsSummoned() ? null : this.worldObj.getClosestPlayerToEntity(this, var1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 350 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 351 */     this.dataWatcher.updateObject(16, Byte.valueOf(par1NBTTagCompound.getByte("Flags")));
/* 352 */     this.damBonus = par1NBTTagCompound.getByte("damBonus");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 361 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 362 */     par1NBTTagCompound.setByte("Flags", this.dataWatcher.getWatchableObjectByte(16));
/* 363 */     par1NBTTagCompound.setByte("damBonus", (byte)this.damBonus);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getCanSpawnHere()
/*     */   {
/* 372 */     int var1 = MathHelper.floor_double(getEntityBoundingBox().minY);
/*     */     
/* 374 */     int var2 = MathHelper.floor_double(this.posX);
/* 375 */     int var3 = MathHelper.floor_double(this.posZ);
/* 376 */     int var4 = this.worldObj.getLight(new BlockPos(var2, var1, var3));
/* 377 */     byte var5 = 7;
/*     */     
/* 379 */     return var4 > this.rand.nextInt(var5) ? false : super.getCanSpawnHere();
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean isValidLightLevel()
/*     */   {
/* 385 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 391 */     return ItemsTC.tainted;
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 396 */     if (this.worldObj.rand.nextBoolean()) {
/* 397 */       entityDropItem(new ItemStack(ItemsTC.tainted), this.height / 2.0F);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\tainted\EntityTaintSwarm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */