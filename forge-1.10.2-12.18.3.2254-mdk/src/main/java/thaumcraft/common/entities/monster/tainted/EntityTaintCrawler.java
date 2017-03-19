/*     */ package thaumcraft.common.entities.monster.tainted;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.ThaumcraftMaterials;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.entities.ITaintedMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.world.taint.BlockTaintFibre;
/*     */ import thaumcraft.common.config.Config;
/*     */ 
/*     */ public class EntityTaintCrawler extends EntityMob implements ITaintedMob
/*     */ {
/*     */   public EntityTaintCrawler(World par1World)
/*     */   {
/*  45 */     super(par1World);
/*  46 */     setSize(0.4F, 0.3F);
/*  47 */     this.experienceValue = 3;
/*  48 */     this.tasks.addTask(1, new net.minecraft.entity.ai.EntityAISwimming(this));
/*  49 */     this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  50 */     this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
/*  51 */     this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  52 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/*  53 */     this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
/*  54 */     this.targetTasks.addTask(2, new net.minecraft.entity.ai.EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/*  60 */     return !ITaintedMob.class.isAssignableFrom(clazz);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/*  66 */     return ((otherEntity instanceof ITaintedMob)) || (isOnTeam(otherEntity.getTeam()));
/*     */   }
/*     */   
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/*  72 */     return 0.1F;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  78 */     super.applyEntityAttributes();
/*  79 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*  80 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.275D);
/*  81 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
/*     */   }
/*     */   
/*     */   protected float getSoundPitch()
/*     */   {
/*  86 */     return 0.7F;
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/*  92 */     return "mob.silverfish.say";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/*  98 */     return "mob.silverfish.hit";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 104 */     return "mob.silverfish.kill";
/*     */   }
/*     */   
/*     */ 
/*     */   protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
/*     */   {
/* 110 */     playSound("mob.silverfish.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/* 116 */     return false;
/*     */   }
/*     */   
/* 119 */   BlockPos lastPos = new BlockPos(0, 0, 0);
/*     */   
/*     */   public void onUpdate()
/*     */   {
/* 123 */     if ((this.worldObj.isRemote) && (this.rand.nextFloat() < 0.05F)) {
/* 124 */       Thaumcraft.proxy.getFX().drawPollutionParticles(getPosition());
/*     */     }
/*     */     
/* 127 */     if ((!this.worldObj.isRemote) && (this.ticksExisted % 5 == 0) && (this.lastPos != getPosition())) {
/* 128 */       this.lastPos = getPosition();
/* 129 */       IBlockState bs = this.worldObj.getBlockState(getPosition());
/* 130 */       Material bm = bs.getBlock().getMaterial();
/*     */       
/* 132 */       if ((!bs.getBlock().isLeaves(this.worldObj, getPosition())) && (!bm.isLiquid()) && (bm != ThaumcraftMaterials.MATERIAL_TAINT) && ((this.worldObj.isAirBlock(getPosition())) || (bs.getBlock().isReplaceable(this.worldObj, getPosition())) || ((bs.getBlock() instanceof BlockFlower)) || ((bs.getBlock() instanceof net.minecraftforge.common.IPlantable))) && (thaumcraft.common.lib.utils.BlockUtils.isAdjacentToSolidBlock(this.worldObj, getPosition())) && (!BlockTaintFibre.isOnlyAdjacentToTaint(this.worldObj, getPosition())))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 138 */         if (this.worldObj.rand.nextFloat() < Config.taintSpreadRate / 3.0F) AuraHelper.drainAura(this.worldObj, getPosition(), Aspect.FLUX, 1);
/* 139 */         this.worldObj.setBlockState(getPosition(), BlocksTC.taintFibre.getDefaultState());
/*     */       }
/*     */     }
/*     */     
/* 143 */     super.onUpdate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean isValidLightLevel()
/*     */   {
/* 150 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumCreatureAttribute getCreatureAttribute()
/*     */   {
/* 156 */     return EnumCreatureAttribute.ARTHROPOD;
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 162 */     return ItemsTC.tainted;
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 167 */     if (this.worldObj.rand.nextInt(8) == 0) {
/* 168 */       if (this.worldObj.rand.nextBoolean()) {
/* 169 */         entityDropItem(new ItemStack(ItemsTC.tainted, 1, 0), this.height / 2.0F);
/*     */       } else {
/* 171 */         entityDropItem(new ItemStack(ItemsTC.tainted, 1, 1), this.height / 2.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_) {
/* 177 */     return p_180482_2_;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityAsMob(Entity victim)
/*     */   {
/* 183 */     if (super.attackEntityAsMob(victim))
/*     */     {
/* 185 */       if ((victim instanceof EntityLivingBase))
/*     */       {
/* 187 */         byte b0 = 0;
/*     */         
/* 189 */         if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL)
/*     */         {
/* 191 */           b0 = 3;
/*     */         }
/* 193 */         else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
/*     */         {
/* 195 */           b0 = 6;
/*     */         }
/*     */         
/* 198 */         if ((b0 > 0) && (this.rand.nextInt(b0 + 1) > 2))
/*     */         {
/* 200 */           ((EntityLivingBase)victim).addPotionEffect(new PotionEffect(PotionFluxTaint.instance.getId(), b0 * 20, 0));
/*     */         }
/*     */       }
/*     */       
/* 204 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 208 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\tainted\EntityTaintCrawler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */