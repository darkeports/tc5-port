/*    */ package thaumcraft.common.entities.monster.cult;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.entity.ai.EntityAILookIdle;
/*    */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*    */ import net.minecraft.entity.ai.EntityAIOpenDoor;
/*    */ import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
/*    */ import net.minecraft.entity.ai.EntityAISwimming;
/*    */ import net.minecraft.entity.ai.EntityAITasks;
/*    */ import net.minecraft.entity.ai.EntityAIWander;
/*    */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.DifficultyInstance;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
/*    */ import thaumcraft.common.entities.ai.combat.AICultistHurtByTarget;
/*    */ 
/*    */ public class EntityCultistKnight extends EntityCultist
/*    */ {
/*    */   public EntityCultistKnight(World p_i1745_1_)
/*    */   {
/* 30 */     super(p_i1745_1_);
/* 31 */     this.tasks.addTask(0, new EntityAISwimming(this));
/* 32 */     this.tasks.addTask(3, new AIAttackOnCollide(this, EntityLivingBase.class, 1.0D, false));
/* 33 */     this.tasks.addTask(4, new EntityAIRestrictOpenDoor(this));
/* 34 */     this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
/* 35 */     this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.8D));
/* 36 */     this.tasks.addTask(7, new EntityAIWander(this, 0.8D));
/* 37 */     this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/* 38 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/* 39 */     this.targetTasks.addTask(1, new AICultistHurtByTarget(this, true));
/* 40 */     this.targetTasks.addTask(2, new net.minecraft.entity.ai.EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void applyEntityAttributes()
/*    */   {
/* 48 */     super.applyEntityAttributes();
/* 49 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void func_180481_a(DifficultyInstance diff)
/*    */   {
/* 58 */     setCurrentItemOrArmor(4, new ItemStack(ItemsTC.crimsonPlateHelm));
/* 59 */     setCurrentItemOrArmor(3, new ItemStack(ItemsTC.crimsonPlateChest));
/* 60 */     setCurrentItemOrArmor(2, new ItemStack(ItemsTC.crimsonPlateLegs));
/* 61 */     setCurrentItemOrArmor(1, new ItemStack(ItemsTC.crimsonBoots));
/*    */     
/* 63 */     if (this.rand.nextFloat() < (this.worldObj.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F))
/*    */     {
/* 65 */       int i = this.rand.nextInt(5);
/* 66 */       if (i == 0)
/*    */       {
/* 68 */         setCurrentItemOrArmor(0, new ItemStack(ItemsTC.voidSword));
/* 69 */         setCurrentItemOrArmor(4, new ItemStack(ItemsTC.crimsonRobeHelm));
/*    */       }
/*    */       else
/*    */       {
/* 73 */         setCurrentItemOrArmor(0, new ItemStack(ItemsTC.thaumiumSword));
/* 74 */         if (this.rand.nextBoolean()) {
/* 75 */           setCurrentItemOrArmor(4, null);
/*    */         }
/*    */       }
/*    */     } else {
/* 79 */       setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance diff)
/*    */   {
/* 86 */     float f = diff.getClampedAdditionalDifficulty();
/* 87 */     if ((getHeldItem() != null) && (this.rand.nextFloat() < 0.25F * f))
/*    */     {
/* 89 */       EnchantmentHelper.addRandomEnchantment(this.rand, getHeldItem(), (int)(5.0F + f * this.rand.nextInt(18)));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\cult\EntityCultistKnight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */