/*     */ package thaumcraft.common.entities.projectile;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.codechicken.libold.math.MathHelper;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ public class EntityShockOrb extends EntityThrowable
/*     */ {
/*     */   public EntityShockOrb(World par1World)
/*     */   {
/*  20 */     super(par1World);
/*     */   }
/*     */   
/*     */   public EntityShockOrb(World par1World, EntityLivingBase p) {
/*  24 */     super(par1World, p);
/*  25 */     Vec3 v = p.getLookVec();
/*  26 */     setLocationAndAngles(p.posX + v.xCoord / 2.0D, p.posY + p.getEyeHeight() + v.yCoord / 2.0D, p.posZ + v.zCoord / 2.0D, p.rotationYaw, p.rotationPitch);
/*     */   }
/*     */   
/*     */   protected float getGravityVelocity()
/*     */   {
/*  31 */     return 0.05F;
/*     */   }
/*     */   
/*  34 */   public int area = 4;
/*  35 */   public int damage = 5;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void onImpact(MovingObjectPosition mop)
/*     */   {
/*  42 */     if (!this.worldObj.isRemote) {
/*  43 */       java.util.ArrayList<Entity> list = EntityUtils.getEntitiesInRange(this.worldObj, this.posX, this.posY, this.posZ, this, Entity.class, this.area);
/*  44 */       for (Entity e : list) {
/*  45 */         if (EntityUtils.canEntityBeSeen(this, e))
/*  46 */           e.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, getThrower()), this.damage);
/*     */       }
/*  48 */       for (int a = 0; a < 20; a++) {
/*  49 */         int xx = MathHelper.floor_double(this.posX) + this.rand.nextInt(this.area) - this.rand.nextInt(this.area);
/*  50 */         int yy = MathHelper.floor_double(this.posY) + this.area;
/*  51 */         int zz = MathHelper.floor_double(this.posZ) + this.rand.nextInt(this.area) - this.rand.nextInt(this.area);
/*  52 */         BlockPos bp = new BlockPos(xx, yy, zz);
/*  53 */         while ((this.worldObj.isAirBlock(bp)) && (yy > MathHelper.floor_double(this.posY) - this.area)) {
/*  54 */           yy--;
/*  55 */           bp = new BlockPos(xx, yy, zz);
/*     */         }
/*  57 */         if ((this.worldObj.isAirBlock(bp.up())) && (!this.worldObj.isAirBlock(bp)) && (this.worldObj.getBlockState(bp.up()).getBlock() != BlocksTC.effect) && (EntityUtils.canEntityBeSeen(this, xx + 0.5D, yy + 1.5D, zz + 0.5D)))
/*     */         {
/*     */ 
/*  60 */           this.worldObj.setBlockState(bp.up(), BlocksTC.effect.getStateFromMeta(0), 3);
/*     */         }
/*     */       }
/*     */     } else {
/*  64 */       thaumcraft.common.Thaumcraft.proxy.getFX().burst(this.posX, this.posY, this.posZ, 3.0F); }
/*  65 */     this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "thaumcraft:shock", 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*  66 */     setDead();
/*     */   }
/*     */   
/*     */   public void onUpdate()
/*     */   {
/*  71 */     super.onUpdate();
/*  72 */     if (this.ticksExisted > 500) { setDead();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
/*     */   {
/*  79 */     if (isEntityInvulnerable(p_70097_1_))
/*     */     {
/*  81 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  85 */     setBeenAttacked();
/*     */     
/*  87 */     if (p_70097_1_.getEntity() != null)
/*     */     {
/*  89 */       Vec3 vec3 = p_70097_1_.getEntity().getLookVec();
/*     */       
/*  91 */       if (vec3 != null)
/*     */       {
/*  93 */         this.motionX = vec3.xCoord;
/*  94 */         this.motionY = vec3.yCoord;
/*  95 */         this.motionZ = vec3.zCoord;
/*  96 */         this.motionX *= 0.9D;
/*  97 */         this.motionY *= 0.9D;
/*  98 */         this.motionZ *= 0.9D;
/*  99 */         this.worldObj.playSoundAtEntity(this, "thaumcraft:zap", 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*     */       }
/* 101 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 105 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityShockOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */