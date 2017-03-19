/*    */ package thaumcraft.common.items.armor;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.lib.events.PlayerEvents;
/*    */ 
/*    */ public class ItemBootsTraveller extends ItemArmor implements thaumcraft.api.items.IRepairable, thaumcraft.api.items.IRunicArmor
/*    */ {
/*    */   public ItemBootsTraveller(ItemArmor.ArmorMaterial enumarmormaterial, int j, int k)
/*    */   {
/* 17 */     super(enumarmormaterial, j, k);
/* 18 */     setMaxDamage(350);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
/*    */   {
/* 24 */     return "thaumcraft:textures/models/armor/bootstraveler.png";
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*    */   {
/* 30 */     return par2ItemStack.isItemEqual(new ItemStack(net.minecraft.init.Items.leather)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public net.minecraft.item.EnumRarity getRarity(ItemStack itemstack)
/*    */   {
/* 37 */     return net.minecraft.item.EnumRarity.RARE;
/*    */   }
/*    */   
/*    */   public int getRunicCharge(ItemStack itemstack)
/*    */   {
/* 42 */     return 0;
/*    */   }
/*    */   
/*    */   public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
/*    */   {
/* 47 */     if ((!player.capabilities.isFlying) && (player.moveForward > 0.0F))
/*    */     {
/* 49 */       if ((player.worldObj.isRemote) && (!player.isSneaking())) {
/* 50 */         if (!Thaumcraft.instance.playerEvents.prevStep.containsKey(Integer.valueOf(player.getEntityId()))) {
/* 51 */           Thaumcraft.instance.playerEvents.prevStep.put(Integer.valueOf(player.getEntityId()), Float.valueOf(player.stepHeight));
/*    */         }
/* 53 */         player.stepHeight = 1.0F;
/*    */       }
/*    */       
/* 56 */       if (player.onGround) {
/* 57 */         float bonus = 0.055F;
/* 58 */         if (player.isInWater()) bonus /= 4.0F;
/* 59 */         player.moveFlying(0.0F, 1.0F, bonus);
/*    */       }
/* 61 */       else if (Hover.getHover(player.getEntityId())) {
/* 62 */         player.jumpMovementFactor = 0.03F;
/*    */       } else {
/* 64 */         player.jumpMovementFactor = 0.05F;
/*    */       }
/*    */     }
/*    */     
/* 68 */     if (player.fallDistance > 0.0F) {
/* 69 */       player.fallDistance -= 0.25F;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\armor\ItemBootsTraveller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */