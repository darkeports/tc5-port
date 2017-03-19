/*     */ package thaumcraft.common.tiles.crafting;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.wands.FocusUpgradeType;
/*     */ import thaumcraft.api.wands.ItemFocusBasic;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.tiles.TileThaumcraftInventory;
/*     */ 
/*     */ public class TileFocalManipulator extends TileThaumcraftInventory implements net.minecraft.util.ITickable
/*     */ {
/*  22 */   public AspectList aspects = new AspectList();
/*  23 */   public int size = 0;
/*  24 */   public int upgrade = -1;
/*  25 */   public int rank = -1;
/*     */   
/*     */   public TileFocalManipulator()
/*     */   {
/*  29 */     this.itemStacks = new ItemStack[1];
/*  30 */     this.syncedSlots = new int[] { 0 };
/*     */   }
/*     */   
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  35 */     this.itemStacks = new ItemStack[1];
/*  36 */     this.syncedSlots = new int[] { 0 };
/*  37 */     super.readCustomNBT(nbttagcompound);
/*  38 */     this.aspects.readFromNBT(nbttagcompound);
/*  39 */     this.size = nbttagcompound.getInteger("size");
/*  40 */     this.upgrade = nbttagcompound.getInteger("upgrade");
/*  41 */     this.rank = nbttagcompound.getInteger("rank");
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  46 */     super.writeCustomNBT(nbttagcompound);
/*  47 */     this.aspects.writeToNBT(nbttagcompound);
/*  48 */     nbttagcompound.setInteger("size", this.size);
/*  49 */     nbttagcompound.setInteger("upgrade", this.upgrade);
/*  50 */     nbttagcompound.setInteger("rank", this.rank);
/*     */   }
/*     */   
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/*  56 */     return AxisAlignedBB.fromBounds(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX() + 1, this.pos.getY() + 1, this.pos.getZ() + 1);
/*     */   }
/*     */   
/*  59 */   int ticks = 0;
/*     */   
/*  61 */   public boolean reset = false;
/*     */   public static final int XP_MULT = 6;
/*     */   public static final int VIS_MULT = 100;
/*     */   
/*  65 */   public void setInventorySlotContents(int par1, ItemStack par2ItemStack) { super.setInventorySlotContents(par1, par2ItemStack);
/*     */     
/*  67 */     if (this.worldObj.isRemote) {
/*  68 */       this.reset = true;
/*     */     } else {
/*  70 */       this.aspects = new AspectList();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/*  77 */     boolean complete = false;
/*  78 */     if (!this.worldObj.isRemote)
/*     */     {
/*  80 */       if (this.rank < 0) {
/*  81 */         this.rank = 0;
/*     */       }
/*  83 */       this.ticks += 1;
/*  84 */       if (this.ticks % 5 == 0) {
/*  85 */         if ((this.size > 0) && ((this.aspects.visSize() <= 0) || (getStackInSlot(0) == null))) {
/*  86 */           complete = true;
/*  87 */           this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:craftfail", 0.33F, 1.0F);
/*     */         }
/*     */         
/*  90 */         if ((!complete) && (this.size > 0)) {
/*  91 */           for (Aspect aspect : this.aspects.getAspectsSortedByAmount()) {
/*  92 */             int amt = Math.min(5, this.aspects.getAmount(aspect));
/*  93 */             if (amt > 0) {
/*  94 */               amt = thaumcraft.common.lib.aura.AuraHandler.drainAuraAvailable(this.worldObj, this.pos, aspect, amt);
/*  95 */               if (amt > 0) {
/*  96 */                 this.worldObj.addBlockEvent(this.pos, getBlockType(), 5, aspect.getColor());
/*  97 */                 this.aspects.reduce(aspect, amt);
/*  98 */                 this.worldObj.markBlockForUpdate(this.pos);
/*  99 */                 markDirty();
/*     */               }
/*     */             }
/*     */           }
/* 103 */           if ((this.aspects.visSize() <= 0) && (getStackInSlot(0) != null)) {
/* 104 */             complete = true;
/* 105 */             ItemFocusBasic focus = (ItemFocusBasic)getStackInSlot(0).getItem();
/* 106 */             boolean b = focus.applyUpgrade(getStackInSlot(0), FocusUpgradeType.types[this.upgrade], this.rank);
/* 107 */             this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:wand", 1.0F, 1.0F);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 112 */     else if (this.size > 0) {
/* 113 */       Thaumcraft.proxy.getFX().drawGenericParticles(this.pos.getX() + 0.5D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F, this.pos.getY() + 1.4D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F, this.pos.getZ() + 0.5D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F, 0.0D, 0.0D, 0.0D, 0.5F + this.worldObj.rand.nextFloat() * 0.4F, 1.0F - this.worldObj.rand.nextFloat() * 0.4F, 1.0F - this.worldObj.rand.nextFloat() * 0.4F, 0.8F, false, 112, 9, 1, 6 + this.worldObj.rand.nextInt(5), 0, 0.5F + this.worldObj.rand.nextFloat() * 0.3F, 0.0F, 0);
/*     */     }
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
/* 127 */     if (complete)
/*     */     {
/* 129 */       this.size = 0;
/* 130 */       this.rank = -1;
/* 131 */       this.aspects = new AspectList();
/* 132 */       this.worldObj.markBlockForUpdate(this.pos);
/* 133 */       markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean startCraft(int id, EntityPlayer p) {
/* 138 */     if ((this.size > 0) || (getStackInSlot(0) == null) || (!(getStackInSlot(0).getItem() instanceof ItemFocusBasic))) { return false;
/*     */     }
/* 140 */     ItemFocusBasic focus = (ItemFocusBasic)getStackInSlot(0).getItem();
/* 141 */     short[] s = focus.getAppliedUpgrades(getStackInSlot(0));
/* 142 */     this.rank = 1;
/* 143 */     while ((this.rank <= 5) && 
/* 144 */       (s[(this.rank - 1)] != -1)) {
/* 143 */       this.rank += 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 149 */     int xp = this.rank * 6;
/* 150 */     if ((!p.capabilities.isCreativeMode) && (p.experienceLevel < xp)) { return false;
/*     */     }
/* 152 */     FocusUpgradeType[] ut = focus.getPossibleUpgradesByRank(getStackInSlot(0), this.rank);
/* 153 */     if (ut == null) return false;
/* 154 */     boolean b = false;
/* 155 */     for (int a = 0; a < ut.length; a++) {
/* 156 */       if (ut[a].id == id) {
/* 157 */         b = true;
/* 158 */         break;
/*     */       }
/*     */     }
/* 161 */     if (!b) { return false;
/*     */     }
/* 163 */     if ((id > FocusUpgradeType.types.length - 1) || (FocusUpgradeType.types[id] == null) || (!focus.canApplyUpgrade(getStackInSlot(0), p, FocusUpgradeType.types[id], this.rank))) {
/* 164 */       return false;
/*     */     }
/* 166 */     int amt = 100;
/* 167 */     for (int a = 1; a < this.rank; a++) amt = (int)(amt * 1.5D);
/* 168 */     AspectList tal = new AspectList();
/* 169 */     for (Aspect as : FocusUpgradeType.types[id].aspects.getAspects()) {
/* 170 */       tal.add(as, amt);
/*     */     }
/* 172 */     this.aspects = thaumcraft.api.aspects.AspectHelper.reduceToPrimals(tal);
/* 173 */     this.size = this.aspects.visSize();
/* 174 */     this.upgrade = id;
/* 175 */     if (!p.capabilities.isCreativeMode) p.removeExperienceLevel(xp);
/* 176 */     markDirty();
/* 177 */     this.worldObj.markBlockForUpdate(this.pos);
/* 178 */     this.worldObj.playSoundEffect(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "thaumcraft:craftstart", 0.25F, 1.0F);
/* 179 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
/*     */   {
/* 188 */     if ((par2ItemStack != null) && ((par2ItemStack.getItem() instanceof ItemFocusBasic)))
/* 189 */       return true;
/* 190 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/* 196 */     if (i == 5)
/*     */     {
/* 198 */       if (this.worldObj.isRemote) {
/* 199 */         Thaumcraft.proxy.getFX().visSparkle(this.pos.getX() + getWorld().rand.nextInt(3) - getWorld().rand.nextInt(3), this.pos.getY() + getWorld().rand.nextInt(3), this.pos.getZ() + getWorld().rand.nextInt(3) - getWorld().rand.nextInt(3), this.pos.getX(), this.pos.getY() + 1, this.pos.getZ(), j);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 205 */       return true;
/*     */     }
/*     */     
/* 208 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\crafting\TileFocalManipulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */