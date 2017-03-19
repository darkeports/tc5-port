/*     */ package thaumcraft.common.blocks.world.eldritch;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLiving.SpawnPlacementType;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.particles.FXSpark;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.tiles.devices.TileAuraTotem;
/*     */ import thaumcraft.common.tiles.misc.TileEldritchAltar;
/*     */ import thaumcraft.common.tiles.misc.TileEldritchCap;
/*     */ import thaumcraft.common.tiles.misc.TileEldritchCrabSpawner;
/*     */ import thaumcraft.common.tiles.misc.TileEldritchLock;
/*     */ import thaumcraft.common.tiles.misc.TileEldritchObelisk;
/*     */ import thaumcraft.common.tiles.misc.TileEldritchPortal;
/*     */ 
/*     */ 
/*     */ public class BlockEldritch
/*     */   extends BlockTCDevice
/*     */ {
/*  39 */   public static final PropertyEnum TYPE = PropertyEnum.create("type", EldritchType.class);
/*     */   
/*     */   public BlockEldritch()
/*     */   {
/*  43 */     super(Material.rock, TileEldritchAltar.class);
/*  44 */     setResistance(20000.0F);
/*  45 */     setHardness(50.0F);
/*  46 */     setStepSound(Block.soundTypeStone);
/*  47 */     setTickRandomly(true);
/*  48 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  49 */     setLightOpacity(0);
/*  50 */     setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EldritchType.ALTAR));
/*  51 */     setCreativeTab(null);
/*     */   }
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta)
/*     */   {
/*  56 */     switch (meta) {
/*  57 */     case 0:  return new TileEldritchAltar();
/*  58 */     case 1:  return new TileEldritchCap();
/*  59 */     case 2:  return new TileEldritchObelisk();
/*  60 */     case 4:  return new TileEldritchLock();
/*  61 */     case 6:  return new TileEldritchPortal();
/*  62 */     case 7:  return new TileEldritchCrabSpawner(); }
/*  63 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/*  70 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */   protected boolean canSilkHarvest()
/*     */   {
/*  75 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasTileEntity(IBlockState state)
/*     */   {
/*  80 */     int meta = state.getBlock().getMetaFromState(state);
/*  81 */     return (meta != 3) && (meta != 5);
/*     */   }
/*     */   
/*     */   public boolean canCreatureSpawn(IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type)
/*     */   {
/*  86 */     return false;
/*     */   }
/*     */   
/*     */   public int getLightValue(IBlockAccess world, BlockPos pos)
/*     */   {
/*  91 */     return 8;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  97 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean isOpaqueCube()
/*     */   {
/* 102 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/* 108 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void breakBlock(World world, BlockPos pos, IBlockState state)
/*     */   {
/* 114 */     int meta = state.getBlock().getMetaFromState(state);
/* 115 */     if ((!world.isRemote) && (meta > 1) && (meta < 4)) {
/* 116 */       for (int xx = pos.getX() - 3; xx <= pos.getX() + 3; xx++) {
/* 117 */         for (int yy = pos.getY() - 2; yy <= pos.getY() + 2; yy++)
/* 118 */           for (int zz = pos.getZ() - 3; zz <= pos.getZ() + 3; zz++)
/* 119 */             if (world.getBlockState(new BlockPos(xx, yy, zz)).getBlock() == this) {
/* 120 */               IBlockState bs = world.getBlockState(new BlockPos(xx, yy, zz));
/* 121 */               int m = bs.getBlock().getMetaFromState(bs);
/* 122 */               if ((meta > 1) && (meta < 4)) world.setBlockToAir(new BlockPos(xx, yy, zz));
/*     */             }
/*     */       }
/* 125 */       world.createExplosion(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 1.0F, false);
/*     */     }
/*     */     
/* 128 */     super.breakBlock(world, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/* 136 */     return getStateFromMeta(meta);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 142 */     return getDefaultState().withProperty(TYPE, EldritchType.values()[meta]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 148 */     return ((EldritchType)state.getValue(TYPE)).ordinal();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 155 */     return new BlockState(this, new IProperty[] { TYPE });
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/* 161 */     EldritchType type = (EldritchType)state.getValue(TYPE);
/* 162 */     return (fullName ? "eldritch_" : "") + type.getName();
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/* 168 */     return new IProperty[] { TYPE };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/* 175 */     TileEntity tile = worldIn.getTileEntity(pos);
/* 176 */     if ((tile != null) && ((tile instanceof TileAuraTotem)))
/*     */     {
/* 178 */       ((TileAuraTotem)tile).checkPoles();
/* 179 */       worldIn.addBlockEvent(pos, this, 1, 1);
/*     */     } else {
/* 181 */       worldIn.notifyBlockOfStateChange(pos.up(), this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/* 188 */     int metadata = state.getBlock().getMetaFromState(state);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 198 */     if ((metadata == 0) && (!world.isRemote) && (!player.isSneaking()) && (player.getHeldItem() != null) && (player.getHeldItem().getItem() == ItemsTC.eldritchEye))
/*     */     {
/* 200 */       TileEntity te = world.getTileEntity(pos);
/* 201 */       if ((te != null) && ((te instanceof TileEldritchAltar))) {
/* 202 */         TileEldritchAltar tile = (TileEldritchAltar)te;
/* 203 */         if (tile.getEyes() < 4) {
/* 204 */           if (tile.getEyes() >= 2) {
/* 205 */             tile.setSpawner(true);
/* 206 */             tile.setSpawnType((byte)1);
/*     */           }
/* 208 */           tile.setEyes((byte)(tile.getEyes() + 1));
/* 209 */           tile.checkForMaze();
/* 210 */           player.getHeldItem().stackSize -= 1;
/* 211 */           tile.markDirty();
/* 212 */           world.markBlockForUpdate(pos);
/* 213 */           world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:crystal", 0.2F, 1.0F);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 218 */     if ((metadata == 4) && (player.getHeldItem() != null) && (player.getHeldItem().getItem() == ItemsTC.runedTablet)) {
/* 219 */       TileEntity te = world.getTileEntity(pos);
/* 220 */       if ((te != null) && ((te instanceof TileEldritchLock)) && (((TileEldritchLock)te).count < 0)) {
/* 221 */         ((TileEldritchLock)te).count = 0;
/* 222 */         te.markDirty();
/* 223 */         world.markBlockForUpdate(pos);
/* 224 */         player.getHeldItem().stackSize -= 1;
/* 225 */         world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:runicShieldCharge", 1.0F, 1.0F);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 230 */     return super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */   public float getBlockHardness(World worldIn, BlockPos pos)
/*     */   {
/* 235 */     IBlockState state = worldIn.getBlockState(pos);
/* 236 */     if (state.getBlock() != this) return super.getBlockHardness(worldIn, pos);
/* 237 */     return (EldritchType)state.getValue(TYPE) == EldritchType.CRABSPAWNER ? 25.0F : ((EldritchType)state.getValue(TYPE) == EldritchType.LOCK) || ((EldritchType)state.getValue(TYPE) == EldritchType.DOOR) || ((EldritchType)state.getValue(TYPE) == EldritchType.TELEPORTER) ? -1.0F : super.getBlockHardness(worldIn, pos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
/*     */   {
/* 248 */     IBlockState state = worldIn.getBlockState(pos);
/* 249 */     return (EldritchType)state.getValue(TYPE) == EldritchType.TELEPORTER ? AxisAlignedBB.fromBounds(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D) : super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 256 */     return (EldritchType)state.getValue(TYPE) == EldritchType.TELEPORTER ? null : super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/* 265 */     if ((EldritchType)state.getValue(TYPE) == EldritchType.LOCK) {
/* 266 */       TileEntity te = worldIn.getTileEntity(pos);
/* 267 */       if ((te == null) || (!(te instanceof TileEldritchLock)) || (((TileEldritchLock)te).count < 0)) return;
/* 268 */       FXSpark ef = new FXSpark(worldIn, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0.5F);
/*     */       
/*     */ 
/*     */ 
/* 272 */       ef.setRBGColorF(0.65F + rand.nextFloat() * 0.1F, 1.0F, 1.0F);
/* 273 */       ef.setAlphaF(0.8F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum EldritchType
/*     */     implements IStringSerializable
/*     */   {
/* 282 */     ALTAR, 
/* 283 */     CAP, 
/* 284 */     OBELISK_BASE, 
/* 285 */     OBELISK_BODY, 
/* 286 */     LOCK, 
/* 287 */     DOOR, 
/* 288 */     TELEPORTER, 
/* 289 */     CRABSPAWNER;
/*     */     
/*     */     private EldritchType() {}
/*     */     
/*     */     public String getName() {
/* 294 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 300 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\eldritch\BlockEldritch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */