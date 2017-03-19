/*    */ package thaumcraft.common.lib.world.biomes;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.common.entities.monster.EntityInhabitedZombie;
/*    */ 
/*    */ public class BiomeGenEldritch extends BiomeGenBase
/*    */ {
/*    */   public BiomeGenEldritch(int p_i1990_1_)
/*    */   {
/* 19 */     super(p_i1990_1_);
/*    */     
/* 21 */     this.spawnableMonsterList.clear();
/* 22 */     this.spawnableCreatureList.clear();
/* 23 */     this.spawnableWaterCreatureList.clear();
/* 24 */     this.spawnableCaveCreatureList.clear();
/* 25 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityInhabitedZombie.class, 1, 1, 1));
/* 26 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(thaumcraft.common.entities.monster.EntityEldritchGuardian.class, 1, 1, 1));
/* 27 */     this.topBlock = Blocks.dirt.getDefaultState();
/* 28 */     this.fillerBlock = Blocks.dirt.getDefaultState();
/* 29 */     setBiomeName("Eldritch");
/* 30 */     setDisableRain();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int getSkyColorByTemp(float p_76731_1_)
/*    */   {
/* 39 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void decorate(World world, Random random, BlockPos pos) {}
/*    */   
/*    */ 
/*    */ 
/*    */   public BiomeGenBase createMutation()
/*    */   {
/* 50 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\biomes\BiomeGenEldritch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */