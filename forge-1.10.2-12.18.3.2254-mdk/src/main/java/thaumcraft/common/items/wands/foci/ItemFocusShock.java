/*     */ package thaumcraft.common.items.wands.foci;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.wands.FocusUpgradeType;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.api.wands.ItemFocusBasic.WandFocusAnimation;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.projectile.EntityShockOrb;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ public class ItemFocusShock extends ItemFocusBasic
/*     */ {
/*     */   public ItemFocusShock()
/*     */   {
/*  33 */     super("shock", 10466239);
/*     */   }
/*     */   
/*  36 */   private static final AspectList costBase = new AspectList().add(Aspect.AIR, 8);
/*  37 */   private static final AspectList costChain = new AspectList().add(Aspect.AIR, 8).add(Aspect.WATER, 8);
/*  38 */   private static final AspectList costGround = new AspectList().add(Aspect.AIR, 8).add(Aspect.EARTH, 8);
/*     */   
/*     */   public boolean canBePlacedInTurret()
/*     */   {
/*  42 */     return true;
/*     */   }
/*     */   
/*     */   public float getTurretCorrection(ItemStack focusstack)
/*     */   {
/*  47 */     return isUpgradedWith(focusstack, earthshock) ? 5.0F : 0.0F;
/*     */   }
/*     */   
/*     */   public float getTurretRange(ItemStack focusstack)
/*     */   {
/*  52 */     return 20.0F;
/*     */   }
/*     */   
/*     */   public AspectList getVisCost(ItemStack itemstack)
/*     */   {
/*  57 */     return isUpgradedWith(itemstack, earthshock) ? costGround : isUpgradedWith(itemstack, chainlightning) ? costChain : costBase;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getActivationCooldown(ItemStack focusstack)
/*     */   {
/*  63 */     return isUpgradedWith(focusstack, earthshock) ? 1000 : isUpgradedWith(focusstack, chainlightning) ? 500 : 250;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack itemstack)
/*     */   {
/*  70 */     return isUpgradedWith(itemstack, earthshock) ? ItemFocusBasic.WandFocusAnimation.WAVE : ItemFocusBasic.WandFocusAnimation.CHARGE;
/*     */   }
/*     */   
/*     */ 
/*     */   public static void shootLightning(World world, EntityLivingBase entityplayer, double xx, double yy, double zz, boolean offset)
/*     */   {
/*  76 */     double px = entityplayer.posX;
/*  77 */     double py = entityplayer.getEntityBoundingBox().minY + entityplayer.height / 2.0F + 0.25D;
/*  78 */     double pz = entityplayer.posZ;
/*  79 */     px += -MathHelper.cos(entityplayer.rotationYaw / 180.0F * 3.141593F) * 0.06F;
/*  80 */     py += -0.05999999865889549D;
/*  81 */     pz += -MathHelper.sin(entityplayer.rotationYaw / 180.0F * 3.141593F) * 0.06F;
/*  82 */     Vec3 vec3d = entityplayer.getLook(1.0F);
/*  83 */     px += vec3d.xCoord * 0.5D;
/*  84 */     py += vec3d.yCoord * 0.5D;
/*  85 */     pz += vec3d.zCoord * 0.5D;
/*  86 */     Thaumcraft.proxy.getFX().arcBolt(px, py, pz, xx, yy, zz, 0.5F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onFocusActivation(ItemStack itemstack, World world, EntityLivingBase p, MovingObjectPosition movingobjectposition, int count)
/*     */   {
/*  93 */     IWand wand = (IWand)itemstack.getItem();
/*  94 */     if (isUpgradedWith(wand.getFocusStack(itemstack), earthshock)) {
/*  95 */       if (!world.isRemote) {
/*  96 */         EntityShockOrb orb = new EntityShockOrb(world, p);
/*  97 */         orb.area += getUpgradeLevel(wand.getFocusStack(itemstack), FocusUpgradeType.enlarge) * 2; EntityShockOrb 
/*  98 */           tmp74_72 = orb;tmp74_72.damage = ((int)(tmp74_72.damage + wand.getFocusPotency(itemstack) * 1.33D));
/*  99 */         world.spawnEntityInWorld(orb);
/* 100 */         world.playSoundAtEntity(orb, "thaumcraft:zap", 1.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F);
/*     */       }
/* 102 */       p.swingItem();
/*     */     } else {
/* 104 */       doLightningBolt(itemstack, p, count);
/*     */     }
/* 106 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void doLightningBolt(ItemStack stack, EntityLivingBase p, int count)
/*     */   {
/* 112 */     IWand wand = (IWand)stack.getItem();
/*     */     
/* 114 */     int potency = wand.getFocusPotency(stack);
/*     */     
/* 116 */     Entity pointedEntity = EntityUtils.getPointedEntity(p.worldObj, p, 0.0D, 20.0D, 1.1F);
/*     */     
/* 118 */     if (p.worldObj.isRemote) {
/* 119 */       MovingObjectPosition mop = thaumcraft.common.lib.utils.BlockUtils.getTargetBlock(p.worldObj, p, false);
/* 120 */       Vec3 v = p.getLook(2.0F);
/* 121 */       double px = p.posX + v.xCoord * 10.0D;
/* 122 */       double py = p.getEntityBoundingBox().minY + 0.25D + p.height / 2.0F + v.yCoord * 10.0D;
/* 123 */       double pz = p.posZ + v.zCoord * 10.0D;
/* 124 */       if (mop != null) {
/* 125 */         px = mop.hitVec.xCoord;
/* 126 */         py = mop.hitVec.yCoord;
/* 127 */         pz = mop.hitVec.zCoord;
/* 128 */         for (int a = 0; a < 5; a++) {
/* 129 */           Thaumcraft.proxy.getFX().sparkle((float)px + (p.worldObj.rand.nextFloat() - p.worldObj.rand.nextFloat()) * 0.3F, (float)py + (p.worldObj.rand.nextFloat() - p.worldObj.rand.nextFloat()) * 0.3F, (float)pz + (p.worldObj.rand.nextFloat() - p.worldObj.rand.nextFloat()) * 0.3F, 2.0F + p.worldObj.rand.nextFloat(), 2, 0.05F + p.worldObj.rand.nextFloat() * 0.05F);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 134 */       if (pointedEntity != null) {
/* 135 */         px = pointedEntity.posX;
/* 136 */         py = pointedEntity.getEntityBoundingBox().minY + pointedEntity.height / 2.0F;
/* 137 */         pz = pointedEntity.posZ;
/* 138 */         for (int a = 0; a < 5; a++) {
/* 139 */           Thaumcraft.proxy.getFX().sparkle((float)px + (p.worldObj.rand.nextFloat() - p.worldObj.rand.nextFloat()) * 0.6F, (float)py + (p.worldObj.rand.nextFloat() - p.worldObj.rand.nextFloat()) * 0.6F, (float)pz + (p.worldObj.rand.nextFloat() - p.worldObj.rand.nextFloat()) * 0.6F, 2.0F + p.worldObj.rand.nextFloat(), 2, 0.05F + p.worldObj.rand.nextFloat() * 0.05F);
/*     */         }
/*     */       }
/*     */       
/* 143 */       shootLightning(p.worldObj, p, px, py, pz, true);
/*     */     } else {
/* 145 */       p.worldObj.playSoundEffect(p.posX, p.posY, p.posZ, "thaumcraft:shock", 0.25F, 1.0F);
/*     */       
/* 147 */       if ((pointedEntity != null) && ((pointedEntity instanceof EntityLivingBase)) && (
/* 148 */         (!(pointedEntity instanceof EntityPlayer)) || (MinecraftServer.getServer().isPVPEnabled())))
/*     */       {
/* 150 */         int cl = getUpgradeLevel(wand.getFocusStack(stack), chainlightning) * 2;
/* 151 */         pointedEntity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(p, p), (cl > 0 ? 6 : 4) + potency);
/*     */         
/* 153 */         if (cl > 0) {
/* 154 */           cl += getUpgradeLevel(wand.getFocusStack(stack), FocusUpgradeType.enlarge) * 2;
/* 155 */           EntityLivingBase center = (EntityLivingBase)pointedEntity;
/* 156 */           ArrayList<Integer> targets = new ArrayList();
/* 157 */           targets.add(Integer.valueOf(pointedEntity.getEntityId()));
/* 158 */           while (cl > 0) {
/* 159 */             cl--;
/* 160 */             ArrayList<Entity> list = EntityUtils.getEntitiesInRange(p.worldObj, center.posX, center.posY, center.posZ, p, EntityLivingBase.class, 8.0D);
/*     */             
/*     */ 
/*     */ 
/* 164 */             double d = Double.MAX_VALUE;
/* 165 */             Entity closest = null;
/* 166 */             for (Entity e : list)
/* 167 */               if ((!targets.contains(Integer.valueOf(e.getEntityId()))) && (!e.isDead) && 
/* 168 */                 ((!(e instanceof IEntityOwnable)) || (!((IEntityOwnable)e).getOwner().equals(p))) && (
/* 169 */                 (!(e instanceof EntityPlayer)) || (MinecraftServer.getServer().isPVPEnabled()))) {
/* 170 */                 double dd = e.getDistanceSqToEntity(center);
/* 171 */                 if (dd < d) {
/* 172 */                   closest = e;
/* 173 */                   d = dd;
/*     */                 }
/*     */               }
/* 176 */             if (closest != null) {
/* 177 */               thaumcraft.common.lib.network.PacketHandler.INSTANCE.sendToAllAround(new thaumcraft.common.lib.network.fx.PacketFXZap(center.getEntityId(), closest.getEntityId()), new net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint(p.worldObj.provider.getDimensionId(), center.posX, center.posY, center.posZ, 64.0D));
/*     */               
/*     */ 
/*     */ 
/* 181 */               targets.add(Integer.valueOf(closest.getEntityId()));
/* 182 */               closest.attackEntityFrom(DamageSource.causeIndirectMagicDamage(p, p), 4 + potency);
/*     */               
/* 184 */               center = (EntityLivingBase)closest;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canApplyUpgrade(ItemStack focusstack, EntityPlayer player, FocusUpgradeType type, int rank)
/*     */   {
/* 196 */     if ((type.equals(FocusUpgradeType.enlarge)) && 
/* 197 */       (!isUpgradedWith(focusstack, chainlightning)) && (!isUpgradedWith(focusstack, earthshock)))
/*     */     {
/* 199 */       return false;
/*     */     }
/* 201 */     return true;
/*     */   }
/*     */   
/*     */   public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemstack, int rank)
/*     */   {
/* 206 */     switch (rank) {
/* 207 */     case 1:  return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency };
/*     */     case 2: 
/* 209 */       return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency };
/*     */     case 3: 
/* 211 */       return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency, chainlightning, earthshock };
/*     */     case 4: 
/* 213 */       return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge };
/*     */     case 5: 
/* 215 */       return new FocusUpgradeType[] { FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge };
/*     */     }
/*     */     
/* 218 */     return null;
/*     */   }
/*     */   
/* 221 */   public static FocusUpgradeType chainlightning = new FocusUpgradeType(new net.minecraft.util.ResourceLocation("thaumcraft", "textures/foci/chainlightning.png"), "focus.upgrade.chainlightning.name", "focus.upgrade.chainlightning.text", new AspectList().add(Aspect.ENERGY, 1));
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 226 */   public static FocusUpgradeType earthshock = new FocusUpgradeType(new net.minecraft.util.ResourceLocation("thaumcraft", "textures/foci/earthshock.png"), "focus.upgrade.earthshock.name", "focus.upgrade.earthshock.text", new AspectList().add(Aspect.ENERGY, 1));
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\wands\foci\ItemFocusShock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */