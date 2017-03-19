/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockContainer;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.particle.EffectRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumFacing.Axis;
/*     */ import net.minecraft.util.EnumFacing.Plane;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.client.fx.particles.FXDigging;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ import thaumcraft.common.tiles.devices.TileHungryChest;
/*     */ 
/*     */ public class BlockHungryChest extends BlockContainer
/*     */ {
/*  40 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
/*  41 */   private final Random rand = new Random();
/*     */   
/*     */   public BlockHungryChest() {
/*  44 */     super(Material.wood);
/*  45 */     setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
/*  46 */     setHardness(2.5F);
/*  47 */     setStepSound(soundTypeWood);
/*  48 */     setCreativeTab(Thaumcraft.tabTC);
/*  49 */     setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */   }
/*     */   
/*     */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
/*     */   {
/*  54 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  60 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  66 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
/*     */   {
/*  78 */     int i = target.getBlockPos().getX();
/*  79 */     int j = target.getBlockPos().getY();
/*  80 */     int k = target.getBlockPos().getZ();
/*  81 */     float f = 0.1F;
/*  82 */     double d0 = i + this.rand.nextDouble() * (getBlockBoundsMaxX() - getBlockBoundsMinX() - f * 2.0F) + f + getBlockBoundsMinX();
/*  83 */     double d1 = j + this.rand.nextDouble() * (getBlockBoundsMaxY() - getBlockBoundsMinY() - f * 2.0F) + f + getBlockBoundsMinY();
/*  84 */     double d2 = k + this.rand.nextDouble() * (getBlockBoundsMaxZ() - getBlockBoundsMinZ() - f * 2.0F) + f + getBlockBoundsMinZ();
/*     */     
/*  86 */     if (target.sideHit == EnumFacing.DOWN)
/*     */     {
/*  88 */       d1 = j + getBlockBoundsMinY() - f;
/*     */     }
/*     */     
/*  91 */     if (target.sideHit == EnumFacing.UP)
/*     */     {
/*  93 */       d1 = j + getBlockBoundsMaxY() + f;
/*     */     }
/*     */     
/*  96 */     if (target.sideHit == EnumFacing.NORTH)
/*     */     {
/*  98 */       d2 = k + getBlockBoundsMinZ() - f;
/*     */     }
/*     */     
/* 101 */     if (target.sideHit == EnumFacing.SOUTH)
/*     */     {
/* 103 */       d2 = k + getBlockBoundsMaxZ() + f;
/*     */     }
/*     */     
/* 106 */     if (target.sideHit == EnumFacing.WEST)
/*     */     {
/* 108 */       d0 = i + getBlockBoundsMinX() - f;
/*     */     }
/*     */     
/* 111 */     if (target.sideHit == EnumFacing.EAST)
/*     */     {
/* 113 */       d0 = i + getBlockBoundsMaxX() + f;
/*     */     }
/*     */     
/* 116 */     effectRenderer.addEffect(new FXDigging(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, BlocksTC.plank.getDefaultState()).func_174846_a(target.getBlockPos()));
/*     */     
/* 118 */     return true;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer)
/*     */   {
/* 124 */     byte b0 = 4;
/* 125 */     for (int i = 0; i < b0; i++)
/*     */     {
/* 127 */       for (int j = 0; j < b0; j++)
/*     */       {
/* 129 */         for (int k = 0; k < b0; k++)
/*     */         {
/* 131 */           double d0 = pos.getX() + (i + 0.5D) / b0;
/* 132 */           double d1 = pos.getY() + (j + 0.5D) / b0;
/* 133 */           double d2 = pos.getZ() + (k + 0.5D) / b0;
/* 134 */           effectRenderer.addEffect(new FXDigging(world, d0, d1, d2, d0 - pos.getX() - 0.5D, d1 - pos.getY() - 0.5D, d2 - pos.getZ() - 0.5D, BlocksTC.plank.getDefaultState()).func_174846_a(pos));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 142 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 149 */     setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 154 */     float var5 = 0.0625F;
/* 155 */     return AxisAlignedBB.fromBounds(pos.getX() + var5, pos.getY(), pos.getZ() + var5, pos.getX() + 1 - var5, pos.getY() + 1 - var5, pos.getZ() + 1 - var5);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/* 163 */     return getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
/*     */   }
/*     */   
/*     */ 
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
/*     */   {
/* 169 */     EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3).getOpposite();
/* 170 */     state = state.withProperty(FACING, enumfacing);
/* 171 */     worldIn.setBlockState(pos, state, 3);
/*     */   }
/*     */   
/*     */ 
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 177 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 179 */     if ((tileentity instanceof IInventory))
/*     */     {
/* 181 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 182 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     }
/*     */     
/* 185 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/* 191 */     if (worldIn.isRemote)
/*     */     {
/* 193 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 197 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 199 */     if ((tileentity instanceof IInventory))
/*     */     {
/* 201 */       playerIn.displayGUIChest((IInventory)tileentity);
/*     */     }
/*     */     
/* 204 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
/*     */   {
/* 213 */     Object var10 = (TileHungryChest)world.getTileEntity(pos);
/* 214 */     if (var10 == null)
/*     */     {
/* 216 */       return;
/*     */     }
/* 218 */     if (world.isRemote) {
/* 219 */       return;
/*     */     }
/*     */     
/* 222 */     if (((entity instanceof EntityItem)) && (!entity.isDead)) {
/* 223 */       ItemStack leftovers = InventoryUtils.placeItemStackIntoInventory(((EntityItem)entity).getEntityItem(), (IInventory)var10, EnumFacing.UP, true);
/* 224 */       if ((leftovers == null) || (leftovers.stackSize != ((EntityItem)entity).getEntityItem().stackSize)) {
/* 225 */         world.playSoundAtEntity(entity, "random.eat", 0.25F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
/* 226 */         world.addBlockEvent(pos, BlocksTC.hungryChest, 2, 2);
/*     */       }
/*     */       
/* 229 */       if (leftovers != null) {
/* 230 */         ((EntityItem)entity).setEntityItemStack(leftovers);
/*     */       } else {
/* 232 */         entity.setDead();
/*     */       }
/* 234 */       ((TileHungryChest)var10).markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasComparatorInputOverride()
/*     */   {
/* 241 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos)
/*     */   {
/* 247 */     Object var10 = worldIn.getTileEntity(pos);
/* 248 */     if ((var10 instanceof TileHungryChest)) {
/* 249 */       return Container.calcRedstoneFromInventory((IInventory)var10);
/*     */     }
/* 251 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 257 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 259 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */     {
/* 261 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 264 */     return getDefaultState().withProperty(FACING, enumfacing);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 270 */     return ((EnumFacing)state.getValue(FACING)).getIndex();
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 276 */     return new BlockState(this, new IProperty[] { FACING });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public TileEntity createNewTileEntity(World par1World, int m)
/*     */   {
/* 283 */     return new TileHungryChest();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockHungryChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */