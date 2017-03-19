/*    */ package thaumcraft.common.entities.monster.mods;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ChampionModMighty implements IChampionModifierEffect
/*    */ {
/*    */   public float performEffect(EntityLivingBase boss, EntityLivingBase target, DamageSource source, float ammount)
/*    */   {
/* 15 */     return 0.0F;
/*    */   }
/*    */   
/*    */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public void showFX(EntityLivingBase boss)
/*    */   {
/* 21 */     if (boss.worldObj.rand.nextFloat() > 0.3F) return;
/* 22 */     float w = boss.worldObj.rand.nextFloat() * boss.width;
/* 23 */     float d = boss.worldObj.rand.nextFloat() * boss.width;
/* 24 */     float h = boss.worldObj.rand.nextFloat() * boss.height;
/* 25 */     int p = 176 + boss.worldObj.rand.nextInt(4) * 3;
/* 26 */     Thaumcraft.proxy.getFX().drawGenericParticles(boss.getEntityBoundingBox().minX + w, boss.getEntityBoundingBox().minY + h, boss.getEntityBoundingBox().minZ + d, 0.0D, 0.0D, 0.0D, 0.8F + boss.worldObj.rand.nextFloat() * 0.2F, 0.8F + boss.worldObj.rand.nextFloat() * 0.2F, 0.8F + boss.worldObj.rand.nextFloat() * 0.2F, 0.7F, false, p, 3, 1, 4 + boss.worldObj.rand.nextInt(3), 0, 1.0F + boss.worldObj.rand.nextFloat() * 0.3F, 0.0F, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\mods\ChampionModMighty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */