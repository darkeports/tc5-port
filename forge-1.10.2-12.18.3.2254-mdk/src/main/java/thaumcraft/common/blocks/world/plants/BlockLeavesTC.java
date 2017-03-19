/*     */ package thaumcraft.common.blocks.world.plants;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockPlanks.EnumType;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockLeavesTC
/*     */   extends BlockLeaves
/*     */ {
/*  38 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockLogsTC.LogType.class, new Predicate()
/*     */   {
/*     */ 
/*     */     public boolean apply(BlockLogsTC.LogType type)
/*     */     {
/*  43 */       return type.getMetadata() < 4;
/*     */     }
/*     */     
/*     */     public boolean apply(Object p_apply_1_) {
/*  47 */       return apply((BlockLogsTC.LogType)p_apply_1_);
/*     */     }
/*  38 */   });
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
/*     */   public BlockLeavesTC()
/*     */   {
/*  54 */     setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockLogsTC.LogType.GREATWOOD).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
/*     */     
/*     */ 
/*  57 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
/*  61 */     return 60;
/*     */   }
/*     */   
/*  64 */   public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) { return 30; }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumWorldBlockLayer getBlockLayer()
/*     */   {
/*  70 */     return Blocks.leaves.getBlockLayer();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  76 */     return Blocks.leaves.isOpaqueCube();
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
/*     */   {
/*  83 */     return (isOpaqueCube()) && (worldIn.getBlockState(pos).getBlock() == this) ? false : super.shouldSideBeRendered(worldIn, pos, side);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getBlockColor()
/*     */   {
/*  90 */     return ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getRenderColor(IBlockState state)
/*     */   {
/*  98 */     return damageDropped(state) == 1 ? 16777215 : ColorizerFoliage.getFoliageColorBasic();
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
/*     */   {
/* 105 */     return damageDropped(worldIn.getBlockState(pos)) == 1 ? 16777215 : BiomeColorHelper.getFoliageColorAtPos(worldIn, pos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/* 112 */     return state.getBlock() == this ? ((BlockLogsTC.LogType)state.getValue(VARIANT)).getMetadata() : 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getDamageValue(World worldIn, BlockPos pos)
/*     */   {
/* 118 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 119 */     return iblockstate.getBlock().getMetaFromState(iblockstate) & 0x3;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
/*     */   {
/* 126 */     list.add(new ItemStack(itemIn, 1, 0));
/* 127 */     list.add(new ItemStack(itemIn, 1, 1));
/*     */   }
/*     */   
/*     */ 
/*     */   protected ItemStack createStackedBlock(IBlockState state)
/*     */   {
/* 133 */     return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockLogsTC.LogType)state.getValue(VARIANT)).getMetadata());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 140 */     return getDefaultState().withProperty(VARIANT, getWoodTCType(meta)).withProperty(DECAYABLE, Boolean.valueOf((meta & 0x4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 0x8) > 0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 148 */     byte b0 = 0;
/* 149 */     int i = b0 | ((BlockLogsTC.LogType)state.getValue(VARIANT)).getMetadata();
/*     */     
/* 151 */     if (!((Boolean)state.getValue(DECAYABLE)).booleanValue())
/*     */     {
/* 153 */       i |= 0x4;
/*     */     }
/*     */     
/* 156 */     if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue())
/*     */     {
/* 158 */       i |= 0x8;
/*     */     }
/*     */     
/* 161 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   protected int getSaplingDropChance(IBlockState state)
/*     */   {
/* 167 */     return ((BlockLogsTC.LogType)state.getValue(VARIANT)).getMetadata() == 0 ? 44 : 200;
/*     */   }
/*     */   
/*     */   protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance)
/*     */   {
/* 172 */     if ((state.getValue(VARIANT) == BlockLogsTC.LogType.SILVERWOOD) && (worldIn.rand.nextInt((int)(chance * 1.5D)) == 0))
/*     */     {
/* 174 */       spawnAsEntity(worldIn, pos, new ItemStack(ItemsTC.quicksilver, 1, 0));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/* 181 */     return Item.getItemFromBlock(BlocksTC.sapling);
/*     */   }
/*     */   
/*     */   public BlockLogsTC.LogType getWoodTCType(int meta)
/*     */   {
/* 186 */     return BlockLogsTC.LogType.byMetadata(meta & 0x3);
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 192 */     return new BlockState(this, new IProperty[] { VARIANT, CHECK_DECAY, DECAYABLE });
/*     */   }
/*     */   
/*     */ 
/*     */   public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
/*     */   {
/* 198 */     IBlockState state = world.getBlockState(pos);
/* 199 */     return new ArrayList(Arrays.asList(new ItemStack[] { new ItemStack(this, 1, ((BlockLogsTC.LogType)state.getValue(VARIANT)).getMetadata()) }));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public BlockPlanks.EnumType getWoodType(int meta)
/*     */   {
/* 206 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\plants\BlockLeavesTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */