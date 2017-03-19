/*    */ package thaumcraft.api.golems;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import thaumcraft.api.ThaumcraftApi;
/*    */ import thaumcraft.api.golems.seals.ISeal;
/*    */ import thaumcraft.api.golems.seals.ISealEntity;
/*    */ import thaumcraft.api.golems.seals.SealPos;
/*    */ import thaumcraft.api.golems.tasks.Task;
/*    */ import thaumcraft.api.internal.IInternalMethodHandler;
/*    */ 
/*    */ 
/*    */ public class GolemHelper
/*    */ {
/*    */   public static void registerSeal(ISeal seal)
/*    */   {
/* 23 */     ThaumcraftApi.internalMethods.registerSeal(seal);
/*    */   }
/*    */   
/*    */   public static ISeal getSeal(String key) {
/* 27 */     return ThaumcraftApi.internalMethods.getSeal(key);
/*    */   }
/*    */   
/*    */   public static ItemStack getSealStack(String key) {
/* 31 */     return ThaumcraftApi.internalMethods.getSealStack(key);
/*    */   }
/*    */   
/*    */   public static ISealEntity getSealEntity(int dim, SealPos pos) {
/* 35 */     return ThaumcraftApi.internalMethods.getSealEntity(dim, pos);
/*    */   }
/*    */   
/*    */   public static void addGolemTask(int dim, Task task) {
/* 39 */     ThaumcraftApi.internalMethods.addGolemTask(dim, task);
/*    */   }
/*    */   
/* 42 */   public static HashMap<Integer, ArrayList<ProvisionRequest>> provisionRequests = new HashMap();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void requestProvisioning(World world, ISealEntity seal, ItemStack stack)
/*    */   {
/* 51 */     if (!provisionRequests.containsKey(Integer.valueOf(world.provider.getDimension())))
/* 52 */       provisionRequests.put(Integer.valueOf(world.provider.getDimension()), new ArrayList());
/* 53 */     ArrayList<ProvisionRequest> list = (ArrayList)provisionRequests.get(Integer.valueOf(world.provider.getDimension()));
/* 54 */     ProvisionRequest pr = new ProvisionRequest(seal, stack.copy());
/* 55 */     if (!list.contains(pr)) {
/* 56 */       list.add(pr);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static BlockPos getPosInArea(ISealEntity seal, int count)
/*    */   {
/* 67 */     int xx = 1 + (seal.getArea().getX() - 1) * (seal.getSealPos().face.getFrontOffsetX() == 0 ? 2 : 1);
/* 68 */     int yy = 1 + (seal.getArea().getY() - 1) * (seal.getSealPos().face.getFrontOffsetY() == 0 ? 2 : 1);
/* 69 */     int zz = 1 + (seal.getArea().getZ() - 1) * (seal.getSealPos().face.getFrontOffsetZ() == 0 ? 2 : 1);
/*    */     
/* 71 */     int qx = seal.getSealPos().face.getFrontOffsetX() != 0 ? seal.getSealPos().face.getFrontOffsetX() : 1;
/* 72 */     int qy = seal.getSealPos().face.getFrontOffsetY() != 0 ? seal.getSealPos().face.getFrontOffsetY() : 1;
/* 73 */     int qz = seal.getSealPos().face.getFrontOffsetZ() != 0 ? seal.getSealPos().face.getFrontOffsetZ() : 1;
/*    */     
/* 75 */     int y = qy * (count / zz / xx) % yy + seal.getSealPos().face.getFrontOffsetY();
/* 76 */     int x = qx * (count / zz) % xx + seal.getSealPos().face.getFrontOffsetX();
/* 77 */     int z = qz * count % zz + seal.getSealPos().face.getFrontOffsetZ();
/*    */     
/* 79 */     BlockPos p = seal.getSealPos().pos.add(x - (seal.getSealPos().face.getFrontOffsetX() == 0 ? xx / 2 : 0), y - (seal.getSealPos().face.getFrontOffsetY() == 0 ? yy / 2 : 0), z - (seal.getSealPos().face.getFrontOffsetZ() == 0 ? zz / 2 : 0));
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 84 */     return p;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static AxisAlignedBB getBoundsForArea(ISealEntity seal)
/*    */   {
/* 94 */     return new AxisAlignedBB(seal.getSealPos().pos.getX(), seal.getSealPos().pos.getY(), seal.getSealPos().pos.getZ(), seal.getSealPos().pos.getX() + 1, seal.getSealPos().pos.getY() + 1, seal.getSealPos().pos.getZ() + 1).offset(seal.getSealPos().face.getFrontOffsetX(), seal.getSealPos().face.getFrontOffsetY(), seal.getSealPos().face.getFrontOffsetZ()).addCoord(seal.getSealPos().face.getFrontOffsetX() != 0 ? (seal.getArea().getX() - 1) * seal.getSealPos().face.getFrontOffsetX() : 0.0D, seal.getSealPos().face.getFrontOffsetY() != 0 ? (seal.getArea().getY() - 1) * seal.getSealPos().face.getFrontOffsetY() : 0.0D, seal.getSealPos().face.getFrontOffsetZ() != 0 ? (seal.getArea().getZ() - 1) * seal.getSealPos().face.getFrontOffsetZ() : 0.0D).expand(seal.getSealPos().face.getFrontOffsetX() == 0 ? seal.getArea().getX() - 1 : 0.0D, seal.getSealPos().face.getFrontOffsetY() == 0 ? seal.getArea().getY() - 1 : 0.0D, seal.getSealPos().face.getFrontOffsetZ() == 0 ? seal.getArea().getZ() - 1 : 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\golems\GolemHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */