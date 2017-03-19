/*      */ package thaumcraft.client.lib;
/*      */ 
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.text.Bidi;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.Gui;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.IResource;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraftforge.fml.relauncher.Side;
/*      */ import net.minecraftforge.fml.relauncher.SideOnly;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ @SideOnly(Side.CLIENT)
/*      */ public class TCFontRenderer
/*      */ {
/*   30 */   private static final ResourceLocation[] field_111274_c = new ResourceLocation['Ā'];
/*      */   
/*   32 */   public static final ResourceLocation FONT_NORMAL = new ResourceLocation("textures/font/ascii.png");
/*   33 */   public static final ResourceLocation FONT_GALACTIC = new ResourceLocation("textures/font/ascii_sga.png");
/*      */   
/*      */ 
/*   36 */   private int[] charWidth = new int['Ā'];
/*      */   
/*      */ 
/*   39 */   public int FONT_HEIGHT = 9;
/*   40 */   public Random fontRandom = new Random();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   45 */   private byte[] glyphWidth = new byte[65536];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   51 */   private int[] colorCode = new int[32];
/*      */   
/*      */ 
/*      */ 
/*      */   private ResourceLocation field_111273_g;
/*      */   
/*      */ 
/*      */ 
/*      */   private final TextureManager renderEngine;
/*      */   
/*      */ 
/*      */   private float posX;
/*      */   
/*      */ 
/*      */   private float posY;
/*      */   
/*      */ 
/*      */   private boolean unicodeFlag;
/*      */   
/*      */ 
/*      */   private boolean bidiFlag;
/*      */   
/*      */ 
/*      */   private float red;
/*      */   
/*      */ 
/*      */   private float blue;
/*      */   
/*      */ 
/*      */   private float green;
/*      */   
/*      */ 
/*      */   private float alpha;
/*      */   
/*      */ 
/*      */   private int textColor;
/*      */   
/*      */ 
/*   89 */   private boolean randomStyle = false;
/*      */   
/*      */ 
/*   92 */   private boolean boldStyle = false;
/*      */   
/*      */ 
/*   95 */   private boolean italicStyle = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  100 */   private boolean underlineStyle = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  105 */   private boolean strikethroughStyle = false;
/*      */   
/*  107 */   boolean uniflagInit = false;
/*      */   
/*      */ 
/*      */   public TCFontRenderer(GameSettings par1GameSettings, ResourceLocation par2ResourceLocation, TextureManager par3RenderEngine, boolean par4)
/*      */   {
/*  112 */     this.field_111273_g = par2ResourceLocation;
/*  113 */     this.renderEngine = par3RenderEngine;
/*  114 */     this.unicodeFlag = par4;
/*  115 */     this.uniflagInit = par4;
/*  116 */     readFontData();
/*  117 */     par3RenderEngine.bindTexture(this.field_111273_g);
/*      */     
/*  119 */     for (int i = 0; i < 32; i++)
/*      */     {
/*  121 */       int j = (i >> 3 & 0x1) * 85;
/*  122 */       int k = (i >> 2 & 0x1) * 170 + j;
/*  123 */       int l = (i >> 1 & 0x1) * 170 + j;
/*  124 */       int i1 = (i >> 0 & 0x1) * 170 + j;
/*      */       
/*  126 */       if (i == 6)
/*      */       {
/*  128 */         k += 85;
/*      */       }
/*      */       
/*  131 */       if (par1GameSettings.anaglyph)
/*      */       {
/*  133 */         int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
/*  134 */         int k1 = (k * 30 + l * 70) / 100;
/*  135 */         int l1 = (k * 30 + i1 * 70) / 100;
/*  136 */         k = j1;
/*  137 */         l = k1;
/*  138 */         i1 = l1;
/*      */       }
/*      */       
/*  141 */       if (i >= 16)
/*      */       {
/*  143 */         k /= 4;
/*  144 */         l /= 4;
/*  145 */         i1 /= 4;
/*      */       }
/*      */       
/*  148 */       this.colorCode[i] = ((k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF);
/*      */     }
/*      */   }
/*      */   
/*      */   public void readFontData()
/*      */   {
/*  154 */     readGlyphSizes();
/*  155 */     readFontTexture();
/*      */   }
/*      */   
/*      */ 
/*      */   private void readFontTexture()
/*      */   {
/*      */     BufferedImage bufferedimage;
/*      */     try
/*      */     {
/*  164 */       bufferedimage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(this.field_111273_g).getInputStream());
/*      */     }
/*      */     catch (IOException ioexception)
/*      */     {
/*  168 */       throw new RuntimeException(ioexception);
/*      */     }
/*      */     
/*  171 */     int i = bufferedimage.getWidth();
/*  172 */     int j = bufferedimage.getHeight();
/*  173 */     int[] aint = new int[i * j];
/*  174 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/*  175 */     int k = 0;
/*      */     
/*  177 */     while (k < 256)
/*      */     {
/*  179 */       int l = k % 16;
/*  180 */       int i1 = k / 16;
/*  181 */       int j1 = 7;
/*      */       
/*      */ 
/*      */ 
/*  185 */       while (j1 >= 0)
/*      */       {
/*  187 */         int k1 = l * 8 + j1;
/*  188 */         boolean flag = true;
/*      */         
/*  190 */         for (int l1 = 0; (l1 < 8) && (flag); l1++)
/*      */         {
/*  192 */           int i2 = (i1 * 8 + l1) * i;
/*  193 */           int j2 = aint[(k1 + i2)] & 0xFF;
/*      */           
/*  195 */           if (j2 > 0)
/*      */           {
/*  197 */             flag = false;
/*      */           }
/*      */         }
/*      */         
/*  201 */         if (!flag)
/*      */           break;
/*  203 */         j1--;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  208 */       if (k == 32)
/*      */       {
/*  210 */         j1 = 2;
/*      */       }
/*      */       
/*  213 */       this.charWidth[k] = (j1 + 2);
/*  214 */       k++;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void readGlyphSizes()
/*      */   {
/*      */     try
/*      */     {
/*  224 */       InputStream inputstream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/glyph_sizes.bin")).getInputStream();
/*  225 */       inputstream.read(this.glyphWidth);
/*      */     }
/*      */     catch (IOException ioexception)
/*      */     {
/*  229 */       throw new RuntimeException(ioexception);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private float renderCharAtPos(int par1, char par2, boolean par3)
/*      */   {
/*  238 */     return (par1 > 0) && (!this.unicodeFlag) ? renderDefaultChar(par1 + 32, par3) : par2 == ' ' ? 4.0F : renderUnicodeChar(par2, par3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private float renderDefaultChar(int par1, boolean par2)
/*      */   {
/*  246 */     float f = par1 % 16 * 8;
/*  247 */     float f1 = par1 / 16 * 8;
/*  248 */     float f2 = par2 ? 1.0F : 0.0F;
/*  249 */     this.renderEngine.bindTexture(this.field_111273_g);
/*  250 */     float f3 = this.charWidth[par1] - 0.01F;
/*  251 */     GL11.glBegin(5);
/*  252 */     GL11.glTexCoord2f(f / 128.0F, f1 / 128.0F);
/*  253 */     GL11.glVertex3f(this.posX + f2, this.posY, 0.0F);
/*  254 */     GL11.glTexCoord2f(f / 128.0F, (f1 + 7.99F) / 128.0F);
/*  255 */     GL11.glVertex3f(this.posX - f2, this.posY + 7.99F, 0.0F);
/*  256 */     GL11.glTexCoord2f((f + f3) / 128.0F, f1 / 128.0F);
/*  257 */     GL11.glVertex3f(this.posX + f3 + f2, this.posY, 0.0F);
/*  258 */     GL11.glTexCoord2f((f + f3) / 128.0F, (f1 + 7.99F) / 128.0F);
/*  259 */     GL11.glVertex3f(this.posX + f3 - f2, this.posY + 7.99F, 0.0F);
/*  260 */     GL11.glEnd();
/*  261 */     return this.charWidth[par1];
/*      */   }
/*      */   
/*      */   private ResourceLocation func_111271_a(int par1)
/*      */   {
/*  266 */     if (field_111274_c[par1] == null)
/*      */     {
/*  268 */       field_111274_c[par1] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(par1) }));
/*      */     }
/*      */     
/*  271 */     return field_111274_c[par1];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void loadGlyphTexture(int par1)
/*      */   {
/*  279 */     this.renderEngine.bindTexture(func_111271_a(par1));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private float renderUnicodeChar(char par1, boolean par2)
/*      */   {
/*  287 */     if (this.glyphWidth[par1] == 0)
/*      */     {
/*  289 */       return 0.0F;
/*      */     }
/*      */     
/*      */ 
/*  293 */     int i = par1 / 'Ā';
/*  294 */     loadGlyphTexture(i);
/*  295 */     int j = this.glyphWidth[par1] >>> 4;
/*  296 */     int k = this.glyphWidth[par1] & 0xF;
/*  297 */     float f = j;
/*  298 */     float f1 = k + 1;
/*  299 */     float f2 = par1 % '\020' * 16 + f;
/*  300 */     float f3 = (par1 & 0xFF) / '\020' * 16;
/*  301 */     float f4 = f1 - f - 0.02F;
/*  302 */     float f5 = par2 ? 1.0F : 0.0F;
/*  303 */     GL11.glBegin(5);
/*  304 */     GL11.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
/*  305 */     GL11.glVertex3f(this.posX + f5, this.posY, 0.0F);
/*  306 */     GL11.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
/*  307 */     GL11.glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
/*  308 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
/*  309 */     GL11.glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
/*  310 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
/*  311 */     GL11.glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
/*  312 */     GL11.glEnd();
/*  313 */     return (f1 - f) / 2.0F + 1.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int drawStringWithShadow(String par1Str, int par2, int par3, int par4)
/*      */   {
/*  322 */     return drawString(par1Str, par2, par3, par4, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int drawString(String par1Str, int par2, int par3, int par4)
/*      */   {
/*  330 */     return drawString(par1Str, par2, par3, par4, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int drawString(String par1Str, int par2, int par3, int par4, boolean par5)
/*      */   {
/*  338 */     resetStyles();
/*      */     
/*  340 */     if (this.bidiFlag)
/*      */     {
/*  342 */       par1Str = bidiReorder(par1Str);
/*      */     }
/*      */     
/*      */     int l;
/*      */     
/*  347 */     if (par5)
/*      */     {
/*  349 */       int l = renderString(par1Str, par2 + 1, par3 + 1, par4, true);
/*  350 */       l = Math.max(l, renderString(par1Str, par2, par3, par4, false));
/*      */     }
/*      */     else
/*      */     {
/*  354 */       l = renderString(par1Str, par2, par3, par4, false);
/*      */     }
/*      */     
/*  357 */     return l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String bidiReorder(String par1Str)
/*      */   {
/*  365 */     if ((par1Str != null) && (Bidi.requiresBidi(par1Str.toCharArray(), 0, par1Str.length())))
/*      */     {
/*  367 */       Bidi bidi = new Bidi(par1Str, -2);
/*  368 */       byte[] abyte = new byte[bidi.getRunCount()];
/*  369 */       String[] astring = new String[abyte.length];
/*      */       
/*      */ 
/*  372 */       for (int j = 0; j < abyte.length; j++)
/*      */       {
/*  374 */         int k = bidi.getRunStart(j);
/*  375 */         int i = bidi.getRunLimit(j);
/*  376 */         int l = bidi.getRunLevel(j);
/*  377 */         String s1 = par1Str.substring(k, i);
/*  378 */         abyte[j] = ((byte)l);
/*  379 */         astring[j] = s1;
/*      */       }
/*      */       
/*  382 */       String[] astring1 = (String[])astring.clone();
/*  383 */       Bidi.reorderVisually(abyte, 0, astring, 0, abyte.length);
/*  384 */       StringBuilder stringbuilder = new StringBuilder();
/*  385 */       int i = 0;
/*      */       
/*  387 */       while (i < astring.length)
/*      */       {
/*  389 */         byte b0 = abyte[i];
/*  390 */         int i1 = 0;
/*      */         
/*      */ 
/*      */ 
/*  394 */         while (i1 < astring1.length)
/*      */         {
/*  396 */           if (!astring1[i1].equals(astring[i]))
/*      */           {
/*  398 */             i1++;
/*      */           }
/*      */           else
/*      */           {
/*  402 */             b0 = abyte[i1];
/*      */           }
/*      */         }
/*  405 */         if ((b0 & 0x1) == 0)
/*      */         {
/*  407 */           stringbuilder.append(astring[i]);
/*      */         }
/*      */         else
/*      */         {
/*  411 */           for (i1 = astring[i].length() - 1; i1 >= 0; i1--)
/*      */           {
/*  413 */             char c0 = astring[i].charAt(i1);
/*      */             
/*  415 */             if (c0 == '(')
/*      */             {
/*  417 */               c0 = ')';
/*      */             }
/*  419 */             else if (c0 == ')')
/*      */             {
/*  421 */               c0 = '(';
/*      */             }
/*      */             
/*  424 */             stringbuilder.append(c0);
/*      */           }
/*      */         }
/*      */         
/*  428 */         i++;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  433 */       return stringbuilder.toString();
/*      */     }
/*      */     
/*      */ 
/*  437 */     return par1Str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void resetStyles()
/*      */   {
/*  446 */     this.randomStyle = false;
/*  447 */     this.boldStyle = false;
/*  448 */     this.italicStyle = false;
/*  449 */     this.underlineStyle = false;
/*  450 */     this.strikethroughStyle = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void renderStringAtPos(String par1Str, boolean par2)
/*      */   {
/*  458 */     for (int i = 0; i < par1Str.length(); i++)
/*      */     {
/*  460 */       char c0 = par1Str.charAt(i);
/*      */       
/*      */ 
/*      */ 
/*  464 */       if ((c0 == '§') && (i + 1 < par1Str.length()))
/*      */       {
/*  466 */         int j = "0123456789abcdefklmnor".indexOf(par1Str.toLowerCase().charAt(i + 1));
/*      */         
/*  468 */         if (j < 16)
/*      */         {
/*  470 */           this.randomStyle = false;
/*  471 */           this.boldStyle = false;
/*  472 */           this.strikethroughStyle = false;
/*  473 */           this.underlineStyle = false;
/*  474 */           this.italicStyle = false;
/*      */           
/*  476 */           if ((j < 0) || (j > 15))
/*      */           {
/*  478 */             j = 15;
/*      */           }
/*      */           
/*  481 */           if (par2)
/*      */           {
/*  483 */             j += 16;
/*      */           }
/*      */           
/*  486 */           int k = this.colorCode[j];
/*  487 */           this.textColor = k;
/*  488 */           GL11.glColor4f((k >> 16) / 255.0F, (k >> 8 & 0xFF) / 255.0F, (k & 0xFF) / 255.0F, this.alpha);
/*      */         }
/*  490 */         else if (j == 16)
/*      */         {
/*  492 */           this.randomStyle = true;
/*      */         }
/*  494 */         else if (j == 17)
/*      */         {
/*  496 */           this.boldStyle = true;
/*      */         }
/*  498 */         else if (j == 18)
/*      */         {
/*  500 */           this.strikethroughStyle = true;
/*      */         }
/*  502 */         else if (j == 19)
/*      */         {
/*  504 */           this.underlineStyle = true;
/*      */         }
/*  506 */         else if (j == 20)
/*      */         {
/*  508 */           this.italicStyle = true;
/*      */         }
/*  510 */         else if (j == 21)
/*      */         {
/*  512 */           this.randomStyle = false;
/*  513 */           this.boldStyle = false;
/*  514 */           this.strikethroughStyle = false;
/*  515 */           this.underlineStyle = false;
/*  516 */           this.italicStyle = false;
/*  517 */           GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
/*      */         }
/*      */         
/*  520 */         i++;
/*      */       }
/*      */       else
/*      */       {
/*  524 */         int j = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(c0);
/*      */         
/*  526 */         if ((this.randomStyle) && (j != -1))
/*      */         {
/*      */           int k;
/*      */           do {
/*  530 */             k = this.fontRandom.nextInt(this.charWidth.length);
/*      */           }
/*  532 */           while (this.charWidth[j] != this.charWidth[k]);
/*      */           
/*  534 */           j = k;
/*      */         }
/*      */         
/*  537 */         float f = this.unicodeFlag ? 0.5F : 1.0F;
/*  538 */         boolean flag1 = ((j <= 0) || (this.unicodeFlag)) && (par2);
/*      */         
/*  540 */         if (flag1)
/*      */         {
/*  542 */           this.posX -= f;
/*  543 */           this.posY -= f;
/*      */         }
/*      */         
/*  546 */         float f1 = renderCharAtPos(j, c0, this.italicStyle);
/*      */         
/*  548 */         if (flag1)
/*      */         {
/*  550 */           this.posX += f;
/*  551 */           this.posY += f;
/*      */         }
/*      */         
/*  554 */         if (this.boldStyle)
/*      */         {
/*  556 */           this.posX += f;
/*      */           
/*  558 */           if (flag1)
/*      */           {
/*  560 */             this.posX -= f;
/*  561 */             this.posY -= f;
/*      */           }
/*      */           
/*  564 */           renderCharAtPos(j, c0, this.italicStyle);
/*  565 */           this.posX -= f;
/*      */           
/*  567 */           if (flag1)
/*      */           {
/*  569 */             this.posX += f;
/*  570 */             this.posY += f;
/*      */           }
/*      */           
/*  573 */           f1 += 1.0F;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  578 */         if (this.strikethroughStyle)
/*      */         {
/*  580 */           Tessellator tessellator = Tessellator.getInstance();
/*  581 */           GL11.glDisable(3553);
/*  582 */           tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION);
/*  583 */           tessellator.getWorldRenderer().pos(this.posX, this.posY + this.FONT_HEIGHT / 2, 0.0D).endVertex();
/*  584 */           tessellator.getWorldRenderer().pos(this.posX + f1, this.posY + this.FONT_HEIGHT / 2, 0.0D).endVertex();
/*  585 */           tessellator.getWorldRenderer().pos(this.posX + f1, this.posY + this.FONT_HEIGHT / 2 - 1.0F, 0.0D).endVertex();
/*  586 */           tessellator.getWorldRenderer().pos(this.posX, this.posY + this.FONT_HEIGHT / 2 - 1.0F, 0.0D).endVertex();
/*  587 */           tessellator.draw();
/*  588 */           GL11.glEnable(3553);
/*      */         }
/*      */         
/*  591 */         if (this.underlineStyle)
/*      */         {
/*  593 */           Tessellator tessellator = Tessellator.getInstance();
/*  594 */           GL11.glDisable(3553);
/*  595 */           tessellator.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION);
/*  596 */           int l = this.underlineStyle ? -1 : 0;
/*  597 */           tessellator.getWorldRenderer().pos(this.posX + l, this.posY + this.FONT_HEIGHT, 0.0D).endVertex();
/*  598 */           tessellator.getWorldRenderer().pos(this.posX + f1, this.posY + this.FONT_HEIGHT, 0.0D).endVertex();
/*  599 */           tessellator.getWorldRenderer().pos(this.posX + f1, this.posY + this.FONT_HEIGHT - 1.0F, 0.0D).endVertex();
/*  600 */           tessellator.getWorldRenderer().pos(this.posX + l, this.posY + this.FONT_HEIGHT - 1.0F, 0.0D).endVertex();
/*  601 */           tessellator.draw();
/*  602 */           GL11.glEnable(3553);
/*      */         }
/*      */         
/*  605 */         this.posX += (int)f1;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int renderStringAligned(String par1Str, int par2, int par3, int par4, int par5, boolean par6)
/*      */   {
/*  615 */     if (this.bidiFlag)
/*      */     {
/*  617 */       par1Str = bidiReorder(par1Str);
/*  618 */       int i1 = getStringWidth(par1Str);
/*  619 */       par2 = par2 + par4 - i1;
/*      */     }
/*      */     
/*  622 */     return renderString(par1Str, par2, par3, par5, par6);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int renderString(String par1Str, int par2, int par3, int par4, boolean par5)
/*      */   {
/*  630 */     if (par1Str == null)
/*      */     {
/*  632 */       return 0;
/*      */     }
/*      */     
/*      */ 
/*  636 */     if ((par4 & 0xFC000000) == 0)
/*      */     {
/*  638 */       par4 |= 0xFF000000;
/*      */     }
/*      */     
/*  641 */     if (par5)
/*      */     {
/*  643 */       par4 = (par4 & 0xFCFCFC) >> 2 | par4 & 0xFF000000;
/*      */     }
/*      */     
/*  646 */     this.red = ((par4 >> 16 & 0xFF) / 255.0F);
/*  647 */     this.blue = ((par4 >> 8 & 0xFF) / 255.0F);
/*  648 */     this.green = ((par4 & 0xFF) / 255.0F);
/*  649 */     this.alpha = ((par4 >> 24 & 0xFF) / 255.0F);
/*  650 */     GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
/*  651 */     this.posX = par2;
/*  652 */     this.posY = par3;
/*  653 */     renderStringAtPos(par1Str, par5);
/*  654 */     return (int)this.posX;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getStringWidth(String par1Str)
/*      */   {
/*  663 */     if (par1Str == null)
/*      */     {
/*  665 */       return 0;
/*      */     }
/*      */     
/*      */ 
/*  669 */     int i = 0;
/*  670 */     boolean flag = false;
/*      */     
/*  672 */     for (int j = 0; j < par1Str.length(); j++)
/*      */     {
/*  674 */       char c0 = par1Str.charAt(j);
/*  675 */       int k = getCharWidth(c0);
/*      */       
/*  677 */       if ((k < 0) && (j < par1Str.length() - 1))
/*      */       {
/*  679 */         j++;
/*  680 */         c0 = par1Str.charAt(j);
/*      */         
/*  682 */         if ((c0 != 'l') && (c0 != 'L'))
/*      */         {
/*  684 */           if ((c0 == 'r') || (c0 == 'R'))
/*      */           {
/*  686 */             flag = false;
/*      */           }
/*      */           
/*      */         }
/*      */         else {
/*  691 */           flag = true;
/*      */         }
/*      */         
/*  694 */         k = 0;
/*      */       }
/*      */       
/*  697 */       i += k;
/*      */       
/*  699 */       if (flag)
/*      */       {
/*  701 */         i++;
/*      */       }
/*      */     }
/*      */     
/*  705 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getCharWidth(char par1)
/*      */   {
/*  714 */     if (par1 == '§')
/*      */     {
/*  716 */       return -1;
/*      */     }
/*  718 */     if (par1 == ' ')
/*      */     {
/*  720 */       return 4;
/*      */     }
/*      */     
/*      */ 
/*  724 */     int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(par1);
/*      */     
/*  726 */     if ((par1 > 0) && (i != -1) && (!this.unicodeFlag))
/*      */     {
/*  728 */       return this.charWidth[i];
/*      */     }
/*  730 */     if (this.glyphWidth[par1] != 0)
/*      */     {
/*  732 */       int j = this.glyphWidth[par1] >>> 4;
/*  733 */       int k = this.glyphWidth[par1] & 0xF;
/*      */       
/*  735 */       if (k > 7)
/*      */       {
/*  737 */         k = 15;
/*  738 */         j = 0;
/*      */       }
/*      */       
/*  741 */       k++;
/*  742 */       return (k - j) / 2 + 1;
/*      */     }
/*      */     
/*      */ 
/*  746 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String trimStringToWidth(String par1Str, int par2)
/*      */   {
/*  756 */     return trimStringToWidth(par1Str, par2, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String trimStringToWidth(String par1Str, int par2, boolean par3)
/*      */   {
/*  764 */     StringBuilder stringbuilder = new StringBuilder();
/*  765 */     int j = 0;
/*  766 */     int k = par3 ? par1Str.length() - 1 : 0;
/*  767 */     int l = par3 ? -1 : 1;
/*  768 */     boolean flag1 = false;
/*  769 */     boolean flag2 = false;
/*      */     
/*  771 */     for (int i1 = k; (i1 >= 0) && (i1 < par1Str.length()) && (j < par2); i1 += l)
/*      */     {
/*  773 */       char c0 = par1Str.charAt(i1);
/*  774 */       int j1 = getCharWidth(c0);
/*      */       
/*  776 */       if (flag1)
/*      */       {
/*  778 */         flag1 = false;
/*      */         
/*  780 */         if ((c0 != 'l') && (c0 != 'L'))
/*      */         {
/*  782 */           if ((c0 == 'r') || (c0 == 'R'))
/*      */           {
/*  784 */             flag2 = false;
/*      */           }
/*      */           
/*      */         }
/*      */         else {
/*  789 */           flag2 = true;
/*      */         }
/*      */       }
/*  792 */       else if (j1 < 0)
/*      */       {
/*  794 */         flag1 = true;
/*      */       }
/*      */       else
/*      */       {
/*  798 */         j += j1;
/*      */         
/*  800 */         if (flag2)
/*      */         {
/*  802 */           j++;
/*      */         }
/*      */       }
/*      */       
/*  806 */       if (j > par2) {
/*      */         break;
/*      */       }
/*      */       
/*      */ 
/*  811 */       if (par3)
/*      */       {
/*  813 */         stringbuilder.insert(0, c0);
/*      */       }
/*      */       else
/*      */       {
/*  817 */         stringbuilder.append(c0);
/*      */       }
/*      */     }
/*      */     
/*  821 */     return stringbuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String trimStringNewline(String par1Str)
/*      */   {
/*  829 */     while ((par1Str != null) && (par1Str.endsWith("\n")))
/*      */     {
/*  831 */       par1Str = par1Str.substring(0, par1Str.length() - 1);
/*      */     }
/*      */     
/*  834 */     return par1Str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void drawSplitString(String par1Str, int par2, int par3, int par4, int par5)
/*      */   {
/*  842 */     resetStyles();
/*  843 */     this.textColor = par5;
/*  844 */     par1Str = trimStringNewline(par1Str);
/*  845 */     renderSplitString(par1Str, par2, par3, par4, false);
/*      */   }
/*      */   
/*      */   public void drawSplitString(String par1Str, int par2, int par3, int par4, int par5, Gui gui)
/*      */   {
/*  850 */     resetStyles();
/*  851 */     this.textColor = par5;
/*  852 */     par1Str = trimStringNewline(par1Str);
/*  853 */     renderSplitString(par1Str, par2, par3, par4, false, gui);
/*      */   }
/*      */   
/*  856 */   ResourceLocation rl1 = new ResourceLocation("thaumcraft", "textures/gui/gui_researchbook.png");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void renderSplitString(String par1Str, int par2, int par3, int par4, boolean par5, Gui gui)
/*      */   {
/*  864 */     List list = listFormattedStringToWidth(par1Str, par4);
/*      */     
/*  866 */     for (Iterator iterator = list.iterator(); iterator.hasNext(); par3 += this.FONT_HEIGHT)
/*      */     {
/*  868 */       String s1 = (String)iterator.next();
/*      */       
/*  870 */       if (s1.contains("@")) {
/*  871 */         int i1 = s1.indexOf("@");
/*  872 */         int i2 = s1.indexOf("@", i1 + 1);
/*      */         
/*  874 */         if ((i1 >= 0) && (i2 > i1)) {
/*  875 */           int index = Integer.parseInt(s1.substring(i1 + 1, i2));
/*  876 */           s1 = s1.replaceAll(s1.substring(i1, i2 + 1), "").trim();
/*      */           
/*  878 */           String s2 = (String)this.inserts.get(index);
/*  879 */           if (s2 != null) {
/*  880 */             if ((s2.contains("<LINE>")) || (s2.contains("<LINE/>"))) {
/*  881 */               Minecraft.getMinecraft().renderEngine.bindTexture(this.rl1);
/*  882 */               gui.drawTexturedModalRect(par2 + par4 / 2 - 48, par3 + 2, 24, 184, 96, 4);
/*      */             }
/*  884 */             else if (s2.contains("<IMG>")) {
/*  885 */               String cont = s2.replace("<IMG>", "");
/*  886 */               cont = cont.replace("</IMG>", "");
/*  887 */               String[] scont = cont.split(":");
/*  888 */               ResourceLocation rl2 = new ResourceLocation(scont[0], scont[1]);
/*  889 */               Minecraft.getMinecraft().renderEngine.bindTexture(rl2);
/*  890 */               float scale = Float.parseFloat(scont[6]);
/*  891 */               GL11.glPushMatrix();
/*  892 */               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  893 */               GL11.glTranslatef(par2 - 3 + par4 / 2 - Integer.parseInt(scont[4]) / 2 * scale, par3, 0.0F);
/*  894 */               GL11.glScalef(scale, scale, scale);
/*  895 */               gui.drawTexturedModalRect(0, 0, Integer.parseInt(scont[2]), Integer.parseInt(scont[3]), Integer.parseInt(scont[4]), Integer.parseInt(scont[5]));
/*      */               
/*      */ 
/*  898 */               GL11.glPopMatrix();
/*  899 */               par3 = (int)(par3 + (Integer.parseInt(scont[5]) * scale - this.FONT_HEIGHT));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/*  905 */         this.renderEngine.bindTexture(this.field_111273_g);
/*  906 */         renderStringAligned(s1, par2, par3, par4, this.textColor, par5);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void renderSplitString(String par1Str, int par2, int par3, int par4, boolean par5)
/*      */   {
/*  913 */     List list = listFormattedStringToWidth(par1Str, par4);
/*      */     
/*  915 */     for (Iterator iterator = list.iterator(); iterator.hasNext(); par3 += this.FONT_HEIGHT)
/*      */     {
/*  917 */       String s1 = (String)iterator.next();
/*  918 */       renderStringAligned(s1, par2, par3, par4, this.textColor, par5);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int splitStringWidth(String par1Str, int par2)
/*      */   {
/*  927 */     return this.FONT_HEIGHT * listFormattedStringToWidth(par1Str, par2).size();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setUnicodeFlag(boolean par1)
/*      */   {
/*  936 */     this.unicodeFlag = par1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getUnicodeFlag()
/*      */   {
/*  945 */     return this.unicodeFlag;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setBidiFlag(boolean par1)
/*      */   {
/*  953 */     this.bidiFlag = par1;
/*      */   }
/*      */   
/*  956 */   ArrayList<String> inserts = new ArrayList();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public List listFormattedStringToWidth(String par1Str, int par2)
/*      */   {
/*  963 */     this.inserts.clear();
/*  964 */     int count = 0;
/*  965 */     boolean found = true;
/*  966 */     while (found) {
/*  967 */       found = false;
/*      */       
/*  969 */       par1Str = par1Str.replaceAll("<BR>", "\n");
/*  970 */       par1Str = par1Str.replaceAll("<BR/>", "\n");
/*      */       
/*  972 */       if ((par1Str.indexOf("<LINE>") >= 0) || (par1Str.indexOf("<LINE/>") >= 0)) {
/*  973 */         this.inserts.add("<LINE>");
/*  974 */         if (par1Str.indexOf("<LINE>") >= 0) {
/*  975 */           par1Str = par1Str.replaceFirst("<LINE>", "\n@" + count + "@\n");
/*      */         } else
/*  977 */           par1Str = par1Str.replaceFirst("<LINE/>", "\n@" + count + "@\n");
/*  978 */         count++;
/*  979 */         found = true;
/*      */       }
/*      */       
/*      */ 
/*  983 */       if (par1Str.indexOf("<IMG>") >= 0) {
/*  984 */         int i1 = par1Str.indexOf("<IMG>");
/*  985 */         int i2 = par1Str.indexOf("</IMG>");
/*  986 */         String s = par1Str.substring(i1, i2) + "</IMG>";
/*  987 */         this.inserts.add(s);
/*  988 */         par1Str = par1Str.replaceFirst(s, "\n@" + count + "@\n");
/*  989 */         count++;
/*  990 */         found = true;
/*      */       }
/*      */     }
/*      */     
/*  994 */     return Arrays.asList(wrapFormattedStringToWidth(par1Str, par2).split("\n"));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   String wrapFormattedStringToWidth(String par1Str, int par2)
/*      */   {
/* 1003 */     int j = sizeStringToWidth(par1Str, par2);
/*      */     
/* 1005 */     if (par1Str.length() <= j)
/*      */     {
/* 1007 */       return par1Str;
/*      */     }
/*      */     
/*      */ 
/* 1011 */     String s1 = par1Str.substring(0, j);
/* 1012 */     char c0 = par1Str.charAt(j);
/* 1013 */     boolean flag = (c0 == ' ') || (c0 == '\n');
/* 1014 */     String s2 = getFormatFromString(s1) + par1Str.substring(j + (flag ? 1 : 0));
/* 1015 */     return s1 + "\n" + wrapFormattedStringToWidth(s2, par2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int sizeStringToWidth(String par1Str, int par2)
/*      */   {
/* 1025 */     int j = par1Str.length();
/* 1026 */     int k = 0;
/* 1027 */     int l = 0;
/* 1028 */     int i1 = -1;
/*      */     
/* 1030 */     for (boolean flag = false; l < j; l++)
/*      */     {
/* 1032 */       char c0 = par1Str.charAt(l);
/*      */       
/* 1034 */       switch (c0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */       case '§': 
/* 1040 */         if (l < j - 1)
/*      */         {
/* 1042 */           l++;
/* 1043 */           char c1 = par1Str.charAt(l);
/*      */           
/* 1045 */           if ((c1 != 'l') && (c1 != 'L'))
/*      */           {
/* 1047 */             if ((c1 == 'r') || (c1 == 'R') || (isFormatColor(c1)))
/*      */             {
/* 1049 */               flag = false;
/*      */             }
/*      */             
/*      */           }
/*      */           else
/* 1054 */             flag = true;
/*      */         }
/* 1056 */         break;
/*      */       
/*      */ 
/*      */       case ' ': 
/* 1060 */         i1 = l;
/*      */       default: 
/* 1062 */         k += getCharWidth(c0);
/*      */         
/* 1064 */         if (flag)
/*      */         {
/* 1066 */           k++; }
/*      */         break;
/*      */       }
/*      */       
/* 1070 */       if (c0 == '\n')
/*      */       {
/* 1072 */         l++;
/* 1073 */         i1 = l;
/*      */       }
/*      */       else
/*      */       {
/* 1077 */         if (k > par2) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1083 */     return (l != j) && (i1 != -1) && (i1 < l) ? i1 : l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isFormatColor(char par0)
/*      */   {
/* 1091 */     return ((par0 >= '0') && (par0 <= '9')) || ((par0 >= 'a') && (par0 <= 'f')) || ((par0 >= 'A') && (par0 <= 'F'));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isFormatSpecial(char par0)
/*      */   {
/* 1099 */     return ((par0 >= 'k') && (par0 <= 'o')) || ((par0 >= 'K') && (par0 <= 'O')) || (par0 == 'r') || (par0 == 'R');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String getFormatFromString(String par0Str)
/*      */   {
/* 1107 */     String s1 = "";
/* 1108 */     int i = -1;
/* 1109 */     int j = par0Str.length();
/*      */     
/* 1111 */     while ((i = par0Str.indexOf('§', i + 1)) != -1)
/*      */     {
/* 1113 */       if (i < j - 1)
/*      */       {
/* 1115 */         char c0 = par0Str.charAt(i + 1);
/*      */         
/* 1117 */         if (isFormatColor(c0))
/*      */         {
/* 1119 */           s1 = "§" + c0;
/*      */         }
/* 1121 */         else if (isFormatSpecial(c0))
/*      */         {
/* 1123 */           s1 = s1 + "§" + c0;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1128 */     return s1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getBidiFlag()
/*      */   {
/* 1136 */     return this.bidiFlag;
/*      */   }
/*      */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\lib\TCFontRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */