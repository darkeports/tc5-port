/*     */ package thaumcraft.common.tiles.misc;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.ServerConfigurationManager;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.api.internal.WorldCoordinates;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.items.tools.ItemSinisterStone;
/*     */ import thaumcraft.common.lib.world.dim.TeleporterThaumcraft;
/*     */ 
/*     */ public class TileEldritchPortal extends TileThaumcraft implements ITickable
/*     */ {
/*     */   public double getMaxRenderDistanceSquared()
/*     */   {
/*  26 */     return 9216.0D;
/*     */   }
/*     */   
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/*  32 */     return AxisAlignedBB.fromBounds(getPos().getX() - 1, getPos().getY() - 1, getPos().getZ() - 1, getPos().getX() + 2, getPos().getY() + 2, getPos().getZ() + 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  38 */   public boolean open = false;
/*  39 */   public int opencount = -1;
/*  40 */   private int count = 0;
/*     */   
/*  42 */   private WorldCoordinates lastLoc = null;
/*     */   
/*     */   public void update()
/*     */   {
/*  46 */     this.count += 1;
/*     */     
/*  48 */     if ((this.worldObj.isRemote) && (this.lastLoc == null)) {
/*  49 */       this.lastLoc = new WorldCoordinates(getPos(), this.worldObj.provider.getDimensionId());
/*  50 */       ItemSinisterStone.eldritchPortals.put(this.lastLoc, Long.valueOf(System.currentTimeMillis()));
/*     */     }
/*     */     
/*  53 */     if ((this.worldObj.provider.getDimensionId() == Config.dimensionOuterId) && (!this.open)) {
/*  54 */       this.open = true;
/*     */     }
/*  56 */     if ((this.worldObj.isRemote) && (this.open) && ((this.count % 250 == 0) || (this.count == 0))) {
/*  57 */       this.worldObj.playSound(getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D, "thaumcraft:evilportal", 1.0F, 1.0F, false);
/*     */     }
/*  59 */     if ((this.worldObj.isRemote) && (this.open) && (this.opencount < 30)) {
/*  60 */       this.opencount += 1;
/*     */     }
/*  62 */     if ((!this.worldObj.isRemote) && (this.open) && (this.count % 5 == 0)) {
/*  63 */       List ents = this.worldObj.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.fromBounds(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 1, getPos().getZ() + 1).expand(0.5D, 1.0D, 0.5D));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  68 */       if (ents.size() > 0) {
/*  69 */         for (Object e : ents) {
/*  70 */           EntityPlayerMP player = (EntityPlayerMP)e;
/*  71 */           if ((player.ridingEntity == null) && (player.riddenByEntity == null))
/*     */           {
/*     */ 
/*  74 */             MinecraftServer mServer = FMLCommonHandler.instance().getMinecraftServerInstance();
/*     */             
/*  76 */             if (player.timeUntilPortal > 0)
/*     */             {
/*  78 */               player.timeUntilPortal = 100;
/*     */             }
/*  80 */             else if (player.dimension != Config.dimensionOuterId)
/*     */             {
/*  82 */               player.timeUntilPortal = 100;
/*     */               
/*  84 */               player.mcServer.getConfigurationManager().transferPlayerToDimension(player, Config.dimensionOuterId, new TeleporterThaumcraft(mServer.worldServerForDimension(Config.dimensionOuterId)));
/*     */               
/*     */ 
/*     */ 
/*  88 */               if (!ResearchHelper.isResearchComplete(player.getName(), "ENTEROUTER")) {
/*  89 */                 ResearchHelper.completeResearch(player, "ENTEROUTER");
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/*  94 */               player.timeUntilPortal = 100;
/*  95 */               player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0, new TeleporterThaumcraft(mServer.worldServerForDimension(0)));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbt)
/*     */   {
/* 107 */     this.open = nbt.getBoolean("open");
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbt)
/*     */   {
/* 112 */     nbt.setBoolean("open", this.open);
/*     */   }
/*     */   
/*     */   public void invalidate()
/*     */   {
/* 117 */     if ((this.worldObj.isRemote) && (this.lastLoc != null)) {
/* 118 */       ItemSinisterStone.eldritchPortals.remove(this.lastLoc);
/*     */     }
/* 120 */     super.invalidate();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\misc\TileEldritchPortal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */