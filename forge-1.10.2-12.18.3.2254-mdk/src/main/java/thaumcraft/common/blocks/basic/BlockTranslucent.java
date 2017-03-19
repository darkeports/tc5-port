/*     */ package thaumcraft.common.blocks.basic;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ 
/*     */ 
/*     */ public class BlockTranslucent
/*     */   extends BlockTC
/*     */ {
/*  24 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", TransType.class);
/*     */   
/*     */   public BlockTranslucent()
/*     */   {
/*  28 */     super(Material.glass);
/*  29 */     setCreativeTab(Thaumcraft.tabTC);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, TransType.AMBER_BLOCK));
/*  31 */     setHardness(0.5F);
/*  32 */     setStepSound(Block.soundTypeStone);
/*     */   }
/*     */   
/*     */   public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
/*     */   {
/*  37 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
/*     */   {
/*  42 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
/*     */   {
/*  49 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  50 */     Block block = iblockstate.getBlock();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  65 */     return block == this ? false : super.shouldSideBeRendered(worldIn, pos, side);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumWorldBlockLayer getBlockLayer()
/*     */   {
/*  72 */     return EnumWorldBlockLayer.TRANSLUCENT;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  78 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMobilityFlag()
/*     */   {
/*  84 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  90 */     return getDefaultState().withProperty(VARIANT, TransType.values()[meta]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/*  96 */     int meta = ((TransType)state.getValue(VARIANT)).ordinal();
/*     */     
/*  98 */     return meta;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 104 */     return new BlockState(this, new IProperty[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/* 110 */     return new IProperty[] { VARIANT };
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/* 116 */     TransType type = (TransType)state.getValue(VARIANT);
/*     */     
/* 118 */     return type.getName();
/*     */   }
/*     */   
/*     */   public static enum TransType
/*     */     implements IStringSerializable
/*     */   {
/* 124 */     AMBER_BLOCK, 
/* 125 */     AMBER_BRICK, 
/* 126 */     EMPTY;
/*     */     
/*     */     private TransType() {}
/*     */     
/*     */     public String getName()
/*     */     {
/* 132 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 138 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockTranslucent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */