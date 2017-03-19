/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.particle.EffectRenderer;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.client.fx.particles.FXDigging;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.blocks.IBlockEnabled;
/*     */ import thaumcraft.common.blocks.IBlockFacing;
/*     */ import thaumcraft.common.tiles.devices.TileBellows;
/*     */ 
/*     */ public class BlockBellows extends BlockTCDevice implements IBlockFacing, IBlockEnabled
/*     */ {
/*     */   public BlockBellows()
/*     */   {
/*  26 */     super(Material.wood, TileBellows.class);
/*  27 */     setStepSound(Block.soundTypeWood);
/*  28 */     setHardness(1.0F);
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/*  33 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  39 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  45 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  51 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  57 */     IBlockState bs = getDefaultState();
/*  58 */     if ((this instanceof IBlockFacing)) bs = bs.withProperty(IBlockFacing.FACING, facing.getOpposite());
/*  59 */     if ((this instanceof IBlockEnabled)) bs = bs.withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(true));
/*  60 */     return bs;
/*     */   }
/*     */   
/*  63 */   private final Random rand = new Random();
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
/*     */   {
/*  68 */     int i = target.getBlockPos().getX();
/*  69 */     int j = target.getBlockPos().getY();
/*  70 */     int k = target.getBlockPos().getZ();
/*  71 */     float f = 0.1F;
/*  72 */     double d0 = i + this.rand.nextDouble() * (getBlockBoundsMaxX() - getBlockBoundsMinX() - f * 2.0F) + f + getBlockBoundsMinX();
/*  73 */     double d1 = j + this.rand.nextDouble() * (getBlockBoundsMaxY() - getBlockBoundsMinY() - f * 2.0F) + f + getBlockBoundsMinY();
/*  74 */     double d2 = k + this.rand.nextDouble() * (getBlockBoundsMaxZ() - getBlockBoundsMinZ() - f * 2.0F) + f + getBlockBoundsMinZ();
/*     */     
/*  76 */     if (target.sideHit == EnumFacing.DOWN)
/*     */     {
/*  78 */       d1 = j + getBlockBoundsMinY() - f;
/*     */     }
/*     */     
/*  81 */     if (target.sideHit == EnumFacing.UP)
/*     */     {
/*  83 */       d1 = j + getBlockBoundsMaxY() + f;
/*     */     }
/*     */     
/*  86 */     if (target.sideHit == EnumFacing.NORTH)
/*     */     {
/*  88 */       d2 = k + getBlockBoundsMinZ() - f;
/*     */     }
/*     */     
/*  91 */     if (target.sideHit == EnumFacing.SOUTH)
/*     */     {
/*  93 */       d2 = k + getBlockBoundsMaxZ() + f;
/*     */     }
/*     */     
/*  96 */     if (target.sideHit == EnumFacing.WEST)
/*     */     {
/*  98 */       d0 = i + getBlockBoundsMinX() - f;
/*     */     }
/*     */     
/* 101 */     if (target.sideHit == EnumFacing.EAST)
/*     */     {
/* 103 */       d0 = i + getBlockBoundsMaxX() + f;
/*     */     }
/*     */     
/* 106 */     effectRenderer.addEffect(new FXDigging(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, BlocksTC.plank.getDefaultState()).func_174846_a(target.getBlockPos()));
/*     */     
/* 108 */     return true;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer)
/*     */   {
/* 114 */     byte b0 = 4;
/* 115 */     for (int i = 0; i < b0; i++)
/*     */     {
/* 117 */       for (int j = 0; j < b0; j++)
/*     */       {
/* 119 */         for (int k = 0; k < b0; k++)
/*     */         {
/* 121 */           double d0 = pos.getX() + (i + 0.5D) / b0;
/* 122 */           double d1 = pos.getY() + (j + 0.5D) / b0;
/* 123 */           double d2 = pos.getZ() + (k + 0.5D) / b0;
/* 124 */           effectRenderer.addEffect(new FXDigging(world, d0, d1, d2, d0 - pos.getX() - 0.5D, d1 - pos.getY() - 0.5D, d2 - pos.getZ() - 0.5D, BlocksTC.plank.getDefaultState()).func_174846_a(pos));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 132 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockBellows.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */