/*    */ package thaumcraft.client.renderers.tile;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.api.ThaumcraftApi;
/*    */ import thaumcraft.api.crafting.CrucibleRecipe;
/*    */ import thaumcraft.client.lib.models.AdvancedModelLoader;
/*    */ import thaumcraft.client.lib.models.IModelCustom;
/*    */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*    */ import thaumcraft.common.tiles.devices.TileThaumatorium;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class TileThaumatoriumRenderer
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/*    */   private IModelCustom model;
/* 28 */   private static final ResourceLocation TM = new ResourceLocation("thaumcraft", "models/obj/thaumatorium.obj");
/* 29 */   private static final ResourceLocation TEX_BORE = new ResourceLocation("thaumcraft", "textures/models/thaumatorium.png");
/*    */   
/* 31 */   public TileThaumatoriumRenderer() { this.model = AdvancedModelLoader.loadModel(TM); }
/*    */   
/*    */ 
/* 34 */   EntityItem entityitem = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void renderTileEntityAt(TileThaumatorium tile, double par2, double par4, double par6, float par8)
/*    */   {
/* 42 */     GL11.glPushMatrix();
/* 43 */     GL11.glTranslatef((float)par2 + 0.5F, (float)par4, (float)par6 + 0.5F);
/* 44 */     GL11.glRotatef(90.0F, -1.0F, 0.0F, 0.0F);
/* 45 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 47 */     bindTexture(TEX_BORE);
/*    */     
/* 49 */     EnumFacing facing = BlockStateUtils.getFacing(tile.getBlockMetadata());
/* 50 */     if (tile.getWorld() != null) {
/* 51 */       switch (facing.ordinal()) {
/* 52 */       case 5:  GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F); break;
/* 53 */       case 3:  GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F); break;
/* 54 */       case 2:  GL11.glRotatef(270.0F, 0.0F, 0.0F, 1.0F);
/*    */       }
/*    */       
/*    */     }
/* 58 */     this.model.renderAll();
/*    */     
/* 60 */     GL11.glPopMatrix();
/*    */     
/* 62 */     float ticks = Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + par8;
/* 63 */     if ((tile != null) && (tile.getWorld() != null) && (tile.recipeHash != null) && (tile.recipeHash.size() > 0)) {
/* 64 */       int stack = Minecraft.getMinecraft().getRenderViewEntity().ticksExisted / 40 % tile.recipeHash.size();
/* 65 */       CrucibleRecipe recipe = ThaumcraftApi.getCrucibleRecipeFromHash(((Integer)tile.recipeHash.get(stack)).intValue());
/* 66 */       if (recipe != null) {
/* 67 */         GL11.glPushMatrix();
/* 68 */         GL11.glTranslatef((float)par2 + 0.5F + facing.getFrontOffsetX() / 1.99F, (float)par4 + 1.25F, (float)par6 + 0.5F + facing.getFrontOffsetZ() / 1.99F);
/* 69 */         switch (facing.ordinal()) {
/* 70 */         case 5:  GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F); break;
/* 71 */         case 4:  GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F); break;
/* 72 */         case 2:  GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/*    */         }
/* 74 */         GL11.glScaled(0.75D, 0.75D, 0.75D);
/*    */         
/* 76 */         ItemStack is = recipe.getRecipeOutput().copy();
/* 77 */         is.stackSize = 1;
/* 78 */         this.entityitem = new EntityItem(tile.getWorld(), 0.0D, 0.0D, 0.0D, is);
/* 79 */         this.entityitem.hoverStart = 0.0F;
/* 80 */         RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/*    */         
/* 82 */         rendermanager.renderEntityWithPosYaw(this.entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/* 83 */         GL11.glPopMatrix();
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8, int q)
/*    */   {
/* 91 */     renderTileEntityAt((TileThaumatorium)par1TileEntity, par2, par4, par6, par8);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileThaumatoriumRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */