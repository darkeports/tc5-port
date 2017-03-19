/*     */ package thaumcraft.common.entities.construct;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemNameTag;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.management.PreYggdrasilConverter;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.CombatTracker;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.GameRules;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityOwnedConstruct extends EntityCreature implements IEntityOwnable
/*     */ {
/*     */   public EntityOwnedConstruct(World worldIn)
/*     */   {
/*  27 */     super(worldIn);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*  33 */     super.entityInit();
/*  34 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*  35 */     this.dataWatcher.addObject(17, "");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int decreaseAirSupply(int air)
/*     */   {
/*  44 */     return air;
/*     */   }
/*     */   
/*     */   public boolean canBreatheUnderwater()
/*     */   {
/*  49 */     return true;
/*     */   }
/*     */   
/*     */   protected String getLivingSound()
/*     */   {
/*  54 */     return "thaumcraft:cameraclack";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/*  60 */     return "thaumcraft:cameraclack";
/*     */   }
/*     */   
/*     */ 
/*     */   protected String getDeathSound()
/*     */   {
/*  66 */     return "thaumcraft:tool";
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTalkInterval()
/*     */   {
/*  72 */     return 240;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean canDespawn()
/*     */   {
/*  78 */     return false;
/*     */   }
/*     */   
/*     */   public void onUpdate()
/*     */   {
/*  83 */     super.onUpdate();
/*     */     
/*  85 */     if ((getAttackTarget() != null) && (isOnSameTeam(getAttackTarget()))) { setAttackTarget(null);
/*     */     }
/*  87 */     if ((!this.worldObj.isRemote) && (!this.validSpawn)) {
/*  88 */       setDead();
/*     */     }
/*     */   }
/*     */   
/*  92 */   boolean validSpawn = false;
/*     */   
/*     */   public void setValidSpawn() {
/*  95 */     this.validSpawn = true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound)
/*     */   {
/* 101 */     super.writeEntityToNBT(tagCompound);
/*     */     
/* 103 */     tagCompound.setBoolean("v", this.validSpawn);
/*     */     
/* 105 */     if (getOwnerId() == null)
/*     */     {
/* 107 */       tagCompound.setString("OwnerUUID", "");
/*     */     }
/*     */     else
/*     */     {
/* 111 */       tagCompound.setString("OwnerUUID", getOwnerId());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompound)
/*     */   {
/* 118 */     super.readEntityFromNBT(tagCompound);
/*     */     
/* 120 */     this.validSpawn = tagCompound.getBoolean("v");
/*     */     
/* 122 */     String s = "";
/*     */     
/* 124 */     if (tagCompound.hasKey("OwnerUUID", 8))
/*     */     {
/* 126 */       s = tagCompound.getString("OwnerUUID");
/*     */     }
/*     */     else
/*     */     {
/* 130 */       String s1 = tagCompound.getString("Owner");
/* 131 */       s = PreYggdrasilConverter.getStringUUIDFromName(s1);
/*     */     }
/*     */     
/* 134 */     if (s.length() > 0)
/*     */     {
/* 136 */       setOwnerId(s);
/* 137 */       setOwned(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isOwned()
/*     */   {
/* 145 */     return (this.dataWatcher.getWatchableObjectByte(16) & 0x4) != 0;
/*     */   }
/*     */   
/*     */   public void setOwned(boolean tamed)
/*     */   {
/* 150 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 152 */     if (tamed)
/*     */     {
/* 154 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x4)));
/*     */     }
/*     */     else
/*     */     {
/* 158 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFB)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String getOwnerId()
/*     */   {
/* 165 */     return this.dataWatcher.getWatchableObjectString(17);
/*     */   }
/*     */   
/*     */   public void setOwnerId(String ownerUuid)
/*     */   {
/* 170 */     this.dataWatcher.updateObject(17, ownerUuid);
/*     */   }
/*     */   
/*     */   public EntityLivingBase getOwnerEntity()
/*     */   {
/*     */     try
/*     */     {
/* 177 */       UUID uuid = UUID.fromString(getOwnerId());
/* 178 */       return uuid == null ? null : this.worldObj.getPlayerEntityByUUID(uuid);
/*     */     }
/*     */     catch (IllegalArgumentException illegalargumentexception) {}
/*     */     
/* 182 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOwner(EntityLivingBase entityIn)
/*     */   {
/* 188 */     return entityIn == getOwnerEntity();
/*     */   }
/*     */   
/*     */ 
/*     */   public Team getTeam()
/*     */   {
/* 194 */     if (isOwned())
/*     */     {
/* 196 */       EntityLivingBase entitylivingbase = getOwnerEntity();
/*     */       
/* 198 */       if (entitylivingbase != null)
/*     */       {
/* 200 */         return entitylivingbase.getTeam();
/*     */       }
/*     */     }
/*     */     
/* 204 */     return super.getTeam();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity)
/*     */   {
/* 210 */     if (isOwned())
/*     */     {
/* 212 */       EntityLivingBase entitylivingbase1 = getOwnerEntity();
/*     */       
/* 214 */       if (otherEntity == entitylivingbase1)
/*     */       {
/* 216 */         return true;
/*     */       }
/*     */       
/* 219 */       if (entitylivingbase1 != null)
/*     */       {
/* 221 */         return entitylivingbase1.isOnSameTeam(otherEntity);
/*     */       }
/*     */     }
/*     */     
/* 225 */     return super.isOnSameTeam(otherEntity);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDeath(DamageSource cause)
/*     */   {
/* 231 */     if ((!this.worldObj.isRemote) && (this.worldObj.getGameRules().getBoolean("showDeathMessages")) && (hasCustomName()) && ((getOwnerEntity() instanceof EntityPlayerMP))) {
/* 232 */       ((EntityPlayerMP)getOwnerEntity()).addChatMessage(getCombatTracker().getDeathMessage());
/*     */     }
/*     */     
/* 235 */     super.onDeath(cause);
/*     */   }
/*     */   
/*     */ 
/*     */   public Entity getOwner()
/*     */   {
/* 241 */     return getOwnerEntity();
/*     */   }
/*     */   
/*     */   public boolean interact(EntityPlayer player)
/*     */   {
/* 246 */     if ((player.isSneaking()) || ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof ItemNameTag)))) return false;
/* 247 */     if ((!this.worldObj.isRemote) && (!isOwner(player))) {
/* 248 */       player.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("tc.notowned")));
/* 249 */       return true;
/*     */     }
/*     */     
/* 252 */     return super.interact(player);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\EntityOwnedConstruct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */