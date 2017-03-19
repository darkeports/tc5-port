/*    */ package thaumcraft.common.entities.monster.mods;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ChampionModSpined implements IChampionModifierEffect
/*    */ {
/*    */   public float performEffect(EntityLivingBase boss, EntityLivingBase target, DamageSource source, float amount)
/*    */   {
/* 13 */     if ((target == null) || (source.damageType.equalsIgnoreCase("thorns"))) return amount;
/* 14 */     target.attackEntityFrom(DamageSource.causeThornsDamage(boss), 1 + boss.worldObj.rand.nextInt(3));
/* 15 */     target.playSound("damage.thorns", 0.5F, 1.0F);
/* 16 */     return amount;
/*    */   }
/*    */   
/*    */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public void showFX(EntityLivingBase boss)
/*    */   {
/* 22 */     if (boss.worldObj.rand.nextBoolean()) { return;
/*    */     }
/* 24 */     float w = boss.worldObj.rand.nextFloat() * boss.width;
/* 25 */     float d = boss.worldObj.rand.nextFloat() * boss.width;
/* 26 */     float h = boss.worldObj.rand.nextFloat() * boss.height;
/* 27 */     int p = 176 + boss.worldObj.rand.nextInt(4) * 3;
/* 28 */     thaumcraft.common.Thaumcraft.proxy.getFX().drawGenericParticles(boss.getEntityBoundingBox().minX + w, boss.getEntityBoundingBox().minY + h, boss.getEntityBoundingBox().minZ + d, 0.0D, 0.0D, 0.0D, 0.5F + boss.worldObj.rand.nextFloat() * 0.2F, 0.1F + boss.worldObj.rand.nextFloat() * 0.2F, 0.1F + boss.worldObj.rand.nextFloat() * 0.2F, 0.7F, false, p, 3, 1, 3, 0, 1.2F + boss.worldObj.rand.nextFloat() * 0.3F, 0.0F, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\mods\ChampionModSpined.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */