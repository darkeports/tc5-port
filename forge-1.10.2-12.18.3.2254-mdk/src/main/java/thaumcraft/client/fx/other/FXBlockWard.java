/*     */ package thaumcraft.client.fx.other;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ 
/*     */ public class FXBlockWard
/*     */   extends Particle
/*     */ {
/*     */   public FXBlockWard(World world, double d, double d1, double d2, EnumFacing side, float f, float f1, float f2)
/*     */   {
/*  23 */     super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */     
/*  25 */     this.side = side;
/*     */     
/*  27 */     this.particleGravity = 0.0F;
/*  28 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/*  29 */     this.particleMaxAge = (12 + this.rand.nextInt(5));
/*  30 */     this.canCollide = true;
/*  31 */     setSize(0.01F, 0.01F);
/*  32 */     this.prevPosX = this.posX;
/*  33 */     this.prevPosY = this.posY;
/*  34 */     this.prevPosZ = this.posZ;
/*  35 */     this.canCollide = false;
/*  36 */     this.particleScale = ((float)(1.4D + this.rand.nextGaussian() * 0.30000001192092896D));
/*  37 */     this.rotation = this.rand.nextInt(360);
/*  38 */     this.sx = MathHelper.clamp_float(f - 0.6F + this.rand.nextFloat() * 0.2F, -0.4F, 0.4F);
/*  39 */     this.sy = MathHelper.clamp_float(f1 - 0.6F + this.rand.nextFloat() * 0.2F, -0.4F, 0.4F);
/*  40 */     this.sz = MathHelper.clamp_float(f2 - 0.6F + this.rand.nextFloat() * 0.2F, -0.4F, 0.4F);
/*  41 */     if (side.getFrontOffsetX() != 0) this.sx = 0.0F;
/*  42 */     if (side.getFrontOffsetY() != 0) this.sy = 0.0F;
/*  43 */     if (side.getFrontOffsetZ() != 0) this.sz = 0.0F;
/*  44 */     for (int a = 0; a < 15; a++) {
/*  45 */       this.tex1[a] = new ResourceLocation("thaumcraft", "textures/models/hemis" + (a + 1) + ".png");
/*     */     }
/*     */   }
/*     */   
/*  49 */   ResourceLocation[] tex1 = new ResourceLocation[15];
/*     */   
/*     */   EnumFacing side;
/*  52 */   int rotation = 0;
/*  53 */   float sx = 0.0F;
/*  54 */   float sy = 0.0F;
/*  55 */   float sz = 0.0F;
/*     */   
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity p_180434_2_, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  61 */     Tessellator.getInstance().draw();
/*  62 */     GL11.glPushMatrix();
/*  63 */     float fade = (this.particleAge + f) / this.particleMaxAge;
/*  64 */     int frame = Math.min(15, (int)(15.0F * fade));
/*  65 */     Minecraft.getMinecraft().renderEngine.bindTexture(this.tex1[(frame - 1)]);
/*     */     
/*  67 */     GL11.glDepthMask(false);
/*  68 */     GL11.glEnable(3042);
/*  69 */     GL11.glBlendFunc(770, 1);
/*     */     
/*  71 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, this.particleAlpha / 2.0F);
/*     */     
/*  73 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/*  74 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/*  75 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*     */     
/*  77 */     GL11.glTranslated(var13 + this.sx, var14 + this.sy, var15 + this.sz);
/*     */     
/*  79 */     GL11.glRotatef(90.0F, this.side.getFrontOffsetY(), -this.side.getFrontOffsetX(), this.side.getFrontOffsetZ());
/*  80 */     GL11.glRotatef(this.rotation, 0.0F, 0.0F, 1.0F);
/*  81 */     if (this.side.getFrontOffsetZ() > 0) {
/*  82 */       GL11.glTranslated(0.0D, 0.0D, 0.5049999952316284D);
/*  83 */       GL11.glRotatef(180.0F, 0.0F, -1.0F, 0.0F);
/*     */     } else {
/*  85 */       GL11.glTranslated(0.0D, 0.0D, -0.5049999952316284D);
/*     */     }
/*     */     
/*  88 */     float var12 = this.particleScale;
/*     */     
/*  90 */     float var16 = 1.0F;
/*     */     
/*  92 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*  93 */     int i = 240;
/*  94 */     int j = i >> 16 & 0xFFFF;
/*  95 */     int k = i & 0xFFFF;
/*  96 */     wr.pos(-0.5D * var12, 0.5D * var12, 0.0D).tex(0.0D, 1.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/*  97 */     wr.pos(0.5D * var12, 0.5D * var12, 0.0D).tex(1.0D, 1.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/*  98 */     wr.pos(0.5D * var12, -0.5D * var12, 0.0D).tex(1.0D, 0.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/*  99 */     wr.pos(-0.5D * var12, -0.5D * var12, 0.0D).tex(0.0D, 0.0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, this.particleAlpha / 2.0F).lightmap(j, k).endVertex();
/* 100 */     Tessellator.getInstance().draw();
/*     */     
/* 102 */     GL11.glDisable(3042);
/* 103 */     GL11.glDepthMask(true);
/*     */     
/* 105 */     GL11.glPopMatrix();
/* 106 */     Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getMCParticleTexture());
/* 107 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 114 */     this.prevPosX = this.posX;
/* 115 */     this.prevPosY = this.posY;
/* 116 */     this.prevPosZ = this.posZ;
/* 117 */     float threshold = this.particleMaxAge / 5.0F;
/* 118 */     if (this.particleAge <= threshold) {
/* 119 */       this.particleAlpha = (this.particleAge / threshold);
/*     */     } else {
/* 121 */       this.particleAlpha = ((this.particleMaxAge - this.particleAge) / this.particleMaxAge);
/*     */     }
/* 123 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/* 125 */       setExpired();
/*     */     }
/*     */     
/* 128 */     this.motionY -= 0.04D * this.particleGravity;
/*     */     
/* 130 */     this.posX += this.motionX;
/* 131 */     this.posY += this.motionY;
/* 132 */     this.posZ += this.motionZ;
/*     */   }
/*     */   
/*     */   public void setGravity(float value)
/*     */   {
/* 137 */     this.particleGravity = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\other\FXBlockWard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */