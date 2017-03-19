/*    */ package thaumcraft.common.entities.monster.mods;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ChampionModArmored implements IChampionModifierEffect
/*    */ {
/*    */   public float performEffect(EntityLivingBase mob, EntityLivingBase target, net.minecraft.util.DamageSource source, float amount)
/*    */   {
/* 11 */     if (!source.isUnblockable()) {
/* 12 */       float f1 = amount * 19.0F;
/* 13 */       amount = f1 / 25.0F;
/*    */     }
/* 15 */     return amount;
/*    */   }
/*    */   
/*    */   public void showFX(EntityLivingBase boss)
/*    */   {
/* 20 */     if (boss.worldObj.rand.nextInt(4) != 0) return;
/* 21 */     float w = boss.worldObj.rand.nextFloat() * boss.width;
/* 22 */     float d = boss.worldObj.rand.nextFloat() * boss.width;
/* 23 */     float h = boss.worldObj.rand.nextFloat() * boss.height;
/*    */     
/* 25 */     thaumcraft.common.Thaumcraft.proxy.getFX().drawGenericParticles(boss.getEntityBoundingBox().minX + w, boss.getEntityBoundingBox().minY + h, boss.getEntityBoundingBox().minZ + d, 0.0D, 0.0D, 0.0D, 0.9F, 0.9F, 0.9F + boss.worldObj.rand.nextFloat() * 0.1F, 0.7F, false, 112, 9, 1, 5 + boss.worldObj.rand.nextInt(4), 0, 0.6F + boss.worldObj.rand.nextFloat() * 0.2F, 0.0F, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\mods\ChampionModArmored.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */