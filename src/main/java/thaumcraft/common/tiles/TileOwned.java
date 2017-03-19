/*    */ package thaumcraft.common.tiles;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import thaumcraft.api.blocks.TileThaumcraft;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileOwned
/*    */   extends TileThaumcraft
/*    */ {
/* 13 */   public String owner = "";
/* 14 */   public ArrayList<String> accessList = new ArrayList();
/*    */   
/* 16 */   public boolean safeToRemove = false;
/*    */   
/*    */ 
/*    */ 
/*    */   public void readFromNBT(NBTTagCompound nbttagcompound)
/*    */   {
/* 22 */     super.readFromNBT(nbttagcompound);
/* 23 */     this.owner = nbttagcompound.getString("owner");
/*    */     
/* 25 */     NBTTagList var2 = nbttagcompound.getTagList("access", 10);
/* 26 */     this.accessList = new ArrayList();
/*    */     
/* 28 */     for (int var3 = 0; var3 < var2.tagCount(); var3++)
/*    */     {
/* 30 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/* 31 */       this.accessList.add(var4.getString("name"));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*    */   {
/* 38 */     super.writeToNBT(nbttagcompound);
/*    */     
/*    */ 
/* 41 */     NBTTagList var2 = new NBTTagList();
/*    */     
/* 43 */     for (int var3 = 0; var3 < this.accessList.size(); var3++)
/*    */     {
/* 45 */       NBTTagCompound var4 = new NBTTagCompound();
/* 46 */       var4.setString("name", (String)this.accessList.get(var3));
/* 47 */       var2.appendTag(var4);
/*    */     }
/* 49 */     nbttagcompound.setTag("access", var2);
/*    */   }
/*    */   
/*    */ 
/*    */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*    */   {
/* 55 */     this.owner = nbttagcompound.getString("owner");
/*    */   }
/*    */   
/*    */ 
/*    */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*    */   {
/* 61 */     nbttagcompound.setString("owner", this.owner);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\TileOwned.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */