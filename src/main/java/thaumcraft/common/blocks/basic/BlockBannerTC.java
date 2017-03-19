/*     */ package thaumcraft.common.blocks.basic;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.ITileEntityProvider;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.IEssentiaContainerItem;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ import thaumcraft.common.items.resources.ItemPhial;
/*     */ import thaumcraft.common.tiles.misc.TileBanner;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockBannerTC
/*     */   extends BlockTC
/*     */   implements ITileEntityProvider
/*     */ {
/*     */   public BlockBannerTC()
/*     */   {
/*  36 */     super(Material.wood);
/*  37 */     setHardness(1.0F);
/*  38 */     setStepSound(soundTypeWood);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*     */   {
/*  45 */     par3List.add(new ItemStack(par1, 1, 1));
/*  46 */     for (int a = 0; a < 16; a++) {
/*  47 */       ItemStack banner = new ItemStack(par1, 1, 0);
/*  48 */       banner.setTagCompound(new NBTTagCompound());
/*  49 */       banner.getTagCompound().setByte("color", (byte)a);
/*  50 */       par3List.add(banner);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public int getRenderType()
/*     */   {
/*  57 */     return -1;
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess, BlockPos pos)
/*     */   {
/*  62 */     TileEntity tile = par1iBlockAccess.getTileEntity(pos);
/*  63 */     if ((tile != null) && ((tile instanceof TileBanner))) {
/*  64 */       if (((TileBanner)tile).getWall()) {
/*  65 */         switch (((TileBanner)tile).getBannerFacing()) {
/*  66 */         case 0:  setBlockBounds(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.25F); break;
/*  67 */         case 8:  setBlockBounds(0.0F, -1.0F, 0.75F, 1.0F, 1.0F, 1.0F); break;
/*  68 */         case 12:  setBlockBounds(0.0F, -1.0F, 0.0F, 0.25F, 1.0F, 1.0F); break;
/*  69 */         case 4:  setBlockBounds(0.75F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */       } else {
/*  72 */         setBlockBounds(0.33F, 0.0F, 0.33F, 0.66F, 2.0F, 0.66F);
/*     */       }
/*     */     } else {
/*  75 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*  77 */     super.setBlockBoundsBasedOnState(par1iBlockAccess, pos);
/*     */   }
/*     */   
/*     */ 
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/*  83 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  89 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  95 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/* 102 */     if ((p.isSneaking()) || ((p.inventory.getCurrentItem() != null) && ((p.inventory.getCurrentItem().getItem() instanceof ItemPhial))))
/*     */     {
/*     */ 
/* 105 */       TileBanner te = (TileBanner)w.getTileEntity(pos);
/* 106 */       if (te != null)
/*     */       {
/* 108 */         if (te.getColor() >= 0) {
/* 109 */           if (p.isSneaking()) {
/* 110 */             te.setAspect(null);
/*     */           }
/* 112 */           else if (((IEssentiaContainerItem)p.getHeldItem().getItem()).getAspects(p.getHeldItem()) != null) {
/* 113 */             te.setAspect(((IEssentiaContainerItem)(IEssentiaContainerItem)p.getHeldItem().getItem()).getAspects(p.getHeldItem()).getAspects()[0]);
/* 114 */             p.getHeldItem().stackSize -= 1;
/*     */           }
/*     */           
/* 117 */           w.markBlockForUpdate(pos);
/* 118 */           te.markDirty();
/* 119 */           w.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "step.cloth", 1.0F, 1.0F);
/*     */         }
/*     */       }
/*     */     }
/* 123 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta)
/*     */   {
/* 129 */     return new TileBanner();
/*     */   }
/*     */   
/*     */   public boolean hasTileEntity(IBlockState state)
/*     */   {
/* 134 */     return true;
/*     */   }
/*     */   
/*     */   public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player)
/*     */   {
/* 139 */     TileEntity te = world.getTileEntity(pos);
/* 140 */     if ((te instanceof TileBanner))
/*     */     {
/* 142 */       ItemStack drop = new ItemStack(this, 1, ((TileBanner)te).getColor() >= 0 ? 0 : 1);
/* 143 */       if ((((TileBanner)te).getColor() >= 0) || (((TileBanner)te).getAspect() != null)) {
/* 144 */         drop.setTagCompound(new NBTTagCompound());
/* 145 */         if (((TileBanner)te).getAspect() != null) {
/* 146 */           drop.getTagCompound().setString("aspect", ((TileBanner)te).getAspect().getTag());
/*     */         }
/* 148 */         drop.getTagCompound().setByte("color", ((TileBanner)te).getColor());
/*     */       }
/* 150 */       return drop;
/*     */     }
/*     */     
/* 153 */     return super.getPickBlock(target, world, pos, player);
/*     */   }
/*     */   
/*     */ 
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
/*     */   {
/* 159 */     TileEntity te = worldIn.getTileEntity(pos);
/*     */     
/* 161 */     if ((te instanceof TileBanner))
/*     */     {
/* 163 */       ItemStack drop = new ItemStack(this, 1, ((TileBanner)te).getColor() >= 0 ? 0 : 1);
/* 164 */       if ((((TileBanner)te).getColor() >= 0) || (((TileBanner)te).getAspect() != null)) {
/* 165 */         drop.setTagCompound(new NBTTagCompound());
/* 166 */         if (((TileBanner)te).getAspect() != null) {
/* 167 */           drop.getTagCompound().setString("aspect", ((TileBanner)te).getAspect().getTag());
/*     */         }
/* 169 */         drop.getTagCompound().setByte("color", ((TileBanner)te).getColor());
/*     */       }
/*     */       
/* 172 */       spawnAsEntity(worldIn, pos, drop);
/*     */     }
/*     */     else
/*     */     {
/* 176 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
/*     */   {
/* 183 */     if ((te instanceof TileBanner))
/*     */     {
/* 185 */       ItemStack drop = new ItemStack(this, 1, ((TileBanner)te).getColor() >= 0 ? 0 : 1);
/* 186 */       if ((((TileBanner)te).getColor() >= 0) || (((TileBanner)te).getAspect() != null)) {
/* 187 */         drop.setTagCompound(new NBTTagCompound());
/* 188 */         if (((TileBanner)te).getAspect() != null) {
/* 189 */           drop.getTagCompound().setString("aspect", ((TileBanner)te).getAspect().getTag());
/*     */         }
/* 191 */         drop.getTagCompound().setByte("color", ((TileBanner)te).getColor());
/*     */       }
/*     */       
/* 194 */       spawnAsEntity(worldIn, pos, drop);
/*     */     }
/*     */     else
/*     */     {
/* 198 */       super.harvestBlock(worldIn, player, pos, state, (TileEntity)null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockBannerTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */