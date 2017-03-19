/*    */ package thaumcraft.common.lib.aura;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import thaumcraft.common.lib.utils.TCVec3;
/*    */ import thaumcraft.common.lib.world.biomes.BiomeHandler;
/*    */ 
/*    */ public class NTDark extends NTNormal
/*    */ {
/*    */   public NTDark(int id)
/*    */   {
/* 13 */     super(id);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   void performPeriodicEvent(EntityAuraNode node)
/*    */   {
/* 20 */     super.performPeriodicEvent(node);
/*    */     
/* 22 */     int rad = node.worldObj.rand.nextInt(180) * 2;
/*    */     
/* 24 */     TCVec3 vsource = TCVec3.createVectorHelper(node.posX, node.posY, node.posZ);
/* 25 */     int r = (int)(4.0D + Math.sqrt(node.getNodeSize()));
/* 26 */     for (int q = 0; q < r; q++) {
/* 27 */       TCVec3 vtar = TCVec3.createVectorHelper(q, 0.0D, 0.0D);
/* 28 */       vtar.rotateAroundY(rad / 180.0F * 3.1415927F);
/* 29 */       TCVec3 vres = vsource.addVector(vtar.xCoord, vtar.yCoord, vtar.zCoord);
/* 30 */       net.minecraft.util.BlockPos t = new net.minecraft.util.BlockPos(MathHelper.floor_double(vres.xCoord), MathHelper.floor_double(vres.yCoord), MathHelper.floor_double(vres.zCoord));
/*    */       
/* 32 */       BiomeGenBase biome = node.worldObj.getBiomeGenForCoords(t);
/* 33 */       if (biome.biomeID != BiomeHandler.biomeEerie.biomeID) {
/* 34 */         thaumcraft.common.lib.utils.Utils.setBiomeAt(node.worldObj, t, BiomeHandler.biomeEerie);
/* 35 */         break;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   int calculateStrength(EntityAuraNode node)
/*    */   {
/* 42 */     int m = node.worldObj.provider.getMoonPhase(node.worldObj.getWorldInfo().getWorldTime());
/* 43 */     float b = 1.0F - (Math.abs(m - 4) - 2) / 5.0F;
/* 44 */     b += (node.getBrightness(1.0F) - 0.5F) / 3.0F;
/* 45 */     return (int)Math.max(1.0D, Math.sqrt(node.getNodeSize() / 3.0F) * b);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\aura\NTDark.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */