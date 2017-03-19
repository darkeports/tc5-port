/*    */ package thaumcraft.common.lib.potions;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class PotionThaumarhia extends Potion
/*    */ {
/* 14 */   public static PotionThaumarhia instance = null;
/* 15 */   private int statusIconIndex = -1;
/*    */   
/*    */   public PotionThaumarhia(boolean par2, int par3)
/*    */   {
/* 19 */     super(new ResourceLocation("thaumarhia"), par2, par3);
/* 20 */     setIconIndex(0, 0);
/*    */   }
/*    */   
/*    */   public static void init()
/*    */   {
/* 25 */     instance.setPotionName("potion.thaumarhia");
/* 26 */     instance.setIconIndex(7, 2);
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
/* 46 */     if ((!target.worldObj.isRemote) && (target.worldObj.rand.nextInt(15) == 0))
/*    */     {
/* 48 */       if (target.worldObj.isAirBlock(new BlockPos(target))) {
/* 49 */         target.worldObj.setBlockState(new BlockPos(target), thaumcraft.api.blocks.BlocksTC.fluxGoo.getDefaultState());
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isReady(int par1, int par2)
/*    */   {
/* 57 */     return par1 % 20 == 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\potions\PotionThaumarhia.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */