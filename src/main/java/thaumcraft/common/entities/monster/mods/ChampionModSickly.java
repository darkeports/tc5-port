/*    */ package thaumcraft.common.entities.monster.mods;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ChampionModSickly implements IChampionModifierEffect
/*    */ {
/*    */   public float performEffect(EntityLivingBase boss, EntityLivingBase target, DamageSource source, float amount)
/*    */   {
/* 15 */     if (boss.worldObj.rand.nextFloat() < 0.4F) {
/* 16 */       target.addPotionEffect(new net.minecraft.potion.PotionEffect(Potion.hunger.id, 500));
/*    */     }
/* 18 */     return amount;
/*    */   }
/*    */   
/*    */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public void showFX(EntityLivingBase boss)
/*    */   {
/* 24 */     if (boss.worldObj.rand.nextBoolean()) return;
/* 25 */     float w = boss.worldObj.rand.nextFloat() * boss.width;
/* 26 */     float d = boss.worldObj.rand.nextFloat() * boss.width;
/* 27 */     float h = boss.worldObj.rand.nextFloat() * boss.height;
/*    */     
/* 29 */     Thaumcraft.proxy.getFX().drawGenericParticles(boss.getEntityBoundingBox().minX + w, boss.getEntityBoundingBox().minY + h, boss.getEntityBoundingBox().minZ + d, 0.0D, -0.02D, 0.0D, 0.2F, 0.6F + boss.worldObj.rand.nextFloat() * 0.1F, 0.2F + boss.worldObj.rand.nextFloat() * 0.1F, 0.5F, false, 1, 4, 2, 5 + boss.worldObj.rand.nextInt(4), 0, 0.9F + boss.worldObj.rand.nextFloat() * 0.3F, 0.0F, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\mods\ChampionModSickly.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */