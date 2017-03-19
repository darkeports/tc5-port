/*    */ package thaumcraft.common.lib.world;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
/*    */ import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
/*    */ import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
/*    */ import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
/*    */ import thaumcraft.common.lib.world.objects.ComponentWizardTower;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VillageWizardManager
/*    */   implements VillagerRegistry.IVillageCreationHandler
/*    */ {
/*    */   public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int i)
/*    */   {
/* 49 */     return new StructureVillagePieces.PieceWeight(ComponentWizardTower.class, 15, MathHelper.getRandomIntegerInRange(random, 0 + i, 1 + i));
/*    */   }
/*    */   
/*    */ 
/*    */   public Class<?> getComponentClass()
/*    */   {
/* 55 */     return ComponentWizardTower.class;
/*    */   }
/*    */   
/*    */ 
/*    */   public StructureVillagePieces.Village buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List pieces, Random random, int p1, int p2, int p3, EnumFacing p4, int p5)
/*    */   {
/* 61 */     return ComponentWizardTower.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\VillageWizardManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */