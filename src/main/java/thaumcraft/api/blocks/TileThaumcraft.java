/*    */ package thaumcraft.api.blocks;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
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
/*    */ public class TileThaumcraft
/*    */   extends TileEntity
/*    */ {
/*    */   public void readFromNBT(NBTTagCompound nbt)
/*    */   {
/* 29 */     super.readFromNBT(nbt);
/* 30 */     readCustomNBT(nbt);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void readCustomNBT(NBTTagCompound nbt) {}
/*    */   
/*    */ 
/*    */ 
/*    */   public NBTTagCompound writeToNBT(NBTTagCompound nbt)
/*    */   {
/* 41 */     super.writeToNBT(nbt);
/* 42 */     writeCustomNBT(nbt); return nbt;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void writeCustomNBT(NBTTagCompound nbt) {}
/*    */   
/*    */ 
/*    */ 
/*    */   public Packet getDescriptionPacket()
/*    */   {
/* 53 */     NBTTagCompound nbt = new NBTTagCompound();
/* 54 */     writeCustomNBT(nbt);
/* 55 */     return new SPacketUpdateTileEntity(getPos(), 64537, nbt);
/*    */   }
/*    */   
/*    */   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
/*    */   {
/* 60 */     super.onDataPacket(net, pkt);
/* 61 */     readCustomNBT(pkt.getNbtCompound());
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
/*    */   {
/* 67 */     return oldState.getBlock() != newState.getBlock();
/*    */   }
/*    */   
/*    */   public EnumFacing getFacing() {
/*    */     try {
/* 72 */       return EnumFacing.getFront(getBlockMetadata() & 0x7);
/*    */     } catch (Exception e) {}
/* 74 */     return EnumFacing.UP;
/*    */   }
/*    */   
/*    */   public boolean gettingPower() {
/* 78 */     return this.worldObj.isBlockPowered(getPos());
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\blocks\TileThaumcraft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */