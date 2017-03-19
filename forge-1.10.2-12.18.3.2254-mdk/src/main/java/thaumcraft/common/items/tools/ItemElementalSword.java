/*     */ package thaumcraft.common.items.tools;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.Item.ToolMaterial;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.items.IRepairable;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ public class ItemElementalSword extends ItemSword implements IRepairable
/*     */ {
/*     */   public ItemElementalSword(Item.ToolMaterial enumtoolmaterial)
/*     */   {
/*  33 */     super(enumtoolmaterial);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*     */   {
/*  39 */     ItemStack w1 = new ItemStack(this);
/*  40 */     EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.ARCING, 2);
/*  41 */     par3List.add(w1);
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  47 */     return EnumRarity.RARE;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*     */   {
/*  53 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 0)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*     */   }
/*     */   
/*     */   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
/*     */   {
/*  58 */     super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
/*     */     
/*  60 */     if (entityIn.ticksExisted % 100 == 0) {
/*  61 */       List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(stack);
/*  62 */       if (!list.contains(EnumInfusionEnchantment.ARCING)) {
/*  63 */         EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.ARCING, 2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
/*     */   {
/*  71 */     super.onUsingTick(stack, player, count);
/*  72 */     int ticks = getMaxItemUseDuration(stack) - count;
/*  73 */     if (player.motionY < 0.0D) {
/*  74 */       player.motionY /= 1.2000000476837158D;
/*  75 */       player.fallDistance /= 1.2F;
/*     */     }
/*     */     
/*     */ 
/*  79 */     player.motionY += 0.07999999821186066D;
/*  80 */     if (player.motionY > 0.5D) player.motionY = 0.20000000298023224D;
/*  81 */     if ((player instanceof EntityPlayerMP)) {
/*  82 */       EntityUtils.resetFloatCounter((EntityPlayerMP)player);
/*     */     }
/*     */     
/*  85 */     List targets = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(2.5D, 2.5D, 2.5D));
/*     */     
/*  87 */     if (targets.size() > 0) {
/*  88 */       for (int var9 = 0; var9 < targets.size(); var9++)
/*     */       {
/*  90 */         Entity entity = (Entity)targets.get(var9);
/*  91 */         if ((!(entity instanceof EntityPlayer)) && ((entity instanceof EntityLivingBase)) && 
/*  92 */           (!entity.isDead) && (
/*  93 */           (player.ridingEntity == null) || (player.ridingEntity != entity)))
/*     */         {
/*  95 */           Vec3 p = new Vec3(player.posX, player.posY, player.posZ);
/*  96 */           Vec3 t = new Vec3(entity.posX, entity.posY, entity.posZ);
/*  97 */           double distance = p.distanceTo(t) + 0.1D;
/*     */           
/*  99 */           Vec3 r = new Vec3(t.xCoord - p.xCoord, t.yCoord - p.yCoord, t.zCoord - p.zCoord);
/*     */           
/* 101 */           entity.motionX += r.xCoord / 2.5D / distance;
/* 102 */           entity.motionY += r.yCoord / 2.5D / distance;
/* 103 */           entity.motionZ += r.zCoord / 2.5D / distance;
/*     */         }
/*     */       }
/*     */     }
/* 107 */     if (player.worldObj.isRemote) {
/* 108 */       int miny = (int)(player.getEntityBoundingBox().minY - 2.0D);
/* 109 */       if (player.onGround) miny = MathHelper.floor_double(player.getEntityBoundingBox().minY);
/* 110 */       for (int a = 0; a < 5; a++) {
/* 111 */         Thaumcraft.proxy.getFX().smokeSpiral(player.posX, player.getEntityBoundingBox().minY + player.height / 2.0F, player.posZ, 1.5F, player.worldObj.rand.nextInt(360), miny, 14540253);
/*     */       }
/*     */       
/*     */ 
/* 115 */       if (player.onGround) {
/* 116 */         float r1 = player.worldObj.rand.nextFloat() * 360.0F;
/* 117 */         float mx = -MathHelper.sin(r1 / 180.0F * 3.1415927F) / 5.0F;
/* 118 */         float mz = MathHelper.cos(r1 / 180.0F * 3.1415927F) / 5.0F;
/* 119 */         player.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, player.posX, player.getEntityBoundingBox().minY + 0.10000000149011612D, player.posZ, mx, 0.0D, mz, new int[0]);
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 124 */     else if ((ticks == 0) || (ticks % 20 == 0)) {
/* 125 */       player.worldObj.playSoundAtEntity(player, "thaumcraft:wind", 0.5F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F);
/*     */     }
/*     */     
/*     */ 
/* 129 */     if (ticks % 20 == 0) stack.damageItem(1, player);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemElementalSword.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */