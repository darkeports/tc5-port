/*    */ package thaumcraft.common.entities.ai.combat;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.ai.EntityAITarget;
/*    */ 
/*    */ 
/*    */ public class AIOldestAttackableTargetSorter
/*    */   implements Comparator
/*    */ {
/*    */   private Entity theEntity;
/*    */   final EntityAITarget parent;
/*    */   
/*    */   public AIOldestAttackableTargetSorter(EntityAITarget par1EntityAIOldestAttackableTarget, Entity par2Entity)
/*    */   {
/* 16 */     this.parent = par1EntityAIOldestAttackableTarget;
/* 17 */     this.theEntity = par2Entity;
/*    */   }
/*    */   
/*    */   public int compareAge(Entity par1Entity, Entity par2Entity)
/*    */   {
/* 22 */     int var3 = par1Entity.ticksExisted;
/* 23 */     int var5 = par2Entity.ticksExisted;
/* 24 */     return var3 < var5 ? 1 : var3 > var5 ? -1 : 0;
/*    */   }
/*    */   
/*    */   public int compare(Object par1Obj, Object par2Obj)
/*    */   {
/* 29 */     return compareAge((Entity)par1Obj, (Entity)par2Obj);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\ai\combat\AIOldestAttackableTargetSorter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */