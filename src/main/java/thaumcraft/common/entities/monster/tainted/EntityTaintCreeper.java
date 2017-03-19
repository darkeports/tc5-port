/*     */ package thaumcraft.common.entities.monster.tainted;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.entities.ITaintedMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
/*     */ import thaumcraft.common.entities.ai.combat.AICreeperSwell;
/*     */ 
/*     */ public class EntityTaintCreeper
/*     */   extends EntityMob
/*     */   implements ITaintedMob
/*     */ {
/*     */   private int lastActiveTime;
/*     */   private int timeSinceIgnited;
/*  46 */   private int fuseTime = 30;
/*     */   
/*     */ 
/*  49 */   private int explosionRadius = 3;
/*     */   
/*     */   public EntityTaintCreeper(World par1World)
/*     */   {
/*  53 */     super(par1World);
/*  54 */     this.tasks.addTask(1, new EntityAISwimming(this));
/*  55 */     this.tasks.addTask(2, new AICreeperSwell(this));
/*  56 */     this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
/*  57 */     this.tasks.addTask(4, new AIAttackOnCollide(this, 1.0D, false));
/*  58 */     this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
/*  59 */     this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
/*  60 */     this.tasks.addTask(6, new EntityAILookIdle(this));
/*  61 */     this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  62 */     this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  68 */     super.applyEntityAttributes();
/*  69 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*  70 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
/*  71 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
/*  72 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/*  78 */     return !ITaintedMob.class.isAssignableFrom(clazz);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/*  84 */     return ((otherEntity instanceof ITaintedMob)) || (isOnTeam(otherEntity.getTeam()));
/*     */   }
/*     */   
/*     */ 
/*     */   public void fall(float distance, float damageMultiplier)
/*     */   {
/*  90 */     super.fall(distance, damageMultiplier);
/*  91 */     this.timeSinceIgnited = ((int)(this.timeSinceIgnited + distance * 1.5F));
/*     */     
/*  93 */     if (this.timeSinceIgnited > this.fuseTime - 5)
/*     */     {
/*  95 */       this.timeSinceIgnited = (this.fuseTime - 5);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 102 */     super.writeEntityToNBT(par1NBTTagCompound);
/*     */     
/* 104 */     if (this.dataWatcher.getWatchableObjectByte(17) == 1)
/*     */     {
/* 106 */       par1NBTTagCompound.setBoolean("powered", true);
/*     */     }
/*     */     
/* 109 */     par1NBTTagCompound.setShort("Fuse", (short)this.fuseTime);
/* 110 */     par1NBTTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 117 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 118 */     this.dataWatcher.updateObject(17, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));
/*     */     
/* 120 */     if (par1NBTTagCompound.hasKey("Fuse"))
/*     */     {
/* 122 */       this.fuseTime = par1NBTTagCompound.getShort("Fuse");
/*     */     }
/*     */     
/* 125 */     if (par1NBTTagCompound.hasKey("ExplosionRadius"))
/*     */     {
/* 127 */       this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/* 134 */     super.entityInit();
/* 135 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)-1));
/* 136 */     this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 143 */     return ItemsTC.tainted;
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 148 */     if (this.worldObj.rand.nextBoolean()) {
/* 149 */       entityDropItem(new ItemStack(ItemsTC.tainted, 1, 0), this.height / 2.0F);
/*     */     } else {
/* 151 */       entityDropItem(new ItemStack(ItemsTC.tainted, 1, 1), this.height / 2.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 160 */     if (isEntityAlive())
/*     */     {
/* 162 */       this.lastActiveTime = this.timeSinceIgnited;
/* 163 */       int var1 = getCreeperState();
/*     */       
/* 165 */       if ((var1 > 0) && (this.timeSinceIgnited == 0))
/*     */       {
/* 167 */         this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
/*     */       }
/*     */       
/* 170 */       this.timeSinceIgnited += var1;
/*     */       
/* 172 */       if (this.timeSinceIgnited < 0)
/*     */       {
/* 174 */         this.timeSinceIgnited = 0;
/*     */       }
/*     */       
/* 177 */       if (this.timeSinceIgnited >= 30)
/*     */       {
/* 179 */         this.timeSinceIgnited = 30;
/*     */         
/* 181 */         if (!this.worldObj.isRemote)
/*     */         {
/* 183 */           this.worldObj.createExplosion(this, this.posX, this.posY + this.height / 2.0F, this.posZ, 1.5F, false);
/* 184 */           List ents = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(6.0D, 6.0D, 6.0D));
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 189 */           if (ents.size() > 0) {
/* 190 */             for (Object ent : ents) {
/* 191 */               EntityLivingBase el = (EntityLivingBase)ent;
/* 192 */               if ((!(el instanceof ITaintedMob)) && (!el.isEntityUndead())) {
/* 193 */                 el.addPotionEffect(new PotionEffect(PotionFluxTaint.instance.getId(), 100, 0, false, true));
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 198 */           int x = (int)this.posX;
/* 199 */           int y = (int)this.posY;
/* 200 */           int z = (int)this.posZ;
/* 201 */           for (int a = 0; a < 10; a++) {
/* 202 */             int xx = x + (int)((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
/* 203 */             int zz = z + (int)((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
/* 204 */             if (this.worldObj.rand.nextBoolean()) {
/* 205 */               BlockPos bp = new BlockPos(xx, y, zz);
/* 206 */               IBlockState bs = this.worldObj.getBlockState(bp);
/* 207 */               if ((this.worldObj.isBlockNormalCube(bp.down(), false)) && (bs.getBlock().isReplaceable(this.worldObj, bp)))
/*     */               {
/* 209 */                 this.worldObj.setBlockState(bp, BlocksTC.taintFibre.getDefaultState());
/*     */               }
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 215 */           setDead();
/*     */         } else {
/* 217 */           for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(100); a++) { Thaumcraft.proxy.getFX().taintsplosionFX(this);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 222 */     super.onUpdate();
/*     */   }
/*     */   
/*     */   public void onLivingUpdate()
/*     */   {
/* 227 */     super.onLivingUpdate();
/* 228 */     if ((this.worldObj.isRemote) && (this.ticksExisted < 5)) {
/* 229 */       for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(10); a++)
/* 230 */         Thaumcraft.proxy.getFX().splooshFX(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getCreeperFlashIntensity(float par1) {
/* 235 */     return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / 28.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 244 */     return "mob.creeper.say";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 253 */     return "mob.creeper.death";
/*     */   }
/*     */   
/*     */   protected float getSoundPitch()
/*     */   {
/* 258 */     return 0.7F;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityAsMob(Entity par1Entity)
/*     */   {
/* 264 */     return true;
/*     */   }
/*     */   
/*     */   public int getCreeperState()
/*     */   {
/* 269 */     return this.dataWatcher.getWatchableObjectByte(16);
/*     */   }
/*     */   
/*     */   public void setCreeperState(int par1)
/*     */   {
/* 274 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\tainted\EntityTaintCreeper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */