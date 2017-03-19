/*    */ package thaumcraft.common.items.consumables;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.PlayerCapabilities;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.entities.projectile.EntityBottleTaint;
/*    */ 
/*    */ public class ItemBottleTaint extends net.minecraft.item.Item
/*    */ {
/*    */   public ItemBottleTaint()
/*    */   {
/* 15 */     this.maxStackSize = 8;
/* 16 */     setMaxDamage(0);
/* 17 */     setCreativeTab(Thaumcraft.tabTC);
/* 18 */     setHasSubtypes(false);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
/*    */   {
/* 25 */     if (!player.capabilities.isCreativeMode)
/*    */     {
/* 27 */       stack.stackSize -= 1;
/*    */     }
/*    */     
/* 30 */     world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*    */     
/* 32 */     if (!world.isRemote)
/*    */     {
/* 34 */       world.spawnEntityInWorld(new EntityBottleTaint(world, player));
/*    */     }
/*    */     
/* 37 */     return stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemBottleTaint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */