/*    */ package thaumcraft.codechicken.libold.vec;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ITransformation<Vector, Transformation extends ITransformation>
/*    */ {
/*    */   public abstract void apply(Vector paramVector);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract Transformation at(Vector paramVector);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract Transformation with(Transformation paramTransformation);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Transformation merge(Transformation next)
/*    */   {
/* 31 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isRedundant()
/*    */   {
/* 38 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public abstract Transformation inverse();
/*    */   
/*    */ 
/*    */   public Transformation $plus$plus(Transformation t)
/*    */   {
/* 47 */     return with(t);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\codechicken\lib\vec\ITransformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */