/*    */ package thaumcraft.client.renderers.tile;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.client.lib.models.AdvancedModelLoader;
/*    */ import thaumcraft.client.lib.models.IModelCustom;
/*    */ import thaumcraft.common.config.Config;
/*    */ import thaumcraft.common.tiles.misc.TileEldritchAltar;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEldritchCapRenderer
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/*    */   private IModelCustom model;
/* 29 */   private static final ResourceLocation CAP = new ResourceLocation("thaumcraft", "models/obj/obelisk_cap.obj");
/* 30 */   private static ResourceLocation t1 = new ResourceLocation("thaumcraft", "textures/models/obelisk_cap.png");
/* 31 */   private static final ResourceLocation t2 = new ResourceLocation("thaumcraft", "textures/models/obelisk_cap_2.png");
/*    */   
/*    */   public TileEldritchCapRenderer(ResourceLocation texture)
/*    */   {
/* 35 */     t1 = texture;
/* 36 */     this.model = AdvancedModelLoader.loadModel(CAP);
/*    */   }
/*    */   
/*    */   public TileEldritchCapRenderer()
/*    */   {
/* 41 */     this.model = AdvancedModelLoader.loadModel(CAP);
/*    */   }
/*    */   
/*    */ 
/* 45 */   private ItemStack eye = null;
/* 46 */   EntityItem entityitem = null;
/*    */   
/*    */ 
/*    */   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f, int q)
/*    */   {
/* 51 */     ResourceLocation tempTex = t1;
/* 52 */     GL11.glPushMatrix();
/*    */     
/* 54 */     if (te.getWorld() != null) {
/* 55 */       int j = te.getBlockType().getMixedBrightnessForBlock(te.getWorld(), te.getPos());
/* 56 */       int k = j % 65536;
/* 57 */       int l = j / 65536;
/* 58 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/* 59 */       if (te.getWorld().provider.getDimensionId() == Config.dimensionOuterId) { tempTex = t2;
/*    */       }
/*    */     }
/* 62 */     GL11.glPushMatrix();
/* 63 */     bindTexture(tempTex);
/* 64 */     GL11.glTranslated(x + 0.5D, y, z + 0.5D);
/* 65 */     GL11.glRotated(90.0D, -1.0D, 0.0D, 0.0D);
/* 66 */     this.model.renderPart("Cap");
/* 67 */     GL11.glPopMatrix();
/*    */     
/* 69 */     if ((te.getWorld() != null) && ((te instanceof TileEldritchAltar)) && (((TileEldritchAltar)te).getEyes() > 0))
/*    */     {
/* 71 */       GL11.glPushMatrix();
/* 72 */       GL11.glTranslatef((float)x + 0.5F, (float)y + 0.0F, (float)z + 0.5F);
/*    */       
/* 74 */       if ((this.entityitem == null) || (this.eye == null)) {
/* 75 */         this.eye = new ItemStack(ItemsTC.eldritchEye);
/* 76 */         this.entityitem = new EntityItem(te.getWorld(), 0.0D, 0.0D, 0.0D, this.eye);
/* 77 */         this.entityitem.hoverStart = 0.0F;
/*    */       }
/*    */       
/* 80 */       if ((this.entityitem != null) && (this.eye != null)) {
/* 81 */         for (int a = 0; a < ((TileEldritchAltar)te).getEyes(); a++) {
/* 82 */           GL11.glPushMatrix();
/* 83 */           GL11.glRotated(a * 90, 0.0D, 1.0D, 0.0D);
/* 84 */           GL11.glTranslatef(0.46F, 0.2F, 0.0F);
/* 85 */           GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
/* 86 */           GL11.glRotated(18.0D, -1.0D, 0.0D, 0.0D);
/* 87 */           RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 88 */           rendermanager.renderEntityWithPosYaw(this.entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/* 89 */           GL11.glPopMatrix();
/*    */         }
/*    */       }
/*    */       
/* 93 */       GL11.glPopMatrix();
/*    */     }
/*    */     
/*    */ 
/* 97 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileEldritchCapRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */