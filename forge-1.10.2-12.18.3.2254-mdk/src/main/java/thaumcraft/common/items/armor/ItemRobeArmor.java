/*     */ package thaumcraft.common.items.armor;
/*     */ 
/*     */ import net.minecraft.block.BlockCauldron;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ 
/*     */ public class ItemRobeArmor extends ItemArmor implements thaumcraft.api.items.IRepairable, thaumcraft.api.items.IVisDiscountGear, thaumcraft.api.items.IRunicArmor
/*     */ {
/*     */   public ItemRobeArmor(ItemArmor.ArmorMaterial enumarmormaterial, int j, int k)
/*     */   {
/*  22 */     super(enumarmormaterial, j, k);
/*     */   }
/*     */   
/*     */   public int getRunicCharge(ItemStack itemstack)
/*     */   {
/*  27 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasColor(ItemStack par1ItemStack)
/*     */   {
/*  33 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getColor(ItemStack par1ItemStack)
/*     */   {
/*  39 */     NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
/*     */     
/*  41 */     if (nbttagcompound == null)
/*     */     {
/*  43 */       return 6961280;
/*     */     }
/*     */     
/*     */ 
/*  47 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*  48 */     return nbttagcompound1.hasKey("color") ? nbttagcompound1.getInteger("color") : nbttagcompound1 == null ? 6961280 : 6961280;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeColor(ItemStack par1ItemStack)
/*     */   {
/*  55 */     NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
/*     */     
/*  57 */     if (nbttagcompound != null)
/*     */     {
/*  59 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */       
/*  61 */       if (nbttagcompound1.hasKey("color"))
/*     */       {
/*  63 */         nbttagcompound1.removeTag("color");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setColor(ItemStack par1ItemStack, int par2)
/*     */   {
/*  72 */     NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
/*     */     
/*  74 */     if (nbttagcompound == null)
/*     */     {
/*  76 */       nbttagcompound = new NBTTagCompound();
/*  77 */       par1ItemStack.setTagCompound(nbttagcompound);
/*     */     }
/*     */     
/*  80 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */     
/*  82 */     if (!nbttagcompound.hasKey("display"))
/*     */     {
/*  84 */       nbttagcompound.setTag("display", nbttagcompound1);
/*     */     }
/*     */     
/*  87 */     nbttagcompound1.setInteger("color", par2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
/*     */   {
/*  94 */     if ((stack.getItem() == ItemsTC.clothChest) || (stack.getItem() == ItemsTC.clothBoots))
/*     */     {
/*  96 */       return type == null ? "thaumcraft:textures/models/armor/robes_1.png" : "thaumcraft:textures/models/armor/robes_1_overlay.png";
/*     */     }
/*  98 */     if (stack.getItem() == ItemsTC.clothLegs) {
/*  99 */       return type == null ? "thaumcraft:textures/models/armor/robes_2.png" : "thaumcraft:textures/models/armor/robes_2_overlay.png";
/*     */     }
/* 101 */     return type == null ? "thaumcraft:textures/models/armor/robes_1.png" : "thaumcraft:textures/models/armor/robes_1_overlay.png";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/* 108 */     return EnumRarity.UNCOMMON;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*     */   {
/* 114 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.fabric)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect)
/*     */   {
/* 120 */     return this.armorType == 3 ? 1 : 2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/* 129 */     IBlockState bs = world.getBlockState(pos);
/* 130 */     if (bs.getBlock() == net.minecraft.init.Blocks.cauldron) {
/* 131 */       int i = ((Integer)bs.getValue(BlockCauldron.LEVEL)).intValue();
/* 132 */       if ((!world.isRemote) && (i > 0)) {
/* 133 */         removeColor(stack);
/* 134 */         net.minecraft.init.Blocks.cauldron.setWaterLevel(world, pos, bs, i - 1);
/* 135 */         return true;
/*     */       }
/*     */     }
/* 138 */     return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\armor\ItemRobeArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */