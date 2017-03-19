/*    */ package thaumcraft.common.items.baubles;
/*    */ 
/*    */ import baubles.api.BaubleType;
/*    */ import baubles.api.IBauble;
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.items.IRunicArmor;
/*    */ import thaumcraft.api.items.ItemRunic;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.items.IVariantItems;
/*    */ import thaumcraft.common.lib.events.PlayerEvents;
/*    */ 
/*    */ public class ItemAmuletRunic
/*    */   extends ItemRunic implements IBauble, IRunicArmor, IVariantItems
/*    */ {
/*    */   public ItemAmuletRunic()
/*    */   {
/* 24 */     super(8);
/* 25 */     this.maxStackSize = 1;
/* 26 */     this.canRepair = false;
/* 27 */     setMaxDamage(0);
/* 28 */     setCreativeTab(Thaumcraft.tabTC);
/* 29 */     setHasSubtypes(true);
/*    */   }
/*    */   
/*    */   public String[] getVariantNames()
/*    */   {
/* 34 */     return new String[] { "base", "emergency" };
/*    */   }
/*    */   
/*    */   public int[] getVariantMeta()
/*    */   {
/* 39 */     return new int[] { 0, 1 };
/*    */   }
/*    */   
/*    */   public EnumRarity getRarity(ItemStack par1ItemStack)
/*    */   {
/* 44 */     return EnumRarity.RARE;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*    */   {
/* 50 */     par3List.add(new ItemStack(this, 1, 0));
/* 51 */     par3List.add(new ItemStack(this, 1, 1));
/*    */   }
/*    */   
/*    */   public BaubleType getBaubleType(ItemStack itemstack)
/*    */   {
/* 56 */     return BaubleType.AMULET;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/*    */   {
/* 62 */     return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
/*    */   }
/*    */   
/*    */   public int getRunicCharge(ItemStack itemstack)
/*    */   {
/* 67 */     return itemstack.getItemDamage() == 0 ? 8 : 7;
/*    */   }
/*    */   
/*    */ 
/*    */   public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}
/*    */   
/*    */   public void onEquipped(ItemStack itemstack, EntityLivingBase player)
/*    */   {
/* 75 */     PlayerEvents.markRunicDirty(player);
/*    */   }
/*    */   
/*    */   public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
/*    */   {
/* 80 */     PlayerEvents.markRunicDirty(player);
/*    */   }
/*    */   
/*    */   public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
/*    */   {
/* 85 */     return true;
/*    */   }
/*    */   
/*    */   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
/*    */   {
/* 90 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\baubles\ItemAmuletRunic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */