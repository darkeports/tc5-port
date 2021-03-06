/*    */ package thaumcraft.common.lib.potions;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class PotionDeathGaze extends Potion
/*    */ {
/* 12 */   public static PotionDeathGaze instance = null;
/* 13 */   private int statusIconIndex = -1;
/*    */   
/*    */   public PotionDeathGaze(boolean par2, int par3)
/*    */   {
/* 17 */     super(new ResourceLocation("death_gaze"), par2, par3);
/* 18 */     setIconIndex(0, 0);
/*    */   }
/*    */   
/*    */   public static void init()
/*    */   {
/* 23 */     instance.setPotionName("potion.deathgaze");
/* 24 */     instance.setIconIndex(4, 2);
/* 25 */     instance.setEffectiveness(0.25D);
/*    */   }
/*    */   
/*    */   public boolean isBadEffect()
/*    */   {
/* 30 */     return true;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int getStatusIconIndex()
/*    */   {
/* 36 */     Minecraft.getMinecraft().renderEngine.bindTexture(rl);
/* 37 */     return super.getStatusIconIndex();
/*    */   }
/*    */   
/* 40 */   static final ResourceLocation rl = new ResourceLocation("thaumcraft", "textures/misc/potions.png");
/*    */   
/*    */   public void performEffect(EntityLivingBase target, int par2) {}
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\potions\PotionDeathGaze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */