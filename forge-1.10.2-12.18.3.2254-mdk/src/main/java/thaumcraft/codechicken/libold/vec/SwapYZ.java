/*    */ package thaumcraft.codechicken.libold.vec;
/*    */ 
/*    */ public class SwapYZ extends VariableTransformation
/*    */ {
/*    */   public SwapYZ()
/*    */   {
/*  7 */     super(new Matrix4(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void apply(Vector3 vec)
/*    */   {
/* 17 */     double vz = vec.z;
/* 18 */     vec.z = vec.y;
/* 19 */     vec.y = vz;
/*    */   }
/*    */   
/*    */ 
/*    */   public Transformation inverse()
/*    */   {
/* 25 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\codechicken\lib\vec\SwapYZ.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */