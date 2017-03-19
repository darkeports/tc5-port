/*     */ package thaumcraft.common.blocks.basic;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ 
/*     */ public class BlockPavingStone extends BlockTC
/*     */ {
/*  31 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", PavingStoneType.class);
/*     */   
/*     */   public BlockPavingStone()
/*     */   {
/*  35 */     super(Material.rock);
/*  36 */     setCreativeTab(Thaumcraft.tabTC);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, PavingStoneType.BARRIER));
/*  38 */     setHardness(2.5F);
/*  39 */     setStepSound(Block.soundTypeStone);
/*  40 */     setTickRandomly(true);
/*  41 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
/*     */   {
/*  46 */     return true;
/*     */   }
/*     */   
/*     */   public boolean hasTileEntity(IBlockState state)
/*     */   {
/*  51 */     return state.getBlock().getMetaFromState(state) == 0;
/*     */   }
/*     */   
/*     */   public TileEntity createTileEntity(World world, IBlockState state)
/*     */   {
/*  56 */     return state.getBlock().getMetaFromState(state) == 0 ? new thaumcraft.common.tiles.misc.TileBarrierStone() : null;
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/*  62 */     return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
/*     */   }
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity e)
/*     */   {
/*  67 */     IBlockState state = worldIn.getBlockState(pos);
/*  68 */     if ((getMetaFromState(state) == 1) && ((e instanceof EntityLivingBase))) {
/*  69 */       if ((worldIn.isRemote) && (worldIn.rand.nextBoolean())) {
/*  70 */         Thaumcraft.proxy.getFX().drawWispyMotesOnBlock(pos.up(), 30);
/*     */       } else {
/*  72 */         ((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 40, 1, true, false));
/*  73 */         ((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.jump.id, 40, 0, true, false));
/*     */       }
/*     */     }
/*  76 */     super.onEntityCollidedWithBlock(worldIn, pos, e);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  82 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  88 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random random)
/*     */   {
/*  94 */     if (state.getBlock().getMetaFromState(state) == 0) {
/*  95 */       if (world.isBlockIndirectlyGettingPowered(pos) > 0) {
/*  96 */         for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(2); a++) {
/*  97 */           Thaumcraft.proxy.getFX().blockRunes(pos.getX(), pos.getY() + 0.7F, pos.getZ(), 0.2F + random.nextFloat() * 0.4F, random.nextFloat() * 0.3F, 0.8F + random.nextFloat() * 0.2F, 20, -0.02F);
/*     */         }
/*     */       }
/* 100 */       else if (((world.getBlockState(pos.up(1)) == BlocksTC.barrier.getDefaultState()) && (world.getBlockState(pos.up(1)).getBlock().isPassable(world, pos.up(1)))) || ((world.getBlockState(pos.up(2)) == BlocksTC.barrier.getDefaultState()) && (world.getBlockState(pos.up(2)).getBlock().isPassable(world, pos.up(2)))))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 105 */         for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(3); a++) {
/* 106 */           Thaumcraft.proxy.getFX().blockRunes(pos.getX(), pos.getY() + 0.7F, pos.getZ(), 0.9F + random.nextFloat() * 0.1F, random.nextFloat() * 0.3F, random.nextFloat() * 0.3F, 24, -0.02F);
/*     */         }
/*     */       }
/*     */       else {
/* 110 */         List list = world.getEntitiesWithinAABBExcludingEntity((Entity)null, AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).expand(1.0D, 1.0D, 1.0D));
/*     */         
/*     */ 
/* 113 */         if (!list.isEmpty())
/*     */         {
/* 115 */           Iterator iterator = list.iterator();
/* 116 */           while (iterator.hasNext())
/*     */           {
/* 118 */             Entity entity = (Entity)iterator.next();
/* 119 */             if (((entity instanceof EntityLivingBase)) && (!(entity instanceof EntityPlayer)))
/*     */             {
/* 121 */               Thaumcraft.proxy.getFX().blockRunes(pos.getX(), pos.getY() + 0.6F + random.nextFloat() * Math.max(0.8F, entity.getEyeHeight()), pos.getZ(), 0.6F + random.nextFloat() * 0.4F, 0.0F, 0.3F + random.nextFloat() * 0.7F, 20, 0.0F);
/*     */               
/* 123 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 135 */     return meta < PavingStoneType.values().length ? getDefaultState().withProperty(VARIANT, PavingStoneType.values()[meta]) : getDefaultState();
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 141 */     int meta = ((PavingStoneType)state.getValue(VARIANT)).ordinal();
/*     */     
/* 143 */     return meta;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 149 */     return new BlockState(this, new IProperty[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/* 155 */     return new IProperty[] { VARIANT };
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/* 161 */     PavingStoneType type = (PavingStoneType)state.getValue(VARIANT);
/*     */     
/* 163 */     return type.getName() + (fullName ? "_stone" : "");
/*     */   }
/*     */   
/*     */   public static enum PavingStoneType
/*     */     implements IStringSerializable
/*     */   {
/* 169 */     BARRIER, 
/* 170 */     TRAVEL;
/*     */     
/*     */     private PavingStoneType() {}
/*     */     
/*     */     public String getName()
/*     */     {
/* 176 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 182 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockPavingStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */