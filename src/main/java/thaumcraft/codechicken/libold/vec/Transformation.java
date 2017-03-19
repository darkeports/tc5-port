/*    */ package thaumcraft.codechicken.libold.vec;
/*    */ 
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.codechicken.lib.render.CCRenderPipeline;
/*    */ import thaumcraft.codechicken.lib.render.CCRenderState;
/*    */ import thaumcraft.codechicken.lib.render.CCRenderState.IVertexOperation;
/*    */ import thaumcraft.codechicken.lib.render.CCRenderState.VertexAttribute;
/*    */ 
/*    */ public abstract class Transformation extends ITransformation<Vector3, Transformation> implements CCRenderState.IVertexOperation
/*    */ {
/* 12 */   public static final int operationIndex = ;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract void applyN(Vector3 paramVector3);
/*    */   
/*    */ 
/*    */ 
/*    */   public abstract void apply(Matrix4 paramMatrix4);
/*    */   
/*    */ 
/*    */ 
/*    */   public Transformation at(Vector3 point)
/*    */   {
/* 27 */     return new TransformationList(new Transformation[] { new Translation(-point.x, -point.y, -point.z), this, point.translation() });
/*    */   }
/*    */   
/*    */   public TransformationList with(Transformation t) {
/* 31 */     return new TransformationList(new Transformation[] { this, t });
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public abstract void glApply();
/*    */   
/*    */   public boolean load()
/*    */   {
/* 39 */     CCRenderState.pipeline.addRequirement(CCRenderState.normalAttrib.operationID());
/* 40 */     return !isRedundant();
/*    */   }
/*    */   
/*    */   public void operate()
/*    */   {
/* 45 */     apply(CCRenderState.vert.vec);
/* 46 */     if (CCRenderState.normalAttrib.active) {
/* 47 */       applyN(CCRenderState.normal);
/*    */     }
/*    */   }
/*    */   
/*    */   public int operationID() {
/* 52 */     return operationIndex;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\codechicken\lib\vec\Transformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */