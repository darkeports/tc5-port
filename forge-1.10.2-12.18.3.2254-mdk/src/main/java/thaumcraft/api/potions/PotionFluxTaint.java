/*    */ package thaumcraft.api.potions;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.damagesource.DamageSourceThaumcraft;
/*    */ import thaumcraft.api.entities.ITaintedMob;
/*    */ 
/*    */ public class PotionFluxTaint extends Potion
/*    */ {
/* 15 */   public static PotionFluxTaint instance = null;
/* 16 */   private int statusIconIndex = -1;
/*    */   
/*    */   public PotionFluxTaint(boolean par2, int par3)
/*    */   {
/* 20 */     super(par2, par3);
			 REGISTRY.register(-1, new ResourceLocation("flux_taint"), this);
/* 21 */     setIconIndex(0, 0);
/*    */   }
/*    */   
/*    */   public static void init()
/*    */   {
/* 26 */     instance.setPotionName("potion.fluxtaint");
/* 27 */     instance.setIconIndex(3, 1);
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
/*    */   public void performEffect(EntityLivingBase target, int par2)
/*    */   {
/* 47 */     if ((target instanceof ITaintedMob)) {
/* 48 */       target.heal(1.0F);
/*    */     }
/* 50 */     else if ((!target.isEntityUndead()) && (!(target instanceof EntityPlayer)))
/*    */     {
/* 52 */       target.attackEntityFrom(DamageSourceThaumcraft.taint, 1.0F);
/*    */ 
/*    */     }
/* 55 */     else if ((!target.isEntityUndead()) && ((target.getMaxHealth() > 1.0F) || ((target instanceof EntityPlayer))))
/*    */     {
/* 57 */       target.attackEntityFrom(DamageSourceThaumcraft.taint, 1.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isReady(int par1, int par2)
/*    */   {
/* 63 */     int k = 40 >> par2;
/* 64 */     return par1 % k == 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\potions\PotionFluxTaint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */