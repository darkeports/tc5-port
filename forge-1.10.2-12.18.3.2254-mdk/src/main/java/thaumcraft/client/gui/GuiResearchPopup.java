/*     */ package thaumcraft.client.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import thaumcraft.api.research.ResearchItem;
/*     */ import thaumcraft.client.lib.UtilsFX;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiResearchPopup
/*     */   extends Gui
/*     */ {
/*     */   private Minecraft theGame;
/*     */   private int windowWidth;
/*     */   private int windowHeight;
/*  26 */   private ArrayList<ResearchItem> theResearch = new ArrayList();
/*     */   
/*     */   private long researchTime;
/*  29 */   private static final ResourceLocation texture = new ResourceLocation("textures/gui/achievement/achievement_background.png");
/*     */   
/*     */ 
/*     */   public GuiResearchPopup(Minecraft par1Minecraft)
/*     */   {
/*  34 */     this.theGame = par1Minecraft;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void queueResearchInformation(ResearchItem research)
/*     */   {
/*  42 */     if (this.researchTime == 0L) this.researchTime = Minecraft.getSystemTime();
/*  43 */     this.theResearch.add(research);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void updateResearchWindowScale()
/*     */   {
/*  55 */     GL11.glViewport(0, 0, this.theGame.displayWidth, this.theGame.displayHeight);
/*  56 */     GL11.glMatrixMode(5889);
/*  57 */     GL11.glLoadIdentity();
/*  58 */     GL11.glMatrixMode(5888);
/*  59 */     GL11.glLoadIdentity();
/*  60 */     this.windowWidth = this.theGame.displayWidth;
/*  61 */     this.windowHeight = this.theGame.displayHeight;
/*  62 */     ScaledResolution var1 = new ScaledResolution(Minecraft.getMinecraft());
/*  63 */     this.windowWidth = var1.getScaledWidth();
/*  64 */     this.windowHeight = var1.getScaledHeight();
/*  65 */     GL11.glClear(256);
/*  66 */     GL11.glMatrixMode(5889);
/*  67 */     GL11.glLoadIdentity();
/*  68 */     GL11.glOrtho(0.0D, this.windowWidth, this.windowHeight, 0.0D, 1000.0D, 3000.0D);
/*  69 */     GL11.glMatrixMode(5888);
/*  70 */     GL11.glLoadIdentity();
/*  71 */     GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateResearchWindow()
/*     */   {
/*  79 */     if ((this.theResearch.size() > 0) && (this.researchTime != 0L))
/*     */     {
/*  81 */       double var1 = (Minecraft.getSystemTime() - this.researchTime) / 3000.0D;
/*     */       
/*  83 */       if ((var1 < 0.0D) || (var1 > 1.0D))
/*     */       {
/*  85 */         this.theResearch.remove(0);
/*  86 */         if (this.theResearch.size() > 0) {
/*  87 */           this.researchTime = Minecraft.getSystemTime();
/*     */         } else {
/*  89 */           this.researchTime = 0L;
/*     */         }
/*     */       }
/*     */       else {
/*  93 */         updateResearchWindowScale();
/*  94 */         GL11.glDisable(2929);
/*  95 */         GL11.glDepthMask(false);
/*  96 */         double var3 = var1 * 2.0D;
/*     */         
/*  98 */         if (var3 > 1.0D)
/*     */         {
/* 100 */           var3 = 2.0D - var3;
/*     */         }
/*     */         
/* 103 */         var3 *= 4.0D;
/* 104 */         var3 = 1.0D - var3;
/*     */         
/* 106 */         if (var3 < 0.0D)
/*     */         {
/* 108 */           var3 = 0.0D;
/*     */         }
/*     */         
/* 111 */         var3 *= var3;
/* 112 */         var3 *= var3;
/* 113 */         int var5 = 0;
/* 114 */         int var6 = 0 - (int)(var3 * 36.0D);
/* 115 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 116 */         GL11.glEnable(3553);
/* 117 */         this.theGame.getTextureManager().bindTexture(texture);
/* 118 */         GL11.glDisable(2896);
/* 119 */         drawTexturedModalRect(var5, var6, 96, 202, 160, 32);
/*     */         
/* 121 */         this.theGame.fontRendererObj.drawString("Research Completed!", var5 + 30, var6 + 7, 65280);
/* 122 */         int offset = this.theGame.fontRendererObj.getStringWidth(((ResearchItem)this.theResearch.get(0)).getName());
/* 123 */         if (offset <= 125) {
/* 124 */           this.theGame.fontRendererObj.drawString(((ResearchItem)this.theResearch.get(0)).getName(), var5 + 30, var6 + 18, -1);
/*     */         }
/*     */         else {
/* 127 */           float vv = 125.0F / offset;
/* 128 */           GL11.glPushMatrix();
/* 129 */           GL11.glTranslatef(var5 + 30, var6 + 16 + 2.0F / vv, 0.0F);
/* 130 */           GL11.glScalef(vv, vv, vv);
/* 131 */           this.theGame.fontRendererObj.drawString(((ResearchItem)this.theResearch.get(0)).getName(), 0, 0, -1);
/* 132 */           GL11.glPopMatrix();
/*     */         }
/*     */         
/* 135 */         GL11.glDepthMask(true);
/* 136 */         GL11.glEnable(2929);
/*     */         
/* 138 */         RenderHelper.enableGUIStandardItemLighting();
/* 139 */         GL11.glDisable(2896);
/* 140 */         GL11.glEnable(32826);
/* 141 */         GL11.glEnable(2903);
/* 142 */         GL11.glEnable(2896);
/* 143 */         if (((ResearchItem)this.theResearch.get(0)).icon_item != null) {
/* 144 */           Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(InventoryUtils.cycleItemStack(((ResearchItem)this.theResearch.get(0)).icon_item), var5 + 8, var6 + 8);
/*     */ 
/*     */         }
/* 147 */         else if (((ResearchItem)this.theResearch.get(0)).icon_resource != null) {
/* 148 */           Minecraft.getMinecraft().renderEngine.bindTexture(((ResearchItem)this.theResearch.get(0)).icon_resource[0]);
/* 149 */           UtilsFX.drawTexturedQuadFull(var5 + 8, var6 + 8, this.zLevel);
/*     */         }
/* 151 */         GL11.glDisable(2896);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\gui\GuiResearchPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */