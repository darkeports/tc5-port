/*     */ package thaumcraft.client.renderers.models.entity;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelTrunk
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer chestLid;
/*     */   public ModelRenderer chestBelow;
/*     */   public ModelRenderer chestKnob;
/*     */   
/*     */   public ModelTrunk()
/*     */   {
/*  26 */     this.chestLid = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
/*  27 */     this.chestLid.addBox(0.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F);
/*  28 */     this.chestLid.rotationPointX = 1.0F;
/*  29 */     this.chestLid.rotationPointY = 7.0F;
/*  30 */     this.chestLid.rotationPointZ = 15.0F;
/*  31 */     this.chestKnob = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
/*  32 */     this.chestKnob.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
/*  33 */     this.chestKnob.rotationPointX = 8.0F;
/*  34 */     this.chestKnob.rotationPointY = 7.0F;
/*  35 */     this.chestKnob.rotationPointZ = 15.0F;
/*  36 */     this.chestBelow = new ModelRenderer(this, 0, 19).setTextureSize(64, 64);
/*  37 */     this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 14, 10, 14, 0.0F);
/*  38 */     this.chestBelow.rotationPointX = 1.0F;
/*  39 */     this.chestBelow.rotationPointY = 6.0F;
/*  40 */     this.chestBelow.rotationPointZ = 1.0F;
/*     */   }
/*     */   
/*     */ 
/*     */   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  46 */     this.chestKnob.rotateAngleX = this.chestLid.rotateAngleX;
/*  47 */     this.chestLid.render(0.0625F);
/*  48 */     this.chestBelow.render(0.0625F);
/*  49 */     this.chestKnob.render(0.0625F);
/*     */     
/*  51 */     GL11.glPushMatrix();
/*     */     
/*  53 */     GL11.glTranslatef(this.chestKnob.offsetX, this.chestKnob.offsetY, this.chestKnob.offsetZ);
/*     */     
/*     */ 
/*  56 */     GL11.glEnable(3042);
/*  57 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  59 */     GL11.glPushMatrix();
/*  60 */     GL11.glTranslatef(this.chestKnob.rotationPointX * 0.0625F, this.chestKnob.rotationPointY * 0.0625F, this.chestKnob.rotationPointZ * 0.0625F);
/*     */     
/*  62 */     if (this.chestKnob.rotateAngleZ != 0.0F)
/*     */     {
/*  64 */       GL11.glRotatef(this.chestKnob.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */     }
/*     */     
/*  67 */     if (this.chestKnob.rotateAngleY != 0.0F)
/*     */     {
/*  69 */       GL11.glRotatef(this.chestKnob.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */     }
/*     */     
/*  72 */     if (this.chestKnob.rotateAngleX != 0.0F)
/*     */     {
/*  74 */       GL11.glRotatef(this.chestKnob.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  95 */     GL11.glPopMatrix();
/*     */     
/*  97 */     GL11.glDisable(3042);
/*     */     
/*  99 */     GL11.glTranslatef(-this.chestKnob.offsetX, -this.chestKnob.offsetY, -this.chestKnob.offsetZ);
/*     */     
/* 101 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\entity\ModelTrunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */