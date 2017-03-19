/*     */ package thaumcraft.common.tiles.essentia;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ import thaumcraft.api.items.ItemGenericEssentiaContainer;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ 
/*     */ public class TileCrystallizer extends TileThaumcraft implements IAspectContainer, IEssentiaTransport, ITickable
/*     */ {
/*  29 */   public Aspect aspect = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  35 */     this.aspect = Aspect.getAspect(nbttagcompound.getString("Aspect"));
/*  36 */     this.fuel = nbttagcompound.getInteger("fuel");
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  42 */     if (this.aspect != null) nbttagcompound.setString("Aspect", this.aspect.getTag());
/*  43 */     nbttagcompound.setInteger("fuel", this.fuel);
/*     */   }
/*     */   
/*     */ 
/*     */   public AspectList getAspects()
/*     */   {
/*  49 */     AspectList al = new AspectList();
/*  50 */     if (this.aspect != null) al.add(this.aspect, 1);
/*  51 */     return al;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setAspects(AspectList aspects) {}
/*     */   
/*     */ 
/*     */   public int addToContainer(Aspect tt, int am)
/*     */   {
/*  60 */     if (am == 0) return am;
/*  61 */     if (this.aspect == null) {
/*  62 */       am--;
/*  63 */       this.aspect = tt;
/*  64 */       this.worldObj.markBlockForUpdate(getPos());
/*  65 */       markDirty();
/*     */     }
/*  67 */     return am;
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(Aspect tt, int am)
/*     */   {
/*  72 */     if ((this.aspect != null) && (am == 1)) {
/*  73 */       this.aspect = null;
/*  74 */       this.worldObj.markBlockForUpdate(getPos());
/*  75 */       markDirty();
/*  76 */       return true;
/*     */     }
/*  78 */     return false;
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(AspectList ot)
/*     */   {
/*  83 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean doesContainerContainAmount(Aspect tag, int amt)
/*     */   {
/*  89 */     if ((amt == 1) && (this.aspect != null) && (tag == this.aspect)) return true;
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   public boolean doesContainerContain(AspectList ot)
/*     */   {
/*  95 */     for (Aspect tt : ot.getAspects()) {
/*  96 */       if ((this.aspect == null) || (this.aspect != tt) || (ot.getAmount(tt) != 1)) return false;
/*     */     }
/*  98 */     return true;
/*     */   }
/*     */   
/*     */   public int containerContains(Aspect tag)
/*     */   {
/* 103 */     return (this.aspect != null) && (tag == this.aspect) ? 1 : 0;
/*     */   }
/*     */   
/*     */   public boolean doesContainerAccept(Aspect tag)
/*     */   {
/* 108 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConnectable(EnumFacing face)
/*     */   {
/* 116 */     return face == getFacing();
/*     */   }
/*     */   
/*     */   public boolean canInputFrom(EnumFacing face)
/*     */   {
/* 121 */     return face == getFacing();
/*     */   }
/*     */   
/*     */   public boolean canOutputTo(EnumFacing face)
/*     */   {
/* 126 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSuction(Aspect aspect, int amount) {}
/*     */   
/*     */ 
/*     */   public int getMinimumSuction()
/*     */   {
/* 135 */     return 0;
/*     */   }
/*     */   
/*     */   public Aspect getSuctionType(EnumFacing loc)
/*     */   {
/* 140 */     return null;
/*     */   }
/*     */   
/*     */   public int getSuctionAmount(EnumFacing loc)
/*     */   {
/* 145 */     return (loc == getFacing()) && (this.aspect == null) ? 128 : gettingPower() ? 0 : 64;
/*     */   }
/*     */   
/*     */   public Aspect getEssentiaType(EnumFacing loc)
/*     */   {
/* 150 */     return this.aspect;
/*     */   }
/*     */   
/*     */   public int getEssentiaAmount(EnumFacing loc)
/*     */   {
/* 155 */     return this.aspect == null ? 0 : 1;
/*     */   }
/*     */   
/*     */   public int takeEssentia(Aspect aspect, int amount, EnumFacing face)
/*     */   {
/* 160 */     return 0;
/*     */   }
/*     */   
/*     */   public int addEssentia(Aspect aspect, int amount, EnumFacing face)
/*     */   {
/* 165 */     return canInputFrom(face) ? amount - addToContainer(aspect, amount) : 0;
/*     */   }
/*     */   
/* 168 */   int count = 0;
/* 169 */   int progress = 0;
/* 170 */   int fuel = 0;
/* 171 */   final int progMax = 200;
/* 172 */   public float spin = 0.0F;
/* 173 */   public float spinInc = 0.0F;
/*     */   
/* 175 */   float tr = 1.0F;
/* 176 */   float tg = 1.0F;
/* 177 */   float tb = 1.0F;
/* 178 */   public float cr = 1.0F;
/* 179 */   public float cg = 1.0F;
/* 180 */   public float cb = 1.0F;
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/* 185 */     if (!this.worldObj.isRemote)
/*     */     {
/* 187 */       if ((++this.count % 5 == 0) && 
/* 188 */         (!gettingPower())) {
/* 189 */         if (this.aspect == null) {
/* 190 */           fillReservoir();
/* 191 */           this.progress = 0;
/*     */         } else {
/* 193 */           if (this.fuel <= 0) {
/* 194 */             this.fuel += AuraHandler.drainAuraAvailable(getWorld(), getPos(), Aspect.EARTH, 4) * 25;
/*     */           }
/* 196 */           if (this.fuel > 0) {
/* 197 */             int q = Math.min(5, Math.min(Math.max(1, (200 - this.progress) / 5), this.fuel));
/* 198 */             this.fuel -= q;
/* 199 */             this.progress += q * 5;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 204 */       if ((this.aspect != null) && (this.progress >= 200)) {
/* 205 */         eject();
/* 206 */         this.aspect = null;
/* 207 */         this.progress = 0;
/* 208 */         this.worldObj.markBlockForUpdate(getPos());
/* 209 */         markDirty();
/*     */       }
/*     */     }
/*     */     else {
/* 213 */       if (this.aspect == null) {
/* 214 */         this.tr = (this.tg = this.tb = 1.0F);
/*     */       } else {
/* 216 */         Color c = new Color(this.aspect.getColor());
/* 217 */         this.tr = (c.getRed() / 220.0F);
/* 218 */         this.tg = (c.getGreen() / 220.0F);
/* 219 */         this.tb = (c.getBlue() / 220.0F);
/*     */       }
/* 221 */       if (this.cr < this.tr) this.cr += 0.05F; if (this.cr > this.tr) this.cr -= 0.05F;
/* 222 */       if (this.cg < this.tg) this.cg += 0.05F; if (this.cg > this.tg) this.cg -= 0.05F;
/* 223 */       if (this.cb < this.tb) this.cb += 0.05F; if (this.cb > this.tb) this.cb -= 0.05F;
/* 224 */       this.spin += this.spinInc;
/* 225 */       if (this.spin > 360.0F) { this.spin -= 360.0F;
/*     */       }
/* 227 */       if ((this.aspect != null) && (this.spinInc < 20.0F) && (!gettingPower())) {
/* 228 */         this.spinInc += 0.1F;
/* 229 */         if (this.spinInc > 20.0F) this.spinInc = 20.0F;
/*     */       }
/* 231 */       else if (((this.aspect == null) || (gettingPower())) && (this.spinInc > 0.0F)) {
/* 232 */         this.spinInc -= 0.2F;
/* 233 */         if (this.spinInc < 0.0F) { this.spinInc = 0.0F;
/*     */         }
/*     */       }
/* 236 */       if (this.venting > 0) {
/* 237 */         this.venting -= 1;
/* 238 */         float fx = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 239 */         float fz = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 240 */         float fy = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 241 */         float fx2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 242 */         float fz2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 243 */         float fy2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 244 */         int color = 16777215;
/* 245 */         Thaumcraft.proxy.getFX().drawVentParticles(getPos().getX() + 0.5F + fx + getFacing().getOpposite().getFrontOffsetX() / 2.1F, getPos().getY() + 0.5F + fy + getFacing().getOpposite().getFrontOffsetY() / 2.1F, getPos().getZ() + 0.5F + fz + getFacing().getOpposite().getFrontOffsetZ() / 2.1F, getFacing().getOpposite().getFrontOffsetX() / 4.0F + fx2, getFacing().getOpposite().getFrontOffsetY() / 4.0F + fy2, getFacing().getOpposite().getFrontOffsetZ() / 4.0F + fz2, color);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 256 */   int venting = 0;
/*     */   
/*     */ 
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/* 261 */     if (i >= 0)
/*     */     {
/* 263 */       if (this.worldObj.isRemote) {
/* 264 */         this.venting = 7;
/*     */       }
/* 266 */       return true;
/*     */     }
/*     */     
/* 269 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */   
/*     */   public void eject()
/*     */   {
/* 274 */     ItemStack stack = new ItemStack(ItemsTC.crystalEssence, 1, 0);
/* 275 */     ((ItemGenericEssentiaContainer)stack.getItem()).setAspects(stack, new AspectList().add(this.aspect, 1));
/* 276 */     TileEntity inventory = this.worldObj.getTileEntity(getPos().offset(getFacing().getOpposite()));
/* 277 */     if ((inventory != null) && ((inventory instanceof IInventory))) {
/* 278 */       stack = InventoryUtils.placeItemStackIntoInventory(stack, (IInventory)inventory, getFacing(), true);
/*     */     }
/*     */     
/* 281 */     if (stack != null) {
/* 282 */       spawnItem(stack);
/*     */     }
/* 284 */     this.worldObj.playSoundEffect(getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D, "random.fizz", 0.25F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean spawnItem(ItemStack stack)
/*     */   {
/* 290 */     EntityItem ie2 = new EntityItem(this.worldObj, getPos().getX() + 0.5D + getFacing().getOpposite().getFrontOffsetX() * 0.65D, getPos().getY() + 0.5D + getFacing().getOpposite().getFrontOffsetY() * 0.65D, getPos().getZ() + 0.5D + getFacing().getOpposite().getFrontOffsetZ() * 0.65D, stack);
/*     */     
/*     */ 
/*     */ 
/* 294 */     ie2.motionX = (getFacing().getOpposite().getFrontOffsetX() * 0.04F);
/* 295 */     ie2.motionY = (getFacing().getOpposite().getFrontOffsetY() * 0.04F);
/* 296 */     ie2.motionZ = (getFacing().getOpposite().getFrontOffsetZ() * 0.04F);
/* 297 */     this.worldObj.addBlockEvent(getPos(), getBlockType(), 0, 0);
/* 298 */     return this.worldObj.spawnEntityInWorld(ie2);
/*     */   }
/*     */   
/*     */   void fillReservoir() {
/* 302 */     TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, getPos(), getFacing());
/* 303 */     if (te != null) {
/* 304 */       IEssentiaTransport ic = (IEssentiaTransport)te;
/* 305 */       if (!ic.canOutputTo(getFacing().getOpposite())) { return;
/*     */       }
/* 307 */       Aspect ta = null;
/*     */       
/* 309 */       if ((ic.getEssentiaAmount(getFacing().getOpposite()) > 0) && 
/* 310 */         (ic.getSuctionAmount(getFacing().getOpposite()) < getSuctionAmount(getFacing())) && (getSuctionAmount(getFacing()) >= ic.getMinimumSuction()))
/*     */       {
/* 312 */         ta = ic.getEssentiaType(getFacing().getOpposite());
/*     */       }
/*     */       
/*     */ 
/* 316 */       if ((ta != null) && (ic.getSuctionAmount(getFacing().getOpposite()) < getSuctionAmount(getFacing()))) {
/* 317 */         addToContainer(ta, ic.takeEssentia(ta, 1, getFacing().getOpposite()));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\essentia\TileCrystallizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */