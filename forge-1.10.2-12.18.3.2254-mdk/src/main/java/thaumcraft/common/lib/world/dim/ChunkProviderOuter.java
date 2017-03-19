/*     */ package thaumcraft.common.lib.world.dim;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.terraingen.PopulateChunkEvent.Post;
/*     */ import net.minecraftforge.event.terraingen.PopulateChunkEvent.Pre;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventBus;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ 
/*     */ public class ChunkProviderOuter implements IChunkProvider
/*     */ {
/*     */   private Random rand;
/*     */   private World worldObj;
/*     */   private WorldType worldType;
/*     */   private BiomeGenBase[] biomesForGeneration;
/*     */   
/*     */   public ChunkProviderOuter(World p_i2006_1_, long p_i2006_2_, boolean p_i2006_4_)
/*     */   {
/*  30 */     this.worldObj = p_i2006_1_;
/*  31 */     this.worldType = p_i2006_1_.getWorldInfo().getTerrainType();
/*  32 */     this.rand = new Random(p_i2006_2_);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Chunk provideChunk(BlockPos blockPosIn)
/*     */   {
/*  39 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Chunk provideChunk(int x, int z)
/*     */   {
/*  46 */     this.rand.setSeed(x * 341873128712L + z * 132897987541L);
/*     */     
/*     */ 
/*  49 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/*     */     
/*  51 */     for (int k = 0; k < 256; k++)
/*     */     {
/*  53 */       for (int l = 0; l < 16; l++)
/*     */       {
/*  55 */         for (int i1 = 0; i1 < 16; i1++)
/*     */         {
/*  57 */           chunkprimer.setBlockState(l, k, i1, BlocksTC.stone.getStateFromMeta(12));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  62 */     this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
/*     */     
/*  64 */     Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
/*  65 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/*  67 */     for (int k = 0; k < abyte.length; k++)
/*     */     {
/*  69 */       abyte[k] = ((byte)this.biomesForGeneration[k].biomeID);
/*     */     }
/*     */     
/*  72 */     chunk.generateSkylightMap();
/*  73 */     return chunk;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean chunkExists(int p_73149_1_, int p_73149_2_)
/*     */   {
/*  79 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
/*     */   {
/*  88 */     net.minecraft.block.BlockFalling.fallInstantly = true;
/*  89 */     MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(p_73153_1_, this.worldObj, this.worldObj.rand, p_73153_2_, p_73153_3_, false));
/*     */     
/*  91 */     int k = p_73153_2_ * 16;
/*  92 */     int l = p_73153_3_ * 16;
/*  93 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(new BlockPos(k + 16, 10, l + 16));
/*  94 */     biomegenbase.decorate(this.worldObj, this.worldObj.rand, new BlockPos(k, 0, l));
/*     */     
/*  96 */     MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(p_73153_1_, this.worldObj, this.worldObj.rand, p_73153_2_, p_73153_3_, false));
/*  97 */     net.minecraft.block.BlockFalling.fallInstantly = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
/*     */   {
/* 107 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void saveExtraData() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean unloadQueuedChunks()
/*     */   {
/* 123 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canSave()
/*     */   {
/* 132 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String makeString()
/*     */   {
/* 141 */     return "RandomLevelSource";
/*     */   }
/*     */   
/*     */ 
/*     */   public int getLoadedChunkCount()
/*     */   {
/* 147 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_)
/*     */   {
/* 152 */     return false;
/*     */   }
/*     */   
/*     */   public java.util.List getPossibleCreatures(EnumCreatureType p_177458_1_, BlockPos p_177458_2_)
/*     */   {
/* 157 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(p_177458_2_);
/* 158 */     return biomegenbase.getSpawnableList(p_177458_1_);
/*     */   }
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String p_180513_2_, BlockPos p_180513_3_)
/*     */   {
/* 163 */     return null;
/*     */   }
/*     */   
/*     */   public void recreateStructures(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\dim\ChunkProviderOuter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */