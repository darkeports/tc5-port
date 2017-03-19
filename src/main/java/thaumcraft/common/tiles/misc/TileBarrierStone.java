/*    */ package thaumcraft.common.tiles.misc;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ITickable;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ 
/*    */ public class TileBarrierStone extends net.minecraft.tileentity.TileEntity implements ITickable
/*    */ {
/* 17 */   int count = 0;
/*    */   
/*    */   public boolean gettingPower() {
/* 20 */     return this.worldObj.isBlockIndirectlyGettingPowered(this.pos) > 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public void update()
/*    */   {
/* 26 */     if (!this.worldObj.isRemote) {
/* 27 */       if (this.count == 0) { this.count = this.worldObj.rand.nextInt(100);
/*    */       }
/* 29 */       if ((this.count % 5 == 0) && (!gettingPower())) {
/* 30 */         List<EntityLivingBase> targets = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX() + 1, this.pos.getY() + 3, this.pos.getZ() + 1).expand(0.1D, 0.1D, 0.1D));
/*    */         
/* 32 */         if (targets.size() > 0) {
/* 33 */           for (EntityLivingBase e : targets) {
/* 34 */             if ((!e.onGround) && (!(e instanceof EntityPlayer))) {
/* 35 */               e.addVelocity(-MathHelper.sin((e.rotationYaw + 180.0F) * 3.1415927F / 180.0F) * 0.2F, -0.1D, MathHelper.cos((e.rotationYaw + 180.0F) * 3.1415927F / 180.0F) * 0.2F);
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/* 40 */       if (++this.count % 100 == 0) {
/* 41 */         if ((this.worldObj.getBlockState(this.pos.up(1)) != BlocksTC.barrier.getDefaultState()) && (this.worldObj.isAirBlock(this.pos.up(1)))) {
/* 42 */           this.worldObj.setBlockState(this.pos.up(1), BlocksTC.barrier.getDefaultState(), 3);
/*    */         }
/* 44 */         if ((this.worldObj.getBlockState(this.pos.up(2)) != BlocksTC.barrier.getDefaultState()) && (this.worldObj.isAirBlock(this.pos.up(2)))) {
/* 45 */           this.worldObj.setBlockState(this.pos.up(2), BlocksTC.barrier.getDefaultState(), 3);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\misc\TileBarrierStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */