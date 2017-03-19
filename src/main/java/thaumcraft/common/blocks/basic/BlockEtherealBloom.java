/*    */ package thaumcraft.common.blocks.basic;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockBush;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.EnumPlantType;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.tiles.misc.TileEtherealBloom;
/*    */ 
/*    */ public class BlockEtherealBloom extends BlockBush
/*    */ {
/*    */   public BlockEtherealBloom()
/*    */   {
/* 21 */     super(Material.plants);
/* 22 */     setCreativeTab(Thaumcraft.tabTC);
/* 23 */     setStepSound(Block.soundTypeGrass);
/* 24 */     setLightLevel(0.8F);
/*    */   }
/*    */   
/*    */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
/*    */   {
/* 29 */     super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
/* 30 */     TileEtherealBloom tile = (TileEtherealBloom)worldIn.getTileEntity(pos);
/* 31 */     if (tile != null)
/*    */     {
/* 33 */       tile.growthCounter = 0;
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean canPlaceBlockOn(Block ground, IBlockState state)
/*    */   {
/* 39 */     return ground.isFullBlock();
/*    */   }
/*    */   
/*    */   public boolean hasTileEntity(IBlockState state)
/*    */   {
/* 44 */     return true;
/*    */   }
/*    */   
/*    */   public TileEntity createTileEntity(World world, IBlockState state)
/*    */   {
/* 49 */     return new TileEtherealBloom();
/*    */   }
/*    */   
/*    */ 
/*    */   public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
/*    */   {
/* 55 */     return EnumPlantType.Cave;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getRenderType()
/*    */   {
/* 61 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockEtherealBloom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */