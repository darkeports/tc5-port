/*    */ package thaumcraft.common.lib.world.dim;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.WorldSavedData;
/*    */ 
/*    */ public class MapBossData extends WorldSavedData
/*    */ {
/*    */   public int bossCount;
/*    */   
/*    */   public MapBossData(String p_i2140_1_)
/*    */   {
/* 12 */     super(p_i2140_1_);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void readFromNBT(NBTTagCompound p_76184_1_)
/*    */   {
/* 20 */     this.bossCount = p_76184_1_.getInteger("bossCount");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void writeToNBT(NBTTagCompound p_76187_1_)
/*    */   {
/* 28 */     p_76187_1_.setInteger("bossCount", this.bossCount);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\dim\MapBossData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */