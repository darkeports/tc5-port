/*     */ package thaumcraft.common.blocks.world.taint;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.BlockFluidFinite;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.potions.PotionVisExhaust;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.client.fx.ParticleEngine;
/*     */ import thaumcraft.client.fx.particles.FXBubble;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.ConfigBlocks.FluidFluxGoo;
/*     */ import thaumcraft.common.entities.monster.EntityThaumicSlime;
/*     */ 
/*     */ public class BlockFluxGoo extends BlockFluidFinite
/*     */ {
/*     */   public BlockFluxGoo()
/*     */   {
/*  30 */     super(ConfigBlocks.FluidFluxGoo.instance, thaumcraft.api.ThaumcraftMaterials.MATERIAL_TAINT);
/*  31 */     setUnlocalizedName("flux_goo");
/*  32 */     setCreativeTab(Thaumcraft.tabTC);
/*  33 */     setStepSound(new thaumcraft.common.lib.CustomSoundType("gore", 1.0F, 1.0F));
/*  34 */     setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, Integer.valueOf(7)));
/*     */   }
/*     */   
/*     */   static {
/*  38 */     defaultDisplacements.put(BlocksTC.taintFibre, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
/*     */   {
/*  43 */     int md = ((Integer)state.getValue(LEVEL)).intValue();
/*  44 */     if ((entity instanceof EntityThaumicSlime)) {
/*  45 */       EntityThaumicSlime slime = (EntityThaumicSlime)entity;
/*  46 */       if ((slime.getSlimeSize() < md) && (world.rand.nextBoolean())) {
/*  47 */         slime.setSlimeSize(slime.getSlimeSize() + 1);
/*  48 */         if (md > 1) {
/*  49 */           world.setBlockState(pos, state.withProperty(LEVEL, Integer.valueOf(md - 1)), 2);
/*     */         } else {
/*  51 */           world.setBlockToAir(pos);
/*     */         }
/*     */       }
/*     */     } else {
/*  55 */       entity.motionX *= (1.0F - getQuantaPercentage(world, pos));
/*  56 */       entity.motionZ *= (1.0F - getQuantaPercentage(world, pos));
/*  57 */       if ((entity instanceof EntityLivingBase)) {
/*  58 */         PotionEffect pe = new PotionEffect(PotionVisExhaust.instance.getId(), 600, md / 3, true, true);
/*  59 */         pe.getCurativeItems().clear();
/*  60 */         ((EntityLivingBase)entity).addPotionEffect(pe);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/*  68 */     int meta = ((Integer)state.getValue(LEVEL)).intValue();
/*     */     
/*  70 */     if ((meta >= 2) && (meta < 6) && (world.isAirBlock(pos.up())) && (rand.nextInt(50) == 0)) {
/*  71 */       world.setBlockToAir(pos);
/*  72 */       EntityThaumicSlime slime = new EntityThaumicSlime(world);
/*  73 */       slime.setLocationAndAngles(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, 0.0F, 0.0F);
/*  74 */       slime.setSlimeSize(1);
/*  75 */       world.spawnEntityInWorld(slime);
/*  76 */       world.playSoundAtEntity(slime, "thaumcraft:gore", 1.0F, 1.0F);
/*  77 */       return; }
/*  78 */     if ((meta >= 6) && (world.isAirBlock(pos.up())) && (rand.nextInt(50) == 0)) {
/*  79 */       world.setBlockToAir(pos);
/*  80 */       EntityThaumicSlime slime = new EntityThaumicSlime(world);
/*  81 */       slime.setLocationAndAngles(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, 0.0F, 0.0F);
/*  82 */       slime.setSlimeSize(2);
/*  83 */       world.spawnEntityInWorld(slime);
/*  84 */       world.playSoundAtEntity(slime, "thaumcraft:gore", 1.0F, 1.0F);
/*  85 */       return; }
/*  86 */     if (rand.nextInt(4) == 0) {
/*  87 */       if (meta == 0) {
/*  88 */         if (rand.nextBoolean()) {
/*  89 */           AuraHelper.pollute(world, pos, 1, true);
/*  90 */           world.setBlockToAir(pos);
/*     */         } else {
/*  92 */           world.setBlockState(pos, BlocksTC.taintFibre.getDefaultState());
/*     */         }
/*     */       } else {
/*  95 */         world.setBlockState(pos, state.withProperty(LEVEL, Integer.valueOf(meta - 1)), 2);
/*  96 */         AuraHelper.pollute(world, pos, 1, true);
/*     */       }
/*  98 */       return;
/*     */     }
/*     */     
/* 101 */     super.updateTick(world, pos, state, rand);
/*     */   }
/*     */   
/*     */   public boolean isReplaceable(World world, BlockPos pos)
/*     */   {
/* 106 */     return ((Integer)world.getBlockState(pos).getValue(LEVEL)).intValue() < 4;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
/*     */   {
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/* 118 */     int meta = getMetaFromState(state);
/* 119 */     if (rand.nextInt(66 - Thaumcraft.proxy.getFX().particleCount(10)) <= meta) {
/* 120 */       FXBubble fb = new FXBubble(world, pos.getX() + rand.nextFloat(), pos.getY() + 0.125F * meta, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D, 0);
/*     */       
/* 122 */       fb.setAlphaF(0.25F);
/* 123 */       ParticleEngine.instance.addEffect(world, fb);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\taint\BlockFluxGoo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */