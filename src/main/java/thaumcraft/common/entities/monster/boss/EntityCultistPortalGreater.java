/*     */ package thaumcraft.common.entities.monster.boss;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultist;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultistCleric;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXBlockArc;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ import thaumcraft.common.tiles.misc.TileBanner;
/*     */ 
/*     */ public class EntityCultistPortalGreater extends EntityMob implements net.minecraft.entity.boss.IBossDisplayData
/*     */ {
/*     */   public EntityCultistPortalGreater(World par1World)
/*     */   {
/*  38 */     super(par1World);
/*  39 */     this.isImmuneToFire = true;
/*  40 */     this.experienceValue = 30;
/*  41 */     setSize(1.5F, 3.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTotalArmorValue()
/*     */   {
/*  47 */     return 5;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  53 */     super.entityInit();
/*     */   }
/*     */   
/*  56 */   int stage = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound nbt)
/*     */   {
/*  64 */     super.writeEntityToNBT(nbt);
/*  65 */     nbt.setInteger("stage", this.stage);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound nbt)
/*     */   {
/*  75 */     super.readEntityFromNBT(nbt);
/*  76 */     this.stage = nbt.getInteger("stage");
/*     */   }
/*     */   
/*     */ 
/*     */   protected void applyEntityAttributes()
/*     */   {
/*  82 */     super.applyEntityAttributes();
/*  83 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
/*  84 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.0D);
/*  85 */     getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/*  91 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canBePushed()
/*     */   {
/*  96 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void moveEntity(double par1, double par3, double par5) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onLivingUpdate() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isInRangeToRenderDist(double par1)
/*     */   {
/* 113 */     return par1 < 4096.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getBrightnessForRender(float par1)
/*     */   {
/* 121 */     return 15728880;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getBrightness(float par1)
/*     */   {
/* 130 */     return 1.0F;
/*     */   }
/*     */   
/* 133 */   int stagecounter = 200;
/*     */   
/*     */   public void onUpdate()
/*     */   {
/* 137 */     super.onUpdate();
/* 138 */     if (!this.worldObj.isRemote) {
/* 139 */       if (this.stagecounter > 0) {
/* 140 */         this.stagecounter -= 1;
/* 141 */         if ((this.stagecounter == 160) && (this.stage == 0)) {
/* 142 */           this.worldObj.setEntityState(this, (byte)16);
/* 143 */           for (EnumFacing dir : EnumFacing.HORIZONTALS) {
/* 144 */             BlockPos bp = new BlockPos((int)this.posX - dir.getFrontOffsetX() * 6, (int)this.posY, (int)this.posZ + dir.getFrontOffsetZ() * 6);
/* 145 */             this.worldObj.setBlockState(bp, BlocksTC.banner.getStateFromMeta(1), 3);
/* 146 */             TileEntity te = this.worldObj.getTileEntity(new BlockPos((int)this.posX - dir.getFrontOffsetX() * 6, (int)this.posY, (int)this.posZ + dir.getFrontOffsetZ() * 6));
/* 147 */             if ((te != null) && ((te instanceof TileBanner))) {
/* 148 */               int face = 0;
/*     */               
/* 150 */               switch (dir.ordinal()) {
/* 151 */               case 2:  face = 8; break;
/* 152 */               case 3:  face = 0; break;
/* 153 */               case 4:  face = 12; break;
/* 154 */               case 5:  face = 4; break;
/*     */               }
/* 156 */               ((TileBanner)te).setBannerFacing((byte)face);
/* 157 */               PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockArc(new BlockPos((int)this.posX - dir.getFrontOffsetX() * 6, (int)this.posY, (int)this.posZ + dir.getFrontOffsetZ() * 6), this, 0.5F + this.rand.nextFloat() * 0.2F, 0.0F, 0.0F), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 32.0D));
/*     */               
/*     */ 
/* 160 */               playSound("thaumcraft:wandfail", 1.0F, 1.0F);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 165 */         if ((this.stagecounter > 20) && (this.stagecounter < 150) && (this.stage == 0) && (this.stagecounter % 13 == 0)) {
/* 166 */           int a = (int)this.posX + this.rand.nextInt(5) - this.rand.nextInt(5);
/* 167 */           int b = (int)this.posZ + this.rand.nextInt(5) - this.rand.nextInt(5);
/* 168 */           BlockPos bp = new BlockPos(a, (int)this.posY, b);
/* 169 */           if ((a != (int)this.posX) && (b != (int)this.posZ) && (this.worldObj.isAirBlock(bp))) {
/* 170 */             this.worldObj.setEntityState(this, (byte)16);
/* 171 */             float rr = this.worldObj.rand.nextFloat();
/* 172 */             int md = rr < 0.2F ? 1 : rr < 0.05F ? 2 : 0;
/* 173 */             this.worldObj.setBlockState(bp, BlocksTC.lootCrate.getStateFromMeta(md));
/* 174 */             PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockArc(new BlockPos(a, (int)this.posY, b), this, 0.5F + this.rand.nextFloat() * 0.2F, 0.0F, 0.0F), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 32.0D));
/*     */             
/*     */ 
/* 177 */             playSound("thaumcraft:wandfail", 1.0F, 1.0F);
/*     */           }
/*     */         }
/*     */       }
/* 181 */       else if (this.worldObj.getClosestPlayerToEntity(this, 48.0D) != null)
/*     */       {
/* 183 */         this.worldObj.setEntityState(this, (byte)16);
/*     */         
/* 185 */         switch (this.stage) {
/*     */         case 0: case 1: case 2: case 3: case 4: 
/* 187 */           this.stagecounter = (15 + this.rand.nextInt(10 - this.stage) - this.stage);
/* 188 */           spawnMinions();
/* 189 */           break;
/*     */         case 12: 
/* 191 */           this.stagecounter = (50 + getTiming() * 2 + this.rand.nextInt(50));
/* 192 */           spawnBoss();
/* 193 */           break;
/*     */         case 5: case 6: case 7: case 8: case 9: case 10: case 11: default: 
/* 195 */           int t = getTiming();
/* 196 */           this.stagecounter = (t + this.rand.nextInt(5 + t / 3));
/* 197 */           spawnMinions();
/*     */         }
/*     */         
/*     */         
/* 201 */         this.stage += 1;
/*     */       }
/*     */       else {
/* 204 */         this.stagecounter = (30 + this.rand.nextInt(30));
/*     */       }
/*     */       
/* 207 */       if (this.stage < 12) { heal(1.0F);
/*     */       }
/*     */     }
/*     */     
/* 211 */     if (this.pulse > 0) this.pulse -= 1;
/*     */   }
/*     */   
/*     */   int getTiming() {
/* 215 */     List<Entity> l = EntityUtils.getEntitiesInRange(this.worldObj, this.posX, this.posY, this.posZ, this, EntityCultist.class, 32.0D);
/* 216 */     return l.size() * 20;
/*     */   }
/*     */   
/*     */   void spawnMinions() {
/* 220 */     EntityCultist cultist = null;
/* 221 */     if (this.rand.nextFloat() > 0.33D) {
/* 222 */       cultist = new thaumcraft.common.entities.monster.cult.EntityCultistKnight(this.worldObj);
/*     */     } else {
/* 224 */       cultist = new EntityCultistCleric(this.worldObj);
/*     */     }
/* 226 */     cultist.setPosition(this.posX + this.rand.nextFloat() - this.rand.nextFloat(), this.posY + 0.25D, this.posZ + this.rand.nextFloat() - this.rand.nextFloat());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 231 */     cultist.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(cultist.getPosition())), (IEntityLivingData)null);
/*     */     
/* 233 */     cultist.setHomePosAndDistance(getPosition(), 32);
/* 234 */     this.worldObj.spawnEntityInWorld(cultist);
/* 235 */     cultist.spawnExplosionParticle();
/* 236 */     cultist.playSound("thaumcraft:wandfail", 1.0F, 1.0F);
/*     */     
/* 238 */     if (this.stage > 12) {
/* 239 */       attackEntityFrom(DamageSource.outOfWorld, 5 + this.rand.nextInt(5));
/*     */     }
/*     */   }
/*     */   
/*     */   void spawnBoss() {
/* 244 */     EntityCultistLeader cultist = new EntityCultistLeader(this.worldObj);
/* 245 */     cultist.setPosition(this.posX + this.rand.nextFloat() - this.rand.nextFloat(), this.posY + 0.25D, this.posZ + this.rand.nextFloat() - this.rand.nextFloat());
/*     */     
/*     */ 
/*     */ 
/* 249 */     cultist.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(cultist.getPosition())), (IEntityLivingData)null);
/* 250 */     cultist.setHomePosAndDistance(getPosition(), 32);
/*     */     
/* 252 */     this.worldObj.spawnEntityInWorld(cultist);
/* 253 */     cultist.spawnExplosionParticle();
/* 254 */     cultist.playSound("thaumcraft:wandfail", 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer p)
/*     */   {
/* 259 */     if ((getDistanceSqToEntity(p) < 3.0D) && (p.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this), 8.0F)))
/*     */     {
/* 261 */       playSound("thaumcraft:zap", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected float getSoundVolume()
/*     */   {
/* 268 */     return 0.75F;
/*     */   }
/*     */   
/*     */   public int getTalkInterval()
/*     */   {
/* 273 */     return 540;
/*     */   }
/*     */   
/*     */   protected String getLivingSound()
/*     */   {
/* 278 */     return "thaumcraft:monolith";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 284 */     return "thaumcraft:zap";
/*     */   }
/*     */   
/*     */   protected String getDeathSound()
/*     */   {
/* 289 */     return "thaumcraft:shock";
/*     */   }
/*     */   
/*     */ 
/*     */   protected Item getDropItem()
/*     */   {
/* 295 */     return Item.getItemById(0);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void dropFewItems(boolean flag, int fortune)
/*     */   {
/* 301 */     EntityUtils.entityDropSpecialItem(this, new net.minecraft.item.ItemStack(ItemsTC.primordialPearl, 1 + this.rand.nextInt(2)), this.height / 2.0F);
/*     */   }
/*     */   
/* 304 */   public int pulse = 0;
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void handleStatusUpdate(byte msg)
/*     */   {
/* 310 */     if (msg == 16)
/*     */     {
/* 312 */       this.pulse = 10;
/*     */     }
/*     */     else
/*     */     {
/* 316 */       super.handleStatusUpdate(msg);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void addPotionEffect(PotionEffect p_70690_1_) {}
/*     */   
/*     */ 
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */   
/*     */ 
/*     */   public void onDeath(DamageSource p_70645_1_)
/*     */   {
/* 329 */     if (!this.worldObj.isRemote) {
/* 330 */       this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 2.0F, false, false);
/*     */     }
/*     */     
/* 333 */     super.onDeath(p_70645_1_);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\boss\EntityCultistPortalGreater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */