/*      */ package thaumcraft.client.gui;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.gui.GuiButton;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiTextField;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.PlayerCapabilities;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraftforge.fml.client.FMLClientHandler;
/*      */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*      */ import org.apache.commons.lang3.tuple.Pair;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import thaumcraft.api.ThaumcraftApi;
/*      */ import thaumcraft.api.research.ResearchCategories;
/*      */ import thaumcraft.api.research.ResearchCategoryList;
/*      */ import thaumcraft.api.research.ResearchItem;
/*      */ import thaumcraft.api.research.ResearchPage;
/*      */ import thaumcraft.client.lib.UtilsFX;
/*      */ import thaumcraft.common.config.Config;
/*      */ import thaumcraft.common.lib.network.PacketHandler;
/*      */ import thaumcraft.common.lib.network.playerdata.PacketPlayerCompleteToServer;
/*      */ import thaumcraft.common.lib.research.ResearchManager;
/*      */ import thaumcraft.common.lib.utils.InventoryUtils;
/*      */ 
/*      */ @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*      */ public class GuiResearchBrowser extends GuiScreen
/*      */ {
/*      */   private static int guiBoundsLeft;
/*      */   private static int guiBoundsTop;
/*      */   private static int guiBoundsRight;
/*      */   private static int guiBoundsBottom;
/*   60 */   protected int mouseX = 0;
/*   61 */   protected int mouseY = 0;
/*      */   
/*   63 */   protected float screenZoom = 1.0F;
/*      */   
/*      */   protected double curMouseX;
/*      */   
/*      */   protected double curMouseY;
/*      */   
/*      */   protected double guiMapX;
/*      */   
/*      */   protected double guiMapY;
/*      */   
/*      */   protected double tempMapX;
/*      */   protected double tempMapY;
/*   75 */   private int isMouseButtonDown = 0;
/*      */   
/*   77 */   public static double lastX = -9999.0D;
/*   78 */   public static double lastY = -9999.0D;
/*      */   
/*   80 */   GuiResearchBrowser instance = null;
/*      */   private int screenX;
/*      */   private int screenY;
/*   83 */   private int startX = 16;
/*   84 */   private int startY = 16;
/*      */   
/*   86 */   long t = 0L;
/*      */   
/*   88 */   private LinkedList<ResearchItem> research = new LinkedList();
/*   89 */   public static HashMap<String, ArrayList<String>> completedResearch = new HashMap();
/*   90 */   static String selectedCategory = null;
/*      */   
/*   92 */   private ResearchItem currentHighlight = null;
/*   93 */   private String player = "";
/*   94 */   long popuptime = 0L;
/*   95 */   String popupmessage = "";
/*      */   
/*      */   private GuiTextField searchField;
/*   98 */   private static boolean searching = false;
/*      */   
/*      */   public GuiResearchBrowser()
/*      */   {
/*  102 */     this.curMouseX = (this.guiMapX = this.tempMapX = lastX);
/*  103 */     this.curMouseY = (this.guiMapY = this.tempMapY = lastY);
/*  104 */     this.player = Minecraft.getMinecraft().thePlayer.getName();
/*  105 */     this.instance = this;
/*      */   }
/*      */   
/*      */ 
/*      */   public GuiResearchBrowser(double x, double y)
/*      */   {
/*  111 */     this.curMouseX = (this.guiMapX = this.tempMapX = x);
/*  112 */     this.curMouseY = (this.guiMapY = this.tempMapY = y);
/*  113 */     this.player = Minecraft.getMinecraft().thePlayer.getName();
/*  114 */     this.instance = this;
/*      */   }
/*      */   
/*  117 */   private ArrayList<String> categoriesTC = new ArrayList();
/*  118 */   private ArrayList<String> categoriesOther = new ArrayList();
/*  119 */   static int catScrollPos = 0;
/*  120 */   static int catScrollMax = 0;
/*      */   
/*  122 */   public boolean hasScribestuff = false;
/*  123 */   public int addonShift = 0;
/*      */   
/*      */ 
/*      */ 
/*      */   public void initGui()
/*      */   {
/*  129 */     updateResearch();
/*      */   }
/*      */   
/*      */   public void updateResearch()
/*      */   {
/*  134 */     if (this.mc == null) { this.mc = Minecraft.getMinecraft();
/*      */     }
/*  136 */     this.buttonList.clear();
/*      */     
/*  138 */     this.buttonList.add(new GuiSearchButton(2, 1, this.height - 17, 16, 16, I18n.format("tc.search", new Object[0])));
/*      */     
/*  140 */     Keyboard.enableRepeatEvents(true);
/*  141 */     this.searchField = new GuiTextField(0, this.fontRendererObj, 20, 20, 89, this.fontRendererObj.FONT_HEIGHT);
/*  142 */     this.searchField.setMaxStringLength(15);
/*  143 */     this.searchField.setEnableBackgroundDrawing(true);
/*  144 */     this.searchField.setVisible(false);
/*  145 */     this.searchField.setTextColor(16777215);
/*      */     
/*  147 */     if (searching) {
/*  148 */       this.searchField.setVisible(true);
/*  149 */       this.searchField.setCanLoseFocus(false);
/*  150 */       this.searchField.setFocused(true);
/*  151 */       this.searchField.setText("");
/*  152 */       updateSearch();
/*      */     }
/*      */     
/*  155 */     this.screenX = (this.width - 32);
/*  156 */     this.screenY = (this.height - 32);
/*      */     
/*  158 */     this.research.clear();
/*  159 */     this.hasScribestuff = false;
/*  160 */     if (selectedCategory == null) {
/*  161 */       Collection cats = ResearchCategories.researchCategories.keySet();
/*  162 */       selectedCategory = (String)cats.iterator().next();
/*      */     }
/*      */     
/*  165 */     int limit = (int)Math.floor((this.screenY - 28) / 24.0F);
/*  166 */     this.addonShift = 0;
/*  167 */     int count = 0;
/*  168 */     this.categoriesTC.clear();
/*  169 */     this.categoriesOther.clear();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  174 */     for (String rcl : ResearchCategories.researchCategories.keySet()) {
/*  175 */       int rt = 0;
/*  176 */       int rco = 0;
/*  177 */       Collection col = ResearchCategories.getResearchList(rcl).research.values();
/*  178 */       for (Object res : col)
/*      */       {
/*  180 */         if (!((ResearchItem)res).isAutoUnlock()) {
/*  181 */           rt++;
/*  182 */           if (ResearchManager.isResearchComplete(this.player, ((ResearchItem)res).key)) rco++;
/*      */         } }
/*  184 */       int v = (int)(rco / rt * 100.0F);
/*      */       
/*  186 */       ResearchCategoryList rc = ResearchCategories.getResearchList(rcl);
/*  187 */       if ((rc.researchKey == null) || (ResearchManager.isResearchComplete(this.player, rc.researchKey))) {
/*  188 */         String[] arr$ = thaumcraft.common.config.ConfigResearch.TCCategories;int len$ = arr$.length; for (int i$ = 0;; i$++) { if (i$ >= len$) break label579; String tcc = arr$[i$];
/*  189 */           if (tcc.equals(rcl)) {
/*  190 */             this.categoriesTC.add(rcl);
/*  191 */             this.buttonList.add(new GuiCategoryButton(rc, rcl, false, 20 + this.categoriesTC.size(), 1, 10 + this.categoriesTC.size() * 24, 16, 16, I18n.format("tc.research_category." + rcl, new Object[0]), v));
/*      */             
/*      */ 
/*  194 */             break;
/*      */           }
/*      */         }
/*  197 */         count++;
/*  198 */         if ((count <= limit + catScrollPos) && (count - 1 >= catScrollPos))
/*      */         {
/*      */ 
/*  201 */           this.categoriesOther.add(rcl);
/*  202 */           this.buttonList.add(new GuiCategoryButton(rc, rcl, true, 50 + this.categoriesOther.size(), this.width - 17, 10 + this.categoriesOther.size() * 24, 16, 16, I18n.format("tc.research_category." + rcl, new Object[0]), v));
/*      */         }
/*      */       }
/*      */     }
/*      */     label579:
/*  207 */     if ((count > limit) || (count < catScrollPos)) {
/*  208 */       this.addonShift = ((this.screenY - 28) % 24 / 2);
/*  209 */       this.buttonList.add(new GuiScrollButton(false, 3, this.width - 14, 20, 10, 11, ""));
/*  210 */       this.buttonList.add(new GuiScrollButton(true, 4, this.width - 14, this.screenY + 1, 10, 11, ""));
/*      */     }
/*      */     
/*  213 */     catScrollMax = count - limit;
/*      */     
/*  215 */     if ((ResearchManager.consumeInkFromPlayer(this.mc.thePlayer, false)) && (InventoryUtils.isPlayerCarrying(this.mc.thePlayer, new ItemStack(net.minecraft.init.Items.paper)) >= 0))
/*      */     {
/*  217 */       this.hasScribestuff = true;
/*      */     }
/*      */     
/*  220 */     if (completedResearch.get(this.player) == null) { completedResearch.put(this.player, new ArrayList());
/*      */     }
/*  222 */     if ((selectedCategory == null) || (selectedCategory.equals(""))) { return;
/*      */     }
/*      */     
/*      */ 
/*  226 */     Collection col = ResearchCategories.getResearchList(selectedCategory).research.values();
/*  227 */     for (Object res : col)
/*      */     {
/*  229 */       this.research.add((ResearchItem)res);
/*      */     }
/*      */     
/*      */ 
/*  233 */     guiBoundsLeft = 99999;
/*  234 */     guiBoundsTop = 99999;
/*  235 */     guiBoundsRight = -99999;
/*  236 */     guiBoundsBottom = -99999;
/*      */     
/*  238 */     for (ResearchItem res : this.research)
/*      */     {
/*  240 */       if ((res != null) && (!res.isVirtual()) && (isVisible(res)))
/*      */       {
/*  242 */         if (res.displayColumn * 24 - this.screenX + 48 < guiBoundsLeft) guiBoundsLeft = res.displayColumn * 24 - this.screenX + 48;
/*  243 */         if (res.displayColumn * 24 - 24 > guiBoundsRight) guiBoundsRight = res.displayColumn * 24 - 24;
/*  244 */         if (res.displayRow * 24 - this.screenY + 48 < guiBoundsTop) guiBoundsTop = res.displayRow * 24 - this.screenY + 48;
/*  245 */         if (res.displayRow * 24 - 24 > guiBoundsBottom) guiBoundsBottom = res.displayRow * 24 - 24;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*  250 */   private ArrayList<String> invisible = new ArrayList();
/*      */   
/*      */   private boolean isVisible(ResearchItem res) {
/*  253 */     if (((ArrayList)completedResearch.get(this.player)).contains(res.key)) { return true;
/*      */     }
/*  255 */     if ((this.invisible.contains(res.key)) || ((res.isHidden()) && (!canUnlockResearch(res)))) {
/*  256 */       return false;
/*      */     }
/*  258 */     if ((res.parents == null) && (res.parentsHidden == null) && (res.isHidden())) { return false;
/*      */     }
/*  260 */     if (res.parents != null) {
/*  261 */       for (String r : res.parents) {
/*  262 */         ResearchItem ri = ResearchCategories.getResearch(r);
/*  263 */         if ((ri != null) && (!isVisible(ri))) {
/*  264 */           this.invisible.add(r);
/*  265 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*  269 */     if (res.parentsHidden != null) {
/*  270 */       for (String r : res.parentsHidden) {
/*  271 */         ResearchItem ri = ResearchCategories.getResearch(r);
/*  272 */         if ((ri != null) && (!isVisible(ri))) {
/*  273 */           this.invisible.add(r);
/*  274 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*  278 */     return true;
/*      */   }
/*      */   
/*      */   private boolean canUnlockResearch(ResearchItem res) {
/*  282 */     if ((res.parents != null) && (res.parents.length > 0)) {
/*  283 */       for (String pt : res.parents) {
/*  284 */         if ((pt != null) && (!((ArrayList)completedResearch.get(this.player)).contains(pt)))
/*  285 */           return false;
/*      */       }
/*      */     }
/*  288 */     if ((res.parentsHidden != null) && (res.parentsHidden.length > 0)) {
/*  289 */       for (String pt : res.parentsHidden) {
/*  290 */         if ((pt != null) && (!((ArrayList)completedResearch.get(this.player)).contains(pt)))
/*  291 */           return false;
/*      */       }
/*      */     }
/*  294 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public void onGuiClosed()
/*      */   {
/*  300 */     lastX = this.guiMapX;
/*  301 */     lastY = this.guiMapY;
/*  302 */     Keyboard.enableRepeatEvents(false);
/*  303 */     super.onGuiClosed();
/*      */   }
/*      */   
/*      */ 
/*      */   public void setWorldAndResolution(Minecraft mc, int width, int height)
/*      */   {
/*  309 */     super.setWorldAndResolution(mc, width, height);
/*  310 */     updateResearch();
/*  311 */     if ((lastX == -9999.0D) || (this.guiMapX > guiBoundsRight) || (this.guiMapX < guiBoundsLeft)) this.guiMapX = (this.tempMapX = (guiBoundsLeft + guiBoundsRight) / 2);
/*  312 */     if ((lastY == -9999.0D) || (this.guiMapY > guiBoundsBottom) || (this.guiMapY < guiBoundsTop)) this.guiMapY = (this.tempMapY = (guiBoundsBottom + guiBoundsTop) / 2);
/*      */   }
/*      */   
/*      */   protected void keyTyped(char par1, int par2)
/*      */     throws IOException
/*      */   {
/*  318 */     if ((searching) && (this.searchField.textboxKeyTyped(par1, par2)))
/*      */     {
/*  320 */       updateSearch();
/*      */ 
/*      */     }
/*  323 */     else if (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
/*      */     {
/*  325 */       this.mc.displayGuiScreen((GuiScreen)null);
/*  326 */       this.mc.setIngameFocus();
/*      */     }
/*  328 */     super.keyTyped(par1, par2);
/*      */   }
/*      */   
/*      */   private class SearchResult implements Comparable {
/*      */     String key;
/*      */     int page;
/*      */     
/*      */     private SearchResult(String key, int page) {
/*  336 */       this.key = key;
/*  337 */       this.page = page;
/*      */     }
/*      */     
/*      */     public int compareTo(Object arg0)
/*      */     {
/*  342 */       SearchResult arg = (SearchResult)arg0;
/*  343 */       int k = this.key.compareTo(arg.key);
/*  344 */       return k == 0 ? 1 : this.page < arg.page ? -1 : this.page == arg.page ? 0 : k;
/*      */     }
/*      */   }
/*      */   
/*  348 */   ArrayList<Pair<String, SearchResult>> searchResults = new ArrayList();
/*      */   
/*  350 */   private void updateSearch() { this.searchResults.clear();
/*  351 */     this.invisible.clear();
/*  352 */     String s1 = this.searchField.getText().toLowerCase();
/*      */     
/*  354 */     for (String cat : this.categoriesTC) {
/*  355 */       if (cat.toLowerCase().contains(s1)) {
/*  356 */         this.searchResults.add(Pair.of(I18n.format("tc.research_category." + cat, new Object[0]), new SearchResult(cat, -2, null)));
/*      */       }
/*      */     }
/*      */     
/*  360 */     for (String cat : this.categoriesOther) {
/*  361 */       if (cat.toLowerCase().contains(s1)) {
/*  362 */         this.searchResults.add(Pair.of(I18n.format("tc.research_category." + cat, new Object[0]), new SearchResult(cat, -2, null)));
/*      */       }
/*      */     }
/*      */     
/*  366 */     for (String pre : (ArrayList)completedResearch.get(this.player)) {
/*  367 */       ResearchItem ri = ResearchCategories.getResearch(pre);
/*  368 */       if ((ri != null) && (ri.getName() != null)) {
/*  369 */         if (ri.getName().toLowerCase().contains(s1)) {
/*  370 */           this.searchResults.add(Pair.of(ri.getName(), new SearchResult(pre, -1, null)));
/*      */         }
/*  372 */         int a = 0;
/*  373 */         if (ri.getPages() != null)
/*  374 */           for (ResearchPage page : ri.getPages()) {
/*  375 */             if ((page.recipeOutput != null) && ((page.recipeOutput instanceof ItemStack)) && (((ItemStack)page.recipeOutput).getDisplayName().toLowerCase().contains(s1)))
/*      */             {
/*  377 */               this.searchResults.add(Pair.of(((ItemStack)page.recipeOutput).getDisplayName(), new SearchResult(pre, a, null)));
/*      */             }
/*  379 */             a++;
/*      */           }
/*      */       }
/*      */     }
/*  383 */     java.util.Collections.sort(this.searchResults);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void drawScreen(int mx, int my, float par3)
/*      */   {
/*  390 */     if (!searching) {
/*  391 */       if (Mouse.isButtonDown(0))
/*      */       {
/*  393 */         if (((this.isMouseButtonDown == 0) || (this.isMouseButtonDown == 1)) && (mx >= this.startX) && (mx < this.startX + this.screenX) && (my >= this.startY) && (my < this.startY + this.screenY))
/*      */         {
/*  395 */           if (this.isMouseButtonDown == 0)
/*      */           {
/*  397 */             this.isMouseButtonDown = 1;
/*      */           }
/*      */           else
/*      */           {
/*  401 */             this.guiMapX -= (mx - this.mouseX) * this.screenZoom;
/*  402 */             this.guiMapY -= (my - this.mouseY) * this.screenZoom;
/*  403 */             this.tempMapX = (this.curMouseX = this.guiMapX);
/*  404 */             this.tempMapY = (this.curMouseY = this.guiMapY);
/*      */           }
/*      */           
/*  407 */           this.mouseX = mx;
/*  408 */           this.mouseY = my;
/*      */         }
/*      */         
/*  411 */         if (this.tempMapX < guiBoundsLeft * this.screenZoom)
/*      */         {
/*  413 */           this.tempMapX = (guiBoundsLeft * this.screenZoom);
/*      */         }
/*      */         
/*  416 */         if (this.tempMapY < guiBoundsTop * this.screenZoom)
/*      */         {
/*  418 */           this.tempMapY = (guiBoundsTop * this.screenZoom);
/*      */         }
/*      */         
/*  421 */         if (this.tempMapX >= guiBoundsRight * this.screenZoom)
/*      */         {
/*  423 */           this.tempMapX = (guiBoundsRight * this.screenZoom - 1.0F);
/*      */         }
/*      */         
/*  426 */         if (this.tempMapY >= guiBoundsBottom * this.screenZoom)
/*      */         {
/*  428 */           this.tempMapY = (guiBoundsBottom * this.screenZoom - 1.0F);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  433 */         this.isMouseButtonDown = 0;
/*      */       }
/*      */       
/*      */ 
/*  437 */       int k = Mouse.getDWheel();
/*  438 */       float f4 = this.screenZoom;
/*  439 */       if (k < 0)
/*      */       {
/*  441 */         this.screenZoom += 0.25F;
/*      */       }
/*  443 */       else if (k > 0)
/*      */       {
/*  445 */         this.screenZoom -= 0.25F;
/*      */       }
/*  447 */       this.screenZoom = MathHelper.clamp_float(this.screenZoom, 1.0F, 2.0F);
/*      */     }
/*      */     
/*      */ 
/*  451 */     drawDefaultBackground();
/*      */     
/*  453 */     this.t = (System.nanoTime() / 50000000L);
/*  454 */     int locX = MathHelper.floor_double(this.curMouseX + (this.guiMapX - this.curMouseX) * par3);
/*  455 */     int locY = MathHelper.floor_double(this.curMouseY + (this.guiMapY - this.curMouseY) * par3);
/*      */     
/*  457 */     if (locX < guiBoundsLeft * this.screenZoom) locX = (int)(guiBoundsLeft * this.screenZoom);
/*  458 */     if (locY < guiBoundsTop * this.screenZoom) locY = (int)(guiBoundsTop * this.screenZoom);
/*  459 */     if (locX >= guiBoundsRight * this.screenZoom) locX = (int)(guiBoundsRight * this.screenZoom - 1.0F);
/*  460 */     if (locY >= guiBoundsBottom * this.screenZoom) { locY = (int)(guiBoundsBottom * this.screenZoom - 1.0F);
/*      */     }
/*      */     
/*  463 */     genResearchBackgroundFixedPre(mx, my, par3, locX, locY);
/*  464 */     int q; if (!searching) {
/*  465 */       GL11.glPushMatrix();
/*  466 */       GL11.glScalef(1.0F / this.screenZoom, 1.0F / this.screenZoom, 1.0F);
/*  467 */       genResearchBackgroundZoomable(mx, my, par3, locX, locY);
/*  468 */       GL11.glPopMatrix();
/*      */     } else {
/*  470 */       this.searchField.drawTextBox();
/*  471 */       q = 0;
/*  472 */       for (Pair p : this.searchResults) {
/*  473 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  474 */         SearchResult sr = (SearchResult)p.getRight();
/*  475 */         int color = sr.page == -2 ? 14527146 : sr.page == -1 ? 14540253 : 11184861;
/*      */         
/*  477 */         if ((sr.page != -1) && (sr.page != -2)) {
/*  478 */           this.mc.renderEngine.bindTexture(this.tx1);
/*  479 */           GL11.glPushMatrix();
/*  480 */           GL11.glScaled(0.5D, 0.5D, 0.5D);
/*  481 */           drawTexturedModalRect(44, (32 + q * 10) * 2, 224, 48, 16, 16);
/*  482 */           GL11.glPopMatrix();
/*      */         }
/*      */         
/*      */ 
/*  486 */         if ((mx > 22) && (mx < 18 + this.screenX) && (my >= 32 + q * 10) && (my < 40 + q * 10))
/*  487 */           color = sr.page == -2 ? 16764108 : sr.page == -1 ? 16777215 : 13421823;
/*  488 */         this.fontRendererObj.drawString((String)p.getLeft(), 32, 32 + q * 10, color);
/*  489 */         q++;
/*  490 */         if (32 + (q + 1) * 10 > this.screenY) {
/*  491 */           this.fontRendererObj.drawString(I18n.format("tc.search.more", new Object[0]), 22, 34 + q * 10, 11184810);
/*  492 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  496 */     genResearchBackgroundFixedPost(mx, my, par3, locX, locY);
/*      */     
/*      */ 
/*  499 */     if (this.popuptime > System.currentTimeMillis()) {
/*  500 */       ArrayList<String> text = new ArrayList();
/*  501 */       text.add(this.popupmessage);
/*  502 */       UtilsFX.drawCustomTooltip(this, this.fontRendererObj, text, 10, 34, -99);
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
/*      */   public void updateScreen()
/*      */   {
/*  516 */     this.curMouseX = this.guiMapX;
/*  517 */     this.curMouseY = this.guiMapY;
/*  518 */     double var1 = this.tempMapX - this.guiMapX;
/*  519 */     double var3 = this.tempMapY - this.guiMapY;
/*      */     
/*  521 */     if (var1 * var1 + var3 * var3 < 4.0D)
/*      */     {
/*  523 */       this.guiMapX += var1;
/*  524 */       this.guiMapY += var3;
/*      */     }
/*      */     else
/*      */     {
/*  528 */       this.guiMapX += var1 * 0.85D;
/*  529 */       this.guiMapY += var3 * 0.85D;
/*      */     }
/*      */   }
/*      */   
/*  533 */   ResourceLocation tx1 = new ResourceLocation("thaumcraft", "textures/gui/gui_research_browser.png");
/*      */   
/*      */   private void genResearchBackgroundFixedPre(int par1, int par2, float par3, int locX, int locY) {
/*  536 */     this.zLevel = 0.0F;
/*  537 */     GL11.glDepthFunc(518);
/*  538 */     GL11.glPushMatrix();
/*  539 */     GL11.glTranslatef(0.0F, 0.0F, -200.0F);
/*  540 */     GlStateManager.enableTexture2D();
/*  541 */     GlStateManager.disableLighting();
/*  542 */     GlStateManager.enableRescaleNormal();
/*  543 */     GlStateManager.enableColorMaterial();
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
/*      */   protected void genResearchBackgroundZoomable(int mx, int my, float par3, int locX, int locY)
/*      */   {
/*  556 */     GL11.glPushMatrix();
/*  557 */     GlStateManager.enableBlend();
/*  558 */     GL11.glEnable(3042);
/*  559 */     GlStateManager.blendFunc(770, 771);
/*  560 */     GL11.glAlphaFunc(516, 0.003921569F);
/*  561 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  562 */     Minecraft.getMinecraft().renderEngine.bindTexture(ResearchCategories.getResearchList(selectedCategory).background);
/*  563 */     drawTexturedModalRectWithDoubles((this.startX - 2) * this.screenZoom, (this.startY - 2) * this.screenZoom, locX / 2.0D, locY / 2.0D, (this.screenX + 4) * this.screenZoom, (this.screenY + 4) * this.screenZoom);
/*  564 */     if (ResearchCategories.getResearchList(selectedCategory).background2 != null) {
/*  565 */       Minecraft.getMinecraft().renderEngine.bindTexture(ResearchCategories.getResearchList(selectedCategory).background2);
/*  566 */       drawTexturedModalRectWithDoubles((this.startX - 2) * this.screenZoom, (this.startY - 2) * this.screenZoom, locX / 1.5D, locY / 1.5D, (this.screenX + 4) * this.screenZoom, (this.screenY + 4) * this.screenZoom);
/*      */     }
/*  568 */     GlStateManager.disableBlend();
/*  569 */     GlStateManager.alphaFunc(516, 0.1F);
/*  570 */     GL11.glPopMatrix();
/*      */     
/*  572 */     GL11.glEnable(2929);
/*  573 */     GL11.glDepthFunc(515);
/*      */     
/*  575 */     this.mc.renderEngine.bindTexture(this.tx1);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  580 */     if (completedResearch.get(this.player) != null) {
/*  581 */       for (int var22 = 0; var22 < this.research.size(); var22++)
/*      */       {
/*  583 */         ResearchItem var33 = (ResearchItem)this.research.get(var22);
/*      */         
/*  585 */         if ((var33.parents != null) && (var33.parents.length > 0))
/*      */         {
/*  587 */           for (int a = 0; a < var33.parents.length; a++) {
/*  588 */             if ((var33.parents[a] != null) && (ResearchCategories.getResearch(var33.parents[a]) != null) && (ResearchCategories.getResearch(var33.parents[a]).category.equals(selectedCategory)))
/*      */             {
/*      */ 
/*  591 */               ResearchItem parent = ResearchCategories.getResearch(var33.parents[a]);
/*  592 */               if ((!parent.isVirtual()) && (
/*  593 */                 (parent.siblings == null) || (!Arrays.asList(parent.siblings).contains(var33.key))))
/*      */               {
/*  595 */                 boolean var28 = ((ArrayList)completedResearch.get(this.player)).contains(var33.key);
/*  596 */                 boolean var29 = ((ArrayList)completedResearch.get(this.player)).contains(parent.key);
/*      */                 
/*  598 */                 boolean b = isVisible(var33);
/*      */                 
/*  600 */                 if (var28)
/*      */                 {
/*  602 */                   drawLine(var33.displayColumn, var33.displayRow, parent.displayColumn, parent.displayRow, 0.6F, 0.6F, 0.7F, locX, locY, 5.0F, true, var33.isFlipped());
/*      */                 }
/*  604 */                 else if (b) {
/*  605 */                   if (var29)
/*      */                   {
/*  607 */                     drawLine(var33.displayColumn, var33.displayRow, parent.displayColumn, parent.displayRow, 0.25F, 0.25F, 0.3F, locX, locY, 3.0F, true, var33.isFlipped());
/*      */                   }
/*  609 */                   else if (isVisible(parent))
/*  610 */                     drawLine(var33.displayColumn, var33.displayRow, parent.displayColumn, parent.displayRow, 0.1F, 0.1F, 0.15F, locX, locY, 2.0F, true, var33.isFlipped());
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*  616 */         if ((var33.siblings != null) && (var33.siblings.length > 0))
/*      */         {
/*  618 */           for (int a = 0; a < var33.siblings.length; a++) {
/*  619 */             if ((var33.siblings[a] != null) && (ResearchCategories.getResearch(var33.siblings[a]) != null) && (ResearchCategories.getResearch(var33.siblings[a]).category.equals(selectedCategory)))
/*      */             {
/*  621 */               ResearchItem sibling = ResearchCategories.getResearch(var33.siblings[a]);
/*  622 */               if (!sibling.isVirtual())
/*      */               {
/*  624 */                 boolean var28 = ((ArrayList)completedResearch.get(this.player)).contains(var33.key);
/*  625 */                 boolean var29 = ((ArrayList)completedResearch.get(this.player)).contains(sibling.key);
/*      */                 
/*  627 */                 if (var28)
/*      */                 {
/*  629 */                   drawLine(sibling.displayColumn, sibling.displayRow, var33.displayColumn, var33.displayRow, 0.33F, 0.33F, 0.33F, locX, locY, 4.0F, false, var33.isFlipped());
/*      */                 }
/*  631 */                 else if (isVisible(var33))
/*  632 */                   if (var29)
/*      */                   {
/*  634 */                     drawLine(sibling.displayColumn, sibling.displayRow, var33.displayColumn, var33.displayRow, 0.2F, 0.2F, 0.2F, locX, locY, 1.0F, false, var33.isFlipped());
/*      */                   }
/*  636 */                   else if (isVisible(sibling))
/*  637 */                     drawLine(sibling.displayColumn, sibling.displayRow, var33.displayColumn, var33.displayRow, 0.1F, 0.1F, 0.1F, locX, locY, 0.0F, false, var33.isFlipped());
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  644 */     this.currentHighlight = null;
/*      */     
/*  646 */     GL11.glEnable(32826);
/*  647 */     GL11.glEnable(2903);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  652 */     if (completedResearch.get(this.player) != null)
/*  653 */       for (int var24 = 0; var24 < this.research.size(); var24++)
/*      */       {
/*  655 */         GL11.glEnable(3042);
/*  656 */         GL11.glBlendFunc(770, 771);
/*      */         
/*  658 */         ResearchItem iconResearch = (ResearchItem)this.research.get(var24);
/*  659 */         int var26 = iconResearch.displayColumn * 24 - locX;
/*  660 */         int var27 = iconResearch.displayRow * 24 - locY;
/*      */         
/*  662 */         if ((!iconResearch.isVirtual()) && (var26 >= -24) && (var27 >= -24) && (var26 <= this.screenX * this.screenZoom) && (var27 <= this.screenY * this.screenZoom))
/*      */         {
/*      */ 
/*  665 */           int iconX = this.startX + var26;
/*  666 */           int iconY = this.startY + var27;
/*      */           
/*  668 */           if (((ArrayList)completedResearch.get(this.player)).contains(iconResearch.key))
/*      */           {
/*  670 */             if (ThaumcraftApi.getWarp(iconResearch.key) > 0) {
/*  671 */               drawForbidden(iconX + 8, iconY + 8);
/*      */             }
/*  673 */             float var38 = 1.0F;
/*  674 */             GL11.glColor4f(var38, var38, var38, 1.0F);
/*      */           }
/*      */           else {
/*  677 */             if (!isVisible(iconResearch))
/*      */               continue;
/*  679 */             if (ThaumcraftApi.getWarp(iconResearch.key) > 0) {
/*  680 */               drawForbidden(iconX + 8, iconY + 8);
/*      */             }
/*      */             
/*  683 */             if (canUnlockResearch(iconResearch))
/*      */             {
/*  685 */               float var38 = (float)Math.sin(Minecraft.getSystemTime() % 600L / 600.0D * 3.141592653589793D * 2.0D) * 0.25F + 0.75F;
/*  686 */               GL11.glColor4f(var38, var38, var38, 1.0F);
/*      */             }
/*      */             else
/*      */             {
/*  690 */               float var38 = 0.3F;
/*  691 */               GL11.glColor4f(var38, var38, var38, 1.0F);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  698 */           this.mc.renderEngine.bindTexture(this.tx1);
/*      */           
/*  700 */           GL11.glEnable(2884);
/*  701 */           GL11.glEnable(3042);
/*  702 */           GL11.glBlendFunc(770, 771);
/*      */           
/*  704 */           if (iconResearch.isRound())
/*      */           {
/*  706 */             drawTexturedModalRect(iconX - 8, iconY - 8, 144, 48 + (iconResearch.isHidden() ? 32 : 0), 32, 32);
/*      */           } else {
/*  708 */             int ix = 80;
/*  709 */             int iy = 48;
/*  710 */             if (iconResearch.isHidden())
/*      */             {
/*  712 */               iy += 32;
/*      */             }
/*  714 */             if ((Config.researchDifficulty == -1) || ((Config.researchDifficulty == 0) && (iconResearch.isSecondary()))) {
/*  715 */               ix += 32;
/*      */             }
/*  717 */             drawTexturedModalRect(iconX - 8, iconY - 8, ix, iy, 32, 32);
/*      */           }
/*      */           
/*  720 */           if (iconResearch.isSpecial())
/*      */           {
/*  722 */             drawTexturedModalRect(iconX - 8, iconY - 8, 176, 48 + (iconResearch.isHidden() ? 32 : 0), 32, 32);
/*      */           }
/*      */           
/*  725 */           boolean bw = false;
/*  726 */           if (!canUnlockResearch(iconResearch))
/*      */           {
/*  728 */             float var40 = 0.1F;
/*  729 */             GL11.glColor4f(var40, var40, var40, 1.0F);
/*  730 */             this.itemRender.func_175039_a(false);
/*  731 */             bw = true;
/*      */           }
/*      */           
/*  734 */           if (ResearchManager.hasNewResearchFlag(this.player, iconResearch.key)) {
/*  735 */             GL11.glPushMatrix();
/*  736 */             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  737 */             GL11.glTranslatef(iconX - 9, iconY - 9, 0.0F);
/*  738 */             GL11.glScaled(0.5D, 0.5D, 1.0D);
/*  739 */             drawTexturedModalRect(0, 0, 176, 16, 32, 32);
/*  740 */             GL11.glPopMatrix();
/*      */           }
/*  742 */           if (ResearchManager.hasNewPageFlag(this.player, iconResearch.key)) {
/*  743 */             GL11.glPushMatrix();
/*  744 */             GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  745 */             GL11.glTranslatef(iconX - 9, iconY + 9, 0.0F);
/*  746 */             GL11.glScaled(0.5D, 0.5D, 1.0D);
/*  747 */             drawTexturedModalRect(0, 0, 208, 16, 32, 32);
/*  748 */             GL11.glPopMatrix();
/*      */           }
/*      */           
/*  751 */           if (iconResearch.icon_item != null) {
/*  752 */             GL11.glPushMatrix();
/*  753 */             GL11.glEnable(3042);
/*  754 */             GL11.glBlendFunc(770, 771);
/*  755 */             RenderHelper.enableGUIStandardItemLighting();
/*  756 */             GL11.glDisable(2896);
/*  757 */             GL11.glEnable(32826);
/*  758 */             GL11.glEnable(2903);
/*  759 */             GL11.glEnable(2896);
/*  760 */             this.itemRender.renderItemAndEffectIntoGUI(InventoryUtils.cycleItemStack(iconResearch.icon_item), iconX, iconY);
/*  761 */             GL11.glDisable(2896);
/*  762 */             GL11.glDepthMask(true);
/*  763 */             GL11.glEnable(2929);
/*  764 */             GL11.glDisable(3042);
/*  765 */             GL11.glPopMatrix();
/*      */           }
/*  767 */           else if ((iconResearch.icon_resource != null) && (iconResearch.icon_resource.length > 0)) {
/*  768 */             int idx = (int)(System.currentTimeMillis() / 1000L % iconResearch.icon_resource.length);
/*  769 */             GL11.glPushMatrix();
/*  770 */             GL11.glEnable(3042);
/*  771 */             GL11.glBlendFunc(770, 771);
/*  772 */             this.mc.renderEngine.bindTexture(iconResearch.icon_resource[idx]);
/*  773 */             if (bw) {
/*  774 */               GL11.glColor4f(0.2F, 0.2F, 0.2F, 1.0F);
/*      */             }
/*  776 */             UtilsFX.drawTexturedQuadFull(iconX, iconY, this.zLevel);
/*  777 */             GL11.glPopMatrix();
/*      */           }
/*      */           
/*      */ 
/*  781 */           if (!canUnlockResearch(iconResearch))
/*      */           {
/*  783 */             this.itemRender.func_175039_a(true);
/*  784 */             bw = false;
/*      */           }
/*      */           
/*  787 */           if ((mx >= this.startX) && (my >= this.startY) && (mx < this.startX + this.screenX) && (my < this.startY + this.screenY) && (mx >= (iconX - 2) / this.screenZoom) && (mx <= (iconX + 18) / this.screenZoom) && (my >= (iconY - 2) / this.screenZoom) && (my <= (iconY + 18) / this.screenZoom))
/*      */           {
/*      */ 
/*  790 */             this.currentHighlight = iconResearch;
/*      */           }
/*  792 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */         }
/*      */       }
/*  795 */     GL11.glDisable(2929);
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
/*      */   private void genResearchBackgroundFixedPost(int mx, int my, float par3, int locX, int locY)
/*      */   {
/*  811 */     this.mc.renderEngine.bindTexture(this.tx1);
/*  812 */     GL11.glEnable(3042);
/*  813 */     GL11.glBlendFunc(770, 771);
/*  814 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  815 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/*  817 */     int c = 16;
/*  818 */     while (c < this.width - 16) {
/*  819 */       int p = 64;
/*  820 */       if (c + p > this.width - 16) p = this.width - 16 - c;
/*  821 */       if (p > 0) {
/*  822 */         drawTexturedModalRect(c, -2, 48, 13, p, 22);
/*  823 */         drawTexturedModalRect(c, this.height - 20, 48, 13, p, 22);
/*      */       }
/*  825 */       c += 64;
/*      */     }
/*      */     
/*  828 */     c = 16;
/*  829 */     while (c < this.height - 16) {
/*  830 */       int p = 64;
/*  831 */       if (c + p > this.height - 16) p = this.height - 16 - c;
/*  832 */       if (p > 0) {
/*  833 */         drawTexturedModalRect(-2, c, 13, 48, 22, p);
/*  834 */         drawTexturedModalRect(this.width - 20, c, 13, 48, 22, p);
/*      */       }
/*  836 */       c += 64;
/*      */     }
/*      */     
/*  839 */     drawTexturedModalRect(-2, -2, 13, 13, 22, 22);
/*  840 */     drawTexturedModalRect(-2, this.height - 20, 13, 13, 22, 22);
/*  841 */     drawTexturedModalRect(this.width - 20, -2, 13, 13, 22, 22);
/*  842 */     drawTexturedModalRect(this.width - 20, this.height - 20, 13, 13, 22, 22);
/*      */     
/*  844 */     GL11.glPopMatrix();
/*      */     
/*      */ 
/*  847 */     this.zLevel = 0.0F;
/*  848 */     GL11.glDepthFunc(515);
/*  849 */     GL11.glDisable(2929);
/*  850 */     GL11.glEnable(3553);
/*      */     
/*  852 */     super.drawScreen(mx, my, par3);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  857 */     if ((completedResearch.get(this.player) != null) && 
/*  858 */       (this.currentHighlight != null))
/*      */     {
/*  860 */       ArrayList<String> text = new ArrayList();
/*  861 */       text.add("§6" + this.currentHighlight.getName());
/*  862 */       text.add(this.currentHighlight.getText());
/*      */       
/*  864 */       int warp = ThaumcraftApi.getWarp(this.currentHighlight.key);
/*  865 */       if (warp > 0) {
/*  866 */         if (warp > 5) warp = 5;
/*  867 */         String ws = StatCollector.translateToLocal("tc.forbidden");
/*  868 */         String wr = StatCollector.translateToLocal("tc.forbidden.level." + warp);
/*  869 */         String wte = "@@" + ws.replaceAll("%n", wr);
/*  870 */         text.add(wte);
/*      */       }
/*      */       
/*  873 */       if (canUnlockResearch(this.currentHighlight))
/*      */       {
/*  875 */         boolean secondary = (!((ArrayList)completedResearch.get(this.player)).contains(this.currentHighlight.key)) && ((Config.researchDifficulty == -1) || ((Config.researchDifficulty == 0) && (this.currentHighlight.isSecondary())));
/*      */         
/*      */ 
/*  878 */         boolean primary = (!secondary) && (!((ArrayList)completedResearch.get(this.player)).contains(this.currentHighlight.key));
/*      */         
/*      */ 
/*  881 */         if (!this.currentHighlight.isStub()) {
/*  882 */           if (primary) {
/*  883 */             if (ResearchManager.getResearchSlot(this.mc.thePlayer, this.currentHighlight.key) >= 0) {
/*  884 */               text.add("@@§o§9" + StatCollector.translateToLocal("tc.research.hasnote"));
/*      */             }
/*  886 */             else if (this.hasScribestuff) {
/*  887 */               text.add("@@§o§2" + StatCollector.translateToLocal("tc.research.getprim"));
/*      */             } else {
/*  889 */               text.add("@@§o§c" + StatCollector.translateToLocal("tc.research.shortprim"));
/*      */             }
/*      */           }
/*  892 */           else if (secondary) {
/*  893 */             boolean enough = (this.mc.thePlayer.experienceLevel >= this.currentHighlight.getExperience()) || (this.mc.thePlayer.capabilities.isCreativeMode);
/*      */             
/*  895 */             String t = null;
/*  896 */             if (enough) {
/*  897 */               t = "@@§o§2" + StatCollector.translateToLocal("tc.research.purchase");
/*      */             } else {
/*  899 */               t = "@@§o§c" + StatCollector.translateToLocal("tc.research.short");
/*      */             }
/*  901 */             text.add(new ChatComponentTranslation(t, new Object[] { "" + this.currentHighlight.getExperience() }).getUnformattedText());
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  907 */         text.add("@@§c" + StatCollector.translateToLocal("tc.researchmissing"));
/*      */       }
/*      */       
/*  910 */       if (ResearchManager.hasNewResearchFlag(this.player, this.currentHighlight.key)) {
/*  911 */         text.add("@@" + StatCollector.translateToLocal("tc.research.newresearch"));
/*      */       }
/*  913 */       if (ResearchManager.hasNewPageFlag(this.player, this.currentHighlight.key)) {
/*  914 */         text.add("@@" + StatCollector.translateToLocal("tc.research.newpage"));
/*      */       }
/*  916 */       UtilsFX.drawCustomTooltip(this, this.fontRendererObj, text, mx + 3, my - 3, -99);
/*      */     }
/*      */     
/*  919 */     GlStateManager.enableDepth();
/*  920 */     GlStateManager.enableLighting();
/*  921 */     RenderHelper.disableStandardItemLighting();
/*      */   }
/*      */   
/*      */   protected void mouseClicked(int mx, int my, int par3)
/*      */   {
/*  926 */     this.popuptime = (System.currentTimeMillis() - 1L);
/*  927 */     int q; if ((!searching) && (this.currentHighlight != null) && (!this.currentHighlight.isStub()) && (!((ArrayList)completedResearch.get(this.player)).contains(this.currentHighlight.key)) && (canUnlockResearch(this.currentHighlight)))
/*      */     {
/*  929 */       updateResearch();
/*  930 */       boolean secondary = (Config.researchDifficulty == -1) || ((Config.researchDifficulty == 0) && (this.currentHighlight.isSecondary()));
/*  931 */       if (secondary) {
/*  932 */         if ((this.mc.thePlayer.capabilities.isCreativeMode) || (this.mc.thePlayer.experienceLevel >= this.currentHighlight.getExperience())) {
/*  933 */           PacketHandler.INSTANCE.sendToServer(new PacketPlayerCompleteToServer(this.currentHighlight.key, this.mc.thePlayer.getName(), this.mc.thePlayer.worldObj.provider.getDimensionId(), (byte)0));
/*      */         }
/*      */       }
/*  936 */       else if ((this.hasScribestuff) && (ResearchManager.getResearchSlot(this.mc.thePlayer, this.currentHighlight.key) == -1)) {
/*  937 */         PacketHandler.INSTANCE.sendToServer(new PacketPlayerCompleteToServer(this.currentHighlight.key, this.mc.thePlayer.getName(), this.mc.thePlayer.worldObj.provider.getDimensionId(), (byte)1));
/*      */         
/*  939 */         this.popuptime = (System.currentTimeMillis() + 3000L);
/*  940 */         this.popupmessage = new ChatComponentTranslation(StatCollector.translateToLocal("tc.research.popup"), new Object[] { "" + this.currentHighlight.getName() }).getUnformattedText();
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*  945 */     else if ((this.currentHighlight != null) && (((ArrayList)completedResearch.get(this.player)).contains(this.currentHighlight.key))) {
/*  946 */       ResearchManager.clearNewPageFlag(this.player, this.currentHighlight.key);
/*  947 */       ResearchManager.clearNewResearchFlag(this.player, this.currentHighlight.key);
/*  948 */       PacketHandler.INSTANCE.sendToServer(new thaumcraft.common.lib.network.playerdata.PacketSyncResearchFlags(this.mc.thePlayer, this.currentHighlight.key));
/*  949 */       this.mc.displayGuiScreen(new GuiResearchRecipe(this.currentHighlight, 0, this.guiMapX, this.guiMapY));
/*      */     }
/*  951 */     else if (searching) {
/*  952 */       q = 0;
/*  953 */       for (Pair p : this.searchResults) {
/*  954 */         SearchResult sr = (SearchResult)p.getRight();
/*  955 */         if ((mx > 22) && (mx < 18 + this.screenX) && (my >= 32 + q * 10) && (my < 40 + q * 10)) {
/*  956 */           if ((((ArrayList)completedResearch.get(this.player)).contains(sr.key)) && (sr.page > -2)) {
/*  957 */             this.mc.displayGuiScreen(new GuiResearchRecipe(ResearchCategories.getResearch(sr.key), sr.page, this.guiMapX, this.guiMapY));
/*  958 */             break;
/*      */           }
/*  960 */           if ((this.categoriesTC.contains(sr.key)) || (this.categoriesOther.contains(sr.key))) {
/*  961 */             searching = false;
/*  962 */             this.searchField.setVisible(false);
/*  963 */             this.searchField.setCanLoseFocus(true);
/*  964 */             this.searchField.setFocused(false);
/*  965 */             selectedCategory = sr.key;
/*  966 */             updateResearch();
/*  967 */             this.guiMapX = (this.tempMapX = (guiBoundsLeft + guiBoundsRight) / 2);
/*  968 */             this.guiMapY = (this.tempMapY = (guiBoundsBottom + guiBoundsTop) / 2);
/*  969 */             break;
/*      */           }
/*      */         }
/*  972 */         q++;
/*  973 */         if (32 + (q + 1) * 10 > this.screenY) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/*  980 */       super.mouseClicked(mx, my, par3);
/*      */     }
/*      */     catch (IOException e) {}
/*      */   }
/*      */   
/*      */   protected void actionPerformed(GuiButton button)
/*      */     throws IOException
/*      */   {
/*  988 */     if (button.id == 2) {
/*  989 */       selectedCategory = "";
/*  990 */       searching = true;
/*  991 */       this.searchField.setVisible(true);
/*  992 */       this.searchField.setCanLoseFocus(false);
/*  993 */       this.searchField.setFocused(true);
/*  994 */       this.searchField.setText("");
/*  995 */       updateSearch();
/*      */     }
/*      */     
/*  998 */     if ((button.id == 3) && (catScrollPos > 0)) {
/*  999 */       catScrollPos -= 1;
/* 1000 */       updateResearch();
/*      */     }
/*      */     
/* 1003 */     if ((button.id == 4) && (catScrollPos < catScrollMax)) {
/* 1004 */       catScrollPos += 1;
/* 1005 */       updateResearch();
/*      */     }
/*      */     
/* 1008 */     if ((button.id >= 20) && ((button instanceof GuiCategoryButton)) && (((GuiCategoryButton)button).key != selectedCategory))
/*      */     {
/* 1010 */       searching = false;
/* 1011 */       this.searchField.setVisible(false);
/* 1012 */       this.searchField.setCanLoseFocus(true);
/* 1013 */       this.searchField.setFocused(false);
/*      */       
/* 1015 */       selectedCategory = ((GuiCategoryButton)button).key;
/* 1016 */       updateResearch();
/* 1017 */       this.guiMapX = (this.tempMapX = (guiBoundsLeft + guiBoundsRight) / 2);
/* 1018 */       this.guiMapY = (this.tempMapY = (guiBoundsBottom + guiBoundsTop) / 2);
/*      */     }
/*      */   }
/*      */   
/*      */   private void playButtonClick()
/*      */   {
/* 1024 */     this.mc.getRenderViewEntity().worldObj.playSound(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ, "thaumcraft:cameraclack", 0.4F, 1.0F, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean doesGuiPauseGame()
/*      */   {
/* 1036 */     return false;
/*      */   }
/*      */   
/*      */   private void drawLine(int x, int y, int x2, int y2, float r, float g, float b, int locX, int locY, float zMod, boolean arrow, boolean flipped) {
/* 1040 */     float zt = this.zLevel;
/*      */     
/* 1042 */     this.zLevel += zMod;
/*      */     
/* 1044 */     boolean bigCorner = false;
/*      */     int yy;
/*      */     int xd;
/*      */     int yd;
/* 1048 */     int xm; int ym; int xx; int yy; if (flipped) {
/* 1049 */       int xd = Math.abs(x2 - x);
/* 1050 */       int yd = Math.abs(y2 - y);
/* 1051 */       int xm = x2 - x > 0 ? -1 : xd == 0 ? 0 : 1;
/* 1052 */       int ym = y2 - y > 0 ? -1 : yd == 0 ? 0 : 1;
/* 1053 */       if ((xd > 1) && (yd > 1)) {
/* 1054 */         bigCorner = true;
/*      */       }
/* 1056 */       int xx = x2 * 24 - 4 - locX + this.startX;
/* 1057 */       yy = y2 * 24 - 4 - locY + this.startY;
/*      */     } else {
/* 1059 */       xd = Math.abs(x - x2);
/* 1060 */       yd = Math.abs(y - y2);
/* 1061 */       xm = x - x2 > 0 ? -1 : xd == 0 ? 0 : 1;
/* 1062 */       ym = y - y2 > 0 ? -1 : yd == 0 ? 0 : 1;
/* 1063 */       if ((xd > 1) && (yd > 1)) {
/* 1064 */         bigCorner = true;
/*      */       }
/* 1066 */       xx = x * 24 - 4 - locX + this.startX;
/* 1067 */       yy = y * 24 - 4 - locY + this.startY;
/*      */     }
/*      */     
/* 1070 */     GL11.glPushMatrix();
/* 1071 */     GL11.glAlphaFunc(516, 0.003921569F);
/* 1072 */     GL11.glEnable(3042);
/* 1073 */     GL11.glBlendFunc(770, 771);
/* 1074 */     GL11.glColor4f(r, g, b, 1.0F);
/*      */     
/* 1076 */     if (arrow) {
/* 1077 */       if (flipped) {
/* 1078 */         int xx3 = x * 24 - 8 - locX + this.startX;
/* 1079 */         int yy3 = y * 24 - 8 - locY + this.startY;
/* 1080 */         if (xm < 0) { drawTexturedModalRect(xx3, yy3, 160, 112, 32, 32);
/* 1081 */         } else if (xm > 0) { drawTexturedModalRect(xx3, yy3, 128, 112, 32, 32);
/* 1082 */         } else if (ym > 0) { drawTexturedModalRect(xx3, yy3, 64, 112, 32, 32);
/* 1083 */         } else if (ym < 0) drawTexturedModalRect(xx3, yy3, 96, 112, 32, 32);
/*      */       }
/* 1085 */       else if (ym < 0) { drawTexturedModalRect(xx - 4, yy - 4, 64, 112, 32, 32);
/* 1086 */       } else if (ym > 0) { drawTexturedModalRect(xx - 4, yy - 4, 96, 112, 32, 32);
/* 1087 */       } else if (xm > 0) { drawTexturedModalRect(xx - 4, yy - 4, 160, 112, 32, 32);
/* 1088 */       } else if (xm < 0) { drawTexturedModalRect(xx - 4, yy - 4, 128, 112, 32, 32);
/*      */       }
/*      */     }
/*      */     
/* 1092 */     int v = 1;
/* 1093 */     int h = 0;
/* 1094 */     for (; v < yd - (bigCorner ? 1 : 0); v++) {
/* 1095 */       drawTexturedModalRect(xx + xm * 24 * h, yy + ym * 24 * v, 0, 228, 24, 24);
/*      */     }
/*      */     
/* 1098 */     if (bigCorner) {
/* 1099 */       if ((xm < 0) && (ym > 0)) drawTexturedModalRect(xx + xm * 24 * h - 24, yy + ym * 24 * v, 0, 180, 48, 48);
/* 1100 */       if ((xm > 0) && (ym > 0)) drawTexturedModalRect(xx + xm * 24 * h, yy + ym * 24 * v, 48, 180, 48, 48);
/* 1101 */       if ((xm < 0) && (ym < 0)) drawTexturedModalRect(xx + xm * 24 * h - 24, yy + ym * 24 * v - 24, 96, 180, 48, 48);
/* 1102 */       if ((xm > 0) && (ym < 0)) drawTexturedModalRect(xx + xm * 24 * h, yy + ym * 24 * v - 24, 144, 180, 48, 48);
/*      */     } else {
/* 1104 */       if ((xm < 0) && (ym > 0)) drawTexturedModalRect(xx + xm * 24 * h, yy + ym * 24 * v, 48, 228, 24, 24);
/* 1105 */       if ((xm > 0) && (ym > 0)) drawTexturedModalRect(xx + xm * 24 * h, yy + ym * 24 * v, 72, 228, 24, 24);
/* 1106 */       if ((xm < 0) && (ym < 0)) drawTexturedModalRect(xx + xm * 24 * h, yy + ym * 24 * v, 96, 228, 24, 24);
/* 1107 */       if ((xm > 0) && (ym < 0)) { drawTexturedModalRect(xx + xm * 24 * h, yy + ym * 24 * v, 120, 228, 24, 24);
/*      */       }
/*      */     }
/* 1110 */     v += (bigCorner ? 1 : 0);
/* 1111 */     h += (bigCorner ? 2 : 1);
/* 1112 */     for (; h < xd; h++) {
/* 1113 */       drawTexturedModalRect(xx + xm * 24 * h, yy + ym * 24 * v, 24, 228, 24, 24);
/*      */     }
/* 1115 */     GL11.glBlendFunc(770, 771);
/* 1116 */     GL11.glDisable(3042);
/* 1117 */     GL11.glAlphaFunc(516, 0.1F);
/* 1118 */     GL11.glPopMatrix();
/*      */     
/* 1120 */     this.zLevel = zt;
/*      */   }
/*      */   
/*      */   private void drawForbidden(double x, double y) {
/* 1124 */     int count = FMLClientHandler.instance().getClient().thePlayer.ticksExisted;
/* 1125 */     GL11.glPushMatrix();
/* 1126 */     GL11.glTranslated(x, y, 0.0D);
/* 1127 */     UtilsFX.renderQuadCentered(thaumcraft.client.renderers.entity.RenderAuraNode.texture, 32, 32, 160 + count % 32, 80.0F, 0.33F, 0.0F, 0.44F, 220, 771, 0.9F);
/* 1128 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1129 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public void drawTexturedModalRectWithDoubles(float xCoord, float yCoord, double minU, double minV, double maxU, double maxV)
/*      */   {
/* 1134 */     float f2 = 0.00390625F;
/* 1135 */     float f3 = 0.00390625F;
/* 1136 */     Tessellator tessellator = Tessellator.getInstance();
/* 1137 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1138 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1139 */     worldrenderer.pos(xCoord + 0.0F, yCoord + maxV, this.zLevel).tex((minU + 0.0D) * f2, (minV + maxV) * f3).endVertex();
/* 1140 */     worldrenderer.pos(xCoord + maxU, yCoord + maxV, this.zLevel).tex((minU + maxU) * f2, (minV + maxV) * f3).endVertex();
/* 1141 */     worldrenderer.pos(xCoord + maxU, yCoord + 0.0F, this.zLevel).tex((minU + maxU) * f2, (minV + 0.0D) * f3).endVertex();
/* 1142 */     worldrenderer.pos(xCoord + 0.0F, yCoord + 0.0F, this.zLevel).tex((minU + 0.0D) * f2, (minV + 0.0D) * f3).endVertex();
/* 1143 */     tessellator.draw();
/*      */   }
/*      */   
/*      */   private class GuiCategoryButton extends GuiButton
/*      */   {
/*      */     ResearchCategoryList rc;
/*      */     String key;
/*      */     boolean flip;
/*      */     int completion;
/*      */     
/*      */     public GuiCategoryButton(ResearchCategoryList rc, String key, boolean flip, int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_, int completion) {
/* 1154 */       super(p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
/* 1155 */       this.rc = rc;
/* 1156 */       this.key = key;
/* 1157 */       this.flip = flip;
/* 1158 */       this.completion = completion;
/*      */     }
/*      */     
/*      */     public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
/*      */     {
/* 1163 */       return (this.enabled) && (this.visible) && (mouseX >= this.xPosition) && (mouseY >= this.yPosition + GuiResearchBrowser.this.addonShift) && (mouseX < this.xPosition + this.width) && (mouseY < this.yPosition + this.height + GuiResearchBrowser.this.addonShift);
/*      */     }
/*      */     
/*      */ 
/*      */     public void drawButton(Minecraft mc, int mouseX, int mouseY)
/*      */     {
/* 1169 */       if (this.visible)
/*      */       {
/* 1171 */         this.hovered = ((mouseX >= this.xPosition) && (mouseY >= this.yPosition + GuiResearchBrowser.this.addonShift) && (mouseX < this.xPosition + this.width) && (mouseY < this.yPosition + this.height + GuiResearchBrowser.this.addonShift));
/* 1172 */         GlStateManager.enableBlend();
/* 1173 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1174 */         GlStateManager.blendFunc(770, 771);
/* 1175 */         mc.renderEngine.bindTexture(GuiResearchBrowser.this.tx1);
/* 1176 */         GL11.glPushMatrix();
/* 1177 */         if (!GuiResearchBrowser.selectedCategory.equals(this.key)) {
/* 1178 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */         } else {
/* 1180 */           GL11.glColor4f(0.6F, 1.0F, 1.0F, 1.0F);
/*      */         }
/* 1182 */         drawTexturedModalRect(this.xPosition - 3, this.yPosition - 3 + GuiResearchBrowser.this.addonShift, 13, 13, 22, 22);
/*      */         
/*      */ 
/*      */ 
/* 1186 */         GL11.glPopMatrix();
/* 1187 */         GL11.glPushMatrix();
/* 1188 */         mc.renderEngine.bindTexture(this.rc.icon);
/* 1189 */         if ((GuiResearchBrowser.selectedCategory.equals(this.key)) || (this.hovered)) {
/* 1190 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */         } else {
/* 1192 */           GL11.glColor4f(0.66F, 0.66F, 0.66F, 0.8F);
/*      */         }
/* 1194 */         UtilsFX.drawTexturedQuadFull(this.xPosition, this.yPosition + GuiResearchBrowser.this.addonShift, -80.0D);
/* 1195 */         GL11.glPopMatrix();
/*      */         
/* 1197 */         mc.renderEngine.bindTexture(GuiResearchBrowser.this.tx1);
/*      */         
/* 1199 */         boolean nr = false;
/* 1200 */         boolean np = false;
/*      */         
/* 1202 */         for (String rk : this.rc.research.keySet())
/* 1203 */           if (((ArrayList)GuiResearchBrowser.completedResearch.get(GuiResearchBrowser.this.player)).contains(rk)) {
/* 1204 */             if ((!nr) && (ResearchManager.hasNewResearchFlag(GuiResearchBrowser.this.player, rk))) {
/* 1205 */               nr = true;
/*      */             }
/* 1207 */             if ((!np) && (ResearchManager.hasNewPageFlag(GuiResearchBrowser.this.player, rk))) {
/* 1208 */               np = true;
/*      */             }
/* 1210 */             if ((nr) && (np))
/*      */               break;
/*      */           }
/* 1213 */         if (nr) {
/* 1214 */           GL11.glPushMatrix();
/* 1215 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.7F);
/* 1216 */           GL11.glTranslated(this.xPosition - 2, this.yPosition + GuiResearchBrowser.this.addonShift - 2, 0.0D);
/* 1217 */           GL11.glScaled(0.25D, 0.25D, 1.0D);
/* 1218 */           drawTexturedModalRect(0, 0, 176, 16, 32, 32);
/* 1219 */           GL11.glPopMatrix();
/*      */         }
/* 1221 */         if (np) {
/* 1222 */           GL11.glPushMatrix();
/* 1223 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.7F);
/* 1224 */           GL11.glTranslated(this.xPosition - 2, this.yPosition + GuiResearchBrowser.this.addonShift + 9, 0.0D);
/* 1225 */           GL11.glScaled(0.25D, 0.25D, 1.0D);
/* 1226 */           drawTexturedModalRect(0, 0, 208, 16, 32, 32);
/* 1227 */           GL11.glPopMatrix();
/*      */         }
/*      */         
/* 1230 */         if (this.hovered) {
/* 1231 */           String dp = this.displayString + " (" + this.completion + "%)";
/* 1232 */           drawString(mc.fontRendererObj, dp, !this.flip ? this.xPosition + 22 : GuiResearchBrowser.this.screenX + 9 - mc.fontRendererObj.getStringWidth(dp), this.yPosition + 4 + GuiResearchBrowser.this.addonShift, 16777215);
/*      */           
/*      */ 
/* 1235 */           int t = 9;
/* 1236 */           if (nr) {
/* 1237 */             drawString(mc.fontRendererObj, StatCollector.translateToLocal("tc.research.newresearch"), !this.flip ? this.xPosition + 22 : GuiResearchBrowser.this.screenX + 9 - mc.fontRendererObj.getStringWidth(StatCollector.translateToLocal("tc.research.newresearch")), this.yPosition + 4 + t + GuiResearchBrowser.this.addonShift, 16777215);
/*      */             
/*      */ 
/* 1240 */             t += 9;
/*      */           }
/* 1242 */           if (np) {
/* 1243 */             drawString(mc.fontRendererObj, StatCollector.translateToLocal("tc.research.newpage"), !this.flip ? this.xPosition + 22 : GuiResearchBrowser.this.screenX + 9 - mc.fontRendererObj.getStringWidth(StatCollector.translateToLocal("tc.research.newpage")), this.yPosition + 4 + t + GuiResearchBrowser.this.addonShift, 16777215);
/*      */           }
/*      */         }
/*      */         
/* 1247 */         mouseDragged(mc, mouseX, mouseY);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class GuiScrollButton extends GuiButton
/*      */   {
/*      */     boolean flip;
/*      */     
/*      */     public GuiScrollButton(boolean flip, int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
/* 1257 */       super(p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
/* 1258 */       this.flip = flip;
/*      */     }
/*      */     
/*      */ 
/*      */     public void drawButton(Minecraft mc, int mouseX, int mouseY)
/*      */     {
/* 1264 */       if (this.visible)
/*      */       {
/* 1266 */         this.hovered = ((mouseX >= this.xPosition) && (mouseY >= this.yPosition) && (mouseX < this.xPosition + this.width) && (mouseY < this.yPosition + this.height));
/* 1267 */         GlStateManager.enableBlend();
/* 1268 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1269 */         GlStateManager.blendFunc(770, 771);
/* 1270 */         mc.renderEngine.bindTexture(GuiResearchBrowser.this.tx1);
/* 1271 */         GL11.glPushMatrix();
/* 1272 */         if (this.hovered) {
/* 1273 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */         } else {
/* 1275 */           GL11.glColor4f(0.7F, 0.7F, 0.7F, 1.0F);
/*      */         }
/* 1277 */         drawTexturedModalRect(this.xPosition, this.yPosition, 51, this.flip ? 71 : 55, 10, 11);
/* 1278 */         GL11.glPopMatrix();
/* 1279 */         mouseDragged(mc, mouseX, mouseY);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class GuiSearchButton extends GuiButton
/*      */   {
/*      */     public GuiSearchButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
/* 1287 */       super(p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
/*      */     }
/*      */     
/*      */ 
/*      */     public void drawButton(Minecraft mc, int mouseX, int mouseY)
/*      */     {
/* 1293 */       if (this.visible)
/*      */       {
/* 1295 */         this.hovered = ((mouseX >= this.xPosition) && (mouseY >= this.yPosition) && (mouseX < this.xPosition + this.width) && (mouseY < this.yPosition + this.height));
/* 1296 */         GlStateManager.enableBlend();
/* 1297 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1298 */         GlStateManager.blendFunc(770, 771);
/* 1299 */         mc.renderEngine.bindTexture(GuiResearchBrowser.this.tx1);
/* 1300 */         GL11.glPushMatrix();
/* 1301 */         if (this.hovered) {
/* 1302 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */         } else
/* 1304 */           GL11.glColor4f(0.8F, 0.8F, 0.8F, 1.0F);
/* 1305 */         drawTexturedModalRect(this.xPosition, this.yPosition, 160, 16, 16, 16);
/* 1306 */         GL11.glPopMatrix();
/* 1307 */         if (this.hovered) {
/* 1308 */           drawString(mc.fontRendererObj, this.displayString, this.xPosition + 19, this.yPosition + 4, 16777215);
/*      */         }
/* 1310 */         mouseDragged(mc, mouseX, mouseY);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiResearchBrowser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */