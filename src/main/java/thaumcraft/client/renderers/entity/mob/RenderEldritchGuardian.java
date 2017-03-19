/*    */ package thaumcraft.client.renderers.entity.mob;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.entity.RenderLiving;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.BossStatus;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.client.renderers.models.entity.ModelEldritchGuardian;
/*    */ import thaumcraft.common.config.Config;
/*    */ import thaumcraft.common.entities.monster.boss.EntityEldritchWarden;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderEldritchGuardian extends RenderLiving
/*    */ {
/*    */   protected ModelEldritchGuardian modelMain;
/* 25 */   private static final ResourceLocation[] skin = { new ResourceLocation("thaumcraft", "textures/models/creature/eldritch_guardian.png"), new ResourceLocation("thaumcraft", "textures/models/creature/eldritch_warden.png") };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public RenderEldritchGuardian(RenderManager rm, ModelEldritchGuardian par1ModelBiped, float par2)
/*    */   {
/* 32 */     super(rm, par1ModelBiped, par2);
/* 33 */     this.modelMain = par1ModelBiped;
/*    */   }
/*    */   
/*    */   protected ResourceLocation getEntityTexture(Entity entity)
/*    */   {
/* 38 */     return (entity instanceof EntityEldritchWarden) ? skin[1] : skin[0];
/*    */   }
/*    */   
/*    */ 
/*    */   protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
/*    */   {
/* 44 */     if ((par1EntityLiving instanceof EntityEldritchWarden)) {
/* 45 */       BossStatus.setBossStatus((EntityEldritchWarden)par1EntityLiving, false);
/* 46 */       GL11.glScalef(1.5F, 1.5F, 1.5F);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void doRenderLiving(EntityLiving guardian, double par2, double par4, double par6, float par8, float par9)
/*    */   {
/* 53 */     GL11.glEnable(3042);
/* 54 */     GL11.glAlphaFunc(516, 0.003921569F);
/* 55 */     GL11.glBlendFunc(770, 771);
/* 56 */     float base = 1.0F;
/* 57 */     double d3 = par4;
/*    */     
/* 59 */     if ((guardian instanceof EntityEldritchWarden)) {
/* 60 */       d3 -= guardian.height * (((EntityEldritchWarden)guardian).getSpawnTimer() / 150.0F);
/*    */     } else {
/* 62 */       Entity e = Minecraft.getMinecraft().getRenderViewEntity();
/* 63 */       float d6 = e.worldObj.getDifficulty() == EnumDifficulty.HARD ? 576.0F : 1024.0F;
/* 64 */       float d7 = 256.0F;
/* 65 */       if ((guardian.worldObj != null) && (guardian.worldObj.provider.getDimensionId() == Config.dimensionOuterId)) {
/* 66 */         base = 1.0F;
/*    */       } else {
/* 68 */         double d8 = guardian.getDistanceSq(e.posX, e.posY, e.posZ);
/* 69 */         if (d8 < 256.0D) {
/* 70 */           base = 0.6F;
/*    */         } else {
/* 72 */           base = (float)(1.0D - Math.min(d6 - d7, d8 - d7) / (d6 - d7)) * 0.6F;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 77 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, base);
/*    */     
/* 79 */     super.doRender(guardian, par2, d3, par6, par8, par9);
/*    */     
/* 81 */     GL11.glDisable(3042);
/* 82 */     GL11.glAlphaFunc(516, 0.1F);
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
/*    */   public void doRender(EntityLiving par1Entity, double par2, double par4, double par6, float par8, float par9)
/*    */   {
/* 95 */     doRenderLiving(par1Entity, par2, par4, par6, par8, par9);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\mob\RenderEldritchGuardian.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */