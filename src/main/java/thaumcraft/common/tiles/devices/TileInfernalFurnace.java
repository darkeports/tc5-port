/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.ThaumcraftApi;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.tiles.TileThaumcraftInventory;
/*     */ 
/*     */ public class TileInfernalFurnace extends TileThaumcraftInventory implements ITickable
/*     */ {
/*     */   public int furnaceCookTime;
/*     */   public int furnaceMaxCookTime;
/*     */   public int speedyTime;
/*     */   
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/*  31 */     return AxisAlignedBB.fromBounds(getPos().getX() - 1.3D, getPos().getY() - 1.3D, getPos().getZ() - 1.3D, getPos().getX() + 2.3D, getPos().getY() + 2.3D, getPos().getZ() + 2.3D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TileInfernalFurnace()
/*     */   {
/*  42 */     this.furnaceCookTime = 0;
/*  43 */     this.furnaceMaxCookTime = 0;
/*  44 */     this.speedyTime = 0;
/*  45 */     this.itemStacks = new ItemStack[32];
/*     */   }
/*     */   
/*     */ 
/*     */   public int[] getSlotsForFace(EnumFacing par1)
/*     */   {
/*  51 */     return par1 == EnumFacing.UP ? new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 } : new int[0];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canExtractItem(int par1, ItemStack par2ItemStack, EnumFacing par3)
/*     */   {
/*  59 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  65 */     super.readFromNBT(nbttagcompound);
/*  66 */     this.furnaceCookTime = nbttagcompound.getShort("CookTime");
/*  67 */     this.speedyTime = nbttagcompound.getShort("SpeedyTime");
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  72 */     super.writeToNBT(nbttagcompound);
/*  73 */     nbttagcompound.setShort("CookTime", (short)this.furnaceCookTime);
/*  74 */     nbttagcompound.setShort("SpeedyTime", (short)this.speedyTime);
/*     */   }
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/*  80 */     if (this.facingX == -5) {
/*  81 */       setFacing();
/*     */     }
/*     */     
/*  84 */     if (!this.worldObj.isRemote)
/*     */     {
/*  86 */       boolean cookedflag = false;
/*     */       
/*  88 */       if (this.furnaceCookTime > 0) {
/*  89 */         this.furnaceCookTime -= 1;
/*  90 */         cookedflag = true;
/*     */       }
/*     */       
/*  93 */       if (this.furnaceMaxCookTime == 0) {
/*  94 */         this.furnaceMaxCookTime = calcCookTime();
/*     */       }
/*     */       
/*  97 */       if (this.furnaceCookTime > this.furnaceMaxCookTime) {
/*  98 */         this.furnaceCookTime = this.furnaceMaxCookTime;
/*     */       }
/*     */       
/*     */ 
/* 102 */       if ((this.furnaceCookTime == 0) && (cookedflag)) {
/* 103 */         for (int a = 0; a < getSizeInventory(); a++) {
/* 104 */           if (this.itemStacks[a] != null) {
/* 105 */             ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.itemStacks[a]);
/* 106 */             if (itemstack != null)
/*     */             {
/* 108 */               if (this.speedyTime > 0) {
/* 109 */                 this.speedyTime -= 1;
/*     */               }
/* 111 */               ejectItem(itemstack.copy(), this.itemStacks[a]);
/* 112 */               this.worldObj.addBlockEvent(getPos(), thaumcraft.api.blocks.BlocksTC.infernalFurnace, 3, 0);
/* 113 */               if (getWorld().rand.nextInt(12) == 0)
/* 114 */                 AuraHelper.pollute(getWorld(), getPos().offset(getFacing().getOpposite()), 1, true);
/* 115 */               this.itemStacks[a].stackSize -= 1;
/* 116 */               if (this.itemStacks[a].stackSize > 0) break;
/* 117 */               this.itemStacks[a] = null; break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 124 */       if (this.speedyTime <= 0) {
/* 125 */         this.speedyTime = (AuraHandler.drainAuraAvailable(getWorld(), getPos(), thaumcraft.api.aspects.Aspect.FIRE, 5) * 5);
/*     */       }
/*     */       
/*     */ 
/* 129 */       if ((this.furnaceCookTime == 0) && (!cookedflag)) {
/* 130 */         for (int a = 0; a < getSizeInventory(); a++) {
/* 131 */           if ((this.itemStacks[a] != null) && (canSmelt(a))) {
/* 132 */             this.furnaceMaxCookTime = calcCookTime();
/* 133 */             this.furnaceCookTime = this.furnaceMaxCookTime;
/* 134 */             break;
/*     */           }
/* 136 */           if ((this.itemStacks[a] != null) && (!canSmelt(a))) {
/* 137 */             destroyItem(a);
/* 138 */             markDirty();
/* 139 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private int getBellows()
/*     */   {
/* 148 */     int bellows = 0;
/* 149 */     for (EnumFacing dir : EnumFacing.VALUES) {
/* 150 */       if (dir != EnumFacing.UP) {
/* 151 */         BlockPos p2 = this.pos.offset(dir, 2);
/* 152 */         net.minecraft.tileentity.TileEntity tile = this.worldObj.getTileEntity(p2);
/* 153 */         if ((tile != null) && ((tile instanceof TileBellows)) && (thaumcraft.common.lib.utils.BlockStateUtils.getFacing(this.worldObj.getBlockState(p2)) == dir.getOpposite()) && (this.worldObj.isBlockIndirectlyGettingPowered(p2) == 0))
/*     */         {
/*     */ 
/* 156 */           bellows++; }
/*     */       }
/*     */     }
/* 159 */     return Math.min(3, bellows);
/*     */   }
/*     */   
/*     */   private int calcCookTime() {
/* 163 */     return (this.speedyTime > 0 ? 80 : 140) - 20 * getBellows();
/*     */   }
/*     */   
/*     */   public boolean addItemsToInventory(ItemStack items)
/*     */   {
/* 168 */     for (int a = 0; a < getSizeInventory(); a++) {
/* 169 */       if ((this.itemStacks[a] != null) && (this.itemStacks[a].isItemEqual(items)) && (this.itemStacks[a].stackSize + items.stackSize <= items.getMaxStackSize()))
/*     */       {
/*     */ 
/* 172 */         this.itemStacks[a].stackSize += items.stackSize;
/* 173 */         if (!canSmelt(a)) {
/* 174 */           destroyItem(a);
/*     */         }
/* 176 */         markDirty();
/* 177 */         return true;
/*     */       }
/* 179 */       if (this.itemStacks[a] == null) {
/* 180 */         setInventorySlotContents(a, items);
/* 181 */         if (!canSmelt(a)) {
/* 182 */           destroyItem(a);
/*     */         }
/* 184 */         markDirty();
/* 185 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 189 */     return false;
/*     */   }
/*     */   
/*     */   private void destroyItem(int slot) {
/* 193 */     this.itemStacks[slot] = null;
/*     */     
/* 195 */     this.worldObj.playSound(this.pos.getX() + 0.5F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.5F, "random.fizz", 0.3F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F, false);
/*     */     
/* 197 */     double var21 = this.pos.getX() + this.worldObj.rand.nextFloat();
/* 198 */     double var22 = this.pos.getY() + 1;
/* 199 */     double var23 = this.pos.getZ() + this.worldObj.rand.nextFloat();
/* 200 */     this.worldObj.spawnParticle(EnumParticleTypes.LAVA, var21, var22, var23, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */   }
/*     */   
/* 203 */   public int facingX = -5;
/* 204 */   public int facingZ = -5;
/*     */   
/*     */ 
/*     */   public void ejectItem(ItemStack items, ItemStack furnaceItemStack)
/*     */   {
/* 209 */     if (items == null) return;
/* 210 */     ItemStack bit = items.copy();
/* 211 */     int bellows = getBellows();
/*     */     
/* 213 */     float lx = 0.5F;lx += this.facingX * 1.2F;
/* 214 */     float lz = 0.5F;lz += this.facingZ * 1.2F;
/* 215 */     float mx = this.facingX == 0 ? (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.03F : this.facingX * 0.13F;
/* 216 */     float mz = this.facingZ == 0 ? (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.03F : this.facingZ * 0.13F;
/*     */     
/* 218 */     EntityItem entityitem = new EntityItem(this.worldObj, this.pos.getX() + lx, this.pos.getY() + 0.4F, this.pos.getZ() + lz, items);
/*     */     
/*     */ 
/*     */ 
/* 222 */     entityitem.motionX = mx;
/* 223 */     entityitem.motionZ = mz;
/* 224 */     entityitem.motionY = 0.0D;
/* 225 */     this.worldObj.spawnEntityInWorld(entityitem);
/*     */     
/* 227 */     if (ThaumcraftApi.getSmeltingBonus(furnaceItemStack) != null) {
/* 228 */       ItemStack bonus = ThaumcraftApi.getSmeltingBonus(furnaceItemStack).copy();
/* 229 */       if (bonus != null) {
/* 230 */         if (bellows == 0) {
/* 231 */           if (this.worldObj.rand.nextInt(4) == 0) bonus.stackSize += 1;
/*     */         } else {
/* 233 */           for (int a = 0; a < bellows; a++) if (this.worldObj.rand.nextFloat() < 0.44F) { bonus.stackSize += 1;
/*     */             }
/*     */         }
/*     */       }
/* 237 */       if ((bonus != null) && (bonus.stackSize > 0)) {
/* 238 */         mx = this.facingX == 0 ? (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.03F : this.facingX * 0.13F;
/* 239 */         mz = this.facingZ == 0 ? (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.03F : this.facingZ * 0.13F;
/* 240 */         EntityItem entityitem2 = new EntityItem(this.worldObj, this.pos.getX() + lx, this.pos.getY() + 0.4F, this.pos.getZ() + lz, bonus);
/*     */         
/*     */ 
/*     */ 
/* 244 */         entityitem2.motionX = mx;
/* 245 */         entityitem2.motionZ = mz;
/* 246 */         entityitem2.motionY = 0.0D;
/* 247 */         this.worldObj.spawnEntityInWorld(entityitem2);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 252 */     int var2 = items.stackSize;
/* 253 */     float var3 = FurnaceRecipes.instance().getSmeltingExperience(bit);
/*     */     
/*     */ 
/* 256 */     if (var3 == 0.0F)
/*     */     {
/* 258 */       var2 = 0;
/*     */     }
/* 260 */     else if (var3 < 1.0F)
/*     */     {
/* 262 */       int var4 = MathHelper.floor_float(var2 * var3);
/*     */       
/* 264 */       if ((var4 < MathHelper.ceiling_float_int(var2 * var3)) && ((float)Math.random() < var2 * var3 - var4))
/*     */       {
/* 266 */         var4++;
/*     */       }
/*     */       
/* 269 */       var2 = var4;
/*     */     }
/*     */     
/* 272 */     while (var2 > 0)
/*     */     {
/* 274 */       int var4 = EntityXPOrb.getXPSplit(var2);
/* 275 */       var2 -= var4;
/* 276 */       EntityXPOrb xp = new EntityXPOrb(this.worldObj, this.pos.getX() + lx, this.pos.getY() + 0.4F, this.pos.getZ() + lz, var4);
/*     */       
/*     */ 
/*     */ 
/* 280 */       mx = this.facingX == 0 ? (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.025F : this.facingX * 0.13F;
/* 281 */       mz = this.facingZ == 0 ? (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.025F : this.facingZ * 0.13F;
/* 282 */       xp.motionX = mx;
/* 283 */       xp.motionZ = mz;
/* 284 */       xp.motionY = 0.0D;
/* 285 */       this.worldObj.spawnEntityInWorld(xp);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean canSmelt(int slotIn)
/*     */   {
/* 292 */     if (this.itemStacks[slotIn] == null)
/*     */     {
/* 294 */       return false;
/*     */     }
/*     */     
/* 297 */     ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.itemStacks[slotIn]);
/* 298 */     if (itemstack == null)
/*     */     {
/* 300 */       return false;
/*     */     }
/*     */     
/* 303 */     return true;
/*     */   }
/*     */   
/*     */   private void setFacing() {
/* 307 */     this.facingX = 0;
/* 308 */     this.facingZ = 0;
/* 309 */     EnumFacing face = getFacing().getOpposite();
/* 310 */     this.facingX = face.getFrontOffsetX();
/* 311 */     this.facingZ = face.getFrontOffsetZ();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/* 318 */     if (i == 3)
/*     */     {
/* 320 */       if (this.worldObj.isRemote) {
/* 321 */         for (int a = 0; a < 5; a++) {
/* 322 */           Thaumcraft.proxy.getFX().furnaceLavaFx(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.facingX, this.facingZ);
/* 323 */           this.worldObj.playSound(this.pos.getX() + 0.5F, this.pos.getY() + 0.5F, this.pos.getZ() + 0.5F, "liquid.lavapop", 0.1F + this.worldObj.rand.nextFloat() * 0.1F, 0.9F + this.worldObj.rand.nextFloat() * 0.15F, false);
/*     */         }
/*     */       }
/*     */       
/* 327 */       return true;
/*     */     }
/*     */     
/* 330 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileInfernalFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */