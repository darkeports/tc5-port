/*    */ package thaumcraft.common.entities.ai.misc;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.ai.EntityAIBase;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.entities.monster.cult.EntityCultistCleric;
/*    */ 
/*    */ public class AIAltarFocus extends EntityAIBase
/*    */ {
/*    */   private EntityCultistCleric entity;
/*    */   private World world;
/* 12 */   int field_48399_a = 0;
/*    */   
/*    */   public AIAltarFocus(EntityCultistCleric par1EntityLiving)
/*    */   {
/* 16 */     this.entity = par1EntityLiving;
/* 17 */     this.world = par1EntityLiving.worldObj;
/* 18 */     setMutexBits(7);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 27 */     if ((!this.entity.getIsRitualist()) || (this.entity.getHomePosition() == null))
/*    */     {
/* 29 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 33 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void startExecuting() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void resetTask() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 60 */     return (this.entity.getIsRitualist()) && (this.entity.getHomePosition() != null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 69 */     if (this.entity.getHomePosition() != null)
/*    */     {
/* 71 */       if ((this.entity.ticksExisted % 40 == 0) && ((this.entity.getHomePosition().distanceSq(this.entity.posX, this.entity.posY, this.entity.posZ) > 16.0D) || (this.world.getBlockState(this.entity.getHomePosition()).getBlock() != thaumcraft.api.blocks.BlocksTC.eldritch)))
/*    */       {
/* 73 */         this.entity.setIsRitualist(false);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\ai\misc\AIAltarFocus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */