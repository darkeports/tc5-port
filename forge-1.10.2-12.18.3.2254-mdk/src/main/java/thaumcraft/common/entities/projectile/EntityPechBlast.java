/*     */ package thaumcraft.common.entities.projectile;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.monster.EntityPech;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXBlockBamf;
/*     */ 
/*     */ public class EntityPechBlast extends EntityThrowable implements net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
/*     */ {
/*     */   public EntityPechBlast(World par1World)
/*     */   {
/*  25 */     super(par1World);
/*     */   }
/*     */   
/*  28 */   int strength = 0;
/*  29 */   int duration = 0;
/*  30 */   boolean nightshade = false;
/*     */   
/*     */   public void writeSpawnData(ByteBuf data)
/*     */   {
/*  34 */     data.writeBoolean(this.nightshade);
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf data)
/*     */   {
/*  39 */     this.nightshade = data.readBoolean();
/*     */   }
/*     */   
/*     */   public EntityPechBlast(World par1World, EntityLivingBase p, int strength, int duration, boolean nightshade)
/*     */   {
/*  44 */     super(par1World, p);
/*  45 */     Vec3 v = p.getLookVec();
/*  46 */     setLocationAndAngles(p.posX + v.xCoord / 2.0D, p.posY + p.getEyeHeight() + v.yCoord / 2.0D, p.posZ + v.zCoord / 2.0D, p.rotationYaw, p.rotationPitch);
/*  47 */     this.strength = strength;
/*  48 */     this.nightshade = nightshade;
/*  49 */     this.duration = duration;
/*     */   }
/*     */   
/*     */   public EntityPechBlast(World par1World, double par2, double par4, double par6, int strength, int duration, boolean nightshade)
/*     */   {
/*  54 */     super(par1World, par2, par4, par6);
/*     */     
/*  56 */     this.strength = strength;
/*  57 */     this.nightshade = nightshade;
/*  58 */     this.duration = duration;
/*     */   }
/*     */   
/*     */   protected float getGravityVelocity()
/*     */   {
/*  63 */     return 0.025F;
/*     */   }
/*     */   
/*     */   protected float getVelocity()
/*     */   {
/*  68 */     return 1.5F;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  74 */     if (this.worldObj.isRemote) {
/*  75 */       Thaumcraft.proxy.getFX().pechsCurseTick(this.posX, this.posY, this.posZ);
/*     */     }
/*     */     
/*  78 */     super.onUpdate();
/*     */     
/*  80 */     if (this.ticksExisted > 500) { setDead();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDead()
/*     */   {
/*  86 */     if (!this.worldObj.isRemote) {}
/*     */     
/*     */ 
/*  89 */     super.setDead();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void onImpact(MovingObjectPosition mop)
/*     */   {
/*  97 */     if (!this.worldObj.isRemote) {
/*  98 */       if (this.nightshade) {
/*  99 */         PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockBamf(mop.hitVec.xCoord, Math.max(mop.hitVec.yCoord, this.posY), mop.hitVec.zCoord, 5770890, true, true, false), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 32.0D));
/*     */       }
/*     */       else
/*     */       {
/* 103 */         PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockBamf(mop.hitVec.xCoord, Math.max(mop.hitVec.yCoord, this.posY), mop.hitVec.zCoord, 2815273, true, true, false), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 32.0D));
/*     */       }
/*     */       
/*     */ 
/* 107 */       List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(getThrower(), getEntityBoundingBox().expand(2.0D, 2.0D, 2.0D));
/*     */       
/* 109 */       for (int i = 0; i < list.size(); i++) {
/* 110 */         Entity entity1 = (Entity)list.get(i);
/*     */         
/* 112 */         if ((!(entity1 instanceof EntityPech)) && ((entity1 instanceof EntityLivingBase)))
/*     */         {
/* 114 */           ((EntityLivingBase)entity1).attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(this, getThrower()), this.strength + 2);
/*     */           try {
/* 116 */             if (this.nightshade) {
/* 117 */               ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.poison.id, 100 + this.duration * 40, this.strength));
/* 118 */               ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100 + this.duration * 40, this.strength + 1));
/* 119 */               ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.weakness.id, 100 + this.duration * 40, this.strength));
/*     */             } else {
/* 121 */               switch (this.rand.nextInt(3)) {
/*     */               case 0: 
/* 123 */                 ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.poison.id, 100 + this.duration * 40, this.strength));
/* 124 */                 break;
/*     */               case 1: 
/* 126 */                 ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100 + this.duration * 40, this.strength + 1));
/* 127 */                 break;
/*     */               case 2: 
/* 129 */                 ((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.weakness.id, 100 + this.duration * 40, this.strength));
/*     */               }
/*     */               
/*     */             }
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */       }
/*     */       
/* 138 */       setDead();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityPechBlast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */