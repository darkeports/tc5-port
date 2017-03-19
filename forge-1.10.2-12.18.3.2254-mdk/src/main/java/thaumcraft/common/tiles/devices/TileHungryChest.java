/*    */ package thaumcraft.common.tiles.devices;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.blocks.devices.BlockHungryChest;
/*    */ 
/*    */ 
/*    */ public class TileHungryChest
/*    */   extends TileEntityChest
/*    */ {
/*    */   public boolean receiveClientEvent(int par1, int par2)
/*    */   {
/* 15 */     if (par1 == 2)
/*    */     {
/* 17 */       if (this.lidAngle < par2 / 10.0F) this.lidAngle = (par2 / 10.0F);
/* 18 */       return true;
/*    */     }
/* 20 */     return super.receiveClientEvent(par1, par2);
/*    */   }
/*    */   
/*    */ 
/*    */   public void checkForAdjacentChests()
/*    */   {
/* 26 */     if (!this.adjacentChestChecked)
/*    */     {
/* 28 */       this.adjacentChestChecked = true;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean canRenderBreaking()
/*    */   {
/* 34 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void closeInventory(EntityPlayer player)
/*    */   {
/* 40 */     if ((!player.isSpectator()) && ((getBlockType() instanceof BlockHungryChest)))
/*    */     {
/* 42 */       this.numPlayersUsing -= 1;
/* 43 */       this.worldObj.addBlockEvent(this.pos, getBlockType(), 1, this.numPlayersUsing);
/* 44 */       this.worldObj.notifyNeighborsOfStateChange(this.pos, getBlockType());
/* 45 */       this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), getBlockType());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileHungryChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */