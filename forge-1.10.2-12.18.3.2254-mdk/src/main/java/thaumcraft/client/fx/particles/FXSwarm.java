/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class FXSwarm extends Particle
/*     */ {
/*     */   private Entity target;
/*  16 */   private float turnSpeed = 10.0F;
/*  17 */   private float speed = 0.2F;
/*     */   
/*     */   public FXSwarm(World par1World, double x, double y, double z, Entity target, float r, float g, float b)
/*     */   {
/*  21 */     super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
/*  22 */     this.particleRed = r;
/*  23 */     this.particleGreen = g;
/*  24 */     this.particleBlue = b;
/*  25 */     this.particleScale = (this.rand.nextFloat() * 0.5F + 1.0F);
/*     */     
/*  27 */     this.target = target;
/*     */     
/*  29 */     float f3 = 0.2F;
/*  30 */     this.motionX = ((this.rand.nextFloat() - this.rand.nextFloat()) * f3);
/*  31 */     this.motionY = ((this.rand.nextFloat() - this.rand.nextFloat()) * f3);
/*  32 */     this.motionZ = ((this.rand.nextFloat() - this.rand.nextFloat()) * f3);
/*     */     
/*  34 */     this.particleGravity = 0.1F;
/*  35 */     this.canCollide = true;
/*     */   }
/*     */   
/*     */ 
/*     */   public FXSwarm(World par1World, double x, double y, double z, Entity target, float r, float g, float b, float sp, float ts, float pg)
/*     */   {
/*  41 */     this(par1World, x, y, z, target, r, g, b);
/*  42 */     this.speed = sp;
/*     */     
/*  44 */     this.particleGravity = pg;
/*     */   }
/*     */   
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  50 */     float bob = MathHelper.sin(this.particleAge / 3.0F) * 0.25F + 1.0F;
/*     */     
/*  52 */     org.lwjgl.opengl.GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
/*  53 */     int part = 7 + this.particleAge % 8;
/*     */     
/*  55 */     float var8 = part / 16.0F;
/*  56 */     float var9 = var8 + 0.0624375F;
/*  57 */     float var10 = 0.25F;
/*  58 */     float var11 = var10 + 0.0624375F;
/*  59 */     float var12 = 0.1F * this.particleScale * bob;
/*     */     
/*  61 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/*     */     
/*  63 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/*     */     
/*  65 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*     */     
/*  67 */     float var16 = 1.0F;
/*     */     
/*  69 */     float trans = (50.0F - this.deathtimer) / 50.0F;
/*     */     
/*  71 */     int i = 240;
/*  72 */     int j = i >> 16 & 0xFFFF;
/*  73 */     int k = i & 0xFFFF;
/*  74 */     float dd = 1.0F;
/*  75 */     if (((this.target instanceof EntityLivingBase)) && (((EntityLivingBase)this.target).hurtTime > 0)) { dd = 2.0F;
/*     */     }
/*  77 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16 / dd, this.particleBlue * var16 / dd, trans).lightmap(j, k).endVertex();
/*     */     
/*  79 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16 / dd, this.particleBlue * var16 / dd, trans).lightmap(j, k).endVertex();
/*     */     
/*  81 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16 / dd, this.particleBlue * var16 / dd, trans).lightmap(j, k).endVertex();
/*     */     
/*  83 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16 / dd, this.particleBlue * var16 / dd, trans).lightmap(j, k).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFXLayer()
/*     */   {
/*  90 */     return 1;
/*     */   }
/*     */   
/*  93 */   int deathtimer = 0;
/*     */   
/*     */   public void onUpdate() {
/*  96 */     this.prevPosX = this.posX;
/*  97 */     this.prevPosY = this.posY;
/*  98 */     this.prevPosZ = this.posZ;
/*     */     
/* 100 */     this.particleAge += 1;
/*     */     
/* 102 */     if ((this.target == null) || (this.target.isDead) || (((this.target instanceof EntityLivingBase)) && (((EntityLivingBase)this.target).deathTime > 0)))
/*     */     {
/*     */ 
/* 105 */       this.deathtimer += 1;
/* 106 */       this.motionX *= 0.9D;
/* 107 */       this.motionZ *= 0.9D;
/* 108 */       this.motionY -= this.particleGravity / 2.0F;
/* 109 */       if (this.deathtimer > 50) {
/* 110 */         setExpired();
/*     */       }
/*     */     } else {
/* 113 */       this.motionY += this.particleGravity;
/*     */     }
/*     */     
/* 116 */     pushOutOfBlocks(this.posX, this.posY, this.posZ);
/* 117 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/* 119 */     this.motionX *= 0.985D;
/* 120 */     this.motionY *= 0.985D;
/* 121 */     this.motionZ *= 0.985D;
/*     */     
/* 123 */     if ((this.target != null) && (!this.target.isDead) && ((!(this.target instanceof EntityLivingBase)) || (((EntityLivingBase)this.target).deathTime <= 0)))
/*     */     {
/*     */ 
/* 126 */       boolean hurt = false;
/*     */       
/* 128 */       if ((this.target instanceof EntityLivingBase))
/* 129 */         hurt = ((EntityLivingBase)this.target).hurtTime > 0;
/* 130 */       if ((getDistanceSqToEntity(this.target) > this.target.width) && (!hurt)) {
/* 131 */         faceEntity(this.target, this.turnSpeed / 2.0F + this.rand.nextInt((int)(this.turnSpeed / 2.0F)), this.turnSpeed / 2.0F + this.rand.nextInt((int)(this.turnSpeed / 2.0F)));
/*     */       }
/*     */       else
/*     */       {
/* 135 */         faceEntity(this.target, -(this.turnSpeed / 2.0F + this.rand.nextInt((int)(this.turnSpeed / 2.0F))), -(this.turnSpeed / 2.0F + this.rand.nextInt((int)(this.turnSpeed / 2.0F))));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 141 */       this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/*     */       
/*     */ 
/* 144 */       this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/*     */       
/*     */ 
/* 147 */       this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F));
/*     */       
/* 149 */       setHeading(this.motionX, this.motionY, this.motionZ, this.speed, 15.0F);
/*     */     }
/*     */     
/* 152 */     if ((buzzcount.size() < 3) && (this.rand.nextInt(50) == 0) && (this.worldObj.getClosestPlayerToEntity(this, 8.0D) != null))
/*     */     {
/* 154 */       this.worldObj.playSound(this.posX, this.posY, this.posZ, "thaumcraft:fly", 0.03F, 0.5F + this.rand.nextFloat() * 0.4F, false);
/*     */       
/* 156 */       buzzcount.add(Long.valueOf(System.nanoTime() + 1500000L));
/*     */     }
/*     */     
/* 159 */     if ((buzzcount.size() >= 3) && (((Long)buzzcount.get(0)).longValue() < System.nanoTime()))
/* 160 */       buzzcount.remove(0);
/*     */   }
/*     */   
/* 163 */   private static ArrayList<Long> buzzcount = new ArrayList();
/*     */   
/*     */   public void faceEntity(Entity par1Entity, float par2, float par3) {
/* 166 */     double d0 = par1Entity.posX - this.posX;
/* 167 */     double d1 = par1Entity.posZ - this.posZ;
/* 168 */     double d2 = (par1Entity.getEntityBoundingBox().minY + par1Entity.getEntityBoundingBox().maxY) / 2.0D - (getEntityBoundingBox().minY + getEntityBoundingBox().maxY) / 2.0D;
/*     */     
/*     */ 
/* 171 */     double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
/* 172 */     float f2 = (float)(Math.atan2(d1, d0) * 180.0D / 3.141592653589793D) - 90.0F;
/* 173 */     float f3 = (float)-(Math.atan2(d2, d3) * 180.0D / 3.141592653589793D);
/* 174 */     this.rotationPitch = updateRotation(this.rotationPitch, f3, par3);
/* 175 */     this.rotationYaw = updateRotation(this.rotationYaw, f2, par2);
/*     */   }
/*     */   
/*     */   private float updateRotation(float par1, float par2, float par3) {
/* 179 */     float f3 = MathHelper.wrapAngleTo180_float(par2 - par1);
/*     */     
/* 181 */     if (f3 > par3) {
/* 182 */       f3 = par3;
/*     */     }
/*     */     
/* 185 */     if (f3 < -par3) {
/* 186 */       f3 = -par3;
/*     */     }
/*     */     
/* 189 */     return par1 + f3;
/*     */   }
/*     */   
/*     */   public void setHeading(double par1, double par3, double par5, float par7, float par8)
/*     */   {
/* 194 */     float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
/*     */     
/* 196 */     par1 /= f2;
/* 197 */     par3 /= f2;
/* 198 */     par5 /= f2;
/* 199 */     par1 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/*     */     
/*     */ 
/* 202 */     par3 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/*     */     
/*     */ 
/* 205 */     par5 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
/*     */     
/*     */ 
/* 208 */     par1 *= par7;
/* 209 */     par3 *= par7;
/* 210 */     par5 *= par7;
/* 211 */     this.motionX = par1;
/* 212 */     this.motionY = par3;
/* 213 */     this.motionZ = par5;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 316 */   public int particle = 40;
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXSwarm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */