/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.blocks.BlockTCDevice;
/*    */ import thaumcraft.common.container.InventoryArcaneWorkbench;
/*    */ import thaumcraft.common.tiles.crafting.TileArcaneWorkbench;
/*    */ 
/*    */ public class BlockArcaneWorkbench extends BlockTCDevice
/*    */ {
/*    */   public BlockArcaneWorkbench()
/*    */   {
/* 22 */     super(net.minecraft.block.material.Material.wood, TileArcaneWorkbench.class);
/* 23 */     setStepSound(Block.soundTypeWood);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 29 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 35 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*    */   {
/* 41 */     if (world.isRemote) return true;
/* 42 */     player.openGui(Thaumcraft.instance, 13, world, pos.getX(), pos.getY(), pos.getZ());
/* 43 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void breakBlock(World world, BlockPos pos, IBlockState state)
/*    */   {
/* 49 */     TileEntity tileEntity = world.getTileEntity(pos);
/* 50 */     if ((tileEntity != null) && ((tileEntity instanceof TileArcaneWorkbench))) {
/* 51 */       InventoryArcaneWorkbench inventory = ((TileArcaneWorkbench)tileEntity).inventory;
/*    */       
/* 53 */       for (int i = 0; i < 11; i++)
/* 54 */         if (i != 9) {
/* 55 */           ItemStack item = inventory.getStackInSlot(i);
/* 56 */           if ((item != null) && (item.stackSize > 0)) {
/* 57 */             float rx = world.rand.nextFloat() * 0.8F + 0.1F;
/* 58 */             float ry = world.rand.nextFloat() * 0.8F + 0.1F;
/* 59 */             float rz = world.rand.nextFloat() * 0.8F + 0.1F;
/*    */             
/* 61 */             EntityItem entityItem = new EntityItem(world, pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz, item.copy());
/*    */             
/*    */ 
/*    */ 
/* 65 */             float factor = 0.05F;
/* 66 */             entityItem.motionX = (world.rand.nextGaussian() * factor);
/* 67 */             entityItem.motionY = (world.rand.nextGaussian() * factor + 0.20000000298023224D);
/* 68 */             entityItem.motionZ = (world.rand.nextGaussian() * factor);
/* 69 */             world.spawnEntityInWorld(entityItem);
/* 70 */             inventory.setInventorySlotContentsSoftly(i, null);
/*    */           }
/*    */         }
/*    */     }
/* 74 */     super.breakBlock(world, pos, state);
/* 75 */     world.removeTileEntity(pos);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockArcaneWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */