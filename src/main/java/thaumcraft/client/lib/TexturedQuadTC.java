/*    */ package thaumcraft.client.lib;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.model.PositionTextureVertex;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TexturedQuadTC
/*    */ {
/*    */   public PositionTextureVertex[] vertexPositions;
/*    */   public int nVertices;
/*    */   private boolean invertNormal;
/*    */   
/*    */   public TexturedQuadTC(PositionTextureVertex[] vertices)
/*    */   {
/* 20 */     this.vertexPositions = vertices;
/* 21 */     this.nVertices = vertices.length;
/*    */   }
/*    */   
/*    */   public TexturedQuadTC(PositionTextureVertex[] vertices, int texcoordU1, int texcoordV1, int texcoordU2, int texcoordV2, float textureWidth, float textureHeight)
/*    */   {
/* 26 */     this(vertices);
/* 27 */     float f2 = 0.0F / textureWidth;
/* 28 */     float f3 = 0.0F / textureHeight;
/* 29 */     vertices[0] = vertices[0].setTexturePosition(texcoordU2 / textureWidth - f2, texcoordV1 / textureHeight + f3);
/* 30 */     vertices[1] = vertices[1].setTexturePosition(texcoordU1 / textureWidth + f2, texcoordV1 / textureHeight + f3);
/* 31 */     vertices[2] = vertices[2].setTexturePosition(texcoordU1 / textureWidth + f2, texcoordV2 / textureHeight - f3);
/* 32 */     vertices[3] = vertices[3].setTexturePosition(texcoordU2 / textureWidth - f2, texcoordV2 / textureHeight - f3);
/*    */   }
/*    */   
/*    */   public void flipFace()
/*    */   {
/* 37 */     PositionTextureVertex[] apositiontexturevertex = new PositionTextureVertex[this.vertexPositions.length];
/*    */     
/* 39 */     for (int i = 0; i < this.vertexPositions.length; i++)
/*    */     {
/* 41 */       apositiontexturevertex[i] = this.vertexPositions[(this.vertexPositions.length - i - 1)];
/*    */     }
/*    */     
/* 44 */     this.vertexPositions = apositiontexturevertex;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void draw(WorldRenderer renderer, float scale, int bright, int color, float alpha)
/*    */   {
/* 53 */     if (bright != -99) {
/* 54 */       renderer.begin(7, UtilsFX.VERTEXFORMAT_POS_TEX_CO_LM_NO);
/*    */     } else {
/* 56 */       renderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
/*    */     }
/* 58 */     Color c = new Color(color);
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 71 */     int aa = bright;
/* 72 */     int j = aa >> 16 & 0xFFFF;
/* 73 */     int k = aa & 0xFFFF;
/*    */     
/* 75 */     for (int i = 0; i < 4; i++)
/*    */     {
/* 77 */       PositionTextureVertex positiontexturevertex = this.vertexPositions[i];
/* 78 */       if (bright != -99) {
/* 79 */         renderer.pos(positiontexturevertex.vector3D.xCoord * scale, positiontexturevertex.vector3D.yCoord * scale, positiontexturevertex.vector3D.zCoord * scale).tex(positiontexturevertex.texturePositionX, positiontexturevertex.texturePositionY).color(c.getRed(), c.getGreen(), c.getBlue(), (int)(alpha * 255.0F)).lightmap(j, k).normal(0.0F, 0.0F, 1.0F).endVertex();
/*    */ 
/*    */ 
/*    */       }
/*    */       else
/*    */       {
/*    */ 
/*    */ 
/* 87 */         renderer.pos(positiontexturevertex.vector3D.xCoord * scale, positiontexturevertex.vector3D.yCoord * scale, positiontexturevertex.vector3D.zCoord * scale).tex(positiontexturevertex.texturePositionX, positiontexturevertex.texturePositionY).color(c.getRed(), c.getGreen(), c.getBlue(), (int)(alpha * 255.0F)).normal(0.0F, 0.0F, 1.0F).endVertex();
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 96 */     Tessellator.getInstance().draw();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\lib\TexturedQuadTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */