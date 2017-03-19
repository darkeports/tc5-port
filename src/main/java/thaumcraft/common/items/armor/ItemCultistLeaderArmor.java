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
/*     */ import net.minecraftforge.common.util.EnumHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.items.IRepairable;
/*     */ import thaumcraft.api.items.IRunicArmor;
/*     */ import thaumcraft.client.renderers.models.gear.ModelLeaderArmor;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class ItemCultistLeaderArmor extends ItemArmor implements IRepairable, IRunicArmor
/*     */ {
/*  23 */   public static ItemArmor.ArmorMaterial ARMORMAT_PREATOR = EnumHelper.addArmorMaterial("FORTRESS", "FORTRESS", 45, new int[] { 3, 8, 6, 3 }, 20);
/*     */   
/*     */   public ItemCultistLeaderArmor(int j, int k)
/*     */   {
/*  27 */     super(ARMORMAT_PREATOR, j, k);
/*  28 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
/*     */   {
/*  33 */     return "thaumcraft:textures/models/armor/cultist_leader_armor.png";
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  39 */     return EnumRarity.RARE;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*     */   {
/*  45 */     return par2ItemStack.isItemEqual(new ItemStack(Items.iron_ingot)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRunicCharge(ItemStack itemstack)
/*     */   {
/*  54 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*  58 */   ModelBiped model1 = null;
/*  59 */   ModelBiped model2 = null;
/*  60 */   ModelBiped model = null;
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
/*     */   {
/*  67 */     int type = ((ItemArmor)itemStack.getItem()).armorType;
/*     */     
/*  69 */     if (this.model1 == null) {
/*  70 */       this.model1 = new ModelLeaderArmor(1.0F);
/*     */     }
/*  72 */     if (this.model2 == null) {
/*  73 */       this.model2 = new ModelLeaderArmor(0.5F);
/*     */     }
/*  75 */     if ((type == 1) || (type == 3)) {
/*  76 */       this.model = this.model1;
/*     */     } else {
/*  78 */       this.model = this.model2;
/*     */     }
/*     */     
/*  81 */     if (this.model != null) {
/*  82 */       this.model.bipedHead.showModel = (armorSlot == 0);
/*  83 */       this.model.bipedHeadwear.showModel = (armorSlot == 0);
/*  84 */       this.model.bipedBody.showModel = ((armorSlot == 1) || (armorSlot == 2));
/*  85 */       this.model.bipedRightArm.showModel = (armorSlot == 1);
/*  86 */       this.model.bipedLeftArm.showModel = (armorSlot == 1);
/*  87 */       this.model.bipedRightLeg.showModel = (armorSlot == 2);
/*  88 */       this.model.bipedLeftLeg.showModel = (armorSlot == 2);
/*  89 */       this.model.isSneak = entityLiving.isSneaking();
/*     */       
/*  91 */       this.model.isRiding = entityLiving.isRiding();
/*  92 */       this.model.isChild = entityLiving.isChild();
/*  93 */       this.model.aimedBow = false;
/*  94 */       this.model.heldItemRight = (entityLiving.getHeldItem() != null ? 1 : 0);
/*  95 */       if ((entityLiving instanceof EntityPlayer))
/*     */       {
/*  97 */         if (((EntityPlayer)entityLiving).getItemInUseDuration() > 0)
/*     */         {
/*  99 */           EnumAction enumaction = ((EntityPlayer)entityLiving).getItemInUse().getItemUseAction();
/*     */           
/* 101 */           if (enumaction == EnumAction.BLOCK)
/*     */           {
/* 103 */             this.model.heldItemRight = 3;
/*     */           }
/* 105 */           else if (enumaction == EnumAction.BOW)
/*     */           {
/* 107 */             this.model.aimedBow = true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 114 */     return this.model;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\armor\ItemCultistLeaderArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */