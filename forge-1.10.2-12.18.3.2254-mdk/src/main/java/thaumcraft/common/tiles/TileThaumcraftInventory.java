/*     */ package thaumcraft.common.tiles;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ 
/*     */ public class TileThaumcraftInventory extends TileThaumcraft implements ISidedInventory
/*     */ {
/*  17 */   protected ItemStack[] itemStacks = new ItemStack[1];
/*     */   protected String customName;
/*  19 */   protected int[] syncedSlots = new int[0];
/*     */   
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/*  24 */     return this.itemStacks.length;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack getStackInSlot(int par1)
/*     */   {
/*  30 */     return this.itemStacks[par1];
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack decrStackSize(int par1, int par2)
/*     */   {
/*  36 */     if (this.itemStacks[par1] != null)
/*     */     {
/*     */ 
/*     */ 
/*  40 */       if (this.itemStacks[par1].stackSize <= par2)
/*     */       {
/*  42 */         ItemStack itemstack = this.itemStacks[par1];
/*  43 */         this.itemStacks[par1] = null;
/*  44 */         markDirty();
/*  45 */         return itemstack;
/*     */       }
/*     */       
/*     */ 
/*  49 */       ItemStack itemstack = this.itemStacks[par1].splitStack(par2);
/*     */       
/*  51 */       if (this.itemStacks[par1].stackSize == 0)
/*     */       {
/*  53 */         this.itemStacks[par1] = null;
/*     */       }
/*  55 */       markDirty();
/*  56 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  61 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ItemStack removeStackFromSlot(int par1)
/*     */   {
/*  68 */     if (this.itemStacks[par1] != null)
/*     */     {
/*  70 */       ItemStack itemstack = this.itemStacks[par1];
/*  71 */       this.itemStacks[par1] = null;
/*  72 */       markDirty();
/*  73 */       return itemstack;
/*     */     }
/*     */     
/*     */ 
/*  77 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*     */   {
/*  84 */     this.itemStacks[par1] = par2ItemStack;
/*     */     
/*  86 */     if ((par2ItemStack != null) && (par2ItemStack.stackSize > getInventoryStackLimit()))
/*     */     {
/*  88 */       par2ItemStack.stackSize = getInventoryStackLimit();
/*     */     }
/*  90 */     markDirty();
/*     */   }
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/*  96 */     return hasCustomName() ? this.customName : "container.thaumcraft";
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasCustomName()
/*     */   {
/* 102 */     return (this.customName != null) && (this.customName.length() > 0);
/*     */   }
/*     */   
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 107 */     return hasCustomName() ? new ChatComponentText(getName()) : new ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean isSyncedSlot(int slot)
/*     */   {
/* 113 */     for (int s : this.syncedSlots) {
/* 114 */       if (s == slot) return true;
/*     */     }
/* 116 */     return false;
/*     */   }
/*     */   
/*     */   public void readCustomNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 121 */     NBTTagList nbttaglist = nbtCompound.getTagList("ItemsSynced", 10);
/* 122 */     this.itemStacks = new ItemStack[getSizeInventory()];
/* 123 */     for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */     {
/* 125 */       if (isSyncedSlot(i)) {
/* 126 */         NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 127 */         byte b0 = nbttagcompound1.getByte("Slot");
/*     */         
/* 129 */         if ((b0 >= 0) && (b0 < this.itemStacks.length))
/*     */         {
/* 131 */           this.itemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 139 */     NBTTagList nbttaglist = new NBTTagList();
/* 140 */     for (int i = 0; i < this.itemStacks.length; i++)
/*     */     {
/* 142 */       if ((this.itemStacks[i] != null) && (isSyncedSlot(i)))
/*     */       {
/* 144 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 145 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 146 */         this.itemStacks[i].writeToNBT(nbttagcompound1);
/* 147 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/* 150 */     nbtCompound.setTag("ItemsSynced", nbttaglist);
/*     */   }
/*     */   
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 156 */     super.readFromNBT(nbtCompound);
/*     */     
/* 158 */     if (nbtCompound.hasKey("CustomName"))
/*     */     {
/* 160 */       this.customName = nbtCompound.getString("CustomName");
/*     */     }
/*     */     
/* 163 */     NBTTagList nbttaglist = nbtCompound.getTagList("Items", 10);
/* 164 */     for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */     {
/* 166 */       if (!isSyncedSlot(i)) {
/* 167 */         NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 168 */         byte b0 = nbttagcompound1.getByte("Slot");
/*     */         
/* 170 */         if ((b0 >= 0) && (b0 < this.itemStacks.length))
/*     */         {
/* 172 */           this.itemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbtCompound)
/*     */   {
/* 182 */     super.writeToNBT(nbtCompound);
/*     */     
/* 184 */     if (hasCustomName())
/*     */     {
/* 186 */       nbtCompound.setString("CustomName", this.customName);
/*     */     }
/*     */     
/* 189 */     NBTTagList nbttaglist = new NBTTagList();
/* 190 */     for (int i = 0; i < this.itemStacks.length; i++)
/*     */     {
/* 192 */       if ((this.itemStacks[i] != null) && (!isSyncedSlot(i)))
/*     */       {
/* 194 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 195 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 196 */         this.itemStacks[i].writeToNBT(nbttagcompound1);
/* 197 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/* 200 */     nbtCompound.setTag("Items", nbttaglist);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getInventoryStackLimit()
/*     */   {
/* 206 */     return 64;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*     */   {
/* 212 */     return this.worldObj.getTileEntity(getPos()) == this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/*     */   {
/* 219 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public int[] getSlotsForFace(EnumFacing par1)
/*     */   {
/* 225 */     return new int[] { 0 };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void openInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public int getField(int id)
/*     */   {
/* 240 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount()
/*     */   {
/* 248 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear() {}
/*     */   
/*     */ 
/*     */   public boolean canInsertItem(int par1, ItemStack par2ItemStack, EnumFacing par3)
/*     */   {
/* 258 */     return isItemValidForSlot(par1, par2ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canExtractItem(int par1, ItemStack par2ItemStack, EnumFacing par3)
/*     */   {
/* 264 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\TileThaumcraftInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */