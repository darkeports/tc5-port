/*    */ package thaumcraft.common.items.tools;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item.ToolMaterial;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.ItemSword;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.items.IRepairable;
/*    */ import thaumcraft.api.items.IWarpingGear;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ItemVoidSword extends ItemSword implements IRepairable, IWarpingGear
/*    */ {
/*    */   public ItemVoidSword(Item.ToolMaterial enumtoolmaterial)
/*    */   {
/* 24 */     super(enumtoolmaterial);
/* 25 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*    */   {
/* 32 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 1)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
/*    */   {
/* 39 */     super.onUpdate(stack, world, entity, p_77663_4_, p_77663_5_);
/*    */     
/* 41 */     if ((stack.isItemDamaged()) && (entity != null) && (entity.ticksExisted % 20 == 0) && ((entity instanceof EntityLivingBase))) {
/* 42 */       stack.damageItem(-1, (EntityLivingBase)entity);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean hitEntity(ItemStack is, EntityLivingBase target, EntityLivingBase hitter)
/*    */   {
/* 48 */     if ((!target.worldObj.isRemote) && (
/* 49 */       (!(target instanceof EntityPlayer)) || (!(hitter instanceof EntityPlayer)) || (MinecraftServer.getServer().isPVPEnabled())))
/*    */     {
/*    */       try
/*    */       {
/* 53 */         target.addPotionEffect(new PotionEffect(Potion.weakness.getId(), 60));
/*    */       }
/*    */       catch (Exception e) {}
/*    */     }
/* 57 */     return super.hitEntity(is, target, hitter);
/*    */   }
/*    */   
/*    */   public int getWarp(ItemStack itemstack, EntityPlayer player)
/*    */   {
/* 62 */     return 1;
/*    */   }
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*    */   {
/* 67 */     list.add(EnumChatFormatting.GOLD + net.minecraft.util.StatCollector.translateToLocal("enchantment.special.sapless"));
/* 68 */     super.addInformation(stack, player, list, par4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemVoidSword.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */