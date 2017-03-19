/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.common.items.resources.ItemShard;
/*     */ import thaumcraft.common.tiles.devices.TileAuraTotem;
/*     */ 
/*     */ public class ContainerAuraTotem extends Container
/*     */ {
/*     */   private TileAuraTotem totem;
/*     */   private int lastBreakTime;
/*     */   private int lastTime;
/*     */   
/*     */   public ContainerAuraTotem(InventoryPlayer par1InventoryPlayer, TileAuraTotem tileEntity)
/*     */   {
/*  22 */     this.totem = tileEntity;
/*  23 */     addSlotToContainer(new SlotLimitedByClass(ItemShard.class, tileEntity, 0, 80, 24));
/*     */     
/*     */ 
/*  26 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  28 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  30 */         addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  34 */     for (i = 0; i < 9; i++)
/*     */     {
/*  36 */       addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*     */   {
/*  44 */     return this.totem.isUseableByPlayer(par1EntityPlayer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
/*     */   {
/*  52 */     ItemStack stack = null;
/*  53 */     Slot slotObject = (Slot)this.inventorySlots.get(slot);
/*     */     
/*     */ 
/*  56 */     if ((slotObject != null) && (slotObject.getHasStack())) {
/*  57 */       ItemStack stackInSlot = slotObject.getStack();
/*  58 */       stack = stackInSlot.copy();
/*     */       
/*  60 */       if (slot == 0) {
/*  61 */         if ((!this.totem.isItemValidForSlot(slot, stackInSlot)) || (!mergeItemStack(stackInSlot, 1, this.inventorySlots.size(), true)))
/*     */         {
/*     */ 
/*  64 */           return null;
/*     */         }
/*     */         
/*     */       }
/*  68 */       else if ((!this.totem.isItemValidForSlot(slot, stackInSlot)) || (!mergeItemStack(stackInSlot, 0, 1, false)))
/*     */       {
/*  70 */         return null;
/*     */       }
/*     */       
/*  73 */       if (stackInSlot.stackSize == 0) {
/*  74 */         slotObject.putStack(null);
/*     */       } else {
/*  76 */         slotObject.onSlotChanged();
/*     */       }
/*     */     }
/*     */     
/*  80 */     return stack;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onCraftGuiOpened(ICrafting par1ICrafting)
/*     */   {
/*  87 */     super.onCraftGuiOpened(par1ICrafting);
/*  88 */     par1ICrafting.sendProgressBarUpdate(this, 0, this.totem.time);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void detectAndSendChanges()
/*     */   {
/*  96 */     super.detectAndSendChanges();
/*     */     
/*  98 */     for (int i = 0; i < this.crafters.size(); i++)
/*     */     {
/* 100 */       ICrafting icrafting = (ICrafting)this.crafters.get(i);
/*     */       
/* 102 */       if (this.lastTime != this.totem.time)
/*     */       {
/* 104 */         icrafting.sendProgressBarUpdate(this, 0, this.totem.time);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 109 */     this.lastTime = this.totem.time;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public void updateProgressBar(int par1, int par2)
/*     */   {
/* 116 */     if (par1 == 0)
/*     */     {
/* 118 */       this.totem.time = par2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerAuraTotem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */