/*    */ package thaumcraft.common.blocks.world;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockGrass;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.EnumSkyBlock;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.client.fx.FXDispatcher;
/*    */ import thaumcraft.common.CommonProxy;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ 
/*    */ public class BlockGrassAmbient
/*    */   extends BlockGrass
/*    */ {
/*    */   public BlockGrassAmbient()
/*    */   {
/* 24 */     setCreativeTab(Thaumcraft.tabTC);
/* 25 */     setHardness(0.6F);
/* 26 */     setStepSound(soundTypeGrass);
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
/*    */   {
/* 32 */     BiomeGenBase biome = worldIn.getBiomeGenForCoords(pos);
/*    */     
/* 34 */     int i = worldIn.getLightFor(EnumSkyBlock.SKY, pos.up()) - worldIn.getSkylightSubtracted();
/* 35 */     float f = worldIn.getCelestialAngleRadians(1.0F);
/* 36 */     float f1 = f < 3.1415927F ? 0.0F : 6.2831855F;
/* 37 */     f += (f1 - f) * 0.2F;
/* 38 */     i = Math.round(i * MathHelper.cos(f));
/* 39 */     i = MathHelper.clamp_int(i, 0, 15);
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 46 */     if (2 + i * 2 < 1 + rand.nextInt(13))
/*    */     {
/* 48 */       int x = MathHelper.getRandomIntegerInRange(rand, -8, 8);
/* 49 */       int z = MathHelper.getRandomIntegerInRange(rand, -8, 8);
/* 50 */       BlockPos pp = pos.add(x, 5, z);
/* 51 */       int q = 0;
/* 52 */       while ((q < 10) && (pp.getY() > 50) && (worldIn.getBlockState(pp).getBlock() != Blocks.grass)) {
/* 53 */         pp = pp.down();
/* 54 */         q++;
/*    */       }
/* 56 */       if (worldIn.getBlockState(pp).getBlock() == Blocks.grass) {
/* 57 */         Thaumcraft.proxy.getFX().drawWispyMotesOnBlock(pp.up(), 400);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\BlockGrassAmbient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */