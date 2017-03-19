/*    */ package thaumcraft.common.lib.potions;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.potions.PotionVisExhaust;
/*    */ 
/*    */ public class PotionInfectiousVisExhaust extends Potion
/*    */ {
/* 15 */   public static PotionInfectiousVisExhaust instance = null;
/*    */   
/*    */ 
/* 18 */   private int statusIconIndex = -1;
/*    */   
/*    */   public PotionInfectiousVisExhaust(boolean par2, int par3) {
/* 21 */     super(new ResourceLocation("infectous_vis_exhaust"), par2, par3);
/* 22 */     setIconIndex(0, 0);
/*    */   }
/*    */   
/*    */   public static void init() {
/* 26 */     instance.setPotionName("potion.infvisexhaust");
/* 27 */     instance.setIconIndex(6, 1);
/* 28 */     instance.setEffectiveness(0.25D);
/*    */   }
/*    */   
/*    */   public boolean isBadEffect()
/*    */   {
/* 33 */     return true;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int getStatusIconIndex()
/*    */   {
/* 39 */     Minecraft.getMinecraft().renderEngine.bindTexture(rl);
/* 40 */     return super.getStatusIconIndex();
/*    */   }
/*    */   
/* 43 */   static final ResourceLocation rl = new ResourceLocation("thaumcraft", "textures/misc/potions.png");
/*    */   
/*    */ 
/*    */ 
/*    */   public void performEffect(EntityLivingBase target, int par2)
/*    */   {
/* 49 */     List<EntityLivingBase> targets = target.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().expand(4.0D, 4.0D, 4.0D));
/*    */     
/* 51 */     if (targets.size() > 0) {
/* 52 */       for (EntityLivingBase e : targets) {
/* 53 */         if (!e.isPotionActive(instance)) {
/* 54 */           if (par2 > 0) {
/* 55 */             e.addPotionEffect(new PotionEffect(instance.getId(), 6000, par2 - 1, false, true));
/*    */           }
/*    */           else
/*    */           {
/* 59 */             e.addPotionEffect(new PotionEffect(PotionVisExhaust.instance.getId(), 6000, 0, false, true));
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isReady(int par1, int par2) {
/* 67 */     return par1 % 40 == 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\potions\PotionInfectiousVisExhaust.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */