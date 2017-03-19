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
/*    */ import thaumcraft.api.items.ItemRunic;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.items.IVariantItems;
/*    */ import thaumcraft.common.lib.events.PlayerEvents;
/*    */ 
/*    */ public class ItemRingRunic
/*    */   extends ItemRunic implements IBauble, IVariantItems
/*    */ {
/*    */   public ItemRingRunic()
/*    */   {
/* 23 */     super(5);
/* 24 */     this.maxStackSize = 1;
/* 25 */     this.canRepair = false;
/* 26 */     setMaxDamage(0);
/* 27 */     setCreativeTab(Thaumcraft.tabTC);
/* 28 */     setHasSubtypes(true);
/*    */   }
/*    */   
/*    */   public String[] getVariantNames()
/*    */   {
/* 33 */     return new String[] { "protection", "base", "charge", "regen" };
/*    */   }
/*    */   
/*    */   public int[] getVariantMeta()
/*    */   {
/* 38 */     return new int[] { 0, 1, 2, 3 };
/*    */   }
/*    */   
/*    */   public EnumRarity getRarity(ItemStack itemstack)
/*    */   {
/* 43 */     return itemstack.getItemDamage() == 0 ? EnumRarity.UNCOMMON : EnumRarity.RARE;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/*    */   {
/* 49 */     return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*    */   {
/* 55 */     par3List.add(new ItemStack(this, 1, 0));
/* 56 */     par3List.add(new ItemStack(this, 1, 1));
/* 57 */     par3List.add(new ItemStack(this, 1, 2));
/* 58 */     par3List.add(new ItemStack(this, 1, 3));
/*    */   }
/*    */   
/*    */   public BaubleType getBaubleType(ItemStack itemstack)
/*    */   {
/* 63 */     return BaubleType.RING;
/*    */   }
/*    */   
/*    */ 
/*    */   public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}
/*    */   
/*    */   public void onEquipped(ItemStack itemstack, EntityLivingBase player)
/*    */   {
/* 71 */     PlayerEvents.markRunicDirty(player);
/*    */   }
/*    */   
/*    */   public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
/*    */   {
/* 76 */     PlayerEvents.markRunicDirty(player);
/*    */   }
/*    */   
/*    */   public int getRunicCharge(ItemStack itemstack)
/*    */   {
/* 81 */     return itemstack.getItemDamage() == 1 ? 5 : itemstack.getItemDamage() == 0 ? 1 : 4;
/*    */   }
/*    */   
/*    */   public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
/*    */   {
/* 86 */     return true;
/*    */   }
/*    */   
/*    */   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
/*    */   {
/* 91 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\baubles\ItemRingRunic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */