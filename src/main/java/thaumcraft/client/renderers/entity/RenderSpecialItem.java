/*    */ package thaumcraft.client.renderers.entity;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderEntityItem;
/*    */ import net.minecraft.client.renderer.entity.RenderItem;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraftforge.fml.client.FMLClientHandler;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderSpecialItem extends RenderEntityItem
/*    */ {
/*    */   public RenderSpecialItem(RenderManager p_i46167_1_, RenderItem p_i46167_2_)
/*    */   {
/* 22 */     super(p_i46167_1_, p_i46167_2_);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void doRender(EntityItem e, double x, double y, double z, float p_177075_8_, float pt)
/*    */   {
/* 30 */     Random random = new Random(187L);
/* 31 */     float var11 = MathHelper.sin((e.getAge() + pt) / 10.0F + e.hoverStart) * 0.1F + 0.1F;
/* 32 */     GL11.glPushMatrix();
/* 33 */     GL11.glTranslatef((float)x, (float)y + var11 + 0.25F, (float)z);
/*    */     
/* 35 */     int q = !FMLClientHandler.instance().getClient().gameSettings.fancyGraphics ? 5 : 10;
/*    */     
/* 37 */     Tessellator tessellator = Tessellator.getInstance();
/* 38 */     WorldRenderer wr = tessellator.getWorldRenderer();
/* 39 */     RenderHelper.disableStandardItemLighting();
/* 40 */     float f1 = e.getAge() / 500.0F;
/* 41 */     float f3 = 0.9F;
/* 42 */     float f2 = 0.0F;
/*    */     
/* 44 */     GL11.glDisable(3553);
/* 45 */     GL11.glShadeModel(7425);
/* 46 */     GL11.glEnable(3042);
/* 47 */     GL11.glBlendFunc(770, 1);
/* 48 */     GL11.glDisable(3008);
/* 49 */     GL11.glEnable(2884);
/* 50 */     GL11.glDepthMask(false);
/* 51 */     GL11.glPushMatrix();
/* 52 */     for (int i = 0; i < q; i++)
/*    */     {
/* 54 */       GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
/* 55 */       GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
/* 56 */       GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
/* 57 */       GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
/* 58 */       GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
/* 59 */       GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 360.0F, 0.0F, 0.0F, 1.0F);
/* 60 */       wr.begin(6, DefaultVertexFormats.POSITION_COLOR);
/* 61 */       float fa = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
/* 62 */       float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
/* 63 */       fa /= 30.0F / (Math.min(e.getAge(), 10) / 10.0F);
/* 64 */       f4 /= 30.0F / (Math.min(e.getAge(), 10) / 10.0F);
/* 65 */       wr.pos(0.0D, 0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F - f2).endVertex();
/* 66 */       wr.pos(-0.866D * f4, fa, -0.5F * f4).color(1.0F, 0.0F, 1.0F, 0.0F).endVertex();
/* 67 */       wr.pos(0.866D * f4, fa, -0.5F * f4).color(1.0F, 0.0F, 1.0F, 0.0F).endVertex();
/* 68 */       wr.pos(0.0D, fa, 1.0F * f4).color(1.0F, 0.0F, 1.0F, 0.0F).endVertex();
/* 69 */       wr.pos(-0.866D * f4, fa, -0.5F * f4).color(1.0F, 0.0F, 1.0F, 0.0F).endVertex();
/* 70 */       tessellator.draw();
/*    */     }
/*    */     
/* 73 */     GL11.glPopMatrix();
/* 74 */     GL11.glDepthMask(true);
/* 75 */     GL11.glDisable(2884);
/* 76 */     GL11.glBlendFunc(770, 771);
/* 77 */     GL11.glDisable(3042);
/* 78 */     GL11.glShadeModel(7424);
/* 79 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 80 */     GL11.glEnable(3553);
/* 81 */     GL11.glEnable(3008);
/* 82 */     RenderHelper.enableStandardItemLighting();
/*    */     
/* 84 */     GL11.glPopMatrix();
/*    */     
/* 86 */     super.doRender(e, x, y, z, p_177075_8_, pt);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\RenderSpecialItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */