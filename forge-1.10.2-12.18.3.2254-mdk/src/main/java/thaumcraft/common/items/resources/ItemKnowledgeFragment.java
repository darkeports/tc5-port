/*    */ package thaumcraft.common.items.resources;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.item.EntityXPOrb;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.PlayerCapabilities;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemKnowledgeFragment
/*    */   extends Item
/*    */ {
/*    */   public ItemKnowledgeFragment()
/*    */   {
/* 18 */     setMaxStackSize(64);
/*    */   }
/*    */   
/*    */ 
/*    */   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
/*    */   {
/* 24 */     if (!world.isRemote)
/*    */     {
/* 26 */       if (!player.capabilities.isCreativeMode) itemstack.stackSize -= 1;
/* 27 */       int i = 4 + world.rand.nextInt(5);
/* 28 */       while (i > 0)
/*    */       {
/* 30 */         int j = EntityXPOrb.getXPSplit(i);
/* 31 */         i -= j;
/* 32 */         world.spawnEntityInWorld(new EntityXPOrb(world, player.posX, player.posY, player.posZ, j));
/*    */       }
/*    */     }
/* 35 */     return itemstack;
/*    */   }
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*    */   {
/* 40 */     list.add("§6" + StatCollector.translateToLocal("item.knowledge_fragment.help"));
/* 41 */     super.addInformation(stack, player, list, par4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\resources\ItemKnowledgeFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */