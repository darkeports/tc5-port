/*     */ package thaumcraft.common.tiles.crafting;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.ThaumcraftApiHelper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IAspectContainer;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.api.crafting.IInfusionStabiliser;
/*     */ import thaumcraft.api.crafting.InfusionRecipe;
/*     */ import thaumcraft.api.internal.EnumWarpType;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.api.potions.PotionVisExhaust;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.basic.BlockPillar;
/*     */ import thaumcraft.common.blocks.basic.BlockPillar.PillarType;
/*     */ import thaumcraft.common.blocks.basic.BlockStoneTC;
/*     */ import thaumcraft.common.blocks.basic.BlockStoneTC.StoneType;
/*     */ import thaumcraft.common.blocks.devices.BlockPedestal;
/*     */ import thaumcraft.common.blocks.devices.BlockPedestal.PedestalType;
/*     */ import thaumcraft.common.container.InventoryFake;
/*     */ import thaumcraft.common.lib.events.EssentiaHandler;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXBlockArc;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXInfusionSource;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ 
/*     */ public class TileInfusionMatrix extends TileThaumcraft implements thaumcraft.api.wands.IWandable, IAspectContainer, ITickable
/*     */ {
/*  68 */   private ArrayList<BlockPos> pedestals = new ArrayList();
/*  69 */   private int dangerCount = 0;
/*  70 */   public boolean active = false;
/*  71 */   public boolean crafting = false;
/*  72 */   public boolean checkSurroundings = true;
/*  73 */   public int symmetryInstability = 0;
/*  74 */   public float costMult = 0.0F;
/*  75 */   public int instability = 0;
/*  76 */   private int cycleTime = 20;
/*     */   
/*     */ 
/*  79 */   private AspectList recipeEssentia = new AspectList();
/*  80 */   private ArrayList<ItemStack> recipeIngredients = null;
/*  81 */   private Object recipeOutput = null;
/*  82 */   private String recipePlayer = null;
/*  83 */   private String recipeOutputLabel = null;
/*  84 */   private ItemStack recipeInput = null;
/*  85 */   private int recipeInstability = 0;
/*  86 */   private int recipeXP = 0;
/*  87 */   private int recipeType = 0;
/*     */   
/*     */   public class SourceFX { public BlockPos loc;
/*     */     public int ticks;
/*     */     
/*  92 */     public SourceFX(BlockPos loc, int ticks, int color) { this.loc = loc;
/*  93 */       this.ticks = ticks;
/*  94 */       this.color = color;
/*     */     }
/*     */     
/*     */ 
/*     */     public int color;
/*     */     
/*     */     public int entity;
/*     */   }
/*     */   
/* 103 */   public HashMap<String, SourceFX> sourceFX = new HashMap();
/*     */   
/*     */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/* 108 */     return AxisAlignedBB.fromBounds(getPos().getX() - 0.1D, getPos().getY() - 0.1D, getPos().getZ() - 0.1D, getPos().getX() + 1.1D, getPos().getY() + 1.1D, getPos().getZ() + 1.1D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 116 */     this.active = nbtCompound.getBoolean("active");
/* 117 */     this.crafting = nbtCompound.getBoolean("crafting");
/* 118 */     this.instability = nbtCompound.getShort("instability");
/* 119 */     this.recipeEssentia.readFromNBT(nbtCompound);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 125 */     nbtCompound.setBoolean("active", this.active);
/* 126 */     nbtCompound.setBoolean("crafting", this.crafting);
/* 127 */     nbtCompound.setShort("instability", (short)this.instability);
/* 128 */     this.recipeEssentia.writeToNBT(nbtCompound);
/*     */   }
/*     */   
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 134 */     super.readFromNBT(nbtCompound);
/*     */     
/* 136 */     NBTTagList nbttaglist = nbtCompound.getTagList("recipein", 10);
/* 137 */     this.recipeIngredients = new ArrayList();
/* 138 */     for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */     {
/* 140 */       NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 141 */       byte b0 = nbttagcompound1.getByte("item");
/* 142 */       this.recipeIngredients.add(ItemStack.loadItemStackFromNBT(nbttagcompound1));
/*     */     }
/*     */     
/* 145 */     String rot = nbtCompound.getString("rotype");
/* 146 */     if ((rot != null) && (rot.equals("@"))) {
/* 147 */       this.recipeOutput = ItemStack.loadItemStackFromNBT(nbtCompound.getCompoundTag("recipeout"));
/*     */     }
/* 149 */     else if (rot != null) {
/* 150 */       this.recipeOutputLabel = rot;
/* 151 */       this.recipeOutput = nbtCompound.getTag("recipeout");
/*     */     }
/*     */     
/* 154 */     this.recipeInput = ItemStack.loadItemStackFromNBT(nbtCompound.getCompoundTag("recipeinput"));
/* 155 */     this.recipeInstability = nbtCompound.getInteger("recipeinst");
/* 156 */     this.recipeType = nbtCompound.getInteger("recipetype");
/* 157 */     this.recipeXP = nbtCompound.getInteger("recipexp");
/* 158 */     this.recipePlayer = nbtCompound.getString("recipeplayer");
/* 159 */     if (this.recipePlayer.isEmpty()) { this.recipePlayer = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 165 */     super.writeToNBT(nbtCompound);
/*     */     
/* 167 */     if ((this.recipeIngredients != null) && (this.recipeIngredients.size() > 0)) {
/* 168 */       NBTTagList nbttaglist = new NBTTagList();
/* 169 */       int count = 0;
/* 170 */       for (ItemStack stack : this.recipeIngredients)
/*     */       {
/* 172 */         if (stack != null)
/*     */         {
/* 174 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 175 */           nbttagcompound1.setByte("item", (byte)count);
/* 176 */           stack.writeToNBT(nbttagcompound1);
/* 177 */           nbttaglist.appendTag(nbttagcompound1);
/* 178 */           count++;
/*     */         }
/*     */       }
/* 181 */       nbtCompound.setTag("recipein", nbttaglist);
/*     */     }
/* 183 */     if ((this.recipeOutput != null) && ((this.recipeOutput instanceof ItemStack))) nbtCompound.setString("rotype", "@");
/* 184 */     if ((this.recipeOutput != null) && ((this.recipeOutput instanceof NBTBase))) {
/* 185 */       nbtCompound.setString("rotype", this.recipeOutputLabel);
/*     */     }
/*     */     
/* 188 */     if ((this.recipeOutput != null) && ((this.recipeOutput instanceof ItemStack))) {
/* 189 */       nbtCompound.setTag("recipeout", ((ItemStack)this.recipeOutput).writeToNBT(new NBTTagCompound()));
/*     */     }
/* 191 */     if ((this.recipeOutput != null) && ((this.recipeOutput instanceof NBTBase))) {
/* 192 */       nbtCompound.setTag("recipeout", (NBTBase)this.recipeOutput);
/*     */     }
/*     */     
/* 195 */     if (this.recipeInput != null) nbtCompound.setTag("recipeinput", this.recipeInput.writeToNBT(new NBTTagCompound()));
/* 196 */     nbtCompound.setInteger("recipeinst", this.recipeInstability);
/* 197 */     nbtCompound.setInteger("recipetype", this.recipeType);
/* 198 */     nbtCompound.setInteger("recipexp", this.recipeXP);
/*     */     
/* 200 */     if (this.recipePlayer == null) {
/* 201 */       nbtCompound.setString("recipeplayer", "");
/*     */     } else {
/* 203 */       nbtCompound.setString("recipeplayer", this.recipePlayer);
/*     */     }
/*     */   }
/*     */   
/* 207 */   public int count = 0;
/* 208 */   public int craftCount = 0;
/*     */   public float startUp;
/* 210 */   private int countDelay = this.cycleTime / 2;
/*     */   
/*     */   public void update()
/*     */   {
/* 214 */     this.count += 1;
/* 215 */     if (this.checkSurroundings) {
/* 216 */       this.checkSurroundings = false;
/* 217 */       getSurroundings();
/*     */     }
/* 219 */     if (this.worldObj.isRemote) {
/* 220 */       doEffects();
/*     */     } else {
/* 222 */       if ((this.count % (this.crafting ? 20 : 100) == 0) && 
/* 223 */         (!validLocation())) {
/* 224 */         this.active = false;
/* 225 */         markDirty();
/* 226 */         this.worldObj.markBlockForUpdate(this.pos);
/* 227 */         return;
/*     */       }
/*     */       
/*     */ 
/* 231 */       if ((this.active) && (this.crafting) && (this.count % this.countDelay == 0)) {
/* 232 */         craftCycle();
/* 233 */         markDirty();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 240 */   ArrayList<ItemStack> ingredients = new ArrayList();
/*     */   
/*     */   public boolean validLocation()
/*     */   {
/* 244 */     if (this.worldObj.getBlockState(this.pos.add(0, -2, 0)).getBlock() != BlocksTC.pedestal) return false;
/* 245 */     if (this.worldObj.getBlockState(this.pos.add(1, -2, 1)).getBlock() != BlocksTC.pillar) return false;
/* 246 */     if (this.worldObj.getBlockState(this.pos.add(-1, -2, 1)).getBlock() != BlocksTC.pillar) return false;
/* 247 */     if (this.worldObj.getBlockState(this.pos.add(1, -2, -1)).getBlock() != BlocksTC.pillar) return false;
/* 248 */     if (this.worldObj.getBlockState(this.pos.add(-1, -2, -1)).getBlock() != BlocksTC.pillar) { return false;
/*     */     }
/* 250 */     return true;
/*     */   }
/*     */   
/*     */   public void craftingStart(EntityPlayer player) {
/* 254 */     if (!validLocation()) {
/* 255 */       this.active = false;
/* 256 */       markDirty();
/* 257 */       this.worldObj.markBlockForUpdate(this.pos);
/* 258 */       return;
/*     */     }
/*     */     
/* 261 */     getSurroundings();
/* 262 */     TileEntity te = null;
/* 263 */     this.recipeInput = null;
/* 264 */     te = this.worldObj.getTileEntity(this.pos.down(2));
/* 265 */     if ((te != null) && ((te instanceof TilePedestal))) {
/* 266 */       TilePedestal ped = (TilePedestal)te;
/* 267 */       if (ped.getStackInSlot(0) != null) {
/* 268 */         this.recipeInput = ped.getStackInSlot(0).copy();
/*     */       }
/*     */     }
/*     */     
/* 272 */     if (this.recipeInput == null) { return;
/*     */     }
/* 274 */     ArrayList<ItemStack> components = new ArrayList();
/* 275 */     for (BlockPos cc : this.pedestals) {
/* 276 */       te = this.worldObj.getTileEntity(cc);
/* 277 */       if ((te != null) && ((te instanceof TilePedestal))) {
/* 278 */         TilePedestal ped = (TilePedestal)te;
/* 279 */         if (ped.getStackInSlot(0) != null) {
/* 280 */           components.add(ped.getStackInSlot(0).copy());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 285 */     if (components.size() == 0) { return;
/*     */     }
/*     */     
/* 288 */     InfusionRecipe recipe = thaumcraft.common.lib.crafting.ThaumcraftCraftingManager.findMatchingInfusionRecipe(components, this.recipeInput, player);
/*     */     
/* 290 */     if (this.costMult < 0.5D) { this.costMult = 0.5F;
/*     */     }
/*     */     
/* 293 */     if (recipe != null) {
/* 294 */       this.recipeType = 0;
/* 295 */       this.recipeIngredients = components;
/*     */       
/* 297 */       if ((recipe.getRecipeOutput(player, this.recipeInput, components) instanceof Object[])) {
/* 298 */         Object[] obj = (Object[])recipe.getRecipeOutput(player, this.recipeInput, components);
/* 299 */         this.recipeOutputLabel = ((String)obj[0]);
/* 300 */         this.recipeOutput = ((NBTBase)obj[1]);
/*     */       } else {
/* 302 */         this.recipeOutput = recipe.getRecipeOutput(player, this.recipeInput, components);
/*     */       }
/* 304 */       this.recipeInstability = recipe.getInstability(player, this.recipeInput, components);
/* 305 */       AspectList al = recipe.getAspects(player, this.recipeInput, components);
/* 306 */       AspectList al2 = new AspectList();
/* 307 */       for (Aspect as : al.getAspects()) {
/* 308 */         if ((int)(al.getAmount(as) * this.costMult) > 0)
/* 309 */           al2.add(as, (int)(al.getAmount(as) * this.costMult));
/*     */       }
/* 311 */       this.recipeEssentia = al2;
/*     */       
/* 313 */       this.recipePlayer = player.getName();
/* 314 */       this.instability = (this.symmetryInstability + this.recipeInstability);
/* 315 */       this.crafting = true;
/* 316 */       this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:craftstart", 0.5F, 1.0F);
/* 317 */       this.worldObj.markBlockForUpdate(this.pos);
/* 318 */       markDirty();
/* 319 */       return;
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
/*     */   public void craftCycle()
/*     */   {
/* 356 */     boolean valid = false;
/*     */     
/* 358 */     TileEntity te = this.worldObj.getTileEntity(this.pos.down(2));
/* 359 */     if ((te != null) && ((te instanceof TilePedestal))) {
/* 360 */       TilePedestal ped = (TilePedestal)te;
/* 361 */       if (ped.getStackInSlot(0) != null) {
/* 362 */         ItemStack i2 = ped.getStackInSlot(0).copy();
/* 363 */         if (this.recipeInput.getItemDamage() == 32767) {
/* 364 */           i2.setItemDamage(32767);
/*     */         }
/* 366 */         if (ThaumcraftApiHelper.areItemStacksEqualForCrafting(i2, this.recipeInput)) { valid = true;
/*     */         }
/*     */       }
/*     */     }
/* 370 */     if ((!valid) || ((this.instability > 0) && (this.worldObj.rand.nextInt(500) <= this.instability)))
/*     */     {
/* 372 */       switch (this.worldObj.rand.nextInt(21)) {
/* 373 */       case 0: case 2: case 10: case 13:  inEvEjectItem(0); break;
/* 374 */       case 6: case 17:  inEvEjectItem(1); break;
/* 375 */       case 1: case 11:  inEvEjectItem(2); break;
/* 376 */       case 3: case 8: case 14:  inEvZap(false); break;
/* 377 */       case 5: case 16:  inEvHarm(false); break;
/* 378 */       case 12:  inEvZap(true); break;
/* 379 */       case 19:  inEvEjectItem(3); break;
/* 380 */       case 7:  inEvEjectItem(4); break;
/* 381 */       case 4: case 15:  inEvEjectItem(5); break;
/* 382 */       case 18:  inEvHarm(true); break;
/* 383 */       case 9:  this.worldObj.createExplosion(null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, 1.5F + this.worldObj.rand.nextFloat(), false); break;
/* 384 */       case 20:  inEvWarp();
/*     */       }
/*     */       
/* 387 */       if (valid) { return;
/*     */       }
/*     */     }
/* 390 */     if (!valid) {
/* 391 */       this.instability = 0;
/* 392 */       this.crafting = false;
/* 393 */       this.recipeEssentia = new AspectList();
/* 394 */       this.worldObj.markBlockForUpdate(this.pos);
/* 395 */       this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:craftfail", 1.0F, 0.6F);
/* 396 */       markDirty();
/* 397 */       return;
/*     */     }
/*     */     
/* 400 */     if ((this.recipeType == 1) && (this.recipeXP > 0)) {
/* 401 */       List<EntityPlayer> targets = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.fromBounds(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 1, getPos().getZ() + 1).expand(10.0D, 10.0D, 10.0D));
/*     */       
/*     */ 
/* 404 */       if ((targets != null) && (targets.size() > 0)) {
/* 405 */         for (EntityPlayer target : targets) {
/* 406 */           if ((target.capabilities.isCreativeMode) || (target.experienceLevel > 0)) {
/* 407 */             if (!target.capabilities.isCreativeMode) target.removeExperienceLevel(1);
/* 408 */             this.recipeXP -= 1;
/* 409 */             target.attackEntityFrom(DamageSource.magic, this.worldObj.rand.nextInt(2));
/* 410 */             PacketHandler.INSTANCE.sendToAllAround(new PacketFXInfusionSource(this.pos, (byte)0, (byte)0, (byte)0, target.getEntityId()), new NetworkRegistry.TargetPoint(getWorld().provider.getDimensionId(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 32.0D));
/*     */             
/*     */ 
/*     */ 
/* 414 */             this.worldObj.playSoundAtEntity(target, "random.fizz", 1.0F, 2.0F + this.worldObj.rand.nextFloat() * 0.4F);
/* 415 */             this.countDelay = this.cycleTime;
/* 416 */             return;
/*     */           }
/*     */         }
/* 419 */         Aspect[] ingEss = this.recipeEssentia.getAspects();
/* 420 */         if ((ingEss != null) && (ingEss.length > 0) && (this.worldObj.rand.nextInt(3) == 0)) {
/* 421 */           Aspect as = ingEss[this.worldObj.rand.nextInt(ingEss.length)];
/* 422 */           this.recipeEssentia.add(as, 1);
/* 423 */           if (this.worldObj.rand.nextInt(50 - this.recipeInstability * 2) == 0) this.instability += 1;
/* 424 */           if (this.instability > 25) this.instability = 25;
/* 425 */           this.worldObj.markBlockForUpdate(this.pos);
/* 426 */           markDirty();
/*     */         }
/*     */       }
/* 429 */       return;
/*     */     }
/*     */     
/* 432 */     if ((this.recipeType == 1) && (this.recipeXP == 0)) { this.countDelay = (this.cycleTime / 2);
/*     */     }
/* 434 */     if (this.recipeEssentia.visSize() > 0) {
/* 435 */       for (Aspect aspect : this.recipeEssentia.getAspects()) {
/* 436 */         int na = this.recipeEssentia.getAmount(aspect);
/* 437 */         if (na > 0) {
/* 438 */           if (EssentiaHandler.drainEssentia(this, aspect, null, 12, na > 1 ? this.countDelay : 0))
/*     */           {
/* 440 */             this.recipeEssentia.reduce(aspect, 1);
/* 441 */             this.worldObj.markBlockForUpdate(this.pos);
/* 442 */             markDirty();
/* 443 */             return;
/*     */           }
/*     */           
/*     */ 
/* 447 */           if (this.worldObj.rand.nextInt(100 - this.recipeInstability * 3) == 0) this.instability += 1;
/* 448 */           if (this.instability > 25) this.instability = 25;
/* 449 */           this.worldObj.markBlockForUpdate(this.pos);
/* 450 */           markDirty();
/*     */         }
/*     */       }
/* 453 */       this.checkSurroundings = true;
/* 454 */       return;
/*     */     }
/*     */     
/* 457 */     if (this.recipeIngredients.size() > 0) {
/* 458 */       for (int a = 0; a < this.recipeIngredients.size(); a++) {
/* 459 */         for (BlockPos cc : this.pedestals) {
/* 460 */           te = this.worldObj.getTileEntity(cc);
/* 461 */           if ((te != null) && ((te instanceof TilePedestal)) && (((TilePedestal)te).getStackInSlot(0) != null) && 
/* 462 */             (ThaumcraftApiHelper.areItemStacksEqualForCrafting(((TilePedestal)te).getStackInSlot(0), this.recipeIngredients.get(a))))
/*     */           {
/* 464 */             if (this.itemCount == 0) {
/* 465 */               this.itemCount = 5;
/* 466 */               PacketHandler.INSTANCE.sendToAllAround(new PacketFXInfusionSource(this.pos, (byte)(this.pos.getX() - cc.getX()), (byte)(this.pos.getY() - cc.getY()), (byte)(this.pos.getZ() - cc.getZ()), 0), new NetworkRegistry.TargetPoint(getWorld().provider.getDimensionId(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 32.0D));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             }
/* 472 */             else if (this.itemCount-- <= 1)
/*     */             {
/* 474 */               ItemStack is = ((TilePedestal)te).getStackInSlot(0).getItem().getContainerItem(((TilePedestal)te).getStackInSlot(0));
/*     */               
/* 476 */               ((TilePedestal)te).setInventorySlotContents(0, is == null ? null : is.copy());
/* 477 */               ((TilePedestal)te).markDirty();
/* 478 */               this.recipeIngredients.remove(a);
/* 479 */               markDirty();
/*     */             }
/* 481 */             return;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 486 */         Aspect[] ingEss = this.recipeEssentia.getAspects();
/* 487 */         if ((ingEss != null) && (ingEss.length > 0) && (this.worldObj.rand.nextInt(1 + a) == 0)) {
/* 488 */           Aspect as = ingEss[this.worldObj.rand.nextInt(ingEss.length)];
/* 489 */           this.recipeEssentia.add(as, 1);
/* 490 */           if (this.worldObj.rand.nextInt(50 - this.recipeInstability * 2) == 0) this.instability += 1;
/* 491 */           if (this.instability > 25) this.instability = 25;
/* 492 */           this.worldObj.markBlockForUpdate(this.pos);
/* 493 */           markDirty();
/*     */         }
/*     */       }
/*     */       
/* 497 */       return;
/*     */     }
/* 499 */     this.instability = 0;
/* 500 */     this.crafting = false;
/* 501 */     craftingFinish(this.recipeOutput, this.recipeOutputLabel);
/* 502 */     this.recipeOutput = null;
/* 503 */     this.worldObj.markBlockForUpdate(this.pos);
/* 504 */     markDirty();
/*     */   }
/*     */   
/*     */   private void inEvZap(boolean all)
/*     */   {
/* 509 */     List<EntityLivingBase> targets = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 1, getPos().getZ() + 1).expand(10.0D, 10.0D, 10.0D));
/*     */     
/*     */ 
/* 512 */     if ((targets != null) && (targets.size() > 0))
/* 513 */       for (EntityLivingBase target : targets) {
/* 514 */         PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockArc(this.pos, target, 0.3F - this.worldObj.rand.nextFloat() * 0.1F, 0.0F, 0.3F - this.worldObj.rand.nextFloat() * 0.1F), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 32.0D));
/*     */         
/*     */ 
/* 517 */         target.attackEntityFrom(DamageSource.magic, 4 + this.worldObj.rand.nextInt(4));
/* 518 */         if (!all)
/*     */           break;
/*     */       }
/*     */   }
/*     */   
/*     */   private void inEvHarm(boolean all) {
/* 524 */     List<EntityLivingBase> targets = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 1, getPos().getZ() + 1).expand(10.0D, 10.0D, 10.0D));
/*     */     
/*     */ 
/* 527 */     if ((targets != null) && (targets.size() > 0))
/* 528 */       for (EntityLivingBase target : targets) {
/* 529 */         if (this.worldObj.rand.nextBoolean()) {
/* 530 */           target.addPotionEffect(new PotionEffect(PotionFluxTaint.instance.getId(), 120, 0, false, true));
/*     */         } else {
/* 532 */           PotionEffect pe = new PotionEffect(PotionVisExhaust.instance.getId(), 2400, 0, true, true);
/* 533 */           pe.getCurativeItems().clear();
/* 534 */           target.addPotionEffect(pe);
/*     */         }
/* 536 */         if (!all)
/*     */           break;
/*     */       }
/*     */   }
/*     */   
/*     */   private void inEvWarp() {
/* 542 */     List<EntityPlayer> targets = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.fromBounds(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 1, getPos().getZ() + 1).expand(10.0D, 10.0D, 10.0D));
/*     */     
/*     */ 
/* 545 */     if ((targets != null) && (targets.size() > 0)) {
/* 546 */       EntityPlayer target = (EntityPlayer)targets.get(this.worldObj.rand.nextInt(targets.size()));
/* 547 */       if (this.worldObj.rand.nextFloat() < 0.25F) {
/* 548 */         ResearchHelper.addWarpToPlayer(target, 1, EnumWarpType.NORMAL);
/*     */       } else {
/* 550 */         ResearchHelper.addWarpToPlayer(target, 2 + this.worldObj.rand.nextInt(4), EnumWarpType.TEMPORARY);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void inEvEjectItem(int type)
/*     */   {
/* 557 */     int q = 0;
/* 558 */     while ((q < 50) && (this.pedestals.size() > 0)) {
/* 559 */       BlockPos cc = (BlockPos)this.pedestals.get(this.worldObj.rand.nextInt(this.pedestals.size()));
/* 560 */       TileEntity te = this.worldObj.getTileEntity(cc);
/* 561 */       if ((te != null) && ((te instanceof TilePedestal)) && (((TilePedestal)te).getStackInSlot(0) != null))
/*     */       {
/* 563 */         if ((type < 3) || (type == 5)) {
/* 564 */           InventoryUtils.dropItems(this.worldObj, cc);
/*     */         } else {
/* 566 */           ((TilePedestal)te).setInventorySlotContents(0, null);
/*     */         }
/* 568 */         if ((type == 1) || (type == 3))
/*     */         {
/* 570 */           this.worldObj.playSoundEffect(cc.getX(), cc.getY(), cc.getZ(), "game.neutral.swim", 0.3F, 1.0F);
/*     */         }
/* 572 */         else if ((type == 2) || (type == 4)) {
/* 573 */           int a = 5 + this.worldObj.rand.nextInt(5);
/* 574 */           AuraHelper.pollute(this.worldObj, cc, a, true);
/*     */         }
/* 576 */         else if (type == 5) {
/* 577 */           this.worldObj.createExplosion(null, cc.getX() + 0.5F, cc.getY() + 0.5F, cc.getZ() + 0.5F, 1.0F, false);
/*     */         }
/* 579 */         this.worldObj.addBlockEvent(cc, BlocksTC.pedestal, 11, 0);
/* 580 */         PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockArc(this.pos, cc.up(), 0.3F - this.worldObj.rand.nextFloat() * 0.1F, 0.0F, 0.3F - this.worldObj.rand.nextFloat() * 0.1F), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), cc.getX(), cc.getY(), cc.getZ(), 32.0D));
/*     */         
/*     */ 
/* 583 */         return;
/*     */       }
/* 585 */       q++;
/*     */     }
/*     */   }
/*     */   
/* 589 */   int itemCount = 0;
/*     */   
/*     */   public void craftingFinish(Object out, String label) {
/* 592 */     TileEntity te = this.worldObj.getTileEntity(this.pos.down(2));
/* 593 */     if ((te != null) && ((te instanceof TilePedestal))) {
/* 594 */       if ((out instanceof ItemStack)) {
/* 595 */         ((TilePedestal)te).setInventorySlotContentsFromInfusion(0, ((ItemStack)out).copy());
/*     */       }
/* 597 */       else if ((out instanceof NBTBase)) {
/* 598 */         ItemStack temp = ((TilePedestal)te).getStackInSlot(0);
/* 599 */         NBTBase tag = (NBTBase)out;
/* 600 */         temp.setTagInfo(label, tag);
/* 601 */         this.worldObj.markBlockForUpdate(this.pos.down(2));
/* 602 */         te.markDirty();
/*     */       }
/* 604 */       else if ((out instanceof Enchantment)) {
/* 605 */         ItemStack temp = ((TilePedestal)te).getStackInSlot(0);
/* 606 */         Map enchantments = EnchantmentHelper.getEnchantments(temp);
/* 607 */         enchantments.put(Integer.valueOf(((Enchantment)out).effectId), Integer.valueOf(EnchantmentHelper.getEnchantmentLevel(((Enchantment)out).effectId, temp) + 1));
/*     */         
/*     */ 
/* 610 */         EnchantmentHelper.setEnchantments(enchantments, temp);
/* 611 */         this.worldObj.markBlockForUpdate(this.pos.down(2));
/* 612 */         te.markDirty();
/*     */       }
/*     */       
/* 615 */       if (this.recipePlayer != null) {
/* 616 */         EntityPlayer p = this.worldObj.getPlayerEntityByName(this.recipePlayer);
/* 617 */         if (p != null) {
/* 618 */           FMLCommonHandler.instance().firePlayerCraftingEvent(p, ((TilePedestal)te).getStackInSlot(0), new InventoryFake(this.recipeIngredients));
/*     */         }
/*     */       }
/*     */       
/* 622 */       this.recipeEssentia = new AspectList();
/* 623 */       this.worldObj.markBlockForUpdate(this.pos);
/* 624 */       markDirty();
/* 625 */       this.worldObj.addBlockEvent(this.pos.down(2), BlocksTC.pedestal, 12, 0);
/* 626 */       this.worldObj.playSoundEffect(this.pos.getX() + 0.5D, this.pos.getY(), this.pos.getZ() + 0.5D, "thaumcraft:wand", 0.5F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   private void getSurroundings() {
/* 631 */     ArrayList<BlockPos> stuff = new ArrayList();
/* 632 */     this.pedestals.clear();
/*     */     try
/*     */     {
/* 635 */       for (int xx = -12; xx <= 12; xx++) {
/* 636 */         for (int zz = -12; zz <= 12; zz++) {
/* 637 */           boolean skip = false;
/* 638 */           for (int yy = -5; yy <= 10; yy++) {
/* 639 */             if ((xx != 0) || (zz != 0)) {
/* 640 */               int x = this.pos.getX() + xx;
/* 641 */               int y = this.pos.getY() - yy;
/* 642 */               int z = this.pos.getZ() + zz;
/* 643 */               BlockPos bp = new BlockPos(x, y, z);
/* 644 */               TileEntity te = this.worldObj.getTileEntity(bp);
/* 645 */               if ((!skip) && (yy > 0) && (Math.abs(xx) <= 8) && (Math.abs(zz) <= 8) && (te != null) && ((te instanceof TilePedestal)))
/*     */               {
/* 647 */                 this.pedestals.add(bp);
/* 648 */                 skip = true;
/*     */               } else {
/* 650 */                 Block bi = this.worldObj.getBlockState(bp).getBlock();
/*     */                 try {
/* 652 */                   if ((bi != null) && ((bi == Blocks.skull) || (((bi instanceof IInfusionStabiliser)) && (((IInfusionStabiliser)bi).canStabaliseInfusion(getWorld(), bp)))))
/*     */                   {
/* 654 */                     stuff.add(bp); }
/*     */                 } catch (Exception e) {}
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 661 */       this.cycleTime = 20;
/* 662 */       this.symmetryInstability = 0;
/* 663 */       this.costMult = 1.0F;
/* 664 */       if ((this.worldObj.getBlockState(this.pos.add(-1, -2, -1)).getBlock() == BlocksTC.pillar) && (this.worldObj.getBlockState(this.pos.add(1, -2, -1)).getBlock() == BlocksTC.pillar) && (this.worldObj.getBlockState(this.pos.add(1, -2, 1)).getBlock() == BlocksTC.pillar) && (this.worldObj.getBlockState(this.pos.add(-1, -2, 1)).getBlock() == BlocksTC.pillar))
/*     */       {
/*     */ 
/* 667 */         if ((this.worldObj.getBlockState(this.pos.add(-1, -2, -1)).getValue(BlockPillar.TYPE) == BlockPillar.PillarType.ANCIENT) && (this.worldObj.getBlockState(this.pos.add(1, -2, -1)).getValue(BlockPillar.TYPE) == BlockPillar.PillarType.ANCIENT) && (this.worldObj.getBlockState(this.pos.add(1, -2, 1)).getValue(BlockPillar.TYPE) == BlockPillar.PillarType.ANCIENT) && (this.worldObj.getBlockState(this.pos.add(-1, -2, 1)).getValue(BlockPillar.TYPE) == BlockPillar.PillarType.ANCIENT))
/*     */         {
/*     */ 
/*     */ 
/* 671 */           this.cycleTime -= 2;
/* 672 */           this.costMult -= 0.1F;
/* 673 */           this.symmetryInstability += 2;
/*     */         }
/*     */         
/* 676 */         if ((this.worldObj.getBlockState(this.pos.add(-1, -2, -1)).getValue(BlockPillar.TYPE) == BlockPillar.PillarType.ELDRITCH) && (this.worldObj.getBlockState(this.pos.add(1, -2, -1)).getValue(BlockPillar.TYPE) == BlockPillar.PillarType.ELDRITCH) && (this.worldObj.getBlockState(this.pos.add(1, -2, 1)).getValue(BlockPillar.TYPE) == BlockPillar.PillarType.ELDRITCH) && (this.worldObj.getBlockState(this.pos.add(-1, -2, 1)).getValue(BlockPillar.TYPE) == BlockPillar.PillarType.ELDRITCH))
/*     */         {
/*     */ 
/*     */ 
/* 680 */           this.cycleTime -= 4;
/* 681 */           this.costMult += 0.05F;
/* 682 */           this.symmetryInstability -= 4;
/*     */         }
/*     */       }
/*     */       
/* 686 */       int[] xm = { -1, 1, 1, -1 };int[] zm = { -1, -1, 1, 1 };
/* 687 */       for (int a = 0; a < 4; a++) {
/* 688 */         if (this.worldObj.getBlockState(this.pos.add(xm[a], -3, zm[a])).getBlock() == BlocksTC.stone) {
/* 689 */           Comparable c = this.worldObj.getBlockState(this.pos.add(xm[a], -3, zm[a])).getValue(BlockStoneTC.VARIANT);
/* 690 */           if (c == BlockStoneTC.StoneType.MATRIX_SPEED) {
/* 691 */             this.cycleTime -= 2;
/* 692 */             this.costMult += 0.01F;
/*     */           }
/* 694 */           if (c == BlockStoneTC.StoneType.MATRIX_COST) {
/* 695 */             this.cycleTime += 1;
/* 696 */             this.costMult -= 0.02F;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 701 */       this.countDelay = (this.cycleTime / 2);
/*     */       
/* 703 */       int apc = 0;
/* 704 */       for (BlockPos cc : this.pedestals) {
/* 705 */         boolean items = false;
/* 706 */         int x = this.pos.getX() - cc.getX();
/* 707 */         int z = this.pos.getZ() - cc.getZ();
/*     */         
/* 709 */         TileEntity te = this.worldObj.getTileEntity(cc);
/* 710 */         if ((te != null) && ((te instanceof TilePedestal))) {
/* 711 */           this.symmetryInstability += 2;
/* 712 */           if (((TilePedestal)te).getStackInSlot(0) != null) {
/* 713 */             this.symmetryInstability += 1;
/* 714 */             if (this.worldObj.getBlockState(cc).getValue(BlockPedestal.VARIANT) == BlockPedestal.PedestalType.ELDRITCH) this.symmetryInstability += 1;
/* 715 */             items = true;
/*     */           }
/* 717 */           if (this.worldObj.getBlockState(cc).getValue(BlockPedestal.VARIANT) == BlockPedestal.PedestalType.ELDRITCH) {
/* 718 */             this.costMult += 0.0025F;
/*     */           }
/* 720 */           if (this.worldObj.getBlockState(cc).getValue(BlockPedestal.VARIANT) == BlockPedestal.PedestalType.ANCIENT) {
/* 721 */             this.costMult -= 0.01F;
/* 722 */             apc++;
/*     */           }
/*     */         }
/*     */         
/* 726 */         int xx = this.pos.getX() + x;
/* 727 */         int zz = this.pos.getZ() + z;
/* 728 */         BlockPos cc2 = new BlockPos(xx, cc.getY(), zz);
/* 729 */         te = this.worldObj.getTileEntity(cc2);
/* 730 */         if ((te != null) && ((te instanceof TilePedestal)) && (this.worldObj.getBlockState(cc2) == this.worldObj.getBlockState(cc))) {
/* 731 */           this.symmetryInstability -= 2;
/* 732 */           if ((((TilePedestal)te).getStackInSlot(0) != null) && (items)) {
/* 733 */             this.symmetryInstability -= 1;
/* 734 */             if (this.worldObj.getBlockState(cc2).getValue(BlockPedestal.VARIANT) == BlockPedestal.PedestalType.ELDRITCH) this.symmetryInstability -= 2;
/*     */           }
/*     */         }
/*     */       }
/* 738 */       this.symmetryInstability += apc / 4;
/*     */       
/* 740 */       float sym = 0.0F;
/* 741 */       for (BlockPos cc : stuff) {
/* 742 */         boolean items = false;
/* 743 */         int x = this.pos.getX() - cc.getX();
/* 744 */         int z = this.pos.getZ() - cc.getZ();
/*     */         
/* 746 */         Block bi = this.worldObj.getBlockState(cc).getBlock();
/*     */         try {
/* 748 */           if ((bi == Blocks.skull) || (((bi instanceof IInfusionStabiliser)) && (((IInfusionStabiliser)bi).canStabaliseInfusion(getWorld(), cc))))
/*     */           {
/* 750 */             sym += 0.1F;
/*     */           }
/*     */         }
/*     */         catch (Exception e) {}
/* 754 */         int xx = this.pos.getX() + x;
/* 755 */         int zz = this.pos.getZ() + z;
/* 756 */         bi = this.worldObj.getBlockState(new BlockPos(xx, cc.getY(), zz)).getBlock();
/*     */         try {
/* 758 */           if ((bi == Blocks.skull) || (((bi instanceof IInfusionStabiliser)) && (((IInfusionStabiliser)bi).canStabaliseInfusion(getWorld(), cc))))
/*     */           {
/* 760 */             sym -= 0.2F;
/*     */           }
/*     */         }
/*     */         catch (Exception e) {}
/*     */       }
/* 765 */       this.symmetryInstability = ((int)(this.symmetryInstability + sym));
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */   public boolean onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, BlockPos pos, EnumFacing side) {
/* 771 */     if ((!world.isRemote) && (this.active) && (!this.crafting)) {
/* 772 */       craftingStart(player);
/* 773 */       return false;
/*     */     }
/* 775 */     if ((!world.isRemote) && (!this.active) && (validLocation())) {
/* 776 */       this.active = true;
/* 777 */       this.worldObj.markBlockForUpdate(pos);
/* 778 */       markDirty();
/* 779 */       return false;
/*     */     }
/* 781 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doEffects()
/*     */   {
/* 799 */     if (this.crafting) {
/* 800 */       if (this.craftCount == 0) {
/* 801 */         this.worldObj.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:infuserstart", 0.5F, 1.0F, false);
/* 802 */       } else if ((this.craftCount == 0) || (this.craftCount % 65 == 0)) {
/* 803 */         this.worldObj.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:infuser", 0.5F, 1.0F, false);
/*     */       }
/* 805 */       this.craftCount += 1;
/* 806 */       Thaumcraft.proxy.getFX().blockRunes(this.pos.getX(), this.pos.getY() - 2, this.pos.getZ(), 0.5F + this.worldObj.rand.nextFloat() * 0.2F, 0.1F, 0.7F + this.worldObj.rand.nextFloat() * 0.3F, 25, -0.03F);
/*     */     }
/* 808 */     else if (this.craftCount > 0) {
/* 809 */       this.craftCount -= 2;
/* 810 */       if (this.craftCount < 0) this.craftCount = 0;
/* 811 */       if (this.craftCount > 50) { this.craftCount = 50;
/*     */       }
/*     */     }
/* 814 */     if ((this.active) && (this.startUp != 1.0F)) {
/* 815 */       if (this.startUp < 1.0F) this.startUp += Math.max(this.startUp / 10.0F, 0.001F);
/* 816 */       if (this.startUp > 0.999D) { this.startUp = 1.0F;
/*     */       }
/*     */     }
/* 819 */     if ((!this.active) && (this.startUp > 0.0F)) {
/* 820 */       if (this.startUp > 0.0F) this.startUp -= this.startUp / 10.0F;
/* 821 */       if (this.startUp < 0.001D) this.startUp = 0.0F;
/*     */     }
/* 823 */     for (String fxk : (String[])this.sourceFX.keySet().toArray(new String[0])) {
/* 824 */       SourceFX fx = (SourceFX)this.sourceFX.get(fxk);
/* 825 */       if (fx.ticks <= 0) {
/* 826 */         this.sourceFX.remove(fxk);
/*     */       }
/*     */       else {
/* 829 */         if (fx.loc.equals(this.pos)) {
/* 830 */           Entity player = this.worldObj.getEntityByID(fx.color);
/* 831 */           if (player != null) {
/* 832 */             for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(2); a++) {
/* 833 */               Thaumcraft.proxy.getFX().drawInfusionParticles4(player.posX + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * player.width, player.getEntityBoundingBox().minY + this.worldObj.rand.nextFloat() * player.height, player.posZ + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * player.width, this.pos.getX(), this.pos.getY(), this.pos.getZ());
/*     */             }
/*     */             
/*     */           }
/*     */           
/*     */         }
/*     */         else
/*     */         {
/* 841 */           TileEntity tile = this.worldObj.getTileEntity(fx.loc);
/* 842 */           if ((tile instanceof TilePedestal)) {
/* 843 */             ItemStack is = ((TilePedestal)tile).getStackInSlot(0);
/* 844 */             if (is != null) {
/* 845 */               if (this.worldObj.rand.nextInt(3) == 0) {
/* 846 */                 Thaumcraft.proxy.getFX().drawInfusionParticles3(fx.loc.getX() + this.worldObj.rand.nextFloat(), fx.loc.getY() + this.worldObj.rand.nextFloat() + 1.0F, fx.loc.getZ() + this.worldObj.rand.nextFloat(), this.pos.getX(), this.pos.getY(), this.pos.getZ());
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/* 851 */                 Item bi = is.getItem();
/* 852 */                 int md = is.getItemDamage();
/* 853 */                 if ((bi instanceof ItemBlock)) {
/* 854 */                   for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(2); a++) {
/* 855 */                     Thaumcraft.proxy.getFX().drawInfusionParticles2(fx.loc.getX() + this.worldObj.rand.nextFloat(), fx.loc.getY() + this.worldObj.rand.nextFloat() + 1.0F, fx.loc.getZ() + this.worldObj.rand.nextFloat(), this.pos, Block.getBlockFromItem(bi), md);
/*     */                   }
/*     */                   
/*     */                 }
/*     */                 else
/*     */                 {
/* 861 */                   for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(2); a++) {
/* 862 */                     Thaumcraft.proxy.getFX().drawInfusionParticles1(fx.loc.getX() + 0.4F + this.worldObj.rand.nextFloat() * 0.2F, fx.loc.getY() + 1.23F + this.worldObj.rand.nextFloat() * 0.2F, fx.loc.getZ() + 0.4F + this.worldObj.rand.nextFloat() * 0.2F, this.pos, bi, md);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 870 */             fx.ticks = 0;
/*     */           } }
/* 872 */         fx.ticks -= 1;
/* 873 */         this.sourceFX.put(fxk, fx);
/*     */       }
/*     */     }
/*     */     
/* 877 */     if ((this.crafting) && (this.instability > 0) && (this.worldObj.rand.nextInt(200) <= this.instability)) {
/* 878 */       float xx = this.pos.getX() + 0.5F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 4.0F;
/* 879 */       float zz = this.pos.getZ() + 0.5F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 4.0F;
/* 880 */       int yy = this.pos.getY() - 2;
/* 881 */       while (!this.worldObj.isAirBlock(new BlockPos(xx, yy, zz))) yy++;
/* 882 */       Thaumcraft.proxy.getFX().arcLightning(this.pos.getX() + 0.5F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.5F, xx, yy, zz, 0.8F, 0.1F, 1.0F, 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public AspectList getAspects()
/*     */   {
/* 889 */     return this.recipeEssentia;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAspects(AspectList aspects) {}
/*     */   
/*     */ 
/*     */   public int addToContainer(Aspect tag, int amount)
/*     */   {
/* 899 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(Aspect tag, int amount)
/*     */   {
/* 904 */     return false;
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(AspectList ot)
/*     */   {
/* 909 */     return false;
/*     */   }
/*     */   
/*     */   public boolean doesContainerContainAmount(Aspect tag, int amount)
/*     */   {
/* 914 */     return false;
/*     */   }
/*     */   
/*     */   public boolean doesContainerContain(AspectList ot)
/*     */   {
/* 919 */     return false;
/*     */   }
/*     */   
/*     */   public int containerContains(Aspect tag)
/*     */   {
/* 924 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean doesContainerAccept(Aspect tag)
/*     */   {
/* 929 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\crafting\TileInfusionMatrix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */