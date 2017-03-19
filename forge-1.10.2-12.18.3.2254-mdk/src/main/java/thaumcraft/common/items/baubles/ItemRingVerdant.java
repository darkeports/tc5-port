/*     */ package thaumcraft.common.items.baubles;
/*     */ 
/*     */ import baubles.api.BaubleType;
/*     */ import baubles.api.IBauble;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.FoodStats;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.items.IRechargable;
/*     */ import thaumcraft.api.potions.PotionFluxTaint;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.lib.events.PlayerEvents;
/*     */ 
/*     */ public class ItemRingVerdant extends Item implements IBauble, IRechargable
/*     */ {
/*     */   public ItemRingVerdant()
/*     */   {
/*  35 */     this.maxStackSize = 1;
/*  36 */     this.canRepair = false;
/*  37 */     setMaxDamage(200);
/*  38 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  48 */     return EnumRarity.RARE;
/*     */   }
/*     */   
/*     */   public BaubleType getBaubleType(ItemStack itemstack)
/*     */   {
/*  53 */     return BaubleType.RING;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*     */   {
/*  59 */     par3List.add(new ItemStack(this));
/*  60 */     ItemStack vhbl = new ItemStack(this);
/*  61 */     vhbl.setTagInfo("type", new NBTTagByte((byte)1));
/*  62 */     par3List.add(vhbl);
/*  63 */     ItemStack vhbl2 = new ItemStack(this);
/*  64 */     vhbl2.setTagInfo("type", new NBTTagByte((byte)2));
/*  65 */     par3List.add(vhbl2);
/*     */   }
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*     */   {
/*  70 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().getByte("type") == 1)) {
/*  71 */       list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.verdant.life.text"));
/*     */     }
/*  73 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().getByte("type") == 2)) {
/*  74 */       list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.verdant.sustain.text"));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onWornTick(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/*  80 */     if ((!player.worldObj.isRemote) && (player.ticksExisted % 20 == 0)) {
/*  81 */       if ((player.getActivePotionEffect(Potion.wither) != null) && (itemstack.getItemDamage() + 20 <= itemstack.getMaxDamage())) {
/*  82 */         itemstack.damageItem(20, player);
/*  83 */         player.removePotionEffect(Potion.wither.getId());
/*  84 */         return;
/*     */       }
/*  86 */       if ((player.getActivePotionEffect(Potion.poison) != null) && (itemstack.getItemDamage() + 10 <= itemstack.getMaxDamage())) {
/*  87 */         itemstack.damageItem(10, player);
/*  88 */         player.removePotionEffect(Potion.poison.getId());
/*  89 */         return;
/*     */       }
/*  91 */       if ((player.getActivePotionEffect(PotionFluxTaint.instance) != null) && (itemstack.getItemDamage() + 5 <= itemstack.getMaxDamage())) {
/*  92 */         itemstack.damageItem(5, player);
/*  93 */         player.removePotionEffect(PotionFluxTaint.instance.getId());
/*  94 */         return;
/*     */       }
/*  96 */       if ((itemstack.hasTagCompound()) && (itemstack.getTagCompound().getByte("type") == 1) && (player.getHealth() < player.getMaxHealth()) && (itemstack.getItemDamage() + 5 <= itemstack.getMaxDamage()))
/*     */       {
/*  98 */         itemstack.damageItem(5, player);
/*  99 */         player.heal(1.0F);
/* 100 */         return;
/*     */       }
/* 102 */       if ((itemstack.hasTagCompound()) && (itemstack.getTagCompound().getByte("type") == 2) && (itemstack.getItemDamage() + 1 <= itemstack.getMaxDamage())) {
/* 103 */         if (player.getAir() < 100) {
/* 104 */           itemstack.damageItem(1, player);
/* 105 */           player.setAir(300);
/* 106 */           return;
/*     */         }
/* 108 */         if (((player instanceof EntityPlayer)) && (((EntityPlayer)player).canEat(false))) {
/* 109 */           itemstack.damageItem(1, player);
/* 110 */           ((EntityPlayer)player).getFoodStats().addStats(1, 0.3F);
/* 111 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
/* 118 */     return true;
/*     */   }
/*     */   
/* 121 */   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) { return true; }
/*     */   
/*     */   public Aspect handleRecharge(World world, ItemStack is, BlockPos pos, EntityPlayer player, int amount)
/*     */   {
/* 125 */     if ((player == null) || ((!AuraHandler.shouldPreserveAura(world, player, pos, Aspect.EARTH)) && (!AuraHandler.shouldPreserveAura(world, player, pos, Aspect.WATER))))
/*     */     {
/* 127 */       amount = Math.min(amount, is.getItemDamage());
/* 128 */       int q = Math.round((AuraHelper.drainAuraAvailable(world, pos, Aspect.EARTH, amount) + AuraHelper.drainAuraAvailable(world, pos, Aspect.WATER, amount)) / 2.0F);
/* 129 */       is.setItemDamage(is.getItemDamage() - q);
/* 130 */       return q > 0 ? Aspect.LIFE : null;
/*     */     }
/* 132 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public AspectList getAspectsInChargable(ItemStack is)
/*     */   {
/* 138 */     return new AspectList().add(Aspect.LIFE, is.getMaxDamage() - is.getItemDamage());
/*     */   }
/*     */   
/*     */   public float getChargeLevel(ItemStack is)
/*     */   {
/* 143 */     return 1.0F - is.getItemDamage() / is.getMaxDamage();
/*     */   }
/*     */   
/*     */ 
/*     */   public void onEquipped(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 149 */     PlayerEvents.markRunicDirty(player);
/*     */   }
/*     */   
/*     */   public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 154 */     PlayerEvents.markRunicDirty(player);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\baubles\ItemRingVerdant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */