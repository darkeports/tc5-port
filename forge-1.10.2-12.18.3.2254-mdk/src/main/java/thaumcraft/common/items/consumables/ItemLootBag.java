/*    */ package thaumcraft.common.items.consumables;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.items.ItemGenericVariants;
/*    */ 
/*    */ public class ItemLootBag extends ItemGenericVariants
/*    */ {
/*    */   public ItemLootBag()
/*    */   {
/* 17 */     super(new String[] { "common", "uncommon", "rare" });
/* 18 */     setMaxStackSize(16);
/* 19 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public EnumRarity getRarity(ItemStack stack)
/*    */   {
/* 26 */     switch (stack.getItemDamage()) {
/* 27 */     case 1:  return EnumRarity.UNCOMMON;
/* 28 */     case 2:  return EnumRarity.RARE;
/*    */     }
/* 30 */     return EnumRarity.COMMON;
/*    */   }
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*    */   {
/* 35 */     super.addInformation(stack, player, list, par4);
/* 36 */     list.add(net.minecraft.util.StatCollector.translateToLocal("tc.lootbag"));
/*    */   }
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
/*    */   {
/* 41 */     if (!world.isRemote) {
/* 42 */       int q = 8 + world.rand.nextInt(5);
/* 43 */       for (int a = 0; a < q; a++) {
/* 44 */         ItemStack is = thaumcraft.common.lib.utils.Utils.generateLoot(stack.getItemDamage(), world.rand);
/* 45 */         if (is != null) {
/* 46 */           world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, is.copy()));
/*    */         }
/*    */       }
/*    */       
/* 50 */       world.playSoundAtEntity(player, "thaumcraft:coins", 0.75F, 1.0F);
/*    */     }
/* 52 */     stack.stackSize -= 1;
/* 53 */     return stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemLootBag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */