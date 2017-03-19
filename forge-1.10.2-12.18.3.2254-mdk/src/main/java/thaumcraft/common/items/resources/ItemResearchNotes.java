/*     */ package thaumcraft.common.items.resources;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.ThaumcraftApi;
/*     */ import thaumcraft.api.research.ResearchCategories;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.api.research.ResearchItem;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.items.IVariantItems;
/*     */ import thaumcraft.common.lib.research.ResearchManager;
/*     */ import thaumcraft.common.lib.research.ResearchNoteData;
/*     */ 
/*     */ public class ItemResearchNotes
/*     */   extends Item
/*     */   implements IVariantItems
/*     */ {
/*     */   public ItemResearchNotes()
/*     */   {
/*  30 */     setHasSubtypes(true);
/*  31 */     setMaxDamage(0);
/*  32 */     setMaxStackSize(64);
/*     */   }
/*     */   
/*     */   public String[] getVariantNames()
/*     */   {
/*  37 */     return new String[] { "*", "discovery" };
/*     */   }
/*     */   
/*     */   public int[] getVariantMeta()
/*     */   {
/*  42 */     return new int[] { 0, 1 };
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {}
/*     */   
/*     */ 
/*     */   public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
/*     */   {
/*  52 */     if ((!world.isRemote) && 
/*  53 */       (ResearchManager.getData(stack) != null) && (ResearchManager.getData(stack).isComplete())) {
/*  54 */       if (ResearchHelper.completeResearch(player, ResearchManager.getData(stack).key)) {
/*  55 */         if (Config.researchAmount > 0) stack.stackSize -= 1;
/*  56 */         world.playSoundAtEntity(player, "thaumcraft:learn", 0.75F, 1.0F);
/*     */       }
/*  58 */       else if (ResearchHelper.isResearchComplete(player.getName(), ResearchManager.getData(stack).key)) {
/*  59 */         player.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("tc.researchexists"), new Object[0]));
/*     */       } else {
/*  61 */         player.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("tc.researcherror"), new Object[0]));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  66 */     return stack;
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getColorFromItemStack(ItemStack stack, int par2)
/*     */   {
/*  73 */     if (par2 == 1) {
/*  74 */       int c = 10066329;
/*  75 */       ResearchNoteData rd = ResearchManager.getData(stack);
/*  76 */       if (rd != null) c = rd.color;
/*  77 */       return c;
/*     */     }
/*  79 */     return super.getColorFromItemStack(stack, par2);
/*     */   }
/*     */   
/*     */   public boolean getShareTag()
/*     */   {
/*  84 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getUnlocalizedName(ItemStack par1ItemStack)
/*     */   {
/*  90 */     if (getVariantNames()[par1ItemStack.getItemDamage()].equals("*")) {
/*  91 */       return super.getUnlocalizedName();
/*     */     }
/*  93 */     return super.getUnlocalizedName() + "." + getVariantNames()[par1ItemStack.getItemDamage()];
/*     */   }
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4)
/*     */   {
/*  98 */     if (stack.getItemDamage() == 1) {
/*  99 */       list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.researchnotes.learn"));
/*     */     }
/* 101 */     ResearchNoteData rd = ResearchManager.getData(stack);
/* 102 */     if ((rd != null) && (rd.key != null) && (ResearchCategories.getResearch(rd.key) != null)) {
/* 103 */       list.add("§6" + ResearchCategories.getResearch(rd.key).getName());
/* 104 */       list.add("§o" + ResearchCategories.getResearch(rd.key).getText());
/* 105 */       int warp = ThaumcraftApi.getWarp(rd.key);
/* 106 */       if (warp > 0) {
/* 107 */         if (warp > 5) warp = 5;
/* 108 */         String ws = StatCollector.translateToLocal("tc.forbidden");
/* 109 */         String wr = StatCollector.translateToLocal("tc.forbidden.level." + warp);
/* 110 */         String wte = ws.replaceAll("%n", wr);
/* 111 */         list.add(EnumChatFormatting.DARK_PURPLE + wte);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/* 119 */     return itemstack.getItemDamage() == 0 ? EnumRarity.RARE : EnumRarity.EPIC;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\resources\ItemResearchNotes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */