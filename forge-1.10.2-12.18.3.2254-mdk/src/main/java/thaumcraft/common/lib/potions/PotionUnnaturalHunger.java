/*    */ package thaumcraft.common.lib.potions;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class PotionUnnaturalHunger extends Potion
/*    */ {
/* 13 */   public static PotionUnnaturalHunger instance = null;
/* 14 */   private int statusIconIndex = -1;
/*    */   
/*    */   public PotionUnnaturalHunger(boolean par2, int par3)
/*    */   {
/* 18 */     super(new ResourceLocation("unnatural_hunger"), par2, par3);
/* 19 */     setIconIndex(0, 0);
/*    */   }
/*    */   
/*    */   public static void init()
/*    */   {
/* 24 */     instance.setPotionName("potion.unhunger");
/* 25 */     instance.setIconIndex(7, 1);
/* 26 */     instance.setEffectiveness(0.25D);
/*    */   }
/*    */   
/*    */   public boolean isBadEffect()
/*    */   {
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int getStatusIconIndex()
/*    */   {
/* 37 */     Minecraft.getMinecraft().renderEngine.bindTexture(rl);
/* 38 */     return super.getStatusIconIndex();
/*    */   }
/*    */   
/* 41 */   static final ResourceLocation rl = new ResourceLocation("thaumcraft", "textures/misc/potions.png");
/*    */   
/*    */   public void performEffect(EntityLivingBase target, int par2)
/*    */   {
/* 45 */     if ((!target.worldObj.isRemote) && ((target instanceof EntityPlayer)))
/*    */     {
/* 47 */       ((EntityPlayer)target).addExhaustion(0.025F * (par2 + 1));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isReady(int par1, int par2)
/*    */   {
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\potions\PotionUnnaturalHunger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */