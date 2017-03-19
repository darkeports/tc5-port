/*     */ package thaumcraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.golems.EnumGolemTrait;
/*     */ import thaumcraft.api.golems.IGolemProperties;
/*     */ import thaumcraft.api.golems.parts.GolemAddon;
/*     */ import thaumcraft.api.golems.parts.GolemArm;
/*     */ import thaumcraft.api.golems.parts.GolemHead;
/*     */ import thaumcraft.api.golems.parts.GolemLeg;
/*     */ import thaumcraft.api.golems.parts.GolemMaterial;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.client.gui.plugins.GuiHoverButton;
/*     */ import thaumcraft.client.gui.plugins.GuiScrollButton;
/*     */ import thaumcraft.common.container.ContainerGolemBuilder;
/*     */ import thaumcraft.common.entities.construct.golem.GolemProperties;
/*     */ import thaumcraft.common.entities.construct.golem.gui.GuiGolemCraftButton;
/*     */ import thaumcraft.common.lib.network.misc.PacketStartGolemCraftToServer;
/*     */ import thaumcraft.common.tiles.crafting.TileGolemBuilder;
/*     */ 
/*     */ @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */ public class GuiGolemBuilder extends GuiContainer
/*     */ {
/*     */   private TileGolemBuilder builder;
/*     */   private EntityPlayer player;
/*     */   
/*     */   public GuiGolemBuilder(InventoryPlayer par1InventoryPlayer, TileGolemBuilder table)
/*     */   {
/*  47 */     super(new ContainerGolemBuilder(par1InventoryPlayer, table));
/*  48 */     this.player = par1InventoryPlayer.player;
/*  49 */     this.builder = table;
/*  50 */     this.xSize = 208;
/*  51 */     this.ySize = 224;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void initGui()
/*     */   {
/*  58 */     super.initGui();
/*     */     
/*     */ 
/*  61 */     this.valHeads.clear();
/*  62 */     for (GolemHead head : GolemHead.getHeads()) {
/*  63 */       if (ResearchHelper.isResearchComplete(this.player.getName(), head.research)) {
/*  64 */         this.valHeads.add(head);
/*     */       }
/*     */     }
/*  67 */     this.valMats.clear();
/*  68 */     for (GolemMaterial mat : GolemMaterial.getMaterials()) {
/*  69 */       if (ResearchHelper.isResearchComplete(this.player.getName(), mat.research)) {
/*  70 */         this.valMats.add(mat);
/*     */       }
/*     */     }
/*  73 */     this.valArms.clear();
/*  74 */     for (GolemArm arm : GolemArm.getArms()) {
/*  75 */       if (ResearchHelper.isResearchComplete(this.player.getName(), arm.research)) {
/*  76 */         this.valArms.add(arm);
/*     */       }
/*     */     }
/*  79 */     this.valLegs.clear();
/*  80 */     for (GolemLeg leg : GolemLeg.getLegs()) {
/*  81 */       if (ResearchHelper.isResearchComplete(this.player.getName(), leg.research)) {
/*  82 */         this.valLegs.add(leg);
/*     */       }
/*     */     }
/*  85 */     this.valAddons.clear();
/*  86 */     for (GolemAddon addon : GolemAddon.getAddons()) {
/*  87 */       if (ResearchHelper.isResearchComplete(this.player.getName(), addon.research)) {
/*  88 */         this.valAddons.add(addon);
/*     */       }
/*     */     }
/*  91 */     if (headIndex >= this.valHeads.size()) headIndex = 0;
/*  92 */     if (matIndex >= this.valMats.size()) matIndex = 0;
/*  93 */     if (armIndex >= this.valArms.size()) armIndex = 0;
/*  94 */     if (legIndex >= this.valLegs.size()) legIndex = 0;
/*  95 */     if (addonIndex >= this.valAddons.size()) { addonIndex = 0;
/*     */     }
/*  97 */     gatherInfo();
/*     */   }
/*     */   
/* 100 */   ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_golembuilder.png");
/*     */   
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/*     */   {
/* 105 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 106 */     this.mc.renderEngine.bindTexture(this.tex);
/*     */     
/* 108 */     GL11.glEnable(3042);
/* 109 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 111 */     drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
/*     */     
/* 113 */     if ((this.components != null) && (this.components.length > 0))
/*     */     {
/* 115 */       int i = 1;
/* 116 */       int q = 0;
/* 117 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
/* 118 */       for (int a = 0; a < this.components.length; a++) {
/* 119 */         if (this.owns[a] == 0) {
/* 120 */           drawTexturedModalRect(this.guiLeft + 144 + q * 16, this.guiTop + 16 + 16 * i, 240, 0, 16, 16);
/*     */         }
/* 122 */         i++;
/* 123 */         if (i > 3) {
/* 124 */           i = 0;
/* 125 */           q++;
/*     */         }
/*     */       }
/* 128 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */     
/* 131 */     if (this.builder.cost > 0) {
/* 132 */       drawTexturedModalRect(this.guiLeft + 145, this.guiTop + 89, 209, 89, (int)(46.0F * (1.0F - this.builder.cost / this.builder.maxCost)), 6);
/* 133 */       if (!this.disableAll) {
/* 134 */         this.disableAll = true;
/* 135 */         redoComps();
/*     */       }
/*     */     }
/* 138 */     else if (this.disableAll) {
/* 139 */       this.disableAll = false;
/* 140 */       redoComps();
/*     */     }
/*     */     
/*     */ 
/* 144 */     drawCenteredString(this.fontRendererObj, "" + this.hearts, this.guiLeft + 48, this.guiTop + 108, 16777215);
/* 145 */     drawCenteredString(this.fontRendererObj, "" + this.armor, this.guiLeft + 72, this.guiTop + 108, 16777215);
/* 146 */     drawCenteredString(this.fontRendererObj, "" + this.damage, this.guiLeft + 97, this.guiTop + 108, 16777215);
/*     */   }
/*     */   
/*     */ 
/* 150 */   ArrayList<GolemHead> valHeads = new ArrayList();
/* 151 */   ArrayList<GolemMaterial> valMats = new ArrayList();
/* 152 */   ArrayList<GolemArm> valArms = new ArrayList();
/* 153 */   ArrayList<GolemLeg> valLegs = new ArrayList();
/* 154 */   ArrayList<GolemAddon> valAddons = new ArrayList();
/*     */   
/* 156 */   static int headIndex = 0;
/* 157 */   static int matIndex = 0;
/* 158 */   static int armIndex = 0;
/* 159 */   static int legIndex = 0;
/* 160 */   static int addonIndex = 0;
/*     */   
/*     */ 
/* 163 */   IGolemProperties props = GolemProperties.fromLong(0L);
/* 164 */   float hearts = 0.0F;
/* 165 */   float armor = 0.0F;
/* 166 */   float damage = 0.0F;
/*     */   
/* 168 */   GuiGolemCraftButton craftButton = null;
/*     */   
/* 170 */   ResourceLocation matIcon = new ResourceLocation("Thaumcraft", "textures/items/golem.png");
/*     */   
/*     */   private void gatherInfo() {
/* 173 */     this.buttonList.clear();
/*     */     
/* 175 */     this.craftButton = new GuiGolemCraftButton(99, this.guiLeft + 120, this.guiTop + 104);
/* 176 */     this.buttonList.add(this.craftButton);
/*     */     
/*     */ 
/* 179 */     if (this.valHeads.size() > 1) {
/* 180 */       this.buttonList.add(new GuiScrollButton(0, this.guiLeft + 112 - 5 - 6, this.guiTop - 5 + 16 + 8, 10, 10, true));
/* 181 */       this.buttonList.add(new GuiScrollButton(1, this.guiLeft + 112 - 5 + 22, this.guiTop - 5 + 16 + 8, 10, 10, false));
/*     */     }
/*     */     
/* 184 */     if (this.valMats.size() > 1) {
/* 185 */       this.buttonList.add(new GuiScrollButton(2, this.guiLeft + 16 - 5 - 6, this.guiTop - 5 + 16 + 8, 10, 10, true));
/* 186 */       this.buttonList.add(new GuiScrollButton(3, this.guiLeft + 16 - 5 + 22, this.guiTop - 5 + 16 + 8, 10, 10, false));
/*     */     }
/*     */     
/* 189 */     if (this.valArms.size() > 1) {
/* 190 */       this.buttonList.add(new GuiScrollButton(4, this.guiLeft + 112 - 5 - 6, this.guiTop - 5 + 40 + 8, 10, 10, true));
/* 191 */       this.buttonList.add(new GuiScrollButton(5, this.guiLeft + 112 - 5 + 22, this.guiTop - 5 + 40 + 8, 10, 10, false));
/*     */     }
/*     */     
/* 194 */     if (this.valLegs.size() > 1) {
/* 195 */       this.buttonList.add(new GuiScrollButton(6, this.guiLeft + 112 - 5 - 6, this.guiTop - 5 + 64 + 8, 10, 10, true));
/* 196 */       this.buttonList.add(new GuiScrollButton(7, this.guiLeft + 112 - 5 + 22, this.guiTop - 5 + 64 + 8, 10, 10, false));
/*     */     }
/*     */     
/* 199 */     if (this.valAddons.size() > 1) {
/* 200 */       this.buttonList.add(new GuiScrollButton(8, this.guiLeft + 16 - 5 - 6, this.guiTop - 5 + 64 + 8, 10, 10, true));
/* 201 */       this.buttonList.add(new GuiScrollButton(9, this.guiLeft + 16 - 5 + 22, this.guiTop - 5 + 64 + 8, 10, 10, false));
/*     */     }
/*     */     
/* 204 */     if (this.valHeads.size() > 0) {
/* 205 */       this.buttonList.add(new GuiHoverButton(this, 100, this.guiLeft + 120, this.guiTop + 24, 16, 16, ((GolemHead)this.valHeads.get(headIndex)).getLocalizedName(), ((GolemHead)this.valHeads.get(headIndex)).getLocalizedDescription(), ((GolemHead)this.valHeads.get(headIndex)).icon));
/*     */     }
/*     */     
/*     */ 
/* 209 */     if (this.valMats.size() > 0) {
/* 210 */       this.buttonList.add(new GuiHoverButton(this, 101, this.guiLeft + 24, this.guiTop + 24, 16, 16, ((GolemMaterial)this.valMats.get(matIndex)).getLocalizedName(), ((GolemMaterial)this.valMats.get(matIndex)).getLocalizedDescription(), this.matIcon, ((GolemMaterial)this.valMats.get(matIndex)).itemColor));
/*     */     }
/*     */     
/*     */ 
/* 214 */     if (this.valArms.size() > 0) {
/* 215 */       this.buttonList.add(new GuiHoverButton(this, 102, this.guiLeft + 120, this.guiTop + 48, 16, 16, ((GolemArm)this.valArms.get(armIndex)).getLocalizedName(), ((GolemArm)this.valArms.get(armIndex)).getLocalizedDescription(), ((GolemArm)this.valArms.get(armIndex)).icon));
/*     */     }
/*     */     
/*     */ 
/* 219 */     if (this.valLegs.size() > 0) {
/* 220 */       this.buttonList.add(new GuiHoverButton(this, 103, this.guiLeft + 120, this.guiTop + 72, 16, 16, ((GolemLeg)this.valLegs.get(legIndex)).getLocalizedName(), ((GolemLeg)this.valLegs.get(legIndex)).getLocalizedDescription(), ((GolemLeg)this.valLegs.get(legIndex)).icon));
/*     */     }
/*     */     
/*     */ 
/* 224 */     if ((this.valAddons.size() > 0) && (!((GolemAddon)this.valAddons.get(addonIndex)).key.equalsIgnoreCase("none"))) {
/* 225 */       this.buttonList.add(new GuiHoverButton(this, 103, this.guiLeft + 24, this.guiTop + 72, 16, 16, ((GolemAddon)this.valAddons.get(addonIndex)).getLocalizedName(), ((GolemAddon)this.valAddons.get(addonIndex)).getLocalizedDescription(), ((GolemAddon)this.valAddons.get(addonIndex)).icon));
/*     */     }
/*     */     
/*     */ 
/* 229 */     this.props = GolemProperties.fromLong(0L);
/* 230 */     this.props.setHead((GolemHead)this.valHeads.get(headIndex));
/* 231 */     this.props.setMaterial((GolemMaterial)this.valMats.get(matIndex));
/* 232 */     this.props.setArms((GolemArm)this.valArms.get(armIndex));
/* 233 */     this.props.setLegs((GolemLeg)this.valLegs.get(legIndex));
/* 234 */     this.props.setAddon((GolemAddon)this.valAddons.get(addonIndex));
/*     */     
/* 236 */     redoComps();
/*     */     
/* 238 */     EnumGolemTrait[] tags = (EnumGolemTrait[])this.props.getTraits().toArray(new EnumGolemTrait[0]);
/* 239 */     if ((tags != null) && (tags.length > 0)) {
/* 240 */       int yy = tags.length <= 4 ? (tags.length - 1) % 4 * 8 : 24;
/* 241 */       int xx = (tags.length - 1) / 4 % 4 * 8;
/* 242 */       int i = 0;
/* 243 */       int q = 0;
/* 244 */       int z = 0;
/* 245 */       for (EnumGolemTrait tag : tags) {
/* 246 */         this.buttonList.add(new GuiHoverButton(this, 30 + z, this.guiLeft + 72 + q * 16 - xx, this.guiTop + 48 + 16 * i - yy, 16, 16, tag.getLocalizedName(), tag.getLocalizedDescription(), tag.icon));
/*     */         
/* 248 */         i++;
/* 249 */         if (i > 3) {
/* 250 */           i = 0;
/* 251 */           q++;
/*     */         }
/* 253 */         z++;
/*     */       }
/*     */     }
/*     */     
/* 257 */     int hh = 10 + this.props.getMaterial().healthMod;
/* 258 */     if (this.props.hasTrait(EnumGolemTrait.FRAGILE)) hh = (int)(hh * 0.75D);
/* 259 */     this.hearts = (hh / 2.0F);
/*     */     
/* 261 */     int aa = this.props.getMaterial().armor;
/* 262 */     if (this.props.hasTrait(EnumGolemTrait.ARMORED)) aa = (int)Math.max(aa * 1.5D, aa + 1);
/* 263 */     if (this.props.hasTrait(EnumGolemTrait.FRAGILE)) aa = (int)(aa * 0.75D);
/* 264 */     this.armor = (aa / 2.0F);
/*     */     
/* 266 */     double dd = this.props.hasTrait(EnumGolemTrait.FIGHTER) ? this.props.getMaterial().damage : 0.0D;
/* 267 */     if (this.props.hasTrait(EnumGolemTrait.BRUTAL)) dd = Math.max(dd * 1.5D, dd + 1.0D);
/* 268 */     this.damage = ((float)(dd / 2.0D));
/*     */   }
/*     */   
/* 271 */   int cost = 0;
/* 272 */   boolean allfound = false;
/* 273 */   ItemStack[] components = null;
/* 274 */   boolean[] owns = null;
/*     */   
/*     */   private void redoComps() {
/* 277 */     this.allfound = true;
/* 278 */     this.cost = (this.props.getTraits().size() * 2);
/* 279 */     this.components = this.props.generateComponents();
/* 280 */     if (this.components.length >= 1) {
/* 281 */       this.owns = new boolean[this.components.length];
/* 282 */       for (int a = 0; a < this.components.length; a++) {
/* 283 */         this.cost += this.components[a].stackSize;
/* 284 */         this.owns[a] = thaumcraft.common.lib.utils.InventoryUtils.isPlayerCarryingAmount(this.player, this.components[a], true);
/* 285 */         if (this.owns[a] == 0) { this.allfound = false;
/*     */         }
/*     */       }
/*     */     }
/* 289 */     if ((this.components != null) && (this.components.length > 0)) {
/* 290 */       this.buttonList.add(new GuiHoverButton(this, 10, this.guiLeft + 152, this.guiTop + 24, 16, 16, Aspect.MECHANISM.getName(), Aspect.MECHANISM.getLocalizedDescription(), Aspect.MECHANISM));
/*     */       
/* 292 */       int i = 1;
/* 293 */       int q = 0;
/* 294 */       int z = 0;
/* 295 */       for (ItemStack stack : this.components) {
/* 296 */         this.buttonList.add(new GuiHoverButton(this, 11 + z, this.guiLeft + 152 + q * 16, this.guiTop + 24 + 16 * i, 16, 16, stack.getDisplayName(), null, stack));
/* 297 */         i++;
/* 298 */         if (i > 3) {
/* 299 */           i = 0;
/* 300 */           q++;
/*     */         }
/* 302 */         z++;
/*     */       }
/*     */     }
/*     */     
/* 306 */     if ((this.buttonList != null) && (this.buttonList.size() > 0)) {
/* 307 */       for (Object b : this.buttonList) {
/* 308 */         if ((b instanceof GuiButton)) {
/* 309 */           ((GuiButton)b).enabled = (!this.disableAll);
/* 310 */           if ((!this.disableAll) && (b == this.craftButton)) {
/* 311 */             this.craftButton.enabled = this.allfound;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
/*     */   {
/* 321 */     if ((this.components != null) && (this.components.length > 0)) {
/* 322 */       drawString(this.fontRendererObj, "" + this.cost, 162 - this.fontRendererObj.getStringWidth("" + this.cost), 24, 16777215);
/*     */     }
/*     */     
/* 325 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 326 */     this.mc.renderEngine.bindTexture(this.tex);
/*     */     
/* 328 */     GL11.glEnable(3042);
/* 329 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 331 */     drawTexturedModalRect(12, 12, 228, 124, 24, 24);
/* 332 */     drawTexturedModalRect(12, 60, 228, 124, 24, 24);
/* 333 */     drawTexturedModalRect(108, 12, 228, 124, 24, 24);
/* 334 */     drawTexturedModalRect(108, 36, 228, 124, 24, 24);
/* 335 */     drawTexturedModalRect(108, 60, 228, 124, 24, 24);
/*     */     
/*     */ 
/*     */ 
/* 339 */     Iterator iterator = this.buttonList.iterator();
/* 340 */     while (iterator.hasNext())
/*     */     {
/* 342 */       GuiButton guibutton = (GuiButton)iterator.next();
/* 343 */       if (guibutton.isMouseOver())
/*     */       {
/* 345 */         guibutton.drawButtonForegroundLayer(mouseX - this.guiLeft, mouseY - this.guiTop);
/* 346 */         break;
/*     */       }
/*     */     }
/*     */     
/* 350 */     if (ContainerGolemBuilder.redo) {
/* 351 */       redoComps();
/* 352 */       ContainerGolemBuilder.redo = false;
/*     */     }
/*     */     
/* 355 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void actionPerformed(GuiButton button)
/*     */     throws IOException
/*     */   {
/* 363 */     if (button.id == 0) {
/* 364 */       headIndex -= 1;
/* 365 */       if (headIndex < 0) headIndex = this.valHeads.size() - 1;
/* 366 */       gatherInfo();
/*     */     }
/* 368 */     else if (button.id == 1) {
/* 369 */       headIndex += 1;
/* 370 */       if (headIndex >= this.valHeads.size()) headIndex = 0;
/* 371 */       gatherInfo();
/*     */ 
/*     */ 
/*     */     }
/* 375 */     else if (button.id == 2) {
/* 376 */       matIndex -= 1;
/* 377 */       if (matIndex < 0) matIndex = this.valMats.size() - 1;
/* 378 */       gatherInfo();
/*     */     }
/* 380 */     else if (button.id == 3) {
/* 381 */       matIndex += 1;
/* 382 */       if (matIndex >= this.valMats.size()) matIndex = 0;
/* 383 */       gatherInfo();
/*     */ 
/*     */ 
/*     */     }
/* 387 */     else if (button.id == 4) {
/* 388 */       armIndex -= 1;
/* 389 */       if (armIndex < 0) armIndex = this.valArms.size() - 1;
/* 390 */       gatherInfo();
/*     */     }
/* 392 */     else if (button.id == 5) {
/* 393 */       armIndex += 1;
/* 394 */       if (armIndex >= this.valArms.size()) armIndex = 0;
/* 395 */       gatherInfo();
/*     */ 
/*     */ 
/*     */     }
/* 399 */     else if (button.id == 6) {
/* 400 */       legIndex -= 1;
/* 401 */       if (legIndex < 0) legIndex = this.valLegs.size() - 1;
/* 402 */       gatherInfo();
/*     */     }
/* 404 */     else if (button.id == 7) {
/* 405 */       legIndex += 1;
/* 406 */       if (legIndex >= this.valLegs.size()) legIndex = 0;
/* 407 */       gatherInfo();
/*     */ 
/*     */ 
/*     */     }
/* 411 */     else if (button.id == 8) {
/* 412 */       addonIndex -= 1;
/* 413 */       if (addonIndex < 0) addonIndex = this.valAddons.size() - 1;
/* 414 */       gatherInfo();
/*     */     }
/* 416 */     else if (button.id == 9) {
/* 417 */       addonIndex += 1;
/* 418 */       if (addonIndex >= this.valAddons.size()) addonIndex = 0;
/* 419 */       gatherInfo();
/*     */     }
/* 421 */     else if ((button.id == 99) && (this.allfound)) {
/* 422 */       thaumcraft.common.lib.network.PacketHandler.INSTANCE.sendToServer(new PacketStartGolemCraftToServer(this.player, this.builder.getPos(), this.props.toLong()));
/* 423 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 99);
/* 424 */       this.disableAll = true;
/*     */     }
/*     */   }
/*     */   
/* 428 */   boolean disableAll = false;
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiGolemBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */