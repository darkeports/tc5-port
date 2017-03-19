/*     */ package thaumcraft.common.entities.projectile;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ public class EntityPrimalOrb extends EntityThrowable implements net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
/*     */ {
/*     */   public EntityPrimalOrb(World par1World)
/*     */   {
/*  26 */     super(par1World);
/*     */   }
/*     */   
/*     */   public EntityPrimalOrb(World par1World, EntityLivingBase p, boolean seeker) {
/*  30 */     super(par1World, p);
/*  31 */     Vec3 v = p.getLookVec();
/*  32 */     setLocationAndAngles(p.posX + v.xCoord / 2.0D, p.posY + p.getEyeHeight() + v.yCoord / 2.0D, p.posZ + v.zCoord / 2.0D, p.rotationYaw, p.rotationPitch);
/*  33 */     this.seeker = seeker;
/*  34 */     this.oi = p.getEntityId();
/*     */   }
/*     */   
/*     */   public void writeSpawnData(ByteBuf data)
/*     */   {
/*  39 */     data.writeBoolean(this.seeker);
/*  40 */     data.writeInt(this.oi);
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf data)
/*     */   {
/*  45 */     this.seeker = data.readBoolean();
/*  46 */     this.oi = data.readInt();
/*     */   }
/*     */   
/*     */   protected float getGravityVelocity()
/*     */   {
/*  51 */     return 0.001F;
/*     */   }
/*     */   
/*     */   protected float getVelocity()
/*     */   {
/*  56 */     return 0.5F;
/*     */   }
/*     */   
/*  59 */   int count = 0;
/*  60 */   boolean seeker = false;
/*  61 */   int oi = 0;
/*     */   
/*     */   public void onUpdate()
/*     */   {
/*  65 */     this.count += 1;
/*  66 */     if (isInsideOfMaterial(Material.portal)) {
/*  67 */       onImpact(new MovingObjectPosition(this));
/*     */     }
/*     */     
/*  70 */     if (this.worldObj.isRemote)
/*     */     {
/*  72 */       for (int a = 0; a < 6; a++) {
/*  73 */         Thaumcraft.proxy.getFX().wispFX4((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, this, a, true, 0.01F);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  83 */       Thaumcraft.proxy.getFX().wispFX2(this.posX + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, this.posY + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, this.posZ + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, 0.1F, this.rand.nextInt(6), true, true, 0.0F);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  91 */     Random rr = new Random(getEntityId() + this.count);
/*     */     
/*  93 */     if (this.ticksExisted > 20) {
/*  94 */       if (!this.seeker) {
/*  95 */         this.motionX += (rr.nextFloat() - rr.nextFloat()) * 0.01F;
/*  96 */         this.motionY += (rr.nextFloat() - rr.nextFloat()) * 0.01F;
/*  97 */         this.motionZ += (rr.nextFloat() - rr.nextFloat()) * 0.01F;
/*     */       } else {
/*  99 */         java.util.List<Entity> l = EntityUtils.getEntitiesInRange(this.worldObj, this.posX, this.posY, this.posZ, this, EntityLivingBase.class, 16.0D);
/* 100 */         double d = Double.MAX_VALUE;
/* 101 */         Entity t = null;
/* 102 */         for (Entity e : l) {
/* 103 */           if ((e.getEntityId() != this.oi) && (!e.isDead)) {
/* 104 */             double dd = getDistanceSqToEntity(e);
/* 105 */             if (dd < d) {
/* 106 */               d = dd;
/* 107 */               t = e;
/*     */             }
/*     */           }
/*     */         }
/* 111 */         if (t != null) {
/* 112 */           double dx = t.posX - this.posX;
/* 113 */           double dy = t.getEntityBoundingBox().minY + t.height * 0.9D - this.posY;
/* 114 */           double dz = t.posZ - this.posZ;
/* 115 */           double d13 = 0.2D;
/* 116 */           dx /= d;
/* 117 */           dy /= d;
/* 118 */           dz /= d;
/*     */           
/* 120 */           this.motionX += dx * d13;
/* 121 */           this.motionY += dy * d13;
/* 122 */           this.motionZ += dz * d13;
/*     */           
/* 124 */           this.motionX = MathHelper.clamp_float((float)this.motionX, -0.2F, 0.2F);
/* 125 */           this.motionY = MathHelper.clamp_float((float)this.motionY, -0.2F, 0.2F);
/* 126 */           this.motionZ = MathHelper.clamp_float((float)this.motionZ, -0.2F, 0.2F);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 131 */     super.onUpdate();
/* 132 */     if (this.ticksExisted > 5000) { setDead();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void onImpact(MovingObjectPosition mop)
/*     */   {
/* 140 */     if (this.worldObj.isRemote) {
/* 141 */       for (int a = 0; a < 6; a++) {
/* 142 */         for (int b = 0; b < 6; b++) {
/* 143 */           float fx = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.5F;
/*     */           
/* 145 */           float fy = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.5F;
/*     */           
/* 147 */           float fz = (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.5F;
/*     */           
/* 149 */           Thaumcraft.proxy.getFX().wispFX3(this.posX + fx, this.posY + fy, this.posZ + fz, this.posX + fx * 10.0F, this.posY + fy * 10.0F, this.posZ + fz * 10.0F, 0.4F, b, true, 0.05F);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 155 */     if (!this.worldObj.isRemote) {
/* 156 */       float specialchance = 1.0F;
/* 157 */       float expl = 2.0F;
/* 158 */       if ((mop.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK) && 
/* 159 */         (isInsideOfMaterial(Material.portal))) {
/* 160 */         expl = 4.0F;
/* 161 */         specialchance = 10.0F;
/*     */       }
/*     */       
/* 164 */       this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, expl, true);
/*     */       
/* 166 */       if ((!this.seeker) && (this.rand.nextInt(100) <= specialchance)) {
/* 167 */         taintSplosion();
/*     */       }
/*     */       
/* 170 */       setDead();
/*     */     }
/*     */   }
/*     */   
/*     */   public void taintSplosion()
/*     */   {
/* 176 */     int x = (int)this.posX;
/* 177 */     int y = (int)this.posY;
/* 178 */     int z = (int)this.posZ;
/* 179 */     for (int a = 0; a < 15; a++) {
/* 180 */       int xx = x + (int)(this.rand.nextFloat() - this.rand.nextFloat() * 6.0F);
/* 181 */       int zz = z + (int)(this.rand.nextFloat() - this.rand.nextFloat() * 6.0F);
/* 182 */       int yy = y + (int)(this.rand.nextFloat() - this.rand.nextFloat() * 6.0F);
/* 183 */       BlockPos bp = new BlockPos(xx, yy, zz);
/*     */       
/* 185 */       if ((this.worldObj.isAirBlock(bp)) && (thaumcraft.common.lib.utils.BlockUtils.isAdjacentToSolidBlock(this.worldObj, bp))) {
/* 186 */         this.worldObj.setBlockState(bp, BlocksTC.taintFibre.getDefaultState(), 3);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityPrimalOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */