/*    */ package thaumcraft.common.blocks.basic;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyEnum;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.blocks.BlockTC;
/*    */ 
/*    */ public class BlockMetalTC extends BlockTC
/*    */ {
/* 17 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", MetalType.class);
/*    */   
/*    */   public BlockMetalTC()
/*    */   {
/* 21 */     super(Material.iron);
/* 22 */     setCreativeTab(Thaumcraft.tabTC);
/* 23 */     setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, MetalType.ALCHEMICAL));
/* 24 */     setHardness(4.0F);
/* 25 */     setResistance(10.0F);
/* 26 */     setStepSound(Block.soundTypeMetal);
/*    */   }
/*    */   
/*    */ 
/*    */   public IBlockState getStateFromMeta(int meta)
/*    */   {
/* 32 */     return meta < MetalType.values().length ? getDefaultState().withProperty(VARIANT, MetalType.values()[meta]) : getDefaultState();
/*    */   }
/*    */   
/*    */ 
/*    */   public int getMetaFromState(IBlockState state)
/*    */   {
/* 38 */     int meta = ((MetalType)state.getValue(VARIANT)).ordinal();
/*    */     
/* 40 */     return meta;
/*    */   }
/*    */   
/*    */ 
/*    */   protected BlockState createBlockState()
/*    */   {
/* 46 */     return new BlockState(this, new IProperty[] { VARIANT });
/*    */   }
/*    */   
/*    */ 
/*    */   public IProperty[] getProperties()
/*    */   {
/* 52 */     return new IProperty[] { VARIANT };
/*    */   }
/*    */   
/*    */ 
/*    */   public String getStateName(IBlockState state, boolean fullName)
/*    */   {
/* 58 */     MetalType type = (MetalType)state.getValue(VARIANT);
/*    */     
/* 60 */     return type.getName() + (fullName ? "_metal" : "");
/*    */   }
/*    */   
/*    */   public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
/*    */   {
/* 65 */     return true;
/*    */   }
/*    */   
/*    */   public static enum MetalType implements IStringSerializable
/*    */   {
/* 70 */     THAUMIUM, 
/* 71 */     VOID, 
/* 72 */     ALCHEMICAL, 
/* 73 */     ADVANCED_ALCHEMICAL, 
/* 74 */     BRASS;
/*    */     
/*    */     private MetalType() {}
/*    */     
/*    */     public String getName() {
/* 79 */       return name().toLowerCase();
/*    */     }
/*    */     
/*    */ 
/*    */     public String toString()
/*    */     {
/* 85 */       return getName();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockMetalTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */