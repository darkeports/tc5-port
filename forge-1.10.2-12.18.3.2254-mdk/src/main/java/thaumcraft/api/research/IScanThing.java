package thaumcraft.api.research;

import net.minecraft.entity.player.EntityPlayer;

public abstract interface IScanThing
{
  public abstract boolean checkThing(EntityPlayer paramEntityPlayer, Object paramObject);
  
  public abstract String getResearchKey();
}


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\research\IScanThing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */