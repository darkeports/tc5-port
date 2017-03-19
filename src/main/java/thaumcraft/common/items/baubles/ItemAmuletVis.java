/*     */ package thaumcraft.common.items.baubles;
/*     */ 
/*     */ import baubles.api.BaubleType;
/*     */ import baubles.api.BaublesApi;
/*     */ import baubles.api.IBauble;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.items.IRechargable;
/*     */ import thaumcraft.api.items.IRunicArmor;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.items.IVariantItems;
/*     */ import thaumcraft.common.lib.events.PlayerEvents;
/*     */ 
/*     */ public class ItemAmuletVis extends Item implements IBauble, IRunicArmor, IVariantItems
/*     */ {
/*     */   public ItemAmuletVis()
/*     */   {
/*  31 */     this.maxStackSize = 1;
/*  32 */     this.canRepair = false;
/*  33 */     setMaxDamage(0);
/*  34 */     setCreativeTab(Thaumcraft.tabTC);
/*  35 */     setHasSubtypes(true);
/*     */   }
/*     */   
/*     */   public String[] getVariantNames()
/*     */   {
/*  40 */     return new String[] { "found", "crafted" };
/*     */   }
/*     */   
/*     */   public int[] getVariantMeta()
/*     */   {
/*  45 */     return new int[] { 0, 1 };
/*     */   }
/*     */   
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  50 */     return itemstack.getItemDamage() == 0 ? EnumRarity.UNCOMMON : EnumRarity.RARE;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getUnlocalizedName(ItemStack stack)
/*     */   {
/*  56 */     return super.getUnlocalizedName() + "." + stack.getItemDamage();
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*     */   {
/*  62 */     par3List.add(new ItemStack(this, 1, 0));
/*  63 */     par3List.add(new ItemStack(this, 1, 1));
/*     */   }
/*     */   
/*     */   public BaubleType getBaubleType(ItemStack itemstack)
/*     */   {
/*  68 */     return BaubleType.AMULET;
/*     */   }
/*     */   
/*     */   public void onWornTick(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/*  73 */     if (((player instanceof EntityPlayer)) && (!player.worldObj.isRemote)) if (player.ticksExisted % (itemstack.getItemDamage() == 0 ? 40 : 5) == 0)
/*     */       {
/*  75 */         ItemStack[] inv = ((EntityPlayer)player).inventory.mainInventory;
/*  76 */         for (int a = 0; a < InventoryPlayer.getHotbarSize(); a++) {
/*  77 */           if ((inv[a] != null) && ((inv[a].getItem() instanceof IRechargable))) {
/*  78 */             IRechargable chargable = (IRechargable)inv[a].getItem();
/*  79 */             if (chargable.handleRecharge(player.worldObj, inv[a], new BlockPos(player), (EntityPlayer)player, 1) != null) { return;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*  84 */         IInventory baubles = BaublesApi.getBaubles((EntityPlayer)player);
/*  85 */         for (int a = 0; a < 4; a++) {
/*  86 */           if ((baubles.getStackInSlot(a) != null) && ((baubles.getStackInSlot(a).getItem() instanceof IRechargable))) {
/*  87 */             IRechargable chargable = (IRechargable)baubles.getStackInSlot(a).getItem();
/*  88 */             if (chargable.handleRecharge(player.worldObj, baubles.getStackInSlot(a), new BlockPos(player), (EntityPlayer)player, 1) != null) { return;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*  93 */         inv = ((EntityPlayer)player).inventory.armorInventory;
/*  94 */         for (int a = 0; a < inv.length; a++) {
/*  95 */           if ((inv[a] != null) && ((inv[a].getItem() instanceof IRechargable))) {
/*  96 */             IRechargable chargable = (IRechargable)inv[a].getItem();
/*  97 */             if (chargable.handleRecharge(player.worldObj, inv[a], new BlockPos(player), (EntityPlayer)player, 1) != null) { return;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */   
/*     */   public void onEquipped(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 106 */     PlayerEvents.markRunicDirty(player);
/*     */   }
/*     */   
/*     */   public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 111 */     PlayerEvents.markRunicDirty(player);
/*     */   }
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*     */   {
/* 116 */     if (stack.getItemDamage() == 0) {
/* 117 */       list.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("item.amulet_vis.text"));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 123 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 128 */     return true;
/*     */   }
/*     */   
/*     */   public int getRunicCharge(ItemStack itemstack) {
/* 132 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\baubles\ItemAmuletVis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */