/*    */ package thaumcraft.common.blocks.basic;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.api.wands.IWand;
/*    */ import thaumcraft.common.blocks.BlockTC;
/*    */ import thaumcraft.common.tiles.crafting.TileArcaneWorkbench;
/*    */ import thaumcraft.common.tiles.crafting.TileResearchTable;
/*    */ 
/*    */ public class BlockTable extends BlockTC
/*    */ {
/*    */   public BlockTable(Material mat)
/*    */   {
/* 23 */     super(mat);
/*    */   }
/*    */   
/*    */   public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
/*    */   {
/* 28 */     if (side == EnumFacing.UP) return true;
/* 29 */     return super.isSideSolid(world, pos, side);
/*    */   }
/*    */   
/*    */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
/*    */   {
/* 34 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 40 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 46 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*    */   {
/* 54 */     if (world.isRemote) { return true;
/*    */     }
/* 56 */     if ((player.inventory.getCurrentItem() != null) && (this == BlocksTC.tableWood) && ((player.inventory.getCurrentItem().getItem() instanceof thaumcraft.api.items.IScribeTools)))
/*    */     {
/* 58 */       IBlockState bs = BlocksTC.researchTable.getDefaultState();
/* 59 */       bs = bs.withProperty(thaumcraft.common.blocks.IBlockFacing.FACING, player.getHorizontalFacing());
/* 60 */       world.setBlockState(pos, bs);
/* 61 */       TileResearchTable tile = (TileResearchTable)world.getTileEntity(pos);
/* 62 */       tile.setInventorySlotContents(0, player.inventory.getCurrentItem().copy());
/* 63 */       player.inventory.getCurrentItem().stackSize -= 1;
/* 64 */       player.setCurrentItemOrArmor(0, null);
/* 65 */       player.inventory.markDirty();
/* 66 */       tile.markDirty();
/* 67 */       world.markBlockForUpdate(pos);
/*    */     }
/*    */     
/* 70 */     if ((player.inventory.getCurrentItem() != null) && (this == BlocksTC.tableWood) && ((player.inventory.getCurrentItem().getItem() instanceof IWand)) && (!((IWand)player.inventory.getCurrentItem().getItem()).isStaff(player.inventory.getCurrentItem())))
/*    */     {
/*    */ 
/* 73 */       world.setBlockState(pos, BlocksTC.arcaneWorkbench.getDefaultState());
/* 74 */       TileArcaneWorkbench tile = (TileArcaneWorkbench)world.getTileEntity(pos);
/* 75 */       tile.inventory.setInventorySlotContentsSoftly(10, player.inventory.getCurrentItem().copy());
/* 76 */       player.inventory.getCurrentItem().stackSize -= 1;
/* 77 */       player.setCurrentItemOrArmor(0, null);
/* 78 */       player.inventory.markDirty();
/* 79 */       tile.markDirty();
/* 80 */       world.markBlockForUpdate(pos);
/*    */     }
/*    */     
/* 83 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */