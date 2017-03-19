/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
import thaumcraft.codechicken.libold.raytracer.IndexedCuboid6;
import thaumcraft.codechicken.libold.vec.Cuboid6;
import thaumcraft.codechicken.libold.vec.Transformation;
import thaumcraft.codechicken.libold.vec.Vector3;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ 
/*     */ 
/*     */ public class TileRedstoneRelay
/*     */   extends TileThaumcraft
/*     */ {
/*  21 */   private int in = 1;
/*  22 */   private int out = 15;
/*     */   
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbt)
/*     */   {
/*  28 */     setIn(nbt.getByte("in"));
/*  29 */     setOut(nbt.getByte("out"));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbt)
/*     */   {
/*  35 */     nbt.setByte("in", (byte)getIn());
/*  36 */     nbt.setByte("out", (byte)getOut());
/*     */   }
/*     */   
/*     */   public void increaseIn() {
/*  40 */     if (!this.worldObj.isRemote) {
/*  41 */       setIn(getIn() + 1);
/*  42 */       if (getIn() > 15) setIn(1);
/*  43 */       markDirty();
/*  44 */       this.worldObj.markBlockForUpdate(getPos());
/*     */     }
/*     */   }
/*     */   
/*     */   public void increaseOut() {
/*  49 */     if (!this.worldObj.isRemote) {
/*  50 */       setOut(getOut() + 1);
/*  51 */       if (getOut() > 15) setOut(1);
/*  52 */       markDirty();
/*  53 */       this.worldObj.markBlockForUpdate(getPos());
/*     */     }
/*     */   }
/*     */   
/*     */   public MovingObjectPosition rayTrace(World world, Vec3 vec3d, Vec3 vec3d1, MovingObjectPosition fullblock)
/*     */   {
/*  59 */     return fullblock;
/*     */   }
/*     */   
/*     */ 
/*     */   public void addTraceableCuboids(List<IndexedCuboid6> cuboids)
/*     */   {
/*  65 */     EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata());
/*     */     
/*  67 */     cuboids.add(new IndexedCuboid6(Integer.valueOf(0), getCuboid0(facing)));
/*  68 */     cuboids.add(new IndexedCuboid6(Integer.valueOf(1), getCuboid1(facing)));
/*     */   }
/*     */   
/*     */   public Cuboid6 getCuboid0(EnumFacing facing) {
/*  72 */     Transformation rot = thaumcraft.codechicken.libold.vec.Rotation.quarterRotations[0];
/*  73 */     switch (facing) {
/*  74 */     case WEST:  rot = thaumcraft.codechicken.libold.vec.Rotation.quarterRotations[1]; break;
/*  75 */     case NORTH:  rot = thaumcraft.codechicken.libold.vec.Rotation.quarterRotations[2]; break;
/*  76 */     case EAST:  rot = thaumcraft.codechicken.libold.vec.Rotation.quarterRotations[3];
/*     */     }
/*  78 */     return new Cuboid6(-0.4375D, 0.125D, -0.4375D, -0.0625D, 0.3125D, -0.0625D).apply(rot).add(new Vector3(getPos().getX() + 0.5D, getPos().getY(), getPos().getZ() + 0.5D));
/*     */   }
/*     */   
/*     */   public Cuboid6 getCuboid1(EnumFacing facing)
/*     */   {
/*  83 */     Transformation rot = thaumcraft.codechicken.libold.vec.Rotation.quarterRotations[0];
/*  84 */     switch (facing) {
/*  85 */     case WEST:  rot = thaumcraft.codechicken.libold.vec.Rotation.quarterRotations[1]; break;
/*  86 */     case NORTH:  rot = thaumcraft.codechicken.libold.vec.Rotation.quarterRotations[2]; break;
/*  87 */     case EAST:  rot = thaumcraft.codechicken.libold.vec.Rotation.quarterRotations[3];
/*     */     }
/*  89 */     return new Cuboid6(-0.1875D, 0.125D, 0.0625D, 0.1875D, 0.3125D, 0.4375D).apply(rot).add(new Vector3(getPos().getX() + 0.5D, getPos().getY(), getPos().getZ() + 0.5D));
/*     */   }
/*     */   
/*     */   public int getOut()
/*     */   {
/*  94 */     return this.out;
/*     */   }
/*     */   
/*     */   public void setOut(int out) {
/*  98 */     this.out = out;
/*     */   }
/*     */   
/*     */   public int getIn() {
/* 102 */     return this.in;
/*     */   }
/*     */   
/*     */   public void setIn(int in) {
/* 106 */     this.in = in;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileRedstoneRelay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */