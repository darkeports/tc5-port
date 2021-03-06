/*    */ package thaumcraft.common.lib.world;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import net.minecraft.world.ChunkCoordIntPair;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkLoc
/*    */   implements Serializable
/*    */ {
/*    */   public final int chunkXPos;
/*    */   public final int chunkZPos;
/*    */   
/*    */   public ChunkLoc(int par1, int par2)
/*    */   {
/* 17 */     this.chunkXPos = par1;
/* 18 */     this.chunkZPos = par2;
/*    */   }
/*    */   
/*    */   public boolean equals(ChunkLoc par1Obj)
/*    */   {
/* 23 */     return (par1Obj.chunkXPos == this.chunkXPos) && (par1Obj.chunkZPos == this.chunkZPos);
/*    */   }
/*    */   
/*    */   public boolean equals(ChunkCoordIntPair par1Obj)
/*    */   {
/* 28 */     ChunkCoordIntPair var2 = par1Obj;
/* 29 */     return (var2.chunkXPos == this.chunkXPos) && (var2.chunkZPos == this.chunkZPos);
/*    */   }
/*    */   
/*    */   public int getCenterXPos()
/*    */   {
/* 34 */     return (this.chunkXPos << 4) + 8;
/*    */   }
/*    */   
/*    */   public int getCenterZPosition()
/*    */   {
/* 39 */     return (this.chunkZPos << 4) + 8;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 44 */     return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\ChunkLoc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */