/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockPistonBase;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.api.wands.IWand;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.blocks.BlockTCDevice;
/*    */ import thaumcraft.common.blocks.IBlockFacing;
/*    */ import thaumcraft.common.tiles.devices.TileArcaneBore;
/*    */ 
/*    */ public class BlockArcaneBore extends BlockTCDevice implements IBlockFacing
/*    */ {
/*    */   public BlockArcaneBore()
/*    */   {
/* 23 */     super(net.minecraft.block.material.Material.wood, TileArcaneBore.class);
/* 24 */     setStepSound(Block.soundTypeWood);
/*    */   }
/*    */   
/*    */   public int damageDropped(IBlockState state)
/*    */   {
/* 29 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*    */   {
/* 35 */     IBlockState bs = getDefaultState();
/* 36 */     bs = bs.withProperty(IBlockFacing.FACING, BlockPistonBase.getFacingFromEntity(worldIn, pos, placer));
/* 37 */     return bs;
/*    */   }
/*    */   
/*    */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
/*    */   {
/* 42 */     if ((!world.isRemote) && (!playerIn.isSneaking()) && ((playerIn.getHeldItem() == null) || (!(playerIn.getHeldItem().getItem() instanceof IWand))))
/*    */     {
/* 44 */       playerIn.openGui(Thaumcraft.instance, 14, world, pos.getX(), pos.getY(), pos.getZ());
/*    */     }
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
/*    */   {
/* 51 */     if (side.getHorizontalIndex() >= 0) return false;
/* 52 */     if (worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock() != BlocksTC.arcaneBoreBase) return false;
/* 53 */     return super.canPlaceBlockOnSide(worldIn, pos, side);
/*    */   }
/*    */   
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*    */   {
/* 58 */     TileArcaneBore tile = (TileArcaneBore)worldIn.getTileEntity(pos);
/* 59 */     if (tile != null)
/*    */     {
/* 61 */       tile.refresh = true;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getRenderType()
/*    */   {
/* 69 */     return -1;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 75 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 81 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockArcaneBore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */