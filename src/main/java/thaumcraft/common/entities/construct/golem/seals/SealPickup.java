/*     */ package thaumcraft.common.entities.construct.golem.seals;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import thaumcraft.api.golems.EnumGolemTrait;
/*     */ import thaumcraft.api.golems.GolemHelper;
/*     */ import thaumcraft.api.golems.IGolemAPI;
/*     */ import thaumcraft.api.golems.seals.ISealConfigArea;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles.SealToggle;
/*     */ import thaumcraft.api.golems.seals.ISealEntity;
/*     */ import thaumcraft.api.golems.tasks.Task;
/*     */ import thaumcraft.common.entities.construct.golem.tasks.TaskHandler;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ 
/*     */ public class SealPickup extends SealFiltered implements ISealConfigArea
/*     */ {
/*     */   public String getKey()
/*     */   {
/*  31 */     return "Thaumcraft:pickup";
/*     */   }
/*     */   
/*  34 */   int delay = new Random(System.nanoTime()).nextInt(100);
/*  35 */   static HashMap<Integer, HashMap<Integer, Integer>> itemEntities = new HashMap();
/*     */   
/*     */   public void tickSeal(World world, ISealEntity seal)
/*     */   {
/*  39 */     if (this.delay++ % 20 != 0) return;
/*  40 */     if (!itemEntities.containsKey(Integer.valueOf(world.provider.getDimensionId()))) {
/*  41 */       itemEntities.put(Integer.valueOf(world.provider.getDimensionId()), new HashMap());
/*     */     }
/*     */     
/*  44 */     AxisAlignedBB area = GolemHelper.getBoundsForArea(seal);
/*     */     
/*  46 */     List list = world.getEntitiesWithinAABB(EntityItem.class, area);
/*  47 */     if (list.size() > 0) {
/*  48 */       for (Object e : list) {
/*  49 */         EntityItem ent = (EntityItem)e;
/*  50 */         if ((ent != null) && (ent.onGround) && (!ent.cannotPickup()) && (ent.getEntityItem() != null) && (!((HashMap)itemEntities.get(Integer.valueOf(world.provider.getDimensionId()))).containsValue(Integer.valueOf(ent.getEntityId()))))
/*     */         {
/*  52 */           ItemStack stack = InventoryUtils.findFirstMatchFromFilter(this.filter, isBlacklist(), new ItemStack[] { ent.getEntityItem() }, !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value);
/*     */           
/*  54 */           if (stack != null) {
/*  55 */             Task task = new Task(seal.getSealPos(), ent);
/*  56 */             task.setPriority(seal.getPriority());
/*  57 */             ((HashMap)itemEntities.get(Integer.valueOf(world.provider.getDimensionId()))).put(Integer.valueOf(task.getId()), Integer.valueOf(ent.getEntityId()));
/*  58 */             TaskHandler.addTask(world.provider.getDimensionId(), task);
/*  59 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  66 */     if (this.delay % 100 != 0) {
/*  67 */       HashMap<Integer, Integer> hm = (HashMap)itemEntities.get(Integer.valueOf(world.provider.getDimensionId()));
/*  68 */       Iterator<Integer> it = hm.values().iterator();
/*  69 */       while (it.hasNext()) {
/*  70 */         Entity e = world.getEntityByID(((Integer)it.next()).intValue());
/*  71 */         if ((e == null) || (e.isDead)) {
/*     */           try {
/*  73 */             it.remove();
/*     */           } catch (Exception e1) {}
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onTaskCompletion(World world, IGolemAPI golem, Task task) {
/*  81 */     if (!itemEntities.containsKey(Integer.valueOf(world.provider.getDimensionId()))) {
/*  82 */       itemEntities.put(Integer.valueOf(world.provider.getDimensionId()), new HashMap());
/*     */     }
/*  84 */     EntityItem ei = getItemEntity(world, task);
/*  85 */     if ((ei != null) && (ei.getEntityItem() != null)) {
/*  86 */       ItemStack stack = InventoryUtils.findFirstMatchFromFilter(this.filter, isBlacklist(), new ItemStack[] { ei.getEntityItem() }, !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value);
/*     */       
/*  88 */       if (stack != null) {
/*  89 */         ItemStack is = golem.holdItem(ei.getEntityItem());
/*  90 */         if ((is != null) && (is.stackSize > 0)) {
/*  91 */           ei.setEntityItemStack(is);
/*     */         }
/*  93 */         if ((is == null) || (is.stackSize <= 0)) {
/*  94 */           ei.setDead();
/*     */         }
/*     */         
/*  97 */         world.playSoundAtEntity((Entity)golem, "random.pop", 0.125F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*  98 */         golem.swingArm();
/*     */       }
/*     */     }
/* 101 */     task.setSuspended(true);
/* 102 */     ((HashMap)itemEntities.get(Integer.valueOf(world.provider.getDimensionId()))).remove(Integer.valueOf(task.getId()));
/* 103 */     return true;
/*     */   }
/*     */   
/*     */   protected EntityItem getItemEntity(World world, Task task) {
/* 107 */     Integer ei = (Integer)((HashMap)itemEntities.get(Integer.valueOf(world.provider.getDimensionId()))).get(Integer.valueOf(task.getId()));
/* 108 */     if (ei != null) {
/* 109 */       Entity ent = world.getEntityByID(ei.intValue());
/* 110 */       if ((ent != null) && ((ent instanceof EntityItem))) {
/* 111 */         return (EntityItem)ent;
/*     */       }
/*     */     }
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   public boolean canGolemPerformTask(IGolemAPI golem, Task task)
/*     */   {
/* 119 */     EntityItem ei = getItemEntity(golem.getGolemWorld(), task);
/* 120 */     if ((ei != null) && (ei.getEntityItem() != null)) {
/* 121 */       if (ei.isDead) {
/* 122 */         task.setSuspended(true);
/* 123 */         return false;
/*     */       }
/* 125 */       return golem.canCarry(ei.getEntityItem(), true);
/*     */     }
/* 127 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlaceAt(World world, BlockPos pos, EnumFacing side)
/*     */   {
/* 132 */     return !world.isAirBlock(pos);
/*     */   }
/*     */   
/*     */   public ResourceLocation getSealIcon()
/*     */   {
/* 137 */     return this.icon;
/*     */   }
/*     */   
/* 140 */   ResourceLocation icon = new ResourceLocation("thaumcraft", "items/seals/seal_pickup");
/*     */   
/*     */   public int[] getGuiCategories()
/*     */   {
/* 144 */     return new int[] { 2, 1, 0, 4 };
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumGolemTrait[] getRequiredTags()
/*     */   {
/* 150 */     return null;
/*     */   }
/*     */   
/*     */   public EnumGolemTrait[] getForbiddenTags()
/*     */   {
/* 155 */     return new EnumGolemTrait[] { EnumGolemTrait.CLUMSY };
/*     */   }
/*     */   
/* 158 */   protected ISealConfigToggles.SealToggle[] props = { new ISealConfigToggles.SealToggle(true, "pmeta", "golem.prop.meta"), new ISealConfigToggles.SealToggle(true, "pnbt", "golem.prop.nbt"), new ISealConfigToggles.SealToggle(false, "pore", "golem.prop.ore"), new ISealConfigToggles.SealToggle(false, "pmod", "golem.prop.mod") };
/*     */   
/*     */   public void onTaskStarted(World world, IGolemAPI golem, Task task) {}
/*     */   
/*     */   public void onTaskSuspension(World world, Task task) {}
/*     */   
/*     */   public void onRemoval(World world, BlockPos pos, EnumFacing side) {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\seals\SealPickup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */