/*     */ package thaumcraft.common.blocks.basic;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumFacing.Axis;
/*     */ import net.minecraft.util.EnumFacing.Plane;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ 
/*     */ public class BlockPillar extends BlockTC
/*     */ {
/*  29 */   public static final PropertyEnum TYPE = PropertyEnum.create("type", PillarType.class);
/*  30 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
/*     */   
/*  32 */   private final Random rand = new Random();
/*     */   
/*     */   public BlockPillar() {
/*  35 */     super(Material.rock);
/*     */     
/*  37 */     setHardness(2.5F);
/*  38 */     setStepSound(soundTypeStone);
/*  39 */     setCreativeTab(null);
/*     */     
/*  41 */     IBlockState bs = this.blockState.getBaseState();
/*  42 */     bs.withProperty(FACING, EnumFacing.NORTH);
/*  43 */     bs.withProperty(TYPE, PillarType.NORMAL);
/*  44 */     setDefaultState(bs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  53 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  59 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos)
/*     */   {
/*  66 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*  67 */     return super.getSelectedBoundingBox(world, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity)
/*     */   {
/*  73 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
/*  74 */     super.addCollisionBoxesToList(world, pos, state, axisalignedbb, arraylist, par7Entity);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  81 */     IBlockState bs = this.blockState.getBaseState();
/*  82 */     bs.withProperty(FACING, placer.getHorizontalFacing());
/*  83 */     bs.withProperty(TYPE, PillarType.values()[(meta / 4)]);
/*  84 */     return bs;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
/*     */   {
/*  90 */     EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3).getOpposite();
/*  91 */     state = state.withProperty(FACING, enumfacing);
/*  92 */     state = state.withProperty(TYPE, PillarType.values()[(stack.getItemDamage() / 4)]);
/*  93 */     worldIn.setBlockState(pos, state, 3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/* 100 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 105 */     int t = ((PillarType)state.getValue(TYPE)).ordinal();
/* 106 */     if (t == 0) spawnAsEntity(worldIn, pos, new ItemStack(BlocksTC.stone, 2, 0));
/* 107 */     if (t == 1) spawnAsEntity(worldIn, pos, new ItemStack(BlocksTC.stone, 2, 2));
/* 108 */     if (t == 2) spawnAsEntity(worldIn, pos, new ItemStack(BlocksTC.stone, 2, 4));
/* 109 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 115 */     EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
/*     */     
/* 117 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */     {
/* 119 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 122 */     return getDefaultState().withProperty(FACING, enumfacing).withProperty(TYPE, PillarType.values()[((meta - enumfacing.ordinal()) / 4)]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 128 */     int baseMeta = ((PillarType)state.getValue(TYPE)).ordinal();
/* 129 */     return ((EnumFacing)state.getValue(FACING)).ordinal() + baseMeta * 4;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 135 */     return new BlockState(this, new IProperty[] { FACING, TYPE });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/* 142 */     PillarType type = (PillarType)state.getValue(TYPE);
/* 143 */     return "pillar_" + type.getName();
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/* 149 */     return new IProperty[] { TYPE };
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum PillarType
/*     */     implements IStringSerializable
/*     */   {
/* 156 */     NORMAL,  ANCIENT,  ELDRITCH;
/*     */     
/*     */     private PillarType() {}
/*     */     
/*     */     public String getName() {
/* 161 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 167 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockPillar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */