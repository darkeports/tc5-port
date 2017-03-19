/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.ThaumcraftApi;
/*     */ import thaumcraft.api.ThaumcraftApiHelper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IAspectContainer;
/*     */ import thaumcraft.api.aspects.IEssentiaTransport;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.api.crafting.CrucibleRecipe;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.IBlockFacing;
/*     */ import thaumcraft.common.container.InventoryFake;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ 
/*     */ public class TileThaumatorium extends TileThaumcraft implements IAspectContainer, IEssentiaTransport, ISidedInventory, ITickable
/*     */ {
/*  45 */   public ItemStack inputStack = null;
/*  46 */   public AspectList essentia = new AspectList();
/*     */   
/*  48 */   public ArrayList<Integer> recipeHash = new ArrayList();
/*  49 */   public ArrayList<AspectList> recipeEssentia = new ArrayList();
/*     */   
/*  51 */   public ArrayList<String> recipePlayer = new ArrayList();
/*  52 */   public int currentCraft = -1;
/*  53 */   public int maxRecipes = 1;
/*     */   
/*  55 */   public Aspect currentSuction = null;
/*     */   
/*     */ 
/*  58 */   int venting = 0;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/*  63 */     return AxisAlignedBB.fromBounds(getPos().getX() - 0.1D, getPos().getY() - 0.1D, getPos().getZ() - 0.1D, getPos().getX() + 1.1D, getPos().getY() + 2.1D, getPos().getZ() + 1.1D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  71 */     this.essentia.readFromNBT(nbttagcompound);
/*  72 */     this.maxRecipes = nbttagcompound.getByte("maxrec");
/*     */     
/*  74 */     this.recipeEssentia = new ArrayList();
/*  75 */     this.recipeHash = new ArrayList();
/*  76 */     this.recipePlayer = new ArrayList();
/*  77 */     int[] hashes = nbttagcompound.getIntArray("recipes");
/*  78 */     if (hashes != null) {
/*  79 */       for (int hash : hashes) {
/*  80 */         CrucibleRecipe recipe = ThaumcraftApi.getCrucibleRecipeFromHash(hash);
/*  81 */         if (recipe != null) {
/*  82 */           this.recipeEssentia.add(recipe.aspects.copy());
/*  83 */           this.recipePlayer.add("");
/*  84 */           this.recipeHash.add(Integer.valueOf(hash));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  94 */     nbttagcompound.setByte("maxrec", (byte)this.maxRecipes);
/*  95 */     this.essentia.writeToNBT(nbttagcompound);
/*  96 */     int[] hashes = new int[this.recipeHash.size()];
/*  97 */     int a = 0;
/*  98 */     for (Integer i : this.recipeHash) {
/*  99 */       hashes[a] = i.intValue();
/* 100 */       a++;
/*     */     }
/* 102 */     nbttagcompound.setIntArray("recipes", hashes);
/*     */   }
/*     */   
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 108 */     super.readFromNBT(nbtCompound);
/* 109 */     NBTTagList nbttaglist = nbtCompound.getTagList("Items", 10);
/* 110 */     if (nbttaglist.tagCount() > 0) {
/* 111 */       this.inputStack = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(0));
/*     */     }
/*     */     
/* 114 */     NBTTagList nbttaglist2 = nbtCompound.getTagList("OutputPlayer", 8);
/* 115 */     for (int a = 0; a < nbttaglist2.tagCount(); a++) {
/* 116 */       if (this.recipePlayer.size() > a) {
/* 117 */         this.recipePlayer.set(a, nbttaglist2.getStringTagAt(a));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 124 */     super.writeToNBT(nbtCompound);
/* 125 */     NBTTagList nbttaglist = new NBTTagList();
/* 126 */     if (this.inputStack != null)
/*     */     {
/* 128 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 129 */       nbttagcompound1.setByte("Slot", (byte)0);
/* 130 */       this.inputStack.writeToNBT(nbttagcompound1);
/* 131 */       nbttaglist.appendTag(nbttagcompound1);
/*     */     }
/* 133 */     nbtCompound.setTag("Items", nbttaglist);
/*     */     
/* 135 */     NBTTagList nbttaglist2 = new NBTTagList();
/* 136 */     if (this.recipePlayer.size() > 0)
/*     */     {
/* 138 */       for (int a = 0; a < this.recipePlayer.size(); a++) {
/* 139 */         if (this.recipePlayer.get(a) != null)
/*     */         {
/* 141 */           NBTTagString nbttagcompound1 = new NBTTagString((String)this.recipePlayer.get(a));
/* 142 */           nbttaglist2.appendTag(nbttagcompound1);
/*     */         }
/*     */       }
/*     */     }
/* 146 */     nbtCompound.setTag("OutputPlayer", nbttaglist2);
/*     */   }
/*     */   
/*     */   boolean checkHeat() {
/* 150 */     Material mat = this.worldObj.getBlockState(this.pos.down(2)).getBlock().getMaterial();
/* 151 */     Block bi = this.worldObj.getBlockState(this.pos.down(2)).getBlock();
/* 152 */     return (mat == Material.lava) || (mat == Material.fire) || (bi == BlocksTC.nitor);
/*     */   }
/*     */   
/* 155 */   int counter = 0;
/* 156 */   boolean heated = false;
/* 157 */   CrucibleRecipe currentRecipe = null;
/*     */   public Container eventHandler;
/*     */   
/* 160 */   public ItemStack getCurrentOutputRecipe() { ItemStack out = null;
/* 161 */     if ((this.currentCraft >= 0) && (this.recipeHash != null) && (this.recipeHash.size() > 0)) {
/* 162 */       CrucibleRecipe recipe = ThaumcraftApi.getCrucibleRecipeFromHash(((Integer)this.recipeHash.get(this.currentCraft)).intValue());
/* 163 */       if (recipe != null) {
/* 164 */         out = recipe.getRecipeOutput().copy();
/*     */       }
/*     */     }
/* 167 */     return out;
/*     */   }
/*     */   
/*     */   public void update()
/*     */   {
/* 172 */     if (!this.worldObj.isRemote) {
/* 173 */       if ((this.counter == 0) || (this.counter % 40 == 0)) {
/* 174 */         this.heated = checkHeat();
/* 175 */         getUpgrades();
/*     */       }
/* 177 */       this.counter += 1;
/* 178 */       if ((this.heated) && (!gettingPower()) && (this.counter % 5 == 0) && (this.recipeHash != null) && (this.recipeHash.size() > 0)) {
/* 179 */         if (this.inputStack == null) {
/* 180 */           this.currentSuction = null;
/* 181 */           return;
/*     */         }
/*     */         
/* 184 */         if ((this.currentCraft < 0) || (this.currentCraft >= this.recipeHash.size()) || (this.currentRecipe == null) || (!this.currentRecipe.catalystMatches(this.inputStack))) {
/* 185 */           for (int a = 0; a < this.recipeHash.size(); a++) {
/* 186 */             CrucibleRecipe recipe = ThaumcraftApi.getCrucibleRecipeFromHash(((Integer)this.recipeHash.get(a)).intValue());
/* 187 */             if (recipe.catalystMatches(this.inputStack)) {
/* 188 */               this.currentCraft = a;
/* 189 */               this.currentRecipe = recipe;
/* 190 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 195 */         if ((this.currentCraft < 0) || (this.currentCraft >= this.recipeHash.size())) { return;
/*     */         }
/* 197 */         TileEntity inventory = this.worldObj.getTileEntity(this.pos.offset(BlockStateUtils.getFacing(getBlockMetadata())));
/* 198 */         if ((inventory != null) && ((inventory instanceof IInventory))) {
/* 199 */           ItemStack dropped = getCurrentOutputRecipe();
/* 200 */           dropped = InventoryUtils.placeItemStackIntoInventory(dropped, (IInventory)inventory, BlockStateUtils.getFacing(getBlockMetadata()).getOpposite(), false);
/*     */           
/* 202 */           if (dropped != null) {
/* 203 */             return;
/*     */           }
/*     */         }
/*     */         
/* 207 */         boolean done = true;
/* 208 */         this.currentSuction = null;
/* 209 */         for (Aspect aspect : ((AspectList)this.recipeEssentia.get(this.currentCraft)).getAspectsSortedByName()) {
/* 210 */           if (this.essentia.getAmount(aspect) < ((AspectList)this.recipeEssentia.get(this.currentCraft)).getAmount(aspect)) {
/* 211 */             this.currentSuction = aspect;
/* 212 */             done = false;
/* 213 */             break;
/*     */           }
/*     */         }
/*     */         
/* 217 */         if (done) {
/* 218 */           completeRecipe();
/*     */ 
/*     */         }
/* 221 */         else if (this.currentSuction != null) fill();
/*     */       }
/*     */     }
/* 224 */     else if (this.venting > 0) {
/* 225 */       this.venting -= 1;
/* 226 */       float fx = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 227 */       float fz = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 228 */       float fy = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 229 */       float fx2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 230 */       float fz2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 231 */       float fy2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 232 */       int color = 16777215;
/* 233 */       EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata());
/* 234 */       Thaumcraft.proxy.getFX().drawVentParticles(this.pos.getX() + 0.5F + fx + facing.getFrontOffsetX() / 2.0F, this.pos.getY() + 0.5F + fy, this.pos.getZ() + 0.5F + fz + facing.getFrontOffsetZ() / 2.0F, facing.getFrontOffsetX() / 4.0F + fx2, fy2, facing.getFrontOffsetZ() / 4.0F + fz2, color);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void completeRecipe()
/*     */   {
/* 244 */     if ((this.currentRecipe != null) && (this.currentCraft < this.recipeHash.size()) && 
/* 245 */       (this.currentRecipe.matches(this.essentia, this.inputStack)) && 
/* 246 */       (decrStackSize(0, 1) != null)) {
/* 247 */       this.essentia = new AspectList();
/* 248 */       ItemStack dropped = getCurrentOutputRecipe();
/*     */       
/* 250 */       EntityPlayer p = this.worldObj.getPlayerEntityByName((String)this.recipePlayer.get(this.currentCraft));
/* 251 */       if (p != null) {
/* 252 */         FMLCommonHandler.instance().firePlayerCraftingEvent(p, dropped, new InventoryFake(new ItemStack[] { this.inputStack }));
/*     */       }
/*     */       
/* 255 */       EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata());
/* 256 */       TileEntity inventory = this.worldObj.getTileEntity(this.pos.offset(facing));
/* 257 */       if ((inventory != null) && ((inventory instanceof IInventory))) {
/* 258 */         dropped = InventoryUtils.placeItemStackIntoInventory(dropped, (IInventory)inventory, facing.getOpposite(), true);
/*     */       }
/*     */       
/*     */ 
/* 262 */       if (dropped != null) {
/* 263 */         EntityItem ei = new EntityItem(this.worldObj, this.pos.getX() + 0.5D + facing.getFrontOffsetX() * 0.66D, this.pos.getY() + 0.33D + facing.getOpposite().getFrontOffsetY(), this.pos.getZ() + 0.5D + facing.getFrontOffsetZ() * 0.66D, dropped.copy());
/*     */         
/*     */ 
/*     */ 
/* 267 */         ei.motionX = (0.075F * facing.getFrontOffsetX());
/* 268 */         ei.motionY = 0.02500000037252903D;
/* 269 */         ei.motionZ = (0.075F * facing.getFrontOffsetZ());
/* 270 */         this.worldObj.spawnEntityInWorld(ei);
/* 271 */         this.worldObj.addBlockEvent(this.pos, getBlockType(), 0, 0);
/*     */       }
/* 273 */       this.worldObj.playSoundEffect(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, "random.fizz", 0.25F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F);
/*     */       
/* 275 */       this.currentCraft = -1;
/* 276 */       this.worldObj.markBlockForUpdate(this.pos);
/* 277 */       markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   void fill()
/*     */   {
/* 284 */     EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata());
/* 285 */     TileEntity te = null;
/* 286 */     IEssentiaTransport ic = null;
/* 287 */     for (int y = 0; y <= 1; y++) {
/* 288 */       for (EnumFacing dir : EnumFacing.VALUES)
/*     */       {
/* 290 */         if ((dir != facing) && (dir != EnumFacing.DOWN) && ((y != 0) || (dir != EnumFacing.UP)))
/*     */         {
/* 292 */           te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.pos.up(y), dir);
/* 293 */           if (te != null) {
/* 294 */             ic = (IEssentiaTransport)te;
/* 295 */             if ((ic.getEssentiaAmount(dir.getOpposite()) > 0) && (ic.getSuctionAmount(dir.getOpposite()) < getSuctionAmount(null)) && (getSuctionAmount(null) >= ic.getMinimumSuction()))
/*     */             {
/*     */ 
/* 298 */               int ess = ic.takeEssentia(this.currentSuction, 1, dir.getOpposite());
/* 299 */               if (ess > 0) {
/* 300 */                 addToContainer(this.currentSuction, ess);
/* 301 */                 return;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int addToContainer(Aspect tt, int am)
/*     */   {
/* 314 */     int ce = this.currentRecipe.aspects.getAmount(tt) - this.essentia.getAmount(tt);
/* 315 */     if ((this.currentRecipe == null) || (ce <= 0)) return am;
/* 316 */     int add = Math.min(ce, am);
/* 317 */     this.essentia.add(tt, add);
/* 318 */     this.worldObj.markBlockForUpdate(this.pos);
/* 319 */     markDirty();
/* 320 */     return am - add;
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(Aspect tt, int am)
/*     */   {
/* 325 */     if (this.essentia.getAmount(tt) >= am) {
/* 326 */       this.essentia.remove(tt, am);
/* 327 */       this.worldObj.markBlockForUpdate(this.pos);
/* 328 */       markDirty();
/* 329 */       return true;
/*     */     }
/* 331 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean takeFromContainer(AspectList ot)
/*     */   {
/* 337 */     return false;
/*     */   }
/*     */   
/*     */   public boolean doesContainerContain(AspectList ot)
/*     */   {
/* 342 */     return false;
/*     */   }
/*     */   
/*     */   public boolean doesContainerContainAmount(Aspect tt, int am)
/*     */   {
/* 347 */     return this.essentia.getAmount(tt) >= am;
/*     */   }
/*     */   
/*     */   public int containerContains(Aspect tt)
/*     */   {
/* 352 */     return this.essentia.getAmount(tt);
/*     */   }
/*     */   
/*     */   public boolean doesContainerAccept(Aspect tag)
/*     */   {
/* 357 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/* 364 */     if (i >= 0)
/*     */     {
/* 366 */       if (this.worldObj.isRemote) {
/* 367 */         this.venting = 7;
/*     */       }
/* 369 */       return true;
/*     */     }
/*     */     
/* 372 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConnectable(EnumFacing face)
/*     */   {
/* 380 */     return face != BlockStateUtils.getFacing(getBlockMetadata());
/*     */   }
/*     */   
/*     */   public boolean canInputFrom(EnumFacing face)
/*     */   {
/* 385 */     return face != BlockStateUtils.getFacing(getBlockMetadata());
/*     */   }
/*     */   
/*     */   public boolean canOutputTo(EnumFacing face)
/*     */   {
/* 390 */     return false;
/*     */   }
/*     */   
/*     */   public void setSuction(Aspect aspect, int amount)
/*     */   {
/* 395 */     this.currentSuction = aspect;
/*     */   }
/*     */   
/*     */   public Aspect getSuctionType(EnumFacing loc)
/*     */   {
/* 400 */     return this.currentSuction;
/*     */   }
/*     */   
/*     */   public int getSuctionAmount(EnumFacing loc)
/*     */   {
/* 405 */     return this.currentSuction != null ? 128 : 0;
/*     */   }
/*     */   
/*     */   public Aspect getEssentiaType(EnumFacing loc)
/*     */   {
/* 410 */     return null;
/*     */   }
/*     */   
/*     */   public int getEssentiaAmount(EnumFacing loc)
/*     */   {
/* 415 */     return 0;
/*     */   }
/*     */   
/*     */   public int takeEssentia(Aspect aspect, int amount, EnumFacing face)
/*     */   {
/* 420 */     return (canOutputTo(face)) && (takeFromContainer(aspect, amount)) ? amount : 0;
/*     */   }
/*     */   
/*     */   public int addEssentia(Aspect aspect, int amount, EnumFacing face)
/*     */   {
/* 425 */     return canInputFrom(face) ? amount - addToContainer(aspect, amount) : 0;
/*     */   }
/*     */   
/*     */   public int getMinimumSuction()
/*     */   {
/* 430 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public AspectList getAspects()
/*     */   {
/* 436 */     return this.essentia;
/*     */   }
/*     */   
/*     */   public void setAspects(AspectList aspects)
/*     */   {
/* 441 */     this.essentia = aspects;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/* 452 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack getStackInSlot(int par1)
/*     */   {
/* 458 */     return this.inputStack;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack decrStackSize(int par1, int par2)
/*     */   {
/* 464 */     if (this.inputStack != null)
/*     */     {
/*     */ 
/*     */ 
/* 468 */       if (this.inputStack.stackSize <= par2)
/*     */       {
/* 470 */         ItemStack itemstack = this.inputStack;
/* 471 */         this.inputStack = null;
/* 472 */         if (this.eventHandler != null) this.eventHandler.onCraftMatrixChanged(this);
/* 473 */         return itemstack;
/*     */       }
/*     */       
/*     */ 
/* 477 */       ItemStack itemstack = this.inputStack.splitStack(par2);
/*     */       
/* 479 */       if (this.inputStack.stackSize == 0)
/*     */       {
/* 481 */         this.inputStack = null;
/*     */       }
/* 483 */       if (this.eventHandler != null) this.eventHandler.onCraftMatrixChanged(this);
/* 484 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 489 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ItemStack removeStackFromSlot(int par1)
/*     */   {
/* 496 */     if (this.inputStack != null)
/*     */     {
/* 498 */       ItemStack itemstack = this.inputStack;
/* 499 */       this.inputStack = null;
/* 500 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/* 504 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*     */   {
/* 513 */     this.inputStack = par2ItemStack;
/*     */     
/* 515 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit()))
/*     */     {
/* 517 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 520 */     if (this.eventHandler != null) { this.eventHandler.onCraftMatrixChanged(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getInventoryStackLimit()
/*     */   {
/* 526 */     return 64;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*     */   {
/* 532 */     return this.worldObj.getTileEntity(this.pos) == this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/*     */   {
/* 541 */     return true;
/*     */   }
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side)
/*     */   {
/* 546 */     return new int[] { 0 };
/*     */   }
/*     */   
/*     */   public boolean gettingPower() {
/* 550 */     return (this.worldObj.isBlockIndirectlyGettingPowered(this.pos) > 0) || (this.worldObj.isBlockIndirectlyGettingPowered(this.pos.down()) > 0) || (this.worldObj.isBlockIndirectlyGettingPowered(this.pos.up()) > 0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void getUpgrades()
/*     */   {
/* 556 */     EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata());
/* 557 */     int mr = 1;
/* 558 */     for (int yy = 0; yy <= 1; yy++) {
/* 559 */       for (EnumFacing dir : EnumFacing.VALUES) {
/* 560 */         if ((dir != EnumFacing.DOWN) && (dir != facing)) {
/* 561 */           int xx = this.pos.getX() + dir.getFrontOffsetX();
/* 562 */           int zz = this.pos.getZ() + dir.getFrontOffsetZ();
/* 563 */           BlockPos bp = new BlockPos(xx, this.pos.getY() + yy + dir.getFrontOffsetY(), zz);
/* 564 */           IBlockState bs = this.worldObj.getBlockState(bp);
/*     */           
/* 566 */           if (bs == BlocksTC.brainBox.getDefaultState().withProperty(IBlockFacing.FACING, dir.getOpposite()))
/* 567 */             mr += 2;
/*     */         }
/*     */       }
/*     */     }
/* 571 */     if (mr != this.maxRecipes) {
/* 572 */       this.maxRecipes = mr;
/* 573 */       while (this.recipeHash.size() > this.maxRecipes) {
/* 574 */         this.recipeHash.remove(this.recipeHash.size() - 1);
/*     */       }
/* 576 */       this.worldObj.markBlockForUpdate(this.pos);
/* 577 */       markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void openInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */   public int getField(int id)
/*     */   {
/* 590 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount()
/*     */   {
/* 598 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void clear() {}
/*     */   
/*     */   public String getName()
/*     */   {
/* 606 */     return "container.alchemyfurnace";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName()
/*     */   {
/* 611 */     return false;
/*     */   }
/*     */   
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 616 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
/*     */   {
/* 622 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
/*     */   {
/* 627 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileThaumatorium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */