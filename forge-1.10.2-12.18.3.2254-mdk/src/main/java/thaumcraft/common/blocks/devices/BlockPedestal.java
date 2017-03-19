/*     */ package thaumcraft.common.blocks.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.blocks.BlockTCDevice;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ import thaumcraft.common.tiles.crafting.TilePedestal;
/*     */ 
/*     */ public class BlockPedestal extends BlockTCDevice
/*     */ {
/*  23 */   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", PedestalType.class);
/*     */   
/*     */   public BlockPedestal() {
/*  26 */     super(net.minecraft.block.material.Material.rock, TilePedestal.class);
/*  27 */     setStepSound(Block.soundTypeStone);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, PedestalType.NORMAL));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOpaqueCube()
/*     */   {
/*  34 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isFullCube()
/*     */   {
/*  40 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/*  49 */     if (world.isRemote) { return true;
/*     */     }
/*  51 */     net.minecraft.tileentity.TileEntity tile = world.getTileEntity(pos);
/*     */     
/*  53 */     if ((tile != null) && ((tile instanceof TilePedestal)))
/*     */     {
/*  55 */       TilePedestal ped = (TilePedestal)tile;
/*  56 */       if ((ped.getStackInSlot(0) == null) && (player.inventory.getCurrentItem() != null))
/*     */       {
/*  58 */         ItemStack i = player.getCurrentEquippedItem().copy();
/*  59 */         i.stackSize = 1;
/*  60 */         ped.setInventorySlotContents(0, i);
/*  61 */         player.getCurrentEquippedItem().stackSize -= 1;
/*  62 */         if (player.getCurrentEquippedItem().stackSize == 0) {
/*  63 */           player.setCurrentItemOrArmor(0, null);
/*     */         }
/*  65 */         player.inventory.markDirty();
/*  66 */         world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.6F);
/*     */         
/*     */ 
/*  69 */         return true; }
/*  70 */       if (ped.getStackInSlot(0) != null) {
/*  71 */         InventoryUtils.dropItemsAtEntity(world, pos, player);
/*  72 */         world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);
/*     */         
/*     */ 
/*  75 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  79 */     return super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/*  85 */     return getStateFromMeta(meta);
/*     */   }
/*     */   
/*     */ 
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/*  91 */     return getDefaultState().withProperty(VARIANT, PedestalType.values()[meta]);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/*  97 */     int meta = ((PedestalType)state.getValue(VARIANT)).ordinal();
/*     */     
/*  99 */     return meta;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 105 */     return new BlockState(this, new IProperty[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public IProperty[] getProperties()
/*     */   {
/* 111 */     return new IProperty[] { VARIANT };
/*     */   }
/*     */   
/*     */ 
/*     */   public String getStateName(IBlockState state, boolean fullName)
/*     */   {
/* 117 */     PedestalType type = (PedestalType)state.getValue(VARIANT);
/*     */     
/* 119 */     return (fullName ? "pedestal_" : "") + type.getName();
/*     */   }
/*     */   
/*     */   public static enum PedestalType
/*     */     implements IStringSerializable
/*     */   {
/* 125 */     NORMAL,  ELDRITCH,  ANCIENT;
/*     */     
/*     */     private PedestalType() {}
/*     */     
/*     */     public String getName() {
/* 130 */       return name().toLowerCase();
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 136 */       return getName();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\devices\BlockPedestal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */