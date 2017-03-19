/*    */ package thaumcraft.common.blocks.basic;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.MaterialLiquid;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fluids.BlockFluidClassic;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.client.fx.ParticleEngine;
/*    */ import thaumcraft.client.fx.particles.FXBubble;
/*    */ import thaumcraft.common.CommonProxy;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.config.ConfigBlocks.FluidPure;
/*    */ import thaumcraft.common.lib.potions.PotionWarpWard;
/*    */ import thaumcraft.common.lib.research.PlayerKnowledge;
/*    */ 
/*    */ public class BlockFluidPure extends BlockFluidClassic
/*    */ {
/* 25 */   public static final net.minecraft.block.material.Material FLUID_PURE_MATERIAL = new MaterialLiquid(MapColor.silverColor);
/*    */   
/*    */   public BlockFluidPure() {
/* 28 */     super(ConfigBlocks.FluidPure.instance, FLUID_PURE_MATERIAL);
/* 29 */     setUnlocalizedName("purifying_fluid");
/* 30 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
/*    */   {
/* 37 */     if ((!world.isRemote) && (isSourceBlock(world, pos)) && ((entity instanceof EntityPlayer)) && (!((EntityPlayer)entity).isPotionActive(PotionWarpWard.instance)))
/*    */     {
/*    */ 
/* 40 */       int warp = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(((EntityPlayer)entity).getName());
/*    */       
/* 42 */       int div = 1;
/* 43 */       if (warp > 0) {
/* 44 */         div = (int)Math.sqrt(warp);
/* 45 */         if (div < 1)
/* 46 */           div = 1;
/*    */       }
/* 48 */       ((EntityPlayer)entity).addPotionEffect(new PotionEffect(PotionWarpWard.instance.getId(), Math.min(32000, 200000 / div), 0, true, true));
/*    */       
/* 50 */       world.setBlockToAir(pos);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
/*    */   {
/* 58 */     int meta = getMetaFromState(state);
/*    */     
/* 60 */     if (rand.nextInt(10) == 0) {
/* 61 */       FXBubble fb = new FXBubble(world, pos.getX() + rand.nextFloat(), pos.getY() + 0.125F * (8 - meta), pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D, 0);
/*    */       
/* 63 */       fb.setAlphaF(0.25F);
/* 64 */       fb.setRGB(1.0F, 1.0F, 1.0F);
/* 65 */       ParticleEngine.instance.addEffect(world, fb);
/*    */     }
/*    */     
/* 68 */     if (rand.nextInt(50) == 0) {
/* 69 */       double var21 = pos.getX() + rand.nextFloat();
/* 70 */       double var22 = pos.getY() + this.maxY;
/* 71 */       double var23 = pos.getZ() + rand.nextFloat();
/* 72 */       world.playSound(var21, var22, var23, "liquid.lavapop", 0.1F + rand.nextFloat() * 0.1F, 0.9F + rand.nextFloat() * 0.15F, false);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockFluidPure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */