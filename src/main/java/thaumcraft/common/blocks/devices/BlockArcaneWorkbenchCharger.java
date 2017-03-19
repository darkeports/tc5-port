/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.particle.EffectRenderer;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.client.fx.particles.FXDigging;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.tiles.crafting.TileArcaneWorkbenchCharger;
/*     */ 
/*     */ public class BlockArcaneWorkbenchCharger
/*     */   extends BlockTCDevice
/*     */ {
/*     */   public BlockArcaneWorkbenchCharger()
/*     */   {
/*  26 */     super(Material.wood, TileArcaneWorkbenchCharger.class);
/*  27 */     setStepSound(Block.soundTypeWood);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  33 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  39 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  45 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/*  51 */     if (world.isRemote) return true;
/*  52 */     if (world.getBlockState(pos.down()).getBlock() == BlocksTC.arcaneWorkbench)
/*  53 */       player.openGui(Thaumcraft.instance, 13, world, pos.getX(), pos.down().getY(), pos.getZ());
/*  54 */     return true;
/*     */   }
/*     */   
/*  57 */   private final Random rand = new Random();
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
/*     */   {
/*  62 */     int i = target.getBlockPos().getX();
/*  63 */     int j = target.getBlockPos().getY();
/*  64 */     int k = target.getBlockPos().getZ();
/*  65 */     float f = 0.1F;
/*  66 */     double d0 = i + this.rand.nextDouble() * (getBlockBoundsMaxX() - getBlockBoundsMinX() - f * 2.0F) + f + getBlockBoundsMinX();
/*  67 */     double d1 = j + this.rand.nextDouble() * (getBlockBoundsMaxY() - getBlockBoundsMinY() - f * 2.0F) + f + getBlockBoundsMinY();
/*  68 */     double d2 = k + this.rand.nextDouble() * (getBlockBoundsMaxZ() - getBlockBoundsMinZ() - f * 2.0F) + f + getBlockBoundsMinZ();
/*     */     
/*  70 */     if (target.sideHit == EnumFacing.DOWN)
/*     */     {
/*  72 */       d1 = j + getBlockBoundsMinY() - f;
/*     */     }
/*     */     
/*  75 */     if (target.sideHit == EnumFacing.UP)
/*     */     {
/*  77 */       d1 = j + getBlockBoundsMaxY() + f;
/*     */     }
/*     */     
/*  80 */     if (target.sideHit == EnumFacing.NORTH)
/*     */     {
/*  82 */       d2 = k + getBlockBoundsMinZ() - f;
/*     */     }
/*     */     
/*  85 */     if (target.sideHit == EnumFacing.SOUTH)
/*     */     {
/*  87 */       d2 = k + getBlockBoundsMaxZ() + f;
/*     */     }
/*     */     
/*  90 */     if (target.sideHit == EnumFacing.WEST)
/*     */     {
/*  92 */       d0 = i + getBlockBoundsMinX() - f;
/*     */     }
/*     */     
/*  95 */     if (target.sideHit == EnumFacing.EAST)
/*     */     {
/*  97 */       d0 = i + getBlockBoundsMaxX() + f;
/*     */     }
/*     */     
/* 100 */     effectRenderer.addEffect(new FXDigging(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, BlocksTC.plank.getDefaultState()).func_174846_a(target.getBlockPos()));
/*     */     
/* 102 */     return true;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer)
/*     */   {
/* 108 */     byte b0 = 4;
/* 109 */     for (int i = 0; i < b0; i++)
/*     */     {
/* 111 */       for (int j = 0; j < b0; j++)
/*     */       {
/* 113 */         for (int k = 0; k < b0; k++)
/*     */         {
/* 115 */           double d0 = pos.getX() + (i + 0.5D) / b0;
/* 116 */           double d1 = pos.getY() + (j + 0.5D) / b0;
/* 117 */           double d2 = pos.getZ() + (k + 0.5D) / b0;
/* 118 */           effectRenderer.addEffect(new FXDigging(world, d0, d1, d2, d0 - pos.getX() - 0.5D, d1 - pos.getY() - 0.5D, d2 - pos.getZ() - 0.5D, BlocksTC.plank.getDefaultState()).func_174846_a(pos));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 126 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockArcaneWorkbenchCharger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */