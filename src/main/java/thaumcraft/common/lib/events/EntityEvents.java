/*     */ package thaumcraft.common.lib.events;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.item.EntityEnderPearl;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityGolem;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraftforge.common.BiomeDictionary;
/*     */ import net.minecraftforge.common.BiomeDictionary.Type;
/*     */ import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
/*     */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*     */ import net.minecraftforge.event.entity.item.ItemExpireEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingDeathEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingDropsEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingHurtEvent;
/*     */ import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectHelper;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.damagesource.DamageSourceThaumcraft;
/*     */ import thaumcraft.api.entities.IEldritchMob;
/*     */ import thaumcraft.api.entities.ITaintedMob;
/*     */ import thaumcraft.api.items.ItemGenericEssentiaContainer;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.config.ConfigEntities;
/*     */ import thaumcraft.common.entities.construct.EntityOwnedConstruct;
/*     */ import thaumcraft.common.entities.monster.EntityBrainyZombie;
/*     */ import thaumcraft.common.entities.monster.EntityThaumicSlime;
/*     */ import thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultist;
/*     */ import thaumcraft.common.entities.monster.mods.ChampionModifier;
/*     */ import thaumcraft.common.entities.monster.mods.IChampionModifierEffect;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintChicken;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintCow;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintCrawler;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintCreeper;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintPig;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintSheep;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintVillager;
/*     */ import thaumcraft.common.items.armor.ItemFortressArmor;
/*     */ import thaumcraft.common.items.consumables.ItemBathSalts;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXShield;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ import thaumcraft.common.lib.world.dim.Cell;
/*     */ import thaumcraft.common.lib.world.dim.CellLoc;
/*     */ import thaumcraft.common.lib.world.dim.MazeHandler;
/*     */ import thaumcraft.common.tiles.TileOwned;
/*     */ 
/*     */ public class EntityEvents
/*     */ {
/*     */   @SubscribeEvent
/*     */   public void itemExpire(ItemExpireEvent event)
/*     */   {
/* 100 */     if ((event.entityItem.getEntityItem() != null) && (event.entityItem.getEntityItem().getItem() != null) && ((event.entityItem.getEntityItem().getItem() instanceof ItemBathSalts)))
/*     */     {
/* 102 */       BlockPos bp = new BlockPos(event.entityItem);
/* 103 */       IBlockState bs = event.entityItem.worldObj.getBlockState(bp);
/* 104 */       if ((bs.getBlock() == Blocks.water) && (bs.getBlock().getMetaFromState(bs) == 0)) {
/* 105 */         event.entityItem.worldObj.setBlockState(bp, BlocksTC.purifyingFluid.getDefaultState());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @SubscribeEvent
/*     */   public void livingTick(LivingEvent.LivingUpdateEvent event)
/*     */   {
/* 114 */     if (((event.entity instanceof EntityMob)) && (!event.entity.isDead)) {
/* 115 */       EntityMob mob = (EntityMob)event.entity;
/* 116 */       int t = (int)mob.getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue();
/*     */       try {
/* 118 */         if ((t >= 0) && (ChampionModifier.mods[t].type == 0)) {
/* 119 */           ChampionModifier.mods[t].effect.performEffect(mob, null, null, 0.0F);
/*     */         }
/*     */       } catch (Exception e) {
/* 122 */         if (t >= ChampionModifier.mods.length) {
/* 123 */           mob.setDead();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @SubscribeEvent
/*     */   public void livingDeath(LivingDeathEvent event)
/*     */   {
/* 133 */     if ((!event.entityLiving.worldObj.isRemote) && (!(event.entityLiving instanceof EntityOwnedConstruct)) && (!(event.entityLiving instanceof EntityGolem)) && (!(event.entityLiving instanceof ITaintedMob)) && (event.entityLiving.isPotionActive(PotionFluxTaint.instance)))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 139 */       Entity entity = null;
/*     */       
/* 141 */       if ((event.entityLiving instanceof EntityCreeper)) {
/* 142 */         entity = new EntityTaintCreeper(event.entityLiving.worldObj);
/*     */       }
/* 144 */       else if ((event.entityLiving instanceof EntitySheep)) {
/* 145 */         entity = new EntityTaintSheep(event.entityLiving.worldObj);
/*     */       }
/* 147 */       else if ((event.entityLiving instanceof EntityCow)) {
/* 148 */         entity = new EntityTaintCow(event.entityLiving.worldObj);
/*     */       }
/* 150 */       else if ((event.entityLiving instanceof EntityPig)) {
/* 151 */         entity = new EntityTaintPig(event.entityLiving.worldObj);
/*     */       }
/* 153 */       else if ((event.entityLiving instanceof EntityChicken)) {
/* 154 */         entity = new EntityTaintChicken(event.entityLiving.worldObj);
/*     */       }
/* 156 */       else if ((event.entityLiving instanceof EntityVillager)) {
/* 157 */         entity = new EntityTaintVillager(event.entityLiving.worldObj);
/*     */       }
/* 159 */       else if ((event.entityLiving.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) || ((event.entityLiving instanceof EntityAnimal)))
/*     */       {
/* 161 */         int n = (int)Math.max(1.0D, Math.sqrt(event.entityLiving.getMaxHealth() + 2.0F));
/* 162 */         for (int a = 0; a < n; a++) {
/* 163 */           Entity e = new EntityTaintCrawler(event.entityLiving.worldObj);
/* 164 */           e.setLocationAndAngles(event.entityLiving.posX + (event.entityLiving.worldObj.rand.nextFloat() - event.entityLiving.worldObj.rand.nextFloat()) * event.entityLiving.width, event.entityLiving.posY + event.entityLiving.worldObj.rand.nextFloat() * event.entityLiving.height, event.entityLiving.posZ + (event.entityLiving.worldObj.rand.nextFloat() - event.entityLiving.worldObj.rand.nextFloat()) * event.entityLiving.width, event.entityLiving.worldObj.rand.nextInt(360), 0.0F);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 169 */           event.entityLiving.worldObj.spawnEntityInWorld(e);
/*     */         }
/* 171 */         event.entityLiving.setDead();
/* 172 */         event.setCanceled(true);
/*     */       }
/* 174 */       else if ((event.entityLiving instanceof EntityRabbit)) {
/* 175 */         entity = new thaumcraft.common.entities.monster.tainted.EntityTaintRabbit(event.entityLiving.worldObj);
/* 176 */         ((EntityRabbit)entity).setRabbitType(((EntityRabbit)event.entityLiving).getRabbitType());
/*     */       }
/*     */       else {
/* 179 */         entity = new EntityThaumicSlime(event.entityLiving.worldObj);
/* 180 */         if (entity != null) { ((EntityThaumicSlime)entity).setSlimeSize((int)(1.0F + Math.min(event.entityLiving.getMaxHealth() / 10.0F, 6.0F)));
/*     */         }
/*     */       }
/* 183 */       if (entity != null)
/*     */       {
/* 185 */         entity.setLocationAndAngles(event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, event.entityLiving.rotationYaw, 0.0F);
/* 186 */         event.entityLiving.worldObj.spawnEntityInWorld(entity);
/* 187 */         if (!(event.entityLiving instanceof EntityPlayer)) {
/* 188 */           event.entityLiving.setDead();
/* 189 */           event.setCanceled(true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SubscribeEvent
/*     */   public void entityHurt(LivingHurtEvent event)
/*     */   {
/* 200 */     if ((event.source.getSourceOfDamage() != null) && ((event.source.getSourceOfDamage() instanceof EntityPlayer))) {
/* 201 */       EntityPlayer leecher = (EntityPlayer)event.source.getSourceOfDamage();
/* 202 */       ItemStack helm = leecher.inventory.armorInventory[3];
/* 203 */       if ((helm != null) && ((helm.getItem() instanceof ItemFortressArmor)) && 
/* 204 */         (helm.hasTagCompound()) && (helm.getTagCompound().hasKey("mask")) && (helm.getTagCompound().getInteger("mask") == 2) && (leecher.worldObj.rand.nextFloat() < event.ammount / 12.0F))
/*     */       {
/*     */ 
/*     */ 
/* 208 */         leecher.heal(1.0F);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 213 */     if ((event.entity instanceof EntityPlayer))
/*     */     {
/* 215 */       EntityPlayer player = (EntityPlayer)event.entity;
/*     */       
/*     */ 
/* 218 */       if ((event.source.getSourceOfDamage() != null) && ((event.source.getSourceOfDamage() instanceof EntityLivingBase))) {
/* 219 */         EntityLivingBase attacker = (EntityLivingBase)event.source.getSourceOfDamage();
/* 220 */         ItemStack helm = player.inventory.armorInventory[3];
/* 221 */         if ((helm != null) && ((helm.getItem() instanceof ItemFortressArmor)) && 
/* 222 */           (helm.hasTagCompound()) && (helm.getTagCompound().hasKey("mask")) && (helm.getTagCompound().getInteger("mask") == 1) && (player.worldObj.rand.nextFloat() < event.ammount / 10.0F))
/*     */         {
/*     */           try
/*     */           {
/* 226 */             attacker.addPotionEffect(new PotionEffect(Potion.wither.getId(), 80));
/*     */           }
/*     */           catch (Exception e) {}
/*     */         }
/*     */       }
/* 231 */       int charge = (int)player.getAbsorptionAmount();
/*     */       
/* 233 */       if ((charge > 0) && (PlayerEvents.runicInfo.containsKey(Integer.valueOf(player.getEntityId()))) && (PlayerEvents.lastMaxCharge.containsKey(Integer.valueOf(player.getEntityId()))))
/*     */       {
/*     */ 
/* 236 */         long time = System.currentTimeMillis();
/*     */         
/* 238 */         int target = -1;
/* 239 */         if (event.source.getEntity() != null) target = event.source.getEntity().getEntityId();
/* 240 */         if (event.source == DamageSource.fall) target = -2;
/* 241 */         if (event.source == DamageSource.fallingBlock) { target = -3;
/*     */         }
/* 243 */         PacketHandler.INSTANCE.sendToAllAround(new PacketFXShield(event.entity.getEntityId(), target), new NetworkRegistry.TargetPoint(event.entity.worldObj.provider.getDimensionId(), event.entity.posX, event.entity.posY, event.entity.posZ, 32.0D));
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 248 */         String key = player.getEntityId() + ":" + 2;
/* 249 */         if ((charge <= event.ammount) && (((Integer[])PlayerEvents.runicInfo.get(Integer.valueOf(player.getEntityId())))[2].intValue() > 0) && ((!PlayerEvents.upgradeCooldown.containsKey(key)) || (((Long)PlayerEvents.upgradeCooldown.get(key)).longValue() < time)))
/*     */         {
/* 251 */           PlayerEvents.upgradeCooldown.put(key, Long.valueOf(time + 20000L));
/* 252 */           player.worldObj.newExplosion(player, player.posX, player.posY + player.height / 2.0F, player.posZ, 1.5F + ((Integer[])PlayerEvents.runicInfo.get(Integer.valueOf(player.getEntityId())))[2].intValue() * 0.5F, false, false);
/*     */         }
/*     */         
/* 255 */         key = player.getEntityId() + ":" + 3;
/* 256 */         if ((charge <= event.ammount) && (((Integer[])PlayerEvents.runicInfo.get(Integer.valueOf(player.getEntityId())))[3].intValue() > 0) && ((!PlayerEvents.upgradeCooldown.containsKey(key)) || (((Long)PlayerEvents.upgradeCooldown.get(key)).longValue() < time)))
/*     */         {
/* 258 */           PlayerEvents.upgradeCooldown.put(key, Long.valueOf(time + 20000L));
/* 259 */           synchronized (player) {
/*     */             try {
/* 261 */               player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 240, ((Integer[])PlayerEvents.runicInfo.get(Integer.valueOf(player.getEntityId())))[3].intValue()));
/*     */             } catch (Exception e) {}
/*     */           }
/* 264 */           player.worldObj.playSoundAtEntity(player, "thaumcraft:runicShieldEffect", 1.0F, 1.0F);
/*     */         }
/*     */         
/* 267 */         key = player.getEntityId() + ":" + 4;
/* 268 */         if ((charge <= event.ammount) && (((Integer[])PlayerEvents.runicInfo.get(Integer.valueOf(player.getEntityId())))[4].intValue() > 0) && ((!PlayerEvents.upgradeCooldown.containsKey(key)) || (((Long)PlayerEvents.upgradeCooldown.get(key)).longValue() < time)))
/*     */         {
/* 270 */           PlayerEvents.upgradeCooldown.put(key, Long.valueOf(time + 60000L));
/* 271 */           int t = 8 * ((Integer[])PlayerEvents.runicInfo.get(Integer.valueOf(player.getEntityId())))[4].intValue();
/* 272 */           charge = Math.min(((Integer[])PlayerEvents.runicInfo.get(Integer.valueOf(player.getEntityId())))[0].intValue(), t);
/* 273 */           player.worldObj.playSoundAtEntity(player, "thaumcraft:runicShieldCharge", 1.0F, 1.0F);
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */     }
/* 279 */     else if ((event.entity instanceof EntityMob)) {
/* 280 */       IAttributeInstance cai = ((EntityMob)event.entity).getEntityAttribute(EntityUtils.CHAMPION_MOD);
/* 281 */       if (((cai != null) && (cai.getAttributeValue() >= 0.0D)) || ((event.entity instanceof IEldritchMob)))
/*     */       {
/* 283 */         EntityMob mob = (EntityMob)event.entity;
/* 284 */         int t = (int)cai.getAttributeValue();
/*     */         
/* 286 */         if (((t == 5) || ((event.entity instanceof IEldritchMob))) && (mob.getAbsorptionAmount() > 0.0F)) {
/* 287 */           int target = -1;
/* 288 */           if (event.source.getEntity() != null) {
/* 289 */             target = event.source.getEntity().getEntityId();
/*     */           }
/* 291 */           if (event.source == DamageSource.fall) target = -2;
/* 292 */           if (event.source == DamageSource.fallingBlock) target = -3;
/* 293 */           PacketHandler.INSTANCE.sendToAllAround(new PacketFXShield(mob.getEntityId(), target), new NetworkRegistry.TargetPoint(event.entity.worldObj.provider.getDimensionId(), event.entity.posX, event.entity.posY, event.entity.posZ, 32.0D));
/*     */           
/*     */ 
/* 296 */           event.entity.worldObj.playSoundEffect(event.entity.posX, event.entity.posY, event.entity.posZ, "thaumcraft:runicShieldEffect", 0.66F, 1.1F + event.entity.worldObj.rand.nextFloat() * 0.1F);
/*     */ 
/*     */         }
/* 299 */         else if ((t >= 0) && (ChampionModifier.mods[t].type == 2) && (event.source.getSourceOfDamage() != null) && ((event.source.getSourceOfDamage() instanceof EntityLivingBase)))
/*     */         {
/* 301 */           EntityLivingBase attacker = (EntityLivingBase)event.source.getSourceOfDamage();
/* 302 */           event.ammount = ChampionModifier.mods[t].effect.performEffect(mob, attacker, event.source, event.ammount);
/*     */         }
/*     */       }
/*     */       
/* 306 */       if ((event.ammount > 0.0F) && (event.source.getSourceOfDamage() != null) && ((event.entity instanceof EntityLivingBase)) && ((event.source.getSourceOfDamage() instanceof EntityMob)) && (((EntityMob)event.source.getSourceOfDamage()).getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue() >= 0.0D))
/*     */       {
/*     */ 
/* 309 */         EntityMob mob = (EntityMob)event.source.getSourceOfDamage();
/* 310 */         int t = (int)mob.getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue();
/* 311 */         if (ChampionModifier.mods[t].type == 1) {
/* 312 */           event.ammount = ChampionModifier.mods[t].effect.performEffect(mob, (EntityLivingBase)event.entity, event.source, event.ammount);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void itemPickup(EntityItemPickupEvent event)
/*     */   {
/* 321 */     if (event.entityPlayer.getName().startsWith("FakeThaumcraft")) {
/* 322 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void entityConstuct(EntityEvent.EntityConstructing event) {
/* 328 */     if ((event.entity instanceof EntityMob)) {
/* 329 */       EntityMob mob = (EntityMob)event.entity;
/* 330 */       mob.getAttributeMap().registerAttribute(EntityUtils.CHAMPION_MOD).setBaseValue(-2.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void livingDrops(LivingDropsEvent event) {
/* 336 */     boolean fakeplayer = (event.source.getEntity() != null) && ((event.source.getEntity() instanceof net.minecraftforge.common.util.FakePlayer));
/*     */     
/*     */ 
/* 339 */     if ((!event.entity.worldObj.isRemote) && (event.recentlyHit) && (!fakeplayer) && ((event.entity instanceof EntityMob)) && (!(event.entity instanceof EntityThaumcraftBoss)) && (((EntityMob)event.entity).getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue() >= 0.0D))
/*     */     {
/*     */ 
/*     */ 
/* 343 */       int i = 5 + event.entity.worldObj.rand.nextInt(3);
/* 344 */       while (i > 0)
/*     */       {
/* 346 */         int j = EntityXPOrb.getXPSplit(i);
/* 347 */         i -= j;
/* 348 */         event.entity.worldObj.spawnEntityInWorld(new EntityXPOrb(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, j));
/*     */       }
/*     */       
/* 351 */       int lb = Math.min(2, MathHelper.floor_float((event.entity.worldObj.rand.nextInt(9) + event.lootingLevel) / 5.0F));
/* 352 */       event.drops.add(new EntityItem(event.entity.worldObj, event.entityLiving.posX, event.entityLiving.posY + event.entityLiving.getEyeHeight(), event.entityLiving.posZ, new ItemStack(ItemsTC.lootBag, 1, lb)));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 361 */     if (((event.entityLiving instanceof EntityZombie)) && (!(event.entityLiving instanceof EntityBrainyZombie)))
/*     */     {
/* 363 */       if ((event.recentlyHit) && (event.entity.worldObj.rand.nextInt(10) - event.lootingLevel < 1)) {
/* 364 */         event.drops.add(new EntityItem(event.entity.worldObj, event.entityLiving.posX, event.entityLiving.posY + event.entityLiving.getEyeHeight(), event.entityLiving.posZ, new ItemStack(ItemsTC.brain)));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 372 */     if (((event.entityLiving instanceof EntityVillager)) && (event.entity.worldObj.rand.nextInt(10) - event.lootingLevel < 1))
/*     */     {
/* 374 */       event.drops.add(new EntityItem(event.entity.worldObj, event.entityLiving.posX, event.entityLiving.posY + event.entityLiving.getEyeHeight(), event.entityLiving.posZ, new ItemStack(ItemsTC.coin)));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 382 */     if (((event.entityLiving instanceof EntityCultist)) && (!fakeplayer) && (event.source.getEntity() != null) && ((event.source.getEntity() instanceof EntityPlayer)))
/*     */     {
/* 384 */       int c = !thaumcraft.common.lib.research.ResearchManager.isResearchComplete(((EntityPlayer)event.source.getEntity()).getName(), "CRIMSON") ? 4 : 50;
/* 385 */       if (InventoryUtils.isPlayerCarrying((EntityPlayer)event.source.getEntity(), new ItemStack(ItemsTC.crimsonRites)) > 0) c = 100;
/* 386 */       if (event.entity.worldObj.rand.nextInt(c) == 0) {
/* 387 */         event.drops.add(new EntityItem(event.entity.worldObj, event.entityLiving.posX, event.entityLiving.posY + event.entityLiving.getEyeHeight(), event.entityLiving.posZ, new ItemStack(ItemsTC.crimsonRites)));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 395 */     if (event.source == DamageSourceThaumcraft.dissolve) {
/* 396 */       AspectList aspects = AspectHelper.getEntityAspects(event.entityLiving);
/* 397 */       if ((aspects != null) && (aspects.size() > 0)) {
/* 398 */         for (Aspect aspect : aspects.getAspects()) {
/* 399 */           if (!event.entity.worldObj.rand.nextBoolean()) {
/* 400 */             int size = 1 + event.entity.worldObj.rand.nextInt(aspects.getAmount(aspect));
/* 401 */             size = Math.max(1, size / 2);
/* 402 */             ItemStack stack = new ItemStack(ItemsTC.crystalEssence, size, 0);
/* 403 */             ((ItemGenericEssentiaContainer)stack.getItem()).setAspects(stack, new AspectList().add(aspect, 1));
/* 404 */             event.drops.add(new EntityItem(event.entity.worldObj, event.entityLiving.posX, event.entityLiving.posY + event.entityLiving.getEyeHeight(), event.entityLiving.posZ, stack));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 416 */   public static HashMap<String, ArrayList<WeakReference<Entity>>> linkedEntities = new HashMap();
/*     */   
/*     */   @SubscribeEvent
/*     */   public void entitySpawns(EntityJoinWorldEvent event) {
/* 420 */     if (!event.world.isRemote) {
/* 421 */       if ((event.entity instanceof EntityEnderPearl)) {
/* 422 */         int x = MathHelper.floor_double(event.entity.posX);
/* 423 */         int y = MathHelper.floor_double(event.entity.posY);
/* 424 */         int z = MathHelper.floor_double(event.entity.posZ);
/*     */         
/* 426 */         for (int xx = -5; xx <= 5; xx++) for (int yy = -5; yy <= 5; yy++) for (int zz = -5; zz <= 5; zz++) {
/* 427 */               TileEntity tile = event.world.getTileEntity(new BlockPos(x + xx, y + yy, z + zz));
/* 428 */               if ((tile != null) && ((tile instanceof TileOwned))) {
/* 429 */                 if ((((EntityEnderPearl)event.entity).getThrower() instanceof EntityPlayer)) {
/* 430 */                   ((EntityPlayer)((EntityEnderPearl)event.entity).getThrower()).addChatMessage(new ChatComponentText("§5§oThe magic of a nearby warded object destroys the ender pearl."));
/*     */                 }
/* 432 */                 event.entity.setDead();
/*     */                 break label195;
/*     */               }
/*     */             }
/*     */       }
/*     */       label195:
/* 438 */       if ((event.entity instanceof EntityPlayer)) {
/* 439 */         ArrayList<WeakReference<Entity>> dudes = (ArrayList)linkedEntities.get(event.entity.getName());
/* 440 */         if (dudes != null) {
/* 441 */           for (WeakReference<Entity> dude : dudes) {
/* 442 */             if ((dude.get() != null) && (((Entity)dude.get()).timeUntilPortal == 0)) {
/* 443 */               ((Entity)dude.get()).timeUntilPortal = ((Entity)dude.get()).getPortalCooldown();
/* 444 */               ((Entity)dude.get()).travelToDimension(event.world.provider.getDimensionId());
/*     */             }
/*     */             
/*     */           }
/*     */         }
/*     */       }
/* 450 */       else if ((event.entity instanceof EntityMob)) {
/* 451 */         EntityMob mob = (EntityMob)event.entity;
/*     */         
/* 453 */         if (mob.getEntityAttribute(EntityUtils.CHAMPION_MOD).getAttributeValue() < -1.0D)
/*     */         {
/* 455 */           int c = event.world.rand.nextInt(100);
/*     */           
/* 457 */           if ((event.world.getDifficulty() == EnumDifficulty.EASY) || (!Config.championMobs)) c += 2;
/* 458 */           if (event.world.getDifficulty() == EnumDifficulty.HARD) c -= (Config.championMobs ? 2 : 0);
/* 459 */           if (event.world.provider.getDimensionId() == Config.dimensionOuterId) c -= 3;
/* 460 */           BiomeGenBase bg = mob.worldObj.getBiomeGenForCoords(new BlockPos(mob));
/* 461 */           if ((BiomeDictionary.isBiomeOfType(bg, BiomeDictionary.Type.SPOOKY)) || (BiomeDictionary.isBiomeOfType(bg, BiomeDictionary.Type.NETHER)) || (BiomeDictionary.isBiomeOfType(bg, BiomeDictionary.Type.END)))
/*     */           {
/* 463 */             c -= (Config.championMobs ? 2 : 1);
/*     */           }
/* 465 */           if (isDangerousLocation(mob.worldObj, MathHelper.ceiling_double_int(mob.posX), MathHelper.ceiling_double_int(mob.posY), MathHelper.ceiling_double_int(mob.posZ)))
/*     */           {
/*     */ 
/*     */ 
/* 469 */             c -= (Config.championMobs ? 10 : 3);
/*     */           }
/*     */           
/* 472 */           int cc = 0;
/* 473 */           boolean whitelisted = false;
/* 474 */           for (Class clazz : ConfigEntities.championModWhitelist.keySet()) {
/* 475 */             if (clazz.isAssignableFrom(event.entity.getClass())) {
/* 476 */               whitelisted = true;
/* 477 */               if ((Config.championMobs) || ((event.entity instanceof EntityThaumcraftBoss))) {
/* 478 */                 cc = Math.max(cc, ((Integer)ConfigEntities.championModWhitelist.get(clazz)).intValue() - 1);
/*     */               }
/*     */             }
/*     */           }
/* 482 */           c -= cc;
/*     */           
/* 484 */           if ((whitelisted) && (c <= 0) && (mob.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() >= 10.0D)) {
/* 485 */             EntityUtils.makeChampion(mob, false);
/*     */           } else {
/* 487 */             IAttributeInstance modai = mob.getEntityAttribute(EntityUtils.CHAMPION_MOD);
/* 488 */             modai.removeModifier(ChampionModifier.ATTRIBUTE_MOD_NONE);
/* 489 */             modai.applyModifier(ChampionModifier.ATTRIBUTE_MOD_NONE);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isDangerousLocation(World world, int x, int y, int z) {
/* 497 */     if (world.provider.getDimensionId() == Config.dimensionOuterId) {
/* 498 */       int xx = x >> 4;
/* 499 */       int zz = z >> 4;
/* 500 */       Cell c = MazeHandler.getFromHashMap(new CellLoc(xx, zz));
/* 501 */       if ((c != null) && ((c.feature == 6) || (c.feature == 8))) {
/* 502 */         return true;
/*     */       }
/*     */     }
/* 505 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\events\EntityEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */