/*    */ package thaumcraft.common.entities.construct.golem.ai;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.pathfinding.PathPoint;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.pathfinder.NodeProcessor;
/*    */ 
/*    */ public class FlightNodeProcessor extends NodeProcessor
/*    */ {
/*    */   public void initProcessor(IBlockAccess p_176162_1_, Entity p_176162_2_)
/*    */   {
/* 18 */     super.initProcessor(p_176162_1_, p_176162_2_);
/*    */   }
/*    */   
/*    */ 
/*    */   public void postProcess()
/*    */   {
/* 24 */     super.postProcess();
/*    */   }
/*    */   
/*    */ 
/*    */   public PathPoint getPathPointTo(Entity p_176161_1_)
/*    */   {
/* 30 */     return openPoint(MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minX), MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minY + 0.5D), MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minZ));
/*    */   }
/*    */   
/*    */ 
/*    */   public PathPoint getPathPointToCoords(Entity p_176160_1_, double p_176160_2_, double p_176160_4_, double p_176160_6_)
/*    */   {
/* 36 */     return openPoint(MathHelper.floor_double(p_176160_2_ - p_176160_1_.width / 2.0F), MathHelper.floor_double(p_176160_4_ + 0.5D), MathHelper.floor_double(p_176160_6_ - p_176160_1_.width / 2.0F));
/*    */   }
/*    */   
/*    */ 
/*    */   public int findPathOptions(PathPoint[] p_176164_1_, Entity p_176164_2_, PathPoint p_176164_3_, PathPoint p_176164_4_, float p_176164_5_)
/*    */   {
/* 42 */     int i = 0;
/* 43 */     EnumFacing[] aenumfacing = EnumFacing.values();
/* 44 */     int j = aenumfacing.length;
/*    */     
/* 46 */     for (int k = 0; k < j; k++)
/*    */     {
/* 48 */       EnumFacing enumfacing = aenumfacing[k];
/* 49 */       PathPoint pathpoint2 = func_176185_a(p_176164_2_, p_176164_3_.xCoord + enumfacing.getFrontOffsetX(), p_176164_3_.yCoord + enumfacing.getFrontOffsetY(), p_176164_3_.zCoord + enumfacing.getFrontOffsetZ());
/*    */       
/* 51 */       if ((pathpoint2 != null) && (!pathpoint2.visited) && (pathpoint2.distanceTo(p_176164_4_) < p_176164_5_))
/*    */       {
/* 53 */         p_176164_1_[(i++)] = pathpoint2;
/*    */       }
/*    */     }
/*    */     
/* 57 */     return i;
/*    */   }
/*    */   
/*    */   private PathPoint func_176185_a(Entity p_176185_1_, int p_176185_2_, int p_176185_3_, int p_176185_4_)
/*    */   {
/* 62 */     int l = func_176186_b(p_176185_1_, p_176185_2_, p_176185_3_, p_176185_4_);
/* 63 */     return l == -1 ? openPoint(p_176185_2_, p_176185_3_, p_176185_4_) : null;
/*    */   }
/*    */   
/*    */   private int func_176186_b(Entity p_176186_1_, int p_176186_2_, int p_176186_3_, int p_176186_4_)
/*    */   {
/* 68 */     for (int l = p_176186_2_; l < p_176186_2_ + this.entitySizeX; l++)
/*    */     {
/* 70 */       for (int i1 = p_176186_3_; i1 < p_176186_3_ + this.entitySizeY; i1++)
/*    */       {
/* 72 */         for (int j1 = p_176186_4_; j1 < p_176186_4_ + this.entitySizeZ; j1++)
/*    */         {
/* 74 */           BlockPos blockpos = new BlockPos(l, i1, j1);
/* 75 */           Block block = this.blockaccess.getBlockState(blockpos).getBlock();
/*    */           
/* 77 */           if ((!this.blockaccess.isAirBlock(blockpos)) && (!block.isPassable(this.blockaccess, blockpos)))
/*    */           {
/* 79 */             return 0;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 85 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\FlightNodeProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */