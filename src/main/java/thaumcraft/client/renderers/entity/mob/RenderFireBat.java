/*     */ package thaumcraft.client.renderers.entity.mob;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBat;
/*     */ import net.minecraft.client.renderer.entity.RenderLiving;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.client.renderers.models.entity.ModelFireBat;
/*     */ import thaumcraft.common.entities.monster.EntityFireBat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RenderFireBat
/*     */   extends RenderLiving
/*     */ {
/*     */   private int renderedBatSize;
/*     */   
/*     */   public RenderFireBat(RenderManager rm)
/*     */   {
/*  29 */     super(rm, new ModelFireBat(), 0.25F);
/*  30 */     this.renderedBatSize = ((ModelFireBat)this.mainModel).getBatSize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void func_82443_a(EntityFireBat par1EntityBat, double par2, double par4, double par6, float par8, float par9)
/*     */   {
/*  37 */     int var10 = ((ModelFireBat)this.mainModel).getBatSize();
/*     */     
/*  39 */     if (var10 != this.renderedBatSize)
/*     */     {
/*  41 */       this.renderedBatSize = var10;
/*  42 */       this.mainModel = new ModelBat();
/*     */     }
/*     */     
/*  45 */     super.doRender(par1EntityBat, par2, par4, par6, par8, par9);
/*     */   }
/*     */   
/*     */   protected void func_82442_a(EntityFireBat par1EntityBat, float par2)
/*     */   {
/*  50 */     if ((par1EntityBat.getIsDevil()) || (par1EntityBat.getIsVampire())) {
/*  51 */       GL11.glScalef(0.6F, 0.6F, 0.6F);
/*     */     } else {
/*  53 */       GL11.glScalef(0.35F, 0.35F, 0.35F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void func_82445_a(EntityFireBat par1EntityBat, double par2, double par4, double par6) {
/*  58 */     super.renderLivingAt(par1EntityBat, par2, par4, par6);
/*     */   }
/*     */   
/*     */   protected void func_82444_a(EntityFireBat par1EntityBat, float par2, float par3, float par4)
/*     */   {
/*  63 */     if (!par1EntityBat.getIsBatHanging())
/*     */     {
/*  65 */       GL11.glTranslatef(0.0F, MathHelper.cos(par2 * 0.3F) * 0.1F, 0.0F);
/*     */     }
/*     */     else
/*     */     {
/*  69 */       GL11.glTranslatef(0.0F, -0.1F, 0.0F);
/*     */     }
/*     */     
/*  72 */     super.rotateCorpse(par1EntityBat, par2, par3, par4);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
/*     */   {
/*  82 */     func_82442_a((EntityFireBat)par1EntityLiving, par2);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void rotateCorpse(EntityLivingBase par1EntityLiving, float par2, float par3, float par4)
/*     */   {
/*  88 */     func_82444_a((EntityFireBat)par1EntityLiving, par2, par3, par4);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void renderLivingAt(EntityLivingBase par1EntityLiving, double par2, double par4, double par6)
/*     */   {
/*  97 */     func_82445_a((EntityFireBat)par1EntityLiving, par2, par4, par6);
/*     */   }
/*     */   
/*     */ 
/*     */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/*     */   {
/* 103 */     func_82443_a((EntityFireBat)par1EntityLiving, par2, par4, par6, par8, par9);
/*     */   }
/*     */   
/*     */ 
/* 107 */   private static final ResourceLocation rl = new ResourceLocation("thaumcraft", "textures/models/creature/firebat.png");
/* 108 */   private static final ResourceLocation rl2 = new ResourceLocation("thaumcraft", "textures/models/creature/vampirebat.png");
/*     */   
/*     */   protected ResourceLocation getEntityTexture(Entity entity)
/*     */   {
/* 112 */     if (((entity instanceof EntityFireBat)) && (((EntityFireBat)entity).getIsVampire())) {
/* 113 */       return rl2;
/*     */     }
/* 115 */     return rl;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\mob\RenderFireBat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */