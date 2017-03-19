/*     */ package thaumcraft.common.entities.construct.golem.ai;
/*     */ 
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.entities.construct.EntityOwnedConstruct;
/*     */ 
/*     */ public class AIFollowOwner extends net.minecraft.entity.ai.EntityAIBase
/*     */ {
/*     */   private EntityOwnedConstruct thePet;
/*     */   private EntityLivingBase theOwner;
/*     */   World theWorld;
/*     */   private double field_75336_f;
/*     */   private PathNavigate petPathfinder;
/*     */   private int field_75343_h;
/*     */   float maxDist;
/*     */   float minDist;
/*     */   private boolean field_75344_i;
/*     */   
/*     */   public AIFollowOwner(EntityOwnedConstruct p_i1625_1_, double p_i1625_2_, float p_i1625_4_, float p_i1625_5_)
/*     */   {
/*  26 */     this.thePet = p_i1625_1_;
/*  27 */     this.theWorld = p_i1625_1_.worldObj;
/*  28 */     this.field_75336_f = p_i1625_2_;
/*  29 */     this.petPathfinder = p_i1625_1_.getNavigator();
/*  30 */     this.minDist = p_i1625_4_;
/*  31 */     this.maxDist = p_i1625_5_;
/*  32 */     setMutexBits(3);
/*     */     
/*  34 */     if ((!(p_i1625_1_.getNavigator() instanceof PathNavigateGround)) && (!(p_i1625_1_.getNavigator() instanceof PathNavigateGolemAir)))
/*     */     {
/*  36 */       throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean shouldExecute()
/*     */   {
/*  42 */     EntityLivingBase entitylivingbase = this.thePet.getOwnerEntity();
/*     */     
/*  44 */     if (entitylivingbase == null)
/*     */     {
/*  46 */       return false;
/*     */     }
/*  48 */     if (this.thePet.getDistanceSqToEntity(entitylivingbase) < this.minDist * this.minDist)
/*     */     {
/*  50 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  54 */     this.theOwner = entitylivingbase;
/*  55 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean continueExecuting()
/*     */   {
/*  61 */     return (!this.petPathfinder.noPath()) && (this.thePet.getDistanceSqToEntity(this.theOwner) > this.maxDist * this.maxDist);
/*     */   }
/*     */   
/*     */   public void startExecuting()
/*     */   {
/*  66 */     this.field_75343_h = 0;
/*  67 */     if ((this.thePet.getNavigator() instanceof PathNavigateGround)) {
/*  68 */       this.field_75344_i = ((PathNavigateGround)this.thePet.getNavigator()).getAvoidsWater();
/*  69 */       ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater(false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetTask()
/*     */   {
/*  75 */     this.theOwner = null;
/*  76 */     this.petPathfinder.clearPathEntity();
/*  77 */     if ((this.thePet.getNavigator() instanceof PathNavigateGround)) {
/*  78 */       ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateTask()
/*     */   {
/*  84 */     this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, this.thePet.getVerticalFaceSpeed());
/*     */     
/*  86 */     if (--this.field_75343_h <= 0)
/*     */     {
/*  88 */       this.field_75343_h = 10;
/*     */       
/*  90 */       if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_75336_f))
/*     */       {
/*  92 */         if (!this.thePet.getLeashed())
/*     */         {
/*  94 */           if (this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0D)
/*     */           {
/*  96 */             int i = MathHelper.floor_double(this.theOwner.posX) - 2;
/*  97 */             int j = MathHelper.floor_double(this.theOwner.posZ) - 2;
/*  98 */             int k = MathHelper.floor_double(this.theOwner.getEntityBoundingBox().minY);
/*     */             
/* 100 */             for (int l = 0; l <= 4; l++)
/*     */             {
/* 102 */               for (int i1 = 0; i1 <= 4; i1++)
/*     */               {
/* 104 */                 if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3)) && (World.doesBlockHaveSolidTopSurface(this.theWorld, new BlockPos(i + l, k - 1, j + i1))) && (!this.theWorld.getBlockState(new BlockPos(i + l, k, j + i1)).getBlock().isFullCube()) && (!this.theWorld.getBlockState(new BlockPos(i + l, k + 1, j + i1)).getBlock().isFullCube()))
/*     */                 {
/* 106 */                   this.thePet.setLocationAndAngles(i + l + 0.5F, k, j + i1 + 0.5F, this.thePet.rotationYaw, this.thePet.rotationPitch);
/* 107 */                   this.petPathfinder.clearPathEntity();
/* 108 */                   return;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\AIFollowOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */