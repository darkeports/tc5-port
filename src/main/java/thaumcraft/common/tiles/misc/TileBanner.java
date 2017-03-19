/*    */ package thaumcraft.common.tiles.misc;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ import thaumcraft.api.blocks.TileThaumcraft;
/*    */ 
/*    */ public class TileBanner
/*    */   extends TileThaumcraft
/*    */ {
/*    */   @SideOnly(Side.CLIENT)
/*    */   public AxisAlignedBB getRenderBoundingBox()
/*    */   {
/* 17 */     return AxisAlignedBB.fromBounds(getPos().getX(), getPos().getY() - 1, getPos().getZ(), getPos().getX() + 1, getPos().getY() + 2, getPos().getZ() + 1);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 22 */   private byte facing = 0;
/* 23 */   private byte color = -1;
/* 24 */   private Aspect aspect = null;
/* 25 */   private boolean onWall = false;
/*    */   
/*    */   public byte getBannerFacing() {
/* 28 */     return this.facing;
/*    */   }
/*    */   
/*    */   public void setBannerFacing(byte face) {
/* 32 */     this.facing = face;
/* 33 */     markDirty();
/*    */   }
/*    */   
/*    */   public boolean getWall() {
/* 37 */     return this.onWall;
/*    */   }
/*    */   
/*    */   public void setWall(boolean b) {
/* 41 */     this.onWall = b;
/* 42 */     markDirty();
/*    */   }
/*    */   
/*    */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*    */   {
/* 47 */     this.facing = nbttagcompound.getByte("facing");
/* 48 */     setColor(nbttagcompound.getByte("color"));
/* 49 */     String as = nbttagcompound.getString("aspect");
/* 50 */     if ((as != null) && (as.length() > 0)) setAspect(Aspect.getAspect(as)); else
/* 51 */       this.aspect = null;
/* 52 */     this.onWall = nbttagcompound.getBoolean("wall");
/*    */   }
/*    */   
/*    */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*    */   {
/* 57 */     nbttagcompound.setByte("facing", this.facing);
/* 58 */     nbttagcompound.setByte("color", getColor());
/* 59 */     nbttagcompound.setString("aspect", getAspect() == null ? "" : getAspect().getTag());
/* 60 */     nbttagcompound.setBoolean("wall", this.onWall);
/*    */   }
/*    */   
/*    */   public Aspect getAspect() {
/* 64 */     return this.aspect;
/*    */   }
/*    */   
/*    */   public void setAspect(Aspect aspect) {
/* 68 */     this.aspect = aspect;
/*    */   }
/*    */   
/*    */   public byte getColor() {
/* 72 */     return this.color;
/*    */   }
/*    */   
/*    */   public void setColor(byte color) {
/* 76 */     this.color = color;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\misc\TileBanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */