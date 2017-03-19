/*    */ package thaumcraft.client.renderers.models.block;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelResearchTable
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer Inkwell;
/*    */   ModelRenderer ScrollTube;
/*    */   ModelRenderer ScrollRibbon;
/*    */   
/*    */   public ModelResearchTable()
/*    */   {
/* 21 */     this.textureWidth = 64;
/* 22 */     this.textureHeight = 32;
/*    */     
/* 24 */     this.Inkwell = new ModelRenderer(this, 0, 16);
/* 25 */     this.Inkwell.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
/* 26 */     this.Inkwell.setRotationPoint(-6.0F, -2.0F, 3.0F);
/* 27 */     this.Inkwell.mirror = true;
/* 28 */     setRotation(this.Inkwell, 0.0F, 0.0F, 0.0F);
/*    */     
/* 30 */     this.ScrollTube = new ModelRenderer(this, 0, 0);
/* 31 */     this.ScrollTube.addBox(-8.0F, -0.5F, 0.0F, 8, 2, 2);
/* 32 */     this.ScrollTube.setRotationPoint(-2.0F, -2.0F, 2.0F);
/* 33 */     this.ScrollTube.mirror = true;
/* 34 */     setRotation(this.ScrollTube, 0.0F, 10.0F, 0.0F);
/*    */     
/* 36 */     this.ScrollRibbon = new ModelRenderer(this, 0, 4);
/* 37 */     this.ScrollRibbon.addBox(-4.25F, -0.275F, 0.0F, 1, 2, 2);
/* 38 */     this.ScrollRibbon.setRotationPoint(-2.0F, -2.0F, 2.0F);
/* 39 */     this.ScrollRibbon.mirror = true;
/* 40 */     setRotation(this.ScrollRibbon, 0.0F, 10.0F, 0.0F);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void renderInkwell()
/*    */   {
/* 47 */     GL11.glPushMatrix();
/* 48 */     GL11.glEnable(3042);
/* 49 */     GL11.glBlendFunc(770, 771);
/* 50 */     this.Inkwell.render(0.0625F);
/* 51 */     GL11.glDisable(3042);
/* 52 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   public void renderScroll(int color)
/*    */   {
/* 57 */     GL11.glPushMatrix();
/* 58 */     this.ScrollTube.render(0.0625F);
/* 59 */     Color c = new Color(color);
/* 60 */     GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, 1.0F);
/* 61 */     GL11.glScalef(1.2F, 1.2F, 1.2F);
/* 62 */     this.ScrollRibbon.render(0.0625F);
/* 63 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 64 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*    */   {
/* 69 */     model.rotateAngleX = x;
/* 70 */     model.rotateAngleY = y;
/* 71 */     model.rotateAngleZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\block\ModelResearchTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */