package thaumcraft.api.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import thaumcraft.api.aspects.AspectList;

public abstract interface IArcaneRecipe
  extends IRecipe
{
  public abstract boolean matches(InventoryCrafting paramInventoryCrafting, World paramWorld);
  
  public abstract boolean matches(InventoryCrafting paramInventoryCrafting, World paramWorld, EntityPlayer paramEntityPlayer);
  
  public abstract ItemStack getCraftingResult(InventoryCrafting paramInventoryCrafting);
  
  public abstract int getRecipeSize();
  
  public abstract ItemStack getRecipeOutput();
  
  public abstract ItemStack[] getRemainingItems(InventoryCrafting paramInventoryCrafting);
  
  public abstract AspectList getAspects();
  
  public abstract AspectList getAspects(InventoryCrafting paramInventoryCrafting);
  
  public abstract String[] getResearch();
}


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\crafting\IArcaneRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */