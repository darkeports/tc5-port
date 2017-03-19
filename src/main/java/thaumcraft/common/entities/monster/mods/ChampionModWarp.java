/*    */ package thaumcraft.common.entities.monster.mods;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ChampionModWarp implements IChampionModifierEffect
/*    */ {
/*    */   public float performEffect(EntityLivingBase boss, EntityLivingBase target, DamageSource source, float amount)
/*    */   {
/* 16 */     if ((boss.worldObj.rand.nextFloat() < 0.33F) && ((target instanceof EntityPlayer))) {
/* 17 */       thaumcraft.api.research.ResearchHelper.addWarpToPlayer((EntityPlayer)target, 1 + boss.worldObj.rand.nextInt(3), thaumcraft.api.internal.EnumWarpType.TEMPORARY);
/*    */     }
/* 19 */     return amount;
/*    */   }
/*    */   
/*    */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public void showFX(EntityLivingBase boss)
/*    */   {
/* 25 */     if (boss.worldObj.rand.nextBoolean()) return;
/* 26 */     float w = boss.worldObj.rand.nextFloat() * boss.width;
/* 27 */     float d = boss.worldObj.rand.nextFloat() * boss.width;
/* 28 */     float h = boss.worldObj.rand.nextFloat() * boss.height;
/*    */     
/* 30 */     Thaumcraft.proxy.getFX().drawGenericParticles(boss.getEntityBoundingBox().minX + w, boss.getEntityBoundingBox().minY + h, boss.getEntityBoundingBox().minZ + d, 0.0D, 0.0D, 0.0D, 0.8F + boss.worldObj.rand.nextFloat() * 0.2F, 0.0F, 0.9F + boss.worldObj.rand.nextFloat() * 0.1F, 0.7F, true, 72, 8, 1, 10 + boss.worldObj.rand.nextInt(4), 0, 0.6F + boss.worldObj.rand.nextFloat() * 0.4F, 0.0F, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\mods\ChampionModWarp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */