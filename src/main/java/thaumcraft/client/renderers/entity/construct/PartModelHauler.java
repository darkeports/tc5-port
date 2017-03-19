/*    */ package thaumcraft.client.renderers.entity.construct;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import thaumcraft.api.golems.IGolemAPI;
/*    */ import thaumcraft.api.golems.parts.PartModel;
/*    */ import thaumcraft.api.golems.parts.PartModel.EnumAttachPoint;
/*    */ import thaumcraft.api.golems.parts.PartModel.EnumLimbSide;
/*    */ 
/*    */ public class PartModelHauler extends PartModel
/*    */ {
/*    */   public PartModelHauler(ResourceLocation objModel, ResourceLocation objTexture, PartModel.EnumAttachPoint attachPoint)
/*    */   {
/* 16 */     super(objModel, objTexture, attachPoint);
/*    */   }
/*    */   
/*    */   public void postRenderObjectPart(String partName, IGolemAPI golem, float partialTicks, PartModel.EnumLimbSide side)
/*    */   {
/* 21 */     if ((golem.getCarrying().length > 1) && (golem.getCarrying()[1] != null)) {
/* 22 */       ItemStack itemstack = golem.getCarrying()[1];
/* 23 */       if (itemstack != null)
/*    */       {
/* 25 */         GlStateManager.pushMatrix();
/* 26 */         net.minecraft.item.Item item = itemstack.getItem();
/* 27 */         Minecraft minecraft = Minecraft.getMinecraft();
/* 28 */         GlStateManager.scale(0.375D, 0.375D, 0.375D);
/* 29 */         GlStateManager.translate(0.0F, 0.33F, 0.825F);
/*    */         
/* 31 */         if (!(item instanceof net.minecraft.item.ItemBlock)) {
/* 32 */           GlStateManager.translate(0.0F, 0.0F, -0.25F);
/*    */         }
/*    */         
/* 35 */         minecraft.getItemRenderer().renderItem(golem.getGolemEntity(), itemstack, net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.HEAD);
/* 36 */         GlStateManager.popMatrix();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\construct\PartModelHauler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */