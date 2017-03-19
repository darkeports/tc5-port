/*    */ package thaumcraft.common.blocks.basic;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyEnum;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.blocks.BlockTC;
/*    */ 
/*    */ public class BlockPlanksTC extends BlockTC
/*    */ {
/* 18 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", PlankType.class);
/*    */   
/*    */   public BlockPlanksTC()
/*    */   {
/* 22 */     super(Material.wood);
/* 23 */     setCreativeTab(Thaumcraft.tabTC);
/* 24 */     setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, PlankType.GREATWOOD));
/*    */     
/* 26 */     setHarvestLevel("axe", 0);
/*    */     
/* 28 */     setHardness(2.0F);
/* 29 */     setStepSound(Block.soundTypeWood);
/*    */   }
/*    */   
/*    */   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
/* 33 */     return 20;
/*    */   }
/*    */   
/* 36 */   public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) { return 5; }
/*    */   
/*    */ 
/*    */   public IBlockState getStateFromMeta(int meta)
/*    */   {
/* 41 */     return meta < PlankType.values().length ? getDefaultState().withProperty(VARIANT, PlankType.values()[meta]) : getDefaultState();
/*    */   }
/*    */   
/*    */ 
/*    */   public int getMetaFromState(IBlockState state)
/*    */   {
/* 47 */     int meta = ((PlankType)state.getValue(VARIANT)).ordinal();
/*    */     
/* 49 */     return meta;
/*    */   }
/*    */   
/*    */ 
/*    */   protected BlockState createBlockState()
/*    */   {
/* 55 */     return new BlockState(this, new IProperty[] { VARIANT });
/*    */   }
/*    */   
/*    */ 
/*    */   public IProperty[] getProperties()
/*    */   {
/* 61 */     return new IProperty[] { VARIANT };
/*    */   }
/*    */   
/*    */ 
/*    */   public String getStateName(IBlockState state, boolean fullName)
/*    */   {
/* 67 */     PlankType type = (PlankType)state.getValue(VARIANT);
/*    */     
/* 69 */     return type.getName() + (fullName ? "_plank" : "");
/*    */   }
/*    */   
/*    */   public static enum PlankType
/*    */     implements IStringSerializable
/*    */   {
/* 75 */     GREATWOOD, 
/* 76 */     SILVERWOOD;
/*    */     
/*    */     private PlankType() {}
/*    */     
/*    */     public String getName() {
/* 81 */       return name().toLowerCase();
/*    */     }
/*    */     
/*    */ 
/*    */     public String toString()
/*    */     {
/* 87 */       return getName();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockPlanksTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */