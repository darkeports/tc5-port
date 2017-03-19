/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.common.entities.construct.EntityTurretFocus;
/*     */ 
/*     */ 
/*     */ public class ContainerTurretFocus
/*     */   extends Container
/*     */ {
/*     */   private EntityTurretFocus turret;
/*     */   private EntityPlayer player;
/*     */   private final World theWorld;
/*     */   
/*     */   public ContainerTurretFocus(InventoryPlayer par1InventoryPlayer, World par3World, EntityTurretFocus ent)
/*     */   {
/*  24 */     this.turret = ent;
/*  25 */     this.theWorld = par3World;
/*  26 */     this.player = par1InventoryPlayer.player;
/*     */     
/*  28 */     addSlotToContainer(new SlotTurretFocus(this.turret, 0, 36, 29));
/*     */     
/*     */ 
/*  31 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  33 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  35 */         addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  39 */     for (i = 0; i < 9; i++)
/*     */     {
/*  41 */       addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
/*     */   {
/*  47 */     if (par2 == 1) {
/*  48 */       this.turret.setTargetAnimal(!this.turret.getTargetAnimal());
/*  49 */       return true;
/*     */     }
/*  51 */     if (par2 == 2) {
/*  52 */       this.turret.setTargetMob(!this.turret.getTargetMob());
/*  53 */       return true;
/*     */     }
/*  55 */     if (par2 == 3) {
/*  56 */       this.turret.setTargetPlayer(!this.turret.getTargetPlayer());
/*  57 */       return true;
/*     */     }
/*  59 */     if (par2 == 4) {
/*  60 */       this.turret.setTargetFriendly(!this.turret.getTargetFriendly());
/*  61 */       return true;
/*     */     }
/*  63 */     return super.enchantItem(par1EntityPlayer, par2);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void updateProgressBar(int par1, int par2) {}
/*     */   
/*     */ 
/*     */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*     */   {
/*  73 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
/*     */   {
/*  81 */     ItemStack stack = null;
/*  82 */     Slot slotObject = (Slot)this.inventorySlots.get(slot);
/*     */     
/*     */ 
/*  85 */     if ((slotObject != null) && (slotObject.getHasStack())) {
/*  86 */       ItemStack stackInSlot = slotObject.getStack();
/*  87 */       stack = stackInSlot.copy();
/*     */       
/*  89 */       if (slot == 0) {
/*  90 */         if (!mergeItemStack(stackInSlot, 1, this.inventorySlots.size(), true)) {
/*  91 */           return null;
/*     */         }
/*     */       }
/*  94 */       else if (!mergeItemStack(stackInSlot, 0, 1, false)) {
/*  95 */         return null;
/*     */       }
/*     */       
/*  98 */       if (stackInSlot.stackSize == 0) {
/*  99 */         slotObject.putStack(null);
/*     */       } else {
/* 101 */         slotObject.onSlotChanged();
/*     */       }
/*     */     }
/*     */     
/* 105 */     return stack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerTurretFocus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */