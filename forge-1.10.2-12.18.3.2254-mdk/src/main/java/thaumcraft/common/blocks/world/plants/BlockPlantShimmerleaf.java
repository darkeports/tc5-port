/*    */ package thaumcraft.common.blocks.world.plants;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockBush;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.EnumPlantType;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.client.fx.ParticleEngine;
/*    */ import thaumcraft.client.fx.particles.FXWisp;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class BlockPlantShimmerleaf
/*    */   extends BlockBush
/*    */ {
/*    */   public BlockPlantShimmerleaf()
/*    */   {
/* 24 */     super(Material.plants);
/* 25 */     setCreativeTab(Thaumcraft.tabTC);
/* 26 */     setStepSound(Block.soundTypeGrass);
/* 27 */     setLightLevel(0.4F);
/*    */   }
/*    */   
/*    */   protected boolean canPlaceBlockOn(Block ground, IBlockState state)
/*    */   {
/* 32 */     return (ground == Blocks.grass) || (ground == Blocks.dirt);
/*    */   }
/*    */   
/*    */ 
/*    */   public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
/*    */   {
/* 38 */     return EnumPlantType.Plains;
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
/*    */   {
/* 45 */     if (rand.nextInt(3) == 0) {
/* 46 */       float cr = 0.3F + world.rand.nextFloat() * 0.3F;
/* 47 */       float cg = 0.7F + world.rand.nextFloat() * 0.3F;
/* 48 */       float cb = 0.7F + world.rand.nextFloat() * 0.3F;
/* 49 */       float xr = pos.getX() + 0.5F + (rand.nextFloat() - rand.nextFloat()) * 0.1F;
/* 50 */       float yr = pos.getY() + 0.5F + (rand.nextFloat() - rand.nextFloat()) * 0.15F;
/* 51 */       float zr = pos.getZ() + 0.5F + (rand.nextFloat() - rand.nextFloat()) * 0.1F;
/*    */       
/* 53 */       FXWisp ef = new FXWisp(world, xr, yr, zr, 0.2F, cr, cg, cb);
/*    */       
/* 55 */       ef.tinkle = false;
/* 56 */       ParticleEngine.instance.addEffect(world, ef);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\plants\BlockPlantShimmerleaf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */