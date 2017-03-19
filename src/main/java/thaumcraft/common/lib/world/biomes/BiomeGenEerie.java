/*    */ package thaumcraft.common.lib.world.biomes;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ import net.minecraft.entity.monster.EntityWitch;
/*    */ import net.minecraft.entity.passive.EntityBat;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.common.config.Config;
/*    */ import thaumcraft.common.entities.monster.EntityBrainyZombie;
/*    */ import thaumcraft.common.entities.monster.EntityGiantBrainyZombie;
/*    */ 
/*    */ public class BiomeGenEerie extends BiomeGenBase
/*    */ {
/*    */   public BiomeGenEerie(int par1)
/*    */   {
/* 20 */     super(par1);
/* 21 */     this.spawnableCreatureList.clear();
/* 22 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityBat.class, 3, 1, 1));
/*    */     
/* 24 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 8, 1, 1));
/* 25 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 4, 1, 1));
/* 26 */     if (Config.spawnAngryZombie) {
/* 27 */       this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityBrainyZombie.class, 32, 1, 1));
/* 28 */       this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityGiantBrainyZombie.class, 8, 1, 1));
/*    */     }
/* 30 */     if (Config.spawnWisp)
/* 31 */       this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(thaumcraft.common.entities.monster.EntityWisp.class, 3, 1, 1));
/* 32 */     if (Config.spawnElder) {
/* 33 */       this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(thaumcraft.common.entities.monster.EntityEldritchGuardian.class, 1, 1, 1));
/*    */     }
/* 35 */     this.theBiomeDecorator.treesPerChunk = 2;
/* 36 */     this.theBiomeDecorator.flowersPerChunk = 1;
/* 37 */     this.theBiomeDecorator.grassPerChunk = 2;
/* 38 */     setTemperatureRainfall(0.5F, 0.5F);
/* 39 */     setBiomeName("Eerie");
/* 40 */     setColor(4212800);
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int getGrassColorAtPos(BlockPos p_180627_1_)
/*    */   {
/* 47 */     return 4212800;
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int getFoliageColorAtPos(BlockPos p_180625_1_)
/*    */   {
/* 54 */     return 4212800;
/*    */   }
/*    */   
/*    */   public int getSkyColorByTemp(float par1)
/*    */   {
/* 59 */     return 2237081;
/*    */   }
/*    */   
/*    */   public int getWaterColorMultiplier()
/*    */   {
/* 64 */     return 3035999;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public BiomeGenBase createMutation()
/*    */   {
/* 71 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\biomes\BiomeGenEerie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */