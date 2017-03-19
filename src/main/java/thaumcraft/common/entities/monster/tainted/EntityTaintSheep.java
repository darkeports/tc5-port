/*     */ package thaumcraft.common.entities.monster.tainted;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.IShearable;
/*     */ import thaumcraft.api.entities.ITaintedMob;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
/*     */ import thaumcraft.common.entities.ai.misc.AIConvertGrass;
/*     */ 
/*     */ public class EntityTaintSheep extends EntityMob implements IShearable, ITaintedMob
/*     */ {
/*     */   private int sheepTimer;
/*  44 */   private AIConvertGrass field_48137_c = new AIConvertGrass(this);
/*     */   
/*     */   public EntityTaintSheep(World par1World)
/*     */   {
/*  48 */     super(par1World);
/*  49 */     setSize(0.9F, 1.3F);
/*  50 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  51 */     this.tasks.addTask(0, new EntityAISwimming(this));
/*  52 */     this.tasks.addTask(2, this.field_48137_c);
/*  53 */     this.tasks.addTask(3, new AIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  54 */     this.tasks.addTask(3, new AIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
/*  55 */     this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
/*  56 */     this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
/*  57 */     this.tasks.addTask(8, new EntityAILookIdle(this));
/*  58 */     this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false, new Class[0]));
/*  59 */     this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  60 */     this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canAttackClass(Class clazz)
/*     */   {
/*  66 */     return !ITaintedMob.class.isAssignableFrom(clazz);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/*  72 */     return ((otherEntity instanceof ITaintedMob)) || (isOnTeam(otherEntity.getTeam()));
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  78 */     super.applyEntityAttributes();
/*  79 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
/*  80 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
/*  81 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getTotalArmorValue()
/*     */   {
/*  91 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void updateAITasks()
/*     */   {
/*  97 */     this.sheepTimer = this.field_48137_c.func_48396_h();
/*  98 */     super.updateAITasks();
/*     */   }
/*     */   
/*     */ 
/*     */   public void onLivingUpdate()
/*     */   {
/* 104 */     if (this.worldObj.isRemote)
/*     */     {
/* 106 */       this.sheepTimer = Math.max(0, this.sheepTimer - 1);
/*     */     }
/*     */     
/* 109 */     super.onLivingUpdate();
/* 110 */     if ((this.worldObj.isRemote) && (this.ticksExisted < 5)) {
/* 111 */       for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(10); a++) {
/* 112 */         Thaumcraft.proxy.getFX().splooshFX(this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/* 118 */     super.entityInit();
/* 119 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 125 */     return ItemsTC.tainted;
/*     */   }
/*     */   
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 130 */     if (this.worldObj.rand.nextInt(3) == 0) {
/* 131 */       if (this.worldObj.rand.nextBoolean()) {
/* 132 */         entityDropItem(new ItemStack(ItemsTC.tainted, 1, 0), this.height / 2.0F);
/*     */       } else {
/* 134 */         entityDropItem(new ItemStack(ItemsTC.tainted, 1, 1), this.height / 2.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte par1) {
/* 140 */     if (par1 == 10)
/*     */     {
/* 142 */       this.sheepTimer = 40;
/*     */     }
/*     */     else
/*     */     {
/* 146 */       super.handleStatusUpdate(par1);
/*     */     }
/*     */   }
/*     */   
/*     */   public float func_44003_c(float par1)
/*     */   {
/* 152 */     return this.sheepTimer < 4 ? (this.sheepTimer - par1) / 4.0F : (this.sheepTimer >= 4) && (this.sheepTimer <= 36) ? 1.0F : this.sheepTimer <= 0 ? 0.0F : -(this.sheepTimer - 40 - par1) / 4.0F;
/*     */   }
/*     */   
/*     */   public float func_44002_d(float par1)
/*     */   {
/* 157 */     if ((this.sheepTimer > 4) && (this.sheepTimer <= 36))
/*     */     {
/* 159 */       float var2 = (this.sheepTimer - 4 - par1) / 32.0F;
/* 160 */       return 0.62831855F + 0.2199115F * MathHelper.sin(var2 * 28.7F);
/*     */     }
/*     */     
/*     */ 
/* 164 */     return this.sheepTimer > 0 ? 0.62831855F : this.rotationPitch / 57.295776F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 172 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 173 */     par1NBTTagCompound.setBoolean("Sheared", getSheared());
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 179 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 180 */     setSheared(par1NBTTagCompound.getBoolean("Sheared"));
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 186 */     return "mob.sheep.say";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 192 */     return "mob.sheep.say";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 198 */     return "mob.sheep.say";
/*     */   }
/*     */   
/*     */ 
/*     */   protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
/*     */   {
/* 204 */     playSound("mob.sheep.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected float getSoundPitch()
/*     */   {
/* 209 */     return 0.7F;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getSheared()
/*     */   {
/* 215 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x10) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSheared(boolean par1)
/*     */   {
/* 221 */     byte var2 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 223 */     if (par1)
/*     */     {
/* 225 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x10)));
/*     */     }
/*     */     else
/*     */     {
/* 229 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFEF)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
/*     */   {
/* 238 */     return !getSheared();
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
/*     */   {
/* 244 */     ArrayList<ItemStack> ret = new ArrayList();
/* 245 */     setSheared(true);
/* 246 */     int i = 1 + this.rand.nextInt(3);
/* 247 */     for (int j = 0; j < i; j++)
/*     */     {
/* 249 */       ret.add(new ItemStack(net.minecraft.init.Blocks.wool, 1, 10));
/*     */     }
/* 251 */     return ret;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityAsMob(Entity victim)
/*     */   {
/* 257 */     if (super.attackEntityAsMob(victim))
/*     */     {
/* 259 */       if ((victim instanceof EntityLivingBase))
/*     */       {
/* 261 */         byte b0 = 0;
/*     */         
/* 263 */         if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL)
/*     */         {
/* 265 */           b0 = 3;
/*     */         }
/* 267 */         else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
/*     */         {
/* 269 */           b0 = 6;
/*     */         }
/*     */         
/* 272 */         if ((b0 > 0) && (this.rand.nextInt(b0 + 1) > 2))
/*     */         {
/* 274 */           ((EntityLivingBase)victim).addPotionEffect(new PotionEffect(PotionFluxTaint.instance.getId(), b0 * 20, 0));
/*     */         }
/*     */       }
/*     */       
/* 278 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 282 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\tainted\EntityTaintSheep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */