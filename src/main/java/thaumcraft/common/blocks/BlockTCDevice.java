/*     */ package thaumcraft.common.blocks;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.ITileEntityProvider;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import thaumcraft.api.aspects.IEssentiaTransport;
/*     */ import thaumcraft.api.aura.AuraHelper;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ 
/*     */ public class BlockTCDevice
/*     */   extends BlockTC implements ITileEntityProvider
/*     */ {
/*     */   protected final Class tileClass;
/*     */   
/*     */   public BlockTCDevice(Material mat, Class tc)
/*     */   {
/*  33 */     super(mat);
/*     */     
/*  35 */     setHardness(2.0F);
/*  36 */     setResistance(20.0F);
/*     */     
/*  38 */     this.tileClass = tc;
/*  39 */     this.isBlockContainer = true;
/*     */     
/*  41 */     IBlockState bs = this.blockState.getBaseState();
/*  42 */     if ((this instanceof IBlockFacingHorizontal)) { bs.withProperty(IBlockFacingHorizontal.FACING, EnumFacing.NORTH);
/*  43 */     } else if ((this instanceof IBlockFacing)) bs.withProperty(IBlockFacing.FACING, EnumFacing.UP);
/*  44 */     if ((this instanceof IBlockEnabled)) bs.withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(true));
/*  45 */     setDefaultState(bs);
/*     */   }
/*     */   
/*     */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
/*     */   {
/*  50 */     return true;
/*     */   }
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta)
/*     */   {
/*     */     try {
/*  56 */       return (TileEntity)this.tileClass.newInstance();
/*     */     } catch (InstantiationException e) {
/*  58 */       Thaumcraft.log.catching(e);
/*     */     } catch (IllegalAccessException e) {
/*  60 */       Thaumcraft.log.catching(e);
/*     */     }
/*  62 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasTileEntity(IBlockState state)
/*     */   {
/*  67 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/*  73 */     updateState(worldIn, pos, state);
/*     */   }
/*     */   
/*  76 */   protected static boolean keepInventory = false;
/*  77 */   protected static boolean spillEssentia = true;
/*     */   
/*     */ 
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/*  82 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  84 */     if ((tileentity != null) && ((tileentity instanceof IInventory)) && (!keepInventory))
/*     */     {
/*  86 */       InventoryUtils.dropItems(worldIn, pos);
/*     */     }
/*     */     
/*  89 */     if ((tileentity != null) && ((tileentity instanceof IEssentiaTransport)) && (spillEssentia) && (!worldIn.isRemote))
/*     */     {
/*  91 */       int ess = ((IEssentiaTransport)tileentity).getEssentiaAmount(EnumFacing.UP);
/*  92 */       if (ess > 0) {
/*  93 */         AuraHelper.pollute(worldIn, pos, ess, true);
/*     */       }
/*     */     }
/*     */     
/*  97 */     super.breakBlock(worldIn, pos, state);
/*  98 */     worldIn.removeTileEntity(pos);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
/*     */   {
/* 104 */     updateState(worldIn, pos, state);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
/*     */   {
/* 110 */     super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
/* 111 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 112 */     return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
/*     */   {
/* 120 */     IBlockState bs = getDefaultState();
/* 121 */     if ((this instanceof IBlockFacingHorizontal)) bs = bs.withProperty(IBlockFacingHorizontal.FACING, placer.isSneaking() ? placer.getHorizontalFacing() : placer.getHorizontalFacing().getOpposite());
/* 122 */     if ((this instanceof IBlockFacing)) { bs = bs.withProperty(IBlockFacing.FACING, placer.isSneaking() ? BlockPistonBase.getFacingFromEntity(worldIn, pos, placer).getOpposite() : BlockPistonBase.getFacingFromEntity(worldIn, pos, placer));
/*     */     }
/* 124 */     if ((this instanceof IBlockEnabled)) bs = bs.withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(true));
/* 125 */     return bs;
/*     */   }
/*     */   
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state)
/*     */   {
/* 130 */     if ((this instanceof IBlockEnabled)) {
/* 131 */       boolean flag = !worldIn.isBlockPowered(pos);
/*     */       
/* 133 */       if (flag != ((Boolean)state.getValue(IBlockEnabled.ENABLED)).booleanValue())
/*     */       {
/* 135 */         worldIn.setBlockState(pos, state.withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(flag)), 3);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateFacing(World world, BlockPos pos, EnumFacing face) {
/* 141 */     if (((this instanceof IBlockFacing)) || ((this instanceof IBlockFacingHorizontal))) {
/* 142 */       if (face == BlockStateUtils.getFacing(world.getBlockState(pos))) return;
/* 143 */       if (((this instanceof IBlockFacingHorizontal)) && (face.getHorizontalIndex() >= 0))
/* 144 */         world.setBlockState(pos, world.getBlockState(pos).withProperty(IBlockFacingHorizontal.FACING, face), 3);
/* 145 */       if ((this instanceof IBlockFacing)) {
/* 146 */         world.setBlockState(pos, world.getBlockState(pos).withProperty(IBlockFacing.FACING, face), 3);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta)
/*     */   {
/* 153 */     IBlockState bs = getDefaultState();
/*     */     try {
/* 155 */       if ((this instanceof IBlockFacingHorizontal)) bs = bs.withProperty(IBlockFacingHorizontal.FACING, BlockStateUtils.getFacing(meta));
/* 156 */       if ((this instanceof IBlockFacing)) bs = bs.withProperty(IBlockFacing.FACING, BlockStateUtils.getFacing(meta));
/* 157 */       if ((this instanceof IBlockEnabled)) bs = bs.withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(BlockStateUtils.isEnabled(meta)));
/*     */     } catch (Exception e) {}
/* 159 */     return bs;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMetaFromState(IBlockState state)
/*     */   {
/* 165 */     byte b0 = 0;
/* 166 */     int i = (this instanceof IBlockFacing) ? b0 | ((EnumFacing)state.getValue(IBlockFacing.FACING)).getIndex() : (this instanceof IBlockFacingHorizontal) ? b0 | ((EnumFacing)state.getValue(IBlockFacingHorizontal.FACING)).getIndex() : b0;
/*     */     
/*     */ 
/*     */ 
/* 170 */     if (((this instanceof IBlockEnabled)) && (!((Boolean)state.getValue(IBlockEnabled.ENABLED)).booleanValue()))
/*     */     {
/* 172 */       i |= 0x8;
/*     */     }
/*     */     
/* 175 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   protected BlockState createBlockState()
/*     */   {
/* 181 */     ArrayList<IProperty> ip = new ArrayList();
/* 182 */     if ((this instanceof IBlockFacingHorizontal)) ip.add(IBlockFacingHorizontal.FACING);
/* 183 */     if ((this instanceof IBlockFacing)) ip.add(IBlockFacing.FACING);
/* 184 */     if ((this instanceof IBlockEnabled)) ip.add(IBlockEnabled.ENABLED);
/* 185 */     return ip.size() == 0 ? super.createBlockState() : new BlockState(this, (IProperty[])ip.toArray(new IProperty[ip.size()]));
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\BlockTCDevice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */