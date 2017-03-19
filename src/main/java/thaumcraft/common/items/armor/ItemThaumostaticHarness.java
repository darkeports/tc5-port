/*     */ package thaumcraft.common.items.armor;
/*     */ 
/*     */ import baubles.api.BaublesApi;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagShort;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.items.IRechargable;
/*     */ import thaumcraft.api.items.IRepairable;
/*     */ import thaumcraft.api.items.IRunicArmor;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ 
/*     */ public class ItemThaumostaticHarness extends ItemArmor implements IRepairable, thaumcraft.api.items.IVisDiscountGear, IRunicArmor, IRechargable
/*     */ {
/*     */   public ItemThaumostaticHarness(ItemArmor.ArmorMaterial enumarmormaterial, int j, int k)
/*     */   {
/*  35 */     super(enumarmormaterial, j, k);
/*  36 */     setMaxDamage(400);
/*  37 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*  40 */   ModelBiped model = null;
/*     */   
/*     */ 
/*     */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
/*     */   {
/*  46 */     if (this.model == null) this.model = new thaumcraft.client.renderers.models.gear.ModelHoverHarness();
/*  47 */     return this.model;
/*     */   }
/*     */   
/*     */   public int getRunicCharge(ItemStack itemstack)
/*     */   {
/*  52 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
/*     */   {
/*  58 */     return "thaumcraft:textures/models/armor/hoverharness.png";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  65 */     return EnumRarity.RARE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*     */   {
/*  72 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 2)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect)
/*     */   {
/*  78 */     return aspect == Aspect.AIR ? 3 : 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
/*     */   {
/*  85 */     if (!player.capabilities.isCreativeMode) {
/*  86 */       Hover.handleHoverArmor(player, player.inventory.armorItemInSlot(2));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Aspect handleRecharge(World world, ItemStack is, BlockPos pos, EntityPlayer player, int amount)
/*     */   {
/*  95 */     if ((player == null) || ((!AuraHandler.shouldPreserveAura(world, player, player.getPosition(), Aspect.AIR)) && (!AuraHandler.shouldPreserveAura(world, player, player.getPosition(), Aspect.FIRE))))
/*     */     {
/*  97 */       short fuel = 0;
/*  98 */       if (is.hasTagCompound()) {
/*  99 */         fuel = is.getTagCompound().getShort("fuel");
/*     */       }
/*     */       
/* 102 */       int te = 20;
/* 103 */       if ((player != null) && (BaublesApi.getBaubles(player).getStackInSlot(3) != null) && ((BaublesApi.getBaubles(player).getStackInSlot(3).getItem() instanceof thaumcraft.common.items.baubles.ItemGirdleHover)))
/*     */       {
/* 105 */         te = 16;
/*     */       }
/* 107 */       boolean b = false;
/* 108 */       for (int a = 0; a < amount; a++) {
/* 109 */         if ((fuel < 6000) && (AuraHandler.drainAura(world, pos, Aspect.AIR, 1, false)) && (AuraHandler.drainAura(world, pos, Aspect.FIRE, 1, false)))
/*     */         {
/*     */ 
/* 112 */           AuraHandler.drainAura(world, pos, Aspect.AIR, 1);
/* 113 */           AuraHandler.drainAura(world, pos, Aspect.FIRE, 1);
/* 114 */           fuel = (short)(fuel + te);
/* 115 */           b = true;
/*     */         }
/*     */       }
/* 118 */       if (b) {
/* 119 */         fuel = (short)MathHelper.clamp_int(fuel, 0, 6000);
/* 120 */         is.setTagInfo("fuel", new NBTTagShort(fuel));
/* 121 */         return world.rand.nextBoolean() ? Aspect.AIR : Aspect.FIRE;
/*     */       }
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   public AspectList getAspectsInChargable(ItemStack is)
/*     */   {
/* 129 */     short fuel = 0;
/* 130 */     if (is.hasTagCompound()) {
/* 131 */       fuel = (short)(is.getTagCompound().getShort("fuel") / 20);
/*     */     }
/* 133 */     return new AspectList().add(Aspect.FIRE, fuel).add(Aspect.AIR, fuel);
/*     */   }
/*     */   
/*     */   public float getChargeLevel(ItemStack is)
/*     */   {
/* 138 */     short fuel = 0;
/* 139 */     if (is.hasTagCompound()) {
/* 140 */       fuel = is.getTagCompound().getShort("fuel");
/*     */     }
/* 142 */     return fuel / 6000.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\armor\ItemThaumostaticHarness.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */