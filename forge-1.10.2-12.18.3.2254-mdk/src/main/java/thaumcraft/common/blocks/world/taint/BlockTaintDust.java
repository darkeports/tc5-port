/*     */ package thaumcraft.common.blocks.world.taint;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.BlockFluidFinite;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ 
/*     */ public class BlockTaintDust extends BlockFluidFinite
/*     */ {
/*     */   public BlockTaintDust()
/*     */   {
/*  30 */     super(thaumcraft.common.config.ConfigBlocks.FluidTaintDust.instance, Material.sand);
/*  31 */     setHardness(0.3F);
/*  32 */     setUnlocalizedName("taint_dust");
/*  33 */     setCreativeTab(Thaumcraft.tabTC);
/*  34 */     setStepSound(soundTypeSand);
/*  35 */     setQuantaPerBlock(4);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, Integer.valueOf(3)));
/*  37 */     setHarvestLevel("shovel", 0);
/*     */   }
/*     */   
/*     */   public boolean isToolEffective(String type, IBlockState state)
/*     */   {
/*  42 */     if (type.equals("shovel")) return true;
/*  43 */     return super.isToolEffective(type, state);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumWorldBlockLayer getBlockLayer()
/*     */   {
/*  50 */     return EnumWorldBlockLayer.SOLID;
/*     */   }
/*     */   
/*     */   public boolean isReplaceable(World world, BlockPos pos)
/*     */   {
/*  55 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
/*     */   {
/*  61 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canCollideCheck(IBlockState state, boolean fullHit)
/*     */   {
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/*  72 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
/*     */   {
/*  77 */     int t = getMetaFromState(state);
/*  78 */     if ((!worldIn.isRemote) && (t > 0) && (worldIn.rand.nextFloat() < t * 0.0333F)) {
/*  79 */       if (worldIn.rand.nextBoolean()) {
/*  80 */         spawnAsEntity(worldIn, pos, new ItemStack(Items.dye, 1, 15));
/*     */       } else
/*  82 */         spawnAsEntity(worldIn, pos, new ItemStack(ItemsTC.tainted, 1, 1));
/*     */     }
/*  84 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
/*     */   {
/*  89 */     return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + 0.25F * (getMetaFromState(worldIn.getBlockState(pos)) + 1), pos.getZ() + this.maxZ);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/*  96 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F * getMetaFromState(worldIn.getBlockState(pos)), 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/* 102 */     AxisAlignedBB axisalignedbb1 = getCollisionBoundingBox(worldIn, pos, state);
/*     */     
/* 104 */     if ((axisalignedbb1 != null) && (mask.intersectsWith(axisalignedbb1)))
/*     */     {
/* 106 */       list.add(axisalignedbb1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
/*     */   {
/* 113 */     return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + 0.25F * getMetaFromState(state), pos.getZ() + this.maxZ);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/* 120 */     int meta = ((Integer)state.getValue(LEVEL)).intValue();
/* 121 */     if (rand.nextInt(Config.dustDegrade) == 0) {
/* 122 */       if (meta == 0) {
/* 123 */         world.setBlockToAir(pos);
/*     */       } else {
/* 125 */         world.setBlockState(pos, state.withProperty(LEVEL, Integer.valueOf(meta - 1)), 2);
/*     */       }
/* 127 */       return;
/*     */     }
/* 129 */     super.updateTick(world, pos, state, rand);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\taint\BlockTaintDust.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */