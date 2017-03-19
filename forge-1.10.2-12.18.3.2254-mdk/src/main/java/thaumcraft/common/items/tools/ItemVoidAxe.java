/*    */ package thaumcraft.common.items.tools;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item.ToolMaterial;
/*    */ import net.minecraft.item.ItemAxe;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.items.IRepairable;
/*    */ import thaumcraft.api.items.IWarpingGear;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ItemVoidAxe extends ItemAxe implements IRepairable, IWarpingGear
/*    */ {
/*    */   public ItemVoidAxe(Item.ToolMaterial enumtoolmaterial)
/*    */   {
/* 24 */     super(enumtoolmaterial);
/* 25 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */   public Set<String> getToolClasses(ItemStack stack)
/*    */   {
/* 30 */     return ImmutableSet.of("axe");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*    */   {
/* 37 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 1)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
/*    */   {
/* 44 */     super.onUpdate(stack, world, entity, p_77663_4_, p_77663_5_);
/*    */     
/* 46 */     if ((stack.isItemDamaged()) && (entity != null) && (entity.ticksExisted % 20 == 0) && ((entity instanceof EntityLivingBase))) {
/* 47 */       stack.damageItem(-1, (EntityLivingBase)entity);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
/*    */   {
/* 54 */     if ((!player.worldObj.isRemote) && ((entity instanceof EntityLivingBase)) && (
/* 55 */       (!(entity instanceof EntityPlayer)) || (MinecraftServer.getServer().isPVPEnabled())))
/*    */     {
/*    */ 
/* 58 */       ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.weakness.getId(), 80));
/*    */     }
/*    */     
/* 61 */     return super.onLeftClickEntity(stack, player, entity);
/*    */   }
/*    */   
/*    */   public int getWarp(ItemStack itemstack, EntityPlayer player)
/*    */   {
/* 66 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemVoidAxe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */