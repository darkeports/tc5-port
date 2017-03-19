/*     */ package thaumcraft.common.items.armor;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
/*     */ import net.minecraftforge.common.util.EnumHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.items.IGoggles;
/*     */ import thaumcraft.api.items.IRepairable;
/*     */ import thaumcraft.api.items.IRunicArmor;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.renderers.models.gear.ModelFortressArmor;
/*     */ 
/*     */ public class ItemFortressArmor extends ItemArmor implements IRepairable, IRunicArmor, ISpecialArmor, IGoggles, thaumcraft.api.items.IRevealer
/*     */ {
/*  30 */   public static ItemArmor.ArmorMaterial ARMORMAT_FORTRESS = EnumHelper.addArmorMaterial("FORTRESS", "FORTRESS", 40, new int[] { 3, 7, 6, 3 }, 25);
/*     */   
/*     */ 
/*     */   public ItemFortressArmor(ItemArmor.ArmorMaterial material, int renderIndex, int armorType)
/*     */   {
/*  35 */     super(material, renderIndex, armorType);
/*     */   }
/*     */   
/*     */ 
/*  39 */   ModelBiped model1 = null;
/*  40 */   ModelBiped model2 = null;
/*  41 */   ModelBiped model = null;
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
/*     */   {
/*  48 */     int type = ((ItemArmor)itemStack.getItem()).armorType;
/*  49 */     if (this.model1 == null) {
/*  50 */       this.model1 = new ModelFortressArmor(1.0F);
/*     */     }
/*  52 */     if (this.model2 == null) {
/*  53 */       this.model2 = new ModelFortressArmor(0.5F);
/*     */     }
/*  55 */     if ((type == 1) || (type == 3)) {
/*  56 */       this.model = this.model1;
/*     */     } else {
/*  58 */       this.model = this.model2;
/*     */     }
/*     */     
/*  61 */     if (this.model != null) {
/*  62 */       this.model.bipedHead.showModel = (armorSlot == 0);
/*  63 */       this.model.bipedHeadwear.showModel = (armorSlot == 0);
/*  64 */       this.model.bipedBody.showModel = ((armorSlot == 1) || (armorSlot == 2));
/*  65 */       this.model.bipedRightArm.showModel = (armorSlot == 1);
/*  66 */       this.model.bipedLeftArm.showModel = (armorSlot == 1);
/*  67 */       this.model.bipedRightLeg.showModel = (armorSlot == 2);
/*  68 */       this.model.bipedLeftLeg.showModel = (armorSlot == 2);
/*  69 */       this.model.isSneak = entityLiving.isSneaking();
/*     */       
/*  71 */       this.model.isRiding = entityLiving.isRiding();
/*  72 */       this.model.isChild = entityLiving.isChild();
/*  73 */       this.model.aimedBow = false;
/*  74 */       this.model.heldItemRight = (entityLiving.getHeldItem() != null ? 1 : 0);
/*  75 */       if ((entityLiving instanceof EntityPlayer))
/*     */       {
/*  77 */         if (((EntityPlayer)entityLiving).getItemInUseDuration() > 0)
/*     */         {
/*  79 */           EnumAction enumaction = ((EntityPlayer)entityLiving).getItemInUse().getItemUseAction();
/*     */           
/*  81 */           if (enumaction == EnumAction.BLOCK)
/*     */           {
/*  83 */             this.model.heldItemRight = 3;
/*     */           }
/*  85 */           else if (enumaction == EnumAction.BOW)
/*     */           {
/*  87 */             this.model.aimedBow = true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  94 */     return this.model;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
/*     */   {
/* 100 */     return "thaumcraft:textures/models/armor/fortress_armor.png";
/*     */   }
/*     */   
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/* 105 */     return EnumRarity.RARE;
/*     */   }
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*     */   {
/* 110 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("goggles"))) {
/* 111 */       list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("item.goggles.name"));
/*     */     }
/*     */     
/* 114 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("mask"))) {
/* 115 */       list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal(new StringBuilder().append("item.fortress_helm.mask.").append(stack.getTagCompound().getInteger("mask")).toString()));
/*     */     }
/*     */     
/*     */ 
/* 119 */     super.addInformation(stack, player, list, par4);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*     */   {
/* 125 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRunicCharge(ItemStack itemstack)
/*     */   {
/* 134 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
/*     */   {
/* 143 */     int priority = 0;
/* 144 */     double ratio = this.damageReduceAmount / 25.0D;
/*     */     
/* 146 */     if (source.isMagicDamage() == true) {
/* 147 */       priority = 1;
/* 148 */       ratio = this.damageReduceAmount / 35.0D;
/*     */     }
/* 150 */     else if ((source.isFireDamage() == true) || (source.isExplosion())) {
/* 151 */       priority = 1;
/* 152 */       ratio = this.damageReduceAmount / 20.0D;
/* 153 */     } else if (source.isUnblockable()) {
/* 154 */       priority = 0;
/* 155 */       ratio = 0.0D;
/*     */     }
/*     */     
/* 158 */     if ((player instanceof EntityPlayer)) {
/* 159 */       double set = 0.875D;
/* 160 */       for (int a = 1; a < 4; a++) {
/* 161 */         ItemStack piece = ((EntityPlayer)player).inventory.armorInventory[a];
/* 162 */         if ((piece != null) && ((piece.getItem() instanceof ItemFortressArmor))) {
/* 163 */           set += 0.125D;
/* 164 */           if ((piece.hasTagCompound()) && (piece.getTagCompound().hasKey("mask"))) {
/* 165 */             set += 0.05D;
/*     */           }
/*     */         }
/*     */       }
/* 169 */       ratio *= set;
/*     */     }
/*     */     
/* 172 */     return new ISpecialArmor.ArmorProperties(priority, ratio, armor.getMaxDamage() + 1 - armor.getItemDamage());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
/*     */   {
/* 179 */     return this.damageReduceAmount;
/*     */   }
/*     */   
/*     */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
/*     */   {
/* 184 */     if (source != DamageSource.fall) {
/* 185 */       stack.damageItem(damage, entity);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean showNodes(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 192 */     return (itemstack.hasTagCompound()) && (itemstack.getTagCompound().hasKey("goggles"));
/*     */   }
/*     */   
/*     */   public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 197 */     return (itemstack.hasTagCompound()) && (itemstack.getTagCompound().hasKey("goggles"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\armor\ItemFortressArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */