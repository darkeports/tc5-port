/*     */ package thaumcraft.common.items.tools;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.Item.ToolMaterial;
/*     */ import net.minecraft.item.ItemAxe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.items.IRepairable;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.EntityFollowingItem;
/*     */ import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ public class ItemElementalAxe
/*     */   extends ItemAxe implements IRepairable
/*     */ {
/*     */   public ItemElementalAxe(Item.ToolMaterial enumtoolmaterial)
/*     */   {
/*  36 */     super(enumtoolmaterial);
/*     */   }
/*     */   
/*     */   public Set<String> getToolClasses(ItemStack stack)
/*     */   {
/*  41 */     return ImmutableSet.of("axe");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  48 */     return EnumRarity.RARE;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*     */   {
/*  54 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EnumAction getItemUseAction(ItemStack itemstack)
/*     */   {
/*  61 */     return EnumAction.BOW;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMaxItemUseDuration(ItemStack p_77626_1_)
/*     */   {
/*  67 */     return 72000;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
/*     */   {
/*  73 */     p_77659_3_.setItemInUse(p_77659_1_, getMaxItemUseDuration(p_77659_1_));
/*  74 */     return p_77659_1_;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
/*     */   {
/*  80 */     ArrayList<Entity> stuff = EntityUtils.getEntitiesInRange(player.worldObj, player.posX, player.posY, player.posZ, player, EntityItem.class, 10.0D);
/*     */     
/*     */ 
/*  83 */     if ((stuff != null) && (stuff.size() > 0))
/*     */     {
/*  85 */       for (Entity e : stuff) {
/*  86 */         if ((!(e instanceof EntityFollowingItem)) || (((EntityFollowingItem)e).target == null)) {
/*  87 */           if ((!e.isDead) && ((e instanceof EntityItem))) {
/*  88 */             double d6 = e.posX - player.posX;
/*  89 */             double d8 = e.posY - player.posY + player.height / 2.0F;
/*  90 */             double d10 = e.posZ - player.posZ;
/*  91 */             double d11 = MathHelper.sqrt_double(d6 * d6 + d8 * d8 + d10 * d10);
/*  92 */             d6 /= d11;
/*  93 */             d8 /= d11;
/*  94 */             d10 /= d11;
/*  95 */             double d13 = 0.3D;
/*  96 */             e.motionX -= d6 * d13;
/*  97 */             e.motionY -= d8 * d13 - 0.1D;
/*  98 */             e.motionZ -= d10 * d13;
/*  99 */             if (e.motionX > 0.25D) e.motionX = 0.25D;
/* 100 */             if (e.motionX < -0.25D) e.motionX = -0.25D;
/* 101 */             if (e.motionY > 0.25D) e.motionY = 0.25D;
/* 102 */             if (e.motionY < -0.25D) e.motionY = -0.25D;
/* 103 */             if (e.motionZ > 0.25D) e.motionZ = 0.25D;
/* 104 */             if (e.motionZ < -0.25D) e.motionZ = -0.25D;
/* 105 */             if (player.worldObj.isRemote) {
/* 106 */               Thaumcraft.proxy.getFX().crucibleBubble((float)e.posX + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.125F, (float)e.posY + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.125F, (float)e.posZ + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.125F, 0.33F, 0.33F, 1.0F);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*     */   {
/* 119 */     ItemStack w1 = new ItemStack(this);
/* 120 */     EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.BURROWING, 1);
/* 121 */     EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.COLLECTOR, 1);
/* 122 */     par3List.add(w1);
/*     */   }
/*     */   
/*     */   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
/*     */   {
/* 127 */     super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
/*     */     
/* 129 */     if (entityIn.ticksExisted % 100 == 0) {
/* 130 */       List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(stack);
/* 131 */       if (!list.contains(EnumInfusionEnchantment.BURROWING)) {
/* 132 */         EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.BURROWING, 1);
/*     */       }
/* 134 */       if (!list.contains(EnumInfusionEnchantment.COLLECTOR)) {
/* 135 */         EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.COLLECTOR, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemElementalAxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */