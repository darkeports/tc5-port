/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.client.particle.EntitySpellParticleFX.AmbientMobFactory;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class TileMirror extends TileThaumcraft implements IInventory, ITickable
/*     */ {
/*  34 */   public boolean linked = false;
/*     */   public int linkX;
/*     */   public int linkY;
/*     */   public int linkZ;
/*     */   public int linkDim;
/*     */   public int instability;
/*     */   
/*     */   public void restoreLink()
/*     */   {
/*  43 */     if (isDestinationValid()) {
/*  44 */       World targetWorld = MinecraftServer.getServer().worldServerForDimension(this.linkDim);
/*  45 */       if (targetWorld == null) return;
/*  46 */       TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/*  47 */       if ((te != null) && ((te instanceof TileMirror))) {
/*  48 */         TileMirror tm = (TileMirror)te;
/*  49 */         tm.linked = true;
/*  50 */         tm.linkX = getPos().getX();
/*  51 */         tm.linkY = getPos().getY();
/*  52 */         tm.linkZ = getPos().getZ();
/*  53 */         tm.linkDim = this.worldObj.provider.getDimensionId();
/*  54 */         targetWorld.markBlockForUpdate(tm.getPos());
/*  55 */         this.linked = true;
/*  56 */         markDirty();
/*  57 */         tm.markDirty();
/*  58 */         this.worldObj.markBlockForUpdate(getPos());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void invalidateLink() {
/*  64 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/*  65 */     if (targetWorld == null) return;
/*  66 */     if (!Utils.isChunkLoaded(targetWorld, this.linkX, this.linkZ)) return;
/*  67 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/*  68 */     if ((te != null) && ((te instanceof TileMirror))) {
/*  69 */       TileMirror tm = (TileMirror)te;
/*  70 */       tm.linked = false;
/*  71 */       markDirty();
/*  72 */       tm.markDirty();
/*  73 */       targetWorld.markBlockForUpdate(new BlockPos(this.linkX, this.linkY, this.linkZ));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isLinkValid()
/*     */   {
/*  79 */     if (!this.linked) return false;
/*  80 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/*  81 */     if (targetWorld == null) {
/*  82 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  87 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/*  88 */     if ((te == null) || (!(te instanceof TileMirror))) {
/*  89 */       this.linked = false;
/*  90 */       markDirty();
/*  91 */       this.worldObj.markBlockForUpdate(getPos());
/*  92 */       return false;
/*     */     }
/*  94 */     TileMirror tm = (TileMirror)te;
/*  95 */     if (!tm.linked) {
/*  96 */       this.linked = false;
/*  97 */       markDirty();
/*  98 */       this.worldObj.markBlockForUpdate(getPos());
/*  99 */       return false;
/*     */     }
/* 101 */     if ((tm.linkX != getPos().getX()) || (tm.linkY != getPos().getY()) || (tm.linkZ != getPos().getZ()) || (tm.linkDim != this.worldObj.provider.getDimensionId()))
/*     */     {
/*     */ 
/* 104 */       this.linked = false;
/* 105 */       markDirty();
/* 106 */       this.worldObj.markBlockForUpdate(getPos());
/* 107 */       return false;
/*     */     }
/* 109 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isLinkValidSimple() {
/* 113 */     if (!this.linked) return false;
/* 114 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/* 115 */     if (targetWorld == null) {
/* 116 */       return false;
/*     */     }
/*     */     
/* 119 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 120 */     if ((te == null) || (!(te instanceof TileMirror))) {
/* 121 */       return false;
/*     */     }
/* 123 */     TileMirror tm = (TileMirror)te;
/* 124 */     if (!tm.linked) {
/* 125 */       return false;
/*     */     }
/* 127 */     if ((tm.linkX != getPos().getX()) || (tm.linkY != getPos().getY()) || (tm.linkZ != getPos().getZ()) || (tm.linkDim != this.worldObj.provider.getDimensionId()))
/*     */     {
/*     */ 
/* 130 */       return false;
/*     */     }
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isDestinationValid() {
/* 136 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/* 137 */     if (targetWorld == null) {
/* 138 */       return false;
/*     */     }
/*     */     
/* 141 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 142 */     if ((te == null) || (!(te instanceof TileMirror))) {
/* 143 */       this.linked = false;
/* 144 */       markDirty();
/* 145 */       this.worldObj.markBlockForUpdate(getPos());
/* 146 */       return false;
/*     */     }
/*     */     
/* 149 */     TileMirror tm = (TileMirror)te;
/* 150 */     if (tm.isLinkValid()) {
/* 151 */       return false;
/*     */     }
/* 153 */     return true;
/*     */   }
/*     */   
/*     */   public boolean transport(EntityItem ie) {
/* 157 */     ItemStack items = ie.getEntityItem();
/* 158 */     if ((!this.linked) || (!isLinkValid())) return false;
/* 159 */     World world = MinecraftServer.getServer().worldServerForDimension(this.linkDim);
/* 160 */     TileEntity target = world.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 161 */     if ((target != null) && ((target instanceof TileMirror))) {
/* 162 */       ((TileMirror)target).addStack(items);
/* 163 */       addInstability(null, items.stackSize);
/* 164 */       ie.setDead();
/* 165 */       markDirty();
/* 166 */       target.markDirty();
/* 167 */       this.worldObj.addBlockEvent(getPos(), this.blockType, 1, 0);
/* 168 */       return true;
/*     */     }
/* 170 */     return false;
/*     */   }
/*     */   
/*     */   public boolean transportDirect(ItemStack items) {
/* 174 */     if ((items == null) || (items.stackSize <= 0)) return false;
/* 175 */     addStack(items.copy());
/* 176 */     markDirty();
/* 177 */     return true;
/*     */   }
/*     */   
/*     */   public void eject() {
/* 181 */     if ((this.outputStacks.size() > 0) && (this.count > 20)) {
/* 182 */       int i = this.worldObj.rand.nextInt(this.outputStacks.size());
/* 183 */       if (this.outputStacks.get(i) != null) {
/* 184 */         ItemStack outItem = ((ItemStack)this.outputStacks.get(i)).copy();
/* 185 */         outItem.stackSize = 1;
/* 186 */         if (spawnItem(outItem)) {
/* 187 */           ((ItemStack)this.outputStacks.get(i)).stackSize -= 1;
/* 188 */           addInstability(null, 1);
/* 189 */           this.worldObj.addBlockEvent(getPos(), this.blockType, 1, 0);
/* 190 */           if (((ItemStack)this.outputStacks.get(i)).stackSize <= 0) {
/* 191 */             this.outputStacks.remove(i);
/*     */           }
/* 193 */           markDirty();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean spawnItem(ItemStack stack) {
/*     */     try {
/* 201 */       EnumFacing face = BlockStateUtils.getFacing(getBlockMetadata());
/* 202 */       EntityItem ie2 = new EntityItem(this.worldObj, getPos().getX() + 0.5D - face.getFrontOffsetX() * 0.3D, getPos().getY() + 0.25D - face.getFrontOffsetY() * 0.3D, getPos().getZ() + 0.5D - face.getFrontOffsetZ() * 0.3D, stack);
/*     */       
/*     */ 
/*     */ 
/* 206 */       ie2.motionX = (face.getFrontOffsetX() * 0.15F);
/* 207 */       ie2.motionY = (face.getFrontOffsetY() * 0.15F);
/* 208 */       ie2.motionZ = (face.getFrontOffsetZ() * 0.15F);
/* 209 */       ie2.timeUntilPortal = 20;
/* 210 */       this.worldObj.spawnEntityInWorld(ie2);
/* 211 */       return true;
/*     */     } catch (Exception e) {}
/* 213 */     return false;
/*     */   }
/*     */   
/*     */   protected void addInstability(World targetWorld, int amt)
/*     */   {
/* 218 */     this.instability += amt;
/* 219 */     markDirty();
/* 220 */     if (targetWorld != null) {
/* 221 */       TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 222 */       if ((te != null) && ((te instanceof TileMirror))) {
/* 223 */         ((TileMirror)te).instability += amt;
/* 224 */         if (((TileMirror)te).instability < 0) ((TileMirror)te).instability = 0;
/* 225 */         te.markDirty();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 234 */     super.readCustomNBT(nbttagcompound);
/* 235 */     this.linked = nbttagcompound.getBoolean("linked");
/* 236 */     this.linkX = nbttagcompound.getInteger("linkX");
/* 237 */     this.linkY = nbttagcompound.getInteger("linkY");
/* 238 */     this.linkZ = nbttagcompound.getInteger("linkZ");
/* 239 */     this.linkDim = nbttagcompound.getInteger("linkDim");
/* 240 */     this.instability = nbttagcompound.getInteger("instability");
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 246 */     super.writeCustomNBT(nbttagcompound);
/* 247 */     nbttagcompound.setBoolean("linked", this.linked);
/* 248 */     nbttagcompound.setInteger("linkX", this.linkX);
/* 249 */     nbttagcompound.setInteger("linkY", this.linkY);
/* 250 */     nbttagcompound.setInteger("linkZ", this.linkZ);
/* 251 */     nbttagcompound.setInteger("linkDim", this.linkDim);
/* 252 */     nbttagcompound.setInteger("instability", this.instability);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/* 262 */     if (i == 1)
/*     */     {
/* 264 */       if (this.worldObj.isRemote) {
/* 265 */         EnumFacing face = BlockStateUtils.getFacing(getBlockMetadata());
/* 266 */         for (int q = 0; q < Thaumcraft.proxy.getFX().particleCount(1); q++) {
/* 267 */           double xx = getPos().getX() + 0.33D + this.worldObj.rand.nextFloat() * 0.33F - face.getFrontOffsetX() / 2.0D;
/* 268 */           double yy = getPos().getY() + 0.33D + this.worldObj.rand.nextFloat() * 0.33F - face.getFrontOffsetY() / 2.0D;
/* 269 */           double zz = getPos().getZ() + 0.33D + this.worldObj.rand.nextFloat() * 0.33F - face.getFrontOffsetZ() / 2.0D;
/* 270 */           EntitySpellParticleFX.AmbientMobFactory amf = new EntitySpellParticleFX.AmbientMobFactory();
/* 271 */           EntityFX var21 = amf.getEntityFX(0, this.worldObj, xx, yy, zz, 0.0D, 0.0D, 0.0D, new int[0]);
/* 272 */           var21.motionX = (face.getFrontOffsetX() * 0.05D);
/* 273 */           var21.motionY = (face.getFrontOffsetY() * 0.05D);
/* 274 */           var21.motionZ = (face.getFrontOffsetZ() * 0.05D);
/* 275 */           Minecraft.getMinecraft().effectRenderer.addEffect(var21);
/*     */         }
/*     */       }
/* 278 */       return true;
/*     */     }
/* 280 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */   
/* 283 */   int count = 0;
/* 284 */   int inc = 40;
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/* 289 */     if (!this.worldObj.isRemote)
/*     */     {
/* 291 */       eject();
/*     */       
/* 293 */       checkInstability();
/*     */       
/* 295 */       if (this.count++ % this.inc == 0) {
/* 296 */         if (!isLinkValidSimple()) {
/* 297 */           if (this.inc < 600) this.inc += 20;
/* 298 */           restoreLink();
/*     */         } else {
/* 300 */           this.inc = 40;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void checkInstability() {
/* 307 */     if (this.instability > 128) {
/* 308 */       AuraHelper.pollute(this.worldObj, this.pos, 1, true);
/* 309 */       this.instability -= 128;
/* 310 */       markDirty();
/*     */     }
/* 312 */     if ((this.instability > 0) && (this.count % 100 == 0)) {
/* 313 */       this.instability -= 1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 318 */   private ArrayList<ItemStack> outputStacks = new ArrayList();
/*     */   
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 323 */     super.readFromNBT(nbtCompound);
/* 324 */     NBTTagList nbttaglist = nbtCompound.getTagList("Items", 10);
/* 325 */     this.outputStacks = new ArrayList();
/*     */     
/* 327 */     for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */     {
/* 329 */       NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 330 */       byte b0 = nbttagcompound1.getByte("Slot");
/*     */       
/* 332 */       this.outputStacks.add(ItemStack.loadItemStackFromNBT(nbttagcompound1));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 342 */     super.writeToNBT(nbtCompound);
/* 343 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 345 */     for (int i = 0; i < this.outputStacks.size(); i++)
/*     */     {
/* 347 */       if ((this.outputStacks.get(i) != null) && (((ItemStack)this.outputStacks.get(i)).stackSize > 0)) {
/* 348 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 349 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 350 */         ((ItemStack)this.outputStacks.get(i)).writeToNBT(nbttagcompound1);
/* 351 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 355 */     nbtCompound.setTag("Items", nbttaglist);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/* 361 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack getStackInSlot(int par1)
/*     */   {
/* 367 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack decrStackSize(int par1, int par2)
/*     */   {
/* 372 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ItemStack removeStackFromSlot(int par1)
/*     */   {
/* 379 */     return null;
/*     */   }
/*     */   
/*     */   public void addStack(ItemStack stack) {
/* 383 */     this.outputStacks.add(stack);
/* 384 */     markDirty();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*     */   {
/* 390 */     World world = MinecraftServer.getServer().worldServerForDimension(this.linkDim);
/* 391 */     TileEntity target = world.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 392 */     if ((target != null) && ((target instanceof TileMirror))) {
/* 393 */       ((TileMirror)target).addStack(par2ItemStack.copy());
/* 394 */       addInstability(null, par2ItemStack.stackSize);
/* 395 */       this.worldObj.addBlockEvent(getPos(), this.blockType, 1, 0);
/*     */     } else {
/* 397 */       spawnItem(par2ItemStack.copy());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public int getInventoryStackLimit()
/*     */   {
/* 404 */     return 64;
/*     */   }
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer var1)
/*     */   {
/* 409 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isItemValidForSlot(int var1, ItemStack var2)
/*     */   {
/* 414 */     World world = MinecraftServer.getServer().worldServerForDimension(this.linkDim);
/* 415 */     TileEntity target = world.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 416 */     if ((target != null) && ((target instanceof TileMirror))) {
/* 417 */       return true;
/*     */     }
/* 419 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 426 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasCustomName()
/*     */   {
/* 432 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 438 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void openInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getField(int id)
/*     */   {
/* 456 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public int getFieldCount()
/*     */   {
/* 468 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileMirror.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */