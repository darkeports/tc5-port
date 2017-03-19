/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IAspectContainer;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.api.items.IRechargable;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class TileRechargePedestal extends TileThaumcraft implements net.minecraft.inventory.ISidedInventory, IAspectContainer, ITickable
/*     */ {
/*  26 */   private static final int[] slots = { 0 };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  31 */   private ItemStack[] inventory = new ItemStack[1];
/*     */   
/*     */   private String customName;
/*     */   
/*     */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/*  38 */     return AxisAlignedBB.fromBounds(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 1, getPos().getZ() + 1).expand(2.0D, 2.0D, 2.0D);
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
/*  49 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getStackInSlot(int par1)
/*     */   {
/*  58 */     return this.inventory[par1];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack decrStackSize(int par1, int par2)
/*     */   {
/*  70 */     if (this.inventory[par1] != null)
/*     */     {
/*     */ 
/*  73 */       this.worldObj.markBlockForUpdate(this.pos);
/*  74 */       if (this.inventory[par1].stackSize <= par2)
/*     */       {
/*  76 */         ItemStack itemstack = this.inventory[par1];
/*  77 */         this.inventory[par1] = null;
/*  78 */         markDirty();
/*  79 */         return itemstack;
/*     */       }
/*     */       
/*     */ 
/*  83 */       ItemStack itemstack = this.inventory[par1].splitStack(par2);
/*     */       
/*  85 */       if (this.inventory[par1].stackSize == 0)
/*     */       {
/*  87 */         this.inventory[par1] = null;
/*     */       }
/*  89 */       markDirty();
/*  90 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  95 */     return null;
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
/* 106 */     if (this.inventory[par1] != null)
/*     */     {
/* 108 */       ItemStack itemstack = this.inventory[par1];
/* 109 */       this.inventory[par1] = null;
/* 110 */       markDirty();
/* 111 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/* 115 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*     */   {
/* 125 */     this.inventory[par1] = par2ItemStack;
/*     */     
/* 127 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit()))
/*     */     {
/* 129 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*     */     }
/* 131 */     markDirty();
/* 132 */     this.worldObj.markBlockForUpdate(this.pos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void openInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getField(int id)
/*     */   {
/* 153 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */ 
/*     */   public int getFieldCount()
/*     */   {
/* 162 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void clear() {}
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/* 171 */     return hasCustomName() ? this.customName : "container.rechargepedestal";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName()
/*     */   {
/* 176 */     return (this.customName != null) && (this.customName.length() > 0);
/*     */   }
/*     */   
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 181 */     return hasCustomName() ? new ChatComponentText(getName()) : new net.minecraft.util.ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 188 */     NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
/* 189 */     this.inventory = new ItemStack[getSizeInventory()];
/*     */     
/* 191 */     for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */     {
/* 193 */       NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 194 */       byte b0 = nbttagcompound1.getByte("Slot");
/*     */       
/* 196 */       if ((b0 >= 0) && (b0 < this.inventory.length))
/*     */       {
/* 198 */         this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 205 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 207 */     for (int i = 0; i < this.inventory.length; i++)
/*     */     {
/* 209 */       if (this.inventory[i] != null)
/*     */       {
/* 211 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 212 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 213 */         this.inventory[i].writeToNBT(nbttagcompound1);
/* 214 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 218 */     nbttagcompound.setTag("Items", nbttaglist);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 227 */     super.readFromNBT(nbtCompound);
/*     */     
/* 229 */     if (nbtCompound.hasKey("CustomName"))
/*     */     {
/* 231 */       this.customName = nbtCompound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 242 */     super.writeToNBT(nbtCompound);
/*     */     
/* 244 */     if (hasCustomName())
/*     */     {
/* 246 */       nbtCompound.setString("CustomName", this.customName);
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
/* 258 */     return 1;
/*     */   }
/*     */   
/*     */ 
/* 262 */   int counter = 0;
/*     */   
/*     */   public void update()
/*     */   {
/* 266 */     if ((!getWorld().isRemote) && (this.counter++ % 10 == 0) && (getStackInSlot(0) != null) && ((getStackInSlot(0).getItem() instanceof IRechargable))) {
/* 267 */       IRechargable wand = (IRechargable)getStackInSlot(0).getItem();
/* 268 */       Aspect aspect = wand.handleRecharge(getWorld(), getStackInSlot(0), this.pos, null, 5);
/* 269 */       this.worldObj.markBlockForUpdate(getPos());
/* 270 */       markDirty();
/* 271 */       if (aspect != null) {
/* 272 */         this.worldObj.addBlockEvent(this.pos, getBlockType(), 5, aspect.getColor());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*     */   {
/* 282 */     return this.worldObj.getTileEntity(this.pos) == this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isItemValidForSlot(int par1, ItemStack stack)
/*     */   {
/* 291 */     return (stack != null) && ((stack.getItem() instanceof IRechargable));
/*     */   }
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side)
/*     */   {
/* 296 */     return slots;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canInsertItem(int par1, ItemStack stack, EnumFacing par3)
/*     */   {
/* 302 */     return (getStackInSlot(par1) == null) && ((stack.getItem() instanceof IRechargable));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canExtractItem(int par1, ItemStack par2ItemStack, EnumFacing par3)
/*     */   {
/* 308 */     return true;
/*     */   }
/*     */   
/*     */   public AspectList getAspects()
/*     */   {
/* 313 */     if ((getStackInSlot(0) != null) && ((getStackInSlot(0).getItem() instanceof IRechargable))) {
/* 314 */       IRechargable wand = (IRechargable)getStackInSlot(0).getItem();
/* 315 */       return wand.getAspectsInChargable(getStackInSlot(0));
/*     */     }
/* 317 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAspects(AspectList aspects) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public int addToContainer(Aspect tag, int amount)
/*     */   {
/* 329 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean takeFromContainer(Aspect tag, int amount)
/*     */   {
/* 335 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean takeFromContainer(AspectList ot)
/*     */   {
/* 341 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean doesContainerContainAmount(Aspect tag, int amount)
/*     */   {
/* 347 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean doesContainerContain(AspectList ot)
/*     */   {
/* 353 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int containerContains(Aspect tag)
/*     */   {
/* 359 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean doesContainerAccept(Aspect tag)
/*     */   {
/* 364 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/* 370 */     if (i == 5)
/*     */     {
/* 372 */       if (this.worldObj.isRemote) {
/* 373 */         Thaumcraft.proxy.getFX().visSparkle(this.pos.getX() + getWorld().rand.nextInt(3) - getWorld().rand.nextInt(3), this.pos.up().getY() + getWorld().rand.nextInt(3), this.pos.getZ() + getWorld().rand.nextInt(3) - getWorld().rand.nextInt(3), this.pos.getX(), this.pos.up().getY(), this.pos.getZ(), j);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 379 */       return true;
/*     */     }
/*     */     
/* 382 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileRechargePedestal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */