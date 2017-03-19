/*    */ package thaumcraft.common.lib.events;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
/*    */ import net.minecraftforge.fml.common.IFuelHandler;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.api.internal.EnumWarpType;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.api.wands.IWand;
/*    */ import thaumcraft.api.wands.ItemFocusBasic;
/*    */ import thaumcraft.common.config.Config;
/*    */ import thaumcraft.common.items.resources.ItemPhial;
/*    */ import thaumcraft.common.items.wands.foci.ItemFocusExcavation;
/*    */ import thaumcraft.common.lib.utils.Utils;
/*    */ 
/*    */ public class CraftingEvents implements IFuelHandler
/*    */ {
/*    */   public int getBurnTime(ItemStack fuel)
/*    */   {
/* 29 */     if (fuel.isItemEqual(new ItemStack(ItemsTC.alumentum))) return 6400;
/* 30 */     if (fuel.isItemEqual(new ItemStack(BlocksTC.log))) return 400;
/* 31 */     return 0;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onCrafting(PlayerEvent.ItemCraftedEvent event)
/*    */   {
/* 37 */     int warp = thaumcraft.api.ThaumcraftApi.getWarp(event.crafting);
/* 38 */     if ((!Config.wuss) && (warp > 0) && 
/* 39 */       (!event.player.worldObj.isRemote)) {
/* 40 */       thaumcraft.api.research.ResearchHelper.addWarpToPlayer(event.player, warp, EnumWarpType.NORMAL);
/*    */     }
/*    */     
/*    */ 
/* 44 */     if ((event.crafting.getItem() == ItemsTC.label) && (event.crafting.hasTagCompound())) {
/* 45 */       for (int var2 = 0; var2 < 9; var2++)
/*    */       {
/* 47 */         ItemStack var3 = event.craftMatrix.getStackInSlot(var2);
/* 48 */         if ((var3 != null) && ((var3.getItem() instanceof ItemPhial)))
/*    */         {
/* 50 */           var3.stackSize += 1;
/* 51 */           event.craftMatrix.setInventorySlotContents(var2, var3);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @SubscribeEvent
/*    */   public void harvestEvent(BlockEvent.HarvestDropsEvent event)
/*    */   {
/* 65 */     EntityPlayer player = event.harvester;
/* 66 */     if ((event.drops == null) || (event.drops.size() == 0) || (player == null)) { return;
/*    */     }
/* 68 */     ItemStack held = player.inventory.getCurrentItem();
/*    */     
/* 70 */     if ((held != null) && ((held.getItem() instanceof IWand)) && (((IWand)held.getItem()).getFocus(held) != null) && (((IWand)held.getItem()).getFocus(held).isUpgradedWith(((IWand)held.getItem()).getFocusStack(held), ItemFocusExcavation.dowsing)))
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 77 */       int fortune = 0;
/* 78 */       if ((held.getItem() instanceof IWand)) {
/* 79 */         fortune = ((IWand)held.getItem()).getFocus(held).getUpgradeLevel(((IWand)held.getItem()).getFocusStack(held), thaumcraft.api.wands.FocusUpgradeType.treasure);
/*    */       }
/*    */       
/*    */ 
/* 83 */       float chance = 0.2F + fortune * 0.075F;
/*    */       
/* 85 */       for (int a = 0; a < event.drops.size(); a++)
/*    */       {
/* 87 */         ItemStack is = (ItemStack)event.drops.get(a);
/* 88 */         ItemStack smr = Utils.findSpecialMiningResult(is, chance, event.world.rand);
/* 89 */         if (!is.isItemEqual(smr)) {
/* 90 */           event.drops.set(a, smr);
/* 91 */           if (!event.world.isRemote) {
/* 92 */             event.world.playSoundEffect(event.pos.getX() + 0.5F, event.pos.getY() + 0.5F, event.pos.getZ() + 0.5F, "random.orb", 0.2F, 0.7F + event.world.rand.nextFloat() * 0.2F);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\events\CraftingEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */