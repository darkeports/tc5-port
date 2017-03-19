/*     */ package thaumcraft.common.items.wands.foci;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings.GameType;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.wands.FocusUpgradeType;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.api.wands.ItemFocusBasic.WandFocusAnimation;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.utils.BlockUtils;
/*     */ 
/*     */ public class ItemFocusExcavation extends ItemFocusBasic
/*     */ {
/*     */   public ItemFocusExcavation()
/*     */   {
/*  37 */     super("excavate", 409606);
/*     */   }
/*     */   
/*     */   public AspectList getVisCost(ItemStack itemstack)
/*     */   {
/*  42 */     AspectList cost = new AspectList().add(Aspect.EARTH, 1);
/*     */     
/*  44 */     if (isUpgradedWith(itemstack, FocusUpgradeType.silktouch)) {
/*  45 */       AspectList cost2 = new AspectList();
/*  46 */       cost2 = new AspectList().add(Aspect.AIR, 1).add(Aspect.FIRE, 1).add(Aspect.WATER, 1).add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1);
/*  47 */       cost2.add(cost);
/*  48 */       cost2.add(Aspect.EARTH, getUpgradeLevel(itemstack, FocusUpgradeType.enlarge));
/*  49 */       return cost2;
/*     */     }
/*  51 */     if (isUpgradedWith(itemstack, dowsing)) {
/*  52 */       AspectList cost2 = new AspectList();
/*  53 */       cost2 = new AspectList().add(Aspect.FIRE, 1).add(Aspect.ORDER, 1);
/*  54 */       cost2.add(cost);
/*  55 */       cost2.add(Aspect.EARTH, getUpgradeLevel(itemstack, FocusUpgradeType.enlarge));
/*  56 */       return cost2;
/*     */     }
/*     */     
/*  59 */     cost.add(Aspect.EARTH, getUpgradeLevel(itemstack, FocusUpgradeType.enlarge));
/*  60 */     return cost;
/*     */   }
/*     */   
/*     */   public boolean isVisCostPerTick(ItemStack itemstack)
/*     */   {
/*  65 */     return true;
/*     */   }
/*     */   
/*  68 */   static HashMap<String, Long> soundDelay = new HashMap();
/*  69 */   static HashMap<String, Object> beam = new HashMap();
/*  70 */   static HashMap<String, Float> breakcount = new HashMap();
/*  71 */   static HashMap<String, Integer> lastX = new HashMap();
/*  72 */   static HashMap<String, Integer> lastY = new HashMap();
/*  73 */   static HashMap<String, Integer> lastZ = new HashMap();
/*     */   
/*     */ 
/*     */   public boolean onFocusActivation(ItemStack stack, World world, EntityLivingBase p, MovingObjectPosition movingobjectposition, int count)
/*     */   {
/*  78 */     IWand wand = (IWand)stack.getItem();
/*  79 */     String pp = "R" + p.getName();
/*  80 */     if (!p.worldObj.isRemote) { pp = "S" + p.getName();
/*     */     }
/*  82 */     if (soundDelay.get(pp) == null) soundDelay.put(pp, Long.valueOf(0L));
/*  83 */     if (breakcount.get(pp) == null) breakcount.put(pp, Float.valueOf(0.0F));
/*  84 */     if (lastX.get(pp) == null) lastX.put(pp, Integer.valueOf(0));
/*  85 */     if (lastY.get(pp) == null) lastY.put(pp, Integer.valueOf(0));
/*  86 */     if (lastZ.get(pp) == null) { lastZ.put(pp, Integer.valueOf(0));
/*     */     }
/*  88 */     MovingObjectPosition mop = BlockUtils.getTargetBlock(p.worldObj, p, false);
/*  89 */     Vec3 v = p.getLookVec();
/*  90 */     double tx = p.posX + v.xCoord * 10.0D;
/*  91 */     double ty = p.posY + p.getEyeHeight() + v.yCoord * 10.0D;
/*  92 */     double tz = p.posZ + v.zCoord * 10.0D;
/*  93 */     int impact = 0;
/*  94 */     if (mop != null) {
/*  95 */       tx = mop.hitVec.xCoord;
/*  96 */       ty = mop.hitVec.yCoord;
/*  97 */       tz = mop.hitVec.zCoord;
/*  98 */       impact = 5;
/*  99 */       if ((!p.worldObj.isRemote) && (((Long)soundDelay.get(pp)).longValue() < System.currentTimeMillis()))
/*     */       {
/* 101 */         p.worldObj.playSoundEffect(tx, ty, tz, "thaumcraft:rumble", 0.3F, 1.0F);
/* 102 */         soundDelay.put(pp, Long.valueOf(System.currentTimeMillis() + 1200L));
/*     */       }
/*     */     } else {
/* 105 */       soundDelay.put(pp, Long.valueOf(0L));
/*     */     }
/*     */     
/* 108 */     if (p.worldObj.isRemote) {
/* 109 */       beam.put(pp, Thaumcraft.proxy.getFX().beamCont(p, tx, ty, tz, 2, 65382, false, impact > 0 ? 2.0F : 0.0F, beam.get(pp), impact));
/*     */     }
/*     */     
/* 112 */     if ((mop != null) && (mop.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK) && ((!(p instanceof EntityPlayer)) || (p.worldObj.canMineBlockBody((EntityPlayer)p, mop.getBlockPos()))))
/*     */     {
/*     */ 
/* 115 */       IBlockState bs = p.worldObj.getBlockState(mop.getBlockPos());
/*     */       
/* 117 */       float hardness = bs.getBlock().getBlockHardness(p.worldObj, mop.getBlockPos());
/*     */       
/* 119 */       if (hardness >= 0.0F) {
/* 120 */         int pot = wand.getFocusPotency(stack);
/* 121 */         float speed = 0.05F + pot * 0.1F;
/* 122 */         if ((bs.getBlock().getMaterial() == Material.rock) || (bs.getBlock().getMaterial() == Material.grass) || (bs.getBlock().getMaterial() == Material.ground) || (bs.getBlock().getMaterial() == Material.sand))
/*     */         {
/* 124 */           speed = 0.25F + pot * 0.25F;
/*     */         }
/* 126 */         if (bs.getBlock() == Blocks.obsidian) { speed *= 3.0F;
/*     */         }
/* 128 */         if ((((Integer)lastX.get(pp)).intValue() == mop.getBlockPos().getX()) && (((Integer)lastY.get(pp)).intValue() == mop.getBlockPos().getY()) && (((Integer)lastZ.get(pp)).intValue() == mop.getBlockPos().getZ()))
/*     */         {
/* 130 */           float bc = ((Float)breakcount.get(pp)).floatValue();
/*     */           
/* 132 */           if ((p.worldObj.isRemote) && (bc > 0.0F) && (bs.getBlock() != Blocks.air)) {
/* 133 */             int progress = (int)(bc / hardness * 9.0F);
/* 134 */             Thaumcraft.proxy.getFX().excavateFX(mop.getBlockPos(), p, progress);
/*     */           }
/*     */           
/* 137 */           if (p.worldObj.isRemote) {
/* 138 */             if (bc >= hardness) {
/* 139 */               breakcount.put(pp, Float.valueOf(0.0F));
/*     */             } else {
/* 141 */               breakcount.put(pp, Float.valueOf(bc + speed));
/*     */             }
/*     */           }
/* 144 */           else if (bc >= hardness) {
/* 145 */             if (excavate(p.worldObj, stack, p, bs, mop.getBlockPos()))
/*     */             {
/* 147 */               for (int a = 0; a < wand.getFocusEnlarge(stack); a++) {
/* 148 */                 breakNeighbour(p, mop.getBlockPos(), bs, stack);
/*     */               }
/*     */             }
/*     */             
/* 152 */             lastX.put(pp, Integer.valueOf(Integer.MAX_VALUE));
/* 153 */             lastY.put(pp, Integer.valueOf(Integer.MAX_VALUE));
/* 154 */             lastZ.put(pp, Integer.valueOf(Integer.MAX_VALUE));
/* 155 */             breakcount.put(pp, Float.valueOf(0.0F));
/*     */           } else {
/* 157 */             breakcount.put(pp, Float.valueOf(bc + speed));
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 162 */           lastX.put(pp, Integer.valueOf(mop.getBlockPos().getX()));
/* 163 */           lastY.put(pp, Integer.valueOf(mop.getBlockPos().getY()));
/* 164 */           lastZ.put(pp, Integer.valueOf(mop.getBlockPos().getZ()));
/* 165 */           breakcount.put(pp, Float.valueOf(0.0F));
/*     */         }
/*     */       }
/*     */     } else {
/* 169 */       lastX.put(pp, Integer.valueOf(Integer.MAX_VALUE));
/* 170 */       lastY.put(pp, Integer.valueOf(Integer.MAX_VALUE));
/* 171 */       lastZ.put(pp, Integer.valueOf(Integer.MAX_VALUE));
/* 172 */       breakcount.put(pp, Float.valueOf(0.0F));
/*     */     }
/* 174 */     return true;
/*     */   }
/*     */   
/*     */   private boolean excavate(World world, ItemStack stack, EntityLivingBase p, IBlockState bs, BlockPos pos) {
/* 178 */     WorldSettings.GameType gt = WorldSettings.GameType.SURVIVAL;
/* 179 */     if ((p instanceof EntityPlayer)) {
/* 180 */       if (((EntityPlayer)p).capabilities.allowEdit) {
/* 181 */         if (((EntityPlayer)p).capabilities.isCreativeMode) {
/* 182 */           gt = WorldSettings.GameType.CREATIVE;
/*     */         }
/*     */       } else {
/* 185 */         gt = WorldSettings.GameType.ADVENTURE;
/*     */       }
/*     */     }
/*     */     
/* 189 */     int xp = net.minecraftforge.common.ForgeHooks.onBlockBreakEvent(world, gt, (net.minecraft.entity.player.EntityPlayerMP)p, pos);
/* 190 */     if (xp >= 0)
/*     */     {
/* 192 */       IWand wand = (IWand)stack.getItem();
/* 193 */       int fortune = wand.getFocusTreasure(stack);
/* 194 */       boolean silk = isUpgradedWith(wand.getFocusStack(stack), FocusUpgradeType.silktouch);
/*     */       
/* 196 */       if (((p instanceof EntityPlayer)) && (silk) && (bs.getBlock().canSilkHarvest(p.worldObj, pos, bs, (EntityPlayer)p)))
/*     */       {
/* 198 */         ArrayList<ItemStack> items = new ArrayList();
/*     */         
/* 200 */         ItemStack itemstack = BlockUtils.createStackedBlock(bs);
/*     */         
/* 202 */         if (itemstack != null)
/*     */         {
/* 204 */           items.add(itemstack);
/*     */         }
/*     */         
/* 207 */         net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, world, pos, bs, 0, 1.0F, true, (EntityPlayer)p);
/* 208 */         ItemStack is; for (Iterator i$ = items.iterator(); i$.hasNext(); 
/*     */             
/* 210 */             Block.spawnAsEntity(world, pos, is))
/*     */         {
/* 208 */           is = (ItemStack)i$.next();
/*     */           
/* 210 */           bs.getBlock();
/*     */         }
/*     */       }
/*     */       else {
/* 214 */         bs.getBlock().dropBlockAsItemWithChance(world, pos, bs, 1.0F, fortune);
/* 215 */         bs.getBlock().dropXpOnBlockBreak(world, pos, bs.getBlock().getExpDrop(world, pos, fortune));
/*     */       }
/*     */       
/* 218 */       world.setBlockToAir(pos);
/* 219 */       world.playAuxSFX(2001, pos, Block.getStateId(bs));
/* 220 */       return true;
/*     */     }
/* 222 */     return false;
/*     */   }
/*     */   
/*     */   boolean breakNeighbour(EntityLivingBase p, BlockPos pos, IBlockState bs, ItemStack stack) {
/* 226 */     ArrayList<EnumFacing> faces = new ArrayList();
/* 227 */     faces.add(EnumFacing.DOWN);faces.add(EnumFacing.EAST);
/* 228 */     faces.add(EnumFacing.NORTH);faces.add(EnumFacing.SOUTH);
/* 229 */     faces.add(EnumFacing.UP);faces.add(EnumFacing.WEST);
/* 230 */     Collections.shuffle(faces, p.getRNG());
/*     */     
/* 232 */     Collections.shuffle(faces, p.worldObj.rand);
/* 233 */     for (EnumFacing dir : faces) {
/* 234 */       if (p.worldObj.getBlockState(pos.offset(dir)) == bs)
/*     */       {
/* 236 */         if (excavate(p.worldObj, stack, p, bs, pos.offset(dir)))
/*     */         {
/* 238 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 243 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer p, int count)
/*     */   {
/* 251 */     String pp = "R" + p.getName();
/* 252 */     if (!p.worldObj.isRemote) { pp = "S" + p.getName();
/*     */     }
/* 254 */     if (soundDelay.get(pp) == null) soundDelay.put(pp, Long.valueOf(0L));
/* 255 */     if (breakcount.get(pp) == null) breakcount.put(pp, Float.valueOf(0.0F));
/* 256 */     if (lastX.get(pp) == null) lastX.put(pp, Integer.valueOf(0));
/* 257 */     if (lastY.get(pp) == null) lastY.put(pp, Integer.valueOf(0));
/* 258 */     if (lastZ.get(pp) == null) lastZ.put(pp, Integer.valueOf(0));
/* 259 */     beam.put(pp, null);
/* 260 */     lastX.put(pp, Integer.valueOf(Integer.MAX_VALUE));
/* 261 */     lastY.put(pp, Integer.valueOf(Integer.MAX_VALUE));
/* 262 */     lastZ.put(pp, Integer.valueOf(Integer.MAX_VALUE));
/* 263 */     breakcount.put(pp, Float.valueOf(0.0F));
/*     */   }
/*     */   
/*     */   public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack itemstack)
/*     */   {
/* 268 */     return ItemFocusBasic.WandFocusAnimation.CHARGE;
/*     */   }
/*     */   
/*     */   public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank)
/*     */   {
/* 273 */     switch (rank) {
/* 274 */     case 1:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.treasure };
/* 275 */     case 2:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge };
/* 276 */     case 3:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.treasure, dowsing };
/* 277 */     case 4:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge };
/* 278 */     case 5:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.treasure, FocusUpgradeType.silktouch };
/*     */     }
/* 280 */     return null;
/*     */   }
/*     */   
/* 283 */   public static FocusUpgradeType dowsing = new FocusUpgradeType(new ResourceLocation("thaumcraft", "textures/foci/dowsing.png"), "focus.upgrade.dowsing.name", "focus.upgrade.dowsing.text", new AspectList().add(Aspect.CRAFT, 1));
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\wands\foci\ItemFocusExcavation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */