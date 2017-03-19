/*    */ package thaumcraft.common.items.consumables;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.ItemFood;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.internal.EnumWarpType;
/*    */ import thaumcraft.api.research.ResearchHelper;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ItemZombieBrain extends ItemFood
/*    */ {
/*    */   public ItemZombieBrain()
/*    */   {
/* 18 */     super(4, 0.2F, true);
/* 19 */     setPotionEffect(Potion.hunger.id, 30, 0, 0.8F);
/* 20 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */   public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
/*    */   {
/* 25 */     if ((!world.isRemote) && ((player instanceof EntityPlayerMP))) {
/* 26 */       if (world.rand.nextFloat() < 0.1F) {
/* 27 */         ResearchHelper.addWarpToPlayer(player, 1, EnumWarpType.NORMAL);
/*    */       } else {
/* 29 */         ResearchHelper.addWarpToPlayer(player, 1 + world.rand.nextInt(3), EnumWarpType.TEMPORARY);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemZombieBrain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */