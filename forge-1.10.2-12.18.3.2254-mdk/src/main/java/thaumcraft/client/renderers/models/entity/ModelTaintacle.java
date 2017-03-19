/*     */ package thaumcraft.client.renderers.models.entity;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintacle;
/*     */ 
/*     */ 
/*     */ public class ModelTaintacle
/*     */   extends ModelBase
/*     */ {
/*  14 */   public ModelRenderer tentacle = new ModelRendererTaintacle(this);
/*     */   public ModelRenderer[] tents;
/*  16 */   public ModelRenderer orb = new ModelRendererTaintacle(this);
/*  17 */   private int length = 10;
/*     */   
/*     */   public ModelTaintacle(int length)
/*     */   {
/*  21 */     int var3 = 0;
/*  22 */     this.length = length;
/*  23 */     this.textureHeight = 64;
/*  24 */     this.textureWidth = 64;
/*  25 */     this.tentacle = new ModelRendererTaintacle(this, 0, 0);
/*     */     
/*  27 */     this.tentacle.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/*  28 */     this.tentacle.rotationPointX = 0.0F;
/*  29 */     this.tentacle.rotationPointZ = 0.0F;
/*  30 */     this.tentacle.rotationPointY = 12.0F;
/*  31 */     this.tents = new ModelRendererTaintacle[length];
/*  32 */     for (int k = 0; k < length - 1; k++)
/*     */     {
/*  34 */       this.tents[k] = new ModelRendererTaintacle(this, 0, 16);
/*  35 */       this.tents[k].addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/*  36 */       this.tents[k].rotationPointY = -8.0F;
/*     */       
/*  38 */       if (k == 0)
/*     */       {
/*  40 */         this.tentacle.addChild(this.tents[k]);
/*     */       }
/*     */       else
/*     */       {
/*  44 */         this.tents[(k - 1)].addChild(this.tents[k]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  49 */     this.orb = new ModelRendererTaintacle(this, 0, 56);
/*  50 */     this.orb.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4);
/*  51 */     this.orb.rotationPointY = -8.0F;
/*  52 */     this.tents[(length - 2)].addChild(this.orb);
/*     */     
/*  54 */     this.tents[(length - 1)] = new ModelRendererTaintacle(this, 0, 32);
/*  55 */     this.tents[(length - 1)].addBox(-6.0F, -6.0F, -6.0F, 12, 12, 12);
/*  56 */     this.tents[(length - 1)].rotationPointY = -8.0F;
/*  57 */     this.tents[(length - 2)].addChild(this.tents[(length - 1)]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity)
/*     */   {
/*  67 */     float flail = 0.0F;
/*  68 */     int ht = 0;
/*  69 */     int at = 0;
/*  70 */     if ((entity instanceof EntityTaintacle)) {
/*  71 */       EntityTaintacle tentacle = (EntityTaintacle)entity;
/*  72 */       flail = tentacle.flailIntensity;
/*  73 */       ht = tentacle.hurtTime;
/*     */       
/*  75 */       float mod = par6 * 0.2F;
/*  76 */       float fs = flail > 1.0F ? 3.0F : 1.0F + (flail > 1.0F ? mod : -mod);
/*  77 */       float fi = flail + ((ht > 0) || (at > 0) ? mod : -mod);
/*     */       
/*  79 */       this.tentacle.rotateAngleX = 0.0F;
/*  80 */       for (int k = 0; k < this.length - 1; k++)
/*     */       {
/*  82 */         this.tents[k].rotateAngleX = (0.15F * fi * MathHelper.sin(par3 * 0.1F * fs - k / 2.0F));
/*  83 */         this.tents[k].rotateAngleZ = (0.1F / fi * MathHelper.sin(par3 * 0.15F - k / 2.0F));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*     */   {
/*  94 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/*  95 */     GL11.glPushMatrix();
/*  96 */     GL11.glEnable(3042);
/*  97 */     GL11.glBlendFunc(770, 771);
/*  98 */     float height = 0.0F;
/*  99 */     float hc = par1Entity.height * 10.0F;
/* 100 */     if (par1Entity.ticksExisted < hc) {
/* 101 */       height = (hc - par1Entity.ticksExisted) / hc * par1Entity.height;
/*     */     }
/* 103 */     GL11.glTranslatef(0.0F, (par1Entity.height == 3.0F ? 0.6F : 1.2F) + height, 0.0F);
/* 104 */     GL11.glScalef(par1Entity.height / 3.0F, par1Entity.height / 3.0F, par1Entity.height / 3.0F);
/* 105 */     ((ModelRendererTaintacle)this.tentacle).render(par7, 0.88F);
/* 106 */     GL11.glDisable(3042);
/* 107 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\entity\ModelTaintacle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */