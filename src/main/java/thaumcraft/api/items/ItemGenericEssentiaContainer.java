/*    */ package thaumcraft.api.items;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ import thaumcraft.api.aspects.AspectList;
/*    */ import thaumcraft.api.aspects.IEssentiaContainerItem;
/*    */ 
/*    */ public class ItemGenericEssentiaContainer extends Item implements IEssentiaContainerItem
/*    */ {
/*    */   public ItemGenericEssentiaContainer(int base)
/*    */   {
/* 23 */     this.base = base;
/* 24 */     setMaxStackSize(64);
/* 25 */     setHasSubtypes(true);
/* 26 */     setMaxDamage(0);
/*    */   }
/*    */   
/* 29 */   protected int base = 1;
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*    */   {
/* 34 */     for (Aspect tag : Aspect.aspects.values()) {
/* 35 */       ItemStack i = new ItemStack(this);
/* 36 */       setAspects(i, new AspectList().add(tag, this.base));
/* 37 */       par3List.add(i);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int getColorFromItemStack(ItemStack stack, int par2)
/*    */   {
/* 45 */     if (getAspects(stack) != null) {
/* 46 */       return getAspects(stack).getAspects()[0].getColor();
/*    */     }
/* 48 */     return 16777215;
/*    */   }
/*    */   
/*    */ 
/*    */   public AspectList getAspects(ItemStack itemstack)
/*    */   {
/* 54 */     if (itemstack.hasTagCompound()) {
/* 55 */       AspectList aspects = new AspectList();
/* 56 */       aspects.readFromNBT(itemstack.getTagCompound());
/* 57 */       return aspects.size() > 0 ? aspects : null;
/*    */     }
/* 59 */     return null;
/*    */   }
/*    */   
/*    */   public void setAspects(ItemStack itemstack, AspectList aspects)
/*    */   {
/* 64 */     if (!itemstack.hasTagCompound())
/* 65 */       itemstack.setTagCompound(new NBTTagCompound());
/* 66 */     aspects.writeToNBT(itemstack.getTagCompound());
/*    */   }
/*    */   
/*    */   public boolean ignoreContainedAspects() {
/* 70 */     return false;
/*    */   }
/*    */   
/*    */   public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
/* 74 */     if ((!world.isRemote) && (!stack.hasTagCompound())) {
/* 75 */       Aspect[] displayAspects = (Aspect[])Aspect.aspects.values().toArray(new Aspect[0]);
/* 76 */       setAspects(stack, new AspectList().add(displayAspects[world.rand.nextInt(displayAspects.length)], this.base));
/*    */     }
/* 78 */     super.onUpdate(stack, world, entity, par4, par5);
/*    */   }
/*    */   
/*    */   public void onCreated(ItemStack stack, World world, EntityPlayer player)
/*    */   {
/* 83 */     if ((!world.isRemote) && (!stack.hasTagCompound())) {
/* 84 */       Aspect[] displayAspects = (Aspect[])Aspect.aspects.values().toArray(new Aspect[0]);
/* 85 */       setAspects(stack, new AspectList().add(displayAspects[world.rand.nextInt(displayAspects.length)], this.base));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\api\items\ItemGenericEssentiaContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */