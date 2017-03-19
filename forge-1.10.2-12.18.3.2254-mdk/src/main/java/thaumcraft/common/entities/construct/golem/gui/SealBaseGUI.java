/*     */ package thaumcraft.common.entities.construct.golem.gui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.api.golems.EnumGolemTrait;
/*     */ import thaumcraft.api.golems.seals.ISeal;
/*     */ import thaumcraft.api.golems.seals.ISealConfigFilter;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles.SealToggle;
/*     */ import thaumcraft.api.golems.seals.ISealEntity;
/*     */ import thaumcraft.api.golems.seals.ISealGui;
/*     */ import thaumcraft.client.gui.plugins.GuiHoverButton;
/*     */ import thaumcraft.client.gui.plugins.GuiPlusMinusButton;
/*     */ import thaumcraft.client.lib.CustomRenderItem;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class SealBaseGUI
/*     */   extends GuiContainer
/*     */ {
/*     */   ISealEntity seal;
/*     */   int middleX;
/*     */   int middleY;
/*  48 */   int category = -1;
/*     */   int[] categories;
/*     */   
/*     */   public SealBaseGUI(InventoryPlayer player, World world, ISealEntity seal)
/*     */   {
/*  53 */     super(new SealBaseContainer(player, world, seal));
/*     */     
/*  55 */     this.seal = seal;
/*  56 */     this.xSize = 176;
/*  57 */     this.ySize = 232;
/*  58 */     this.middleX = (this.xSize / 2);
/*  59 */     this.middleY = ((this.ySize - 72) / 2 - 8);
/*  60 */     if ((seal.getSeal() instanceof ISealGui)) {
/*  61 */       this.categories = ((ISealGui)seal.getSeal()).getGuiCategories();
/*     */     } else {
/*  63 */       this.categories = new int[] { 0, 4 };
/*     */     }
/*     */   }
/*     */   
/*     */   private ModelManager getModelmanager() {
/*     */     try {
/*  69 */       return (ModelManager)ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), new String[] { "modelManager", "field_175617_aL", "aP" });
/*     */     } catch (Exception e) {}
/*  71 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void initGui()
/*     */   {
/*  77 */     super.initGui();
/*  78 */     this.itemRender = new CustomRenderItem();
/*  79 */     setupCategories();
/*     */   }
/*     */   
/*     */   private void setupCategories() {
/*  83 */     this.buttonList.clear();
/*     */     
/*  85 */     int c = 0;
/*  86 */     float slice = 60.0F / this.categories.length;
/*  87 */     float start = -180.0F + (this.categories.length - 1) * slice / 2.0F;
/*  88 */     if (slice > 24.0F) slice = 24.0F;
/*  89 */     if (slice < 12.0F) slice = 12.0F;
/*  90 */     for (int cat : this.categories) {
/*  91 */       if (this.category < 0) this.category = cat;
/*  92 */       if (this.categories.length > 1) {
/*  93 */         int xx = (int)(MathHelper.cos((start - c * slice) / 180.0F * 3.1415927F) * 86.0F);
/*  94 */         int yy = (int)(MathHelper.sin((start - c * slice) / 180.0F * 3.1415927F) * 86.0F);
/*  95 */         this.buttonList.add(new GuiGolemCategoryButton(c, this.guiLeft + this.middleX + xx, this.guiTop + this.middleY + yy, 16, 16, "button.category." + cat, cat, this.category == cat));
/*     */       }
/*     */       
/*  98 */       c++;
/*     */     }
/*     */     
/* 101 */     int xx = (int)(MathHelper.cos((start - c * slice) / 180.0F * 3.1415927F) * 86.0F);
/* 102 */     int yy = (int)(MathHelper.sin((start - c * slice) / 180.0F * 3.1415927F) * 86.0F);
/* 103 */     this.buttonList.add(new GuiGolemRedstoneButton(27, this.guiLeft + this.middleX + xx - 8, this.guiTop + this.middleY + yy - 8, 16, 16, this.seal));
/*     */     
/* 105 */     switch (this.category) {
/*     */     case 0: 
/* 107 */       this.buttonList.add(new GuiPlusMinusButton(80, this.guiLeft + this.middleX - 5 - 14, this.guiTop + this.middleY - 17, 10, 10, true));
/* 108 */       this.buttonList.add(new GuiPlusMinusButton(81, this.guiLeft + this.middleX - 5 + 14, this.guiTop + this.middleY - 17, 10, 10, false));
/*     */       
/* 110 */       this.buttonList.add(new GuiPlusMinusButton(82, this.guiLeft + this.middleX + 18 - 12, this.guiTop + this.middleY + 4, 10, 10, true));
/* 111 */       this.buttonList.add(new GuiPlusMinusButton(83, this.guiLeft + this.middleX + 18 + 11, this.guiTop + this.middleY + 4, 10, 10, false));
/*     */       
/* 113 */       this.buttonList.add(new GuiGolemLockButton(25, this.guiLeft + this.middleX - 32, this.guiTop + this.middleY, 16, 16, this.seal));
/* 114 */       break;
/*     */     case 1: 
/* 116 */       if ((this.seal.getSeal() instanceof ISealConfigFilter)) {
/* 117 */         int s = ((ISealConfigFilter)this.seal.getSeal()).getFilterSize();
/* 118 */         int sy = 16 + (s - 1) / 3 * 12;
/* 119 */         this.buttonList.add(new GuiGolemBWListButton(20, this.guiLeft + this.middleX - 8, this.guiTop + this.middleY + (s - 1) / 3 * 24 - sy + 27, 16, 16, (ISealConfigFilter)this.seal.getSeal()));
/*     */       }
/* 121 */       break;
/*     */     
/*     */     case 2: 
/* 124 */       this.buttonList.add(new GuiPlusMinusButton(90, this.guiLeft + this.middleX - 5 - 14, this.guiTop + this.middleY - 25, 10, 10, true));
/* 125 */       this.buttonList.add(new GuiPlusMinusButton(91, this.guiLeft + this.middleX - 5 + 14, this.guiTop + this.middleY - 25, 10, 10, false));
/* 126 */       this.buttonList.add(new GuiPlusMinusButton(92, this.guiLeft + this.middleX - 5 - 14, this.guiTop + this.middleY, 10, 10, true));
/* 127 */       this.buttonList.add(new GuiPlusMinusButton(93, this.guiLeft + this.middleX - 5 + 14, this.guiTop + this.middleY, 10, 10, false));
/* 128 */       this.buttonList.add(new GuiPlusMinusButton(94, this.guiLeft + this.middleX - 5 - 14, this.guiTop + this.middleY + 25, 10, 10, true));
/* 129 */       this.buttonList.add(new GuiPlusMinusButton(95, this.guiLeft + this.middleX - 5 + 14, this.guiTop + this.middleY + 25, 10, 10, false));
/* 130 */       break;
/*     */     case 3: 
/* 132 */       if ((this.seal.getSeal() instanceof ISealConfigToggles)) {
/* 133 */         ISealConfigToggles cp = (ISealConfigToggles)this.seal.getSeal();
/* 134 */         int s = cp.getToggles().length < 9 ? 6 : cp.getToggles().length < 6 ? 7 : cp.getToggles().length < 4 ? 8 : 5;
/* 135 */         int h = (cp.getToggles().length - 1) * s;
/* 136 */         int w = 12;
/* 137 */         for (ISealConfigToggles.SealToggle prop : cp.getToggles()) {
/* 138 */           int ww = 12 + Math.min(100, this.fontRendererObj.getStringWidth(StatCollector.translateToLocal(prop.getName())));
/* 139 */           ww /= 2;
/* 140 */           if (ww > w) w = ww;
/*     */         }
/* 142 */         int p = 0;
/* 143 */         for (ISealConfigToggles.SealToggle prop : cp.getToggles()) {
/* 144 */           this.buttonList.add(new GuiGolemPropButton(30 + p, this.guiLeft + this.middleX - w, this.guiTop + this.middleY - 5 - h + p * (s * 2), 8, 8, prop.getName(), prop));
/* 145 */           p++;
/*     */         } }
/* 147 */       break;
/*     */     
/*     */     case 4: 
/* 150 */       EnumGolemTrait[] tags = this.seal.getSeal().getRequiredTags();
/* 151 */       if ((tags != null) && (tags.length > 0)) {
/* 152 */         int p = 0;
/* 153 */         for (EnumGolemTrait tag : tags) {
/* 154 */           this.buttonList.add(new GuiHoverButton(this, 500 + p, this.guiLeft + this.middleX + p * 18 - (tags.length - 1) * 9, this.guiTop + this.middleY - 8, 16, 16, tag.getLocalizedName(), tag.getLocalizedDescription(), tag.icon));
/*     */           
/* 156 */           p++;
/*     */         }
/*     */       }
/*     */       
/* 160 */       tags = this.seal.getSeal().getForbiddenTags();
/* 161 */       if ((tags != null) && (tags.length > 0)) {
/* 162 */         int p = 0;
/* 163 */         for (EnumGolemTrait tag : tags) {
/* 164 */           this.buttonList.add(new GuiHoverButton(this, 600 + p, this.guiLeft + this.middleX + p * 18 - (tags.length - 1) * 9, this.guiTop + this.middleY + 24, 16, 16, tag.getLocalizedName(), tag.getLocalizedDescription(), tag.icon));
/*     */           
/* 166 */           p++;
/*     */         }
/*     */       }
/*     */       break;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   protected boolean checkHotbarKeys(int par1)
/*     */   {
/* 176 */     return false;
/*     */   }
/*     */   
/* 179 */   ResourceLocation tex = new ResourceLocation("thaumcraft", "textures/gui/gui_base.png");
/*     */   
/*     */ 
/*     */   protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY)
/*     */   {
/* 184 */     GL11.glEnable(3042);
/* 185 */     GL11.glBlendFunc(770, 771);
/* 186 */     this.mc.renderEngine.bindTexture(this.tex);
/* 187 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 188 */     drawTexturedModalRect(this.guiLeft + this.middleX - 80, this.guiTop + this.middleY - 80, 96, 0, 160, 160);
/* 189 */     drawTexturedModalRect(this.guiLeft, this.guiTop + 143, 0, 167, 176, 89);
/*     */     
/* 191 */     drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("button.category." + this.category), this.guiLeft + this.middleX, this.guiTop + this.middleY - 64, 16777215);
/*     */     
/*     */ 
/* 194 */     GL11.glEnable(3042);
/* 195 */     GL11.glBlendFunc(770, 771);
/* 196 */     this.mc.renderEngine.bindTexture(this.tex);
/* 197 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 199 */     switch (this.category) {
/*     */     case 0: 
/* 201 */       drawTexturedModalRect(this.guiLeft + this.middleX + 17, this.guiTop + this.middleY + 3, 2, 18, 12, 12);
/* 202 */       if ((this.seal.getColor() >= 1) && (this.seal.getColor() <= 16)) {
/* 203 */         Color c = new Color(EnumDyeColor.byMetadata(this.seal.getColor() - 1).getMapColor().colorValue);
/* 204 */         GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, 1.0F);
/* 205 */         drawTexturedModalRect(this.guiLeft + this.middleX + 20, this.guiTop + this.middleY + 6, 74, 31, 6, 6);
/* 206 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       }
/*     */       
/* 209 */       int mx = mouseX - this.guiLeft;
/* 210 */       int my = mouseY - this.guiTop;
/*     */       
/* 212 */       if ((mx >= this.middleX + 5) && (mx <= this.middleX + 41) && (my >= this.middleY + 3) && (my <= this.middleY + 15)) {
/* 213 */         if ((this.seal.getColor() >= 1) && (this.seal.getColor() <= 16)) {
/* 214 */           String s = "color." + EnumDyeColor.byMetadata(this.seal.getColor() - 1).getName();
/* 215 */           String s2 = StatCollector.translateToLocal("golem.prop.color");
/* 216 */           s2 = s2.replace("%s", StatCollector.translateToLocal(s));
/* 217 */           drawCenteredString(this.fontRendererObj, s2, this.guiLeft + this.middleX + 23, this.guiTop + this.middleY + 17, 16777215);
/*     */         } else {
/* 219 */           drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("golem.prop.colorall"), this.guiLeft + this.middleX + 23, this.guiTop + this.middleY + 17, 16777215);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 224 */       drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("golem.prop.priority"), this.guiLeft + this.middleX, this.guiTop + this.middleY - 28, 12299007);
/*     */       
/* 226 */       drawCenteredString(this.fontRendererObj, "" + this.seal.getPriority(), this.guiLeft + this.middleX, this.guiTop + this.middleY - 16, 16777215);
/*     */       
/*     */ 
/* 229 */       if (this.seal.getOwner().equals(this.mc.thePlayer.getUniqueID().toString())) {
/* 230 */         drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("golem.prop.owner"), this.guiLeft + this.middleX, this.guiTop + this.middleY + 32, 12299007);
/*     */       }
/*     */       break;
/*     */     case 1: 
/* 234 */       if ((this.seal.getSeal() instanceof ISealConfigFilter)) {
/* 235 */         int s = ((ISealConfigFilter)this.seal.getSeal()).getFilterSize();
/* 236 */         int sx = 16 + (s - 1) % 3 * 12;
/* 237 */         int sy = 16 + (s - 1) / 3 * 12;
/* 238 */         for (int a = 0; a < s; a++) {
/* 239 */           int x = a % 3;
/* 240 */           int y = a / 3;
/* 241 */           drawTexturedModalRect(this.guiLeft + this.middleX + x * 24 - sx, this.guiTop + this.middleY + y * 24 - sy, 0, 56, 32, 32);
/*     */         } }
/* 243 */       break;
/*     */     
/*     */     case 2: 
/* 246 */       drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("button.caption.y"), this.guiLeft + this.middleX, this.guiTop + this.middleY - 24 - 9, 14540253);
/* 247 */       drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("button.caption.x"), this.guiLeft + this.middleX, this.guiTop + this.middleY - 9, 14540253);
/* 248 */       drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("button.caption.z"), this.guiLeft + this.middleX, this.guiTop + this.middleY + 24 - 9, 14540253);
/*     */       
/* 250 */       drawCenteredString(this.fontRendererObj, "" + this.seal.getArea().getY(), this.guiLeft + this.middleX, this.guiTop + this.middleY - 24, 16777215);
/* 251 */       drawCenteredString(this.fontRendererObj, "" + this.seal.getArea().getX(), this.guiLeft + this.middleX, this.guiTop + this.middleY, 16777215);
/* 252 */       drawCenteredString(this.fontRendererObj, "" + this.seal.getArea().getZ(), this.guiLeft + this.middleX, this.guiTop + this.middleY + 24, 16777215);
/* 253 */       break;
/*     */     case 4: 
/* 255 */       drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("button.caption.required"), this.guiLeft + this.middleX, this.guiTop + this.middleY - 26, 14540253);
/* 256 */       drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("button.caption.forbidden"), this.guiLeft + this.middleX, this.guiTop + this.middleY + 6, 14540253);
/*     */     }
/*     */     
/*     */     
/* 260 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
/*     */   {
/* 266 */     RenderHelper.disableStandardItemLighting();
/* 267 */     Iterator iterator = this.buttonList.iterator();
/* 268 */     while (iterator.hasNext())
/*     */     {
/* 270 */       GuiButton guibutton = (GuiButton)iterator.next();
/* 271 */       if (guibutton.isMouseOver())
/*     */       {
/* 273 */         guibutton.drawButtonForegroundLayer(mouseX - this.guiLeft, mouseY - this.guiTop);
/* 274 */         break;
/*     */       }
/*     */     }
/* 277 */     RenderHelper.enableGUIStandardItemLighting();
/*     */   }
/*     */   
/*     */   protected void actionPerformed(GuiButton button)
/*     */     throws IOException
/*     */   {
/* 283 */     if ((button.id < this.categories.length) && (this.categories[button.id] != this.category)) {
/* 284 */       this.category = this.categories[button.id];
/* 285 */       ((SealBaseContainer)this.inventorySlots).category = this.category;
/* 286 */       ((SealBaseContainer)this.inventorySlots).setupCategories();
/* 287 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, button.id);
/* 288 */       setupCategories();
/*     */ 
/*     */ 
/*     */     }
/* 292 */     else if ((this.category == 0) && (button.id == 25) && (this.seal.getOwner().equals(this.mc.thePlayer.getUniqueID().toString()))) {
/* 293 */       this.seal.setLocked(!this.seal.isLocked());
/* 294 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, this.seal.isLocked() ? 25 : 26);
/*     */ 
/*     */ 
/*     */     }
/* 298 */     else if ((this.category == 1) && ((this.seal.getSeal() instanceof ISealConfigFilter)) && (button.id == 20)) {
/* 299 */       ISealConfigFilter cp = (ISealConfigFilter)this.seal.getSeal();
/* 300 */       cp.setBlacklist(!cp.isBlacklist());
/* 301 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, cp.isBlacklist() ? 20 : 21);
/*     */ 
/*     */ 
/*     */     }
/* 305 */     else if ((this.category == 3) && ((this.seal.getSeal() instanceof ISealConfigToggles)) && (button.id >= 30) && (button.id < 30 + ((ISealConfigToggles)this.seal.getSeal()).getToggles().length))
/*     */     {
/* 307 */       ISealConfigToggles cp = (ISealConfigToggles)this.seal.getSeal();
/* 308 */       cp.setToggle(button.id - 30, !cp.getToggles()[(button.id - 30)].getValue());
/* 309 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, cp.getToggles()[(button.id - 30)].getValue() ? button.id : button.id + 30);
/*     */ 
/*     */ 
/*     */     }
/* 313 */     else if ((button.id == 27) && (this.seal.getOwner().equals(this.mc.thePlayer.getUniqueID().toString()))) {
/* 314 */       this.seal.setRedstoneSensitive(!this.seal.isRedstoneSensitive());
/* 315 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, this.seal.isRedstoneSensitive() ? 27 : 28);
/*     */     }
/*     */     else
/*     */     {
/* 319 */       this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, button.id);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\gui\SealBaseGUI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */