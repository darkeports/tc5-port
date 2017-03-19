/*     */ package thaumcraft.common.entities.monster;
/*     */ 
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMindSpider extends EntitySpider implements thaumcraft.api.entities.IEldritchMob
/*     */ {
/*     */   public EntityMindSpider(World par1World)
/*     */   {
/*  18 */     super(par1World);
/*  19 */     setSize(0.7F, 0.5F);
/*  20 */     this.experienceValue = 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/*  26 */     return 0.45F;
/*     */   }
/*     */   
/*  29 */   private int lifeSpan = Integer.MAX_VALUE;
/*     */   
/*     */   protected int getExperiencePoints(EntityPlayer p_70693_1_)
/*     */   {
/*  33 */     return isHarmless() ? 0 : super.getExperiencePoints(p_70693_1_);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  39 */     super.applyEntityAttributes();
/*  40 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
/*  41 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  47 */     super.entityInit();
/*  48 */     this.dataWatcher.addObject(22, new Byte((byte)0));
/*  49 */     this.dataWatcher.addObject(23, new String(""));
/*     */   }
/*     */   
/*     */   public String getViewer()
/*     */   {
/*  54 */     return this.dataWatcher.getWatchableObjectString(23);
/*     */   }
/*     */   
/*     */   public void setViewer(String player) {
/*  58 */     this.dataWatcher.updateObject(23, String.valueOf(player));
/*     */   }
/*     */   
/*     */   public boolean isHarmless()
/*     */   {
/*  63 */     return this.dataWatcher.getWatchableObjectByte(22) != 0;
/*     */   }
/*     */   
/*     */   public void setHarmless(boolean h) {
/*  67 */     if (h) this.lifeSpan = 1200;
/*  68 */     this.dataWatcher.updateObject(22, Byte.valueOf((byte)(h ? 1 : 0)));
/*     */   }
/*     */   
/*     */   protected float getSoundPitch()
/*     */   {
/*  73 */     return 0.7F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  80 */     super.onUpdate();
/*  81 */     if ((!this.worldObj.isRemote) && (this.ticksExisted > this.lifeSpan)) { setDead();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/*  88 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {}
/*     */   
/*     */ 
/*     */   public boolean doesEntityNotTriggerPressurePlate()
/*     */   {
/*  98 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/* 104 */     return false;
/*     */   }
/*     */   
/*     */   public boolean attackEntityAsMob(net.minecraft.entity.Entity p_70652_1_)
/*     */   {
/* 109 */     if (isHarmless()) {
/* 110 */       return false;
/*     */     }
/* 112 */     return super.attackEntityAsMob(p_70652_1_);
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 118 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 119 */     this.dataWatcher.updateObject(22, Byte.valueOf(par1NBTTagCompound.getByte("harmless")));
/* 120 */     this.dataWatcher.updateObject(23, String.valueOf(par1NBTTagCompound.getString("viewer")));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 126 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 127 */     par1NBTTagCompound.setByte("harmless", this.dataWatcher.getWatchableObjectByte(22));
/* 128 */     par1NBTTagCompound.setString("viewer", this.dataWatcher.getWatchableObjectString(23));
/*     */   }
/*     */   
/*     */ 
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
/*     */   {
/* 134 */     return p_180482_2_;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\EntityMindSpider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */