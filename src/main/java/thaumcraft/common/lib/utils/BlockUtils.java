/*     */ package thaumcraft.common.lib.utils;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLog.EnumAxis;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.ServerConfigurationManager;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumFacing.Axis;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.RegistryNamespacedDefaultedByKey;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ import thaumcraft.api.ThaumcraftApiHelper;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.items.wands.ItemWand;
/*     */ 
/*     */ public class BlockUtils
/*     */ {
/*     */   public static boolean harvestBlock(World world, EntityPlayer player, BlockPos pos)
/*     */   {
/*  42 */     return harvestBlock(world, player, pos, false);
/*     */   }
/*     */   
/*     */   public static boolean harvestBlock(World world, EntityPlayer player, BlockPos pos, boolean alwaysDrop) {
/*  46 */     IBlockState bs = world.getBlockState(pos);
/*  47 */     net.minecraft.tileentity.TileEntity tile = world.getTileEntity(pos);
/*     */     
/*  49 */     if (bs.getBlock().getBlockHardness(world, pos) < 0.0F) {
/*  50 */       return false;
/*     */     }
/*     */     
/*  53 */     world.playAuxSFX(2001, pos, Block.getStateId(bs));
/*  54 */     boolean flag = false;
/*     */     
/*  56 */     if (player.capabilities.isCreativeMode) {
/*  57 */       flag = removeBlock(world, pos, player, false);
/*     */     } else {
/*  59 */       boolean flag1 = false;
/*  60 */       if (bs != null) {
/*  61 */         flag1 = (alwaysDrop) || (bs.getBlock().canHarvestBlock(world, pos, player));
/*     */       }
/*     */       
/*  64 */       flag = removeBlock(world, pos, player, true);
/*  65 */       if ((flag) && (flag1)) {
/*  66 */         bs.getBlock().harvestBlock(world, player, pos, bs, tile);
/*     */         try
/*     */         {
/*  69 */           int fortune = EnchantmentHelper.getFortuneModifier(player);
/*  70 */           if ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof ItemWand))) {
/*  71 */             fortune += ((ItemWand)player.getHeldItem().getItem()).getFocusTreasure(player.getHeldItem());
/*     */           }
/*  73 */           if ((!bs.getBlock().canSilkHarvest(world, pos, bs, player)) || (!EnchantmentHelper.getSilkTouchModifier(player))) {
/*  74 */             int exp = bs.getBlock().getExpDrop(world, pos, fortune);
/*  75 */             if (exp > 0)
/*     */             {
/*  77 */               bs.getBlock().dropXpOnBlockBreak(world, pos, exp);
/*     */             }
/*     */           }
/*     */         } catch (Exception e1) {}
/*     */       }
/*     */     }
/*  83 */     return true;
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
/*     */   public static ItemStack createStackedBlock(IBlockState bs)
/*     */   {
/* 105 */     ItemStack dropped = null;
/*     */     try {
/* 107 */       Method m = net.minecraftforge.fml.relauncher.ReflectionHelper.findMethod(Block.class, bs.getBlock(), new String[] { "createStackedBlock", "func_180643_i", "i" }, new Class[] { IBlockState.class });
/*     */       
/* 109 */       dropped = (ItemStack)m.invoke(bs.getBlock(), new Object[] { bs });
/*     */     } catch (Exception e) {
/* 111 */       Thaumcraft.log.warn("Could not invoke net.minecraft.block.Block method createStackedBlock");
/*     */     }
/* 113 */     return dropped;
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
/*     */   public static void destroyBlockPartially(World world, int par1, BlockPos pos, int par5)
/*     */   {
/* 145 */     Iterator iterator = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
/*     */     
/*     */ 
/* 148 */     while (iterator.hasNext()) {
/* 149 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();
/*     */       
/* 151 */       if ((entityplayermp != null) && (entityplayermp.worldObj == MinecraftServer.getServer().getEntityWorld()) && (entityplayermp.getEntityId() != par1))
/*     */       {
/*     */ 
/* 154 */         double d0 = pos.getX() - entityplayermp.posX;
/* 155 */         double d1 = pos.getY() - entityplayermp.posY;
/* 156 */         double d2 = pos.getZ() - entityplayermp.posZ;
/*     */         
/* 158 */         if (d0 * d0 + d1 * d1 + d2 * d2 < 1024.0D) {
/* 159 */           entityplayermp.playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S25PacketBlockBreakAnim(par1, pos, par5));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean removeBlock(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
/* 166 */     Block block = world.getBlockState(pos).getBlock();
/*     */     
/* 168 */     if (block != null) {
/* 169 */       block.onBlockHarvested(world, pos, world.getBlockState(pos), player);
/*     */     }
/*     */     
/* 172 */     boolean flag = (block != null) && (block.removedByPlayer(world, pos, player, willHarvest));
/*     */     
/* 174 */     if ((block != null) && (flag)) {
/* 175 */       block.onBlockDestroyedByPlayer(world, pos, world.getBlockState(pos));
/*     */     }
/*     */     
/* 178 */     return flag;
/*     */   }
/*     */   
/*     */   public static void findBlocks(World world, BlockPos pos, IBlockState block, int reach) {
/* 182 */     int count = 0;
/* 183 */     for (int xx = -reach; xx <= reach; xx++)
/* 184 */       for (int yy = reach; yy >= -reach; yy--)
/* 185 */         for (int zz = -reach; zz <= reach; zz++) {
/* 186 */           if (Math.abs(lastPos.getX() + xx - pos.getX()) > 24) return;
/* 187 */           if (Math.abs(lastPos.getY() + yy - pos.getY()) > 48) return;
/* 188 */           if (Math.abs(lastPos.getZ() + zz - pos.getZ()) > 24) return;
/* 189 */           IBlockState bs = world.getBlockState(lastPos.add(xx, yy, zz));
/*     */           
/* 191 */           boolean same = (bs.getBlock() == block.getBlock()) && (bs.getBlock().damageDropped(bs) == block.getBlock().damageDropped(block));
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 199 */           if ((same) && (bs.getBlock().getBlockHardness(world, lastPos.add(xx, yy, zz)) >= 0.0F)) {
/* 200 */             double xd = lastPos.getX() + xx - pos.getX();
/* 201 */             double yd = lastPos.getY() + yy - pos.getY();
/* 202 */             double zd = lastPos.getZ() + zz - pos.getZ();
/* 203 */             double d = xd * xd + yd * yd + zd * zd;
/* 204 */             if (d > lastdistance) {
/* 205 */               lastdistance = d;
/* 206 */               lastPos = lastPos.add(xx, yy, zz);
/* 207 */               findBlocks(world, pos, block, reach);
/* 208 */               return;
/*     */             }
/*     */           }
/*     */         }
/*     */   }
/*     */   
/* 214 */   static BlockPos lastPos = BlockPos.ORIGIN;
/* 215 */   static int lasty = 0;
/* 216 */   static int lastz = 0;
/* 217 */   static double lastdistance = 0.0D;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean breakFurthestBlock(World world, BlockPos pos, IBlockState block, EntityPlayer player)
/*     */   {
/* 226 */     lastPos = new BlockPos(pos);
/* 227 */     lastdistance = 0.0D;
/*     */     
/* 229 */     int reach = Utils.isWoodLog(world, pos) ? 2 : 1;
/*     */     
/* 231 */     findBlocks(world, pos, block, reach);
/*     */     
/* 233 */     boolean worked = harvestBlock(world, player, lastPos, false);
/* 234 */     world.markBlockForUpdate(pos);
/*     */     
/* 236 */     if ((worked) && (Utils.isWoodLog(world, pos)))
/*     */     {
/* 238 */       world.markBlockForUpdate(lastPos);
/* 239 */       for (int xx = -3; xx <= 3; xx++) {
/* 240 */         for (int yy = -3; yy <= 3; yy++) {
/* 241 */           for (int zz = -3; zz <= 3; zz++) {
/* 242 */             world.scheduleUpdate(lastPos.add(xx, yy, zz), world.getBlockState(lastPos.add(xx, yy, zz)).getBlock(), 50 + world.rand.nextInt(75));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 248 */     return worked;
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
/*     */   public static MovingObjectPosition getTargetBlock(World world, Entity entity, boolean par3)
/*     */   {
/* 268 */     return getTargetBlock(world, entity, par3, par3, 10.0D);
/*     */   }
/*     */   
/*     */   public static MovingObjectPosition getTargetBlock(World world, Entity entity, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, double range) {
/* 272 */     float var4 = 1.0F;
/* 273 */     float var5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * var4;
/* 274 */     float var6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * var4;
/* 275 */     double var7 = entity.prevPosX + (entity.posX - entity.prevPosX) * var4;
/* 276 */     double var9 = entity.prevPosY + (entity.posY - entity.prevPosY) * var4 + entity.getEyeHeight();
/* 277 */     double var11 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * var4;
/* 278 */     Vec3 var13 = new Vec3(var7, var9, var11);
/* 279 */     float var14 = MathHelper.cos(-var6 * 0.017453292F - 3.1415927F);
/* 280 */     float var15 = MathHelper.sin(-var6 * 0.017453292F - 3.1415927F);
/* 281 */     float var16 = -MathHelper.cos(-var5 * 0.017453292F);
/* 282 */     float var17 = MathHelper.sin(-var5 * 0.017453292F);
/* 283 */     float var18 = var15 * var16;
/* 284 */     float var20 = var14 * var16;
/* 285 */     Vec3 var23 = var13.addVector(var18 * range, var17 * range, var20 * range);
/* 286 */     return world.rayTraceBlocks(var13, var23, stopOnLiquid, !ignoreBlockWithoutBoundingBox, false);
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
/*     */   public static int countExposedSides(World world, BlockPos pos)
/*     */   {
/* 316 */     int count = 0;
/* 317 */     for (EnumFacing dir : EnumFacing.VALUES) {
/* 318 */       if (world.isAirBlock(pos.offset(dir))) count++;
/*     */     }
/* 320 */     return count;
/*     */   }
/*     */   
/*     */   public static boolean isBlockExposed(World world, BlockPos pos) {
/* 324 */     for (EnumFacing face : ) {
/* 325 */       if (!world.getBlockState(pos.offset(face)).getBlock().isOpaqueCube()) {
/* 326 */         return true;
/*     */       }
/*     */     }
/* 329 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isAdjacentToSolidBlock(World world, BlockPos pos) {
/* 333 */     for (EnumFacing face : ) {
/* 334 */       if (world.isSideSolid(pos.offset(face), face.getOpposite())) return true;
/*     */     }
/* 336 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isBlockTouching(IBlockAccess world, BlockPos pos, IBlockState bs) {
/* 340 */     for (EnumFacing face : ) {
/* 341 */       if (world.getBlockState(pos.offset(face)) == bs) return true;
/*     */     }
/* 343 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isBlockTouching(IBlockAccess world, BlockPos pos, Block bs) {
/* 347 */     for (EnumFacing face : ) {
/* 348 */       if (world.getBlockState(pos.offset(face)).getBlock() == bs) return true;
/*     */     }
/* 350 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isBlockTouching(IBlockAccess world, BlockPos pos, Material mat, boolean solid) {
/* 354 */     for (EnumFacing face : ) {
/* 355 */       if ((world.getBlockState(pos.offset(face)).getBlock().getMaterial() == mat) && ((!solid) || (world.getBlockState(pos.offset(face)).getBlock().isSideSolid(world, pos.offset(face), face.getOpposite()))))
/*     */       {
/* 357 */         return true; }
/*     */     }
/* 359 */     return false;
/*     */   }
/*     */   
/*     */   public static EnumFacing getFaceBlockTouching(IBlockAccess world, BlockPos pos, Block bs) {
/* 363 */     for (EnumFacing face : ) {
/* 364 */       if (world.getBlockState(pos.offset(face)).getBlock() == bs) return face;
/*     */     }
/* 366 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 371 */   public static ArrayList<String> portableHoleBlackList = new ArrayList();
/*     */   
/*     */   public static boolean isPortableHoleBlackListed(IBlockState blockstate) {
/* 374 */     return isBlockListed(blockstate, portableHoleBlackList);
/*     */   }
/*     */   
/*     */   public static boolean isBlockListed(IBlockState blockstate, List<String> list) {
/* 378 */     String stateString = blockstate.toString();
/* 379 */     for (String key : list) {
/* 380 */       String[] splitString = key.split(";");
/* 381 */       if (splitString[0].contains(":")) {
/* 382 */         if (((ResourceLocation)Block.blockRegistry.getNameForObject(blockstate.getBlock())).toString().equals(splitString[0])) {
/* 383 */           if (splitString.length > 1) {
/* 384 */             int matches = 0;
/* 385 */             for (int a = 1; a < splitString.length; a++) {
/* 386 */               if (stateString.contains(splitString[a])) matches++;
/*     */             }
/* 388 */             if (matches == splitString.length - 1) {
/* 389 */               return true;
/*     */             }
/*     */           } else {
/* 392 */             return true;
/*     */           }
/*     */         }
/*     */       } else {
/* 396 */         bs = new ItemStack(Item.getItemFromBlock(blockstate.getBlock()), 1, blockstate.getBlock().getMetaFromState(blockstate));
/* 397 */         for (ItemStack stack : OreDictionary.getOres(splitString[0])) {
/* 398 */           if (OreDictionary.itemMatches(stack, bs, false))
/* 399 */             return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     ItemStack bs;
/* 404 */     return false;
/*     */   }
/*     */   
/*     */   public static double distance(BlockPos b1, BlockPos b2) {
/* 408 */     double d3 = b1.getX() - b2.getX();
/* 409 */     double d4 = b1.getY() - b2.getY();
/* 410 */     double d5 = b1.getZ() - b2.getZ();
/* 411 */     return d3 * d3 + d4 * d4 + d5 * d5;
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
/*     */   public static EnumFacing.Axis getBlockAxis(World world, BlockPos pos)
/*     */   {
/* 483 */     IBlockState state = world.getBlockState(pos);
/* 484 */     EnumFacing.Axis ax = EnumFacing.Axis.Y;
/* 485 */     for (IProperty prop : state.getProperties().keySet())
/*     */     {
/* 487 */       if (prop.getName().equals("axis"))
/*     */       {
/* 489 */         if ((state.getValue(prop) instanceof BlockLog.EnumAxis)) {
/* 490 */           ax = (BlockLog.EnumAxis)state.getValue(prop) == BlockLog.EnumAxis.Z ? EnumFacing.Axis.Z : (BlockLog.EnumAxis)state.getValue(prop) == BlockLog.EnumAxis.Y ? EnumFacing.Axis.Y : (BlockLog.EnumAxis)state.getValue(prop) == BlockLog.EnumAxis.X ? EnumFacing.Axis.X : EnumFacing.Axis.Y;
/*     */           
/*     */ 
/* 493 */           break;
/*     */         }
/* 495 */         if ((state.getValue(prop) instanceof EnumFacing.Axis)) {
/* 496 */           ax = (EnumFacing.Axis)state.getValue(prop);
/* 497 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 501 */     if (ax == null) ax = EnumFacing.Axis.Y;
/* 502 */     return ax;
/*     */   }
/*     */   
/*     */   public static boolean hasLOS(World world, BlockPos source, BlockPos target)
/*     */   {
/* 507 */     MovingObjectPosition mop = ThaumcraftApiHelper.rayTraceIgnoringSource(world, new Vec3(source.getX() + 0.5D, source.getY() + 0.5D, source.getZ() + 0.5D), new Vec3(target.getX() + 0.5D, target.getY() + 0.5D, target.getZ() + 0.5D), false, true, false);
/*     */     
/*     */ 
/*     */ 
/* 511 */     return (mop == null) || ((mop.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK) && (mop.getBlockPos().getX() == target.getX()) && (mop.getBlockPos().getY() == target.getY()) && (mop.getBlockPos().getZ() == target.getZ()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\utils\BlockUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */