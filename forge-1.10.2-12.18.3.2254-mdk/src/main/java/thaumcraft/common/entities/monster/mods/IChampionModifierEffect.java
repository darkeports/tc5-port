package thaumcraft.common.entities.monster.mods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract interface IChampionModifierEffect
{
  public abstract float performEffect(EntityLivingBase paramEntityLivingBase1, EntityLivingBase paramEntityLivingBase2, DamageSource paramDamageSource, float paramFloat);
  
  @SideOnly(Side.CLIENT)
  public abstract void showFX(EntityLivingBase paramEntityLivingBase);
}


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\monster\mods\IChampionModifierEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */