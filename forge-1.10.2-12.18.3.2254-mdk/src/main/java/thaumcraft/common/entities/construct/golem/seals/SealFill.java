/*     */ package thaumcraft.common.entities.construct.golem.seals;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import thaumcraft.api.golems.EnumGolemTrait;
/*     */ import thaumcraft.api.golems.IGolemAPI;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles.SealToggle;
/*     */ import thaumcraft.api.golems.seals.ISealEntity;
/*     */ import thaumcraft.api.golems.seals.SealPos;
/*     */ import thaumcraft.api.golems.tasks.Task;
/*     */ import thaumcraft.common.entities.construct.golem.tasks.TaskHandler;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ 
/*     */ 
/*     */ public class SealFill
/*     */   extends SealFiltered
/*     */ {
/*     */   public String getKey()
/*     */   {
/*  30 */     return "Thaumcraft:fill";
/*     */   }
/*     */   
/*  33 */   int delay = new Random(System.nanoTime()).nextInt(50);
/*  34 */   int watchedTask = Integer.MIN_VALUE;
/*     */   
/*     */   public void tickSeal(World world, ISealEntity seal)
/*     */   {
/*  38 */     if (this.delay++ % 20 != 0) { return;
/*     */     }
/*  40 */     Task oldTask = TaskHandler.getTask(world.provider.getDimensionId(), this.watchedTask);
/*  41 */     if ((oldTask == null) || (oldTask.isReserved()) || (oldTask.isSuspended()) || (oldTask.isCompleted())) {
/*  42 */       Task task = new Task(seal.getSealPos(), seal.getSealPos().pos);
/*  43 */       task.setPriority(seal.getPriority());
/*  44 */       TaskHandler.addTask(world.provider.getDimensionId(), task);
/*  45 */       this.watchedTask = task.getId();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onTaskStarted(World world, IGolemAPI golem, Task task)
/*     */   {
/*  51 */     ISealEntity se = SealHandler.getSealEntity(world.provider.getDimensionId(), task.getSealPos());
/*  52 */     if ((se != null) && (!se.isStoppedByRedstone(world))) {
/*  53 */       Task newTask = new Task(task.getSealPos(), task.getSealPos().pos);
/*  54 */       newTask.setPriority(se.getPriority());
/*  55 */       TaskHandler.addTask(world.provider.getDimensionId(), newTask);
/*  56 */       this.watchedTask = newTask.getId();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onTaskCompletion(World world, IGolemAPI golem, Task task)
/*     */   {
/*  62 */     TileEntity te = world.getTileEntity(task.getSealPos().pos);
/*  63 */     if ((te != null) && ((te instanceof IInventory))) {
/*  64 */       Tuple<ItemStack, ItemStack> tuple = InventoryUtils.findFirstMatchFromFilterTuple(getInv(), isBlacklist(), golem.getCarrying(), !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value);
/*     */       
/*     */ 
/*  67 */       if (tuple.getFirst() != null) {
/*  68 */         int limit = ((ItemStack)tuple.getFirst()).stackSize;
/*     */         
/*  70 */         if ((hasStacksizeLimiters()) && (tuple.getSecond() != null) && (((ItemStack)tuple.getSecond()).stackSize > 0))
/*     */         {
/*  72 */           int c = InventoryUtils.inventoryContainsAmount((IInventory)te, (ItemStack)tuple.getSecond(), task.getSealPos().face, !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value);
/*     */           
/*  74 */           if (c < ((ItemStack)tuple.getSecond()).stackSize)
/*  75 */             limit = ((ItemStack)tuple.getSecond()).stackSize - c; else {
/*  76 */             limit = 0;
/*     */           }
/*     */         }
/*  79 */         if (limit > 0) {
/*  80 */           ItemStack t = ((ItemStack)tuple.getFirst()).copy();
/*  81 */           t.stackSize = limit;
/*  82 */           ItemStack s = golem.dropItem(t);
/*  83 */           golem.holdItem(InventoryUtils.placeItemStackIntoInventory(s, (IInventory)te, task.getSealPos().face, true));
/*  84 */           world.playSoundAtEntity((Entity)golem, "random.pop", 0.125F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.0F);
/*  85 */           golem.addRankXp(1);
/*  86 */           golem.swingArm();
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/*  91 */       Tuple<ItemStack, ItemStack> tuple = InventoryUtils.findFirstMatchFromFilterTuple(getInv(), isBlacklist(), golem.getCarrying(), !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value);
/*     */       
/*  93 */       if (tuple.getFirst() != null) {
/*  94 */         int limit = ((ItemStack)tuple.getFirst()).stackSize;
/*     */         
/*  96 */         if ((hasStacksizeLimiters()) && (tuple.getSecond() != null) && (((ItemStack)tuple.getSecond()).stackSize > 0)) {
/*  97 */           int c = InventoryUtils.countItemsInWorld(golem.getGolemWorld(), task.getSealPos().pos, (ItemStack)tuple.getSecond(), 1.5D, !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value);
/*     */           
/*  99 */           if (c < ((ItemStack)tuple.getSecond()).stackSize)
/* 100 */             limit = ((ItemStack)tuple.getSecond()).stackSize - c; else {
/* 101 */             limit = 0;
/*     */           }
/*     */         }
/* 104 */         if (limit > 0) {
/* 105 */           ItemStack t = ((ItemStack)tuple.getFirst()).copy();
/* 106 */           t.stackSize = limit;
/* 107 */           ItemStack s = golem.dropItem(t);
/* 108 */           EntityItem ie = new EntityItem(world, task.getSealPos().pos.getX() + 0.5D + task.getSealPos().face.getFrontOffsetX(), task.getSealPos().pos.getY() + 0.5D + task.getSealPos().face.getFrontOffsetY(), task.getSealPos().pos.getZ() + 0.5D + task.getSealPos().face.getFrontOffsetZ(), s);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 113 */           ie.motionX /= 5.0D;
/* 114 */           ie.motionY /= 2.0D;
/* 115 */           ie.motionZ /= 5.0D;
/* 116 */           world.spawnEntityInWorld(ie);
/* 117 */           world.playSoundAtEntity((Entity)golem, "random.pop", 0.125F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.0F);
/*     */           
/* 119 */           golem.swingArm();
/*     */         }
/*     */       }
/*     */     }
/* 123 */     task.setSuspended(true);
/* 124 */     return true;
/*     */   }
/*     */   
/* 127 */   protected ISealConfigToggles.SealToggle[] props = { new ISealConfigToggles.SealToggle(true, "pmeta", "golem.prop.meta"), new ISealConfigToggles.SealToggle(true, "pnbt", "golem.prop.nbt"), new ISealConfigToggles.SealToggle(false, "pore", "golem.prop.ore"), new ISealConfigToggles.SealToggle(false, "pmod", "golem.prop.mod"), new ISealConfigToggles.SealToggle(false, "pexist", "golem.prop.exist") };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canGolemPerformTask(IGolemAPI golem, Task task)
/*     */   {
/* 137 */     TileEntity te = golem.getGolemWorld().getTileEntity(task.getSealPos().pos);
/* 138 */     if ((te != null) && ((te instanceof IInventory))) {
/* 139 */       Tuple<ItemStack, ItemStack> tuple = InventoryUtils.findFirstMatchFromFilterTuple(getInv(), isBlacklist(), golem.getCarrying(), !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value);
/*     */       
/*     */ 
/* 142 */       if ((tuple.getFirst() != null) && (this.props[4].value)) if (InventoryUtils.inventoryContainsAmount((IInventory)te, (ItemStack)tuple.getFirst(), task.getSealPos().face, !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value) <= 0)
/*     */         {
/* 144 */           return false;
/*     */         }
/* 146 */       if ((tuple.getFirst() != null) && (InventoryUtils.hasRoomFor((ItemStack)tuple.getFirst(), (IInventory)te, task.getSealPos().face))) {
/* 147 */         if ((hasStacksizeLimiters()) && (tuple.getSecond() != null) && (((ItemStack)tuple.getSecond()).stackSize > 0)) {
/* 148 */           if (InventoryUtils.inventoryContainsAmount((IInventory)te, (ItemStack)tuple.getSecond(), task.getSealPos().face, !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value) < ((ItemStack)tuple.getSecond()).stackSize)
/*     */           {
/* 150 */             return true;
/*     */           }
/*     */         } else
/* 153 */           return true;
/*     */       }
/*     */     } else {
/* 156 */       Tuple<ItemStack, ItemStack> tuple = InventoryUtils.findFirstMatchFromFilterTuple(getInv(), isBlacklist(), golem.getCarrying(), !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value);
/*     */       
/* 158 */       if (tuple.getFirst() != null) {
/* 159 */         if ((hasStacksizeLimiters()) && (tuple.getSecond() != null) && (((ItemStack)tuple.getSecond()).stackSize > 0)) {
/* 160 */           return InventoryUtils.countItemsInWorld(golem.getGolemWorld(), task.getSealPos().pos, (ItemStack)tuple.getSecond(), 1.5D, !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value) < ((ItemStack)tuple.getSecond()).stackSize;
/*     */         }
/*     */         
/* 163 */         return true;
/*     */       }
/* 165 */       return false;
/*     */     }
/* 167 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean canPlaceAt(World world, BlockPos pos, EnumFacing side)
/*     */   {
/* 174 */     return !world.isAirBlock(pos);
/*     */   }
/*     */   
/*     */   public ResourceLocation getSealIcon()
/*     */   {
/* 179 */     return this.icon;
/*     */   }
/*     */   
/* 182 */   ResourceLocation icon = new ResourceLocation("thaumcraft", "items/seals/seal_fill");
/*     */   
/*     */   public int[] getGuiCategories()
/*     */   {
/* 186 */     return new int[] { 1, 0, 4 };
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumGolemTrait[] getRequiredTags()
/*     */   {
/* 192 */     return null;
/*     */   }
/*     */   
/*     */   public EnumGolemTrait[] getForbiddenTags()
/*     */   {
/* 197 */     return new EnumGolemTrait[] { EnumGolemTrait.CLUMSY };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onTaskSuspension(World world, Task task) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onRemoval(World world, BlockPos pos, EnumFacing side) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasStacksizeLimiters()
/*     */   {
/* 216 */     return !isBlacklist();
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\seals\SealFill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */