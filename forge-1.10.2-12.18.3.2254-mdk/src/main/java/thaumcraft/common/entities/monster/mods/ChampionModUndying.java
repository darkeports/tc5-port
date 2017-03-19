/*    */ package thaumcraft.common.entities.monster.mods;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ChampionModUndying implements IChampionModifierEffect
/*    */ {
/*    */   public float performEffect(EntityLivingBase mob, EntityLivingBase target, DamageSource source, float amount)
/*    */   {
/* 13 */     if (mob.ticksExisted % 20 == 0) {
/* 14 */       mob.heal(1.0F);
/*    */     }
/* 16 */     return amount;
/*    */   }
/*    */   
/*    */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public void showFX(EntityLivingBase boss)
/*    */   {
/* 22 */     if (boss.worldObj.rand.nextBoolean()) return;
/* 23 */     float w = boss.worldObj.rand.nextFloat() * boss.width;
/* 24 */     float d = boss.worldObj.rand.nextFloat() * boss.width;
/* 25 */     float h = boss.worldObj.rand.nextFloat() * boss.height;
/*    */     
/* 27 */     thaumcraft.common.Thaumcraft.proxy.getFX().drawGenericParticles(boss.getEntityBoundingBox().minX + w, boss.getEntityBoundingBox().minY + h, boss.getEntityBoundingBox().minZ + d, 0.0D, 0.03D, 0.0D, 0.1F + boss.worldObj.rand.nextFloat() * 0.1F, 0.8F + boss.worldObj.rand.nextFloat() * 0.2F, 0.1F + boss.worldObj.rand.nextFloat() * 0.1F, 0.9F, true, 21, 4, 1, 4 + boss.worldObj.rand.nextInt(4), 0, 0.5F + boss.worldObj.rand.nextFloat() * 0.2F, 0.0F, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\mods\ChampionModUndying.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */