/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.particle.EffectRenderer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.client.fx.particles.FXDigging;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.tiles.crafting.TileInfusionMatrix;
/*     */ 
/*     */ public class BlockInfusionMatrix extends BlockTCDevice
/*     */ {
/*     */   public BlockInfusionMatrix()
/*     */   {
/*  22 */     super(Material.rock, TileInfusionMatrix.class);
/*  23 */     setStepSound(Block.soundTypeStone);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  29 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  35 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  41 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*  45 */   private final Random rand = new Random();
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
/*     */   {
/*  50 */     int i = target.getBlockPos().getX();
/*  51 */     int j = target.getBlockPos().getY();
/*  52 */     int k = target.getBlockPos().getZ();
/*  53 */     float f = 0.1F;
/*  54 */     double d0 = i + this.rand.nextDouble() * (getBlockBoundsMaxX() - getBlockBoundsMinX() - f * 2.0F) + f + getBlockBoundsMinX();
/*  55 */     double d1 = j + this.rand.nextDouble() * (getBlockBoundsMaxY() - getBlockBoundsMinY() - f * 2.0F) + f + getBlockBoundsMinY();
/*  56 */     double d2 = k + this.rand.nextDouble() * (getBlockBoundsMaxZ() - getBlockBoundsMinZ() - f * 2.0F) + f + getBlockBoundsMinZ();
/*     */     
/*  58 */     if (target.sideHit == EnumFacing.DOWN)
/*     */     {
/*  60 */       d1 = j + getBlockBoundsMinY() - f;
/*     */     }
/*     */     
/*  63 */     if (target.sideHit == EnumFacing.UP)
/*     */     {
/*  65 */       d1 = j + getBlockBoundsMaxY() + f;
/*     */     }
/*     */     
/*  68 */     if (target.sideHit == EnumFacing.NORTH)
/*     */     {
/*  70 */       d2 = k + getBlockBoundsMinZ() - f;
/*     */     }
/*     */     
/*  73 */     if (target.sideHit == EnumFacing.SOUTH)
/*     */     {
/*  75 */       d2 = k + getBlockBoundsMaxZ() + f;
/*     */     }
/*     */     
/*  78 */     if (target.sideHit == EnumFacing.WEST)
/*     */     {
/*  80 */       d0 = i + getBlockBoundsMinX() - f;
/*     */     }
/*     */     
/*  83 */     if (target.sideHit == EnumFacing.EAST)
/*     */     {
/*  85 */       d0 = i + getBlockBoundsMaxX() + f;
/*     */     }
/*     */     
/*  88 */     effectRenderer.addEffect(new FXDigging(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, BlocksTC.stone.getDefaultState()).func_174846_a(target.getBlockPos()));
/*     */     
/*  90 */     return true;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer)
/*     */   {
/*  96 */     byte b0 = 4;
/*  97 */     for (int i = 0; i < b0; i++)
/*     */     {
/*  99 */       for (int j = 0; j < b0; j++)
/*     */       {
/* 101 */         for (int k = 0; k < b0; k++)
/*     */         {
/* 103 */           double d0 = pos.getX() + (i + 0.5D) / b0;
/* 104 */           double d1 = pos.getY() + (j + 0.5D) / b0;
/* 105 */           double d2 = pos.getZ() + (k + 0.5D) / b0;
/* 106 */           effectRenderer.addEffect(new FXDigging(world, d0, d1, d2, d0 - pos.getX() - 0.5D, d1 - pos.getY() - 0.5D, d2 - pos.getZ() - 0.5D, BlocksTC.stone.getDefaultState()).func_174846_a(pos));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 114 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockInfusionMatrix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */