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
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ 
/*    */ public class ItemBucketDeath extends net.minecraft.item.Item
/*    */ {
/*    */   public ItemBucketDeath()
/*    */   {
/* 20 */     setCreativeTab(Thaumcraft.tabTC);
/* 21 */     setHasSubtypes(false);
/* 22 */     setMaxStackSize(1);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
/*    */   {
/* 29 */     boolean flag = false;
/* 30 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, flag);
/*    */     
/* 32 */     if (movingobjectposition == null)
/*    */     {
/* 34 */       return itemStackIn;
/*    */     }
/*    */     
/*    */ 
/* 38 */     ItemStack ret = ForgeEventFactory.onBucketUse(playerIn, worldIn, itemStackIn, movingobjectposition);
/* 39 */     if (ret != null) { return ret;
/*    */     }
/* 41 */     if (movingobjectposition.typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK)
/*    */     {
/* 43 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*    */       
/* 45 */       if (!worldIn.isBlockModifiable(playerIn, blockpos))
/*    */       {
/* 47 */         return itemStackIn;
/*    */       }
/*    */       
/*    */ 
/* 51 */       BlockPos blockpos1 = blockpos.offset(movingobjectposition.sideHit);
/*    */       
/* 53 */       if (!playerIn.canPlayerEdit(blockpos1, movingobjectposition.sideHit, itemStackIn))
/*    */       {
/* 55 */         return itemStackIn;
/*    */       }
/*    */       
/* 58 */       if ((tryPlaceContainedLiquid(worldIn, blockpos1)) && (!playerIn.capabilities.isCreativeMode))
/*    */       {
/* 60 */         playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[net.minecraft.item.Item.getIdFromItem(this)]);
/* 61 */         return new ItemStack(net.minecraft.init.Items.bucket);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 66 */     return itemStackIn;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean tryPlaceContainedLiquid(World world, BlockPos pos)
/*    */   {
/* 76 */     Material material = world.getBlockState(pos).getBlock().getMaterial();
/* 77 */     boolean flag = !material.isSolid();
/*    */     
/* 79 */     if ((!world.isAirBlock(pos)) && (!flag)) {
/* 80 */       return false;
/*    */     }
/* 82 */     if ((!world.isRemote) && (flag) && (!material.isLiquid()))
/*    */     {
/* 84 */       world.destroyBlock(pos, true);
/*    */     }
/*    */     
/* 87 */     world.setBlockState(pos, BlocksTC.liquidDeath.getDefaultState());
/*    */     
/* 89 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemBucketDeath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */