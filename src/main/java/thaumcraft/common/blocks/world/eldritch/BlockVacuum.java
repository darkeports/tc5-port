/*     */ package thaumcraft.common.blocks.world.eldritch;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.client.fx.ParticleEngine;
/*     */ import thaumcraft.client.fx.particles.FXGenericP2P;
/*     */ 
/*     */ public class BlockVacuum extends Block
/*     */ {
/*  31 */   public static final PropertyInteger SPREAD = PropertyInteger.create("spread", 0, 12);
/*     */   
/*     */   public BlockVacuum()
/*     */   {
/*  35 */     super(new MaterialAiry(MapColor.airColor));
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty(SPREAD, Integer.valueOf(0)));
/*  37 */     setBlockUnbreakable();
/*  38 */     this.blockResistance = 999.0F;
/*  39 */     setTickRandomly(true);
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canSilkHarvest()
/*     */   {
/*  45 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  51 */     return getDefaultState().withProperty(SPREAD, Integer.valueOf(meta));
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/*  57 */     return ((Integer)state.getValue(SPREAD)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/*  63 */     return new BlockState(this, new IProperty[] { SPREAD });
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  69 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/*  75 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  81 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isVisuallyOpaque()
/*     */   {
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isBlockNormalCube()
/*     */   {
/*  92 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid)
/*     */   {
/*  98 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos)
/*     */   {
/* 106 */     IBlockState state = worldIn.getBlockState(pos);
/* 107 */     int spread = state.getBlock().getMetaFromState(state);
/* 108 */     return spread > 0;
/*     */   }
/*     */   
/*     */   private void check(World worldIn, BlockPos pos, IBlockState state) {
/* 112 */     if (!worldIn.isRemote)
/*     */     {
/* 114 */       int spread = state.getBlock().getMetaFromState(state);
/* 115 */       boolean con = spread == 0;
/* 116 */       if (!con) {
/* 117 */         for (EnumFacing face : EnumFacing.VALUES)
/* 118 */           if (worldIn.isBlockLoaded(pos.offset(face))) {
/* 119 */             IBlockState bs = worldIn.getBlockState(pos.offset(face));
/* 120 */             if ((bs.getBlock() == this) && (bs.getBlock().getMetaFromState(bs) < spread)) {
/* 121 */               con = true;
/* 122 */               break;
/*     */             }
/*     */           }
/*     */       }
/* 126 */       if (con) {
/* 127 */         if (spread < 12) {
/* 128 */           for (EnumFacing face : EnumFacing.VALUES) {
/* 129 */             if (worldIn.isBlockLoaded(pos.offset(face))) {
/* 130 */               IBlockState bs = worldIn.getBlockState(pos.offset(face));
/* 131 */               if (((bs.getBlock() != this) && (worldIn.isAirBlock(pos.offset(face)))) || ((bs.getBlock() == this) && (bs.getBlock().getMetaFromState(bs) > spread + 1)))
/*     */               {
/* 133 */                 worldIn.setBlockState(pos.offset(face), BlocksTC.vacuum.getStateFromMeta(spread + 1));
/* 134 */                 worldIn.scheduleUpdate(pos.offset(face), this, 5);
/* 135 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 140 */       } else if (spread < 12) {
/* 141 */         worldIn.setBlockState(pos, BlocksTC.vacuum.getStateFromMeta(spread + 1));
/* 142 */         worldIn.scheduleUpdate(pos, this, 5);
/*     */       } else {
/* 144 */         worldIn.setBlockState(pos, Blocks.air.getDefaultState());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/* 154 */     check(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public int getLightOpacity(IBlockAccess world, BlockPos pos)
/*     */   {
/* 165 */     IBlockState state = world.getBlockState(pos);
/* 166 */     int spread = state.getBlock().getMetaFromState(state);
/* 167 */     return spread * 21;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/* 173 */     check(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
/*     */   {
/* 179 */     if ((((entityIn instanceof EntityPlayer)) && (((EntityPlayer)entityIn).capabilities.isCreativeMode)) || (!entityIn.isEntityInvulnerable(DamageSource.outOfWorld))) {
/* 180 */       return;
/*     */     }
/* 182 */     int md = getMetaFromState(state);
/* 183 */     if ((!worldIn.isRemote) && (
/* 184 */       (md == 0) || (!(entityIn instanceof EntityLiving)) || (!((EntityLiving)entityIn).canBreatheUnderwater())))
/*     */     {
/*     */ 
/* 187 */       if ((md == 0) || (worldIn.rand.nextInt(2000) < 12 - getMetaFromState(state))) {
/* 188 */         entityIn.attackEntityFrom(DamageSource.outOfWorld, md == 0 ? 10.0F : 1.0F);
/*     */       }
/*     */     }
/*     */     
/* 192 */     if ((md > 0) && (entityIn.canBePushed())) {
/* 193 */       for (EnumFacing face : EnumFacing.VALUES) {
/* 194 */         IBlockState bs = worldIn.getBlockState(pos.offset(face));
/* 195 */         if ((bs.getBlock() == this) && (bs.getBlock().getMetaFromState(bs) < md)) {
/* 196 */           entityIn.motionX += face.getFrontOffsetX() / (25.0F * (md + 1));
/* 197 */           entityIn.motionY += face.getFrontOffsetY() / (((entityIn instanceof EntityItem) ? 1.0F : 10.0F) * (md + 1));
/* 198 */           entityIn.motionZ += face.getFrontOffsetZ() / (25.0F * (md + 1));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/* 207 */     if ((getMetaFromState(state) > 3) && (rand.nextInt(500) < getMetaFromState(state))) {
/* 208 */       BlockPos p = findSource(worldIn, pos, getMetaFromState(state));
/* 209 */       if (p != pos) {
/* 210 */         FXGenericP2P bp = new FXGenericP2P(worldIn, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), p.getX() + 0.5D, p.getY() + 0.5D, p.getZ() + 0.5D);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 215 */         bp.setAlphaF(0.4F);
/* 216 */         bp.setParticles(48, 14, 1);
/* 217 */         bp.setLoop(true);
/* 218 */         bp.setScale(3.0F);
/* 219 */         bp.setLayer(0);
/* 220 */         ParticleEngine.instance.addEffect(worldIn, bp);
/* 221 */         if (rand.nextInt(33) == 0) {
/* 222 */           worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:wind", 0.33F, 1.0F, true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   BlockPos findSource(World worldIn, BlockPos pos, int spread) {
/* 229 */     for (EnumFacing face : EnumFacing.VALUES) {
/* 230 */       IBlockState bs = worldIn.getBlockState(pos.offset(face));
/* 231 */       if ((bs.getBlock() == this) && (bs.getBlock().getMetaFromState(bs) < spread)) {
/* 232 */         return findSource(worldIn, pos.offset(face), bs.getBlock().getMetaFromState(bs));
/*     */       }
/*     */     }
/* 235 */     return pos;
/*     */   }
/*     */   
/*     */   public static class MaterialAiry extends Material
/*     */   {
/*     */     public MaterialAiry(MapColor par1MapColor)
/*     */     {
/* 242 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isSolid()
/*     */     {
/* 248 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean blocksLight()
/*     */     {
/* 254 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean isReplaceable()
/*     */     {
/* 260 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean blocksMovement()
/*     */     {
/* 266 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\eldritch\BlockVacuum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */