/*     */ package thaumcraft.common.entities.projectile;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntityDamageSourceIndirect;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityEmber extends EntityThrowable implements net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
/*     */ {
/*     */   public EntityEmber(World par1World)
/*     */   {
/*  19 */     super(par1World);
/*     */   }
/*     */   
/*     */   public EntityEmber(World par1World, EntityLivingBase p, float scatter) {
/*  23 */     super(par1World, p);
/*  24 */     Vec3 v = p.getLookVec();
/*  25 */     setLocationAndAngles(p.posX + v.xCoord / 2.0D, p.posY + p.getEyeHeight() + v.yCoord / 2.0D, p.posZ + v.zCoord / 2.0D, p.rotationYaw, p.rotationPitch);
/*  26 */     setThrowableHeading(this.motionX, this.motionY, this.motionZ, getVelocity(), scatter);
/*     */   }
/*     */   
/*     */   protected float getGravityVelocity()
/*     */   {
/*  31 */     return 0.0F;
/*     */   }
/*     */   
/*     */   protected float getVelocity()
/*     */   {
/*  36 */     return 1.0F;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  42 */     if (this.ticksExisted > this.duration) { setDead();
/*     */     }
/*  44 */     if (this.duration <= 20) {
/*  45 */       this.motionX *= 0.95D;
/*  46 */       this.motionY *= 0.95D;
/*  47 */       this.motionZ *= 0.95D;
/*     */     } else {
/*  49 */       this.motionX *= 0.975D;
/*  50 */       this.motionY *= 0.975D;
/*  51 */       this.motionZ *= 0.975D;
/*     */     }
/*     */     
/*  54 */     if (this.onGround) {
/*  55 */       this.motionX *= 0.66D;
/*  56 */       this.motionY *= 0.66D;
/*  57 */       this.motionZ *= 0.66D;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  62 */     super.onUpdate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  67 */   public int duration = 20;
/*  68 */   public int firey = 0;
/*  69 */   public float damage = 1.0F;
/*     */   
/*     */   public void writeSpawnData(ByteBuf data)
/*     */   {
/*  73 */     data.writeByte(this.duration);
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf data)
/*     */   {
/*  78 */     this.duration = data.readByte();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void onImpact(MovingObjectPosition mop)
/*     */   {
/*  87 */     if (!this.worldObj.isRemote)
/*     */     {
/*  89 */       if (mop.entityHit != null)
/*     */       {
/*  91 */         if ((!mop.entityHit.isImmuneToFire()) && (mop.entityHit.attackEntityFrom(new EntityDamageSourceIndirect("fireball", this, getThrower()).setFireDamage(), this.damage)))
/*     */         {
/*     */ 
/*  94 */           mop.entityHit.setFire(3 + this.firey);
/*     */         }
/*     */       }
/*  97 */       else if (this.rand.nextFloat() < 0.025F * this.firey)
/*     */       {
/*  99 */         boolean flag = true;
/*     */         
/* 101 */         if ((getThrower() != null) && ((getThrower() instanceof net.minecraft.entity.EntityLiving)))
/*     */         {
/* 103 */           flag = this.worldObj.getGameRules().getBoolean("mobGriefing");
/*     */         }
/*     */         
/* 106 */         if (flag)
/*     */         {
/* 108 */           BlockPos blockpos = mop.getBlockPos().offset(mop.sideHit);
/*     */           
/* 110 */           if (this.worldObj.isAirBlock(blockpos))
/*     */           {
/* 112 */             this.worldObj.setBlockState(blockpos, net.minecraft.init.Blocks.fire.getDefaultState());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 118 */     setDead();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/* 126 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 132 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 133 */     par1NBTTagCompound.setFloat("damage", this.damage);
/* 134 */     par1NBTTagCompound.setInteger("firey", this.firey);
/* 135 */     par1NBTTagCompound.setInteger("duration", this.duration);
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 141 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 142 */     this.damage = par1NBTTagCompound.getFloat("damage");
/* 143 */     this.firey = par1NBTTagCompound.getInteger("firey");
/* 144 */     this.duration = par1NBTTagCompound.getInteger("duration");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 151 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
/*     */   {
/* 157 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityEmber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */