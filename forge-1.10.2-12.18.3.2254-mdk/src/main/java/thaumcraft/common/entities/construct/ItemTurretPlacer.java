/*    */ package thaumcraft.common.entities.construct;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.items.ItemGenericVariants;
/*    */ 
/*    */ public class ItemTurretPlacer extends ItemGenericVariants
/*    */ {
/*    */   public ItemTurretPlacer()
/*    */   {
/* 15 */     super(new String[] { "basic", "focus", "magnet", "advanced" });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, net.minecraft.util.EnumFacing side, float hitX, float hitY, float hitZ)
/*    */   {
/* 23 */     IBlockState bs = world.getBlockState(pos);
/*    */     
/* 25 */     if (!bs.getBlock().getMaterial().isSolid()) return false;
/* 26 */     if (world.isRemote) { return false;
/*    */     }
/* 28 */     pos = pos.offset(side);
/* 29 */     bs = world.getBlockState(pos);
/*    */     
/* 31 */     if (!player.canPlayerEdit(pos, side, stack)) { return false;
/*    */     }
/* 33 */     if (!bs.getBlock().isReplaceable(world, pos)) { return false;
/*    */     }
/* 35 */     if ((stack.getItemDamage() != 1) && (!world.getBlockState(pos.down()).getBlock().getMaterial().isSolid())) { return false;
/*    */     }
/* 37 */     world.setBlockToAir(pos);
/* 38 */     EntityOwnedConstruct turret = null;
/* 39 */     switch (stack.getItemDamage()) {
/* 40 */     case 0:  turret = new EntityTurretCrossbow(world, pos); break;
/* 41 */     case 1:  turret = new EntityTurretFocus(world, pos, side); break;
/* 42 */     case 2:  turret = new EntityNodeMagnet(world, pos); break;
/* 43 */     case 3:  turret = new EntityTurretCrossbowAdvanced(world, pos);
/*    */     }
/*    */     
/* 46 */     if (turret != null) {
/* 47 */       world.spawnEntityInWorld(turret);
/* 48 */       turret.setOwned(true);
/* 49 */       turret.setValidSpawn();
/* 50 */       turret.setOwnerId(player.getUniqueID().toString());
/* 51 */       if (!player.capabilities.isCreativeMode) { stack.stackSize -= 1;
/*    */       }
/*    */     }
/*    */     
/* 55 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\ItemTurretPlacer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */