/*    */ package thaumcraft.common.blocks.world.plants;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockBush;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.EnumPlantType;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class BlockPlantCinderpearl
/*    */   extends BlockBush
/*    */ {
/*    */   public BlockPlantCinderpearl()
/*    */   {
/* 23 */     super(Material.plants);
/* 24 */     setCreativeTab(Thaumcraft.tabTC);
/* 25 */     setStepSound(Block.soundTypeGrass);
/* 26 */     setLightLevel(0.5F);
/*    */   }
/*    */   
/*    */   protected boolean canPlaceBlockOn(Block ground, IBlockState state)
/*    */   {
/* 31 */     return (ground == Blocks.sand) || (ground == Blocks.dirt) || (ground == Blocks.stained_hardened_clay) || (ground == Blocks.hardened_clay);
/*    */   }
/*    */   
/*    */ 
/*    */   public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
/*    */   {
/* 37 */     return EnumPlantType.Desert;
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
/*    */   {
/* 44 */     if (rand.nextBoolean()) {
/* 45 */       float xr = pos.getX() + 0.5F + (rand.nextFloat() - rand.nextFloat()) * 0.1F;
/* 46 */       float yr = pos.getY() + 0.6F + (rand.nextFloat() - rand.nextFloat()) * 0.1F;
/* 47 */       float zr = pos.getZ() + 0.5F + (rand.nextFloat() - rand.nextFloat()) * 0.1F;
/* 48 */       world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, xr, yr, zr, 0.0D, 0.0D, 0.0D, new int[0]);
/* 49 */       world.spawnParticle(EnumParticleTypes.FLAME, xr, yr, zr, 0.0D, 0.0D, 0.0D, new int[0]);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\plants\BlockPlantCinderpearl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */