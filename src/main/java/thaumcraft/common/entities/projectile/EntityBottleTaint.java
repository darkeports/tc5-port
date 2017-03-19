/*    */ package thaumcraft.common.entities.projectile;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.projectile.EntityThrowable;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.api.potions.PotionFluxTaint;
/*    */ import thaumcraft.client.fx.FXDispatcher;
/*    */ import thaumcraft.common.CommonProxy;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.config.Config;
/*    */ 
/*    */ public class EntityBottleTaint extends EntityThrowable
/*    */ {
/*    */   public EntityBottleTaint(World p_i1788_1_)
/*    */   {
/* 25 */     super(p_i1788_1_);
/*    */   }
/*    */   
/*    */   public EntityBottleTaint(World p_i1790_1_, EntityLivingBase p_i1790_2)
/*    */   {
/* 30 */     super(p_i1790_1_, p_i1790_2);
/*    */   }
/*    */   
/*    */ 
/*    */   protected float getGravityVelocity()
/*    */   {
/* 36 */     return 0.05F;
/*    */   }
/*    */   
/*    */   protected float getVelocity()
/*    */   {
/* 41 */     return 0.5F;
/*    */   }
/*    */   
/*    */ 
/*    */   protected float getInaccuracy()
/*    */   {
/* 47 */     return -20.0F;
/*    */   }
/*    */   
/*    */ 
/*    */   protected void onImpact(MovingObjectPosition p_70184_1_)
/*    */   {
/* 53 */     if (!this.worldObj.isRemote)
/*    */     {
/* 55 */       List ents = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(5.0D, 5.0D, 5.0D));
/*    */       
/*    */ 
/*    */ 
/*    */ 
/* 60 */       if (ents.size() > 0) {
/* 61 */         for (Object ent : ents) {
/* 62 */           EntityLivingBase el = (EntityLivingBase)ent;
/* 63 */           if ((!(el instanceof thaumcraft.api.entities.ITaintedMob)) && (!el.isEntityUndead())) {
/* 64 */             el.addPotionEffect(new PotionEffect(PotionFluxTaint.instance.getId(), 100, 0, false, true));
/*    */           }
/*    */         }
/*    */       }
/*    */       
/* 69 */       for (int a = 0; a < 10; a++) {
/* 70 */         int xx = (int)((this.rand.nextFloat() - this.rand.nextFloat()) * 4.0F);
/* 71 */         int zz = (int)((this.rand.nextFloat() - this.rand.nextFloat()) * 4.0F);
/* 72 */         BlockPos p = getPosition().add(xx, 0, zz);
/* 73 */         if ((this.worldObj.rand.nextBoolean()) && (this.worldObj.getBiomeGenForCoords(p).biomeID != Config.biomeTaintID))
/*    */         {
/* 75 */           thaumcraft.common.lib.utils.Utils.setBiomeAt(this.worldObj, p, thaumcraft.common.lib.world.biomes.BiomeHandler.biomeTaint);
/*    */           
/* 77 */           if ((this.worldObj.isBlockNormalCube(p.down(), false)) && (this.worldObj.getBlockState(p).getBlock().isReplaceable(this.worldObj, p)))
/*    */           {
/* 79 */             this.worldObj.setBlockState(p, BlocksTC.fluxGoo.getDefaultState());
/*    */           } else {
/* 81 */             p = p.down();
/* 82 */             if ((this.worldObj.isBlockNormalCube(p.down(), false)) && (this.worldObj.getBlockState(p).getBlock().isReplaceable(this.worldObj, p)))
/*    */             {
/* 84 */               this.worldObj.setBlockState(p, BlocksTC.fluxGoo.getDefaultState());
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */       
/*    */ 
/* 91 */       setDead();
/*    */     } else {
/* 93 */       for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(100); a++) Thaumcraft.proxy.getFX().taintsplosionFX(this);
/* 94 */       Thaumcraft.proxy.getFX().bottleTaintBreak(this.posX, this.posY, this.posZ);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityBottleTaint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */