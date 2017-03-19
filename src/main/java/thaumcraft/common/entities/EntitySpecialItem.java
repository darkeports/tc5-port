/*    */ package thaumcraft.common.entities;
/*    */ 
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class EntitySpecialItem
/*    */   extends EntityItem
/*    */ {
/*    */   public EntitySpecialItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
/*    */   {
/* 14 */     super(par1World);
/* 15 */     setSize(0.25F, 0.25F);
/*    */     
/* 17 */     setPosition(par2, par4, par6);
/* 18 */     setEntityItemStack(par8ItemStack);
/* 19 */     this.rotationYaw = ((float)(Math.random() * 360.0D));
/* 20 */     this.motionX = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
/* 21 */     this.motionY = 0.20000000298023224D;
/* 22 */     this.motionZ = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
/*    */   }
/*    */   
/*    */   public EntitySpecialItem(World par1World) {
/* 26 */     super(par1World);
/* 27 */     setSize(0.25F, 0.25F);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onUpdate()
/*    */   {
/* 34 */     if (this.motionY > 0.0D) this.motionY *= 0.8999999761581421D;
/* 35 */     this.motionY += 0.03999999910593033D;
/* 36 */     super.onUpdate();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
/*    */   {
/* 44 */     if (p_70097_1_.isExplosion())
/*    */     {
/* 46 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 50 */     return super.attackEntityFrom(p_70097_1_, p_70097_2_);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\EntitySpecialItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */