/*      */ package thaumcraft.client.lib;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.gui.inventory.GuiContainer;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.ItemRenderer;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.shader.ShaderGroup;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.monster.EntityMob;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityNote;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraftforge.client.event.DrawBlockHighlightEvent;
/*      */ import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
/*      */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*      */ import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
/*      */ import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
/*      */ import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre;
/*      */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*      */ import net.minecraftforge.client.event.TextureStitchEvent.Pre;
/*      */ import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
/*      */ import net.minecraftforge.fml.client.FMLClientHandler;
/*      */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*      */ import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
/*      */ import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
/*      */ import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
/*      */ import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
/*      */ import net.minecraftforge.fml.relauncher.Side;
/*      */ import net.minecraftforge.fml.relauncher.SideOnly;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import thaumcraft.api.aspects.Aspect;
/*      */ import thaumcraft.api.aspects.AspectList;
/*      */ import thaumcraft.api.aspects.IAspectContainer;
/*      */ import thaumcraft.api.golems.seals.ISealEntity;
/*      */ import thaumcraft.api.golems.seals.SealPos;
/*      */ import thaumcraft.api.items.IArchitect;
/*      */ import thaumcraft.api.items.IArchitectExtended;
/*      */ import thaumcraft.api.items.IRechargable;
/*      */ import thaumcraft.api.wands.IWand;
/*      */ import thaumcraft.api.wands.ItemFocusBasic;
/*      */ import thaumcraft.api.wands.WandCap;
/*      */ import thaumcraft.api.wands.WandRod;
/*      */ import thaumcraft.client.fx.ParticleEngine;
/*      */ import thaumcraft.client.fx.particles.FXSpark;
/*      */ import thaumcraft.client.gui.GuiResearchPopup;
/*      */ import thaumcraft.codechicken.lib.raytracer.RayTracer;
/*      */ import thaumcraft.common.CommonProxy;
/*      */ import thaumcraft.common.Thaumcraft;
/*      */ import thaumcraft.common.config.Config;
/*      */ import thaumcraft.common.entities.EntityTaintSource;
/*      */ import thaumcraft.common.entities.construct.golem.seals.SealHandler;
/*      */ import thaumcraft.common.entities.monster.mods.ChampionModifier;
/*      */ import thaumcraft.common.items.armor.ItemFortressArmor;
/*      */ import thaumcraft.common.items.tools.ItemThaumometer;
/*      */ import thaumcraft.common.lib.events.EssentiaHandler;
/*      */ import thaumcraft.common.lib.events.EssentiaHandler.EssentiaSourceFX;
/*      */ import thaumcraft.common.lib.utils.EntityUtils;
/*      */ import thaumcraft.common.tiles.devices.TileArcaneEar;
/*      */ import thaumcraft.common.tiles.devices.TileRechargePedestal;
/*      */ import thaumcraft.common.tiles.devices.TileRedstoneRelay;
/*      */ 
/*      */ public class RenderEventHandler
/*      */ {
/*      */   @SideOnly(Side.CLIENT)
/*  101 */   HudHandler hudHandler = new HudHandler();
/*      */   @SideOnly(Side.CLIENT)
/*  103 */   WandRenderingHandler wandHandler = new WandRenderingHandler();
/*      */   @SideOnly(Side.CLIENT)
/*  105 */   ShaderHandler shaderhandler = new ShaderHandler();
/*      */   
/*      */ 
/*      */ 
/*  109 */   public static List blockTags = new ArrayList();
/*      */   
/*      */ 
/*  112 */   public static float tagscale = 0.0F;
/*      */   
/*      */ 
/*  115 */   public static GuiResearchPopup researchPopup = null;
/*  116 */   public int tickCount = 0;
/*      */   int prevWorld;
/*  118 */   boolean checkedDate = false;
/*      */   
/*  120 */   private Random random = new Random();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @SideOnly(Side.CLIENT)
/*      */   @SubscribeEvent
/*      */   public void playerTick(TickEvent.PlayerTickEvent event)
/*      */   {
/*  135 */     if (event.side == Side.SERVER) return;
/*  136 */     Minecraft mc = Minecraft.getMinecraft();
/*      */     
/*  138 */     if (event.phase == TickEvent.Phase.START)
/*      */     {
/*  140 */       if (event.player.ticksExisted % 20 == 0) { checkChargedItems(event.player);
/*      */       }
/*      */       
/*  143 */       if ((event.player.getItemInUse() != null) && ((event.player.getItemInUse().getItem() instanceof IWand))) {
/*  144 */         event.player.setItemInUse(event.player.getHeldItem(), event.player.getItemInUseCount());
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  149 */         if (event.player.getEntityId() == mc.thePlayer.getEntityId())
/*      */         {
/*  151 */           this.shaderhandler.checkShaders(event, mc);
/*      */           
/*  153 */           if (ShaderHandler.warpVignette > 0) {
/*  154 */             ShaderHandler.warpVignette -= 1;
/*  155 */             targetBrightness = 0.0F;
/*      */           } else {
/*  157 */             targetBrightness = 1.0F;
/*      */           }
/*      */           
/*  160 */           if (fogFiddled) {
/*  161 */             if (fogDuration < 100) {
/*  162 */               fogTarget = 0.1F * (fogDuration / 100.0F);
/*  163 */             } else if (fogTarget < 0.1F) {
/*  164 */               fogTarget += 0.001F;
/*      */             }
/*  166 */             fogDuration -= 1;
/*  167 */             if (fogDuration < 0)
/*  168 */               fogFiddled = false;
/*      */           }
/*      */         }
/*      */       } catch (Exception e) {}
/*      */     }
/*      */   }
/*      */   
/*      */   public class ChargeEntry {
/*      */     public long time;
/*      */     public long tickTime;
/*      */     public ItemStack item;
/*  179 */     float charge = 0.0F;
/*  180 */     byte diff = 0;
/*      */     
/*  182 */     public ChargeEntry(long time, ItemStack item, float charge) { this.time = time;
/*  183 */       this.item = item;
/*  184 */       this.charge = charge;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*  189 */   public static TreeMap<Integer, ChargeEntry> chargedItems = new TreeMap();
/*      */   
/*      */   private void checkChargedItems(EntityPlayer player) {
/*  192 */     long time = System.currentTimeMillis();
/*  193 */     int count = 0;
/*      */     
/*      */ 
/*  196 */     ItemStack[] inv = player.inventory.mainInventory;
/*  197 */     for (int a = 0; a < InventoryPlayer.getHotbarSize(); a++) {
/*  198 */       if ((inv[a] != null) && ((inv[a].getItem() instanceof IRechargable))) {
/*  199 */         addItemToChargedList(inv[a], count, time);
/*      */       } else {
/*  201 */         chargedItems.remove(Integer.valueOf(count));
/*      */       }
/*  203 */       count++;
/*      */     }
/*      */     
/*      */ 
/*  207 */     IInventory baubles = baubles.api.BaublesApi.getBaubles(player);
/*  208 */     for (int a = 0; a < 4; a++) {
/*  209 */       if ((baubles.getStackInSlot(a) != null) && ((baubles.getStackInSlot(a).getItem() instanceof IRechargable))) {
/*  210 */         addItemToChargedList(baubles.getStackInSlot(a), count, time);
/*      */       } else {
/*  212 */         chargedItems.remove(Integer.valueOf(count));
/*      */       }
/*  214 */       count++;
/*      */     }
/*      */     
/*      */ 
/*  218 */     inv = player.inventory.armorInventory;
/*  219 */     for (int a = 0; a < inv.length; a++) {
/*  220 */       if ((inv[a] != null) && ((inv[a].getItem() instanceof IRechargable))) {
/*  221 */         addItemToChargedList(inv[a], count, time);
/*      */       } else {
/*  223 */         chargedItems.remove(Integer.valueOf(count));
/*      */       }
/*  225 */       count++;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void addItemToChargedList(ItemStack item, int count, long time)
/*      */   {
/*  232 */     float nc = ((IRechargable)item.getItem()).getChargeLevel(item);
/*  233 */     if (nc < 0.0F) {
/*  234 */       chargedItems.remove(Integer.valueOf(count));
/*  235 */       return;
/*      */     }
/*  237 */     ChargeEntry ce = (ChargeEntry)chargedItems.get(Integer.valueOf(count));
/*  238 */     if ((ce != null) && (ce.item.getItem() == item.getItem()) && (ce.charge != nc)) {
/*  239 */       if (nc > ce.charge) {
/*  240 */         ce.diff = 1;
/*      */       } else {
/*  242 */         ce.diff = -1;
/*      */       }
/*  244 */       ce.tickTime = time;
/*  245 */       ce.charge = nc;
/*      */       
/*  247 */       if (ce.time < time - 9500L) {
/*  248 */         ce.time = time;
/*      */       } else {
/*  250 */         ce.time = (time - 500L);
/*      */       }
/*  252 */     } else if (ce == null) {
/*  253 */       chargedItems.put(Integer.valueOf(count), new ChargeEntry(time, item, nc));
/*      */     } else {
/*  255 */       ce.diff = 0;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   @SideOnly(Side.CLIENT)
/*      */   @SubscribeEvent
/*      */   public void clientWorldTick(TickEvent.ClientTickEvent event)
/*      */   {
/*  265 */     if (event.side == Side.SERVER) return;
/*  266 */     Minecraft mc = FMLClientHandler.instance().getClient();
/*  267 */     World world = mc.theWorld;
/*  268 */     if (event.phase == TickEvent.Phase.START)
/*      */     {
/*  270 */       if ((mc.thePlayer != null) && (mc.thePlayer.getHeldItem() != null) && ((mc.thePlayer.getHeldItem().getItem() instanceof IWand)))
/*      */       {
/*  272 */         ItemStack itr = getItemToRender(mc.getItemRenderer());
/*  273 */         if ((itr != null) && ((itr.getItem() instanceof IWand))) {
/*  274 */           setItemToRender(mc.getItemRenderer(), mc.thePlayer.getHeldItem());
/*      */         }
/*      */       }
/*  277 */       this.tickCount += 1;
/*  278 */       for (String fxk : (String[])EssentiaHandler.sourceFX.keySet().toArray(new String[0])) {
/*  279 */         EssentiaHandler.EssentiaSourceFX fx = (EssentiaHandler.EssentiaSourceFX)EssentiaHandler.sourceFX.get(fxk);
/*  280 */         if (world != null) {
/*  281 */           int mod = 0;
/*  282 */           TileEntity tile = world.getTileEntity(fx.start);
/*  283 */           if ((tile != null) && ((tile instanceof thaumcraft.common.tiles.crafting.TileInfusionMatrix)))
/*  284 */             mod = -1;
/*  285 */           Thaumcraft.proxy.getFX().essentiaTrailFx(fx.end, fx.start.up(mod), this.tickCount, fx.color, 0.1F, fx.ext);
/*  286 */           EssentiaHandler.sourceFX.remove(fxk);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*  291 */     else if ((mc.theWorld != null) && (!this.checkedDate)) {
/*  292 */       this.checkedDate = true;
/*  293 */       Calendar calendar = mc.theWorld.getCurrentDate();
/*  294 */       if ((calendar.get(2) + 1 == 10) && (calendar.get(5) == 31)) {
/*  295 */         Thaumcraft.isHalloween = true;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   @SideOnly(Side.CLIENT)
/*      */   @SubscribeEvent
/*      */   public void renderTick(TickEvent.RenderTickEvent event)
/*      */   {
/*  306 */     if (event.phase == TickEvent.Phase.START) {
/*  307 */       UtilsFX.sysPartialTicks = event.renderTickTime;
/*      */     } else {
/*  309 */       Minecraft mc = FMLClientHandler.instance().getClient();
/*  310 */       World world = mc.theWorld;
/*      */       
/*  312 */       if ((Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer)) {
/*  313 */         EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
/*  314 */         long time = System.currentTimeMillis();
/*      */         
/*  316 */         if (researchPopup == null)
/*  317 */           researchPopup = new GuiResearchPopup(mc);
/*  318 */         researchPopup.updateResearchWindow();
/*      */         
/*  320 */         GuiScreen gui = mc.currentScreen;
/*      */         
/*  322 */         if (((gui instanceof GuiContainer)) && (((GuiScreen.isShiftKeyDown()) && (!Config.showTags)) || ((!GuiScreen.isShiftKeyDown()) && (Config.showTags) && (!Mouse.isGrabbed()))))
/*      */         {
/*      */ 
/*      */ 
/*  326 */           this.hudHandler.renderAspectsInGui((GuiContainer)gui, player);
/*      */         }
/*      */         
/*  329 */         if ((player != null) && (mc.inGameHasFocus) && (Minecraft.isGuiEnabled()))
/*      */         {
/*  331 */           if (chargedItems.size() > 0) {
/*  332 */             this.hudHandler.renderChargeMeters(mc, event.renderTickTime, player, time);
/*      */           }
/*      */           
/*  335 */           if (player.getHeldItem() != null) {
/*  336 */             if ((player.getHeldItem().getItem() instanceof ItemThaumometer)) {
/*  337 */               this.hudHandler.renderThaumometerHud(mc, Float.valueOf(event.renderTickTime), player, time);
/*      */ 
/*      */             }
/*  340 */             else if ((player.getHeldItem().getItem() instanceof IWand)) {
/*  341 */               this.hudHandler.renderCastingWandHud(mc, event.renderTickTime, player, time, player.getHeldItem());
/*      */ 
/*      */ 
/*      */             }
/*  345 */             else if ((player.getHeldItem().getItem() instanceof thaumcraft.common.items.tools.ItemSanityChecker)) {
/*  346 */               this.hudHandler.renderSanityHud(mc, Float.valueOf(event.renderTickTime), player, time);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @SideOnly(Side.CLIENT)
/*      */   @SubscribeEvent
/*      */   public void renderOverlay(RenderGameOverlayEvent event)
/*      */   {
/*  365 */     Minecraft mc = Minecraft.getMinecraft();
/*  366 */     long time = System.nanoTime() / 1000000L;
/*  367 */     if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
/*  368 */       this.wandHandler.handleFociRadial(mc, time, event);
/*      */     }
/*      */     
/*  371 */     if (event.type == RenderGameOverlayEvent.ElementType.PORTAL) {
/*  372 */       renderVignette(targetBrightness, event.resolution.getScaledWidth_double(), event.resolution.getScaledHeight_double());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   @SideOnly(Side.CLIENT)
/*      */   @SubscribeEvent
/*      */   public void renderShaders(RenderGameOverlayEvent.Pre event)
/*      */   {
/*  381 */     if ((Config.shaders) && (event.type == RenderGameOverlayEvent.ElementType.ALL)) {
/*  382 */       Minecraft mc = Minecraft.getMinecraft();
/*  383 */       if ((OpenGlHelper.shadersSupported) && (shaderGroups.size() > 0))
/*      */       {
/*      */ 
/*  386 */         updateShaderFrameBuffers(mc);
/*  387 */         GL11.glMatrixMode(5890);
/*  388 */         GL11.glLoadIdentity();
/*  389 */         for (ShaderGroup sg : shaderGroups.values()) {
/*  390 */           GL11.glPushMatrix();
/*      */           try {
/*  392 */             sg.loadShaderGroup(event.partialTicks);
/*      */           } catch (Exception e) {}
/*  394 */           GL11.glPopMatrix();
/*      */         }
/*  396 */         mc.getFramebuffer().bindFramebuffer(true);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*  401 */   public static boolean resetShaders = false;
/*  402 */   private static int oldDisplayWidth = 0;
/*  403 */   private static int oldDisplayHeight = 0;
/*      */   
/*  405 */   private void updateShaderFrameBuffers(Minecraft mc) { if ((resetShaders) || (mc.displayWidth != oldDisplayWidth) || (oldDisplayHeight != mc.displayHeight)) {
/*  406 */       for (ShaderGroup sg : shaderGroups.values()) {
/*  407 */         sg.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
/*      */       }
/*  409 */       oldDisplayWidth = mc.displayWidth;
/*  410 */       oldDisplayHeight = mc.displayHeight;
/*  411 */       resetShaders = false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   @SideOnly(Side.CLIENT)
/*      */   @SubscribeEvent
/*      */   public void blockHighlight(DrawBlockHighlightEvent event)
/*      */   {
/*  421 */     int ticks = event.player.ticksExisted;
/*  422 */     MovingObjectPosition target = event.target;
/*      */     
/*  424 */     if (blockTags.size() > 0) {
/*  425 */       int x = ((Integer)blockTags.get(0)).intValue();
/*  426 */       int y = ((Integer)blockTags.get(1)).intValue();
/*  427 */       int z = ((Integer)blockTags.get(2)).intValue();
/*  428 */       AspectList ot = (AspectList)blockTags.get(3);
/*  429 */       EnumFacing dir = EnumFacing.VALUES[((Integer)blockTags.get(4)).intValue()];
/*  430 */       if ((x == target.getBlockPos().getX()) && (y == target.getBlockPos().getY()) && (z == target.getBlockPos().getZ())) {
/*  431 */         if (tagscale < 0.5F) tagscale += 0.031F - tagscale / 10.0F;
/*  432 */         drawTagsOnContainer(target.getBlockPos().getX() + dir.getFrontOffsetX() / 2.0F, target.getBlockPos().getY() + dir.getFrontOffsetY() / 2.0F, target.getBlockPos().getZ() + dir.getFrontOffsetZ() / 2.0F, ot, 220, dir, event.partialTicks);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  440 */     if ((target != null) && (target.getBlockPos() != null))
/*      */     {
/*  442 */       TileEntity te = event.player.worldObj.getTileEntity(target.getBlockPos());
/*  443 */       if ((te != null) && ((te instanceof TileRedstoneRelay))) {
/*  444 */         MovingObjectPosition hit = RayTracer.retraceBlock(event.player.worldObj, event.player, target.getBlockPos());
/*  445 */         if (hit != null) {
/*  446 */           if (hit.subHit == 0) {
/*  447 */             drawTextInAir(target.getBlockPos().getX(), target.getBlockPos().getY() + 0.3D, target.getBlockPos().getZ(), event.partialTicks, "Out: " + ((TileRedstoneRelay)te).getOut());
/*      */ 
/*      */           }
/*  450 */           else if (hit.subHit == 1) {
/*  451 */             drawTextInAir(target.getBlockPos().getX(), target.getBlockPos().getY() + 0.3D, target.getBlockPos().getZ(), event.partialTicks, "In: " + ((TileRedstoneRelay)te).getIn());
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  456 */       if (EntityUtils.hasGoggles(event.player)) {
/*  457 */         boolean spaceAbove = event.player.worldObj.isAirBlock(target.getBlockPos().up());
/*  458 */         if (te != null) {
/*  459 */           int note = -1;
/*  460 */           if ((te instanceof TileEntityNote)) {
/*  461 */             note = ((TileEntityNote)te).note;
/*      */           }
/*  463 */           else if ((te instanceof TileArcaneEar)) {
/*  464 */             note = ((TileArcaneEar)te).note;
/*      */           }
/*  466 */           else if (((te instanceof IAspectContainer)) && (((IAspectContainer)te).getAspects() != null) && (((IAspectContainer)te).getAspects().size() > 0))
/*      */           {
/*  468 */             float shift = 0.0F;
/*  469 */             if ((te instanceof TileRechargePedestal)) shift = 0.6F;
/*  470 */             if (tagscale < 0.3F) tagscale += 0.031F - tagscale / 10.0F;
/*  471 */             drawTagsOnContainer(target.getBlockPos().getX(), target.getBlockPos().getY() + (spaceAbove ? 0.4F : 0.0F) + shift, target.getBlockPos().getZ(), ((IAspectContainer)te).getAspects(), 220, spaceAbove ? EnumFacing.UP : event.target.sideHit, event.partialTicks);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  476 */           if (note >= 0) {
/*  477 */             if (ticks % 5 == 0) {
/*  478 */               thaumcraft.common.lib.network.PacketHandler.INSTANCE.sendToServer(new thaumcraft.common.lib.network.misc.PacketNote(target.getBlockPos().getX(), target.getBlockPos().getY(), target.getBlockPos().getZ(), event.player.worldObj.provider.getDimensionId()));
/*      */             }
/*      */             
/*  481 */             drawTextInAir(target.getBlockPos().getX(), target.getBlockPos().getY() + 1, target.getBlockPos().getZ(), event.partialTicks, "Note: " + note);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  487 */     if ((target.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK) && (event.player.getHeldItem() != null) && ((event.player.getHeldItem().getItem() instanceof IArchitect)) && (!(event.player.getHeldItem().getItem() instanceof ItemFocusBasic)))
/*      */     {
/*      */ 
/*      */ 
/*  491 */       boolean proceed = true;
/*  492 */       MovingObjectPosition target2 = null;
/*  493 */       if ((event.player.getHeldItem().getItem() instanceof IArchitectExtended)) {
/*  494 */         proceed = ((IArchitectExtended)event.player.getHeldItem().getItem()).useBlockHighlight(event.player.getHeldItem());
/*  495 */         target2 = ((IArchitectExtended)event.player.getHeldItem().getItem()).getArchitectMOP(event.player.getHeldItem(), event.player.worldObj, event.player);
/*      */       }
/*      */       
/*  498 */       if (proceed) { if (this.wandHandler.handleArchitectOverlay(event.player.getHeldItem(), event.player, event.partialTicks, ticks, target2 == null ? target : target2)) {
/*  499 */           event.setCanceled(true);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   @SideOnly(Side.CLIENT)
/*      */   @SubscribeEvent
/*      */   public void renderLast(RenderWorldLastEvent event)
/*      */   {
/*  510 */     if (tagscale > 0.0F) tagscale -= 0.005F;
/*  511 */     float partialTicks = event.partialTicks;
/*  512 */     Minecraft mc = Minecraft.getMinecraft();
/*      */     Iterator i$;
/*  514 */     if ((Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer)) {
/*  515 */       EntityPlayer player = (EntityPlayer)mc.getRenderViewEntity();
/*  516 */       long time = System.currentTimeMillis();
/*      */       
/*  518 */       if (player.getHeldItem() != null) {
/*  519 */         if ((player.getHeldItem().getItem() instanceof thaumcraft.api.golems.ISealDisplayer)) {
/*  520 */           drawSeals(event, partialTicks, player, time);
/*      */         }
/*      */         
/*  523 */         if (((player.getHeldItem().getItem() instanceof IArchitectExtended)) && (!(player.getHeldItem().getItem() instanceof ItemFocusBasic)))
/*      */         {
/*  525 */           MovingObjectPosition target = ((IArchitectExtended)player.getHeldItem().getItem()).getArchitectMOP(player.getHeldItem(), player.worldObj, player);
/*  526 */           this.wandHandler.handleArchitectOverlay(player.getHeldItem(), player, partialTicks, player.ticksExisted, target);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  534 */       if (!clouds.isEmpty()) {
/*  535 */         ArrayList<Integer> rl = new ArrayList();
/*  536 */         for (Iterator i$ = clouds.keySet().iterator(); i$.hasNext();) { int key = ((Integer)i$.next()).intValue();
/*  537 */           EntityTaintSource cloud = (EntityTaintSource)clouds.get(Integer.valueOf(key));
/*  538 */           if (cloud.isDead) {
/*  539 */             rl.add(Integer.valueOf(key));
/*      */           } else {
/*  541 */             renderFluxRain(mc, cloud, partialTicks);
/*      */           }
/*      */         }
/*  544 */         for (i$ = rl.iterator(); i$.hasNext();) { int key = ((Integer)i$.next()).intValue();
/*  545 */           clouds.remove(Integer.valueOf(key));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void drawSeals(RenderWorldLastEvent event, float partialTicks, EntityPlayer player, long time)
/*      */   {
/*  554 */     ConcurrentHashMap<SealPos, thaumcraft.common.entities.construct.golem.seals.SealEntity> seals = (ConcurrentHashMap)SealHandler.sealEntities.get(Integer.valueOf(player.worldObj.provider.getDimensionId()));
/*  555 */     if ((seals != null) && (seals.size() > 0)) {
/*  556 */       GL11.glPushMatrix();
/*      */       
/*  558 */       if (player.isSneaking()) { GL11.glDisable(2929);
/*      */       }
/*  560 */       BlockPos pos = player.getPosition();
/*  561 */       GL11.glEnable(3042);
/*  562 */       GL11.glBlendFunc(770, 771);
/*      */       
/*  564 */       GL11.glDisable(2884);
/*      */       
/*      */ 
/*  567 */       double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
/*  568 */       double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
/*  569 */       double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
/*  570 */       GL11.glTranslated(-iPX, -iPY, -iPZ);
/*      */       
/*  572 */       for (ISealEntity seal : seals.values()) {
/*  573 */         double dis = player.getDistanceSqToCenter(seal.getSealPos().pos);
/*  574 */         if (dis <= 256.0D) {
/*  575 */           float alpha = 1.0F - (float)(dis / 256.0D);
/*  576 */           boolean ia = false;
/*  577 */           if (seal.isStoppedByRedstone(player.worldObj)) {
/*  578 */             ia = true;
/*  579 */             if (player.worldObj.rand.nextFloat() < partialTicks / 12.0F) {
/*  580 */               FXSpark ef = new FXSpark(player.worldObj, seal.getSealPos().pos.getX() + 0.5F + seal.getSealPos().face.getFrontOffsetX() * 0.66F, seal.getSealPos().pos.getY() + 0.5F + seal.getSealPos().face.getFrontOffsetY() * 0.66F, seal.getSealPos().pos.getZ() + 0.5F + seal.getSealPos().face.getFrontOffsetZ() * 0.66F, 0.3F);
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*  585 */               ef.layer = 3;
/*  586 */               ef.setRBGColorF(0.75F - player.worldObj.rand.nextFloat() * 0.2F, 0.0F, 0.0F);
/*  587 */               ParticleEngine.instance.addEffect(player.worldObj, ef);
/*  588 */               ia = false;
/*      */             }
/*      */           }
/*      */           
/*  592 */           renderSeal(seal.getSealPos().pos.getX(), seal.getSealPos().pos.getY(), seal.getSealPos().pos.getZ(), partialTicks, alpha, seal.getSealPos().face, seal.getSeal().getSealIcon(), player.isSneaking(), ia);
/*      */           
/*      */ 
/*  595 */           drawSealArea(player, seal, alpha, partialTicks);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  600 */       GL11.glDisable(3042);
/*  601 */       GL11.glEnable(2884);
/*      */       
/*  603 */       if (player.isSneaking()) { GL11.glEnable(2929);
/*      */       }
/*  605 */       GL11.glPopMatrix();
/*      */     }
/*      */   }
/*      */   
/*  609 */   final ResourceLocation CFRAME = new ResourceLocation("thaumcraft", "textures/misc/frame_corner.png");
/*  610 */   final ResourceLocation MIDDLE = new ResourceLocation("thaumcraft", "textures/misc/seal_area.png");
/*      */   
/*      */ 
/*      */   private void drawSealArea(EntityPlayer player, ISealEntity seal, float alpha, float partialTicks)
/*      */   {
/*  615 */     GL11.glPushMatrix();
/*      */     
/*  617 */     float r = 0.0F;
/*  618 */     float g = 0.0F;
/*  619 */     float b = 0.0F;
/*      */     
/*  621 */     if (seal.getColor() > 0) {
/*  622 */       Color c = new Color(EnumDyeColor.byMetadata(seal.getColor() - 1).getMapColor().colorValue);
/*  623 */       r = c.getRed() / 255.0F;
/*  624 */       g = c.getGreen() / 255.0F;
/*  625 */       b = c.getBlue() / 255.0F;
/*      */     } else {
/*  627 */       r = 0.7F + MathHelper.sin((player.ticksExisted + partialTicks + seal.getSealPos().pos.getX()) / 4.0F) * 0.1F;
/*  628 */       g = 0.7F + MathHelper.sin((player.ticksExisted + partialTicks + seal.getSealPos().pos.getY()) / 5.0F) * 0.1F;
/*  629 */       b = 0.7F + MathHelper.sin((player.ticksExisted + partialTicks + seal.getSealPos().pos.getZ()) / 6.0F) * 0.1F;
/*      */     }
/*      */     
/*  632 */     GL11.glPushMatrix();
/*  633 */     GL11.glTranslated(seal.getSealPos().pos.getX() + 0.5D, seal.getSealPos().pos.getY() + 0.5D, seal.getSealPos().pos.getZ() + 0.5D);
/*  634 */     GL11.glRotatef(90.0F, -seal.getSealPos().face.getFrontOffsetY(), seal.getSealPos().face.getFrontOffsetX(), -seal.getSealPos().face.getFrontOffsetZ());
/*  635 */     if (seal.getSealPos().face.getFrontOffsetZ() < 0) {
/*  636 */       GL11.glTranslated(0.0D, 0.0D, -0.5099999904632568D);
/*      */     } else {
/*  638 */       GL11.glTranslated(0.0D, 0.0D, 0.5099999904632568D);
/*      */     }
/*  640 */     GL11.glRotatef(player.ticksExisted % 360 + partialTicks, 0.0F, 0.0F, 1.0F);
/*  641 */     UtilsFX.renderQuadCentered(this.MIDDLE, 0.9F, r, g, b, 200, 771, alpha * 0.8F);
/*  642 */     GL11.glPopMatrix();
/*      */     
/*  644 */     if ((seal.getSeal() instanceof thaumcraft.api.golems.seals.ISealConfigArea)) {
/*  645 */       GL11.glDepthMask(false);
/*  646 */       AxisAlignedBB area = AxisAlignedBB.fromBounds(seal.getSealPos().pos.getX(), seal.getSealPos().pos.getY(), seal.getSealPos().pos.getZ(), seal.getSealPos().pos.getX() + 1, seal.getSealPos().pos.getY() + 1, seal.getSealPos().pos.getZ() + 1).offset(seal.getSealPos().face.getFrontOffsetX(), seal.getSealPos().face.getFrontOffsetY(), seal.getSealPos().face.getFrontOffsetZ()).addCoord(seal.getSealPos().face.getFrontOffsetX() != 0 ? (seal.getArea().getX() - 1) * seal.getSealPos().face.getFrontOffsetX() : 0.0D, seal.getSealPos().face.getFrontOffsetY() != 0 ? (seal.getArea().getY() - 1) * seal.getSealPos().face.getFrontOffsetY() : 0.0D, seal.getSealPos().face.getFrontOffsetZ() != 0 ? (seal.getArea().getZ() - 1) * seal.getSealPos().face.getFrontOffsetZ() : 0.0D).expand(seal.getSealPos().face.getFrontOffsetX() == 0 ? seal.getArea().getX() - 1 : 0.0D, seal.getSealPos().face.getFrontOffsetY() == 0 ? seal.getArea().getY() - 1 : 0.0D, seal.getSealPos().face.getFrontOffsetZ() == 0 ? seal.getArea().getZ() - 1 : 0.0D);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  662 */       double[][] locs = { { area.minX, area.minY, area.minZ }, { area.minX, area.maxY - 1.0D, area.minZ }, { area.maxX - 1.0D, area.minY, area.minZ }, { area.maxX - 1.0D, area.maxY - 1.0D, area.minZ }, { area.maxX - 1.0D, area.minY, area.maxZ - 1.0D }, { area.maxX - 1.0D, area.maxY - 1.0D, area.maxZ - 1.0D }, { area.minX, area.minY, area.maxZ - 1.0D }, { area.minX, area.maxY - 1.0D, area.maxZ - 1.0D } };
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  667 */       int q = 0;
/*  668 */       for (double[] loc : locs) {
/*  669 */         GL11.glPushMatrix();
/*  670 */         GL11.glTranslated(loc[0] + 0.5D, loc[1] + 0.5D, loc[2] + 0.5D);
/*  671 */         int w = 0;
/*  672 */         for (EnumFacing face : this.rotfaces[q]) {
/*  673 */           GL11.glPushMatrix();
/*  674 */           GL11.glRotatef(90.0F, -face.getFrontOffsetY(), face.getFrontOffsetX(), -face.getFrontOffsetZ());
/*  675 */           if (face.getFrontOffsetZ() < 0) {
/*  676 */             GL11.glTranslated(0.0D, 0.0D, -0.49000000953674316D);
/*      */           } else {
/*  678 */             GL11.glTranslated(0.0D, 0.0D, 0.49000000953674316D);
/*      */           }
/*  680 */           GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
/*  681 */           GL11.glRotatef(this.rotmat[q][w], 0.0F, 0.0F, 1.0F);
/*  682 */           UtilsFX.renderQuadCentered(this.CFRAME, 1.0F, r, g, b, 200, 771, alpha * 0.7F);
/*  683 */           GL11.glPopMatrix();
/*  684 */           w++;
/*      */         }
/*  686 */         GL11.glPopMatrix();
/*  687 */         q++;
/*      */       }
/*  689 */       GL11.glDepthMask(true);
/*      */     }
/*      */     
/*  692 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*  695 */   EnumFacing[][] rotfaces = { { EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.WEST }, { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.WEST }, { EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.EAST }, { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST }, { EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.EAST }, { EnumFacing.UP, EnumFacing.SOUTH, EnumFacing.EAST }, { EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.WEST }, { EnumFacing.UP, EnumFacing.SOUTH, EnumFacing.WEST } };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  700 */   int[][] rotmat = { { 0, 270, 0 }, { 270, 180, 270 }, { 90, 0, 90 }, { 180, 90, 180 }, { 180, 180, 0 }, { 90, 270, 270 }, { 270, 90, 90 }, { 0, 0, 180 } };
/*      */   
/*      */ 
/*      */   void renderSeal(int x, int y, int z, float partialTicks, float alpha, EnumFacing face, ResourceLocation resourceLocation, boolean b, boolean ia)
/*      */   {
/*  705 */     GL11.glPushMatrix();
/*  706 */     GL11.glColor4f(ia ? 0.5F : 1.0F, ia ? 0.5F : 1.0F, ia ? 0.5F : 1.0F, alpha);
/*  707 */     translateSeal(x, y, z, face.ordinal(), -0.05F);
/*  708 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  709 */     UtilsFX.renderItemIn2D(resourceLocation.toString(), 0.1F);
/*  710 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  711 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   private void translateSeal(float x, float y, float z, int orientation, float off) {
/*  715 */     if (orientation == 1)
/*      */     {
/*  717 */       GL11.glTranslatef(x + 0.25F, y + 1.0F, z + 0.75F);
/*  718 */       GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/*  719 */     } else if (orientation == 0)
/*      */     {
/*  721 */       GL11.glTranslatef(x + 0.25F, y, z + 0.25F);
/*  722 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  723 */     } else if (orientation == 3)
/*      */     {
/*  725 */       GL11.glTranslatef(x + 0.25F, y + 0.25F, z + 1.0F);
/*  726 */     } else if (orientation == 2)
/*      */     {
/*  728 */       GL11.glTranslatef(x + 0.75F, y + 0.25F, z);
/*  729 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*  730 */     } else if (orientation == 5)
/*      */     {
/*  732 */       GL11.glTranslatef(x + 1.0F, y + 0.25F, z + 0.75F);
/*  733 */       GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*  734 */     } else if (orientation == 4)
/*      */     {
/*  736 */       GL11.glTranslatef(x, y + 0.25F, z + 0.25F);
/*  737 */       GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/*      */     }
/*  739 */     GL11.glTranslatef(0.0F, 0.0F, -off);
/*      */   }
/*      */   
/*  742 */   public static final ResourceLocation rainTexture = new ResourceLocation("textures/environment/rain.png");
/*  743 */   private float[] rainXCoords = null;
/*  744 */   private float[] rainYCoords = null;
/*  745 */   public static ConcurrentHashMap<Integer, EntityTaintSource> clouds = new ConcurrentHashMap();
/*      */   
/*      */   private void renderFluxRain(Minecraft mc, EntityTaintSource cloud, float partialTicks)
/*      */   {
/*  749 */     if (this.rainXCoords == null) {
/*  750 */       this.rainXCoords = new float['Ѐ'];
/*  751 */       this.rainYCoords = new float['Ѐ'];
/*  752 */       for (int i = 0; i < 32; i++)
/*      */       {
/*  754 */         for (int j = 0; j < 32; j++)
/*      */         {
/*  756 */           float f = j - 16;
/*  757 */           float f1 = i - 16;
/*  758 */           float f2 = MathHelper.sqrt_float(f * f + f1 * f1);
/*  759 */           this.rainXCoords[(i << 5 | j)] = (-f1 / f2);
/*  760 */           this.rainYCoords[(i << 5 | j)] = (f / f2);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  765 */     GlStateManager.pushMatrix();
/*  766 */     GlStateManager.depthMask(false);
/*  767 */     GlStateManager.enableCull();
/*  768 */     mc.entityRenderer.enableLightmap();
/*  769 */     Entity entity = mc.getRenderViewEntity();
/*  770 */     this.random.setSeed(entity.ticksExisted * 312987231L);
/*  771 */     WorldClient worldclient = mc.theWorld;
/*  772 */     int i = MathHelper.floor_double(entity.posX);
/*  773 */     int j = MathHelper.floor_double(entity.posY);
/*  774 */     int k = MathHelper.floor_double(entity.posZ);
/*  775 */     Tessellator tessellator = Tessellator.getInstance();
/*  776 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  777 */     GlStateManager.disableCull();
/*  778 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*  779 */     GlStateManager.enableBlend();
/*  780 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  781 */     GlStateManager.alphaFunc(516, 0.1F);
/*  782 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/*  783 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/*  784 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/*  785 */     int l = MathHelper.floor_double(d1);
/*  786 */     byte b0 = 10;
/*  787 */     if (mc.gameSettings.fancyGraphics)
/*      */     {
/*  789 */       b0 = 15;
/*      */     }
/*  791 */     byte b1 = -1;
/*  792 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  793 */     for (int i1 = k - b0; i1 <= k + b0; i1++)
/*      */     {
/*  795 */       for (int j1 = i - b0; j1 <= i + b0; j1++)
/*      */       {
/*  797 */         int k1 = (i1 - k + 16) * 32 + j1 - i + 16;
/*  798 */         float f3 = this.rainXCoords[k1] * 0.5F;
/*  799 */         float f4 = this.rainYCoords[k1] * 0.5F;
/*  800 */         BlockPos blockpos = new BlockPos(j1, MathHelper.floor_double(cloud.posY), i1);
/*      */         
/*  802 */         if (cloud.getDistanceSqToCenter(blockpos) <= 256.0D)
/*      */         {
/*  804 */           int l1 = worldclient.getPrecipitationHeight(blockpos).getY();
/*  805 */           int i2 = j - b0;
/*  806 */           int j2 = j + b0;
/*      */           
/*  808 */           if (i2 < l1)
/*      */           {
/*  810 */             i2 = l1;
/*      */           }
/*      */           
/*  813 */           if (j2 < l1)
/*      */           {
/*  815 */             j2 = l1;
/*      */           }
/*      */           
/*  818 */           float f5 = 1.0F;
/*  819 */           int k2 = l1;
/*      */           
/*  821 */           if (l1 < l)
/*      */           {
/*  823 */             k2 = l;
/*      */           }
/*      */           
/*  826 */           if (j2 > MathHelper.floor_double(cloud.posY)) { j2 = MathHelper.floor_double(cloud.posY);
/*      */           }
/*  828 */           if (i2 != j2)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  833 */             if (b1 != 0)
/*      */             {
/*  835 */               if (b1 >= 0)
/*      */               {
/*  837 */                 tessellator.draw();
/*      */               }
/*      */               
/*  840 */               b1 = 0;
/*  841 */               mc.getTextureManager().bindTexture(rainTexture);
/*  842 */               worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
/*      */             }
/*      */             
/*  845 */             float f7 = ((entity.ticksExisted + j1 * j1 * 3121 + j1 * 45238971 + i1 * i1 * 418711 + i1 * 13761 & 0x1F) + partialTicks) / 32.0F * (3.0F + this.random.nextFloat());
/*      */             
/*  847 */             double d3 = j1 + 0.5F - entity.posX;
/*  848 */             double d4 = i1 + 0.5F - entity.posZ;
/*  849 */             float f9 = MathHelper.sqrt_double(d3 * d3 + d4 * d4) / b0;
/*  850 */             int ii = worldclient.getCombinedLight(new BlockPos(j1, k2, i1), 0);
/*  851 */             int jj = ii >> 16 & 0xFFFF;
/*  852 */             int kk = ii & 0xFFFF;
/*      */             
/*  854 */             worldrenderer.setTranslation(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
/*  855 */             worldrenderer.pos(j1 - f3 + 0.5D, i2, i1 - f4 + 0.5D).tex(0.0F * f5, i2 * f5 / 4.0F + f7 * f5).lightmap(jj, kk).color(1.0F, 0.2F, 0.8F, ((1.0F - f9 * f9) * 0.5F + 0.5F) * 0.5F).endVertex();
/*      */             
/*  857 */             worldrenderer.pos(j1 + f3 + 0.5D, i2, i1 + f4 + 0.5D).tex(1.0F * f5, i2 * f5 / 4.0F + f7 * f5).lightmap(jj, kk).color(1.0F, 0.2F, 0.8F, ((1.0F - f9 * f9) * 0.5F + 0.5F) * 0.5F).endVertex();
/*      */             
/*  859 */             worldrenderer.pos(j1 + f3 + 0.5D, j2, i1 + f4 + 0.5D).tex(1.0F * f5, j2 * f5 / 4.0F + f7 * f5).lightmap(jj, kk).color(1.0F, 0.2F, 0.8F, ((1.0F - f9 * f9) * 0.5F + 0.5F) * 0.5F).endVertex();
/*      */             
/*  861 */             worldrenderer.pos(j1 - f3 + 0.5D, j2, i1 - f4 + 0.5D).tex(0.0F * f5, j2 * f5 / 4.0F + f7 * f5).lightmap(jj, kk).color(1.0F, 0.2F, 0.8F, ((1.0F - f9 * f9) * 0.5F + 0.5F) * 0.5F).endVertex();
/*      */             
/*  863 */             worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  869 */     if (b1 >= 0)
/*      */     {
/*  871 */       tessellator.draw();
/*      */     }
/*      */     
/*  874 */     GlStateManager.enableCull();
/*  875 */     GlStateManager.disableBlend();
/*  876 */     GlStateManager.alphaFunc(516, 0.1F);
/*  877 */     mc.entityRenderer.disableLightmap();
/*  878 */     GlStateManager.depthMask(true);
/*  879 */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */ 
/*  883 */   public static HashMap<Integer, ShaderGroup> shaderGroups = new HashMap();
/*      */   
/*      */ 
/*      */ 
/*  887 */   public static boolean fogFiddled = false;
/*  888 */   public static float fogTarget = 0.0F;
/*  889 */   public static int fogDuration = 0;
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   @SubscribeEvent
/*      */   public void fogDensityEvent(EntityViewRenderEvent.RenderFogEvent event) {
/*  894 */     if ((fogFiddled) && (fogTarget > 0.0F)) {
/*  895 */       GL11.glFogi(2917, 2048);
/*  896 */       GL11.glFogf(2914, fogTarget);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   @SideOnly(Side.CLIENT)
/*      */   @SubscribeEvent
/*      */   public void renderPlayerSpecialsEvent(RenderPlayerEvent.Specials.Pre event)
/*      */   {
/*  905 */     if ((event.entityPlayer != null) && (event.entityPlayer.inventory.armorInventory[2] != null) && (((event.entityPlayer.inventory.armorInventory[2].getItem() instanceof ItemFortressArmor)) || ((event.entityPlayer.inventory.armorInventory[2].getItem() instanceof thaumcraft.common.items.armor.ItemVoidRobeArmor))))
/*      */     {
/*      */ 
/*  908 */       event.renderCape = false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   @SubscribeEvent
/*      */   public void livingTick(LivingEvent.LivingUpdateEvent event)
/*      */   {
/*  916 */     if ((event.entity.worldObj.isRemote) && ((event.entity instanceof EntityMob)) && (!event.entity.isDead))
/*      */     {
/*  918 */       EntityMob mob = (EntityMob)event.entity;
/*  919 */       if (mob.getEntityAttribute(EntityUtils.CHAMPION_MOD) != null) {
/*  920 */         Integer t = Integer.valueOf((int)mob.getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue());
/*  921 */         if ((t != null) && (t.intValue() >= 0) && (t.intValue() < ChampionModifier.mods.length)) {
/*  922 */           ChampionModifier.mods[t.intValue()].effect.showFX(mob);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void drawTagsOnContainer(double x, double y, double z, AspectList tags, int bright, EnumFacing dir, float partialTicks)
/*      */   {
/*  932 */     if (((Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer)) && (tags != null) && (tags.size() > 0) && (dir != null)) {
/*  933 */       EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
/*  934 */       double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
/*  935 */       double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
/*  936 */       double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
/*      */       
/*      */ 
/*  939 */       int e = 0;
/*      */       
/*  941 */       int rowsize = 5;
/*  942 */       int current = 0;
/*  943 */       float shifty = 0.0F;
/*  944 */       int left = tags.size();
/*  945 */       for (Aspect tag : tags.getAspects()) {
/*  946 */         int div = Math.min(left, rowsize);
/*      */         
/*  948 */         if (current >= rowsize) {
/*  949 */           current = 0;
/*  950 */           shifty -= tagscale * 1.05F;
/*  951 */           left -= rowsize;
/*  952 */           if (left < rowsize) {
/*  953 */             div = left % rowsize;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  958 */         float shift = (current - div / 2.0F + 0.5F) * tagscale * 4.0F;
/*  959 */         shift *= tagscale;
/*      */         
/*  961 */         Color color = new Color(tag.getColor());
/*      */         
/*  963 */         GL11.glPushMatrix();
/*      */         
/*  965 */         GL11.glDisable(2929);
/*      */         
/*  967 */         GL11.glTranslated(-iPX + x + 0.5D + tagscale * 2.0F * dir.getFrontOffsetX(), -iPY + y - shifty + 0.5D + tagscale * 2.0F * dir.getFrontOffsetY(), -iPZ + z + 0.5D + tagscale * 2.0F * dir.getFrontOffsetZ());
/*      */         
/*  969 */         float xd = (float)(iPX - (x + 0.5D));
/*  970 */         float zd = (float)(iPZ - (z + 0.5D));
/*  971 */         float rotYaw = (float)(Math.atan2(xd, zd) * 180.0D / 3.141592653589793D);
/*      */         
/*  973 */         GL11.glRotatef(rotYaw + 180.0F, 0.0F, 1.0F, 0.0F);
/*      */         
/*  975 */         GL11.glTranslated(shift, 0.0D, 0.0D);
/*  976 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  977 */         GL11.glScalef(tagscale, tagscale, tagscale);
/*      */         
/*  979 */         UtilsFX.renderQuadCentered(tag.getImage(), 1.0F, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, bright, 771, 0.75F);
/*      */         
/*  981 */         if (tags.getAmount(tag) >= 0) {
/*  982 */           GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  983 */           String am = "" + tags.getAmount(tag);
/*  984 */           GL11.glScalef(0.04F, 0.04F, 0.04F);
/*  985 */           GL11.glTranslated(0.0D, 6.0D, -0.1D);
/*  986 */           int sw = Minecraft.getMinecraft().fontRendererObj.getStringWidth(am);
/*  987 */           GL11.glEnable(3042);
/*  988 */           GL11.glBlendFunc(770, 771);
/*  989 */           Minecraft.getMinecraft().fontRendererObj.drawString(am, 14 - sw, 1, 1118481);
/*  990 */           GL11.glTranslated(0.0D, 0.0D, -0.1D);
/*  991 */           Minecraft.getMinecraft().fontRendererObj.drawString(am, 13 - sw, 0, 16777215);
/*      */         }
/*  993 */         GL11.glEnable(2929);
/*  994 */         GL11.glPopMatrix();
/*  995 */         current++;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void drawTextInAir(double x, double y, double z, float partialTicks, String text) {
/* 1001 */     if ((Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer)) {
/* 1002 */       EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
/* 1003 */       double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
/* 1004 */       double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
/* 1005 */       double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
/*      */       
/* 1007 */       GL11.glPushMatrix();
/*      */       
/* 1009 */       GL11.glTranslated(-iPX + x + 0.5D, -iPY + y + 0.5D, -iPZ + z + 0.5D);
/*      */       
/* 1011 */       float xd = (float)(iPX - (x + 0.5D));
/* 1012 */       float zd = (float)(iPZ - (z + 0.5D));
/* 1013 */       float rotYaw = (float)(Math.atan2(xd, zd) * 180.0D / 3.141592653589793D);
/*      */       
/* 1015 */       GL11.glRotatef(rotYaw + 180.0F, 0.0F, 1.0F, 0.0F);
/*      */       
/*      */ 
/* 1018 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 1019 */       GL11.glScalef(0.02F, 0.02F, 0.02F);
/* 1020 */       int sw = Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
/* 1021 */       GL11.glEnable(3042);
/* 1022 */       GL11.glBlendFunc(770, 771);
/* 1023 */       Minecraft.getMinecraft().fontRendererObj.drawString(text, 1 - sw / 2, 1, 1118481);
/* 1024 */       GL11.glTranslated(0.0D, 0.0D, -0.1D);
/* 1025 */       Minecraft.getMinecraft().fontRendererObj.drawString(text, -sw / 2, 0, 16777215);
/*      */       
/*      */ 
/* 1028 */       GL11.glPopMatrix();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1434 */   public static float prevVignetteBrightness = 0.0F;
/* 1435 */   public static float targetBrightness = 1.0F;
/* 1436 */   protected static final ResourceLocation vignetteTexPath = new ResourceLocation("thaumcraft", "textures/misc/vignette.png");
/*      */   
/*      */   protected void renderVignette(float brightness, double sw, double sh)
/*      */   {
/* 1440 */     int k = (int)sw;
/* 1441 */     int l = (int)sh;
/*      */     
/* 1443 */     brightness = 1.0F - brightness;
/* 1444 */     prevVignetteBrightness = (float)(prevVignetteBrightness + (brightness - prevVignetteBrightness) * 0.01D);
/*      */     
/* 1446 */     if (prevVignetteBrightness > 0.0F) {
/* 1447 */       float b = prevVignetteBrightness * (1.0F + MathHelper.sin(Minecraft.getMinecraft().thePlayer.ticksExisted / 2.0F) * 0.1F);
/*      */       
/* 1449 */       GL11.glPushMatrix();
/* 1450 */       GL11.glClear(256);
/* 1451 */       GL11.glMatrixMode(5889);
/* 1452 */       GL11.glLoadIdentity();
/* 1453 */       GL11.glOrtho(0.0D, sw, sh, 0.0D, 1000.0D, 3000.0D);
/*      */       
/* 1455 */       Minecraft.getMinecraft().getTextureManager().bindTexture(vignetteTexPath);
/*      */       
/* 1457 */       GL11.glMatrixMode(5888);
/* 1458 */       GL11.glLoadIdentity();
/* 1459 */       GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
/* 1460 */       GL11.glDisable(2929);
/* 1461 */       GL11.glDepthMask(false);
/* 1462 */       OpenGlHelper.glBlendFunc(0, 769, 1, 0);
/* 1463 */       GL11.glColor4f(b, b, b, 1.0F);
/*      */       
/*      */ 
/* 1466 */       Tessellator tessellator = Tessellator.getInstance();
/* 1467 */       tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1468 */       tessellator.getWorldRenderer().pos(0.0D, l, -90.0D).tex(0.0D, 1.0D).endVertex();
/* 1469 */       tessellator.getWorldRenderer().pos(k, l, -90.0D).tex(1.0D, 1.0D).endVertex();
/* 1470 */       tessellator.getWorldRenderer().pos(k, 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/* 1471 */       tessellator.getWorldRenderer().pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/* 1472 */       tessellator.draw();
/* 1473 */       GL11.glDepthMask(true);
/* 1474 */       GL11.glEnable(2929);
/* 1475 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1476 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*      */       
/*      */ 
/*      */ 
/* 1480 */       GL11.glPopMatrix();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   @SideOnly(Side.CLIENT)
/*      */   @SubscribeEvent
/*      */   public void textureStitchEventPre(TextureStitchEvent.Pre event)
/*      */   {
/* 1489 */     for (WandRod rod : WandRod.rods.values()) {
/* 1490 */       event.map.registerSprite(rod.getTexture());
/*      */     }
/* 1492 */     for (WandCap cap : WandCap.caps.values()) {
/* 1493 */       event.map.registerSprite(cap.getTexture());
/*      */     }
/*      */     
/* 1496 */     event.map.registerSprite(new ResourceLocation("thaumcraft", "blocks/tablequill"));
/*      */   }
/*      */   
/*      */   public void setItemToRender(ItemRenderer ir, ItemStack stack) {
/*      */     try {
/* 1501 */       net.minecraftforge.fml.common.ObfuscationReflectionHelper.setPrivateValue(ItemRenderer.class, ir, stack, new String[] { "itemToRender", "field_78453_b" });
/*      */     }
/*      */     catch (Exception e) {}
/*      */   }
/*      */   
/*      */   public ItemStack getItemToRender(ItemRenderer ir) {
/*      */     try {
/* 1508 */       return (ItemStack)net.minecraftforge.fml.relauncher.ReflectionHelper.getPrivateValue(ItemRenderer.class, ir, new String[] { "itemToRender", "field_78453_b" }); } catch (Exception e) {}
/* 1509 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\lib\RenderEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */