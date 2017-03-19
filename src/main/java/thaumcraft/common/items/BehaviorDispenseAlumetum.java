/*    */ package thaumcraft.common.items;
/*    */ 
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*    */ import net.minecraft.dispenser.BehaviorProjectileDispense;
/*    */ import net.minecraft.dispenser.IBlockSource;
/*    */ import net.minecraft.dispenser.IPosition;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.IProjectile;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.common.entities.projectile.EntityAlumentum;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BehaviorDispenseAlumetum
/*    */   extends BehaviorProjectileDispense
/*    */ {
/*    */   public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/*    */   {
/* 25 */     if (par2ItemStack.getItemDamage() != 0) {
/* 26 */       BehaviorDefaultDispenseItem def = new BehaviorDefaultDispenseItem();
/* 27 */       return def.dispense(par1IBlockSource, par2ItemStack);
/*    */     }
/* 29 */     World var3 = par1IBlockSource.getWorld();
/* 30 */     IPosition var4 = BlockDispenser.getDispensePosition(par1IBlockSource);
/* 31 */     EnumFacing var5 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
/* 32 */     IProjectile var6 = getProjectileEntity(var3, var4);
/* 33 */     var6.setThrowableHeading(var5.getFrontOffsetX(), var5.getFrontOffsetY() + 0.1F, var5.getFrontOffsetZ(), func_82500_b(), func_82498_a());
/* 34 */     var3.spawnEntityInWorld((Entity)var6);
/* 35 */     par2ItemStack.splitStack(1);
/* 36 */     return par2ItemStack;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition)
/*    */   {
/* 43 */     return new EntityAlumentum(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
/*    */   }
/*    */   
/*    */ 
/*    */   protected void playDispenseSound(IBlockSource par1IBlockSource)
/*    */   {
/* 49 */     par1IBlockSource.getWorld().playAuxSFX(1009, par1IBlockSource.getBlockPos(), 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\BehaviorDispenseAlumetum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */