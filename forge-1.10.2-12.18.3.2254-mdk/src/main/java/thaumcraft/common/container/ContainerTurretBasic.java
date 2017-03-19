/*    */ package thaumcraft.common.container;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.common.entities.construct.EntityTurretCrossbow;
/*    */ 
/*    */ 
/*    */ public class ContainerTurretBasic
/*    */   extends Container
/*    */ {
/*    */   private EntityTurretCrossbow turret;
/*    */   private EntityPlayer player;
/*    */   private final World theWorld;
/*    */   
/*    */   public ContainerTurretBasic(InventoryPlayer par1InventoryPlayer, World par3World, EntityTurretCrossbow ent)
/*    */   {
/* 24 */     this.turret = ent;
/* 25 */     this.theWorld = par3World;
/* 26 */     this.player = par1InventoryPlayer.player;
/*    */     
/* 28 */     addSlotToContainer(new SlotTurretBasic(this.turret, 0, 80, 29));
/*    */     
/*    */ 
/* 31 */     for (int i = 0; i < 3; i++)
/*    */     {
/* 33 */       for (int j = 0; j < 9; j++)
/*    */       {
/* 35 */         addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*    */       }
/*    */     }
/*    */     
/* 39 */     for (i = 0; i < 9; i++)
/*    */     {
/* 41 */       addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2)
/*    */   {
/* 47 */     if (par2 == 0)
/*    */     {
/* 49 */       return true;
/*    */     }
/* 51 */     return super.enchantItem(par1EntityPlayer, par2);
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void updateProgressBar(int par1, int par2) {}
/*    */   
/*    */ 
/*    */   public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*    */   {
/* 61 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot)
/*    */   {
/* 69 */     ItemStack stack = null;
/* 70 */     Slot slotObject = (Slot)this.inventorySlots.get(slot);
/*    */     
/*    */ 
/* 73 */     if ((slotObject != null) && (slotObject.getHasStack())) {
/* 74 */       ItemStack stackInSlot = slotObject.getStack();
/* 75 */       stack = stackInSlot.copy();
/*    */       
/* 77 */       if (slot == 0) {
/* 78 */         if (!mergeItemStack(stackInSlot, 1, this.inventorySlots.size(), true)) {
/* 79 */           return null;
/*    */         }
/*    */       }
/* 82 */       else if (!mergeItemStack(stackInSlot, 0, 1, false)) {
/* 83 */         return null;
/*    */       }
/*    */       
/* 86 */       if (stackInSlot.stackSize == 0) {
/* 87 */         slotObject.putStack(null);
/*    */       } else {
/* 89 */         slotObject.onSlotChanged();
/*    */       }
/*    */     }
/*    */     
/* 93 */     return stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\ContainerTurretBasic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */