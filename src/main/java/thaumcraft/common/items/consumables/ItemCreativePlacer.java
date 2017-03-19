/*    */ package thaumcraft.common.items.consumables;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.IEntityLivingData;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.entities.construct.EntityTurretEldritch;
/*    */ import thaumcraft.common.items.ItemGenericVariants;
/*    */ import thaumcraft.common.lib.world.dim.GenCommon;
/*    */ 
/*    */ public class ItemCreativePlacer extends ItemGenericVariants
/*    */ {
/*    */   public ItemCreativePlacer()
/*    */   {
/* 23 */     super(new String[] { "obelisk", "node", "caster" });
/*    */   }
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*    */   {
/* 28 */     super.addInformation(stack, player, list, par4);
/* 29 */     list.add(EnumChatFormatting.DARK_PURPLE + "Creative only");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
/*    */   {
/* 37 */     IBlockState bs = world.getBlockState(pos);
/*    */     
/* 39 */     if (!bs.getBlock().getMaterial().isSolid()) return false;
/* 40 */     if (world.isRemote) { return false;
/*    */     }
/* 42 */     pos = pos.offset(side);
/* 43 */     bs = world.getBlockState(pos);
/*    */     
/* 45 */     if (!player.canPlayerEdit(pos, side, stack)) { return false;
/*    */     }
/* 47 */     if (!bs.getBlock().isReplaceable(world, pos)) { return false;
/*    */     }
/* 49 */     if ((stack.getItemDamage() == 0) && (!world.getBlockState(pos.down()).getBlock().getMaterial().isSolid())) { return false;
/*    */     }
/* 51 */     world.setBlockToAir(pos);
/*    */     
/* 53 */     switch (stack.getItemDamage()) {
/* 54 */     case 0:  GenCommon.genObelisk(world, pos); break;
/* 55 */     case 1:  thaumcraft.common.lib.world.ThaumcraftWorldGenerator.spawnNode(world, pos, -1, 1.0F); break;
/*    */     case 2: 
/* 57 */       EntityTurretEldritch turret = new EntityTurretEldritch(world, pos, side);
/* 58 */       if (turret != null) {
/* 59 */         turret.onInitialSpawn(world.getDifficultyForLocation(pos), (IEntityLivingData)null);
/* 60 */         turret.setValidSpawn();
/* 61 */         world.spawnEntityInWorld(turret);
/*    */       }
/*    */       break;
/*    */     }
/*    */     
/* 66 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public EnumRarity getRarity(ItemStack itemstack)
/*    */   {
/* 73 */     return EnumRarity.EPIC;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\consumables\ItemCreativePlacer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */