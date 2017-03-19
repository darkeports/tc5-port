/*    */ package thaumcraft.common.entities.construct.golem.parts;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.golems.IGolemAPI;
/*    */ 
/*    */ public class GolemLegWheels implements thaumcraft.api.golems.parts.GolemLeg.ILegFunction
/*    */ {
/* 15 */   public static HashMap<Integer, Float> ani = new HashMap();
/*    */   
/*    */   public void onUpdateTick(IGolemAPI golem)
/*    */   {
/* 19 */     if (golem.getGolemWorld().isRemote)
/*    */     {
/* 21 */       double dist = golem.getGolemEntity().getDistance(golem.getGolemEntity().prevPosX, golem.getGolemEntity().prevPosY, golem.getGolemEntity().prevPosZ);
/* 22 */       float lastRot = 0.0F;
/*    */       
/* 24 */       if (ani.containsKey(Integer.valueOf(golem.getGolemEntity().getEntityId()))) {
/* 25 */         lastRot = ((Float)ani.get(Integer.valueOf(golem.getGolemEntity().getEntityId()))).floatValue();
/*    */       }
/*    */       
/* 28 */       double d0 = golem.getGolemEntity().posX - golem.getGolemEntity().prevPosX;
/* 29 */       double d1 = golem.getGolemEntity().posY - golem.getGolemEntity().prevPosY;
/* 30 */       double d2 = golem.getGolemEntity().posZ - golem.getGolemEntity().prevPosZ;
/* 31 */       float dirTravel = (float)(Math.atan2(d2, d0) * 180.0D / 3.141592653589793D) - 90.0F;
/*    */       
/* 33 */       double dir = 360.0F - (golem.getGolemEntity().rotationYaw - dirTravel);
/* 34 */       lastRot = (float)(lastRot + dist / 1.571D * dir);
/* 35 */       if (lastRot > 360.0F) { lastRot -= 360.0F;
/*    */       }
/* 37 */       ani.put(Integer.valueOf(golem.getGolemEntity().getEntityId()), Float.valueOf(lastRot));
/*    */       
/* 39 */       if ((golem.getGolemEntity().onGround) && (!golem.getGolemEntity().isInWater()) && (dist > 0.25D)) {
/* 40 */         int i = MathHelper.floor_double(golem.getGolemEntity().posX);
/* 41 */         int j = MathHelper.floor_double(golem.getGolemEntity().posY - 0.20000000298023224D);
/* 42 */         int k = MathHelper.floor_double(golem.getGolemEntity().posZ);
/* 43 */         BlockPos blockpos = new BlockPos(i, j, k);
/* 44 */         IBlockState iblockstate = golem.getGolemEntity().worldObj.getBlockState(blockpos);
/* 45 */         Block block = iblockstate.getBlock();
/*    */         
/* 47 */         if (block.getRenderType() != -1)
/*    */         {
/* 49 */           golem.getGolemEntity().worldObj.spawnParticle(net.minecraft.util.EnumParticleTypes.BLOCK_CRACK, golem.getGolemEntity().posX + (golem.getGolemWorld().rand.nextFloat() - 0.5D) * golem.getGolemEntity().width, golem.getGolemEntity().getEntityBoundingBox().minY + 0.1D, golem.getGolemEntity().posZ + (golem.getGolemWorld().rand.nextFloat() - 0.5D) * golem.getGolemEntity().width, -golem.getGolemEntity().motionX * 4.0D, 1.5D, -golem.getGolemEntity().motionZ * 4.0D, new int[] { Block.getStateId(iblockstate) });
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\parts\GolemLegWheels.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */