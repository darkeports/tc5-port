/*     */ package thaumcraft.common.entities.construct.golem.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.golems.seals.ISealConfigArea;
/*     */ import thaumcraft.api.golems.seals.ISealConfigFilter;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles;
/*     */ import thaumcraft.api.golems.seals.ISealEntity;
/*     */ import thaumcraft.api.golems.seals.ISealGui;
/*     */ import thaumcraft.common.container.InventoryFake;
/*     */ import thaumcraft.common.container.SlotGhost;
/*     */ 
/*     */ public class SealBaseContainer extends Container
/*     */ {
/*     */   private World worldObj;
/*  26 */   ISealEntity seal = null;
/*  27 */   EntityPlayer player = null;
/*     */   InventoryFake temp;
/*     */   int[] categories;
/*  30 */   int category = -1;
/*     */   InventoryPlayer pinv;
/*     */   
/*     */   public SealBaseContainer(InventoryPlayer iinventory, World par2World, ISealEntity seal)
/*     */   {
/*  35 */     this.worldObj = par2World;
/*  36 */     this.player = iinventory.player;
/*  37 */     this.pinv = iinventory;
/*  38 */     this.seal = seal;
/*     */     
/*  40 */     if ((seal.getSeal() instanceof ISealGui)) {
/*  41 */       this.categories = ((ISealGui)seal.getSeal()).getGuiCategories();
/*     */     } else {
/*  43 */       this.categories = new int[] { 0 };
/*     */     }
/*     */     
/*  46 */     setupCategories();
/*     */   }
/*     */   
/*     */   void setupCategories()
/*     */   {
/*  51 */     this.inventoryItemStacks = Lists.newArrayList();
/*  52 */     this.inventorySlots = Lists.newArrayList();
/*     */     
/*  54 */     this.t = 0;
/*     */     
/*  56 */     if (this.category < 0) { this.category = this.categories[0];
/*     */     }
/*  58 */     switch (this.category) {
/*     */     case 1: 
/*  60 */       setupFilterInventory();
/*     */     }
/*     */     
/*     */     
/*  64 */     bindPlayerInventory(this.pinv);
/*     */   }
/*     */   
/*  67 */   int t = 0;
/*     */   private byte lastPriority;
/*     */   
/*  70 */   private void setupFilterInventory() { if ((this.seal.getSeal() instanceof ISealConfigFilter)) {
/*  71 */       int s = ((ISealConfigFilter)this.seal.getSeal()).getFilterSize();
/*  72 */       int sx = 16 + (s - 1) % 3 * 12;
/*  73 */       int sy = 16 + (s - 1) / 3 * 12;
/*  74 */       int middleX = 88;
/*  75 */       int middleY = 72;
/*  76 */       this.temp = new InventoryFake(((ISealConfigFilter)this.seal.getSeal()).getInv());
/*  77 */       for (int a = 0; a < s; a++) {
/*  78 */         int x = a % 3;
/*  79 */         int y = a / 3;
/*  80 */         addSlotToContainer(new SlotGhost(this.temp, a, middleX + x * 24 - sx + 8, middleY + y * 24 - sy + 8));
/*  81 */         this.t += 1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
/*  87 */     for (int i = 0; i < 3; i++) {
/*  88 */       for (int j = 0; j < 9; j++) {
/*  89 */         addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 150 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  94 */     for (int i = 0; i < 9; i++) {
/*  95 */       addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 208));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer var1)
/*     */   {
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   public boolean enchantItem(EntityPlayer player, int par2)
/*     */   {
/* 106 */     if ((par2 >= 0) && (par2 < this.categories.length)) {
/* 107 */       this.category = this.categories[par2];
/* 108 */       setupCategories();
/* 109 */       return true;
/*     */     }
/*     */     
/* 112 */     if ((this.category == 3) && ((this.seal.getSeal() instanceof ISealConfigToggles)) && (par2 >= 30) && (par2 < 30 + ((ISealConfigToggles)this.seal.getSeal()).getToggles().length))
/*     */     {
/* 114 */       ISealConfigToggles cp = (ISealConfigToggles)this.seal.getSeal();
/* 115 */       cp.setToggle(par2 - 30, true);
/* 116 */       return true;
/*     */     }
/*     */     
/* 119 */     if ((this.category == 3) && ((this.seal.getSeal() instanceof ISealConfigToggles)) && (par2 >= 60) && (par2 < 60 + ((ISealConfigToggles)this.seal.getSeal()).getToggles().length))
/*     */     {
/* 121 */       ISealConfigToggles cp = (ISealConfigToggles)this.seal.getSeal();
/* 122 */       cp.setToggle(par2 - 60, false);
/* 123 */       return true;
/*     */     }
/*     */     
/* 126 */     if ((this.category == 0) && (par2 >= 25) && (par2 <= 26)) {
/* 127 */       this.seal.setLocked(par2 == 25);
/* 128 */       return true;
/*     */     }
/*     */     
/* 131 */     if ((par2 >= 27) && (par2 <= 28)) {
/* 132 */       this.seal.setRedstoneSensitive(par2 == 27);
/* 133 */       return true;
/*     */     }
/*     */     
/* 136 */     if ((this.category == 1) && ((this.seal.getSeal() instanceof ISealConfigFilter)) && (par2 >= 20) && (par2 <= 21)) {
/* 137 */       ISealConfigFilter cp = (ISealConfigFilter)this.seal.getSeal();
/* 138 */       cp.setBlacklist(par2 == 20);
/* 139 */       return true;
/*     */     }
/*     */     
/* 142 */     if ((par2 == 80) && (this.seal.getPriority() > -5)) {
/* 143 */       this.seal.setPriority((byte)(this.seal.getPriority() - 1));
/* 144 */       return true;
/*     */     }
/* 146 */     if ((par2 == 81) && (this.seal.getPriority() < 5)) {
/* 147 */       this.seal.setPriority((byte)(this.seal.getPriority() + 1));
/* 148 */       return true;
/*     */     }
/*     */     
/* 151 */     if ((par2 == 82) && (this.seal.getColor() > 0)) {
/* 152 */       this.seal.setColor((byte)(this.seal.getColor() - 1));
/* 153 */       return true;
/*     */     }
/* 155 */     if ((par2 == 83) && (this.seal.getColor() < 16)) {
/* 156 */       this.seal.setColor((byte)(this.seal.getColor() + 1));
/* 157 */       return true;
/*     */     }
/*     */     
/* 160 */     if ((this.seal.getSeal() instanceof ISealConfigArea)) {
/* 161 */       if ((par2 == 90) && (this.seal.getArea().getY() > 1)) {
/* 162 */         this.seal.setArea(this.seal.getArea().add(0, -1, 0));
/* 163 */         return true;
/*     */       }
/* 165 */       if ((par2 == 91) && (this.seal.getArea().getY() < 8)) {
/* 166 */         this.seal.setArea(this.seal.getArea().add(0, 1, 0));
/* 167 */         return true;
/*     */       }
/*     */       
/* 170 */       if ((par2 == 92) && (this.seal.getArea().getX() > 1)) {
/* 171 */         this.seal.setArea(this.seal.getArea().add(-1, 0, 0));
/* 172 */         return true;
/*     */       }
/* 174 */       if ((par2 == 93) && (this.seal.getArea().getX() < 8)) {
/* 175 */         this.seal.setArea(this.seal.getArea().add(1, 0, 0));
/* 176 */         return true;
/*     */       }
/*     */       
/* 179 */       if ((par2 == 94) && (this.seal.getArea().getZ() > 1)) {
/* 180 */         this.seal.setArea(this.seal.getArea().add(0, 0, -1));
/* 181 */         return true;
/*     */       }
/* 183 */       if ((par2 == 95) && (this.seal.getArea().getZ() < 8)) {
/* 184 */         this.seal.setArea(this.seal.getArea().add(0, 0, 1));
/* 185 */         return true;
/*     */       }
/*     */     }
/* 188 */     return super.enchantItem(player, par2);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onCraftGuiOpened(ICrafting par1ICrafting)
/*     */   {
/* 194 */     super.onCraftGuiOpened(par1ICrafting);
/* 195 */     par1ICrafting.sendProgressBarUpdate(this, 0, this.seal.getPriority());
/* 196 */     par1ICrafting.sendProgressBarUpdate(this, 4, this.seal.getColor());
/*     */   }
/*     */   
/*     */ 
/*     */   private byte lastColor;
/*     */   
/*     */   private int lastAreaX;
/*     */   
/*     */   private int lastAreaY;
/*     */   private int lastAreaZ;
/*     */   public void detectAndSendChanges()
/*     */   {
/* 208 */     super.detectAndSendChanges();
/*     */     
/* 210 */     for (int i = 0; i < this.crafters.size(); i++)
/*     */     {
/* 212 */       ICrafting icrafting = (ICrafting)this.crafters.get(i);
/*     */       
/* 214 */       if (this.lastPriority != this.seal.getPriority())
/*     */       {
/* 216 */         icrafting.sendProgressBarUpdate(this, 0, this.seal.getPriority());
/*     */       }
/*     */       
/* 219 */       if (this.lastAreaX != this.seal.getArea().getX()) icrafting.sendProgressBarUpdate(this, 1, this.seal.getArea().getX());
/* 220 */       if (this.lastAreaY != this.seal.getArea().getY()) icrafting.sendProgressBarUpdate(this, 2, this.seal.getArea().getY());
/* 221 */       if (this.lastAreaZ != this.seal.getArea().getZ()) { icrafting.sendProgressBarUpdate(this, 3, this.seal.getArea().getZ());
/*     */       }
/* 223 */       if (this.lastColor != this.seal.getColor())
/*     */       {
/* 225 */         icrafting.sendProgressBarUpdate(this, 4, this.seal.getColor());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 230 */     this.lastPriority = this.seal.getPriority();
/* 231 */     this.lastColor = this.seal.getColor();
/* 232 */     this.lastAreaX = this.seal.getArea().getX();
/* 233 */     this.lastAreaY = this.seal.getArea().getY();
/* 234 */     this.lastAreaZ = this.seal.getArea().getZ();
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void updateProgressBar(int par1, int par2)
/*     */   {
/* 241 */     if (par1 == 0) this.seal.setPriority((byte)par2);
/* 242 */     if (par1 == 1) this.seal.setArea(new BlockPos(par2, this.seal.getArea().getY(), this.seal.getArea().getZ()));
/* 243 */     if (par1 == 2) this.seal.setArea(new BlockPos(this.seal.getArea().getX(), par2, this.seal.getArea().getZ()));
/* 244 */     if (par1 == 3) this.seal.setArea(new BlockPos(this.seal.getArea().getX(), this.seal.getArea().getY(), par2));
/* 245 */     if (par1 == 4) this.seal.setColor((byte)par2);
/*     */   }
/*     */   
/*     */   public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn)
/*     */   {
/* 250 */     if (slotId >= 0)
/*     */     {
/* 252 */       Slot slot = (Slot)this.inventorySlots.get(slotId);
/* 253 */       InventoryPlayer inventoryplayer = playerIn.inventory;
/* 254 */       ItemStack ic = null;
/* 255 */       if (inventoryplayer.getItemStack() != null) { ic = inventoryplayer.getItemStack().copy();
/*     */       }
/* 257 */       if ((slot != null) && ((slot instanceof SlotGhost))) {
/* 258 */         boolean filter = ((ISealConfigFilter)this.seal.getSeal()).hasStacksizeLimiters();
/* 259 */         if (!playerIn.worldObj.isRemote) {
/* 260 */           if (clickedButton == 1) {
/* 261 */             if (!filter) {
/* 262 */               slot.putStack((ItemStack)null);
/*     */             }
/* 264 */             else if (ic == null) {
/* 265 */               if (mode == 1) {
/* 266 */                 slot.putStack((ItemStack)null);
/*     */               }
/* 268 */               else if (slot.getHasStack()) {
/* 269 */                 slot.getStack().stackSize -= 1;
/* 270 */                 if (slot.getStack().stackSize < 0) {
/* 271 */                   slot.putStack(null);
/*     */                 }
/*     */               }
/*     */             }
/* 275 */             else if ((slot.getHasStack()) && (slot.getStack().stackSize == 0)) {
/* 276 */               slot.putStack(null);
/*     */             }
/* 278 */             else if ((slot.getHasStack()) && (ItemStack.areItemsEqual(ic, slot.getStack())) && (ItemStack.areItemStackTagsEqual(ic, slot.getStack()))) {
/* 279 */               slot.getStack().stackSize -= ic.stackSize;
/*     */             }
/*     */             
/*     */ 
/*     */           }
/* 284 */           else if (ic == null) {
/* 285 */             if ((filter) && (slot.getHasStack())) slot.getStack().stackSize += 1;
/*     */           } else {
/* 287 */             if (!filter) {
/* 288 */               ic.stackSize = 1;
/*     */             }
/* 290 */             else if ((slot.getHasStack()) && (ItemStack.areItemsEqual(ic, slot.getStack())) && (ItemStack.areItemStackTagsEqual(ic, slot.getStack()))) {
/* 291 */               ic.stackSize += slot.getStack().stackSize;
/*     */             } else {
/* 293 */               ic.stackSize = 0;
/*     */             }
/*     */             
/* 296 */             slot.putStack(ic);
/*     */           }
/*     */           
/*     */ 
/* 300 */           if ((slot.getHasStack()) && (slot.getStack().stackSize < 0)) slot.getStack().stackSize = 0;
/* 301 */           detectAndSendChanges();
/*     */         }
/*     */         
/* 304 */         return null;
/*     */       }
/*     */     }
/*     */     
/* 308 */     return super.slotClick(slotId, clickedButton, mode, playerIn);
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*     */   {
/* 314 */     ItemStack itemstack = null;
/* 315 */     Slot slot = (Slot)this.inventorySlots.get(par2);
/*     */     
/* 317 */     if ((slot != null) && (slot.getHasStack()))
/*     */     {
/* 319 */       ItemStack itemstack1 = slot.getStack();
/* 320 */       itemstack = itemstack1.copy();
/*     */       
/* 322 */       if (itemstack1.stackSize == 0)
/*     */       {
/* 324 */         slot.putStack((ItemStack)null);
/*     */       }
/*     */       else
/*     */       {
/* 328 */         slot.onSlotChanged();
/*     */       }
/*     */       
/* 331 */       if (itemstack1.stackSize == itemstack.stackSize)
/*     */       {
/* 333 */         return null;
/*     */       }
/*     */       
/* 336 */       slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
/*     */     }
/*     */     
/* 339 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\gui\SealBaseContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */