/*     */ package thaumcraft.common.tiles.misc;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.blocks.world.taint.ITaintBlock;
/*     */ import thaumcraft.common.lib.utils.TCVec3;
/*     */ 
/*     */ public class TileEtherealBloom extends net.minecraft.tileentity.TileEntity implements ITickable
/*     */ {
/*  15 */   public int counter = 0;
/*  16 */   public int rad; public int rad1 = 0;
/*  17 */   public int growthCounter = 100;
/*     */   public int foundTaint;
/*     */   public static final int bloomsleep = 300;
/*     */   public int sleepcounter;
/*     */   public boolean sleep;
/*     */   
/*     */   public TileEtherealBloom()
/*     */   {
/*  25 */     this.counter = 0;
/*  26 */     this.rad1 = 0;
/*  27 */     this.growthCounter = 100;
/*  28 */     this.foundTaint = 300;
/*  29 */     this.sleepcounter = ('Ĭ' * 4);
/*  30 */     this.sleep = false;
/*     */   }
/*     */   
/*     */   public void resetSleep() {
/*  34 */     this.foundTaint = 300;
/*  35 */     this.sleepcounter = ('Ĭ' * 4);
/*  36 */     this.sleep = false;
/*     */   }
/*     */   
/*     */   public void update()
/*     */   {
/*  41 */     if (this.counter == 0) {
/*  42 */       this.counter = this.worldObj.rand.nextInt(100);
/*  43 */       this.rad = this.counter;
/*     */     }
/*  45 */     this.counter += 1;
/*     */     
/*  47 */     if (this.foundTaint == 0) {
/*  48 */       this.sleepcounter -= 1;
/*  49 */       this.sleep = (this.sleepcounter != 0);
/*  50 */       if (!this.sleep) {
/*  51 */         this.counter = 0;
/*  52 */         this.sleepcounter = ('Ĭ' * 4);
/*     */       }
/*     */     }
/*     */     
/*  56 */     if ((!this.worldObj.isRemote) && (this.counter % 20 == 0) && (!this.sleep)) {
/*  57 */       this.rad = ((int)(this.rad + (5.0D + Math.sqrt(1 + this.rad1) * 5.0D + this.worldObj.rand.nextInt(5))));
/*  58 */       if (this.rad > 360) {
/*  59 */         this.rad -= 360;
/*  60 */         this.rad1 += 5 + this.worldObj.rand.nextInt(5);
/*  61 */         if (this.rad1 > 87) {
/*  62 */           this.rad1 -= 87;
/*     */         }
/*     */       }
/*     */       
/*  66 */       boolean foundsomething = false;
/*     */       
/*  68 */       TCVec3 vsource = TCVec3.createVectorHelper(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D);
/*  69 */       for (int q = 1; q < 8; q++) {
/*  70 */         TCVec3 vtar = TCVec3.createVectorHelper(q, 0.0D, 0.0D);
/*  71 */         vtar.rotateAroundZ(this.rad1 / 180.0F * 3.1415927F);
/*  72 */         vtar.rotateAroundY(this.rad / 180.0F * 3.1415927F);
/*     */         
/*  74 */         TCVec3 vres1 = vsource.addVector(vtar.xCoord, vtar.yCoord, vtar.zCoord);
/*  75 */         TCVec3 vres2 = vsource.addVector(-vtar.xCoord, -vtar.yCoord, -vtar.zCoord);
/*  76 */         BlockPos t1 = new BlockPos(MathHelper.floor_double(vres1.xCoord), MathHelper.floor_double(vres1.yCoord), MathHelper.floor_double(vres1.zCoord));
/*  77 */         while ((this.worldObj.isAirBlock(t1)) && (t1.getY() > 0)) {
/*  78 */           t1 = t1.down();
/*     */         }
/*  80 */         BlockPos t2 = new BlockPos(MathHelper.floor_double(vres2.xCoord), MathHelper.floor_double(vres2.yCoord), MathHelper.floor_double(vres2.zCoord));
/*  81 */         while ((this.worldObj.isAirBlock(t2)) && (t2.getY() > 0)) {
/*  82 */           t2 = t2.down();
/*     */         }
/*     */         
/*  85 */         if (clearBlock(t1)) foundsomething = true;
/*  86 */         if (clearBlock(t2)) { foundsomething = true;
/*     */         }
/*  88 */         if (foundsomething) {
/*  89 */           resetSleep();
/*  90 */           break;
/*     */         }
/*     */       }
/*  93 */       if ((this.foundTaint > 0) && (!foundsomething)) {
/*  94 */         this.foundTaint -= 1;
/*     */       }
/*     */     }
/*     */     
/*  98 */     if ((this.worldObj.isRemote) && 
/*  99 */       (this.growthCounter == 0)) {
/* 100 */       this.worldObj.playSound(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, "thaumcraft:roots", 1.0F, 0.6F, false);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 105 */     this.growthCounter += 1;
/*     */   }
/*     */   
/*     */   private boolean clearBlock(BlockPos p) {
/* 109 */     boolean bc = thaumcraft.common.lib.utils.Utils.resetBiomeAt(this.worldObj, p);
/* 110 */     Block bt = this.worldObj.getBlockState(p).getBlock();
/* 111 */     if ((bt instanceof ITaintBlock)) {
/* 112 */       ((ITaintBlock)bt).die(this.worldObj, p, this.worldObj.getBlockState(p));
/* 113 */       bc = true;
/*     */     }
/* 115 */     return bc;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\misc\TileEtherealBloom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */