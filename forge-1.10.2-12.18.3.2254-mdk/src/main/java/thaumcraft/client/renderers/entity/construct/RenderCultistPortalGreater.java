/*     */ package thaumcraft.client.renderers.entity.construct;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.boss.BossStatus;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.common.entities.monster.boss.EntityCultistPortalGreater;
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RenderCultistPortalGreater
/*     */   extends Render
/*     */ {
/*     */   public RenderCultistPortalGreater(RenderManager renderManager)
/*     */   {
/*  29 */     super(renderManager);
/*  30 */     this.shadowSize = 0.1F;
/*  31 */     this.shadowOpaque = 0.5F;
/*     */   }
/*     */   
/*  34 */   public static final ResourceLocation portaltex = new ResourceLocation("thaumcraft", "textures/misc/cultist_portal.png");
/*     */   
/*     */ 
/*     */   public void renderPortal(EntityCultistPortalGreater portal, double px, double py, double pz, float par8, float f)
/*     */   {
/*  39 */     if (BossStatus.statusBarTime < 100) {
/*  40 */       BossStatus.setBossStatus(portal, false);
/*     */     }
/*  42 */     long nt = System.nanoTime();
/*  43 */     long time = nt / 50000000L;
/*  44 */     float scaley = 1.5F;
/*  45 */     int e = (int)Math.min(50.0F, portal.ticksExisted + f);
/*     */     
/*  47 */     if (portal.hurtTime > 0) {
/*  48 */       double d = Math.sin(portal.hurtTime * 72 * 3.141592653589793D / 180.0D);
/*  49 */       scaley = (float)(scaley - d / 4.0D);
/*  50 */       e = (int)(e + 6.0D * d);
/*     */     }
/*     */     
/*  53 */     if (portal.pulse > 0) {
/*  54 */       double d = Math.sin(portal.pulse * 36 * 3.141592653589793D / 180.0D);
/*  55 */       scaley = (float)(scaley + d / 4.0D);
/*  56 */       e = (int)(e + 12.0D * d);
/*     */     }
/*     */     
/*  59 */     float scale = e / 50.0F * 1.3F;
/*     */     
/*  61 */     py += portal.height / 2.0F;
/*     */     
/*  63 */     float m = (1.0F - portal.getHealth() / portal.getMaxHealth()) / 3.0F;
/*  64 */     float bob = MathHelper.sin(portal.ticksExisted / (5.0F - 12.0F * m)) * m + m;
/*  65 */     float bob2 = MathHelper.sin(portal.ticksExisted / (6.0F - 15.0F * m)) * m + m;
/*  66 */     float alpha = 1.0F - bob;
/*     */     
/*  68 */     scaley -= bob / 4.0F;
/*  69 */     scale -= bob2 / 3.0F;
/*     */     
/*  71 */     bindTexture(portaltex);
/*  72 */     GL11.glPushMatrix();
/*     */     
/*  74 */     GL11.glEnable(3042);
/*  75 */     GL11.glBlendFunc(770, 771);
/*  76 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
/*     */     
/*  78 */     if ((Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer))
/*     */     {
/*  80 */       Tessellator tessellator = Tessellator.getInstance();
/*  81 */       float arX = ActiveRenderInfo.getRotationX();
/*  82 */       float arZ = ActiveRenderInfo.getRotationZ();
/*  83 */       float arYZ = ActiveRenderInfo.getRotationYZ();
/*  84 */       float arXY = ActiveRenderInfo.getRotationXY();
/*  85 */       float arXZ = ActiveRenderInfo.getRotationXZ();
/*     */       
/*  87 */       EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
/*  88 */       double iPX = player.prevPosX + (player.posX - player.prevPosX) * f;
/*  89 */       double iPY = player.prevPosY + (player.posY - player.prevPosY) * f;
/*  90 */       double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
/*  91 */       tessellator.getWorldRenderer().begin(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
/*  92 */       Vec3 v1 = new Vec3(-arX - arYZ, -arXZ, -arZ - arXY);
/*  93 */       Vec3 v2 = new Vec3(-arX + arYZ, arXZ, -arZ + arXY);
/*  94 */       Vec3 v3 = new Vec3(arX + arYZ, arXZ, arZ + arXY);
/*  95 */       Vec3 v4 = new Vec3(arX - arYZ, -arXZ, arZ - arXY);
/*  96 */       int frame = 15 - (int)time % 16;
/*  97 */       float f2 = frame / 16.0F;
/*  98 */       float f3 = f2 + 0.0625F;
/*  99 */       float f4 = 0.0F;
/* 100 */       float f5 = 1.0F;
/* 101 */       int i = 220;
/* 102 */       int j = i >> 16 & 0xFFFF;
/* 103 */       int k = i & 0xFFFF;
/* 104 */       tessellator.getWorldRenderer().pos(px + v1.xCoord * scale, py + v1.yCoord * scaley, pz + v1.zCoord * scale).tex(f3, f4).color(1.0F, 1.0F, 1.0F, alpha).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 105 */       tessellator.getWorldRenderer().pos(px + v2.xCoord * scale, py + v2.yCoord * scaley, pz + v2.zCoord * scale).tex(f3, f5).color(1.0F, 1.0F, 1.0F, alpha).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 106 */       tessellator.getWorldRenderer().pos(px + v3.xCoord * scale, py + v3.yCoord * scaley, pz + v3.zCoord * scale).tex(f2, f5).color(1.0F, 1.0F, 1.0F, alpha).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 107 */       tessellator.getWorldRenderer().pos(px + v4.xCoord * scale, py + v4.yCoord * scaley, pz + v4.zCoord * scale).tex(f2, f4).color(1.0F, 1.0F, 1.0F, alpha).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 108 */       tessellator.draw();
/*     */     }
/*     */     
/* 111 */     GL11.glDisable(32826);
/* 112 */     GL11.glDisable(3042);
/* 113 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */ 
/*     */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/*     */   {
/* 119 */     renderPortal((EntityCultistPortalGreater)par1Entity, par2, par4, par6, par8, par9);
/*     */   }
/*     */   
/*     */ 
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 125 */     return portaltex;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\construct\RenderCultistPortalGreater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */