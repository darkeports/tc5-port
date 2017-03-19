/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.DrawBlockHighlightEvent;
/*     */ import net.minecraftforge.common.property.ExtendedBlockState;
/*     */ import net.minecraftforge.common.property.IExtendedBlockState;
/*     */ import net.minecraftforge.common.property.IUnlistedProperty;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.ThaumcraftApiHelper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IEssentiaContainerItem;
/*     */ import thaumcraft.api.aspects.IEssentiaTransport;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.codechicken.libold.raytracer.IndexedCuboid6;
import thaumcraft.codechicken.libold.raytracer.RayTracer;
import thaumcraft.codechicken.libold.vec.Cuboid6;
import thaumcraft.codechicken.libold.vec.Vector3;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.items.tools.ItemResonator;
/*     */ import thaumcraft.common.tiles.essentia.TileTube;
/*     */ import thaumcraft.common.tiles.essentia.TileTubeBuffer;
/*     */ import thaumcraft.common.tiles.essentia.TileTubeFilter;
/*     */ import thaumcraft.common.tiles.essentia.TileTubeRestrict;
/*     */ import thaumcraft.common.tiles.essentia.TileTubeValve;
/*     */ 
/*     */ public class BlockTube extends BlockTCDevice
/*     */ {
/*  63 */   public static final PropertyEnum TYPE = PropertyEnum.create("type", TubeType.class);
/*     */   
/*     */   public BlockTube()
/*     */   {
/*  67 */     super(Material.iron, TileTube.class);
/*  68 */     setHardness(0.5F);
/*  69 */     setResistance(5.0F);
/*  70 */     setStepSound(Block.soundTypeMetal);
/*  71 */     setDefaultState(this.blockState.getBaseState().withProperty(TYPE, TubeType.NORMAL));
/*     */   }
/*     */   
/*     */ 
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta)
/*     */   {
/*  77 */     switch (meta) {
/*  78 */     case 0:  return new TileTube();
/*  79 */     case 1:  return new TileTubeValve();
/*  80 */     case 2:  return new TileTubeRestrict();
/*  81 */     case 3:  return new thaumcraft.common.tiles.essentia.TileTubeOneway();
/*  82 */     case 4:  return new TileTubeFilter();
/*  83 */     case 5:  return new TileTubeBuffer();
/*     */     }
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  90 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  96 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/* 102 */     return getStateFromMeta(meta);
/*     */   }
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
/*     */   {
/* 107 */     TileEntity tile = worldIn.getTileEntity(pos);
/* 108 */     if ((tile != null) && ((tile instanceof TileTube))) {
/* 109 */       ((TileTube)tile).facing = BlockPistonBase.getFacingFromEntity(worldIn, pos, placer);
/* 110 */       tile.markDirty();
/*     */     }
/* 112 */     super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 118 */     return meta >= TubeType.values().length ? getDefaultState() : getDefaultState().withProperty(TYPE, TubeType.values()[meta]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 124 */     return ((TubeType)state.getValue(TYPE)).ordinal();
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 130 */     return new ExtendedBlockState(this, new IProperty[] { TYPE }, new IUnlistedProperty[] { CONNECTIONS });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/* 139 */     TubeType type = (TubeType)state.getValue(TYPE);
/* 140 */     return "tube_" + type.getName();
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/* 146 */     return new IProperty[] { TYPE };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
/*     */   {
/* 153 */     if ((state instanceof IExtendedBlockState)) {
/* 154 */       Boolean[] cons = { Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false) };
/* 155 */       TileEntity t = world.getTileEntity(pos);
/* 156 */       if ((t != null) && ((t instanceof IEssentiaTransport))) {
/* 157 */         IEssentiaTransport tube = (IEssentiaTransport)t;
/* 158 */         int a = 0;
/* 159 */         for (EnumFacing face : EnumFacing.VALUES) {
/* 160 */           if ((tube.isConnectable(face)) && (ThaumcraftApiHelper.getConnectableTile(world, pos, face) != null)) {
/* 161 */             cons[a] = Boolean.valueOf(true);
/*     */           }
/* 163 */           a++;
/*     */         }
/*     */       }
/* 166 */       return ((IExtendedBlockState)state).withProperty(CONNECTIONS, cons);
/*     */     }
/* 168 */     return state;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos)
/*     */   {
/* 177 */     boolean noDoodads = (Minecraft.getMinecraft().thePlayer == null) || (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() == null) || ((!(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof IWand)) && (!(Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemResonator)));
/*     */     
/*     */ 
/*     */ 
/* 181 */     TileEntity tile = world.getTileEntity(pos);
/* 182 */     if ((tile != null) && ((tile instanceof TileTube))) {
/* 183 */       MovingObjectPosition hit = RayTracer.retraceBlock(world, Minecraft.getMinecraft().thePlayer, pos);
/* 184 */       List<IndexedCuboid6> cuboids = new LinkedList();
/* 185 */       ((TileTube)tile).addTraceableCuboids(cuboids);
/* 186 */       if ((hit != null) && (hit.subHit >= 0) && (hit.subHit <= 6) && (!noDoodads)) {
/* 187 */         for (IndexedCuboid6 cc : cuboids) {
/* 188 */           if (((Integer)cc.data).intValue() == hit.subHit) {
/* 189 */             Vector3 v = new Vector3(pos);
/* 190 */             Cuboid6 c = cc.sub(v);
/* 191 */             setBlockBounds((float)c.min.x, (float)c.min.y, (float)c.min.z, (float)c.max.x, (float)c.max.y, (float)c.max.z);
/*     */             
/*     */ 
/* 194 */             break;
/*     */           }
/*     */         }
/*     */       } else {
/* 198 */         setBlockBoundsBasedOnState(world, pos);
/*     */       }
/*     */     }
/*     */     
/* 202 */     return super.getSelectedBoundingBox(world, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
/*     */   {
/* 208 */     float minx = 0.375F;float maxx = 0.625F;
/* 209 */     float miny = 0.3125F;float maxy = 0.6875F;
/* 210 */     float minz = 0.3125F;float maxz = 0.6875F;
/* 211 */     EnumFacing fd = null;
/* 212 */     for (int side = 0; side < 6; side++) {
/* 213 */       fd = EnumFacing.VALUES[side];
/* 214 */       TileEntity te = ThaumcraftApiHelper.getConnectableTile(world, pos, fd);
/* 215 */       if (te != null) {
/* 216 */         switch (side) {
/*     */         case 0: 
/* 218 */           miny = 0.0F; break;
/* 219 */         case 1:  maxy = 1.0F; break;
/* 220 */         case 2:  minz = 0.0F; break;
/* 221 */         case 3:  maxz = 1.0F; break;
/* 222 */         case 4:  minx = 0.0F; break;
/* 223 */         case 5:  maxx = 1.0F;
/*     */         }
/*     */       }
/*     */     }
/* 227 */     setBlockBounds(minx, miny, minz, maxx, maxy, maxz);
/*     */   }
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
/*     */   public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity)
/*     */   {
/* 241 */     float minx = 0.375F;float maxx = 0.625F;
/* 242 */     float miny = 0.375F;float maxy = 0.625F;
/* 243 */     float minz = 0.375F;float maxz = 0.625F;
/*     */     
/* 245 */     EnumFacing fd = null;
/* 246 */     for (int side = 0; side < 6; side++) {
/* 247 */       fd = EnumFacing.VALUES[side];
/* 248 */       TileEntity te = ThaumcraftApiHelper.getConnectableTile(world, pos, fd);
/* 249 */       if (te != null) {
/* 250 */         switch (side) {
/*     */         case 0: 
/* 252 */           miny = 0.0F; break;
/* 253 */         case 1:  maxy = 1.0F; break;
/* 254 */         case 2:  minz = 0.0F; break;
/* 255 */         case 3:  maxz = 1.0F; break;
/* 256 */         case 4:  minx = 0.0F; break;
/* 257 */         case 5:  maxx = 1.0F;
/*     */         }
/*     */         
/*     */       }
/*     */     }
/* 262 */     setBlockBounds(minx, miny, minz, maxx, maxy, maxz);
/* 263 */     super.addCollisionBoxesToList(world, pos, state, axisalignedbb, arraylist, par7Entity);
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride()
/*     */   {
/* 268 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getComparatorInputOverride(World world, BlockPos pos)
/*     */   {
/* 274 */     TileEntity te = world.getTileEntity(pos);
/* 275 */     if ((te != null) && ((te instanceof TileTubeBuffer))) {
/* 276 */       ((TileTubeBuffer)te).getClass();float r = ((TileTubeBuffer)te).aspects.visSize() / 8.0F;
/* 277 */       return MathHelper.floor_float(r * 14.0F) + (((TileTubeBuffer)te).aspects.visSize() > 0 ? 1 : 0);
/*     */     }
/* 279 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
/*     */   {
/* 286 */     if (renderPass == 1) {
/* 287 */       TileEntity te = worldIn.getTileEntity(pos);
/* 288 */       if ((te != null) && ((te instanceof TileTubeFilter)) && (((TileTubeFilter)te).aspectFilter != null)) {
/* 289 */         return ((TileTubeFilter)te).aspectFilter.getColor();
/*     */       }
/*     */     }
/* 292 */     return super.colorMultiplier(worldIn, pos, renderPass);
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 297 */     TileEntity te = worldIn.getTileEntity(pos);
/* 298 */     if ((te != null) && ((te instanceof TileTube)) && (((TileTube)te).getEssentiaAmount(EnumFacing.UP) > 0)) {
/* 299 */       if (!worldIn.isRemote) {
/* 300 */         AuraHelper.pollute(worldIn, pos, ((TileTube)te).getEssentiaAmount(EnumFacing.UP), true);
/*     */       } else {
/* 302 */         worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.fizz", 0.1F, 1.0F + worldIn.rand.nextFloat() * 0.1F, false);
/* 303 */         for (int a = 0; a < 5; a++) {
/* 304 */           Thaumcraft.proxy.getFX().drawVentParticles(pos.getX() + 0.33D + worldIn.rand.nextFloat() * 0.33D, pos.getY() + 0.33D + worldIn.rand.nextFloat() * 0.33D, pos.getZ() + 0.33D + worldIn.rand.nextFloat() * 0.33D, 0.0D, 0.0D, 0.0D, Aspect.FLUX.getColor());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 310 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/* 317 */     if (state.getValue(TYPE) == TubeType.VALVE) {
/* 318 */       if ((player.getHeldItem() != null) && (((player.getHeldItem().getItem() instanceof IWand)) || ((player.getHeldItem().getItem() instanceof ItemResonator)) || (player.getHeldItem().getItem() == Item.getItemFromBlock(this))))
/*     */       {
/*     */ 
/*     */ 
/* 322 */         return false; }
/* 323 */       TileEntity te = world.getTileEntity(pos);
/* 324 */       if ((te instanceof TileTubeValve)) {
/* 325 */         ((TileTubeValve)te).allowFlow = (!((TileTubeValve)te).allowFlow);
/* 326 */         world.markBlockForUpdate(pos);
/* 327 */         te.markDirty();
/* 328 */         if (!world.isRemote) {
/* 329 */           world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:squeek", 0.7F, 0.9F + world.rand.nextFloat() * 0.2F);
/*     */         }
/* 331 */         return true;
/*     */       }
/*     */     }
/* 334 */     if (state.getValue(TYPE) == TubeType.FILTER) {
/* 335 */       TileEntity te = world.getTileEntity(pos);
/* 336 */       if ((te != null) && ((te instanceof TileTubeFilter)) && (player.isSneaking()) && (((TileTubeFilter)te).aspectFilter != null))
/*     */       {
/* 338 */         ((TileTubeFilter)te).aspectFilter = null;
/* 339 */         world.markBlockForUpdate(pos);
/* 340 */         te.markDirty();
/* 341 */         if (world.isRemote) {
/* 342 */           world.playSound(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:page", 1.0F, 1.0F, false);
/*     */         }
/* 344 */         return true;
/*     */       }
/* 346 */       if ((te != null) && ((te instanceof TileTubeFilter)) && (player.getHeldItem() != null) && (((TileTubeFilter)te).aspectFilter == null) && ((player.getHeldItem().getItem() instanceof IEssentiaContainerItem)))
/*     */       {
/*     */ 
/*     */ 
/* 350 */         if (((IEssentiaContainerItem)player.getHeldItem().getItem()).getAspects(player.getHeldItem()) != null) {
/* 351 */           ((TileTubeFilter)te).aspectFilter = ((IEssentiaContainerItem)(IEssentiaContainerItem)player.getHeldItem().getItem()).getAspects(player.getHeldItem()).getAspects()[0];
/* 352 */           world.markBlockForUpdate(pos);
/* 353 */           te.markDirty();
/* 354 */           if (world.isRemote) {
/* 355 */             world.playSound(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:page", 1.0F, 1.0F, false);
/*     */           }
/*     */         }
/* 358 */         return true;
/*     */       }
/*     */     }
/* 361 */     return super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */ 
/* 365 */   private RayTracer rayTracer = new RayTracer();
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void onBlockHighlight(DrawBlockHighlightEvent event)
/*     */   {
/* 373 */     if ((event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && (event.player.worldObj.getBlockState(event.target.getBlockPos()).getBlock() == this) && (event.player.getCurrentEquippedItem() != null) && (((event.player.getCurrentEquippedItem().getItem() instanceof IWand)) || ((event.player.getCurrentEquippedItem().getItem() instanceof ItemResonator))))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 378 */       RayTracer.retraceBlock(event.player.worldObj, event.player, event.target.getBlockPos());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public MovingObjectPosition collisionRayTrace(World world, BlockPos pos, Vec3 start, Vec3 end)
/*     */   {
/* 385 */     TileEntity tile = world.getTileEntity(pos);
/* 386 */     if ((tile == null) || ((!(tile instanceof TileTube)) && (!(tile instanceof TileTubeBuffer)))) {
/* 387 */       return super.collisionRayTrace(world, pos, start, end);
/*     */     }
/* 389 */     List<IndexedCuboid6> cuboids = new LinkedList();
/* 390 */     if ((tile instanceof TileTube)) {
/* 391 */       ((TileTube)tile).addTraceableCuboids(cuboids);
/* 392 */     } else if ((tile instanceof TileTubeBuffer)) {
/* 393 */       ((TileTubeBuffer)tile).addTraceableCuboids(cuboids);
/*     */     }
/* 395 */     ArrayList<thaumcraft.codechicken.libold.raytracer.ExtendedMOP> list = new ArrayList();
/* 396 */     this.rayTracer.rayTraceCuboids(new Vector3(start), new Vector3(end), cuboids, new thaumcraft.codechicken.libold.vec.BlockCoord(pos), this, list);
/* 397 */     return list.size() > 0 ? (MovingObjectPosition)list.get(0) : super.collisionRayTrace(world, pos, start, end);
/*     */   }
/*     */   
/* 400 */   public static final IUnlistedProperty<Boolean[]> CONNECTIONS = new IUnlistedProperty()
/*     */   {
/*     */     public String getName() {
/* 403 */       return "connections";
/*     */     }
/*     */     
/*     */     public boolean isValid(Boolean[] value) {
/* 407 */       return true;
/*     */     }
/*     */     
/*     */     public Class<Boolean[]> getType() {
/* 411 */       return Boolean[].class;
/*     */     }
/*     */     
/*     */     public String valueToString(Boolean[] value) {
/* 415 */       return value.toString();
/*     */     }
/*     */   };
/*     */   
/*     */   public static enum TubeType implements IStringSerializable
/*     */   {
/* 421 */     NORMAL, 
/* 422 */     VALVE, 
/* 423 */     RESTRICT, 
/* 424 */     ONEWAY, 
/* 425 */     FILTER, 
/* 426 */     BUFFER;
/*     */     
/*     */     private TubeType() {}
/*     */     
/*     */     public String getName() {
/* 431 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 437 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */