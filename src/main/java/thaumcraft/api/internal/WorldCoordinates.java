/*    */ package thaumcraft.api.internal;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ 
/*    */ public class WorldCoordinates implements Comparable
/*    */ {
/*    */   public BlockPos pos;
/*    */   public int dim;
/*    */   
/*    */   public WorldCoordinates() {}
/*    */   
/*    */   public WorldCoordinates(BlockPos pos, int d)
/*    */   {
/* 17 */     this.pos = pos;
/* 18 */     this.dim = d;
/*    */   }
/*    */   
/*    */   public WorldCoordinates(TileEntity tile)
/*    */   {
/* 23 */     this.pos = tile.getPos();
/* 24 */     this.dim = tile.getWorld().provider.getDimension();
/*    */   }
/*    */   
/*    */   public WorldCoordinates(WorldCoordinates par1ChunkCoordinates)
/*    */   {
/* 29 */     this.pos = par1ChunkCoordinates.pos;
/* 30 */     this.dim = par1ChunkCoordinates.dim;
/*    */   }
/*    */   
/*    */   public boolean equals(Object par1Obj)
/*    */   {
/* 35 */     if (!(par1Obj instanceof WorldCoordinates))
/*    */     {
/* 37 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 41 */     WorldCoordinates coordinates = (WorldCoordinates)par1Obj;
/* 42 */     return (this.pos.equals(coordinates.pos)) && (this.dim == coordinates.dim);
/*    */   }
/*    */   
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 48 */     return this.pos.getX() + this.pos.getY() << 8 + this.pos.getZ() << 16 + this.dim << 24;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int compareWorldCoordinate(WorldCoordinates par1)
/*    */   {
/* 56 */     return this.dim == par1.dim ? this.pos.compareTo(par1.pos) : -1;
/*    */   }
/*    */   
/*    */   public void set(BlockPos pos, int d)
/*    */   {
/* 61 */     this.pos = pos;
/* 62 */     this.dim = d;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public double getDistanceSquared(BlockPos pos)
/*    */   {
/* 70 */     return this.pos.distanceSq(pos);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public double getDistanceSquaredToWorldCoordinates(WorldCoordinates par1ChunkCoordinates)
/*    */   {
/* 78 */     return getDistanceSquared(par1ChunkCoordinates.pos);
/*    */   }
/*    */   
/*    */   public int compareTo(Object par1Obj)
/*    */   {
/* 83 */     return compareWorldCoordinate((WorldCoordinates)par1Obj);
/*    */   }
/*    */   
/*    */   public void readNBT(NBTTagCompound nbt) {
/* 87 */     int x = nbt.getInteger("w_x");
/* 88 */     int y = nbt.getInteger("w_y");
/* 89 */     int z = nbt.getInteger("w_z");
/* 90 */     this.pos = new BlockPos(x, y, z);
/* 91 */     this.dim = nbt.getInteger("w_d");
/*    */   }
/*    */   
/*    */   public void writeNBT(NBTTagCompound nbt) {
/* 95 */     nbt.setInteger("w_x", this.pos.getX());
/* 96 */     nbt.setInteger("w_y", this.pos.getY());
/* 97 */     nbt.setInteger("w_z", this.pos.getZ());
/* 98 */     nbt.setInteger("w_d", this.dim);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\internal\WorldCoordinates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */