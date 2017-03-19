/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.lib.research.ResearchNoteData;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ import thaumcraft.common.tiles.crafting.TileResearchTable;
/*     */ 
/*     */ public class ContainerResearchTable extends Container
/*     */ {
/*     */   public TileResearchTable tileEntity;
/*     */   String[] aspects;
/*     */   EntityPlayer player;
/*     */   
/*     */   public ContainerResearchTable(InventoryPlayer iinventory, TileResearchTable iinventory1)
/*     */   {
/*  24 */     this.player = iinventory.player;
/*  25 */     this.tileEntity = iinventory1;
/*  26 */     this.aspects = ((String[])Aspect.aspects.keySet().toArray(new String[0]));
/*     */     
/*  28 */     addSlotToContainer(new SlotLimitedByClass(thaumcraft.api.items.IScribeTools.class, iinventory1, 0, 14, 10));
/*  29 */     addSlotToContainer(new SlotLimitedByClass(thaumcraft.common.items.resources.ItemResearchNotes.class, iinventory1, 1, 70, 10));
/*     */     
/*  31 */     bindPlayerInventory(iinventory);
/*     */   }
/*     */   
/*     */   protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
/*  35 */     for (int i = 0; i < 3; i++) {
/*  36 */       for (int j = 0; j < 9; j++) {
/*  37 */         addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 48 + j * 18, 175 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  42 */     for (int i = 0; i < 9; i++) {
/*  43 */       addSlotToContainer(new Slot(inventoryPlayer, i, 48 + i * 18, 233));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean enchantItem(EntityPlayer par1EntityPlayer, int button)
/*     */   {
/*  50 */     if (button == 1) {
/*  51 */       return true;
/*     */     }
/*  53 */     if (button == 5) {
/*  54 */       if (this.tileEntity.data == null) this.tileEntity.gatherResults();
/*  55 */       if ((this.tileEntity.data.aspects != null) && (InventoryUtils.isPlayerCarrying(par1EntityPlayer, new ItemStack(ItemsTC.knowledgeFragment)) > 0)) {
/*     */         Aspect a;
/*  57 */         for (Iterator i$ = Aspect.getPrimalAspects().iterator(); i$.hasNext(); this.tileEntity.data.aspects.add(a, 1)) a = (Aspect)i$.next();
/*  58 */         this.tileEntity.updateNoteAndConsumeInk();
/*  59 */         InventoryUtils.consumeInventoryItem(par1EntityPlayer, ItemsTC.knowledgeFragment, 0);
/*     */       }
/*  61 */       return true;
/*     */     }
/*  63 */     return false;
/*     */   }
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
/*     */   {
/*  68 */     ItemStack stack = null;
/*  69 */     Slot slotObject = (Slot)this.inventorySlots.get(slot);
/*     */     
/*     */ 
/*  72 */     if ((slotObject != null) && (slotObject.getHasStack())) {
/*  73 */       ItemStack stackInSlot = slotObject.getStack();
/*  74 */       stack = stackInSlot.copy();
/*     */       
/*     */ 
/*  77 */       if (slot < 2) {
/*  78 */         if (!mergeItemStack(stackInSlot, 2, this.inventorySlots.size(), true))
/*     */         {
/*  80 */           return null;
/*     */         }
/*     */       }
/*  83 */       else if (!mergeItemStack(stackInSlot, 0, 2, false)) {
/*  84 */         return null;
/*     */       }
/*     */       
/*  87 */       if (stackInSlot.stackSize == 0) {
/*  88 */         slotObject.putStack(null);
/*     */       } else {
/*  90 */         slotObject.onSlotChanged();
/*     */       }
/*     */     }
/*     */     
/*  94 */     return stack;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4)
/*     */   {
/* 100 */     boolean var5 = false;
/* 101 */     int var6 = par2;
/*     */     
/* 103 */     if (par4)
/*     */     {
/* 105 */       var6 = par3 - 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 111 */     if (par1ItemStack.isStackable())
/*     */     {
/* 113 */       while ((par1ItemStack.stackSize > 0) && (((!par4) && (var6 < par3)) || ((par4) && (var6 >= par2))))
/*     */       {
/* 115 */         Slot var7 = (Slot)this.inventorySlots.get(var6);
/* 116 */         ItemStack var8 = var7.getStack();
/*     */         
/* 118 */         if ((var8 != null) && (var7.isItemValid(par1ItemStack)) && (var8.getItem() == par1ItemStack.getItem()) && ((!par1ItemStack.getHasSubtypes()) || (par1ItemStack.getItemDamage() == var8.getItemDamage())) && (ItemStack.areItemStackTagsEqual(par1ItemStack, var8)))
/*     */         {
/*     */ 
/*     */ 
/* 122 */           int var9 = var8.stackSize + par1ItemStack.stackSize;
/*     */           
/* 124 */           if (var9 <= par1ItemStack.getMaxStackSize())
/*     */           {
/* 126 */             par1ItemStack.stackSize = 0;
/* 127 */             var8.stackSize = var9;
/* 128 */             var7.onSlotChanged();
/* 129 */             var5 = true;
/*     */           }
/* 131 */           else if (var8.stackSize < par1ItemStack.getMaxStackSize())
/*     */           {
/* 133 */             par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - var8.stackSize;
/* 134 */             var8.stackSize = par1ItemStack.getMaxStackSize();
/* 135 */             var7.onSlotChanged();
/* 136 */             var5 = true;
/*     */           }
/*     */         }
/*     */         
/* 140 */         if (par4)
/*     */         {
/* 142 */           var6--;
/*     */         }
/*     */         else
/*     */         {
/* 146 */           var6++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 151 */     if (par1ItemStack.stackSize > 0)
/*     */     {
/* 153 */       if (par4)
/*     */       {
/* 155 */         var6 = par3 - 1;
/*     */       }
/*     */       else
/*     */       {
/* 159 */         var6 = par2;
/*     */       }
/*     */       
/* 162 */       while (((!par4) && (var6 < par3)) || ((par4) && (var6 >= par2)))
/*     */       {
/* 164 */         Slot var7 = (Slot)this.inventorySlots.get(var6);
/* 165 */         ItemStack var8 = var7.getStack();
/*     */         
/* 167 */         if ((var8 == null) && (var7.isItemValid(par1ItemStack)))
/*     */         {
/* 169 */           var7.putStack(par1ItemStack.copy());
/* 170 */           var7.onSlotChanged();
/* 171 */           par1ItemStack.stackSize = 0;
/* 172 */           var5 = true;
/* 173 */           break;
/*     */         }
/*     */         
/* 176 */         if (par4)
/*     */         {
/* 178 */           var6--;
/*     */         }
/*     */         else
/*     */         {
/* 182 */           var6++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 187 */     return var5;
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer player)
/*     */   {
/* 192 */     return this.tileEntity.isUseableByPlayer(player);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerResearchTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */