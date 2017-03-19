/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.ThaumcraftApiHelper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.IEssentiaTransport;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.api.wands.IWandable;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ 
/*     */ public class TileArcaneBoreBase
/*     */   extends TileThaumcraft implements IWandable, IEssentiaTransport
/*     */ {
/*     */   public boolean onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, BlockPos pos, EnumFacing side)
/*     */   {
/*  23 */     if (side.getHorizontalIndex() >= 0) {
/*  24 */       ((BlockTCDevice)getBlockType()).updateFacing(world, getPos(), side);
/*  25 */       player.worldObj.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "thaumcraft:tool", 0.3F, 1.9F + player.worldObj.rand.nextFloat() * 0.2F, false);
/*  26 */       player.swingItem();
/*  27 */       markDirty();
/*     */     }
/*  29 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}
/*     */   
/*     */   public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
/*     */   
/*     */   boolean drawEssentia()
/*     */   {
/*  39 */     for (EnumFacing facing : EnumFacing.HORIZONTALS) {
/*  40 */       TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.pos, facing);
/*  41 */       if (te != null) {
/*  42 */         IEssentiaTransport ic = (IEssentiaTransport)te;
/*  43 */         if (!ic.canOutputTo(facing.getOpposite())) return false;
/*  44 */         if ((ic.getSuctionAmount(facing.getOpposite()) < getSuctionAmount(facing)) && (ic.takeEssentia(Aspect.ENTROPY, 1, facing.getOpposite()) == 1))
/*     */         {
/*  46 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*  50 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isConnectable(EnumFacing face)
/*     */   {
/*  55 */     return (face != EnumFacing.UP) && (face != EnumFacing.DOWN);
/*     */   }
/*     */   
/*     */   public boolean canInputFrom(EnumFacing face)
/*     */   {
/*  60 */     return isConnectable(face);
/*     */   }
/*     */   
/*     */   public boolean canOutputTo(EnumFacing face)
/*     */   {
/*  65 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSuction(Aspect aspect, int amount) {}
/*     */   
/*     */ 
/*     */   public Aspect getSuctionType(EnumFacing face)
/*     */   {
/*  74 */     return Aspect.ENTROPY;
/*     */   }
/*     */   
/*     */   public int getSuctionAmount(EnumFacing face)
/*     */   {
/*  79 */     return face != BlockStateUtils.getFacing(getBlockMetadata()) ? 128 : 0;
/*     */   }
/*     */   
/*     */   public int takeEssentia(Aspect aspect, int amount, EnumFacing face)
/*     */   {
/*  84 */     return 0;
/*     */   }
/*     */   
/*     */   public int addEssentia(Aspect aspect, int amount, EnumFacing face)
/*     */   {
/*  89 */     return 0;
/*     */   }
/*     */   
/*     */   public Aspect getEssentiaType(EnumFacing face)
/*     */   {
/*  94 */     return null;
/*     */   }
/*     */   
/*     */   public int getEssentiaAmount(EnumFacing face)
/*     */   {
/*  99 */     return 0;
/*     */   }
/*     */   
/*     */   public int getMinimumSuction()
/*     */   {
/* 104 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileArcaneBoreBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */