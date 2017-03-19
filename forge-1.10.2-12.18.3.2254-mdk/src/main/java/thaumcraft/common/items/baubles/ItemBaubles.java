/*     */ package thaumcraft.common.items.baubles;
/*     */ 
/*     */ import baubles.api.BaubleType;
/*     */ import baubles.api.IBauble;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.items.IRunicArmor;
/*     */ import thaumcraft.api.items.IVisDiscountGear;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.items.IVariantItems;
/*     */ import thaumcraft.common.lib.events.PlayerEvents;
/*     */ 
/*     */ public class ItemBaubles
/*     */   extends Item implements IBauble, IVisDiscountGear, IRunicArmor, IVariantItems
/*     */ {
/*     */   public ItemBaubles()
/*     */   {
/*  29 */     this.maxStackSize = 1;
/*  30 */     this.canRepair = false;
/*  31 */     setMaxDamage(0);
/*  32 */     setCreativeTab(Thaumcraft.tabTC);
/*  33 */     setHasSubtypes(true);
/*     */   }
/*     */   
/*     */   public String[] getVariantNames()
/*     */   {
/*  38 */     return new String[] { "amulet_mundane", "ring_mundane", "girdle_mundane", "ring_apprentice", "ring_apprentice", "ring_apprentice", "ring_apprentice", "ring_apprentice", "ring_apprentice", "amulet_fancy", "ring_fancy", "girdle_fancy" };
/*     */   }
/*     */   
/*     */   public int[] getVariantMeta()
/*     */   {
/*  43 */     return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
/*     */   }
/*     */   
/*     */ 
/*     */   public String getUnlocalizedName(ItemStack par1ItemStack)
/*     */   {
/*  49 */     return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*     */   {
/*  55 */     for (int a = 0; a < getVariantNames().length; a++) {
/*  56 */       par3List.add(new ItemStack(this, 1, a));
/*     */     }
/*     */   }
/*     */   
/*     */   public BaubleType getBaubleType(ItemStack itemstack)
/*     */   {
/*  62 */     switch (itemstack.getItemDamage()) {
/*  63 */     case 1: case 3: case 4: case 5: case 6: case 7: case 8: case 10:  return BaubleType.RING;
/*  64 */     case 2: case 11:  return BaubleType.BELT;
/*     */     }
/*  66 */     return BaubleType.AMULET;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}
/*     */   
/*     */   public void onEquipped(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/*  74 */     PlayerEvents.markRunicDirty(player);
/*     */   }
/*     */   
/*     */   public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/*  79 */     PlayerEvents.markRunicDirty(player);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack stack)
/*     */   {
/*  86 */     if ((stack.getItemDamage() >= 3) && (stack.getItemDamage() <= 8)) {
/*  87 */       return EnumRarity.UNCOMMON;
/*     */     }
/*  89 */     return super.getRarity(stack);
/*     */   }
/*     */   
/*     */   public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect)
/*     */   {
/*  94 */     if ((stack.getItemDamage() >= 3) && (stack.getItemDamage() <= 8) && (Aspect.getPrimalAspects().get(stack.getItemDamage() - 3) == aspect))
/*     */     {
/*  96 */       if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey(aspect.getTag()))) {
/*  97 */         return stack.getTagCompound().getInteger(aspect.getTag());
/*     */       }
/*  99 */       return 5;
/*     */     }
/* 101 */     return 0;
/*     */   }
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int par2)
/*     */   {
/* 106 */     if ((stack.getItemDamage() >= 3) && (stack.getItemDamage() <= 8)) {
/* 107 */       if ((!stack.hasTagCompound()) || (!stack.getTagCompound().hasKey("color"))) {
/* 108 */         calcColor(stack);
/*     */       }
/* 110 */       return stack.getTagCompound().getInteger("color");
/*     */     }
/* 112 */     return super.getColorFromItemStack(stack, par2);
/*     */   }
/*     */   
/*     */   public void calcColor(ItemStack stack) {
/* 116 */     int l = ((Aspect)Aspect.getPrimalAspects().get(stack.getItemDamage() - 3)).getColor();
/* 117 */     int c = 1;
/* 118 */     int r = l >> 16 & 0xFF;
/* 119 */     int g = l >> 8 & 0xFF;
/* 120 */     int b = l & 0xFF;
/* 121 */     if (stack.hasTagCompound()) {
/* 122 */       for (Aspect aspect : Aspect.getPrimalAspects()) {
/* 123 */         if ((aspect != Aspect.getPrimalAspects().get(stack.getItemDamage() - 3)) && (stack.getTagCompound().hasKey(aspect.getTag())))
/*     */         {
/* 125 */           l = aspect.getColor();
/* 126 */           r += (l >> 16 & 0xFF);
/* 127 */           g += (l >> 8 & 0xFF);
/* 128 */           b += (l & 0xFF);
/* 129 */           c++;
/*     */         }
/*     */       }
/*     */     }
/* 133 */     r /= c;
/* 134 */     g /= c;
/* 135 */     b /= c;
/* 136 */     int oc = r << 16 | g << 8 | b;
/* 137 */     if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
/* 138 */     stack.getTagCompound().setInteger("color", oc);
/*     */   }
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack)
/*     */   {
/* 143 */     if ((stack.getItemDamage() >= 3) && (stack.getItemDamage() <= 8)) {
/* 144 */       Aspect aspect = (Aspect)Aspect.getPrimalAspects().get(stack.getItemDamage() - 3);
/* 145 */       return StatCollector.translateToLocal("item.baubles.3.name").replace("%TYPE", aspect.getName());
/*     */     }
/* 147 */     return super.getItemStackDisplayName(stack);
/*     */   }
/*     */   
/*     */   public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 152 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 157 */     return true;
/*     */   }
/*     */   
/*     */   public int getRunicCharge(ItemStack itemstack)
/*     */   {
/* 162 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\baubles\ItemBaubles.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */