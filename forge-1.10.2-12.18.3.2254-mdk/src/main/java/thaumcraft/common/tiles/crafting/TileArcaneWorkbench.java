/*    */ package thaumcraft.common.tiles.crafting;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import thaumcraft.api.blocks.TileThaumcraft;
/*    */ import thaumcraft.common.container.InventoryArcaneWorkbench;
/*    */ 
/*    */ 
/*    */ public class TileArcaneWorkbench
/*    */   extends TileThaumcraft
/*    */ {
/*    */   public InventoryArcaneWorkbench inventory;
/*    */   
/*    */   public TileArcaneWorkbench()
/*    */   {
/* 17 */     this.inventory = new InventoryArcaneWorkbench(null, 3, 3);
/*    */   }
/*    */   
/*    */ 
/*    */   public void readCustomNBT(NBTTagCompound par1NBTTagCompound)
/*    */   {
/* 23 */     NBTTagList var2 = par1NBTTagCompound.getTagList("Inventory", 10);
/* 24 */     this.inventory.stackList = new ItemStack[11];
/*    */     
/* 26 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/*    */     {
/* 28 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 29 */       int var5 = var4.getByte("Slot") & 0xFF;
/*    */       
/* 31 */       if ((var5 >= 0) && (var5 < this.inventory.stackList.length))
/*    */       {
/* 33 */         this.inventory.stackList[var5] = ItemStack.loadItemStackFromNBT(var4);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void writeCustomNBT(NBTTagCompound par1NBTTagCompound)
/*    */   {
/* 43 */     NBTTagList var2 = new NBTTagList();
/*    */     
/* 45 */     for (int var3 = 0; var3 < this.inventory.stackList.length; var3++)
/*    */     {
/* 47 */       if (this.inventory.stackList[var3] != null)
/*    */       {
/* 49 */         NBTTagCompound var4 = new NBTTagCompound();
/* 50 */         var4.setByte("Slot", (byte)var3);
/* 51 */         this.inventory.stackList[var3].writeToNBT(var4);
/* 52 */         var2.appendTag(var4);
/*    */       }
/*    */     }
/*    */     
/* 56 */     par1NBTTagCompound.setTag("Inventory", var2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\crafting\TileArcaneWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */