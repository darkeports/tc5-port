/*    */ package thaumcraft.common.items.tools;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.config.Config;
/*    */ import thaumcraft.common.lib.aura.AuraHandler;
/*    */ 
/*    */ 
/*    */ public class ItemCreativeFluxSponge
/*    */   extends Item
/*    */ {
/*    */   public ItemCreativeFluxSponge()
/*    */   {
/* 23 */     setMaxStackSize(1);
/* 24 */     setHasSubtypes(false);
/* 25 */     setMaxDamage(0);
/* 26 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*    */   {
/* 31 */     super.addInformation(stack, player, list, par4);
/* 32 */     list.add(EnumChatFormatting.GREEN + "Right-click to drain all");
/* 33 */     list.add(EnumChatFormatting.GREEN + "flux from 9x9 chunk area");
/* 34 */     list.add(EnumChatFormatting.DARK_PURPLE + "Creative only");
/*    */   }
/*    */   
/*    */ 
/*    */   public EnumRarity getRarity(ItemStack itemstack)
/*    */   {
/* 40 */     return EnumRarity.EPIC;
/*    */   }
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
/*    */   {
/* 45 */     if (worldIn.isRemote) {
/* 46 */       playerIn.swingItem();
/* 47 */       playerIn.worldObj.playSound(playerIn.posX, playerIn.posY, playerIn.posZ, "thaumcraft:craftstart", 0.15F, 1.0F, false);
/*    */     } else {
/* 49 */       int q = 0;
/* 50 */       BlockPos p = playerIn.getPosition();
/* 51 */       for (int x = -4; x <= 4; x++) {
/* 52 */         for (int z = -4; z <= 4; z++)
/* 53 */           q += AuraHandler.drainAuraAvailable(worldIn, p.add(16 * x, 0, 16 * z), Aspect.FLUX, Config.AURABASE);
/*    */       }
/* 55 */       playerIn.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + q + " flux drained from 81 chunks."));
/*    */     }
/* 57 */     return super.onItemRightClick(itemStackIn, worldIn, playerIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemCreativeFluxSponge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */