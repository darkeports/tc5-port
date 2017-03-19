/*     */ package thaumcraft.common.blocks.basic;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockSlab;
/*     */ import net.minecraft.block.BlockSlab.EnumBlockHalf;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ 
/*     */ public class BlockWoodSlabTC
/*     */   extends BlockSlab
/*     */ {
/*  29 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", PlankType.class);
/*     */   
/*     */   public BlockWoodSlabTC()
/*     */   {
/*  33 */     super(Material.wood);
/*  34 */     IBlockState iblockstate = this.blockState.getBaseState();
/*     */     
/*  36 */     if (!isDouble())
/*     */     {
/*  38 */       iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
/*  39 */       setCreativeTab(Thaumcraft.tabTC);
/*     */     }
/*     */     
/*  42 */     setDefaultState(iblockstate.withProperty(VARIANT, PlankType.GREATWOOD));
/*  43 */     this.useNeighborBrightness = (!isDouble());
/*     */   }
/*     */   
/*     */   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
/*  47 */     return 20;
/*     */   }
/*     */   
/*  50 */   public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) { return 5; }
/*     */   
/*     */ 
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/*  55 */     return Item.getItemFromBlock(BlocksTC.slabWood);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public Item getItem(World worldIn, BlockPos pos)
/*     */   {
/*  62 */     return Item.getItemFromBlock(BlocksTC.slabWood);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getUnlocalizedName(int meta)
/*     */   {
/*  68 */     return getUnlocalizedName();
/*     */   }
/*     */   
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/*  73 */     return ((PlankType)state.getValue(VARIANT)).getName();
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty getVariantProperty()
/*     */   {
/*  79 */     return VARIANT;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object getVariant(ItemStack stack)
/*     */   {
/*  85 */     return (PlankType)getStateFromMeta(stack.getMetadata() & 0x7).getValue(VARIANT);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
/*     */   {
/*  92 */     if (itemIn != Item.getItemFromBlock(BlocksTC.doubleSlabWood))
/*     */     {
/*  94 */       PlankType[] aenumtype = PlankType.values();
/*  95 */       int i = aenumtype.length;
/*     */       
/*  97 */       for (int j = 0; j < i; j++)
/*     */       {
/*  99 */         PlankType enumtype = aenumtype[j];
/* 100 */         list.add(new ItemStack(itemIn, 1, enumtype.ordinal()));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 108 */     IBlockState iblockstate = getDefaultState().withProperty(VARIANT, PlankType.values()[(meta & 0x7)]);
/*     */     
/* 110 */     if (!isDouble())
/*     */     {
/* 112 */       iblockstate = iblockstate.withProperty(HALF, (meta & 0x8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
/*     */     }
/*     */     
/* 115 */     return iblockstate;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 121 */     byte b0 = 0;
/* 122 */     int i = b0 | ((PlankType)state.getValue(VARIANT)).ordinal();
/*     */     
/* 124 */     if ((!isDouble()) && (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP))
/*     */     {
/* 126 */       i |= 0x8;
/*     */     }
/*     */     
/* 129 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 135 */     return isDouble() ? new BlockState(this, new IProperty[] { VARIANT }) : new BlockState(this, new IProperty[] { HALF, VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/* 141 */     return ((PlankType)state.getValue(VARIANT)).ordinal();
/*     */   }
/*     */   
/*     */   public boolean isDouble()
/*     */   {
/* 146 */     return this == BlocksTC.doubleSlabWood;
/*     */   }
/*     */   
/*     */   public static enum PlankType
/*     */     implements IStringSerializable
/*     */   {
/* 152 */     GREATWOOD, 
/* 153 */     SILVERWOOD;
/*     */     
/*     */     private PlankType() {}
/*     */     
/*     */     public String getName() {
/* 158 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 164 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockWoodSlabTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */