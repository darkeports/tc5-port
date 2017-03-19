/*    */ package thaumcraft.common.lib.network.fx;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.client.FMLClientHandler;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.client.fx.other.FXSonic;
/*    */ import thaumcraft.common.CommonProxy;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class PacketFXSonic implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<PacketFXSonic, IMessage>
/*    */ {
/*    */   private int source;
/*    */   
/*    */   public PacketFXSonic() {}
/*    */   
/*    */   public PacketFXSonic(int source)
/*    */   {
/* 22 */     this.source = source;
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buffer)
/*    */   {
/* 27 */     buffer.writeInt(this.source);
/*    */   }
/*    */   
/*    */   public void fromBytes(ByteBuf buffer)
/*    */   {
/* 32 */     this.source = buffer.readInt();
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IMessage onMessage(PacketFXSonic message, MessageContext ctx)
/*    */   {
/* 38 */     Entity p = Thaumcraft.proxy.getClientWorld().getEntityByID(message.source);
/* 39 */     if (p != null) {
/* 40 */       FXSonic fb = new FXSonic(Thaumcraft.proxy.getClientWorld(), p.posX, p.posY, p.posZ, p, 10);
/*    */       
/*    */ 
/* 43 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/*    */     }
/* 45 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\network\fx\PacketFXSonic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */