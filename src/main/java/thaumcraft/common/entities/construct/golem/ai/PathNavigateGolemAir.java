/*    */ package thaumcraft.common.entities.construct.golem.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.pathfinding.PathEntity;
/*    */ import net.minecraft.pathfinding.PathFinder;
/*    */ import net.minecraft.pathfinding.PathNavigate;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class PathNavigateGolemAir extends PathNavigate
/*    */ {
/*    */   public PathNavigateGolemAir(EntityLiving p_i45873_1_, World worldIn)
/*    */   {
/* 15 */     super(p_i45873_1_, worldIn);
/*    */   }
/*    */   
/*    */ 
/*    */   protected PathFinder getPathFinder()
/*    */   {
/* 21 */     return new PathFinder(new FlightNodeProcessor());
/*    */   }
/*    */   
/*    */ 
/*    */   protected boolean canNavigate()
/*    */   {
/* 27 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   protected Vec3 getEntityPosition()
/*    */   {
/* 33 */     return new Vec3(this.theEntity.posX, this.theEntity.posY + this.theEntity.height * 0.5D, this.theEntity.posZ);
/*    */   }
/*    */   
/*    */ 
/*    */   protected void pathFollow()
/*    */   {
/* 39 */     Vec3 vec3 = getEntityPosition();
/* 40 */     float f = this.theEntity.width * this.theEntity.width;
/* 41 */     byte b0 = 6;
/*    */     
/* 43 */     if (vec3.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex())) < f)
/*    */     {
/* 45 */       this.currentPath.incrementPathIndex();
/*    */     }
/*    */     
/* 48 */     for (int i = Math.min(this.currentPath.getCurrentPathIndex() + b0, this.currentPath.getCurrentPathLength() - 1); i > this.currentPath.getCurrentPathIndex(); i--)
/*    */     {
/* 50 */       Vec3 vec31 = this.currentPath.getVectorFromIndex(this.theEntity, i);
/*    */       
/* 52 */       if ((vec31.squareDistanceTo(vec3) <= 36.0D) && (isDirectPathBetweenPoints(vec3, vec31, 0, 0, 0)))
/*    */       {
/* 54 */         this.currentPath.setCurrentPathIndex(i);
/* 55 */         break;
/*    */       }
/*    */     }
/*    */     
/* 59 */     checkForStuck(vec3);
/*    */   }
/*    */   
/*    */ 
/*    */   protected void removeSunnyPath()
/*    */   {
/* 65 */     super.removeSunnyPath();
/*    */   }
/*    */   
/*    */ 
/*    */   protected boolean isDirectPathBetweenPoints(Vec3 p_75493_1_, Vec3 p_75493_2_, int p_75493_3_, int p_75493_4_, int p_75493_5_)
/*    */   {
/* 71 */     MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(p_75493_1_, new Vec3(p_75493_2_.xCoord, p_75493_2_.yCoord + this.theEntity.height * 0.5D, p_75493_2_.zCoord), false, true, false);
/* 72 */     return (movingobjectposition == null) || (movingobjectposition.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.MISS);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\PathNavigateGolemAir.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */