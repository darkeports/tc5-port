/*     */ package thaumcraft.common.lib.world.dim;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.lib.world.biomes.BiomeHandler;
/*     */ 
/*     */ public class WorldProviderOuter extends WorldProvider
/*     */ {
/*     */   public String getDimensionName()
/*     */   {
/*  20 */     return "The Outer Lands";
/*     */   }
/*     */   
/*     */ 
/*     */   public String getWelcomeMessage()
/*     */   {
/*  26 */     return "Entering The Outer Lands";
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDepartMessage()
/*     */   {
/*  32 */     return "Leaving The Outer Lands";
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean shouldMapSpin(String entity, double x, double y, double z)
/*     */   {
/*  38 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBlockFreeze(BlockPos pos, boolean byWater)
/*     */   {
/*  44 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canSnowAt(BlockPos pos, boolean checkLight)
/*     */   {
/*  50 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canDoLightning(Chunk chunk)
/*     */   {
/*  56 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canDoRainSnowIce(Chunk chunk)
/*     */   {
/*  62 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void registerWorldChunkManager()
/*     */   {
/*  69 */     this.worldChunkMgr = new net.minecraft.world.biome.WorldChunkManagerHell(BiomeHandler.biomeEldritchLands, 0.0F);
/*  70 */     this.dimensionId = Config.dimensionOuterId;
/*  71 */     this.hasNoSky = true;
/*     */   }
/*     */   
/*     */ 
/*     */   public IChunkProvider createChunkGenerator()
/*     */   {
/*  77 */     return new ChunkProviderOuter(this.worldObj, this.worldObj.getSeed(), true);
/*     */   }
/*     */   
/*     */ 
/*     */   public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
/*     */   {
/*  83 */     return 0.0F;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
/*     */   {
/*  90 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public Vec3 getFogColor(float p_76562_1_, float p_76562_2_)
/*     */   {
/*  97 */     int i = 10518688;
/*  98 */     float f2 = MathHelper.cos(p_76562_1_ * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/*     */     
/* 100 */     if (f2 < 0.0F)
/*     */     {
/* 102 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 105 */     if (f2 > 1.0F)
/*     */     {
/* 107 */       f2 = 1.0F;
/*     */     }
/*     */     
/* 110 */     float f3 = (i >> 16 & 0xFF) / 255.0F;
/* 111 */     float f4 = (i >> 8 & 0xFF) / 255.0F;
/* 112 */     float f5 = (i & 0xFF) / 255.0F;
/* 113 */     f3 *= (f2 * 0.0F + 0.15F);
/* 114 */     f4 *= (f2 * 0.0F + 0.15F);
/* 115 */     f5 *= (f2 * 0.0F + 0.15F);
/* 116 */     return new Vec3(f3, f4, f5);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean isSkyColored()
/*     */   {
/* 123 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canRespawnHere()
/*     */   {
/* 129 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isSurfaceWorld()
/*     */   {
/* 135 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getCloudHeight()
/*     */   {
/* 142 */     return 1.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canCoordinateBeSpawn(int x, int z)
/*     */   {
/* 149 */     return this.worldObj.getGroundAboveSeaLevel(new BlockPos(x, 0, z)).getMaterial().blocksMovement();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getAverageGroundLevel()
/*     */   {
/* 156 */     return 50;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean doesXZShowFog(int p_76568_1_, int p_76568_2_)
/*     */   {
/* 163 */     return true;
/*     */   }
/*     */   
/*     */   public String getInternalNameSuffix()
/*     */   {
/* 168 */     return "OL";
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\dim\WorldProviderOuter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */