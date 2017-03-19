/*    */ package thaumcraft.common.blocks.misc;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockContainer;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.common.tiles.misc.TileHole;
/*    */ 
/*    */ 
/*    */ public class BlockHole
/*    */   extends BlockContainer
/*    */ {
/*    */   public BlockHole()
/*    */   {
/* 30 */     super(Material.rock);
/* 31 */     setBlockUnbreakable();
/* 32 */     setResistance(6000000.0F);
/* 33 */     setStepSound(Block.soundTypeCloth);
/* 34 */     setLightLevel(0.7F);
/* 35 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 36 */     setTickRandomly(true);
/* 37 */     setCreativeTab(null);
/*    */   }
/*    */   
/*    */   public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
/*    */   {
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {}
/*    */   
/*    */   public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing o) {
/* 49 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {}
/*    */   
/*    */ 
/*    */   public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, BlockPos pos)
/*    */   {
/* 59 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
/*    */   {
/* 64 */     return AxisAlignedBB.fromBounds(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */   
/*    */   public boolean isFullCube() {
/* 68 */     return false;
/*    */   }
/*    */   
/* 71 */   public boolean isOpaqueCube() { return false; }
/*    */   
/*    */   public TileEntity createNewTileEntity(World var1, int var2) {
/* 74 */     return new TileHole();
/*    */   }
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 78 */     return Item.getItemById(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\misc\BlockHole.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */