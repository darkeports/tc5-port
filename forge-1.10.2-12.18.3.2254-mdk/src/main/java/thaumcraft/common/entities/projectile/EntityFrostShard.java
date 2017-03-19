/*     */ package thaumcraft.common.entities.projectile;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.Block.SoundType;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class EntityFrostShard extends EntityThrowable implements net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
/*     */ {
/*  25 */   public double bounce = 0.5D;
/*     */   
/*  27 */   public int bounceLimit = 3;
/*     */   
/*     */   public EntityFrostShard(World par1World) {
/*  30 */     super(par1World);
/*     */   }
/*     */   
/*     */   public EntityFrostShard(World par1World, EntityLivingBase par2EntityLiving, float scatter) {
/*  34 */     super(par1World, par2EntityLiving);
/*  35 */     setThrowableHeading(this.motionX, this.motionY, this.motionZ, getVelocity(), scatter);
/*     */   }
/*     */   
/*     */   protected float getGravityVelocity()
/*     */   {
/*  40 */     return this.fragile ? 0.015F : 0.05F;
/*     */   }
/*     */   
/*     */   public void writeSpawnData(ByteBuf data)
/*     */   {
/*  45 */     data.writeDouble(this.bounce);
/*  46 */     data.writeInt(this.bounceLimit);
/*  47 */     data.writeBoolean(this.fragile);
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf data)
/*     */   {
/*  52 */     this.bounce = data.readDouble();
/*  53 */     this.bounceLimit = data.readInt();
/*  54 */     this.fragile = data.readBoolean();
/*     */   }
/*     */   
/*     */ 
/*     */   protected void onImpact(MovingObjectPosition mop)
/*     */   {
/*  60 */     if (mop.entityHit != null)
/*     */     {
/*  62 */       int ox = MathHelper.floor_double(this.posX) - MathHelper.floor_double(mop.entityHit.posX);
/*  63 */       int oy = MathHelper.floor_double(this.posY) - MathHelper.floor_double(mop.entityHit.posY);
/*  64 */       int oz = MathHelper.floor_double(this.posZ) - MathHelper.floor_double(mop.entityHit.posZ);
/*  65 */       if (oz != 0) this.motionZ *= -1.0D;
/*  66 */       if (ox != 0) this.motionX *= -1.0D;
/*  67 */       if (oy != 0) this.motionY *= -0.9D;
/*  68 */       this.motionX *= 0.15D;
/*  69 */       this.motionY *= 0.15D;
/*  70 */       this.motionZ *= 0.15D;
/*  71 */       for (int a = 0; a < getDamage(); a++) {
/*  72 */         this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX, this.posY, this.posZ, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(Blocks.ice.getDefaultState()) });
/*     */       }
/*     */       
/*     */     }
/*  76 */     else if (mop.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK) {
/*  77 */       if (mop.sideHit.getFrontOffsetZ() != 0) this.motionZ *= -1.0D;
/*  78 */       if (mop.sideHit.getFrontOffsetX() != 0) this.motionX *= -1.0D;
/*  79 */       if (mop.sideHit.getFrontOffsetY() != 0) this.motionY *= -0.9D;
/*  80 */       IBlockState bhit = this.worldObj.getBlockState(mop.getBlockPos());
/*  81 */       try { playSound(bhit.getBlock().stepSound.getBreakSound(), 0.3F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/*     */       } catch (Exception e) {}
/*  83 */       for (int a = 0; a < getDamage(); a++) {
/*  84 */         this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX, this.posY, this.posZ, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(bhit) });
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  90 */     this.motionX *= this.bounce;
/*  91 */     this.motionY *= this.bounce;
/*  92 */     this.motionZ *= this.bounce;
/*  93 */     float var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/*  94 */     this.posX -= this.motionX / var20 * 0.05000000074505806D;
/*  95 */     this.posY -= this.motionY / var20 * 0.05000000074505806D;
/*  96 */     this.posZ -= this.motionZ / var20 * 0.05000000074505806D;
/*  97 */     setBeenAttacked();
/*     */     
/*  99 */     if ((!this.worldObj.isRemote) && (mop.entityHit != null)) {
/* 100 */       double mx = mop.entityHit.motionX;
/* 101 */       double my = mop.entityHit.motionY;
/* 102 */       double mz = mop.entityHit.motionZ;
/*     */       
/* 104 */       if ((this.fragile) && (mop.entityHit.hurtResistantTime > 0) && (this.rand.nextInt(10) < this.ticksExisted + 3)) {
/* 105 */         mop.entityHit.hurtResistantTime = 0;
/*     */       }
/* 107 */       mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), getDamage());
/*     */       
/* 109 */       if (((mop.entityHit instanceof EntityLivingBase)) && (getFrosty() > 0)) {
/* 110 */         ((EntityLivingBase)mop.entityHit).addPotionEffect(new net.minecraft.potion.PotionEffect(Potion.moveSlowdown.id, 200, getFrosty() - 1));
/*     */       }
/*     */       
/* 113 */       if (this.fragile) {
/* 114 */         setDead();
/* 115 */         playSound(Blocks.ice.stepSound.getBreakSound(), 0.3F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 116 */         mop.entityHit.motionX = (mx + (mop.entityHit.motionX - mx) / 10.0D);
/* 117 */         mop.entityHit.motionY = (my + (mop.entityHit.motionY - my) / 10.0D);
/* 118 */         mop.entityHit.motionZ = (mz + (mop.entityHit.motionZ - mz) / 10.0D);
/*     */       }
/*     */     }
/*     */     
/* 122 */     if (this.bounceLimit-- <= 0) {
/* 123 */       setDead();
/* 124 */       playSound(Blocks.ice.stepSound.getBreakSound(), 0.3F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 125 */       for (int a = 0; a < 8.0F * getDamage(); a++) {
/* 126 */         this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX, this.posY, this.posZ, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(Blocks.ice.getDefaultState()) });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 136 */     super.onUpdate();
/*     */     
/* 138 */     if ((this.worldObj.isRemote) && (getFrosty() > 0)) {
/* 139 */       float s = getDamage() / 10.0F;
/* 140 */       for (int a = 0; a < getFrosty(); a++) {
/* 141 */         Thaumcraft.proxy.getFX().sparkle((float)this.posX - s + this.rand.nextFloat() * (s * 2.0F), (float)this.posY - s + this.rand.nextFloat() * (s * 2.0F), (float)this.posZ - s + this.rand.nextFloat() * (s * 2.0F), 0.4F, 6, 0.005F);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 148 */     float var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 149 */     this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
/*     */     
/* 151 */     for (this.rotationPitch = ((float)(Math.atan2(this.motionY, var20) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 156 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */     {
/* 158 */       this.prevRotationPitch += 360.0F;
/*     */     }
/*     */     
/* 161 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */     {
/* 163 */       this.prevRotationYaw -= 360.0F;
/*     */     }
/*     */     
/* 166 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */     {
/* 168 */       this.prevRotationYaw += 360.0F;
/*     */     }
/*     */     
/* 171 */     this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
/* 172 */     this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 178 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 179 */     par1NBTTagCompound.setFloat("damage", getDamage());
/* 180 */     par1NBTTagCompound.setBoolean("fragile", this.fragile);
/* 181 */     par1NBTTagCompound.setInteger("frost", getFrosty());
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 187 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 188 */     setDamage(par1NBTTagCompound.getFloat("damage"));
/* 189 */     this.fragile = par1NBTTagCompound.getBoolean("fragile");
/* 190 */     setFrosty(par1NBTTagCompound.getInteger("frost"));
/*     */   }
/*     */   
/*     */ 
/* 194 */   public boolean fragile = false;
/*     */   
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/* 199 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void entityInit()
/*     */   {
/* 206 */     super.entityInit();
/* 207 */     this.dataWatcher.addObject(16, new Float(0.0F));
/* 208 */     this.dataWatcher.addObject(17, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public void setDamage(float par1)
/*     */   {
/* 213 */     this.dataWatcher.updateObject(16, Float.valueOf(par1));
/*     */     
/* 215 */     setSize(0.15F + par1 * 0.15F, 0.15F + par1 * 0.15F);
/*     */   }
/*     */   
/*     */   public float getDamage()
/*     */   {
/* 220 */     return this.dataWatcher.getWatchableObjectFloat(16);
/*     */   }
/*     */   
/*     */   public void setFrosty(int frosty)
/*     */   {
/* 225 */     this.dataWatcher.updateObject(17, Byte.valueOf((byte)frosty));
/*     */   }
/*     */   
/*     */   public int getFrosty()
/*     */   {
/* 230 */     return this.dataWatcher.getWatchableObjectByte(17);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityFrostShard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */