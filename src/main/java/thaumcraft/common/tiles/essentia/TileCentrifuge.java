/*     */ package thaumcraft.common.tiles.essentia;
/*     */ 
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.ThaumcraftApiHelper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IAspectContainer;
/*     */ import thaumcraft.api.aspects.IEssentiaTransport;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ 
/*     */ public class TileCentrifuge
/*     */   extends TileThaumcraft
/*     */   implements IAspectContainer, IEssentiaTransport, ITickable
/*     */ {
/*  20 */   public Aspect aspectOut = null;
/*  21 */   public Aspect aspectIn = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  27 */     this.aspectIn = Aspect.getAspect(nbttagcompound.getString("aspectIn"));
/*  28 */     this.aspectOut = Aspect.getAspect(nbttagcompound.getString("aspectOut"));
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  34 */     if (this.aspectIn != null) nbttagcompound.setString("aspectIn", this.aspectIn.getTag());
/*  35 */     if (this.aspectOut != null) { nbttagcompound.setString("aspectOut", this.aspectOut.getTag());
/*     */     }
/*     */   }
/*     */   
/*     */   public AspectList getAspects()
/*     */   {
/*  41 */     AspectList al = new AspectList();
/*  42 */     if (this.aspectOut != null) al.add(this.aspectOut, 1);
/*  43 */     return al;
/*     */   }
/*     */   
/*     */ 
/*     */   public int addToContainer(Aspect tt, int am)
/*     */   {
/*  49 */     if ((am > 0) && (this.aspectOut == null)) {
/*  50 */       this.aspectOut = tt;
/*  51 */       markDirty();
/*  52 */       this.worldObj.markBlockForUpdate(getPos());
/*  53 */       am--;
/*     */     }
/*  55 */     return am;
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(Aspect tt, int am)
/*     */   {
/*  60 */     if ((this.aspectOut != null) && (tt == this.aspectOut)) {
/*  61 */       this.aspectOut = null;
/*  62 */       markDirty();
/*  63 */       this.worldObj.markBlockForUpdate(getPos());
/*  64 */       return true;
/*     */     }
/*  66 */     return false;
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(AspectList ot)
/*     */   {
/*  71 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean doesContainerContainAmount(Aspect tag, int amt)
/*     */   {
/*  77 */     if ((amt == 1) && (tag == this.aspectOut)) return true;
/*  78 */     return false;
/*     */   }
/*     */   
/*     */   public boolean doesContainerContain(AspectList ot)
/*     */   {
/*  83 */     for (Aspect tt : ot.getAspects()) {
/*  84 */       if (tt == this.aspectOut) return true;
/*     */     }
/*  86 */     return false;
/*     */   }
/*     */   
/*     */   public int containerContains(Aspect tag)
/*     */   {
/*  91 */     return tag == this.aspectOut ? 1 : 0;
/*     */   }
/*     */   
/*     */   public boolean doesContainerAccept(Aspect tag)
/*     */   {
/*  96 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isConnectable(EnumFacing face)
/*     */   {
/* 103 */     return (face == EnumFacing.UP) || (face == EnumFacing.DOWN);
/*     */   }
/*     */   
/*     */   public boolean canInputFrom(EnumFacing face)
/*     */   {
/* 108 */     return face == EnumFacing.DOWN;
/*     */   }
/*     */   
/*     */   public boolean canOutputTo(EnumFacing face)
/*     */   {
/* 113 */     return face == EnumFacing.UP;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSuction(Aspect aspect, int amount) {}
/*     */   
/*     */ 
/*     */   public int getMinimumSuction()
/*     */   {
/* 122 */     return 0;
/*     */   }
/*     */   
/*     */   public Aspect getSuctionType(EnumFacing face)
/*     */   {
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   public int getSuctionAmount(EnumFacing face)
/*     */   {
/* 132 */     return face == EnumFacing.DOWN ? 64 : this.aspectIn == null ? 128 : gettingPower() ? 0 : 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public Aspect getEssentiaType(EnumFacing loc)
/*     */   {
/* 138 */     return this.aspectOut;
/*     */   }
/*     */   
/*     */   public int getEssentiaAmount(EnumFacing loc)
/*     */   {
/* 143 */     return this.aspectOut != null ? 1 : 0;
/*     */   }
/*     */   
/*     */   public int takeEssentia(Aspect aspect, int amount, EnumFacing face)
/*     */   {
/* 148 */     return (canOutputTo(face)) && (takeFromContainer(aspect, amount)) ? amount : 0;
/*     */   }
/*     */   
/*     */   public int addEssentia(Aspect aspect, int amount, EnumFacing face)
/*     */   {
/* 153 */     if ((this.aspectIn == null) && (!aspect.isPrimal())) {
/* 154 */       this.aspectIn = aspect;
/* 155 */       this.process = 39;
/* 156 */       markDirty();
/* 157 */       this.worldObj.markBlockForUpdate(getPos());
/* 158 */       return 1;
/*     */     }
/* 160 */     return 0;
/*     */   }
/*     */   
/* 163 */   int count = 0;
/* 164 */   int process = 0;
/* 165 */   float rotationSpeed = 0.0F;
/* 166 */   public float rotation = 0.0F;
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/* 171 */     if (!this.worldObj.isRemote) {
/* 172 */       if (!gettingPower()) {
/* 173 */         if ((this.aspectOut == null) && (this.aspectIn == null) && (++this.count % 5 == 0)) {
/* 174 */           drawEssentia();
/*     */         }
/* 176 */         if (this.process > 0) this.process -= 1;
/* 177 */         if ((this.aspectOut == null) && (this.aspectIn != null) && (this.process == 0)) {
/* 178 */           processEssentia();
/*     */         }
/*     */       }
/*     */     } else {
/* 182 */       if ((this.aspectIn != null) && (!gettingPower()) && (this.rotationSpeed < 20.0F)) this.rotationSpeed += 2.0F;
/* 183 */       if (((this.aspectIn == null) || (gettingPower())) && (this.rotationSpeed > 0.0F)) this.rotationSpeed -= 0.5F;
/* 184 */       int pr = (int)this.rotation;
/* 185 */       this.rotation += this.rotationSpeed;
/* 186 */       if ((this.rotation % 180.0F <= 20.0F) && (pr % 180 >= 160) && (this.rotationSpeed > 0.0F)) {
/* 187 */         this.worldObj.playSound(getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D, "thaumcraft:pump", 1.0F, 1.0F, false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void processEssentia() {
/* 193 */     Aspect[] comps = this.aspectIn.getComponents();
/* 194 */     this.aspectOut = comps[this.worldObj.rand.nextInt(2)];
/* 195 */     this.aspectIn = null;
/* 196 */     markDirty();
/* 197 */     this.worldObj.markBlockForUpdate(getPos());
/*     */   }
/*     */   
/*     */   void drawEssentia() {
/* 201 */     TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, getPos(), EnumFacing.DOWN);
/* 202 */     if (te != null) {
/* 203 */       IEssentiaTransport ic = (IEssentiaTransport)te;
/* 204 */       if (!ic.canOutputTo(EnumFacing.UP)) return;
/* 205 */       Aspect ta = null;
/* 206 */       if ((ic.getEssentiaAmount(EnumFacing.UP) > 0) && 
/* 207 */         (ic.getSuctionAmount(EnumFacing.UP) < getSuctionAmount(EnumFacing.DOWN)) && (getSuctionAmount(EnumFacing.DOWN) >= ic.getMinimumSuction()))
/*     */       {
/* 209 */         ta = ic.getEssentiaType(EnumFacing.UP);
/*     */       }
/*     */       
/*     */ 
/* 213 */       if ((ta != null) && (!ta.isPrimal()) && (ic.getSuctionAmount(EnumFacing.UP) < getSuctionAmount(EnumFacing.DOWN)) && 
/* 214 */         (ic.takeEssentia(ta, 1, EnumFacing.UP) == 1)) {
/* 215 */         this.aspectIn = ta;
/* 216 */         this.process = 39;
/* 217 */         markDirty();
/* 218 */         this.worldObj.markBlockForUpdate(getPos());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setAspects(AspectList aspects) {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\essentia\TileCentrifuge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */