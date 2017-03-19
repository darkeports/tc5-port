/*    */ package thaumcraft.common.lib;
/*    */ 
/*    */ import net.minecraft.block.Block.SoundType;
/*    */ 
/*    */ public class CustomSoundType extends Block.SoundType
/*    */ {
/*    */   public CustomSoundType(String par1Str, float par2, float par3)
/*    */   {
/*  9 */     super(par1Str, par2, par3);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getBreakSound()
/*    */   {
/* 15 */     return "thaumcraft:" + this.soundName;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getStepSound()
/*    */   {
/* 21 */     return getBreakSound();
/*    */   }
/*    */   
/*    */ 
/*    */   public String getPlaceSound()
/*    */   {
/* 27 */     return getBreakSound();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\CustomSoundType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */