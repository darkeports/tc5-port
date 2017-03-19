/*    */ package thaumcraft.common.items.wands;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ import thaumcraft.api.wands.IWand;
/*    */ import thaumcraft.api.wands.IWandRodOnUpdate;
/*    */ 
/*    */ public class WandRodPrimalOnUpdate implements IWandRodOnUpdate
/*    */ {
/*    */   Aspect aspect;
/*    */   ArrayList<Aspect> primals;
/*    */   
/*    */   public WandRodPrimalOnUpdate(Aspect aspect)
/*    */   {
/* 17 */     this.aspect = aspect;
/*    */   }
/*    */   
/*    */   public WandRodPrimalOnUpdate() {
/* 21 */     this.aspect = null;
/* 22 */     this.primals = Aspect.getPrimalAspects();
/*    */   }
/*    */   
/*    */   public void onUpdate(ItemStack itemstack, EntityPlayer player)
/*    */   {
/* 27 */     if (this.aspect != null) {
/* 28 */       if ((player.ticksExisted % 5 == 0) && 
/* 29 */         (((IWand)itemstack.getItem()).getVis(itemstack, this.aspect) < ((IWand)itemstack.getItem()).getMaxVis(itemstack) / 2)) {
/* 30 */         ((IWand)itemstack.getItem()).addVis(itemstack, this.aspect, 1, true);
/*    */       }
/*    */       
/*    */     }
/* 34 */     else if (player.ticksExisted % 5 == 0) {
/* 35 */       ArrayList<Aspect> q = new ArrayList();
/* 36 */       for (Aspect as : this.primals) {
/* 37 */         if (((IWand)itemstack.getItem()).getVis(itemstack, as) < ((IWand)itemstack.getItem()).getMaxVis(itemstack) / 2) {
/* 38 */           q.add(as);
/*    */         }
/*    */       }
/* 41 */       if (q.size() > 0) {
/* 42 */         ((IWand)itemstack.getItem()).addVis(itemstack, (Aspect)q.get(player.worldObj.rand.nextInt(q.size())), 1, true);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\wands\WandRodPrimalOnUpdate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */