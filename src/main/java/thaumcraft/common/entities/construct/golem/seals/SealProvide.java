/*     */ package thaumcraft.common.entities.construct.golem.seals;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import thaumcraft.api.golems.EnumGolemTrait;
/*     */ import thaumcraft.api.golems.IGolemAPI;
/*     */ import thaumcraft.api.golems.ProvisionRequest;
/*     */ import thaumcraft.api.golems.seals.ISeal;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles.SealToggle;
/*     */ import thaumcraft.api.golems.seals.ISealEntity;
/*     */ import thaumcraft.api.golems.seals.SealPos;
/*     */ import thaumcraft.api.golems.tasks.Task;
/*     */ import thaumcraft.common.entities.construct.golem.EntityThaumcraftGolem;
/*     */ import thaumcraft.common.entities.construct.golem.tasks.TaskHandler;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ 
/*     */ public class SealProvide extends SealFiltered implements thaumcraft.api.golems.seals.ISealConfigToggles
/*     */ {
/*     */   public String getKey()
/*     */   {
/*  35 */     return "Thaumcraft:provider";
/*     */   }
/*     */   
/*     */   public int getFilterSize()
/*     */   {
/*  40 */     return 9;
/*     */   }
/*     */   
/*  43 */   int delay = new Random(System.nanoTime()).nextInt(88);
/*     */   
/*  45 */   HashMap<Integer, ProvisionRequest> cache = new HashMap();
/*     */   
/*     */ 
/*     */   public void tickSeal(World world, ISealEntity seal)
/*     */   {
/*  50 */     if (this.delay % 100 == 0) {
/*  51 */       Iterator<Integer> it = this.cache.keySet().iterator();
/*  52 */       while (it.hasNext()) {
/*  53 */         Task t = TaskHandler.getTask(world.provider.getDimensionId(), ((Integer)it.next()).intValue());
/*  54 */         if (t == null) {
/*  55 */           it.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  60 */     if (this.delay++ % 20 != 0) { return;
/*     */     }
/*  62 */     TileEntity te = world.getTileEntity(seal.getSealPos().pos);
/*  63 */     if ((te != null) && ((te instanceof IInventory)) && (thaumcraft.api.golems.GolemHelper.provisionRequests.containsKey(Integer.valueOf(world.provider.getDimensionId())))) {
/*  64 */       ListIterator<ProvisionRequest> it = ((ArrayList)thaumcraft.api.golems.GolemHelper.provisionRequests.get(Integer.valueOf(world.provider.getDimensionId()))).listIterator();
/*  65 */       while (it.hasNext()) {
/*  66 */         ProvisionRequest pr = (ProvisionRequest)it.next();
/*  67 */         if (pr.getSeal().getSealPos().pos.distanceSq(seal.getSealPos().pos) < 4096.0D) {
/*  68 */           if (InventoryUtils.findFirstMatchFromFilter(getInv(), this.blacklist, new ItemStack[] { pr.getStack() }, !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value) != null) { if (InventoryUtils.inventoryContainsAmount((IInventory)te, pr.getStack(), seal.getSealPos().face, false, false, false, false) > (this.props[5].value ? 1 : 0))
/*     */             {
/*     */ 
/*  71 */               Task task = new Task(seal.getSealPos(), seal.getSealPos().pos);
/*  72 */               task.setPriority(pr.getSeal().getPriority());
/*  73 */               task.setLifespan((short)5);
/*  74 */               TaskHandler.addTask(world.provider.getDimensionId(), task);
/*  75 */               this.cache.put(Integer.valueOf(task.getId()), pr);
/*  76 */               it.remove();
/*  77 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onTaskCompletion(World world, IGolemAPI golem, Task task)
/*     */   {
/*  87 */     TileEntity te = world.getTileEntity(task.getSealPos().pos);
/*  88 */     if ((te != null) && ((te instanceof IInventory))) {
/*  89 */       ItemStack stack = null;
/*     */       try {
/*  91 */         stack = ((ProvisionRequest)this.cache.get(Integer.valueOf(task.getId()))).getStack().copy();
/*     */       } catch (Exception e) {}
/*  93 */       if ((stack != null) && (this.props[4].value)) { stack.stackSize = 1;
/*     */       }
/*  95 */       int sa = 0;
/*  96 */       if ((stack != null) && (this.props[5].value) && ((sa = InventoryUtils.inventoryContainsAmount((IInventory)te, stack, task.getSealPos().face, false, false, false, false)) <= stack.stackSize))
/*     */       {
/*  98 */         stack.stackSize = (sa - 1);
/*     */       }
/* 100 */       if ((stack != null) && (golem.canCarry(stack, true))) {
/* 101 */         ItemStack s = golem.holdItem(InventoryUtils.extractStack((IInventory)te, stack.copy(), task.getSealPos().face, false, false, false, false, true));
/* 102 */         if (s != null) {
/* 103 */           ItemStack q = InventoryUtils.placeItemStackIntoInventory(s, (IInventory)te, task.getSealPos().face, true);
/* 104 */           if (q != null) ((Entity)golem).entityDropItem(q, 0.25F);
/*     */         }
/* 106 */         world.playSoundAtEntity((Entity)golem, "random.pop", 0.125F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 107 */         golem.addRankXp(1);
/* 108 */         golem.swingArm();
/*     */       }
/* 110 */       this.cache.remove(Integer.valueOf(task.getId()));
/*     */     }
/* 112 */     task.setSuspended(true);
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canGolemPerformTask(IGolemAPI golem, Task task)
/*     */   {
/* 118 */     ProvisionRequest pr = (ProvisionRequest)this.cache.get(Integer.valueOf(task.getId()));
/* 119 */     return (pr != null) && (((EntityThaumcraftGolem)golem).isWithinHomeDistanceFromPosition(pr.getSeal().getSealPos().pos)) && (areGolemTagsValidForTask(pr.getSeal(), golem)) && (pr.getStack() != null) && (!golem.isCarrying(pr.getStack())) && (golem.canCarry(pr.getStack(), true));
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean areGolemTagsValidForTask(ISealEntity se, IGolemAPI golem)
/*     */   {
/* 125 */     if (se != null) {
/* 126 */       if ((se.isLocked()) && (!((IEntityOwnable)golem).getOwnerId().equals(se.getOwner()))) return false;
/* 127 */       if ((se.getSeal().getRequiredTags() != null) && (!golem.getProperties().getTraits().containsAll(java.util.Arrays.asList(se.getSeal().getRequiredTags()))))
/* 128 */         return false;
/* 129 */       if (se.getSeal().getForbiddenTags() != null) {
/* 130 */         for (EnumGolemTrait tag : se.getSeal().getForbiddenTags()) {
/* 131 */           if (golem.getProperties().getTraits().contains(tag)) return false;
/*     */         }
/*     */       }
/*     */     } else {
/* 135 */       return true;
/*     */     }
/* 137 */     return true;
/*     */   }
/*     */   
/*     */   public void onTaskSuspension(World world, Task task)
/*     */   {
/* 142 */     this.cache.remove(Integer.valueOf(task.getId()));
/*     */   }
/*     */   
/*     */   public boolean canPlaceAt(World world, BlockPos pos, EnumFacing side)
/*     */   {
/* 147 */     TileEntity te = world.getTileEntity(pos);
/* 148 */     if ((te != null) && ((te instanceof IInventory))) {
/* 149 */       return true;
/*     */     }
/* 151 */     return false;
/*     */   }
/*     */   
/*     */   public ResourceLocation getSealIcon()
/*     */   {
/* 156 */     return this.icon;
/*     */   }
/*     */   
/* 159 */   ResourceLocation icon = new ResourceLocation("thaumcraft", "items/seals/seal_provider");
/*     */   
/*     */   public int[] getGuiCategories()
/*     */   {
/* 163 */     return new int[] { 1, 3, 0, 4 };
/*     */   }
/*     */   
/* 166 */   protected ISealConfigToggles.SealToggle[] props = { new ISealConfigToggles.SealToggle(true, "pmeta", "golem.prop.meta"), new ISealConfigToggles.SealToggle(true, "pnbt", "golem.prop.nbt"), new ISealConfigToggles.SealToggle(false, "pore", "golem.prop.ore"), new ISealConfigToggles.SealToggle(false, "pmod", "golem.prop.mod"), new ISealConfigToggles.SealToggle(false, "psing", "golem.prop.single"), new ISealConfigToggles.SealToggle(false, "pleave", "golem.prop.leave") };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumGolemTrait[] getRequiredTags()
/*     */   {
/* 177 */     return new EnumGolemTrait[] { EnumGolemTrait.SMART };
/*     */   }
/*     */   
/*     */   public EnumGolemTrait[] getForbiddenTags()
/*     */   {
/* 182 */     return new EnumGolemTrait[] { EnumGolemTrait.CLUMSY };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onTaskStarted(World world, IGolemAPI golem, Task task) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onRemoval(World world, BlockPos pos, EnumFacing side) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ISealConfigToggles.SealToggle[] getToggles()
/*     */   {
/* 201 */     return this.props;
/*     */   }
/*     */   
/*     */   public void setToggle(int indx, boolean value)
/*     */   {
/* 206 */     this.props[indx].setValue(value);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\seals\SealProvide.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */