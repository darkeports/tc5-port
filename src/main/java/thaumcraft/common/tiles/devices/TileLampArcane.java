/*    */ package thaumcraft.common.tiles.devices;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ITickable;
/*    */ import net.minecraft.world.EnumSkyBlock;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ 
/*    */ public class TileLampArcane extends thaumcraft.api.blocks.TileThaumcraft implements ITickable
/*    */ {
/*    */   public int rad;
/* 14 */   public int rad1 = 0;
/*    */   
/*    */   public void update()
/*    */   {
/* 18 */     if ((!this.worldObj.isRemote) && (this.worldObj.getTotalWorldTime() % 5L == 0L) && (!gettingPower())) {
/* 19 */       int x = this.worldObj.rand.nextInt(16) - this.worldObj.rand.nextInt(16);
/* 20 */       int y = this.worldObj.rand.nextInt(16) - this.worldObj.rand.nextInt(16);
/* 21 */       int z = this.worldObj.rand.nextInt(16) - this.worldObj.rand.nextInt(16);
/* 22 */       BlockPos bp = this.pos.add(x, y, z);
/*    */       
/* 24 */       if (bp.getY() > this.worldObj.getPrecipitationHeight(bp).getY() + 4) {
/* 25 */         bp = this.worldObj.getPrecipitationHeight(bp).up(4);
/*    */       }
/* 27 */       if (bp.getY() < 5) {
/* 28 */         bp = new BlockPos(bp.getX(), 5, bp.getZ());
/*    */       }
/*    */       
/* 31 */       if ((this.worldObj.isAirBlock(bp)) && (this.worldObj.getBlockState(bp) != BlocksTC.effect.getStateFromMeta(2)) && (this.worldObj.getLightFor(EnumSkyBlock.BLOCK, bp) < 11) && (thaumcraft.common.lib.utils.BlockUtils.hasLOS(getWorld(), getPos(), bp)))
/*    */       {
/* 33 */         this.worldObj.setBlockState(bp, BlocksTC.effect.getStateFromMeta(2), 3);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void removeLights() {
/* 39 */     for (int x = -15; x <= 15; x++) {
/* 40 */       for (int y = -15; y <= 15; y++) {
/* 41 */         for (int z = -15; z <= 15; z++) {
/* 42 */           BlockPos bp = this.pos.add(x, y, z);
/* 43 */           if (this.worldObj.getBlockState(bp) == BlocksTC.effect.getStateFromMeta(2)) {
/* 44 */             this.worldObj.setBlockToAir(bp);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileLampArcane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */