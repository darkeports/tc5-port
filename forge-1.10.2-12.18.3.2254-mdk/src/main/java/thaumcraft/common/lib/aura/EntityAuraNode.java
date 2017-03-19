/*     */ package thaumcraft.common.lib.aura;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectHelper;
/*     */ import thaumcraft.common.config.Config;
/*     */ 
/*     */ public class EntityAuraNode extends Entity
/*     */ {
/*  22 */   private int tickCounter = -1;
/*     */   
/*     */   public EntityAuraNode(World worldIn) {
/*  25 */     super(worldIn);
/*  26 */     setSize(0.5F, 0.5F);
/*  27 */     this.isImmuneToFire = true;
/*  28 */     this.renderDistanceWeight = 4.0D;
/*  29 */     this.noClip = true;
/*     */   }
/*     */   
/*  32 */   int checkDelay = -1;
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  37 */     if (getNodeSize() == 0) randomizeNode();
/*  38 */     if (this.tickCounter < 0) { this.tickCounter = this.rand.nextInt(200);
/*     */     }
/*  40 */     this.worldObj.theProfiler.startSection("entityBaseTick");
/*     */     
/*  42 */     this.prevPosX = this.posX;
/*  43 */     this.prevPosY = this.posY;
/*  44 */     this.prevPosZ = this.posZ;
/*     */     
/*  46 */     NodeType.nodeTypes[getNodeType()].performTickEvent(this);
/*     */     
/*  48 */     if (this.tickCounter++ > 200)
/*     */     {
/*  50 */       this.tickCounter = 0;
/*  51 */       NodeType.nodeTypes[getNodeType()].performPeriodicEvent(this);
/*     */     }
/*     */     
/*  54 */     checkAdjacentNodes();
/*     */     
/*  56 */     if ((this.motionX != 0.0D) || (this.motionY != 0.0D) || (this.motionZ != 0.0D)) {
/*  57 */       this.motionX *= 0.8D;
/*  58 */       this.motionY *= 0.8D;
/*  59 */       this.motionZ *= 0.8D;
/*  60 */       super.moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     }
/*     */     
/*  63 */     this.worldObj.theProfiler.endSection();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  68 */   ArrayList<Entity> neighbours = null;
/*  69 */   public boolean stablized = false;
/*     */   
/*     */   private void checkAdjacentNodes() {
/*  72 */     if ((this.neighbours == null) || (this.checkDelay < this.ticksExisted)) {
/*  73 */       this.neighbours = thaumcraft.common.lib.utils.EntityUtils.getEntitiesInRange(this.worldObj, this.posX, this.posY, this.posZ, this, EntityAuraNode.class, 32.0D);
/*  74 */       this.checkDelay = (this.ticksExisted + 750);
/*     */     }
/*     */     
/*  77 */     if (!this.stablized) {
/*     */       try {
/*  79 */         Iterator<Entity> i = this.neighbours.iterator();
/*  80 */         while (i.hasNext()) {
/*  81 */           Entity e = (Entity)i.next();
/*  82 */           if ((e == null) || (e.isDead) || (!(e instanceof EntityAuraNode))) {
/*  83 */             i.remove();
/*     */           } else {
/*  85 */             EntityAuraNode an = (EntityAuraNode)e;
/*  86 */             if (!an.stablized) {
/*  87 */               double xd = this.posX - an.posX;
/*  88 */               double yd = this.posY - an.posY;
/*  89 */               double zd = this.posZ - an.posZ;
/*  90 */               double d = xd * xd + yd * yd + zd * zd;
/*  91 */               if ((d < (getNodeSize() + an.getNodeSize()) * 1.5D) && (d > 0.1D)) {
/*  92 */                 float tq = (getNodeSize() + an.getNodeSize()) * 1.5F;
/*  93 */                 float xm = (float)(-xd / d / tq * (an.getNodeSize() / 50.0D));
/*  94 */                 float ym = (float)(-yd / d / tq * (an.getNodeSize() / 50.0D));
/*  95 */                 float zm = (float)(-zd / d / tq * (an.getNodeSize() / 50.0D));
/*  96 */                 this.motionX += xm;
/*  97 */                 this.motionY += ym;
/*  98 */                 this.motionZ += zm;
/*  99 */               } else if ((d <= 0.1D) && (getNodeSize() >= an.getNodeSize()) && (!this.worldObj.isRemote)) {
/* 100 */                 int bonus = (int)Math.sqrt(an.getNodeSize());
/* 101 */                 int n = getNodeSize() + bonus;
/* 102 */                 setNodeSize(n);
/*     */                 
/* 104 */                 if (this.rand.nextInt(100) < Math.sqrt(an.getNodeSize())) {
/* 105 */                   Aspect a = AspectHelper.getCombinationResult(getAspect(), an.getAspect());
/* 106 */                   if (a != null) { setAspectTag(a.getTag());
/*     */                   }
/*     */                 }
/* 109 */                 if (((getNodeType() == 0) && (an.getNodeType() != 0) && (this.rand.nextInt(3) == 0)) || ((getNodeType() != 0) && (an.getNodeType() != 0) && (this.rand.nextInt(100) < Math.sqrt(an.getNodeSize() / 2))))
/*     */                 {
/* 111 */                   setNodeType(an.getNodeType());
/*     */                 }
/* 113 */                 an.setDead();
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/* 121 */     if (this.ticksExisted % 50 == 0) {
/* 122 */       this.stablized = ((this.worldObj.getBlockState(getPosition().down()).getBlock() == thaumcraft.api.blocks.BlocksTC.nodeStabilizer) && (!this.worldObj.isBlockPowered(getPosition().down())));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getNodeSize()
/*     */   {
/* 131 */     return this.dataWatcher.getWatchableObjectInt(8);
/*     */   }
/*     */   
/*     */   public void setNodeSize(int p)
/*     */   {
/* 136 */     this.dataWatcher.updateObject(8, Integer.valueOf(p));
/*     */   }
/*     */   
/*     */   public String getAspectTag() {
/* 140 */     return this.dataWatcher.getWatchableObjectString(9);
/*     */   }
/*     */   
/*     */   public Aspect getAspect() {
/* 144 */     return Aspect.getAspect(getAspectTag());
/*     */   }
/*     */   
/*     */   public void setAspectTag(String t) {
/* 148 */     this.dataWatcher.updateObject(9, String.valueOf(t));
/*     */   }
/*     */   
/*     */   public int getNodeType() {
/* 152 */     return this.dataWatcher.getWatchableObjectByte(10);
/*     */   }
/*     */   
/*     */   public void setNodeType(int p)
/*     */   {
/* 157 */     this.dataWatcher.updateObject(10, Byte.valueOf((byte)net.minecraft.util.MathHelper.clamp_int(p, 0, NodeType.nodeTypes.length - 1)));
/*     */   }
/*     */   
/*     */   protected void entityInit()
/*     */   {
/* 162 */     this.dataWatcher.addObject(8, Integer.valueOf(0));
/* 163 */     this.dataWatcher.addObject(9, String.valueOf(""));
/* 164 */     this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void randomizeNode() {
/* 168 */     setNodeSize(2 + Config.AURABASE / 3 + this.rand.nextInt(2 + Config.AURABASE / 3));
/*     */     
/* 170 */     if ((this.rand.nextInt(Config.specialNodeRarity) == 0) && (NodeType.nodeTypes.length > 1)) {
/* 171 */       setNodeType(1 + this.rand.nextInt(NodeType.nodeTypes.length - 1));
/* 172 */       if ((getNodeType() == 4) && (this.rand.nextFloat() < 0.33D)) setNodeType(0);
/* 173 */       if ((!Config.genTaint) && (getNodeType() == 4)) setNodeType(0);
/*     */     } else {
/* 175 */       setNodeType(0);
/*     */     }
/*     */     
/* 178 */     ArrayList<Aspect> al = Aspect.getPrimalAspects();
/* 179 */     if (this.rand.nextInt(20) == 0) {
/* 180 */       al = Aspect.getCompoundAspects();
/*     */     }
/* 182 */     setAspectTag(((Aspect)al.get(this.rand.nextInt(al.size()))).getTag());
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isPushedByWater()
/*     */   {
/* 188 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isImmuneToExplosions()
/*     */   {
/* 194 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hitByEntity(Entity entityIn)
/*     */   {
/* 200 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean attackEntityFrom(DamageSource source, float amount)
/*     */   {
/* 206 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addVelocity(double x, double y, double z) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void moveEntity(double x, double y, double z) {}
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean shouldSetPosAfterLoading()
/*     */   {
/* 222 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private void onBroken(Entity entity) {}
/*     */   
/*     */ 
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompound)
/*     */   {
/* 231 */     setNodeSize(tagCompound.getInteger("size"));
/* 232 */     setNodeType(tagCompound.getByte("type"));
/* 233 */     setAspectTag(tagCompound.getString("aspect"));
/*     */   }
/*     */   
/*     */ 
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound)
/*     */   {
/* 239 */     tagCompound.setInteger("size", getNodeSize());
/* 240 */     tagCompound.setByte("type", (byte)getNodeType());
/* 241 */     tagCompound.setString("aspect", getAspectTag());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setPositionAndRotation2(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_)
/*     */   {
/* 249 */     setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
/* 250 */     setRotation(p_180426_7_, p_180426_8_);
/*     */   }
/*     */   
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean canRenderOnFire()
/*     */   {
/* 257 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\aura\EntityAuraNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */