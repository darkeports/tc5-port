/*    */ package thaumcraft.common.items.tools;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.Item.ToolMaterial;
/*    */ import net.minecraft.item.ItemPickaxe;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.items.IRepairable;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
/*    */ 
/*    */ public class ItemElementalPickaxe extends ItemPickaxe implements IRepairable
/*    */ {
/*    */   public ItemElementalPickaxe(Item.ToolMaterial enumtoolmaterial)
/*    */   {
/* 26 */     super(enumtoolmaterial);
/*    */   }
/*    */   
/*    */   public Set<String> getToolClasses(ItemStack stack)
/*    */   {
/* 31 */     return ImmutableSet.of("pickaxe");
/*    */   }
/*    */   
/*    */ 
/*    */   public EnumRarity getRarity(ItemStack itemstack)
/*    */   {
/* 37 */     return EnumRarity.RARE;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*    */   {
/* 43 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
/*    */   {
/* 49 */     if ((!player.worldObj.isRemote) && (
/* 50 */       (!(entity instanceof EntityPlayer)) || (MinecraftServer.getServer().isPVPEnabled())))
/*    */     {
/*    */ 
/* 53 */       entity.setFire(2);
/*    */     }
/*    */     
/* 56 */     return super.onLeftClickEntity(stack, player, entity);
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*    */   {
/* 62 */     ItemStack w1 = new ItemStack(this);
/* 63 */     EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.REFINING, 1);
/* 64 */     EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.SOUNDING, 2);
/* 65 */     par3List.add(w1);
/*    */   }
/*    */   
/*    */   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
/*    */   {
/* 70 */     super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
/*    */     
/* 72 */     if (entityIn.ticksExisted % 100 == 0) {
/* 73 */       List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(stack);
/* 74 */       if (!list.contains(EnumInfusionEnchantment.REFINING)) {
/* 75 */         EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.REFINING, 1);
/*    */       }
/* 77 */       if (!list.contains(EnumInfusionEnchantment.SOUNDING)) {
/* 78 */         EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.SOUNDING, 2);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemElementalPickaxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */