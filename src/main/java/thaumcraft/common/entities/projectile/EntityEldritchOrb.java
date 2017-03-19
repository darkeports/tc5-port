/*    */ package thaumcraft.common.entities.projectile;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.projectile.EntityThrowable;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class EntityEldritchOrb extends EntityThrowable
/*    */ {
/*    */   public EntityEldritchOrb(World par1World)
/*    */   {
/* 19 */     super(par1World);
/*    */   }
/*    */   
/*    */   public EntityEldritchOrb(World par1World, EntityLivingBase p) {
/* 23 */     super(par1World, p);
/* 24 */     Vec3 v = p.getLookVec();
/* 25 */     setLocationAndAngles(p.posX + v.xCoord / 2.0D, p.posY + p.getEyeHeight() + v.yCoord / 2.0D, p.posZ + v.zCoord / 2.0D, p.rotationYaw, p.rotationPitch);
/*    */   }
/*    */   
/*    */   protected float getGravityVelocity()
/*    */   {
/* 30 */     return 0.0F;
/*    */   }
/*    */   
/*    */   public void onUpdate()
/*    */   {
/* 35 */     super.onUpdate();
/* 36 */     if (this.ticksExisted > 100) {
/* 37 */       setDead();
/*    */     }
/*    */   }
/*    */   
/*    */   public void handleStatusUpdate(byte b)
/*    */   {
/* 43 */     if (b == 16) {
/* 44 */       if (this.worldObj.isRemote) {
/* 45 */         for (int a = 0; a < 30; a++) {
/* 46 */           float fx = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
/* 47 */           float fy = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
/* 48 */           float fz = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
/* 49 */           Thaumcraft.proxy.getFX().wispFX3(this.posX + fx, this.posY + fy, this.posZ + fz, this.posX + fx * 8.0F, this.posY + fy * 8.0F, this.posZ + fz * 8.0F, 0.3F, 5, true, 0.02F);
/*    */         }
/*    */         
/*    */       }
/*    */       
/*    */     }
/*    */     else {
/* 56 */       super.handleStatusUpdate(b);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void onImpact(MovingObjectPosition mop)
/*    */   {
/* 65 */     if ((!this.worldObj.isRemote) && (getThrower() != null)) {
/* 66 */       List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(getThrower(), getEntityBoundingBox().expand(2.0D, 2.0D, 2.0D));
/* 67 */       for (int i = 0; i < list.size(); i++) {
/* 68 */         Entity entity1 = (Entity)list.get(i);
/* 69 */         if (((entity1 instanceof EntityLivingBase)) && (!((EntityLivingBase)entity1).isEntityUndead())) {
/* 70 */           ((EntityLivingBase)entity1).attackEntityFrom(net.minecraft.util.DamageSource.causeIndirectMagicDamage(this, getThrower()), (float)getThrower().getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.attackDamage).getAttributeValue() * 0.666F);
/*    */           
/*    */           try
/*    */           {
/* 74 */             ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.weakness.id, 160, 0));
/*    */           }
/*    */           catch (Exception e) {}
/*    */         }
/*    */       }
/*    */       
/* 80 */       this.worldObj.playSoundAtEntity(this, "random.fizz", 0.5F, 2.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8F);
/* 81 */       this.ticksExisted = 100;
/* 82 */       this.worldObj.setEntityState(this, (byte)16);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityEldritchOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */