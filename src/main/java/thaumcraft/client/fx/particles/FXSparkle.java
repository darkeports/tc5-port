/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ 
/*     */ public class FXSparkle extends Particle
/*     */ {
/*     */   public FXSparkle(World world, double d, double d1, double d2, float f, float f1, float f2, float f3, int m)
/*     */   {
/*  15 */     super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
/*  16 */     if (f1 == 0.0F) {
/*  17 */       f1 = 1.0F;
/*     */     }
/*  19 */     this.particleRed = f1;
/*  20 */     this.particleGreen = f2;
/*  21 */     this.particleBlue = f3;
/*  22 */     this.particleGravity = 0.0F;
/*  23 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/*  24 */     this.particleScale *= f;
/*  25 */     this.particleMaxAge = (3 * m);
/*  26 */     this.multiplier = m;
/*  27 */     this.canCollide = true;
/*  28 */     setSize(0.01F, 0.01F);
/*  29 */     this.prevPosX = this.posX;
/*  30 */     this.prevPosY = this.posY;
/*  31 */     this.prevPosZ = this.posZ;
/*     */   }
/*     */   
/*     */   public FXSparkle(World world, double d, double d1, double d2, float f, int type, int m) {
/*  35 */     this(world, d, d1, d2, f, 0.0F, 0.0F, 0.0F, m);
/*  36 */     this.currentColor = type;
/*  37 */     switch (type) {
/*     */     case 0: 
/*  39 */       this.particleRed = (0.75F + world.rand.nextFloat() * 0.25F);
/*  40 */       this.particleGreen = (0.25F + world.rand.nextFloat() * 0.25F);
/*  41 */       this.particleBlue = (0.75F + world.rand.nextFloat() * 0.25F);
/*  42 */       break;
/*     */     case 1: 
/*  44 */       this.particleRed = (0.5F + world.rand.nextFloat() * 0.3F);
/*  45 */       this.particleGreen = (0.5F + world.rand.nextFloat() * 0.3F);
/*  46 */       this.particleBlue = 0.2F;
/*  47 */       break;
/*     */     case 2: 
/*  49 */       this.particleRed = 0.2F;
/*  50 */       this.particleGreen = 0.2F;
/*  51 */       this.particleBlue = (0.7F + world.rand.nextFloat() * 0.3F);
/*  52 */       break;
/*     */     case 3: 
/*  54 */       this.particleRed = 0.2F;
/*  55 */       this.particleGreen = (0.7F + world.rand.nextFloat() * 0.3F);
/*  56 */       this.particleBlue = 0.2F;
/*  57 */       break;
/*     */     case 4: 
/*  59 */       this.particleRed = (0.7F + world.rand.nextFloat() * 0.3F);
/*  60 */       this.particleGreen = 0.2F;
/*  61 */       this.particleBlue = 0.2F;
/*  62 */       break;
/*     */     case 5: 
/*  64 */       this.blendmode = 771;
/*  65 */       this.particleRed = (world.rand.nextFloat() * 0.1F);
/*  66 */       this.particleGreen = (world.rand.nextFloat() * 0.1F);
/*  67 */       this.particleBlue = (world.rand.nextFloat() * 0.1F);
/*  68 */       break;
/*     */     case 6: 
/*  70 */       this.particleRed = (0.8F + world.rand.nextFloat() * 0.2F);
/*  71 */       this.particleGreen = (0.8F + world.rand.nextFloat() * 0.2F);
/*  72 */       this.particleBlue = (0.8F + world.rand.nextFloat() * 0.2F);
/*  73 */       break;
/*     */     case 7: 
/*  75 */       this.particleRed = 0.2F;
/*  76 */       this.particleGreen = (0.5F + world.rand.nextFloat() * 0.3F);
/*  77 */       this.particleBlue = (0.6F + world.rand.nextFloat() * 0.3F);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */   public FXSparkle(World world, double d, double d1, double d2, double x, double y, double z, float f, int type, int m)
/*     */   {
/*  85 */     this(world, d, d1, d2, f, type, m);
/*     */     
/*  87 */     double dx = x - this.posX;
/*  88 */     double dy = y - this.posY;
/*  89 */     double dz = z - this.posZ;
/*     */     
/*  91 */     this.motionX = (dx / this.particleMaxAge);
/*  92 */     this.motionY = (dy / this.particleMaxAge);
/*  93 */     this.motionZ = (dz / this.particleMaxAge);
/*     */   }
/*     */   
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  98 */     org.lwjgl.opengl.GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
/*  99 */     int part = this.particle + this.particleAge / this.multiplier;
/*     */     
/* 101 */     float var8 = part % 4 / 16.0F;
/* 102 */     float var9 = var8 + 0.0624375F;
/* 103 */     float var10 = 0.25F;
/* 104 */     float var11 = var10 + 0.0624375F;
/* 105 */     float var12 = 0.1F * this.particleScale;
/* 106 */     if (this.shrink)
/* 107 */       var12 *= (this.particleMaxAge - this.particleAge + 1) / this.particleMaxAge;
/* 108 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/* 109 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/* 110 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/* 111 */     float var16 = 1.0F;
/*     */     
/* 113 */     int i = 240;
/* 114 */     int j = i >> 16 & 0xFFFF;
/* 115 */     int k = i & 0xFFFF;
/*     */     
/* 117 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */     
/* 119 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */     
/* 121 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */     
/* 123 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFXLayer()
/*     */   {
/* 130 */     return this.blendmode == 1 ? 0 : 1;
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
/*     */   public void onUpdate()
/*     */   {
/* 145 */     this.prevPosX = this.posX;
/* 146 */     this.prevPosY = this.posY;
/* 147 */     this.prevPosZ = this.posZ;
/*     */     
/* 149 */     if ((this.particleAge == 0) && (this.tinkle) && (this.worldObj.rand.nextInt(10) == 0)) {
/* 150 */       this.worldObj.playSoundAtEntity(this, "random.orb", 0.02F, 0.7F * ((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.6F + 2.0F));
/*     */     }
/*     */     
/*     */ 
/* 154 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/* 156 */       setExpired();
/*     */     }
/*     */     
/* 159 */     this.motionY -= 0.04D * this.particleGravity;
/* 160 */     if (!this.noClip) {
/* 161 */       pushOutOfBlocks(this.posX, (getEntityBoundingBox().minY + getEntityBoundingBox().maxY) / 2.0D, this.posZ);
/*     */     }
/*     */     
/*     */ 
/* 165 */     this.posX += this.motionX;
/* 166 */     this.posY += this.motionY;
/* 167 */     this.posZ += this.motionZ;
/* 168 */     if (this.slowdown) {
/* 169 */       this.motionX *= 0.9080000019073486D;
/* 170 */       this.motionY *= 0.9080000019073486D;
/* 171 */       this.motionZ *= 0.9080000019073486D;
/* 172 */       if (this.onGround) {
/* 173 */         this.motionX *= 0.699999988079071D;
/* 174 */         this.motionZ *= 0.699999988079071D;
/*     */       }
/*     */     }
/*     */     
/* 178 */     if (this.leyLineEffect)
/*     */     {
/* 180 */       FXSparkle fx = new FXSparkle(this.worldObj, this.prevPosX + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F, this.prevPosY + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F, this.prevPosZ + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F, 1.0F, this.currentColor, 3 + this.worldObj.rand.nextInt(3));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 187 */       fx.noClip = true;
/* 188 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setGravity(float value)
/*     */   {
/* 194 */     this.particleGravity = value;
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
/* 290 */   public boolean leyLineEffect = false;
/* 291 */   public int multiplier = 2;
/* 292 */   public boolean shrink = true;
/* 293 */   public int particle = 16;
/* 294 */   public boolean tinkle = false;
/* 295 */   public int blendmode = 1;
/* 296 */   public boolean slowdown = true;
/* 297 */   public int currentColor = 0;
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXSparkle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */