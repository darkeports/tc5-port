package thaumcraft.api.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;

public abstract interface ILabelable
{
  public abstract boolean applyLabel(EntityPlayer paramEntityPlayer, BlockPos paramBlockPos, EnumFacing paramEnumFacing, ItemStack paramItemStack);
}


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\blocks\ILabelable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */