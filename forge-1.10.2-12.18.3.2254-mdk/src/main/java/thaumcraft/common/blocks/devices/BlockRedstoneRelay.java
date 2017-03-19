/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockRedstoneComparator;
/*     */ import net.minecraft.block.BlockRedstoneWire;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.DrawBlockHighlightEvent;
/*     */ import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.codechicken.libold.raytracer.RayTracer;
import thaumcraft.codechicken.libold.vec.Cuboid6;
import thaumcraft.codechicken.libold.vec.Vector3;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.blocks.IBlockEnabled;
/*     */ import thaumcraft.common.blocks.IBlockFacingHorizontal;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.tiles.devices.TileRedstoneRelay;
/*     */ 
/*     */ public class BlockRedstoneRelay extends BlockTCDevice implements IBlockFacingHorizontal, IBlockEnabled
/*     */ {
/*     */   public BlockRedstoneRelay()
/*     */   {
/*  47 */     super(Material.circuits, TileRedstoneRelay.class);
/*  48 */     setHardness(0.0F);
/*  49 */     setResistance(0.0F);
/*  50 */     setStepSound(soundTypeWood);
/*  51 */     disableStats();
/*  52 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  58 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  64 */     return false;
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  69 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
/*     */   {
/*  75 */     return World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) ? super.canPlaceBlockAt(worldIn, pos) : false;
/*     */   }
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos)
/*     */   {
/*  80 */     return World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/*  92 */     if (!playerIn.capabilities.allowEdit)
/*     */     {
/*  94 */       return false;
/*     */     }
/*     */     
/*  97 */     MovingObjectPosition hit = RayTracer.retraceBlock(world, playerIn, pos);
/*  98 */     if (hit == null) { return super.onBlockActivated(world, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */     }
/* 100 */     TileEntity tile = world.getTileEntity(pos);
/*     */     
/* 102 */     if ((tile != null) && ((tile instanceof TileRedstoneRelay)))
/*     */     {
/* 104 */       if (hit.subHit == 0) {
/* 105 */         ((TileRedstoneRelay)tile).increaseOut();
/* 106 */         world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:key", 0.5F, 1.0F);
/* 107 */         updateState(world, pos, state);
/* 108 */         notifyNeighbors(world, pos, state);
/*     */       }
/* 110 */       if (hit.subHit == 1) {
/* 111 */         ((TileRedstoneRelay)tile).increaseIn();
/* 112 */         world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:key", 0.5F, 1.0F);
/* 113 */         updateState(world, pos, state);
/* 114 */         notifyNeighbors(world, pos, state);
/*     */       }
/* 116 */       return true;
/*     */     }
/*     */     
/* 119 */     return super.onBlockActivated(world, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/* 126 */     boolean flag = shouldBePowered(worldIn, pos, state);
/*     */     
/* 128 */     if ((isPowered(state)) && (!flag))
/*     */     {
/* 130 */       worldIn.setBlockState(pos, getUnpoweredState(state), 2);
/* 131 */       notifyNeighbors(worldIn, pos, state);
/*     */     }
/* 133 */     else if (!isPowered(state))
/*     */     {
/* 135 */       worldIn.setBlockState(pos, getPoweredState(state), 2);
/* 136 */       notifyNeighbors(worldIn, pos, state);
/* 137 */       if (!flag)
/*     */       {
/* 139 */         worldIn.updateBlockTick(pos, getPoweredState(state).getBlock(), getTickDelay(state), -1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 147 */     super.breakBlock(worldIn, pos, state);
/* 148 */     notifyNeighbors(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
/*     */   {
/* 155 */     return side.getAxis() != net.minecraft.util.EnumFacing.Axis.Y;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean isPowered(IBlockState state)
/*     */   {
/* 161 */     return BlockStateUtils.isEnabled(state);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
/*     */   {
/* 167 */     return getWeakPower(worldIn, pos, state, side);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
/*     */   {
/* 173 */     return state.getValue(FACING) == side ? getActiveSignal(worldIn, pos, state) : !isPowered(state) ? 0 : 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/* 179 */     if (canBlockStay(worldIn, pos))
/*     */     {
/* 181 */       updateState(worldIn, pos, state);
/*     */     }
/*     */     else
/*     */     {
/* 185 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 186 */       worldIn.setBlockToAir(pos);
/* 187 */       EnumFacing[] aenumfacing = EnumFacing.values();
/* 188 */       int i = aenumfacing.length;
/*     */       
/* 190 */       for (int j = 0; j < i; j++)
/*     */       {
/* 192 */         EnumFacing enumfacing = aenumfacing[j];
/* 193 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 201 */     boolean flag = shouldBePowered(worldIn, pos, state);
/*     */     
/* 203 */     if (((isPowered(state)) && (!flag)) || ((!isPowered(state)) && (flag) && (!worldIn.isBlockTickPending(pos, this))))
/*     */     {
/* 205 */       byte b0 = -1;
/*     */       
/* 207 */       if (isFacingTowardsRepeater(worldIn, pos, state))
/*     */       {
/* 209 */         b0 = -3;
/*     */       }
/* 211 */       else if (isPowered(state))
/*     */       {
/* 213 */         b0 = -2;
/*     */       }
/*     */       
/* 216 */       worldIn.updateBlockTick(pos, this, getTickDelay(state), b0);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 223 */     int pr = 1;
/* 224 */     TileEntity tile = worldIn.getTileEntity(pos);
/* 225 */     if ((tile != null) && ((tile instanceof TileRedstoneRelay)))
/*     */     {
/* 227 */       pr = ((TileRedstoneRelay)tile).getIn();
/*     */     }
/* 229 */     return calculateInputStrength(worldIn, pos, state) >= pr;
/*     */   }
/*     */   
/*     */   protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 234 */     EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
/* 235 */     BlockPos blockpos1 = pos.offset(enumfacing);
/* 236 */     int i = worldIn.getRedstonePower(blockpos1, enumfacing);
/*     */     
/* 238 */     if (i >= 15)
/*     */     {
/* 240 */       return i;
/*     */     }
/*     */     
/*     */ 
/* 244 */     IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
/* 245 */     return Math.max(i, iblockstate1.getBlock() == Blocks.redstone_wire ? ((Integer)iblockstate1.getValue(BlockRedstoneWire.POWER)).intValue() : 0);
/*     */   }
/*     */   
/*     */ 
/*     */   protected int getPowerOnSides(IBlockAccess worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 251 */     EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
/* 252 */     EnumFacing enumfacing1 = enumfacing.rotateY();
/* 253 */     EnumFacing enumfacing2 = enumfacing.rotateYCCW();
/* 254 */     return Math.max(getPowerOnSide(worldIn, pos.offset(enumfacing1), enumfacing1), getPowerOnSide(worldIn, pos.offset(enumfacing2), enumfacing2));
/*     */   }
/*     */   
/*     */   protected int getPowerOnSide(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
/*     */   {
/* 259 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 260 */     Block block = iblockstate.getBlock();
/* 261 */     return canPowerSide(block) ? worldIn.getStrongPower(pos, side) : block == Blocks.redstone_wire ? ((Integer)iblockstate.getValue(BlockRedstoneWire.POWER)).intValue() : 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canProvidePower()
/*     */   {
/* 267 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
/*     */   {
/* 273 */     if (shouldBePowered(worldIn, pos, state))
/*     */     {
/* 275 */       worldIn.scheduleUpdate(pos, this, 1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/* 282 */     IBlockState bs = getDefaultState();
/* 283 */     bs = bs.withProperty(FACING, placer.isSneaking() ? placer.getHorizontalFacing() : placer.getHorizontalFacing().getOpposite());
/* 284 */     bs = bs.withProperty(ENABLED, Boolean.valueOf(false));
/* 285 */     return bs;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 291 */     notifyNeighbors(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void notifyNeighbors(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 297 */     EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
/* 298 */     BlockPos blockpos1 = pos.offset(enumfacing.getOpposite());
/* 299 */     if (net.minecraftforge.event.ForgeEventFactory.onNeighborNotify(worldIn, pos, worldIn.getBlockState(pos), EnumSet.of(enumfacing.getOpposite())).isCanceled())
/* 300 */       return;
/* 301 */     worldIn.notifyBlockOfStateChange(blockpos1, this);
/* 302 */     worldIn.notifyNeighborsOfStateExcept(blockpos1, this, enumfacing);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 308 */     if (isPowered(state))
/*     */     {
/* 310 */       EnumFacing[] aenumfacing = EnumFacing.values();
/* 311 */       int i = aenumfacing.length;
/*     */       
/* 313 */       for (int j = 0; j < i; j++)
/*     */       {
/* 315 */         EnumFacing enumfacing = aenumfacing[j];
/* 316 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */     }
/*     */     
/* 320 */     super.onBlockDestroyedByPlayer(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canPowerSide(Block blockIn)
/*     */   {
/* 326 */     return blockIn.canProvidePower();
/*     */   }
/*     */   
/*     */   protected int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 331 */     TileEntity tile = worldIn.getTileEntity(pos);
/* 332 */     if ((tile != null) && ((tile instanceof TileRedstoneRelay)))
/*     */     {
/* 334 */       return ((TileRedstoneRelay)tile).getOut();
/*     */     }
/* 336 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean isRedstoneRepeaterBlockID(Block blockIn)
/*     */   {
/* 341 */     return (Blocks.unpowered_repeater.isAssociated(blockIn)) || (Blocks.unpowered_comparator.isAssociated(blockIn));
/*     */   }
/*     */   
/*     */   public boolean isAssociated(Block other)
/*     */   {
/* 346 */     return (other == getPoweredState(getDefaultState()).getBlock()) || (other == getUnpoweredState(getDefaultState()).getBlock());
/*     */   }
/*     */   
/*     */   public boolean isFacingTowardsRepeater(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 351 */     EnumFacing enumfacing = ((EnumFacing)state.getValue(FACING)).getOpposite();
/* 352 */     BlockPos blockpos1 = pos.offset(enumfacing);
/* 353 */     return worldIn.getBlockState(blockpos1).getValue(FACING) != enumfacing;
/*     */   }
/*     */   
/*     */   protected int getTickDelay(IBlockState state)
/*     */   {
/* 358 */     return 2;
/*     */   }
/*     */   
/*     */   protected IBlockState getPoweredState(IBlockState unpoweredState)
/*     */   {
/* 363 */     EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue(FACING);
/* 364 */     return getDefaultState().withProperty(FACING, enumfacing).withProperty(ENABLED, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   protected IBlockState getUnpoweredState(IBlockState poweredState)
/*     */   {
/* 369 */     EnumFacing enumfacing = (EnumFacing)poweredState.getValue(FACING);
/* 370 */     return getDefaultState().withProperty(FACING, enumfacing).withProperty(ENABLED, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isAssociatedBlock(Block other)
/*     */   {
/* 376 */     return isAssociated(other);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumWorldBlockLayer getBlockLayer()
/*     */   {
/* 383 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 388 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/* 394 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/* 395 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */   
/*     */ 
/* 399 */   private RayTracer rayTracer = new RayTracer();
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos)
/*     */   {
/* 404 */     TileEntity tile = world.getTileEntity(pos);
/* 405 */     if ((tile != null) && ((tile instanceof TileRedstoneRelay))) {
/* 406 */       MovingObjectPosition hit = RayTracer.retraceBlock(world, Minecraft.getMinecraft().thePlayer, pos);
/* 407 */       if ((hit != null) && (hit.subHit == 0)) {
/* 408 */         Cuboid6 cubeoid = ((TileRedstoneRelay)tile).getCuboid0(BlockStateUtils.getFacing(tile.getBlockMetadata()));
/* 409 */         Vector3 v = new Vector3(pos);
/* 410 */         Cuboid6 c = cubeoid.sub(v);
/* 411 */         setBlockBounds((float)c.min.x, (float)c.min.y, (float)c.min.z, (float)c.max.x, (float)c.max.y, (float)c.max.z);
/*     */ 
/*     */ 
/*     */       }
/* 415 */       else if ((hit != null) && (hit.subHit == 1)) {
/* 416 */         Cuboid6 cubeoid = ((TileRedstoneRelay)tile).getCuboid1(BlockStateUtils.getFacing(tile.getBlockMetadata()));
/* 417 */         Vector3 v = new Vector3(pos);
/* 418 */         Cuboid6 c = cubeoid.sub(v);
/* 419 */         setBlockBounds((float)c.min.x, (float)c.min.y, (float)c.min.z, (float)c.max.x, (float)c.max.y, (float)c.max.z);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 424 */         setBlockBoundsBasedOnState(world, pos);
/*     */       }
/*     */     }
/* 427 */     return super.getSelectedBoundingBox(world, pos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void onBlockHighlight(DrawBlockHighlightEvent event)
/*     */   {
/* 436 */     if ((event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && (event.player.worldObj.getBlockState(event.target.getBlockPos()).getBlock() == this))
/*     */     {
/* 438 */       RayTracer.retraceBlock(event.player.worldObj, event.player, event.target.getBlockPos());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public MovingObjectPosition collisionRayTrace(World world, BlockPos pos, Vec3 start, Vec3 end)
/*     */   {
/* 445 */     TileEntity tile = world.getTileEntity(pos);
/* 446 */     if ((tile == null) || (!(tile instanceof TileRedstoneRelay))) {
/* 447 */       return super.collisionRayTrace(world, pos, start, end);
/*     */     }
/* 449 */     List<thaumcraft.codechicken.libold.raytracer.IndexedCuboid6> cuboids = new java.util.LinkedList();
/* 450 */     if ((tile instanceof TileRedstoneRelay)) {
/* 451 */       ((TileRedstoneRelay)tile).addTraceableCuboids(cuboids);
/*     */     }
/* 453 */     ArrayList<thaumcraft.codechicken.libold.raytracer.ExtendedMOP> list = new ArrayList();
/* 454 */     this.rayTracer.rayTraceCuboids(new Vector3(start), new Vector3(end), cuboids, new thaumcraft.codechicken.libold.vec.BlockCoord(pos), this, list);
/* 455 */     return list.size() > 0 ? (MovingObjectPosition)list.get(0) : super.collisionRayTrace(world, pos, start, end);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockRedstoneRelay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */