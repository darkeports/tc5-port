/*     */ package thaumcraft.common.blocks.world.plants;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBush;
/*     */ import net.minecraft.block.IGrowable;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenTrees;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ import net.minecraftforge.event.terraingen.TerrainGen;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.world.objects.WorldGenGreatwoodTrees;
/*     */ import thaumcraft.common.lib.world.objects.WorldGenSilverwoodTrees;
/*     */ 
/*     */ public class BlockSaplingTC extends BlockBush implements IGrowable
/*     */ {
/*  32 */   public static final PropertyEnum TYPE = PropertyEnum.create("type", BlockLogsTC.LogType.class);
/*  33 */   public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
/*     */   
/*     */   public BlockSaplingTC()
/*     */   {
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockLogsTC.LogType.GREATWOOD).withProperty(STAGE, Integer.valueOf(0)));
/*  38 */     float f = 0.4F;
/*  39 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/*  40 */     setCreativeTab(Thaumcraft.tabTC);
/*  41 */     setStepSound(soundTypeGrass);
/*     */   }
/*     */   
/*     */   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
/*  45 */     return 60;
/*     */   }
/*     */   
/*  48 */   public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) { return 30; }
/*     */   
/*     */ 
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/*  53 */     if (!worldIn.isRemote)
/*     */     {
/*  55 */       super.updateTick(worldIn, pos, state, rand);
/*     */       
/*  57 */       if ((worldIn.getLightFromNeighbors(pos.up()) >= 9) && (rand.nextInt(7) == 0))
/*     */       {
/*  59 */         grow(worldIn, pos, state, rand);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/*  67 */     if (((Integer)state.getValue(STAGE)).intValue() == 0)
/*     */     {
/*  69 */       worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
/*     */     }
/*     */     else
/*     */     {
/*  73 */       generateTree(worldIn, pos, state, rand);
/*     */     }
/*     */   }
/*     */   
/*     */   public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/*  79 */     if (!TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
/*  80 */     Object object = rand.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true);
/*  81 */     int i = 0;
/*  82 */     int j = 0;
/*     */     
/*  84 */     switch (SwitchEnumType.WOOD_TYPE_LOOKUP[((BlockLogsTC.LogType)state.getValue(TYPE)).getMetadata()]) {
/*     */     case 1: 
/*  86 */       object = new WorldGenGreatwoodTrees(true, false); break;
/*  87 */     case 2:  object = new WorldGenSilverwoodTrees(true, 7, 4);
/*     */     }
/*     */     
/*  90 */     IBlockState iblockstate1 = net.minecraft.init.Blocks.air.getDefaultState();
/*     */     
/*  92 */     worldIn.setBlockState(pos, iblockstate1, 4);
/*     */     
/*  94 */     if (!((WorldGenerator)object).generate(worldIn, rand, pos.add(i, 0, j)))
/*     */     {
/*  96 */       worldIn.setBlockState(pos, state, 4);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/* 103 */     return ((BlockLogsTC.LogType)state.getValue(TYPE)).getMetadata();
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
/*     */   {
/* 110 */     BlockLogsTC.LogType[] aenumtype = BlockLogsTC.LogType.values();
/* 111 */     int i = aenumtype.length;
/*     */     
/* 113 */     for (int j = 0; j < i; j++)
/*     */     {
/* 115 */       BlockLogsTC.LogType enumtype = aenumtype[j];
/* 116 */       list.add(new ItemStack(itemIn, 1, enumtype.getMetadata()));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
/*     */   {
/* 123 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
/*     */   {
/* 129 */     return worldIn.rand.nextFloat() < 0.25D;
/*     */   }
/*     */   
/*     */ 
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
/*     */   {
/* 135 */     grow(worldIn, pos, state, rand);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 142 */     return getDefaultState().withProperty(TYPE, BlockLogsTC.LogType.byMetadata(meta & 0x7)).withProperty(STAGE, Integer.valueOf((meta & 0x8) >> 3));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 150 */     byte b0 = 0;
/* 151 */     int i = b0 | ((BlockLogsTC.LogType)state.getValue(TYPE)).getMetadata();
/* 152 */     i |= ((Integer)state.getValue(STAGE)).intValue() << 3;
/* 153 */     return i;
/*     */   }
/*     */   
/*     */   public String getStateName(IBlockState state)
/*     */   {
/* 158 */     return ((BlockLogsTC.LogType)state.getValue(TYPE)).getName();
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 164 */     return new BlockState(this, new IProperty[] { TYPE, STAGE });
/*     */   }
/*     */   
/*     */   static final class SwitchEnumType
/*     */   {
/* 169 */     static final int[] WOOD_TYPE_LOOKUP = new int[BlockLogsTC.LogType.values().length];
/*     */     
/*     */     static
/*     */     {
/*     */       try
/*     */       {
/* 175 */         WOOD_TYPE_LOOKUP[BlockLogsTC.LogType.GREATWOOD.getMetadata()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError var6) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 184 */         WOOD_TYPE_LOOKUP[BlockLogsTC.LogType.SILVERWOOD.getMetadata()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError var5) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\plants\BlockSaplingTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */