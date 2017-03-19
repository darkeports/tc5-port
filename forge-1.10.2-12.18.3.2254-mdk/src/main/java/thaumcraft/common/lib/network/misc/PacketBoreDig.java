/*    */ package thaumcraft.common.lib.network.misc;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.tiles.devices.TileArcaneBore;
/*    */ 
/*    */ public class PacketBoreDig implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<PacketBoreDig, IMessage>
/*    */ {
/*    */   private long loc;
/*    */   private long digloc;
/*    */   
/*    */   public PacketBoreDig() {}
/*    */   
/*    */   public PacketBoreDig(BlockPos pos, BlockPos digloc)
/*    */   {
/* 19 */     this.loc = pos.toLong();
/* 20 */     this.digloc = digloc.toLong();
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buffer)
/*    */   {
/* 25 */     buffer.writeLong(this.loc);
/* 26 */     buffer.writeLong(this.digloc);
/*    */   }
/*    */   
/*    */   public void fromBytes(ByteBuf buffer)
/*    */   {
/* 31 */     this.loc = buffer.readLong();
/* 32 */     this.digloc = buffer.readLong();
/*    */   }
/*    */   
/*    */   public IMessage onMessage(PacketBoreDig message, MessageContext ctx)
/*    */   {
/* 37 */     net.minecraft.tileentity.TileEntity tile = Thaumcraft.proxy.getClientWorld().getTileEntity(BlockPos.fromLong(message.loc));
/* 38 */     if ((tile != null) && ((tile instanceof TileArcaneBore))) {
/* 39 */       ((TileArcaneBore)tile).getDigEvent(BlockPos.fromLong(message.digloc));
/*    */     }
/* 41 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\network\misc\PacketBoreDig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */