/*     */ package thaumcraft.common.lib.world.objects;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockLog;
/*     */ import net.minecraft.block.BlockLog.EnumAxis;
/*     */ import net.minecraft.block.BlockSapling;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ 
/*     */ public class WorldGenBigMagicTree extends WorldGenAbstractTree
/*     */ {
/*     */   private Random field_175949_k;
/*     */   private World world;
/*     */   private BlockPos field_175947_m;
/*     */   int heightLimit;
/*     */   int height;
/*     */   double heightAttenuation;
/*     */   double field_175944_d;
/*     */   double field_175945_e;
/*     */   double leafDensity;
/*     */   int field_175943_g;
/*     */   int field_175950_h;
/*     */   int leafDistanceLimit;
/*     */   List field_175948_j;
/*     */   private static final String __OBFID = "CL_00000400";
/*     */   
/*     */   public WorldGenBigMagicTree(boolean p_i2008_1_)
/*     */   {
/*  39 */     super(p_i2008_1_);
/*  40 */     this.field_175947_m = BlockPos.ORIGIN;
/*  41 */     this.heightAttenuation = 0.6618D;
/*  42 */     this.field_175944_d = 0.381D;
/*  43 */     this.field_175945_e = 1.0D;
/*  44 */     this.leafDensity = 1.0D;
/*  45 */     this.field_175943_g = 1;
/*  46 */     this.field_175950_h = 12;
/*  47 */     this.leafDistanceLimit = 3;
/*     */   }
/*     */   
/*     */ 
/*     */   void generateLeafNodeList()
/*     */   {
/*  53 */     this.height = ((int)(this.heightLimit * this.heightAttenuation));
/*     */     
/*  55 */     if (this.height >= this.heightLimit)
/*     */     {
/*  57 */       this.height = (this.heightLimit - 1);
/*     */     }
/*     */     
/*  60 */     int i = (int)(1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));
/*     */     
/*  62 */     if (i < 1)
/*     */     {
/*  64 */       i = 1;
/*     */     }
/*     */     
/*  67 */     int j = this.field_175947_m.getY() + this.height;
/*  68 */     int k = this.heightLimit - this.leafDistanceLimit;
/*  69 */     this.field_175948_j = Lists.newArrayList();
/*  70 */     this.field_175948_j.add(new FoliageCoordinates(this.field_175947_m.up(k), j));
/*  72 */     for (; 
/*  72 */         k >= 0; k--)
/*     */     {
/*  74 */       float f = layerSize(k);
/*     */       
/*  76 */       if (f >= 0.0F)
/*     */       {
/*  78 */         for (int l = 0; l < i; l++)
/*     */         {
/*  80 */           double d0 = this.field_175945_e * f * (this.field_175949_k.nextFloat() + 0.328D);
/*  81 */           double d1 = this.field_175949_k.nextFloat() * 2.0F * 3.141592653589793D;
/*  82 */           double d2 = d0 * Math.sin(d1) + 0.5D;
/*  83 */           double d3 = d0 * Math.cos(d1) + 0.5D;
/*  84 */           BlockPos blockpos = this.field_175947_m.add(d2, k - 1, d3);
/*  85 */           BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);
/*     */           
/*  87 */           if (func_175936_a(blockpos, blockpos1) == -1)
/*     */           {
/*  89 */             int i1 = this.field_175947_m.getX() - blockpos.getX();
/*  90 */             int j1 = this.field_175947_m.getZ() - blockpos.getZ();
/*  91 */             double d4 = blockpos.getY() - Math.sqrt(i1 * i1 + j1 * j1) * this.field_175944_d;
/*  92 */             int k1 = d4 > j ? j : (int)d4;
/*  93 */             BlockPos blockpos2 = new BlockPos(this.field_175947_m.getX(), k1, this.field_175947_m.getZ());
/*     */             
/*  95 */             if (func_175936_a(blockpos2, blockpos) == -1)
/*     */             {
/*  97 */               this.field_175948_j.add(new FoliageCoordinates(blockpos, blockpos2.getY()));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void func_181631_a(BlockPos p_181631_1_, float p_181631_2_, IBlockState p_181631_3_)
/*     */   {
/* 107 */     int i = (int)(p_181631_2_ + 0.618D);
/*     */     
/* 109 */     for (int j = -i; j <= i; j++)
/*     */     {
/* 111 */       for (int k = -i; k <= i; k++)
/*     */       {
/* 113 */         if (Math.pow(Math.abs(j) + 0.5D, 2.0D) + Math.pow(Math.abs(k) + 0.5D, 2.0D) <= p_181631_2_ * p_181631_2_)
/*     */         {
/* 115 */           BlockPos blockpos = p_181631_1_.add(j, 0, k);
/* 116 */           IBlockState state = this.world.getBlockState(blockpos);
/*     */           
/* 118 */           if ((state.getBlock().isAir(this.world, blockpos)) || (state.getBlock().isLeaves(this.world, blockpos)))
/*     */           {
/* 120 */             setBlockAndNotifyAdequately(this.world, blockpos, p_181631_3_);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   float layerSize(int p_76490_1_)
/*     */   {
/* 129 */     if (p_76490_1_ < this.heightLimit * 0.3F)
/*     */     {
/* 131 */       return -1.0F;
/*     */     }
/*     */     
/*     */ 
/* 135 */     float f = this.heightLimit / 2.0F;
/* 136 */     float f1 = f - p_76490_1_;
/* 137 */     float f2 = MathHelper.sqrt_float(f * f - f1 * f1);
/*     */     
/* 139 */     if (f1 == 0.0F)
/*     */     {
/* 141 */       f2 = f;
/*     */     }
/* 143 */     else if (Math.abs(f1) >= f)
/*     */     {
/* 145 */       return 0.0F;
/*     */     }
/*     */     
/* 148 */     return f2 * 0.5F;
/*     */   }
/*     */   
/*     */ 
/*     */   float leafSize(int p_76495_1_)
/*     */   {
/* 154 */     return (p_76495_1_ >= 0) && (p_76495_1_ < this.leafDistanceLimit) ? 2.0F : (p_76495_1_ != 0) && (p_76495_1_ != this.leafDistanceLimit - 1) ? 3.0F : -1.0F;
/*     */   }
/*     */   
/*     */   void generateLeafNode(BlockPos pos)
/*     */   {
/* 159 */     for (int i = 0; i < this.leafDistanceLimit; i++)
/*     */     {
/* 161 */       func_181631_a(pos.up(i), leafSize(i), Blocks.leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)));
/*     */     }
/*     */   }
/*     */   
/*     */   void func_175937_a(BlockPos p_175937_1_, BlockPos p_175937_2_, Block p_175937_3_)
/*     */   {
/* 167 */     BlockPos blockpos2 = p_175937_2_.add(-p_175937_1_.getX(), -p_175937_1_.getY(), -p_175937_1_.getZ());
/* 168 */     int i = func_175935_b(blockpos2);
/* 169 */     float f = blockpos2.getX() / i;
/* 170 */     float f1 = blockpos2.getY() / i;
/* 171 */     float f2 = blockpos2.getZ() / i;
/*     */     
/* 173 */     for (int j = 0; j <= i; j++)
/*     */     {
/* 175 */       BlockPos blockpos3 = p_175937_1_.add(0.5F + j * f, 0.5F + j * f1, 0.5F + j * f2);
/* 176 */       BlockLog.EnumAxis enumaxis = func_175938_b(p_175937_1_, blockpos3);
/* 177 */       setBlockAndNotifyAdequately(this.world, blockpos3, p_175937_3_.getDefaultState().withProperty(BlockLog.LOG_AXIS, enumaxis));
/*     */     }
/*     */   }
/*     */   
/*     */   private int func_175935_b(BlockPos p_175935_1_)
/*     */   {
/* 183 */     int i = MathHelper.abs_int(p_175935_1_.getX());
/* 184 */     int j = MathHelper.abs_int(p_175935_1_.getY());
/* 185 */     int k = MathHelper.abs_int(p_175935_1_.getZ());
/* 186 */     return j > i ? j : (k > i) && (k > j) ? k : i;
/*     */   }
/*     */   
/*     */   private BlockLog.EnumAxis func_175938_b(BlockPos p_175938_1_, BlockPos p_175938_2_)
/*     */   {
/* 191 */     BlockLog.EnumAxis enumaxis = BlockLog.EnumAxis.Y;
/* 192 */     int i = Math.abs(p_175938_2_.getX() - p_175938_1_.getX());
/* 193 */     int j = Math.abs(p_175938_2_.getZ() - p_175938_1_.getZ());
/* 194 */     int k = Math.max(i, j);
/*     */     
/* 196 */     if (k > 0)
/*     */     {
/* 198 */       if (i == k)
/*     */       {
/* 200 */         enumaxis = BlockLog.EnumAxis.X;
/*     */       }
/* 202 */       else if (j == k)
/*     */       {
/* 204 */         enumaxis = BlockLog.EnumAxis.Z;
/*     */       }
/*     */     }
/*     */     
/* 208 */     return enumaxis;
/*     */   }
/*     */   
/*     */   void func_175941_b()
/*     */   {
/* 213 */     Iterator iterator = this.field_175948_j.iterator();
/*     */     
/* 215 */     while (iterator.hasNext())
/*     */     {
/* 217 */       FoliageCoordinates foliagecoordinates = (FoliageCoordinates)iterator.next();
/* 218 */       generateLeafNode(foliagecoordinates);
/*     */     }
/*     */   }
/*     */   
/*     */   boolean leafNodeNeedsBase(int p_76493_1_)
/*     */   {
/* 224 */     return p_76493_1_ >= this.heightLimit * 0.2D;
/*     */   }
/*     */   
/*     */   void func_175942_c()
/*     */   {
/* 229 */     BlockPos blockpos = this.field_175947_m;
/* 230 */     BlockPos blockpos1 = this.field_175947_m.up(this.height);
/* 231 */     Block block = Blocks.log;
/* 232 */     func_175937_a(blockpos, blockpos1, block);
/*     */     
/* 234 */     if (this.field_175943_g == 2)
/*     */     {
/* 236 */       func_175937_a(blockpos.east(), blockpos1.east(), block);
/* 237 */       func_175937_a(blockpos.east().south(), blockpos1.east().south(), block);
/* 238 */       func_175937_a(blockpos.south(), blockpos1.south(), block);
/*     */     }
/*     */   }
/*     */   
/*     */   void func_175939_d()
/*     */   {
/* 244 */     Iterator iterator = this.field_175948_j.iterator();
/*     */     
/* 246 */     while (iterator.hasNext())
/*     */     {
/* 248 */       FoliageCoordinates foliagecoordinates = (FoliageCoordinates)iterator.next();
/* 249 */       int i = foliagecoordinates.func_177999_q();
/* 250 */       BlockPos blockpos = new BlockPos(this.field_175947_m.getX(), i, this.field_175947_m.getZ());
/*     */       
/* 252 */       if (leafNodeNeedsBase(i - this.field_175947_m.getY()))
/*     */       {
/* 254 */         func_175937_a(blockpos, foliagecoordinates, Blocks.log);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   int func_175936_a(BlockPos p_175936_1_, BlockPos p_175936_2_)
/*     */   {
/* 261 */     BlockPos blockpos2 = p_175936_2_.add(-p_175936_1_.getX(), -p_175936_1_.getY(), -p_175936_1_.getZ());
/* 262 */     int i = func_175935_b(blockpos2);
/* 263 */     float f = blockpos2.getX() / i;
/* 264 */     float f1 = blockpos2.getY() / i;
/* 265 */     float f2 = blockpos2.getZ() / i;
/*     */     
/* 267 */     if (i == 0)
/*     */     {
/* 269 */       return -1;
/*     */     }
/*     */     
/*     */ 
/* 273 */     for (int j = 0; j <= i; j++)
/*     */     {
/* 275 */       BlockPos blockpos3 = p_175936_1_.add(0.5F + j * f, 0.5F + j * f1, 0.5F + j * f2);
/*     */       
/* 277 */       if (!isReplaceable(this.world, blockpos3))
/*     */       {
/* 279 */         return j;
/*     */       }
/*     */     }
/*     */     
/* 283 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public void func_175904_e()
/*     */   {
/* 289 */     this.leafDistanceLimit = 5;
/*     */   }
/*     */   
/*     */   public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_)
/*     */   {
/* 294 */     this.world = worldIn;
/* 295 */     this.field_175947_m = p_180709_3_;
/* 296 */     this.field_175949_k = new Random(p_180709_2_.nextLong());
/*     */     
/* 298 */     if (this.heightLimit == 0)
/*     */     {
/* 300 */       this.heightLimit = (11 + this.field_175949_k.nextInt(this.field_175950_h));
/*     */     }
/*     */     
/* 303 */     if (!validTreeLocation())
/*     */     {
/* 305 */       this.world = null;
/* 306 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 310 */     generateLeafNodeList();
/* 311 */     func_175941_b();
/* 312 */     func_175942_c();
/* 313 */     func_175939_d();
/* 314 */     this.world = null;
/* 315 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean validTreeLocation()
/*     */   {
/* 321 */     BlockPos down = this.field_175947_m.down();
/* 322 */     IBlockState state = this.world.getBlockState(down);
/* 323 */     boolean isSoil = state.getBlock().canSustainPlant(this.world, down, EnumFacing.UP, (BlockSapling)Blocks.sapling);
/*     */     
/* 325 */     if (!isSoil)
/*     */     {
/* 327 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 331 */     int i = func_175936_a(this.field_175947_m, this.field_175947_m.up(this.heightLimit - 1));
/*     */     
/* 333 */     if (i == -1)
/*     */     {
/* 335 */       return true;
/*     */     }
/* 337 */     if (i < 6)
/*     */     {
/* 339 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 343 */     this.heightLimit = i;
/* 344 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   static class FoliageCoordinates
/*     */     extends BlockPos
/*     */   {
/*     */     private final int field_178000_b;
/*     */     private static final String __OBFID = "CL_00002001";
/*     */     
/*     */     public FoliageCoordinates(BlockPos p_i45635_1_, int p_i45635_2_)
/*     */     {
/* 356 */       super(p_i45635_1_.getY(), p_i45635_1_.getZ());
/* 357 */       this.field_178000_b = p_i45635_2_;
/*     */     }
/*     */     
/*     */     public int func_177999_q()
/*     */     {
/* 362 */       return this.field_178000_b;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\objects\WorldGenBigMagicTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */