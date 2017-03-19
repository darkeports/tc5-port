/*     */ package thaumcraft.common.entities.construct.golem.ai;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class PathNavigateGolemGround extends PathNavigateGround
/*     */ {
/*     */   protected GolemNodeProcessor field_179695_a;
/*     */   private boolean field_179694_f;
/*     */   
/*     */   public PathNavigateGolemGround(EntityLiving p_i45875_1_, World worldIn)
/*     */   {
/*  24 */     super(p_i45875_1_, worldIn);
/*     */   }
/*     */   
/*     */ 
/*     */   protected net.minecraft.pathfinding.PathFinder getPathFinder()
/*     */   {
/*  30 */     this.field_179695_a = new GolemNodeProcessor();
/*  31 */     this.field_179695_a.func_176175_a(true);
/*  32 */     return new net.minecraft.pathfinding.PathFinder(this.field_179695_a);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int func_179687_p()
/*     */   {
/*  39 */     if ((this.theEntity.isInWater()) && (getCanSwim()))
/*     */     {
/*  41 */       int i = (int)this.theEntity.getEntityBoundingBox().minY;
/*  42 */       Block block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), i, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
/*  43 */       int j = 0;
/*     */       
/*     */       do
/*     */       {
/*  47 */         if ((block != net.minecraft.init.Blocks.flowing_water) && (block != net.minecraft.init.Blocks.water))
/*     */         {
/*  49 */           return i;
/*     */         }
/*     */         
/*  52 */         i++;
/*  53 */         block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), i, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
/*  54 */         j++;
/*     */       }
/*  56 */       while (j <= 16);
/*     */       
/*  58 */       return (int)this.theEntity.getEntityBoundingBox().minY;
/*     */     }
/*     */     
/*     */ 
/*  62 */     return (int)(this.theEntity.getEntityBoundingBox().minY + 0.5D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void removeSunnyPath()
/*     */   {
/*  69 */     super.removeSunnyPath();
/*     */     
/*  71 */     if (this.field_179694_f)
/*     */     {
/*  73 */       if (this.worldObj.canSeeSky(new BlockPos(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.getEntityBoundingBox().minY + 0.5D), MathHelper.floor_double(this.theEntity.posZ))))
/*     */       {
/*  75 */         return;
/*     */       }
/*     */       
/*  78 */       for (int i = 0; i < this.currentPath.getCurrentPathLength(); i++)
/*     */       {
/*  80 */         PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
/*     */         
/*  82 */         if (this.worldObj.canSeeSky(new BlockPos(pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord)))
/*     */         {
/*  84 */           this.currentPath.setCurrentPathLength(i - 1);
/*  85 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean func_179683_a(int p_179683_1_, int p_179683_2_, int p_179683_3_, int p_179683_4_, int p_179683_5_, int p_179683_6_, Vec3 p_179683_7_, double p_179683_8_, double p_179683_10_)
/*     */   {
/* 174 */     int k1 = p_179683_1_ - p_179683_4_ / 2;
/* 175 */     int l1 = p_179683_3_ - p_179683_6_ / 2;
/*     */     
/* 177 */     if (!func_179692_b(k1, p_179683_2_, l1, p_179683_4_, p_179683_5_, p_179683_6_, p_179683_7_, p_179683_8_, p_179683_10_))
/*     */     {
/* 179 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 183 */     for (int i2 = k1; i2 < k1 + p_179683_4_; i2++)
/*     */     {
/* 185 */       for (int j2 = l1; j2 < l1 + p_179683_6_; j2++)
/*     */       {
/* 187 */         double d2 = i2 + 0.5D - p_179683_7_.xCoord;
/* 188 */         double d3 = j2 + 0.5D - p_179683_7_.zCoord;
/*     */         
/* 190 */         if (d2 * p_179683_8_ + d3 * p_179683_10_ >= 0.0D)
/*     */         {
/* 192 */           Block block = this.worldObj.getBlockState(new BlockPos(i2, p_179683_2_ - 1, j2)).getBlock();
/* 193 */           Material material = block.getMaterial();
/*     */           
/* 195 */           if (material == Material.air)
/*     */           {
/* 197 */             return false;
/*     */           }
/*     */           
/* 200 */           if ((material == Material.water) && (!this.theEntity.isInWater()))
/*     */           {
/* 202 */             return false;
/*     */           }
/*     */           
/* 205 */           if (material == Material.lava)
/*     */           {
/* 207 */             return false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 213 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean func_179692_b(int p_179692_1_, int p_179692_2_, int p_179692_3_, int p_179692_4_, int p_179692_5_, int p_179692_6_, Vec3 p_179692_7_, double p_179692_8_, double p_179692_10_)
/*     */   {
/* 219 */     Iterator iterator = BlockPos.getAllInBox(new BlockPos(p_179692_1_, p_179692_2_, p_179692_3_), new BlockPos(p_179692_1_ + p_179692_4_ - 1, p_179692_2_ + p_179692_5_ - 1, p_179692_3_ + p_179692_6_ - 1)).iterator();
/*     */     
/* 221 */     while (iterator.hasNext())
/*     */     {
/* 223 */       BlockPos blockpos = (BlockPos)iterator.next();
/* 224 */       double d2 = blockpos.getX() + 0.5D - p_179692_7_.xCoord;
/* 225 */       double d3 = blockpos.getZ() + 0.5D - p_179692_7_.zCoord;
/*     */       
/* 227 */       if (d2 * p_179692_8_ + d3 * p_179692_10_ >= 0.0D)
/*     */       {
/* 229 */         Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*     */         
/* 231 */         if (!block.isPassable(this.worldObj, blockpos))
/*     */         {
/* 233 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 238 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setAvoidsWater(boolean p_179690_1_)
/*     */   {
/* 244 */     this.field_179695_a.func_176176_c(p_179690_1_);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getAvoidsWater()
/*     */   {
/* 250 */     return this.field_179695_a.func_176173_e();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setBreakDoors(boolean p_179688_1_)
/*     */   {
/* 256 */     this.field_179695_a.func_176172_b(p_179688_1_);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setEnterDoors(boolean p_179691_1_)
/*     */   {
/* 262 */     this.field_179695_a.func_176175_a(p_179691_1_);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getEnterDoors()
/*     */   {
/* 268 */     return this.field_179695_a.func_176179_b();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setCanSwim(boolean p_179693_1_)
/*     */   {
/* 274 */     this.field_179695_a.func_176178_d(p_179693_1_);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getCanSwim()
/*     */   {
/* 280 */     return this.field_179695_a.func_176174_d();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setAvoidSun(boolean p_179685_1_)
/*     */   {
/* 286 */     this.field_179694_f = p_179685_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\PathNavigateGolemGround.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */