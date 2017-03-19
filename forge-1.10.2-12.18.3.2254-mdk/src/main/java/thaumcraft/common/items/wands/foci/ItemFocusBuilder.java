/*     */ package thaumcraft.common.items.wands.foci;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumFacing.Axis;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.items.IArchitect.EnumAxis;
/*     */ import thaumcraft.api.items.IArchitectExtended;
/*     */ import thaumcraft.api.items.IFocusPicker;
/*     */ import thaumcraft.api.wands.FocusUpgradeType;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.common.lib.utils.BlockUtils;
/*     */ 
/*     */ public class ItemFocusBuilder extends ItemFocusBasic implements IArchitectExtended, IFocusPicker
/*     */ {
/*     */   public ItemFocusBuilder()
/*     */   {
/*  31 */     super("builder", 8776595);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onFocusActivation(ItemStack itemstack, World world, EntityLivingBase player, MovingObjectPosition mop, int count)
/*     */   {
/*  37 */     IWand wand = (IWand)itemstack.getItem();
/*     */     
/*  39 */     if ((player instanceof EntityPlayer)) {
/*  40 */       if ((player.isSneaking()) && (mop != null) && (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)) {
/*  41 */         IBlockState bi = world.getBlockState(mop.getBlockPos());
/*  42 */         if ((!world.isRemote) && (world.getTileEntity(mop.getBlockPos()) == null)) {
/*  43 */           ItemStack isout = new ItemStack(bi.getBlock(), 1, bi.getBlock().getMetaFromState(bi));
/*     */           try {
/*  45 */             if (bi.getBlock() != Blocks.air) {
/*  46 */               ItemStack is = BlockUtils.createStackedBlock(bi);
/*  47 */               if (is != null) {
/*  48 */                 isout = is.copy();
/*     */               }
/*     */             }
/*     */           } catch (Exception e) {}
/*  52 */           storePickedBlock(itemstack, isout);
/*     */         } else {
/*  54 */           player.swingItem();
/*     */         }
/*     */       } else {
/*  57 */         mop = getArchitectMOP(itemstack, world, player);
/*  58 */         ItemStack pb = getPickedBlock(itemstack);
/*  59 */         if ((pb != null) && (world.isRemote)) {
/*  60 */           player.swingItem();
/*     */         }
/*  62 */         else if ((pb != null) && (mop != null) && (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)) {
/*  63 */           ArrayList<BlockPos> blocks = getArchitectBlocks(itemstack, world, mop.getBlockPos(), mop.sideHit, (EntityPlayer)player);
/*     */           
/*  65 */           for (BlockPos c : blocks) {
/*  66 */             thaumcraft.common.lib.events.ServerTickEventsFML.addSwapper(world, c, null, pb, 0, (EntityPlayer)player, ((EntityPlayer)player).inventory.currentItem);
/*     */           }
/*  68 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   public void storePickedBlock(ItemStack stack, ItemStack stackout)
/*     */   {
/*  77 */     NBTTagCompound item = new NBTTagCompound();
/*  78 */     stack.setTagInfo("picked", stackout.writeToNBT(item));
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack getPickedBlock(ItemStack stack)
/*     */   {
/*  84 */     ItemStack out = null;
/*  85 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("picked"))) {
/*  86 */       out = new ItemStack(Blocks.air);
/*  87 */       out.readFromNBT(stack.getTagCompound().getCompoundTag("picked"));
/*     */     }
/*  89 */     return out;
/*     */   }
/*     */   
/*  92 */   private static final AspectList cost = new AspectList().add(Aspect.AIR, 1).add(Aspect.EARTH, 1).add(Aspect.ORDER, 1);
/*     */   
/*     */   public AspectList getVisCost(ItemStack itemstack)
/*     */   {
/*  96 */     return cost;
/*     */   }
/*     */   
/*     */ 
/*     */   public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank)
/*     */   {
/* 102 */     switch (rank) {
/* 103 */     case 1:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
/* 104 */     case 2:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
/* 105 */     case 3:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
/* 106 */     case 4:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
/* 107 */     case 5:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
/*     */     }
/* 109 */     return null;
/*     */   }
/*     */   
/*     */   public int getMaxAreaSize(ItemStack focusstack)
/*     */   {
/* 114 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public ArrayList<BlockPos> getArchitectBlocks(ItemStack stack, World world, BlockPos pos, EnumFacing side, EntityPlayer player)
/*     */   {
/* 120 */     IWand wand = (IWand)stack.getItem();
/* 121 */     ArrayList<BlockPos> out = new ArrayList();
/*     */     
/* 123 */     int l = 0;
/* 124 */     if (side.getAxis() == EnumFacing.Axis.X) l = (int)Math.abs(pos.getX() + 0.5D - player.posX);
/* 125 */     if (side.getAxis() == EnumFacing.Axis.Y) l = (int)Math.abs(pos.getY() + 0.5D - player.posY);
/* 126 */     if (side.getAxis() == EnumFacing.Axis.Z) l = (int)Math.abs(pos.getZ() + 0.5D - player.posZ);
/* 127 */     l = Math.min(l, 16 + getUpgradeLevel(wand.getFocusStack(stack), FocusUpgradeType.enlarge) * 8);
/* 128 */     for (int a = 1; a < l; a++) {
/* 129 */       out.add(pos.offset(side, a));
/*     */     }
/* 131 */     return out;
/*     */   }
/*     */   
/*     */   public boolean showAxis(ItemStack stack, World world, EntityPlayer player, EnumFacing side, IArchitect.EnumAxis axis)
/*     */   {
/* 136 */     return false;
/*     */   }
/*     */   
/*     */   public MovingObjectPosition getArchitectMOP(ItemStack stack, World world, EntityLivingBase player)
/*     */   {
/* 141 */     IWand wand = (IWand)stack.getItem();
/* 142 */     return BlockUtils.getTargetBlock(world, player, false, true, 64.0D);
/*     */   }
/*     */   
/*     */   public boolean useBlockHighlight(ItemStack stack)
/*     */   {
/* 147 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\wands\foci\ItemFocusBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */