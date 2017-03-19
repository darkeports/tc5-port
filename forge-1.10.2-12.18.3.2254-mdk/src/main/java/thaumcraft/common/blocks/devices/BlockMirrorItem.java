/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.tiles.devices.TileMirror;
/*     */ import thaumcraft.common.tiles.devices.TileMirrorEssentia;
/*     */ 
/*     */ public class BlockMirrorItem extends ItemBlock
/*     */ {
/*     */   public BlockMirrorItem(Block par1)
/*     */   {
/*  32 */     super(par1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/*  39 */     if ((world.getBlockState(pos).getBlock() instanceof BlockMirror)) {
/*  40 */       if (world.isRemote) {
/*  41 */         player.swingItem();
/*  42 */         return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ);
/*     */       }
/*  44 */       if (this.block == BlocksTC.mirror) {
/*  45 */         TileEntity tm = world.getTileEntity(pos);
/*  46 */         if ((tm != null) && ((tm instanceof TileMirror)) && (!((TileMirror)tm).isLinkValid())) {
/*  47 */           ItemStack st = stack.copy();
/*  48 */           st.stackSize = 1;
/*  49 */           st.setItemDamage(1);
/*  50 */           st.setTagInfo("linkX", new NBTTagInt(tm.getPos().getX()));
/*  51 */           st.setTagInfo("linkY", new NBTTagInt(tm.getPos().getY()));
/*  52 */           st.setTagInfo("linkZ", new NBTTagInt(tm.getPos().getZ()));
/*  53 */           st.setTagInfo("linkDim", new NBTTagInt(world.provider.getDimensionId()));
/*  54 */           st.setTagInfo("dimname", new NBTTagString(DimensionManager.getProvider(world.provider.getDimensionId()).getDimensionName()));
/*  55 */           world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:jar", 1.0F, 2.0F);
/*  56 */           if ((!player.inventory.addItemStackToInventory(st)) && 
/*  57 */             (!world.isRemote)) {
/*  58 */             world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, st));
/*     */           }
/*     */           
/*  61 */           if (!player.capabilities.isCreativeMode) {
/*  62 */             stack.stackSize -= 1;
/*     */           }
/*  64 */           player.inventoryContainer.detectAndSendChanges();
/*  65 */         } else if ((tm != null) && ((tm instanceof TileMirror))) {
/*  66 */           player.addChatMessage(new ChatComponentTranslation("§5§oThat mirror is already linked to a valid destination.", new Object[0]));
/*     */         }
/*     */       } else {
/*  69 */         TileEntity tm = world.getTileEntity(pos);
/*  70 */         if ((tm != null) && ((tm instanceof TileMirrorEssentia)) && (!((TileMirrorEssentia)tm).isLinkValid())) {
/*  71 */           ItemStack st = stack.copy();
/*  72 */           st.stackSize = 1;
/*  73 */           st.setItemDamage(1);
/*  74 */           st.setTagInfo("linkX", new NBTTagInt(tm.getPos().getX()));
/*  75 */           st.setTagInfo("linkY", new NBTTagInt(tm.getPos().getY()));
/*  76 */           st.setTagInfo("linkZ", new NBTTagInt(tm.getPos().getZ()));
/*  77 */           st.setTagInfo("linkDim", new NBTTagInt(world.provider.getDimensionId()));
/*  78 */           st.setTagInfo("dimname", new NBTTagString(DimensionManager.getProvider(world.provider.getDimensionId()).getDimensionName()));
/*  79 */           world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:jar", 1.0F, 2.0F);
/*  80 */           if ((!player.inventory.addItemStackToInventory(st)) && 
/*  81 */             (!world.isRemote)) {
/*  82 */             world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, st));
/*     */           }
/*     */           
/*  85 */           if (!player.capabilities.isCreativeMode) {
/*  86 */             stack.stackSize -= 1;
/*     */           }
/*  88 */           player.inventoryContainer.detectAndSendChanges();
/*  89 */         } else if ((tm != null) && ((tm instanceof TileMirrorEssentia))) {
/*  90 */           player.addChatMessage(new ChatComponentTranslation("§5§oThat mirror is already linked to a valid destination.", new Object[0]));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  95 */     return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
/*     */   {
/* 104 */     boolean ret = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
/* 105 */     if ((ret) && (!world.isRemote)) {
/* 106 */       if (this.block == BlocksTC.mirror) {
/* 107 */         TileEntity te = world.getTileEntity(pos);
/* 108 */         if ((te != null) && ((te instanceof TileMirror)) && 
/* 109 */           (stack.hasTagCompound())) {
/* 110 */           ((TileMirror)te).linkX = stack.getTagCompound().getInteger("linkX");
/* 111 */           ((TileMirror)te).linkY = stack.getTagCompound().getInteger("linkY");
/* 112 */           ((TileMirror)te).linkZ = stack.getTagCompound().getInteger("linkZ");
/* 113 */           ((TileMirror)te).linkDim = stack.getTagCompound().getInteger("linkDim");
/* 114 */           ((TileMirror)te).restoreLink();
/*     */         }
/*     */       }
/*     */       else {
/* 118 */         TileEntity te = world.getTileEntity(pos);
/* 119 */         if ((te != null) && ((te instanceof TileMirrorEssentia)) && 
/* 120 */           (stack.hasTagCompound())) {
/* 121 */           ((TileMirrorEssentia)te).linkX = stack.getTagCompound().getInteger("linkX");
/* 122 */           ((TileMirrorEssentia)te).linkY = stack.getTagCompound().getInteger("linkY");
/* 123 */           ((TileMirrorEssentia)te).linkZ = stack.getTagCompound().getInteger("linkZ");
/* 124 */           ((TileMirrorEssentia)te).linkDim = stack.getTagCompound().getInteger("linkDim");
/* 125 */           ((TileMirrorEssentia)te).restoreLink();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 130 */     return ret;
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/* 136 */     return EnumRarity.UNCOMMON;
/*     */   }
/*     */   
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public void addInformation(ItemStack item, EntityPlayer par2EntityPlayer, List list, boolean par4)
/*     */   {
/* 142 */     if (item.hasTagCompound()) {
/* 143 */       int lx = item.getTagCompound().getInteger("linkX");
/* 144 */       int ly = item.getTagCompound().getInteger("linkY");
/* 145 */       int lz = item.getTagCompound().getInteger("linkZ");
/* 146 */       int ldim = item.getTagCompound().getInteger("linkDim");
/* 147 */       String dimname = item.getTagCompound().getString("dimname");
/* 148 */       list.add("Linked to " + lx + "," + ly + "," + lz + " in " + dimname);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockMirrorItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */