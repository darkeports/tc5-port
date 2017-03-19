/*    */ package thaumcraft.common.lib.network.fx;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.particle.EffectRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraftforge.fml.client.FMLClientHandler;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import thaumcraft.client.fx.other.FXShieldRunes;
/*    */ import thaumcraft.common.CommonProxy;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class PacketFXShield implements IMessage, net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<PacketFXShield, IMessage>
/*    */ {
/*    */   private int source;
/*    */   private int target;
/*    */   
/*    */   public PacketFXShield() {}
/*    */   
/*    */   public PacketFXShield(int source, int target)
/*    */   {
/* 23 */     this.source = source;
/* 24 */     this.target = target;
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buffer)
/*    */   {
/* 29 */     buffer.writeInt(this.source);
/* 30 */     buffer.writeInt(this.target);
/*    */   }
/*    */   
/*    */   public void fromBytes(ByteBuf buffer)
/*    */   {
/* 35 */     this.source = buffer.readInt();
/* 36 */     this.target = buffer.readInt();
/*    */   }
/*    */   
/*    */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*    */   public IMessage onMessage(PacketFXShield message, net.minecraftforge.fml.common.network.simpleimpl.MessageContext ctx)
/*    */   {
/* 42 */     Entity p = Thaumcraft.proxy.getClientWorld().getEntityByID(message.source);
/* 43 */     if (p == null) return null;
/* 44 */     float pitch = 0.0F;
/* 45 */     float yaw = 0.0F;
/* 46 */     if (message.target >= 0) {
/* 47 */       Entity t = Thaumcraft.proxy.getClientWorld().getEntityByID(message.target);
/* 48 */       if (t != null) {
/* 49 */         double d0 = p.posX - t.posX;
/* 50 */         double d1 = (p.getEntityBoundingBox().minY + p.getEntityBoundingBox().maxY) / 2.0D - (t.getEntityBoundingBox().minY + t.getEntityBoundingBox().maxY) / 2.0D;
/* 51 */         double d2 = p.posZ - t.posZ;
/* 52 */         double d3 = net.minecraft.util.MathHelper.sqrt_double(d0 * d0 + d2 * d2);
/* 53 */         float f = (float)(Math.atan2(d2, d0) * 180.0D / 3.141592653589793D) - 90.0F;
/* 54 */         float f1 = (float)-(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D);
/* 55 */         pitch = f1;
/* 56 */         yaw = f;
/*    */       } else {
/* 58 */         pitch = 90.0F;
/* 59 */         yaw = 0.0F;
/*    */       }
/* 61 */       FXShieldRunes fb = new FXShieldRunes(Thaumcraft.proxy.getClientWorld(), p.posX, p.posY, p.posZ, p, 8, yaw, pitch);
/* 62 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/*    */     }
/* 64 */     else if (message.target == -1) {
/* 65 */       FXShieldRunes fb = new FXShieldRunes(Thaumcraft.proxy.getClientWorld(), p.posX, p.posY, p.posZ, p, 8, 0.0F, 90.0F);
/* 66 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/* 67 */       fb = new FXShieldRunes(Thaumcraft.proxy.getClientWorld(), p.posX, p.posY, p.posZ, p, 8, 0.0F, 270.0F);
/* 68 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/*    */     }
/* 70 */     else if (message.target == -2) {
/* 71 */       FXShieldRunes fb = new FXShieldRunes(Thaumcraft.proxy.getClientWorld(), p.posX, p.posY, p.posZ, p, 8, 0.0F, 270.0F);
/* 72 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/*    */     }
/* 74 */     else if (message.target == -3) {
/* 75 */       FXShieldRunes fb = new FXShieldRunes(Thaumcraft.proxy.getClientWorld(), p.posX, p.posY, p.posZ, p, 8, 0.0F, 90.0F);
/* 76 */       FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
/*    */     }
/* 78 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\network\fx\PacketFXShield.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */