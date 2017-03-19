/*     */ package thaumcraft.common.entities.construct.golem.seals;
/*     */ 
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import thaumcraft.api.golems.seals.ISeal;
/*     */ import thaumcraft.api.golems.seals.ISealConfigArea;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles.SealToggle;
/*     */ import thaumcraft.api.golems.seals.SealPos;
/*     */ import thaumcraft.api.golems.tasks.Task;
/*     */ import thaumcraft.common.entities.construct.golem.tasks.TaskHandler;
/*     */ import thaumcraft.common.lib.network.PacketHandler;
/*     */ import thaumcraft.common.lib.network.misc.PacketSealToClient;
/*     */ 
/*     */ public class SealEntity implements thaumcraft.api.golems.seals.ISealEntity
/*     */ {
/*     */   SealPos sealPos;
/*     */   ISeal seal;
/*  22 */   byte priority = 0;
/*  23 */   byte color = 0;
/*  24 */   boolean locked = false;
/*  25 */   boolean redstone = false;
/*  26 */   String owner = "";
/*     */   
/*     */ 
/*     */   public SealEntity() {}
/*     */   
/*     */   public SealEntity(World world, SealPos sealPos, ISeal seal)
/*     */   {
/*  33 */     this.sealPos = sealPos;
/*  34 */     this.seal = seal;
/*  35 */     if ((seal instanceof ISealConfigArea)) {
/*  36 */       int x = sealPos.face.getFrontOffsetX() == 0 ? 3 : 1;
/*  37 */       int y = sealPos.face.getFrontOffsetY() == 0 ? 3 : 1;
/*  38 */       int z = sealPos.face.getFrontOffsetZ() == 0 ? 3 : 1;
/*  39 */       this.area = new BlockPos(x, y, z);
/*     */     }
/*     */   }
/*     */   
/*     */   public void tickSealEntity(World world)
/*     */   {
/*  45 */     if (this.seal != null) {
/*  46 */       if (isStoppedByRedstone(world)) {
/*  47 */         if (!this.stopped) {
/*  48 */           for (Task t : TaskHandler.getTasks(world.provider.getDimensionId()).values()) {
/*  49 */             if ((t.getSealPos() != null) && (t.getSealPos().equals(this.sealPos))) {
/*  50 */               t.setSuspended(true);
/*     */             }
/*     */           }
/*     */         }
/*  54 */         this.stopped = true;
/*  55 */         return;
/*     */       }
/*  57 */       this.stopped = false;
/*  58 */       this.seal.tickSeal(world, this);
/*     */     } }
/*     */   
/*  61 */   boolean stopped = false;
/*     */   
/*     */   public boolean isStoppedByRedstone(World world)
/*     */   {
/*  65 */     return (isRedstoneSensitive()) && ((world.isBlockPowered(getSealPos().pos)) || (world.isBlockPowered(getSealPos().pos.offset(getSealPos().face))));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ISeal getSeal()
/*     */   {
/*  72 */     return this.seal;
/*     */   }
/*     */   
/*     */   public SealPos getSealPos()
/*     */   {
/*  77 */     return this.sealPos;
/*     */   }
/*     */   
/*     */   public byte getPriority()
/*     */   {
/*  82 */     return this.priority;
/*     */   }
/*     */   
/*     */   public void setPriority(byte priority)
/*     */   {
/*  87 */     this.priority = priority;
/*     */   }
/*     */   
/*     */   public byte getColor()
/*     */   {
/*  92 */     return this.color;
/*     */   }
/*     */   
/*     */   public void setColor(byte color)
/*     */   {
/*  97 */     this.color = color;
/*     */   }
/*     */   
/*     */   public String getOwner()
/*     */   {
/* 102 */     return this.owner;
/*     */   }
/*     */   
/*     */   public void setOwner(String owner)
/*     */   {
/* 107 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public boolean isLocked()
/*     */   {
/* 112 */     return this.locked;
/*     */   }
/*     */   
/*     */   public void setLocked(boolean locked)
/*     */   {
/* 117 */     this.locked = locked;
/*     */   }
/*     */   
/*     */   public boolean isRedstoneSensitive() {
/* 121 */     return this.redstone;
/*     */   }
/*     */   
/*     */   public void setRedstoneSensitive(boolean redstone) {
/* 125 */     this.redstone = redstone;
/*     */   }
/*     */   
/*     */ 
/*     */   public void readNBT(NBTTagCompound nbt)
/*     */   {
/* 131 */     BlockPos p = BlockPos.fromLong(nbt.getLong("pos"));
/* 132 */     EnumFacing face = EnumFacing.VALUES[nbt.getByte("face")];
/* 133 */     this.sealPos = new SealPos(p, face);
/* 134 */     setPriority(nbt.getByte("priority"));
/* 135 */     setColor(nbt.getByte("color"));
/* 136 */     setLocked(nbt.getBoolean("locked"));
/* 137 */     setRedstoneSensitive(nbt.getBoolean("redstone"));
/* 138 */     setOwner(nbt.getString("owner"));
/*     */     try {
/* 140 */       this.seal = ((ISeal)SealHandler.getSeal(nbt.getString("type")).getClass().newInstance());
/*     */     } catch (Exception e) {}
/* 142 */     if (this.seal != null) {
/* 143 */       this.seal.readCustomNBT(nbt);
/* 144 */       if ((this.seal instanceof ISealConfigArea)) {
/* 145 */         this.area = BlockPos.fromLong(nbt.getLong("area"));
/*     */       }
/* 147 */       if ((this.seal instanceof ISealConfigToggles)) {
/* 148 */         for (ISealConfigToggles.SealToggle prop : ((ISealConfigToggles)this.seal).getToggles()) {
/* 149 */           if (nbt.hasKey(prop.getKey())) {
/* 150 */             prop.setValue(nbt.getBoolean(prop.getKey()));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagCompound writeNBT()
/*     */   {
/* 159 */     NBTTagCompound nbt = new NBTTagCompound();
/* 160 */     nbt.setLong("pos", this.sealPos.pos.toLong());
/* 161 */     nbt.setByte("face", (byte)this.sealPos.face.ordinal());
/* 162 */     nbt.setString("type", this.seal.getKey());
/* 163 */     nbt.setByte("priority", getPriority());
/* 164 */     nbt.setByte("color", getColor());
/* 165 */     nbt.setBoolean("locked", isLocked());
/* 166 */     nbt.setBoolean("redstone", isRedstoneSensitive());
/* 167 */     nbt.setString("owner", getOwner());
/* 168 */     if (this.seal != null) {
/* 169 */       this.seal.writeCustomNBT(nbt);
/* 170 */       if ((this.seal instanceof ISealConfigArea)) {
/* 171 */         nbt.setLong("area", this.area.toLong());
/*     */       }
/* 173 */       if ((this.seal instanceof ISealConfigToggles)) {
/* 174 */         for (ISealConfigToggles.SealToggle prop : ((ISealConfigToggles)this.seal).getToggles()) {
/* 175 */           nbt.setBoolean(prop.getKey(), prop.getValue());
/*     */         }
/*     */       }
/*     */     }
/* 179 */     return nbt;
/*     */   }
/*     */   
/*     */   public void syncToClient(World world)
/*     */   {
/* 184 */     if (!world.isRemote) {
/* 185 */       PacketHandler.INSTANCE.sendToDimension(new PacketSealToClient(this), world.provider.getDimensionId());
/*     */     }
/*     */   }
/*     */   
/* 189 */   private BlockPos area = new BlockPos(1, 1, 1);
/*     */   
/*     */   public BlockPos getArea()
/*     */   {
/* 193 */     return this.area;
/*     */   }
/*     */   
/*     */   public void setArea(BlockPos v)
/*     */   {
/* 198 */     this.area = v;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\seals\SealEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */