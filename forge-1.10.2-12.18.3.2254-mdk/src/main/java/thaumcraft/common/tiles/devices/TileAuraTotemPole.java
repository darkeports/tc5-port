/*    */ package thaumcraft.common.tiles.devices;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.common.tiles.TileThaumcraftInventory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileAuraTotemPole
/*    */   extends TileThaumcraftInventory
/*    */ {
/*    */   public boolean isItemValidForSlot(int par1, ItemStack stack)
/*    */   {
/* 20 */     return (stack != null) && (stack.getItem() == ItemsTC.shard);
/*    */   }
/*    */   
/*    */   public ItemStack decrStackSize(int par1, int par2)
/*    */   {
/* 25 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ItemStack getStackInSlot(int par1)
/*    */   {
/* 32 */     TileEntity tile = this.worldObj.getTileEntity(getPos().up());
/* 33 */     if ((tile != null) && (((tile instanceof TileAuraTotem)) || ((tile instanceof TileAuraTotemPole)))) {
/* 34 */       return ((TileThaumcraftInventory)tile).getStackInSlot(par1);
/*    */     }
/* 36 */     return null;
/*    */   }
/*    */   
/*    */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*    */   {
/* 41 */     TileEntity tile = this.worldObj.getTileEntity(getPos().up());
/* 42 */     if ((tile != null) && (((tile instanceof TileAuraTotem)) || ((tile instanceof TileAuraTotemPole)))) {
/* 43 */       ((TileThaumcraftInventory)tile).setInventorySlotContents(par1, par2ItemStack);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean canInsertItem(int par1, ItemStack par2ItemStack, EnumFacing par3)
/*    */   {
/* 49 */     TileEntity tile = this.worldObj.getTileEntity(getPos().up());
/* 50 */     if ((tile != null) && (((tile instanceof TileAuraTotem)) || ((tile instanceof TileAuraTotemPole)))) {
/* 51 */       return ((TileThaumcraftInventory)tile).canInsertItem(par1, par2ItemStack, par3);
/*    */     }
/* 53 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean canExtractItem(int par1, ItemStack par2ItemStack, EnumFacing par3)
/*    */   {
/* 59 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileAuraTotemPole.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */