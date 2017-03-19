/*    */ package thaumcraft.client.renderers.models.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelQuadruped;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import thaumcraft.common.entities.monster.tainted.EntityTaintSheep;
/*    */ 
/*    */ public class ModelTaintSheep1 extends ModelQuadruped
/*    */ {
/*    */   private float field_44016_o;
/*    */   
/*    */   public ModelTaintSheep1()
/*    */   {
/* 15 */     super(12, 0.0F);
/* 16 */     this.head = new ModelRenderer(this, 0, 0);
/* 17 */     this.head.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
/* 18 */     this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
/* 19 */     this.body = new ModelRenderer(this, 28, 8);
/* 20 */     this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
/* 21 */     this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
/* 22 */     float var1 = 0.5F;
/* 23 */     this.leg1 = new ModelRenderer(this, 0, 16);
/* 24 */     this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
/* 25 */     this.leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
/* 26 */     this.leg2 = new ModelRenderer(this, 0, 16);
/* 27 */     this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
/* 28 */     this.leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
/* 29 */     this.leg3 = new ModelRenderer(this, 0, 16);
/* 30 */     this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
/* 31 */     this.leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
/* 32 */     this.leg4 = new ModelRenderer(this, 0, 16);
/* 33 */     this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
/* 34 */     this.leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setLivingAnimations(EntityLivingBase par1EntityLiving, float par2, float par3, float par4)
/*    */   {
/* 44 */     super.setLivingAnimations(par1EntityLiving, par2, par3, par4);
/* 45 */     this.head.rotationPointY = (6.0F + ((EntityTaintSheep)par1EntityLiving).func_44003_c(par4) * 9.0F);
/* 46 */     this.field_44016_o = ((EntityTaintSheep)par1EntityLiving).func_44002_d(par4);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity e)
/*    */   {
/* 54 */     super.setRotationAngles(par1, par2, par3, par4, par5, par6, e);
/* 55 */     this.head.rotateAngleX = this.field_44016_o;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\entity\ModelTaintSheep1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */