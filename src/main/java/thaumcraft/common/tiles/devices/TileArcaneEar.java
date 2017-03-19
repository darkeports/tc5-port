/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.WeakHashMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import thaumcraft.common.blocks.IBlockEnabled;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ 
/*     */ public class TileArcaneEar extends TileEntity implements ITickable
/*     */ {
/*  20 */   public byte note = 0;
/*  21 */   public byte tone = 0;
/*  22 */   public int redstoneSignal = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/*  29 */     super.writeToNBT(par1NBTTagCompound);
/*  30 */     par1NBTTagCompound.setByte("note", this.note);
/*  31 */     par1NBTTagCompound.setByte("tone", this.tone);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/*  39 */     super.readFromNBT(par1NBTTagCompound);
/*  40 */     this.note = par1NBTTagCompound.getByte("note");
/*  41 */     this.tone = par1NBTTagCompound.getByte("tone");
/*     */     
/*  43 */     if (this.note < 0)
/*     */     {
/*  45 */       this.note = 0;
/*     */     }
/*     */     
/*  48 */     if (this.note > 24)
/*     */     {
/*  50 */       this.note = 24;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/*  57 */     if (!this.worldObj.isRemote) {
/*  58 */       if (this.redstoneSignal > 0) {
/*  59 */         this.redstoneSignal -= 1;
/*  60 */         if (this.redstoneSignal == 0) {
/*  61 */           EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata()).getOpposite();
/*  62 */           TileEntity tileentity = this.worldObj.getTileEntity(this.pos);
/*  63 */           this.worldObj.setBlockState(this.pos, this.worldObj.getBlockState(this.pos).withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(false)), 3);
/*  64 */           if (tileentity != null)
/*     */           {
/*  66 */             tileentity.validate();
/*  67 */             this.worldObj.setTileEntity(this.pos, tileentity);
/*     */           }
/*  69 */           this.worldObj.notifyBlockOfStateChange(this.pos, getBlockType());
/*  70 */           this.worldObj.notifyBlockOfStateChange(this.pos.offset(facing), getBlockType());
/*  71 */           this.worldObj.markBlockForUpdate(this.pos);
/*     */         }
/*     */       }
/*  74 */       ArrayList<Integer[]> nbe = (ArrayList)noteBlockEvents.get(Integer.valueOf(this.worldObj.provider.getDimensionId()));
/*  75 */       if (nbe != null) {
/*  76 */         for (Integer[] dat : nbe) {
/*  77 */           if ((dat[3].intValue() == this.tone) && (dat[4].intValue() == this.note) && 
/*  78 */             (getDistanceSq(dat[0].intValue() + 0.5D, dat[1].intValue() + 0.5D, dat[2].intValue() + 0.5D) <= 4096.0D)) {
/*  79 */             EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata()).getOpposite();
/*  80 */             triggerNote(this.worldObj, this.pos, false);
/*  81 */             this.redstoneSignal = 10;
/*  82 */             TileEntity tileentity = this.worldObj.getTileEntity(this.pos);
/*  83 */             this.worldObj.setBlockState(this.pos, this.worldObj.getBlockState(this.pos).withProperty(IBlockEnabled.ENABLED, Boolean.valueOf(true)), 3);
/*  84 */             if (tileentity != null)
/*     */             {
/*  86 */               tileentity.validate();
/*  87 */               this.worldObj.setTileEntity(this.pos, tileentity);
/*     */             }
/*  89 */             this.worldObj.notifyBlockOfStateChange(this.pos, getBlockType());
/*  90 */             this.worldObj.notifyBlockOfStateChange(this.pos.offset(facing), getBlockType());
/*  91 */             this.worldObj.markBlockForUpdate(this.pos);
/*  92 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 100 */   public static WeakHashMap<Integer, ArrayList<Integer[]>> noteBlockEvents = new WeakHashMap();
/*     */   
/*     */   public void updateTone() {
/*     */     try {
/* 104 */       EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata()).getOpposite();
/* 105 */       Material var5 = this.worldObj.getBlockState(this.pos.offset(facing)).getBlock().getMaterial();
/* 106 */       this.tone = 0;
/* 107 */       if (var5 == Material.rock)
/*     */       {
/* 109 */         this.tone = 1;
/*     */       }
/*     */       
/* 112 */       if (var5 == Material.sand)
/*     */       {
/* 114 */         this.tone = 2;
/*     */       }
/*     */       
/* 117 */       if (var5 == Material.glass)
/*     */       {
/* 119 */         this.tone = 3;
/*     */       }
/*     */       
/* 122 */       if (var5 == Material.wood)
/*     */       {
/* 124 */         this.tone = 4;
/*     */       }
/* 126 */       markDirty();
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void changePitch()
/*     */   {
/* 136 */     this.note = ((byte)((this.note + 1) % 25));
/* 137 */     markDirty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void triggerNote(World world, BlockPos pos, boolean sound)
/*     */   {
/* 145 */     byte var6 = -1;
/* 146 */     if (sound) {
/* 147 */       EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata()).getOpposite();
/* 148 */       Material var5 = world.getBlockState(pos.offset(facing)).getBlock().getMaterial();
/* 149 */       var6 = 0;
/* 150 */       if (var5 == Material.rock)
/*     */       {
/* 152 */         var6 = 1;
/*     */       }
/*     */       
/* 155 */       if (var5 == Material.sand)
/*     */       {
/* 157 */         var6 = 2;
/*     */       }
/*     */       
/* 160 */       if (var5 == Material.glass)
/*     */       {
/* 162 */         var6 = 3;
/*     */       }
/*     */       
/* 165 */       if (var5 == Material.wood)
/*     */       {
/* 167 */         var6 = 4;
/*     */       }
/*     */     }
/* 170 */     world.addBlockEvent(pos, getBlockType(), var6, this.note);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileArcaneEar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */