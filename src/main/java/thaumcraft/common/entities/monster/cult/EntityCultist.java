/*     */ package thaumcraft.common.entities.monster.cult;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.monster.boss.EntityCultistLeader;
/*     */ 
/*     */ public class EntityCultist extends EntityMob
/*     */ {
/*     */   public EntityCultist(World p_i1745_1_)
/*     */   {
/*  26 */     super(p_i1745_1_);
/*  27 */     setSize(0.6F, 1.8F);
/*  28 */     this.experienceValue = 10;
/*  29 */     ((PathNavigateGround)getNavigator()).setBreakDoors(true);
/*     */     
/*  31 */     for (int i = 0; i < this.equipmentDropChances.length; i++)
/*     */     {
/*  33 */       this.equipmentDropChances[i] = 0.05F;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  40 */     super.applyEntityAttributes();
/*  41 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
/*  42 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
/*  43 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  49 */     super.entityInit();
/*     */   }
/*     */   
/*     */   public boolean canPickUpLoot()
/*     */   {
/*  54 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean isValidLightLevel()
/*     */   {
/*  59 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/*  65 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/*  70 */     int r = this.rand.nextInt(10);
/*  71 */     if (r <= 1) {
/*  72 */       entityDropItem(new ItemStack(ItemsTC.voidSeed), 1.5F);
/*     */     }
/*  74 */     else if (r <= 3 + i) {
/*  75 */       entityDropItem(new ItemStack(ItemsTC.coin), 1.5F);
/*     */     }
/*  77 */     super.dropFewItems(flag, i);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void addRandomDrop()
/*     */   {
/*  83 */     entityDropItem(new ItemStack(ItemsTC.knowledgeFragment), 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void func_180481_a(DifficultyInstance diff) {}
/*     */   
/*     */ 
/*     */   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance diff) {}
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance diff, IEntityLivingData data)
/*     */   {
/*  94 */     func_180481_a(diff);
/*  95 */     setEnchantmentBasedOnDifficulty(diff);
/*     */     
/*  97 */     return super.onInitialSpawn(diff, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean canDespawn()
/*     */   {
/* 106 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/* 115 */     super.readEntityFromNBT(nbt);
/* 116 */     if (nbt.hasKey("HomeD")) {
/* 117 */       setHomePosAndDistance(new BlockPos(nbt.getInteger("HomeX"), nbt.getInteger("HomeY"), nbt.getInteger("HomeZ")), nbt.getInteger("HomeD"));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/* 124 */     super.writeEntityToNBT(nbt);
/* 125 */     if ((getHomePosition() != null) && (getMaximumHomeDistance() > 0.0F)) {
/* 126 */       nbt.setInteger("HomeD", (int)getMaximumHomeDistance());
/* 127 */       nbt.setInteger("HomeX", getHomePosition().getX());
/* 128 */       nbt.setInteger("HomeY", getHomePosition().getY());
/* 129 */       nbt.setInteger("HomeZ", getHomePosition().getZ());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase el)
/*     */   {
/* 136 */     return ((el instanceof EntityCultist)) || ((el instanceof EntityCultistLeader));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/* 142 */     if ((clazz == EntityCultistCleric.class) || (clazz == EntityCultistLeader.class) || (clazz == EntityCultistKnight.class))
/*     */     {
/*     */ 
/* 145 */       return false; }
/* 146 */     return super.canAttackClass(clazz);
/*     */   }
/*     */   
/*     */   public void spawnExplosionParticle()
/*     */   {
/* 151 */     if (this.worldObj.isRemote) {
/* 152 */       for (int i = 0; i < 20; i++)
/*     */       {
/* 154 */         double d0 = this.rand.nextGaussian() * 0.05D;
/* 155 */         double d1 = this.rand.nextGaussian() * 0.05D;
/* 156 */         double d2 = this.rand.nextGaussian() * 0.05D;
/* 157 */         double d3 = 2.0D;
/*     */         
/* 159 */         Thaumcraft.proxy.getFX().cultistSpawn(this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width + d0 * d3, this.posY + this.rand.nextFloat() * this.height + d1 * d3, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width + d2 * d3, d0, d1, d2);
/*     */       }
/*     */       
/*     */     }
/*     */     else
/*     */     {
/* 165 */       this.worldObj.setEntityState(this, (byte)20);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\cult\EntityCultist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */