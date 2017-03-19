/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IAspectContainer;
/*     */ import thaumcraft.api.aspects.IEssentiaTransport;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ 
/*     */ public class TileThaumatoriumTop extends TileThaumcraft implements IAspectContainer, IEssentiaTransport, ISidedInventory, ITickable
/*     */ {
/*  20 */   public TileThaumatorium thaumatorium = null;
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/*  25 */     if (this.thaumatorium == null) {
/*  26 */       TileEntity tile = this.worldObj.getTileEntity(this.pos.down());
/*  27 */       if ((tile != null) && ((tile instanceof TileThaumatorium))) {
/*  28 */         this.thaumatorium = ((TileThaumatorium)tile);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int addToContainer(Aspect tt, int am)
/*     */   {
/*  38 */     if (this.thaumatorium == null) return am;
/*  39 */     return this.thaumatorium.addToContainer(tt, am);
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(Aspect tt, int am)
/*     */   {
/*  44 */     if (this.thaumatorium == null) return false;
/*  45 */     return this.thaumatorium.takeFromContainer(tt, am);
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(AspectList ot)
/*     */   {
/*  50 */     return false;
/*     */   }
/*     */   
/*     */   public boolean doesContainerContain(AspectList ot)
/*     */   {
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   public boolean doesContainerContainAmount(Aspect tt, int am)
/*     */   {
/*  60 */     if (this.thaumatorium == null) return false;
/*  61 */     return this.thaumatorium.doesContainerContainAmount(tt, am);
/*     */   }
/*     */   
/*     */   public int containerContains(Aspect tt)
/*     */   {
/*  66 */     if (this.thaumatorium == null) return 0;
/*  67 */     return this.thaumatorium.containerContains(tt);
/*     */   }
/*     */   
/*     */   public boolean doesContainerAccept(Aspect tag)
/*     */   {
/*  72 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isConnectable(EnumFacing face)
/*     */   {
/*  79 */     if (this.thaumatorium == null) return false;
/*  80 */     return this.thaumatorium.isConnectable(face);
/*     */   }
/*     */   
/*     */   public boolean canInputFrom(EnumFacing face)
/*     */   {
/*  85 */     if (this.thaumatorium == null) return false;
/*  86 */     return this.thaumatorium.canInputFrom(face);
/*     */   }
/*     */   
/*     */   public boolean canOutputTo(EnumFacing face)
/*     */   {
/*  91 */     return false;
/*     */   }
/*     */   
/*     */   public void setSuction(Aspect aspect, int amount)
/*     */   {
/*  96 */     if (this.thaumatorium == null) return;
/*  97 */     this.thaumatorium.setSuction(aspect, amount);
/*     */   }
/*     */   
/*     */   public Aspect getSuctionType(EnumFacing loc)
/*     */   {
/* 102 */     if (this.thaumatorium == null) return null;
/* 103 */     return this.thaumatorium.getSuctionType(loc);
/*     */   }
/*     */   
/*     */   public int getSuctionAmount(EnumFacing loc)
/*     */   {
/* 108 */     if (this.thaumatorium == null) return 0;
/* 109 */     return this.thaumatorium.getSuctionAmount(loc);
/*     */   }
/*     */   
/*     */   public Aspect getEssentiaType(EnumFacing loc)
/*     */   {
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   public int getEssentiaAmount(EnumFacing loc)
/*     */   {
/* 119 */     return 0;
/*     */   }
/*     */   
/*     */   public int takeEssentia(Aspect aspect, int amount, EnumFacing face)
/*     */   {
/* 124 */     if (this.thaumatorium == null) return 0;
/* 125 */     return this.thaumatorium.takeEssentia(aspect, amount, face);
/*     */   }
/*     */   
/*     */   public int addEssentia(Aspect aspect, int amount, EnumFacing face)
/*     */   {
/* 130 */     if (this.thaumatorium == null) return 0;
/* 131 */     return this.thaumatorium.addEssentia(aspect, amount, face);
/*     */   }
/*     */   
/*     */   public int getMinimumSuction()
/*     */   {
/* 136 */     return 0;
/*     */   }
/*     */   
/*     */   public AspectList getAspects()
/*     */   {
/* 141 */     if (this.thaumatorium == null) return null;
/* 142 */     return this.thaumatorium.essentia;
/*     */   }
/*     */   
/*     */   public void setAspects(AspectList aspects)
/*     */   {
/* 147 */     if (this.thaumatorium == null) return;
/* 148 */     this.thaumatorium.setAspects(aspects);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getSizeInventory()
/*     */   {
/* 156 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack getStackInSlot(int par1)
/*     */   {
/* 162 */     if (this.thaumatorium == null) return null;
/* 163 */     return this.thaumatorium.getStackInSlot(par1);
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack decrStackSize(int par1, int par2)
/*     */   {
/* 169 */     if (this.thaumatorium == null) return null;
/* 170 */     return this.thaumatorium.decrStackSize(par1, par2);
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack removeStackFromSlot(int par1)
/*     */   {
/* 176 */     if (this.thaumatorium == null) return null;
/* 177 */     return this.thaumatorium.removeStackFromSlot(par1);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
/*     */   {
/* 183 */     if (this.thaumatorium == null) return;
/* 184 */     this.thaumatorium.setInventorySlotContents(par1, par2ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getInventoryStackLimit()
/*     */   {
/* 191 */     return 64;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/*     */   {
/* 197 */     return this.worldObj.getTileEntity(this.pos) == this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/*     */   {
/* 206 */     return true;
/*     */   }
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side)
/*     */   {
/* 211 */     return new int[] { 0 };
/*     */   }
/*     */   
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
/*     */   {
/* 216 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
/*     */   {
/* 221 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void openInventory(EntityPlayer player) {}
/*     */   
/*     */ 
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */   
/*     */   public int getField(int id)
/*     */   {
/* 232 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount()
/*     */   {
/* 240 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void clear() {}
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/* 249 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasCustomName()
/*     */   {
/* 254 */     return false;
/*     */   }
/*     */   
/*     */   public IChatComponent getDisplayName()
/*     */   {
/* 259 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileThaumatoriumTop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */