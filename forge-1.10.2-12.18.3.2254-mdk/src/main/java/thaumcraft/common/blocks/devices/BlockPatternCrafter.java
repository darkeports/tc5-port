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
/*     */ import net.minecraft.entity.EntityLivingBase;
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
/*     */ import thaumcraft.common.blocks.IBlockFacingHorizontal;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.tiles.devices.TilePatternCrafter;
/*     */ 
/*     */ public class BlockPatternCrafter
/*     */   extends BlockTCDevice
/*     */   implements IBlockFacingHorizontal, IBlockEnabled
/*     */ {
/*     */   public BlockPatternCrafter()
/*     */   {
/*  44 */     super(Material.iron, TilePatternCrafter.class);
/*  45 */     setStepSound(Block.soundTypeMetal);
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  50 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  56 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  62 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
/*     */   {
/*  67 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  73 */     IBlockState bs = getDefaultState();
/*  74 */     bs = bs.withProperty(IBlockFacingHorizontal.FACING, placer.getHorizontalFacing());
/*  75 */     return bs;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/*  84 */     MovingObjectPosition hit = RayTracer.retraceBlock(world, playerIn, pos);
/*  85 */     if (hit == null) { return super.onBlockActivated(world, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */     }
/*  87 */     TileEntity tile = world.getTileEntity(pos);
/*     */     
/*  89 */     if ((hit.subHit == 0) && ((tile instanceof TilePatternCrafter)))
/*     */     {
/*  91 */       ((TilePatternCrafter)tile).cycle();
/*  92 */       world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:key", 0.5F, 1.0F);
/*  93 */       return true;
/*     */     }
/*     */     
/*  96 */     return super.onBlockActivated(world, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos)
/*     */   {
/* 102 */     TileEntity tile = world.getTileEntity(pos);
/* 103 */     if ((tile != null) && ((tile instanceof TilePatternCrafter))) {
/* 104 */       MovingObjectPosition hit = RayTracer.retraceBlock(world, Minecraft.getMinecraft().thePlayer, pos);
/* 105 */       if ((hit != null) && (hit.subHit == 0)) {
/* 106 */         Cuboid6 cubeoid = ((TilePatternCrafter)tile).getCuboidByFacing(BlockStateUtils.getFacing(tile.getBlockMetadata()));
/* 107 */         Vector3 v = new Vector3(pos);
/* 108 */         Cuboid6 c = cubeoid.sub(v);
/* 109 */         setBlockBounds((float)c.min.x, (float)c.min.y, (float)c.min.z, (float)c.max.x, (float)c.max.y, (float)c.max.z);
/*     */       }
/*     */       else
/*     */       {
/* 113 */         setBlockBoundsBasedOnState(world, pos);
/*     */       }
/*     */     }
/* 116 */     return super.getSelectedBoundingBox(world, pos);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 121 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/* 127 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 128 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 133 */   private RayTracer rayTracer = new RayTracer();
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void onBlockHighlight(DrawBlockHighlightEvent event)
/*     */   {
/* 139 */     if ((event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && (event.player.worldObj.getBlockState(event.target.getBlockPos()).getBlock() == this))
/*     */     {
/* 141 */       RayTracer.retraceBlock(event.player.worldObj, event.player, event.target.getBlockPos());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public MovingObjectPosition collisionRayTrace(World world, BlockPos pos, Vec3 start, Vec3 end)
/*     */   {
/* 148 */     TileEntity tile = world.getTileEntity(pos);
/* 149 */     if ((tile == null) || (!(tile instanceof TilePatternCrafter))) {
/* 150 */       return super.collisionRayTrace(world, pos, start, end);
/*     */     }
/* 152 */     List<IndexedCuboid6> cuboids = new LinkedList();
/* 153 */     if ((tile instanceof TilePatternCrafter)) {
/* 154 */       ((TilePatternCrafter)tile).addTraceableCuboids(cuboids);
/*     */     }
/* 156 */     ArrayList<ExtendedMOP> list = new ArrayList();
/* 157 */     this.rayTracer.rayTraceCuboids(new Vector3(start), new Vector3(end), cuboids, new BlockCoord(pos), this, list);
/* 158 */     return list.size() > 0 ? (MovingObjectPosition)list.get(0) : super.collisionRayTrace(world, pos, start, end);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockPatternCrafter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */