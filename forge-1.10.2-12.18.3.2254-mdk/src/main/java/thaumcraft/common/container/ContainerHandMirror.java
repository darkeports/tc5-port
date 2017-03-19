/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.items.tools.ItemHandMirror;
/*     */ 
/*     */ public class ContainerHandMirror extends Container
/*     */ {
/*     */   private World worldObj;
/*     */   private int posX;
/*     */   private int posY;
/*     */   private int posZ;
/*  19 */   public IInventory input = new InventoryHandMirror(this);
/*  20 */   ItemStack mirror = null;
/*  21 */   EntityPlayer player = null;
/*     */   
/*     */   public ContainerHandMirror(InventoryPlayer iinventory, World par2World, int par3, int par4, int par5)
/*     */   {
/*  25 */     this.worldObj = par2World;
/*  26 */     this.posX = par3;
/*  27 */     this.posY = par4;
/*  28 */     this.posZ = par5;
/*  29 */     this.player = iinventory.player;
/*  30 */     this.mirror = iinventory.getCurrentItem();
/*     */     
/*  32 */     addSlotToContainer(new Slot(this.input, 0, 80, 24));
/*  33 */     bindPlayerInventory(iinventory);
/*  34 */     onCraftMatrixChanged(this.input);
/*     */   }
/*     */   
/*     */   protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
/*  38 */     for (int i = 0; i < 3; i++) {
/*  39 */       for (int j = 0; j < 9; j++) {
/*  40 */         addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  45 */     for (int i = 0; i < 9; i++) {
/*  46 */       addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onCraftMatrixChanged(IInventory par1IInventory)
/*     */   {
/*  53 */     if ((this.input.getStackInSlot(0) != null) && (ItemStack.areItemStacksEqual(this.input.getStackInSlot(0), this.mirror)))
/*     */     {
/*  55 */       this.player.openContainer = this.player.inventoryContainer;
/*     */     }
/*  57 */     else if ((!this.worldObj.isRemote) && (this.input.getStackInSlot(0) != null) && (this.player != null) && 
/*  58 */       (ItemHandMirror.transport(this.mirror, this.input.getStackInSlot(0), this.player, this.worldObj))) {
/*  59 */       this.input.setInventorySlotContents(0, null);
/*  60 */       for (int var4 = 0; var4 < this.crafters.size(); var4++)
/*     */       {
/*  62 */         ((net.minecraft.inventory.ICrafting)this.crafters.get(var4)).sendSlotContents(this, 0, null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn)
/*     */   {
/*     */     try
/*     */     {
/*  74 */       ItemStack s = getSlot(slotId).getStack();
/*  75 */       if ((s != null) && ((s.getItem() instanceof ItemHandMirror))) return null;
/*     */     }
/*     */     catch (Exception e) {}
/*  78 */     return super.slotClick(slotId, clickedButton, mode, playerIn);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
/*     */   {
/*  88 */     ItemStack stack = null;
/*  89 */     Slot slotObject = (Slot)this.inventorySlots.get(slot);
/*     */     
/*     */ 
/*  92 */     if ((slotObject != null) && (slotObject.getHasStack()) && (!(slotObject.getStack().getItem() instanceof ItemHandMirror))) {
/*  93 */       ItemStack stackInSlot = slotObject.getStack();
/*  94 */       stack = stackInSlot.copy();
/*     */       
/*  96 */       if (slot == 0) {
/*  97 */         if (!mergeItemStack(stackInSlot, 1, this.inventorySlots.size(), true, 64))
/*     */         {
/*  99 */           return null;
/*     */         }
/*     */       }
/* 102 */       else if (!mergeItemStack(stackInSlot, 0, 1, false, 64)) {
/* 103 */         return null;
/*     */       }
/*     */       
/* 106 */       if (stackInSlot.stackSize == 0) {
/* 107 */         slotObject.putStack(null);
/*     */       } else {
/* 109 */         slotObject.onSlotChanged();
/*     */       }
/*     */     }
/*     */     
/* 113 */     return stack;
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer var1)
/*     */   {
/* 118 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/*     */   {
/* 124 */     super.onContainerClosed(par1EntityPlayer);
/*     */     
/* 126 */     if (!this.worldObj.isRemote)
/*     */     {
/* 128 */       for (int var2 = 0; var2 < 1; var2++)
/*     */       {
/* 130 */         ItemStack var3 = this.input.removeStackFromSlot(var2);
/*     */         
/* 132 */         if (var3 != null)
/*     */         {
/* 134 */           par1EntityPlayer.dropPlayerItemWithRandomChoice(var3, false);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4, int limit)
/*     */   {
/* 142 */     boolean var5 = false;
/* 143 */     int var6 = par2;
/*     */     
/* 145 */     if (par4)
/*     */     {
/* 147 */       var6 = par3 - 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 153 */     if (par1ItemStack.isStackable())
/*     */     {
/* 155 */       while ((par1ItemStack.stackSize > 0) && (((!par4) && (var6 < par3)) || ((par4) && (var6 >= par2))))
/*     */       {
/* 157 */         Slot var7 = (Slot)this.inventorySlots.get(var6);
/* 158 */         ItemStack var8 = var7.getStack();
/*     */         
/* 160 */         if ((var8 != null) && (var8.getItem() == par1ItemStack.getItem()) && ((!par1ItemStack.getHasSubtypes()) || (par1ItemStack.getItemDamage() == var8.getItemDamage())) && (ItemStack.areItemStackTagsEqual(par1ItemStack, var8)))
/*     */         {
/*     */ 
/*     */ 
/* 164 */           int var9 = var8.stackSize + par1ItemStack.stackSize;
/*     */           
/* 166 */           if (var9 <= Math.min(par1ItemStack.getMaxStackSize(), limit))
/*     */           {
/* 168 */             par1ItemStack.stackSize = 0;
/* 169 */             var8.stackSize = var9;
/* 170 */             var7.onSlotChanged();
/* 171 */             var5 = true;
/*     */           }
/* 173 */           else if (var8.stackSize < Math.min(par1ItemStack.getMaxStackSize(), limit))
/*     */           {
/* 175 */             par1ItemStack.stackSize -= Math.min(par1ItemStack.getMaxStackSize(), limit) - var8.stackSize;
/* 176 */             var8.stackSize = Math.min(par1ItemStack.getMaxStackSize(), limit);
/* 177 */             var7.onSlotChanged();
/* 178 */             var5 = true;
/*     */           }
/*     */         }
/*     */         
/* 182 */         if (par4)
/*     */         {
/* 184 */           var6--;
/*     */         }
/*     */         else
/*     */         {
/* 188 */           var6++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 193 */     if (par1ItemStack.stackSize > 0)
/*     */     {
/* 195 */       if (par4)
/*     */       {
/* 197 */         var6 = par3 - 1;
/*     */       }
/*     */       else
/*     */       {
/* 201 */         var6 = par2;
/*     */       }
/*     */       
/* 204 */       while (((!par4) && (var6 < par3)) || ((par4) && (var6 >= par2)))
/*     */       {
/* 206 */         Slot var7 = (Slot)this.inventorySlots.get(var6);
/* 207 */         ItemStack var8 = var7.getStack();
/*     */         
/* 209 */         if (var8 == null)
/*     */         {
/* 211 */           ItemStack res = par1ItemStack.copy();
/* 212 */           res.stackSize = Math.min(res.stackSize, limit);
/* 213 */           var7.putStack(res);
/* 214 */           var7.onSlotChanged();
/* 215 */           par1ItemStack.stackSize -= res.stackSize;
/* 216 */           var5 = true;
/* 217 */           break;
/*     */         }
/*     */         
/* 220 */         if (par4)
/*     */         {
/* 222 */           var6--;
/*     */         }
/*     */         else
/*     */         {
/* 226 */           var6++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 231 */     return var5;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerHandMirror.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */