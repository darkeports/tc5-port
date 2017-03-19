/*     */ package thaumcraft.common.entities;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.effect.EntityWeatherEffect;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.common.blocks.world.taint.BlockFluxGoo;
/*     */ 
/*     */ public class EntityTaintSourceLightning extends EntityWeatherEffect
/*     */ {
/*     */   private int lightningState;
/*     */   public long boltVertex;
/*     */   private int boltLivingTime;
/*     */   
/*     */   public EntityTaintSourceLightning(World par1World)
/*     */   {
/*  27 */     super(par1World);
/*     */   }
/*     */   
/*     */   public EntityTaintSourceLightning(World worldIn, double posX, double posY, double posZ)
/*     */   {
/*  32 */     super(worldIn);
/*  33 */     setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
/*  34 */     this.lightningState = 2;
/*  35 */     this.boltVertex = this.rand.nextLong();
/*  36 */     this.boltLivingTime = (this.rand.nextInt(3) + 1);
/*     */     
/*  38 */     if ((!worldIn.isRemote) && ((worldIn.getDifficulty() == EnumDifficulty.NORMAL) || (worldIn.getDifficulty() == EnumDifficulty.HARD)) && (worldIn.isAreaLoaded(new BlockPos(this), 10)))
/*     */     {
/*     */ 
/*     */ 
/*  42 */       BlockPos blockpos = new BlockPos(this);
/*     */       
/*  44 */       if ((worldIn.isAirBlock(blockpos)) && (BlocksTC.fluxGoo.canPlaceBlockAt(worldIn, blockpos)))
/*     */       {
/*  46 */         this.worldObj.setBlockState(blockpos, BlocksTC.fluxGoo.getDefaultState().withProperty(BlockFluxGoo.LEVEL, Integer.valueOf(7)));
/*  47 */         this.worldObj.scheduleUpdate(blockpos, BlocksTC.fluxGoo, 2);
/*     */       }
/*     */       
/*  50 */       for (int i = 0; i < 4; i++)
/*     */       {
/*  52 */         BlockPos blockpos1 = blockpos.add(this.rand.nextInt(5) - 2, this.rand.nextInt(5) - 2, this.rand.nextInt(5) - 2);
/*     */         
/*  54 */         if ((worldIn.isAirBlock(blockpos1)) && (BlocksTC.fluxGoo.canPlaceBlockAt(worldIn, blockpos1)))
/*     */         {
/*  56 */           this.worldObj.setBlockState(blockpos1, BlocksTC.fluxGoo.getDefaultState().withProperty(BlockFluxGoo.LEVEL, Integer.valueOf(7)));
/*  57 */           this.worldObj.scheduleUpdate(blockpos1, BlocksTC.fluxGoo, 3);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  67 */     super.onUpdate();
/*     */     
/*  69 */     if (this.lightningState == 2)
/*     */     {
/*  71 */       this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
/*  72 */       this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
/*     */     }
/*     */     
/*  75 */     this.lightningState -= 1;
/*     */     
/*  77 */     if (this.lightningState < 0)
/*     */     {
/*  79 */       if (this.boltLivingTime == 0)
/*     */       {
/*  81 */         setDead();
/*     */       }
/*  83 */       else if (this.lightningState < -this.rand.nextInt(10))
/*     */       {
/*  85 */         this.boltLivingTime -= 1;
/*  86 */         this.lightningState = 1;
/*  87 */         this.boltVertex = this.rand.nextLong();
/*  88 */         BlockPos blockpos = new BlockPos(this);
/*     */         
/*  90 */         if ((!this.worldObj.isRemote) && (this.worldObj.isAreaLoaded(blockpos, 10)) && (this.worldObj.isAirBlock(blockpos)) && (BlocksTC.fluxGoo.canPlaceBlockAt(this.worldObj, blockpos)))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*  95 */           this.worldObj.setBlockState(blockpos, BlocksTC.fluxGoo.getDefaultState().withProperty(BlockFluxGoo.LEVEL, Integer.valueOf(7)));
/*  96 */           this.worldObj.scheduleUpdate(blockpos, BlocksTC.fluxGoo, 4);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 101 */     if (this.lightningState >= 0)
/*     */     {
/* 103 */       if (!this.worldObj.isRemote)
/*     */       {
/* 105 */         double d0 = 3.0D;
/* 106 */         List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, new net.minecraft.util.AxisAlignedBB(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + 6.0D + d0, this.posZ + d0));
/*     */         
/* 108 */         for (int i = 0; i < list.size(); i++)
/*     */         {
/* 110 */           Entity entity = (Entity)list.get(i);
/* 111 */           entity.attackEntityFrom(net.minecraft.util.DamageSource.lightningBolt, 3.0F);
/* 112 */           if ((entity instanceof EntityLivingBase)) {
/* 113 */             ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(PotionFluxTaint.instance.getId(), 1200, 0, false, true));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void entityInit() {}
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {}
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\EntityTaintSourceLightning.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */