/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.blocks.BlockTCDevice;
/*    */ import thaumcraft.common.lib.utils.InventoryUtils;
/*    */ import thaumcraft.common.tiles.devices.TileRechargePedestal;
/*    */ 
/*    */ public class BlockRechargePedestal extends BlockTCDevice
/*    */ {
/*    */   public BlockRechargePedestal()
/*    */   {
/* 20 */     super(net.minecraft.block.material.Material.rock, TileRechargePedestal.class);
/* 21 */     setStepSound(Block.soundTypeStone);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 27 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 33 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*    */   {
/* 42 */     if (world.isRemote) { return true;
/*    */     }
/* 44 */     net.minecraft.tileentity.TileEntity tile = world.getTileEntity(pos);
/*    */     
/* 46 */     if ((tile != null) && ((tile instanceof TileRechargePedestal)))
/*    */     {
/* 48 */       TileRechargePedestal ped = (TileRechargePedestal)tile;
/* 49 */       if ((ped.getStackInSlot(0) == null) && (player.inventory.getCurrentItem() != null) && ((player.inventory.getCurrentItem().getItem() instanceof thaumcraft.api.items.IRechargable)))
/*    */       {
/*    */ 
/* 52 */         ItemStack i = player.getCurrentEquippedItem().copy();
/* 53 */         i.stackSize = 1;
/* 54 */         ped.setInventorySlotContents(0, i);
/* 55 */         player.getCurrentEquippedItem().stackSize -= 1;
/* 56 */         if (player.getCurrentEquippedItem().stackSize == 0) {
/* 57 */           player.setCurrentItemOrArmor(0, null);
/*    */         }
/* 59 */         player.inventory.markDirty();
/* 60 */         world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.6F);
/*    */         
/*    */ 
/* 63 */         return true; }
/* 64 */       if (ped.getStackInSlot(0) != null) {
/* 65 */         InventoryUtils.dropItemsAtEntity(world, pos, player);
/* 66 */         world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);
/*    */         
/*    */ 
/* 69 */         return true;
/*    */       }
/*    */     }
/*    */     
/* 73 */     return super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockRechargePedestal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */