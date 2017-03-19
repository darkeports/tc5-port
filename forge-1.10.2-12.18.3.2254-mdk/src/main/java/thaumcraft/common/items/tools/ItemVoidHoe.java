/*    */ package thaumcraft.common.items.tools;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item.ToolMaterial;
/*    */ import net.minecraft.item.ItemHoe;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ItemVoidHoe extends ItemHoe implements thaumcraft.api.items.IRepairable, thaumcraft.api.items.IWarpingGear
/*    */ {
/*    */   public ItemVoidHoe(Item.ToolMaterial enumtoolmaterial)
/*    */   {
/* 20 */     super(enumtoolmaterial);
/* 21 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*    */   {
/* 27 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 1)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
/*    */   {
/* 34 */     super.onUpdate(stack, world, entity, p_77663_4_, p_77663_5_);
/*    */     
/* 36 */     if ((stack.isItemDamaged()) && (entity != null) && (entity.ticksExisted % 20 == 0) && ((entity instanceof EntityLivingBase))) {
/* 37 */       stack.damageItem(-1, (EntityLivingBase)entity);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
/*    */   {
/* 43 */     if ((!player.worldObj.isRemote) && ((entity instanceof EntityLivingBase)) && (
/* 44 */       (!(entity instanceof EntityPlayer)) || (MinecraftServer.getServer().isPVPEnabled())))
/*    */     {
/*    */ 
/* 47 */       ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.weakness.getId(), 80));
/*    */     }
/*    */     
/* 50 */     return super.onLeftClickEntity(stack, player, entity);
/*    */   }
/*    */   
/*    */   public int getWarp(ItemStack itemstack, EntityPlayer player)
/*    */   {
/* 55 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemVoidHoe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */