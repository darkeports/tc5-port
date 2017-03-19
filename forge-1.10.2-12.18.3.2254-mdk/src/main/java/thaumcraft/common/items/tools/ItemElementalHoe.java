/*    */ package thaumcraft.common.items.tools;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.Item.ToolMaterial;
/*    */ import net.minecraft.item.ItemHoe;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.items.IRepairable;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.client.fx.FXDispatcher;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ItemElementalHoe extends ItemHoe implements IRepairable
/*    */ {
/*    */   public ItemElementalHoe(Item.ToolMaterial enumtoolmaterial)
/*    */   {
/* 20 */     super(enumtoolmaterial);
/*    */   }
/*    */   
/*    */   public int getItemEnchantability()
/*    */   {
/* 25 */     return 5;
/*    */   }
/*    */   
/*    */ 
/*    */   public EnumRarity getRarity(ItemStack itemstack)
/*    */   {
/* 31 */     return EnumRarity.RARE;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*    */   {
/* 37 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing par7, float par8, float par9, float par10)
/*    */   {
/* 46 */     if (player.isSneaking()) { return super.onItemUse(stack, player, world, pos, par7, par8, par9, par10);
/*    */     }
/* 48 */     boolean did = false;
/* 49 */     for (int xx = -1; xx <= 1; xx++) {
/* 50 */       for (int zz = -1; zz <= 1; zz++) {
/* 51 */         if (super.onItemUse(stack, player, world, pos.add(xx, 0, zz), par7, par8, par9, par10)) {
/* 52 */           if (world.isRemote) {
/* 53 */             BlockPos pp = pos.add(xx, 0, zz);
/* 54 */             Thaumcraft.proxy.getFX().drawBamf(pp.getX() + 0.5D, pp.getY() + 1.01D, pp.getZ() + 0.5D, 0.3F, 0.12F, 0.1F, (xx == 0) && (zz == 0), true, true);
/*    */           }
/* 56 */           if (!did) { did = true;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 61 */     if (!did) {
/* 62 */       did = thaumcraft.common.lib.utils.Utils.useBonemealAtLoc(world, player, pos);
/* 63 */       if (did) {
/* 64 */         stack.damageItem(3, player);
/* 65 */         if (!world.isRemote)
/*    */         {
/* 67 */           world.playAuxSFX(2005, pos, 0);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 72 */     return did;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemElementalHoe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */