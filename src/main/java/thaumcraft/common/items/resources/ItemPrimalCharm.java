/*    */ package thaumcraft.common.items.resources;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.research.ResearchHelper;
/*    */ import thaumcraft.common.lib.research.ResearchManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemPrimalCharm
/*    */   extends Item
/*    */ {
/*    */   public ItemPrimalCharm()
/*    */   {
/* 25 */     setMaxStackSize(1);
/* 26 */     setHasSubtypes(true);
/* 27 */     setMaxDamage(0);
/*    */   }
/*    */   
/*    */   public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
/*    */   {
/* 32 */     super.onUpdate(stack, world, entity, par4, par5);
/*    */     
/* 34 */     if (!entity.worldObj.isRemote) {
/* 35 */       int r = world.rand.nextInt(20000);
/* 36 */       if ((r == 42) && ((entity instanceof EntityPlayer)) && (!ResearchManager.isResearchComplete(((EntityPlayer)entity).getName(), "FOCUSPRIMAL")) && (!ResearchManager.isResearchComplete(((EntityPlayer)entity).getName(), "@FOCUSPRIMAL")))
/*    */       {
/*    */ 
/* 39 */         ((EntityPlayer)entity).addChatMessage(new ChatComponentTranslation("§5§o" + StatCollector.translateToLocal("tc.primalcharm.trigger"), new Object[0]));
/* 40 */         ResearchHelper.completeResearch((EntityPlayerMP)entity, "@FOCUSPRIMAL");
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*    */   {
/* 47 */     Random rand = new Random(player.getEntityId() + player.ticksExisted / 120);
/* 48 */     int r = rand.nextInt(100);
/* 49 */     if (r < 25) {
/* 50 */       list.add("§6" + StatCollector.translateToLocal(new StringBuilder().append("tc.primalcharm.").append(rand.nextInt(5)).toString()));
/*    */     }
/* 52 */     super.addInformation(stack, player, list, par4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\resources\ItemPrimalCharm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */