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
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class BlockStoneSlabTC
/*     */   extends BlockSlab
/*     */ {
/*  26 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", StoneType.class);
/*     */   
/*     */   public BlockStoneSlabTC()
/*     */   {
/*  30 */     super(Material.rock);
/*     */     
/*  32 */     IBlockState iblockstate = this.blockState.getBaseState();
/*     */     
/*  34 */     if (!isDouble())
/*     */     {
/*  36 */       iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
/*  37 */       setCreativeTab(Thaumcraft.tabTC);
/*     */     }
/*     */     
/*  40 */     setDefaultState(iblockstate.withProperty(VARIANT, StoneType.ARCANE));
/*  41 */     this.useNeighborBrightness = (!isDouble());
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/*  47 */     return Item.getItemFromBlock(BlocksTC.slabStone);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public Item getItem(World worldIn, BlockPos pos)
/*     */   {
/*  54 */     return Item.getItemFromBlock(BlocksTC.slabStone);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getUnlocalizedName(int meta)
/*     */   {
/*  60 */     return getUnlocalizedName();
/*     */   }
/*     */   
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/*  65 */     return ((StoneType)state.getValue(VARIANT)).getName();
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty getVariantProperty()
/*     */   {
/*  71 */     return VARIANT;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object getVariant(ItemStack stack)
/*     */   {
/*  77 */     return (StoneType)getStateFromMeta(stack.getMetadata() & 0x7).getValue(VARIANT);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
/*     */   {
/*  84 */     if (itemIn != Item.getItemFromBlock(BlocksTC.doubleSlabStone))
/*     */     {
/*  86 */       StoneType[] aenumtype = StoneType.values();
/*  87 */       int i = aenumtype.length;
/*     */       
/*  89 */       for (int j = 0; j < i; j++)
/*     */       {
/*  91 */         StoneType enumtype = aenumtype[j];
/*  92 */         list.add(new ItemStack(itemIn, 1, enumtype.ordinal()));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 100 */     IBlockState iblockstate = getDefaultState().withProperty(VARIANT, StoneType.values()[(meta & 0x7)]);
/*     */     
/* 102 */     if (!isDouble())
/*     */     {
/* 104 */       iblockstate = iblockstate.withProperty(HALF, (meta & 0x8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
/*     */     }
/*     */     
/* 107 */     return iblockstate;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 113 */     byte b0 = 0;
/* 114 */     int i = b0 | ((StoneType)state.getValue(VARIANT)).ordinal();
/*     */     
/* 116 */     if ((!isDouble()) && (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP))
/*     */     {
/* 118 */       i |= 0x8;
/*     */     }
/*     */     
/* 121 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 127 */     return isDouble() ? new BlockState(this, new IProperty[] { VARIANT }) : new BlockState(this, new IProperty[] { HALF, VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/* 133 */     return ((StoneType)state.getValue(VARIANT)).ordinal();
/*     */   }
/*     */   
/*     */   public boolean isDouble()
/*     */   {
/* 138 */     return this == BlocksTC.doubleSlabStone;
/*     */   }
/*     */   
/*     */   public static enum StoneType implements IStringSerializable
/*     */   {
/* 143 */     ARCANE, 
/* 144 */     ARCANE_BRICK, 
/* 145 */     ANCIENT, 
/* 146 */     ELDRITCH;
/*     */     
/*     */     private StoneType() {}
/*     */     
/*     */     public String getName()
/*     */     {
/* 152 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 158 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockStoneSlabTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */