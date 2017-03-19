/*    */ package thaumcraft.common.lib.network.playerdata;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.entity.player.PlayerCapabilities;
/*    */ import net.minecraft.network.NetHandlerPlayServer;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ import thaumcraft.api.research.ResearchCategories;
/*    */ import thaumcraft.api.research.ResearchItem;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.lib.network.PacketHandler;
/*    */ import thaumcraft.common.lib.research.ResearchManager;
/*    */ 
/*    */ public class PacketPlayerCompleteToServer implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<PacketPlayerCompleteToServer, IMessage>
/*    */ {
/*    */   private String key;
/*    */   private int dim;
/*    */   private String username;
/*    */   private byte type;
/*    */   
/*    */   public PacketPlayerCompleteToServer() {}
/*    */   
/*    */   public PacketPlayerCompleteToServer(String key, String username, int dim, byte type)
/*    */   {
/* 29 */     this.key = key;
/* 30 */     this.dim = dim;
/* 31 */     this.username = username;
/* 32 */     this.type = type;
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buffer)
/*    */   {
/* 37 */     ByteBufUtils.writeUTF8String(buffer, this.key);
/* 38 */     buffer.writeInt(this.dim);
/* 39 */     ByteBufUtils.writeUTF8String(buffer, this.username);
/* 40 */     buffer.writeByte(this.type);
/*    */   }
/*    */   
/*    */   public void fromBytes(ByteBuf buffer)
/*    */   {
/* 45 */     this.key = ByteBufUtils.readUTF8String(buffer);
/* 46 */     this.dim = buffer.readInt();
/* 47 */     this.username = ByteBufUtils.readUTF8String(buffer);
/* 48 */     this.type = buffer.readByte();
/*    */   }
/*    */   
/*    */ 
/*    */   public IMessage onMessage(PacketPlayerCompleteToServer message, MessageContext ctx)
/*    */   {
/* 54 */     World world = net.minecraftforge.common.DimensionManager.getWorld(message.dim);
/*    */     
/* 56 */     if ((world == null) || ((ctx.getServerHandler().playerEntity != null) && (!ctx.getServerHandler().playerEntity.getName().equals(message.username)))) {
/* 57 */       return null;
/*    */     }
/* 59 */     EntityPlayer player = world.getPlayerEntityByName(message.username);
/*    */     
/*    */ 
/* 62 */     if ((player != null) && (!ResearchManager.isResearchComplete(message.username, message.key))) {
/* 63 */       if (ResearchManager.doesPlayerHaveRequisites(message.username, message.key)) {
/* 64 */         if (message.type == 0)
/*    */         {
/* 66 */           int xp = ResearchCategories.getResearch(message.key).getExperience();
/* 67 */           if ((player.capabilities.isCreativeMode) || (player.experienceLevel >= xp)) {
/* 68 */             if (!player.capabilities.isCreativeMode) player.removeExperienceLevel(xp);
/* 69 */             PacketHandler.INSTANCE.sendTo(new PacketResearchComplete(message.key, (byte)0), (EntityPlayerMP)player);
/* 70 */             Thaumcraft.proxy.getResearchManager().completeResearch(player, message.key, (byte)0);
/* 71 */             if (ResearchCategories.getResearch(message.key).siblings != null) {
/* 72 */               for (String sibling : ResearchCategories.getResearch(message.key).siblings) {
/* 73 */                 if ((!ResearchManager.isResearchComplete(message.username, sibling)) && (ResearchManager.doesPlayerHaveRequisites(message.username, sibling)))
/*    */                 {
/* 75 */                   PacketHandler.INSTANCE.sendTo(new PacketResearchComplete(sibling, (byte)0), (EntityPlayerMP)player);
/* 76 */                   Thaumcraft.proxy.getResearchManager().completeResearch(player, sibling, (byte)0);
/*    */                 }
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/* 82 */         else if (message.type == 1) {
/* 83 */           ResearchManager.createResearchNoteForPlayer(world, player, message.key);
/*    */         }
/* 85 */         world.playSoundAtEntity(player, "thaumcraft:learn", 0.75F, 1.0F);
/*    */       } else {
/* 87 */         player.addChatMessage(new net.minecraft.util.ChatComponentTranslation(net.minecraft.util.StatCollector.translateToLocal("tc.researcherror"), new Object[0]));
/*    */       }
/*    */     }
/* 90 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\network\playerdata\PacketPlayerCompleteToServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */