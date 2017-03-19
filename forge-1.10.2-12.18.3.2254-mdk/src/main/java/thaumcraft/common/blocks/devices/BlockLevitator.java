/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.DrawBlockHighlightEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.codechicken.libold.raytracer.ExtendedMOP;
import thaumcraft.codechicken.libold.raytracer.IndexedCuboid6;
import thaumcraft.codechicken.libold.raytracer.RayTracer;
import thaumcraft.codechicken.libold.vec.BlockCoord;
import thaumcraft.codechicken.libold.vec.Cuboid6;
import thaumcraft.codechicken.libold.vec.Vector3;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.blocks.IBlockEnabled;
/*     */ import thaumcraft.common.blocks.IBlockFacing;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.tiles.devices.TileLevitator;
/*     */ 
/*     */ 
/*     */ public class BlockLevitator
/*     */   extends BlockTCDevice
/*     */   implements IBlockFacing, IBlockEnabled
/*     */ {
/*     */   public BlockLevitator()
/*     */   {
/*  44 */     super(Material.wood, TileLevitator.class);
/*  45 */     setStepSound(Block.soundTypeWood);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  51 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  57 */     return false;
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  62 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/*  70 */     MovingObjectPosition hit = RayTracer.retraceBlock(world, playerIn, pos);
/*  71 */     if (hit == null) { return super.onBlockActivated(world, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */     }
/*  73 */     TileEntity tile = world.getTileEntity(pos);
/*     */     
/*  75 */     if ((hit.subHit == 0) && ((tile instanceof TileLevitator)))
/*     */     {
/*  77 */       ((TileLevitator)tile).increaseRange(playerIn);
/*  78 */       world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:key", 0.5F, 1.0F);
/*  79 */       return true;
/*     */     }
/*     */     
/*  82 */     return super.onBlockActivated(world, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos)
/*     */   {
/*  88 */     TileEntity tile = world.getTileEntity(pos);
/*  89 */     if ((tile != null) && ((tile instanceof TileLevitator))) {
/*  90 */       MovingObjectPosition hit = RayTracer.retraceBlock(world, Minecraft.getMinecraft().thePlayer, pos);
/*  91 */       if ((hit != null) && (hit.subHit == 0)) {
/*  92 */         Cuboid6 cubeoid = ((TileLevitator)tile).getCuboidByFacing(BlockStateUtils.getFacing(tile.getBlockMetadata()));
/*  93 */         Vector3 v = new Vector3(pos);
/*  94 */         Cuboid6 c = cubeoid.sub(v);
/*  95 */         setBlockBounds((float)c.min.x, (float)c.min.y, (float)c.min.z, (float)c.max.x, (float)c.max.y, (float)c.max.z);
/*     */       }
/*     */       else
/*     */       {
/*  99 */         setBlockBoundsBasedOnState(world, pos);
/*     */       }
/*     */     }
/* 102 */     return super.getSelectedBoundingBox(world, pos);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 107 */     EnumFacing facing = BlockStateUtils.getFacing(getMetaFromState(worldIn.getBlockState(pos)));
/* 108 */     float f = 0.125F;
/* 109 */     float minx = 0.0F + (facing.getFrontOffsetX() > 0 ? f : 0.0F);
/* 110 */     float maxx = 1.0F - (facing.getFrontOffsetX() < 0 ? f : 0.0F);
/* 111 */     float miny = 0.0F + (facing.getFrontOffsetY() > 0 ? f : 0.0F);
/* 112 */     float maxy = 1.0F - (facing.getFrontOffsetY() < 0 ? f : 0.0F);
/* 113 */     float minz = 0.0F + (facing.getFrontOffsetZ() > 0 ? f : 0.0F);
/* 114 */     float maxz = 1.0F - (facing.getFrontOffsetZ() < 0 ? f : 0.0F);
/* 115 */     setBlockBounds(minx, miny, minz, maxx, maxy, maxz);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/* 121 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 122 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 127 */   private RayTracer rayTracer = new RayTracer();
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void onBlockHighlight(DrawBlockHighlightEvent event)
/*     */   {
/* 133 */     if ((event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && (event.player.worldObj.getBlockState(event.target.getBlockPos()).getBlock() == this))
/*     */     {
/* 135 */       RayTracer.retraceBlock(event.player.worldObj, event.player, event.target.getBlockPos());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public MovingObjectPosition collisionRayTrace(World world, BlockPos pos, Vec3 start, Vec3 end)
/*     */   {
/* 142 */     TileEntity tile = world.getTileEntity(pos);
/* 143 */     if ((tile == null) || (!(tile instanceof TileLevitator))) {
/* 144 */       return super.collisionRayTrace(world, pos, start, end);
/*     */     }
/* 146 */     List<IndexedCuboid6> cuboids = new LinkedList();
/* 147 */     if ((tile instanceof TileLevitator)) {
/* 148 */       ((TileLevitator)tile).addTraceableCuboids(cuboids);
/*     */     }
/* 150 */     ArrayList<ExtendedMOP> list = new ArrayList();
/* 151 */     this.rayTracer.rayTraceCuboids(new Vector3(start), new Vector3(end), cuboids, new BlockCoord(pos), this, list);
/* 152 */     return list.size() > 0 ? (MovingObjectPosition)list.get(0) : super.collisionRayTrace(world, pos, start, end);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockLevitator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */