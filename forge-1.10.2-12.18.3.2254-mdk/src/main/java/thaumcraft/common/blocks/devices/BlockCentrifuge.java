/*    */ package thaumcraft.common.blocks.devices;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import thaumcraft.common.blocks.BlockTCDevice;
/*    */ import thaumcraft.common.tiles.essentia.TileCentrifuge;
/*    */ 
/*    */ public class BlockCentrifuge extends BlockTCDevice
/*    */ {
/*    */   public BlockCentrifuge()
/*    */   {
/* 11 */     super(net.minecraft.block.material.Material.wood, TileCentrifuge.class);
/* 12 */     setStepSound(Block.soundTypeWood);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isOpaqueCube()
/*    */   {
/* 18 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean isFullCube()
/*    */   {
/* 24 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getRenderType()
/*    */   {
/* 30 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockCentrifuge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */