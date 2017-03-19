/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.tiles.devices.TileAuraTotem;
/*     */ import thaumcraft.common.tiles.devices.TileAuraTotemPole;
/*     */ import thaumcraft.common.tiles.devices.TileAuraTotemPull;
/*     */ import thaumcraft.common.tiles.devices.TileAuraTotemPush;
/*     */ 
/*     */ 
/*     */ public class BlockAuraTotem
/*     */   extends BlockTCDevice
/*     */ {
/*  36 */   public static final PropertyEnum TYPE = PropertyEnum.create("type", TotemType.class);
/*     */   
/*     */   public BlockAuraTotem()
/*     */   {
/*  40 */     super(Material.wood, TileAuraTotemPush.class);
/*  41 */     setHardness(0.5F);
/*  42 */     setResistance(5.0F);
/*  43 */     setStepSound(Block.soundTypeWood);
/*  44 */     setDefaultState(this.blockState.getBaseState().withProperty(TYPE, TotemType.PUSH));
/*     */   }
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta)
/*     */   {
/*  49 */     switch (meta) {
/*  50 */     case 0:  return new TileAuraTotemPush();
/*  51 */     case 1:  return new TileAuraTotemPull(); }
/*  52 */     return new TileAuraTotemPole();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  58 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  64 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/*  70 */     if (((TotemType)state.getValue(TYPE)).isPole) {
/*  71 */       worldIn.removeTileEntity(pos);
/*     */     } else {
/*  73 */       super.breakBlock(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
/*     */   {
/*  79 */     IBlockState state = world.getBlockState(pos);
/*  80 */     if (((TotemType)state.getValue(TYPE)).isPole) {
/*  81 */       setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
/*     */     } else {
/*  83 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/*  89 */     setBlockBoundsBasedOnState(worldIn, pos);
/*  90 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  96 */     return getStateFromMeta(meta);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 102 */     return getDefaultState().withProperty(TYPE, TotemType.values()[meta]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 108 */     return ((TotemType)state.getValue(TYPE)).ordinal();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 115 */     return new BlockState(this, new IProperty[] { TYPE });
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/* 121 */     TotemType type = (TotemType)state.getValue(TYPE);
/* 122 */     return (fullName ? "aura_totem_" : "") + type.getName();
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/* 128 */     return new IProperty[] { TYPE };
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumWorldBlockLayer getBlockLayer()
/*     */   {
/* 135 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
/*     */   {
/* 142 */     TileEntity te = worldIn.getTileEntity(pos);
/* 143 */     if ((te != null) && ((te instanceof TileAuraTotem))) {
/* 144 */       if (((TileAuraTotem)te).type >= 0) {
/* 145 */         Aspect a = ((TileAuraTotem)te).getAspect();
/* 146 */         return a == null ? 15790320 : a.getColor();
/*     */       }
/* 148 */       return 1052688;
/*     */     }
/*     */     
/*     */ 
/* 152 */     return super.colorMultiplier(worldIn, pos, renderPass);
/*     */   }
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/* 157 */     TileEntity tile = worldIn.getTileEntity(pos);
/* 158 */     if ((tile != null) && ((tile instanceof TileAuraTotem)))
/*     */     {
/* 160 */       ((TileAuraTotem)tile).checkPoles();
/* 161 */       worldIn.addBlockEvent(pos, this, 1, 1);
/*     */     } else {
/* 163 */       worldIn.notifyBlockOfStateChange(pos.up(), this);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/* 169 */     if ((player.getHeldItem() != null) && (Block.getBlockFromItem(player.getHeldItem().getItem()) == this)) return false;
/* 170 */     if (!((TotemType)state.getValue(TYPE)).isPole) {
/* 171 */       player.openGui(Thaumcraft.instance, 8, world, pos.getX(), pos.getY(), pos.getZ());
/*     */     } else {
/* 173 */       IBlockState bs = world.getBlockState(pos.up());
/* 174 */       if (bs.getBlock() == this)
/* 175 */         return bs.getBlock().onBlockActivated(world, pos.up(), bs, player, side, hitX, hitY, hitZ);
/*     */     }
/* 177 */     return true;
/*     */   }
/*     */   
/*     */   public static enum TotemType
/*     */     implements IStringSerializable
/*     */   {
/* 183 */     PUSH(Boolean.valueOf(false)), 
/* 184 */     PULL(Boolean.valueOf(false)), 
/* 185 */     POLE_OUTER(Boolean.valueOf(true)), 
/* 186 */     POLE_INNER(Boolean.valueOf(true)), 
/* 187 */     POLE_PURE(Boolean.valueOf(true));
/*     */     
/*     */     private final boolean isPole;
/*     */     
/*     */     private TotemType(Boolean b) {
/* 192 */       this.isPole = b.booleanValue();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public String getName()
/*     */     {
/* 199 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 205 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockAuraTotem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */