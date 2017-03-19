/*    */ package thaumcraft.common.lib.network.playerdata;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ import thaumcraft.common.tiles.crafting.TileResearchTable;
/*    */ 
/*    */ public class PacketAspectPlaceToServer implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<PacketAspectPlaceToServer, IMessage>
/*    */ {
/*    */   private int dim;
/*    */   private int playerid;
/*    */   private int x;
/*    */   private int y;
/*    */   private int z;
/*    */   Aspect aspect;
/*    */   byte q;
/*    */   byte r;
/*    */   
/*    */   public PacketAspectPlaceToServer() {}
/*    */   
/*    */   public PacketAspectPlaceToServer(EntityPlayer player, byte q, byte r, BlockPos pos, Aspect aspect)
/*    */   {
/* 28 */     this.dim = player.worldObj.provider.getDimensionId();
/* 29 */     this.playerid = player.getEntityId();
/* 30 */     this.x = pos.getX();
/* 31 */     this.y = pos.getY();
/* 32 */     this.z = pos.getZ();
/* 33 */     this.aspect = aspect;
/* 34 */     this.q = q;
/* 35 */     this.r = r;
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buffer)
/*    */   {
/* 40 */     buffer.writeInt(this.dim);
/* 41 */     buffer.writeInt(this.playerid);
/* 42 */     buffer.writeInt(this.x);
/* 43 */     buffer.writeInt(this.y);
/* 44 */     buffer.writeInt(this.z);
/* 45 */     ByteBufUtils.writeUTF8String(buffer, this.aspect == null ? "null" : this.aspect.getTag());
/* 46 */     buffer.writeByte(this.q);
/* 47 */     buffer.writeByte(this.r);
/*    */   }
/*    */   
/*    */   public void fromBytes(ByteBuf buffer)
/*    */   {
/* 52 */     this.dim = buffer.readInt();
/* 53 */     this.playerid = buffer.readInt();
/* 54 */     this.x = buffer.readInt();
/* 55 */     this.y = buffer.readInt();
/* 56 */     this.z = buffer.readInt();
/* 57 */     this.aspect = Aspect.getAspect(ByteBufUtils.readUTF8String(buffer));
/* 58 */     this.q = buffer.readByte();
/* 59 */     this.r = buffer.readByte();
/*    */   }
/*    */   
/*    */   public IMessage onMessage(PacketAspectPlaceToServer message, MessageContext ctx)
/*    */   {
/* 64 */     World world = net.minecraftforge.common.DimensionManager.getWorld(message.dim);
/* 65 */     if ((world == null) || ((ctx.getServerHandler().playerEntity != null) && (ctx.getServerHandler().playerEntity.getEntityId() != message.playerid))) {
/* 66 */       return null;
/*    */     }
/* 68 */     net.minecraft.entity.Entity player = world.getEntityByID(message.playerid);
/*    */     
/* 70 */     if (player == null) { return null;
/*    */     }
/* 72 */     net.minecraft.tileentity.TileEntity rt = world.getTileEntity(new BlockPos(message.x, message.y, message.z));
/* 73 */     if ((rt != null) && ((rt instanceof TileResearchTable))) {
/* 74 */       ((TileResearchTable)rt).placeAspect(message.q, message.r, message.aspect, (EntityPlayer)player);
/*    */     }
/* 76 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\network\playerdata\PacketAspectPlaceToServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */