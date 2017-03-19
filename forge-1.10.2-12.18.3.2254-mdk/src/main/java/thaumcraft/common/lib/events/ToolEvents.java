/*     */ package thaumcraft.common.lib.events;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ import net.minecraftforge.event.entity.living.LivingDropsEvent;
/*     */ import net.minecraftforge.event.entity.player.ArrowLooseEvent;
/*     */ import net.minecraftforge.event.entity.player.ArrowNockEvent;
/*     */ import net.minecraftforge.event.entity.player.AttackEntityEvent;
/*     */ import net.minecraftforge.event.entity.player.FillBucketEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
/*     */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
/*     */ import net.minecraftforge.event.world.BlockEvent.BreakEvent;
/*     */ import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
/*     */ import net.minecraftforge.fluids.BlockFluidClassic;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event.Result;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectHelper;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemGenericEssentiaContainer;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.entities.EntityFollowingItem;
/*     */ import thaumcraft.common.entities.projectile.EntityPrimalArrow;
/*     */ import thaumcraft.common.items.armor.Hover;
/*     */ import thaumcraft.common.items.tools.ItemBowBone;
/*     */ import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXScanSource;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXSlash;
/*     */ import thaumcraft.common.lib.utils.BlockUtils;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ 
/*     */ public class ToolEvents
/*     */ {
/*     */   @SubscribeEvent
/*     */   public void playerAttack(AttackEntityEvent event)
/*     */   {
/*  74 */     ItemStack heldItem = event.entityPlayer.getHeldItem();
/*  75 */     if (heldItem != null) {
/*  76 */       List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(heldItem);
/*     */       
/*     */ 
/*  79 */       if ((list.contains(EnumInfusionEnchantment.ARCING)) && (event.target.isEntityAlive())) {
/*  80 */         int rank = EnumInfusionEnchantment.getInfusionEnchantmentLevel(heldItem, EnumInfusionEnchantment.ARCING);
/*  81 */         List targets = event.entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(event.entityPlayer, event.target.getEntityBoundingBox().expand(1.5D + rank, 1.0F + rank / 2.0F, 1.5D + rank));
/*     */         
/*  83 */         int count = 0;
/*  84 */         if (targets.size() > 1) {
/*  85 */           for (int var9 = 0; var9 < targets.size(); var9++)
/*     */           {
/*  87 */             Entity var10 = (Entity)targets.get(var9);
/*  88 */             if ((!var10.isDead) && (
/*  89 */               (!(var10 instanceof IEntityOwnable)) || (((IEntityOwnable)var10).getOwner() == null) || (!((IEntityOwnable)var10).getOwner().equals(event.entityPlayer))))
/*     */             {
/*  91 */               if (((var10 instanceof EntityLiving)) && (var10.getEntityId() != event.target.getEntityId()) && (
/*  92 */                 (!(var10 instanceof EntityPlayer)) || (((EntityPlayer)var10).getName() != event.entityPlayer.getName())))
/*     */               {
/*     */ 
/*     */ 
/*  96 */                 if (var10.isEntityAlive()) {
/*  97 */                   float f = (float)event.entityPlayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
/*     */                   
/*  99 */                   event.entityPlayer.attackEntityAsMob(var10);
/* 100 */                   if (var10.attackEntityFrom(DamageSource.causePlayerDamage(event.entityPlayer), f * 0.5F))
/*     */                   {
/*     */                     try {
/* 103 */                       if ((var10 instanceof EntityLivingBase)) {
/* 104 */                         EnchantmentHelper.applyThornEnchantments((EntityLivingBase)var10, event.entityPlayer);
/*     */                       }
/*     */                     }
/*     */                     catch (Exception e) {}
/*     */                     
/*     */ 
/*     */ 
/* 111 */                     var10.addVelocity(-MathHelper.sin(event.entityPlayer.rotationYaw * 3.1415927F / 180.0F) * 0.5F, 0.1D, MathHelper.cos(event.entityPlayer.rotationYaw * 3.1415927F / 180.0F) * 0.5F);
/*     */                     
/*     */ 
/*     */ 
/* 115 */                     count++;
/* 116 */                     if (!event.entityPlayer.worldObj.isRemote) {
/* 117 */                       PacketHandler.INSTANCE.sendToAllAround(new PacketFXSlash(event.target.getEntityId(), var10.getEntityId()), new NetworkRegistry.TargetPoint(event.entityPlayer.worldObj.provider.getDimensionId(), event.target.posX, event.target.posY, event.target.posZ, 64.0D));
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */               
/*     */ 
/* 124 */               if (count >= rank) break;
/*     */             }
/*     */           }
/* 127 */           if ((count > 0) && (!event.entityPlayer.worldObj.isRemote)) {
/* 128 */             event.entityPlayer.worldObj.playSoundAtEntity(event.target, "thaumcraft:swing", 1.0F, 0.9F + event.entityPlayer.worldObj.rand.nextFloat() * 0.2F);
/* 129 */             PacketHandler.INSTANCE.sendToAllAround(new PacketFXSlash(event.entityPlayer.getEntityId(), event.target.getEntityId()), new NetworkRegistry.TargetPoint(event.entityPlayer.worldObj.provider.getDimensionId(), event.entityPlayer.posX, event.entityPlayer.posY, event.entityPlayer.posZ, 64.0D));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SubscribeEvent
/*     */   public void playerInteract(PlayerInteractEvent event)
/*     */   {
/* 141 */     if ((event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) && (!event.world.isRemote) && (event.entityPlayer != null)) {
/* 142 */       ItemStack heldItem = event.entityPlayer.getHeldItem();
/*     */       
/* 144 */       if (heldItem != null) {
/* 145 */         List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(heldItem);
/*     */         
/*     */ 
/* 148 */         if ((list.contains(EnumInfusionEnchantment.SOUNDING)) && (!event.entityPlayer.isSneaking())) {
/* 149 */           heldItem.damageItem(5, event.entityPlayer);
/* 150 */           event.world.playSoundEffect(event.pos.getX() + 0.5D, event.pos.getY() + 0.5D, event.pos.getZ() + 0.5D, "thaumcraft:wandfail", 0.2F, 0.2F + event.world.rand.nextFloat() * 0.2F);
/*     */           
/* 152 */           PacketHandler.INSTANCE.sendTo(new PacketFXScanSource(event.pos, EnumInfusionEnchantment.getInfusionEnchantmentLevel(heldItem, EnumInfusionEnchantment.SOUNDING)), (EntityPlayerMP)event.entityPlayer);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 160 */     if ((event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) && (event.entityPlayer != null)) {
/* 161 */       this.lastFaceClicked.put(Integer.valueOf(event.entityPlayer.getEntityId()), event.face);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 172 */   HashMap<Integer, EnumFacing> lastFaceClicked = new HashMap();
/*     */   
/*     */ 
/*     */   @SubscribeEvent
/*     */   public void breakBlockEvent(BlockEvent.BreakEvent event)
/*     */   {
/* 178 */     if ((!event.world.isRemote) && (event.getPlayer() != null)) {
/* 179 */       ItemStack heldItem = event.getPlayer().getHeldItem();
/*     */       
/* 181 */       if (heldItem != null) {
/* 182 */         List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(heldItem);
/* 183 */         if (ForgeHooks.isToolEffective(event.world, event.pos, heldItem))
/*     */         {
/*     */ 
/* 186 */           if ((list.contains(EnumInfusionEnchantment.BURROWING)) && (!event.getPlayer().isSneaking()) && (isValidBurrowBlock(event.world, event.pos))) {
/* 187 */             event.setCanceled(true);
/* 188 */             BlockUtils.breakFurthestBlock(event.world, event.pos, event.state, event.getPlayer());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isValidBurrowBlock(World world, BlockPos pos)
/*     */   {
/* 197 */     return (Utils.isWoodLog(world, pos)) || (Utils.isOreBlock(world, pos));
/*     */   }
/*     */   
/* 200 */   boolean blockDestructiveRecursion = false;
/*     */   
/*     */   @SubscribeEvent
/*     */   public void harvestBlockEvent(BlockEvent.HarvestDropsEvent event) {
/* 204 */     if ((!event.world.isRemote) && (event.harvester != null)) {
/* 205 */       ItemStack heldItem = event.harvester.getHeldItem();
/*     */       
/* 207 */       if (heldItem != null) {
/* 208 */         List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(heldItem);
/*     */         
/* 210 */         if ((event.isSilkTouching) || (ForgeHooks.isToolEffective(event.world, event.pos, heldItem)) || (((heldItem.getItem() instanceof ItemTool)) && (((ItemTool)heldItem.getItem()).getStrVsBlock(heldItem, event.state.getBlock()) > 1.0F)))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 215 */           if (list.contains(EnumInfusionEnchantment.REFINING)) {
/* 216 */             int fortune = 1 + EnumInfusionEnchantment.getInfusionEnchantmentLevel(heldItem, EnumInfusionEnchantment.REFINING);
/* 217 */             float chance = fortune * 0.125F;
/* 218 */             boolean b = false;
/* 219 */             for (int a = 0; a < event.drops.size(); a++)
/*     */             {
/* 221 */               ItemStack is = (ItemStack)event.drops.get(a);
/* 222 */               ItemStack smr = Utils.findSpecialMiningResult(is, chance, event.world.rand);
/* 223 */               if (!is.isItemEqual(smr)) {
/* 224 */                 event.drops.set(a, smr);
/* 225 */                 b = true;
/*     */               }
/*     */             }
/* 228 */             if (b) {
/* 229 */               event.world.playSoundEffect(event.pos.getX() + 0.5F, event.pos.getY() + 0.5F, event.pos.getZ() + 0.5F, "random.orb", 0.2F, 0.7F + event.world.rand.nextFloat() * 0.2F);
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 234 */           if ((!this.blockDestructiveRecursion) && (list.contains(EnumInfusionEnchantment.DESTRUCTIVE)) && (!event.harvester.isSneaking())) {
/* 235 */             this.blockDestructiveRecursion = true;
/*     */             
/* 237 */             EnumFacing face = (EnumFacing)this.lastFaceClicked.get(Integer.valueOf(event.harvester.getEntityId()));
/* 238 */             if (face == null) {
/* 239 */               face = BlockPistonBase.getFacingFromEntity(event.world, event.pos, event.harvester);
/*     */             }
/* 241 */             for (int aa = -1; aa <= 1; aa++)
/* 242 */               for (int bb = -1; bb <= 1; bb++)
/*     */               {
/* 244 */                 if ((aa != 0) || (bb != 0))
/*     */                 {
/* 246 */                   int xx = 0;
/* 247 */                   int yy = 0;
/* 248 */                   int zz = 0;
/*     */                   
/* 250 */                   if (face.ordinal() <= 1) {
/* 251 */                     xx = aa;
/* 252 */                     zz = bb;
/* 253 */                   } else if (face.ordinal() <= 3) {
/* 254 */                     xx = aa;
/* 255 */                     yy = bb;
/*     */                   } else {
/* 257 */                     zz = aa;
/* 258 */                     yy = bb;
/*     */                   }
/*     */                   
/* 261 */                   Block bl = event.world.getBlockState(event.pos.add(xx, yy, zz)).getBlock();
/*     */                   
/* 263 */                   if ((bl.getBlockHardness(event.world, event.pos.add(xx, yy, zz)) >= 0.0F) && ((ForgeHooks.isToolEffective(event.world, event.pos.add(xx, yy, zz), heldItem)) || (((heldItem.getItem() instanceof ItemTool)) && (((ItemTool)heldItem.getItem()).getStrVsBlock(heldItem, bl) > 1.0F))))
/*     */                   {
/*     */ 
/*     */ 
/*     */ 
/* 268 */                     heldItem.damageItem(1, event.harvester);
/* 269 */                     BlockUtils.harvestBlock(event.world, event.harvester, event.pos.add(xx, yy, zz));
/*     */                   }
/*     */                 } }
/* 272 */             this.blockDestructiveRecursion = false;
/*     */           }
/*     */           
/*     */ 
/* 276 */           if ((list.contains(EnumInfusionEnchantment.COLLECTOR)) && (!event.harvester.isSneaking())) {
/* 277 */             InventoryUtils.dropHarvestsAtPos(event.world, event.pos, event.drops, true, 10, event.harvester);
/* 278 */             event.drops.clear();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void breakSpeedEvent(PlayerEvent.BreakSpeed event)
/*     */   {
/* 288 */     if ((!event.entityPlayer.onGround) && (Hover.getHover(event.entityPlayer.getEntityId()))) {
/* 289 */       event.newSpeed = (event.originalSpeed * 5.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void bowNocked(ArrowNockEvent event) {
/* 295 */     if (event.entityPlayer.inventory.hasItem(ItemsTC.primalArrows))
/*     */     {
/* 297 */       event.entityPlayer.setItemInUse(event.result, event.result.getItem().getMaxItemUseDuration(event.result));
/*     */       
/* 299 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void bowShot(ArrowLooseEvent event) {
/* 305 */     if (event.entityPlayer.inventory.hasItem(ItemsTC.primalArrows))
/*     */     {
/* 307 */       float f = 0.0F;
/* 308 */       float dam = 2.0F;
/*     */       
/* 310 */       if ((event.bow.getItem() instanceof ItemBowBone)) {
/* 311 */         f = event.charge / 10.0F;
/* 312 */         f = (f * f + f * 2.0F) / 3.0F;
/*     */         
/* 314 */         if (f < 0.1D)
/*     */         {
/* 316 */           return;
/*     */         }
/* 318 */         dam = 2.5F;
/*     */       } else {
/* 320 */         f = event.charge / 20.0F;
/* 321 */         f = (f * f + f * 2.0F) / 3.0F;
/*     */         
/* 323 */         if (f < 0.1D)
/*     */         {
/* 325 */           return;
/*     */         }
/*     */       }
/*     */       
/* 329 */       if (f > 1.0F)
/*     */       {
/* 331 */         f = 1.0F;
/*     */       }
/*     */       
/* 334 */       int type = 0;
/*     */       
/* 336 */       for (int j = 0; j < event.entityPlayer.inventory.mainInventory.length; j++)
/*     */       {
/* 338 */         if ((event.entityPlayer.inventory.mainInventory[j] != null) && (event.entityPlayer.inventory.mainInventory[j].getItem() == ItemsTC.primalArrows))
/*     */         {
/*     */ 
/* 341 */           type = event.entityPlayer.inventory.mainInventory[j].getItemDamage();
/* 342 */           break;
/*     */         }
/*     */       }
/*     */       
/* 346 */       EntityPrimalArrow entityarrow = new EntityPrimalArrow(event.entityPlayer.worldObj, event.entityPlayer, f * dam, type);
/*     */       
/*     */ 
/* 349 */       if ((event.bow.getItem() instanceof ItemBowBone)) {
/* 350 */         entityarrow.setDamage(entityarrow.getDamage() + 0.5D);
/*     */       }
/* 352 */       else if (f == 1.0F)
/*     */       {
/* 354 */         entityarrow.setIsCritical(true);
/*     */       }
/*     */       
/*     */ 
/* 358 */       int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, event.bow);
/*     */       
/* 360 */       if (k > 0)
/*     */       {
/* 362 */         entityarrow.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);
/*     */       }
/*     */       
/* 365 */       int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, event.bow);
/* 366 */       if (type == 3) l++;
/* 367 */       if (l > 0)
/*     */       {
/* 369 */         entityarrow.setKnockbackStrength(l);
/*     */       }
/*     */       
/* 372 */       if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, event.bow) > 0)
/*     */       {
/* 374 */         entityarrow.setFire(100);
/*     */       }
/*     */       
/* 377 */       event.bow.damageItem(1, event.entityPlayer);
/* 378 */       event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.bow", 1.0F, 1.0F / (event.entityPlayer.worldObj.rand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
/*     */       
/* 380 */       boolean flag = false;
/* 381 */       if ((EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, event.bow) > 0) && (event.entityPlayer.worldObj.rand.nextFloat() < 0.33F))
/*     */       {
/* 383 */         flag = true;
/*     */       }
/* 385 */       if ((!event.entityPlayer.capabilities.isCreativeMode) || (!flag)) {
/* 386 */         InventoryUtils.consumeInventoryItem(event.entityPlayer, ItemsTC.primalArrows, type);
/*     */       }
/*     */       
/* 389 */       if (!event.entityPlayer.worldObj.isRemote)
/*     */       {
/* 391 */         event.entityPlayer.worldObj.spawnEntityInWorld(entityarrow);
/*     */       }
/*     */       
/* 394 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void fillBucket(FillBucketEvent event) {
/* 400 */     if (event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 401 */       IBlockState bs = event.world.getBlockState(event.target.getBlockPos());
/* 402 */       if ((bs.getBlock() == BlocksTC.purifyingFluid) && (((BlockFluidClassic)bs.getBlock()).isSourceBlock(event.world, event.target.getBlockPos()))) {
/* 403 */         event.world.setBlockToAir(event.target.getBlockPos());
/* 404 */         event.result = new ItemStack(ItemsTC.bucketPure);
/* 405 */         event.setResult(Event.Result.ALLOW);
/* 406 */         return;
/*     */       }
/* 408 */       if ((bs.getBlock() == BlocksTC.liquidDeath) && (((BlockFluidClassic)bs.getBlock()).isSourceBlock(event.world, event.target.getBlockPos()))) {
/* 409 */         event.world.setBlockToAir(event.target.getBlockPos());
/* 410 */         event.result = new ItemStack(ItemsTC.bucketDeath);
/* 411 */         event.setResult(Event.Result.ALLOW);
/* 412 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void livingDrops(LivingDropsEvent event) {
/* 419 */     if ((event.source.getEntity() != null) && ((event.source.getEntity() instanceof EntityPlayer))) {
/* 420 */       ItemStack heldItem = ((EntityPlayer)event.source.getEntity()).getHeldItem();
/*     */       
/* 422 */       if (heldItem != null) {
/* 423 */         List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(heldItem);
/*     */         
/*     */ 
/* 426 */         if (list.contains(EnumInfusionEnchantment.COLLECTOR)) {
/* 427 */           boolean b = false;
/* 428 */           for (int a = 0; a < event.drops.size(); a++)
/*     */           {
/* 430 */             EntityItem ei = (EntityItem)event.drops.get(a);
/* 431 */             ItemStack is = ei.getEntityItem().copy();
/* 432 */             EntityItem nei = new EntityFollowingItem(event.entity.worldObj, ei.posX, ei.posY, ei.posZ, is, event.source.getEntity(), 10);
/* 433 */             nei.motionX = ei.motionX;
/* 434 */             nei.motionY = ei.motionY;
/* 435 */             nei.motionZ = ei.motionZ;
/* 436 */             nei.setDefaultPickupDelay();
/* 437 */             ei.setDead();
/* 438 */             event.drops.set(a, nei);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 443 */         if (list.contains(EnumInfusionEnchantment.ESSENCE)) {
/* 444 */           AspectList aspects = AspectHelper.getEntityAspects(event.entityLiving).copy();
/* 445 */           if ((aspects != null) && (aspects.size() > 0)) {
/* 446 */             int q = EnumInfusionEnchantment.getInfusionEnchantmentLevel(heldItem, EnumInfusionEnchantment.ESSENCE);
/* 447 */             Aspect[] al = aspects.getAspects();
/* 448 */             int b = event.entity.worldObj.rand.nextInt(5) < q ? 0 : 99;
/* 449 */             while ((b < q) && (al != null) && (al.length > 0)) {
/* 450 */               Aspect aspect = al[event.entity.worldObj.rand.nextInt(al.length)];
/* 451 */               if (aspects.getAmount(aspect) > 0) {
/* 452 */                 aspects.remove(aspect, 1);
/* 453 */                 ItemStack stack = new ItemStack(ItemsTC.crystalEssence, 1, 0);
/* 454 */                 ((ItemGenericEssentiaContainer)stack.getItem()).setAspects(stack, new AspectList().add(aspect, 1));
/* 455 */                 if (list.contains(EnumInfusionEnchantment.COLLECTOR)) {
/* 456 */                   event.drops.add(new EntityFollowingItem(event.entity.worldObj, event.entityLiving.posX, event.entityLiving.posY + event.entityLiving.getEyeHeight(), event.entityLiving.posZ, stack, event.source.getEntity(), 10));
/*     */ 
/*     */                 }
/*     */                 else
/*     */                 {
/*     */ 
/* 462 */                   event.drops.add(new EntityItem(event.entity.worldObj, event.entityLiving.posX, event.entityLiving.posY + event.entityLiving.getEyeHeight(), event.entityLiving.posZ, stack));
/*     */                 }
/*     */                 
/*     */ 
/*     */ 
/*     */ 
/* 468 */                 b++;
/*     */               }
/* 470 */               al = aspects.getAspects();
/* 471 */               if (event.entity.worldObj.rand.nextInt(q) == 0) b += 1 + event.entity.worldObj.rand.nextInt(2);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\events\ToolEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */