/*    */ package thaumcraft.common.entities;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityPermanentItem
/*    */   extends EntitySpecialItem
/*    */ {
/*    */   public EntityPermanentItem(World par1World)
/*    */   {
/* 11 */     super(par1World);
/*    */   }
/*    */   
/*    */   public EntityPermanentItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
/*    */   {
/* 16 */     super(par1World);
/* 17 */     setSize(0.25F, 0.25F);
/* 18 */     setPosition(par2, par4, par6);
/* 19 */     setEntityItemStack(par8ItemStack);
/* 20 */     this.rotationYaw = ((float)(Math.random() * 360.0D));
/* 21 */     this.motionX = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
/* 22 */     this.motionY = 0.20000000298023224D;
/* 23 */     this.motionZ = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onUpdate()
/*    */   {
/* 30 */     super.onUpdate();
/* 31 */     setNoDespawn();
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\EntityPermanentItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */