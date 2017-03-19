/*     */ package thaumcraft.common.lib.world.dim;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.Teleporter;
/*     */ import net.minecraft.world.Teleporter.PortalPosition;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ 
/*     */ 
/*     */ public class TeleporterThaumcraft
/*     */   extends Teleporter
/*     */ {
/*     */   private final WorldServer worldServerInstance;
/*     */   private final Random random;
/*  24 */   private static final LongHashMap destinationCoordinateCache = new LongHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  30 */   private static final List destinationCoordinateKeys = new ArrayList();
/*     */   
/*     */   public TeleporterThaumcraft(WorldServer par1WorldServer)
/*     */   {
/*  34 */     super(par1WorldServer);
/*  35 */     this.worldServerInstance = par1WorldServer;
/*  36 */     this.random = new Random(par1WorldServer.getSeed());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void placeInPortal(Entity entityIn, float rotationYaw)
/*     */   {
/*  45 */     if (this.worldServerInstance.provider.getDimensionId() != 1)
/*     */     {
/*  47 */       if (!placeInExistingPortal(entityIn, rotationYaw))
/*     */       {
/*  49 */         makePortal(entityIn);
/*  50 */         placeInExistingPortal(entityIn, rotationYaw);
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*  55 */     else if (!placeInExistingPortal(entityIn, rotationYaw))
/*     */     {
/*  57 */       int i = MathHelper.floor_double(entityIn.posX);
/*  58 */       int k = MathHelper.floor_double(entityIn.posZ);
/*  59 */       BlockPos j = this.worldServerInstance.getHeight(new BlockPos(i, 0, k));
/*  60 */       byte b0 = 1;
/*  61 */       byte b1 = 0;
/*  62 */       entityIn.setLocationAndAngles(i, j.getY() + 4.0D, k, entityIn.rotationYaw, 0.0F);
/*  63 */       entityIn.motionX = (entityIn.motionY = entityIn.motionZ = 0.0D);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
/*     */   {
/*  75 */     short short1 = 128;
/*  76 */     double d3 = -1.0D;
/*  77 */     Object object = BlockPos.ORIGIN;
/*  78 */     int l = MathHelper.floor_double(entityIn.posX);
/*  79 */     int i1 = MathHelper.floor_double(entityIn.posZ);
/*  80 */     int chunkX = l >> 4;
/*  81 */     int chunkZ = i1 >> 4;
/*  82 */     String hs = chunkX + ":" + chunkZ + ":" + this.worldServerInstance.provider.getDimensionId();
/*  83 */     long j1 = hs.hashCode();
/*  84 */     boolean flag = true;
/*     */     
/*     */ 
/*     */ 
/*  88 */     if (destinationCoordinateCache.containsItem(j1))
/*     */     {
/*  90 */       Teleporter.PortalPosition portalposition = (Teleporter.PortalPosition)destinationCoordinateCache.getValueByKey(j1);
/*  91 */       d3 = 0.0D;
/*  92 */       object = portalposition;
/*  93 */       portalposition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
/*  94 */       flag = false;
/*     */     }
/*     */     else
/*     */     {
/*  98 */       for (int k1 = l - short1; k1 <= l + short1; k1++)
/*     */       {
/* 100 */         double d5 = k1 + 0.5D - entityIn.posX;
/*     */         
/* 102 */         for (int l1 = i1 - short1; l1 <= i1 + short1; l1++)
/*     */         {
/* 104 */           double d6 = l1 + 0.5D - entityIn.posZ;
/*     */           
/* 106 */           for (int i2 = this.worldServerInstance.getActualHeight() - 1; i2 >= 0; i2--)
/*     */           {
/* 108 */             BlockPos blockpos = new BlockPos(k1, i2, l1);
/* 109 */             if (this.worldServerInstance.getBlockState(blockpos) == BlocksTC.eldritch.getStateFromMeta(6))
/*     */             {
/*     */ 
/* 112 */               double d4 = i2 + 0.5D - entityIn.posY;
/* 113 */               double d7 = d5 * d5 + d4 * d4 + d6 * d6;
/*     */               
/* 115 */               if ((d3 < 0.0D) || (d7 < d3))
/*     */               {
/* 117 */                 d3 = d7;
/* 118 */                 object = blockpos;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 126 */     if (d3 >= 0.0D)
/*     */     {
/* 128 */       if (flag)
/*     */       {
/* 130 */         destinationCoordinateCache.add(j1, new Teleporter.PortalPosition(this, (BlockPos)object, this.worldServerInstance.getTotalWorldTime()));
/* 131 */         destinationCoordinateKeys.add(Long.valueOf(j1));
/*     */       }
/*     */       
/* 134 */       double d8 = ((BlockPos)object).getX() + 0.5D + (this.worldServerInstance.rand.nextBoolean() ? 1 : -1);
/* 135 */       double d9 = ((BlockPos)object).getY();
/* 136 */       double d4 = ((BlockPos)object).getZ() + 0.5D + (this.worldServerInstance.rand.nextBoolean() ? 1 : -1);
/*     */       
/* 138 */       entityIn.motionX = (entityIn.motionY = entityIn.motionZ = 0.0D);
/*     */       
/* 140 */       entityIn.setLocationAndAngles(d8, d9, d4, entityIn.rotationYaw, entityIn.rotationPitch);
/* 141 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 145 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean makePortal(Entity par1Entity)
/*     */   {
/* 152 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeStalePortalLocations(long par1)
/*     */   {
/* 162 */     if (par1 % 100L == 0L)
/*     */     {
/* 164 */       Iterator iterator = destinationCoordinateKeys.iterator();
/* 165 */       long j = par1 - 600L;
/*     */       
/* 167 */       while (iterator.hasNext())
/*     */       {
/* 169 */         Long olong = (Long)iterator.next();
/* 170 */         Teleporter.PortalPosition portalposition = (Teleporter.PortalPosition)destinationCoordinateCache.getValueByKey(olong.longValue());
/*     */         
/* 172 */         if ((portalposition == null) || (portalposition.lastUpdateTime < j))
/*     */         {
/* 174 */           iterator.remove();
/* 175 */           destinationCoordinateCache.remove(olong.longValue());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\dim\TeleporterThaumcraft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */