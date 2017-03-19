/*    */ package thaumcraft.common.entities.monster.mods;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ChampionModFire implements IChampionModifierEffect
/*    */ {
/*    */   public float performEffect(EntityLivingBase boss, EntityLivingBase target, DamageSource source, float amount)
/*    */   {
/* 13 */     if (boss.worldObj.rand.nextFloat() < 0.4F) {
/* 14 */       target.setFire(4);
/*    */     }
/* 16 */     return amount;
/*    */   }
/*    */   
/*    */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public void showFX(EntityLivingBase boss)
/*    */   {
/* 22 */     float w = boss.worldObj.rand.nextFloat() * boss.width;
/* 23 */     float d = boss.worldObj.rand.nextFloat() * boss.width;
/* 24 */     float h = boss.worldObj.rand.nextFloat() * boss.height;
/*    */     
/* 26 */     thaumcraft.common.Thaumcraft.proxy.getFX().drawGenericParticles(boss.getEntityBoundingBox().minX + w, boss.getEntityBoundingBox().minY + h, boss.getEntityBoundingBox().minZ + d, 0.0D, 0.03D, 0.0D, 0.9F + boss.worldObj.rand.nextFloat() * 0.1F, 1.0F, 1.0F, 0.7F, false, 160, 10, 1, 8 + boss.worldObj.rand.nextInt(4), 0, 0.7F + boss.worldObj.rand.nextFloat() * 0.2F, 0.0F, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\mods\ChampionModFire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */