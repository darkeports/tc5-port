/*     */ package thaumcraft.common.lib.crafting;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.crafting.InfusionRecipe;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.common.items.baubles.ItemBaubles;
/*     */ 
/*     */ public class InfusionVisDiscountCombineRecipe
/*     */   extends InfusionRecipe
/*     */ {
/*     */   private ItemStack[] components;
/*     */   
/*     */   public InfusionVisDiscountCombineRecipe()
/*     */   {
/*  22 */     super("", null, 0, null, null, new ItemStack[] { new ItemStack(ItemsTC.baubles), new ItemStack(ItemsTC.baubles) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean matches(ArrayList<ItemStack> input, ItemStack central, World world, EntityPlayer player)
/*     */   {
/*  35 */     if ((this.research != null) && (this.research[0].length() > 0) && (!ResearchHelper.isResearchComplete(player.getName(), this.research))) {
/*  36 */       return false;
/*     */     }
/*     */     
/*  39 */     if ((!(central.getItem() instanceof ItemBaubles)) || (central.getItemDamage() < 3) || (central.getItemDamage() > 8))
/*     */     {
/*     */ 
/*  42 */       return false;
/*     */     }
/*  44 */     int c = 0;
/*  45 */     for (ItemStack is : input) {
/*  46 */       if (is != null) {
/*  47 */         if ((!(is.getItem() instanceof ItemBaubles)) || (is.getItemDamage() < 3) || (is.getItemDamage() > 8))
/*     */         {
/*  49 */           return false;
/*     */         }
/*  51 */         c++;
/*     */       }
/*     */     }
/*  54 */     return c == 2;
/*     */   }
/*     */   
/*     */   private AspectList calcAspects(EntityPlayer player, ItemStack input, ArrayList<ItemStack> comps) {
/*  58 */     AspectList al = new AspectList();
/*  59 */     for (Aspect aspect : Aspect.getPrimalAspects()) {
/*  60 */       int d = ((ItemBaubles)input.getItem()).getVisDiscount(input, player, aspect);
/*  61 */       int c = 0;
/*  62 */       for (ItemStack is : comps) {
/*  63 */         int q = ((ItemBaubles)is.getItem()).getVisDiscount(is, player, aspect);
/*  64 */         if ((d > 0) && (q == d)) c++;
/*  65 */         if (q > d) { d = q;
/*     */         }
/*     */       }
/*  68 */       if (c == 2) d++;
/*  69 */       if (d > 0) {
/*  70 */         al.merge(aspect, d);
/*     */       }
/*     */     }
/*  73 */     return al;
/*     */   }
/*     */   
/*     */   public Object getRecipeOutput(EntityPlayer player, ItemStack input, ArrayList<ItemStack> comps)
/*     */   {
/*  78 */     ItemStack out = input.copy();
/*  79 */     AspectList al = calcAspects(player, input, comps);
/*  80 */     for (Aspect aspect : al.getAspects()) {
/*  81 */       if (al.getAmount(aspect) > 0) {
/*  82 */         if (out.getTagCompound() == null)
/*     */         {
/*  84 */           out.setTagCompound(new NBTTagCompound());
/*     */         }
/*  86 */         out.getTagCompound().setInteger(aspect.getTag(), al.getAmount(aspect));
/*     */       }
/*     */     }
/*  89 */     ((ItemBaubles)out.getItem()).calcColor(out);
/*  90 */     return out;
/*     */   }
/*     */   
/*     */   public AspectList getAspects(EntityPlayer player, ItemStack input, ArrayList<ItemStack> comps)
/*     */   {
/*  95 */     AspectList al = new AspectList();
/*  96 */     int d = 0;
/*  97 */     AspectList al2 = calcAspects(player, input, comps);
/*  98 */     for (Aspect aspect : al2.getAspects()) {
/*  99 */       int q = al2.getAmount(aspect) * 2;
/* 100 */       al.merge(aspect, q * q);
/* 101 */       if (q * q > d) d = q * q;
/*     */     }
/* 103 */     al.merge(Aspect.EXCHANGE, d);
/* 104 */     return al;
/*     */   }
/*     */   
/*     */   public int getInstability(EntityPlayer player, ItemStack input, ArrayList<ItemStack> comps)
/*     */   {
/* 109 */     return this.instability;
/*     */   }
/*     */   
/*     */   public ItemStack[] getComponents()
/*     */   {
/* 114 */     return this.components;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\crafting\InfusionVisDiscountCombineRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */