/*    */ package thaumcraft.common.lib.network.fx;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraftforge.fml.client.FMLClientHandler;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class PacketFXZap implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<PacketFXZap, IMessage>
/*    */ {
/*    */   private int source;
/*    */   private int target;
/*    */   
/*    */   public PacketFXZap() {}
/*    */   
/*    */   public PacketFXZap(int source, int target)
/*    */   {
/* 22 */     this.source = source;
/* 23 */     this.target = target;
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buffer)
/*    */   {
/* 28 */     buffer.writeInt(this.source);
/* 29 */     buffer.writeInt(this.target);
/*    */   }
/*    */   
/*    */   public void fromBytes(ByteBuf buffer)
/*    */   {
/* 34 */     this.source = buffer.readInt();
/* 35 */     this.target = buffer.readInt();
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IMessage onMessage(PacketFXZap message, net.minecraftforge.fml.common.network.simpleimpl.MessageContext ctx)
/*    */   {
/* 41 */     Minecraft mc = FMLClientHandler.instance().getClient();
/* 42 */     WorldClient world = mc.theWorld;
/*    */     
/* 44 */     Entity var2 = getEntityByID(message.source, mc, world);
/* 45 */     Entity var3 = getEntityByID(message.target, mc, world);
/* 46 */     if ((var2 != null) && (var3 != null)) {
/* 47 */       thaumcraft.common.Thaumcraft.proxy.getFX().arcBolt(var2.posX, var2.getEntityBoundingBox().minY + var2.height / 2.0F, var2.posZ, var3.posX, var3.getEntityBoundingBox().minY + var3.height / 2.0F, var3.posZ, 0.5F, 1.0F, 1.0F);
/*    */     }
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
/* 60 */     return null;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   private Entity getEntityByID(int par1, Minecraft mc, WorldClient world)
/*    */   {
/* 66 */     return par1 == mc.thePlayer.getEntityId() ? mc.thePlayer : world.getEntityByID(par1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\network\fx\PacketFXZap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */