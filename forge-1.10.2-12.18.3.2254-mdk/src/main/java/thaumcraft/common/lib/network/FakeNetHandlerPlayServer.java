/*    */ package thaumcraft.common.lib.network;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.network.NetHandlerPlayServer;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*    */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ import net.minecraft.network.play.client.C0CPacketInput;
/*    */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*    */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*    */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*    */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*    */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*    */ import net.minecraft.network.play.client.C12PacketUpdateSign;
/*    */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*    */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*    */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*    */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*    */ import net.minecraft.server.MinecraftServer;
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
/*    */ public class FakeNetHandlerPlayServer
/*    */   extends NetHandlerPlayServer
/*    */ {
/*    */   public FakeNetHandlerPlayServer(MinecraftServer server, NetworkManager networkManagerIn, EntityPlayerMP playerIn)
/*    */   {
/* 39 */     super(server, networkManagerIn, playerIn);
/*    */   }
/*    */   
/*    */   public void update() {}
/*    */   
/*    */   public void kickPlayerFromServer(String reason) {}
/*    */   
/*    */   public void processInput(C0CPacketInput packetIn) {}
/*    */   
/*    */   public void processPlayer(C03PacketPlayer packetIn) {}
/*    */   
/*    */   public void sendPacket(Packet packetIn) {}
/*    */   
/*    */   public void processUseEntity(C02PacketUseEntity packetIn) {}
/*    */   
/*    */   public void processClientStatus(C16PacketClientStatus packetIn) {}
/*    */   
/*    */   public void processCloseWindow(C0DPacketCloseWindow packetIn) {}
/*    */   
/*    */   public void processClickWindow(C0EPacketClickWindow packetIn) {}
/*    */   
/*    */   public void processEnchantItem(C11PacketEnchantItem packetIn) {}
/*    */   
/*    */   public void processCreativeInventoryAction(C10PacketCreativeInventoryAction packetIn) {}
/*    */   
/*    */   public void processConfirmTransaction(C0FPacketConfirmTransaction packetIn) {}
/*    */   
/*    */   public void processUpdateSign(C12PacketUpdateSign packetIn) {}
/*    */   
/*    */   public void processKeepAlive(C00PacketKeepAlive packetIn) {}
/*    */   
/*    */   public void processTabComplete(C14PacketTabComplete packetIn) {}
/*    */   
/*    */   public void processClientSettings(C15PacketClientSettings packetIn) {}
/*    */   
/*    */   public void processVanilla250Packet(C17PacketCustomPayload packetIn) {}
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\network\FakeNetHandlerPlayServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */