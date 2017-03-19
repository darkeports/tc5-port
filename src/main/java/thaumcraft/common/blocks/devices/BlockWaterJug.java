/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import thaumcraft.common.blocks.BlockTCDevice;
/*    */ import thaumcraft.common.tiles.devices.TileWaterJug;
/*    */ 
/*    */ public class BlockWaterJug extends BlockTCDevice
/*    */ {
/*    */   public BlockWaterJug()
/*    */   {
/* 25 */     super(net.minecraft.block.material.Material.rock, TileWaterJug.class);
/* 26 */     setStepSound(Block.soundTypeStone);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 32 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 38 */     return false;
/*    */   }
/*    */   
/*    */   public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
/*    */   {
/* 43 */     setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*    */   {
/* 52 */     if ((!world.isRemote) && 
/* 53 */       (FluidContainerRegistry.isEmptyContainer(player.inventory.getCurrentItem()))) {
/* 54 */       int cap = FluidContainerRegistry.getContainerCapacity(new FluidStack(FluidRegistry.WATER, 1), player.inventory.getCurrentItem());
/* 55 */       ItemStack res = FluidContainerRegistry.fillFluidContainer(new FluidStack(FluidRegistry.WATER, cap), player.inventory.getCurrentItem());
/* 56 */       if (res != null) {
/* 57 */         player.inventory.decrStackSize(player.inventory.currentItem, 1);
/* 58 */         if (!player.inventory.addItemStackToInventory(res)) {
/* 59 */           player.dropPlayerItemWithRandomChoice(res, false);
/*    */         }
/* 61 */         player.inventoryContainer.detectAndSendChanges();
/* 62 */         TileEntity te = world.getTileEntity(pos);
/* 63 */         if ((te != null) && ((te instanceof TileWaterJug))) {
/* 64 */           TileWaterJug tile = (TileWaterJug)te;
/* 65 */           tile.charge -= cap;
/* 66 */           te.markDirty();
/*    */         }
/* 68 */         world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "game.neutral.swim", 0.33F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 74 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockWaterJug.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */