/*     */ package thaumcraft.common.items.tools;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.player.ArrowLooseEvent;
/*     */ import net.minecraftforge.event.entity.player.ArrowNockEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventBus;
/*     */ import thaumcraft.api.items.IRepairable;
/*     */ 
/*     */ public class ItemBowBone extends net.minecraft.item.ItemBow implements IRepairable, thaumcraft.common.items.IVariantItems
/*     */ {
/*     */   public ItemBowBone()
/*     */   {
/*  24 */     this.maxStackSize = 1;
/*  25 */     setMaxDamage(512);
/*     */   }
/*     */   
/*     */   public String[] getVariantNames()
/*     */   {
/*  30 */     return new String[] { "base", "pulling_0", "pulling_1", "pulling_2" };
/*     */   }
/*     */   
/*     */   public int[] getVariantMeta()
/*     */   {
/*  35 */     return new int[] { 0, 1, 2, 3 };
/*     */   }
/*     */   
/*     */   public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
/*     */   {
/*  40 */     int ticks = getMaxItemUseDuration(stack) - count;
/*  41 */     if (ticks > 18) { player.stopUsingItem();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
/*     */   {
/*  50 */     int j = getMaxItemUseDuration(par1ItemStack) - par4;
/*     */     
/*  52 */     ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, j);
/*  53 */     MinecraftForge.EVENT_BUS.post(event);
/*  54 */     if (event.isCanceled())
/*     */     {
/*  56 */       return;
/*     */     }
/*  58 */     j = event.charge;
/*     */     
/*  60 */     boolean flag = (par3EntityPlayer.capabilities.isCreativeMode) || (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0);
/*     */     
/*  62 */     if ((flag) || (par3EntityPlayer.inventory.hasItem(Items.arrow)))
/*     */     {
/*  64 */       float f = j / 10.0F;
/*  65 */       f = (f * f + f * 2.0F) / 3.0F;
/*     */       
/*  67 */       if (f < 0.1D)
/*     */       {
/*  69 */         return;
/*     */       }
/*     */       
/*  72 */       if (f > 1.0F)
/*     */       {
/*  74 */         f = 1.0F;
/*     */       }
/*     */       
/*  77 */       EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, f * 2.5F);
/*  78 */       entityarrow.setDamage(entityarrow.getDamage() + 0.5D);
/*     */       
/*  80 */       int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
/*     */       
/*  82 */       if (k > 0)
/*     */       {
/*  84 */         entityarrow.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);
/*     */       }
/*     */       
/*  87 */       int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);
/*     */       
/*  89 */       if (l > 0)
/*     */       {
/*  91 */         entityarrow.setKnockbackStrength(l);
/*     */       }
/*     */       
/*  94 */       if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
/*     */       {
/*  96 */         entityarrow.setFire(100);
/*     */       }
/*     */       
/*  99 */       par1ItemStack.damageItem(1, par3EntityPlayer);
/* 100 */       par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
/*     */       
/* 102 */       if (flag)
/*     */       {
/* 104 */         entityarrow.canBePickedUp = 2;
/*     */       }
/*     */       else
/*     */       {
/* 108 */         par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);
/*     */       }
/*     */       
/* 111 */       if (!par2World.isRemote)
/*     */       {
/* 113 */         par2World.spawnEntityInWorld(entityarrow);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*     */   {
/* 125 */     ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer, par1ItemStack);
/* 126 */     MinecraftForge.EVENT_BUS.post(event);
/* 127 */     if (event.isCanceled())
/*     */     {
/* 129 */       return event.result;
/*     */     }
/*     */     
/* 132 */     if ((par3EntityPlayer.capabilities.isCreativeMode) || (par3EntityPlayer.inventory.hasItem(Items.arrow)) || (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0))
/*     */     {
/*     */ 
/* 135 */       par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
/*     */     }
/*     */     
/* 138 */     return par1ItemStack;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getItemEnchantability()
/*     */   {
/* 147 */     return 3;
/*     */   }
/*     */   
/*     */   public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
/*     */   {
/* 152 */     ModelResourceLocation modelresourcelocation = new ModelResourceLocation("Thaumcraft:bone_bow_base", "inventory");
/*     */     
/* 154 */     if ((stack.getItem() == this) && (player.getItemInUse() != null))
/*     */     {
/* 156 */       if (getMaxItemUseDuration(stack) - useRemaining >= 13)
/*     */       {
/* 158 */         modelresourcelocation = new ModelResourceLocation("Thaumcraft:bone_bow_pulling_2", "inventory");
/*     */ 
/*     */       }
/* 161 */       else if (getMaxItemUseDuration(stack) - useRemaining > 7)
/*     */       {
/* 163 */         modelresourcelocation = new ModelResourceLocation("Thaumcraft:bone_bow_pulling_1", "inventory");
/*     */ 
/*     */       }
/* 166 */       else if (getMaxItemUseDuration(stack) - useRemaining > 0)
/*     */       {
/* 168 */         modelresourcelocation = new ModelResourceLocation("Thaumcraft:bone_bow_pulling_0", "inventory");
/*     */       }
/*     */     }
/*     */     
/* 172 */     return modelresourcelocation;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemBowBone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */