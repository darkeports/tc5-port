/*   */ package thaumcraft.common.blocks;
/*   */ 
/*   */ import com.google.common.base.Predicate;
/*   */ import net.minecraft.block.properties.PropertyDirection;
/*   */ import net.minecraft.util.EnumFacing;
/*   */ 
/*   */ public abstract interface IBlockFacingHorizontal
/*   */ {
/* 9 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate()
/*   */   {
/*   */     public boolean apply(EnumFacing facing)
/*   */     {
/* = */       return (facing != EnumFacing.UP) && (facing != EnumFacing.DOWN);
/*   */     }
/*   */     
/*   */     public boolean apply(Object p_apply_1_) {
/* A */       return apply((EnumFacing)p_apply_1_);
/*   */     }
/* 9 */   });
/*   */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\IBlockFacingHorizontal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */