/*     */ package thaumcraft.common.tiles.crafting;
/*     */ 
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagLong;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.ThaumcraftApiHelper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.IEssentiaTransport;
/*     */ import thaumcraft.api.golems.IGolemProperties;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.entities.construct.golem.GolemProperties;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ import thaumcraft.common.tiles.TileThaumcraftInventory;
/*     */ 
/*     */ public class TileGolemBuilder extends TileThaumcraftInventory implements ITickable, IEssentiaTransport
/*     */ {
/*  28 */   public long golem = -1L;
/*  29 */   public int cost = 0;
/*  30 */   public int maxCost = 0;
/*  31 */   boolean bufferedEssentia = false;
/*     */   
/*     */   public TileGolemBuilder()
/*     */   {
/*  35 */     this.itemStacks = new ItemStack[1];
/*  36 */     this.syncedSlots = new int[] { 0 };
/*     */   }
/*     */   
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  41 */     this.itemStacks = new ItemStack[1];
/*  42 */     this.syncedSlots = new int[] { 0 };
/*  43 */     super.readCustomNBT(nbttagcompound);
/*  44 */     this.golem = nbttagcompound.getLong("golem");
/*  45 */     this.cost = nbttagcompound.getInteger("cost");
/*  46 */     this.maxCost = nbttagcompound.getInteger("mcost");
/*  47 */     if (this.golem >= 0L) {
/*     */       try {
/*  49 */         this.props = GolemProperties.fromLong(this.golem);
/*  50 */         this.components = this.props.generateComponents();
/*     */       } catch (Exception e) {
/*  52 */         this.props = null;
/*  53 */         this.components = null;
/*  54 */         this.cost = 0;
/*  55 */         this.golem = -1L;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  63 */     super.writeCustomNBT(nbttagcompound);
/*  64 */     nbttagcompound.setLong("golem", this.golem);
/*  65 */     nbttagcompound.setInteger("cost", this.cost);
/*  66 */     nbttagcompound.setInteger("mcost", this.maxCost);
/*     */   }
/*     */   
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/*  72 */     return AxisAlignedBB.fromBounds(this.pos.getX() - 1, this.pos.getY(), this.pos.getZ() - 1, this.pos.getX() + 2, this.pos.getY() + 2, this.pos.getZ() + 2);
/*     */   }
/*     */   
/*  75 */   int ticks = 0;
/*  76 */   public int press = 0;
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/*  81 */     boolean complete = false;
/*  82 */     if (!this.worldObj.isRemote)
/*     */     {
/*  84 */       this.ticks += 1;
/*  85 */       if ((this.ticks % 5 == 0) && 
/*  86 */         (!complete) && (this.cost > 0) && (this.golem >= 0L))
/*     */       {
/*  88 */         if ((this.bufferedEssentia) || (drawEssentia())) {
/*  89 */           this.bufferedEssentia = false;
/*  90 */           this.cost -= 1;
/*  91 */           markDirty();
/*     */         }
/*     */         
/*  94 */         if (this.cost <= 0) {
/*  95 */           ItemStack placer = new ItemStack(ItemsTC.golemPlacer);
/*  96 */           placer.setTagInfo("props", new NBTTagLong(this.golem));
/*     */           
/*  98 */           if ((getStackInSlot(0) == null) || ((getStackInSlot(0).stackSize < getStackInSlot(0).getMaxStackSize()) && (getStackInSlot(0).isItemEqual(placer)) && (ItemStack.areItemStackTagsEqual(getStackInSlot(0), placer))))
/*     */           {
/*     */ 
/*     */ 
/* 102 */             if (getStackInSlot(0) == null) {
/* 103 */               setInventorySlotContents(0, placer.copy());
/*     */             } else {
/* 105 */               getStackInSlot(0).stackSize += 1;
/*     */             }
/* 107 */             complete = true;
/* 108 */             this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:wand", 1.0F, 1.0F);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 114 */       if ((this.press < 90) && (this.cost > 0) && (this.golem > 0L)) {
/* 115 */         this.press += 6;
/* 116 */         if (this.press >= 60) {
/* 117 */           this.worldObj.playSound(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, "random.fizz", 0.66F, 1.0F + this.worldObj.rand.nextFloat() * 0.1F, false);
/* 118 */           for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(8); a++) {
/* 119 */             Thaumcraft.proxy.getFX().drawVentParticles(this.pos.getX() + 0.5D, this.pos.getY() + 1, this.pos.getZ() + 0.5D, this.worldObj.rand.nextGaussian() * 0.1D, 0.0D, this.worldObj.rand.nextGaussian() * 0.1D, 11184810);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 125 */       if ((this.press >= 90) && (this.worldObj.rand.nextInt(8) == 0)) {
/* 126 */         Thaumcraft.proxy.getFX().drawVentParticles(this.pos.getX() + 0.5D, this.pos.getY() + 1, this.pos.getZ() + 0.5D, this.worldObj.rand.nextGaussian() * 0.1D, 0.0D, this.worldObj.rand.nextGaussian() * 0.1D, 11184810);
/*     */         
/*     */ 
/*     */ 
/* 130 */         this.worldObj.playSound(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, "random.fizz", 0.1F, 1.0F + this.worldObj.rand.nextFloat() * 0.1F, false);
/*     */       }
/* 132 */       if ((this.press > 0) && ((this.cost <= 0) || (this.golem == -1L))) {
/* 133 */         if (this.press >= 90) {
/* 134 */           for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(5); a++) {
/* 135 */             Thaumcraft.proxy.getFX().drawVentParticles(this.pos.getX() + 0.5D, this.pos.getY() + 1, this.pos.getZ() + 0.5D, this.worldObj.rand.nextGaussian() * 0.1D, 0.0D, this.worldObj.rand.nextGaussian() * 0.1D, 11184810);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 140 */         this.press -= 3;
/*     */       }
/*     */     }
/*     */     
/* 144 */     if (complete)
/*     */     {
/* 146 */       this.cost = 0;
/* 147 */       this.golem = -1L;
/* 148 */       this.worldObj.markBlockForUpdate(this.pos);
/* 149 */       markDirty();
/*     */     }
/*     */   }
/*     */   
/* 153 */   IGolemProperties props = null;
/* 154 */   ItemStack[] components = null;
/*     */   
/*     */   public boolean startCraft(long id, EntityPlayer p)
/*     */   {
/* 158 */     ItemStack placer = new ItemStack(ItemsTC.golemPlacer);
/* 159 */     placer.setTagInfo("props", new NBTTagLong(id));
/*     */     
/* 161 */     if ((getStackInSlot(0) == null) || ((getStackInSlot(0).stackSize < getStackInSlot(0).getMaxStackSize()) && (getStackInSlot(0).isItemEqual(placer)) && (ItemStack.areItemStackTagsEqual(getStackInSlot(0), placer))))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 166 */       this.golem = id;
/* 167 */       this.props = GolemProperties.fromLong(this.golem);
/* 168 */       this.components = this.props.generateComponents();
/*     */       
/* 170 */       for (ItemStack stack : this.components) {
/* 171 */         if (!InventoryUtils.isPlayerCarryingAmount(p, stack, true)) {
/* 172 */           this.cost = 0;
/* 173 */           this.props = null;
/* 174 */           this.components = null;
/* 175 */           this.golem = -1L;
/* 176 */           return false;
/*     */         }
/*     */       }
/*     */       
/* 180 */       this.cost = (this.props.getTraits().size() * 2);
/* 181 */       for (ItemStack stack : this.components) {
/* 182 */         this.cost += stack.stackSize;
/* 183 */         InventoryUtils.consumeInventoryItems(p, stack, true, true);
/*     */       }
/*     */       
/* 186 */       this.maxCost = this.cost;
/*     */       
/* 188 */       markDirty();
/* 189 */       this.worldObj.markBlockForUpdate(this.pos);
/* 190 */       this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:craftstart", 0.25F, 1.0F);
/* 191 */       return true;
/*     */     }
/* 193 */     this.cost = 0;
/* 194 */     this.props = null;
/* 195 */     this.components = null;
/* 196 */     this.golem = -1L;
/* 197 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/*     */   {
/* 204 */     if ((par2ItemStack != null) && ((par2ItemStack.getItem() instanceof thaumcraft.common.entities.construct.golem.ItemGolemPlacer)))
/* 205 */       return true;
/* 206 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean drawEssentia()
/*     */   {
/* 230 */     for (EnumFacing face : EnumFacing.VALUES) {
/* 231 */       net.minecraft.tileentity.TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, getPos(), face);
/* 232 */       if (te != null) {
/* 233 */         IEssentiaTransport ic = (IEssentiaTransport)te;
/* 234 */         if (!ic.canOutputTo(face.getOpposite())) return false;
/* 235 */         if ((ic.getSuctionAmount(face.getOpposite()) < getSuctionAmount(face)) && (ic.takeEssentia(Aspect.MECHANISM, 1, face.getOpposite()) == 1))
/*     */         {
/* 237 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 241 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isConnectable(EnumFacing face)
/*     */   {
/* 246 */     return (face.getHorizontalIndex() >= 0) || (face == EnumFacing.DOWN);
/*     */   }
/*     */   
/*     */   public boolean canInputFrom(EnumFacing face)
/*     */   {
/* 251 */     return isConnectable(face);
/*     */   }
/*     */   
/*     */   public boolean canOutputTo(EnumFacing face)
/*     */   {
/* 256 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSuction(Aspect aspect, int amount) {}
/*     */   
/*     */ 
/*     */   public int getMinimumSuction()
/*     */   {
/* 265 */     return 0;
/*     */   }
/*     */   
/*     */   public Aspect getSuctionType(EnumFacing face)
/*     */   {
/* 270 */     return Aspect.MECHANISM;
/*     */   }
/*     */   
/*     */   public int getSuctionAmount(EnumFacing face)
/*     */   {
/* 275 */     return (this.cost > 0) && (this.golem >= 0L) ? 128 : 0;
/*     */   }
/*     */   
/*     */   public Aspect getEssentiaType(EnumFacing loc)
/*     */   {
/* 280 */     return null;
/*     */   }
/*     */   
/*     */   public int getEssentiaAmount(EnumFacing loc)
/*     */   {
/* 285 */     return 0;
/*     */   }
/*     */   
/*     */   public int takeEssentia(Aspect aspect, int amount, EnumFacing facing)
/*     */   {
/* 290 */     return 0;
/*     */   }
/*     */   
/*     */   public int addEssentia(Aspect aspect, int amount, EnumFacing facing)
/*     */   {
/* 295 */     if ((!this.bufferedEssentia) && (this.cost > 0) && (this.golem >= 0L) && (aspect == Aspect.MECHANISM)) {
/* 296 */       this.bufferedEssentia = true;
/* 297 */       return 1;
/*     */     }
/* 299 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\crafting\TileGolemBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */