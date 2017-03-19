/*    */ package thaumcraft.common.container;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*    */ 
/*    */ 
/*    */ public class SlotGhostFluid
/*    */   extends SlotGhost
/*    */ {
/*    */   public SlotGhostFluid(IInventory par1iInventory, int par2, int par3, int par4)
/*    */   {
/* 14 */     super(par1iInventory, par2, par3, par4);
/*    */   }
/*    */   
/*    */ 
/*    */   public int getSlotStackLimit()
/*    */   {
/* 20 */     return 1;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isItemValid(ItemStack par1ItemStack)
/*    */   {
/* 28 */     return FluidContainerRegistry.isContainer(par1ItemStack);
/*    */   }
/*    */   
/*    */   public boolean canTakeStack(EntityPlayer par1EntityPlayer)
/*    */   {
/* 33 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\SlotGhostFluid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */