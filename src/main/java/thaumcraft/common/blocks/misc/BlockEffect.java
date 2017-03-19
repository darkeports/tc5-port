/*     */ package thaumcraft.common.blocks.misc;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.entities.IEldritchMob;
/*     */ import thaumcraft.client.fx.ParticleEngine;
/*     */ import thaumcraft.client.fx.particles.FXSpark;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockEffect
/*     */   extends BlockTC
/*     */ {
/*  35 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EffType.class);
/*     */   
/*     */   public BlockEffect()
/*     */   {
/*  39 */     super(Material.air);
/*  40 */     setTickRandomly(true);
/*  41 */     setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EffType.SHOCK));
/*  42 */     this.blockResistance = 999.0F;
/*  43 */     setLightLevel(0.5F);
/*  44 */     setCreativeTab(null);
/*     */   }
/*     */   
/*     */   public int getLightValue(IBlockAccess world, BlockPos pos)
/*     */   {
/*  49 */     IBlockState state = world.getBlockState(pos);
/*  50 */     if (state.getBlock() != this) return super.getLightValue(world, pos);
/*  51 */     if ((EffType)world.getBlockState(pos).getValue(VARIANT) == EffType.GLIMMER) {
/*  52 */       return 15;
/*     */     }
/*  54 */     return super.getLightValue(world, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
/*     */   {
/*  60 */     if ((EffType)world.getBlockState(pos).getValue(VARIANT) == EffType.SHOCK) {
/*  61 */       entity.attackEntityFrom(DamageSource.magic, 1 + world.rand.nextInt(2));
/*  62 */       if ((entity instanceof EntityLivingBase)) {
/*  63 */         PotionEffect pe = new PotionEffect(Potion.moveSlowdown.id, 20, 0, true, true);
/*  64 */         ((EntityLivingBase)entity).addPotionEffect(pe);
/*     */       }
/*  66 */       if ((!world.isRemote) && (world.rand.nextInt(100) == 0)) {
/*  67 */         world.setBlockToAir(pos);
/*     */       }
/*     */     }
/*  70 */     else if (((EffType)world.getBlockState(pos).getValue(VARIANT) == EffType.SAPPING) && (!(entity instanceof IEldritchMob)) && 
/*  71 */       ((entity instanceof EntityLivingBase)) && (!((EntityLivingBase)entity).isPotionActive(Potion.wither))) {
/*  72 */       PotionEffect pe0 = new PotionEffect(Potion.wither.id, 40, 0, true, true);
/*  73 */       ((EntityLivingBase)entity).addPotionEffect(pe0);
/*  74 */       PotionEffect pe1 = new PotionEffect(Potion.moveSlowdown.id, 40, 1, true, true);
/*  75 */       ((EntityLivingBase)entity).addPotionEffect(pe1);
/*  76 */       PotionEffect pe2 = new PotionEffect(Potion.hunger.id, 40, 1, true, true);
/*  77 */       ((EntityLivingBase)entity).addPotionEffect(pe2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/*  84 */     super.updateTick(worldIn, pos, state, rand);
/*  85 */     if ((!worldIn.isRemote) && (((EffType)state.getValue(VARIANT) == EffType.SAPPING) || ((EffType)state.getValue(VARIANT) == EffType.SHOCK))) {
/*  86 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void randomDisplayTick(World w, BlockPos pos, IBlockState state, Random r)
/*     */   {
/*  94 */     if (((EffType)state.getValue(VARIANT) == EffType.SAPPING) || ((EffType)state.getValue(VARIANT) == EffType.SHOCK)) {
/*  95 */       float h = r.nextFloat() * 0.33F;
/*  96 */       FXSpark ef = new FXSpark(w, pos.getX() + w.rand.nextFloat(), pos.getY() + 0.1515F + h / 2.0F, pos.getZ() + w.rand.nextFloat(), 0.33F + h);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 101 */       if ((EffType)state.getValue(VARIANT) == EffType.SHOCK) {
/* 102 */         ef.setRBGColorF(0.65F + w.rand.nextFloat() * 0.1F, 1.0F, 1.0F);
/* 103 */         ef.setAlphaF(0.8F);
/*     */       } else {
/* 105 */         ef.setRBGColorF(0.3F - w.rand.nextFloat() * 0.1F, 0.0F, 0.5F + w.rand.nextFloat() * 0.2F);
/*     */       }
/*     */       
/* 108 */       ParticleEngine.instance.addEffect(w, ef);
/*     */       
/* 110 */       if (r.nextInt(50) == 0) {
/* 111 */         w.playSound(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:jacobs", 0.5F, 1.0F + (r.nextFloat() - r.nextFloat()) * 0.2F, false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAir(IBlockAccess world, BlockPos pos)
/*     */   {
/* 121 */     return true;
/*     */   }
/*     */   
/*     */   public int getRenderType()
/*     */   {
/* 126 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean canReplace(World worldIn, BlockPos pos, EnumFacing side, ItemStack stack)
/*     */   {
/* 131 */     return true;
/*     */   }
/*     */   
/*     */   public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
/* 135 */     return null;
/*     */   }
/*     */   
/* 138 */   public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing o) { return false; }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/* 144 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 151 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid)
/*     */   {
/* 157 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
/*     */   {
/* 162 */     return AxisAlignedBB.fromBounds(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 166 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/* 172 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 178 */     return getDefaultState().withProperty(VARIANT, EffType.values()[meta]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 184 */     int meta = ((EffType)state.getValue(VARIANT)).ordinal();
/*     */     
/* 186 */     return meta;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 192 */     return new BlockState(this, new IProperty[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/* 198 */     return new IProperty[] { VARIANT };
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/* 204 */     EffType type = (EffType)state.getValue(VARIANT);
/*     */     
/* 206 */     return type.getName();
/*     */   }
/*     */   
/*     */   public static enum EffType implements IStringSerializable
/*     */   {
/* 211 */     SHOCK, 
/* 212 */     SAPPING, 
/* 213 */     GLIMMER;
/*     */     
/*     */     private EffType() {}
/*     */     
/*     */     public String getName()
/*     */     {
/* 219 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 225 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\misc\BlockEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */