/*     */ package thaumcraft.common.tiles.misc;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.items.wands.foci.ItemFocusPortableHole;
/*     */ import thaumcraft.common.tiles.TileMemory;
/*     */ 
/*     */ public class TileHole extends TileMemory implements net.minecraft.util.ITickable
/*     */ {
/*  17 */   public short countdown = 0;
/*  18 */   public short countdownmax = 120;
/*  19 */   public byte count = 0;
/*  20 */   public EnumFacing direction = null;
/*     */   
/*     */ 
/*     */   public TileHole() {}
/*     */   
/*     */ 
/*     */   public TileHole(IBlockState bi, short max, byte count, EnumFacing direction)
/*     */   {
/*  28 */     super(bi);
/*  29 */     this.count = count;
/*  30 */     this.countdownmax = max;
/*  31 */     this.direction = direction;
/*     */   }
/*     */   
/*     */   public TileHole(byte count)
/*     */   {
/*  36 */     this.count = count;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void update()
/*     */   {
/*  43 */     if (this.worldObj.isRemote)
/*     */     {
/*  45 */       for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(1); a++) {
/*  46 */         surroundwithsparkles();
/*     */       }
/*     */     } else {
/*  49 */       if ((this.countdown == 0) && (this.count > 1) && (this.direction != null)) {
/*  50 */         switch (this.direction.getAxis()) {
/*     */         case Y: 
/*  52 */           for (int a = 0; a < 9; a++) if ((a / 3 != 1) || (a % 3 != 1)) {
/*  53 */               ItemFocusPortableHole.createHole(this.worldObj, getPos().add(-1 + a / 3, 0, -1 + a % 3), null, (byte)1, this.countdownmax);
/*     */             }
/*  55 */           break;
/*     */         case Z: 
/*  57 */           for (int a = 0; a < 9; a++) if ((a / 3 != 1) || (a % 3 != 1)) {
/*  58 */               ItemFocusPortableHole.createHole(this.worldObj, getPos().add(-1 + a / 3, -1 + a % 3, 0), null, (byte)1, this.countdownmax);
/*     */             }
/*  60 */           break;
/*     */         case X: 
/*  62 */           for (int a = 0; a < 9; a++) { if ((a / 3 != 1) || (a % 3 != 1)) {
/*  63 */               ItemFocusPortableHole.createHole(this.worldObj, getPos().add(0, -1 + a / 3, -1 + a % 3), null, (byte)1, this.countdownmax);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*  68 */         if (!ItemFocusPortableHole.createHole(this.worldObj, getPos().offset(this.direction.getOpposite()), this.direction, (byte)(this.count - 1), this.countdownmax))
/*     */         {
/*     */ 
/*  71 */           this.count = 0;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  76 */       this.countdown = ((short)(this.countdown + 1));
/*     */       
/*  78 */       if (this.countdown % 20 == 0) { markDirty();
/*     */       }
/*  80 */       if (this.countdown >= this.countdownmax) {
/*  81 */         this.worldObj.setBlockState(getPos(), this.oldblock, 3);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void surroundwithsparkles()
/*     */   {
/*  88 */     for (EnumFacing d1 : ) {
/*  89 */       Block b1 = this.worldObj.getBlockState(getPos().offset(d1)).getBlock();
/*  90 */       if ((b1 != thaumcraft.api.blocks.BlocksTC.hole) && (!b1.isOpaqueCube())) {
/*  91 */         for (EnumFacing d2 : EnumFacing.values()) {
/*  92 */           if ((d1.getAxis() != d2.getAxis()) && (
/*  93 */             (this.worldObj.getBlockState(getPos().offset(d2)).getBlock().isOpaqueCube()) || (this.worldObj.getBlockState(getPos().offset(d1).offset(d2)).getBlock().isOpaqueCube())))
/*     */           {
/*     */ 
/*  96 */             float sx = 0.5F * d1.getFrontOffsetX();
/*  97 */             float sy = 0.5F * d1.getFrontOffsetY();
/*  98 */             float sz = 0.5F * d1.getFrontOffsetZ();
/*     */             
/* 100 */             if (sx == 0.0F) sx = 0.5F * d2.getFrontOffsetX();
/* 101 */             if (sy == 0.0F) sy = 0.5F * d2.getFrontOffsetY();
/* 102 */             if (sz == 0.0F) { sz = 0.5F * d2.getFrontOffsetZ();
/*     */             }
/* 104 */             if (sx == 0.0F) sx = this.worldObj.rand.nextFloat(); else sx += 0.5F;
/* 105 */             if (sy == 0.0F) sy = this.worldObj.rand.nextFloat(); else sy += 0.5F;
/* 106 */             if (sz == 0.0F) sz = this.worldObj.rand.nextFloat(); else { sz += 0.5F;
/*     */             }
/* 108 */             Thaumcraft.proxy.getFX().sparkle(getPos().getX() + sx, getPos().getY() + sy, getPos().getZ() + sz, 2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 120 */     super.readFromNBT(nbttagcompound);
/* 121 */     this.countdown = nbttagcompound.getShort("countdown");
/* 122 */     this.countdownmax = nbttagcompound.getShort("countdownmax");
/* 123 */     this.count = nbttagcompound.getByte("count");
/* 124 */     byte db = nbttagcompound.getByte("direction");
/* 125 */     this.direction = (db >= 0 ? EnumFacing.values()[db] : null);
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 131 */     super.writeToNBT(nbttagcompound);
/* 132 */     nbttagcompound.setShort("countdown", this.countdown);
/* 133 */     nbttagcompound.setShort("countdownmax", this.countdownmax);
/* 134 */     nbttagcompound.setByte("count", this.count);
/* 135 */     nbttagcompound.setByte("direction", this.direction == null ? -1 : (byte)this.direction.ordinal());
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\misc\TileHole.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */