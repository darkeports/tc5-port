/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ 
/*     */ public class FXWisp extends Particle
/*     */ {
/*     */   public FXWisp(World world, double d, double d1, double d2, float f, float f1, float f2)
/*     */   {
/*  16 */     this(world, d, d1, d2, 1.0F, f, f1, f2);
/*     */   }
/*     */   
/*     */ 
/*  20 */   Entity target = null;
/*     */   
/*     */   public FXWisp(World world, double d, double d1, double d2, float f, float red, float green, float blue)
/*     */   {
/*  24 */     super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
/*  25 */     if (red == 0.0F) {
/*  26 */       red = 1.0F;
/*     */     }
/*  28 */     this.particleRed = red;
/*  29 */     this.particleGreen = green;
/*  30 */     this.particleBlue = blue;
/*  31 */     this.particleGravity = 0.0F;
/*  32 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/*  33 */     this.particleScale *= f;
/*  34 */     this.moteParticleScale = this.particleScale;
/*  35 */     this.particleMaxAge = ((int)(36.0D / (Math.random() * 0.3D + 0.7D)));
/*  36 */     this.moteHalfLife = (this.particleMaxAge / 2);
/*  37 */     this.canCollide = true;
/*  38 */     setSize(0.1F, 0.1F);
/*  39 */     Entity renderentity = FMLClientHandler.instance().getClient().getRenderViewEntity();
/*  40 */     int visibleDistance = 50;
/*  41 */     if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics)
/*  42 */       visibleDistance = 25;
/*  43 */     if (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance)
/*  44 */       this.particleMaxAge = 0;
/*  45 */     this.prevPosX = this.posX;
/*  46 */     this.prevPosY = this.posY;
/*  47 */     this.prevPosZ = this.posZ;
/*     */   }
/*     */   
/*     */   public FXWisp(World world, double d, double d1, double d2, float f, int type) {
/*  51 */     this(world, d, d1, d2, f, 0.0F, 0.0F, 0.0F);
/*     */     
/*  53 */     switch (type) {
/*     */     case 0: 
/*  55 */       this.particleRed = (0.75F + world.rand.nextFloat() * 0.25F);
/*  56 */       this.particleGreen = (0.25F + world.rand.nextFloat() * 0.25F);
/*  57 */       this.particleBlue = (0.75F + world.rand.nextFloat() * 0.25F);
/*  58 */       break;
/*     */     case 1: 
/*  60 */       this.particleRed = (0.5F + world.rand.nextFloat() * 0.3F);
/*  61 */       this.particleGreen = (0.5F + world.rand.nextFloat() * 0.3F);
/*  62 */       this.particleBlue = 0.2F;
/*  63 */       break;
/*     */     case 2: 
/*  65 */       this.particleRed = 0.2F;
/*  66 */       this.particleGreen = 0.2F;
/*  67 */       this.particleBlue = (0.7F + world.rand.nextFloat() * 0.3F);
/*  68 */       break;
/*     */     case 3: 
/*  70 */       this.particleRed = 0.2F;
/*  71 */       this.particleGreen = (0.7F + world.rand.nextFloat() * 0.3F);
/*  72 */       this.particleBlue = 0.2F;
/*  73 */       break;
/*     */     case 4: 
/*  75 */       this.particleRed = (0.7F + world.rand.nextFloat() * 0.3F);
/*  76 */       this.particleGreen = 0.2F;
/*  77 */       this.particleBlue = 0.2F;
/*  78 */       break;
/*     */     case 5: 
/*  80 */       this.blendmode = 771;
/*  81 */       this.particleRed = (world.rand.nextFloat() * 0.1F);
/*  82 */       this.particleGreen = (world.rand.nextFloat() * 0.1F);
/*  83 */       this.particleBlue = (world.rand.nextFloat() * 0.1F);
/*  84 */       break;
/*     */     case 6: 
/*  86 */       this.particleRed = (0.8F + world.rand.nextFloat() * 0.2F);
/*  87 */       this.particleGreen = (0.8F + world.rand.nextFloat() * 0.2F);
/*  88 */       this.particleBlue = (0.8F + world.rand.nextFloat() * 0.2F);
/*  89 */       break;
/*     */     case 7: 
/*  91 */       this.particleRed = (0.7F + world.rand.nextFloat() * 0.3F);
/*  92 */       this.particleGreen = (0.5F + world.rand.nextFloat() * 0.2F);
/*  93 */       this.particleBlue = (0.3F + world.rand.nextFloat() * 0.1F);
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */   public FXWisp(World world, double d, double d1, double d2, double x, double y, double z, float f, int type)
/*     */   {
/* 101 */     this(world, d, d1, d2, f, type);
/* 102 */     if (this.particleMaxAge > 0) {
/* 103 */       double dx = x - this.posX;
/* 104 */       double dy = y - this.posY;
/* 105 */       double dz = z - this.posZ;
/*     */       
/* 107 */       this.motionX = (dx / this.particleMaxAge);
/* 108 */       this.motionY = (dy / this.particleMaxAge);
/* 109 */       this.motionZ = (dz / this.particleMaxAge);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public FXWisp(World world, double d, double d1, double d2, Entity tar, int type)
/*     */   {
/* 116 */     this(world, d, d1, d2, 0.4F, type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public FXWisp(World world, double d, double d1, double d2, double x, double y, double z, float f, float red, float green, float blue)
/*     */   {
/* 123 */     this(world, d, d1, d2, f, red, green, blue);
/* 124 */     if (this.particleMaxAge > 0) {
/* 125 */       double dx = x - this.posX;
/* 126 */       double dy = y - this.posY;
/* 127 */       double dz = z - this.posZ;
/*     */       
/* 129 */       this.motionX = (dx / this.particleMaxAge);
/* 130 */       this.motionY = (dy / this.particleMaxAge);
/* 131 */       this.motionZ = (dz / this.particleMaxAge);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 139 */     float agescale = 0.0F;
/* 140 */     if (this.shrink) {
/* 141 */       agescale = (this.particleMaxAge - this.particleAge) / this.particleMaxAge;
/*     */     }
/*     */     else {
/* 144 */       agescale = this.particleAge / this.moteHalfLife;
/* 145 */       if (agescale > 1.0F) {
/* 146 */         agescale = 2.0F - agescale;
/*     */       }
/*     */     }
/* 149 */     this.particleScale = (this.moteParticleScale * agescale);
/*     */     
/* 151 */     org.lwjgl.opengl.GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
/*     */     
/* 153 */     float f10 = 0.5F * this.particleScale;
/* 154 */     float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/* 155 */     float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/* 156 */     float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*     */     
/* 158 */     float var8 = 0.0F;
/* 159 */     float var9 = 0.125F;
/* 160 */     float var10 = 0.875F;
/* 161 */     float var11 = 1.0F;
/*     */     
/* 163 */     int i = 240;
/* 164 */     int j = i >> 16 & 0xFFFF;
/* 165 */     int k = i & 0xFFFF;
/*     */     
/*     */ 
/* 168 */     wr.pos(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10).tex(var9, var11).color(this.particleRed, this.particleGreen, this.particleBlue, 0.5F).lightmap(j, k).endVertex();
/* 169 */     wr.pos(f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10).tex(var9, var10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.5F).lightmap(j, k).endVertex();
/* 170 */     wr.pos(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10).tex(var8, var10).color(this.particleRed, this.particleGreen, this.particleBlue, 0.5F).lightmap(j, k).endVertex();
/* 171 */     wr.pos(f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10).tex(var8, var11).color(this.particleRed, this.particleGreen, this.particleBlue, 0.5F).lightmap(j, k).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */   public int getFXLayer()
/*     */   {
/* 177 */     return this.blendmode == 1 ? 0 : 1;
/*     */   }
/*     */   
/*     */   public void onUpdate()
/*     */   {
/* 182 */     this.prevPosX = this.posX;
/* 183 */     this.prevPosY = this.posY;
/* 184 */     this.prevPosZ = this.posZ;
/*     */     
/* 186 */     if ((this.particleAge == 0) && (this.tinkle) && (this.worldObj.rand.nextInt(3) == 0)) {
/* 187 */       this.worldObj.playSoundAtEntity(this, "random.orb", 0.02F, 0.5F * ((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.6F + 2.0F));
/*     */     }
/*     */     
/*     */ 
/* 191 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/* 193 */       setExpired();
/*     */     }
/*     */     
/* 196 */     this.motionY -= 0.04D * this.particleGravity;
/*     */     
/* 198 */     if (!this.noClip)
/* 199 */       pushOutOfBlocks(this.posX, this.posY, this.posZ);
/* 200 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/* 202 */     if (this.target != null)
/*     */     {
/*     */ 
/* 205 */       this.motionX *= 0.985D;
/* 206 */       this.motionY *= 0.985D;
/* 207 */       this.motionZ *= 0.985D;
/*     */       
/* 209 */       double dx = this.target.posX - this.posX;
/* 210 */       double dy = this.target.posY + this.target.height / 2.0F - this.posY;
/* 211 */       double dz = this.target.posZ - this.posZ;
/* 212 */       double d13 = 0.2D;
/* 213 */       double d11 = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
/*     */       
/* 215 */       dx /= d11;
/* 216 */       dy /= d11;
/* 217 */       dz /= d11;
/*     */       
/* 219 */       this.motionX += dx * d13;
/* 220 */       this.motionY += dy * d13;
/* 221 */       this.motionZ += dz * d13;
/*     */       
/* 223 */       this.motionX = MathHelper.clamp_float((float)this.motionX, -0.2F, 0.2F);
/* 224 */       this.motionY = MathHelper.clamp_float((float)this.motionY, -0.2F, 0.2F);
/* 225 */       this.motionZ = MathHelper.clamp_float((float)this.motionZ, -0.2F, 0.2F);
/*     */     }
/*     */     else {
/* 228 */       this.motionX *= 0.9800000190734863D;
/* 229 */       this.motionY *= 0.9800000190734863D;
/* 230 */       this.motionZ *= 0.9800000190734863D;
/* 231 */       if (this.onGround) {
/* 232 */         this.motionX *= 0.699999988079071D;
/* 233 */         this.motionZ *= 0.699999988079071D;
/*     */       }
/*     */     }
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
/*     */   public void setGravity(float value)
/*     */   {
/* 335 */     this.particleGravity = value;
/*     */   }
/*     */   
/* 338 */   public boolean shrink = false;
/*     */   float moteParticleScale;
/*     */   int moteHalfLife;
/* 341 */   public boolean tinkle = false;
/* 342 */   public int blendmode = 1;
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXWisp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */