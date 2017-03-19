/*     */ package thaumcraft.common.tiles.crafting;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.blocks.devices.BlockSmelter;
/*     */ import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.tiles.TileThaumcraftInventory;
/*     */ 
/*     */ public class TileSmelter extends TileThaumcraftInventory implements ITickable
/*     */ {
/*  29 */   private static final int[] slots_bottom = { 1 };
/*  30 */   private static final int[] slots_top = new int[0];
/*  31 */   private static final int[] slots_sides = { 0 };
/*     */   
/*  33 */   public AspectList aspects = new AspectList();
/*     */   public int vis;
/*  35 */   private int maxVis = 50;
/*  36 */   public int smeltTime = 100;
/*  37 */   boolean speedBoost = false;
/*     */   public int furnaceBurnTime;
/*     */   public int currentItemBurnTime;
/*     */   public int furnaceCookTime;
/*  41 */   int count = 0;
/*     */   
/*     */   public TileSmelter()
/*     */   {
/*  45 */     this.itemStacks = new ItemStack[2];
/*     */   }
/*     */   
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  50 */     super.readCustomNBT(nbttagcompound);
/*  51 */     this.furnaceBurnTime = nbttagcompound.getShort("BurnTime");
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  56 */     super.writeCustomNBT(nbttagcompound);
/*  57 */     nbttagcompound.setShort("BurnTime", (short)this.furnaceBurnTime);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound nbtCompound)
/*     */   {
/*  66 */     super.readFromNBT(nbtCompound);
/*  67 */     this.speedBoost = nbtCompound.getBoolean("speedBoost");
/*  68 */     this.furnaceCookTime = nbtCompound.getShort("CookTime");
/*  69 */     this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.itemStacks[1]);
/*  70 */     this.aspects.readFromNBT(nbtCompound);
/*  71 */     this.vis = this.aspects.visSize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound nbtCompound)
/*     */   {
/*  80 */     super.writeToNBT(nbtCompound);
/*  81 */     nbtCompound.setBoolean("speedBoost", this.speedBoost);
/*  82 */     nbtCompound.setShort("CookTime", (short)this.furnaceCookTime);
/*  83 */     this.aspects.writeToNBT(nbtCompound);
/*     */   }
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/*  89 */     boolean flag = this.furnaceBurnTime > 0;
/*  90 */     boolean flag1 = false;
/*  91 */     this.count += 1;
/*  92 */     if (this.furnaceBurnTime > 0)
/*     */     {
/*  94 */       this.furnaceBurnTime -= 1;
/*     */     }
/*     */     
/*  97 */     if ((this.worldObj != null) && (!this.worldObj.isRemote))
/*     */     {
/*  99 */       if (this.bellows < 0) { checkNeighbours();
/*     */       }
/* 101 */       int speed = getSpeed();
/* 102 */       if (this.speedBoost) { speed = (int)(speed * 0.8D);
/*     */       }
/* 104 */       if ((this.count % speed == 0) && (this.aspects.size() > 0))
/*     */       {
/* 106 */         for (Aspect aspect : this.aspects.getAspects()) {
/* 107 */           if ((this.aspects.getAmount(aspect) > 0) && (TileAlembic.processAlembics(getWorld(), getPos(), aspect))) {
/* 108 */             takeFromContainer(aspect, 1);
/* 109 */             break;
/*     */           }
/*     */         }
/*     */         
/* 113 */         for (EnumFacing face : EnumFacing.HORIZONTALS) {
/* 114 */           IBlockState aux = this.worldObj.getBlockState(getPos().offset(face));
/* 115 */           if ((aux.getBlock() == BlocksTC.smelterAux) && (BlockStateUtils.getFacing(aux) == face.getOpposite())) {
/* 116 */             for (Aspect aspect : this.aspects.getAspects()) {
/* 117 */               if ((this.aspects.getAmount(aspect) > 0) && (TileAlembic.processAlembics(getWorld(), getPos().offset(face), aspect))) {
/* 118 */                 takeFromContainer(aspect, 1);
/* 119 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 127 */       if (this.furnaceBurnTime == 0)
/*     */       {
/* 129 */         if (canSmelt()) {
/* 130 */           this.currentItemBurnTime = (this.furnaceBurnTime = TileEntityFurnace.getItemBurnTime(this.itemStacks[1]));
/* 131 */           if (this.furnaceBurnTime > 0)
/*     */           {
/* 133 */             BlockSmelter.setFurnaceState(this.worldObj, getPos(), true);
/*     */             
/* 135 */             flag1 = true;
/* 136 */             this.speedBoost = false;
/*     */             
/* 138 */             if (this.itemStacks[1] != null)
/*     */             {
/* 140 */               if (this.itemStacks[1].isItemEqual(new ItemStack(ItemsTC.alumentum))) {
/* 141 */                 this.speedBoost = true;
/*     */               }
/* 143 */               this.itemStacks[1].stackSize -= 1;
/*     */               
/* 145 */               if (this.itemStacks[1].stackSize == 0)
/*     */               {
/* 147 */                 this.itemStacks[1] = this.itemStacks[1].getItem().getContainerItem(this.itemStacks[1]);
/*     */               }
/*     */             }
/*     */           } else {
/* 151 */             BlockSmelter.setFurnaceState(this.worldObj, getPos(), false);
/*     */           }
/*     */         } else {
/* 154 */           BlockSmelter.setFurnaceState(this.worldObj, getPos(), false);
/*     */         }
/*     */       }
/*     */       
/* 158 */       if ((BlockStateUtils.isEnabled(getBlockMetadata())) && (canSmelt()))
/*     */       {
/* 160 */         this.furnaceCookTime += 1;
/*     */         
/* 162 */         if (this.furnaceCookTime >= this.smeltTime)
/*     */         {
/* 164 */           this.furnaceCookTime = 0;
/*     */           
/* 166 */           smeltItem();
/* 167 */           flag1 = true;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 172 */         this.furnaceCookTime = 0;
/*     */       }
/*     */       
/* 175 */       if (flag != this.furnaceBurnTime > 0)
/*     */       {
/* 177 */         flag1 = true;
/*     */       }
/*     */     }
/*     */     
/* 181 */     if (flag1)
/*     */     {
/* 183 */       markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean canSmelt()
/*     */   {
/* 192 */     if (this.itemStacks[0] == null)
/*     */     {
/* 194 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 198 */     AspectList al = ThaumcraftCraftingManager.getObjectTags(this.itemStacks[0]);
/*     */     
/* 200 */     if ((al == null) || (al.size() == 0)) return false;
/* 201 */     int vs = al.visSize();
/* 202 */     if (vs > this.maxVis - this.vis) return false;
/* 203 */     this.smeltTime = ((int)(vs * 10 * (1.0F - 0.125F * this.bellows)));
/* 204 */     return true;
/*     */   }
/*     */   
/*     */ 
/* 208 */   int bellows = -1;
/*     */   
/*     */   public void checkNeighbours() {
/* 211 */     EnumFacing[] faces = EnumFacing.HORIZONTALS;
/*     */     try {
/* 213 */       if (BlockStateUtils.getFacing(getBlockMetadata()) == EnumFacing.NORTH) faces = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST };
/* 214 */       if (BlockStateUtils.getFacing(getBlockMetadata()) == EnumFacing.SOUTH) faces = new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST };
/* 215 */       if (BlockStateUtils.getFacing(getBlockMetadata()) == EnumFacing.EAST) faces = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.WEST };
/* 216 */       if (BlockStateUtils.getFacing(getBlockMetadata()) == EnumFacing.WEST) faces = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.NORTH };
/*     */     } catch (Exception e) {}
/* 218 */     this.bellows = thaumcraft.common.tiles.devices.TileBellows.getBellows(this.worldObj, this.pos, faces);
/*     */   }
/*     */   
/*     */   private int getType()
/*     */   {
/* 223 */     return getBlockType() == BlocksTC.smelterThaumium ? 1 : getBlockType() == BlocksTC.smelterBasic ? 0 : 2;
/*     */   }
/*     */   
/*     */   private float getEfficiency() {
/* 227 */     float efficiency = 0.8F;
/* 228 */     if (getType() == 1) efficiency = 0.85F;
/* 229 */     if (getType() == 2) efficiency = 0.95F;
/* 230 */     return efficiency;
/*     */   }
/*     */   
/*     */   private int getSpeed() {
/* 234 */     int speed = 50 - (getType() == 1 ? 25 : 10);
/* 235 */     return speed;
/*     */   }
/*     */   
/*     */   public void smeltItem()
/*     */   {
/* 240 */     if (canSmelt())
/*     */     {
/* 242 */       int flux = 0;
/* 243 */       AspectList al = ThaumcraftCraftingManager.getObjectTags(this.itemStacks[0]);
/*     */       
/* 245 */       for (Aspect a : al.getAspects()) {
/* 246 */         if (getEfficiency() < 1.0F) {
/* 247 */           int qq = al.getAmount(a);
/* 248 */           for (int q = 0; q < qq; q++) {
/* 249 */             if (this.worldObj.rand.nextFloat() > (a == Aspect.FLUX ? getEfficiency() * 0.66F : getEfficiency())) {
/* 250 */               al.reduce(a, 1);
/* 251 */               flux++;
/*     */             }
/*     */           }
/*     */         }
/* 255 */         this.aspects.add(a, al.getAmount(a));
/*     */       }
/*     */       
/*     */ 
/* 259 */       if (flux > 0) {
/* 260 */         int pp = 0;
/*     */         label286:
/* 262 */         for (int c = 0; c < flux; c++) {
/* 263 */           for (EnumFacing face : EnumFacing.HORIZONTALS) {
/* 264 */             IBlockState vent = this.worldObj.getBlockState(getPos().offset(face));
/* 265 */             if ((vent.getBlock() == BlocksTC.smelterVent) && (BlockStateUtils.getFacing(vent) == face.getOpposite()) && 
/* 266 */               (this.worldObj.rand.nextFloat() < 0.333D)) {
/* 267 */               this.worldObj.addBlockEvent(getPos(), getBlockType(), 1, face.getOpposite().ordinal());
/*     */               
/*     */               break label286;
/*     */             }
/*     */           }
/* 272 */           pp++;
/*     */         }
/* 274 */         AuraHelper.pollute(getWorld(), getPos(), pp, true);
/*     */       }
/*     */       
/* 277 */       this.vis = this.aspects.visSize();
/*     */       
/* 279 */       this.itemStacks[0].stackSize -= 1;
/*     */       
/* 281 */       if (this.itemStacks[0].stackSize <= 0)
/*     */       {
/* 283 */         this.itemStacks[0] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isItemFuel(ItemStack par0ItemStack)
/*     */   {
/* 290 */     return TileEntityFurnace.getItemBurnTime(par0ItemStack) > 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/*     */   {
/* 296 */     if (par1 == 0) {
/* 297 */       AspectList al = ThaumcraftCraftingManager.getObjectTags(par2ItemStack);
/* 298 */       if ((al != null) && (al.size() > 0))
/* 299 */         return true;
/*     */     }
/* 301 */     return par1 == 1 ? isItemFuel(par2ItemStack) : false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int[] getSlotsForFace(EnumFacing par1)
/*     */   {
/* 307 */     return par1 == EnumFacing.UP ? slots_top : par1 == EnumFacing.DOWN ? slots_bottom : slots_sides;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canInsertItem(int par1, ItemStack par2ItemStack, EnumFacing par3)
/*     */   {
/* 313 */     return par3 == EnumFacing.UP ? false : isItemValidForSlot(par1, par2ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canExtractItem(int par1, ItemStack par2ItemStack, EnumFacing par3)
/*     */   {
/* 319 */     return (par3 != EnumFacing.UP) || (par1 != 1) || (par2ItemStack.getItem() == net.minecraft.init.Items.bucket);
/*     */   }
/*     */   
/*     */   public boolean takeFromContainer(Aspect tag, int amount) {
/* 323 */     if ((this.aspects != null) && (this.aspects.getAmount(tag) >= amount)) {
/* 324 */       this.aspects.remove(tag, amount);
/* 325 */       this.vis = this.aspects.visSize();
/* 326 */       markDirty();
/* 327 */       return true;
/*     */     }
/* 329 */     return false;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getCookProgressScaled(int par1)
/*     */   {
/* 335 */     if (this.smeltTime <= 0) this.smeltTime = 1;
/* 336 */     return this.furnaceCookTime * par1 / this.smeltTime;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getVisScaled(int par1)
/*     */   {
/* 342 */     return this.vis * par1 / this.maxVis;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getBurnTimeRemainingScaled(int par1)
/*     */   {
/* 348 */     if (this.currentItemBurnTime == 0)
/*     */     {
/* 350 */       this.currentItemBurnTime = 200;
/*     */     }
/* 352 */     return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
/*     */   }
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
/*     */   {
/* 357 */     super.onDataPacket(net, pkt);
/* 358 */     if (this.worldObj != null) { this.worldObj.checkLightFor(net.minecraft.world.EnumSkyBlock.BLOCK, this.pos);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/* 365 */     if (i == 1)
/*     */     {
/* 367 */       if (this.worldObj.isRemote) {
/* 368 */         EnumFacing d = EnumFacing.VALUES[j];
/* 369 */         this.worldObj.playSound(getPos().getX() + 0.5D + d.getOpposite().getFrontOffsetX(), getPos().getY() + 0.5D, getPos().getZ() + 0.5D + d.getOpposite().getFrontOffsetZ(), "random.fizz", 0.25F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F, true);
/*     */         
/*     */ 
/* 372 */         for (int a = 0; a < 4; a++) {
/* 373 */           float fx = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 374 */           float fz = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 375 */           float fy = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 376 */           float fx2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 377 */           float fz2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 378 */           float fy2 = 0.1F - this.worldObj.rand.nextFloat() * 0.2F;
/* 379 */           int color = 11184810;
/* 380 */           Thaumcraft.proxy.getFX().drawVentParticles(getPos().getX() + 0.5F + fx + d.getOpposite().getFrontOffsetX(), getPos().getY() + 0.5F + fy, getPos().getZ() + 0.5F + fz + d.getOpposite().getFrontOffsetZ(), d.getOpposite().getFrontOffsetX() / 4.0F + fx2, d.getOpposite().getFrontOffsetY() / 4.0F + fy2, d.getOpposite().getFrontOffsetZ() / 4.0F + fz2, color);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 389 */       return true;
/*     */     }
/*     */     
/* 392 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\crafting\TileSmelter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */