/*     */ package thaumcraft.common.blocks.basic;
/*     */ 
/*     */ import net.minecraft.block.ITileEntityProvider;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ import thaumcraft.common.tiles.misc.TileNitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockNitor
/*     */   extends BlockTC
/*     */   implements ITileEntityProvider
/*     */ {
/*  27 */   public static final PropertyEnum COLOR = PropertyEnum.create("color", EnumDyeColor.class);
/*     */   
/*     */   public BlockNitor()
/*     */   {
/*  31 */     super(Material.circuits);
/*  32 */     setHardness(0.1F);
/*  33 */     setStepSound(soundTypeCloth);
/*  34 */     setLightLevel(1.0F);
/*  35 */     setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
/*     */   }
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta)
/*     */   {
/*  40 */     return new TileNitor();
/*     */   }
/*     */   
/*     */   public boolean hasTileEntity(IBlockState state)
/*     */   {
/*  45 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public MapColor getMapColor(IBlockState state)
/*     */   {
/*  51 */     return ((EnumDyeColor)state.getValue(COLOR)).getMapColor();
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getRenderColor(IBlockState state)
/*     */   {
/*  58 */     return ((EnumDyeColor)state.getValue(COLOR)).getMapColor().colorValue;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
/*     */   {
/*  65 */     return ((EnumDyeColor)worldIn.getBlockState(pos).getValue(COLOR)).getMapColor().colorValue;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  71 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  77 */     return meta < EnumDyeColor.values().length ? getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta)) : getDefaultState();
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/*  83 */     return ((EnumDyeColor)state.getValue(COLOR)).getMetadata();
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/*  89 */     return new BlockState(this, new IProperty[] { COLOR });
/*     */   }
/*     */   
/*     */   public IProperty[] getProperties() {
/*  93 */     return new IProperty[] { COLOR };
/*     */   }
/*     */   
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/*  98 */     EnumDyeColor type = (EnumDyeColor)state.getValue(COLOR);
/*  99 */     return type.getName();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess, BlockPos pos)
/*     */   {
/* 105 */     setBlockBounds(0.33F, 0.33F, 0.33F, 0.66F, 0.66F, 0.66F);
/* 106 */     super.setBlockBoundsBasedOnState(par1iBlockAccess, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 112 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/* 119 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/* 125 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockNitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */