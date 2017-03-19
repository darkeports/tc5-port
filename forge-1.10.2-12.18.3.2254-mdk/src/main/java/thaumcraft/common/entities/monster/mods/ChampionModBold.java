/*    */ package thaumcraft.common.entities.monster.mods;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.client.fx.ParticleEngine;
/*    */ import thaumcraft.client.fx.particles.FXSpark;
/*    */ 
/*    */ public class ChampionModBold implements IChampionModifierEffect
/*    */ {
/*    */   public float performEffect(EntityLivingBase boss, EntityLivingBase target, DamageSource source, float ammount)
/*    */   {
/* 16 */     return 0.0F;
/*    */   }
/*    */   
/*    */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public void showFX(EntityLivingBase boss)
/*    */   {
/* 22 */     if (boss.worldObj.rand.nextBoolean()) return;
/* 23 */     float w = boss.worldObj.rand.nextFloat() * boss.width;
/* 24 */     float d = boss.worldObj.rand.nextFloat() * boss.width;
/* 25 */     float h = boss.worldObj.rand.nextFloat() * boss.height / 3.0F;
/*    */     
/* 27 */     FXSpark ef = new FXSpark(boss.worldObj, boss.getEntityBoundingBox().minX + w, boss.getEntityBoundingBox().minY + h, boss.getEntityBoundingBox().minZ + d, 0.2F);
/* 28 */     ef.setRBGColorF(0.3F - boss.worldObj.rand.nextFloat() * 0.1F, 0.0F, 0.8F + boss.worldObj.rand.nextFloat() * 0.2F);
/* 29 */     ParticleEngine.instance.addEffect(boss.worldObj, ef);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\mods\ChampionModBold.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */