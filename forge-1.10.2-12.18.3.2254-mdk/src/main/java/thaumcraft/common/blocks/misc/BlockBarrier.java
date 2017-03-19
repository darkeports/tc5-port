/*     */ package thaumcraft.common.blocks.misc;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.tiles.misc.TileBarrierStone;
/*     */ 
/*     */ 
/*     */ public class BlockBarrier
/*     */   extends Block
/*     */ {
/*  29 */   public static final Material barrierMat = new MaterialBarrier();
/*     */   
/*     */   public BlockBarrier()
/*     */   {
/*  33 */     super(barrierMat);
/*  34 */     setCreativeTab(null);
/*  35 */     setLightOpacity(0);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  41 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {}
/*     */   
/*     */   public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos)
/*     */   {
/*  49 */     return null;
/*     */   }
/*     */   
/*  52 */   public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing o) { return false; }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/*  58 */     if ((collidingEntity != null) && ((collidingEntity instanceof EntityLivingBase)) && (!(collidingEntity instanceof EntityPlayer))) {
/*  59 */       int a = 1;
/*  60 */       if (world.getBlockState(pos.down(a)) != BlocksTC.pavingStone.getDefaultState()) a++;
/*  61 */       if (world.isBlockIndirectlyGettingPowered(pos.down(a)) == 0) {
/*  62 */         setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  63 */         super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/*  70 */     if ((world.getBlockState(pos.down(1)) != BlocksTC.pavingStone.getDefaultState()) && (world.getBlockState(pos.down(1)) != getDefaultState()))
/*     */     {
/*  72 */       world.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/*  78 */     int a = 1;
/*  79 */     while (a < 3) {
/*  80 */       TileEntity te = worldIn.getTileEntity(pos.down(a));
/*  81 */       if ((te != null) && ((te instanceof TileBarrierStone))) {
/*  82 */         return te.getWorld().isBlockIndirectlyGettingPowered(pos.down(a)) > 0;
/*     */       }
/*  84 */       a++;
/*     */     }
/*     */     
/*  87 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, BlockPos pos)
/*     */   {
/*  93 */     setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos)
/*     */   {
/*  98 */     return true;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
/*     */   {
/* 103 */     return AxisAlignedBB.fromBounds(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/* 107 */     return false;
/*     */   }
/*     */   
/* 110 */   public boolean isOpaqueCube() { return false; }
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/* 114 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */   public boolean isAir(IBlockAccess world, BlockPos pos)
/*     */   {
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   private static class MaterialBarrier extends Material
/*     */   {
/*     */     public MaterialBarrier()
/*     */     {
/* 126 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean blocksMovement()
/*     */     {
/* 132 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isSolid()
/*     */     {
/* 138 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean blocksLight()
/*     */     {
/* 144 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\misc\BlockBarrier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */