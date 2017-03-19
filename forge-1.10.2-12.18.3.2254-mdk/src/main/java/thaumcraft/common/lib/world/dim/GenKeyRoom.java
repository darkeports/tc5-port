/*    */ package thaumcraft.common.lib.world.dim;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.IEntityLivingData;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.common.blocks.devices.BlockPedestal;
/*    */ import thaumcraft.common.entities.monster.EntityEldritchGuardian;
/*    */ import thaumcraft.common.lib.utils.EntityUtils;
/*    */ import thaumcraft.common.tiles.crafting.TilePedestal;
/*    */ 
/*    */ public class GenKeyRoom extends GenCommon
/*    */ {
/*    */   static void generateRoom(World world, Random random, int cx, int cz, int y, Cell cell)
/*    */   {
/* 24 */     int x = cx * 16;
/* 25 */     int z = cz * 16;
/*    */     
/* 27 */     GenCommon.generateChamberA(world, random, cx, cz, y, cell);
/*    */     
/* 29 */     GenCommon.generateConnections(world, random, cx, cz, y, cell, 3, true);
/*    */     
/* 31 */     world.setBlockState(new BlockPos(x + 8, y + 2, z + 8), BlocksTC.pedestal.getDefaultState().withProperty(BlockPedestal.VARIANT, thaumcraft.common.blocks.devices.BlockPedestal.PedestalType.ANCIENT));
/* 32 */     TileEntity te = world.getTileEntity(new BlockPos(x + 8, y + 2, z + 8));
/* 33 */     if ((te != null) && ((te instanceof TilePedestal))) {
/* 34 */       ((TilePedestal)te).setInventorySlotContents(0, new ItemStack(ItemsTC.runedTablet));
/*    */     }
/*    */     
/* 37 */     int zz = 2 + (world.getDifficulty() == EnumDifficulty.NORMAL ? 1 : world.getDifficulty() == EnumDifficulty.HARD ? 2 : 0);
/* 38 */     for (int qq = 0; qq < zz; qq++)
/*    */     {
/* 40 */       EntityEldritchGuardian eg = new EntityEldritchGuardian(world);
/* 41 */       double i1 = x + 8.5D + MathHelper.getRandomIntegerInRange(world.rand, 1, 3) * MathHelper.getRandomIntegerInRange(world.rand, -1, 1);
/* 42 */       double j1 = y + 3;
/* 43 */       double k1 = z + 8.5D + MathHelper.getRandomIntegerInRange(world.rand, 1, 3) * MathHelper.getRandomIntegerInRange(world.rand, -1, 1);
/*    */       
/* 45 */       eg.setPosition(i1, j1, k1);
/* 46 */       eg.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(eg)), (IEntityLivingData)null);
/* 47 */       eg.setHomePosAndDistance(new BlockPos(x + 8, y + 2, z + 8), 16);
/* 48 */       world.spawnEntityInWorld(eg);
/* 49 */       if ((qq == 0) || (qq == 3)) EntityUtils.makeChampion(eg, true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\dim\GenKeyRoom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */