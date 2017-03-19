/*     */ package thaumcraft.common.lib.events;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import net.minecraftforge.fml.client.registry.ClientRegistry;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.common.entities.construct.golem.ItemGolemBell;
/*     */ import thaumcraft.common.items.tools.ItemThaumometer;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.misc.PacketFocusChangeToServer;
/*     */ import thaumcraft.common.lib.network.misc.PacketItemKeyToServer;
/*     */ 
/*     */ public class KeyHandler
/*     */ {
/*  26 */   public KeyBinding keyF = new KeyBinding("Change Wand Focus", 33, "key.categories.misc");
/*     */   
/*  28 */   public KeyBinding keyH = new KeyBinding("Activate Hover Harness", 35, "key.categories.misc");
/*     */   
/*  30 */   public KeyBinding keyG = new KeyBinding("Misc Wand Toggle", 34, "key.categories.misc");
/*     */   
/*  32 */   private boolean keyPressedF = false;
/*  33 */   private boolean keyPressedH = false;
/*  34 */   private boolean keyPressedG = false;
/*  35 */   public static boolean radialActive = false;
/*  36 */   public static boolean radialLock = false;
/*  37 */   public static long lastPressF = 0L;
/*  38 */   public static long lastPressH = 0L;
/*  39 */   public static long lastPressG = 0L;
/*     */   
/*     */   public KeyHandler() {
/*  42 */     ClientRegistry.registerKeyBinding(this.keyF);
/*  43 */     ClientRegistry.registerKeyBinding(this.keyH);
/*  44 */     ClientRegistry.registerKeyBinding(this.keyG);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void playerTick(TickEvent.PlayerTickEvent event) {
/*  50 */     if (event.side == Side.SERVER)
/*  51 */       return;
/*  52 */     if (event.phase == TickEvent.Phase.START)
/*     */     {
/*  54 */       if (this.keyF.isKeyDown()) {
/*  55 */         if (FMLClientHandler.instance().getClient().inGameHasFocus) {
/*  56 */           EntityPlayer player = event.player;
/*  57 */           if (player != null) {
/*  58 */             if (!this.keyPressedF) {
/*  59 */               lastPressF = System.currentTimeMillis();
/*  60 */               radialLock = false;
/*     */             }
/*  62 */             if ((!radialLock) && (player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof IWand)) && (!((IWand)player.getHeldItem().getItem()).isSceptre(player.getHeldItem())))
/*     */             {
/*  64 */               if (player.isSneaking()) {
/*  65 */                 PacketHandler.INSTANCE.sendToServer(new PacketFocusChangeToServer(player, "REMOVE"));
/*     */               } else
/*  67 */                 radialActive = true;
/*  68 */             } else if ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof ItemGolemBell)) && (!this.keyPressedF))
/*     */             {
/*  70 */               PacketHandler.INSTANCE.sendToServer(new PacketItemKeyToServer(player, 0));
/*  71 */             } else if ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof ItemThaumometer)) && (!this.keyPressedF))
/*     */             {
/*  73 */               PacketHandler.INSTANCE.sendToServer(new PacketItemKeyToServer(player, 2));
/*  74 */               player.worldObj.playSound(player.posX, player.posY, player.posZ, "thaumcraft:key", 0.5F, 1.0F, false);
/*     */             }
/*     */           }
/*  77 */           this.keyPressedF = true;
/*     */         }
/*     */       } else {
/*  80 */         radialActive = false;
/*  81 */         if (this.keyPressedF) {
/*  82 */           lastPressF = System.currentTimeMillis();
/*     */         }
/*  84 */         this.keyPressedF = false;
/*     */       }
/*     */       
/*  87 */       if (this.keyH.isKeyDown())
/*     */       {
/*  89 */         if (FMLClientHandler.instance().getClient().inGameHasFocus) {
/*  90 */           EntityPlayer player = event.player;
/*  91 */           if (player != null) {
/*  92 */             if (!this.keyPressedH) {
/*  93 */               lastPressH = System.currentTimeMillis();
/*     */             }
/*  95 */             if ((player.inventory.armorItemInSlot(2) != null) && ((player.inventory.armorItemInSlot(2).getItem() instanceof thaumcraft.common.items.armor.ItemThaumostaticHarness)) && (!this.keyPressedH))
/*     */             {
/*     */ 
/*     */ 
/*  99 */               thaumcraft.common.items.armor.Hover.toggleHover(player, player.getEntityId(), player.inventory.armorItemInSlot(2));
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 105 */           this.keyPressedH = true;
/*     */         }
/*     */       } else {
/* 108 */         if (this.keyPressedH) {
/* 109 */           lastPressH = System.currentTimeMillis();
/*     */         }
/* 111 */         this.keyPressedH = false;
/*     */       }
/*     */       
/*     */ 
/* 115 */       if (this.keyG.isKeyDown())
/*     */       {
/* 117 */         if (FMLClientHandler.instance().getClient().inGameHasFocus) {
/* 118 */           EntityPlayer player = event.player;
/* 119 */           if ((player != null) && 
/* 120 */             (!this.keyPressedG)) {
/* 121 */             lastPressG = System.currentTimeMillis();
/* 122 */             PacketHandler.INSTANCE.sendToServer(new PacketItemKeyToServer(player, 1));
/*     */           }
/*     */           
/*     */ 
/* 126 */           this.keyPressedG = true;
/*     */         }
/*     */       } else {
/* 129 */         if (this.keyPressedG) {
/* 130 */           lastPressG = System.currentTimeMillis();
/*     */         }
/* 132 */         this.keyPressedG = false;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\events\KeyHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */