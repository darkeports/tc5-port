/*     */ package thaumcraft.common.entities.monster;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAIOpenDoor;
/*     */ import net.minecraft.entity.ai.EntityAITasks;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest2;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemEnchantedBook;
/*     */ import net.minecraft.item.ItemNameTag;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraftforge.common.BiomeDictionary;
/*     */ import net.minecraftforge.common.BiomeDictionary.Type;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IEssentiaContainerItem;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
/*     */ import thaumcraft.common.entities.ai.pech.AIPechItemEntityGoto;
/*     */ import thaumcraft.common.entities.ai.pech.AIPechTradePlayer;
/*     */ import thaumcraft.common.entities.projectile.EntityPechBlast;
/*     */ import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ 
/*     */ public class EntityPech extends EntityMob implements IRangedAttackMob
/*     */ {
/*     */   public String getName()
/*     */   {
/*  72 */     if (hasCustomName())
/*     */     {
/*  74 */       return getCustomNameTag();
/*     */     }
/*  76 */     switch (getPechType()) {
/*  77 */     case 0:  return StatCollector.translateToLocal("entity.Thaumcraft.Pech.name");
/*  78 */     case 1:  return StatCollector.translateToLocal("entity.Thaumcraft.Pech.1.name");
/*  79 */     case 2:  return StatCollector.translateToLocal("entity.Thaumcraft.Pech.2.name");
/*     */     }
/*  81 */     return StatCollector.translateToLocal("entity.Thaumcraft.Pech.name");
/*     */   }
/*     */   
/*     */ 
/*  85 */   public ItemStack[] loot = new ItemStack[9];
/*     */   
/*  87 */   public boolean trading = false;
/*  88 */   public boolean updateAINextTick = false;
/*     */   
/*  90 */   private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 0.6D, 20, 50, 15.0F);
/*  91 */   private EntityAIArrowAttack aiBlastAttack = new EntityAIArrowAttack(this, 0.6D, 20, 30, 15.0F);
/*  92 */   private AIAttackOnCollide aiMeleeAttack = new AIAttackOnCollide(this, EntityLivingBase.class, 0.6D, false);
/*  93 */   private EntityAIAvoidEntity aiAvoidPlayer = new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 0.5D, 0.6D);
/*     */   
/*     */   public EntityPech(World world)
/*     */   {
/*  97 */     super(world);
/*  98 */     setSize(0.6F, 1.8F);
/*  99 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*     */     
/* 101 */     this.tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
/* 102 */     this.tasks.addTask(1, new AIPechTradePlayer(this));
/*     */     
/* 104 */     this.tasks.addTask(3, new AIPechItemEntityGoto(this));
/*     */     
/* 106 */     this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
/*     */     
/* 108 */     this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.5D));
/* 109 */     this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
/*     */     
/* 111 */     this.tasks.addTask(9, new EntityAIWander(this, 0.6D));
/* 112 */     this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
/* 113 */     this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
/* 114 */     this.tasks.addTask(11, new EntityAILookIdle(this));
/*     */     
/* 116 */     this.targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
/* 117 */     this.targetTasks.addTask(2, new thaumcraft.common.entities.ai.combat.AINearestAttackableTargetPech(this, EntityPlayer.class, true));
/*     */     
/* 119 */     if ((world != null) && (!world.isRemote))
/*     */     {
/* 121 */       setCombatTask();
/*     */     }
/*     */     
/* 124 */     this.equipmentDropChances[0] = 0.2F;
/*     */   }
/*     */   
/*     */   public void setCombatTask()
/*     */   {
/* 129 */     this.tasks.removeTask(this.aiMeleeAttack);
/* 130 */     this.tasks.removeTask(this.aiArrowAttack);
/* 131 */     this.tasks.removeTask(this.aiBlastAttack);
/* 132 */     ItemStack itemstack = getHeldItem();
/*     */     
/* 134 */     if ((itemstack != null) && (itemstack.getItem() == Items.bow))
/*     */     {
/* 136 */       this.tasks.addTask(2, this.aiArrowAttack);
/*     */ 
/*     */     }
/* 139 */     else if ((itemstack != null) && (itemstack.getItem() == ItemsTC.wand))
/*     */     {
/* 141 */       this.tasks.addTask(2, this.aiBlastAttack);
/*     */     }
/*     */     else
/*     */     {
/* 145 */       this.tasks.addTask(2, this.aiMeleeAttack);
/*     */     }
/*     */     
/* 148 */     if (isTamed()) {
/* 149 */       this.tasks.removeTask(this.aiAvoidPlayer);
/*     */     } else {
/* 151 */       this.tasks.addTask(4, this.aiAvoidPlayer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float f)
/*     */   {
/* 158 */     if (getPechType() == 2) {
/* 159 */       EntityArrow entityarrow = new EntityArrow(this.worldObj, this, entitylivingbase, 1.6F, 14 - this.worldObj.getDifficulty().getDifficultyId() * 4);
/*     */       
/* 161 */       int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
/*     */       
/* 163 */       int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
/*     */       
/* 165 */       entityarrow.setDamage(f * 2.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.getDifficulty().getDifficultyId() * 0.11F);
/*     */       
/*     */ 
/* 168 */       if (i > 0)
/*     */       {
/* 170 */         entityarrow.setDamage(entityarrow.getDamage() + i * 0.5D + 0.5D);
/*     */       }
/*     */       
/* 173 */       if (j > 0)
/*     */       {
/* 175 */         entityarrow.setKnockbackStrength(j);
/*     */       }
/*     */       
/* 178 */       playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
/* 179 */       this.worldObj.spawnEntityInWorld(entityarrow);
/*     */     }
/* 181 */     else if (getPechType() == 1) {
/* 182 */       EntityPechBlast blast = new EntityPechBlast(this.worldObj, this, 1, 0, this.rand.nextFloat() < 0.1F);
/* 183 */       double d0 = entitylivingbase.posX + entitylivingbase.motionX - this.posX;
/* 184 */       double d1 = entitylivingbase.posY + entitylivingbase.getEyeHeight() - 1.500000023841858D - this.posY;
/* 185 */       double d2 = entitylivingbase.posZ + entitylivingbase.motionZ - this.posZ;
/* 186 */       float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
/* 187 */       blast.setThrowableHeading(d0, d1 + f1 * 0.1F, d2, 1.5F, 4.0F);
/* 188 */       playSound("thaumcraft:ice", 0.4F, 1.0F + this.rand.nextFloat() * 0.1F);
/* 189 */       this.worldObj.spawnEntityInWorld(blast);
/*     */     }
/*     */     
/* 192 */     swingItem();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
/*     */   {
/* 199 */     super.setCurrentItemOrArmor(par1, par2ItemStack);
/*     */     
/* 201 */     if ((!this.worldObj.isRemote) && (par1 == 0))
/*     */     {
/* 203 */       this.updateAINextTick = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addRandomDrop()
/*     */   {
/* 213 */     super.addRandomDrop();
/* 214 */     switch (this.rand.nextInt(20)) {
/*     */     case 0: case 12: 
/* 216 */       ItemStack wand = new ItemStack(ItemsTC.wand);
/* 217 */       ItemStack focus = new ItemStack(ItemsTC.focusPech);
/* 218 */       ((IWand)wand.getItem()).setFocus(wand, focus);
/* 219 */       ((IWand)wand.getItem()).addVis(wand, Aspect.EARTH, 2 + this.rand.nextInt(6), true);
/* 220 */       ((IWand)wand.getItem()).addVis(wand, Aspect.ENTROPY, 2 + this.rand.nextInt(6), true);
/* 221 */       ((IWand)wand.getItem()).addVis(wand, Aspect.WATER, 2 + this.rand.nextInt(6), true);
/* 222 */       ((IWand)wand.getItem()).addVis(wand, Aspect.AIR, this.rand.nextInt(4), true);
/* 223 */       ((IWand)wand.getItem()).addVis(wand, Aspect.FIRE, this.rand.nextInt(4), true);
/* 224 */       ((IWand)wand.getItem()).addVis(wand, Aspect.ORDER, this.rand.nextInt(4), true);
/* 225 */       setCurrentItemOrArmor(0, wand);
/* 226 */       break;
/*     */     case 1: 
/* 228 */       setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword)); break;
/*     */     case 3: 
/* 230 */       setCurrentItemOrArmor(0, new ItemStack(Items.stone_axe)); break;
/*     */     case 5: 
/* 232 */       setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword)); break;
/*     */     case 6: 
/* 234 */       setCurrentItemOrArmor(0, new ItemStack(Items.iron_axe)); break;
/*     */     case 7: 
/* 236 */       setCurrentItemOrArmor(0, new ItemStack(Items.fishing_rod)); break;
/*     */     case 8: 
/* 238 */       setCurrentItemOrArmor(0, new ItemStack(Items.stone_pickaxe)); break;
/*     */     case 9: 
/* 240 */       setCurrentItemOrArmor(0, new ItemStack(Items.iron_pickaxe)); break;
/*     */     case 2: case 4: case 10: case 11: case 13: 
/* 242 */       setCurrentItemOrArmor(0, new ItemStack(Items.bow));
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance diff, IEntityLivingData data)
/*     */   {
/* 249 */     addRandomDrop();
/*     */     
/* 251 */     ItemStack itemstack = getHeldItem();
/* 252 */     if ((itemstack != null) && (itemstack.getItem() == ItemsTC.wand))
/*     */     {
/* 254 */       setPechType(1);
/* 255 */       this.equipmentDropChances[0] = 0.1F;
/*     */     }
/* 257 */     else if (itemstack != null)
/*     */     {
/* 259 */       if (itemstack.getItem() == Items.bow) setPechType(2);
/* 260 */       setEnchantmentBasedOnDifficulty(diff);
/*     */     }
/*     */     
/* 263 */     float f = diff.getClampedAdditionalDifficulty();
/* 264 */     setCanPickUpLoot(this.rand.nextFloat() < 0.75F * f);
/*     */     
/* 266 */     return super.onInitialSpawn(diff, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean getCanSpawnHere()
/*     */   {
/* 273 */     BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(new net.minecraft.util.BlockPos(this));
/* 274 */     boolean magicBiome = false;
/* 275 */     if (biome != null) {
/* 276 */       magicBiome = BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MAGICAL);
/*     */     }
/* 278 */     int count = 0;
/*     */     try {
/* 280 */       List l = this.worldObj.getEntitiesWithinAABB(EntityPech.class, getEntityBoundingBox().expand(16.0D, 16.0D, 16.0D));
/* 281 */       if (l != null) count = l.size();
/*     */     }
/*     */     catch (Exception e) {}
/* 284 */     if ((this.worldObj.provider.getDimensionId() != Config.overworldDim) && (biome.biomeID != Config.biomeMagicalForestID) && (biome.biomeID != Config.biomeEerieID))
/*     */     {
/* 286 */       magicBiome = false;
/*     */     }
/* 288 */     if (biome.biomeID == Config.biomeTaintID) { magicBiome = false;
/*     */     }
/* 290 */     return (count < 4) && (magicBiome) && (super.getCanSpawnHere());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public float getEyeHeight()
/*     */   {
/* 297 */     return this.height * 0.66F;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onLivingUpdate()
/*     */   {
/* 303 */     super.onLivingUpdate();
/*     */   }
/*     */   
/*     */   protected void entityInit()
/*     */   {
/* 308 */     super.entityInit();
/* 309 */     this.dataWatcher.addObject(13, new Byte((byte)0));
/* 310 */     this.dataWatcher.addObject(14, new Short((short)0));
/* 311 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public int getPechType()
/*     */   {
/* 316 */     return this.dataWatcher.getWatchableObjectByte(13);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getAnger()
/*     */   {
/* 324 */     return this.dataWatcher.getWatchableObjectShort(14);
/*     */   }
/*     */   
/*     */   public boolean isTamed()
/*     */   {
/* 329 */     return this.dataWatcher.getWatchableObjectByte(16) == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPechType(int par1)
/*     */   {
/* 337 */     this.dataWatcher.updateObject(13, Byte.valueOf((byte)par1));
/*     */   }
/*     */   
/*     */   public void setAnger(int par1)
/*     */   {
/* 342 */     this.dataWatcher.updateObject(14, Short.valueOf((short)par1));
/*     */   }
/*     */   
/*     */   public void setTamed(boolean par1)
/*     */   {
/* 347 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)(par1 ? 1 : 0)));
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/* 353 */     super.applyEntityAttributes();
/* 354 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
/* 355 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
/* 356 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 362 */     super.writeEntityToNBT(par1NBTTagCompound);
/*     */     
/* 364 */     par1NBTTagCompound.setByte("PechType", (byte)getPechType());
/*     */     
/* 366 */     par1NBTTagCompound.setShort("Anger", (short)getAnger());
/*     */     
/* 368 */     par1NBTTagCompound.setBoolean("Tamed", isTamed());
/*     */     
/*     */ 
/* 371 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 373 */     for (int i = 0; i < this.loot.length; i++)
/*     */     {
/* 375 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */       
/* 377 */       if (this.loot[i] != null)
/*     */       {
/* 379 */         this.loot[i].writeToNBT(nbttagcompound1);
/*     */       }
/*     */       
/* 382 */       nbttaglist.appendTag(nbttagcompound1);
/*     */     }
/* 384 */     par1NBTTagCompound.setTag("Loot", nbttaglist);
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 390 */     super.readEntityFromNBT(par1NBTTagCompound);
/*     */     
/* 392 */     if (par1NBTTagCompound.hasKey("PechType"))
/*     */     {
/* 394 */       byte b0 = par1NBTTagCompound.getByte("PechType");
/* 395 */       setPechType(b0);
/*     */     }
/*     */     
/* 398 */     setAnger(par1NBTTagCompound.getShort("Anger"));
/*     */     
/* 400 */     setTamed(par1NBTTagCompound.getBoolean("Tamed"));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 405 */     if (par1NBTTagCompound.hasKey("Loot"))
/*     */     {
/* 407 */       NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Loot", 10);
/* 408 */       for (int i = 0; i < this.loot.length; i++)
/*     */       {
/* 410 */         this.loot[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
/*     */       }
/*     */     }
/*     */     
/* 414 */     this.updateAINextTick = true;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canDespawn()
/*     */   {
/*     */     try
/*     */     {
/* 422 */       if (this.loot == null) return true;
/* 423 */       int q = 0;
/* 424 */       for (ItemStack is : this.loot) {
/* 425 */         if ((is != null) && (is.stackSize > 0)) q++;
/*     */       }
/* 427 */       return q < 5; } catch (Exception e) {}
/* 428 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean allowLeashing()
/*     */   {
/* 434 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void dropFewItems(boolean flag, int i)
/*     */   {
/* 443 */     for (int a = 0; a < this.loot.length; a++) {
/* 444 */       if ((this.loot[a] != null) && (this.worldObj.rand.nextFloat() < 0.33F)) {
/* 445 */         entityDropItem(this.loot[a].copy(), 1.5F);
/*     */       }
/* 447 */       else if (this.worldObj.rand.nextFloat() > 0.33F) {
/* 448 */         entityDropItem(new ItemStack(ItemsTC.coin), 1.5F);
/*     */       }
/*     */     }
/*     */     
/* 452 */     Aspect[] aspects = (Aspect[])Aspect.getPrimalAspects().toArray(new Aspect[0]);
/* 453 */     for (int a = 0; a < 1 + i; a++) {
/* 454 */       if (this.rand.nextBoolean()) {
/* 455 */         ItemStack is = new ItemStack(ItemsTC.wispyEssence);
/* 456 */         ((IEssentiaContainerItem)is.getItem()).setAspects(is, new AspectList().add(aspects[this.rand.nextInt(aspects.length)], 2));
/* 457 */         entityDropItem(is, 1.5F);
/*     */       }
/*     */     }
/* 460 */     if (this.worldObj.rand.nextInt(50) < 1 + i) { entityDropItem(new ItemStack(ItemsTC.knowledgeFragment), 1.5F);
/*     */     }
/* 462 */     super.dropFewItems(flag, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public void handleStatusUpdate(byte par1)
/*     */   {
/* 470 */     if (par1 == 16)
/*     */     {
/* 472 */       this.mumble = 3.1415927F;
/*     */ 
/*     */     }
/* 475 */     else if (par1 == 17)
/*     */     {
/* 477 */       this.mumble = 6.2831855F;
/*     */ 
/*     */     }
/* 480 */     else if (par1 == 18)
/*     */     {
/* 482 */       for (int i = 0; i < 5; i++)
/*     */       {
/* 484 */         double d0 = this.rand.nextGaussian() * 0.02D;
/* 485 */         double d1 = this.rand.nextGaussian() * 0.02D;
/* 486 */         double d2 = this.rand.nextGaussian() * 0.02D;
/* 487 */         this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2, new int[0]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 493 */     if (par1 == 19)
/*     */     {
/* 495 */       for (int i = 0; i < 5; i++)
/*     */       {
/* 497 */         double d0 = this.rand.nextGaussian() * 0.02D;
/* 498 */         double d1 = this.rand.nextGaussian() * 0.02D;
/* 499 */         double d2 = this.rand.nextGaussian() * 0.02D;
/* 500 */         this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2, new int[0]);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 505 */       this.mumble = 6.2831855F;
/*     */     }
/*     */     else
/*     */     {
/* 509 */       super.handleStatusUpdate(par1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 515 */   public float mumble = 0.0F;
/*     */   
/*     */   public void playLivingSound()
/*     */   {
/* 519 */     if (!this.worldObj.isRemote) {
/* 520 */       if (this.rand.nextInt(3) == 0) {
/* 521 */         List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D));
/*     */         
/* 523 */         for (int i = 0; i < list.size(); i++)
/*     */         {
/* 525 */           Entity entity1 = (Entity)list.get(i);
/*     */           
/* 527 */           if ((entity1 instanceof EntityPech))
/*     */           {
/* 529 */             this.worldObj.setEntityState(this, (byte)17);
/* 530 */             playSound("thaumcraft:pech_trade", getSoundVolume(), getSoundPitch());
/* 531 */             return;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 536 */       this.worldObj.setEntityState(this, (byte)16);
/*     */     }
/* 538 */     super.playLivingSound();
/*     */   }
/*     */   
/*     */   public int getTalkInterval()
/*     */   {
/* 543 */     return 120;
/*     */   }
/*     */   
/*     */ 
/*     */   protected float getSoundVolume()
/*     */   {
/* 549 */     return 0.4F;
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 555 */     return "thaumcraft:pech_idle";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 561 */     return "thaumcraft:pech_hit";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/* 567 */     return "thaumcraft:pech_death";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void becomeAngryAt(Entity par1Entity)
/*     */   {
/* 574 */     if (getAnger() <= 0) {
/* 575 */       this.worldObj.setEntityState(this, (byte)19);
/* 576 */       playSound("thaumcraft:pech_charge", getSoundVolume(), getSoundPitch());
/*     */     }
/* 578 */     setAttackTarget((EntityLivingBase)par1Entity);
/* 579 */     setAnger(400 + this.rand.nextInt(400));
/* 580 */     setTamed(false);
/*     */     
/* 582 */     this.updateAINextTick = true;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTotalArmorValue()
/*     */   {
/* 588 */     int i = super.getTotalArmorValue() + 2;
/*     */     
/* 590 */     if (i > 20)
/*     */     {
/* 592 */       i = 20;
/*     */     }
/*     */     
/* 595 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource damSource, float par2)
/*     */   {
/* 601 */     if (isEntityInvulnerable(damSource))
/*     */     {
/* 603 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 607 */     Entity entity = damSource.getEntity();
/*     */     
/* 609 */     if ((entity instanceof EntityPlayer))
/*     */     {
/* 611 */       List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(32.0D, 16.0D, 32.0D));
/*     */       
/* 613 */       for (int i = 0; i < list.size(); i++)
/*     */       {
/* 615 */         Entity entity1 = (Entity)list.get(i);
/*     */         
/* 617 */         if ((entity1 instanceof EntityPech))
/*     */         {
/* 619 */           EntityPech entitypech = (EntityPech)entity1;
/* 620 */           entitypech.becomeAngryAt(entity);
/*     */         }
/*     */       }
/*     */       
/* 624 */       becomeAngryAt(entity);
/*     */     }
/*     */     
/* 627 */     return super.attackEntityFrom(damSource, par2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 633 */   int chargecount = 0;
/*     */   
/*     */   public void onUpdate()
/*     */   {
/* 637 */     if (this.mumble > 0.0F) this.mumble *= 0.75F;
/* 638 */     if (getAnger() > 0) { setAnger(getAnger() - 1);
/*     */     }
/* 640 */     if ((getAnger() > 0) && (getAttackTarget() != null)) {
/* 641 */       if (this.chargecount > 0) this.chargecount -= 1;
/* 642 */       if (this.chargecount == 0) {
/* 643 */         this.chargecount = 100;
/* 644 */         playSound("thaumcraft:pech_charge", getSoundVolume(), getSoundPitch());
/*     */       }
/* 646 */       this.worldObj.setEntityState(this, (byte)17);
/*     */     }
/*     */     
/* 649 */     if ((this.worldObj.isRemote) && (this.rand.nextInt(15) == 0) && (getAnger() > 0)) {
/* 650 */       double d0 = this.rand.nextGaussian() * 0.02D;
/* 651 */       double d1 = this.rand.nextGaussian() * 0.02D;
/* 652 */       double d2 = this.rand.nextGaussian() * 0.02D;
/* 653 */       this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2, new int[0]);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 659 */     if ((this.worldObj.isRemote) && (this.rand.nextInt(25) == 0) && (isTamed())) {
/* 660 */       double d0 = this.rand.nextGaussian() * 0.02D;
/* 661 */       double d1 = this.rand.nextGaussian() * 0.02D;
/* 662 */       double d2 = this.rand.nextGaussian() * 0.02D;
/* 663 */       this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2, new int[0]);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 669 */     super.onUpdate();
/*     */   }
/*     */   
/*     */ 
/*     */   public void updateAITasks()
/*     */   {
/* 675 */     if (this.updateAINextTick) {
/* 676 */       this.updateAINextTick = false;
/* 677 */       setCombatTask();
/*     */     }
/*     */     
/* 680 */     super.updateAITasks();
/* 681 */     if (this.ticksExisted % 40 == 0)
/*     */     {
/* 683 */       heal(1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canPickup(ItemStack entityItem)
/*     */   {
/* 690 */     if (entityItem == null) return false;
/* 691 */     if ((!isTamed()) && (valuedItems.containsKey(Integer.valueOf(Item.getIdFromItem(entityItem.getItem()))))) {
/* 692 */       return true;
/*     */     }
/* 694 */     for (int a = 0; a < this.loot.length; a++) {
/* 695 */       if ((this.loot[a] != null) && (this.loot[a].stackSize <= 0)) this.loot[a] = null;
/* 696 */       if (this.loot[a] == null) return true;
/* 697 */       if ((InventoryUtils.areItemStacksEqualStrict(entityItem, this.loot[a])) && (entityItem.stackSize + this.loot[a].stackSize <= this.loot[a].getMaxStackSize()))
/* 698 */         return true;
/*     */     }
/* 700 */     return false;
/*     */   }
/*     */   
/*     */   public ItemStack pickupItem(ItemStack entityItem) {
/* 704 */     if (entityItem == null) { return entityItem;
/*     */     }
/* 706 */     if ((!isTamed()) && (isValued(entityItem)))
/*     */     {
/* 708 */       if (this.rand.nextInt(10) < getValue(entityItem)) {
/* 709 */         setTamed(true);
/* 710 */         this.updateAINextTick = true;
/* 711 */         this.worldObj.setEntityState(this, (byte)18);
/*     */       }
/*     */       
/* 714 */       entityItem.stackSize -= 1;
/*     */       
/* 716 */       if (entityItem.stackSize <= 0) {
/* 717 */         return null;
/*     */       }
/* 719 */       return entityItem;
/*     */     }
/*     */     
/*     */ 
/* 723 */     for (int a = 0; a < this.loot.length; a++) {
/* 724 */       if ((this.loot[a] != null) && (this.loot[a].stackSize <= 0)) this.loot[a] = null;
/* 725 */       if ((entityItem != null) && (entityItem.stackSize > 0) && (this.loot[a] != null) && (this.loot[a].stackSize < this.loot[a].getMaxStackSize()) && (InventoryUtils.areItemStacksEqualStrict(entityItem, this.loot[a])))
/*     */       {
/*     */ 
/*     */ 
/* 729 */         if (entityItem.stackSize + this.loot[a].stackSize <= this.loot[a].getMaxStackSize()) {
/* 730 */           this.loot[a].stackSize += entityItem.stackSize;
/* 731 */           return null;
/*     */         }
/* 733 */         int sz = Math.min(entityItem.stackSize, this.loot[a].getMaxStackSize() - this.loot[a].stackSize);
/* 734 */         this.loot[a].stackSize += sz;
/* 735 */         entityItem.stackSize -= sz;
/*     */       }
/*     */       
/* 738 */       if ((entityItem != null) && (entityItem.stackSize <= 0)) { entityItem = null;
/*     */       }
/*     */     }
/*     */     
/* 742 */     for (int a = 0; a < this.loot.length; a++) {
/* 743 */       if ((this.loot[a] != null) && (this.loot[a].stackSize <= 0)) this.loot[a] = null;
/* 744 */       if ((entityItem != null) && (entityItem.stackSize > 0) && (this.loot[a] == null)) {
/* 745 */         this.loot[a] = entityItem.copy();
/* 746 */         return null;
/*     */       }
/*     */     }
/* 749 */     if ((entityItem != null) && (entityItem.stackSize <= 0)) entityItem = null;
/* 750 */     return entityItem;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean interact(EntityPlayer player)
/*     */   {
/* 758 */     if ((player.isSneaking()) || ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof ItemNameTag))))
/* 759 */       return false;
/* 760 */     if (isTamed()) {
/* 761 */       if (!this.worldObj.isRemote) {
/* 762 */         player.openGui(Thaumcraft.instance, 1, this.worldObj, getEntityId(), 0, 0);
/*     */       }
/* 764 */       return true;
/*     */     }
/*     */     
/* 767 */     return super.interact(player);
/*     */   }
/*     */   
/*     */   public boolean isValued(ItemStack item) {
/* 771 */     if (item == null) return false;
/* 772 */     boolean value = valuedItems.containsKey(Integer.valueOf(Item.getIdFromItem(item.getItem())));
/* 773 */     if (!value) {
/* 774 */       AspectList al = ThaumcraftCraftingManager.getObjectTags(item);
/* 775 */       if (al.getAmount(Aspect.DESIRE) > 1)
/* 776 */         value = true;
/*     */     }
/* 778 */     return value;
/*     */   }
/*     */   
/*     */   public int getValue(ItemStack item) {
/* 782 */     if (item == null) return 0;
/* 783 */     int value = valuedItems.containsKey(Integer.valueOf(Item.getIdFromItem(item.getItem()))) ? ((Integer)valuedItems.get(Integer.valueOf(Item.getIdFromItem(item.getItem())))).intValue() : 0;
/* 784 */     if (value == 0) {
/* 785 */       AspectList al = ThaumcraftCraftingManager.getObjectTags(item);
/* 786 */       value = Math.min(32, al.getAmount(Aspect.DESIRE));
/*     */     }
/* 788 */     return value;
/*     */   }
/*     */   
/* 791 */   static HashMap<Integer, Integer> valuedItems = new HashMap();
/* 792 */   public static HashMap<Integer, ArrayList<List>> tradeInventory = new HashMap();
/*     */   
/*     */   static {
/* 795 */     valuedItems.put(Integer.valueOf(Item.getIdFromItem(Items.gold_ingot)), Integer.valueOf(2));
/* 796 */     valuedItems.put(Integer.valueOf(Item.getIdFromItem(Items.golden_apple)), Integer.valueOf(2));
/* 797 */     valuedItems.put(Integer.valueOf(Item.getIdFromItem(Items.ender_pearl)), Integer.valueOf(3));
/* 798 */     valuedItems.put(Integer.valueOf(Item.getIdFromItem(Items.diamond)), Integer.valueOf(4));
/* 799 */     valuedItems.put(Integer.valueOf(Item.getIdFromItem(Items.emerald)), Integer.valueOf(5));
/*     */     
/*     */ 
/*     */ 
/* 803 */     ArrayList<List> forInv = new ArrayList();
/*     */     
/* 805 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(1), new ItemStack(ItemsTC.clusters, 1, 0) }));
/* 806 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(1), new ItemStack(ItemsTC.clusters, 1, 1) }));
/* 807 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(1), new ItemStack(ItemsTC.clusters, 1, 6) }));
/* 808 */     if (Config.foundCopperIngot)
/* 809 */       forInv.add(Arrays.asList(new Object[] { Integer.valueOf(1), new ItemStack(ItemsTC.clusters, 1, 2) }));
/* 810 */     if (Config.foundTinIngot)
/* 811 */       forInv.add(Arrays.asList(new Object[] { Integer.valueOf(1), new ItemStack(ItemsTC.clusters, 1, 3) }));
/* 812 */     if (Config.foundSilverIngot)
/* 813 */       forInv.add(Arrays.asList(new Object[] { Integer.valueOf(1), new ItemStack(ItemsTC.clusters, 1, 4) }));
/* 814 */     if (Config.foundLeadIngot)
/* 815 */       forInv.add(Arrays.asList(new Object[] { Integer.valueOf(1), new ItemStack(ItemsTC.clusters, 1, 5) }));
/* 816 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(2), new ItemStack(Items.blaze_rod) }));
/* 817 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(2), new ItemStack(BlocksTC.sapling, 1, 0) }));
/* 818 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(2), new ItemStack(Items.potionitem, 1, 8201) }));
/* 819 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(2), new ItemStack(Items.potionitem, 1, 8194) }));
/* 820 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.experience_bottle) }));
/* 821 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.experience_bottle) }));
/* 822 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(ItemsTC.knowledgeFragment) }));
/* 823 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.golden_apple, 1, 0) }));
/* 824 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.potionitem, 1, 8265) }));
/* 825 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.potionitem, 1, 8262) }));
/* 826 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(4), new ItemStack(ItemsTC.thaumiumPick) }));
/* 827 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(4), new ItemStack(ItemsTC.thaumiumAxe) }));
/* 828 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(4), new ItemStack(ItemsTC.thaumiumHoe) }));
/* 829 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(5), new ItemStack(Items.golden_apple, 1, 1) }));
/* 830 */     forInv.add(Arrays.asList(new Object[] { Integer.valueOf(5), new ItemStack(BlocksTC.sapling, 1, 1) }));
/* 831 */     tradeInventory.put(Integer.valueOf(0), forInv);
/*     */     
/*     */ 
/* 834 */     ArrayList<List> forMag = new ArrayList();
/*     */     
/* 836 */     for (int a = 0; a < 6; a++)
/* 837 */       forMag.add(Arrays.asList(new Object[] { Integer.valueOf(1), new ItemStack(ItemsTC.shard, 1, a) }));
/* 838 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(1), new ItemStack(ItemsTC.knowledgeFragment) }));
/* 839 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(2), new ItemStack(ItemsTC.knowledgeFragment) }));
/* 840 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(2), new ItemStack(Items.potionitem, 1, 8193) }));
/* 841 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(2), new ItemStack(Items.potionitem, 1, 8261) }));
/* 842 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.experience_bottle) }));
/* 843 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.experience_bottle) }));
/* 844 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(3), Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Config.enchHaste, 1)) }));
/* 845 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.golden_apple, 1, 0) }));
/* 846 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.potionitem, 1, 8225) }));
/* 847 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.potionitem, 1, 8229) }));
/* 848 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(4), new ItemStack(ItemsTC.clothBoots) }));
/* 849 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(4), new ItemStack(ItemsTC.clothChest) }));
/* 850 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(4), new ItemStack(ItemsTC.clothLegs) }));
/* 851 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(5), new ItemStack(Items.golden_apple, 1, 1) }));
/* 852 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(5), Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Config.enchRepair, 1)) }));
/* 853 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(5), new ItemStack(ItemsTC.focusPech) }));
/* 854 */     forMag.add(Arrays.asList(new Object[] { Integer.valueOf(5), new ItemStack(ItemsTC.amuletVis, 1, 0) }));
/* 855 */     tradeInventory.put(Integer.valueOf(1), forMag);
/*     */     
/*     */ 
/* 858 */     ArrayList<List> forArc = new ArrayList();
/*     */     
/* 860 */     for (int a = 0; a < 15; a++)
/* 861 */       forArc.add(Arrays.asList(new Object[] { Integer.valueOf(1), new ItemStack(BlocksTC.candle, 1, a) }));
/* 862 */     for (int a = 0; a < 6; a++)
/* 863 */       forArc.add(Arrays.asList(new Object[] { Integer.valueOf(2), new ItemStack(ItemsTC.primalArrows, 8, a) }));
/* 864 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(2), new ItemStack(Items.ghast_tear) }));
/* 865 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(2), new ItemStack(Items.potionitem, 1, 8194) }));
/* 866 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(2), new ItemStack(Items.potionitem, 1, 8201) }));
/* 867 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(2), Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Enchantment.power, 1)) }));
/* 868 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.experience_bottle) }));
/* 869 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.experience_bottle) }));
/* 870 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(ItemsTC.knowledgeFragment) }));
/* 871 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.potionitem, 1, 8270) }));
/* 872 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.potionitem, 1, 8225) }));
/* 873 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(3), new ItemStack(Items.golden_apple, 1, 0) }));
/* 874 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(5), new ItemStack(Items.golden_apple, 1, 1) }));
/* 875 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(4), new ItemStack(ItemsTC.boneBow) }));
/* 876 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(5), new ItemStack(ItemsTC.ringRunic, 1, 0) }));
/* 877 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(5), Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Enchantment.flame, 1)) }));
/* 878 */     forArc.add(Arrays.asList(new Object[] { Integer.valueOf(5), Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Enchantment.infinity, 1)) }));
/* 879 */     tradeInventory.put(Integer.valueOf(2), forArc);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\EntityPech.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */