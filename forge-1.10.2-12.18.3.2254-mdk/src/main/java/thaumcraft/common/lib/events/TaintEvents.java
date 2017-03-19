/*     */ package thaumcraft.common.lib.events;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.internal.EnumWarpType;
/*     */ import thaumcraft.api.research.ResearchHelper;
/*     */ import thaumcraft.common.config.Config;
/*     */ import thaumcraft.common.entities.EntityTaintSource;
/*     */ import thaumcraft.common.entities.EntityTaintSourceCloud;
/*     */ import thaumcraft.common.entities.monster.EntityWisp;
/*     */ import thaumcraft.common.entities.monster.tainted.EntityTaintCrawler;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.lib.aura.EntityAuraNode;
/*     */ import thaumcraft.common.lib.potions.PotionInfectiousVisExhaust;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ import thaumcraft.common.lib.utils.RandomItemChooser;
/*     */ import thaumcraft.common.lib.utils.RandomItemChooser.Item;
/*     */ 
/*     */ public class TaintEvents
/*     */ {
/*     */   static class TaintItem implements RandomItemChooser.Item
/*     */   {
/*     */     int weight;
/*     */     int event;
/*     */     int cost;
/*     */     boolean nearTaintAllowed;
/*     */     
/*     */     protected TaintItem(int event, int weight, int cost, boolean nearTaintAllowed)
/*     */     {
/*  43 */       this.weight = weight;
/*  44 */       this.event = event;
/*  45 */       this.cost = cost;
/*  46 */       this.nearTaintAllowed = nearTaintAllowed;
/*     */     }
/*     */     
/*     */     public double getWeight() {
/*  50 */       return this.weight;
/*     */     }
/*     */   }
/*     */   
/*  54 */   static ArrayList<RandomItemChooser.Item> events = new ArrayList();
/*     */   
/*  56 */   static { events.add(new TaintItem(0, 25, 5, true));
/*  57 */     events.add(new TaintItem(1, 5, 25, false));
/*  58 */     events.add(new TaintItem(2, 1, 40, false));
/*  59 */     events.add(new TaintItem(3, 2, 10, true));
/*  60 */     events.add(new TaintItem(4, 5, 20, true));
/*  61 */     events.add(new TaintItem(5, 5, 15, true));
/*  62 */     events.add(new TaintItem(6, 3, 20, true));
/*     */   }
/*     */   
/*     */   protected static boolean taintEvent(World world, BlockPos pos) {
/*  66 */     RandomItemChooser ric = new RandomItemChooser();
/*  67 */     TaintItem ei = (TaintItem)ric.chooseOnWeight(events);
/*  68 */     if (ei == null) { return false;
/*     */     }
/*  70 */     pos = pos.add(world.rand.nextInt(16), 0, world.rand.nextInt(16));
/*  71 */     BlockPos p2 = world.getPrecipitationHeight(pos);
/*     */     
/*  73 */     if ((!ei.nearTaintAllowed) && (nearTaint(world, p2))) {
/*  74 */       return false;
/*     */     }
/*  76 */     if (!AuraHandler.drainAura(world, p2, Aspect.FLUX, ei.cost, false))
/*  77 */       return false;
/*  78 */     boolean didit = false;
/*  79 */     switch (ei.event) {
/*     */     case 0: 
/*  81 */       if (p2.getY() + 5 < world.getActualHeight()) {
/*  82 */         EntityWisp wisp = new EntityWisp(world);
/*  83 */         wisp.setLocationAndAngles(p2.getX(), p2.getY() + 5, p2.getZ(), 0.0F, 0.0F);
/*  84 */         if (world.rand.nextInt(3) == 0)
/*  85 */           wisp.setType(Aspect.FLUX.getTag());
/*  86 */         if (world.spawnEntityInWorld(wisp))
/*  87 */           didit = true;
/*     */       }
/*  89 */       break;
/*     */     
/*     */     case 1: 
/*  92 */       BlockPos blockpos = findSpotToStrike(world, pos);
/*  93 */       if ((world.canSeeSky(blockpos)) && 
/*  94 */         (world.spawnEntityInWorld(new thaumcraft.common.entities.EntityTaintSourceLightning(world, blockpos.getX(), blockpos.getY(), blockpos.getZ())))) {
/*  95 */         didit = true;
/*     */       }
/*     */       break;
/*     */     case 2: 
/*  99 */       pos = p2.up(20);
/* 100 */       if (pos.getY() < world.getActualHeight()) {
/* 101 */         EntityTaintSourceCloud e = new EntityTaintSourceCloud(world, pos, (30 + world.rand.nextInt(10)) * 20);
/* 102 */         if (world.spawnEntityInWorld(e))
/* 103 */           didit = true; }
/* 104 */       break;
/*     */     
/*     */     case 3: 
/* 107 */       if (p2.getY() + 1 < world.getActualHeight()) {
/* 108 */         EntityTaintCrawler crawler = new EntityTaintCrawler(world);
/* 109 */         crawler.setLocationAndAngles(p2.getX(), p2.getY() + 1, p2.getZ(), 0.0F, 0.0F);
/* 110 */         if (world.spawnEntityInWorld(crawler))
/* 111 */           didit = true; }
/* 112 */       break;
/*     */     
/*     */     case 4: 
/* 115 */       List<EntityPlayer> targets = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.fromBounds(p2.getX(), p2.getY(), p2.getZ(), p2.getX() + 1, p2.getY() + 1, p2.getZ() + 1).expand(16.0D, 16.0D, 16.0D));
/*     */       
/* 117 */       if ((targets != null) && (targets.size() > 0))
/* 118 */         for (EntityPlayer target : targets) {
/* 119 */           target.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("tc.fluxevent.1")));
/* 120 */           if (world.rand.nextFloat() < 0.25F) {
/* 121 */             ResearchHelper.addWarpToPlayer(target, 1, EnumWarpType.NORMAL);
/*     */           } else {
/* 123 */             ResearchHelper.addWarpToPlayer(target, 2 + world.rand.nextInt(4), EnumWarpType.TEMPORARY);
/*     */           }
/* 125 */           didit = true; }
/* 126 */       break;
/*     */     
/*     */ 
/*     */     case 5: 
/* 130 */       List<EntityLivingBase> targets2 = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(p2.getX(), p2.getY(), p2.getZ(), p2.getX() + 1, p2.getY() + 1, p2.getZ() + 1).expand(16.0D, 16.0D, 16.0D));
/*     */       
/* 132 */       if ((targets2 != null) && (targets2.size() > 0))
/* 133 */         for (EntityLivingBase target : targets2) {
/* 134 */           didit = true;
/* 135 */           if ((target instanceof EntityPlayer)) {
/* 136 */             target.addChatMessage(new ChatComponentText("§5§o" + StatCollector.translateToLocal("tc.fluxevent.2")));
/*     */           }
/* 138 */           PotionEffect pe = new PotionEffect(PotionInfectiousVisExhaust.instance.getId(), 3000, 2);
/* 139 */           pe.getCurativeItems().clear();
/*     */           try {
/* 141 */             target.addPotionEffect(pe);
/*     */           } catch (Exception e) {
/* 143 */             e.printStackTrace();
/*     */           } }
/* 145 */       break;
/*     */     
/*     */ 
/*     */     case 6: 
/* 149 */       List<EntityAuraNode> targets3 = world.getEntitiesWithinAABB(EntityAuraNode.class, AxisAlignedBB.fromBounds(p2.getX(), p2.getY(), p2.getZ(), p2.getX() + 1, p2.getY() + 1, p2.getZ() + 1).expand(16.0D, 16.0D, 16.0D));
/*     */       
/* 151 */       if ((targets3 != null) && (targets3.size() > 0)) {
/* 152 */         EntityAuraNode node = (EntityAuraNode)targets3.get(world.rand.nextInt(targets3.size()));
/* 153 */         if (node != null) {
/* 154 */           didit = true;
/* 155 */           node.setNodeType(world.rand.nextBoolean() ? 5 : world.rand.nextInt(thaumcraft.common.lib.aura.NodeType.nodeTypes.length));
/*     */         }
/*     */       }
/*     */       break;
/*     */     }
/* 160 */     if (didit) AuraHandler.drainAura(world, p2, Aspect.FLUX, ei.cost);
/* 161 */     return true;
/*     */   }
/*     */   
/*     */   private static BlockPos findSpotToStrike(World world, BlockPos pos)
/*     */   {
/* 166 */     BlockPos blockpos1 = world.getPrecipitationHeight(pos);
/* 167 */     AxisAlignedBB axisalignedbb = new AxisAlignedBB(blockpos1, new BlockPos(blockpos1.getX(), world.getHeight(), blockpos1.getZ())).expand(4.0D, 4.0D, 4.0D);
/* 168 */     List list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, new Predicate()
/*     */     {
/*     */       public boolean applyLiving(EntityLivingBase living)
/*     */       {
/* 172 */         return (living != null) && (living.isEntityAlive()) && (this.val$world.canSeeSky(living.getPosition()));
/*     */       }
/*     */       
/*     */       public boolean apply(Object p_apply_1_) {
/* 176 */         return applyLiving((EntityLivingBase)p_apply_1_);
/*     */       }
/* 178 */     });
/* 179 */     return !list.isEmpty() ? ((EntityLivingBase)list.get(world.rand.nextInt(list.size()))).getPosition() : blockpos1;
/*     */   }
/*     */   
/*     */   private static boolean nearTaint(World world, BlockPos pos) {
/* 183 */     for (EnumFacing d : EnumFacing.HORIZONTALS) {
/* 184 */       BiomeGenBase biome = world.getBiomeGenForCoords(pos.offset(d, 16));
/* 185 */       if (biome.biomeID == Config.biomeTaintID) {
/* 186 */         return true;
/*     */       }
/*     */     }
/* 189 */     if (EntityUtils.getEntitiesInRange(world, pos, null, EntityTaintSource.class, 32.0D).size() > 0) return true;
/* 190 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\events\TaintEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */