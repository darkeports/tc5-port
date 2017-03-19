/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.aspects.IEssentiaContainerItem;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ import thaumcraft.common.blocks.ItemBlockTC;
/*     */ import thaumcraft.common.tiles.crafting.TileAlembic;
/*     */ import thaumcraft.common.tiles.devices.TileJarBrain;
/*     */ import thaumcraft.common.tiles.essentia.TileJarFillable;
/*     */ 
/*     */ public class BlockJarItem
/*     */   extends ItemBlockTC implements IEssentiaContainerItem
/*     */ {
/*     */   public BlockJarItem(Block block)
/*     */   {
/*  34 */     super(block);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float f1, float f2, float f3)
/*     */   {
/*  40 */     Block bi = world.getBlockState(pos).getBlock();
/*     */     
/*     */ 
/*  43 */     if ((bi == BlocksTC.alembic) && (!world.isRemote)) {
/*  44 */       TileAlembic tile = (TileAlembic)world.getTileEntity(pos);
/*  45 */       if (tile.amount > 0) {
/*  46 */         if ((getFilter(itemstack) != null) && (getFilter(itemstack) != tile.aspect)) return false;
/*  47 */         if ((getAspects(itemstack) != null) && (getAspects(itemstack).getAspects()[0] != tile.aspect)) { return false;
/*     */         }
/*     */         
/*  50 */         int amt = tile.amount;
/*  51 */         if ((getAspects(itemstack) != null) && (getAspects(itemstack).visSize() + amt > 64)) amt = Math.abs(getAspects(itemstack).visSize() - 64);
/*  52 */         if (amt <= 0) return false;
/*  53 */         Aspect a = tile.aspect;
/*  54 */         if (tile.takeFromContainer(tile.aspect, amt)) {
/*  55 */           int base = getAspects(itemstack) == null ? 0 : getAspects(itemstack).visSize();
/*  56 */           if (itemstack.stackSize > 1) {
/*  57 */             ItemStack stack = itemstack.copy();
/*  58 */             setAspects(stack, new AspectList().add(a, base + amt));
/*  59 */             itemstack.stackSize -= 1;
/*  60 */             stack.stackSize = 1;
/*  61 */             if (!player.inventory.addItemStackToInventory(stack)) {
/*  62 */               world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, stack));
/*     */             }
/*     */           } else {
/*  65 */             setAspects(itemstack, new AspectList().add(a, base + amt));
/*     */           }
/*     */           
/*  68 */           world.playSoundAtEntity(player, "game.neutral.swim", 0.25F, 1.0F);
/*  69 */           player.inventoryContainer.detectAndSendChanges();
/*  70 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*  74 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
/*     */   {
/*  82 */     boolean b = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
/*     */     
/*  84 */     if ((b) && (!world.isRemote)) {
/*  85 */       TileEntity te = world.getTileEntity(pos);
/*  86 */       if ((te != null) && ((te instanceof TileJarFillable))) {
/*  87 */         TileJarFillable jar = (TileJarFillable)te;
/*  88 */         jar.setAspects(getAspects(stack));
/*  89 */         if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("AspectFilter"))) {
/*  90 */           jar.aspectFilter = Aspect.getAspect(stack.getTagCompound().getString("AspectFilter"));
/*     */         }
/*  92 */         te.markDirty();
/*  93 */         world.markBlockForUpdate(pos);
/*     */       }
/*  95 */       if ((te != null) && ((te instanceof TileJarBrain))) {
/*  96 */         TileJarBrain jar = (TileJarBrain)te;
/*  97 */         if (stack.hasTagCompound()) {
/*  98 */           jar.xp = stack.getTagCompound().getInteger("xp");
/*     */         }
/* 100 */         te.markDirty();
/* 101 */         world.markBlockForUpdate(pos);
/*     */       }
/*     */     }
/*     */     
/* 105 */     return b;
/*     */   }
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*     */   {
/* 110 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("AspectFilter"))) {
/* 111 */       String tf = stack.getTagCompound().getString("AspectFilter");
/* 112 */       Aspect tag = Aspect.getAspect(tf);
/* 113 */       list.add("§5" + tag.getName());
/*     */     }
/* 115 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("xp"))) {
/* 116 */       int tf = stack.getTagCompound().getInteger("xp");
/* 117 */       list.add("§5" + tf + " xp");
/*     */     }
/* 119 */     super.addInformation(stack, player, list, par4);
/*     */   }
/*     */   
/*     */   public AspectList getAspects(ItemStack itemstack)
/*     */   {
/* 124 */     if (itemstack.hasTagCompound()) {
/* 125 */       AspectList aspects = new AspectList();
/* 126 */       aspects.readFromNBT(itemstack.getTagCompound());
/* 127 */       return aspects.size() > 0 ? aspects : null;
/*     */     }
/* 129 */     return null;
/*     */   }
/*     */   
/*     */   public Aspect getFilter(ItemStack itemstack) {
/* 133 */     if (itemstack.hasTagCompound()) {
/* 134 */       return Aspect.getAspect(itemstack.getTagCompound().getString("AspectFilter"));
/*     */     }
/* 136 */     return null;
/*     */   }
/*     */   
/*     */   public void setAspects(ItemStack itemstack, AspectList aspects)
/*     */   {
/* 141 */     if (!itemstack.hasTagCompound()) itemstack.setTagCompound(new NBTTagCompound());
/* 142 */     aspects.writeToNBT(itemstack.getTagCompound());
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getColorFromItemStack(ItemStack stack, int layer)
/*     */   {
/* 149 */     if (getAspects(stack) != null) {
/* 150 */       return getAspects(stack).getAspects()[0].getColor();
/*     */     }
/*     */     
/* 153 */     return super.getColorFromItemStack(stack, layer);
/*     */   }
/*     */   
/*     */ 
/*     */   public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
/*     */   {
/* 159 */     BlockTC block = (BlockTC)this.block;
/* 160 */     ModelResourceLocation modelresourcelocation = null;
/*     */     
/* 162 */     if ((getAspects(stack) != null) && (getAspects(stack).visSize() > 0))
/*     */     {
/*     */ 
/* 165 */       if (getAspects(stack).visSize() < 16)
/*     */       {
/* 167 */         modelresourcelocation = new ModelResourceLocation("Thaumcraft:" + block.getStateName(block.getStateFromMeta(stack.getMetadata()), false) + "_0", "inventory");
/*     */ 
/*     */       }
/* 170 */       else if (getAspects(stack).visSize() < 32)
/*     */       {
/* 172 */         modelresourcelocation = new ModelResourceLocation("Thaumcraft:" + block.getStateName(block.getStateFromMeta(stack.getMetadata()), false) + "_1", "inventory");
/*     */ 
/*     */       }
/* 175 */       else if (getAspects(stack).visSize() < 48)
/*     */       {
/* 177 */         modelresourcelocation = new ModelResourceLocation("Thaumcraft:" + block.getStateName(block.getStateFromMeta(stack.getMetadata()), false) + "_2", "inventory");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 182 */         modelresourcelocation = new ModelResourceLocation("Thaumcraft:" + block.getStateName(block.getStateFromMeta(stack.getMetadata()), false) + "_3", "inventory");
/*     */       }
/*     */     }
/*     */     
/* 186 */     return modelresourcelocation;
/*     */   }
/*     */   
/*     */   public boolean ignoreContainedAspects()
/*     */   {
/* 191 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockJarItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */