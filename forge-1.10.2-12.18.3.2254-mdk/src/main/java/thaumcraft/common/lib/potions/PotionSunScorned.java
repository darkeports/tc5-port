/*    */ package thaumcraft.common.lib.potions;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class PotionSunScorned extends Potion
/*    */ {
/* 14 */   public static PotionSunScorned instance = null;
/* 15 */   private int statusIconIndex = -1;
/*    */   
/*    */   public PotionSunScorned(boolean par2, int par3)
/*    */   {
/* 19 */     super(new ResourceLocation("sun_scorned"), par2, par3);
/* 20 */     setIconIndex(0, 0);
/*    */   }
/*    */   
/*    */   public static void init()
/*    */   {
/* 25 */     instance.setPotionName("potion.sunscorned");
/* 26 */     instance.setIconIndex(6, 2);
/* 27 */     instance.setEffectiveness(0.25D);
/*    */   }
/*    */   
/*    */   public boolean isBadEffect()
/*    */   {
/* 32 */     return true;
/*    */   }
/*    */   
/*    */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public int getStatusIconIndex()
/*    */   {
/* 38 */     Minecraft.getMinecraft().renderEngine.bindTexture(rl);
/* 39 */     return super.getStatusIconIndex();
/*    */   }
/*    */   
/* 42 */   static final ResourceLocation rl = new ResourceLocation("thaumcraft", "textures/misc/potions.png");
/*    */   
/*    */   public void performEffect(EntityLivingBase target, int par2)
/*    */   {
/* 46 */     if (!target.worldObj.isRemote)
/*    */     {
/* 48 */       float f = target.getBrightness(1.0F);
/* 49 */       if ((f > 0.5F) && (target.worldObj.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) && (target.worldObj.canBlockSeeSky(new net.minecraft.util.BlockPos(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ)))))
/*    */       {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 55 */         target.setFire(4);
/*    */       }
/* 57 */       else if ((f < 0.25F) && (target.worldObj.rand.nextFloat() > f * 2.0F))
/*    */       {
/* 59 */         target.heal(1.0F);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isReady(int par1, int par2)
/*    */   {
/* 67 */     return par1 % 40 == 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\potions\PotionSunScorned.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */