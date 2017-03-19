/*     */ package thaumcraft.common.entities.ai.pech;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.entities.monster.EntityPech;
/*     */ 
/*     */ public class AIPechItemEntityGoto extends net.minecraft.entity.ai.EntityAIBase
/*     */ {
/*     */   private EntityPech pech;
/*     */   private Entity targetEntity;
/*  19 */   float maxTargetDistance = 16.0F;
/*     */   private int count;
/*     */   private int failedPathFindingPenalty;
/*     */   
/*  23 */   public AIPechItemEntityGoto(EntityPech par1EntityCreature) { this.pech = par1EntityCreature;
/*  24 */     setMutexBits(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  32 */     if (--this.count > 0) { return false;
/*     */     }
/*  34 */     double range = Double.MAX_VALUE;
/*     */     
/*     */ 
/*  37 */     java.util.List<Entity> targets = this.pech.worldObj.getEntitiesWithinAABBExcludingEntity(this.pech, this.pech.getEntityBoundingBox().expand(this.maxTargetDistance, this.maxTargetDistance, this.maxTargetDistance));
/*     */     
/*     */ 
/*  40 */     if (targets.size() == 0) return false;
/*  41 */     for (Entity e : targets) {
/*  42 */       if (((e instanceof EntityItem)) && (this.pech.canPickup(((EntityItem)e).getEntityItem())))
/*     */       {
/*  44 */         net.minecraft.nbt.NBTTagCompound itemData = ((EntityItem)e).getEntityData();
/*  45 */         String username = ((EntityItem)e).getThrower();
/*  46 */         if ((username == null) || (!username.equals("PechDrop")))
/*     */         {
/*  48 */           double distance = e.getDistanceSq(this.pech.posX, this.pech.posY, this.pech.posZ);
/*  49 */           if ((distance < range) && (distance <= this.maxTargetDistance * this.maxTargetDistance)) {
/*  50 */             range = distance;
/*  51 */             this.targetEntity = e;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  56 */     if (this.targetEntity == null)
/*     */     {
/*  58 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  63 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean continueExecuting()
/*     */   {
/*  73 */     return this.targetEntity != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resetTask()
/*     */   {
/*  83 */     this.targetEntity = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/*  94 */     this.pech.getNavigator().setPath(this.pech.getNavigator().getPathToEntityLiving(this.targetEntity), this.pech.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() * 1.5D);
/*     */     
/*  96 */     this.count = 0;
/*     */   }
/*     */   
/*     */   public void updateTask()
/*     */   {
/* 101 */     this.pech.getLookHelper().setLookPositionWithEntity(this.targetEntity, 30.0F, 30.0F);
/*     */     
/* 103 */     if ((this.pech.getEntitySenses().canSee(this.targetEntity)) && (--this.count <= 0))
/*     */     {
/* 105 */       this.count = (this.failedPathFindingPenalty + 4 + this.pech.getRNG().nextInt(4));
/* 106 */       this.pech.getNavigator().tryMoveToEntityLiving(this.targetEntity, this.pech.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() * 1.5D);
/* 107 */       if (this.pech.getNavigator().getPath() != null)
/*     */       {
/* 109 */         PathPoint finalPathPoint = this.pech.getNavigator().getPath().getFinalPathPoint();
/* 110 */         if ((finalPathPoint != null) && (this.targetEntity.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1.0D))
/*     */         {
/* 112 */           this.failedPathFindingPenalty = 0;
/*     */         }
/*     */         else
/*     */         {
/* 116 */           this.failedPathFindingPenalty += 10;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 121 */         this.failedPathFindingPenalty += 10;
/*     */       }
/*     */     }
/* 124 */     double distance = this.pech.getDistanceSq(this.targetEntity.posX, this.targetEntity.getEntityBoundingBox().minY, this.targetEntity.posZ);
/*     */     
/*     */ 
/*     */ 
/* 128 */     if (distance <= 1.5D)
/*     */     {
/* 130 */       this.count = 0;
/*     */       
/* 132 */       int am = ((EntityItem)this.targetEntity).getEntityItem().stackSize;
/* 133 */       ItemStack is = this.pech.pickupItem(((EntityItem)this.targetEntity).getEntityItem());
/* 134 */       if ((is != null) && (is.stackSize > 0)) {
/* 135 */         ((EntityItem)this.targetEntity).setEntityItemStack(is);
/*     */       } else {
/* 137 */         this.targetEntity.setDead();
/*     */       }
/* 139 */       if ((is == null) || (is.stackSize != am)) {
/* 140 */         this.targetEntity.worldObj.playSoundAtEntity(this.targetEntity, "random.pop", 0.2F, ((this.targetEntity.worldObj.rand.nextFloat() - this.targetEntity.worldObj.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\ai\pech\AIPechItemEntityGoto.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */