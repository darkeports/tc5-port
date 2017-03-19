/*    */ package thaumcraft.common.tiles;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ 
/*    */ public class TileMemory
/*    */   extends TileEntity
/*    */ {
/* 13 */   public IBlockState oldblock = Blocks.air.getDefaultState();
/*    */   
/*    */   public NBTTagCompound tileEntityCompound;
/*    */   
/*    */ 
/*    */   public TileMemory() {}
/*    */   
/*    */ 
/*    */   public TileMemory(IBlockState bi)
/*    */   {
/* 23 */     this.oldblock = bi;
/*    */   }
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
/*    */   public void readFromNBT(NBTTagCompound nbttagcompound)
/*    */   {
/* 46 */     super.readFromNBT(nbttagcompound);
/*    */     
/* 48 */     Block b = Block.getBlockById(nbttagcompound.getInteger("oldblock"));
/* 49 */     int meta = nbttagcompound.getInteger("oldmeta");
/* 50 */     this.oldblock = b.getStateFromMeta(meta);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*    */   {
/* 60 */     super.writeToNBT(nbttagcompound);
/* 61 */     nbttagcompound.setInteger("oldblock", Block.getIdFromBlock(this.oldblock.getBlock()));
/* 62 */     nbttagcompound.setInteger("oldmeta", this.oldblock.getBlock().getMetaFromState(this.oldblock));
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\TileMemory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */