/*    */ package thaumcraft.common.entities.ai.combat;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*    */ import thaumcraft.common.entities.monster.EntityPech;
/*    */ 
/*    */ public class AINearestAttackableTargetPech
/*    */   extends EntityAINearestAttackableTarget
/*    */ {
/*    */   public AINearestAttackableTargetPech(EntityCreature p_i45878_1_, Class p_i45878_2_, boolean p_i45878_3_)
/*    */   {
/* 12 */     super(p_i45878_1_, p_i45878_2_, p_i45878_3_);
/*    */   }
/*    */   
/*    */   public boolean shouldExecute()
/*    */   {
/* 17 */     if (((this.taskOwner instanceof EntityPech)) && (((EntityPech)this.taskOwner).getAnger() == 0))
/* 18 */       return false;
/* 19 */     return super.shouldExecute();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\ai\combat\AINearestAttackableTargetPech.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */