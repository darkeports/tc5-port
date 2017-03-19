/*     */ package thaumcraft.common.items.armor;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.items.IRepairable;
/*     */ import thaumcraft.api.items.IRunicArmor;
/*     */ import thaumcraft.api.items.IVisDiscountGear;
/*     */ import thaumcraft.api.items.IWarpingGear;
/*     */ import thaumcraft.client.renderers.models.gear.ModelRobe;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ 
/*     */ public class ItemCultistRobeArmor
/*     */   extends ItemArmor
/*     */   implements IRepairable, IRunicArmor, IVisDiscountGear, IWarpingGear
/*     */ {
/*     */   public ItemCultistRobeArmor(ItemArmor.ArmorMaterial enumarmormaterial, int j, int k)
/*     */   {
/*  30 */     super(enumarmormaterial, j, k);
/*  31 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
/*     */   {
/*  37 */     return "thaumcraft:textures/models/armor/cultist_robe_armor.png";
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  43 */     return EnumRarity.UNCOMMON;
/*     */   }
/*     */   
/*     */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*     */   {
/*  48 */     return par2ItemStack.isItemEqual(new ItemStack(Items.iron_ingot)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRunicCharge(ItemStack itemstack)
/*     */   {
/*  57 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect)
/*     */   {
/*  63 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*  67 */   ModelBiped model1 = null;
/*  68 */   ModelBiped model2 = null;
/*  69 */   ModelBiped model = null;
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
/*     */   {
/*  76 */     int type = ((ItemArmor)itemStack.getItem()).armorType;
/*     */     
/*  78 */     if (this.model1 == null) {
/*  79 */       this.model1 = new ModelRobe(1.0F);
/*     */     }
/*  81 */     if (this.model2 == null) {
/*  82 */       this.model2 = new ModelRobe(0.5F);
/*     */     }
/*  84 */     if ((type == 1) || (type == 3)) {
/*  85 */       this.model = this.model1;
/*     */     } else {
/*  87 */       this.model = this.model2;
/*     */     }
/*     */     
/*  90 */     if (this.model != null) {
/*  91 */       this.model.bipedHead.showModel = (armorSlot == 0);
/*  92 */       this.model.bipedHeadwear.showModel = (armorSlot == 0);
/*  93 */       this.model.bipedBody.showModel = ((armorSlot == 1) || (armorSlot == 2));
/*  94 */       this.model.bipedRightArm.showModel = (armorSlot == 1);
/*  95 */       this.model.bipedLeftArm.showModel = (armorSlot == 1);
/*  96 */       this.model.bipedRightLeg.showModel = (armorSlot == 2);
/*  97 */       this.model.bipedLeftLeg.showModel = (armorSlot == 2);
/*  98 */       this.model.isSneak = entityLiving.isSneaking();
/*     */       
/* 100 */       this.model.isRiding = entityLiving.isRiding();
/* 101 */       this.model.isChild = entityLiving.isChild();
/* 102 */       this.model.aimedBow = false;
/* 103 */       this.model.heldItemRight = (entityLiving.getHeldItem() != null ? 1 : 0);
/* 104 */       if ((entityLiving instanceof EntityPlayer))
/*     */       {
/* 106 */         if (((EntityPlayer)entityLiving).getItemInUseDuration() > 0)
/*     */         {
/* 108 */           EnumAction enumaction = ((EntityPlayer)entityLiving).getItemInUse().getItemUseAction();
/*     */           
/* 110 */           if (enumaction == EnumAction.BLOCK)
/*     */           {
/* 112 */             this.model.heldItemRight = 3;
/*     */           }
/* 114 */           else if (enumaction == EnumAction.BOW)
/*     */           {
/* 116 */             this.model.aimedBow = true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 123 */     return this.model;
/*     */   }
/*     */   
/*     */   public int getWarp(ItemStack itemstack, EntityPlayer player)
/*     */   {
/* 128 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\armor\ItemCultistRobeArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */