/*     */ package thaumcraft.common.entities;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.ThaumcraftMaterials;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.lib.utils.BlockUtils;
/*     */ 
/*     */ public class EntityTaintSourceCloud extends EntityTaintSource
/*     */ {
/*     */   private int rainSoundCounter;
/*     */   
/*     */   public EntityTaintSourceCloud(World par1World)
/*     */   {
/*  25 */     super(par1World);
/*     */   }
/*     */   
/*     */   public EntityTaintSourceCloud(World world, BlockPos pos, int lifespan) {
/*  29 */     super(world, pos, lifespan);
/*     */     
/*  31 */     setSize(64.0F, 64.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  37 */     super.entityInit();
/*  38 */     if (this.worldObj.isRemote) {
/*  39 */       thaumcraft.client.lib.RenderEventHandler.clouds.put(Integer.valueOf(getEntityId()), this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  46 */     super.onUpdate();
/*     */     
/*  48 */     if (this.worldObj.isRemote)
/*     */     {
/*  50 */       addFluxRainParticles();
/*     */       
/*  52 */       addCloudparticles();
/*     */ 
/*     */ 
/*     */     }
/*  56 */     else if (this.rand.nextInt(20) == 0) { createPools();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void addCloudparticles()
/*     */   {
/*  63 */     BlockPos blockpos = new BlockPos(this);
/*     */     
/*  65 */     byte b0 = 18;
/*     */     
/*  67 */     for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(1); a++) {
/*  68 */       BlockPos bp = blockpos.add(this.rand.nextInt(b0) - this.rand.nextInt(b0), 1, this.rand.nextInt(b0) - this.rand.nextInt(b0));
/*  69 */       Thaumcraft.proxy.getFX().drawTaintCloudParticles(bp.getX() + this.rand.nextFloat(), bp.getY() + this.rand.nextFloat(), bp.getZ() + this.rand.nextFloat());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void createPools()
/*     */   {
/*  79 */     BlockPos blockpos = new BlockPos(this);
/*     */     
/*  81 */     byte b0 = 16;
/*     */     
/*  83 */     BlockPos blockpos1 = this.worldObj.getPrecipitationHeight(blockpos.add(this.rand.nextInt(b0) - this.rand.nextInt(b0), 0, this.rand.nextInt(b0) - this.rand.nextInt(b0)));
/*     */     
/*     */ 
/*  86 */     if (BlockUtils.distance(new BlockPos(blockpos1.getX(), 0, blockpos1.getZ()), new BlockPos(blockpos.getX(), 0, blockpos.getZ())) < 256.0D)
/*     */     {
/*     */ 
/*     */ 
/*  90 */       BlockPos blockpos2 = blockpos1.down();
/*  91 */       Block blockBelow = this.worldObj.getBlockState(blockpos2).getBlock();
/*  92 */       Block block = this.worldObj.getBlockState(blockpos1).getBlock();
/*     */       
/*  94 */       if (blockpos1.getY() <= blockpos.getY())
/*     */       {
/*     */ 
/*  97 */         if ((blockBelow.getMaterial() != Material.lava) && (blockBelow.getMaterial() != Material.water) && (blockBelow.getMaterial() != ThaumcraftMaterials.MATERIAL_TAINT) && (block.getMaterial() != ThaumcraftMaterials.MATERIAL_TAINT) && (block.isReplaceable(this.worldObj, blockpos1)))
/*     */         {
/*     */ 
/*     */ 
/* 101 */           if (AuraHandler.drainAura(this.worldObj, blockpos1, thaumcraft.api.aspects.Aspect.FLUX, 1)) {
/* 102 */             this.worldObj.setBlockState(blockpos1, thaumcraft.api.blocks.BlocksTC.fluxGoo.getDefaultState());
/*     */           } else {
/* 104 */             this.lifespan -= 20;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   private void addFluxRainParticles()
/*     */   {
/* 116 */     BlockPos blockpos = new BlockPos(this);
/*     */     
/* 118 */     Minecraft mc = Minecraft.getMinecraft();
/*     */     
/* 120 */     byte b0 = 16;
/* 121 */     double d0 = 0.0D;
/* 122 */     double d1 = 0.0D;
/* 123 */     double d2 = 0.0D;
/* 124 */     int i = 0;
/* 125 */     int j = 5;
/*     */     
/* 127 */     if (mc.gameSettings.particleSetting == 1)
/*     */     {
/* 129 */       j >>= 1;
/*     */     }
/* 131 */     else if (mc.gameSettings.particleSetting == 2)
/*     */     {
/* 133 */       j = 0;
/*     */     }
/*     */     
/* 136 */     for (int k = 0; k < j; k++)
/*     */     {
/* 138 */       BlockPos blockpos1 = this.worldObj.getPrecipitationHeight(blockpos.add(this.rand.nextInt(b0) - this.rand.nextInt(b0), 0, this.rand.nextInt(b0) - this.rand.nextInt(b0)));
/*     */       
/*     */ 
/* 141 */       if (BlockUtils.distance(new BlockPos(blockpos1.getX(), 0, blockpos1.getZ()), new BlockPos(blockpos.getX(), 0, blockpos.getZ())) <= 256.0D)
/*     */       {
/*     */ 
/*     */ 
/* 145 */         BlockPos blockpos2 = blockpos1.down();
/* 146 */         Block block = this.worldObj.getBlockState(blockpos2).getBlock();
/*     */         
/* 148 */         if ((blockpos1.getY() <= blockpos.getY() + b0) && (blockpos1.getY() >= blockpos.getY() - b0))
/*     */         {
/* 150 */           float f1 = this.rand.nextFloat();
/* 151 */           float f2 = this.rand.nextFloat();
/*     */           
/* 153 */           if (block.getMaterial() == Material.lava)
/*     */           {
/* 155 */             this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockpos1.getX() + f1, blockpos1.getY() + 0.1F - block.getBlockBoundsMinY(), blockpos1.getZ() + f2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           }
/* 157 */           else if (block.getMaterial() != Material.air)
/*     */           {
/* 159 */             block.setBlockBoundsBasedOnState(this.worldObj, blockpos2);
/* 160 */             i++;
/*     */             
/* 162 */             if (this.rand.nextInt(i) == 0)
/*     */             {
/* 164 */               d0 = blockpos2.getX() + f1;
/* 165 */               d1 = blockpos2.getY() + 0.1F + block.getBlockBoundsMaxY() - 1.0D;
/* 166 */               d2 = blockpos2.getZ() + f2;
/*     */             }
/*     */             
/* 169 */             Thaumcraft.proxy.getFX().fluxRainSplashFX(blockpos1);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 175 */     if ((i > 0) && (this.rand.nextInt(3) < this.rainSoundCounter++))
/*     */     {
/* 177 */       this.rainSoundCounter = 0;
/*     */       
/* 179 */       if ((d1 > blockpos.getY() + 1) && (this.worldObj.getPrecipitationHeight(blockpos).getY() > net.minecraft.util.MathHelper.floor_float(blockpos.getY())))
/*     */       {
/* 181 */         this.worldObj.playSound(d0, d1, d2, "ambient.weather.rain", 0.1F, 0.5F, false);
/*     */       }
/*     */       else
/*     */       {
/* 185 */         this.worldObj.playSound(d0, d1, d2, "ambient.weather.rain", 0.2F, 1.0F, false);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\EntityTaintSourceCloud.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */