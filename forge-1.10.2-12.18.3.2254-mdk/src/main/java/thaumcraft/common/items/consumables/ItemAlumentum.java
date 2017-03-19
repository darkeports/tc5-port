/*    */ package thaumcraft.common.items.consumables;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.PlayerCapabilities;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.entities.projectile.EntityAlumentum;
/*    */ 
/*    */ public class ItemAlumentum extends net.minecraft.item.Item
/*    */ {
/*    */   public ItemAlumentum()
/*    */   {
/* 14 */     setMaxStackSize(64);
/* 15 */     setHasSubtypes(true);
/* 16 */     setMaxDamage(0);
/*    */   }
/*    */   
/*    */ 
/*    */   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
/*    */   {
/* 22 */     if (!player.capabilities.isCreativeMode)
/*    */     {
/* 24 */       itemstack.stackSize -= 1;
/*    */     }
/* 26 */     world.playSoundAtEntity(player, "random.bow", 0.3F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 27 */     if (!world.isRemote)
/*    */     {
/* 29 */       world.spawnEntityInWorld(new EntityAlumentum(world, player));
/*    */     }
/* 31 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemAlumentum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */