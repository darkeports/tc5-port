/*    */ package thaumcraft.common.lib.network.misc;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraftforge.common.DimensionManager;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import thaumcraft.api.wands.IWand;
/*    */ import thaumcraft.common.items.tools.ItemElementalShovel;
/*    */ import thaumcraft.common.items.tools.ItemThaumometer;
/*    */ 
/*    */ public class PacketItemKeyToServer implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<PacketItemKeyToServer, IMessage>
/*    */ {
/*    */   private int dim;
/*    */   private int playerid;
/*    */   private byte key;
/*    */   
/*    */   public PacketItemKeyToServer() {}
/*    */   
/*    */   public PacketItemKeyToServer(EntityPlayer player, int key)
/*    */   {
/* 25 */     this.dim = player.worldObj.provider.getDimensionId();
/* 26 */     this.playerid = player.getEntityId();
/* 27 */     this.key = ((byte)key);
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buffer)
/*    */   {
/* 32 */     buffer.writeInt(this.dim);
/* 33 */     buffer.writeInt(this.playerid);
/* 34 */     buffer.writeByte(this.key);
/*    */   }
/*    */   
/*    */   public void fromBytes(ByteBuf buffer)
/*    */   {
/* 39 */     this.dim = buffer.readInt();
/* 40 */     this.playerid = buffer.readInt();
/* 41 */     this.key = buffer.readByte();
/*    */   }
/*    */   
/*    */   public IMessage onMessage(PacketItemKeyToServer message, MessageContext ctx)
/*    */   {
/* 46 */     World world = DimensionManager.getWorld(message.dim);
/* 47 */     if (world == null) { return null;
/*    */     }
/* 49 */     net.minecraft.entity.Entity player = world.getEntityByID(message.playerid);
/*    */     
/* 51 */     if ((player != null) && ((player instanceof EntityPlayer)) && (((EntityPlayer)player).getHeldItem() != null))
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 58 */       if ((message.key == 1) && ((((EntityPlayer)player).getHeldItem().getItem() instanceof IWand))) {
/* 59 */         thaumcraft.common.items.wands.WandManager.toggleMisc(((EntityPlayer)player).getHeldItem(), world, (EntityPlayer)player);
/*    */       }
/*    */       
/* 62 */       if ((message.key == 1) && ((((EntityPlayer)player).getHeldItem().getItem() instanceof ItemElementalShovel))) {
/* 63 */         ((ItemElementalShovel)((EntityPlayer)player).getHeldItem().getItem());byte b = ItemElementalShovel.getOrientation(((EntityPlayer)player).getHeldItem());
/* 64 */         ((ItemElementalShovel)((EntityPlayer)player).getHeldItem().getItem());ItemElementalShovel.setOrientation(((EntityPlayer)player).getHeldItem(), (byte)(b + 1));
/*    */       }
/*    */       
/* 67 */       if ((message.key == 2) && ((((EntityPlayer)player).getHeldItem().getItem() instanceof ItemThaumometer))) {
/* 68 */         ItemThaumometer.changeVis(((EntityPlayer)player).getHeldItem(), world, (EntityPlayer)player);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 73 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\network\misc\PacketItemKeyToServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */