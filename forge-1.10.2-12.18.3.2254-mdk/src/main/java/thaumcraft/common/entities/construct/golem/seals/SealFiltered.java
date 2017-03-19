/*     */ package thaumcraft.common.entities.construct.golem.seals;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.golems.seals.ISeal;
/*     */ import thaumcraft.api.golems.seals.ISealConfigFilter;
/*     */ import thaumcraft.api.golems.seals.ISealEntity;
/*     */ import thaumcraft.api.golems.seals.ISealGui;
/*     */ import thaumcraft.common.entities.construct.golem.gui.SealBaseContainer;
/*     */ import thaumcraft.common.entities.construct.golem.gui.SealBaseGUI;
/*     */ 
/*     */ public abstract class SealFiltered implements ISeal, ISealGui, ISealConfigFilter
/*     */ {
/*     */   public void readCustomNBT(NBTTagCompound nbt)
/*     */   {
/*  23 */     NBTTagList nbttaglist = nbt.getTagList("Items", 10);
/*  24 */     this.filter = new ItemStack[getFilterSize()];
/*  25 */     for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */     {
/*  27 */       NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/*  28 */       byte b0 = nbttagcompound1.getByte("Slot");
/*     */       
/*  30 */       if ((b0 >= 0) && (b0 < this.filter.length))
/*     */       {
/*  32 */         this.filter[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */       }
/*     */     }
/*  35 */     this.blacklist = nbt.getBoolean("bl");
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbt)
/*     */   {
/*  40 */     NBTTagList nbttaglist = new NBTTagList();
/*  41 */     for (int i = 0; i < this.filter.length; i++)
/*     */     {
/*  43 */       if (this.filter[i] != null)
/*     */       {
/*  45 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  46 */         nbttagcompound1.setByte("Slot", (byte)i);
/*  47 */         this.filter[i].writeToNBT(nbttagcompound1);
/*  48 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/*     */     }
/*  51 */     nbt.setTag("Items", nbttaglist);
/*  52 */     nbt.setBoolean("bl", this.blacklist);
/*     */   }
/*     */   
/*     */   public Object returnContainer(World world, EntityPlayer player, BlockPos pos, EnumFacing side, ISealEntity seal)
/*     */   {
/*  57 */     return new SealBaseContainer(player.inventory, world, seal);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public Object returnGui(World world, EntityPlayer player, BlockPos pos, EnumFacing side, ISealEntity seal)
/*     */   {
/*  63 */     return new SealBaseGUI(player.inventory, world, seal);
/*     */   }
/*     */   
/*     */   public int[] getGuiCategories() {
/*  67 */     return new int[] { 0 };
/*     */   }
/*     */   
/*     */   public int getFilterSize() {
/*  71 */     return 1;
/*     */   }
/*     */   
/*  74 */   ItemStack[] filter = new ItemStack[getFilterSize()];
/*     */   
/*     */   public ItemStack[] getInv() {
/*  77 */     return this.filter;
/*     */   }
/*     */   
/*     */   public ItemStack getFilterSlot(int i)
/*     */   {
/*  82 */     return this.filter[i];
/*     */   }
/*     */   
/*     */   public void setFilterSlot(int i, ItemStack stack)
/*     */   {
/*  87 */     this.filter[i] = (stack == null ? null : stack.copy());
/*     */   }
/*     */   
/*  90 */   boolean blacklist = true;
/*     */   
/*     */   public boolean isBlacklist()
/*     */   {
/*  94 */     return this.blacklist;
/*     */   }
/*     */   
/*     */   public void setBlacklist(boolean black)
/*     */   {
/*  99 */     this.blacklist = black;
/*     */   }
/*     */   
/*     */   public boolean hasStacksizeLimiters()
/*     */   {
/* 104 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\seals\SealFiltered.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */