/*     */ package thaumcraft.common.entities;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ 
/*     */ public class EntityFollowingItem extends EntitySpecialItem implements net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData
/*     */ {
/*  14 */   double targetX = 0.0D;
/*  15 */   double targetY = 0.0D;
/*  16 */   double targetZ = 0.0D;
/*  17 */   int type = 3;
/*  18 */   public Entity target = null;
/*  19 */   int age = 20;
/*  20 */   public double gravity = 0.03999999910593033D;
/*     */   
/*     */   public EntityFollowingItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack)
/*     */   {
/*  24 */     super(par1World);
/*  25 */     setSize(0.25F, 0.25F);
/*  26 */     setPosition(par2, par4, par6);
/*  27 */     setEntityItemStack(par8ItemStack);
/*  28 */     this.rotationYaw = ((float)(Math.random() * 360.0D));
/*     */   }
/*     */   
/*     */ 
/*     */   public EntityFollowingItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack, Entity target, int t)
/*     */   {
/*  34 */     this(par1World, par2, par4, par6, par8ItemStack);
/*  35 */     this.target = target;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  40 */     this.noClip = true;
/*     */   }
/*     */   
/*     */   public EntityFollowingItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack, double tx, double ty, double tz)
/*     */   {
/*  45 */     this(par1World, par2, par4, par6, par8ItemStack);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EntityFollowingItem(World par1World)
/*     */   {
/*  53 */     super(par1World);
/*  54 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  60 */     if (this.target != null) {
/*  61 */       this.targetX = this.target.posX;
/*  62 */       this.targetY = (this.target.getEntityBoundingBox().minY + this.target.height / 2.0F);
/*  63 */       this.targetZ = this.target.posZ;
/*     */     }
/*     */     
/*  66 */     if ((this.targetX != 0.0D) || (this.targetY != 0.0D) || (this.targetZ != 0.0D)) {
/*  67 */       float xd = (float)(this.targetX - this.posX);
/*  68 */       float yd = (float)(this.targetY - this.posY);
/*  69 */       float zd = (float)(this.targetZ - this.posZ);
/*  70 */       if (this.age > 1) { this.age -= 1;
/*     */       }
/*  72 */       double distance = net.minecraft.util.MathHelper.sqrt_float(xd * xd + yd * yd + zd * zd);
/*     */       
/*  74 */       if (distance > 0.5D) {
/*  75 */         distance *= this.age;
/*  76 */         this.motionX = (xd / distance);
/*  77 */         this.motionY = (yd / distance);
/*  78 */         this.motionZ = (zd / distance);
/*     */       } else {
/*  80 */         this.motionX *= 0.10000000149011612D;
/*  81 */         this.motionY *= 0.10000000149011612D;
/*  82 */         this.motionZ *= 0.10000000149011612D;
/*  83 */         this.targetX = 0.0D;
/*  84 */         this.targetY = 0.0D;
/*  85 */         this.targetZ = 0.0D;
/*  86 */         this.target = null;
/*  87 */         this.noClip = false;
/*     */       }
/*  89 */       if (this.worldObj.isRemote) {
/*  90 */         float h = (float)((getEntityBoundingBox().maxY - getEntityBoundingBox().minY) / 2.0D) + net.minecraft.util.MathHelper.sin(getAge() / 10.0F + this.hoverStart) * 0.1F + 0.1F;
/*     */         
/*     */ 
/*  93 */         if (this.type != 10) {
/*  94 */           Thaumcraft.proxy.getFX().sparkle((float)this.prevPosX + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.125F, (float)this.prevPosY + h + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.125F, (float)this.prevPosZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.125F, this.type);
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*  99 */           Thaumcraft.proxy.getFX().crucibleBubble((float)this.prevPosX + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.125F, (float)this.prevPosY + h + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.125F, (float)this.prevPosZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.125F, 0.33F, 0.33F, 1.0F);
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */     }
/*     */     else
/*     */     {
/* 107 */       this.motionY -= this.gravity;
/*     */     }
/*     */     
/* 110 */     super.onUpdate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 120 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 121 */     par1NBTTagCompound.setShort("type", (short)this.type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*     */   {
/* 129 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 130 */     this.type = par1NBTTagCompound.getShort("type");
/*     */   }
/*     */   
/*     */   public void writeSpawnData(ByteBuf data)
/*     */   {
/* 135 */     if (this.target != null) {
/* 136 */       data.writeInt(this.target == null ? -1 : this.target.getEntityId());
/* 137 */       data.writeDouble(this.targetX);
/* 138 */       data.writeDouble(this.targetY);
/* 139 */       data.writeDouble(this.targetZ);
/* 140 */       data.writeByte(this.type);
/*     */     }
/*     */   }
/*     */   
/*     */   public void readSpawnData(ByteBuf data)
/*     */   {
/*     */     try {
/* 147 */       int ent = data.readInt();
/* 148 */       if (ent > -1) {
/* 149 */         this.target = this.worldObj.getEntityByID(ent);
/*     */       }
/* 151 */       this.targetX = data.readDouble();
/* 152 */       this.targetY = data.readDouble();
/* 153 */       this.targetZ = data.readDouble();
/* 154 */       this.type = data.readByte();
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\EntityFollowingItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */