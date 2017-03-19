/*     */ package thaumcraft.common.lib.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraftforge.event.world.ChunkDataEvent.Load;
/*     */ import net.minecraftforge.event.world.ChunkDataEvent.Save;
/*     */ import net.minecraftforge.event.world.ChunkWatchEvent.Watch;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.golems.seals.ISealEntity;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.construct.golem.seals.SealEntity;
/*     */ import thaumcraft.common.entities.construct.golem.seals.SealHandler;
/*     */ import thaumcraft.common.lib.aura.AuraChunk;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ 
/*     */ public class ChunkEvents
/*     */ {
/*     */   @SubscribeEvent
/*     */   public void chunkSave(ChunkDataEvent.Save event)
/*     */   {
/*  28 */     int dim = event.world.provider.getDimensionId();
/*  29 */     ChunkCoordIntPair loc = event.getChunk().getChunkCoordIntPair();
/*     */     
/*  31 */     NBTTagCompound nbt = new NBTTagCompound();
/*  32 */     event.getData().setTag("Thaumcraft", nbt);
/*  33 */     nbt.setBoolean(Config.regenKey, true);
/*     */     
/*     */ 
/*  36 */     AuraChunk ac = AuraHandler.getAuraChunk(dim, loc.chunkXPos, loc.chunkZPos);
/*  37 */     if (ac != null) {
/*  38 */       nbt.setShort("base", ac.getBase());
/*  39 */       ac.getCurrentAspects().writeToNBT(nbt, "current");
/*  40 */       if (!event.getChunk().isLoaded()) {
/*  41 */         AuraHandler.removeAuraChunk(dim, loc.chunkXPos, loc.chunkZPos);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  47 */     NBTTagList tagList = new NBTTagList();
/*  48 */     for (ISealEntity seal : SealHandler.getSealsInChunk(event.world, loc)) {
/*  49 */       NBTTagCompound sealnbt = seal.writeNBT();
/*  50 */       tagList.appendTag(sealnbt);
/*  51 */       if (!event.getChunk().isLoaded()) {
/*  52 */         SealHandler.removeSealEntity(event.world, seal.getSealPos(), true);
/*     */       }
/*     */     }
/*     */     
/*  56 */     nbt.setTag("seals", tagList);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void chunkLoad(ChunkDataEvent.Load event)
/*     */   {
/*  62 */     int dim = event.world.provider.getDimensionId();
/*  63 */     ChunkCoordIntPair loc = event.getChunk().getChunkCoordIntPair();
/*     */     
/*     */ 
/*  66 */     if (event.getData().getCompoundTag("Thaumcraft").hasKey("base")) {
/*  67 */       NBTTagCompound nbt = event.getData().getCompoundTag("Thaumcraft");
/*  68 */       short base = nbt.getShort("base");
/*  69 */       AspectList current = new AspectList();
/*  70 */       current.readFromNBT(nbt, "current");
/*  71 */       AuraHandler.addAuraChunk(dim, event.getChunk(), base, current);
/*     */     } else {
/*  73 */       AuraHandler.generateAura(event.getChunk(), event.world.rand);
/*     */     }
/*     */     
/*     */ 
/*  77 */     if (event.getData().getCompoundTag("Thaumcraft").hasKey("seals")) {
/*  78 */       NBTTagCompound nbt = event.getData().getCompoundTag("Thaumcraft");
/*  79 */       NBTTagList tagList = nbt.getTagList("seals", 10);
/*  80 */       for (int a = 0; a < tagList.tagCount(); a++) {
/*  81 */         NBTTagCompound tasknbt = tagList.getCompoundTagAt(a);
/*  82 */         SealEntity seal = new SealEntity();
/*  83 */         seal.readNBT(tasknbt);
/*  84 */         SealHandler.addSealEntity(event.world, seal);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  90 */     if ((!event.getData().getCompoundTag("Thaumcraft").hasKey(Config.regenKey)) && ((Config.regenAmber) || (Config.regenAura) || (Config.regenCinnibar) || (Config.regenCrystals) || (Config.regenStructure) || (Config.regenTrees)))
/*     */     {
/*  92 */       thaumcraft.common.Thaumcraft.log.warn("World gen was never run for chunk at " + event.getChunk().getChunkCoordIntPair() + ". Adding to queue for regeneration.");
/*  93 */       ArrayList<thaumcraft.common.lib.world.ChunkLoc> chunks = (ArrayList)ServerTickEventsFML.chunksToGenerate.get(Integer.valueOf(dim));
/*  94 */       if (chunks == null) {
/*  95 */         ServerTickEventsFML.chunksToGenerate.put(Integer.valueOf(dim), new ArrayList());
/*  96 */         chunks = (ArrayList)ServerTickEventsFML.chunksToGenerate.get(Integer.valueOf(dim));
/*     */       }
/*  98 */       if (chunks != null) {
/*  99 */         chunks.add(new thaumcraft.common.lib.world.ChunkLoc(loc.chunkXPos, loc.chunkZPos));
/* 100 */         ServerTickEventsFML.chunksToGenerate.put(Integer.valueOf(dim), chunks);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @SubscribeEvent
/*     */   public void chunkWatch(ChunkWatchEvent.Watch event)
/*     */   {
/* 112 */     for (ISealEntity seal : SealHandler.getSealsInChunk(event.player.worldObj, event.chunk)) {
/* 113 */       thaumcraft.common.lib.network.PacketHandler.INSTANCE.sendTo(new thaumcraft.common.lib.network.misc.PacketSealToClient(seal), event.player);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\events\ChunkEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */