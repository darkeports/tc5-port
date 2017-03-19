/*     */ package thaumcraft.client.fx.other;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.VertexBuffer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.client.lib.models.AdvancedModelLoader;
/*     */ import thaumcraft.client.lib.models.IModelCustom;
/*     */ 
/*     */ public class FXSonic extends Particle
/*     */ {
/*     */   public FXSonic(World world, double d, double d1, double d2, Entity target, int age)
/*     */   {
/*  23 */     super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */     
/*  25 */     this.particleRed = 1.0F;
/*  26 */     this.particleGreen = 1.0F;
/*  27 */     this.particleBlue = 1.0F;
/*  28 */     this.particleGravity = 0.0F;
/*  29 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/*     */     
/*  31 */     this.particleMaxAge = (age + this.rand.nextInt(age / 2));
/*     */     
/*  33 */     this.canCollide = true;
/*     */     
/*  35 */     setSize(0.01F, 0.01F);
/*     */     
/*  37 */     this.canCollide = false;
/*     */     
/*  39 */     this.particleScale = 1.0F;
/*     */     
/*  41 */     this.target = target;
/*  42 */     this.yaw = target.getRotationYawHead();
/*  43 */     this.pitch = target.rotationPitch;
/*     */     
/*  45 */     this.prevPosX = (this.posX = target.posX);
/*  46 */     this.prevPosY = (this.posY = target.posY + target.getEyeHeight());
/*  47 */     this.prevPosZ = (this.posZ = target.posZ);
/*     */   }
/*     */   
/*     */ 
/*  51 */   Entity target = null;
/*  52 */   float yaw = 0.0F;
/*  53 */   float pitch = 0.0F;
/*     */   
/*     */   private IModelCustom model;
/*  56 */   private static final ResourceLocation MODEL = new ResourceLocation("thaumcraft", "models/obj/hemis.obj");
/*     */   
/*     */   public void renderParticle(VertexBuffer wr, Entity p_180434_2_, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  60 */     Tessellator.getInstance().draw();
/*  61 */     GL11.glPushMatrix();
/*  62 */     GL11.glDepthMask(false);
/*  63 */     GL11.glEnable(3042);
/*  64 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  66 */     if (this.model == null) {
/*  67 */       this.model = AdvancedModelLoader.loadModel(MODEL);
/*     */     }
/*     */     
/*  70 */     float fade = (this.particleAge + f) / this.particleMaxAge;
/*     */     
/*  72 */     float xx = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/*  73 */     float yy = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/*  74 */     float zz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*  75 */     GL11.glTranslated(xx, yy, zz);
/*  76 */     float b = 1.0F;
/*  77 */     int frame = Math.min(15, (int)(14.0F * fade) + 1);
/*  78 */     Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("thaumcraft", "textures/models/ripple" + frame + ".png"));
/*  79 */     b = 0.5F;
/*  80 */     int i = 220;
/*  81 */     int j = i % 65536;
/*  82 */     int k = i / 65536;
/*  83 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/*  84 */     GL11.glRotatef(-this.yaw, 0.0F, 1.0F, 0.0F);
/*  85 */     GL11.glRotatef(this.pitch, 1.0F, 0.0F, 0.0F);
/*  86 */     GL11.glTranslated(0.0D, 0.0D, 2.0F * this.target.height + this.target.width / 2.0F);
/*  87 */     GL11.glScaled(0.25D * this.target.height, 0.25D * this.target.height, -1.0F * this.target.height);
/*  88 */     GL11.glColor4f(b, b, b, 1.0F);
/*  89 */     this.model.renderAll();
/*     */     
/*  91 */     GL11.glDisable(3042);
/*  92 */     GL11.glDepthMask(true);
/*  93 */     GL11.glPopMatrix();
/*     */     
/*  95 */     Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getMCParticleTexture());
/*  96 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 102 */     this.prevPosX = this.posX;
/* 103 */     this.prevPosY = this.posY;
/* 104 */     this.prevPosZ = this.posZ;
/*     */     
/* 106 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 107 */       setExpired();
/*     */     }
/*     */     
/* 110 */     this.posX = this.target.posX;
/* 111 */     this.posY = (this.target.posY + this.target.getEyeHeight());
/* 112 */     this.posZ = this.target.posZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\other\FXSonic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */