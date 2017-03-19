/*    */ package thaumcraft.common.items.consumables;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.PlayerCapabilities;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.ForgeEventFactory;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ItemBucketPure extends net.minecraft.item.Item
/*    */ {
/*    */   public ItemBucketPure()
/*    */   {
/* 19 */     setCreativeTab(Thaumcraft.tabTC);
/* 20 */     setHasSubtypes(false);
/* 21 */     setMaxStackSize(1);
/*    */   }
/*    */   
/*    */ 
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
/*    */   {
/* 27 */     boolean flag = false;
/* 28 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, flag);
/*    */     
/* 30 */     if (movingobjectposition == null)
/*    */     {
/* 32 */       return itemStackIn;
/*    */     }
/*    */     
/*    */ 
/* 36 */     ItemStack ret = ForgeEventFactory.onBucketUse(playerIn, worldIn, itemStackIn, movingobjectposition);
/* 37 */     if (ret != null) { return ret;
/*    */     }
/* 39 */     if (movingobjectposition.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK)
/*    */     {
/* 41 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*    */       
/* 43 */       if (!worldIn.isBlockModifiable(playerIn, blockpos))
/*    */       {
/* 45 */         return itemStackIn;
/*    */       }
/*    */       
/*    */ 
/* 49 */       BlockPos blockpos1 = blockpos.offset(movingobjectposition.sideHit);
/*    */       
/* 51 */       if (!playerIn.canPlayerEdit(blockpos1, movingobjectposition.sideHit, itemStackIn))
/*    */       {
/* 53 */         return itemStackIn;
/*    */       }
/*    */       
/* 56 */       if ((tryPlaceContainedLiquid(worldIn, blockpos1)) && (!playerIn.capabilities.isCreativeMode))
/*    */       {
/* 58 */         playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[net.minecraft.item.Item.getIdFromItem(this)]);
/* 59 */         return new ItemStack(net.minecraft.init.Items.bucket);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 64 */     return itemStackIn;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean tryPlaceContainedLiquid(World world, BlockPos pos)
/*    */   {
/* 74 */     Material material = world.getBlockState(pos).getBlock().getMaterial();
/* 75 */     boolean flag = !material.isSolid();
/*    */     
/* 77 */     if ((!world.isAirBlock(pos)) && (!flag)) {
/* 78 */       return false;
/*    */     }
/* 80 */     if ((!world.isRemote) && (flag) && (!material.isLiquid()))
/*    */     {
/* 82 */       world.destroyBlock(pos, true);
/*    */     }
/*    */     
/* 85 */     world.setBlockState(pos, thaumcraft.api.blocks.BlocksTC.purifyingFluid.getDefaultState());
/*    */     
/* 87 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemBucketPure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */