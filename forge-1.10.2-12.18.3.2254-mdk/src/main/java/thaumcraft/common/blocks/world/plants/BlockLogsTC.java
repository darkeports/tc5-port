/*     */ package thaumcraft.common.blocks.world.plants;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumFacing.Axis;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ 
/*     */ public class BlockLogsTC
/*     */   extends BlockTC
/*     */ {
/*  24 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", LogType.class);
/*  25 */   public static final PropertyEnum AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);
/*     */   
/*     */   public BlockLogsTC()
/*     */   {
/*  29 */     super(Material.wood);
/*  30 */     setHarvestLevel("axe", 0);
/*  31 */     setHardness(2.0F);
/*  32 */     setResistance(5.0F);
/*  33 */     setStepSound(Block.soundTypeWood);
/*  34 */     setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, LogType.GREATWOOD).withProperty(AXIS, EnumFacing.Axis.Y));
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, int metadata, EntityLivingBase entity)
/*     */   {
/*  40 */     return super.onBlockPlaced(world, pos, side, hitX, hitY, hitZ, metadata, entity).withProperty(AXIS, side.getAxis());
/*     */   }
/*     */   
/*     */ 
/*     */   public int getLightValue(IBlockAccess world, BlockPos pos)
/*     */   {
/*  46 */     return damageDropped(world.getBlockState(pos)) == 1 ? 6 : super.getLightValue(world, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   protected ItemStack createStackedBlock(IBlockState state)
/*     */   {
/*  52 */     return new ItemStack(Item.getItemFromBlock(this), 1, damageDropped(state));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  59 */     int baseMeta = ((LogType)state.getValue(VARIANT)).ordinal();
/*  60 */     return baseMeta * 3;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  66 */     int axis = meta % 3;
/*  67 */     int type = (meta - axis) / 3;
/*  68 */     return getDefaultState().withProperty(VARIANT, LogType.values()[type]).withProperty(AXIS, EnumFacing.Axis.values()[axis]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/*  74 */     int baseMeta = ((LogType)state.getValue(VARIANT)).ordinal();
/*  75 */     return baseMeta * 3 + ((EnumFacing.Axis)state.getValue(AXIS)).ordinal();
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/*  81 */     return new BlockState(this, new IProperty[] { AXIS, VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/*  87 */     return new IProperty[] { VARIANT };
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/*  93 */     return ((LogType)state.getValue(VARIANT)).getName() + (fullName ? "_log" : "");
/*     */   }
/*     */   
/*  96 */   public boolean canSustainLeaves(IBlockAccess world, BlockPos pos) { return true; }
/*  97 */   public boolean isWood(IBlockAccess world, BlockPos pos) { return true; }
/*     */   
/*     */ 
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 102 */     byte b0 = 4;
/* 103 */     int i = b0 + 1;
/*     */     
/* 105 */     if (worldIn.isAreaLoaded(pos.add(-i, -i, -i), pos.add(i, i, i)))
/*     */     {
/* 107 */       Iterator iterator = BlockPos.getAllInBox(pos.add(-b0, -b0, -b0), pos.add(b0, b0, b0)).iterator();
/*     */       
/* 109 */       while (iterator.hasNext())
/*     */       {
/* 111 */         BlockPos blockpos1 = (BlockPos)iterator.next();
/* 112 */         IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
/*     */         
/* 114 */         if (iblockstate1.getBlock().isLeaves(worldIn, blockpos1))
/*     */         {
/* 116 */           iblockstate1.getBlock().beginLeavesDecay(worldIn, blockpos1);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
/*     */   {
/* 124 */     return 5;
/*     */   }
/*     */   
/* 127 */   public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) { return 5; }
/*     */   
/*     */   public static enum LogType
/*     */     implements IStringSerializable
/*     */   {
/* 132 */     GREATWOOD(0), 
/* 133 */     SILVERWOOD(1);
/*     */     
/*     */     private static final LogType[] META_LOOKUP;
/*     */     private final int meta;
/*     */     
/* 138 */     public String getName() { return name().toLowerCase(); }
/*     */     
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 144 */       return getName();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private LogType(int meta)
/*     */     {
/* 152 */       this.meta = meta;
/*     */     }
/*     */     
/*     */ 
/*     */     public int getMetadata()
/*     */     {
/* 158 */       return this.meta;
/*     */     }
/*     */     
/*     */     public static LogType byMetadata(int meta)
/*     */     {
/* 163 */       if ((meta < 0) || (meta >= META_LOOKUP.length))
/*     */       {
/* 165 */         meta = 0;
/*     */       }
/*     */       
/* 168 */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     static
/*     */     {
/* 147 */       META_LOOKUP = new LogType[values().length];
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
/* 173 */       LogType[] var0 = values();
/* 174 */       int var1 = var0.length;
/*     */       
/* 176 */       for (int var2 = 0; var2 < var1; var2++)
/*     */       {
/* 178 */         LogType var3 = var0[var2];
/* 179 */         META_LOOKUP[var3.getMetadata()] = var3;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\plants\BlockLogsTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */