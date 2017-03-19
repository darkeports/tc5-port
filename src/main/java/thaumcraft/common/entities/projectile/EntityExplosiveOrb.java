/*     */ package thaumcraft.common.entities.projectile;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityExplosiveOrb extends EntityThrowable
/*     */ {
/*     */   public EntityExplosiveOrb(World par1World)
/*     */   {
/*  15 */     super(par1World);
/*     */   }
/*     */   
/*     */   public EntityExplosiveOrb(World par1World, EntityLivingBase p) {
/*  19 */     super(par1World, p);
/*  20 */     Vec3 v = p.getLookVec();
/*  21 */     setLocationAndAngles(p.posX + v.xCoord / 2.0D, p.posY + p.getEyeHeight() + v.yCoord / 2.0D, p.posZ + v.zCoord / 2.0D, p.rotationYaw, p.rotationPitch);
/*     */   }
/*     */   
/*     */   protected float getGravityVelocity()
/*     */   {
/*  26 */     return 0.01F;
/*     */   }
/*     */   
/*  29 */   public float strength = 1.0F;
/*  30 */   public boolean onFire = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void onImpact(net.minecraft.util.MovingObjectPosition mop)
/*     */   {
/*  37 */     if (!this.worldObj.isRemote)
/*     */     {
/*  39 */       if (mop.entityHit != null)
/*     */       {
/*  41 */         mop.entityHit.attackEntityFrom(causeFireballDamage(this, getThrower()), this.strength * 1.5F);
/*     */       }
/*     */       
/*  44 */       this.worldObj.newExplosion((Entity)null, this.posX, this.posY, this.posZ, this.strength, this.onFire, false);
/*  45 */       setDead();
/*     */     }
/*  47 */     setDead();
/*     */   }
/*     */   
/*     */   public static DamageSource causeFireballDamage(EntityExplosiveOrb p_76362_0_, Entity p_76362_1_)
/*     */   {
/*  52 */     return p_76362_1_ == null ? new net.minecraft.util.EntityDamageSourceIndirect("onFire", p_76362_0_, p_76362_0_).setFireDamage().setProjectile() : new net.minecraft.util.EntityDamageSourceIndirect("fireball", p_76362_0_, p_76362_1_).setFireDamage().setProjectile();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  60 */     super.onUpdate();
/*  61 */     if (this.worldObj.isRemote) {
/*  62 */       thaumcraft.common.Thaumcraft.proxy.getFX().drawGenericParticles(this.prevPosX + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F, this.prevPosY + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F, this.prevPosZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F, 0.0D, 0.0D, 0.0D, 1.0F, 1.0F, 1.0F, 0.8F, false, 151, 9, 1, 7 + this.rand.nextInt(5), 0, 2.0F + this.rand.nextFloat(), 0.5F, 0);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  69 */     if (this.ticksExisted > 500) { setDead();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
/*     */   {
/*  75 */     if (isEntityInvulnerable(p_70097_1_))
/*     */     {
/*  77 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  81 */     setBeenAttacked();
/*     */     
/*  83 */     if (p_70097_1_.getEntity() != null)
/*     */     {
/*  85 */       Vec3 vec3 = p_70097_1_.getEntity().getLookVec();
/*     */       
/*  87 */       if (vec3 != null)
/*     */       {
/*  89 */         this.motionX = vec3.xCoord;
/*  90 */         this.motionY = vec3.yCoord;
/*  91 */         this.motionZ = vec3.zCoord;
/*  92 */         this.motionX *= 0.9D;
/*  93 */         this.motionY *= 0.9D;
/*  94 */         this.motionZ *= 0.9D;
/*     */       }
/*  96 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 100 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityExplosiveOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */