/*     */ package thaumcraft.common.tiles.misc;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.common.entities.monster.EntityEldritchGuardian;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultist;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultistCleric;
/*     */ import thaumcraft.common.entities.monster.cult.EntityCultistKnight;
/*     */ import thaumcraft.common.lib.world.dim.MazeHandler;
/*     */ import thaumcraft.common.lib.world.dim.MazeThread;
/*     */ 
/*     */ public class TileEldritchAltar extends TileThaumcraft implements ITickable
/*     */ {
/*  24 */   private boolean spawner = false;
/*  25 */   private boolean open = false;
/*  26 */   private boolean spawnedClerics = false;
/*  27 */   private byte spawnType = 0;
/*     */   
/*  29 */   private byte eyes = 0;
/*     */   
/*     */   public void readCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  33 */     setEyes(nbttagcompound.getByte("eyes"));
/*  34 */     setOpen(nbttagcompound.getBoolean("open"));
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  39 */     nbttagcompound.setByte("eyes", getEyes());
/*  40 */     nbttagcompound.setBoolean("open", isOpen());
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  45 */     super.readFromNBT(nbttagcompound);
/*  46 */     this.spawnedClerics = nbttagcompound.getBoolean("spawnedClerics");
/*  47 */     this.spawner = nbttagcompound.getBoolean("spawner");
/*  48 */     this.spawnType = nbttagcompound.getByte("spawntype");
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbttagcompound)
/*     */   {
/*  53 */     super.writeToNBT(nbttagcompound);
/*  54 */     nbttagcompound.setBoolean("spawnedClerics", this.spawnedClerics);
/*  55 */     nbttagcompound.setBoolean("spawner", this.spawner);
/*  56 */     nbttagcompound.setByte("spawntype", this.spawnType);
/*     */   }
/*     */   
/*     */   public double getMaxRenderDistanceSquared()
/*     */   {
/*  61 */     return 9216.0D;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/*  67 */     return AxisAlignedBB.fromBounds(getPos().getX() - 0.3D, getPos().getY() - 0.3D, getPos().getZ() - 0.3D, getPos().getX() + 1.3D, getPos().getY() + 1.3D, getPos().getZ() + 1.3D);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isSpawner()
/*     */   {
/*  73 */     return this.spawner;
/*     */   }
/*     */   
/*     */   public void setSpawner(boolean spawner) {
/*  77 */     this.spawner = spawner;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  82 */   private int counter = 0;
/*     */   
/*     */ 
/*     */   public void update()
/*     */   {
/*  87 */     if ((!this.worldObj.isRemote) && (isSpawner()) && (this.counter++ >= 80) && (this.counter % 40 == 0))
/*     */     {
/*  89 */       switch (this.spawnType) {
/*     */       case 0: 
/*  91 */         if (!this.spawnedClerics) {
/*  92 */           spawnClerics();
/*     */         } else {
/*  94 */           spawnGuards();
/*     */         }
/*  96 */         break;
/*     */       case 1: 
/*  98 */         spawnGuardian();
/*     */       }
/*     */       
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void spawnGuards()
/*     */   {
/* 110 */     List ents = this.worldObj.getEntitiesWithinAABB(EntityCultistCleric.class, AxisAlignedBB.fromBounds(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 1, getPos().getZ() + 1).expand(24.0D, 16.0D, 24.0D));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 116 */     if (ents.size() < 1) {
/* 117 */       setSpawner(false);
/* 118 */       return;
/*     */     }
/*     */     
/* 121 */     ents = this.worldObj.getEntitiesWithinAABB(EntityCultist.class, AxisAlignedBB.fromBounds(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 1, getPos().getZ() + 1).expand(24.0D, 16.0D, 24.0D));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 126 */     if (ents.size() < 8) {
/* 127 */       EntityCultistKnight eg = new EntityCultistKnight(this.worldObj);
/* 128 */       int i1 = getPos().getX() + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 4, 10) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
/* 129 */       int j1 = getPos().getY() + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 0, 3) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
/* 130 */       int k1 = getPos().getZ() + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 4, 10) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
/*     */       
/* 132 */       if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(i1, j1 - 1, k1)))
/*     */       {
/* 134 */         eg.setPosition(i1, j1, k1);
/*     */         
/* 136 */         if ((this.worldObj.checkNoEntityCollision(eg.getEntityBoundingBox())) && (this.worldObj.getCollidingBoundingBoxes(eg, eg.getEntityBoundingBox()).isEmpty()) && (!this.worldObj.isAnyLiquid(eg.getEntityBoundingBox())))
/*     */         {
/*     */ 
/*     */ 
/* 140 */           eg.onInitialSpawn(this.worldObj.getDifficultyForLocation(this.pos), (IEntityLivingData)null);
/* 141 */           eg.setHomePosAndDistance(this.pos, 16);
/* 142 */           this.worldObj.spawnEntityInWorld(eg);
/* 143 */           eg.spawnExplosionParticle();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void spawnGuardian()
/*     */   {
/* 151 */     EntityEldritchGuardian eg = new EntityEldritchGuardian(this.worldObj);
/* 152 */     int i1 = getPos().getX() + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 4, 10) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
/* 153 */     int j1 = getPos().getY() + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 0, 3) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
/* 154 */     int k1 = getPos().getZ() + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 4, 10) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
/*     */     
/* 156 */     if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(i1, j1 - 1, k1)))
/*     */     {
/* 158 */       eg.setPosition(i1, j1, k1);
/*     */       
/* 160 */       if ((this.worldObj.checkNoEntityCollision(eg.getEntityBoundingBox())) && (this.worldObj.getCollidingBoundingBoxes(eg, eg.getEntityBoundingBox()).isEmpty()) && (!this.worldObj.isAnyLiquid(eg.getEntityBoundingBox())) && (eg.getCanSpawnHere()))
/*     */       {
/*     */ 
/*     */ 
/* 164 */         eg.onInitialSpawn(this.worldObj.getDifficultyForLocation(this.pos), (IEntityLivingData)null);
/* 165 */         eg.spawnExplosionParticle();
/* 166 */         eg.setHomePosAndDistance(this.pos, 16);
/* 167 */         this.worldObj.spawnEntityInWorld(eg);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void spawnClerics()
/*     */   {
/* 174 */     int success = 0;
/* 175 */     for (int a = 0; a < 4; a++) {
/* 176 */       int xx = 0;
/* 177 */       int zz = 0;
/* 178 */       switch (a) {
/* 179 */       case 0:  xx = -2;zz = -2; break;
/* 180 */       case 1:  xx = -2;zz = 2; break;
/* 181 */       case 2:  xx = 2;zz = -2; break;
/* 182 */       case 3:  xx = 2;zz = 2;
/*     */       }
/* 184 */       EntityCultistCleric cleric = new EntityCultistCleric(this.worldObj);
/* 185 */       if (World.doesBlockHaveSolidTopSurface(this.worldObj, this.pos.add(xx, -1, zz)))
/*     */       {
/* 187 */         cleric.setPosition(getPos().getX() + 0.5D + xx, getPos().getY(), getPos().getZ() + 0.5D + zz);
/*     */         
/* 189 */         if ((this.worldObj.checkNoEntityCollision(cleric.getEntityBoundingBox())) && (this.worldObj.getCollidingBoundingBoxes(cleric, cleric.getEntityBoundingBox()).isEmpty()) && (!this.worldObj.isAnyLiquid(cleric.getEntityBoundingBox())))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 194 */           cleric.setHomePosAndDistance(this.pos, 8);
/* 195 */           cleric.onInitialSpawn(this.worldObj.getDifficultyForLocation(this.pos), (IEntityLivingData)null);
/*     */           
/* 197 */           if (this.worldObj.spawnEntityInWorld(cleric)) {
/* 198 */             success++;
/* 199 */             cleric.setIsRitualist(true);
/* 200 */             cleric.spawnExplosionParticle();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 205 */     if (success > 2) {
/* 206 */       this.spawnedClerics = true;
/* 207 */       markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */   public byte getSpawnType()
/*     */   {
/* 213 */     return this.spawnType;
/*     */   }
/*     */   
/*     */   public void setSpawnType(byte spawnType) {
/* 217 */     this.spawnType = spawnType;
/*     */   }
/*     */   
/*     */   public byte getEyes() {
/* 221 */     return this.eyes;
/*     */   }
/*     */   
/*     */   public void setEyes(byte eyes) {
/* 225 */     this.eyes = eyes;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 229 */     return this.open;
/*     */   }
/*     */   
/*     */   public void setOpen(boolean open) {
/* 233 */     this.open = open;
/*     */   }
/*     */   
/*     */   public boolean checkForMaze() {
/* 237 */     int w = 15 + this.worldObj.rand.nextInt(8) * 2;
/* 238 */     int h = 15 + this.worldObj.rand.nextInt(8) * 2;
/* 239 */     if (!MazeHandler.mazesInRange(this.pos.getX() >> 4, this.pos.getZ() >> 4, w, h)) {
/* 240 */       Thread t = new Thread(new MazeThread(this.pos.getY() >> 4, this.pos.getZ() >> 4, w, h, this.worldObj.rand.nextLong()));
/* 241 */       t.start();
/* 242 */       return false;
/*     */     }
/* 244 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\misc\TileEldritchAltar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */