/*    */ package thaumcraft.common.blocks.world.ore;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.blocks.BlocksTC;
/*    */ import thaumcraft.api.items.ItemsTC;
/*    */ import thaumcraft.common.blocks.BlockTC;
/*    */ 
/*    */ public class BlockOreTC extends BlockTC
/*    */ {
/*    */   public BlockOreTC()
/*    */   {
/* 22 */     super(net.minecraft.block.material.Material.rock);
/* 23 */     setResistance(5.0F);
/* 24 */     setStepSound(Block.soundTypeStone);
/*    */   }
/*    */   
/*    */ 
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune)
/*    */   {
/* 30 */     return this == BlocksTC.oreAmber ? ItemsTC.amber : Item.getItemFromBlock(this);
/*    */   }
/*    */   
/*    */ 
/*    */   public int quantityDropped(Random random)
/*    */   {
/* 36 */     return this == BlocksTC.oreAmber ? 1 + random.nextInt(2) : 1;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
/*    */   {
/* 42 */     IBlockState state = world.getBlockState(pos);
/* 43 */     Random rand = (world instanceof World) ? ((World)world).rand : new Random();
/* 44 */     if (getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
/*    */     {
/* 46 */       int j = 0;
/*    */       
/* 48 */       if (this == BlocksTC.oreAmber)
/*    */       {
/* 50 */         j = MathHelper.getRandomIntegerInRange(rand, 1, 4);
/*    */       }
/*    */       
/* 53 */       return j;
/*    */     }
/* 55 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public int quantityDroppedWithBonus(int fortune, Random random)
/*    */   {
/* 61 */     if ((fortune > 0) && (Item.getItemFromBlock(this) != getItemDropped((IBlockState)getBlockState().getValidStates().iterator().next(), random, fortune)))
/*    */     {
/* 63 */       int j = random.nextInt(fortune + 2) - 1;
/*    */       
/* 65 */       if (j < 0)
/*    */       {
/* 67 */         j = 0;
/*    */       }
/*    */       
/* 70 */       return quantityDropped(random) * (j + 1);
/*    */     }
/*    */     
/*    */ 
/* 74 */     return quantityDropped(random);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getDamageValue(World worldIn, BlockPos pos)
/*    */   {
/* 81 */     return 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public int damageDropped(IBlockState state)
/*    */   {
/* 87 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\world\ore\BlockOreTC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */