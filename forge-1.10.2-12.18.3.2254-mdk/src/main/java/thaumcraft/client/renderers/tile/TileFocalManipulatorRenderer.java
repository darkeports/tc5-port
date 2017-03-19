/*    */ package thaumcraft.client.renderers.tile;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import thaumcraft.api.wands.ItemFocusBasic;
/*    */ import thaumcraft.common.tiles.crafting.TileFocalManipulator;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class TileFocalManipulatorRenderer
/*    */   extends TileEntitySpecialRenderer
/*    */ {
/* 22 */   EntityItem entityitem = null;
/*    */   
/*    */ 
/*    */   public void renderTileEntityAt(TileFocalManipulator table, double par2, double par4, double par6, float par8)
/*    */   {
/* 27 */     if ((table.getWorld() != null) && (table.getStackInSlot(0) != null) && ((table.getStackInSlot(0).getItem() instanceof ItemFocusBasic)))
/*    */     {
/* 29 */       float ticks = Minecraft.getMinecraft().getRenderViewEntity().ticksExisted + par8;
/* 30 */       GL11.glPushMatrix();
/* 31 */       GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 1.0F, (float)par6 + 0.5F);
/* 32 */       GL11.glRotatef(ticks % 360.0F, 0.0F, 1.0F, 0.0F);
/* 33 */       ItemStack is = table.getStackInSlot(0).copy();
/* 34 */       this.entityitem = new EntityItem(table.getWorld(), 0.0D, 0.0D, 0.0D, is);
/* 35 */       this.entityitem.hoverStart = (MathHelper.sin(ticks / 14.0F) * 0.2F + 0.2F);
/* 36 */       RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 37 */       rendermanager.renderEntityWithPosYaw(this.entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/* 38 */       GL11.glPopMatrix();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8, int p)
/*    */   {
/* 45 */     renderTileEntityAt((TileFocalManipulator)par1TileEntity, par2, par4, par6, par8);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\tile\TileFocalManipulatorRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */