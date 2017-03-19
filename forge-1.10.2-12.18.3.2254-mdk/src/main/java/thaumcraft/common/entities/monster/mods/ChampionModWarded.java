/*    */ package thaumcraft.common.entities.monster.mods;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ChampionModWarded implements IChampionModifierEffect
/*    */ {
/*    */   public float performEffect(EntityLivingBase mob, EntityLivingBase target, DamageSource source, float amount)
/*    */   {
/* 14 */     if ((mob.hurtResistantTime <= 0) && (mob.ticksExisted % 25 == 0)) {
/* 15 */       int bh = (int)mob.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.maxHealth).getBaseValue() / 2;
/* 16 */       if (mob.getAbsorptionAmount() < bh)
/* 17 */         mob.setAbsorptionAmount(mob.getAbsorptionAmount() + 1.0F);
/*    */     }
/* 19 */     return amount;
/*    */   }
/*    */   
/*    */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public void showFX(EntityLivingBase boss)
/*    */   {
/* 25 */     if (boss.worldObj.rand.nextBoolean()) return;
/* 26 */     float w = boss.worldObj.rand.nextFloat() * boss.width;
/* 27 */     float d = boss.worldObj.rand.nextFloat() * boss.width;
/* 28 */     float h = boss.worldObj.rand.nextFloat() * boss.height;
/*    */     
/* 30 */     Thaumcraft.proxy.getFX().drawGenericParticles(boss.getEntityBoundingBox().minX + w, boss.getEntityBoundingBox().minY + h, boss.getEntityBoundingBox().minZ + d, 0.0D, 0.0D, 0.0D, 0.5F + boss.worldObj.rand.nextFloat() * 0.1F, 0.5F + boss.worldObj.rand.nextFloat() * 0.1F, 0.5F + boss.worldObj.rand.nextFloat() * 0.1F, 0.6F, true, 21, 4, 1, 4 + boss.worldObj.rand.nextInt(4), 0, 0.8F + boss.worldObj.rand.nextFloat() * 0.3F, 0.0F, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\mods\ChampionModWarded.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */