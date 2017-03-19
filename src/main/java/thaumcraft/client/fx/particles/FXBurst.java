/*     */ package thaumcraft.client.fx.particles;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.client.renderers.entity.RenderAuraNode;
/*     */ 
/*     */ 
/*     */ public class FXBurst
/*     */   extends Particle
/*     */ {
/*     */   public FXBurst(World world, double d, double d1, double d2, float f)
/*     */   {
/*  21 */     super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */     
/*  23 */     this.particleRed = 1.0F;
/*  24 */     this.particleGreen = 1.0F;
/*  25 */     this.particleBlue = 1.0F;
/*  26 */     this.particleGravity = 0.0F;
/*  27 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/*  28 */     this.particleScale *= f;
/*  29 */     this.particleMaxAge = 31;
/*  30 */     this.canCollide = true;
/*  31 */     setSize(0.01F, 0.01F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void renderParticle(VertexBuffer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  40 */     Tessellator.getInstance().draw();
/*  41 */     GL11.glPushMatrix();
/*     */     
/*     */ 
/*  44 */     GL11.glDepthMask(false);
/*  45 */     GL11.glEnable(3042);
/*  46 */     GL11.glBlendFunc(770, 1);
/*     */     
/*  48 */     Minecraft.getMinecraft().renderEngine.bindTexture(RenderAuraNode.texture);
/*     */     
/*  50 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
/*     */     
/*  52 */     float var8 = this.particleAge % 32 / 32.0F;
/*  53 */     float var9 = var8 + 0.03125F;
/*  54 */     float var10 = 0.96875F;
/*  55 */     float var11 = 1.0F;
/*  56 */     float var12 = this.particleScale;
/*     */     
/*  58 */     float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/*  59 */     float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/*  60 */     float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*  61 */     float var16 = 1.0F;
/*     */     
/*  63 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*  64 */     int i = 240;
/*  65 */     int j = i >> 16 & 0xFFFF;
/*  66 */     int k = i & 0xFFFF;
/*  67 */     wr.pos(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */     
/*  69 */     wr.pos(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */     
/*  71 */     wr.pos(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */     
/*  73 */     wr.pos(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
/*     */     
/*     */ 
/*  76 */     Tessellator.getInstance().draw();
/*  77 */     GL11.glBlendFunc(770, 771);
/*  78 */     GL11.glDisable(3042);
/*  79 */     GL11.glDepthMask(true);
/*     */     
/*  81 */     GL11.glPopMatrix();
/*  82 */     Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getMCParticleTexture());
/*  83 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  90 */     this.prevPosX = this.posX;
/*  91 */     this.prevPosY = this.posY;
/*  92 */     this.prevPosZ = this.posZ;
/*     */     
/*  94 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/*  96 */       setExpired();
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
/*     */   public void setGravity(float value)
/*     */   {
/* 109 */     this.particleGravity = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\particles\FXBurst.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */