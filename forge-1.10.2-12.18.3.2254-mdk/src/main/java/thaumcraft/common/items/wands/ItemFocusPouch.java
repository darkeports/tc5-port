/*     */ package thaumcraft.common.items.wands;
/*     */ 
/*     */ import baubles.api.BaubleType;
/*     */ import baubles.api.IBauble;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class ItemFocusPouch
/*     */   extends Item
/*     */   implements IBauble
/*     */ {
/*     */   public ItemFocusPouch()
/*     */   {
/*  22 */     setMaxStackSize(1);
/*  23 */     setHasSubtypes(false);
/*  24 */     setMaxDamage(0);
/*  25 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */   public boolean getShareTag()
/*     */   {
/*  30 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  36 */     return EnumRarity.UNCOMMON;
/*     */   }
/*     */   
/*     */   public boolean hasEffect(ItemStack par1ItemStack)
/*     */   {
/*  41 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*     */   {
/*  48 */     if (!par2World.isRemote) {
/*  49 */       par3EntityPlayer.openGui(Thaumcraft.instance, 5, par2World, MathHelper.floor_double(par3EntityPlayer.posX), MathHelper.floor_double(par3EntityPlayer.posY), MathHelper.floor_double(par3EntityPlayer.posZ));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  55 */     return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
/*     */   }
/*     */   
/*     */   public ItemStack[] getInventory(ItemStack item) {
/*  59 */     ItemStack[] stackList = new ItemStack[18];
/*     */     
/*  61 */     if (item.hasTagCompound()) {
/*  62 */       NBTTagList var2 = item.getTagCompound().getTagList("Inventory", 10);
/*  63 */       for (int var3 = 0; var3 < var2.tagCount(); var3++)
/*     */       {
/*  65 */         NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/*  66 */         int var5 = var4.getByte("Slot") & 0xFF;
/*     */         
/*  68 */         if ((var5 >= 0) && (var5 < stackList.length))
/*     */         {
/*  70 */           stackList[var5] = ItemStack.loadItemStackFromNBT(var4);
/*     */         }
/*     */       }
/*     */     }
/*  74 */     return stackList;
/*     */   }
/*     */   
/*     */   public void setInventory(ItemStack item, ItemStack[] stackList) {
/*  78 */     NBTTagList var2 = new NBTTagList();
/*     */     
/*  80 */     for (int var3 = 0; var3 < stackList.length; var3++)
/*     */     {
/*  82 */       if (stackList[var3] != null)
/*     */       {
/*  84 */         NBTTagCompound var4 = new NBTTagCompound();
/*  85 */         var4.setByte("Slot", (byte)var3);
/*  86 */         stackList[var3].writeToNBT(var4);
/*  87 */         var2.appendTag(var4);
/*     */       }
/*     */     }
/*     */     
/*  91 */     item.setTagInfo("Inventory", var2);
/*     */   }
/*     */   
/*     */   public BaubleType getBaubleType(ItemStack itemstack)
/*     */   {
/*  96 */     return BaubleType.BELT;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}
/*     */   
/*     */ 
/*     */   public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}
/*     */   
/*     */ 
/*     */   public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}
/*     */   
/*     */   public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 110 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
/*     */   {
/* 115 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\wands\ItemFocusPouch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */