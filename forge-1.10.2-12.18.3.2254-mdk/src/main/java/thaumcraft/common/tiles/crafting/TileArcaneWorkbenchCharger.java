/*    */ package thaumcraft.common.tiles.crafting;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ import thaumcraft.api.items.IRechargable;
/*    */ import thaumcraft.common.container.InventoryArcaneWorkbench;
/*    */ 
/*    */ public class TileArcaneWorkbenchCharger extends thaumcraft.api.blocks.TileThaumcraft implements net.minecraft.util.ITickable
/*    */ {
/* 12 */   int counter = 0;
/*    */   
/*    */   public void update()
/*    */   {
/* 16 */     if ((!this.worldObj.isRemote) && (this.counter++ % 10 == 0)) {
/* 17 */       net.minecraft.tileentity.TileEntity te = this.worldObj.getTileEntity(this.pos.down());
/* 18 */       if ((te != null) && ((te instanceof TileArcaneWorkbench))) {
/* 19 */         TileArcaneWorkbench tm = (TileArcaneWorkbench)te;
/* 20 */         if ((tm.inventory.getStackInSlot(10) != null) && ((tm.inventory.getStackInSlot(10).getItem() instanceof IRechargable)))
/*    */         {
/* 22 */           IRechargable wand = (IRechargable)tm.inventory.getStackInSlot(10).getItem();
/*    */           
/* 24 */           Aspect aspect = wand.handleRecharge(getWorld(), tm.inventory.getStackInSlot(10), this.pos, null, 5);
/* 25 */           if (aspect != null) {
/* 26 */             this.worldObj.addBlockEvent(this.pos, getBlockType(), 5, aspect.getColor());
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean receiveClientEvent(int i, int j)
/*    */   {
/* 36 */     if (i == 5)
/*    */     {
/* 38 */       if (this.worldObj.isRemote) {
/* 39 */         thaumcraft.common.Thaumcraft.proxy.getFX().visSparkle(this.pos.getX() + getWorld().rand.nextInt(3) - getWorld().rand.nextInt(3), this.pos.getY() + getWorld().rand.nextInt(3), this.pos.getZ() + getWorld().rand.nextInt(3) - getWorld().rand.nextInt(3), this.pos.getX(), this.pos.getY(), this.pos.getZ(), j);
/*    */       }
/*    */       
/*    */ 
/*    */ 
/*    */ 
/* 45 */       return true;
/*    */     }
/*    */     
/* 48 */     return super.receiveClientEvent(i, j);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\crafting\TileArcaneWorkbenchCharger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */