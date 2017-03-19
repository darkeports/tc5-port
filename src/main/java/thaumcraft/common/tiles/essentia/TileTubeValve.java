/*     */ package thaumcraft.common.tiles.essentia;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
import thaumcraft.codechicken.libold.raytracer.RayTracer;
/*     */ 
/*     */ public class TileTubeValve extends TileTube
/*     */ {
/*  17 */   public boolean allowFlow = true;
/*  18 */   boolean wasPoweredLastTick = false;
/*     */   
/*  20 */   public float rotation = 0.0F;
/*     */   
/*     */ 
/*     */ 
/*     */   public void update()
/*     */   {
/*  26 */     if ((!this.worldObj.isRemote) && (this.count % 5 == 0)) {
/*  27 */       boolean gettingPower = gettingPower();
/*  28 */       if ((this.wasPoweredLastTick) && (!gettingPower) && 
/*  29 */         (this.allowFlow != true)) {
/*  30 */         this.allowFlow = true;
/*  31 */         this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:squeek", 0.7F, 0.9F + this.worldObj.rand.nextFloat() * 0.2F);
/*  32 */         this.worldObj.markBlockForUpdate(this.pos);
/*  33 */         markDirty();
/*     */       }
/*     */       
/*     */ 
/*  37 */       if ((!this.wasPoweredLastTick) && (gettingPower) && 
/*  38 */         (this.allowFlow)) {
/*  39 */         this.allowFlow = false;
/*  40 */         this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:squeek", 0.7F, 0.9F + this.worldObj.rand.nextFloat() * 0.2F);
/*  41 */         this.worldObj.markBlockForUpdate(this.pos);
/*  42 */         markDirty();
/*     */       }
/*     */       
/*     */ 
/*  46 */       this.wasPoweredLastTick = gettingPower;
/*     */     }
/*     */     
/*  49 */     if (this.worldObj.isRemote) {
/*  50 */       if ((!this.allowFlow) && (this.rotation < 360.0F)) {
/*  51 */         this.rotation += 20.0F;
/*     */       }
/*  53 */       else if ((this.allowFlow) && (this.rotation > 0.0F)) {
/*  54 */         this.rotation -= 20.0F;
/*     */       }
/*     */     }
/*     */     
/*  58 */     super.update();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, BlockPos bp, EnumFacing side)
/*     */   {
/*  65 */     MovingObjectPosition hit = RayTracer.retraceBlock(world, player, this.pos);
/*  66 */     if (hit == null) { return false;
/*     */     }
/*  68 */     if ((hit.subHit >= 0) && (hit.subHit < 6))
/*     */     {
/*  70 */       player.worldObj.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:tool", 0.5F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F, false);
/*  71 */       player.swingItem();
/*  72 */       markDirty();
/*  73 */       world.markBlockForUpdate(this.pos);
/*  74 */       this.openSides[hit.subHit] = (this.openSides[hit.subHit] == 0 ? 1 : false);
/*  75 */       EnumFacing dir = EnumFacing.VALUES[hit.subHit];
/*  76 */       TileEntity tile = this.worldObj.getTileEntity(this.pos.offset(dir));
/*  77 */       if ((tile != null) && ((tile instanceof TileTube))) {
/*  78 */         ((TileTube)tile).openSides[dir.getOpposite().ordinal()] = this.openSides[hit.subHit];
/*  79 */         world.markBlockForUpdate(this.pos.offset(dir));
/*  80 */         tile.markDirty();
/*     */       }
/*     */     }
/*     */     
/*  84 */     if (hit.subHit == 6)
/*     */     {
/*  86 */       player.worldObj.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:tool", 0.5F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F, false);
/*  87 */       player.swingItem();
/*  88 */       int a = this.facing.ordinal();
/*  89 */       markDirty();
/*  90 */       do { a++; if (a >= 20) break;
/*  91 */       } while (canConnectSide(EnumFacing.VALUES[(a % 6)]));
/*  92 */       a %= 6;
/*  93 */       this.facing = EnumFacing.VALUES[a];
/*  94 */       world.markBlockForUpdate(this.pos);
/*  95 */       markDirty();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 102 */     return !world.isRemote;
/*     */   }
/*     */   
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 108 */     super.readCustomNBT(nbttagcompound);
/* 109 */     this.allowFlow = nbttagcompound.getBoolean("flow");
/* 110 */     this.wasPoweredLastTick = nbttagcompound.getBoolean("hadpower");
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 116 */     super.writeCustomNBT(nbttagcompound);
/* 117 */     nbttagcompound.setBoolean("flow", this.allowFlow);
/* 118 */     nbttagcompound.setBoolean("hadpower", this.wasPoweredLastTick);
/*     */   }
/*     */   
/*     */   public boolean isConnectable(EnumFacing face)
/*     */   {
/* 123 */     return (face != this.facing) && (super.isConnectable(face));
/*     */   }
/*     */   
/*     */   public void setSuction(Aspect aspect, int amount)
/*     */   {
/* 128 */     if (this.allowFlow) super.setSuction(aspect, amount);
/*     */   }
/*     */   
/*     */   public boolean gettingPower() {
/* 132 */     return this.worldObj.isBlockIndirectlyGettingPowered(this.pos) > 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\essentia\TileTubeValve.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */