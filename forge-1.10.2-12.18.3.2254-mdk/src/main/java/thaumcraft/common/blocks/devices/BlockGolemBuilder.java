/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockCauldron;
/*    */ import net.minecraft.block.BlockPistonBase;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.blocks.BlockTCDevice;
/*    */ import thaumcraft.common.blocks.IBlockFacingHorizontal;
/*    */ import thaumcraft.common.blocks.misc.BlockPlaceholder;
/*    */ import thaumcraft.common.blocks.misc.BlockPlaceholder.PlaceholderType;
/*    */ import thaumcraft.common.tiles.crafting.TileGolemBuilder;
/*    */ 
/*    */ public class BlockGolemBuilder
/*    */   extends BlockTCDevice implements IBlockFacingHorizontal
/*    */ {
/*    */   public BlockGolemBuilder()
/*    */   {
/* 28 */     super(Material.rock, TileGolemBuilder.class);
/* 29 */     setStepSound(Block.soundTypeStone);
/* 30 */     setCreativeTab(null);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 36 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 42 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getRenderType()
/*    */   {
/* 48 */     return -1;
/*    */   }
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*    */   {
/* 53 */     return Item.getItemFromBlock(Blocks.piston);
/*    */   }
/*    */   
/*    */   public int damageDropped(IBlockState state)
/*    */   {
/* 58 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*    */   {
/* 64 */     destroy(worldIn, pos, state, pos);
/* 65 */     super.breakBlock(worldIn, pos, state);
/*    */   }
/*    */   
/* 68 */   public static boolean ignore = false;
/*    */   
/* 70 */   public static void destroy(World worldIn, BlockPos pos, IBlockState state, BlockPos startpos) { if ((ignore) || (worldIn.isRemote)) return;
/* 71 */     ignore = true;
/* 72 */     for (int a = -1; a <= 1; a++) {
/* 73 */       for (int b = 0; b <= 1; b++)
/* 74 */         for (int c = -1; c <= 1; c++)
/* 75 */           if (pos.add(a, b, c) != startpos) {
/* 76 */             IBlockState bs = worldIn.getBlockState(pos.add(a, b, c));
/* 77 */             if ((bs.getBlock() == BlocksTC.placeholder) && (bs.getValue(BlockPlaceholder.VARIANT) == BlockPlaceholder.PlaceholderType.GB_BARS)) {
/* 78 */               worldIn.setBlockState(pos.add(a, b, c), Blocks.iron_bars.getDefaultState());
/*    */             }
/* 80 */             if ((bs.getBlock() == BlocksTC.placeholder) && (bs.getValue(BlockPlaceholder.VARIANT) == BlockPlaceholder.PlaceholderType.GB_ANVIL)) {
/* 81 */               worldIn.setBlockState(pos.add(a, b, c), Blocks.anvil.getDefaultState());
/*    */             }
/* 83 */             if ((bs.getBlock() == BlocksTC.placeholder) && (bs.getValue(BlockPlaceholder.VARIANT) == BlockPlaceholder.PlaceholderType.GB_CAULDRON)) {
/* 84 */               worldIn.setBlockState(pos.add(a, b, c), Blocks.cauldron.getDefaultState());
/*    */             }
/* 86 */             if ((bs.getBlock() == BlocksTC.placeholder) && (bs.getValue(BlockPlaceholder.VARIANT) == BlockPlaceholder.PlaceholderType.GB_TABLE))
/* 87 */               worldIn.setBlockState(pos.add(a, b, c), BlocksTC.tableStone.getDefaultState());
/*    */           }
/*    */     }
/* 90 */     if (pos != startpos) worldIn.setBlockState(pos, Blocks.piston.getDefaultState().withProperty(BlockPistonBase.FACING, EnumFacing.UP));
/* 91 */     ignore = false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*    */   {
/* 97 */     if (world.isRemote) return true;
/* 98 */     player.openGui(Thaumcraft.instance, 19, world, pos.getX(), pos.getY(), pos.getZ());
/* 99 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockGolemBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */