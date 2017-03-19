/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.IGrowable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import thaumcraft.api.ThaumcraftApiHelper;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.IEssentiaTransport;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.common.blocks.IBlockEnabled;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.fx.PacketFXBlockMist;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.lib.utils.CropUtils;
/*     */ 
/*     */ public class TileLampGrowth
/*     */   extends TileThaumcraft implements IEssentiaTransport, ITickable
/*     */ {
/*  36 */   private boolean reserve = false;
/*  37 */   public int charges = -1;
/*     */   
/*     */ 
/*     */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
/*     */   {
/*  42 */     super.onDataPacket(net, pkt);
/*  43 */     if ((this.worldObj != null) && 
/*  44 */       (this.worldObj.isRemote)) {
/*  45 */       this.worldObj.checkLightFor(EnumSkyBlock.BLOCK, getPos());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/*  52 */     if (!this.worldObj.isRemote) {
/*  53 */       if (this.charges <= 0) {
/*  54 */         if (this.reserve) {
/*  55 */           this.charges = 100;
/*  56 */           this.reserve = false;
/*  57 */           markDirty();
/*  58 */           this.worldObj.markBlockForUpdate(getPos());
/*     */         }
/*  60 */         else if (drawEssentia()) {
/*  61 */           this.charges = 100;
/*  62 */           markDirty();
/*  63 */           this.worldObj.markBlockForUpdate(getPos());
/*     */         }
/*  65 */         if (this.charges <= 0) {
/*  66 */           if (BlockStateUtils.isEnabled(getBlockMetadata()))
/*     */           {
/*  68 */             this.worldObj.setBlockState(this.pos, this.worldObj.getBlockState(getPos()).withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(false)), 3);
/*     */           }
/*     */         }
/*  71 */         else if ((!gettingPower()) && (!BlockStateUtils.isEnabled(getBlockMetadata())))
/*     */         {
/*  73 */           this.worldObj.setBlockState(this.pos, this.worldObj.getBlockState(getPos()).withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(true)), 3);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  78 */       if ((!this.reserve) && 
/*  79 */         (drawEssentia())) {
/*  80 */         this.reserve = true;
/*     */       }
/*     */       
/*     */ 
/*  84 */       if (this.charges == 0) {
/*  85 */         this.charges = -1;
/*  86 */         this.worldObj.markBlockForUpdate(getPos());
/*     */       }
/*     */       
/*  89 */       if ((!gettingPower()) && (this.charges > 0)) {
/*  90 */         updatePlant();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   boolean isPlant(BlockPos bp)
/*     */   {
/*  98 */     Block b = this.worldObj.getBlockState(bp).getBlock();
/*  99 */     boolean flag = b instanceof IGrowable;
/* 100 */     Material mat = b.getMaterial();
/* 101 */     return ((flag) || (mat == Material.cactus) || (mat == Material.plants)) && (mat != Material.grass);
/*     */   }
/*     */   
/* 104 */   int lx = 0;
/* 105 */   int ly = 0;
/* 106 */   int lz = 0;
/* 107 */   Block lid = Blocks.air;
/* 108 */   int lmd = 0;
/*     */   
/* 110 */   ArrayList<BlockPos> checklist = new ArrayList();
/*     */   
/*     */   private void updatePlant() {
/* 113 */     IBlockState bs = this.worldObj.getBlockState(new BlockPos(this.lx, this.ly, this.lz));
/* 114 */     if ((this.lid != bs.getBlock()) || (this.lmd != bs.getBlock().getMetaFromState(bs))) {
/* 115 */       EntityPlayer p = this.worldObj.getClosestPlayer(this.lx, this.ly, this.lz, 32.0D);
/* 116 */       if (p != null) {
/* 117 */         PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockMist(new BlockPos(this.lx, this.ly, this.lz), 4259648), new NetworkRegistry.TargetPoint(this.worldObj.provider.getDimensionId(), this.lx, this.ly, this.lz, 32.0D));
/*     */       }
/*     */       
/*     */ 
/* 121 */       this.lid = bs.getBlock();
/* 122 */       this.lmd = bs.getBlock().getMetaFromState(bs);
/*     */     }
/*     */     
/* 125 */     int distance = 6;
/*     */     
/* 127 */     if (this.checklist.size() == 0) {
/* 128 */       for (int a = -distance; a <= distance; a++) {
/* 129 */         for (int b = -distance; b <= distance; b++)
/* 130 */           this.checklist.add(getPos().add(a, distance, b));
/*     */       }
/* 132 */       Collections.shuffle(this.checklist, this.worldObj.rand);
/*     */     }
/*     */     
/* 135 */     int x = ((BlockPos)this.checklist.get(0)).getX();
/* 136 */     int y = ((BlockPos)this.checklist.get(0)).getY();
/* 137 */     int z = ((BlockPos)this.checklist.get(0)).getZ();
/* 138 */     this.checklist.remove(0);
/* 140 */     for (; 
/* 140 */         y >= this.pos.getY() - distance; y--) {
/* 141 */       BlockPos bp = new BlockPos(x, y, z);
/* 142 */       if ((!this.worldObj.isAirBlock(bp)) && (isPlant(bp)) && (getDistanceSq(x + 0.5D, y + 0.5D, z + 0.5D) < distance * distance) && (!CropUtils.isGrownCrop(this.worldObj, bp)) && (CropUtils.doesLampGrow(this.worldObj, bp)))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 147 */         this.charges -= 1;
/*     */         
/* 149 */         this.lx = x;
/* 150 */         this.ly = y;
/* 151 */         this.lz = z;
/* 152 */         IBlockState bs2 = this.worldObj.getBlockState(bp);
/* 153 */         this.lid = bs2.getBlock();
/* 154 */         this.lmd = bs2.getBlock().getMetaFromState(bs2);
/*     */         
/* 156 */         this.worldObj.scheduleUpdate(bp, this.lid, 1);
/* 157 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 166 */     this.reserve = nbttagcompound.getBoolean("reserve");
/* 167 */     this.charges = nbttagcompound.getInteger("charges");
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/* 173 */     nbttagcompound.setBoolean("reserve", this.reserve);
/* 174 */     nbttagcompound.setInteger("charges", this.charges);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 179 */   int drawDelay = 0;
/*     */   
/*     */   boolean drawEssentia() {
/* 182 */     if (++this.drawDelay % 5 != 0) return false;
/* 183 */     TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, getPos(), BlockStateUtils.getFacing(getBlockMetadata()));
/* 184 */     if (te != null) {
/* 185 */       IEssentiaTransport ic = (IEssentiaTransport)te;
/* 186 */       if (!ic.canOutputTo(BlockStateUtils.getFacing(getBlockMetadata()).getOpposite())) return false;
/* 187 */       if ((ic.getSuctionAmount(BlockStateUtils.getFacing(getBlockMetadata()).getOpposite()) < getSuctionAmount(BlockStateUtils.getFacing(getBlockMetadata()))) && (ic.takeEssentia(Aspect.PLANT, 1, BlockStateUtils.getFacing(getBlockMetadata()).getOpposite()) == 1))
/*     */       {
/*     */ 
/* 190 */         return true;
/*     */       }
/*     */     }
/* 193 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isConnectable(EnumFacing face)
/*     */   {
/* 198 */     return face == BlockStateUtils.getFacing(getBlockMetadata());
/*     */   }
/*     */   
/*     */   public boolean canInputFrom(EnumFacing face)
/*     */   {
/* 203 */     return face == BlockStateUtils.getFacing(getBlockMetadata());
/*     */   }
/*     */   
/*     */   public boolean canOutputTo(EnumFacing face)
/*     */   {
/* 208 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSuction(Aspect aspect, int amount) {}
/*     */   
/*     */ 
/*     */   public int getMinimumSuction()
/*     */   {
/* 217 */     return 0;
/*     */   }
/*     */   
/*     */   public Aspect getSuctionType(EnumFacing face)
/*     */   {
/* 222 */     return Aspect.PLANT;
/*     */   }
/*     */   
/*     */   public int getSuctionAmount(EnumFacing face)
/*     */   {
/* 227 */     return (face == BlockStateUtils.getFacing(getBlockMetadata())) && ((!this.reserve) || (this.charges <= 0)) ? 128 : 0;
/*     */   }
/*     */   
/*     */   public Aspect getEssentiaType(EnumFacing loc)
/*     */   {
/* 232 */     return null;
/*     */   }
/*     */   
/*     */   public int getEssentiaAmount(EnumFacing loc)
/*     */   {
/* 237 */     return 0;
/*     */   }
/*     */   
/*     */   public int takeEssentia(Aspect aspect, int amount, EnumFacing loc)
/*     */   {
/* 242 */     return 0;
/*     */   }
/*     */   
/*     */   public int addEssentia(Aspect aspect, int amount, EnumFacing loc)
/*     */   {
/* 247 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileLampGrowth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */