/*     */ package thaumcraft.common.entities.projectile;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
/*     */ import thaumcraft.common.items.armor.Hover;
/*     */ 
/*     */ public class EntityGrapple
/*     */   extends EntityThrowable implements IEntityAdditionalSpawnData
/*     */ {
/*     */   private int potency;
/*  20 */   public boolean sticky = false;
/*     */   EntityLivingBase cthrower;
/*     */   
/*     */   public EntityGrapple(World par1World) {
/*  24 */     super(par1World);
/*  25 */     setSize(0.1F, 0.1F);
/*     */   }
/*     */   
/*     */   public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
/*     */   {
/*  30 */     super.setThrowableHeading(x, y, z, velocity, 0.0F);
/*     */   }
/*     */   
/*     */   public EntityGrapple(World par1World, EntityLivingBase par2EntityLiving, int potency, boolean sticky)
/*     */   {
/*  35 */     super(par1World, par2EntityLiving);
/*  36 */     this.potency = potency;
/*  37 */     this.sticky = sticky;
/*  38 */     setSize(0.1F, 0.1F);
/*     */   }
/*     */   
/*     */   public EntityGrapple(World par1World, double par2, double par4, double par6)
/*     */   {
/*  43 */     super(par1World, par2, par4, par6);
/*  44 */     setSize(0.1F, 0.1F);
/*     */   }
/*     */   
/*     */   public void writeSpawnData(ByteBuf data)
/*     */   {
/*  49 */     int id = -1;
/*  50 */     if (getThrower() != null) id = getThrower().getEntityId();
/*  51 */     data.writeInt(id);
/*  52 */     data.writeByte(this.potency);
/*  53 */     data.writeBoolean(this.sticky);
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf data)
/*     */   {
/*  58 */     int id = data.readInt();
/*  59 */     this.potency = data.readByte();
/*  60 */     this.sticky = data.readBoolean();
/*     */     try {
/*  62 */       if (id >= 0) this.cthrower = ((EntityLivingBase)this.worldObj.getEntityByID(id));
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */   public EntityLivingBase getThrower()
/*     */   {
/*  69 */     if (this.cthrower != null) return this.cthrower;
/*  70 */     return super.getThrower();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected float getVelocity()
/*     */   {
/*  80 */     return 3.0F;
/*     */   }
/*     */   
/*     */ 
/*     */   protected float getGravityVelocity()
/*     */   {
/*  86 */     return getPulling() ? 0.0F : 0.03F;
/*     */   }
/*     */   
/*     */ 
/*     */   public void entityInit()
/*     */   {
/*  92 */     super.entityInit();
/*  93 */     this.dataWatcher.addObject(17, new Byte((byte)0));
/*     */   }
/*     */   
/*     */ 
/*  97 */   boolean p = false;
/*     */   boolean boost;
/*     */   
/*     */   public void setPulling() {
/* 101 */     this.p = true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getPulling()
/*     */   {
/* 107 */     return this.p;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 112 */   int prevDist = 0;
/* 113 */   int count = 0;
/* 114 */   boolean added = false;
/*     */   
/* 116 */   public float ampl = 0.0F;
/*     */   
/* 118 */   private static HashMap<Integer, Integer> grapples = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 124 */     super.onUpdate();
/*     */     
/* 126 */     if ((!getPulling()) && (!this.isDead) && ((this.ticksExisted > 10 + this.potency * 5) || (getThrower() == null))) {
/* 127 */       setDead();
/*     */     }
/*     */     
/* 130 */     if (getThrower() != null)
/*     */     {
/* 132 */       if ((!this.worldObj.isRemote) && (!this.isDead) && (!this.added)) {
/* 133 */         if (grapples.containsKey(Integer.valueOf(getThrower().getEntityId()))) {
/* 134 */           int ii = ((Integer)grapples.get(Integer.valueOf(getThrower().getEntityId()))).intValue();
/* 135 */           if (ii != getEntityId()) {
/* 136 */             Entity e = this.worldObj.getEntityByID(ii);
/* 137 */             if (e != null) e.setDead();
/*     */           }
/*     */         }
/* 140 */         grapples.put(Integer.valueOf(getThrower().getEntityId()), Integer.valueOf(getEntityId()));
/* 141 */         this.added = true;
/*     */       }
/*     */       
/* 144 */       double dis = getThrower().getDistanceToEntity(this);
/* 145 */       if ((getPulling()) && (!this.isDead)) {
/* 146 */         if (((!this.sticky) && (dis < 2.0D)) || (getThrower().isSneaking()) || ((!this.sticky) && (this.count > 20))) {
/* 147 */           setDead();
/*     */         }
/*     */         else {
/* 150 */           if ((!this.worldObj.isRemote) && ((getThrower() instanceof EntityPlayerMP))) {
/* 151 */             Hover.resetFloatCounter((EntityPlayerMP)getThrower());
/*     */           }
/* 153 */           getThrower().fallDistance = 0.0F;
/* 154 */           double mx = this.posX - getThrower().posX;
/* 155 */           double my = this.posY - getThrower().posY;
/* 156 */           double mz = this.posZ - getThrower().posZ;
/* 157 */           double dd = dis;
/* 158 */           if ((this.sticky) && (dis < 8.0D))
/* 159 */             dd = dis * (8.0D - dis);
/* 160 */           mx /= dd * 5.0D;
/* 161 */           my /= dd * 5.0D;
/* 162 */           mz /= dd * 5.0D;
/* 163 */           Vec3 v2 = new Vec3(mx, my, mz);
/* 164 */           if (v2.lengthVector() > 0.25D) {
/* 165 */             v2 = v2.normalize();
/* 166 */             mx = v2.xCoord / 4.0D;
/* 167 */             my = v2.yCoord / 4.0D;
/* 168 */             mz = v2.zCoord / 4.0D;
/*     */           }
/* 170 */           getThrower().motionX += mx;
/* 171 */           getThrower().motionY += my + 0.033D;
/* 172 */           getThrower().motionZ += mz;
/* 173 */           if (!this.boost) {
/* 174 */             getThrower().motionY += 0.4000000059604645D;
/* 175 */             this.boost = true;
/*     */           }
/* 177 */           int d = (int)(dis / 2.0D);
/* 178 */           if (d == this.prevDist) {
/* 179 */             this.count += 1;
/*     */           } else {
/* 181 */             this.count = 0;
/*     */           }
/* 183 */           this.prevDist = d;
/*     */         }
/*     */       }
/*     */       
/* 187 */       if (this.worldObj.isRemote) {
/* 188 */         if (!getPulling()) {
/* 189 */           this.ampl += 0.02F;
/*     */         } else {
/* 191 */           this.ampl *= 0.66F;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void onImpact(MovingObjectPosition mop)
/*     */   {
/* 201 */     setPulling();
/* 202 */     this.motionX = 0.0D;
/* 203 */     this.motionY = 0.0D;
/* 204 */     this.motionZ = 0.0D;
/* 205 */     this.posX = mop.hitVec.xCoord;
/* 206 */     this.posY = mop.hitVec.yCoord;
/* 207 */     this.posZ = mop.hitVec.zCoord;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\projectile\EntityGrapple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */