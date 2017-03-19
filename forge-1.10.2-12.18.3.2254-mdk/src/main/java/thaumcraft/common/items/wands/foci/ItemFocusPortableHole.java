/*     */ package thaumcraft.common.items.wands.foci;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.wands.FocusUpgradeType;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.common.lib.utils.BlockUtils;
/*     */ import thaumcraft.common.tiles.misc.TileHole;
/*     */ 
/*     */ public class ItemFocusPortableHole extends ItemFocusBasic
/*     */ {
/*     */   public ItemFocusPortableHole()
/*     */   {
/*  25 */     super("hole", 594985);
/*     */   }
/*     */   
/*     */ 
/*  29 */   private static final AspectList cost = new AspectList().add(Aspect.ENTROPY, 25).add(Aspect.AIR, 25).add(Aspect.EARTH, 25);
/*     */   
/*     */   public AspectList getVisCost(ItemStack itemstack)
/*     */   {
/*  33 */     return cost;
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean createHole(World world, BlockPos pos, EnumFacing side, byte count, int max)
/*     */   {
/*  39 */     IBlockState bs = world.getBlockState(pos);
/*  40 */     if ((!world.isRemote) && (world.getTileEntity(pos) == null) && (!BlockUtils.isPortableHoleBlackListed(bs)) && (bs.getBlock() != Blocks.bedrock) && (bs.getBlock() != BlocksTC.hole) && ((bs.getBlock().isAir(world, pos)) || (!bs.getBlock().canPlaceBlockAt(world, pos))) && (bs.getBlock().getBlockHardness(world, pos) != -1.0F))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  46 */       if (world.setBlockState(pos, BlocksTC.hole.getDefaultState())) {
/*  47 */         TileHole ts = (TileHole)world.getTileEntity(pos);
/*  48 */         ts.oldblock = bs;
/*  49 */         ts.countdownmax = ((short)max);
/*  50 */         ts.count = count;
/*  51 */         ts.direction = side;
/*  52 */         ts.markDirty();
/*     */       }
/*  54 */       return true; }
/*  55 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onFocusActivation(ItemStack itemstack, World world, EntityLivingBase player, MovingObjectPosition mop, int count)
/*     */   {
/*  61 */     IWand wand = (IWand)itemstack.getItem();
/*     */     
/*  63 */     if ((mop != null) && (mop.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK))
/*     */     {
/*  65 */       if (world.provider.getDimensionId() == thaumcraft.common.config.Config.dimensionOuterId) {
/*  66 */         if (!world.isRemote) {
/*  67 */           world.playSoundEffect(mop.getBlockPos().getX() + 0.5D, mop.getBlockPos().getY() + 0.5D, mop.getBlockPos().getZ() + 0.5D, "thaumcraft:wandfail", 1.0F, 1.0F);
/*     */         }
/*     */         
/*     */ 
/*  71 */         player.swingItem();
/*  72 */         return false;
/*     */       }
/*     */       
/*  75 */       int enlarge = wand.getFocusEnlarge(itemstack);
/*  76 */       int distance = 0;
/*  77 */       int maxdis = 33 + enlarge * 8;
/*  78 */       BlockPos pos = new BlockPos(mop.getBlockPos());
/*  79 */       for (distance = 0; distance < maxdis; distance++) {
/*  80 */         IBlockState bi = world.getBlockState(pos);
/*  81 */         if ((BlockUtils.isPortableHoleBlackListed(bi)) || (bi.getBlock() == Blocks.bedrock) || (bi.getBlock() == BlocksTC.hole) || (bi.getBlock().isAir(world, pos)) || (bi.getBlock().getBlockHardness(world, pos) == -1.0F)) {
/*     */           break;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*  87 */         pos = pos.offset(mop.sideHit.getOpposite());
/*     */       }
/*     */       
/*     */ 
/*  91 */       int di = getUpgradeLevel(wand.getFocusStack(itemstack), FocusUpgradeType.extend);
/*  92 */       short dur = (short)(120 + 60 * di);
/*  93 */       createHole(world, mop.getBlockPos(), mop.sideHit, (byte)(distance + 1), dur);
/*     */       
/*  95 */       player.swingItem();
/*  96 */       if (!world.isRemote) { world.playSoundEffect(mop.getBlockPos().getX() + 0.5D, mop.getBlockPos().getY() + 0.5D, mop.getBlockPos().getZ() + 0.5D, "mob.endermen.portal", 1.0F, 1.0F);
/*     */       }
/*     */     }
/*     */     
/* 100 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank)
/*     */   {
/* 106 */     switch (rank) {
/* 107 */     case 1:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge, FocusUpgradeType.extend };
/* 108 */     case 2:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge, FocusUpgradeType.extend };
/* 109 */     case 3:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge, FocusUpgradeType.extend };
/* 110 */     case 4:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge, FocusUpgradeType.extend };
/* 111 */     case 5:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.enlarge, FocusUpgradeType.extend };
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\wands\foci\ItemFocusPortableHole.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */