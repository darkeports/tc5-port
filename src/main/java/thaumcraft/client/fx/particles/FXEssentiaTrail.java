/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.common.blocks.devices.BlockEssentiaTransport;
/*     */ import thaumcraft.common.blocks.devices.BlockJar;
/*     */ 
/*     */ public class FXEssentiaTrail extends Particle
/*     */ {
/*     */   private double targetX;
/*     */   private double targetY;
/*     */   private double targetZ;
/*  25 */   private int count = 0;
/*     */   
/*     */ 
/*     */   public FXEssentiaTrail(World w, double par2, double par4, double par6, double tx, double ty, double tz, int count, int color, float scale)
/*     */   {
/*  30 */     super(w, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/*  31 */     this.particleRed = (this.particleGreen = this.particleBlue = 0.6F);
/*  32 */     this.particleScale = ((MathHelper.sin(count / 2.0F) * 0.1F + 1.0F) * scale);
/*     */     
/*  34 */     this.count = count;
/*  35 */     this.targetX = tx;
/*  36 */     this.targetY = ty;
/*  37 */     this.targetZ = tz;
/*  38 */     IBlockState bs = w.getBlockState(new BlockPos(this.posX, this.posY, this.posZ));
/*  39 */     if ((bs.getBlock() instanceof BlockJar)) {
/*  40 */       this.posY += 0.33000001311302185D;
/*     */     }
/*     */     
/*  43 */     if ((bs.getBlock() instanceof BlockEssentiaTransport)) {
/*  44 */       EnumFacing f = thaumcraft.common.lib.utils.BlockStateUtils.getFacing(bs);
/*  45 */       this.posX += f.getFrontOffsetX() * 0.05F;
/*  46 */       this.posY += f.getFrontOffsetY() * 0.05F;
/*  47 */       this.posZ += f.getFrontOffsetZ() * 0.05F;
/*     */     }
/*     */     
/*  50 */     double dx = tx - this.posX;
/*  51 */     double dy = ty - this.posY;
/*  52 */     double dz = tz - this.posZ;
/*  53 */     int base = (int)(MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz) * 30.0F);
/*  54 */     if (base < 1)
/*  55 */       base = 1;
/*  56 */     this.particleMaxAge = (base / 2 + this.rand.nextInt(base));
/*     */     
/*  58 */     this.motionX = (MathHelper.sin(count / 4.0F) * 0.015F + this.rand.nextGaussian() * 0.0020000000949949026D);
/*  59 */     this.motionY = (MathHelper.sin(count / 3.0F) * 0.015F + this.rand.nextGaussian() * 0.0020000000949949026D);
/*  60 */     this.motionZ = (MathHelper.sin(count / 2.0F) * 0.015F + this.rand.nextGaussian() * 0.0020000000949949026D);
/*     */     
/*  62 */     Color c = new Color(color);
/*  63 */     float mr = c.getRed() / 255.0F * 0.2F;
/*  64 */     float mg = c.getGreen() / 255.0F * 0.2F;
/*  65 */     float mb = c.getBlue() / 255.0F * 0.2F;
/*  66 */     this.particleRed = (c.getRed() / 255.0F - mr + this.rand.nextFloat() * mr);
/*  67 */     this.particleGreen = (c.getGreen() / 255.0F - mg + this.rand.nextFloat() * mg);
/*  68 */     this.particleBlue = (c.getBlue() / 255.0F - mb + this.rand.nextFloat() * mb);
/*     */     
/*  70 */     this.particleGravity = 0.2F;
/*  71 */     this.canCollide = true;
/*     */     try
/*     */     {
/*  74 */       Entity renderentity = FMLClientHandler.instance().getClient().getRenderViewEntity();
/*  75 */       int visibleDistance = 64;
/*  76 */       if (!FMLClientHandler.instance().getClient().gameSettings.fancyGraphics) visibleDistance = 32;
/*  77 */       if (renderentity.getDistance(this.posX, this.posY, this.posZ) > visibleDistance) { this.particleMaxAge = 0;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  87 */     float t2 = 0.5625F;
/*  88 */     float t3 = 0.625F;
/*  89 */     float t4 = 0.0625F;
/*  90 */     float t5 = 0.125F;
/*     */     
/*  92 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
/*  93 */     int part = this.particle + this.particleAge % 16;
/*     */     
/*  95 */     float s = MathHelper.sin((this.particleAge - this.count) / 5.0F) * 0.25F + 1.0F;
/*     */     
/*  97 */     float var12 = 0.1F * this.particleScale * s;
/*     */     
/*  99 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/* 100 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/* 101 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/* 102 */     float var16 = 1.0F;
/*     */     
/* 104 */     int i = 240;
/* 105 */     int j = i >> 16 & 0xFFFF;
/* 106 */     int k = i & 0xFFFF;
/*     */     
/* 108 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(t2, t5).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.5F).lightmap(j, k).endVertex();
/*     */     
/* 110 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(t3, t5).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.5F).lightmap(j, k).endVertex();
/*     */     
/* 112 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(t3, t4).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.5F).lightmap(j, k).endVertex();
/*     */     
/* 114 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(t2, t4).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 0.5F).lightmap(j, k).endVertex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFXLayer()
/*     */   {
/* 121 */     return 1;
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 125 */     this.prevPosX = this.posX;
/* 126 */     this.prevPosY = this.posY;
/* 127 */     this.prevPosZ = this.posZ;
/*     */     
/* 129 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 130 */       setExpired();
/* 131 */       return;
/*     */     }
/*     */     //TODO:???
/* 134 */     this.motionY += 0.01D * this.particleGravity;
/* 135 */     if (this.canCollide)
/* 136 */       pushOutOfBlocks(this.posX, this.posY, this.posZ);
/* 137 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/* 139 */     this.motionX *= 0.985D;
/* 140 */     this.motionY *= 0.985D;
/* 141 */     this.motionZ *= 0.985D;
/*     */     
/* 143 */     this.motionX = MathHelper.clamp_float((float)this.motionX, -0.05F, 0.05F);
/* 144 */     this.motionY = MathHelper.clamp_float((float)this.motionY, -0.05F, 0.05F);
/* 145 */     this.motionZ = MathHelper.clamp_float((float)this.motionZ, -0.05F, 0.05F);
/*     */     
/* 147 */     double dx = this.targetX - this.posX;
/* 148 */     double dy = this.targetY - this.posY;
/* 149 */     double dz = this.targetZ - this.posZ;
/* 150 */     double d13 = 0.01D;
/* 151 */     double d11 = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
/*     */     
/* 153 */     if (d11 < 2.0D) {
/* 154 */       this.particleScale *= 0.98F;
/*     */     }
/*     */     
/* 157 */     if (this.particleScale < 0.2F) {
/* 158 */       setExpired();
/* 159 */       return;
/*     */     }
/*     */     
/* 162 */     dx /= d11;
/* 163 */     dy /= d11;
/* 164 */     dz /= d11;
/*     */     
/* 166 */     this.motionX += dx * (d13 / Math.min(1.0D, d11));
/* 167 */     this.motionY += dy * (d13 / Math.min(1.0D, d11));
/* 168 */     this.motionZ += dz * (d13 / Math.min(1.0D, d11));
/*     */   }
/*     */   
/*     */   public void setGravity(float value)
/*     */   {
/* 173 */     this.particleGravity = value;
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
/* 271 */   public int particle = 24;
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXEssentiaTrail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */