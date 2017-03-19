/*     */ package thaumcraft.common.lib.crafting;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ import net.minecraftforge.oredict.ShapelessOreRecipe;
/*     */ 
/*     */ public class ShapelessNBTOreRecipe
/*     */   extends ShapelessOreRecipe
/*     */ {
/*  16 */   private ItemStack output = null;
/*  17 */   private ArrayList input = new ArrayList();
/*     */   
/*  19 */   public ShapelessNBTOreRecipe(Block result, Object... recipe) { this(new ItemStack(result), recipe); }
/*  20 */   public ShapelessNBTOreRecipe(Item result, Object... recipe) { this(new ItemStack(result), recipe); }
/*     */   
/*     */   public ShapelessNBTOreRecipe(ItemStack result, Object... recipe)
/*     */   {
/*  24 */     super(result, recipe);
/*  25 */     this.output = result.copy();
/*  26 */     for (Object in : recipe)
/*     */     {
/*  28 */       if ((in instanceof ItemStack))
/*     */       {
/*  30 */         this.input.add(((ItemStack)in).copy());
/*     */       }
/*  32 */       else if ((in instanceof Item))
/*     */       {
/*  34 */         this.input.add(new ItemStack((Item)in));
/*     */       }
/*  36 */       else if ((in instanceof Block))
/*     */       {
/*  38 */         this.input.add(new ItemStack((Block)in));
/*     */       }
/*  40 */       else if ((in instanceof String))
/*     */       {
/*  42 */         this.input.add(OreDictionary.getOres((String)in));
/*     */       }
/*     */       else
/*     */       {
/*  46 */         String ret = "Invalid shapeless ore recipe: ";
/*  47 */         for (Object tmp : recipe)
/*     */         {
/*  49 */           ret = ret + tmp + ", ";
/*     */         }
/*  51 */         ret = ret + this.output;
/*  52 */         throw new RuntimeException(ret);
/*     */       }
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRecipeSize()
/*     */   {
/*  78 */     return this.input.size();
/*     */   }
/*     */   
/*  81 */   public ItemStack getRecipeOutput() { return this.output; }
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting var1) {
/*  84 */     return this.output.copy();
/*     */   }
/*     */   
/*     */   public boolean matches(InventoryCrafting var1, World world)
/*     */   {
/*  89 */     ArrayList required = new ArrayList(this.input);
/*     */     
/*  91 */     for (int x = 0; x < var1.getSizeInventory(); x++)
/*     */     {
/*  93 */       ItemStack slot = var1.getStackInSlot(x);
/*     */       
/*  95 */       if (slot != null)
/*     */       {
/*  97 */         boolean inRecipe = false;
/*  98 */         Iterator req = required.iterator();
/*     */         
/* 100 */         while (req.hasNext())
/*     */         {
/* 102 */           boolean match = false;
/*     */           
/* 104 */           Object next = req.next();
/*     */           
/* 106 */           if ((next instanceof ItemStack))
/*     */           {
/* 108 */             match = checkItemEquals((ItemStack)next, slot);
/*     */           }
/* 110 */           else if ((next instanceof ArrayList))
/*     */           {
/* 112 */             for (ItemStack item : (ArrayList)next)
/*     */             {
/* 114 */               match = (match) || (checkItemEquals(item, slot));
/*     */             }
/*     */           }
/*     */           
/* 118 */           if (match)
/*     */           {
/* 120 */             inRecipe = true;
/* 121 */             required.remove(next);
/* 122 */             break;
/*     */           }
/*     */         }
/*     */         
/* 126 */         if (!inRecipe)
/*     */         {
/* 128 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 133 */     return required.isEmpty();
/*     */   }
/*     */   
/*     */   private boolean checkItemEquals(ItemStack target, ItemStack input)
/*     */   {
/* 138 */     return (target.getItem() == input.getItem()) && (ItemStack.areItemStackTagsEqual(target, input)) && ((target.getItemDamage() == 32767) || (target.getItemDamage() == input.getItemDamage()));
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
/* 150 */     return this.input;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\crafting\ShapelessNBTOreRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */