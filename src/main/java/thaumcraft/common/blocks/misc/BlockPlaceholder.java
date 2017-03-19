/*     */ package thaumcraft.common.blocks.misc;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ import thaumcraft.common.blocks.devices.BlockGolemBuilder;
/*     */ import thaumcraft.common.blocks.devices.BlockInfernalFurnace;
/*     */ 
/*     */ 
/*     */ public class BlockPlaceholder
/*     */   extends BlockTC
/*     */ {
/*  26 */   public static final PropertyEnum VARIANT = PropertyEnum.create("type", PlaceholderType.class);
/*     */   
/*  28 */   private final Random rand = new Random();
/*     */   
/*     */   public BlockPlaceholder() {
/*  31 */     super(Material.rock);
/*     */     
/*  33 */     setHardness(2.5F);
/*  34 */     setStepSound(soundTypeStone);
/*     */     
/*  36 */     IBlockState bs = this.blockState.getBaseState();
/*  37 */     bs.withProperty(VARIANT, PlaceholderType.FURNACE_BRICK);
/*  38 */     setDefaultState(bs);
/*     */     
/*  40 */     setCreativeTab(null);
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canSilkHarvest()
/*     */   {
/*  46 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  52 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  58 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  64 */     return -1;
/*     */   }
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/*  69 */     int t = ((PlaceholderType)state.getValue(VARIANT)).ordinal();
/*  70 */     if (t == 0) return Item.getItemFromBlock(Blocks.nether_brick);
/*  71 */     if (t == 1) return Item.getItemFromBlock(Blocks.obsidian);
/*  72 */     if (t == 2) return Item.getItemFromBlock(Blocks.iron_bars);
/*  73 */     if (t == 3) return Item.getItemFromBlock(Blocks.anvil);
/*  74 */     if (t == 4) return Item.getItemFromBlock(Blocks.cauldron);
/*  75 */     if (t == 5) return Item.getItemFromBlock(BlocksTC.tableStone);
/*  76 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  81 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/*  86 */     if (world.isRemote) return true;
/*  87 */     int t = ((PlaceholderType)state.getValue(VARIANT)).ordinal();
/*  88 */     if ((t > 1) && (t <= 5)) {
/*  89 */       for (int a = -1; a <= 1; a++) {
/*  90 */         for (int b = -1; b <= 1; b++)
/*  91 */           for (int c = -1; c <= 1; c++) {
/*  92 */             IBlockState s = world.getBlockState(pos.add(a, b, c));
/*  93 */             if (s.getBlock() == BlocksTC.golemBuilder) {
/*  94 */               player.openGui(Thaumcraft.instance, 19, world, pos.add(a, b, c).getX(), pos.add(a, b, c).getY(), pos.add(a, b, c).getZ());
/*     */               
/*  96 */               return true;
/*     */             }
/*     */           }
/*     */       }
/*     */     }
/* 101 */     return super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 106 */     int t = ((PlaceholderType)state.getValue(VARIANT)).ordinal();
/* 107 */     if ((t <= 1) && (!BlockInfernalFurnace.ignore) && (!worldIn.isRemote))
/*     */     {
/* 109 */       for (int a = -1; a <= 1; a++) {
/* 110 */         for (int b = -1; b <= 1; b++)
/* 111 */           for (int c = -1; c <= 1; c++) {
/* 112 */             IBlockState s = worldIn.getBlockState(pos.add(a, b, c));
/* 113 */             if (s.getBlock() == BlocksTC.infernalFurnace) {
/* 114 */               BlockInfernalFurnace.destroyFurnace(worldIn, pos.add(a, b, c), s, pos);
/*     */               break label246;
/*     */             }
/*     */           }
/*     */       }
/* 119 */     } else if ((t <= 5) && (!BlockGolemBuilder.ignore) && (!worldIn.isRemote))
/*     */     {
/* 121 */       for (int a = -1; a <= 1; a++)
/* 122 */         for (int b = -1; b <= 1; b++)
/* 123 */           for (int c = -1; c <= 1; c++) {
/* 124 */             IBlockState s = worldIn.getBlockState(pos.add(a, b, c));
/* 125 */             if (s.getBlock() == BlocksTC.golemBuilder) {
/* 126 */               BlockGolemBuilder.destroy(worldIn, pos.add(a, b, c), s, pos);
/*     */               break label246;
/*     */             }
/*     */           } }
/*     */     label246:
/* 131 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 138 */     return getDefaultState().withProperty(VARIANT, PlaceholderType.values()[meta]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 144 */     int meta = ((PlaceholderType)state.getValue(VARIANT)).ordinal();
/*     */     
/* 146 */     return meta;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 152 */     return new BlockState(this, new IProperty[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/* 158 */     return new IProperty[] { VARIANT };
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/* 164 */     PlaceholderType type = (PlaceholderType)state.getValue(VARIANT);
/*     */     
/* 166 */     return type.getName() + (fullName ? "_placeholder" : "");
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum PlaceholderType
/*     */     implements IStringSerializable
/*     */   {
/* 173 */     FURNACE_BRICK,  FURNACE_OBSIDIAN,  GB_BARS,  GB_ANVIL,  GB_CAULDRON,  GB_TABLE;
/*     */     
/*     */     private PlaceholderType() {}
/*     */     
/*     */     public String getName() {
/* 178 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 184 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\misc\BlockPlaceholder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */