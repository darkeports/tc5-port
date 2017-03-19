/*    */ package thaumcraft.common.blocks.world.plants;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockBush;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
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
/*    */ public class BlockPlantVishroom
/*    */   extends BlockBush
/*    */ {
/*    */   public BlockPlantVishroom()
/*    */   {
/* 27 */     super(Material.plants);
/* 28 */     setCreativeTab(Thaumcraft.tabTC);
/* 29 */     setStepSound(Block.soundTypeGrass);
/* 30 */     setLightLevel(0.4F);
/*    */   }
/*    */   
/*    */   protected boolean canPlaceBlockOn(Block ground, IBlockState state)
/*    */   {
/* 35 */     return ground.isFullBlock();
/*    */   }
/*    */   
/*    */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
/*    */   {
/* 40 */     if ((!worldIn.isRemote) && ((entityIn instanceof EntityLivingBase)) && (worldIn.rand.nextInt(5) == 0)) {
/* 41 */       ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
/*    */   {
/* 48 */     return EnumPlantType.Cave;
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
/*    */   {
/* 55 */     if (rand.nextInt(3) == 0) {
/* 56 */       float xr = pos.getX() + 0.5F + (rand.nextFloat() - rand.nextFloat()) * 0.4F;
/* 57 */       float yr = pos.getY() + 0.3F;
/* 58 */       float zr = pos.getZ() + 0.5F + (rand.nextFloat() - rand.nextFloat()) * 0.4F;
/* 59 */       FXWisp ef = new FXWisp(world, xr, yr, zr, 0.1F, 0.5F, 0.3F, 0.8F);
/* 60 */       ef.tinkle = false;
/* 61 */       ef.shrink = true;
/* 62 */       ef.setGravity(0.015F);
/* 63 */       ParticleEngine.instance.addEffect(world, ef);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\plants\BlockPlantVishroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */