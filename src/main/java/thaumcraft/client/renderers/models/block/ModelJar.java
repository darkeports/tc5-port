/*    */ package thaumcraft.client.renderers.models.block;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelJar
/*    */   extends ModelBase
/*    */ {
/*    */   public ModelRenderer Core;
/*    */   public ModelRenderer Brine;
/*    */   public ModelRenderer Lid;
/*    */   public ModelRenderer LidExtension;
/*    */   
/*    */   public ModelJar()
/*    */   {
/* 19 */     this.textureWidth = 64;
/* 20 */     this.textureHeight = 32;
/*    */     
/* 22 */     this.Core = new ModelRenderer(this, 0, 0);
/* 23 */     this.Core.addBox(-5.0F, -12.0F, -5.0F, 10, 12, 10);
/* 24 */     this.Core.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 25 */     this.Core.setTextureSize(64, 32);
/* 26 */     this.Core.mirror = true;
/* 27 */     setRotation(this.Core, 0.0F, 0.0F, 0.0F);
/*    */     
/* 29 */     this.Brine = new ModelRenderer(this, 0, 0);
/* 30 */     this.Brine.addBox(-4.0F, -11.0F, -4.0F, 8, 10, 8);
/* 31 */     this.Brine.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 32 */     this.Brine.setTextureSize(64, 32);
/* 33 */     this.Brine.mirror = true;
/* 34 */     setRotation(this.Brine, 0.0F, 0.0F, 0.0F);
/*    */     
/* 36 */     this.Lid = new ModelRenderer(this, 32, 24);
/* 37 */     this.Lid.addBox(-3.0F, 0.0F, -3.0F, 6, 2, 6);
/* 38 */     this.Lid.setRotationPoint(0.0F, -14.0F, 0.0F);
/* 39 */     this.Lid.setTextureSize(64, 32);
/* 40 */     this.Lid.mirror = true;
/*    */     
/*    */ 
/* 43 */     this.LidExtension = new ModelRenderer(this, 0, 23);
/* 44 */     this.LidExtension.addBox(-2.0F, -16.0F, -2.0F, 4, 2, 4);
/* 45 */     this.LidExtension.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 46 */     this.LidExtension.setTextureSize(64, 32);
/* 47 */     this.LidExtension.mirror = true;
/*    */   }
/*    */   
/*    */   public void renderBrine()
/*    */   {
/* 52 */     GL11.glEnable(3042);
/* 53 */     GL11.glBlendFunc(770, 771);
/* 54 */     this.Brine.render(0.0625F);
/* 55 */     GL11.glDisable(3042);
/*    */   }
/*    */   
/*    */   public void renderLidExtension()
/*    */   {
/* 60 */     this.LidExtension.render(0.0625F);
/*    */   }
/*    */   
/*    */   public void renderLidBrace()
/*    */   {
/* 65 */     this.Lid.render(0.0625F);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private void setRotation(ModelRenderer model, float x, float y, float z)
/*    */   {
/* 79 */     model.rotateAngleX = x;
/* 80 */     model.rotateAngleY = y;
/* 81 */     model.rotateAngleZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\block\ModelJar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */