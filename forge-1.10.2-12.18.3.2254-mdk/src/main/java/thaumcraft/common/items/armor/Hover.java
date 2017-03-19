/*     */ package thaumcraft.common.items.armor;
/*     */ 
/*     */ import baubles.api.BaublesApi;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagShort;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.items.baubles.ItemGirdleHover;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.misc.PacketFlyToServer;
/*     */ 
/*     */ public class Hover
/*     */ {
/*     */   public static final int FUELMAX = 6000;
/*     */   public static final int EFFICIENCY = 20;
/*  29 */   static HashMap<Integer, Boolean> hovering = new HashMap();
/*     */   
/*  31 */   static HashMap<Integer, Long> timing = new HashMap();
/*     */   
/*     */   public static boolean toggleHover(EntityPlayer player, int playerId, ItemStack armor) {
/*  34 */     boolean hover = hovering.get(Integer.valueOf(playerId)) == null ? false : ((Boolean)hovering.get(Integer.valueOf(playerId))).booleanValue();
/*  35 */     short fuel = 0;
/*     */     
/*  37 */     if ((armor.hasTagCompound()) && (armor.getTagCompound().hasKey("fuel"))) {
/*  38 */       fuel = armor.getTagCompound().getShort("fuel");
/*     */     }
/*     */     
/*  41 */     if ((!hover) && (fuel <= 0)) { return false;
/*     */     }
/*  43 */     hovering.put(Integer.valueOf(playerId), Boolean.valueOf(!hover));
/*  44 */     if (player.worldObj.isRemote) {
/*  45 */       PacketHandler.INSTANCE.sendToServer(new PacketFlyToServer(player, !hover));
/*  46 */       player.worldObj.playSound(player.posX, player.posY, player.posZ, !hover ? "thaumcraft:hhon" : "thaumcraft:hhoff", 0.33F, 1.0F, false);
/*     */     }
/*     */     
/*  49 */     return !hover;
/*     */   }
/*     */   
/*     */   public static void setHover(int playerId, boolean hover) {
/*  53 */     hovering.put(Integer.valueOf(playerId), Boolean.valueOf(hover));
/*     */   }
/*     */   
/*     */   public static boolean getHover(int playerId) {
/*  57 */     return hovering.containsKey(Integer.valueOf(playerId)) ? ((Boolean)hovering.get(Integer.valueOf(playerId))).booleanValue() : false;
/*     */   }
/*     */   
/*     */   public static void handleHoverArmor(EntityPlayer player, ItemStack armor)
/*     */   {
/*  62 */     if ((hovering.get(Integer.valueOf(player.getEntityId())) == null) && (armor.hasTagCompound()) && (armor.getTagCompound().hasKey("hover")))
/*     */     {
/*  64 */       hovering.put(Integer.valueOf(player.getEntityId()), Boolean.valueOf(armor.getTagCompound().getByte("hover") == 1));
/*  65 */       if (player.worldObj.isRemote) {
/*  66 */         PacketHandler.INSTANCE.sendToServer(new PacketFlyToServer(player, armor.getTagCompound().getByte("hover") == 1));
/*     */       }
/*     */     }
/*     */     
/*  70 */     boolean hover = hovering.get(Integer.valueOf(player.getEntityId())) == null ? false : ((Boolean)hovering.get(Integer.valueOf(player.getEntityId()))).booleanValue();
/*  71 */     World world = player.worldObj;
/*     */     
/*  73 */     player.capabilities.isFlying = hover;
/*     */     
/*  75 */     short fuel = 0;
/*  76 */     if ((armor.hasTagCompound()) && (armor.getTagCompound().hasKey("fuel"))) {
/*  77 */       fuel = armor.getTagCompound().getShort("fuel");
/*     */     }
/*     */     
/*  80 */     if ((world.isRemote) && ((player instanceof EntityPlayerSP)))
/*     */     {
/*  82 */       if ((hover) && (expendCharge(player, armor, fuel)))
/*     */       {
/*  84 */         long currenttime = System.currentTimeMillis();
/*  85 */         long time = 0L;
/*  86 */         if (timing.get(Integer.valueOf(player.getEntityId())) != null) { time = ((Long)timing.get(Integer.valueOf(player.getEntityId()))).longValue();
/*     */         }
/*  88 */         if (time < currenttime) {
/*  89 */           time = currenttime + (player.isSprinting() ? 'Ϩ' : 'Ұ');
/*     */           
/*  91 */           timing.put(Integer.valueOf(player.getEntityId()), Long.valueOf(time));
/*     */           
/*  93 */           player.worldObj.playSound(player.posX, player.posY, player.posZ, "thaumcraft:jacobs", player.isSprinting() ? 0.1F : 0.05F, 1.0F + player.worldObj.rand.nextFloat() * 0.05F + (player.isSprinting() ? 0.1F : 0.0F), false);
/*     */         }
/*     */         
/*     */ 
/*  97 */         int haste = EnchantmentHelper.getEnchantmentLevel(Config.enchHaste.effectId, armor);
/*  98 */         float mod = 0.825F + 0.075F * haste;
/*     */         
/* 100 */         if ((BaublesApi.getBaubles(player).getStackInSlot(3) != null) && ((BaublesApi.getBaubles(player).getStackInSlot(3).getItem() instanceof ItemGirdleHover)))
/*     */         {
/* 102 */           mod += 0.175F;
/*     */         }
/*     */         
/* 105 */         if (mod > 1.0F) { mod = 1.0F;
/*     */         }
/* 107 */         player.motionX *= mod;
/* 108 */         player.motionZ *= mod;
/*     */ 
/*     */       }
/* 111 */       else if (hover) { toggleHover(player, player.getEntityId(), armor);
/*     */       }
/*     */     }
/*     */     else {
/* 115 */       if ((armor.hasTagCompound()) && (!armor.getTagCompound().hasKey("hover"))) {
/* 116 */         armor.setTagInfo("hover", new NBTTagByte((byte)(hover ? 1 : 0)));
/*     */       }
/* 118 */       if ((hover) && (expendCharge(player, armor, fuel))) {
/* 119 */         if ((player instanceof EntityPlayerMP)) {
/* 120 */           resetFloatCounter((EntityPlayerMP)player);
/*     */         }
/* 122 */         player.fallDistance = 0.0F;
/* 123 */         if ((armor.hasTagCompound()) && (armor.getTagCompound().hasKey("hover")) && (armor.getTagCompound().getByte("hover") != 1))
/*     */         {
/* 125 */           armor.setTagInfo("hover", new NBTTagByte((byte)(hover ? 1 : 0)));
/*     */         }
/*     */       } else {
/* 128 */         if (hover) toggleHover(player, player.getEntityId(), armor);
/* 129 */         player.fallDistance *= 0.75F;
/* 130 */         if ((armor.hasTagCompound()) && (armor.getTagCompound().hasKey("hover")) && (armor.getTagCompound().getByte("hover") == 1))
/*     */         {
/* 132 */           armor.setTagInfo("hover", new NBTTagByte((byte)(hover ? 1 : 0)));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean expendCharge(EntityPlayer player, ItemStack armor, short fuel)
/*     */   {
/* 140 */     if (fuel > 0) {
/* 141 */       fuel = (short)(fuel - 1);
/* 142 */       if (player.isSprinting()) {
/* 143 */         fuel = (short)(fuel - 2);
/*     */       }
/* 145 */       if (!player.worldObj.isRemote) {
/* 146 */         armor.setTagInfo("fuel", new NBTTagShort(fuel));
/*     */       }
/* 148 */       return true;
/*     */     }
/*     */     
/* 151 */     return false;
/*     */   }
/*     */   
/*     */   public static void resetFloatCounter(EntityPlayerMP player) {
/*     */     try {
/* 156 */       ObfuscationReflectionHelper.setPrivateValue(NetHandlerPlayServer.class, player.playerNetServerHandler, Integer.valueOf(0), new String[] { "floatingTickCount", "g", "field_147365_f" });
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\armor\Hover.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */