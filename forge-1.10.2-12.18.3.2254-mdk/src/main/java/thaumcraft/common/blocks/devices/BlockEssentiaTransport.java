/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.blocks.BlockTCDevice;
/*    */ import thaumcraft.common.blocks.IBlockFacing;
/*    */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*    */ 
/*    */ public class BlockEssentiaTransport
/*    */   extends BlockTCDevice implements IBlockFacing
/*    */ {
/*    */   public BlockEssentiaTransport(Class te)
/*    */   {
/* 25 */     super(Material.iron, te);
/* 26 */     setStepSound(Block.soundTypeMetal);
/* 27 */     setHardness(1.0F);
/* 28 */     setResistance(10.0F);
/* 29 */     IBlockState bs = this.blockState.getBaseState();
/* 30 */     bs.withProperty(IBlockFacing.FACING, EnumFacing.UP);
/* 31 */     setDefaultState(bs);
/*    */   }
/*    */   
/*    */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
/*    */   {
/* 36 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 42 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 48 */     return false;
/*    */   }
/*    */   
/*    */   public int damageDropped(IBlockState state)
/*    */   {
/* 53 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*    */   {
/* 59 */     IBlockState bs = getDefaultState();
/* 60 */     bs = bs.withProperty(IBlockFacing.FACING, facing);
/* 61 */     return bs;
/*    */   }
/*    */   
/*    */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*    */   {
/* 66 */     EnumFacing facing = BlockStateUtils.getFacing(getMetaFromState(worldIn.getBlockState(pos)));
/* 67 */     switch (facing.ordinal()) {
/* 68 */     case 0:  setBlockBounds(0.25F, 0.5F, 0.25F, 0.75F, 1.0F, 0.75F); break;
/* 69 */     case 1:  setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F); break;
/* 70 */     case 2:  setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F); break;
/* 71 */     case 3:  setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F); break;
/* 72 */     case 4:  setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F); break;
/* 73 */     case 5:  setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
/*    */     }
/*    */     
/*    */   }
/*    */   
/*    */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*    */   {
/* 80 */     setBlockBoundsBasedOnState(worldIn, pos);
/* 81 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockEssentiaTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */