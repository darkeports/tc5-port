/*    */ package thaumcraft.common.lib.world.biomes;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.common.config.Config;
/*    */ import thaumcraft.common.entities.monster.tainted.EntityTaintacle;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeGenTaint
/*    */   extends BiomeGenBase
/*    */ {
/*    */   public BiomeGenTaint(int par1)
/*    */   {
/* 21 */     super(par1);
/* 22 */     this.theBiomeDecorator.treesPerChunk = 2;
/* 23 */     this.theBiomeDecorator.flowersPerChunk = 64537;
/* 24 */     this.theBiomeDecorator.grassPerChunk = 2;
/* 25 */     this.theBiomeDecorator.reedsPerChunk = 64537;
/* 26 */     setBiomeName("Tainted Land");
/* 27 */     setColor(7160201);
/* 28 */     this.spawnableCreatureList.clear();
/* 29 */     this.spawnableMonsterList.clear();
/* 30 */     this.spawnableWaterCreatureList.clear();
/* 31 */     if (Config.spawnTaintacle) { this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityTaintacle.class, 1, 1, 1));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void decorate(World world, Random random, BlockPos pos) {}
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int getGrassColorAtPos(BlockPos p_180627_1_)
/*    */   {
/* 43 */     return 7160201;
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int getFoliageColorAtPos(BlockPos p_180625_1_)
/*    */   {
/* 50 */     return 8154503;
/*    */   }
/*    */   
/*    */   public int getSkyColorByTemp(float par1)
/*    */   {
/* 55 */     return 8144127;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getWaterColorMultiplier()
/*    */   {
/* 61 */     return 13373832;
/*    */   }
/*    */   
/*    */   public BiomeGenBase createMutation()
/*    */   {
/* 66 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\biomes\BiomeGenTaint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */