/*     */ package thaumcraft.common.tiles.crafting;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.items.resources.ItemResearchNotes;
/*     */ import thaumcraft.common.lib.research.ResearchManager;
/*     */ import thaumcraft.common.lib.research.ResearchManager.HexEntry;
/*     */ import thaumcraft.common.lib.research.ResearchNoteData;
/*     */ import thaumcraft.common.lib.utils.HexUtils.Hex;
/*     */ 
/*     */ public class TileResearchTable extends TileThaumcraft implements net.minecraft.inventory.IInventory
/*     */ {
/*  26 */   public ItemStack[] contents = new ItemStack[2];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  33 */     NBTTagList var2 = nbttagcompound.getTagList("Inventory", 10);
/*  34 */     this.contents = new ItemStack[getSizeInventory()];
/*  35 */     for (int var3 = 0; var3 < Math.min(2, var2.tagCount()); var3++)
/*     */     {
/*  37 */       NBTTagCompound var4 = var2.getCompoundTagAt(var3);
/*  38 */       int var5 = var4.getByte("Slot") & 0xFF;
/*     */       
/*  40 */       if ((var5 >= 0) && (var5 < this.contents.length))
/*     */       {
/*  42 */         this.contents[var5] = ItemStack.loadItemStackFromNBT(var4);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  50 */     NBTTagList var2 = new NBTTagList();
/*  51 */     for (int var3 = 0; var3 < this.contents.length; var3++)
/*     */     {
/*  53 */       if (this.contents[var3] != null)
/*     */       {
/*  55 */         NBTTagCompound var4 = new NBTTagCompound();
/*  56 */         var4.setByte("Slot", (byte)var3);
/*  57 */         this.contents[var3].writeToNBT(var4);
/*  58 */         var2.appendTag(var4);
/*     */       }
/*     */     }
/*  61 */     nbttagcompound.setTag("Inventory", var2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void markDirty()
/*     */   {
/*  68 */     super.markDirty();
/*  69 */     gatherResults();
/*     */   }
/*     */   
/*  72 */   EntityPlayer researcher = null;
/*     */   
/*  74 */   public ResearchNoteData data = null;
/*     */   
/*  76 */   public void gatherResults() { this.data = null;
/*  77 */     if ((this.contents[1] != null) && ((this.contents[1].getItem() instanceof ItemResearchNotes))) {
/*  78 */       this.data = ResearchManager.getData(this.contents[1]);
/*     */     }
/*     */   }
/*     */   
/*     */   public void placeAspect(int q, int r, Aspect aspect, EntityPlayer player) {
/*  83 */     if (this.data == null) gatherResults();
/*  84 */     if (!ResearchManager.consumeInkFromTable(this.contents[0], false)) {
/*  85 */       return;
/*     */     }
/*  87 */     if ((this.contents[1] != null) && ((this.contents[1].getItem() instanceof ItemResearchNotes)) && (this.data != null) && (this.contents[1].getItemDamage() == 0))
/*     */     {
/*     */ 
/*  90 */       boolean r1 = ResearchManager.isResearchComplete(player.getName(), "RESEARCHER1");
/*  91 */       boolean r2 = ResearchManager.isResearchComplete(player.getName(), "RESEARCHER2");
/*     */       
/*  93 */       HexUtils.Hex hex = new HexUtils.Hex(q, r);
/*  94 */       ResearchManager.HexEntry he = null;
/*  95 */       if (aspect != null) {
/*  96 */         he = new ResearchManager.HexEntry(aspect, 2);
/*  97 */         if ((r2) && (this.worldObj.rand.nextFloat() < 0.1F)) {
/*  98 */           this.worldObj.playSoundAtEntity(player, "random.orb", 0.2F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F);
/*     */         }
/*     */         else {
/* 101 */           this.data.aspects.remove(aspect, 1);
/*     */         }
/*     */       } else {
/* 104 */         float f = this.worldObj.rand.nextFloat();
/* 105 */         if ((((ResearchManager.HexEntry)this.data.hexEntries.get(hex.toString())).aspect != null) && (((r1) && (f < 0.25F)) || ((r2) && (f < 0.5F))))
/*     */         {
/* 107 */           this.worldObj.playSoundAtEntity(player, "random.orb", 0.2F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F);
/*     */           
/* 109 */           this.data.aspects.add(((ResearchManager.HexEntry)this.data.hexEntries.get(hex.toString())).aspect, 1);
/*     */         }
/* 111 */         he = new ResearchManager.HexEntry(null, 0);
/*     */       }
/*     */       
/* 114 */       this.data.hexEntries.put(hex.toString(), he);
/* 115 */       this.data.hexes.put(hex.toString(), hex);
/*     */       
/* 117 */       updateNoteAndConsumeInk();
/*     */       
/* 119 */       if ((!this.worldObj.isRemote) && (ResearchManager.checkResearchCompletion(this.contents[1], this.data, player.getName()))) {
/* 120 */         this.contents[1].setItemDamage(1);
/* 121 */         if (Config.researchAmount > 0) this.contents[1].stackSize = Config.researchAmount;
/* 122 */         this.worldObj.addBlockEvent(getPos(), getBlockType(), 1, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateNoteAndConsumeInk()
/*     */   {
/* 129 */     ResearchManager.updateData(this.contents[1], this.data);
/* 130 */     ResearchManager.consumeInkFromTable(this.contents[0], true);
/* 131 */     this.worldObj.markBlockForUpdate(getPos());
/* 132 */     markDirty();
/*     */   }
/*     */   
/*     */   public int getSizeInventory()
/*     */   {
/* 137 */     return 2;
/*     */   }
/*     */   
/*     */   public ItemStack getStackInSlot(int var1)
/*     */   {
/* 142 */     return this.contents[var1];
/*     */   }
/*     */   
/*     */   public ItemStack decrStackSize(int var1, int var2)
/*     */   {
/* 147 */     if (this.contents[var1] != null)
/*     */     {
/*     */ 
/*     */ 
/* 151 */       if (this.contents[var1].stackSize <= var2)
/*     */       {
/* 153 */         ItemStack var3 = this.contents[var1];
/* 154 */         this.contents[var1] = null;
/* 155 */         markDirty();
/* 156 */         return var3;
/*     */       }
/*     */       
/*     */ 
/* 160 */       ItemStack var3 = this.contents[var1].splitStack(var2);
/*     */       
/* 162 */       if (this.contents[var1].stackSize == 0)
/*     */       {
/* 164 */         this.contents[var1] = null;
/*     */       }
/*     */       
/* 167 */       markDirty();
/* 168 */       return var3;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 173 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack removeStackFromSlot(int var1)
/*     */   {
/* 179 */     if (this.contents[var1] != null)
/*     */     {
/* 181 */       ItemStack var2 = this.contents[var1];
/* 182 */       this.contents[var1] = null;
/* 183 */       return var2;
/*     */     }
/*     */     
/*     */ 
/* 187 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setInventorySlotContents(int var1, ItemStack var2)
/*     */   {
/* 193 */     this.contents[var1] = var2;
/*     */     
/* 195 */     if ((var2 != null) && (var2.stackSize > getInventoryStackLimit()))
/*     */     {
/* 197 */       var2.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 200 */     markDirty();
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 205 */     return "Research Table";
/*     */   }
/*     */   
/*     */   public int getInventoryStackLimit()
/*     */   {
/* 210 */     return 64;
/*     */   }
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer var1)
/*     */   {
/* 215 */     return this.worldObj.getTileEntity(getPos()) == this;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasCustomName()
/*     */   {
/* 221 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/*     */   {
/* 226 */     if (itemstack == null) return false;
/* 227 */     switch (i) {
/* 228 */     case 0:  if ((itemstack.getItem() instanceof thaumcraft.api.items.IScribeTools)) return true;
/*     */       break; case 1:  if ((itemstack.getItem() == ItemsTC.researchNotes) && (itemstack.getItemDamage() == 0)) return true;
/*     */       break; }
/* 231 */     return false;
/*     */   }
/*     */   
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
/*     */   {
/* 236 */     super.onDataPacket(net, pkt);
/* 237 */     if ((this.worldObj != null) && (this.worldObj.isRemote)) {
/* 238 */       this.worldObj.markBlockForUpdate(getPos());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/* 245 */     if (i == 1)
/*     */     {
/* 247 */       if (this.worldObj.isRemote) {
/* 248 */         this.worldObj.playSound(getPos().getX(), getPos().getY(), getPos().getZ(), "thaumcraft:learn", 1.0F, 1.0F, false);
/*     */       }
/* 250 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 254 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */   
/*     */   public net.minecraft.util.IChatComponent getDisplayName() {
/* 258 */     return null;
/*     */   }
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */   
/*     */   public int getField(int id)
/*     */   {
/* 267 */     return 0;
/*     */   }
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 273 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\crafting\TileResearchTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */