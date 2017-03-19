/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fluids.BlockFluidBase;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidHandler;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.common.items.consumables.ItemBathSalts;
/*     */ 
/*     */ public class TileSpa extends TileThaumcraft implements net.minecraft.inventory.ISidedInventory, IFluidHandler, ITickable
/*     */ {
/*  29 */   private ItemStack[] itemStacks = new ItemStack[1];
/*  30 */   private boolean mix = true;
/*     */   private String customName;
/*     */   
/*  33 */   public void toggleMix() { this.mix = (!this.mix);
/*  34 */     this.worldObj.markBlockForUpdate(this.pos);
/*  35 */     markDirty();
/*     */   }
/*     */   
/*     */   public boolean getMix() {
/*  39 */     return this.mix;
/*     */   }
/*     */   
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  44 */     this.mix = nbttagcompound.getBoolean("mix");
/*  45 */     this.tank.setFluid(FluidStack.loadFluidStackFromNBT(nbttagcompound));
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  50 */     nbttagcompound.setBoolean("mix", this.mix);
/*  51 */     if (this.tank.getFluid() != null) {
/*  52 */       this.tank.getFluid().writeToNBT(nbttagcompound);
/*     */     }
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound) {
/*  57 */     super.readFromNBT(nbttagcompound);
/*  58 */     NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
/*  59 */     this.itemStacks = new ItemStack[getSizeInventory()];
/*  60 */     for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */     {
/*  62 */       NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/*  63 */       byte b0 = nbttagcompound1.getByte("Slot");
/*     */       
/*  65 */       if ((b0 >= 0) && (b0 < this.itemStacks.length))
/*     */       {
/*  67 */         this.itemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  74 */     super.writeToNBT(nbttagcompound);
/*  75 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  77 */     for (int i = 0; i < this.itemStacks.length; i++)
/*     */     {
/*  79 */       if (this.itemStacks[i] != null)
/*     */       {
/*  81 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  82 */         nbttagcompound1.setByte("Slot", (byte)i);
/*  83 */         this.itemStacks[i].writeToNBT(nbttagcompound1);
/*  84 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/*  88 */     nbttagcompound.setTag("Items", nbttaglist);
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
/*     */   public int getSizeInventory()
/*     */   {
/* 102 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getStackInSlot(int par1)
/*     */   {
/* 111 */     return this.itemStacks[par1];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack decrStackSize(int par1, int par2)
/*     */   {
/* 121 */     if (this.itemStacks[par1] != null)
/*     */     {
/*     */ 
/*     */ 
/* 125 */       if (this.itemStacks[par1].stackSize <= par2)
/*     */       {
/* 127 */         ItemStack itemstack = this.itemStacks[par1];
/* 128 */         this.itemStacks[par1] = null;
/* 129 */         return itemstack;
/*     */       }
/*     */       
/*     */ 
/* 133 */       ItemStack itemstack = this.itemStacks[par1].splitStack(par2);
/*     */       
/* 135 */       if (this.itemStacks[par1].stackSize == 0)
/*     */       {
/* 137 */         this.itemStacks[par1] = null;
/*     */       }
/*     */       
/* 140 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 145 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack removeStackFromSlot(int par1)
/*     */   {
/* 156 */     if (this.itemStacks[par1] != null)
/*     */     {
/* 158 */       ItemStack itemstack = this.itemStacks[par1];
/* 159 */       this.itemStacks[par1] = null;
/* 160 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/* 164 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*     */   {
/* 174 */     this.itemStacks[par1] = par2ItemStack;
/*     */     
/* 176 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit()))
/*     */     {
/* 178 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getInventoryStackLimit()
/*     */   {
/* 190 */     return 64;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*     */   {
/* 199 */     return this.worldObj.getTileEntity(getPos()) == this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/*     */   {
/* 210 */     return (par2ItemStack != null) && ((par2ItemStack.getItem() instanceof ItemBathSalts));
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
/* 222 */     return 0;
/*     */   }
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount()
/*     */   {
/* 229 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {}
/*     */   
/*     */   public String getName()
/*     */   {
/* 236 */     return "thaumcraft.spa";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName()
/*     */   {
/* 241 */     return false;
/*     */   }
/*     */   
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 246 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public int[] getSlotsForFace(EnumFacing side)
/*     */   {
/* 252 */     return side != EnumFacing.UP ? new int[] { 0 } : new int[0];
/*     */   }
/*     */   
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing side)
/*     */   {
/* 257 */     return side != EnumFacing.UP;
/*     */   }
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing side)
/*     */   {
/* 262 */     return side != EnumFacing.UP;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 267 */   private int counter = 0;
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/* 272 */     if ((!this.worldObj.isRemote) && (this.counter++ % 40 == 0) && (!this.worldObj.isBlockPowered(this.pos)) && (hasIngredients()))
/*     */     {
/* 274 */       Block b = this.worldObj.getBlockState(this.pos.up()).getBlock();
/* 275 */       int m = b.getMetaFromState(this.worldObj.getBlockState(this.pos.up()));
/* 276 */       Block tb = null;
/* 277 */       if (this.mix) {
/* 278 */         tb = BlocksTC.purifyingFluid;
/*     */       } else {
/* 280 */         tb = this.tank.getFluid().getFluid().getBlock();
/*     */       }
/* 282 */       if ((b == tb) && (m == 0))
/*     */       {
/* 284 */         for (int xx = -2; xx <= 2; xx++) {
/* 285 */           for (int zz = -2; zz <= 2; zz++) {
/* 286 */             BlockPos p = getPos().add(xx, 1, zz);
/* 287 */             if (isValidLocation(p, true, tb)) {
/* 288 */               consumeIngredients();
/* 289 */               this.worldObj.setBlockState(p, tb.getDefaultState());
/* 290 */               checkQuanta(p);
/* 291 */               return;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 296 */       else if (isValidLocation(this.pos.up(), false, tb)) {
/* 297 */         consumeIngredients();
/* 298 */         this.worldObj.setBlockState(this.pos.up(), tb.getDefaultState());
/* 299 */         checkQuanta(this.pos.up());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkQuanta(BlockPos pos)
/*     */   {
/* 306 */     Block b = this.worldObj.getBlockState(pos).getBlock();
/*     */     
/* 308 */     if ((b instanceof BlockFluidBase)) {
/* 309 */       float p = ((BlockFluidBase)b).getQuantaPercentage(this.worldObj, pos);
/* 310 */       if (p < 1.0F) {
/* 311 */         int md = (int)(1.0F / p) - 1;
/* 312 */         if ((md >= 0) && (md < 16)) this.worldObj.setBlockState(pos, b.getStateFromMeta(md));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean hasIngredients()
/*     */   {
/* 319 */     if (this.mix) {
/* 320 */       if ((this.tank.getInfo().fluid == null) || (!this.tank.getInfo().fluid.containsFluid(new FluidStack(FluidRegistry.WATER, 1000))))
/*     */       {
/*     */ 
/* 323 */         return false;
/*     */       }
/* 325 */       if ((this.itemStacks[0] == null) || (!(this.itemStacks[0].getItem() instanceof ItemBathSalts))) {
/* 326 */         return false;
/*     */       }
/* 328 */     } else if ((this.tank.getInfo().fluid == null) || (!this.tank.getFluid().getFluid().canBePlacedInWorld()) || (this.tank.getFluidAmount() < 1000))
/*     */     {
/*     */ 
/* 331 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 335 */     return true;
/*     */   }
/*     */   
/*     */   private void consumeIngredients() {
/* 339 */     if (this.mix) decrStackSize(0, 1);
/* 340 */     drain(EnumFacing.DOWN, 1000, true);
/*     */   }
/*     */   
/*     */   private boolean isValidLocation(BlockPos pos, boolean mustBeAdjacent, Block target) {
/* 344 */     if (((target == Blocks.water) || (target == Blocks.flowing_water)) && (this.worldObj.provider.doesWaterVaporize())) return false;
/* 345 */     Block b = this.worldObj.getBlockState(pos).getBlock();
/* 346 */     Block bb = this.worldObj.getBlockState(pos.down()).getBlock();
/* 347 */     int m = b.getMetaFromState(this.worldObj.getBlockState(pos));
/* 348 */     if ((bb.isSideSolid(this.worldObj, pos.down(), EnumFacing.UP)) && (b.isReplaceable(this.worldObj, pos)) && ((b != target) || (m != 0))) {
/* 349 */       if (!mustBeAdjacent) {
/* 350 */         return true;
/*     */       }
/* 352 */       return thaumcraft.common.lib.utils.BlockUtils.isBlockTouching(this.worldObj, pos, target.getStateFromMeta(0));
/*     */     }
/*     */     
/* 355 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 360 */   public FluidTank tank = new FluidTank(5000);
/*     */   
/*     */ 
/*     */   public int fill(EnumFacing from, FluidStack resource, boolean doFill)
/*     */   {
/* 365 */     int df = this.tank.fill(resource, doFill);
/* 366 */     if ((df > 0) && (doFill)) {
/* 367 */       this.worldObj.markBlockForUpdate(this.pos);
/* 368 */       markDirty();
/*     */     }
/* 370 */     return df;
/*     */   }
/*     */   
/*     */ 
/*     */   public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain)
/*     */   {
/* 376 */     if ((resource == null) || (!resource.isFluidEqual(this.tank.getFluid())))
/*     */     {
/* 378 */       return null;
/*     */     }
/* 380 */     return this.tank.drain(resource.amount, doDrain);
/*     */   }
/*     */   
/*     */ 
/*     */   public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
/*     */   {
/* 386 */     FluidStack fs = this.tank.drain(maxDrain, doDrain);
/* 387 */     if ((fs != null) && (doDrain)) {
/* 388 */       this.worldObj.markBlockForUpdate(this.pos);
/* 389 */       markDirty();
/*     */     }
/* 391 */     return fs;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canFill(EnumFacing from, Fluid fluid)
/*     */   {
/* 397 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canDrain(EnumFacing from, Fluid fluid)
/*     */   {
/* 403 */     return from != EnumFacing.UP;
/*     */   }
/*     */   
/*     */ 
/*     */   public FluidTankInfo[] getTankInfo(EnumFacing from)
/*     */   {
/* 409 */     return new FluidTankInfo[] { this.tank.getInfo() };
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileSpa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */