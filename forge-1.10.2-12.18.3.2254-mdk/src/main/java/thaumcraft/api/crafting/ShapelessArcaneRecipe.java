/*     */ package thaumcraft.api.crafting;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ import thaumcraft.api.ThaumcraftApiHelper;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ 
/*     */ public class ShapelessArcaneRecipe implements IArcaneRecipe
/*     */ {
/*  20 */   private ItemStack output = null;
/*  21 */   private ArrayList input = new ArrayList();
/*     */   
/*  23 */   public AspectList aspects = null;
/*     */   public String[] research;
/*     */   
/*  26 */   public ShapelessArcaneRecipe(String research, ItemStack result, AspectList aspects, Object... recipe) { this(new String[] { research }, result, aspects, recipe); }
/*     */   
/*     */   public ShapelessArcaneRecipe(String[] research, ItemStack result, AspectList aspects, Object... recipe)
/*     */   {
/*  30 */     this.output = result.copy();
/*  31 */     this.research = research;
/*  32 */     this.aspects = aspects;
/*  33 */     for (Object in : recipe)
/*     */     {
/*  35 */       if ((in instanceof ItemStack))
/*     */       {
/*  37 */         this.input.add(((ItemStack)in).copy());
/*     */       }
/*  39 */       else if ((in instanceof Item))
/*     */       {
/*  41 */         this.input.add(new ItemStack((Item)in));
/*     */       }
/*  43 */       else if ((in instanceof Block))
/*     */       {
/*  45 */         this.input.add(new ItemStack((Block)in));
/*     */       }
/*  47 */       else if ((in instanceof String))
/*     */       {
/*  49 */         this.input.add(OreDictionary.getOres((String)in));
/*     */       }
/*     */       else
/*     */       {
/*  53 */         String ret = "Invalid shapeless ore recipe: ";
/*  54 */         for (Object tmp : recipe)
/*     */         {
/*  56 */           ret = ret + tmp + ", ";
/*     */         }
/*  58 */         ret = ret + this.output;
/*  59 */         throw new RuntimeException(ret);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int getRecipeSize() {
/*  65 */     return this.input.size();
/*     */   }
/*     */   
/*  68 */   public ItemStack getRecipeOutput() { return this.output; }
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting var1) {
/*  71 */     return this.output.copy();
/*     */   }
/*     */   
/*     */   public boolean matches(InventoryCrafting inv, World world)
/*     */   {
/*  76 */     return ((inv instanceof IArcaneWorkbench)) && (matches(inv, world, null));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean matches(InventoryCrafting var1, World world, EntityPlayer player)
/*     */   {
/*  82 */     if ((player != null) && (this.research != null) && (this.research[0].length() > 0) && (!ResearchHelper.isResearchComplete(player.getName(), this.research))) {
/*  83 */       return false;
/*     */     }
/*     */     
/*  86 */     ArrayList required = new ArrayList(this.input);
/*     */     
/*  88 */     for (int x = 0; x < 9; x++)
/*     */     {
/*  90 */       ItemStack slot = var1.getStackInSlot(x);
/*     */       
/*  92 */       if (slot != null)
/*     */       {
/*  94 */         boolean inRecipe = false;
/*  95 */         Iterator req = required.iterator();
/*     */         
/*  97 */         while (req.hasNext())
/*     */         {
/*  99 */           boolean match = false;
/*     */           
/* 101 */           Object next = req.next();
/*     */           
/* 103 */           if ((next instanceof ItemStack))
/*     */           {
/* 105 */             match = checkItemEquals((ItemStack)next, slot);
/*     */           }
/* 107 */           else if ((next instanceof List))
/*     */           {
/* 109 */             for (ItemStack item : (List<ItemStack>)next)
/*     */             {
/* 111 */               match = (match) || (checkItemEquals(item, slot));
/*     */             }
/*     */           }
/*     */           
/* 115 */           if (match)
/*     */           {
/* 117 */             inRecipe = true;
/* 118 */             required.remove(next);
/* 119 */             break;
/*     */           }
/*     */         }
/*     */         
/* 123 */         if (!inRecipe)
/*     */         {
/* 125 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 130 */     return required.isEmpty();
/*     */   }
/*     */   
/*     */   private boolean checkItemEquals(ItemStack target, ItemStack input)
/*     */   {
/* 135 */     if (((input == null) && (target != null)) || ((input != null) && (target == null)))
/*     */     {
/* 137 */       return false;
/*     */     }
/* 139 */     return (target.getItem() == input.getItem()) && ((!target.hasTagCompound()) || (ThaumcraftApiHelper.areItemStackTagsEqualForCrafting(input, target))) && ((target.getItemDamage() == 32767) || (target.getItemDamage() == input.getItemDamage()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList getInput()
/*     */   {
/* 151 */     return this.input;
/*     */   }
/*     */   
/*     */   public AspectList getAspects()
/*     */   {
/* 156 */     return this.aspects;
/*     */   }
/*     */   
/*     */   public AspectList getAspects(InventoryCrafting inv)
/*     */   {
/* 161 */     return this.aspects;
/*     */   }
/*     */   
/*     */   public String[] getResearch()
/*     */   {
/* 166 */     return this.research;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack[] getRemainingItems(InventoryCrafting p_179532_1_)
/*     */   {
/* 172 */     ItemStack[] aitemstack = new ItemStack[p_179532_1_.getSizeInventory()];
/*     */     
/* 174 */     for (int i = 0; i < Math.min(9, aitemstack.length); i++)
/*     */     {
/* 176 */       ItemStack itemstack = p_179532_1_.getStackInSlot(i);
/* 177 */       aitemstack[i] = ForgeHooks.getContainerItem(itemstack);
/*     */     }
/*     */     
/* 180 */     return aitemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\crafting\ShapelessArcaneRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */