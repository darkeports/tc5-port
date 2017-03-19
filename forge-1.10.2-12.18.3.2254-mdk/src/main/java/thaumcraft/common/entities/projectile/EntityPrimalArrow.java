/*     */ package thaumcraft.common.entities.projectile;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntityDamageSourceIndirect;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
/*     */ import thaumcraft.api.damagesource.DamageSourceIndirectThaumcraftEntity;
/*     */ 
/*     */ public class EntityPrimalArrow extends EntityArrow implements IProjectile, IEntityAdditionalSpawnData
/*     */ {
/*  37 */   protected int xTile = -1;
/*  38 */   protected int yTile = -1;
/*  39 */   protected int zTile = -1;
/*  40 */   protected Block inTile = net.minecraft.init.Blocks.air;
/*  41 */   protected int inData = 0;
/*  42 */   protected boolean inGround = false;
/*     */   
/*     */   public int ticksInGround;
/*     */   
/*  46 */   protected int ticksInAir = 0;
/*  47 */   protected double damage = 2.1D;
/*     */   
/*     */   public int shootingEntityId;
/*     */   
/*     */   protected int knockbackStrength;
/*  52 */   public int type = 0;
/*     */   
/*     */   public void writeSpawnData(ByteBuf data)
/*     */   {
/*  56 */     data.writeDouble(this.motionX);
/*  57 */     data.writeDouble(this.motionY);
/*  58 */     data.writeDouble(this.motionZ);
/*  59 */     data.writeFloat(this.rotationYaw);
/*  60 */     data.writeFloat(this.rotationPitch);
/*  61 */     data.writeByte(this.type);
/*  62 */     data.writeInt(this.shootingEntityId);
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf data)
/*     */   {
/*  67 */     this.motionX = data.readDouble();
/*  68 */     this.motionY = data.readDouble();
/*  69 */     this.motionZ = data.readDouble();
/*  70 */     this.rotationYaw = data.readFloat();
/*  71 */     this.rotationPitch = data.readFloat();
/*  72 */     this.prevRotationYaw = this.rotationYaw;
/*  73 */     this.prevRotationPitch = this.rotationPitch;
/*  74 */     this.type = data.readByte();
/*  75 */     this.shootingEntityId = data.readInt();
/*     */   }
/*     */   
/*     */   public EntityPrimalArrow(World par1World)
/*     */   {
/*  80 */     super(par1World);
/*  81 */     this.renderDistanceWeight = 10.0D;
/*  82 */     setSize(0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public EntityPrimalArrow(World par1World, double par2, double par4, double par6)
/*     */   {
/*  87 */     super(par1World);
/*  88 */     this.renderDistanceWeight = 10.0D;
/*  89 */     setSize(0.25F, 0.25F);
/*  90 */     setPosition(par2, par4, par6);
/*     */   }
/*     */   
/*     */   public EntityPrimalArrow(World par1World, EntityLivingBase par2EntityLivingBase, float par3, int type)
/*     */   {
/*  95 */     super(par1World);
/*  96 */     this.renderDistanceWeight = 10.0D;
/*  97 */     this.shootingEntity = par2EntityLivingBase;
/*  98 */     this.type = type;
/*  99 */     this.canBePickedUp = 0;
/* 100 */     this.shootingEntityId = this.shootingEntity.getEntityId();
/* 101 */     setSize(0.5F, 0.5F);
/* 102 */     setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight(), par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
/* 103 */     this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F;
/* 104 */     this.posY -= 0.10000000014901161D;
/* 105 */     this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F;
/* 106 */     Vec3 vec3d = par2EntityLivingBase.getLook(1.0F);
/* 107 */     this.posX += vec3d.xCoord;
/* 108 */     this.posY += vec3d.yCoord;
/* 109 */     this.posZ += vec3d.zCoord;
/*     */     
/* 111 */     setPosition(this.posX, this.posY, this.posZ);
/* 112 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/* 113 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/* 114 */     this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F));
/* 115 */     setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 128 */     onEntityUpdate();
/*     */     
/* 130 */     if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
/*     */     {
/* 132 */       float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 133 */       this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
/* 134 */       this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(this.motionY, f) * 180.0D / 3.141592653589793D));
/*     */     }
/*     */     
/* 137 */     BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
/* 138 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 139 */     Block block = iblockstate.getBlock();
/*     */     
/* 141 */     if (block.getMaterial() != Material.air)
/*     */     {
/* 143 */       block.setBlockBoundsBasedOnState(this.worldObj, blockpos);
/* 144 */       AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(this.worldObj, blockpos, iblockstate);
/*     */       
/* 146 */       if ((axisalignedbb != null) && (axisalignedbb.isVecInside(new Vec3(this.posX, this.posY, this.posZ))))
/*     */       {
/* 148 */         this.inGround = true;
/*     */       }
/*     */     }
/*     */     
/* 152 */     if (this.arrowShake > 0)
/*     */     {
/* 154 */       this.arrowShake -= 1;
/*     */     }
/*     */     
/* 157 */     if (this.inGround)
/*     */     {
/* 159 */       int j = block.getMetaFromState(iblockstate);
/*     */       
/* 161 */       if ((block == this.inTile) && (j == this.inData))
/*     */       {
/* 163 */         this.ticksInGround += 1;
/*     */         
/* 165 */         if (this.ticksInGround >= (this.type < 8 ? 100 : 20))
/*     */         {
/* 167 */           setDead();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 172 */         this.inGround = false;
/* 173 */         this.motionX *= this.rand.nextFloat() * 0.2F;
/* 174 */         this.motionY *= this.rand.nextFloat() * 0.2F;
/* 175 */         this.motionZ *= this.rand.nextFloat() * 0.2F;
/* 176 */         this.ticksInGround = 0;
/* 177 */         this.ticksInAir = 0;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 182 */       this.ticksInAir += 1;
/* 183 */       Vec3 vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 184 */       Vec3 vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 185 */       MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3, false, true, false);
/* 186 */       vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 187 */       vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */       
/* 189 */       if (movingobjectposition != null)
/*     */       {
/* 191 */         vec3 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */       }
/*     */       
/* 194 */       Entity entity = null;
/* 195 */       List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 196 */       double d0 = 0.0D;
/*     */       
/*     */ 
/*     */ 
/* 200 */       for (int i = 0; i < list.size(); i++)
/*     */       {
/* 202 */         Entity entity1 = (Entity)list.get(i);
/*     */         
/* 204 */         if ((entity1.canBeCollidedWith()) && ((entity1 != this.shootingEntity) || (this.ticksInAir >= 5)))
/*     */         {
/* 206 */           float f1 = 0.3F;
/* 207 */           AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand(f1, f1, f1);
/* 208 */           MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
/*     */           
/* 210 */           if (movingobjectposition1 != null)
/*     */           {
/* 212 */             double d1 = vec31.distanceTo(movingobjectposition1.hitVec);
/*     */             
/* 214 */             if ((d1 < d0) || (d0 == 0.0D))
/*     */             {
/* 216 */               entity = entity1;
/* 217 */               d0 = d1;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 223 */       if (entity != null)
/*     */       {
/* 225 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 228 */       if ((movingobjectposition != null) && (movingobjectposition.entityHit != null) && ((movingobjectposition.entityHit instanceof EntityPlayer)))
/*     */       {
/* 230 */         EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;
/*     */         
/* 232 */         if ((entityplayer.capabilities.disableDamage) || (((this.shootingEntity instanceof EntityPlayer)) && (!((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))))
/*     */         {
/* 234 */           movingobjectposition = null;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 241 */       if (movingobjectposition != null)
/*     */       {
/* 243 */         if (movingobjectposition.entityHit != null)
/*     */         {
/*     */ 
/*     */ 
/* 247 */           if (inflictDamage(movingobjectposition))
/*     */           {
/* 249 */             if ((movingobjectposition.entityHit instanceof EntityLivingBase))
/*     */             {
/* 251 */               EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;
/*     */               
/* 253 */               if (this.knockbackStrength > 0)
/*     */               {
/* 255 */                 float f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */                 
/* 257 */                 if (f3 > 0.0F)
/*     */                 {
/* 259 */                   movingobjectposition.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579D / f3, 0.1D, this.motionZ * this.knockbackStrength * 0.6000000238418579D / f3);
/*     */                 }
/*     */               }
/*     */               
/* 263 */               if ((this.shootingEntity != null) && ((this.shootingEntity instanceof EntityLivingBase)))
/*     */               {
/* 265 */                 EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
/* 266 */                 EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
/*     */               }
/*     */               
/* 269 */               if ((this.shootingEntity != null) && (movingobjectposition.entityHit != this.shootingEntity) && ((movingobjectposition.entityHit instanceof EntityPlayer)) && ((this.shootingEntity instanceof EntityPlayerMP)))
/*     */               {
/* 271 */                 ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
/*     */               }
/*     */             }
/*     */             
/* 275 */             playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/*     */             
/* 277 */             if (!(movingobjectposition.entityHit instanceof EntityEnderman))
/*     */             {
/* 279 */               setDead();
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 284 */             this.motionX *= -0.10000000149011612D;
/* 285 */             this.motionY *= -0.10000000149011612D;
/* 286 */             this.motionZ *= -0.10000000149011612D;
/* 287 */             this.rotationYaw += 180.0F;
/* 288 */             this.prevRotationYaw += 180.0F;
/* 289 */             this.ticksInAir = 0;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 294 */           BlockPos blockpos1 = movingobjectposition.getBlockPos();
/* 295 */           this.xTile = blockpos1.getX();
/* 296 */           this.yTile = blockpos1.getY();
/* 297 */           this.zTile = blockpos1.getZ();
/* 298 */           iblockstate = this.worldObj.getBlockState(blockpos1);
/* 299 */           this.inTile = iblockstate.getBlock();
/* 300 */           this.inData = this.inTile.getMetaFromState(iblockstate);
/* 301 */           this.motionX = ((float)(movingobjectposition.hitVec.xCoord - this.posX));
/* 302 */           this.motionY = ((float)(movingobjectposition.hitVec.yCoord - this.posY));
/* 303 */           this.motionZ = ((float)(movingobjectposition.hitVec.zCoord - this.posZ));
/* 304 */           float f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 305 */           this.posX -= this.motionX / f3 * 0.05000000074505806D;
/* 306 */           this.posY -= this.motionY / f3 * 0.05000000074505806D;
/* 307 */           this.posZ -= this.motionZ / f3 * 0.05000000074505806D;
/* 308 */           playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 309 */           this.inGround = true;
/* 310 */           this.arrowShake = 7;
/* 311 */           setIsCritical(false);
/*     */           
/* 313 */           if (this.inTile.getMaterial() != Material.air)
/*     */           {
/* 315 */             this.inTile.onEntityCollidedWithBlock(this.worldObj, blockpos1, iblockstate, this);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 320 */       if (getIsCritical())
/*     */       {
/* 322 */         for (i = 0; i < 4; i++)
/*     */         {
/* 324 */           this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * i / 4.0D, this.posY + this.motionY * i / 4.0D, this.posZ + this.motionZ * i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ, new int[0]);
/*     */         }
/*     */       }
/*     */       
/* 328 */       this.posX += this.motionX;
/* 329 */       this.posY += this.motionY;
/* 330 */       this.posZ += this.motionZ;
/* 331 */       float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 332 */       this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
/*     */       
/* 334 */       for (this.rotationPitch = ((float)(Math.atan2(this.motionY, f2) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 339 */       while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */       {
/* 341 */         this.prevRotationPitch += 360.0F;
/*     */       }
/*     */       
/* 344 */       while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */       {
/* 346 */         this.prevRotationYaw -= 360.0F;
/*     */       }
/*     */       
/* 349 */       while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */       {
/* 351 */         this.prevRotationYaw += 360.0F;
/*     */       }
/*     */       
/* 354 */       this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
/* 355 */       this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
/* 356 */       float f3 = 0.99F;
/* 357 */       float f1 = 0.05F;
/*     */       
/* 359 */       if (isInWater())
/*     */       {
/* 361 */         for (int l = 0; l < 4; l++)
/*     */         {
/* 363 */           float f4 = 0.25F;
/* 364 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f4, this.posY - this.motionY * f4, this.posZ - this.motionZ * f4, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         }
/*     */         
/* 367 */         f3 = 0.6F;
/*     */       }
/*     */       
/* 370 */       if (isWet())
/*     */       {
/* 372 */         extinguish();
/*     */       }
/*     */       
/* 375 */       this.motionX *= f3;
/* 376 */       this.motionY *= f3;
/* 377 */       this.motionZ *= f3;
/* 378 */       this.motionY -= f1;
/* 379 */       setPosition(this.posX, this.posY, this.posZ);
/* 380 */       doBlockCollisions();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean inflictDamage(MovingObjectPosition movingobjectposition)
/*     */   {
/* 387 */     float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 388 */     int i1 = MathHelper.ceiling_double_int(f2 * getDamage());
/* 389 */     int fire = (isBurning()) && (this.type != 2) ? 5 : 0;
/* 390 */     if (getIsCritical())
/*     */     {
/* 392 */       i1 += this.rand.nextInt(i1 / 2 + 2);
/*     */     }
/*     */     
/* 395 */     DamageSource damagesource = null;
/*     */     
/* 397 */     switch (this.type) {
/*     */     case 0: 
/* 399 */       if (this.shootingEntity == null)
/*     */       {
/* 401 */         damagesource = new DamageSourceIndirectThaumcraftEntity("airarrow", this, this).setDamageBypassesArmor().setMagicDamage().setProjectile();
/*     */       }
/*     */       else
/*     */       {
/* 405 */         damagesource = new DamageSourceIndirectThaumcraftEntity("airarrow", this, this.shootingEntity).setDamageBypassesArmor().setMagicDamage().setProjectile();
/*     */       }
/* 407 */       break;
/*     */     
/*     */     case 1: 
/* 410 */       fire += 5;
/* 411 */       if (this.shootingEntity == null)
/*     */       {
/* 413 */         damagesource = new DamageSourceIndirectThaumcraftEntity("firearrow", this, this).setFireDamage().setProjectile();
/*     */       }
/*     */       else
/*     */       {
/* 417 */         damagesource = new DamageSourceIndirectThaumcraftEntity("firearrow", this, this.shootingEntity).setFireDamage().setProjectile();
/*     */       }
/* 419 */       break;
/*     */     
/*     */     case 4: 
/* 422 */       if (this.shootingEntity == null)
/*     */       {
/* 424 */         damagesource = new DamageSourceIndirectThaumcraftEntity("orderarrow", this, this).setDamageBypassesArmor().setMagicDamage().setProjectile();
/*     */       }
/*     */       else
/*     */       {
/* 428 */         damagesource = new DamageSourceIndirectThaumcraftEntity("orderarrow", this, this.shootingEntity).setDamageBypassesArmor().setMagicDamage().setProjectile();
/*     */       }
/* 430 */       if (!(movingobjectposition.entityHit instanceof EntityLivingBase)) break label471;
/* 431 */       ((EntityLivingBase)movingobjectposition.entityHit).addPotionEffect(new PotionEffect(Potion.weakness.id, 200, 4)); break;
/*     */     
/*     */ 
/*     */ 
/*     */     case 2: 
/* 436 */       if ((movingobjectposition.entityHit instanceof EntityLivingBase)) {
/* 437 */         ((EntityLivingBase)movingobjectposition.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 4));
/*     */       }
/*     */     
/*     */     case 5: 
/* 441 */       if ((this.type == 5) && ((movingobjectposition.entityHit instanceof EntityLivingBase))) {
/* 442 */         ((EntityLivingBase)movingobjectposition.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 100));
/*     */       }
/*     */       break;
/*     */     }
/* 446 */     if (this.shootingEntity == null)
/*     */     {
/* 448 */       damagesource = new EntityDamageSourceIndirect("arrow", this, this).setProjectile();
/*     */     }
/*     */     else
/*     */     {
/* 452 */       damagesource = new EntityDamageSourceIndirect("arrow", this, this.shootingEntity).setProjectile();
/*     */     }
/*     */     
/*     */     label471:
/*     */     
/* 457 */     if ((fire > 0) && (!(movingobjectposition.entityHit instanceof EntityEnderman)))
/*     */     {
/* 459 */       movingobjectposition.entityHit.setFire(fire);
/*     */     }
/*     */     
/*     */ 
/* 463 */     return movingobjectposition.entityHit.attackEntityFrom(damagesource, i1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDamage(double damageIn)
/*     */   {
/* 470 */     this.damage = damageIn;
/*     */   }
/*     */   
/*     */   public double getDamage()
/*     */   {
/* 475 */     switch (this.type) {
/* 476 */     case 3:  return this.damage * 1.5D;
/* 477 */     case 4:  return this.damage * 0.8D;
/* 478 */     case 5:  return this.damage * 0.8D;
/*     */     }
/* 480 */     return this.damage;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setKnockbackStrength(int knockbackStrengthIn)
/*     */   {
/* 486 */     this.knockbackStrength = knockbackStrengthIn;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 493 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 494 */     par1NBTTagCompound.setByte("type", (byte)this.type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 503 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 504 */     this.type = par1NBTTagCompound.getByte("type");
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityPrimalArrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */