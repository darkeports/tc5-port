/*    */ package thaumcraft.common.items.resources;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.potions.PotionFluxTaint;
/*    */ import thaumcraft.common.items.ItemGenericVariants;
/*    */ import thaumcraft.common.lib.utils.InventoryUtils;
/*    */ 
/*    */ public class ItemTainted extends ItemGenericVariants
/*    */ {
/*    */   public ItemTainted()
/*    */   {
/* 19 */     super(new String[] { "slime", "tendril" });
/*    */   }
/*    */   
/*    */   public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
/*    */   {
/* 24 */     super.onUpdate(stack, world, entity, par4, par5);
/*    */     
/* 26 */     if ((!entity.worldObj.isRemote) && ((entity instanceof EntityLivingBase)) && (!((EntityLivingBase)entity).isEntityUndead()) && (!((EntityLivingBase)entity).isPotionActive(PotionFluxTaint.instance)) && (world.rand.nextInt(4321) <= stack.stackSize))
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/* 31 */       ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(PotionFluxTaint.instance.getId(), 120, 0, false, true));
/*    */       
/*    */ 
/* 34 */       if ((entity instanceof EntityPlayer)) {
/* 35 */         String s = net.minecraft.util.StatCollector.translateToLocal("tc.taint_item_poison").replace("%s", "§5§o" + stack.getDisplayName() + "§r");
/* 36 */         ((EntityPlayer)entity).addChatMessage(new ChatComponentTranslation(s, new Object[0]));
/* 37 */         InventoryUtils.consumeInventoryItem((EntityPlayer)entity, stack.getItem(), stack.getItemDamage());
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\resources\ItemTainted.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */