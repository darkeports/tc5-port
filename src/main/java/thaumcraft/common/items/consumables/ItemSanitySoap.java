/*    */ package thaumcraft.common.items.consumables;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumAction;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.internal.EnumWarpType;
/*    */ import thaumcraft.api.research.ResearchHelper;
/*    */ import thaumcraft.client.fx.FXDispatcher;
/*    */ import thaumcraft.common.CommonProxy;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.lib.research.PlayerKnowledge;
/*    */ 
/*    */ public class ItemSanitySoap extends net.minecraft.item.Item
/*    */ {
/*    */   public ItemSanitySoap()
/*    */   {
/* 21 */     setCreativeTab(Thaumcraft.tabTC);
/* 22 */     setHasSubtypes(false);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getMaxItemUseDuration(ItemStack p_77626_1_)
/*    */   {
/* 31 */     return 200;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EnumAction getItemUseAction(ItemStack p_77661_1_)
/*    */   {
/* 40 */     return EnumAction.BLOCK;
/*    */   }
/*    */   
/*    */ 
/*    */   public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
/*    */   {
/* 46 */     p_77659_3_.setItemInUse(p_77659_1_, getMaxItemUseDuration(p_77659_1_));
/* 47 */     return p_77659_1_;
/*    */   }
/*    */   
/*    */   public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
/*    */   {
/* 52 */     int ticks = getMaxItemUseDuration(stack) - count;
/* 53 */     if (ticks > 195) player.stopUsingItem();
/* 54 */     if (player.worldObj.isRemote) {
/* 55 */       if (player.worldObj.rand.nextFloat() < 0.2F) {
/* 56 */         player.worldObj.playSound(player.posX, player.posY, player.posZ, "thaumcraft:roots", 0.1F, 1.5F + player.worldObj.rand.nextFloat() * 0.2F, false);
/*    */       }
/* 58 */       for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(5); a++) {
/* 59 */         Thaumcraft.proxy.getFX().crucibleBubble((float)player.posX - 0.5F + player.worldObj.rand.nextFloat(), (float)player.getEntityBoundingBox().minY + player.worldObj.rand.nextFloat() * player.height, (float)player.posZ - 0.5F + player.worldObj.rand.nextFloat(), 1.0F, 0.8F, 0.9F);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int par4)
/*    */   {
/* 70 */     int qq = getMaxItemUseDuration(stack) - par4;
/* 71 */     if (qq > 195) {
/* 72 */       stack.stackSize -= 1;
/* 73 */       if (!world.isRemote) {
/* 74 */         float chance = 0.33F;
/* 75 */         if (player.isPotionActive(thaumcraft.common.lib.potions.PotionWarpWard.instance)) chance += 0.25F;
/* 76 */         int i = MathHelper.floor_double(player.posX);
/* 77 */         int j = MathHelper.floor_double(player.posY);
/* 78 */         int k = MathHelper.floor_double(player.posZ);
/* 79 */         if (world.getBlockState(new net.minecraft.util.BlockPos(i, j, k)).getBlock() == thaumcraft.api.blocks.BlocksTC.purifyingFluid) chance += 0.25F;
/* 80 */         if ((world.rand.nextFloat() < chance) && (Thaumcraft.proxy.getPlayerKnowledge().getWarpSticky(player.getName()) > 0)) {
/* 81 */           ResearchHelper.addWarpToPlayer(player, -1, EnumWarpType.NORMAL);
/*    */         }
/* 83 */         if (Thaumcraft.proxy.getPlayerKnowledge().getWarpTemp(player.getName()) > 0) {
/* 84 */           ResearchHelper.addWarpToPlayer(player, -Thaumcraft.proxy.getPlayerKnowledge().getWarpTemp(player.getName()), EnumWarpType.TEMPORARY);
/*    */         }
/*    */       }
/*    */       else {
/* 88 */         player.worldObj.playSound(player.posX, player.posY, player.posZ, "thaumcraft:craftstart", 0.25F, 1.0F, false);
/* 89 */         for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(20); a++) {
/* 90 */           Thaumcraft.proxy.getFX().crucibleBubble((float)player.posX - 0.5F + player.worldObj.rand.nextFloat() * 1.5F, (float)player.getEntityBoundingBox().minY + player.worldObj.rand.nextFloat() * player.height, (float)player.posZ - 0.5F + player.worldObj.rand.nextFloat() * 1.5F, 1.0F, 0.7F, 0.9F);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemSanitySoap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */