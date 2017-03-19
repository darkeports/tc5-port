/*    */ package thaumcraft.codechicken.libold.lighting;
import thaumcraft.codechicken.libold.colour.ColourRGBA;
import thaumcraft.codechicken.libold.render.CCRenderPipeline;
import thaumcraft.codechicken.libold.render.CCRenderState;
import thaumcraft.codechicken.libold.render.CCRenderState.IVertexOperation;
/*    */ 
/*    */ public class PlanarLightModel
/*    */   implements CCRenderState.IVertexOperation
/*    */ {
/* 11 */   public static PlanarLightModel standardLightModel = LightModel.standardLightModel.reducePlanar();
/*    */   public int[] colours;
/*    */   
/*    */   public PlanarLightModel(int[] colours)
/*    */   {
/* 16 */     this.colours = colours;
/*    */   }
/*    */   
/*    */   public boolean load()
/*    */   {
/* 21 */     CCRenderState.pipeline.addDependency(CCRenderState.sideAttrib);
/* 22 */     CCRenderState.pipeline.addDependency(CCRenderState.colourAttrib);
/* 23 */     return true;
/*    */   }
/*    */   
/*    */   public void operate()
/*    */   {
/* 28 */     CCRenderState.setColour(ColourRGBA.multiply(CCRenderState.colour, this.colours[CCRenderState.side]));
/*    */   }
/*    */   
/*    */   public int operationID()
/*    */   {
/* 33 */     return LightModel.operationIndex;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\codechicken\lib\lighting\PlanarLightModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */