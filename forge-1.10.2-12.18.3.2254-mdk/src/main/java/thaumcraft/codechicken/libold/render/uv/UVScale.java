/*    */ package thaumcraft.codechicken.libold.render.uv;
/*    */ 
/*    */ import java.math.MathContext;
/*    */ 
/*    */ public class UVScale extends UVTransformation
/*    */ {
/*    */   double su;
/*    */   double sv;
/*    */   
/*    */   public UVScale(double scaleu, double scalev)
/*    */   {
/* 12 */     this.su = scaleu;
/* 13 */     this.sv = scalev;
/*    */   }
/*    */   
/*    */   public UVScale(double d) {
/* 17 */     this(d, d);
/*    */   }
/*    */   
/*    */   public void apply(UV uv)
/*    */   {
/* 22 */     uv.u *= this.su;
/* 23 */     uv.v *= this.sv;
/*    */   }
/*    */   
/*    */   public UVTransformation inverse()
/*    */   {
/* 28 */     return new UVScale(1.0D / this.su, 1.0D / this.sv);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 33 */     MathContext cont = new MathContext(4, java.math.RoundingMode.HALF_UP);
/* 34 */     return "UVScale(" + new java.math.BigDecimal(this.su, cont) + ", " + new java.math.BigDecimal(this.sv, cont) + ")";
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\codechicken\lib\render\uv\UVScale.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */