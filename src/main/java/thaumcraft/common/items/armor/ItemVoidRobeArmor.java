/*     */ package thaumcraft.common.items.armor;
/*     */ 
/*     */ import net.minecraft.block.BlockCauldron;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
/*     */ import net.minecraftforge.common.util.EnumHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.items.IGoggles;
/*     */ import thaumcraft.api.items.IRepairable;
/*     */ import thaumcraft.api.items.IRevealer;
/*     */ import thaumcraft.api.items.IRunicArmor;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.renderers.models.gear.ModelRobe;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class ItemVoidRobeArmor extends ItemArmor implements IRepairable, IRunicArmor, thaumcraft.api.items.IVisDiscountGear, IGoggles, IRevealer, ISpecialArmor, thaumcraft.api.items.IWarpingGear
/*     */ {
/*  36 */   public static ItemArmor.ArmorMaterial ARMORMAT_VOIDROBE = EnumHelper.addArmorMaterial("VOIDROBE", "VOIDROBE", 18, new int[] { 4, 8, 7, 4 }, 10);
/*     */   
/*     */   public ItemVoidRobeArmor(ItemArmor.ArmorMaterial enumarmormaterial, int j, int k)
/*     */   {
/*  40 */     super(enumarmormaterial, j, k);
/*  41 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
/*     */   {
/*  48 */     return type == null ? "thaumcraft:textures/models/armor/void_robe_armor_overlay.png" : "thaumcraft:textures/models/armor/void_robe_armor.png";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  55 */     return EnumRarity.EPIC;
/*     */   }
/*     */   
/*     */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*     */   {
/*  60 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 1)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
/*     */   {
/*  67 */     super.onUpdate(stack, world, entity, p_77663_4_, p_77663_5_);
/*     */     
/*  69 */     if ((!world.isRemote) && (stack.isItemDamaged()) && (entity.ticksExisted % 20 == 0) && ((entity instanceof EntityLivingBase))) {
/*  70 */       stack.damageItem(-1, (EntityLivingBase)entity);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onArmorTick(World world, EntityPlayer player, ItemStack armor)
/*     */   {
/*  76 */     super.onArmorTick(world, player, armor);
/*  77 */     if ((!world.isRemote) && (armor.getItemDamage() > 0) && (player.ticksExisted % 20 == 0)) {
/*  78 */       armor.damageItem(-1, player);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getRunicCharge(ItemStack itemstack)
/*     */   {
/*  86 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean showNodes(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/*  91 */     int type = ((ItemArmor)itemstack.getItem()).armorType;
/*  92 */     return type == 0;
/*     */   }
/*     */   
/*     */   public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/*  97 */     int type = ((ItemArmor)itemstack.getItem()).armorType;
/*  98 */     return type == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect)
/*     */   {
/* 105 */     return 5;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 110 */   ModelBiped model1 = null;
/* 111 */   ModelBiped model2 = null;
/* 112 */   ModelBiped model = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
/*     */   {
/* 120 */     int type = ((ItemArmor)itemStack.getItem()).armorType;
/*     */     
/* 122 */     if (this.model1 == null) {
/* 123 */       this.model1 = new ModelRobe(1.0F);
/*     */     }
/* 125 */     if (this.model2 == null) {
/* 126 */       this.model2 = new ModelRobe(0.5F);
/*     */     }
/* 128 */     if ((type == 1) || (type == 3)) {
/* 129 */       this.model = this.model1;
/*     */     } else {
/* 131 */       this.model = this.model2;
/*     */     }
/*     */     
/* 134 */     if (this.model != null) {
/* 135 */       this.model.bipedHead.showModel = (armorSlot == 0);
/* 136 */       this.model.bipedHeadwear.showModel = (armorSlot == 0);
/* 137 */       this.model.bipedBody.showModel = ((armorSlot == 1) || (armorSlot == 2));
/* 138 */       this.model.bipedRightArm.showModel = (armorSlot == 1);
/* 139 */       this.model.bipedLeftArm.showModel = (armorSlot == 1);
/* 140 */       this.model.bipedRightLeg.showModel = (armorSlot == 2);
/* 141 */       this.model.bipedLeftLeg.showModel = (armorSlot == 2);
/* 142 */       this.model.isSneak = entityLiving.isSneaking();
/*     */       
/* 144 */       this.model.isRiding = entityLiving.isRiding();
/* 145 */       this.model.isChild = entityLiving.isChild();
/* 146 */       this.model.aimedBow = false;
/* 147 */       this.model.heldItemRight = (entityLiving.getHeldItem() != null ? 1 : 0);
/* 148 */       if ((entityLiving instanceof EntityPlayer))
/*     */       {
/* 150 */         if (((EntityPlayer)entityLiving).getItemInUseDuration() > 0)
/*     */         {
/* 152 */           EnumAction enumaction = ((EntityPlayer)entityLiving).getItemInUse().getItemUseAction();
/*     */           
/* 154 */           if (enumaction == EnumAction.BLOCK)
/*     */           {
/* 156 */             this.model.heldItemRight = 3;
/*     */           }
/* 158 */           else if (enumaction == EnumAction.BOW)
/*     */           {
/* 160 */             this.model.aimedBow = true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 167 */     return this.model;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasColor(ItemStack par1ItemStack)
/*     */   {
/* 176 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getColor(ItemStack par1ItemStack)
/*     */   {
/* 183 */     NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
/*     */     
/* 185 */     if (nbttagcompound == null)
/*     */     {
/* 187 */       return 6961280;
/*     */     }
/*     */     
/*     */ 
/* 191 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/* 192 */     return nbttagcompound1.hasKey("color") ? nbttagcompound1.getInteger("color") : nbttagcompound1 == null ? 6961280 : 6961280;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeColor(ItemStack par1ItemStack)
/*     */   {
/* 199 */     NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
/*     */     
/* 201 */     if (nbttagcompound != null)
/*     */     {
/* 203 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */       
/* 205 */       if (nbttagcompound1.hasKey("color"))
/*     */       {
/* 207 */         nbttagcompound1.removeTag("color");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setColor(ItemStack par1ItemStack, int par2)
/*     */   {
/* 216 */     NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
/*     */     
/* 218 */     if (nbttagcompound == null)
/*     */     {
/* 220 */       nbttagcompound = new NBTTagCompound();
/* 221 */       par1ItemStack.setTagCompound(nbttagcompound);
/*     */     }
/*     */     
/* 224 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */     
/* 226 */     if (!nbttagcompound.hasKey("display"))
/*     */     {
/* 228 */       nbttagcompound.setTag("display", nbttagcompound1);
/*     */     }
/*     */     
/* 231 */     nbttagcompound1.setInteger("color", par2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
/*     */   {
/* 240 */     int priority = 0;
/* 241 */     double ratio = this.damageReduceAmount / 25.0D;
/*     */     
/* 243 */     if (source.isMagicDamage()) {
/* 244 */       priority = 1;
/* 245 */       ratio = this.damageReduceAmount / 35.0D;
/* 246 */     } else if (source.isUnblockable()) {
/* 247 */       priority = 0;
/* 248 */       ratio = 0.0D;
/*     */     }
/* 250 */     return new ISpecialArmor.ArmorProperties(priority, ratio, armor.getMaxDamage() + 1 - armor.getItemDamage());
/*     */   }
/*     */   
/*     */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
/*     */   {
/* 255 */     return this.damageReduceAmount;
/*     */   }
/*     */   
/*     */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
/*     */   {
/* 260 */     if (source != DamageSource.fall) { stack.damageItem(damage, entity);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/* 267 */     IBlockState bs = world.getBlockState(pos);
/* 268 */     if (bs.getBlock() == Blocks.cauldron) {
/* 269 */       int i = ((Integer)bs.getValue(BlockCauldron.LEVEL)).intValue();
/* 270 */       if ((!world.isRemote) && (i > 0)) {
/* 271 */         removeColor(stack);
/* 272 */         Blocks.cauldron.setWaterLevel(world, pos, bs, i - 1);
/* 273 */         return true;
/*     */       }
/*     */     }
/* 276 */     return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getWarp(ItemStack itemstack, EntityPlayer player)
/*     */   {
/* 282 */     return 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\armor\ItemVoidRobeArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */