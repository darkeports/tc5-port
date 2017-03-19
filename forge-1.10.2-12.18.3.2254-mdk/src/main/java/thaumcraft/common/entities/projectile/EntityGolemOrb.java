/*     */ package thaumcraft.common.entities.projectile;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityGolemOrb extends EntityThrowable implements net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
/*     */ {
/*     */   public EntityGolemOrb(World par1World)
/*     */   {
/*  18 */     super(par1World);
/*     */   }
/*     */   
/*     */   public EntityGolemOrb(World par1World, EntityLivingBase par2EntityLiving, EntityLivingBase t, boolean r) {
/*  22 */     super(par1World, par2EntityLiving);
/*  23 */     this.target = t;
/*  24 */     this.red = r;
/*     */   }
/*     */   
/*  27 */   int targetID = 0;
/*     */   EntityLivingBase target;
/*  29 */   public boolean red = false;
/*     */   
/*     */ 
/*     */   protected float getGravityVelocity()
/*     */   {
/*  34 */     return 0.0F;
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeSpawnData(ByteBuf data)
/*     */   {
/*  40 */     int id = -1;
/*  41 */     if (this.target != null) id = this.target.getEntityId();
/*  42 */     data.writeInt(id);
/*  43 */     data.writeBoolean(this.red);
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf data)
/*     */   {
/*  48 */     int id = data.readInt();
/*     */     try {
/*  50 */       if (id >= 0) this.target = ((EntityLivingBase)this.worldObj.getEntityByID(id));
/*     */     } catch (Exception e) {}
/*  52 */     this.red = data.readBoolean();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void onImpact(MovingObjectPosition mop)
/*     */   {
/*  60 */     if ((!this.worldObj.isRemote) && (getThrower() != null) && (mop.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.ENTITY)) {
/*  61 */       mop.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, getThrower()), (float)getThrower().getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.attackDamage).getAttributeValue() * (this.red ? 1.0F : 0.6F));
/*     */     }
/*     */     
/*  64 */     this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "thaumcraft:shock", 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*  65 */     if (this.worldObj.isRemote) thaumcraft.common.Thaumcraft.proxy.getFX().burst(this.posX, this.posY, this.posZ, 1.0F);
/*  66 */     setDead();
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  72 */     super.onUpdate();
/*  73 */     if (this.ticksExisted > (this.red ? 240 : 160)) { setDead();
/*     */     }
/*  75 */     if (this.target != null) {
/*  76 */       double d = getDistanceSqToEntity(this.target);
/*  77 */       double dx = this.target.posX - this.posX;
/*  78 */       double dy = this.target.getEntityBoundingBox().minY + this.target.height * 0.6D - this.posY;
/*  79 */       double dz = this.target.posZ - this.posZ;
/*  80 */       double d13 = 0.2D;
/*  81 */       dx /= d;
/*  82 */       dy /= d;
/*  83 */       dz /= d;
/*     */       
/*  85 */       this.motionX += dx * d13;
/*  86 */       this.motionY += dy * d13;
/*  87 */       this.motionZ += dz * d13;
/*     */       
/*  89 */       this.motionX = MathHelper.clamp_float((float)this.motionX, -0.25F, 0.25F);
/*  90 */       this.motionY = MathHelper.clamp_float((float)this.motionY, -0.25F, 0.25F);
/*  91 */       this.motionZ = MathHelper.clamp_float((float)this.motionZ, -0.25F, 0.25F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
/*     */   {
/*  98 */     if (isEntityInvulnerable(p_70097_1_))
/*     */     {
/* 100 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 104 */     setBeenAttacked();
/*     */     
/* 106 */     if (p_70097_1_.getEntity() != null)
/*     */     {
/* 108 */       Vec3 vec3 = p_70097_1_.getEntity().getLookVec();
/*     */       
/* 110 */       if (vec3 != null)
/*     */       {
/* 112 */         this.motionX = vec3.xCoord;
/* 113 */         this.motionY = vec3.yCoord;
/* 114 */         this.motionZ = vec3.zCoord;
/* 115 */         this.motionX *= 0.9D;
/* 116 */         this.motionY *= 0.9D;
/* 117 */         this.motionZ *= 0.9D;
/* 118 */         this.worldObj.playSoundAtEntity(this, "thaumcraft:zap", 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*     */       }
/* 120 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 124 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityGolemOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */