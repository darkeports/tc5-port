/*     */ package thaumcraft.common.items.wands.foci;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.ThaumcraftMaterials;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.items.IArchitect.EnumAxis;
/*     */ import thaumcraft.api.wands.FocusUpgradeType;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.common.items.wands.WandManager;
/*     */ import thaumcraft.common.lib.events.ServerTickEventsFML;
/*     */ import thaumcraft.common.lib.utils.BlockUtils;
/*     */ 
/*     */ public class ItemFocusTrade extends ItemFocusBasic implements thaumcraft.api.items.IArchitect, thaumcraft.api.items.IFocusPicker
/*     */ {
/*     */   public ItemFocusTrade()
/*     */   {
/*  35 */     super("equal_trade", 8747923);
/*     */   }
/*     */   
/*     */ 
/*     */   protected MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityPlayer par2EntityPlayer)
/*     */   {
/*  41 */     float f = 1.0F;
/*  42 */     float f1 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
/*  43 */     float f2 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
/*  44 */     double d0 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * f;
/*  45 */     double d1 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * f + (par1World.isRemote ? par2EntityPlayer.getEyeHeight() - par2EntityPlayer.getDefaultEyeHeight() : par2EntityPlayer.getEyeHeight());
/*  46 */     double d2 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * f;
/*  47 */     Vec3 vec3 = new Vec3(d0, d1, d2);
/*  48 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/*  49 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/*  50 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/*  51 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*  52 */     float f7 = f4 * f5;
/*  53 */     float f8 = f3 * f5;
/*  54 */     double d3 = 5.0D;
/*  55 */     if ((par2EntityPlayer instanceof EntityPlayerMP))
/*     */     {
/*  57 */       d3 = ((EntityPlayerMP)par2EntityPlayer).theItemInWorldManager.getBlockReachDistance();
/*     */     }
/*  59 */     Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
/*  60 */     return par1World.rayTraceBlocks(vec3, vec31, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onFocusActivation(ItemStack itemstack, World world, EntityLivingBase player, MovingObjectPosition mop, int count)
/*     */   {
/*  66 */     IWand wand = (IWand)itemstack.getItem();
/*     */     
/*  68 */     if (((player instanceof EntityPlayer)) && (mop != null) && (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK))
/*     */     {
/*  70 */       IBlockState bi = world.getBlockState(mop.getBlockPos());
/*     */       
/*  72 */       if (player.isSneaking()) {
/*  73 */         if ((!world.isRemote) && (world.getTileEntity(mop.getBlockPos()) == null)) {
/*  74 */           ItemStack isout = new ItemStack(bi.getBlock(), 1, bi.getBlock().getMetaFromState(bi));
/*     */           try {
/*  76 */             if (bi != Blocks.air) {
/*  77 */               ItemStack is = BlockUtils.createStackedBlock(bi);
/*  78 */               if (is != null) {
/*  79 */                 isout = is.copy();
/*     */               }
/*     */             }
/*     */           } catch (Exception e) {}
/*  83 */           storePickedBlock(itemstack, isout);
/*     */         } else {
/*  85 */           player.swingItem();
/*     */         }
/*     */       } else {
/*  88 */         mop = getMovingObjectPositionFromPlayer(world, (EntityPlayer)player, false);
/*  89 */         ItemStack pb = getPickedBlock(itemstack);
/*  90 */         if ((pb != null) && (world.isRemote)) {
/*  91 */           player.swingItem();
/*     */         }
/*  93 */         else if ((pb != null) && (world.getTileEntity(mop.getBlockPos()) == null) && (world.getBlockState(mop.getBlockPos()).getBlock().getMaterial() != ThaumcraftMaterials.MATERIAL_TAINT))
/*     */         {
/*     */ 
/*  96 */           if (isUpgradedWith(wand.getFocusStack(itemstack), FocusUpgradeType.architect)) {
/*  97 */             int sizeX = WandManager.getAreaX(itemstack);
/*  98 */             int sizeZ = WandManager.getAreaZ(itemstack);
/*  99 */             ArrayList<BlockPos> blocks = getArchitectBlocks(itemstack, world, mop.getBlockPos(), mop.sideHit, (EntityPlayer)player);
/*     */             
/* 101 */             for (BlockPos c : blocks) {
/* 102 */               ServerTickEventsFML.addSwapper(world, c, world.getBlockState(c), pb, 0, (EntityPlayer)player, ((EntityPlayer)player).inventory.currentItem);
/*     */             }
/*     */           }
/*     */           else {
/* 106 */             ServerTickEventsFML.addSwapper(world, mop.getBlockPos(), world.getBlockState(mop.getBlockPos()), pb, 3 + wand.getFocusEnlarge(itemstack), (EntityPlayer)player, ((EntityPlayer)player).inventory.currentItem);
/*     */           }
/*     */           
/*     */ 
/* 110 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 114 */     return false;
/*     */   }
/*     */   
/*     */   public boolean onEntitySwing(EntityLivingBase player, ItemStack stack)
/*     */   {
/* 119 */     if ((!player.worldObj.isRemote) && ((player instanceof EntityPlayer))) {
/* 120 */       ItemStack pb = getPickedBlock(stack);
/* 121 */       MovingObjectPosition mop = getMovingObjectPositionFromPlayer(player.worldObj, (EntityPlayer)player);
/* 122 */       if ((mop != null) && (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && 
/* 123 */         (pb != null) && (player.worldObj.getTileEntity(mop.getBlockPos()) == null) && (player.worldObj.getBlockState(mop.getBlockPos()).getBlock().getMaterial() != ThaumcraftMaterials.MATERIAL_TAINT))
/*     */       {
/*     */ 
/* 126 */         ServerTickEventsFML.addSwapper(player.worldObj, mop.getBlockPos(), player.worldObj.getBlockState(mop.getBlockPos()), pb, 0, (EntityPlayer)player, ((EntityPlayer)player).inventory.currentItem);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 133 */     return super.onEntitySwing(player, stack);
/*     */   }
/*     */   
/*     */   public void storePickedBlock(ItemStack stack, ItemStack stackout)
/*     */   {
/* 138 */     NBTTagCompound item = new NBTTagCompound();
/* 139 */     stack.setTagInfo("picked", stackout.writeToNBT(item));
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack getPickedBlock(ItemStack stack)
/*     */   {
/* 145 */     ItemStack out = null;
/* 146 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("picked"))) {
/* 147 */       out = new ItemStack(Blocks.air);
/* 148 */       out.readFromNBT(stack.getTagCompound().getCompoundTag("picked"));
/*     */     }
/* 150 */     return out;
/*     */   }
/*     */   
/* 153 */   private static final AspectList cost = new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.EARTH, 1).add(Aspect.ORDER, 1);
/*     */   
/*     */   public AspectList getVisCost(ItemStack itemstack)
/*     */   {
/* 157 */     if (isUpgradedWith(itemstack, FocusUpgradeType.silktouch)) {
/* 158 */       AspectList cost2 = new AspectList().add(Aspect.AIR, 1).add(Aspect.FIRE, 1).add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.ORDER, 1);
/* 159 */       cost2.add(cost);
/* 160 */       return cost2;
/*     */     }
/* 162 */     return cost;
/*     */   }
/*     */   
/*     */ 
/*     */   public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank)
/*     */   {
/* 168 */     switch (rank) {
/* 169 */     case 1:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
/* 170 */     case 2:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
/* 171 */     case 3:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge, FocusUpgradeType.treasure, FocusUpgradeType.architect };
/* 172 */     case 4:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge };
/* 173 */     case 5:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge, FocusUpgradeType.silktouch };
/*     */     }
/* 175 */     return null;
/*     */   }
/*     */   
/*     */   public int getMaxAreaSize(ItemStack focusstack)
/*     */   {
/* 180 */     return 3 + getUpgradeLevel(focusstack, FocusUpgradeType.enlarge) * 2;
/*     */   }
/*     */   
/* 183 */   ArrayList<BlockPos> checked = new ArrayList();
/*     */   
/*     */   public ArrayList<BlockPos> getArchitectBlocks(ItemStack stack, World world, BlockPos pos, EnumFacing side, EntityPlayer player)
/*     */   {
/* 187 */     IWand wand = (IWand)stack.getItem();
/* 188 */     ItemFocusBasic focus = wand.getFocus(stack);
/*     */     
/* 190 */     if (!focus.isUpgradedWith(wand.getFocusStack(stack), FocusUpgradeType.architect)) { return null;
/*     */     }
/* 192 */     IBlockState bi = world.getBlockState(pos);
/*     */     
/* 194 */     ArrayList<BlockPos> out = new ArrayList();
/* 195 */     this.checked.clear();
/* 196 */     if (side.getAxis() == net.minecraft.util.EnumFacing.Axis.Z) {
/* 197 */       checkNeighbours(world, pos, bi, new BlockPos(pos), side, WandManager.getAreaZ(stack), WandManager.getAreaY(stack), WandManager.getAreaX(stack), out, player);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 202 */       checkNeighbours(world, pos, bi, new BlockPos(pos), side, WandManager.getAreaX(stack), WandManager.getAreaY(stack), WandManager.getAreaZ(stack), out, player);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 207 */     return out;
/*     */   }
/*     */   
/*     */   public void checkNeighbours(World world, BlockPos pos1, IBlockState bi, BlockPos pos2, EnumFacing side, int sizeX, int sizeY, int sizeZ, ArrayList<BlockPos> list, EntityPlayer player)
/*     */   {
/* 212 */     if (this.checked.contains(pos2)) return;
/* 213 */     this.checked.add(pos2);
/* 214 */     switch (side.getAxis()) {
/*     */     case Y: 
/* 216 */       if (Math.abs(pos2.getX() - pos1.getX()) > sizeX) return;
/* 217 */       if (Math.abs(pos2.getZ() - pos1.getZ()) > sizeZ)
/*     */         return;
/*     */       break;
/* 220 */     case Z:  if (Math.abs(pos2.getX() - pos1.getX()) > sizeX) return;
/* 221 */       if (Math.abs(pos2.getY() - pos1.getY()) > sizeZ)
/*     */         return;
/*     */       break;
/* 224 */     case X:  if (Math.abs(pos2.getY() - pos1.getY()) > sizeX) return;
/* 225 */       if (Math.abs(pos2.getZ() - pos1.getZ()) > sizeZ)
/*     */         return;
/*     */       break;
/*     */     }
/* 229 */     if ((world.getBlockState(pos2) == bi) && (BlockUtils.isBlockExposed(world, pos2)) && (!world.isAirBlock(pos2)) && (world.getBlockState(pos2).getBlock().getBlockHardness(world, pos2) >= 0.0F) && (world.canMineBlockBody(player, pos2)))
/*     */     {
/*     */ 
/*     */ 
/* 233 */       list.add(pos2);
/*     */     } else {
/* 235 */       return;
/*     */     }
/* 237 */     for (EnumFacing dir : EnumFacing.values()) {
/* 238 */       if ((dir != side) && (dir.getOpposite() != side)) {
/* 239 */         checkNeighbours(world, pos1, bi, pos2.offset(dir), side, sizeX, sizeY, sizeZ, list, player);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean showAxis(ItemStack stack, World world, EntityPlayer player, EnumFacing side, IArchitect.EnumAxis axis) {
/* 245 */     int dim = WandManager.getAreaDim(stack);
/* 246 */     switch (side.getAxis()) {
/*     */     case Y: 
/* 248 */       if (((axis == IArchitect.EnumAxis.X) && ((dim == 0) || (dim == 1))) || ((axis == IArchitect.EnumAxis.Z) && ((dim == 0) || (dim == 2)))) return true;
/*     */       break;
/*     */     case Z: 
/* 251 */       if (((axis == IArchitect.EnumAxis.Y) && ((dim == 0) || (dim == 1))) || ((axis == IArchitect.EnumAxis.X) && ((dim == 0) || (dim == 2)))) return true;
/*     */       break;
/*     */     case X: 
/* 254 */       if (((axis == IArchitect.EnumAxis.Y) && ((dim == 0) || (dim == 1))) || ((axis == IArchitect.EnumAxis.Z) && ((dim == 0) || (dim == 2)))) return true;
/*     */       break;
/*     */     }
/* 257 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\wands\foci\ItemFocusTrade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */