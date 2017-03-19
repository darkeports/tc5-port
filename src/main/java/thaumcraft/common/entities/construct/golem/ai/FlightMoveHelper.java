/*    */ package thaumcraft.common.entities.construct.golem.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.entity.ai.EntityLookHelper;
/*    */ import net.minecraft.entity.ai.EntityMoveHelper;
/*    */ import net.minecraft.pathfinding.PathNavigate;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class FlightMoveHelper extends EntityMoveHelper
/*    */ {
/*    */   private static final String __OBFID = "CL_00002209";
/*    */   
/*    */   public FlightMoveHelper(EntityLiving entity)
/*    */   {
/* 16 */     super(entity);
/*    */   }
/*    */   
/*    */   public void onUpdateMoveHelper()
/*    */   {
/* 21 */     if ((this.update) && (!this.entity.getNavigator().noPath()))
/*    */     {
/* 23 */       double d0 = this.posX - this.entity.posX;
/* 24 */       double d1 = this.posY - this.entity.posY;
/* 25 */       double d2 = this.posZ - this.entity.posZ;
/* 26 */       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 27 */       d3 = MathHelper.sqrt_double(d3);
/* 28 */       d1 /= d3;
/* 29 */       float f = (float)(Math.atan2(d2, d0) * 180.0D / 3.141592653589793D) - 90.0F;
/* 30 */       this.entity.rotationYaw = limitAngle(this.entity.rotationYaw, f, 30.0F);
/* 31 */       this.entity.renderYawOffset = this.entity.rotationYaw;
/* 32 */       float f1 = (float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
/* 33 */       this.entity.setAIMoveSpeed(this.entity.getAIMoveSpeed() + (f1 - this.entity.getAIMoveSpeed()) * 0.125F);
/* 34 */       double d4 = Math.sin((this.entity.ticksExisted + this.entity.getEntityId()) * 0.5D) * 0.05D;
/* 35 */       double d5 = Math.cos(this.entity.rotationYaw * 3.1415927F / 180.0F);
/* 36 */       double d6 = Math.sin(this.entity.rotationYaw * 3.1415927F / 180.0F);
/* 37 */       this.entity.motionX += d4 * d5;
/* 38 */       this.entity.motionZ += d4 * d6;
/* 39 */       d4 = Math.sin((this.entity.ticksExisted + this.entity.getEntityId()) * 0.75D) * 0.05D;
/* 40 */       this.entity.motionY += d4 * (d6 + d5) * 0.25D;
/* 41 */       this.entity.motionY += this.entity.getAIMoveSpeed() * d1 * 0.1D;
/* 42 */       EntityLookHelper entitylookhelper = this.entity.getLookHelper();
/* 43 */       double d7 = this.entity.posX + d0 / d3 * 2.0D;
/* 44 */       double d8 = this.entity.getEyeHeight() + this.entity.posY + d1 / d3 * 1.0D;
/* 45 */       double d9 = this.entity.posZ + d2 / d3 * 2.0D;
/* 46 */       double d10 = entitylookhelper.getLookPosX();
/* 47 */       double d11 = entitylookhelper.getLookPosY();
/* 48 */       double d12 = entitylookhelper.getLookPosZ();
/*    */       
/* 50 */       if (!entitylookhelper.getIsLooking())
/*    */       {
/* 52 */         d10 = d7;
/* 53 */         d11 = d8;
/* 54 */         d12 = d9;
/*    */       }
/*    */       
/* 57 */       this.entity.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
/*    */     }
/*    */     else
/*    */     {
/* 61 */       this.entity.setAIMoveSpeed(0.0F);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ai\FlightMoveHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */