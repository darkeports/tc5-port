/*    */ package thaumcraft.common.tiles.devices;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ITickable;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.blocks.TileThaumcraft;
/*    */ import thaumcraft.common.lib.aura.EntityAuraNode;
/*    */ import thaumcraft.common.lib.utils.EntityUtils;
/*    */ 
/*    */ public class TileNodeStabilizer extends TileThaumcraft implements ITickable
/*    */ {
/* 19 */   public int count = 0;
/* 20 */   int delay = 0;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 26 */   ArrayList<Entity> nodes = null;
/*    */   
/*    */   public void update()
/*    */   {
/* 30 */     if ((this.nodes == null) || (this.delay % 100 == 0)) {
/* 31 */       this.nodes = EntityUtils.getEntitiesInRange(this.worldObj, this.pos.getX() + 0.5D, this.pos.getY() + 1.5D, this.pos.getZ() + 0.5D, null, EntityAuraNode.class, 0.5D);
/*    */     }
/*    */     boolean notFirst;
/* 34 */     if (!gettingPower()) {
/* 35 */       notFirst = false;
/* 36 */       for (Entity e : this.nodes) {
/* 37 */         if ((e != null) && (!e.isDead) && ((e instanceof EntityAuraNode))) {
/* 38 */           EntityAuraNode an = (EntityAuraNode)e;
/* 39 */           an.stablized = (!notFirst);
/* 40 */           Vec3 v1 = new Vec3(this.pos.getX() + 0.5D, this.pos.getY() + 1.5D, this.pos.getZ() + 0.5D);
/* 41 */           Vec3 v2 = new Vec3(an.posX, an.posY, an.posZ);
/* 42 */           double d = v1.squareDistanceTo(v2);
/* 43 */           if (d > 0.001D) {
/* 44 */             v1 = v1.subtract(v2).normalize();
/* 45 */             if (notFirst) {
/* 46 */               an.motionX -= v1.xCoord / 750.0D;
/* 47 */               an.motionY -= v1.yCoord / 750.0D;
/* 48 */               an.motionZ -= v1.zCoord / 750.0D;
/*    */             } else {
/* 50 */               an.motionX += v1.xCoord / 1000.0D;
/* 51 */               an.motionY += v1.yCoord / 1000.0D;
/* 52 */               an.motionZ += v1.zCoord / 1000.0D;
/*    */             }
/*    */           }
/* 55 */           else if (notFirst) {
/* 56 */             an.motionY += 0.005D;
/*    */           }
/* 58 */           notFirst = true;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 63 */     if ((this.worldObj.isRemote) && (this.nodes.size() > 0)) {
/* 64 */       if (!gettingPower()) {
/* 65 */         if (this.delay == 0) {
/* 66 */           this.count = 37;
/*    */         }
/* 68 */         if (this.count < 37) this.count += 1;
/*    */       }
/* 70 */       else if (this.count > 0) { this.count -= 1;
/*    */       }
/*    */     }
/*    */     
/* 74 */     if (this.delay == 0) {
/* 75 */       this.delay = this.worldObj.rand.nextInt(100);
/*    */     }
/*    */     
/* 78 */     this.delay += 1;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public AxisAlignedBB getRenderBoundingBox()
/*    */   {
/* 84 */     return AxisAlignedBB.fromBounds(getPos().getX() - 0.3D, getPos().getY() - 0.3D, getPos().getZ() - 0.3D, getPos().getX() + 1.3D, getPos().getY() + 1.3D, getPos().getZ() + 1.3D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileNodeStabilizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */