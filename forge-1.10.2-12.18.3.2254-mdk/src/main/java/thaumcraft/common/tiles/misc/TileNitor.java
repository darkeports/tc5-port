/*    */ package thaumcraft.common.tiles.misc;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ITickable;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.client.fx.FXDispatcher;
/*    */ import thaumcraft.common.CommonProxy;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.blocks.basic.BlockNitor;
/*    */ 
/*    */ public class TileNitor extends net.minecraft.tileentity.TileEntity implements ITickable
/*    */ {
/*    */   public void update()
/*    */   {
/* 20 */     if (this.worldObj.isRemote) {
/* 21 */       IBlockState state = this.worldObj.getBlockState(getPos());
/* 22 */       if (state.getBlock() == BlocksTC.nitor) {
/* 23 */         if (this.worldObj.rand.nextInt(9 - Thaumcraft.proxy.getFX().particleCount(2)) == 0) {
/* 24 */           Thaumcraft.proxy.getFX().wispFX5(this.pos.getX() + 0.5F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.5F, this.pos.getX() + 0.3F + this.worldObj.rand.nextFloat() * 0.4F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.3F + this.worldObj.rand.nextFloat() * 0.4F, 0.5F, true, -0.025F, ((EnumDyeColor)state.getValue(BlockNitor.COLOR)).getMapColor().colorValue);
/*    */         }
/*    */         
/*    */ 
/*    */ 
/* 29 */         if (this.worldObj.rand.nextInt(15 - Thaumcraft.proxy.getFX().particleCount(4)) == 0) {
/* 30 */           Thaumcraft.proxy.getFX().wispFX3(this.pos.getX() + 0.5F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.5F, this.pos.getX() + 0.4F + this.worldObj.rand.nextFloat() * 0.2F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.4F + this.worldObj.rand.nextFloat() * 0.2F, 0.25F, 1, true, -0.02F);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\misc\TileNitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */