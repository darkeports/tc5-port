/*     */ package thaumcraft.common.blocks.world.taint;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.BlockFluidBase;
/*     */ import net.minecraftforge.fluids.BlockFluidFinite;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.ThaumcraftMaterials;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.internal.WeightedRandomLoot;
/*     */ import thaumcraft.api.items.ItemGenericEssentiaContainer;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.EntityFallingTaint;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintSwarm;
/*     */ import thaumcraft.common.lib.CustomSoundType;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class BlockTaint
/*     */   extends BlockTC
/*     */   implements ITaintBlock
/*     */ {
/*  51 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", TaintType.class);
/*     */   
/*     */   public BlockTaint()
/*     */   {
/*  55 */     super(ThaumcraftMaterials.MATERIAL_TAINT);
/*  56 */     setHardness(10.0F);
/*  57 */     setResistance(100.0F);
/*  58 */     setStepSound(new CustomSoundType("gore", 0.5F, 0.8F));
/*  59 */     setTickRandomly(true);
/*  60 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public MapColor getMapColor(IBlockState state)
/*     */   {
/*  67 */     return MapColor.purpleColor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumWorldBlockLayer getBlockLayer()
/*     */   {
/*  76 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */   
/*     */   public void die(World world, BlockPos pos, IBlockState blockState)
/*     */   {
/*  81 */     if ((TaintType)blockState.getValue(VARIANT) == TaintType.ROCK) {
/*  82 */       world.setBlockState(pos, BlocksTC.stone.getStateFromMeta(13));
/*     */     }
/*  84 */     else if (((TaintType)blockState.getValue(VARIANT) == TaintType.CRUST) || ((TaintType)blockState.getValue(VARIANT) == TaintType.SOIL))
/*     */     {
/*  86 */       if ((Config.soilToDirt) && ((TaintType)blockState.getValue(VARIANT) == TaintType.SOIL)) {
/*  87 */         world.setBlockState(pos, Blocks.dirt.getDefaultState());
/*     */       } else {
/*  89 */         world.setBlockState(pos, BlocksTC.taintDust.getDefaultState().withProperty(BlockFluidBase.LEVEL, Integer.valueOf(3)));
/*     */       }
/*     */     }
/*  92 */     else if ((TaintType)blockState.getValue(VARIANT) == TaintType.GEYSER) {
/*  93 */       world.setBlockState(pos, BlocksTC.fluxGoo.getDefaultState());
/*     */     } else {
/*  95 */       world.setBlockToAir(pos);
/*     */     }
/*  97 */     Utils.resetBiomeAt(world, pos, world.rand.nextInt(25) == 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
/*     */   {
/* 105 */     if (!world.isRemote)
/*     */     {
/*     */ 
/* 108 */       if ((AuraHelper.getAura(world, pos, Aspect.FLUX) < Config.AURABASE / 25) && (random.nextInt(10) == 0)) {
/* 109 */         die(world, pos, state);
/* 110 */         return;
/*     */       }
/*     */       
/* 113 */       if ((TaintType)state.getValue(VARIANT) != TaintType.ROCK) { BlockTaintFibre.spreadFibres(world, pos);
/*     */       }
/*     */       
/* 116 */       if ((TaintType)state.getValue(VARIANT) == TaintType.CRUST) {
/* 117 */         Random r = new Random(pos.toLong());
/* 118 */         if (tryToFall(world, pos, pos)) {
/* 119 */           return;
/*     */         }
/* 121 */         if (world.isAirBlock(pos.up())) {
/* 122 */           boolean doIt = true;
/* 123 */           EnumFacing dir = EnumFacing.HORIZONTALS[random.nextInt(4)];
/* 124 */           for (int a = 1; a < 4; a++) {
/* 125 */             if (!world.isAirBlock(pos.offset(dir).down(a))) {
/* 126 */               doIt = false;
/* 127 */               break;
/*     */             }
/* 129 */             if (world.getBlockState(pos.down(a)).getBlock() != this) {
/* 130 */               doIt = false;
/* 131 */               break;
/*     */             }
/*     */           }
/* 134 */           if ((doIt) && 
/* 135 */             (tryToFall(world, pos, pos.offset(dir)))) { return;
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 140 */       else if ((TaintType)state.getValue(VARIANT) == TaintType.GEYSER) {
/* 141 */         if ((world.rand.nextFloat() < 0.2D) && (world.getClosestPlayer(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 24.0D) != null)) {
/* 142 */           Entity e = new EntityTaintSwarm(world);
/* 143 */           e.setLocationAndAngles(pos.getX() + 0.5F, pos.getY() + 1.25F, pos.getZ() + 0.5F, world.rand.nextInt(360), 0.0F);
/* 144 */           world.spawnEntityInWorld(e);
/*     */         }
/* 146 */         else if (AuraHelper.getAura(world, pos, Aspect.FLUX) < Config.AURABASE / 4) {
/* 147 */           AuraHelper.pollute(world, pos, 1, true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*     */   {
/* 157 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */   public int damageDropped(IBlockState state)
/*     */   {
/* 162 */     return getMetaFromState(state);
/*     */   }
/*     */   
/*     */   public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
/*     */   {
/* 167 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity)
/*     */   {
/* 174 */     if ((!world.isRemote) && ((entity instanceof EntityLivingBase)) && (!((EntityLivingBase)entity).isEntityUndead()) && 
/* 175 */       (world.rand.nextInt(750) == 0)) {
/* 176 */       ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(PotionFluxTaint.instance.getId(), 200, 0, false, true));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
/*     */   {
/* 183 */     if (eventID == 1)
/*     */     {
/* 185 */       if (worldIn.isRemote) {
/* 186 */         worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:roots", 0.1F, 0.9F + worldIn.rand.nextFloat() * 0.2F, false);
/*     */       }
/* 188 */       return true;
/*     */     }
/* 190 */     return super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 196 */     return getDefaultState().withProperty(VARIANT, TaintType.values()[meta]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 202 */     int meta = ((TaintType)state.getValue(VARIANT)).ordinal();
/*     */     
/* 204 */     return meta;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 210 */     return new BlockState(this, new IProperty[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/* 216 */     return new IProperty[] { VARIANT };
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/* 222 */     TaintType type = (TaintType)state.getValue(VARIANT);
/*     */     
/* 224 */     return (fullName ? "taint_" : "") + type.getName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean canFallBelow(World world, BlockPos pos)
/*     */   {
/* 234 */     IBlockState bs = world.getBlockState(pos);
/* 235 */     Block l = bs.getBlock();
/*     */     
/* 237 */     for (int xx = -1; xx <= 1; xx++) for (int zz = -1; zz <= 1; zz++) for (int yy = -1; yy <= 1; yy++) {
/* 238 */           if (Utils.isWoodLog(world, pos.add(xx, yy, zz))) {
/* 239 */             return false;
/*     */           }
/*     */         }
/* 242 */     if (l.isAir(world, pos))
/*     */     {
/* 244 */       return true;
/*     */     }
/* 246 */     if ((l == BlocksTC.fluxGoo) && (((Integer)bs.getValue(BlockFluidFinite.LEVEL)).intValue() >= 4))
/*     */     {
/* 248 */       return false;
/*     */     }
/* 250 */     if ((l == Blocks.fire) || (l == BlocksTC.taintFibre))
/*     */     {
/* 252 */       return true;
/*     */     }
/* 254 */     if (l.isReplaceable(world, pos))
/*     */     {
/* 256 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 260 */     return l.getMaterial() == Material.water;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean tryToFall(World world, BlockPos pos, BlockPos pos2)
/*     */   {
/* 266 */     if (!BlockTaintFibre.isOnlyAdjacentToTaint(world, pos)) { return false;
/*     */     }
/* 268 */     if ((canFallBelow(world, pos2.down())) && (pos2.getY() >= 0))
/*     */     {
/* 270 */       byte b0 = 32;
/*     */       
/* 272 */       if (world.isAreaLoaded(pos2.add(-b0, -b0, -b0), pos2.add(b0, b0, b0)))
/*     */       {
/* 274 */         if (!world.isRemote)
/*     */         {
/* 276 */           EntityFallingTaint entityfalling = new EntityFallingTaint(world, pos2.getX() + 0.5F, pos2.getY() + 0.5F, pos2.getZ() + 0.5F, world.getBlockState(pos), pos);
/*     */           
/* 278 */           world.spawnEntityInWorld(entityfalling);
/* 279 */           return true;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 284 */         world.setBlockToAir(pos);
/* 285 */         BlockPos p2 = new BlockPos(pos2);
/* 286 */         while ((canFallBelow(world, p2.down())) && (p2.getY() > 0))
/*     */         {
/* 288 */           p2 = p2.down();
/*     */         }
/*     */         
/* 291 */         if (p2.getY() > 0)
/*     */         {
/* 293 */           world.setBlockState(p2, BlocksTC.taintBlock.getDefaultState());
/*     */         }
/*     */       }
/*     */     }
/* 297 */     return false;
/*     */   }
/*     */   
/* 300 */   static Random r = new Random(System.currentTimeMillis());
/* 301 */   static ArrayList<WeightedRandomLoot> pdrops = null;
/*     */   
/*     */ 
/*     */   public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
/*     */   {
/* 306 */     if ((state.getBlock() == this) && ((TaintType)state.getValue(VARIANT) == TaintType.ROCK)) {
/* 307 */       int rr = r.nextInt(15) + fortune;
/* 308 */       if (rr > 13) {
/* 309 */         List<ItemStack> ret = new ArrayList();
/* 310 */         ItemStack is = new ItemStack(ItemsTC.crystalEssence);
/* 311 */         ((ItemGenericEssentiaContainer)ItemsTC.crystalEssence).setAspects(is, new AspectList().add(Aspect.FLUX, 1));
/* 312 */         ret.add(is);
/* 313 */         return ret;
/*     */       }
/*     */     }
/* 316 */     return super.getDrops(world, pos, state, fortune);
/*     */   }
/*     */   
/*     */   protected boolean canSilkHarvest()
/*     */   {
/* 321 */     return false;
/*     */   }
/*     */   
/*     */   public static enum TaintType implements IStringSerializable
/*     */   {
/* 326 */     CRUST, 
/* 327 */     SOIL, 
/* 328 */     GEYSER, 
/* 329 */     ROCK;
/*     */     
/*     */     private TaintType() {}
/*     */     
/*     */     public String getName()
/*     */     {
/* 335 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 341 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\taint\BlockTaint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */