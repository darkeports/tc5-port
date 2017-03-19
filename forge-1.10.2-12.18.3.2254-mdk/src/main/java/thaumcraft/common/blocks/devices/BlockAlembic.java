/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.IEssentiaContainerItem;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.ILabelable;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.tiles.crafting.TileAlembic;
/*     */ 
/*     */ public class BlockAlembic extends BlockTCDevice implements ILabelable
/*     */ {
/*  31 */   public static final PropertyEnum TYPE = PropertyEnum.create("type", AlembicType.class);
/*     */   
/*     */   public BlockAlembic()
/*     */   {
/*  35 */     super(Material.wood, null);
/*  36 */     setStepSound(Block.soundTypeWood);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty(TYPE, AlembicType.NORMAL));
/*     */   }
/*     */   
/*     */   public TileEntity createNewTileEntity(World world, int metadata)
/*     */   {
/*  42 */     if (metadata == 0) return new TileAlembic();
/*  43 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  48 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  54 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  60 */     return getStateFromMeta(meta);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  66 */     return getDefaultState().withProperty(TYPE, AlembicType.values()[meta]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/*  72 */     return ((AlembicType)state.getValue(TYPE)).ordinal();
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/*  78 */     return new BlockState(this, new IProperty[] { TYPE });
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/*  84 */     AlembicType type = (AlembicType)state.getValue(TYPE);
/*  85 */     return "alembic_" + type.getName();
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/*  91 */     return new IProperty[] { TYPE };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float fx, float fy, float fz)
/*     */   {
/*  98 */     TileEntity te = world.getTileEntity(pos);
/*     */     
/* 100 */     if ((te != null) && ((te instanceof TileAlembic)) && (player.isSneaking()) && (((TileAlembic)te).aspectFilter != null) && (side.ordinal() == ((TileAlembic)te).facing))
/*     */     {
/* 102 */       ((TileAlembic)te).aspectFilter = null;
/* 103 */       ((TileAlembic)te).facing = EnumFacing.DOWN.ordinal();
/* 104 */       te.markDirty();
/* 105 */       world.markBlockForUpdate(pos);
/* 106 */       if (world.isRemote) {
/* 107 */         world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:page", 1.0F, 1.0F, false);
/*     */       } else {
/* 109 */         world.spawnEntityInWorld(new EntityItem(world, pos.getX() + 0.5F + side.getFrontOffsetX() / 3.0F, pos.getY() + 0.5F, pos.getZ() + 0.5F + side.getFrontOffsetZ() / 3.0F, new ItemStack(ItemsTC.label)));
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 115 */     else if ((te != null) && ((te instanceof TileAlembic)) && (player.isSneaking()) && (player.getHeldItem() == null) && ((((TileAlembic)te).aspectFilter == null) || (side.ordinal() != ((TileAlembic)te).facing)))
/*     */     {
/* 117 */       ((TileAlembic)te).aspect = null;
/* 118 */       if (world.isRemote) {
/* 119 */         world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:jar", 0.4F, 1.0F, false);
/* 120 */         world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "game.neutral.swim", 0.5F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F, false);
/*     */       } else {
/* 122 */         AuraHelper.pollute(world, pos, ((TileAlembic)te).amount, true);
/*     */       }
/* 124 */       ((TileAlembic)te).amount = 0;
/* 125 */       te.markDirty();
/* 126 */       world.markBlockForUpdate(pos);
/*     */     }
/*     */     
/* 129 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
/*     */   {
/* 135 */     setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride()
/*     */   {
/* 140 */     return true;
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(World world, BlockPos pos)
/*     */   {
/* 145 */     TileEntity tile = world.getTileEntity(pos);
/* 146 */     if ((tile != null) && ((tile instanceof TileAlembic))) {
/* 147 */       float r = ((TileAlembic)tile).amount / ((TileAlembic)tile).maxAmount;
/* 148 */       return MathHelper.floor_float(r * 14.0F) + (((TileAlembic)tile).amount > 0 ? 1 : 0);
/*     */     }
/* 150 */     return super.getComparatorInputOverride(world, pos);
/*     */   }
/*     */   
/*     */   public static enum AlembicType implements IStringSerializable
/*     */   {
/* 155 */     NORMAL;
/*     */     
/*     */     private AlembicType() {}
/*     */     
/*     */     public String getName() {
/* 160 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 166 */       return getName();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean applyLabel(EntityPlayer player, BlockPos pos, EnumFacing side, ItemStack labelstack)
/*     */   {
/* 172 */     TileEntity te = player.worldObj.getTileEntity(pos);
/* 173 */     if ((te != null) && ((te instanceof TileAlembic)) && (side.ordinal() > 1) && (((TileAlembic)te).aspectFilter == null)) {
/* 174 */       Aspect la = null;
/* 175 */       if (((IEssentiaContainerItem)labelstack.getItem()).getAspects(labelstack) != null) {
/* 176 */         la = ((IEssentiaContainerItem)(IEssentiaContainerItem)labelstack.getItem()).getAspects(labelstack).getAspects()[0];
/*     */       }
/* 178 */       if ((((TileAlembic)te).amount == 0) && (la == null)) {
/* 179 */         return false;
/*     */       }
/* 181 */       Aspect aspect = null;
/* 182 */       if ((((TileAlembic)te).amount == 0) && (la != null)) {
/* 183 */         aspect = la;
/*     */       }
/* 185 */       if (((TileAlembic)te).amount > 0) {
/* 186 */         aspect = ((TileAlembic)te).aspect;
/*     */       }
/*     */       
/* 189 */       if (aspect == null) { return false;
/*     */       }
/* 191 */       onBlockPlacedBy(player.worldObj, pos, player.worldObj.getBlockState(pos), player, null);
/* 192 */       ((TileAlembic)te).aspectFilter = aspect;
/* 193 */       ((TileAlembic)te).facing = side.ordinal();
/* 194 */       te.markDirty();
/* 195 */       player.worldObj.markBlockForUpdate(pos);
/* 196 */       player.worldObj.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:page", 1.0F, 1.0F);
/* 197 */       return true;
/*     */     }
/* 199 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockAlembic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */