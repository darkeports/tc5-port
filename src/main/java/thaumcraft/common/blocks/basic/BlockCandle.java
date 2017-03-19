/*     */ package thaumcraft.common.blocks.basic;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.crafting.IInfusionStabiliser;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ 
/*     */ 
/*     */ public class BlockCandle
/*     */   extends BlockTC
/*     */   implements IInfusionStabiliser
/*     */ {
/*  28 */   public static final PropertyEnum COLOR = PropertyEnum.create("color", EnumDyeColor.class);
/*     */   
/*     */   public BlockCandle()
/*     */   {
/*  32 */     super(Material.circuits);
/*  33 */     setHardness(0.1F);
/*  34 */     setStepSound(soundTypeCloth);
/*  35 */     setLightLevel(0.9375F);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
/*     */   }
/*     */   
/*     */ 
/*     */   public MapColor getMapColor(IBlockState state)
/*     */   {
/*  42 */     return ((EnumDyeColor)state.getValue(COLOR)).getMapColor();
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getRenderColor(IBlockState state)
/*     */   {
/*  49 */     return ((EnumDyeColor)state.getValue(COLOR)).getMapColor().colorValue;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
/*     */   {
/*  56 */     return ((EnumDyeColor)worldIn.getBlockState(pos).getValue(COLOR)).getMapColor().colorValue;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  62 */     return meta < EnumDyeColor.values().length ? getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta)) : getDefaultState();
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/*  68 */     return ((EnumDyeColor)state.getValue(COLOR)).getMetadata();
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/*  74 */     return new BlockState(this, new IProperty[] { COLOR });
/*     */   }
/*     */   
/*     */   public IProperty[] getProperties() {
/*  78 */     return new IProperty[] { COLOR };
/*     */   }
/*     */   
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/*  83 */     EnumDyeColor type = (EnumDyeColor)state.getValue(COLOR);
/*  84 */     return type.getName();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canPlaceBlockAt(World par1World, BlockPos pos)
/*     */   {
/*  90 */     return World.doesBlockHaveSolidTopSurface(par1World, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/*  96 */     if (!canPlaceBlockAt(worldIn, pos.down()))
/*     */     {
/*  98 */       dropBlockAsItem(worldIn, pos, state, 0);
/*  99 */       worldIn.setBlockToAir(pos);
/*     */     }
/* 101 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canPlaceBlockOnSide(World par1World, BlockPos pos, EnumFacing par5)
/*     */   {
/* 107 */     return canPlaceBlockAt(par1World, pos.down());
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess, BlockPos pos)
/*     */   {
/* 112 */     setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.5F, 0.625F);
/* 113 */     super.setBlockBoundsBasedOnState(par1iBlockAccess, pos);
/*     */   }
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
/*     */   {
/* 118 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 124 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/* 131 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/* 137 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void randomDisplayTick(World par1World, BlockPos pos, IBlockState state, Random par5Random)
/*     */   {
/* 143 */     double var7 = pos.getX() + 0.5F;
/* 144 */     double var9 = pos.getY() + 0.7F;
/* 145 */     double var11 = pos.getZ() + 0.5F;
/*     */     
/* 147 */     par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var7, var9, var11, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     
/* 149 */     par1World.spawnParticle(EnumParticleTypes.FLAME, var7, var9, var11, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canStabaliseInfusion(World world, BlockPos pos)
/*     */   {
/* 156 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockCandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */