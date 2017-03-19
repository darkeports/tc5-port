/*     */ package thaumcraft.client.renderers.tile;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.common.items.tools.ItemThaumometer;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ import thaumcraft.common.tiles.misc.TileEldritchPortal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEldritchPortalRenderer
/*     */   extends TileEntitySpecialRenderer
/*     */ {
/*  31 */   public static final ResourceLocation portaltex = new ResourceLocation("thaumcraft", "textures/misc/eldritch_portal.png");
/*     */   
/*     */ 
/*     */   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f, int q)
/*     */   {
/*     */     
/*  37 */     if (te.getWorld() != null) {
/*  38 */       renderPortal((TileEldritchPortal)te, x, y, z, f);
/*     */     }
/*  40 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void renderPortal(TileEldritchPortal te, double x, double y, double z, float f) {
/*  44 */     if (!te.open) {
/*  45 */       boolean canSee = EntityUtils.hasRevealer(Minecraft.getMinecraft().getRenderViewEntity());
/*  46 */       if (!canSee) {
/*  47 */         canSee = (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) && ((Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof ItemThaumometer)) && (EntityUtils.isVisibleTo(0.8F, Minecraft.getMinecraft().getRenderViewEntity(), te.getPos().getX() + 0.5D, te.getPos().getY() + 0.5D, te.getPos().getZ() + 0.5D, 16.0F));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  52 */       if (!canSee) { return;
/*     */       }
/*     */     }
/*  55 */     long nt = System.nanoTime();
/*  56 */     long time = nt / 50000000L;
/*     */     
/*  58 */     int c = (int)Math.min(30.0F, te.opencount + f);
/*  59 */     int e = (int)Math.min(5.0F, te.opencount + f);
/*  60 */     float scale = e / 5.0F + 0.075F;
/*  61 */     float scaley = c / 30.0F + 0.1F;
/*  62 */     bindTexture(portaltex);
/*  63 */     GL11.glPushMatrix();
/*     */     
/*  65 */     GL11.glDepthMask(false);
/*     */     
/*  67 */     GL11.glEnable(3042);
/*  68 */     GL11.glBlendFunc(770, 771);
/*  69 */     GL11.glColor4f(1.0F, 0.0F, 1.0F, 1.0F);
/*     */     
/*  71 */     if ((Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer))
/*     */     {
/*  73 */       Tessellator tessellator = Tessellator.getInstance();
/*  74 */       float arX = ActiveRenderInfo.getRotationX();
/*  75 */       float arZ = ActiveRenderInfo.getRotationZ();
/*  76 */       float arYZ = ActiveRenderInfo.getRotationYZ();
/*  77 */       float arXY = ActiveRenderInfo.getRotationXY();
/*  78 */       float arXZ = ActiveRenderInfo.getRotationXZ();
/*     */       
/*  80 */       tessellator.getWorldRenderer().begin(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
/*  81 */       double px = x + 0.5D;
/*  82 */       double py = y + 0.5D;
/*  83 */       double pz = z + 0.5D;
/*  84 */       Vec3 v1 = new Vec3(-arX - arYZ, -arXZ, -arZ - arXY);
/*  85 */       Vec3 v2 = new Vec3(-arX + arYZ, arXZ, -arZ + arXY);
/*  86 */       Vec3 v3 = new Vec3(arX + arYZ, arXZ, arZ + arXY);
/*  87 */       Vec3 v4 = new Vec3(arX - arYZ, -arXZ, arZ - arXY);
/*  88 */       int frame = (int)time % 16;
/*  89 */       float f2 = frame / 16.0F;
/*  90 */       float f3 = f2 + 0.0625F;
/*  91 */       float f4 = 0.0F;
/*  92 */       float f5 = 1.0F;
/*  93 */       int i = 220;
/*  94 */       int j = i >> 16 & 0xFFFF;
/*  95 */       int k = i & 0xFFFF;
/*  96 */       tessellator.getWorldRenderer().pos(px + v1.xCoord * scale, py + v1.yCoord * scaley, pz + v1.zCoord * scale).tex(f2, f5).color(1.0F, 1.0F, 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  97 */       tessellator.getWorldRenderer().pos(px + v2.xCoord * scale, py + v2.yCoord * scaley, pz + v2.zCoord * scale).tex(f3, f5).color(1.0F, 1.0F, 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  98 */       tessellator.getWorldRenderer().pos(px + v3.xCoord * scale, py + v3.yCoord * scaley, pz + v3.zCoord * scale).tex(f3, f4).color(1.0F, 1.0F, 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  99 */       tessellator.getWorldRenderer().pos(px + v4.xCoord * scale, py + v4.yCoord * scaley, pz + v4.zCoord * scale).tex(f2, f4).color(1.0F, 1.0F, 1.0F, 1.0F).lightmap(j, k).normal(0.0F, 0.0F, -1.0F).endVertex();
/*     */       
/* 101 */       tessellator.draw();
/*     */     }
/*     */     
/* 104 */     GL11.glDisable(3042);
/*     */     
/* 106 */     GL11.glDepthMask(true);
/*     */     
/* 108 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileEldritchPortalRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */