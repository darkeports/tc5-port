/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.codechicken.lib.raytracer.IndexedCuboid6;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.lib.aura.AuraChunk;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.lib.aura.AuraWorld;
/*     */ 
/*     */ public class TileDioptra extends TileThaumcraft implements ITickable
/*     */ {
/*  32 */   public int counter = 0;
/*     */   
/*  34 */   public byte[] grid_amt = new byte['©'];
/*  35 */   public byte[] grid_type = new byte['©'];
/*  36 */   private byte[] grid_amt_p = new byte['©'];
/*  37 */   public byte type = -1;
/*     */   
/*     */   public TileDioptra()
/*     */   {
/*  41 */     Arrays.fill(this.grid_amt, (byte)0);
/*  42 */     Arrays.fill(this.grid_type, (byte)-1);
/*  43 */     Arrays.fill(this.grid_amt_p, (byte)0);
/*     */   }
/*     */   
/*     */   @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/*  49 */     return AxisAlignedBB.fromBounds(getPos().getX() - 0.3D, getPos().getY() - 0.3D, getPos().getZ() - 0.3D, getPos().getX() + 1.3D, getPos().getY() + 2.3D, getPos().getZ() + 1.3D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void update()
/*     */   {
/*  56 */     this.counter += 1;
/*     */     
/*  58 */     if ((thaumcraft.common.lib.utils.BlockStateUtils.isEnabled(getBlockMetadata())) && (this.type >= 0)) {
/*  59 */       if (!this.worldObj.isRemote) {
/*  60 */         if (this.counter % 20 == 0) {
/*  61 */           Arrays.fill(this.grid_amt, (byte)0);
/*  62 */           Arrays.fill(this.grid_type, (byte)-1);
/*  63 */           for (int xx = 0; xx < 13; xx++) {
/*  64 */             for (int zz = 0; zz < 13; zz++) {
/*  65 */               AuraChunk ac = AuraHandler.getAuraChunk(this.worldObj.provider.getDimensionId(), (this.pos.getX() >> 4) + xx - 6, (this.pos.getZ() >> 4) + zz - 6);
/*     */               
/*  67 */               if ((ac != null) && (ac.getCurrentAspects().size() > 0)) {
/*  68 */                 switch (this.type) {
/*     */                 case 0: 
/*  70 */                   AspectList al = ac.getCurrentAspects().copy();
/*  71 */                   al.remove(Aspect.FLUX);
/*  72 */                   Aspect as = al.getAspectsSortedByAmount()[0];
/*  73 */                   this.grid_type[(xx + zz * 13)] = getAspectByte(as);
/*  74 */                   this.grid_amt[(xx + zz * 13)] = ((byte)(int)Math.min(110.0F, al.getAmount(as) / (Config.AURABASE * 1.5F) * 100.0F));
/*  75 */                   break;
/*     */                 default: 
/*  77 */                   this.grid_type[(xx + zz * 13)] = this.type;
/*  78 */                   this.grid_amt[(xx + zz * 13)] = ((byte)(int)Math.min(110.0F, ac.getCurrentAspects().getAmount(getAspect(this.type)) / (Config.AURABASE * 1.5F) * 100.0F));
/*     */                 }
/*     */                 
/*     */                 
/*  82 */                 AspectList al = (AspectList)AuraHandler.getAuraWorld(this.worldObj.provider.getDimensionId()).getNodeTickets().get(ac.getLoc());
/*     */                 
/*     */ 
/*     */ 
/*  86 */                 if ((al != null) && (al.visSize() > 0)) {
/*  87 */                   int tmp351_350 = (xx + zz * 13); byte[] tmp351_342 = this.grid_type;tmp351_342[tmp351_350] = ((byte)(tmp351_342[tmp351_350] + 10));
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*  92 */           markDirty();
/*  93 */           this.worldObj.markBlockForUpdate(getPos());
/*     */         }
/*     */       }
/*     */       else {
/*  97 */         drawFX();
/*     */       }
/*     */     } else {
/* 100 */       this.counter = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void drawFX()
/*     */   {
/* 108 */     for (int xx = 0; xx < 13; xx++) {
/* 109 */       for (int zz = 0; zz < 13; zz++) {
/* 110 */         if ((this.grid_type[(xx + zz * 13)] > 0) && (this.grid_amt[(xx + zz * 13)] > 0)) {
/* 111 */           boolean sparkle = this.grid_type[(xx + zz * 13)] > 10;
/*     */           
/* 113 */           float h = this.grid_amt[(xx + zz * 13)] / (Config.AURABASE * 1.5F);
/*     */           
/* 115 */           int i = this.grid_amt_p[(xx + zz * 13)] < this.grid_amt[(xx + zz * 13)] ? 6 : this.grid_amt_p[(xx + zz * 13)] > this.grid_amt[(xx + zz * 13)] ? 8 : 7;
/*     */           
/* 117 */           if ((i == 7) && (xx == 6) && (zz == 6)) { i = 9;
/*     */           }
/*     */           
/* 120 */           if ((sparkle) && (this.worldObj.rand.nextInt(3) == 0)) {
/* 121 */             Thaumcraft.proxy.getFX().drawGenericParticles(this.pos.getX() + 0.5D + (xx - 6) * 0.075F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.04F, this.pos.getY() + 1 + h + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.04F, this.pos.getZ() + 0.5D + (zz - 6) * 0.075F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.04F, (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.01F, this.worldObj.rand.nextFloat() * 0.01F, (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.01F, 0.7F + this.worldObj.rand.nextFloat() * 0.3F, 0.7F + this.worldObj.rand.nextFloat() * 0.3F, 0.7F + this.worldObj.rand.nextFloat() * 0.3F, 0.9F, false, 112, 9, 1, 6 + this.worldObj.rand.nextInt(4), 0, 0.25F, 0.0F, 0);
/*     */           }
/*     */         }
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
/* 136 */         this.grid_amt_p[(xx + zz * 13)] = this.grid_amt[(xx + zz * 13)];
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbt)
/*     */   {
/* 144 */     this.type = nbt.getByte("type");
/* 145 */     if (this.type > 7) { this.type = ((byte)(this.type % 8));
/*     */     }
/* 147 */     if ((nbt.hasKey("grid_t")) && (nbt.hasKey("grid_a"))) {
/* 148 */       this.grid_type = nbt.getByteArray("grid_t");
/* 149 */       this.grid_amt = nbt.getByteArray("grid_a");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeCustomNBT(NBTTagCompound nbt)
/*     */   {
/* 156 */     nbt.setByte("type", this.type);
/* 157 */     nbt.setByteArray("grid_a", this.grid_amt);
/* 158 */     nbt.setByteArray("grid_t", this.grid_type);
/*     */   }
/*     */   
/*     */   private byte getAspectByte(Aspect aspect2) {
/* 162 */     if (aspect2 == Aspect.AIR) return 1;
/* 163 */     if (aspect2 == Aspect.FIRE) return 2;
/* 164 */     if (aspect2 == Aspect.WATER) return 3;
/* 165 */     if (aspect2 == Aspect.EARTH) return 4;
/* 166 */     if (aspect2 == Aspect.ORDER) return 5;
/* 167 */     if (aspect2 == Aspect.ENTROPY) return 6;
/* 168 */     if (aspect2 == Aspect.FLUX) return 7;
/* 169 */     return 0;
/*     */   }
/*     */   
/*     */   public Aspect getAspect(byte b) {
/* 173 */     switch (b) {
/* 174 */     default:  return null;
/* 175 */     case 1:  return Aspect.AIR;
/* 176 */     case 2:  return Aspect.FIRE;
/* 177 */     case 3:  return Aspect.WATER;
/* 178 */     case 4:  return Aspect.EARTH;
/* 179 */     case 5:  return Aspect.ORDER;
/* 180 */     case 6:  return Aspect.ENTROPY; }
/* 181 */     return Aspect.FLUX;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public MovingObjectPosition rayTrace(World world, Vec3 vec3d, Vec3 vec3d1, MovingObjectPosition fullblock)
/*     */   {
/* 189 */     return fullblock;
/*     */   }
/*     */   
/*     */   public void addTraceableCuboids(List<IndexedCuboid6> cuboids)
/*     */   {
/* 194 */     cuboids.add(new IndexedCuboid6(Integer.valueOf(0), new thaumcraft.codechicken.lib.vec.Cuboid6(getPos().getX() + 0.375D, getPos().getY() + 0.875D, getPos().getZ() + 0.375D, getPos().getX() + 0.625D, getPos().getY() + 1.1D, getPos().getZ() + 0.625D)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void switchType(EntityPlayer playerIn, byte type)
/*     */   {
/* 202 */     this.grid_amt = new byte['©'];
/* 203 */     this.grid_type = new byte['©'];
/* 204 */     this.grid_amt_p = new byte['©'];
/* 205 */     if (!this.worldObj.isRemote) {
/* 206 */       this.type = type;
/* 207 */       markDirty();
/* 208 */       this.worldObj.markBlockForUpdate(getPos());
/* 209 */       if (getAspect(type) != null) {
/* 210 */         playerIn.addChatMessage(new ChatComponentText(String.format(StatCollector.translateToLocal("tc.dioptra.1"), new Object[] { getAspect(type).getName() })));
/*     */ 
/*     */       }
/* 213 */       else if (type >= 0) {
/* 214 */         playerIn.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.dioptra.2")));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileDioptra.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */