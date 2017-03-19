/*     */ package thaumcraft.client.renderers.models.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ModelRendererTaintacle extends net.minecraft.client.model.ModelRenderer
/*     */ {
/*     */   private int textureOffsetX;
/*     */   private int textureOffsetY;
/*     */   private boolean compiled;
/*     */   private int displayList;
/*     */   private ModelBase baseModel;
/*     */   
/*     */   public ModelRendererTaintacle(ModelBase par1ModelBase)
/*     */   {
/*  18 */     super(par1ModelBase);
/*     */   }
/*     */   
/*     */   public ModelRendererTaintacle(ModelBase par1ModelBase, int par2, int par3)
/*     */   {
/*  23 */     this(par1ModelBase);
/*  24 */     setTextureOffset(par2, par3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public void render(float par1, float scale)
/*     */   {
/*  46 */     if (!this.isHidden)
/*     */     {
/*  48 */       if (this.showModel)
/*     */       {
/*  50 */         if (!this.compiled)
/*     */         {
/*  52 */           compileDisplayList(par1);
/*     */         }
/*     */         
/*  55 */         GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
/*     */         
/*     */         int i;
/*  58 */         if ((this.rotateAngleX == 0.0F) && (this.rotateAngleY == 0.0F) && (this.rotateAngleZ == 0.0F))
/*     */         {
/*  60 */           if ((this.rotationPointX == 0.0F) && (this.rotationPointY == 0.0F) && (this.rotationPointZ == 0.0F))
/*     */           {
/*  62 */             if (this.childModels == null) {
/*  63 */               int j = 15728880;
/*  64 */               int k = j % 65536;
/*  65 */               int l = j / 65536;
/*  66 */               OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/*     */             }
/*  68 */             GL11.glCallList(this.displayList);
/*     */             
/*  70 */             if (this.childModels == null)
/*     */               break label554;
/*  72 */             for (i = 0; i < this.childModels.size();)
/*     */             {
/*  74 */               GL11.glPushMatrix();
/*  75 */               GL11.glScalef(scale, scale, scale);
/*  76 */               ((ModelRendererTaintacle)this.childModels.get(i)).render(par1, scale);
/*  77 */               GL11.glPopMatrix();i++; continue;
/*     */               
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  83 */               GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
/*  84 */               if (this.childModels == null) {
/*  85 */                 int j = 15728880;
/*  86 */                 int k = j % 65536;
/*  87 */                 int l = j / 65536;
/*  88 */                 OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/*     */               }
/*  90 */               GL11.glCallList(this.displayList);
/*     */               
/*  92 */               if (this.childModels != null)
/*     */               {
/*  94 */                 for (int i = 0; i < this.childModels.size(); i++)
/*     */                 {
/*  96 */                   GL11.glPushMatrix();
/*  97 */                   GL11.glScalef(scale, scale, scale);
/*  98 */                   ((ModelRendererTaintacle)this.childModels.get(i)).render(par1, scale);
/*  99 */                   GL11.glPopMatrix();
/*     */                 }
/*     */               }
/*     */               
/* 103 */               GL11.glTranslatef(-this.rotationPointX * par1, -this.rotationPointY * par1, -this.rotationPointZ * par1); break;
/*     */               
/*     */ 
/*     */ 
/*     */ 
/* 108 */               GL11.glPushMatrix();
/* 109 */               GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
/*     */               
/* 111 */               if (this.rotateAngleZ != 0.0F)
/*     */               {
/* 113 */                 GL11.glRotatef(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */               }
/*     */               
/* 116 */               if (this.rotateAngleY != 0.0F)
/*     */               {
/* 118 */                 GL11.glRotatef(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */               }
/*     */               
/* 121 */               if (this.rotateAngleX != 0.0F)
/*     */               {
/* 123 */                 GL11.glRotatef(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */               }
/*     */               
/* 126 */               if (this.childModels == null) {
/* 127 */                 int j = 15728880;
/* 128 */                 int k = j % 65536;
/* 129 */                 int l = j / 65536;
/* 130 */                 OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/*     */               }
/* 132 */               GL11.glCallList(this.displayList);
/*     */               
/* 134 */               if (this.childModels != null)
/*     */               {
/* 136 */                 for (int i = 0; i < this.childModels.size(); i++)
/*     */                 {
/* 138 */                   GL11.glPushMatrix();
/* 139 */                   GL11.glScalef(scale, scale, scale);
/* 140 */                   ((ModelRendererTaintacle)this.childModels.get(i)).render(par1, scale);
/* 141 */                   GL11.glPopMatrix();
/*     */                 }
/*     */               }
/*     */               
/* 145 */               GL11.glPopMatrix();
/*     */             } } }
/*     */         label554:
/* 148 */         GL11.glTranslatef(-this.offsetX, -this.offsetY, -this.offsetZ);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   private void compileDisplayList(float par1)
/*     */   {
/* 161 */     this.displayList = net.minecraft.client.renderer.GLAllocation.generateDisplayLists(1);
/* 162 */     GL11.glNewList(this.displayList, 4864);
/* 163 */     net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
/*     */     
/* 165 */     for (int i = 0; i < this.cubeList.size(); i++)
/*     */     {
/* 167 */       ((net.minecraft.client.model.ModelBox)this.cubeList.get(i)).render(tessellator.getWorldRenderer(), par1);
/*     */     }
/*     */     
/* 170 */     GL11.glEndList();
/* 171 */     this.compiled = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\entity\ModelRendererTaintacle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */