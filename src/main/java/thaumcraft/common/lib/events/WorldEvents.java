/*    */ package thaumcraft.common.lib.events;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.WeakHashMap;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraftforge.event.world.BlockEvent.MultiPlaceEvent;
/*    */ import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
/*    */ import net.minecraftforge.event.world.NoteBlockEvent.Play;
/*    */ import net.minecraftforge.event.world.WorldEvent.Load;
/*    */ import net.minecraftforge.event.world.WorldEvent.Save;
/*    */ import net.minecraftforge.event.world.WorldEvent.Unload;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import thaumcraft.common.lib.aura.AuraHandler;
/*    */ import thaumcraft.common.lib.world.dim.Cell;
/*    */ import thaumcraft.common.lib.world.dim.MazeHandler;
/*    */ import thaumcraft.common.tiles.devices.TileArcaneEar;
/*    */ 
/*    */ public class WorldEvents
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void worldLoad(WorldEvent.Load event)
/*    */   {
/* 26 */     if (!event.world.isRemote) {
/* 27 */       if (event.world.provider.getDimensionId() == 0) { MazeHandler.loadMaze(event.world);
/*    */       }
/* 29 */       AuraHandler.addAuraWorld(event.world.provider.getDimensionId());
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void worldSave(WorldEvent.Save event) {
/* 35 */     if ((!event.world.isRemote) && 
/* 36 */       (event.world.provider.getDimensionId() == 0)) { MazeHandler.saveMaze(event.world);
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void worldUnload(WorldEvent.Unload event)
/*    */   {
/* 43 */     if (event.world.isRemote) { return;
/*    */     }
/* 45 */     thaumcraft.common.entities.construct.golem.seals.SealHandler.sealEntities.remove(Integer.valueOf(event.world.provider.getDimensionId()));
/*    */     
/* 47 */     AuraHandler.removeAuraWorld(event.world.provider.getDimensionId());
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void placeBlockEvent(BlockEvent.PlaceEvent event)
/*    */   {
/* 53 */     if (isNearActiveBoss(event.world, event.player, event.pos.getX(), event.pos.getY(), event.pos.getZ())) event.setCanceled(true);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void placeBlockEvent(BlockEvent.MultiPlaceEvent event) {
/* 58 */     if (isNearActiveBoss(event.world, event.player, event.pos.getX(), event.pos.getY(), event.pos.getZ())) event.setCanceled(true);
/*    */   }
/*    */   
/*    */   private boolean isNearActiveBoss(World world, EntityPlayer player, int x, int y, int z) {
/* 62 */     if ((world.provider.getDimensionId() == thaumcraft.common.config.Config.dimensionOuterId) && (player != null) && (!player.capabilities.isCreativeMode)) {
/* 63 */       int xx = x >> 4;
/* 64 */       int zz = z >> 4;
/* 65 */       Cell c = MazeHandler.getFromHashMap(new thaumcraft.common.lib.world.dim.CellLoc(xx, zz));
/* 66 */       if ((c != null) && (c.feature >= 2) && (c.feature <= 5)) {
/* 67 */         ArrayList<net.minecraft.entity.Entity> list = thaumcraft.common.lib.utils.EntityUtils.getEntitiesInRange(world, x, y, z, null, thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss.class, 32.0D);
/* 68 */         if ((list != null) && (list.size() > 0)) return true;
/*    */       }
/*    */     }
/* 71 */     return false;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void noteEvent(NoteBlockEvent.Play event)
/*    */   {
/* 77 */     if (event.world.isRemote) { return;
/*    */     }
/* 79 */     if (!TileArcaneEar.noteBlockEvents.containsKey(Integer.valueOf(event.world.provider.getDimensionId()))) {
/* 80 */       TileArcaneEar.noteBlockEvents.put(Integer.valueOf(event.world.provider.getDimensionId()), new ArrayList());
/*    */     }
/*    */     
/* 83 */     ArrayList<Integer[]> list = (ArrayList)TileArcaneEar.noteBlockEvents.get(Integer.valueOf(event.world.provider.getDimensionId()));
/*    */     
/* 85 */     list.add(new Integer[] { Integer.valueOf(event.pos.getX()), Integer.valueOf(event.pos.getY()), Integer.valueOf(event.pos.getZ()), Integer.valueOf(event.instrument.ordinal()), Integer.valueOf(event.getVanillaNoteId()) });
/*    */     
/* 87 */     TileArcaneEar.noteBlockEvents.put(Integer.valueOf(event.world.provider.getDimensionId()), list);
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\events\WorldEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */