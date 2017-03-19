/*     */ package thaumcraft.common.items.armor;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.items.IRepairable;
/*     */ import thaumcraft.api.items.IRunicArmor;
/*     */ import thaumcraft.client.renderers.models.gear.ModelKnightArmor;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class ItemCultistPlateArmor extends ItemArmor implements IRepairable, IRunicArmor
/*     */ {
/*     */   public ItemCultistPlateArmor(ItemArmor.ArmorMaterial enumarmormaterial, int j, int k)
/*     */   {
/*  23 */     super(enumarmormaterial, j, k);
/*  24 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
/*     */   {
/*  30 */     return (entity instanceof thaumcraft.common.entities.monster.EntityInhabitedZombie) ? "thaumcraft:textures/models/armor/zombie_plate_armor.png" : "thaumcraft:textures/models/armor/cultist_plate_armor.png";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  38 */     return EnumRarity.UNCOMMON;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*     */   {
/*  44 */     return par2ItemStack.isItemEqual(new ItemStack(net.minecraft.init.Items.iron_ingot)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRunicCharge(ItemStack itemstack)
/*     */   {
/*  53 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*  57 */   ModelBiped model1 = null;
/*  58 */   ModelBiped model2 = null;
/*  59 */   ModelBiped model = null;
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
/*     */   {
/*  66 */     int type = ((ItemArmor)itemStack.getItem()).armorType;
/*     */     
/*  68 */     if (this.model1 == null) {
/*  69 */       this.model1 = new ModelKnightArmor(1.0F);
/*     */     }
/*  71 */     if (this.model2 == null) {
/*  72 */       this.model2 = new ModelKnightArmor(0.5F);
/*     */     }
/*  74 */     if ((type == 1) || (type == 3)) {
/*  75 */       this.model = this.model1;
/*     */     } else {
/*  77 */       this.model = this.model2;
/*     */     }
/*     */     
/*  80 */     if (this.model != null) {
/*  81 */       this.model.bipedHead.showModel = (armorSlot == 0);
/*  82 */       this.model.bipedHeadwear.showModel = (armorSlot == 0);
/*  83 */       this.model.bipedBody.showModel = ((armorSlot == 1) || (armorSlot == 2));
/*  84 */       this.model.bipedRightArm.showModel = (armorSlot == 1);
/*  85 */       this.model.bipedLeftArm.showModel = (armorSlot == 1);
/*  86 */       this.model.bipedRightLeg.showModel = (armorSlot == 2);
/*  87 */       this.model.bipedLeftLeg.showModel = (armorSlot == 2);
/*  88 */       this.model.isSneak = entityLiving.isSneaking();
/*     */       
/*  90 */       this.model.isRiding = entityLiving.isRiding();
/*  91 */       this.model.isChild = entityLiving.isChild();
/*  92 */       this.model.aimedBow = false;
/*  93 */       this.model.heldItemRight = (entityLiving.getHeldItem() != null ? 1 : 0);
/*  94 */       if ((entityLiving instanceof EntityPlayer))
/*     */       {
/*  96 */         if (((EntityPlayer)entityLiving).getItemInUseDuration() > 0)
/*     */         {
/*  98 */           EnumAction enumaction = ((EntityPlayer)entityLiving).getItemInUse().getItemUseAction();
/*     */           
/* 100 */           if (enumaction == EnumAction.BLOCK)
/*     */           {
/* 102 */             this.model.heldItemRight = 3;
/*     */           }
/* 104 */           else if (enumaction == EnumAction.BOW)
/*     */           {
/* 106 */             this.model.aimedBow = true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 113 */     return this.model;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\armor\ItemCultistPlateArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */