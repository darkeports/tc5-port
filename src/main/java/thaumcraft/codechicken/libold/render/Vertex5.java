/*    */ package thaumcraft.codechicken.libold.render;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.MathContext;
/*    */ import java.math.RoundingMode;

import thaumcraft.codechicken.libold.render.uv.UV;
import thaumcraft.codechicken.libold.render.uv.UVTransformation;
import thaumcraft.codechicken.libold.util.Copyable;
import thaumcraft.codechicken.libold.vec.Transformation;
import thaumcraft.codechicken.libold.vec.Vector3;
/*    */ 
/*    */ 
/*    */ public class Vertex5
/*    */   implements Copyable<Vertex5>
/*    */ {
/*    */   public Vector3 vec;
/*    */   public UV uv;
/*    */   
/*    */   public Vertex5()
/*    */   {
/* 21 */     this(new Vector3(), new UV());
/*    */   }
/*    */   
/*    */   public Vertex5(Vector3 vert, UV uv)
/*    */   {
/* 26 */     this.vec = vert;
/* 27 */     this.uv = uv;
/*    */   }
/*    */   
/*    */   public Vertex5(Vector3 vert, double u, double v)
/*    */   {
/* 32 */     this(vert, new UV(u, v));
/*    */   }
/*    */   
/*    */   public Vertex5(double x, double y, double z, double u, double v)
/*    */   {
/* 37 */     this(new Vector3(x, y, z), new UV(u, v));
/*    */   }
/*    */   
/*    */   public Vertex5 set(double x, double y, double z, double u, double v)
/*    */   {
/* 42 */     this.vec.set(x, y, z);
/* 43 */     this.uv.set(u, v);
/* 44 */     return this;
/*    */   }
/*    */   
/*    */   public Vertex5 set(double x, double y, double z, double u, double v, int tex)
/*    */   {
/* 49 */     this.vec.set(x, y, z);
/* 50 */     this.uv.set(u, v, tex);
/* 51 */     return this;
/*    */   }
/*    */   
/*    */   public Vertex5 set(Vertex5 vert) {
/* 55 */     this.vec.set(vert.vec);
/* 56 */     this.uv.set(vert.uv);
/* 57 */     return this;
/*    */   }
/*    */   
/*    */   public Vertex5(Vertex5 vertex5)
/*    */   {
/* 62 */     this(vertex5.vec.copy(), vertex5.uv.copy());
/*    */   }
/*    */   
/*    */   public Vertex5 copy()
/*    */   {
/* 67 */     return new Vertex5(this);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 72 */     MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
/* 73 */     return "Vertex: (" + new BigDecimal(this.vec.x, cont) + ", " + new BigDecimal(this.vec.y, cont) + ", " + new BigDecimal(this.vec.z, cont) + ") (" + new BigDecimal(this.uv.u, cont) + ", " + new BigDecimal(this.uv.v, cont) + ")";
/*    */   }
/*    */   
/*    */   public Vertex5 apply(Transformation t)
/*    */   {
/* 78 */     this.vec.apply(t);
/* 79 */     return this;
/*    */   }
/*    */   
/*    */   public Vertex5 apply(UVTransformation t)
/*    */   {
/* 84 */     this.uv.apply(t);
/* 85 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\codechicken\lib\render\Vertex5.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */