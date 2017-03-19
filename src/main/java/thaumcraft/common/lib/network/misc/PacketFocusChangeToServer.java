/*    */ package thaumcraft.common.lib.network.misc;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.NetHandlerPlayServer;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import thaumcraft.api.wands.IWand;
/*    */ 
/*    */ public class PacketFocusChangeToServer implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<PacketFocusChangeToServer, IMessage>
/*    */ {
/*    */   private int dim;
/*    */   private int playerid;
/*    */   private String focus;
/*    */   
/*    */   public PacketFocusChangeToServer() {}
/*    */   
/*    */   public PacketFocusChangeToServer(EntityPlayer player, String focus)
/*    */   {
/* 23 */     this.dim = player.worldObj.provider.getDimensionId();
/* 24 */     this.playerid = player.getEntityId();
/* 25 */     this.focus = focus;
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buffer)
/*    */   {
/* 30 */     buffer.writeInt(this.dim);
/* 31 */     buffer.writeInt(this.playerid);
/* 32 */     ByteBufUtils.writeUTF8String(buffer, this.focus);
/*    */   }
/*    */   
/*    */   public void fromBytes(ByteBuf buffer)
/*    */   {
/* 37 */     this.dim = buffer.readInt();
/* 38 */     this.playerid = buffer.readInt();
/* 39 */     this.focus = ByteBufUtils.readUTF8String(buffer);
/*    */   }
/*    */   
/*    */   public IMessage onMessage(PacketFocusChangeToServer message, MessageContext ctx)
/*    */   {
/* 44 */     World world = net.minecraftforge.common.DimensionManager.getWorld(message.dim);
/* 45 */     if ((world == null) || ((ctx.getServerHandler().playerEntity != null) && (ctx.getServerHandler().playerEntity.getEntityId() != message.playerid))) { return null;
/*    */     }
/* 47 */     net.minecraft.entity.Entity player = world.getEntityByID(message.playerid);
/*    */     
/* 49 */     if ((player != null) && ((player instanceof EntityPlayer)) && (((EntityPlayer)player).getHeldItem() != null))
/*    */     {
/* 51 */       if (((((EntityPlayer)player).getHeldItem().getItem() instanceof IWand)) && (!((IWand)((EntityPlayer)player).getHeldItem().getItem()).isSceptre(((EntityPlayer)player).getHeldItem())))
/*    */       {
/* 53 */         thaumcraft.common.items.wands.WandManager.changeFocus(((EntityPlayer)player).getHeldItem(), world, (EntityPlayer)player, message.focus); }
/*    */     }
/* 55 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\network\misc\PacketFocusChangeToServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */