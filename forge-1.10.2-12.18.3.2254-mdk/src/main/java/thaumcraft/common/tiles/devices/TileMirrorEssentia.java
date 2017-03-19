/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IAspectSource;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.common.lib.events.EssentiaHandler;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.lib.utils.Utils;
/*     */ 
/*     */ public class TileMirrorEssentia
/*     */   extends TileThaumcraft
/*     */   implements IAspectSource, ITickable
/*     */ {
/*  25 */   public boolean linked = false;
/*     */   public int linkX;
/*     */   public int linkY;
/*     */   public int linkZ;
/*     */   public int linkDim;
/*  30 */   public EnumFacing linkedFacing = EnumFacing.DOWN;
/*     */   
/*     */   public int instability;
/*     */   
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  36 */     this.linked = nbttagcompound.getBoolean("linked");
/*  37 */     this.linkX = nbttagcompound.getInteger("linkX");
/*  38 */     this.linkY = nbttagcompound.getInteger("linkY");
/*  39 */     this.linkZ = nbttagcompound.getInteger("linkZ");
/*  40 */     this.linkDim = nbttagcompound.getInteger("linkDim");
/*  41 */     this.instability = nbttagcompound.getInteger("instability");
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  47 */     nbttagcompound.setBoolean("linked", this.linked);
/*  48 */     nbttagcompound.setInteger("linkX", this.linkX);
/*  49 */     nbttagcompound.setInteger("linkY", this.linkY);
/*  50 */     nbttagcompound.setInteger("linkZ", this.linkZ);
/*  51 */     nbttagcompound.setInteger("linkDim", this.linkDim);
/*  52 */     nbttagcompound.setInteger("instability", this.instability);
/*     */   }
/*     */   
/*     */   protected void addInstability(World targetWorld, int amt) {
/*  56 */     this.instability += amt;
/*  57 */     markDirty();
/*  58 */     if (targetWorld != null) {
/*  59 */       TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/*  60 */       if ((te != null) && ((te instanceof TileMirrorEssentia))) {
/*  61 */         ((TileMirrorEssentia)te).instability += amt;
/*  62 */         if (((TileMirrorEssentia)te).instability < 0) ((TileMirrorEssentia)te).instability = 0;
/*  63 */         te.markDirty();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void restoreLink() {
/*  69 */     if (isDestinationValid()) {
/*  70 */       World targetWorld = MinecraftServer.getServer().worldServerForDimension(this.linkDim);
/*  71 */       if (targetWorld == null) return;
/*  72 */       TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/*  73 */       if ((te != null) && ((te instanceof TileMirrorEssentia))) {
/*  74 */         TileMirrorEssentia tm = (TileMirrorEssentia)te;
/*  75 */         tm.linked = true;
/*  76 */         tm.linkX = getPos().getX();
/*  77 */         tm.linkY = getPos().getY();
/*  78 */         tm.linkZ = getPos().getZ();
/*  79 */         tm.linkDim = this.worldObj.provider.getDimensionId();
/*  80 */         targetWorld.markBlockForUpdate(tm.getPos());
/*  81 */         this.linkedFacing = BlockStateUtils.getFacing(targetWorld.getBlockState(new BlockPos(this.linkX, this.linkY, this.linkZ)));
/*  82 */         this.linked = true;
/*  83 */         markDirty();
/*  84 */         tm.markDirty();
/*  85 */         this.worldObj.markBlockForUpdate(getPos());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void invalidateLink() {
/*  91 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/*  92 */     if (targetWorld == null) return;
/*  93 */     if (!Utils.isChunkLoaded(targetWorld, this.linkX, this.linkZ)) return;
/*  94 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/*  95 */     if ((te != null) && ((te instanceof TileMirrorEssentia))) {
/*  96 */       TileMirrorEssentia tm = (TileMirrorEssentia)te;
/*  97 */       tm.linked = false;
/*  98 */       tm.linkedFacing = EnumFacing.DOWN;
/*  99 */       markDirty();
/* 100 */       tm.markDirty();
/* 101 */       targetWorld.markBlockForUpdate(new BlockPos(this.linkX, this.linkY, this.linkZ));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isLinkValid()
/*     */   {
/* 107 */     if (!this.linked) return false;
/* 108 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/* 109 */     if (targetWorld == null) {
/* 110 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 115 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 116 */     if ((te == null) || (!(te instanceof TileMirrorEssentia))) {
/* 117 */       this.linked = false;
/* 118 */       markDirty();
/* 119 */       this.worldObj.markBlockForUpdate(getPos());
/* 120 */       return false;
/*     */     }
/* 122 */     TileMirrorEssentia tm = (TileMirrorEssentia)te;
/* 123 */     if (!tm.linked) {
/* 124 */       this.linked = false;
/* 125 */       markDirty();
/* 126 */       this.worldObj.markBlockForUpdate(getPos());
/* 127 */       return false;
/*     */     }
/* 129 */     if ((tm.linkX != getPos().getX()) || (tm.linkY != getPos().getY()) || (tm.linkZ != getPos().getZ()) || (tm.linkDim != this.worldObj.provider.getDimensionId()))
/*     */     {
/*     */ 
/* 132 */       this.linked = false;
/* 133 */       markDirty();
/* 134 */       this.worldObj.markBlockForUpdate(getPos());
/* 135 */       return false;
/*     */     }
/* 137 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isLinkValidSimple() {
/* 141 */     if (!this.linked) return false;
/* 142 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/* 143 */     if (targetWorld == null) {
/* 144 */       return false;
/*     */     }
/*     */     
/* 147 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 148 */     if ((te == null) || (!(te instanceof TileMirrorEssentia))) {
/* 149 */       return false;
/*     */     }
/* 151 */     TileMirrorEssentia tm = (TileMirrorEssentia)te;
/* 152 */     if (!tm.linked) {
/* 153 */       return false;
/*     */     }
/* 155 */     if ((tm.linkX != getPos().getX()) || (tm.linkY != getPos().getY()) || (tm.linkZ != getPos().getZ()) || (tm.linkDim != this.worldObj.provider.getDimensionId()))
/*     */     {
/*     */ 
/* 158 */       return false;
/*     */     }
/* 160 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isDestinationValid() {
/* 164 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/* 165 */     if (targetWorld == null) {
/* 166 */       return false;
/*     */     }
/*     */     
/* 169 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 170 */     if ((te == null) || (!(te instanceof TileMirrorEssentia))) {
/* 171 */       this.linked = false;
/* 172 */       markDirty();
/* 173 */       this.worldObj.markBlockForUpdate(getPos());
/* 174 */       return false;
/*     */     }
/*     */     
/* 177 */     TileMirrorEssentia tm = (TileMirrorEssentia)te;
/* 178 */     if (tm.isLinkValid()) {
/* 179 */       return false;
/*     */     }
/* 181 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AspectList getAspects()
/*     */   {
/* 190 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setAspects(AspectList aspects) {}
/*     */   
/*     */ 
/*     */   public boolean doesContainerAccept(Aspect tag)
/*     */   {
/* 199 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/* 200 */     if ((this.linkedFacing == EnumFacing.DOWN) && 
/* 201 */       (targetWorld != null)) {
/* 202 */       this.linkedFacing = BlockStateUtils.getFacing(targetWorld.getBlockState(new BlockPos(this.linkX, this.linkY, this.linkZ)));
/*     */     }
/*     */     
/*     */ 
/* 206 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 207 */     if ((te != null) && ((te instanceof TileMirrorEssentia))) {
/* 208 */       return EssentiaHandler.canAcceptEssentia(te, tag, this.linkedFacing, 8, true);
/*     */     }
/*     */     
/* 211 */     return true;
/*     */   }
/*     */   
/*     */   public int addToContainer(Aspect tag, int amount)
/*     */   {
/* 216 */     if ((!isLinkValid()) || (amount > 1)) { return amount;
/*     */     }
/* 218 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/* 219 */     if ((this.linkedFacing == EnumFacing.DOWN) && 
/* 220 */       (targetWorld != null)) {
/* 221 */       this.linkedFacing = BlockStateUtils.getFacing(targetWorld.getBlockState(new BlockPos(this.linkX, this.linkY, this.linkZ)));
/*     */     }
/*     */     
/*     */ 
/* 225 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 226 */     if ((te != null) && ((te instanceof TileMirrorEssentia))) {
/* 227 */       boolean b = EssentiaHandler.addEssentia(te, tag, this.linkedFacing, 8, true, 5);
/* 228 */       if (b) addInstability(null, amount);
/* 229 */       return b ? 0 : 1;
/*     */     }
/*     */     
/* 232 */     return amount;
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(Aspect tag, int amount)
/*     */   {
/* 237 */     if ((!isLinkValid()) || (amount > 1)) { return false;
/*     */     }
/* 239 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/* 240 */     if ((this.linkedFacing == EnumFacing.DOWN) && 
/* 241 */       (targetWorld != null)) {
/* 242 */       this.linkedFacing = BlockStateUtils.getFacing(targetWorld.getBlockState(new BlockPos(this.linkX, this.linkY, this.linkZ)));
/*     */     }
/*     */     
/*     */ 
/* 246 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 247 */     if ((te != null) && ((te instanceof TileMirrorEssentia))) {
/* 248 */       boolean b = EssentiaHandler.drainEssentia(te, tag, this.linkedFacing, 8, true, 5);
/* 249 */       if (b) addInstability(null, amount);
/* 250 */       return b;
/*     */     }
/*     */     
/* 253 */     return false;
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(AspectList ot)
/*     */   {
/* 258 */     return false;
/*     */   }
/*     */   
/*     */   public boolean doesContainerContainAmount(Aspect tag, int amount)
/*     */   {
/* 263 */     if ((!isLinkValid()) || (amount > 1)) { return false;
/*     */     }
/* 265 */     World targetWorld = DimensionManager.getWorld(this.linkDim);
/* 266 */     if ((this.linkedFacing == EnumFacing.DOWN) && 
/* 267 */       (targetWorld != null)) {
/* 268 */       this.linkedFacing = BlockStateUtils.getFacing(targetWorld.getBlockState(new BlockPos(this.linkX, this.linkY, this.linkZ)));
/*     */     }
/*     */     
/*     */ 
/* 272 */     TileEntity te = targetWorld.getTileEntity(new BlockPos(this.linkX, this.linkY, this.linkZ));
/* 273 */     if ((te != null) && ((te instanceof TileMirrorEssentia))) {
/* 274 */       return EssentiaHandler.findEssentia(te, tag, this.linkedFacing, 8, true);
/*     */     }
/* 276 */     return false;
/*     */   }
/*     */   
/*     */   public boolean doesContainerContain(AspectList ot)
/*     */   {
/* 281 */     return false;
/*     */   }
/*     */   
/*     */   public int containerContains(Aspect tag)
/*     */   {
/* 286 */     return 0;
/*     */   }
/*     */   
/*     */ 
/* 290 */   int count = 0;
/* 291 */   int inc = 40;
/*     */   
/*     */   public void update()
/*     */   {
/* 295 */     if (!this.worldObj.isRemote)
/*     */     {
/* 297 */       checkInstability();
/*     */       
/* 299 */       if (this.count++ % this.inc == 0) {
/* 300 */         if (!isLinkValidSimple()) {
/* 301 */           if (this.inc < 600) this.inc += 20;
/* 302 */           restoreLink();
/*     */         } else {
/* 304 */           this.inc = 40;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void checkInstability() {
/* 311 */     if (this.instability > 64) {
/* 312 */       AuraHelper.pollute(this.worldObj, this.pos, 1, true);
/* 313 */       this.instability -= 64;
/* 314 */       markDirty();
/*     */     }
/* 316 */     if ((this.instability > 0) && (this.count % 100 == 0)) {
/* 317 */       this.instability -= 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isBlocked()
/*     */   {
/* 323 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileMirrorEssentia.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */