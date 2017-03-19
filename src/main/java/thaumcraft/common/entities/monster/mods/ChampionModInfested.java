/*    */ package thaumcraft.common.entities.monster.mods;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.entities.monster.tainted.EntityTaintCrawler;
/*    */ 
/*    */ public class ChampionModInfested implements IChampionModifierEffect
/*    */ {
/*    */   public float performEffect(EntityLivingBase boss, EntityLivingBase target, DamageSource source, float amount)
/*    */   {
/* 14 */     if ((boss.worldObj.rand.nextFloat() < 0.4F) && (!boss.worldObj.isRemote)) {
/* 15 */       EntityTaintCrawler spiderling = new EntityTaintCrawler(boss.worldObj);
/* 16 */       spiderling.setLocationAndAngles(boss.posX, boss.posY + boss.height / 2.0F, boss.posZ, boss.worldObj.rand.nextFloat() * 360.0F, 0.0F);
/* 17 */       boss.worldObj.spawnEntityInWorld(spiderling);
/* 18 */       boss.playSound("thaumcraft:gore", 0.5F, 1.0F);
/*    */     }
/* 20 */     return amount;
/*    */   }
/*    */   
/*    */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public void showFX(EntityLivingBase boss)
/*    */   {
/* 26 */     if (boss.worldObj.rand.nextBoolean()) Thaumcraft.proxy.getFX().slimeJumpFX(boss, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\mods\ChampionModInfested.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */