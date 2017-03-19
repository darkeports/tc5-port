/*     */ package thaumcraft.common.entities;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTaintSource
/*     */   extends Entity
/*     */ {
/*  16 */   int lifespan = 100;
/*     */   
/*     */   public EntityTaintSource(World par1World)
/*     */   {
/*  20 */     super(par1World);
/*  21 */     this.ignoreFrustumCheck = true;
/*  22 */     this.renderDistanceWeight = 10.0D;
/*  23 */     this.preventEntitySpawning = true;
/*  24 */     this.motionX = 0.0D;
/*  25 */     this.motionY = 0.0D;
/*  26 */     this.motionZ = 0.0D;
/*  27 */     this.isImmuneToFire = true;
/*  28 */     setSize(0.98F, 0.98F);
/*     */   }
/*     */   
/*     */   public EntityTaintSource(World par1World, BlockPos pos, int lifespan)
/*     */   {
/*  33 */     this(par1World);
/*  34 */     setPosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
/*  35 */     this.prevPosX = (pos.getX() + 0.5D);
/*  36 */     this.prevPosY = (pos.getY() + 0.5D);
/*  37 */     this.prevPosZ = (pos.getZ() + 0.5D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/*  45 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void entityInit() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/*  58 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  66 */     super.onUpdate();
/*  67 */     if ((!this.worldObj.isRemote) && (this.lifespan-- < 0)) {
/*  68 */       setDead();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource source, float amount)
/*     */   {
/*  75 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canBePushed()
/*     */   {
/*  80 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canAttackWithItem()
/*     */   {
/*  85 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */   
/*     */ 
/*     */   protected void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/*  94 */     nbt.setInteger("lifespan", this.lifespan);
/*     */   }
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/*  99 */     this.lifespan = nbt.getInteger("lifespan");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean canRenderOnFire()
/*     */   {
/* 107 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\EntityTaintSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */