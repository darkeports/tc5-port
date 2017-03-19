/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ChestGenHooks;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.common.entities.monster.EntityPech;
/*     */ 
/*     */ 
/*     */ public class ContainerPech
/*     */   extends Container
/*     */ {
/*     */   private EntityPech pech;
/*     */   private InventoryPech inventory;
/*     */   private EntityPlayer player;
/*     */   private final World theWorld;
/*     */   
/*     */   public ContainerPech(InventoryPlayer par1InventoryPlayer, World par3World, EntityPech par2IMerchant)
/*     */   {
/*  32 */     this.pech = par2IMerchant;
/*  33 */     this.theWorld = par3World;
/*  34 */     this.player = par1InventoryPlayer.player;
/*  35 */     this.inventory = new InventoryPech(par1InventoryPlayer.player, par2IMerchant, this);
/*  36 */     this.pech.trading = true;
/*  37 */     addSlotToContainer(new Slot(this.inventory, 0, 36, 29));
/*     */     
/*     */ 
/*     */ 
/*  41 */     for (int i = 0; i < 2; i++) {
/*  42 */       for (int j = 0; j < 2; j++) {
/*  43 */         addSlotToContainer(new SlotOutput(this.inventory, 1 + j + i * 2, 106 + 18 * j, 20 + 18 * i));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  48 */     for (i = 0; i < 3; i++)
/*     */     {
/*  50 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  52 */         addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  56 */     for (i = 0; i < 9; i++)
/*     */     {
/*  58 */       addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public InventoryPech getMerchantInventory()
/*     */   {
/*  64 */     return this.inventory;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onCraftGuiOpened(ICrafting par1ICrafting)
/*     */   {
/*  70 */     super.onCraftGuiOpened(par1ICrafting);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void detectAndSendChanges()
/*     */   {
/*  81 */     super.detectAndSendChanges();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
/*     */   {
/*  88 */     if (par2 == 0) {
/*  89 */       generateContents();
/*  90 */       return true;
/*     */     }
/*  92 */     return super.enchantItem(par1EntityPlayer, par2);
/*     */   }
/*     */   
/*  95 */   ChestGenHooks chest = ChestGenHooks.getInfo("dungeonChest");
/*     */   
/*     */   private boolean hasStuffInPack() {
/*  98 */     for (ItemStack stack : this.pech.loot) {
/*  99 */       if ((stack != null) && (stack.stackSize > 0))
/* 100 */         return true;
/*     */     }
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   private void generateContents()
/*     */   {
/* 107 */     if ((!this.theWorld.isRemote) && (this.inventory.getStackInSlot(0) != null) && (this.inventory.getStackInSlot(1) == null) && (this.inventory.getStackInSlot(2) == null) && (this.inventory.getStackInSlot(3) == null) && (this.inventory.getStackInSlot(4) == null))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 112 */       if (this.pech.isValued(this.inventory.getStackInSlot(0))) {
/* 113 */         int value = this.pech.getValue(this.inventory.getStackInSlot(0));
/* 114 */         if (this.theWorld.rand.nextInt(100) <= value / 2) {
/* 115 */           this.pech.setTamed(false);
/* 116 */           this.pech.updateAINextTick = true;
/* 117 */           this.pech.playSound("thaumcraft:pech_trade", 0.4F, 1.0F);
/*     */         }
/*     */         
/* 120 */         if (this.theWorld.rand.nextInt(5) == 0) {
/* 121 */           value += this.theWorld.rand.nextInt(3);
/*     */         }
/* 123 */         else if (this.theWorld.rand.nextBoolean()) {
/* 124 */           value -= this.theWorld.rand.nextInt(3);
/*     */         }
/*     */         
/* 127 */         ArrayList<List> pos = (ArrayList)EntityPech.tradeInventory.get(Integer.valueOf(this.pech.getPechType()));
/* 128 */         while (value > 0) {
/* 129 */           int am = Math.min(5, Math.max((value + 1) / 2, this.theWorld.rand.nextInt(value) + 1));
/*     */           
/*     */ 
/*     */ 
/* 133 */           value -= am;
/*     */           
/* 135 */           if ((am == 1) && (this.theWorld.rand.nextBoolean()) && (hasStuffInPack())) {
/* 136 */             ArrayList<Integer> loot = new ArrayList();
/* 137 */             for (int a = 0; a < this.pech.loot.length; a++) {
/* 138 */               if ((this.pech.loot[a] != null) && (this.pech.loot[a].stackSize > 0))
/* 139 */                 loot.add(Integer.valueOf(a));
/*     */             }
/* 141 */             int r = ((Integer)loot.get(this.theWorld.rand.nextInt(loot.size()))).intValue();
/* 142 */             ItemStack is = this.pech.loot[r].copy();
/* 143 */             is.stackSize = 1;
/* 144 */             mergeItemStack(is, 1, 5, false);
/* 145 */             this.pech.loot[r].stackSize -= 1;
/* 146 */             if (this.pech.loot[r].stackSize <= 0) this.pech.loot[r] = null;
/*     */           }
/* 148 */           else if ((am >= 4) && (this.theWorld.rand.nextBoolean())) {
/* 149 */             List<WeightedRandomChestContent> contents = this.chest.getItems(this.theWorld.rand);
/* 150 */             WeightedRandomChestContent wc = null;
/* 151 */             int cc = 0;
/*     */             do {
/* 153 */               wc = (WeightedRandomChestContent)contents.get(this.theWorld.rand.nextInt(contents.size()));
/* 154 */               cc++;
/* 155 */             } while ((cc < 50) && ((wc.theItemId == null) || (wc.itemWeight > 5)));
/* 156 */             if ((wc == null) || (wc.theItemId == null)) {
/* 157 */               value += am;
/*     */             }
/*     */             else {
/* 160 */               ItemStack is = wc.theItemId.copy();
/* 161 */               is.onCrafting(this.theWorld, this.player, 0);
/* 162 */               mergeItemStack(is, 1, 5, false);
/*     */             }
/* 164 */           } else { List it = null;
/*     */             do {
/* 166 */               it = (List)pos.get(this.theWorld.rand.nextInt(pos.size()));
/* 167 */             } while (((Integer)it.get(0)).intValue() != am);
/* 168 */             ItemStack is = ((ItemStack)it.get(1)).copy();
/* 169 */             is.onCrafting(this.theWorld, this.player, 0);
/* 170 */             mergeItemStack(is, 1, 5, false);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 175 */         this.inventory.decrStackSize(0, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void updateProgressBar(int par1, int par2) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*     */   {
/* 189 */     return this.pech.isTamed();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*     */   {
/* 198 */     ItemStack itemstack = null;
/* 199 */     Slot slot = (Slot)this.inventorySlots.get(par2);
/*     */     
/* 201 */     if ((slot != null) && (slot.getHasStack()))
/*     */     {
/* 203 */       ItemStack itemstack1 = slot.getStack();
/* 204 */       itemstack = itemstack1.copy();
/*     */       
/* 206 */       if (par2 == 0)
/*     */       {
/* 208 */         if (!mergeItemStack(itemstack1, 5, 41, true))
/*     */         {
/* 210 */           return null;
/*     */         }
/*     */         
/*     */       }
/* 214 */       else if ((par2 >= 1) && (par2 < 5))
/*     */       {
/* 216 */         if (!mergeItemStack(itemstack1, 5, 41, true))
/*     */         {
/* 218 */           return null;
/*     */         }
/*     */       }
/* 221 */       else if (par2 != 0)
/*     */       {
/* 223 */         if ((par2 >= 5) && (par2 < 41))
/*     */         {
/*     */ 
/* 226 */           if (!mergeItemStack(itemstack1, 0, 1, true))
/*     */           {
/* 228 */             return null;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 233 */       if (itemstack1.stackSize == 0)
/*     */       {
/* 235 */         slot.putStack((ItemStack)null);
/*     */       }
/*     */       else
/*     */       {
/* 239 */         slot.onSlotChanged();
/*     */       }
/*     */       
/* 242 */       if (itemstack1.stackSize == itemstack.stackSize)
/*     */       {
/* 244 */         return null;
/*     */       }
/*     */       
/* 247 */       slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
/*     */     }
/*     */     
/* 250 */     return itemstack;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onContainerClosed(EntityPlayer par1EntityPlayer)
/*     */   {
/* 259 */     super.onContainerClosed(par1EntityPlayer);
/* 260 */     this.pech.trading = false;
/*     */     
/* 262 */     if (!this.theWorld.isRemote)
/*     */     {
/*     */ 
/* 265 */       for (int a = 0; a < 5; a++)
/*     */       {
/* 267 */         ItemStack itemstack = this.inventory.removeStackFromSlot(a);
/*     */         
/* 269 */         if (itemstack != null)
/*     */         {
/* 271 */           EntityItem ei = par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
/* 272 */           if (ei != null) {
/* 273 */             ei.setThrower("PechDrop");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_)
/*     */   {
/* 285 */     boolean flag1 = false;
/* 286 */     int k = p_75135_2_;
/*     */     
/* 288 */     if (p_75135_4_)
/*     */     {
/* 290 */       k = p_75135_3_ - 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 296 */     if (p_75135_1_.isStackable())
/*     */     {
/* 298 */       while ((p_75135_1_.stackSize > 0) && (((!p_75135_4_) && (k < p_75135_3_)) || ((p_75135_4_) && (k >= p_75135_2_))))
/*     */       {
/* 300 */         Slot slot = (Slot)this.inventorySlots.get(k);
/* 301 */         ItemStack itemstack1 = slot.getStack();
/*     */         
/* 303 */         if ((itemstack1 != null) && (itemstack1.getItem() == p_75135_1_.getItem()) && ((!p_75135_1_.getHasSubtypes()) || (p_75135_1_.getItemDamage() == itemstack1.getItemDamage())) && (ItemStack.areItemStackTagsEqual(p_75135_1_, itemstack1)))
/*     */         {
/* 305 */           int l = itemstack1.stackSize + p_75135_1_.stackSize;
/*     */           
/* 307 */           if (l <= p_75135_1_.getMaxStackSize())
/*     */           {
/* 309 */             p_75135_1_.stackSize = 0;
/* 310 */             itemstack1.stackSize = l;
/* 311 */             slot.onSlotChanged();
/* 312 */             flag1 = true;
/*     */           }
/* 314 */           else if (itemstack1.stackSize < p_75135_1_.getMaxStackSize())
/*     */           {
/* 316 */             p_75135_1_.stackSize -= p_75135_1_.getMaxStackSize() - itemstack1.stackSize;
/* 317 */             itemstack1.stackSize = p_75135_1_.getMaxStackSize();
/* 318 */             slot.onSlotChanged();
/* 319 */             flag1 = true;
/*     */           }
/*     */         }
/*     */         
/* 323 */         if (p_75135_4_)
/*     */         {
/* 325 */           k--;
/*     */         }
/*     */         else
/*     */         {
/* 329 */           k++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 334 */     if (p_75135_1_.stackSize > 0)
/*     */     {
/* 336 */       if (p_75135_4_)
/*     */       {
/* 338 */         k = p_75135_3_ - 1;
/*     */       }
/*     */       else
/*     */       {
/* 342 */         k = p_75135_2_;
/*     */       }
/*     */       
/* 345 */       while (((!p_75135_4_) && (k < p_75135_3_)) || ((p_75135_4_) && (k >= p_75135_2_)))
/*     */       {
/* 347 */         Slot slot = (Slot)this.inventorySlots.get(k);
/* 348 */         ItemStack itemstack1 = slot.getStack();
/*     */         
/* 350 */         if (itemstack1 == null)
/*     */         {
/* 352 */           slot.putStack(p_75135_1_.copy());
/* 353 */           slot.onSlotChanged();
/* 354 */           p_75135_1_.stackSize = 0;
/* 355 */           flag1 = true;
/* 356 */           break;
/*     */         }
/*     */         
/* 359 */         if (p_75135_4_)
/*     */         {
/* 361 */           k--;
/*     */         }
/*     */         else
/*     */         {
/* 365 */           k++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 370 */     return flag1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerPech.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */