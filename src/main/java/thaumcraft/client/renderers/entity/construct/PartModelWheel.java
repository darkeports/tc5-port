/*    */ package thaumcraft.client.renderers.entity.construct;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import thaumcraft.api.golems.IGolemAPI;
/*    */ import thaumcraft.api.golems.parts.PartModel;
/*    */ import thaumcraft.api.golems.parts.PartModel.EnumLimbSide;
/*    */ 
/*    */ public class PartModelWheel extends PartModel
/*    */ {
/*    */   public PartModelWheel(ResourceLocation objModel, ResourceLocation objTexture, thaumcraft.api.golems.parts.PartModel.EnumAttachPoint attachPoint)
/*    */   {
/* 12 */     super(objModel, objTexture, attachPoint);
/*    */   }
/*    */   
/*    */   public void preRenderObjectPart(String partName, IGolemAPI golem, float partialTicks, PartModel.EnumLimbSide side)
/*    */   {
/* 17 */     if (partName.equals("wheel")) {
/* 18 */       float lastRot = 0.0F;
/* 19 */       if (thaumcraft.common.entities.construct.golem.parts.GolemLegWheels.ani.containsKey(Integer.valueOf(golem.getGolemEntity().getEntityId()))) {
/* 20 */         lastRot = ((Float)thaumcraft.common.entities.construct.golem.parts.GolemLegWheels.ani.get(Integer.valueOf(golem.getGolemEntity().getEntityId()))).floatValue();
/*    */       }
/* 22 */       net.minecraft.client.renderer.GlStateManager.translate(0.0D, -0.375D, 0.0D);
/* 23 */       net.minecraft.client.renderer.GlStateManager.rotate(lastRot, -1.0F, 0.0F, 0.0F);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\entity\construct\PartModelWheel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */