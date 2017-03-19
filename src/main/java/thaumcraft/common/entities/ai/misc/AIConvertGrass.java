/*    */ package thaumcraft.common.entities.ai.misc;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ 
/*    */ public class AIConvertGrass extends net.minecraft.entity.ai.EntityAIBase
/*    */ {
/*    */   private EntityLiving entity;
/*    */   private World world;
/* 16 */   int field_48399_a = 0;
/*    */   
/*    */   public AIConvertGrass(EntityLiving par1EntityLiving)
/*    */   {
/* 20 */     this.entity = par1EntityLiving;
/* 21 */     this.world = par1EntityLiving.worldObj;
/* 22 */     setMutexBits(7);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 30 */     if (this.entity.getRNG().nextInt(250) != 0)
/*    */     {
/* 32 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 36 */     BlockPos bp = new BlockPos(this.entity);
/* 37 */     IBlockState bs = this.world.getBlockState(bp);
/* 38 */     return (bs.getBlock() == Blocks.tallgrass) && (bs.getBlock().getMetaFromState(bs) == 1);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 49 */     this.field_48399_a = 40;
/* 50 */     this.world.setEntityState(this.entity, (byte)10);
/* 51 */     this.entity.getNavigator().clearPathEntity();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void resetTask()
/*    */   {
/* 59 */     this.field_48399_a = 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 67 */     return this.field_48399_a > 0;
/*    */   }
/*    */   
/*    */   public int func_48396_h()
/*    */   {
/* 72 */     return this.field_48399_a;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 80 */     this.field_48399_a = Math.max(0, this.field_48399_a - 1);
/*    */     
/* 82 */     if (this.field_48399_a == 4)
/*    */     {
/* 84 */       BlockPos bp = new BlockPos(this.entity);
/* 85 */       IBlockState bs = this.world.getBlockState(bp);
/*    */       
/* 87 */       if (bs.getBlock() == Blocks.tallgrass)
/*    */       {
/* 89 */         this.world.playAuxSFX(2001, bp, Block.getIdFromBlock(Blocks.grass) + 4096);
/* 90 */         this.world.setBlockToAir(bp);
/* 91 */         this.world.setBlockState(bp, BlocksTC.taintFibre.getDefaultState());
/* 92 */         this.entity.eatGrassBonus();
/*    */       }
/* 94 */       else if (this.world.getBlockState(bp.down()).getBlock() == Blocks.grass)
/*    */       {
/* 96 */         this.world.playAuxSFX(2001, bp.down(), Block.getIdFromBlock(Blocks.grass));
/* 97 */         this.world.setBlockState(bp, BlocksTC.taintFibre.getDefaultState());
/* 98 */         this.entity.eatGrassBonus();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\ai\misc\AIConvertGrass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */