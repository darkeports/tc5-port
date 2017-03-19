/*    */ package thaumcraft.common.items.resources;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ import thaumcraft.api.blocks.ILabelable;
/*    */ import thaumcraft.api.items.ItemGenericEssentiaContainer;
/*    */ import thaumcraft.common.items.IVariantItems;
/*    */ 
/*    */ public class ItemLabel
/*    */   extends ItemGenericEssentiaContainer implements IVariantItems
/*    */ {
/*    */   public ItemLabel()
/*    */   {
/* 27 */     super(1);
/*    */   }
/*    */   
/*    */   public String[] getVariantNames()
/*    */   {
/* 32 */     return new String[] { "blank", "filled" };
/*    */   }
/*    */   
/*    */   public int[] getVariantMeta()
/*    */   {
/* 37 */     return new int[] { 0, 1 };
/*    */   }
/*    */   
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int getColorFromItemStack(ItemStack stack, int layer)
/*    */   {
/* 44 */     if ((stack.getItemDamage() == 1) && (getAspects(stack) != null) && (layer == 1)) {
/* 45 */       return getAspects(stack).getAspects()[0].getColor();
/*    */     }
/* 47 */     return 16777215;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*    */   {
/* 53 */     par3List.add(new ItemStack(this, 1, 0));
/*    */   }
/*    */   
/*    */ 
/*    */   public String getUnlocalizedName(ItemStack stack)
/*    */   {
/* 59 */     return super.getUnlocalizedName() + "." + getVariantNames()[stack.getItemDamage()];
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
/*    */   {
/* 66 */     if (world.isRemote) { return false;
/*    */     }
/* 68 */     IBlockState bs = world.getBlockState(pos);
/* 69 */     if ((bs.getBlock() instanceof ILabelable)) {
/* 70 */       if (((ILabelable)bs.getBlock()).applyLabel(player, pos, side, stack)) {
/* 71 */         stack.stackSize -= 1;
/* 72 */         player.inventoryContainer.detectAndSendChanges();
/*    */       }
/* 74 */       return true;
/*    */     }
/*    */     
/* 77 */     TileEntity te = world.getTileEntity(pos);
/* 78 */     if ((te instanceof ILabelable)) {
/* 79 */       if (((ILabelable)te).applyLabel(player, pos, side, stack)) {
/* 80 */         stack.stackSize -= 1;
/* 81 */         player.inventoryContainer.detectAndSendChanges();
/*    */       }
/* 83 */       return true;
/*    */     }
/*    */     
/* 86 */     return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ);
/*    */   }
/*    */   
/*    */ 
/*    */   public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {}
/*    */   
/*    */   public void onCreated(ItemStack stack, World world, EntityPlayer player) {}
/*    */   
/*    */   public boolean ignoreContainedAspects()
/*    */   {
/* 96 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\resources\ItemLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */