/*    */ package thaumcraft.common.entities.projectile;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.projectile.EntityThrowable;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.CommonProxy;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class EntityAlumentum extends EntityThrowable
/*    */ {
/*    */   public EntityAlumentum(World par1World)
/*    */   {
/* 13 */     super(par1World);
/*    */   }
/*    */   
/*    */   public EntityAlumentum(World par1World, net.minecraft.entity.EntityLivingBase par2EntityLiving)
/*    */   {
/* 18 */     super(par1World, par2EntityLiving);
/*    */   }
/*    */   
/*    */   public EntityAlumentum(World par1World, double par2, double par4, double par6)
/*    */   {
/* 23 */     super(par1World, par2, par4, par6);
/*    */   }
/*    */   
/*    */ 
/*    */   protected float getVelocity()
/*    */   {
/* 29 */     return 0.75F;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onUpdate()
/*    */   {
/* 36 */     super.onUpdate();
/*    */     
/* 38 */     if (this.worldObj.isRemote)
/*    */     {
/* 40 */       for (int a = 0; a < 3; a++) {
/* 41 */         Thaumcraft.proxy.getFX().wispFX2(this.posX + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F, this.posY + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F, this.posZ + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F, 0.3F, 5, true, true, 0.02F);
/*    */         
/*    */ 
/*    */ 
/* 45 */         double x2 = (this.posX + this.prevPosX) / 2.0D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
/* 46 */         double y2 = (this.posY + this.prevPosY) / 2.0D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
/* 47 */         double z2 = (this.posZ + this.prevPosZ) / 2.0D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
/* 48 */         Thaumcraft.proxy.getFX().wispFX2(x2, y2, z2, 0.3F, 5, true, true, 0.02F);
/* 49 */         Thaumcraft.proxy.getFX().sparkle((float)this.posX + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F, (float)this.posY + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F, (float)this.posZ + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F, 6);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void onImpact(net.minecraft.util.MovingObjectPosition par1MovingObjectPosition)
/*    */   {
/* 61 */     if (!this.worldObj.isRemote)
/*    */     {
/* 63 */       boolean var2 = this.worldObj.getGameRules().getBoolean("mobGriefing");
/* 64 */       this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 1.66F, var2);
/* 65 */       setDead();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityAlumentum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */