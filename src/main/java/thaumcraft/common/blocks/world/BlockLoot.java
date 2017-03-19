/*     */ package thaumcraft.common.blocks.world;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class BlockLoot
/*     */   extends BlockTC
/*     */ {
/*  24 */   public static final PropertyEnum TYPE = PropertyEnum.create("type", LootType.class);
/*     */   
/*     */   public BlockLoot(Material mat) {
/*  27 */     super(mat);
/*  28 */     setHardness(0.15F);
/*  29 */     setResistance(0.0F);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty(TYPE, LootType.COMMON));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  36 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  42 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canSilkHarvest()
/*     */   {
/*  48 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
/*     */   {
/*  53 */     return true;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World w, BlockPos pos)
/*     */   {
/*  58 */     if (getMaterial() == Material.rock) {
/*  59 */       setBlockBounds(0.125F, 0.0625F, 0.125F, 0.875F, 0.8125F, 0.875F);
/*     */     } else {
/*  61 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */     }
/*  63 */     return super.getSelectedBoundingBox(w, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  69 */     return getDefaultState().withProperty(TYPE, LootType.values()[meta]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/*  75 */     int meta = ((LootType)state.getValue(TYPE)).ordinal();
/*     */     
/*  77 */     return meta;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/*  83 */     return new BlockState(this, new IProperty[] { TYPE });
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/*  89 */     return new IProperty[] { TYPE };
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/*  95 */     LootType type = (LootType)state.getValue(TYPE);
/*     */     
/*  97 */     return (fullName ? (getMaterial() == Material.rock ? "loot_urn" : "loot_crate") + "_" : "") + type.getName();
/*     */   }
/*     */   
/* 100 */   Random rand = new Random();
/*     */   
/*     */   public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
/*     */   {
/* 104 */     int md = getMetaFromState(state);
/* 105 */     ArrayList<ItemStack> ret = new ArrayList();
/* 106 */     int q = 1 + md + this.rand.nextInt(3);
/* 107 */     for (int a = 0; a < q; a++) {
/* 108 */       ItemStack is = Utils.generateLoot(md, this.rand);
/* 109 */       if (is != null) {
/* 110 */         ret.add(is.copy());
/*     */       }
/*     */     }
/* 113 */     return ret;
/*     */   }
/*     */   
/*     */   public static enum LootType implements IStringSerializable
/*     */   {
/* 118 */     COMMON,  UNCOMMON,  RARE;
/*     */     
/*     */     private LootType() {}
/*     */     
/*     */     public String getName() {
/* 123 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 129 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\BlockLoot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */