/*    */ package thaumcraft.common.entities.monster;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*    */ import net.minecraft.entity.ai.EntityAITasks;
/*    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*    */ import net.minecraft.entity.monster.EntityZombie;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ 
/*    */ public class EntityBrainyZombie extends EntityZombie
/*    */ {
/*    */   protected void applyEntityAttributes()
/*    */   {
/* 17 */     super.applyEntityAttributes();
/* 18 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
/* 19 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
/* 20 */     getEntityAttribute(reinforcementChance).setBaseValue(0.0D);
/*    */   }
/*    */   
/*    */   public EntityBrainyZombie(World world)
/*    */   {
/* 25 */     super(world);
/* 26 */     this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getTotalArmorValue()
/*    */   {
/* 35 */     int var1 = super.getTotalArmorValue() + 3;
/*    */     
/* 37 */     if (var1 > 20)
/*    */     {
/* 39 */       var1 = 20;
/*    */     }
/*    */     
/* 42 */     return var1;
/*    */   }
/*    */   
/*    */   protected void dropFewItems(boolean flag, int i)
/*    */   {
/* 47 */     for (int a = 0; a < 3; a++) if (this.worldObj.rand.nextBoolean()) dropItem(Items.rotten_flesh, 1);
/* 48 */     if (this.worldObj.rand.nextInt(10) - i <= 4) entityDropItem(new net.minecraft.item.ItemStack(ItemsTC.brain), 1.5F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\EntityBrainyZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */