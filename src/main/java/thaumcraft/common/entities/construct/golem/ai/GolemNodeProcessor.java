/*     */ package thaumcraft.common.entities.construct.golem.ai;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.BlockFence;
/*     */ import net.minecraft.block.BlockFenceGate;
/*     */ import net.minecraft.block.BlockRailBase;
/*     */ import net.minecraft.block.BlockWall;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.pathfinder.NodeProcessor;
/*     */ 
/*     */ public class GolemNodeProcessor extends NodeProcessor
/*     */ {
/*     */   private boolean field_176180_f;
/*     */   private boolean field_176181_g;
/*     */   private boolean field_176183_h;
/*     */   private boolean field_176184_i;
/*     */   private boolean field_176182_j;
/*     */   
/*     */   public void initProcessor(IBlockAccess p_176162_1_, Entity p_176162_2_)
/*     */   {
/*  31 */     super.initProcessor(p_176162_1_, p_176162_2_);
/*  32 */     this.field_176182_j = this.field_176183_h;
/*     */   }
/*     */   
/*     */ 
/*     */   public void postProcess()
/*     */   {
/*  38 */     super.postProcess();
/*  39 */     this.field_176183_h = this.field_176182_j;
/*     */   }
/*     */   
/*     */ 
/*     */   public PathPoint getPathPointTo(Entity p_176161_1_)
/*     */   {
/*     */     int i;
/*     */     
/*  47 */     if ((this.field_176184_i) && (p_176161_1_.isInWater()))
/*     */     {
/*  49 */       int i = (int)p_176161_1_.getEntityBoundingBox().minY;
/*     */       
/*  51 */       for (Block block = this.blockaccess.getBlockState(new BlockPos(MathHelper.floor_double(p_176161_1_.posX), i, MathHelper.floor_double(p_176161_1_.posZ))).getBlock(); 
/*  52 */           (block == Blocks.flowing_water) || (block == Blocks.water); 
/*  53 */           block = this.blockaccess.getBlockState(new BlockPos(MathHelper.floor_double(p_176161_1_.posX), i, MathHelper.floor_double(p_176161_1_.posZ))).getBlock())
/*     */       {
/*  55 */         i++;
/*     */       }
/*     */       
/*  58 */       this.field_176183_h = false;
/*     */     }
/*     */     else
/*     */     {
/*  62 */       i = MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minY + 0.5D);
/*     */     }
/*     */     
/*  65 */     return openPoint(MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minX), i, MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minZ));
/*     */   }
/*     */   
/*     */ 
/*     */   public PathPoint getPathPointToCoords(Entity p_176160_1_, double p_176160_2_, double p_176160_4_, double p_176160_6_)
/*     */   {
/*  71 */     return openPoint(MathHelper.floor_double(p_176160_2_ - p_176160_1_.width / 2.0F), MathHelper.floor_double(p_176160_4_), MathHelper.floor_double(p_176160_6_ - p_176160_1_.width / 2.0F));
/*     */   }
/*     */   
/*     */ 
/*     */   public int findPathOptions(PathPoint[] p_176164_1_, Entity entity, PathPoint pp1, PathPoint pp2, float p_176164_5_)
/*     */   {
/*  77 */     int i = 0;
/*  78 */     byte b0 = 0;
/*     */     
/*  80 */     if (canWalkOver(entity, pp1.xCoord, pp1.yCoord + 1, pp1.zCoord) == 1)
/*     */     {
/*  82 */       b0 = 1;
/*  83 */       if (entity.stepHeight < 0.6D) {
/*  84 */         b0 = 0;
/*     */       }
/*     */     }
/*     */     
/*  88 */     PathPoint pathpoint2 = func_176171_a(entity, pp1.xCoord, pp1.yCoord, pp1.zCoord + 1, b0);
/*  89 */     PathPoint pathpoint3 = func_176171_a(entity, pp1.xCoord - 1, pp1.yCoord, pp1.zCoord, b0);
/*  90 */     PathPoint pathpoint4 = func_176171_a(entity, pp1.xCoord + 1, pp1.yCoord, pp1.zCoord, b0);
/*  91 */     PathPoint pathpoint5 = func_176171_a(entity, pp1.xCoord, pp1.yCoord, pp1.zCoord - 1, b0);
/*     */     
/*  93 */     if ((pathpoint2 != null) && (!pathpoint2.visited) && (pathpoint2.distanceTo(pp2) < p_176164_5_))
/*     */     {
/*  95 */       p_176164_1_[(i++)] = pathpoint2;
/*     */     }
/*     */     
/*  98 */     if ((pathpoint3 != null) && (!pathpoint3.visited) && (pathpoint3.distanceTo(pp2) < p_176164_5_))
/*     */     {
/* 100 */       p_176164_1_[(i++)] = pathpoint3;
/*     */     }
/*     */     
/* 103 */     if ((pathpoint4 != null) && (!pathpoint4.visited) && (pathpoint4.distanceTo(pp2) < p_176164_5_))
/*     */     {
/* 105 */       p_176164_1_[(i++)] = pathpoint4;
/*     */     }
/*     */     
/* 108 */     if ((pathpoint5 != null) && (!pathpoint5.visited) && (pathpoint5.distanceTo(pp2) < p_176164_5_))
/*     */     {
/* 110 */       p_176164_1_[(i++)] = pathpoint5;
/*     */     }
/*     */     
/* 113 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   private PathPoint func_176171_a(Entity entity, int x, int y, int z, int step)
/*     */   {
/* 119 */     PathPoint pathpoint = null;
/* 120 */     int i1 = canWalkOver(entity, x, y, z);
/*     */     
/* 122 */     if (i1 == 2)
/*     */     {
/* 124 */       return openPoint(x, y, z);
/*     */     }
/*     */     
/*     */ 
/* 128 */     if (i1 == 1)
/*     */     {
/* 130 */       pathpoint = openPoint(x, y, z);
/*     */     }
/*     */     
/* 133 */     if ((pathpoint == null) && (step > 0) && (i1 != -3) && (i1 != -4) && (canWalkOver(entity, x, y + step, z) == 1))
/*     */     {
/* 135 */       pathpoint = openPoint(x, y + step, z);
/* 136 */       y += step;
/*     */     }
/*     */     
/* 139 */     if (pathpoint != null)
/*     */     {
/* 141 */       int j1 = 0;
/*     */       
/*     */ 
/* 144 */       for (int k1 = 0; y > 0; pathpoint = openPoint(x, y, z))
/*     */       {
/* 146 */         k1 = canWalkOver(entity, x, y - 1, z);
/*     */         
/* 148 */         if ((this.field_176183_h) && (k1 == -1))
/*     */         {
/* 150 */           return null;
/*     */         }
/*     */         
/* 153 */         if (k1 != 1) {
/*     */           break;
/*     */         }
/*     */         
/*     */ 
/* 158 */         if (j1++ >= entity.getMaxFallHeight())
/*     */         {
/* 160 */           return null;
/*     */         }
/*     */         
/* 163 */         y--;
/*     */         
/* 165 */         if (y <= 0)
/*     */         {
/* 167 */           return null;
/*     */         }
/*     */       }
/*     */       
/* 171 */       if (k1 == -2)
/*     */       {
/* 173 */         return null;
/*     */       }
/*     */     }
/*     */     
/* 177 */     return pathpoint;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int canWalkOver(Entity p_176177_1_, int p_176177_2_, int p_176177_3_, int p_176177_4_)
/*     */   {
/* 184 */     return canWalkOver(this.blockaccess, p_176177_1_, p_176177_2_, p_176177_3_, p_176177_4_, this.entitySizeX, this.entitySizeY, this.entitySizeZ, this.field_176183_h, this.field_176181_g, this.field_176180_f);
/*     */   }
/*     */   
/*     */ 
/*     */   public static int canWalkOver(IBlockAccess ba, Entity entity, int x, int y, int z, int p_176170_5_, int p_176170_6_, int p_176170_7_, boolean p_176170_8_, boolean p_176170_9_, boolean p_176170_10_)
/*     */   {
/* 190 */     boolean flag3 = false;
/* 191 */     BlockPos blockpos = new BlockPos(entity);
/*     */     
/* 193 */     for (int k1 = x; k1 < x + p_176170_5_; k1++)
/*     */     {
/* 195 */       for (int l1 = y; l1 < y + p_176170_6_; l1++)
/*     */       {
/* 197 */         for (int i2 = z; i2 < z + p_176170_7_; i2++)
/*     */         {
/* 199 */           BlockPos blockpos1 = new BlockPos(k1, l1, i2);
/* 200 */           Block block = ba.getBlockState(blockpos1).getBlock();
/*     */           
/* 202 */           if (block.getMaterial() != Material.air)
/*     */           {
/* 204 */             if ((block != Blocks.trapdoor) && (block != Blocks.iron_trapdoor))
/*     */             {
/* 206 */               if ((block != Blocks.flowing_water) && (block != Blocks.water))
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/* 211 */                 if ((!p_176170_10_) && ((block instanceof BlockDoor)) && (block.getMaterial() == Material.wood))
/*     */                 {
/* 213 */                   return 0;
/*     */                 }
/*     */               }
/*     */               else
/*     */               {
/* 218 */                 if (p_176170_8_)
/*     */                 {
/*     */ 
/* 221 */                   return -1;
/*     */                 }
/*     */                 
/* 224 */                 flag3 = true;
/*     */               }
/*     */               
/*     */             }
/*     */             else {
/* 229 */               flag3 = true;
/*     */             }
/*     */             
/* 232 */             if ((entity.worldObj.getBlockState(blockpos1).getBlock() instanceof BlockRailBase))
/*     */             {
/* 234 */               if ((!(entity.worldObj.getBlockState(blockpos).getBlock() instanceof BlockRailBase)) && (!(entity.worldObj.getBlockState(blockpos.down()).getBlock() instanceof BlockRailBase)))
/*     */               {
/*     */ 
/* 237 */                 return -3;
/*     */               }
/*     */             }
/* 240 */             else if ((!block.isPassable(ba, blockpos1)) && ((!p_176170_9_) || (!(block instanceof BlockDoor)) || (block.getMaterial() != Material.wood)))
/*     */             {
/* 242 */               if (((block instanceof BlockFence)) || ((block instanceof BlockFenceGate)) || ((block instanceof BlockWall)))
/*     */               {
/* 244 */                 return -3;
/*     */               }
/*     */               
/* 247 */               if ((block == Blocks.trapdoor) || (block == Blocks.iron_trapdoor))
/*     */               {
/* 249 */                 return -4;
/*     */               }
/*     */               
/* 252 */               Material material = block.getMaterial();
/*     */               
/* 254 */               if (material != Material.lava)
/*     */               {
/* 256 */                 return 0;
/*     */               }
/*     */               
/* 259 */               if (!entity.isInLava())
/*     */               {
/* 261 */                 return -2;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 269 */     return flag3 ? 2 : 1;
/*     */   }
/*     */   
/*     */   public void func_176175_a(boolean p_176175_1_)
/*     */   {
/* 274 */     this.field_176180_f = p_176175_1_;
/*     */   }
/*     */   
/*     */   public void func_176172_b(boolean p_176172_1_)
/*     */   {
/* 279 */     this.field_176181_g = p_176172_1_;
/*     */   }
/*     */   
/*     */   public void func_176176_c(boolean p_176176_1_)
/*     */   {
/* 284 */     this.field_176183_h = p_176176_1_;
/*     */   }
/*     */   
/*     */   public void func_176178_d(boolean p_176178_1_)
/*     */   {
/* 289 */     this.field_176184_i = p_176178_1_;
/*     */   }
/*     */   
/*     */   public boolean func_176179_b()
/*     */   {
/* 294 */     return this.field_176180_f;
/*     */   }
/*     */   
/*     */   public boolean func_176174_d()
/*     */   {
/* 299 */     return this.field_176184_i;
/*     */   }
/*     */   
/*     */   public boolean func_176173_e()
/*     */   {
/* 304 */     return this.field_176183_h;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\GolemNodeProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */