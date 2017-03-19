/*     */ package thaumcraft.common.items.tools;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.tiles.devices.TileMirror;
/*     */ 
/*     */ public class ItemHandMirror
/*     */   extends Item
/*     */ {
/*     */   public ItemHandMirror()
/*     */   {
/*  35 */     setMaxStackSize(1);
/*  36 */     setHasSubtypes(false);
/*  37 */     setMaxDamage(0);
/*  38 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getShareTag()
/*     */   {
/*  44 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  50 */     return EnumRarity.UNCOMMON;
/*     */   }
/*     */   
/*     */   public boolean hasEffect(ItemStack par1ItemStack)
/*     */   {
/*  55 */     return par1ItemStack.hasTagCompound();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float par8, float par9, float par10)
/*     */   {
/*  62 */     Block bi = world.getBlockState(pos).getBlock();
/*  63 */     if (bi == BlocksTC.mirror) {
/*  64 */       if (world.isRemote) {
/*  65 */         player.swingItem();
/*  66 */         return super.onItemUseFirst(itemstack, player, world, pos, side, par8, par9, par10);
/*     */       }
/*  68 */       TileEntity tm = world.getTileEntity(pos);
/*  69 */       if ((tm != null) && ((tm instanceof TileMirror))) {
/*  70 */         itemstack.setTagInfo("linkX", new NBTTagInt(pos.getX()));
/*  71 */         itemstack.setTagInfo("linkY", new NBTTagInt(pos.getY()));
/*  72 */         itemstack.setTagInfo("linkZ", new NBTTagInt(pos.getZ()));
/*  73 */         itemstack.setTagInfo("linkDim", new NBTTagInt(world.provider.getDimensionId()));
/*  74 */         itemstack.setTagInfo("dimname", new NBTTagString(DimensionManager.getProvider(world.provider.getDimensionId()).getDimensionName()));
/*  75 */         world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:jar", 1.0F, 2.0F);
/*  76 */         player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("tc.handmirrorlinked")));
/*  77 */         player.inventoryContainer.detectAndSendChanges();
/*     */       }
/*     */       
/*  80 */       return true;
/*     */     }
/*     */     
/*  83 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*     */   {
/*  91 */     if ((!par2World.isRemote) && (par1ItemStack.hasTagCompound()))
/*     */     {
/*  93 */       int lx = par1ItemStack.getTagCompound().getInteger("linkX");
/*  94 */       int ly = par1ItemStack.getTagCompound().getInteger("linkY");
/*  95 */       int lz = par1ItemStack.getTagCompound().getInteger("linkZ");
/*  96 */       int ldim = par1ItemStack.getTagCompound().getInteger("linkDim");
/*     */       
/*  98 */       World targetWorld = MinecraftServer.getServer().worldServerForDimension(ldim);
/*  99 */       if (targetWorld == null) return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
/* 100 */       TileEntity te = targetWorld.getTileEntity(new BlockPos(lx, ly, lz));
/* 101 */       if ((te == null) || (!(te instanceof TileMirror))) {
/* 102 */         par1ItemStack.setTagCompound(null);
/* 103 */         par2World.playSoundAtEntity(par3EntityPlayer, "thaumcraft:zap", 1.0F, 0.8F);
/* 104 */         par3EntityPlayer.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("tc.handmirrorerror")));
/* 105 */         return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
/*     */       }
/*     */       
/* 108 */       par3EntityPlayer.openGui(Thaumcraft.instance, 4, par2World, MathHelper.floor_double(par3EntityPlayer.posX), MathHelper.floor_double(par3EntityPlayer.posY), MathHelper.floor_double(par3EntityPlayer.posZ));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 114 */     return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void addInformation(ItemStack item, EntityPlayer par2EntityPlayer, List list, boolean par4)
/*     */   {
/* 120 */     if (item.hasTagCompound()) {
/* 121 */       int lx = item.getTagCompound().getInteger("linkX");
/* 122 */       int ly = item.getTagCompound().getInteger("linkY");
/* 123 */       int lz = item.getTagCompound().getInteger("linkZ");
/* 124 */       int ldim = item.getTagCompound().getInteger("linkDim");
/* 125 */       String dimname = item.getTagCompound().getString("dimname");
/* 126 */       list.add(StatCollector.translateToLocal("tc.handmirrorlinkedto") + " " + lx + "," + ly + "," + lz + " in " + dimname);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean transport(ItemStack mirror, ItemStack items, EntityPlayer player, World worldObj)
/*     */   {
/* 133 */     if (mirror.hasTagCompound()) {
/* 134 */       int lx = mirror.getTagCompound().getInteger("linkX");
/* 135 */       int ly = mirror.getTagCompound().getInteger("linkY");
/* 136 */       int lz = mirror.getTagCompound().getInteger("linkZ");
/* 137 */       int ldim = mirror.getTagCompound().getInteger("linkDim");
/*     */       
/* 139 */       World targetWorld = MinecraftServer.getServer().worldServerForDimension(ldim);
/* 140 */       if (targetWorld == null) return false;
/* 141 */       TileEntity te = targetWorld.getTileEntity(new BlockPos(lx, ly, lz));
/* 142 */       if ((te == null) || (!(te instanceof TileMirror))) {
/* 143 */         mirror.setTagCompound(null);
/* 144 */         worldObj.playSoundAtEntity(player, "thaumcraft:zap", 1.0F, 0.8F);
/* 145 */         player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("tc.handmirrorerror")));
/* 146 */         return false;
/*     */       }
/* 148 */       TileMirror tm = (TileMirror)te;
/*     */       
/* 150 */       if (tm.transportDirect(items)) {
/* 151 */         items = null;
/* 152 */         worldObj.playSoundAtEntity(player, "mob.endermen.portal", 0.1F, 1.0F);
/*     */       }
/* 154 */       return true;
/*     */     }
/* 156 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemHandMirror.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */