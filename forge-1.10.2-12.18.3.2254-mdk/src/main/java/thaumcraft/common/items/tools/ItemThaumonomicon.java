/*     */ package thaumcraft.common.items.tools;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.research.ResearchCategories;
/*     */ import thaumcraft.api.research.ResearchCategoryList;
/*     */ import thaumcraft.api.research.ResearchItem;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.items.IVariantItems;
/*     */ import thaumcraft.common.lib.events.CommandThaumcraft;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.playerdata.PacketSyncResearch;
/*     */ import thaumcraft.common.lib.research.ResearchManager;
/*     */ 
/*     */ public class ItemThaumonomicon extends Item implements IVariantItems
/*     */ {
/*     */   public ItemThaumonomicon()
/*     */   {
/*  34 */     setHasSubtypes(true);
/*  35 */     setMaxDamage(0);
/*  36 */     setMaxStackSize(1);
/*     */   }
/*     */   
/*     */   public String[] getVariantNames()
/*     */   {
/*  41 */     return new String[] { "*", "cheat" };
/*     */   }
/*     */   
/*     */   public int[] getVariantMeta()
/*     */   {
/*  46 */     return new int[] { 0, 1 };
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*     */   {
/*  52 */     par3List.add(new ItemStack(this, 1, 0));
/*  53 */     if (Config.allowCheatSheet) par3List.add(new ItemStack(this, 1, 1));
/*     */   }
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*     */   {
/*  58 */     super.addInformation(stack, player, list, par4);
/*  59 */     if (stack.getItemDamage() == 1) list.add(EnumChatFormatting.DARK_PURPLE + "Creative only");
/*     */   }
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
/*     */   {
/*  64 */     if (!world.isRemote)
/*     */     {
/*  66 */       if ((Config.allowCheatSheet) && (stack.getItemDamage() == 1)) {
/*  67 */         Collection<ResearchCategoryList> rc = ResearchCategories.researchCategories.values();
/*  68 */         for (ResearchCategoryList cat : rc) {
/*  69 */           Collection<ResearchItem> rl = cat.research.values();
/*  70 */           for (ResearchItem ri : rl) {
/*  71 */             CommandThaumcraft.giveRecursiveResearch(player, ri.key, (byte)0);
/*     */           }
/*     */         }
/*     */       } else {
/*  75 */         Collection<ResearchCategoryList> rc = ResearchCategories.researchCategories.values();
/*  76 */         for (ResearchCategoryList cat : rc) {
/*  77 */           Collection<ResearchItem> rl = cat.research.values();
/*  78 */           for (ResearchItem ri : rl) {
/*  79 */             if ((ResearchManager.isResearchComplete(player.getName(), ri.key)) && 
/*  80 */               (ri.siblings != null)) {
/*  81 */               for (String sib : ri.siblings) {
/*  82 */                 if (!ResearchManager.isResearchComplete(player.getName(), sib)) {
/*  83 */                   Thaumcraft.proxy.getResearchManager().completeResearch(player, sib, (byte)0);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  92 */       PacketHandler.INSTANCE.sendTo(new PacketSyncResearch(player), (EntityPlayerMP)player);
/*     */     } else {
/*  94 */       world.playSound(player.posX, player.posY, player.posZ, "thaumcraft:page", 1.0F, 1.0F, false);
/*     */     }
/*  96 */     player.openGui(Thaumcraft.instance, 12, world, 0, 0, 0);
/*  97 */     return stack;
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/* 103 */     return itemstack.getItemDamage() != 1 ? EnumRarity.UNCOMMON : EnumRarity.EPIC;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getUnlocalizedName(ItemStack par1ItemStack)
/*     */   {
/* 109 */     if (getVariantNames()[par1ItemStack.getItemDamage()].equals("*")) {
/* 110 */       return super.getUnlocalizedName();
/*     */     }
/* 112 */     return super.getUnlocalizedName() + "." + getVariantNames()[par1ItemStack.getItemDamage()];
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemThaumonomicon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */