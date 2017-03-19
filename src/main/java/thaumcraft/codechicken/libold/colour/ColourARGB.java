/*    */ package thaumcraft.codechicken.libold.colour;
/*    */ 
/*    */ public class ColourARGB extends Colour
/*    */ {
/*    */   public ColourARGB(int colour)
/*    */   {
/*  7 */     super(colour >> 16 & 0xFF, colour >> 8 & 0xFF, colour & 0xFF, colour >> 24 & 0xFF);
/*    */   }
/*    */   
/*    */   public ColourARGB(int a, int r, int g, int b)
/*    */   {
/* 12 */     super(r, g, b, a);
/*    */   }
/*    */   
/*    */   public ColourARGB(ColourARGB colour)
/*    */   {
/* 17 */     super(colour);
/*    */   }
/*    */   
/*    */   public ColourARGB copy()
/*    */   {
/* 22 */     return new ColourARGB(this);
/*    */   }
/*    */   
/*    */   public int pack()
/*    */   {
/* 27 */     return pack(this);
/*    */   }
/*    */   
/*    */   public static int pack(Colour colour)
/*    */   {
/* 32 */     return (colour.a & 0xFF) << 24 | (colour.r & 0xFF) << 16 | (colour.g & 0xFF) << 8 | colour.b & 0xFF;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\codechicken\lib\colour\ColourARGB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */