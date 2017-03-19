/*    */ package thaumcraft.common.entities.monster.tainted;
/*    */ 
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.entities.ITaintedMob;
/*    */ 
/*    */ public class EntityTaintacleSmall extends EntityTaintacle implements ITaintedMob
/*    */ {
/* 11 */   int lifetime = 200;
/*    */   
/*    */   public EntityTaintacleSmall(World par1World)
/*    */   {
/* 15 */     super(par1World);
/* 16 */     setSize(0.22F, 1.0F);
/* 17 */     this.experienceValue = 0;
/*    */   }
/*    */   
/*    */   public double getReach()
/*    */   {
/* 22 */     return 0.0D;
/*    */   }
/*    */   
/*    */   protected void applyEntityAttributes()
/*    */   {
/* 27 */     super.applyEntityAttributes();
/* 28 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5.0D);
/* 29 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
/*    */   }
/*    */   
/*    */ 
/*    */   public void onUpdate()
/*    */   {
/* 35 */     super.onUpdate();
/*    */     
/* 37 */     if (this.lifetime-- <= 0) {
/* 38 */       damageEntity(net.minecraft.util.DamageSource.magic, 10.0F);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean getCanSpawnHere()
/*    */   {
/* 45 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   protected Item getDropItem()
/*    */   {
/* 51 */     return Item.getItemById(0);
/*    */   }
/*    */   
/*    */   protected void dropFewItems(boolean flag, int i) {}
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\tainted\EntityTaintacleSmall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */