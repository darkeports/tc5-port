/*     */ package thaumcraft.client.lib;
/*     */ 
/*     */ import baubles.api.BaublesApi;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MouseHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.api.items.IArchitect;
/*     */ import thaumcraft.api.items.IArchitect.EnumAxis;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.common.items.wands.ItemFocusPouch;
/*     */ import thaumcraft.common.lib.events.KeyHandler;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.misc.PacketFocusChangeToServer;
/*     */ 
/*     */ public class WandRenderingHandler
/*     */ {
/*  40 */   static float radialHudScale = 0.0F;
/*  41 */   TreeMap<String, Integer> foci = new TreeMap();
/*  42 */   HashMap<String, ItemStack> fociItem = new HashMap();
/*  43 */   HashMap<String, Boolean> fociHover = new HashMap();
/*  44 */   HashMap<String, Float> fociScale = new HashMap();
/*  45 */   long lastTime = 0L;
/*  46 */   boolean lastState = false;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void handleFociRadial(Minecraft mc, long time, RenderGameOverlayEvent event) {
/*  50 */     if ((KeyHandler.radialActive) || (radialHudScale > 0.0F)) {
/*  51 */       long timeDiff = System.currentTimeMillis() - KeyHandler.lastPressF;
/*  52 */       if (KeyHandler.radialActive)
/*     */       {
/*  54 */         if (mc.currentScreen != null) {
/*  55 */           KeyHandler.radialActive = false;
/*  56 */           KeyHandler.radialLock = true;
/*  57 */           mc.setIngameFocus();
/*  58 */           mc.setIngameNotInFocus();
/*  59 */           return;
/*     */         }
/*     */         
/*  62 */         if (radialHudScale == 0.0F) {
/*  63 */           this.foci.clear();
/*  64 */           this.fociItem.clear();
/*  65 */           this.fociHover.clear();
/*  66 */           this.fociScale.clear();
/*     */           
/*  68 */           int pouchcount = 0;
/*  69 */           ItemStack item = null;
/*     */           
/*  71 */           IInventory baubles = BaublesApi.getBaubles(mc.thePlayer);
/*  72 */           for (int a = 0; a < 4; a++) {
/*  73 */             if ((baubles.getStackInSlot(a) != null) && ((baubles.getStackInSlot(a).getItem() instanceof ItemFocusPouch))) {
/*  74 */               pouchcount++;
/*  75 */               item = baubles.getStackInSlot(a);
/*  76 */               ItemStack[] inv = ((ItemFocusPouch)item.getItem()).getInventory(item);
/*  77 */               for (int q = 0; q < inv.length; q++) {
/*  78 */                 item = inv[q];
/*  79 */                 if ((item != null) && ((item.getItem() instanceof ItemFocusBasic))) {
/*  80 */                   this.foci.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Integer.valueOf(q + pouchcount * 1000));
/*  81 */                   this.fociItem.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), item.copy());
/*  82 */                   this.fociScale.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Float.valueOf(1.0F));
/*  83 */                   this.fociHover.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Boolean.valueOf(false));
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           
/*  89 */           for (int a = 0; a < 36; a++) {
/*  90 */             item = mc.thePlayer.inventory.mainInventory[a];
/*  91 */             if ((item != null) && ((item.getItem() instanceof ItemFocusBasic))) {
/*  92 */               this.foci.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Integer.valueOf(a));
/*  93 */               this.fociItem.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), item.copy());
/*  94 */               this.fociScale.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Float.valueOf(1.0F));
/*  95 */               this.fociHover.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Boolean.valueOf(false));
/*     */             }
/*  97 */             if ((item != null) && ((item.getItem() instanceof ItemFocusPouch))) {
/*  98 */               pouchcount++;
/*  99 */               ItemStack[] inv = ((ItemFocusPouch)item.getItem()).getInventory(item);
/* 100 */               for (int q = 0; q < inv.length; q++) {
/* 101 */                 item = inv[q];
/* 102 */                 if ((item != null) && ((item.getItem() instanceof ItemFocusBasic))) {
/* 103 */                   this.foci.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Integer.valueOf(q + pouchcount * 1000));
/* 104 */                   this.fociItem.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), item.copy());
/* 105 */                   this.fociScale.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Float.valueOf(1.0F));
/* 106 */                   this.fociHover.put(((ItemFocusBasic)item.getItem()).getSortingHelper(item), Boolean.valueOf(false));
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 111 */           if ((this.foci.size() > 0) && 
/* 112 */             (mc.inGameHasFocus))
/*     */           {
/* 114 */             mc.inGameHasFocus = false;
/* 115 */             mc.mouseHelper.ungrabMouseCursor();
/*     */           }
/*     */         }
/*     */       }
/* 119 */       else if (mc.currentScreen == null)
/*     */       {
/* 121 */         if (this.lastState) {
/* 122 */           if (org.lwjgl.opengl.Display.isActive())
/*     */           {
/* 124 */             if (!mc.inGameHasFocus)
/*     */             {
/* 126 */               mc.inGameHasFocus = true;
/* 127 */               mc.mouseHelper.grabMouseCursor();
/*     */             }
/*     */           }
/* 130 */           this.lastState = false;
/*     */         }
/*     */       }
/* 133 */       renderFocusRadialHUD(event.resolution.getScaledWidth_double(), event.resolution.getScaledHeight_double(), time, event.partialTicks);
/* 134 */       if (time > this.lastTime) {
/* 135 */         for (String key : this.fociHover.keySet()) {
/* 136 */           if (((Boolean)this.fociHover.get(key)).booleanValue()) {
/* 137 */             if ((!KeyHandler.radialActive) && (!KeyHandler.radialLock)) {
/* 138 */               PacketHandler.INSTANCE.sendToServer(new PacketFocusChangeToServer(mc.thePlayer, key));
/* 139 */               KeyHandler.radialLock = true;
/*     */             }
/* 141 */             if (((Float)this.fociScale.get(key)).floatValue() < 1.3F) {
/* 142 */               this.fociScale.put(key, Float.valueOf(((Float)this.fociScale.get(key)).floatValue() + 0.025F));
/*     */             }
/*     */           }
/* 145 */           else if (((Float)this.fociScale.get(key)).floatValue() > 1.0F) {
/* 146 */             this.fociScale.put(key, Float.valueOf(((Float)this.fociScale.get(key)).floatValue() - 0.025F));
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 151 */         if (!KeyHandler.radialActive) {
/* 152 */           radialHudScale -= 0.05F;
/*     */         }
/* 154 */         else if ((KeyHandler.radialActive) && (radialHudScale < 1.0F)) { radialHudScale += 0.05F;
/*     */         }
/* 156 */         if (radialHudScale > 1.0F) radialHudScale = 1.0F;
/* 157 */         if (radialHudScale < 0.0F) {
/* 158 */           radialHudScale = 0.0F;
/* 159 */           KeyHandler.radialLock = false;
/*     */         }
/* 161 */         this.lastTime = (time + 5L);
/* 162 */         this.lastState = KeyHandler.radialActive;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 167 */   final ResourceLocation R1 = new ResourceLocation("thaumcraft", "textures/misc/radial.png");
/* 168 */   final ResourceLocation R2 = new ResourceLocation("thaumcraft", "textures/misc/radial2.png");
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private void renderFocusRadialHUD(double sw, double sh, long time, float partialTicks) {
/* 172 */     Minecraft mc = Minecraft.getMinecraft();
/*     */     
/* 174 */     if ((mc.thePlayer.getCurrentEquippedItem() == null) || (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof IWand)))
/*     */     {
/* 176 */       return;
/*     */     }
/* 178 */     IWand wand = (IWand)mc.thePlayer.getCurrentEquippedItem().getItem();
/* 179 */     ItemFocusBasic focus = wand.getFocus(mc.thePlayer.getCurrentEquippedItem());
/*     */     
/* 181 */     int i = (int)(Mouse.getEventX() * sw / mc.displayWidth);
/* 182 */     int j = (int)(sh - Mouse.getEventY() * sh / mc.displayHeight - 1.0D);
/* 183 */     int k = Mouse.getEventButton();
/*     */     
/* 185 */     if (this.fociItem.size() == 0) { return;
/*     */     }
/* 187 */     GL11.glPushMatrix();
/* 188 */     GL11.glClear(256);
/* 189 */     GL11.glMatrixMode(5889);
/* 190 */     GL11.glLoadIdentity();
/* 191 */     GL11.glOrtho(0.0D, sw, sh, 0.0D, 1000.0D, 3000.0D);
/* 192 */     GL11.glMatrixMode(5888);
/* 193 */     GL11.glLoadIdentity();
/* 194 */     GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
/* 195 */     GL11.glDisable(2929);
/* 196 */     GL11.glDepthMask(false);
/*     */     
/* 198 */     GL11.glPushMatrix();
/*     */     
/* 200 */     GL11.glTranslated(sw / 2.0D, sh / 2.0D, 0.0D);
/*     */     
/* 202 */     ItemStack tt = null;
/*     */     
/* 204 */     float width = 16.0F + this.fociItem.size() * 2.5F;
/*     */     
/* 206 */     mc.renderEngine.bindTexture(this.R1);
/* 207 */     GL11.glPushMatrix();
/* 208 */     GL11.glRotatef(partialTicks + mc.thePlayer.ticksExisted % 720 / 2.0F, 0.0F, 0.0F, 1.0F);
/* 209 */     GL11.glAlphaFunc(516, 0.003921569F);
/* 210 */     GL11.glEnable(3042);
/* 211 */     GL11.glBlendFunc(770, 771);
/* 212 */     UtilsFX.renderQuadCentered(1, 1, 0, width * 2.75F * radialHudScale, 0.5F, 0.5F, 0.5F, 200, 771, 0.5F);
/*     */     
/* 214 */     GL11.glDisable(3042);
/* 215 */     GL11.glAlphaFunc(516, 0.1F);
/* 216 */     GL11.glPopMatrix();
/*     */     
/* 218 */     mc.renderEngine.bindTexture(this.R2);
/* 219 */     GL11.glPushMatrix();
/* 220 */     GL11.glRotatef(-(partialTicks + mc.thePlayer.ticksExisted % 720 / 2.0F), 0.0F, 0.0F, 1.0F);
/* 221 */     GL11.glAlphaFunc(516, 0.003921569F);
/* 222 */     GL11.glEnable(3042);
/* 223 */     GL11.glBlendFunc(770, 771);
/* 224 */     UtilsFX.renderQuadCentered(1, 1, 0, width * 2.55F * radialHudScale, 0.5F, 0.5F, 0.5F, 200, 771, 0.5F);
/*     */     
/* 226 */     GL11.glDisable(3042);
/* 227 */     GL11.glAlphaFunc(516, 0.1F);
/* 228 */     GL11.glPopMatrix();
/*     */     
/* 230 */     if (focus != null) {
/* 231 */       ItemStack item = wand.getFocusStack(mc.thePlayer.getCurrentEquippedItem()).copy();
/* 232 */       UtilsFX.renderItemInGUI(-8, -8, 100, item);
/*     */       
/* 234 */       int mx = (int)(i - sw / 2.0D);
/* 235 */       int my = (int)(j - sh / 2.0D);
/*     */       
/* 237 */       if ((mx >= -10) && (mx <= 10) && (my >= -10) && (my <= 10)) {
/* 238 */         tt = item;
/*     */       }
/*     */     }
/*     */     
/* 242 */     GL11.glScaled(radialHudScale, radialHudScale, radialHudScale);
/*     */     
/* 244 */     float currentRot = -90.0F * radialHudScale;
/* 245 */     float pieSlice = 360.0F / this.fociItem.size();
/* 246 */     String key = (String)this.foci.firstKey();
/*     */     
/* 248 */     for (int a = 0; a < this.fociItem.size(); a++)
/*     */     {
/* 250 */       double xx = MathHelper.cos(currentRot / 180.0F * 3.1415927F) * width;
/* 251 */       double yy = MathHelper.sin(currentRot / 180.0F * 3.1415927F) * width;
/* 252 */       currentRot += pieSlice;
/*     */       
/* 254 */       GL11.glPushMatrix();
/* 255 */       GL11.glTranslated(xx, yy, 100.0D);
/*     */       
/* 257 */       GL11.glScalef(((Float)this.fociScale.get(key)).floatValue(), ((Float)this.fociScale.get(key)).floatValue(), ((Float)this.fociScale.get(key)).floatValue());
/* 258 */       GL11.glEnable(32826);
/* 259 */       ItemStack item = ((ItemStack)this.fociItem.get(key)).copy();
/* 260 */       UtilsFX.renderItemInGUI(-8, -8, 100, item);
/* 261 */       GL11.glDisable(32826);
/* 262 */       GL11.glPopMatrix();
/*     */       
/* 264 */       if ((!KeyHandler.radialLock) && (KeyHandler.radialActive)) {
/* 265 */         int mx = (int)(i - sw / 2.0D - xx);
/* 266 */         int my = (int)(j - sh / 2.0D - yy);
/*     */         
/* 268 */         if ((mx >= -10) && (mx <= 10) && (my >= -10) && (my <= 10)) {
/* 269 */           this.fociHover.put(key, Boolean.valueOf(true));
/*     */           
/* 271 */           tt = (ItemStack)this.fociItem.get(key);
/*     */           
/* 273 */           if (k == 0) {
/* 274 */             KeyHandler.radialActive = false;
/* 275 */             KeyHandler.radialLock = true;
/* 276 */             PacketHandler.INSTANCE.sendToServer(new PacketFocusChangeToServer(mc.thePlayer, key));
/* 277 */             break;
/*     */           }
/*     */         } else {
/* 280 */           this.fociHover.put(key, Boolean.valueOf(false));
/*     */         }
/*     */       }
/*     */       
/* 284 */       key = (String)this.foci.higherKey(key);
/*     */     }
/*     */     
/* 287 */     GL11.glPopMatrix();
/*     */     
/* 289 */     if (tt != null) {
/* 290 */       UtilsFX.drawCustomTooltip(mc.currentScreen, mc.fontRendererObj, tt.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips), -4, 20, 11);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 301 */     GL11.glDepthMask(true);
/* 302 */     GL11.glEnable(2929);
/* 303 */     GL11.glDisable(3042);
/* 304 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*     */ 
/* 307 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */ 
/* 311 */   int lastArcHash = 0;
/* 312 */   ArrayList<BlockPos> architectBlocks = new ArrayList();
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean handleArchitectOverlay(ItemStack stack, EntityPlayer player, float partialTicks, int playerticks, MovingObjectPosition target) {
/* 316 */     if (target == null) return false;
/* 317 */     Minecraft mc = Minecraft.getMinecraft();
/* 318 */     IArchitect af = (IArchitect)stack.getItem();
/* 319 */     String h = target.getBlockPos().getX() + "" + target.getBlockPos().getY() + "" + target.getBlockPos().getZ() + "" + target.sideHit + "" + playerticks / 5;
/*     */     
/* 321 */     int hc = h.hashCode();
/* 322 */     if (hc != this.lastArcHash)
/*     */     {
/* 324 */       this.lastArcHash = hc;
/* 325 */       this.bmCache.clear();
/* 326 */       this.architectBlocks = af.getArchitectBlocks(stack, mc.theWorld, target.getBlockPos(), target.sideHit, player);
/*     */     }
/*     */     
/* 329 */     if ((this.architectBlocks == null) || (this.architectBlocks.size() == 0)) { return false;
/*     */     }
/* 331 */     drawArchitectAxis(target.getBlockPos(), partialTicks, af.showAxis(stack, mc.theWorld, player, target.sideHit, IArchitect.EnumAxis.X), af.showAxis(stack, mc.theWorld, player, target.sideHit, IArchitect.EnumAxis.Y), af.showAxis(stack, mc.theWorld, player, target.sideHit, IArchitect.EnumAxis.Z));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 336 */     for (BlockPos cc : this.architectBlocks) {
/* 337 */       drawOverlayBlock(cc, playerticks, mc, partialTicks);
/*     */     }
/*     */     
/* 340 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 341 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isConnectedBlock(World world, BlockPos pos) {
/* 345 */     if (this.architectBlocks.contains(pos)) {
/* 346 */       return true;
/*     */     }
/* 348 */     return false;
/*     */   }
/*     */   
/* 351 */   HashMap<BlockPos, boolean[]> bmCache = new HashMap();
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private boolean[] getConnectedSides(World world, BlockPos pos) {
/* 355 */     if (this.bmCache.containsKey(pos)) return (boolean[])this.bmCache.get(pos);
/* 356 */     boolean[] bitMatrix = new boolean[8];
/*     */     
/* 358 */     bitMatrix[0] = ((!isConnectedBlock(world, pos.add(-1, 0, 0))) && (!isConnectedBlock(world, pos.add(0, 0, -1))) && (!isConnectedBlock(world, pos.add(0, 1, 0))) ? 1 : false);
/*     */     
/*     */ 
/* 361 */     bitMatrix[1] = ((!isConnectedBlock(world, pos.add(1, 0, 0))) && (!isConnectedBlock(world, pos.add(0, 0, -1))) && (!isConnectedBlock(world, pos.add(0, 1, 0))) ? 1 : false);
/*     */     
/*     */ 
/* 364 */     bitMatrix[2] = ((!isConnectedBlock(world, pos.add(-1, 0, 0))) && (!isConnectedBlock(world, pos.add(0, 0, 1))) && (!isConnectedBlock(world, pos.add(0, 1, 0))) ? 1 : false);
/*     */     
/*     */ 
/* 367 */     bitMatrix[3] = ((!isConnectedBlock(world, pos.add(1, 0, 0))) && (!isConnectedBlock(world, pos.add(0, 0, 1))) && (!isConnectedBlock(world, pos.add(0, 1, 0))) ? 1 : false);
/*     */     
/*     */ 
/*     */ 
/* 371 */     bitMatrix[4] = ((!isConnectedBlock(world, pos.add(-1, 0, 0))) && (!isConnectedBlock(world, pos.add(0, 0, -1))) && (!isConnectedBlock(world, pos.add(0, -1, 0))) ? 1 : false);
/*     */     
/*     */ 
/* 374 */     bitMatrix[5] = ((!isConnectedBlock(world, pos.add(1, 0, 0))) && (!isConnectedBlock(world, pos.add(0, 0, -1))) && (!isConnectedBlock(world, pos.add(0, -1, 0))) ? 1 : false);
/*     */     
/*     */ 
/* 377 */     bitMatrix[6] = ((!isConnectedBlock(world, pos.add(-1, 0, 0))) && (!isConnectedBlock(world, pos.add(0, 0, 1))) && (!isConnectedBlock(world, pos.add(0, -1, 0))) ? 1 : false);
/*     */     
/*     */ 
/* 380 */     bitMatrix[7] = ((!isConnectedBlock(world, pos.add(1, 0, 0))) && (!isConnectedBlock(world, pos.add(0, 0, 1))) && (!isConnectedBlock(world, pos.add(0, -1, 0))) ? 1 : false);
/*     */     
/*     */ 
/*     */ 
/* 384 */     this.bmCache.put(pos, bitMatrix);
/* 385 */     return bitMatrix;
/*     */   }
/*     */   
/* 388 */   final ResourceLocation CFRAME = new ResourceLocation("thaumcraft", "textures/misc/frame_corner.png");
/* 389 */   final ResourceLocation SFRAME = new ResourceLocation("thaumcraft", "textures/misc/frame_side.png");
/*     */   
/* 391 */   int[][] mos = { { 4, 5, 6, 7 }, { 0, 1, 2, 3 }, { 0, 1, 4, 5 }, { 2, 3, 6, 7 }, { 0, 2, 4, 6 }, { 1, 3, 5, 7 } };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 396 */   int[][] rotmat = { { 0, 90, 270, 180 }, { 270, 180, 0, 90 }, { 180, 90, 270, 0 }, { 0, 270, 90, 180 }, { 270, 180, 0, 90 }, { 180, 270, 90, 0 } };
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void drawOverlayBlock(BlockPos pos, int ticks, Minecraft mc, float partialTicks)
/*     */   {
/* 403 */     boolean[] bitMatrix = getConnectedSides(mc.theWorld, pos);
/*     */     
/* 405 */     GL11.glPushMatrix();
/* 406 */     GlStateManager.blendFunc(770, 771);
/* 407 */     GL11.glAlphaFunc(516, 0.003921569F);
/* 408 */     GL11.glDepthMask(false);
/* 409 */     GL11.glDisable(2929);
/* 410 */     GL11.glDisable(2884);
/*     */     
/* 412 */     EntityPlayer player = (EntityPlayer)mc.getRenderViewEntity();
/* 413 */     double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
/* 414 */     double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
/* 415 */     double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
/*     */     
/* 417 */     GL11.glTranslated(-iPX + pos.getX() + 0.5D, -iPY + pos.getY() + 0.5D, -iPZ + pos.getZ() + 0.5D);
/*     */     
/*     */ 
/*     */ 
/* 421 */     for (EnumFacing face : EnumFacing.values()) {
/* 422 */       if (!isConnectedBlock(mc.theWorld, pos.offset(face))) {
/* 423 */         GL11.glPushMatrix();
/* 424 */         GL11.glRotatef(90.0F, -face.getFrontOffsetY(), face.getFrontOffsetX(), -face.getFrontOffsetZ());
/* 425 */         if (face.getFrontOffsetZ() < 0) {
/* 426 */           GL11.glTranslated(0.0D, 0.0D, -0.5D);
/*     */         } else {
/* 428 */           GL11.glTranslated(0.0D, 0.0D, 0.5D);
/*     */         }
/* 430 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
/*     */         
/* 432 */         GL11.glPushMatrix();
/* 433 */         UtilsFX.renderQuadCentered(this.SFRAME, 1, 1, 0, 1.0F, 1.0F, 1.0F, 1.0F, 200, 1, 0.33F);
/* 434 */         GL11.glPopMatrix();
/*     */         
/* 436 */         for (int a = 0; a < 4; a++) {
/* 437 */           if (bitMatrix[this.mos[face.ordinal()][a]] != 0) {
/* 438 */             GL11.glPushMatrix();
/* 439 */             GL11.glRotatef(this.rotmat[face.ordinal()][a], 0.0F, 0.0F, 1.0F);
/* 440 */             UtilsFX.renderQuadCentered(this.CFRAME, 1, 1, 0, 1.0F, 1.0F, 1.0F, 1.0F, 200, 1, 0.66F);
/* 441 */             GL11.glPopMatrix();
/*     */           }
/*     */         }
/* 444 */         GL11.glPopMatrix();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 450 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 452 */     GL11.glEnable(2884);
/* 453 */     GL11.glEnable(2929);
/* 454 */     GL11.glDepthMask(true);
/* 455 */     GlStateManager.disableBlend();
/* 456 */     GlStateManager.alphaFunc(516, 0.1F);
/* 457 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/* 460 */   ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/misc/architect_arrows.png");
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/* 463 */   public void drawArchitectAxis(BlockPos pos, float partialTicks, boolean dx, boolean dy, boolean dz) { if ((!dx) && (!dy) && (!dz)) return;
/* 464 */     EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
/* 465 */     double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
/* 466 */     double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
/* 467 */     double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
/*     */     
/* 469 */     float r = MathHelper.sin(player.ticksExisted / 4.0F + pos.getX()) * 0.2F + 0.3F;
/* 470 */     float g = MathHelper.sin(player.ticksExisted / 3.0F + pos.getY()) * 0.2F + 0.3F;
/* 471 */     float b = MathHelper.sin(player.ticksExisted / 2.0F + pos.getZ()) * 0.2F + 0.8F;
/* 472 */     GL11.glPushMatrix();
/* 473 */     GL11.glDepthMask(false);
/* 474 */     GL11.glDisable(2929);
/* 475 */     GL11.glDisable(2884);
/* 476 */     GL11.glEnable(3042);
/* 477 */     GL11.glBlendFunc(770, 1);
/*     */     
/* 479 */     GL11.glTranslated(-iPX + pos.getX() + 0.5D, -iPY + pos.getY() + 0.5D, -iPZ + pos.getZ() + 0.5D);
/*     */     
/* 481 */     GL11.glPushMatrix();
/* 482 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.33F);
/* 483 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 484 */     if (dx) {
/* 485 */       GL11.glPushMatrix();
/* 486 */       UtilsFX.renderQuadCentered(this.tex, 1.0F, r, g, b, 200, 1, 1.0F);
/* 487 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 488 */       UtilsFX.renderQuadCentered(this.tex, 1.0F, r, g, b, 200, 1, 1.0F);
/* 489 */       GL11.glPopMatrix();
/*     */     }
/*     */     
/* 492 */     if (dz) {
/* 493 */       GL11.glPushMatrix();
/* 494 */       GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 495 */       UtilsFX.renderQuadCentered(this.tex, 1.0F, r, g, b, 200, 1, 1.0F);
/* 496 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 497 */       UtilsFX.renderQuadCentered(this.tex, 1.0F, r, g, b, 200, 1, 1.0F);
/* 498 */       GL11.glPopMatrix();
/*     */     }
/*     */     
/* 501 */     if (dy) {
/* 502 */       GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 503 */       UtilsFX.renderQuadCentered(this.tex, 1.0F, r, g, b, 200, 1, 1.0F);
/* 504 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 505 */       UtilsFX.renderQuadCentered(this.tex, 1.0F, r, g, b, 200, 1, 1.0F);
/*     */     }
/*     */     
/* 508 */     GL11.glPopMatrix();
/* 509 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 511 */     GL11.glDisable(3042);
/* 512 */     GL11.glEnable(2884);
/* 513 */     GL11.glEnable(2929);
/* 514 */     GL11.glDepthMask(true);
/* 515 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\lib\WandRenderingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */