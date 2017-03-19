/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.tiles.crafting.TileCrucible;
/*     */ 
/*     */ public class BlockCrucible extends BlockTCDevice
/*     */ {
/*     */   public BlockCrucible()
/*     */   {
/*  36 */     super(net.minecraft.block.material.Material.iron, TileCrucible.class);
/*  37 */     setStepSound(net.minecraft.block.Block.soundTypeMetal);
/*     */   }
/*     */   
/*  40 */   private int delay = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
/*     */   {
/*  46 */     if (!world.isRemote) {
/*  47 */       TileCrucible tile = (TileCrucible)world.getTileEntity(pos);
/*  48 */       if ((tile != null) && ((entity instanceof EntityItem)) && (!(entity instanceof thaumcraft.common.entities.EntitySpecialItem)) && (tile.heat > 150) && (tile.tank.getFluidAmount() > 0))
/*     */       {
/*     */ 
/*     */ 
/*  52 */         tile.attemptSmelt((EntityItem)entity);
/*     */       } else {
/*  54 */         this.delay += 1;
/*  55 */         if (this.delay < 10) return;
/*  56 */         this.delay = 0;
/*  57 */         if (((entity instanceof net.minecraft.entity.EntityLivingBase)) && (tile != null) && (tile.heat > 150) && (tile.tank.getFluidAmount() > 0))
/*     */         {
/*     */ 
/*  60 */           entity.attackEntityFrom(DamageSource.inFire, 1.0F);
/*  61 */           world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.fizz", 0.4F, 2.0F + world.rand.nextFloat() * 0.4F);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  66 */     super.onEntityCollidedWithBlock(world, pos, state, entity);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
/*     */   {
/*  75 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
/*  76 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  77 */     float f = 0.125F;
/*  78 */     setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  79 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  80 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  81 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  82 */     setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  83 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  84 */     setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  85 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
/*     */   {
/*  90 */     setBlockBoundsForItemRender();
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos)
/*     */   {
/*  96 */     return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBlockBoundsForItemRender()
/*     */   {
/* 104 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/* 110 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/* 116 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 122 */     TileEntity te = worldIn.getTileEntity(pos);
/* 123 */     if ((te != null) && ((te instanceof TileCrucible))) {
/* 124 */       ((TileCrucible)te).spillRemnants();
/*     */     }
/* 126 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/* 134 */     if (!world.isRemote) {
/* 135 */       FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(player.inventory.getCurrentItem());
/* 136 */       if ((fs != null) && (fs.isFluidEqual(new FluidStack(FluidRegistry.WATER, 1000)))) {
/* 137 */         int volume = fs.amount;
/* 138 */         TileEntity te = world.getTileEntity(pos);
/* 139 */         if ((te != null) && ((te instanceof TileCrucible))) {
/* 140 */           TileCrucible tile = (TileCrucible)te;
/* 141 */           if (tile.tank.getFluidAmount() < tile.tank.getCapacity()) {
/* 142 */             tile.fill(EnumFacing.UP, FluidContainerRegistry.getFluidForFilledItem(player.inventory.getCurrentItem()), true);
/* 143 */             ItemStack emptyContainer = null;
/* 144 */             FluidContainerRegistry.FluidContainerData[] fcs = FluidContainerRegistry.getRegisteredFluidContainerData();
/* 145 */             for (FluidContainerRegistry.FluidContainerData fcd : fcs) {
/* 146 */               if (fcd.filledContainer.isItemEqual(player.inventory.getCurrentItem())) {
/* 147 */                 emptyContainer = fcd.emptyContainer.copy();
/*     */               }
/*     */             }
/* 150 */             player.inventory.decrStackSize(player.inventory.currentItem, 1);
/* 151 */             if ((emptyContainer != null) && 
/* 152 */               (!player.inventory.addItemStackToInventory(emptyContainer))) {
/* 153 */               player.dropPlayerItemWithRandomChoice(emptyContainer, false);
/*     */             }
/*     */             
/* 156 */             player.inventoryContainer.detectAndSendChanges();
/* 157 */             te.markDirty();
/* 158 */             world.markBlockForUpdate(pos);
/* 159 */             world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "game.neutral.swim", 0.33F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F);
/*     */             
/* 161 */             return true;
/*     */           }
/*     */         }
/*     */       }
/* 165 */       else if ((player.inventory.getCurrentItem() != null) && (!player.isSneaking()) && (!(player.getCurrentEquippedItem().getItem() instanceof IWand)) && (side == EnumFacing.UP))
/*     */       {
/* 167 */         TileEntity te = world.getTileEntity(pos);
/* 168 */         if ((te != null) && ((te instanceof TileCrucible))) {
/* 169 */           TileCrucible tile = (TileCrucible)te;
/* 170 */           ItemStack ti = player.inventory.getCurrentItem().copy();
/* 171 */           ti.stackSize = 1;
/* 172 */           if ((tile.heat > 150) && (tile.tank.getFluidAmount() > 0) && (tile.attemptSmelt(ti, player.getName()) == null)) {
/* 173 */             player.inventory.decrStackSize(player.inventory.currentItem, 1);
/* 174 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     } else {
/* 179 */       return true;
/*     */     }
/*     */     
/* 182 */     return super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride()
/*     */   {
/* 187 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getComparatorInputOverride(World world, BlockPos pos)
/*     */   {
/* 193 */     TileEntity te = world.getTileEntity(pos);
/* 194 */     if ((te != null) && ((te instanceof TileCrucible))) {
/* 195 */       ((TileCrucible)te).getClass();float r = ((TileCrucible)te).aspects.visSize() / 100.0F;
/* 196 */       return MathHelper.floor_float(r * 14.0F) + (((TileCrucible)te).aspects.visSize() > 0 ? 1 : 0);
/*     */     }
/* 198 */     return 0;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void randomDisplayTick(World w, BlockPos pos, IBlockState state, Random r)
/*     */   {
/* 204 */     if (r.nextInt(10) == 0) {
/* 205 */       TileEntity te = w.getTileEntity(pos);
/* 206 */       if ((te != null) && ((te instanceof TileCrucible)) && 
/* 207 */         (((TileCrucible)te).tank.getFluidAmount() > 0) && (((TileCrucible)te).heat > 150)) {
/* 208 */         w.playSound(pos.getX(), pos.getY(), pos.getZ(), "liquid.lavapop", 0.1F + r.nextFloat() * 0.1F, 1.2F + r.nextFloat() * 0.2F, false);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockCrucible.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */