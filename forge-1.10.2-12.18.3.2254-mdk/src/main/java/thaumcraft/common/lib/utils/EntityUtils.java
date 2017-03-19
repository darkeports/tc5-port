/*     */ package thaumcraft.common.lib.utils;
/*     */ 
/*     */ import baubles.api.BaublesApi;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
/*     */ import thaumcraft.api.items.IGoggles;
/*     */ import thaumcraft.api.items.IRevealer;
/*     */ import thaumcraft.common.entities.EntitySpecialItem;
/*     */ import thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss;
/*     */ import thaumcraft.common.entities.monster.mods.ChampionModifier;
/*     */ 
/*     */ public class EntityUtils
/*     */ {
/*     */   public static boolean hasGoggles(Entity e)
/*     */   {
/*  40 */     if (!(e instanceof EntityPlayer)) return false;
/*  41 */     EntityPlayer viewer = (EntityPlayer)e;
/*     */     
/*  43 */     if ((viewer.getHeldItem() != null) && ((viewer.getHeldItem().getItem() instanceof IGoggles)) && 
/*  44 */       (showPopups(viewer.getHeldItem(), viewer))) { return true;
/*     */     }
/*     */     
/*  47 */     for (int a = 0; a < 4; a++) {
/*  48 */       if ((viewer.inventory.armorInventory[a] != null) && ((viewer.inventory.armorInventory[a].getItem() instanceof IGoggles)))
/*     */       {
/*  50 */         if (showPopups(viewer.inventory.armorInventory[a], viewer)) { return true;
/*     */         }
/*     */       }
/*     */     }
/*  54 */     IInventory baubles = BaublesApi.getBaubles(viewer);
/*  55 */     for (int a = 0; a < 4; a++) {
/*  56 */       if ((baubles.getStackInSlot(a) != null) && ((baubles.getStackInSlot(a).getItem() instanceof IGoggles)) && 
/*  57 */         (showPopups(baubles.getStackInSlot(a), viewer))) { return true;
/*     */       }
/*     */     }
/*     */     
/*  61 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean showPopups(ItemStack stack, EntityPlayer player) {
/*  65 */     return ((IGoggles)stack.getItem()).showIngamePopups(stack, player);
/*     */   }
/*     */   
/*     */   public static boolean hasRevealer(Entity e) {
/*  69 */     if (!(e instanceof EntityPlayer)) return false;
/*  70 */     EntityPlayer viewer = (EntityPlayer)e;
/*     */     
/*  72 */     if ((viewer.getHeldItem() != null) && ((viewer.getHeldItem().getItem() instanceof IRevealer)) && 
/*  73 */       (reveals(viewer.getHeldItem(), viewer))) { return true;
/*     */     }
/*     */     
/*  76 */     for (int a = 0; a < 4; a++) {
/*  77 */       if ((viewer.inventory.armorInventory[a] != null) && ((viewer.inventory.armorInventory[a].getItem() instanceof IRevealer)))
/*     */       {
/*  79 */         if (reveals(viewer.inventory.armorInventory[a], viewer)) { return true;
/*     */         }
/*     */       }
/*     */     }
/*  83 */     IInventory baubles = BaublesApi.getBaubles(viewer);
/*  84 */     for (int a = 0; a < 4; a++) {
/*  85 */       if ((baubles.getStackInSlot(a) != null) && ((baubles.getStackInSlot(a).getItem() instanceof IRevealer)) && 
/*  86 */         (reveals(baubles.getStackInSlot(a), viewer))) { return true;
/*     */       }
/*     */     }
/*     */     
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean reveals(ItemStack stack, EntityPlayer player) {
/*  94 */     return ((IRevealer)stack.getItem()).showNodes(stack, player);
/*     */   }
/*     */   
/*     */ 
/*     */   public static Entity getPointedEntity(World world, Entity entityplayer, double minrange, double range, float padding)
/*     */   {
/* 100 */     return getPointedEntity(world, entityplayer, minrange, range, padding, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static Entity getPointedEntity(World world, Entity entityplayer, double minrange, double range, float padding, boolean nonCollide)
/*     */   {
/* 107 */     Entity pointedEntity = null;
/* 108 */     double d = range;
/* 109 */     Vec3 vec3d = new Vec3(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ);
/*     */     
/*     */ 
/* 112 */     Vec3 vec3d1 = entityplayer.getLookVec();
/* 113 */     Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
/*     */     
/* 115 */     float f1 = padding;
/* 116 */     List list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.getEntityBoundingBox().addCoord(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d).expand(f1, f1, f1));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 121 */     double d2 = 0.0D;
/* 122 */     for (int i = 0; i < list.size(); i++) {
/* 123 */       Entity entity = (Entity)list.get(i);
/* 124 */       if (entity.getDistanceToEntity(entityplayer) >= minrange)
/*     */       {
/* 126 */         if (((entity.canBeCollidedWith()) || (nonCollide)) && (world.rayTraceBlocks(new Vec3(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ), new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), false, true, false) == null))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 138 */           float f2 = Math.max(0.8F, entity.getCollisionBorderSize());
/* 139 */           AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(f2, f2, f2);
/* 140 */           MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
/*     */           
/* 142 */           if (axisalignedbb.isVecInside(vec3d)) {
/* 143 */             if ((0.0D < d2) || (d2 == 0.0D)) {
/* 144 */               pointedEntity = entity;
/* 145 */               d2 = 0.0D;
/*     */             }
/*     */             
/*     */           }
/* 149 */           else if (movingobjectposition != null)
/*     */           {
/*     */ 
/* 152 */             double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
/* 153 */             if ((d3 < d2) || (d2 == 0.0D)) {
/* 154 */               pointedEntity = entity;
/* 155 */               d2 = d3;
/*     */             }
/*     */           } } } }
/* 158 */     return pointedEntity;
/*     */   }
/*     */   
/*     */   public static Entity getPointedEntity(World world, EntityLivingBase player, double range, Class<?> clazz) {
/* 162 */     Entity pointedEntity = null;
/* 163 */     double d = range;
/* 164 */     Vec3 vec3d = new Vec3(player.posX, player.posY + player.getEyeHeight(), player.posZ);
/*     */     
/*     */ 
/* 167 */     Vec3 vec3d1 = player.getLookVec();
/* 168 */     Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
/*     */     
/* 170 */     float f1 = 1.1F;
/* 171 */     List list = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().addCoord(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d).expand(f1, f1, f1));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 176 */     double d2 = 0.0D;
/* 177 */     for (int i = 0; i < list.size(); i++)
/*     */     {
/* 179 */       Entity entity = (Entity)list.get(i);
/* 180 */       if ((entity.canBeCollidedWith()) && (world.rayTraceBlocks(new Vec3(player.posX, player.posY + player.getEyeHeight(), player.posZ), new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), false, true, false) == null) && (!clazz.isInstance(entity)))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 191 */         float f2 = Math.max(0.8F, entity.getCollisionBorderSize());
/* 192 */         AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(f2, f2, f2);
/* 193 */         MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
/*     */         
/* 195 */         if (axisalignedbb.isVecInside(vec3d)) {
/* 196 */           if ((0.0D < d2) || (d2 == 0.0D)) {
/* 197 */             pointedEntity = entity;
/* 198 */             d2 = 0.0D;
/*     */           }
/*     */           
/*     */         }
/* 202 */         else if (movingobjectposition != null)
/*     */         {
/*     */ 
/* 205 */           double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
/* 206 */           if ((d3 < d2) || (d2 == 0.0D)) {
/* 207 */             pointedEntity = entity;
/* 208 */             d2 = d3;
/*     */           }
/*     */         } } }
/* 211 */     return pointedEntity;
/*     */   }
/*     */   
/*     */   public static boolean canEntityBeSeen(Entity entity, TileEntity te) {
/* 215 */     return te.getWorld().rayTraceBlocks(new Vec3(te.getPos().getX() + 0.5D, te.getPos().getY() + 1.25D, te.getPos().getZ() + 0.5D), new Vec3(entity.posX, entity.posY, entity.posZ), false) == null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean canEntityBeSeen(Entity entity, double x, double y, double z)
/*     */   {
/* 222 */     return entity.worldObj.rayTraceBlocks(new Vec3(x, y, z), new Vec3(entity.posX, entity.posY, entity.posZ), false) == null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean canEntityBeSeen(Entity entity, Entity entity2)
/*     */   {
/* 229 */     return entity.worldObj.rayTraceBlocks(new Vec3(entity.posX, entity.posY, entity.posZ), new Vec3(entity2.posX, entity2.posY, entity2.posZ), false) == null;
/*     */   }
/*     */   
/*     */ 
/*     */   public static void setRecentlyHit(EntityLivingBase ent, int hit)
/*     */   {
/*     */     try
/*     */     {
/* 237 */       ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, ent, Integer.valueOf(hit), new String[] { "recentlyHit", "field_70718_bc", "aM" });
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */   public static int getRecentlyHit(EntityLivingBase ent) {
/*     */     try {
/* 244 */       return ((Integer)net.minecraftforge.fml.relauncher.ReflectionHelper.getPrivateValue(EntityLivingBase.class, ent, new String[] { "recentlyHit", "field_70718_bc", "aM" })).intValue();
/*     */     }
/*     */     catch (Exception e) {}
/* 247 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void resetFloatCounter(EntityPlayerMP player)
/*     */   {
/*     */     try
/*     */     {
/* 300 */       ObfuscationReflectionHelper.setPrivateValue(NetHandlerPlayServer.class, player.playerNetServerHandler, Integer.valueOf(0), new String[] { "floatingTickCount", "field_147365_f", "g" });
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ArrayList<Entity> getEntitiesInRange(World world, BlockPos pos, Entity entity, Class clazz, double range)
/*     */   {
/* 309 */     return getEntitiesInRange(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, entity, clazz, range);
/*     */   }
/*     */   
/*     */ 
/*     */   public static ArrayList<Entity> getEntitiesInRange(World world, double x, double y, double z, Entity entity, Class clazz, double range)
/*     */   {
/* 315 */     ArrayList<Entity> out = new ArrayList();
/* 316 */     List list = world.getEntitiesWithinAABB(clazz, AxisAlignedBB.fromBounds(x, y, z, x, y, z).expand(range, range, range));
/*     */     
/*     */ 
/* 319 */     if (list.size() > 0) {
/* 320 */       for (Object e : list) {
/* 321 */         Entity ent = (Entity)e;
/* 322 */         if ((entity == null) || (entity.getEntityId() != ent.getEntityId()))
/*     */         {
/*     */ 
/* 325 */           out.add(ent);
/*     */         }
/*     */       }
/*     */     }
/* 329 */     return out;
/*     */   }
/*     */   
/*     */   public static boolean isVisibleTo(float fov, Entity ent, Entity ent2, float range)
/*     */   {
/* 334 */     double[] x = { ent2.posX, ent2.getEntityBoundingBox().minY + ent2.height / 2.0F, ent2.posZ };
/* 335 */     double[] t = { ent.posX, ent.getEntityBoundingBox().minY + ent.getEyeHeight(), ent.posZ };
/* 336 */     Vec3 q = ent.getLookVec();
/* 337 */     q = new Vec3(q.xCoord * range, q.yCoord * range, q.zCoord * range);
/* 338 */     Vec3 l = q.addVector(ent.posX, ent.getEntityBoundingBox().minY + ent.getEyeHeight(), ent.posZ);
/* 339 */     double[] b = { l.xCoord, l.yCoord, l.zCoord };
/* 340 */     return Utils.isLyingInCone(x, t, b, fov);
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean isVisibleTo(float fov, Entity ent, double xx, double yy, double zz, float range)
/*     */   {
/* 346 */     double[] x = { xx, yy, zz };
/* 347 */     double[] t = { ent.posX, ent.getEntityBoundingBox().minY + ent.getEyeHeight(), ent.posZ };
/* 348 */     Vec3 q = ent.getLookVec();
/* 349 */     q = new Vec3(q.xCoord * range, q.yCoord * range, q.zCoord * range);
/* 350 */     Vec3 l = q.addVector(ent.posX, ent.getEntityBoundingBox().minY + ent.getEyeHeight(), ent.posZ);
/* 351 */     double[] b = { l.xCoord, l.yCoord, l.zCoord };
/* 352 */     return Utils.isLyingInCone(x, t, b, fov);
/*     */   }
/*     */   
/*     */   public static EntityItem entityDropSpecialItem(Entity entity, ItemStack stack, float dropheight)
/*     */   {
/* 357 */     if ((stack.stackSize != 0) && (stack.getItem() != null))
/*     */     {
/* 359 */       EntitySpecialItem entityitem = new EntitySpecialItem(entity.worldObj, entity.posX, entity.posY + dropheight, entity.posZ, stack);
/*     */       
/* 361 */       entityitem.setDefaultPickupDelay();
/* 362 */       entityitem.motionY = 0.10000000149011612D;
/* 363 */       entityitem.motionX = 0.0D;
/* 364 */       entityitem.motionZ = 0.0D;
/* 365 */       if (entity.captureDrops)
/*     */       {
/* 367 */         entity.capturedDrops.add(entityitem);
/*     */       }
/*     */       else
/*     */       {
/* 371 */         entity.worldObj.spawnEntityInWorld(entityitem);
/*     */       }
/* 373 */       return entityitem;
/*     */     }
/*     */     
/*     */ 
/* 377 */     return null;
/*     */   }
/*     */   
/*     */   public static void makeChampion(EntityMob entity, boolean persist)
/*     */   {
/*     */     try
/*     */     {
/* 384 */       if (entity.getEntityAttribute(CHAMPION_MOD).getAttributeValue() > -2.0D) return;
/*     */     } catch (Exception e) {
/* 386 */       return;
/*     */     }
/*     */     
/* 389 */     int type = entity.worldObj.rand.nextInt(ChampionModifier.mods.length);
/*     */     
/* 391 */     if ((entity instanceof EntityCreeper)) {
/* 392 */       type = 0;
/*     */     }
/*     */     
/* 395 */     IAttributeInstance modai = entity.getEntityAttribute(CHAMPION_MOD);
/* 396 */     modai.removeModifier(ChampionModifier.mods[type].attributeMod);
/* 397 */     modai.applyModifier(ChampionModifier.mods[type].attributeMod);
/*     */     
/* 399 */     if (!(entity instanceof EntityThaumcraftBoss)) {
/* 400 */       IAttributeInstance iattributeinstance = entity.getEntityAttribute(SharedMonsterAttributes.maxHealth);
/* 401 */       iattributeinstance.removeModifier(CHAMPION_HEALTH);
/* 402 */       iattributeinstance.applyModifier(CHAMPION_HEALTH);
/* 403 */       IAttributeInstance iattributeinstance2 = entity.getEntityAttribute(SharedMonsterAttributes.attackDamage);
/* 404 */       iattributeinstance2.removeModifier(CHAMPION_DAMAGE);
/* 405 */       iattributeinstance2.applyModifier(CHAMPION_DAMAGE);
/* 406 */       entity.heal(25.0F);
/* 407 */       entity.setCustomNameTag(ChampionModifier.mods[type].getModNameLocalized() + " " + entity.getName());
/*     */     } else {
/* 409 */       ((EntityThaumcraftBoss)entity).generateName();
/*     */     }
/*     */     
/* 412 */     if (persist) { entity.enablePersistence();
/*     */     }
/* 414 */     switch (type) {
/*     */     case 0: 
/* 416 */       IAttributeInstance sai = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 417 */       sai.removeModifier(BOLDBUFF);
/* 418 */       sai.applyModifier(BOLDBUFF);
/* 419 */       break;
/*     */     case 3: 
/* 421 */       IAttributeInstance mai = entity.getEntityAttribute(SharedMonsterAttributes.attackDamage);
/* 422 */       mai.removeModifier(MIGHTYBUFF);
/* 423 */       mai.applyModifier(MIGHTYBUFF);
/* 424 */       break;
/*     */     case 5: 
/* 426 */       int bh = (int)entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() / 2;
/* 427 */       entity.setAbsorptionAmount(entity.getAbsorptionAmount() + bh);
/*     */     }
/*     */     
/*     */   }
/*     */   
/* 432 */   public static final IAttribute CHAMPION_MOD = new RangedAttribute((IAttribute)null, "tc.mobmod", -2.0D, -2.0D, 100.0D).setDescription("Champion modifier").setShouldWatch(true);
/* 433 */   public static final AttributeModifier CHAMPION_HEALTH = new AttributeModifier(UUID.fromString("a62bef38-48cc-42a6-ac5e-ef913841c4fd"), "Champion health buff", 100.0D, 0);
/* 434 */   public static final AttributeModifier CHAMPION_DAMAGE = new AttributeModifier(UUID.fromString("a340d2db-d881-4c25-ac62-f0ad14cd63b0"), "Champion damage buff", 2.0D, 2);
/* 435 */   public static final AttributeModifier BOLDBUFF = new AttributeModifier(UUID.fromString("4b1edd33-caa9-47ae-a702-d86c05701037"), "Bold speed boost", 0.3D, 1);
/* 436 */   public static final AttributeModifier MIGHTYBUFF = new AttributeModifier(UUID.fromString("7163897f-07f5-49b3-9ce4-b74beb83d2d3"), "Mighty damage boost", 2.0D, 2);
/* 437 */   public static final AttributeModifier[] HPBUFF = { new AttributeModifier(UUID.fromString("54d621c1-dd4d-4b43-8bd2-5531c8875797"), "HEALTH BUFF 1", 50.0D, 0), new AttributeModifier(UUID.fromString("f51257dc-b7fa-4f7a-92d7-75d68e8592c4"), "HEALTH BUFF 2", 50.0D, 0), new AttributeModifier(UUID.fromString("3d6b2e42-4141-4364-b76d-0e8664bbd0bb"), "HEALTH BUFF 3", 50.0D, 0), new AttributeModifier(UUID.fromString("02c97a08-801c-4131-afa2-1427a6151934"), "HEALTH BUFF 4", 50.0D, 0), new AttributeModifier(UUID.fromString("0f354f6a-33c5-40be-93be-81b1338567f1"), "HEALTH BUFF 5", 50.0D, 0) };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 443 */   public static final AttributeModifier[] DMGBUFF = { new AttributeModifier(UUID.fromString("534f8c57-929a-48cf-bbd6-0fd851030748"), "DAMAGE BUFF 1", 0.5D, 0), new AttributeModifier(UUID.fromString("d317a76e-0e7c-4c61-acfd-9fa286053b32"), "DAMAGE BUFF 2", 0.5D, 0), new AttributeModifier(UUID.fromString("ff462d63-26a2-4363-830e-143ed97e2a4f"), "DAMAGE BUFF 3", 0.5D, 0), new AttributeModifier(UUID.fromString("cf1eb39e-0c67-495f-887c-0d3080828d2f"), "DAMAGE BUFF 4", 0.5D, 0), new AttributeModifier(UUID.fromString("3cfab9da-2701-43d8-ac07-885f16fa4117"), "DAMAGE BUFF 5", 0.5D, 0) };
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\utils\EntityUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */