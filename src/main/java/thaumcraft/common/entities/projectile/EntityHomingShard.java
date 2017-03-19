/*     */ package thaumcraft.common.entities.projectile;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.client.lib.UtilsFX.Vector;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class EntityHomingShard extends EntityThrowable implements net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
/*     */ {
/*     */   public EntityHomingShard(World par1World)
/*     */   {
/*  26 */     super(par1World);
/*     */   }
/*     */   
/*     */   public EntityHomingShard(World par1World, EntityLivingBase p, EntityLivingBase t, int strength, boolean b) {
/*  30 */     super(par1World, p);
/*  31 */     this.target = t;
/*  32 */     this.tclass = t.getClass();
/*  33 */     this.persistant = b;
/*  34 */     setStrength(strength);
/*  35 */     Vec3 v = p.getLookVec();
/*  36 */     setLocationAndAngles(p.posX + v.xCoord / 2.0D, p.posY + p.getEyeHeight() + v.yCoord / 2.0D, p.posZ + v.zCoord / 2.0D, p.rotationYaw, p.rotationPitch);
/*  37 */     float f = 0.5F;
/*  38 */     float ry = p.rotationYaw + (this.rand.nextFloat() - this.rand.nextFloat()) * 60.0F;
/*  39 */     float rp = p.rotationPitch + (this.rand.nextFloat() - this.rand.nextFloat()) * 60.0F;
/*  40 */     this.motionX = (-MathHelper.sin(ry / 180.0F * 3.1415927F) * MathHelper.cos(rp / 180.0F * 3.1415927F) * f);
/*  41 */     this.motionZ = (MathHelper.cos(ry / 180.0F * 3.1415927F) * MathHelper.cos(rp / 180.0F * 3.1415927F) * f);
/*  42 */     this.motionY = (-MathHelper.sin((rp + getInaccuracy()) / 180.0F * 3.1415927F) * f);
/*     */   }
/*     */   
/*  45 */   Class tclass = null;
/*  46 */   boolean persistant = false;
/*     */   
/*  48 */   int targetID = 0;
/*     */   
/*     */   EntityLivingBase target;
/*     */   
/*     */   public void entityInit()
/*     */   {
/*  54 */     super.entityInit();
/*  55 */     this.dataWatcher.addObject(17, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public void setStrength(int str)
/*     */   {
/*  60 */     this.dataWatcher.updateObject(17, Byte.valueOf((byte)str));
/*     */   }
/*     */   
/*     */   public int getStrength()
/*     */   {
/*  65 */     return this.dataWatcher.getWatchableObjectByte(17);
/*     */   }
/*     */   
/*     */   protected float getGravityVelocity()
/*     */   {
/*  70 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public void writeSpawnData(ByteBuf data)
/*     */   {
/*  75 */     int id = -1;
/*  76 */     if (this.target != null) id = this.target.getEntityId();
/*  77 */     data.writeInt(id);
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf data)
/*     */   {
/*  82 */     int id = data.readInt();
/*     */     try {
/*  84 */       if (id >= 0) { this.target = ((EntityLivingBase)this.worldObj.getEntityByID(id));
/*     */       }
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */ 
/*     */   protected void onImpact(MovingObjectPosition mop)
/*     */   {
/*  93 */     if ((!this.worldObj.isRemote) && 
/*  94 */       (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) && ((getThrower() == null) || ((getThrower() != null) && (mop.entityHit != getThrower())))) {
/*  95 */       mop.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, getThrower()), 1.0F + getStrength() * 0.5F);
/*  96 */       this.worldObj.playSoundAtEntity(this, "thaumcraft:zap", 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*  97 */       this.worldObj.setEntityState(this, (byte)16);
/*  98 */       setDead();
/*     */     }
/*     */     
/* 101 */     if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 102 */       if (mop.sideHit.getFrontOffsetZ() != 0) this.motionZ *= -0.8D;
/* 103 */       if (mop.sideHit.getFrontOffsetX() != 0) this.motionX *= -0.8D;
/* 104 */       if (mop.sideHit.getFrontOffsetY() != 0) { this.motionY *= -0.8D;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public void handleStatusUpdate(byte par1)
/*     */   {
/* 114 */     if (par1 == 16)
/*     */     {
/* 116 */       Thaumcraft.proxy.getFX().burst(this.posX, this.posY, this.posZ, 0.3F);
/*     */     }
/*     */     else
/*     */     {
/* 120 */       super.handleStatusUpdate(par1);
/*     */     }
/*     */   }
/*     */   
/* 124 */   public ArrayList<UtilsFX.Vector> vl = new ArrayList();
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 129 */     this.vl.add(0, new UtilsFX.Vector((float)this.lastTickPosX, (float)this.lastTickPosY, (float)this.lastTickPosZ));
/* 130 */     if (this.vl.size() > 6) { this.vl.remove(this.vl.size() - 1);
/*     */     }
/* 132 */     super.onUpdate();
/*     */     
/* 134 */     if (!this.worldObj.isRemote) {
/* 135 */       if ((this.persistant) && ((this.target == null) || (this.target.isDead) || (this.target.getDistanceSqToEntity(this) > 1250.0D))) {
/* 136 */         ArrayList<Entity> es = thaumcraft.common.lib.utils.EntityUtils.getEntitiesInRange(this.worldObj, this.posX, this.posY, this.posZ, this, this.tclass, 16.0D);
/* 137 */         for (Entity e : es) {
/* 138 */           if (((e instanceof EntityLivingBase)) && (!e.isDead) && ((getThrower() == null) || (e.getEntityId() != getThrower().getEntityId())))
/*     */           {
/* 140 */             this.target = ((EntityLivingBase)e);
/* 141 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 145 */       if ((this.target == null) || (this.target.isDead)) {
/* 146 */         this.worldObj.setEntityState(this, (byte)16);
/* 147 */         setDead();
/*     */       }
/*     */     }
/*     */     
/* 151 */     if (this.ticksExisted > 300) {
/* 152 */       this.worldObj.setEntityState(this, (byte)16);
/* 153 */       setDead();
/*     */     }
/*     */     
/*     */ 
/* 157 */     if ((this.ticksExisted % 20 == 0) && (this.target != null) && (!this.target.isDead)) {
/* 158 */       double d = getDistanceToEntity(this.target);
/* 159 */       double dx = this.target.posX - this.posX;
/* 160 */       double dy = this.target.getEntityBoundingBox().minY + this.target.height * 0.6D - this.posY;
/* 161 */       double dz = this.target.posZ - this.posZ;
/* 162 */       dx /= d;
/* 163 */       dy /= d;
/* 164 */       dz /= d;
/*     */       
/* 166 */       this.motionX = dx;
/* 167 */       this.motionY = dy;
/* 168 */       this.motionZ = dz;
/*     */     }
/*     */     
/*     */ 
/* 172 */     this.motionX *= 0.85D;
/* 173 */     this.motionY *= 0.85D;
/* 174 */     this.motionZ *= 0.85D;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityHomingShard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */