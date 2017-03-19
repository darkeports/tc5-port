/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.blocks.IBlockEnabled;
/*     */ import thaumcraft.common.blocks.IBlockFacingHorizontal;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.tiles.devices.TileThaumatorium;
/*     */ import thaumcraft.common.tiles.devices.TileThaumatoriumTop;
/*     */ 
/*     */ 
/*     */ public class BlockThaumatorium
/*     */   extends BlockTCDevice
/*     */   implements IBlockFacingHorizontal, IBlockEnabled
/*     */ {
/*     */   public BlockThaumatorium()
/*     */   {
/*  31 */     super(Material.iron, null);
/*  32 */     setStepSound(Block.soundTypeMetal);
/*  33 */     setCreativeTab(null);
/*     */   }
/*     */   
/*     */   public TileEntity createNewTileEntity(World world, int metadata)
/*     */   {
/*  38 */     if (BlockStateUtils.isEnabled(metadata)) return new TileThaumatorium();
/*  39 */     if (!BlockStateUtils.isEnabled(metadata)) return new TileThaumatoriumTop();
/*  40 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
/*     */   
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  48 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  54 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  60 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float fx, float fy, float fz)
/*     */   {
/*  65 */     if ((!world.isRemote) && (!player.isSneaking())) {
/*  66 */       if (BlockStateUtils.isEnabled(state)) {
/*  67 */         player.openGui(Thaumcraft.instance, 3, world, pos.getX(), pos.getY(), pos.getZ());
/*     */       }
/*  69 */       if (!BlockStateUtils.isEnabled(state)) {
/*  70 */         player.openGui(Thaumcraft.instance, 3, world, pos.down().getX(), pos.down().getY(), pos.down().getZ());
/*     */       }
/*     */     }
/*  73 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/*  79 */     return Item.getItemFromBlock(BlocksTC.metal);
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  84 */     return 2;
/*     */   }
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/*  89 */     if ((!BlockStateUtils.isEnabled(state)) && (worldIn.getBlockState(pos.down()).getBlock() != BlocksTC.thaumatorium)) {
/*  90 */       worldIn.setBlockState(pos, BlocksTC.metal.getStateFromMeta(2));
/*     */     }
/*  92 */     if ((BlockStateUtils.isEnabled(state)) && ((worldIn.getBlockState(pos.up()).getBlock() != BlocksTC.thaumatorium) || (worldIn.getBlockState(pos.down()).getBlock() != BlocksTC.crucible)))
/*     */     {
/*  94 */       worldIn.setBlockState(pos, BlocksTC.metal.getStateFromMeta(2));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride()
/*     */   {
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(World world, BlockPos pos)
/*     */   {
/* 105 */     TileEntity tile = world.getTileEntity(pos);
/* 106 */     if ((tile != null) && ((tile instanceof TileThaumatorium))) {
/* 107 */       return Container.calcRedstoneFromInventory((IInventory)tile);
/*     */     }
/* 109 */     return super.getComparatorInputOverride(world, pos);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockThaumatorium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */