/*    */ package thaumcraft.common.tiles.essentia;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ITickable;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.blocks.TileThaumcraft;
/*    */ 
/*    */ public class TileJar
/*    */   extends TileThaumcraft
/*    */   implements ITickable
/*    */ {
/* 15 */   protected static Random rand = new Random();
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public AxisAlignedBB getRenderBoundingBox()
/*    */   {
/* 20 */     return AxisAlignedBB.fromBounds(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 1, getPos().getZ() + 1);
/*    */   }
/*    */   
/*    */   public void update() {}
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\essentia\TileJar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */