/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IEssentiaContainerItem;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.blocks.ILabelable;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.lib.CustomSoundType;
/*     */ import thaumcraft.common.tiles.devices.TileJarBrain;
/*     */ import thaumcraft.common.tiles.essentia.TileJarFillable;
/*     */ import thaumcraft.common.tiles.essentia.TileJarFillableVoid;
/*     */ 
/*     */ public class BlockJar extends BlockTCDevice implements ILabelable
/*     */ {
/*  43 */   public static final PropertyEnum TYPE = PropertyEnum.create("type", JarType.class);
/*     */   
/*     */   public BlockJar()
/*     */   {
/*  47 */     super(Material.glass, null);
/*  48 */     setHardness(0.3F);
/*  49 */     setStepSound(new CustomSoundType("jar", 1.0F, 1.0F));
/*  50 */     setCreativeTab(Thaumcraft.tabTC);
/*  51 */     setDefaultState(this.blockState.getBaseState().withProperty(TYPE, JarType.NORMAL));
/*  52 */     setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.75F, 0.8125F);
/*     */   }
/*     */   
/*     */   public boolean defineVariantsForItemBlock()
/*     */   {
/*  57 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumWorldBlockLayer getBlockLayer()
/*     */   {
/*  64 */     return EnumWorldBlockLayer.TRANSLUCENT;
/*     */   }
/*     */   
/*     */   public TileEntity createNewTileEntity(World world, int metadata)
/*     */   {
/*  69 */     if (metadata == 0) return new TileJarFillable();
/*  70 */     if (metadata == 1) return new TileJarFillableVoid();
/*  71 */     if (metadata == 2) return new TileJarBrain();
/*  72 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  77 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  83 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  89 */     return getStateFromMeta(meta);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  95 */     return meta < JarType.values().length ? getDefaultState().withProperty(TYPE, JarType.values()[meta]) : getDefaultState();
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 101 */     return ((JarType)state.getValue(TYPE)).ordinal();
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 107 */     return new BlockState(this, new IProperty[] { TYPE });
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/* 113 */     JarType type = (JarType)state.getValue(TYPE);
/* 114 */     return "jar_" + type.getName();
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/* 120 */     return new IProperty[] { TYPE };
/*     */   }
/*     */   
/*     */ 
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 126 */     spillEssentia = false;
/* 127 */     super.breakBlock(worldIn, pos, state);
/* 128 */     spillEssentia = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
/*     */   {
/* 135 */     TileEntity te = worldIn.getTileEntity(pos);
/* 136 */     if ((te instanceof TileJarFillable))
/*     */     {
/* 138 */       spawnFilledJar(worldIn, pos, state, (TileJarFillable)te);
/*     */ 
/*     */     }
/* 141 */     else if ((te instanceof TileJarBrain))
/*     */     {
/* 143 */       spawnBrainJar(worldIn, pos, state, (TileJarBrain)te);
/*     */     }
/*     */     else
/*     */     {
/* 147 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
/*     */   {
/* 154 */     if ((te instanceof TileJarFillable))
/*     */     {
/* 156 */       spawnFilledJar(worldIn, pos, state, (TileJarFillable)te);
/*     */ 
/*     */     }
/* 159 */     else if ((te instanceof TileJarBrain))
/*     */     {
/* 161 */       spawnBrainJar(worldIn, pos, state, (TileJarBrain)te);
/*     */     }
/*     */     else
/*     */     {
/* 165 */       super.harvestBlock(worldIn, player, pos, state, (TileEntity)null);
/*     */     }
/*     */   }
/*     */   
/*     */   private void spawnFilledJar(World world, BlockPos pos, IBlockState state, TileJarFillable te) {
/* 170 */     ItemStack drop = new ItemStack(BlocksTC.jar, 1, getMetaFromState(state));
/* 171 */     if (te.amount > 0) {
/* 172 */       ((BlockJarItem)drop.getItem()).setAspects(drop, new AspectList().add(te.aspect, te.amount));
/*     */     }
/* 174 */     if (te.aspectFilter != null) {
/* 175 */       if (!drop.hasTagCompound()) drop.setTagCompound(new NBTTagCompound());
/* 176 */       drop.getTagCompound().setString("AspectFilter", te.aspectFilter.getTag());
/*     */     }
/* 178 */     if (te.blocked) spawnAsEntity(world, pos, new ItemStack(ItemsTC.jarBrace));
/* 179 */     spawnAsEntity(world, pos, drop);
/*     */   }
/*     */   
/*     */   private void spawnBrainJar(World world, BlockPos pos, IBlockState state, TileJarBrain te) {
/* 183 */     ItemStack drop = new ItemStack(BlocksTC.jar, 1, getMetaFromState(state));
/* 184 */     if (te.xp > 0) {
/* 185 */       drop.setTagInfo("xp", new NBTTagInt(te.xp));
/*     */     }
/* 187 */     spawnAsEntity(world, pos, drop);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase ent, ItemStack stack)
/*     */   {
/* 193 */     int l = MathHelper.floor_double(ent.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/* 194 */     TileEntity tile = world.getTileEntity(pos);
/* 195 */     if ((tile instanceof TileJarFillable)) {
/* 196 */       if (l == 0) ((TileJarFillable)tile).facing = 2;
/* 197 */       if (l == 1) ((TileJarFillable)tile).facing = 5;
/* 198 */       if (l == 2) ((TileJarFillable)tile).facing = 3;
/* 199 */       if (l == 3) { ((TileJarFillable)tile).facing = 4;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float fx, float fy, float fz)
/*     */   {
/* 207 */     TileEntity te = world.getTileEntity(pos);
/* 208 */     if ((te != null) && ((te instanceof TileJarBrain))) {
/* 209 */       ((TileJarBrain)te).eatDelay = 40;
/* 210 */       if (!world.isRemote)
/*     */       {
/* 212 */         int var6 = world.rand.nextInt(Math.min(((TileJarBrain)te).xp + 1, 64));
/* 213 */         if (var6 > 0) {
/* 214 */           ((TileJarBrain)te).xp -= var6;
/* 215 */           int xp = var6;
/* 216 */           while (xp > 0)
/*     */           {
/* 218 */             int var2 = EntityXPOrb.getXPSplit(xp);
/* 219 */             xp -= var2;
/* 220 */             world.spawnEntityInWorld(new EntityXPOrb(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, var2));
/*     */           }
/* 222 */           world.markBlockForUpdate(pos);
/* 223 */           te.markDirty();
/*     */         }
/*     */       } else {
/* 226 */         world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:jar", 0.2F, 1.0F, false);
/*     */       }
/*     */     }
/*     */     
/* 230 */     if ((te != null) && ((te instanceof TileJarFillable)) && (!((TileJarFillable)te).blocked) && (player.getHeldItem() != null) && (player.getHeldItem().getItem() == ItemsTC.jarBrace))
/*     */     {
/* 232 */       ((TileJarFillable)te).blocked = true;
/* 233 */       player.getHeldItem().stackSize -= 1;
/* 234 */       if (world.isRemote) {
/* 235 */         world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:key", 1.0F, 1.0F, false);
/*     */       } else {
/* 237 */         te.markDirty();
/*     */       }
/*     */       
/*     */     }
/* 241 */     else if ((te != null) && ((te instanceof TileJarFillable)) && (player.isSneaking()) && (((TileJarFillable)te).aspectFilter != null) && (side.ordinal() == ((TileJarFillable)te).facing))
/*     */     {
/* 243 */       ((TileJarFillable)te).aspectFilter = null;
/* 244 */       if (world.isRemote) {
/* 245 */         world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:page", 1.0F, 1.0F, false);
/*     */       } else {
/* 247 */         world.spawnEntityInWorld(new EntityItem(world, pos.getX() + 0.5F + side.getFrontOffsetX() / 3.0F, pos.getY() + 0.5F, pos.getZ() + 0.5F + side.getFrontOffsetZ() / 3.0F, new ItemStack(ItemsTC.label)));
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 253 */     else if ((te != null) && ((te instanceof TileJarFillable)) && (player.isSneaking()) && (player.getHeldItem() == null)) {
/* 254 */       if (((TileJarFillable)te).aspectFilter == null)
/* 255 */         ((TileJarFillable)te).aspect = null;
/* 256 */       if (world.isRemote) {
/* 257 */         world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:jar", 0.4F, 1.0F, false);
/* 258 */         world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "game.neutral.swim", 0.5F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F, false);
/*     */       } else {
/* 260 */         AuraHelper.pollute(world, pos, ((TileJarFillable)te).amount, true);
/*     */       }
/* 262 */       ((TileJarFillable)te).amount = 0;
/* 263 */       te.markDirty();
/*     */     }
/*     */     
/* 266 */     return true;
/*     */   }
/*     */   
/*     */   public boolean applyLabel(EntityPlayer player, BlockPos pos, EnumFacing side, ItemStack labelstack)
/*     */   {
/* 271 */     TileEntity te = player.worldObj.getTileEntity(pos);
/* 272 */     if ((te != null) && ((te instanceof TileJarFillable)) && (((TileJarFillable)te).aspectFilter == null)) {
/* 273 */       if ((((TileJarFillable)te).amount == 0) && (((IEssentiaContainerItem)player.getHeldItem().getItem()).getAspects(player.getHeldItem()) == null)) {
/* 274 */         return false;
/*     */       }
/* 276 */       if ((((TileJarFillable)te).amount == 0) && (((IEssentiaContainerItem)player.getHeldItem().getItem()).getAspects(player.getHeldItem()) != null)) {
/* 277 */         ((TileJarFillable)te).aspect = ((IEssentiaContainerItem)(IEssentiaContainerItem)player.getHeldItem().getItem()).getAspects(player.getHeldItem()).getAspects()[0];
/*     */       }
/* 279 */       onBlockPlacedBy(player.worldObj, pos, player.worldObj.getBlockState(pos), player, null);
/* 280 */       ((TileJarFillable)te).aspectFilter = ((TileJarFillable)te).aspect;
/* 281 */       player.worldObj.markBlockForUpdate(pos);
/* 282 */       te.markDirty();
/* 283 */       player.worldObj.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:jar", 0.4F, 1.0F);
/* 284 */       return true;
/*     */     }
/* 286 */     return false;
/*     */   }
/*     */   
/*     */   public float getEnchantPowerBonus(World world, BlockPos pos)
/*     */   {
/* 291 */     TileEntity te = world.getTileEntity(pos);
/* 292 */     if ((te != null) && ((te instanceof TileJarBrain))) {
/* 293 */       return 2.0F;
/*     */     }
/* 295 */     return super.getEnchantPowerBonus(world, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
/*     */   {
/* 302 */     TileEntity tile = world.getTileEntity(pos);
/* 303 */     if ((tile != null) && ((tile instanceof TileJarBrain)) && (((TileJarBrain)tile).xp >= ((TileJarBrain)tile).xpMax)) {
/* 304 */       Thaumcraft.proxy.getFX().wispFX5(pos.getX() + 0.5F, pos.getY() + 0.8F, pos.getZ() + 0.5F, pos.getX() + 0.3F + world.rand.nextFloat() * 0.4F, pos.getY() + 0.8F, pos.getZ() + 0.3F + world.rand.nextFloat() * 0.4F, 0.5F, true, -0.025F, Aspect.DEATH.getColor());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasComparatorInputOverride()
/*     */   {
/* 313 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getComparatorInputOverride(World world, BlockPos pos)
/*     */   {
/* 320 */     TileEntity tile = world.getTileEntity(pos);
/* 321 */     if ((tile != null) && ((tile instanceof TileJarBrain))) {
/* 322 */       float r = ((TileJarBrain)tile).xp / ((TileJarBrain)tile).xpMax;
/* 323 */       return MathHelper.floor_float(r * 14.0F) + (((TileJarBrain)tile).xp > 0 ? 1 : 0);
/*     */     }
/* 325 */     if ((tile != null) && ((tile instanceof TileJarFillable))) {
/* 326 */       float r = ((TileJarFillable)tile).amount / ((TileJarFillable)tile).maxAmount;
/* 327 */       return MathHelper.floor_float(r * 14.0F) + (((TileJarFillable)tile).amount > 0 ? 1 : 0);
/*     */     }
/* 329 */     return super.getComparatorInputOverride(world, pos);
/*     */   }
/*     */   
/*     */   public static enum JarType implements IStringSerializable
/*     */   {
/* 334 */     NORMAL,  VOID,  BRAIN;
/*     */     
/*     */     private JarType() {}
/*     */     
/*     */     public String getName() {
/* 339 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 345 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockJar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */