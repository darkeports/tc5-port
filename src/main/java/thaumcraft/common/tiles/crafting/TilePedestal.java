/*     */ package thaumcraft.common.tiles.crafting;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class TilePedestal extends TileThaumcraft implements net.minecraft.inventory.ISidedInventory
/*     */ {
/*  20 */   private static final int[] slots = { 0 };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  25 */   private ItemStack[] inventory = new ItemStack[1];
/*     */   
/*     */   private String customName;
/*     */   
/*     */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/*  32 */     return AxisAlignedBB.fromBounds(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 2, getPos().getZ() + 1);
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
/*  43 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getStackInSlot(int par1)
/*     */   {
/*  52 */     return this.inventory[par1];
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
/*     */   public ItemStack decrStackSize(int par1, int par2)
/*     */   {
/*  65 */     if (this.inventory[par1] != null)
/*     */     {
/*  67 */       if (!this.worldObj.isRemote) {
/*  68 */         this.worldObj.markBlockForUpdate(this.pos);
/*     */       }
/*     */       
/*     */ 
/*  72 */       if (this.inventory[par1].stackSize <= par2)
/*     */       {
/*  74 */         ItemStack itemstack = this.inventory[par1];
/*  75 */         this.inventory[par1] = null;
/*  76 */         markDirty();
/*  77 */         return itemstack;
/*     */       }
/*     */       
/*     */ 
/*  81 */       ItemStack itemstack = this.inventory[par1].splitStack(par2);
/*     */       
/*  83 */       if (this.inventory[par1].stackSize == 0)
/*     */       {
/*  85 */         this.inventory[par1] = null;
/*     */       }
/*  87 */       markDirty();
/*  88 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  93 */     return null;
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
/* 104 */     if (this.inventory[par1] != null)
/*     */     {
/* 106 */       ItemStack itemstack = this.inventory[par1];
/* 107 */       this.inventory[par1] = null;
/* 108 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/* 112 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*     */   {
/* 122 */     this.inventory[par1] = par2ItemStack;
/*     */     
/* 124 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit()))
/*     */     {
/* 126 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*     */     }
/* 128 */     markDirty();
/* 129 */     if (!this.worldObj.isRemote) {
/* 130 */       this.worldObj.markBlockForUpdate(this.pos);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setInventorySlotContentsFromInfusion(int par1, ItemStack par2ItemStack)
/*     */   {
/* 136 */     this.inventory[par1] = par2ItemStack;
/* 137 */     markDirty();
/* 138 */     if (!this.worldObj.isRemote) {
/* 139 */       this.worldObj.markBlockForUpdate(this.pos);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 145 */     return hasCustomName() ? this.customName : "container.pedestal";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName()
/*     */   {
/* 150 */     return (this.customName != null) && (this.customName.length() > 0);
/*     */   }
/*     */   
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 155 */     return hasCustomName() ? new net.minecraft.util.ChatComponentText(getName()) : new net.minecraft.util.ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 164 */     NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
/* 165 */     this.inventory = new ItemStack[getSizeInventory()];
/*     */     
/* 167 */     for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */     {
/* 169 */       NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 170 */       byte b0 = nbttagcompound1.getByte("Slot");
/*     */       
/* 172 */       if ((b0 >= 0) && (b0 < this.inventory.length))
/*     */       {
/* 174 */         this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 181 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 183 */     for (int i = 0; i < this.inventory.length; i++)
/*     */     {
/* 185 */       if (this.inventory[i] != null)
/*     */       {
/* 187 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 188 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 189 */         this.inventory[i].writeToNBT(nbttagcompound1);
/* 190 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 194 */     nbttagcompound.setTag("Items", nbttaglist);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 203 */     super.readFromNBT(nbtCompound);
/*     */     
/* 205 */     if (nbtCompound.hasKey("CustomName"))
/*     */     {
/* 207 */       this.customName = nbtCompound.getString("CustomName");
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
/* 218 */     super.writeToNBT(nbtCompound);
/*     */     
/* 220 */     if (hasCustomName())
/*     */     {
/* 222 */       nbtCompound.setString("CustomName", this.customName);
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
/* 234 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*     */   {
/* 243 */     return this.worldObj.getTileEntity(this.pos) == this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void openInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/*     */   {
/* 256 */     return true;
/*     */   }
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side)
/*     */   {
/* 261 */     return slots;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canInsertItem(int par1, ItemStack par2ItemStack, EnumFacing par3)
/*     */   {
/* 267 */     return getStackInSlot(par1) == null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canExtractItem(int par1, ItemStack par2ItemStack, EnumFacing par3)
/*     */   {
/* 273 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/* 279 */     if (i == 11)
/*     */     {
/* 281 */       if (this.worldObj.isRemote) {
/* 282 */         Thaumcraft.proxy.getFX().drawBamf(this.pos.up(), 0.75F, 0.0F, 0.5F, true, true, false);
/*     */       }
/* 284 */       return true;
/*     */     }
/*     */     
/* 287 */     if (i == 12)
/*     */     {
/* 289 */       if (this.worldObj.isRemote) {
/* 290 */         Thaumcraft.proxy.getFX().drawBamf(this.pos.up(), true, true, false);
/*     */       }
/* 292 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 296 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getField(int id)
/*     */   {
/* 302 */     return 0;
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
/* 314 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\crafting\TilePedestal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */