/*    */ package thaumcraft.common.tiles.misc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ITickable;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.entities.IEldritchMob;
/*    */ import thaumcraft.client.fx.FXDispatcher;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.lib.utils.EntityUtils;
/*    */ 
/*    */ public class TileEldritchObelisk extends thaumcraft.api.blocks.TileThaumcraft implements ITickable
/*    */ {
/*    */   @SideOnly(Side.CLIENT)
/*    */   public AxisAlignedBB getRenderBoundingBox()
/*    */   {
/* 25 */     return AxisAlignedBB.fromBounds(getPos().getX() - 0.3D, getPos().getY() - 0.3D, getPos().getZ() - 0.3D, getPos().getX() + 1.3D, getPos().getY() + 5.3D, getPos().getZ() + 1.3D);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public double getMaxRenderDistanceSquared()
/*    */   {
/* 32 */     return 9216.0D;
/*    */   }
/*    */   
/* 35 */   private int counter = 0;
/*    */   
/*    */ 
/*    */   public void update()
/*    */   {
/* 40 */     if ((!this.worldObj.isRemote) && (this.counter % 20 == 0))
/*    */     {
/* 42 */       ArrayList<Entity> list = EntityUtils.getEntitiesInRange(getWorld(), getPos().getX() + 0.5D, getPos().getY(), getPos().getZ() + 0.5D, null, EntityLivingBase.class, 6.0D);
/* 43 */       if ((list != null) && (list.size() > 0)) {
/* 44 */         for (Entity e : list) {
/* 45 */           if (((e instanceof IEldritchMob)) && ((e instanceof EntityLivingBase)) && (!((EntityLivingBase)e).isPotionActive(Potion.regeneration.id)))
/*    */             try
/*    */             {
/* 48 */               ((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 40, 0, true, true));
/* 49 */               ((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 40, 0, true, true));
/*    */             } catch (Exception e1) {}
/*    */         }
/*    */       }
/*    */     }
/* 54 */     if (this.worldObj.isRemote)
/*    */     {
/* 56 */       ArrayList<Entity> list = EntityUtils.getEntitiesInRange(getWorld(), getPos().getX() + 0.5D, getPos().getY(), getPos().getZ() + 0.5D, null, EntityLivingBase.class, 6.0D);
/* 57 */       if ((list != null) && (list.size() > 0)) {
/* 58 */         for (Entity e : list) {
/* 59 */           if (((e instanceof IEldritchMob)) && ((e instanceof EntityLivingBase))) {
/* 60 */             Thaumcraft.proxy.getFX().wispFX4(getPos().getX() + 0.5D, getPos().getY() + 1 + this.worldObj.rand.nextFloat() * 3.0F, getPos().getZ() + 0.5D, e, 5, true, 1.0F);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\misc\TileEldritchObelisk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */