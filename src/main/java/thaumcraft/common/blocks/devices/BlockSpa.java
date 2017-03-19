/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*    */ import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.FluidTank;
/*    */ import thaumcraft.common.blocks.BlockTCDevice;
/*    */ import thaumcraft.common.tiles.devices.TileSpa;
/*    */ 
/*    */ public class BlockSpa extends BlockTCDevice
/*    */ {
/*    */   public BlockSpa()
/*    */   {
/* 23 */     super(net.minecraft.block.material.Material.rock, TileSpa.class);
/* 24 */     setStepSound(net.minecraft.block.Block.soundTypeStone);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*    */   {
/* 41 */     if (world.isRemote) return true;
/* 42 */     net.minecraft.tileentity.TileEntity tileEntity = world.getTileEntity(pos);
/* 43 */     if (((tileEntity instanceof TileSpa)) && (!player.isSneaking())) {
/* 44 */       FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(player.inventory.getCurrentItem());
/* 45 */       if (fs != null) {
/* 46 */         int volume = fs.amount;
/* 47 */         TileSpa tile = (TileSpa)tileEntity;
/* 48 */         if ((tile.tank.getFluidAmount() < tile.tank.getCapacity()) && ((tile.tank.getFluid() == null) || (tile.tank.getFluid().isFluidEqual(fs))))
/*    */         {
/* 50 */           tile.fill(EnumFacing.UP, FluidContainerRegistry.getFluidForFilledItem(player.inventory.getCurrentItem()), true);
/* 51 */           ItemStack emptyContainer = null;
/* 52 */           FluidContainerRegistry.FluidContainerData[] fcs = FluidContainerRegistry.getRegisteredFluidContainerData();
/* 53 */           for (FluidContainerRegistry.FluidContainerData fcd : fcs) {
/* 54 */             if (fcd.filledContainer.isItemEqual(player.inventory.getCurrentItem())) {
/* 55 */               emptyContainer = fcd.emptyContainer.copy();
/*    */             }
/*    */           }
/* 58 */           player.inventory.decrStackSize(player.inventory.currentItem, 1);
/* 59 */           if ((emptyContainer != null) && 
/* 60 */             (!player.inventory.addItemStackToInventory(emptyContainer))) {
/* 61 */             player.dropPlayerItemWithRandomChoice(emptyContainer, false);
/*    */           }
/*    */           
/* 64 */           player.inventoryContainer.detectAndSendChanges();
/* 65 */           tile.markDirty();
/* 66 */           world.markBlockForUpdate(pos);
/* 67 */           world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "game.neutral.swim", 0.33F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F);
/*    */         }
/*    */       }
/*    */       else {
/* 71 */         player.openGui(thaumcraft.common.Thaumcraft.instance, 6, world, pos.getX(), pos.getY(), pos.getZ());
/*    */       }
/*    */     }
/* 74 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockSpa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */