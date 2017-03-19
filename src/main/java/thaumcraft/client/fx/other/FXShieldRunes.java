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
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.client.lib.models.AdvancedModelLoader;
/*     */ import thaumcraft.client.lib.models.IModelCustom;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultist;
/*     */ 
/*     */ public class FXShieldRunes extends Particle
/*     */ {
/*     */   public FXShieldRunes(World world, double d, double d1, double d2, Entity target, int age, float yaw, float pitch)
/*     */   {
/*  25 */     super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */     
/*  27 */     this.particleRed = 1.0F;
/*  28 */     this.particleGreen = 1.0F;
/*  29 */     this.particleBlue = 1.0F;
/*  30 */     this.particleGravity = 0.0F;
/*  31 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/*     */     
/*  33 */     this.particleMaxAge = (age + this.rand.nextInt(age / 2));
/*     */     
/*  35 */     this.canCollide = true;
/*     */     
/*  37 */     setSize(0.01F, 0.01F);
/*     */     
/*  39 */     this.canCollide = false;
/*     */     
/*  41 */     this.particleScale = 1.0F;
/*     */     
/*  43 */     this.target = target;
/*  44 */     this.yaw = yaw;
/*  45 */     this.pitch = pitch;
/*     */     
/*  47 */     this.prevPosX = (this.posX = target.posX);
/*  48 */     this.prevPosY = (this.posY = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0D);
/*  49 */     this.prevPosZ = (this.posZ = target.posZ);
/*     */     
/*  51 */     for (int a = 0; a < 15; a++) {
/*  52 */       this.tex1[a] = new ResourceLocation("thaumcraft", "textures/models/ripple" + (a + 1) + ".png");
/*  53 */       this.tex2[a] = new ResourceLocation("thaumcraft", "textures/models/hemis" + (a + 1) + ".png");
/*     */     }
/*     */   }
/*     */   
/*  57 */   ResourceLocation[] tex1 = new ResourceLocation[15];
/*  58 */   ResourceLocation[] tex2 = new ResourceLocation[15];
/*     */   
/*  60 */   Entity target = null;
/*  61 */   float yaw = 0.0F;
/*  62 */   float pitch = 0.0F;
/*     */   
/*     */   private IModelCustom model;
/*  65 */   private static final ResourceLocation MODEL = new ResourceLocation("thaumcraft", "models/obj/hemis.obj");
/*     */   
/*     */   public void renderParticle(VertexBuffer wr, Entity p_180434_2_, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  69 */     Tessellator.getInstance().draw();
/*  70 */     GL11.glPushMatrix();
/*  71 */     GL11.glDisable(2884);
/*     */     
/*  73 */     GL11.glEnable(3042);
/*  74 */     GL11.glBlendFunc(770, 1);
/*     */     
/*  76 */     if (this.model == null) {
/*  77 */       this.model = AdvancedModelLoader.loadModel(MODEL);
/*     */     }
/*     */     
/*  80 */     float fade = (this.particleAge + f) / this.particleMaxAge;
/*     */     
/*  82 */     float xx = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
/*  83 */     float yy = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
/*  84 */     float zz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
/*  85 */     GL11.glTranslated(xx, yy, zz);
/*  86 */     float b = 1.0F;
/*  87 */     int frame = Math.min(15, (int)(14.0F * fade) + 1);
/*  88 */     if (((this.target instanceof net.minecraft.entity.monster.EntityMob)) && (!(this.target instanceof EntityCultist))) {
/*  89 */       Minecraft.getMinecraft().renderEngine.bindTexture(this.tex1[(frame - 1)]);
/*  90 */       b = 0.5F;
/*     */     } else {
/*  92 */       Minecraft.getMinecraft().renderEngine.bindTexture(this.tex2[(frame - 1)]);
/*     */     }
/*  94 */     int i = 220;
/*  95 */     int j = i % 65536;
/*  96 */     int k = i / 65536;
/*  97 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/*  98 */     GL11.glRotatef(180.0F - this.yaw, 0.0F, 1.0F, 0.0F);
/*  99 */     GL11.glRotatef(-this.pitch, 1.0F, 0.0F, 0.0F);
/* 100 */     GL11.glScaled(0.4D * this.target.height, 0.4D * this.target.height, 0.4D * this.target.height);
/* 101 */     GL11.glColor4f(b, b, b, Math.min(1.0F, (1.0F - fade) * 3.0F));
/* 102 */     this.model.renderAll();
/*     */     
/* 104 */     GL11.glBlendFunc(770, 771);
/* 105 */     GL11.glDisable(3042);
/*     */     
/* 107 */     GL11.glEnable(2884);
/* 108 */     GL11.glPopMatrix();
/*     */     
/* 110 */     Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getMCParticleTexture());
/* 111 */     wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 117 */     this.prevPosX = this.posX;
/* 118 */     this.prevPosY = this.posY;
/* 119 */     this.prevPosZ = this.posZ;
/*     */     
/* 121 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 122 */       setExpired();
/*     */     }
/*     */     
/* 125 */     this.posX = this.target.posX;
/* 126 */     this.posY = ((this.target.getEntityBoundingBox().minY + this.target.getEntityBoundingBox().maxY) / 2.0D);
/* 127 */     this.posZ = this.target.posZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\fx\other\FXShieldRunes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */