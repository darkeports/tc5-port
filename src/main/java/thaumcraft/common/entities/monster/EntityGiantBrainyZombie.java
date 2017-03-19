/*    */ package thaumcraft.common.entities.monster;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.DataWatcher;
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityGiantBrainyZombie extends EntityBrainyZombie
/*    */ {
/*    */   public EntityGiantBrainyZombie(World world)
/*    */   {
/* 15 */     super(world);
/* 16 */     this.experienceValue = 15;
/*    */     
/* 18 */     setSize(this.width * (1.2F + getAnger()), this.height * (1.2F + getAnger()));
/*    */     
/* 20 */     this.tasks.addTask(2, new net.minecraft.entity.ai.EntityAILeapAtTarget(this, 0.4F));
/*    */   }
/*    */   
/*    */ 
/*    */   public void onLivingUpdate()
/*    */   {
/* 26 */     super.onLivingUpdate();
/* 27 */     if (getAnger() > 1.0F) {
/* 28 */       setAnger(getAnger() - 0.002F);
/* 29 */       setSize(0.6F * (1.2F + getAnger()), 1.8F * (1.2F + getAnger()));
/*    */     }
/*    */     
/* 32 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0F + (getAnger() - 1.0F) * 5.0F);
/*    */   }
/*    */   
/*    */ 
/*    */   protected void entityInit()
/*    */   {
/* 38 */     super.entityInit();
/* 39 */     this.dataWatcher.addObject(20, new Float(1.0F));
/*    */   }
/*    */   
/*    */   public float getAnger()
/*    */   {
/* 44 */     return this.dataWatcher.getWatchableObjectFloat(20);
/*    */   }
/*    */   
/*    */   public void setAnger(float par1)
/*    */   {
/* 49 */     this.dataWatcher.updateObject(20, Float.valueOf(par1));
/*    */   }
/*    */   
/*    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*    */   {
/* 54 */     setAnger(Math.min(2.0F, getAnger() + 0.1F));
/* 55 */     return super.attackEntityFrom(par1DamageSource, par2);
/*    */   }
/*    */   
/*    */ 
/*    */   protected void applyEntityAttributes()
/*    */   {
/* 61 */     super.applyEntityAttributes();
/* 62 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
/* 63 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
/*    */   }
/*    */   
/*    */ 
/*    */   protected void dropFewItems(boolean flag, int i)
/*    */   {
/* 69 */     for (int a = 0; a < 6; a++) if (this.worldObj.rand.nextBoolean()) dropItem(Items.rotten_flesh, 2);
/* 70 */     for (int a = 0; a < 6; a++) if (this.worldObj.rand.nextBoolean()) dropItem(Items.rotten_flesh, 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\EntityGiantBrainyZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */