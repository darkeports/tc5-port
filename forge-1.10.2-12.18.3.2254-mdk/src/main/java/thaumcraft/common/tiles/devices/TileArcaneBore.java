/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.Block.SoundType;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemPickaxe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.util.FakePlayerFactory;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.api.wands.FocusUpgradeType;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.items.wands.foci.ItemFocusExcavation;
/*     */ import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
/*     */ import thaumcraft.common.lib.events.PlayerEvents;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.misc.PacketBoreDig;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ import thaumcraft.common.lib.utils.TCVec3;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class TileArcaneBore extends TileThaumcraft implements IInventory, thaumcraft.api.wands.IWandable, ITickable
/*     */ {
/*  59 */   public int spiral = 0;
/*  60 */   public float currentRadius = 0.0F;
/*  61 */   public int maxRadius = 2;
/*     */   
/*     */ 
/*  64 */   public float vRadX = 0.0F;
/*  65 */   public float vRadZ = 0.0F;
/*  66 */   public float tRadX = 0.0F;
/*  67 */   public float tRadZ = 0.0F;
/*  68 */   public float mRadX = 0.0F;
/*  69 */   public float mRadZ = 0.0F;
/*  70 */   private int count = 0;
/*  71 */   public int topRotation = 0;
/*  72 */   long soundDelay = 0L;
/*  73 */   Object beam1 = null;
/*  74 */   Object beam2 = null;
/*  75 */   int beamlength = 0;
/*     */   
/*  77 */   TileArcaneBoreBase base = null;
/*     */   
/*     */ 
/*  80 */   public ItemStack[] contents = new ItemStack[2];
/*  81 */   public int rotX = 0;
/*  82 */   public int rotZ = 0;
/*  83 */   public int tarX = 0;
/*  84 */   public int tarZ = 0;
/*  85 */   public int speedX = 0;
/*  86 */   public int speedZ = 0;
/*  87 */   public boolean hasFocus = false;
/*  88 */   public boolean hasPickaxe = false;
/*  89 */   int lastX = 0;
/*  90 */   int lastZ = 0;
/*  91 */   int lastY = 0;
/*  92 */   boolean toDig = false;
/*  93 */   int digX = 0;
/*  94 */   int digZ = 0;
/*  95 */   int digY = 0;
/*  96 */   Block digBlock = Blocks.air;
/*  97 */   int digMd = 0;
/*  98 */   float radInc = 0.0F;
/*  99 */   int paused = 100;
/* 100 */   int maxPause = 100;
/* 101 */   long repairCounter = 0L;
/* 102 */   public boolean refresh = true;
/* 103 */   int powered = 0;
/*     */   
/* 105 */   public EnumFacing baseOrientation = EnumFacing.WEST;
/*     */   
/* 107 */   net.minecraftforge.common.util.FakePlayer fakePlayer = null;
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/* 112 */     if (!this.worldObj.isRemote) {
/* 113 */       if ((this.speedyTime < 20.0F) && (this.base != null) && (this.base.drawEssentia())) {
/* 114 */         getClass();this.speedyTime += 20.0F;
/* 115 */         this.powered += 20;
/*     */       }
/*     */       
/* 118 */       if ((this.speedyTime < 1.0F) && (this.powered < 5)) {
/* 119 */         this.powered += AuraHelper.drainAuraAvailable(getWorld(), getPos(), Aspect.ENTROPY, 5);
/*     */       }
/*     */     }
/*     */     
/* 123 */     if ((!this.worldObj.isRemote) && (this.fakePlayer == null)) {
/* 124 */       this.fakePlayer = FakePlayerFactory.get((WorldServer)this.worldObj, new GameProfile((UUID)null, "FakeThaumcraftBore"));
/*     */     }
/*     */     
/* 127 */     if ((this.baseOrientation.getHorizontalIndex() >= 0) || (this.refresh)) {
/* 128 */       if (this.worldObj.getBlockState(this.pos.up()).getBlock() == BlocksTC.arcaneBoreBase) {
/* 129 */         this.baseOrientation = EnumFacing.UP;
/*     */       } else {
/* 131 */         this.baseOrientation = EnumFacing.DOWN;
/*     */       }
/* 133 */       this.base = null;
/*     */     }
/*     */     
/* 136 */     if ((this.worldObj.isRemote) && (this.refresh)) {
/* 137 */       setOrientation(BlockStateUtils.getFacing(getBlockMetadata()), true);
/* 138 */       this.refresh = false;
/*     */     }
/*     */     
/* 141 */     if (this.rotX < this.tarX) {
/* 142 */       this.rotX += this.speedX;
/* 143 */       if (this.rotX < this.tarX) {
/* 144 */         this.speedX += 1;
/*     */       } else
/* 146 */         this.speedX = ((int)(this.speedX / 3.0F));
/* 147 */     } else if (this.rotX > this.tarX) {
/* 148 */       this.rotX += this.speedX;
/* 149 */       if (this.rotX > this.tarX) {
/* 150 */         this.speedX -= 1;
/*     */       } else
/* 152 */         this.speedX = ((int)(this.speedX / 3.0F));
/*     */     } else {
/* 154 */       this.speedX = 0;
/*     */     }
/*     */     
/* 157 */     if (this.rotZ < this.tarZ) {
/* 158 */       this.rotZ += this.speedZ;
/* 159 */       if (this.rotZ < this.tarZ) {
/* 160 */         this.speedZ += 1;
/*     */       } else
/* 162 */         this.speedZ = ((int)(this.speedZ / 3.0F));
/* 163 */     } else if (this.rotZ > this.tarZ) {
/* 164 */       this.rotZ += this.speedZ;
/* 165 */       if (this.rotZ > this.tarZ) {
/* 166 */         this.speedZ -= 1;
/*     */       } else
/* 168 */         this.speedZ = ((int)(this.speedZ / 3.0F));
/*     */     } else {
/* 170 */       this.speedZ = 0;
/*     */     }
/*     */     
/*     */ 
/* 174 */     if ((gettingPower()) && (areItemsValid()))
/*     */     {
/* 176 */       dig();
/*     */ 
/*     */     }
/* 179 */     else if (this.worldObj.isRemote) {
/* 180 */       if (this.topRotation % 90 != 0)
/* 181 */         this.topRotation += Math.min(10, 90 - this.topRotation % 90);
/* 182 */       this.vRadX *= 0.9F;
/* 183 */       this.vRadZ *= 0.9F;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 188 */     if ((!this.worldObj.isRemote) && (this.hasPickaxe) && (getStackInSlot(1) != null)) {
/* 189 */       if ((this.repairCounter++ % 40L == 0L) && (getStackInSlot(1).isItemDamaged())) {
/* 190 */         PlayerEvents.doRepair(getStackInSlot(1), this.fakePlayer);
/*     */       }
/* 192 */       this.fakePlayer.ticksExisted = ((int)this.repairCounter);
/*     */       try {
/* 194 */         getStackInSlot(1).updateAnimation(this.worldObj, this.fakePlayer, 0, true);
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean areItemsValid() {
/* 201 */     boolean notNearBroken = true;
/* 202 */     if ((this.hasPickaxe) && (getStackInSlot(1).getItemDamage() + 1 >= getStackInSlot(1).getMaxDamage()))
/* 203 */       notNearBroken = false;
/* 204 */     return (this.hasFocus) && (this.hasPickaxe) && (getStackInSlot(1).isItemStackDamageable()) && (notNearBroken);
/*     */   }
/*     */   
/*     */ 
/* 208 */   public int fortune = 0;
/* 209 */   public int speed = 0;
/* 210 */   public int area = 0;
/*     */   
/*     */   public void markDirty()
/*     */   {
/* 214 */     super.markDirty();
/*     */     
/* 216 */     this.fortune = 0;
/* 217 */     this.area = 0;
/* 218 */     this.speed = 0;
/*     */     
/* 220 */     if ((getStackInSlot(0) != null) && ((getStackInSlot(0).getItem() instanceof ItemFocusExcavation))) {
/* 221 */       this.fortune = ((ItemFocusExcavation)getStackInSlot(0).getItem()).getUpgradeLevel(getStackInSlot(0), FocusUpgradeType.treasure);
/* 222 */       this.area = ((ItemFocusExcavation)getStackInSlot(0).getItem()).getUpgradeLevel(getStackInSlot(0), FocusUpgradeType.enlarge);
/* 223 */       this.speed += ((ItemFocusExcavation)getStackInSlot(0).getItem()).getUpgradeLevel(getStackInSlot(0), FocusUpgradeType.potency);
/* 224 */       this.hasFocus = true;
/*     */     } else {
/* 226 */       this.hasFocus = false;
/*     */     }
/*     */     
/* 229 */     if ((getStackInSlot(1) != null) && ((getStackInSlot(1).getItem() instanceof ItemPickaxe)))
/*     */     {
/* 231 */       this.hasPickaxe = true;
/* 232 */       int f = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, getStackInSlot(1));
/*     */       
/* 234 */       if (f > this.fortune)
/* 235 */         this.fortune = f;
/* 236 */       this.speed += EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, getStackInSlot(1));
/*     */     }
/*     */     else {
/* 239 */       this.hasPickaxe = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private void dig()
/*     */   {
/* 245 */     if ((this.rotX != this.tarX) || (this.rotZ != this.tarZ)) {
/* 246 */       if (this.worldObj.isRemote) {
/* 247 */         if (this.topRotation % 90 != 0)
/* 248 */           this.topRotation += Math.min(10, 90 - this.topRotation % 90);
/* 249 */         this.vRadX *= 0.9F;
/* 250 */         this.vRadZ *= 0.9F;
/*     */       }
/* 252 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 257 */     if (!this.worldObj.isRemote) {
/* 258 */       boolean dug = false;
/*     */       
/* 260 */       if (this.base == null) {
/* 261 */         this.base = ((TileArcaneBoreBase)this.worldObj.getTileEntity(this.pos.offset(this.baseOrientation)));
/*     */       }
/*     */       
/* 264 */       if ((--this.count > 0) || (this.powered < 1)) return;
/* 265 */       if (this.toDig) {
/* 266 */         this.toDig = false;
/* 267 */         BlockPos digPos = new BlockPos(this.digX, this.digY, this.digZ);
/* 268 */         IBlockState digBs = this.worldObj.getBlockState(digPos);
/*     */         boolean silktouch;
/* 270 */         int refining; if (!digBs.getBlock().isAir(this.worldObj, digPos)) {
/* 271 */           int tfortune = this.fortune;
/* 272 */           silktouch = false;
/*     */           
/* 274 */           if ((getStackInSlot(1) != null) && (EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, getStackInSlot(1)) > 0) && (digBs.getBlock().canSilkHarvest(this.worldObj, digPos, digBs, null)))
/*     */           {
/*     */ 
/*     */ 
/* 278 */             silktouch = true;
/* 279 */             tfortune = 0;
/*     */           }
/*     */           
/* 282 */           if ((!silktouch) && (getStackInSlot(0) != null) && (((ItemFocusExcavation)getStackInSlot(0).getItem()).isUpgradedWith(getStackInSlot(0), FocusUpgradeType.silktouch)) && (digBs.getBlock().canSilkHarvest(this.worldObj, digPos, digBs, null)))
/*     */           {
/*     */ 
/*     */ 
/* 286 */             silktouch = true;
/* 287 */             tfortune = 0;
/*     */           }
/*     */           
/* 290 */           this.worldObj.addBlockEvent(this.pos, BlocksTC.arcaneBore, 99, Block.getIdFromBlock(digBs.getBlock()) + (digBs.getBlock().getMetaFromState(digBs) << 12));
/*     */           
/* 292 */           List<ItemStack> items = new ArrayList();
/* 293 */           if (silktouch) {
/* 294 */             ItemStack dropped = thaumcraft.common.lib.utils.BlockUtils.createStackedBlock(digBs);
/* 295 */             if (dropped != null) {
/* 296 */               items.add(dropped);
/*     */             }
/*     */           } else {
/* 299 */             items = digBs.getBlock().getDrops(this.worldObj, digPos, digBs, tfortune);
/*     */           }
/*     */           
/*     */ 
/* 303 */           List<EntityItem> targets = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.fromBounds(this.digX, this.digY, this.digZ, this.digX + 1, this.digY + 1, this.digZ + 1).expand(1.0D, 1.0D, 1.0D));
/*     */           
/* 305 */           if (targets.size() > 0) {
/* 306 */             for (EntityItem e : targets) {
/* 307 */               items.add(e.getEntityItem().copy());
/* 308 */               e.setDead();
/*     */             }
/*     */           }
/* 311 */           refining = getStackInSlot(1) != null ? EnumInfusionEnchantment.getInfusionEnchantmentLevel(getStackInSlot(1), EnumInfusionEnchantment.REFINING) : 0;
/*     */           
/* 313 */           if ((getStackInSlot(0) != null) && ((getStackInSlot(0).getItem() instanceof ItemFocusBasic)) && (((ItemFocusBasic)getStackInSlot(0).getItem()).isUpgradedWith(getStackInSlot(0), ItemFocusExcavation.dowsing)))
/*     */           {
/* 315 */             refining++;
/*     */           }
/* 317 */           if (items.size() > 0) {
/* 318 */             for (ItemStack is : items) {
/* 319 */               ItemStack dropped = is.copy();
/*     */               
/* 321 */               if ((!silktouch) && (refining > 0)) {
/* 322 */                 dropped = Utils.findSpecialMiningResult(is, (refining + 1) * 0.125F, this.worldObj.rand);
/*     */               }
/* 324 */               if ((this.base != null) && ((this.base instanceof TileArcaneBoreBase))) {
/* 325 */                 TileEntity inventory = this.worldObj.getTileEntity(this.base.getPos().offset(BlockStateUtils.getFacing(this.base.getBlockMetadata())));
/* 326 */                 if ((inventory != null) && ((inventory instanceof IInventory))) {
/* 327 */                   dropped = InventoryUtils.placeItemStackIntoInventory(dropped, (IInventory)inventory, BlockStateUtils.getFacing(this.base.getBlockMetadata()).getOpposite(), true);
/*     */                 }
/*     */                 
/*     */ 
/* 331 */                 if (dropped != null) {
/* 332 */                   EntityItem ei = new EntityItem(this.worldObj, this.pos.getX() + 0.5D + BlockStateUtils.getFacing(this.base.getBlockMetadata()).getFrontOffsetX() * 0.66D, this.pos.getY() + 0.4D + this.baseOrientation.getFrontOffsetY(), this.pos.getZ() + 0.5D + BlockStateUtils.getFacing(this.base.getBlockMetadata()).getFrontOffsetZ() * 0.66D, dropped.copy());
/*     */                   
/*     */ 
/*     */ 
/* 336 */                   ei.motionX = (0.075F * BlockStateUtils.getFacing(this.base.getBlockMetadata()).getFrontOffsetX());
/* 337 */                   ei.motionY = 0.02500000037252903D;
/* 338 */                   ei.motionZ = (0.075F * BlockStateUtils.getFacing(this.base.getBlockMetadata()).getFrontOffsetZ());
/* 339 */                   this.worldObj.spawnEntityInWorld(ei);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 346 */         setInventorySlotContents(1, InventoryUtils.damageItem(1, getStackInSlot(1), this.worldObj));
/*     */         
/* 348 */         if (getStackInSlot(1).stackSize <= 0) {
/* 349 */           setInventorySlotContents(1, null);
/*     */         }
/* 351 */         this.worldObj.setBlockToAir(digPos);
/*     */         
/* 353 */         if (this.base != null) {
/* 354 */           for (EnumFacing lbd : EnumFacing.HORIZONTALS) {
/* 355 */             TileEntity lbte = this.worldObj.getTileEntity(this.base.getPos().offset(lbd));
/* 356 */             if ((lbte != null) && ((lbte instanceof TileLampArcane))) {
/* 357 */               int d = this.worldObj.rand.nextInt(32) * 2;
/* 358 */               int xx = this.pos.getX() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetX() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetX() * d;
/* 359 */               int yy = this.pos.getY() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetY() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetY() * d;
/* 360 */               int zz = this.pos.getZ() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetZ() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetZ() * d;
/* 361 */               int p = d / 2 % 4;
/* 362 */               if (BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetX() != 0) {
/* 363 */                 zz += ((p == 1) || (p == 3) ? 0 : p == 0 ? 3 : -3);
/*     */               } else {
/* 365 */                 xx += ((p == 1) || (p == 3) ? 0 : p == 0 ? 3 : -3);
/*     */               }
/* 367 */               if ((p == 3) && (BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetY() == 0)) {
/* 368 */                 yy -= 2;
/*     */               }
/*     */               
/* 371 */               BlockPos bp = new BlockPos(xx, yy, zz);
/*     */               
/* 373 */               if ((!this.worldObj.isAirBlock(bp)) || (this.worldObj.getBlockState(bp) == BlocksTC.effect.getStateFromMeta(2)) || (this.worldObj.getLightFor(EnumSkyBlock.BLOCK, bp) >= 11))
/*     */                 break;
/* 375 */               this.worldObj.setBlockState(bp, BlocksTC.effect.getStateFromMeta(2), 3); break;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 382 */         dug = true;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 387 */       findNextBlockToDig();
/* 388 */       if (dug) {
/* 389 */         if (this.speedyTime > 0.0F) this.speedyTime -= 1.0F;
/* 390 */         if (this.powered > 0) this.powered -= 1;
/*     */       }
/*     */     }
/*     */     else {
/* 394 */       this.paused += 1;
/*     */       
/* 396 */       if (this.worldObj.isAirBlock(this.pos)) {
/* 397 */         invalidate();
/*     */       }
/* 399 */       if ((this.paused < this.maxPause) && (this.soundDelay < System.currentTimeMillis())) {
/* 400 */         this.soundDelay = (System.currentTimeMillis() + 1200L + this.worldObj.rand.nextInt(100));
/* 401 */         this.worldObj.playSound(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, "thaumcraft:rumble", 0.25F, 0.9F + this.worldObj.rand.nextFloat() * 0.2F, false);
/*     */       }
/*     */       
/*     */ 
/* 405 */       if ((this.beamlength > 0) && (this.paused > this.maxPause)) {
/* 406 */         this.beamlength -= 1;
/*     */       }
/* 408 */       BlockPos digPos = new BlockPos(this.digX, this.digY, this.digZ);
/*     */       
/* 410 */       if (this.toDig)
/*     */       {
/* 412 */         this.paused = 0;
/* 413 */         this.beamlength = 64;
/*     */         
/*     */ 
/* 416 */         IBlockState digBs = this.worldObj.getBlockState(digPos);
/*     */         
/*     */ 
/*     */ 
/* 420 */         if (digBs.getBlock() != null) {
/* 421 */           this.maxPause = (10 + Math.max(10 - this.speed, (int)(digBs.getBlock().getBlockHardness(this.worldObj, digPos) * 2.0F) - this.speed * 2));
/*     */         } else {
/* 423 */           this.maxPause = 20;
/*     */         }
/*     */         
/* 426 */         if (this.speedyTime <= 0.0F) { this.maxPause *= 4;
/*     */         }
/* 428 */         this.toDig = false;
/*     */         
/* 430 */         double xd = this.pos.getX() + 0.5D - (this.digX + 0.5D);
/* 431 */         double yd = this.pos.getY() + 0.5D - (this.digY + 0.5D);
/* 432 */         double zd = this.pos.getZ() + 0.5D - (this.digZ + 0.5D);
/*     */         
/* 434 */         double var12 = MathHelper.sqrt_double(xd * xd + zd * zd);
/*     */         
/* 436 */         float rx = (float)(Math.atan2(zd, xd) * 180.0D / 3.141592653589793D);
/* 437 */         float rz = (float)-(Math.atan2(yd, var12) * 180.0D / 3.141592653589793D) + 90.0F;
/*     */         
/* 439 */         this.tRadX = (MathHelper.wrapAngleTo180_float(this.rotX) + rx);
/*     */         
/* 441 */         if (BlockStateUtils.getFacing(getBlockMetadata()).ordinal() == 5) {
/* 442 */           if (this.tRadX > 180.0F)
/* 443 */             this.tRadX -= 360.0F;
/* 444 */           if (this.tRadX < -180.0F) {
/* 445 */             this.tRadX += 360.0F;
/*     */           }
/*     */         }
/* 448 */         this.tRadZ = (rz - this.rotZ);
/* 449 */         if (BlockStateUtils.getFacing(getBlockMetadata()).ordinal() <= 1) {
/* 450 */           this.tRadZ += 180.0F;
/*     */           
/* 452 */           if (this.vRadX - this.tRadX >= 180.0F) {
/* 453 */             this.vRadX -= 360.0F;
/*     */           }
/* 455 */           if (this.vRadX - this.tRadX <= -180.0F) {
/* 456 */             this.vRadX += 360.0F;
/*     */           }
/*     */         }
/*     */         
/* 460 */         this.mRadX = Math.abs((this.vRadX - this.tRadX) / 6.0F);
/* 461 */         this.mRadZ = Math.abs((this.vRadZ - this.tRadZ) / 6.0F);
/*     */         
/* 463 */         if (this.speedyTime > 0.0F) this.speedyTime -= 1.0F;
/* 464 */         if (this.powered > 0) { this.powered -= 1;
/*     */         }
/*     */       }
/* 467 */       if (this.paused < this.maxPause) {
/* 468 */         if (this.vRadX < this.tRadX) {
/* 469 */           this.vRadX += this.mRadX;
/* 470 */         } else if (this.vRadX > this.tRadX) {
/* 471 */           this.vRadX -= this.mRadX;
/*     */         }
/*     */         
/* 474 */         if (this.vRadZ < this.tRadZ) {
/* 475 */           this.vRadZ += this.mRadZ;
/* 476 */         } else if (this.vRadZ > this.tRadZ) {
/* 477 */           this.vRadZ -= this.mRadZ;
/*     */         }
/*     */       } else {
/* 480 */         this.vRadX *= 0.9F;
/* 481 */         this.vRadZ *= 0.9F;
/*     */       }
/*     */       
/* 484 */       this.mRadX *= 0.9F;
/* 485 */       this.mRadZ *= 0.9F;
/*     */       
/* 487 */       float vx = this.rotX + 90 - this.vRadX;
/* 488 */       float vz = this.rotZ + 90 - this.vRadZ;
/* 489 */       float var3 = 1.0F;
/* 490 */       float dX = MathHelper.sin(vx / 180.0F * 3.1415927F) * MathHelper.cos(vz / 180.0F * 3.1415927F) * var3;
/* 491 */       float dZ = MathHelper.cos(vx / 180.0F * 3.1415927F) * MathHelper.cos(vz / 180.0F * 3.1415927F) * var3;
/* 492 */       float dY = MathHelper.sin(vz / 180.0F * 3.1415927F) * var3;
/*     */       
/* 494 */       Vec3 var13 = new Vec3(this.pos.getX() + 0.5D + dX, getPos().getY() + 0.5D + dY, getPos().getZ() + 0.5D + dZ);
/* 495 */       Vec3 var14 = new Vec3(this.pos.getX() + 0.5D + dX * this.beamlength, getPos().getY() + 0.5D + dY * this.beamlength, getPos().getZ() + 0.5D + dZ * this.beamlength);
/*     */       
/* 497 */       MovingObjectPosition mop = this.worldObj.rayTraceBlocks(var13, var14, false, true, false);
/* 498 */       int impact = 0;
/* 499 */       float length = 64.0F;
/* 500 */       double bx = var14.xCoord;
/* 501 */       double by = var14.yCoord;
/* 502 */       double bz = var14.zCoord;
/* 503 */       if (mop != null) {
/* 504 */         double xd = getPos().getX() + 0.5D + dX - mop.hitVec.xCoord;
/* 505 */         double yd = getPos().getY() + 0.5D + dY - mop.hitVec.yCoord;
/* 506 */         double zd = getPos().getZ() + 0.5D + dZ - mop.hitVec.zCoord;
/*     */         
/* 508 */         bx = mop.hitVec.xCoord;
/* 509 */         by = mop.hitVec.yCoord;
/* 510 */         bz = mop.hitVec.zCoord;
/*     */         
/* 512 */         length = MathHelper.sqrt_double(xd * xd + yd * yd + zd * zd) + 0.5F;
/*     */         
/* 514 */         impact = 5;
/* 515 */         int x = MathHelper.floor_double(bx);
/* 516 */         int y = MathHelper.floor_double(by);
/* 517 */         int z = MathHelper.floor_double(bz);
/* 518 */         BlockPos tp = new BlockPos(x, y, z);
/* 519 */         IBlockState ts = this.worldObj.getBlockState(tp);
/* 520 */         if (!this.worldObj.isAirBlock(tp)) {
/* 521 */           Thaumcraft.proxy.getFX().boreDigFx(x, y, z, getPos().getX() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetX(), getPos().getY() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetY(), getPos().getZ() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetZ(), ts.getBlock(), ts.getBlock().getMetaFromState(ts) >> 12 & 0xFF);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 526 */       this.topRotation += this.beamlength / 6;
/* 527 */       this.beam1 = Thaumcraft.proxy.getFX().beamBore(getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D, bx, by, bz, 1, 65382, true, impact > 0 ? 2.0F : 0.0F, this.beam1, impact);
/*     */       
/*     */ 
/* 530 */       this.beam2 = Thaumcraft.proxy.getFX().beamBore(getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D, bx, by, bz, 2, 16746581, false, impact > 0 ? 2.0F : 0.0F, this.beam2, impact);
/*     */       
/*     */ 
/*     */ 
/* 534 */       if ((this.worldObj.isAirBlock(digPos)) && (this.digBlock != Blocks.air)) {
/* 535 */         this.worldObj.playSound(this.digX + 0.5F, this.digY + 0.5F, this.digZ + 0.5F, this.digBlock.stepSound.getBreakSound(), (this.digBlock.stepSound.getVolume() + 1.0F) / 2.0F, this.digBlock.stepSound.getFrequency() * 0.8F, false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 540 */         for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(10); a++) {
/* 541 */           Thaumcraft.proxy.getFX().boreDigFx(this.digX, this.digY, this.digZ, getPos().getX() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetX(), getPos().getY() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetY(), getPos().getZ() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetZ(), this.digBlock, this.digMd >> 12 & 0xFF);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 547 */         this.digBlock = Blocks.air;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 552 */   int stick = 0;
/*     */   private float speedyTime;
/*     */   
/*     */   private void findNextBlockToDig() {
/* 556 */     if (this.radInc == 0.0F) { this.radInc = 1.0F;
/*     */     }
/* 558 */     int x = this.lastX;
/* 559 */     int z = this.lastZ;
/* 560 */     int y = this.lastY;
/* 561 */     if (this.stick == 0) {
/* 562 */       while ((x == this.lastX) && (z == this.lastZ) && (y == this.lastY)) {
/* 563 */         this.spiral = ((int)(this.spiral + (5.0F + (10.0F - Math.abs(this.currentRadius)) * 2.0F)));
/* 564 */         if (this.spiral >= 360) {
/* 565 */           this.spiral -= 360;
/* 566 */           this.currentRadius += this.radInc;
/* 567 */           if ((this.currentRadius >= this.maxRadius + this.area) || (this.currentRadius <= -(this.maxRadius + this.area))) {
/* 568 */             this.radInc *= -1.0F;
/*     */           }
/*     */         }
/*     */         
/* 572 */         TCVec3 vsource = TCVec3.createVectorHelper(getPos().getX() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetX() + 0.5D, getPos().getY() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetY() + 0.5D, getPos().getZ() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetZ() + 0.5D);
/*     */         
/*     */ 
/* 575 */         TCVec3 vtar = TCVec3.createVectorHelper(0.0D, this.currentRadius, 0.0D);
/*     */         
/* 577 */         vtar.rotateAroundZ(this.spiral / 180.0F * 3.1415927F);
/* 578 */         vtar.rotateAroundY(1.5707964F * BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetX());
/*     */         
/* 580 */         vtar.rotateAroundX(1.5707964F * BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetY());
/*     */         
/* 582 */         TCVec3 vres = vsource.addVector(vtar.xCoord, vtar.yCoord, vtar.zCoord);
/*     */         
/*     */ 
/* 585 */         x = MathHelper.floor_double(vres.xCoord);
/* 586 */         y = MathHelper.floor_double(vres.yCoord);
/* 587 */         z = MathHelper.floor_double(vres.zCoord);
/*     */       }
/*     */     }
/* 590 */     this.lastX = x;
/* 591 */     this.lastZ = z;
/* 592 */     this.lastY = y;
/*     */     
/* 594 */     x += BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetX();
/* 595 */     y += BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetY();
/* 596 */     z += BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetZ();
/*     */     
/* 598 */     for (int depth = 0; depth < 64; depth++)
/*     */     {
/* 600 */       x += BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetX();
/* 601 */       y += BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetY();
/* 602 */       z += BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetZ();
/*     */       
/* 604 */       if (depth >= this.stick)
/*     */       {
/* 606 */         BlockPos bp = new BlockPos(x, y, z);
/* 607 */         Block block = this.worldObj.getBlockState(bp).getBlock();
/*     */         
/* 609 */         if ((block != null) && (block.getBlockHardness(this.worldObj, bp) < 0.0F)) {
/*     */           break;
/*     */         }
/*     */         
/* 613 */         if ((!this.worldObj.isAirBlock(bp)) && (block != null) && (block.canCollideCheck(this.worldObj.getBlockState(bp), false)) && (block.getCollisionBoundingBox(this.worldObj, bp, this.worldObj.getBlockState(bp)) != null))
/*     */         {
/*     */ 
/*     */ 
/* 617 */           this.digX = x;
/* 618 */           this.digY = y;
/* 619 */           this.digZ = z;
/*     */           
/* 621 */           this.stick = depth;
/*     */           
/* 623 */           this.count = Math.max(10 - this.speed, (int)(block.getBlockHardness(this.worldObj, bp) * 2.0F) - this.speed * 2);
/* 624 */           if (this.speedyTime < 1.0F) this.count *= 4;
/* 625 */           this.toDig = true;
/*     */           
/* 627 */           Vec3 var13 = new Vec3(getPos().getX() + 0.5D + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetX(), getPos().getY() + 0.5D + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetY(), getPos().getZ() + 0.5D + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetZ());
/*     */           
/*     */ 
/*     */ 
/* 631 */           Vec3 var14 = new Vec3(this.digX + 0.5D, this.digY + 0.5D, this.digZ + 0.5D);
/*     */           
/* 633 */           MovingObjectPosition mop = this.worldObj.rayTraceBlocks(var13, var14, false, true, false);
/* 634 */           if (mop != null) {
/* 635 */             block = this.worldObj.getBlockState(mop.getBlockPos()).getBlock();
/* 636 */             if ((block.getBlockHardness(this.worldObj, mop.getBlockPos()) > -1.0F) && (block.getCollisionBoundingBox(this.worldObj, mop.getBlockPos(), this.worldObj.getBlockState(mop.getBlockPos())) != null))
/*     */             {
/*     */ 
/* 639 */               this.count = Math.max(10 - this.speed, (int)(block.getBlockHardness(this.worldObj, mop.getBlockPos()) * 2.0F) - this.speed * 2);
/*     */               
/* 641 */               if (this.speedyTime < 1.0F) {
/* 642 */                 this.count *= 4;
/*     */               }
/* 644 */               if ((this.digX != mop.getBlockPos().getX()) || (this.digY != mop.getBlockPos().getY()) || (this.digZ != mop.getBlockPos().getZ())) {
/* 645 */                 this.stick = 0;
/*     */               }
/* 647 */               this.digX = mop.getBlockPos().getX();
/* 648 */               this.digY = mop.getBlockPos().getY();
/* 649 */               this.digZ = mop.getBlockPos().getZ();
/*     */             }
/*     */           }
/* 652 */           sendDigEvent();
/* 653 */           return;
/*     */         }
/* 655 */         this.stick = 0;
/*     */       } }
/* 657 */     this.stick = 0;
/*     */   }
/*     */   
/*     */   public boolean gettingPower() {
/* 661 */     return (this.worldObj.isBlockIndirectlyGettingPowered(this.pos) > 0) || (this.worldObj.isBlockIndirectlyGettingPowered(this.pos.offset(this.baseOrientation)) > 0);
/*     */   }
/*     */   
/*     */   public void setOrientation(EnumFacing or, boolean initial)
/*     */   {
/* 666 */     ((BlockTCDevice)getBlockType()).updateFacing(this.worldObj, getPos(), or);
/* 667 */     this.lastX = 0;
/* 668 */     this.lastZ = 0;
/* 669 */     switch (or.ordinal()) {
/*     */     case 0: 
/* 671 */       this.tarZ = 180;
/* 672 */       this.tarX = 0;
/* 673 */       break;
/*     */     case 1: 
/* 675 */       this.tarZ = 0;
/* 676 */       this.tarX = 0;
/* 677 */       break;
/*     */     case 2: 
/* 679 */       this.tarZ = 90;
/* 680 */       this.tarX = 270;
/* 681 */       break;
/*     */     case 3: 
/* 683 */       this.tarZ = 90;
/* 684 */       this.tarX = 90;
/* 685 */       break;
/*     */     case 4: 
/* 687 */       this.tarZ = 90;
/* 688 */       this.tarX = 0;
/* 689 */       break;
/*     */     case 5: 
/* 691 */       this.tarZ = 90;
/* 692 */       this.tarX = 180;
/*     */     }
/*     */     
/*     */     
/* 696 */     if (initial) {
/* 697 */       this.rotX = this.tarX;
/* 698 */       this.rotZ = this.tarZ;
/*     */     }
/*     */     
/* 701 */     this.toDig = false;
/* 702 */     this.radInc = 0.0F;
/* 703 */     this.paused = 100;
/* 704 */     this.tRadX = 0.0F;
/* 705 */     this.tRadZ = 0.0F;
/* 706 */     this.mRadX = 0.0F;
/* 707 */     this.mRadZ = 0.0F;
/* 708 */     this.digX = 0;
/* 709 */     this.digY = 0;
/* 710 */     this.digZ = 0;
/* 711 */     if (this.worldObj != null) this.worldObj.markBlockForUpdate(this.pos);
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 716 */     super.readFromNBT(nbttagcompound);
/* 717 */     this.speedyTime = nbttagcompound.getShort("SpeedyTime");
/* 718 */     this.powered = ((int)Math.ceil(this.speedyTime));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 724 */     super.writeToNBT(nbttagcompound);
/* 725 */     nbttagcompound.setShort("SpeedyTime", (short)(int)this.speedyTime);
/*     */   }
/*     */   
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 730 */     NBTTagList var2 = nbttagcompound.getTagList("Inventory", 10);
/* 731 */     this.contents = new ItemStack[getSizeInventory()];
/*     */     
/* 733 */     for (int var3 = 0; var3 < var2.tagCount(); var3++) {
/* 734 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 735 */       int var5 = var4.getByte("Slot") & 0xFF;
/*     */       
/* 737 */       if ((var5 >= 0) && (var5 < this.contents.length)) {
/* 738 */         this.contents[var5] = ItemStack.loadItemStackFromNBT(var4);
/*     */       }
/*     */     }
/*     */     
/* 742 */     markDirty();
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
/* 746 */     NBTTagList var2 = new NBTTagList();
/* 747 */     for (int var3 = 0; var3 < this.contents.length; var3++) {
/* 748 */       if (this.contents[var3] != null) {
/* 749 */         NBTTagCompound var4 = new NBTTagCompound();
/* 750 */         var4.setByte("Slot", (byte)var3);
/* 751 */         this.contents[var3].writeToNBT(var4);
/* 752 */         var2.appendTag(var4);
/*     */       }
/*     */     }
/* 755 */     nbttagcompound.setTag("Inventory", var2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/* 762 */     if (i == 99) {
/*     */       try {
/* 764 */         if ((this.worldObj.isRemote) && ((j & 0xFFF) > 0)) {
/* 765 */           Block var40 = Block.getBlockById(j & 0xFFF);
/* 766 */           if (var40 != null) {
/* 767 */             this.worldObj.playSound(this.digX + 0.5F, this.digY + 0.5F, this.digZ + 0.5F, var40.stepSound.getBreakSound(), (var40.stepSound.getVolume() + 1.0F) / 2.0F, var40.stepSound.getFrequency() * 0.8F, false);
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 772 */             for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(10); a++) {
/* 773 */               Thaumcraft.proxy.getFX().boreDigFx(this.digX, this.digY, this.digZ, this.pos.getX() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetX(), getPos().getY() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetY(), getPos().getZ() + BlockStateUtils.getFacing(getBlockMetadata()).getFrontOffsetZ(), var40, j >> 12 & 0xFF);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 783 */       return true;
/*     */     }
/* 785 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */   
/*     */   public void getDigEvent(BlockPos blockPos) {
/* 789 */     this.digX = blockPos.getX();
/* 790 */     this.digY = blockPos.getY();
/* 791 */     this.digZ = blockPos.getZ();
/* 792 */     this.toDig = true;
/* 793 */     this.digBlock = this.worldObj.getBlockState(new BlockPos(this.digX, this.digY, this.digZ)).getBlock();
/* 794 */     this.digMd = this.digBlock.getMetaFromState(this.worldObj.getBlockState(new BlockPos(this.digX, this.digY, this.digZ)));
/*     */   }
/*     */   
/*     */   public void sendDigEvent() {
/* 798 */     PacketHandler.INSTANCE.sendToAllAround(new PacketBoreDig(getPos(), new BlockPos(this.digX, this.digY, this.digZ)), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), getPos().getX(), getPos().getY(), getPos().getZ(), 64.0D));
/*     */   }
/*     */   
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/* 804 */     return 2;
/*     */   }
/*     */   
/*     */   public ItemStack getStackInSlot(int var1)
/*     */   {
/* 809 */     return this.contents[var1];
/*     */   }
/*     */   
/*     */   public ItemStack decrStackSize(int var1, int var2)
/*     */   {
/* 814 */     if (this.contents[var1] != null)
/*     */     {
/*     */ 
/* 817 */       if (this.contents[var1].stackSize <= var2) {
/* 818 */         ItemStack var3 = this.contents[var1];
/* 819 */         this.contents[var1] = null;
/* 820 */         markDirty();
/* 821 */         return var3;
/*     */       }
/* 823 */       ItemStack var3 = this.contents[var1].splitStack(var2);
/*     */       
/* 825 */       if (this.contents[var1].stackSize == 0) {
/* 826 */         this.contents[var1] = null;
/*     */       }
/*     */       
/* 829 */       markDirty();
/* 830 */       return var3;
/*     */     }
/*     */     
/* 833 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack removeStackFromSlot(int var1)
/*     */   {
/* 839 */     if (this.contents[var1] != null) {
/* 840 */       ItemStack var2 = this.contents[var1];
/* 841 */       this.contents[var1] = null;
/* 842 */       return var2;
/*     */     }
/* 844 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setInventorySlotContents(int var1, ItemStack var2)
/*     */   {
/* 850 */     this.contents[var1] = var2;
/*     */     
/* 852 */     if ((var2 != null) && (var2.stackSize > getInventoryStackLimit())) {
/* 853 */       var2.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 856 */     markDirty();
/*     */   }
/*     */   
/*     */ 
/*     */   public int getInventoryStackLimit()
/*     */   {
/* 862 */     return 64;
/*     */   }
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer var1)
/*     */   {
/* 867 */     return this.worldObj.getTileEntity(this.pos) == this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/*     */   {
/* 875 */     if ((i == 0) && (itemstack != null) && ((itemstack.getItem() instanceof ItemFocusExcavation))) return true;
/* 876 */     if ((i == 1) && (itemstack != null) && ((itemstack.getItem() instanceof ItemPickaxe))) return true;
/* 877 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, BlockPos pos, EnumFacing side)
/*     */   {
/* 883 */     setOrientation(side, false);
/* 884 */     player.worldObj.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:tool", 0.5F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F, false);
/*     */     
/* 886 */     player.swingItem();
/* 887 */     markDirty();
/* 888 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 905 */   private final int itemsPerVis = 20;
/*     */   
/*     */   public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
/*     */   
/*     */   public String getName() {
/* 910 */     return null; }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasCustomName()
/*     */   {
/* 916 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 922 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void openInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getField(int id)
/*     */   {
/* 940 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFieldCount()
/*     */   {
/* 952 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileArcaneBore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */