/*     */ package thaumcraft.client.renderers.entity.construct;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultistPortalLesser;
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RenderCultistPortalLesser
/*     */   extends Render
/*     */ {
/*     */   public RenderCultistPortalLesser(RenderManager renderManager)
/*     */   {
/*  27 */     super(renderManager);
/*  28 */     this.shadowSize = 0.0F;
/*  29 */     this.shadowOpaque = 0.0F;
/*     */   }
/*     */   
/*  32 */   public static final ResourceLocation portaltex = new ResourceLocation("thaumcraft", "textures/misc/cultist_portal.png");
/*     */   
/*     */   public void renderPortal(EntityCultistPortalLesser portal, double px, double py, double pz, float par8, float f)
/*     */   {
/*  36 */     if (portal.isActive()) {
/*  37 */       long nt = System.nanoTime();
/*  38 */       long time = nt / 50000000L;
/*  39 */       float scaley = 1.4F;
/*  40 */       int e = (int)Math.min(50.0F, portal.activeCounter + f);
/*     */       
/*  42 */       if (portal.hurtTime > 0) {
/*  43 */         double d = Math.sin(portal.hurtTime * 72 * 3.141592653589793D / 180.0D);
/*  44 */         scaley = (float)(scaley - d / 4.0D);
/*  45 */         e = (int)(e + 6.0D * d);
/*     */       }
/*     */       
/*  48 */       if (portal.pulse > 0) {
/*  49 */         double d = Math.sin(portal.pulse * 36 * 3.141592653589793D / 180.0D);
/*  50 */         scaley = (float)(scaley + d / 4.0D);
/*  51 */         e = (int)(e + 12.0D * d);
/*     */       }
/*     */       
/*  54 */       float scale = e / 50.0F * 1.25F;
/*     */       
/*  56 */       py += portal.height / 2.0F;
/*     */       
/*  58 */       float m = (1.0F - portal.getHealth() / portal.getMaxHealth()) / 3.0F;
/*  59 */       float bob = MathHelper.sin(portal.activeCounter / (5.0F - 12.0F * m)) * m + m;
/*  60 */       float bob2 = MathHelper.sin(portal.activeCounter / (6.0F - 15.0F * m)) * m + m;
/*  61 */       float alpha = 1.0F - bob;
/*     */       
/*  63 */       scaley -= bob / 4.0F;
/*  64 */       scale -= bob2 / 3.0F;
/*     */       
/*  66 */       bindTexture(portaltex);
/*  67 */       GL11.glPushMatrix();
/*     */       
/*  69 */       GL11.glEnable(3042);
/*  70 */       GL11.glBlendFunc(770, 771);
/*  71 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
/*     */       
/*  73 */       if ((Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer))
/*     */       {
/*  75 */         Tessellator tessellator = Tessellator.getInstance();
/*  76 */         float arX = ActiveRenderInfo.getRotationX();
/*  77 */         float arZ = ActiveRenderInfo.getRotationZ();
/*  78 */         float arYZ = ActiveRenderInfo.getRotationYZ();
/*  79 */         float arXY = ActiveRenderInfo.getRotationXY();
/*  80 */         float arXZ = ActiveRenderInfo.getRotationXZ();
/*     */         
/*  82 */         tessellator.getWorldRenderer().begin(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
/*  83 */         Vec3 v1 = new Vec3(-arX - arYZ, -arXZ, -arZ - arXY);
/*  84 */         Vec3 v2 = new Vec3(-arX + arYZ, arXZ, -arZ + arXY);
/*  85 */         Vec3 v3 = new Vec3(arX + arYZ, arXZ, arZ + arXY);
/*  86 */         Vec3 v4 = new Vec3(arX - arYZ, -arXZ, arZ - arXY);
/*  87 */         int frame = 15 - (int)time % 16;
/*  88 */         float f2 = frame / 16.0F;
/*  89 */         float f3 = f2 + 0.0625F;
/*  90 */         float f4 = 0.0F;
/*  91 */         float f5 = 1.0F;
/*  92 */         int i = 220;
/*  93 */         int j = i >> 16 & 0xFFFF;
/*  94 */         int k = i & 0xFFFF;
/*  95 */         tessellator.getWorldRenderer().pos(px + v1.xCoord * scale, py + v1.yCoord * scaley, pz + v1.zCoord * scale).tex(f3, f4).color(1.0F, 1.0F, 1.0F, alpha).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  96 */         tessellator.getWorldRenderer().pos(px + v2.xCoord * scale, py + v2.yCoord * scaley, pz + v2.zCoord * scale).tex(f3, f5).color(1.0F, 1.0F, 1.0F, alpha).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  97 */         tessellator.getWorldRenderer().pos(px + v3.xCoord * scale, py + v3.yCoord * scaley, pz + v3.zCoord * scale).tex(f2, f5).color(1.0F, 1.0F, 1.0F, alpha).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  98 */         tessellator.getWorldRenderer().pos(px + v4.xCoord * scale, py + v4.yCoord * scaley, pz + v4.zCoord * scale).tex(f2, f4).color(1.0F, 1.0F, 1.0F, alpha).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  99 */         tessellator.draw();
/*     */       }
/*     */       
/* 102 */       GL11.glDisable(32826);
/* 103 */       GL11.glDisable(3042);
/* 104 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/*     */   {
/* 111 */     renderPortal((EntityCultistPortalLesser)par1Entity, par2, par4, par6, par8, par9);
/*     */   }
/*     */   
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 117 */     return portaltex;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\construct\RenderCultistPortalLesser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */