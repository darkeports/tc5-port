/*     */ package thaumcraft.common.entities.construct;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.client.fx.other.FXSonic;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.monster.EntityWisp;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.lib.aura.EntityAuraNode;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ public class EntityNodeMagnet extends EntityOwnedConstruct
/*     */ {
/*  35 */   EntityAuraNode nodeTarget = null;
/*     */   
/*     */   public EntityNodeMagnet(World worldIn) {
/*  38 */     super(worldIn);
/*  39 */     setSize(0.9F, 0.9F);
/*     */   }
/*     */   
/*     */   public EntityNodeMagnet(World worldIn, BlockPos pos) {
/*  43 */     this(worldIn);
/*  44 */     setPositionAndRotation(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  50 */     super.entityInit();
/*  51 */     this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/*  57 */     return isOnTeam(otherEntity.getTeam());
/*     */   }
/*     */   
/*     */ 
/*     */   public Team getTeam()
/*     */   {
/*  63 */     if (isOwned())
/*     */     {
/*  65 */       EntityLivingBase entitylivingbase = getOwnerEntity();
/*     */       
/*  67 */       if (entitylivingbase != null)
/*     */       {
/*  69 */         return entitylivingbase.getTeam();
/*     */       }
/*     */     }
/*     */     
/*  73 */     return super.getTeam();
/*     */   }
/*     */   
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/*  79 */     return 0.8125F;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  85 */     super.applyEntityAttributes();
/*  86 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
/*  87 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
/*     */   }
/*     */   
/*     */   public void onUpdate()
/*     */   {
/*  92 */     super.onUpdate();
/*     */     
/*  94 */     if (!this.worldObj.isRemote) {
/*  95 */       this.rotationYaw = this.rotationYawHead;
/*     */       
/*  97 */       if (this.ticksExisted % 50 == 0) {
/*  98 */         heal(1.0F);
/*     */         
/* 100 */         ArrayList<Entity> list = EntityUtils.getEntitiesInRange(this.worldObj, getPosition(), this, EntityMob.class, 32.0D);
/* 101 */         if (list != null) {
/* 102 */           for (Entity e : list) {
/* 103 */             EntityMob mob = (EntityMob)e;
/* 104 */             if ((this.rand.nextBoolean()) && 
/* 105 */               (mob.getAttackTarget() == null)) {
/* 106 */               mob.setAttackTarget(this);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 112 */         if ((getOwnerEntity() != null) && ((getOwnerEntity() instanceof EntityPlayer)) && (!ResearchHelper.isResearchComplete(getOwnerEntity().getName(), "!NODEMAGNETDANGER")))
/*     */         {
/* 114 */           ResearchHelper.completeResearch((EntityPlayer)getOwnerEntity(), "!NODEMAGNETDANGER");
/*     */         }
/*     */       }
/*     */       
/* 118 */       if ((this.ticksExisted % 10 == 0) && (getCharge() < 10)) {
/* 119 */         rechargeVis();
/*     */       }
/*     */     }
/*     */     
/* 123 */     if ((this.nodeTarget != null) && (this.nodeTarget.isDead)) {
/* 124 */       this.nodeTarget = null;
/*     */     }
/*     */     
/* 127 */     if ((this.nodeTarget != null) && (getCharge() > 0)) {
/* 128 */       if (!this.worldObj.isRemote) {
/* 129 */         getLookHelper().setLookPosition(this.nodeTarget.posX, this.nodeTarget.posY + this.nodeTarget.getEyeHeight(), this.nodeTarget.posZ, 10.0F, getVerticalFaceSpeed());
/*     */ 
/*     */ 
/*     */       }
/* 133 */       else if (this.ticksExisted % 10 == 0) {
/* 134 */         showFX();
/*     */       }
/*     */       
/* 137 */       double gap = getDistanceToEntity(this.nodeTarget);
/* 138 */       if ((gap >= 1.0D) && (gap <= 32.0D) && (!this.nodeTarget.stablized)) {
/* 139 */         double mx = this.posX - this.nodeTarget.posX;
/* 140 */         double my = this.posY - this.nodeTarget.posY;
/* 141 */         double mz = this.posZ - this.nodeTarget.posZ;
/* 142 */         mx /= gap * (200 + this.nodeTarget.getNodeSize() * 3);
/* 143 */         my /= gap * (200 + this.nodeTarget.getNodeSize() * 3);
/* 144 */         mz /= gap * (200 + this.nodeTarget.getNodeSize() * 3);
/* 145 */         this.nodeTarget.motionX += mx;
/* 146 */         this.nodeTarget.motionY += my;
/* 147 */         this.nodeTarget.motionZ += mz;
/* 148 */         if (!this.worldObj.isRemote) {
/* 149 */           setCharge((byte)(getCharge() - 1));
/* 150 */           if (this.rand.nextFloat() < 0.01F)
/* 151 */             if (this.rand.nextFloat() < 0.2F) {
/* 152 */               EntityWisp wisp = new EntityWisp(this.worldObj);
/* 153 */               wisp.setLocationAndAngles(this.nodeTarget.posX, this.nodeTarget.posY, this.nodeTarget.posZ, 0.0F, 0.0F);
/* 154 */               wisp.setType(this.nodeTarget.getAspectTag());
/* 155 */               this.worldObj.spawnEntityInWorld(wisp);
/*     */             } else {
/* 157 */               AuraHelper.pollute(this.worldObj, this.nodeTarget.getPosition(), 1, true);
/*     */             }
/*     */         }
/* 160 */         this.rs += 0.36F;
/*     */       } else {
/* 162 */         this.nodeTarget = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 168 */     if (this.nodeTarget == null) {
/* 169 */       this.rs -= 0.5F;
/*     */     }
/*     */     
/* 172 */     if ((this.nodeTarget == null) && (this.ticksExisted % 20 == 0)) {
/* 173 */       ArrayList<Entity> list = EntityUtils.getEntitiesInRange(this.worldObj, this.posX, this.posY, this.posZ, this, EntityAuraNode.class, 32.0D);
/* 174 */       Entity closest = null;
/* 175 */       double d = Double.MAX_VALUE;
/* 176 */       for (Entity e : list)
/* 177 */         if (!((EntityAuraNode)e).stablized) {
/* 178 */           double gap = getDistanceSqToEntity(e);
/* 179 */           if (gap < d) {
/* 180 */             d = gap;
/* 181 */             closest = e;
/*     */           }
/*     */         }
/* 184 */       if (closest != null) {
/* 185 */         this.nodeTarget = ((EntityAuraNode)closest);
/*     */       }
/*     */     }
/*     */     
/* 189 */     if (this.rs > 36.0F) this.rs = 36.0F;
/* 190 */     if (this.rs < 0.0F) { this.rs = 0.0F;
/*     */     }
/* 192 */     this.rot += this.rs;
/* 193 */     if (this.rot > 360.0F) {
/* 194 */       this.rot -= 360.0F;
/* 195 */       if (this.worldObj.isRemote) this.worldObj.playSound(this.posX, this.posY, this.posZ, "thaumcraft:pump", 0.7F, 1.0F, false);
/*     */     }
/*     */   }
/*     */   
/* 199 */   float rs = 0.0F;
/* 200 */   public float rot = 0.0F;
/*     */   
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   private void showFX() {
/* 204 */     FXSonic fb = new FXSonic(Thaumcraft.proxy.getClientWorld(), this.nodeTarget.posX, this.nodeTarget.posY, this.nodeTarget.posZ, this, 10);
/* 205 */     FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/*     */   }
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount)
/*     */   {
/* 210 */     this.rotationYaw = ((float)(this.rotationYaw + getRNG().nextGaussian() * 45.0D));
/* 211 */     this.rotationPitch = ((float)(this.rotationPitch + getRNG().nextGaussian() * 20.0D));
/* 212 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */   
/*     */   protected void rechargeVis() {
/* 216 */     for (Aspect aspect : ) {
/* 217 */       if (AuraHandler.drainAura(this.worldObj, getPosition(), aspect, 1)) {
/* 218 */         setCharge((byte)(getCharge() + 3));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBePushed()
/*     */   {
/* 226 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 232 */     return true;
/*     */   }
/*     */   
/*     */   public boolean interact(EntityPlayer player)
/*     */   {
/* 237 */     if ((player.isSneaking()) || ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof net.minecraft.item.ItemNameTag)))) return false;
/* 238 */     if ((!this.worldObj.isRemote) && (isOwner(player)) && (!this.isDead)) {
/* 239 */       if ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof IWand))) {
/* 240 */         playSound("thaumcraft:zap", 1.0F, 1.0F);
/* 241 */         entityDropItem(new ItemStack(ItemsTC.turretPlacer, 1, 2), 0.5F);
/* 242 */         setDead();
/* 243 */         player.swingItem();
/*     */       }
/* 245 */       return true;
/*     */     }
/*     */     
/* 248 */     return super.interact(player);
/*     */   }
/*     */   
/*     */   public void knockBack(Entity p_70653_1_, float p_70653_2_, double p_70653_3_, double p_70653_5_)
/*     */   {
/* 253 */     super.knockBack(p_70653_1_, p_70653_2_, p_70653_3_ / 10.0D, p_70653_5_ / 10.0D);
/* 254 */     if (this.motionY > 0.1D) { this.motionY = 0.1D;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/* 262 */     super.readEntityFromNBT(nbt);
/* 263 */     this.dataWatcher.updateObject(19, Byte.valueOf(nbt.getByte("charge")));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/* 270 */     super.writeEntityToNBT(nbt);
/* 271 */     nbt.setByte("charge", this.dataWatcher.getWatchableObjectByte(19));
/*     */   }
/*     */   
/*     */   public byte getCharge()
/*     */   {
/* 276 */     return this.dataWatcher.getWatchableObjectByte(19);
/*     */   }
/*     */   
/*     */   public void setCharge(byte c) {
/* 280 */     this.dataWatcher.updateObject(19, Byte.valueOf(c));
/*     */   }
/*     */   
/*     */   public void moveEntity(double x, double y, double z)
/*     */   {
/* 285 */     super.moveEntity(x / 5.0D, y, z / 5.0D);
/* 286 */     float c = (float)((Math.abs(this.motionX) + Math.abs(this.motionY) + Math.abs(this.motionZ)) / 3.0D);
/* 287 */     if (c > 0.25D) { kill();
/* 288 */     } else if (this.rand.nextFloat() < c / 20.0F) { kill();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void kill()
/*     */   {
/* 294 */     attackEntityFrom(DamageSource.outOfWorld, 40.0F);
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
/*     */   {
/* 299 */     float b = p_70628_2_ * 0.15F;
/* 300 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(ItemsTC.mind, 1, 1), 0.5F);
/* 301 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(ItemsTC.morphicResonator), 0.5F);
/* 302 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(BlocksTC.crystalAir), 0.5F);
/* 303 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(BlocksTC.crystalFire), 0.5F);
/* 304 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(BlocksTC.crystalWater), 0.5F);
/* 305 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(BlocksTC.crystalEarth), 0.5F);
/* 306 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(BlocksTC.crystalOrder), 0.5F);
/* 307 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(BlocksTC.crystalEntropy), 0.5F);
/* 308 */     if (this.rand.nextFloat() < 0.2F + b) entityDropItem(new ItemStack(BlocksTC.crystalTaint), 0.5F);
/* 309 */     if (this.rand.nextFloat() < 0.5F + b) entityDropItem(new ItemStack(ItemsTC.gear), 0.5F);
/* 310 */     if (this.rand.nextFloat() < 0.5F + b) entityDropItem(new ItemStack(ItemsTC.plate), 0.5F);
/* 311 */     if (this.rand.nextFloat() < 0.5F + b) { entityDropItem(new ItemStack(BlocksTC.plank), 0.5F);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getVerticalFaceSpeed()
/*     */   {
/* 317 */     return 20;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\EntityNodeMagnet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */