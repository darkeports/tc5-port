/*     */ package thaumcraft.common.entities.construct.golem.seals;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDirectional;
/*     */ import net.minecraft.block.BlockFarmland;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.IPlantable;
/*     */ import net.minecraftforge.common.util.FakePlayer;
/*     */ import net.minecraftforge.common.util.FakePlayerFactory;
/*     */ import thaumcraft.api.golems.EnumGolemTrait;
/*     */ import thaumcraft.api.golems.GolemHelper;
/*     */ import thaumcraft.api.golems.IGolemAPI;
/*     */ import thaumcraft.api.golems.seals.ISealConfigArea;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles.SealToggle;
/*     */ import thaumcraft.api.golems.seals.ISealEntity;
/*     */ import thaumcraft.api.golems.tasks.Task;
/*     */ import thaumcraft.common.entities.construct.golem.gui.SealBaseContainer;
/*     */ import thaumcraft.common.entities.construct.golem.gui.SealBaseGUI;
/*     */ import thaumcraft.common.entities.construct.golem.tasks.TaskHandler;
/*     */ import thaumcraft.common.lib.utils.CropUtils;
/*     */ 
/*     */ public class SealHarvest implements thaumcraft.api.golems.seals.ISeal, thaumcraft.api.golems.seals.ISealGui, ISealConfigArea, ISealConfigToggles
/*     */ {
/*     */   int delay;
/*     */   int count;
/*     */   HashMap<Long, ReplantInfo> replantTasks;
/*     */   ResourceLocation icon;
/*     */   protected ISealConfigToggles.SealToggle[] props;
/*     */   
/*     */   public String getKey()
/*     */   {
/*  55 */     return "Thaumcraft:harvest";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void tickSeal(World world, ISealEntity seal)
/*     */   {
/*  66 */     if (this.delay % 100 == 0) {
/*  67 */       AxisAlignedBB area = GolemHelper.getBoundsForArea(seal);
/*     */       
/*  69 */       Iterator<Long> rt = this.replantTasks.keySet().iterator();
/*  70 */       while (rt.hasNext()) {
/*  71 */         BlockPos pp = BlockPos.fromLong(((Long)rt.next()).longValue());
/*  72 */         if (!area.isVecInside(new Vec3(pp.getX() + 0.5D, pp.getY() + 0.5D, pp.getZ() + 0.5D))) {
/*  73 */           if (this.replantTasks.get(rt) != null) {
/*  74 */             Task tt = TaskHandler.getTask(world.provider.getDimensionId(), ((ReplantInfo)this.replantTasks.get(rt)).taskid);
/*  75 */             if (tt != null) tt.setSuspended(true);
/*     */           }
/*  77 */           rt.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  82 */     if (this.delay++ % 5 != 0) { return;
/*     */     }
/*  84 */     BlockPos p = GolemHelper.getPosInArea(seal, this.count++);
/*     */     
/*  86 */     if (CropUtils.isGrownCrop(world, p)) {
/*  87 */       Task task = new Task(seal.getSealPos(), p);
/*  88 */       task.setPriority(seal.getPriority());
/*  89 */       TaskHandler.addTask(world.provider.getDimensionId(), task);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*  96 */     else if ((getToggles()[0].value) && (this.replantTasks.containsKey(Long.valueOf(p.toLong()))) && (world.isAirBlock(p))) {
/*  97 */       Task t = TaskHandler.getTask(world.provider.getDimensionId(), ((ReplantInfo)this.replantTasks.get(Long.valueOf(p.toLong()))).taskid);
/*     */       
/*  99 */       if (t == null) {
/* 100 */         Task tt = new Task(seal.getSealPos(), ((ReplantInfo)this.replantTasks.get(Long.valueOf(p.toLong()))).pos);
/* 101 */         tt.setPriority(seal.getPriority());
/* 102 */         TaskHandler.addTask(world.provider.getDimensionId(), tt);
/* 103 */         ((ReplantInfo)this.replantTasks.get(Long.valueOf(p.toLong()))).taskid = tt.getId();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onTaskCompletion(World world, IGolemAPI golem, Task task)
/*     */   {
/* 120 */     if (CropUtils.isGrownCrop(world, task.getPos()))
/*     */     {
/* 122 */       FakePlayer fp = FakePlayerFactory.get((WorldServer)world, new GameProfile((UUID)null, "FakeThaumcraftGolem"));
/* 123 */       fp.setPosition(golem.getGolemEntity().posX, golem.getGolemEntity().posY, golem.getGolemEntity().posZ);
/*     */       
/* 125 */       EnumFacing face = BlockPistonBase.getFacingFromEntity(world, task.getPos(), golem.getGolemEntity());
/* 126 */       IBlockState bs = world.getBlockState(task.getPos());
/*     */       
/* 128 */       if (CropUtils.clickableCrops.contains(bs.getBlock().getUnlocalizedName() + bs.getBlock().getMetaFromState(bs))) {
/* 129 */         bs.getBlock().onBlockActivated(world, task.getPos(), bs, fp, face, 0.0F, 0.0F, 0.0F);
/* 130 */         golem.addRankXp(1);
/* 131 */         golem.swingArm();
/*     */       }
/*     */       else {
/* 134 */         thaumcraft.common.lib.utils.BlockUtils.harvestBlock(world, fp, task.getPos(), true);
/* 135 */         golem.addRankXp(1);
/* 136 */         golem.swingArm();
/* 137 */         if (getToggles()[0].value) {
/* 138 */           ItemStack seed = thaumcraft.api.ThaumcraftApi.getSeed(bs.getBlock());
/*     */           
/* 140 */           if (seed != null) {
/* 141 */             IBlockState bb = world.getBlockState(task.getPos().down());
/* 142 */             EnumFacing rf = null;
/* 143 */             if (((seed.getItem() instanceof IPlantable)) && (bb.getBlock().canSustainPlant(world, task.getPos().down(), EnumFacing.UP, (IPlantable)seed.getItem())))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/* 148 */               rf = EnumFacing.DOWN;
/* 149 */             } else if ((!(seed.getItem() instanceof IPlantable)) && 
/* 150 */               ((bs.getBlock() instanceof BlockDirectional))) {
/* 151 */               rf = (EnumFacing)bs.getValue(BlockDirectional.FACING);
/*     */             }
/*     */             
/*     */ 
/* 155 */             if (rf != null) {
/* 156 */               Task tt = new Task(task.getSealPos(), task.getPos());
/*     */               
/* 158 */               tt.setPriority(task.getPriority());
/* 159 */               tt.setLifespan((short)300);
/* 160 */               this.replantTasks.put(Long.valueOf(tt.getPos().toLong()), new ReplantInfo(tt.getPos(), rf, tt.getId(), seed.copy(), bb.getBlock() instanceof BlockFarmland));
/*     */               
/*     */ 
/*     */ 
/* 164 */               TaskHandler.addTask(world.provider.getDimensionId(), tt);
/*     */ 
/*     */             }
/*     */             
/*     */ 
/*     */           }
/*     */           
/*     */ 
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */ 
/*     */     }
/* 178 */     else if ((this.replantTasks.containsKey(Long.valueOf(task.getPos().toLong()))) && (((ReplantInfo)this.replantTasks.get(Long.valueOf(task.getPos().toLong()))).taskid == task.getId()) && (world.isAirBlock(task.getPos())) && (golem.isCarrying(((ReplantInfo)this.replantTasks.get(Long.valueOf(task.getPos().toLong()))).stack)))
/*     */     {
/* 180 */       FakePlayer fp = FakePlayerFactory.get((WorldServer)world, new GameProfile((UUID)null, "FakeThaumcraftGolem"));
/* 181 */       fp.setPosition(golem.getGolemEntity().posX, golem.getGolemEntity().posY, golem.getGolemEntity().posZ);
/* 182 */       IBlockState bb = world.getBlockState(task.getPos().down());
/* 183 */       ReplantInfo ri = (ReplantInfo)this.replantTasks.get(Long.valueOf(task.getPos().toLong()));
/* 184 */       if ((((bb.getBlock() instanceof net.minecraft.block.BlockDirt)) || ((bb.getBlock() instanceof net.minecraft.block.BlockGrass))) && (ri.farmland)) {
/* 185 */         Items.diamond_hoe.onItemUse(new ItemStack(Items.diamond_hoe), fp, world, task.getPos().down(), EnumFacing.UP, 0.5F, 0.5F, 0.5F);
/*     */       }
/* 187 */       ItemStack seed = ri.stack.copy();
/* 188 */       seed.stackSize = 1;
/* 189 */       if (seed.getItem().onItemUse(seed.copy(), fp, world, task.getPos().offset(ri.face), ri.face.getOpposite(), 0.5F, 0.5F, 0.5F))
/*     */       {
/* 191 */         world.playAuxSFX(2001, task.getPos(), Block.getStateId(world.getBlockState(task.getPos())));
/* 192 */         golem.dropItem(seed);
/* 193 */         golem.addRankXp(1);
/* 194 */         golem.swingArm();
/*     */       }
/*     */     }
/*     */     
/* 198 */     task.setSuspended(true);
/* 199 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canGolemPerformTask(IGolemAPI golem, Task task)
/*     */   {
/* 204 */     if ((this.replantTasks.containsKey(Long.valueOf(task.getPos().toLong()))) && (((ReplantInfo)this.replantTasks.get(Long.valueOf(task.getPos().toLong()))).taskid == task.getId())) {
/* 205 */       boolean carry = golem.isCarrying(((ReplantInfo)this.replantTasks.get(Long.valueOf(task.getPos().toLong()))).stack);
/* 206 */       if ((!carry) && (getToggles()[1].value)) {
/* 207 */         ISealEntity se = SealHandler.getSealEntity(golem.getGolemWorld().provider.getDimensionId(), task.getSealPos());
/* 208 */         if (se != null) GolemHelper.requestProvisioning(golem.getGolemWorld(), se, ((ReplantInfo)this.replantTasks.get(Long.valueOf(task.getPos().toLong()))).stack);
/*     */       }
/* 210 */       return carry;
/*     */     }
/* 212 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbt)
/*     */   {
/* 221 */     NBTTagList nbttaglist = nbt.getTagList("replant", 10);
/* 222 */     for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */     {
/* 224 */       NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/* 225 */       long loc = nbttagcompound1.getLong("taskloc");
/* 226 */       byte face = nbttagcompound1.getByte("taskface");
/* 227 */       boolean farmland = nbttagcompound1.getBoolean("farmland");
/* 228 */       ItemStack stack = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/* 229 */       this.replantTasks.put(Long.valueOf(loc), new ReplantInfo(BlockPos.fromLong(loc), EnumFacing.VALUES[face], 0, stack, farmland));
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbt)
/*     */   {
/* 235 */     if (getToggles()[0].value) {
/* 236 */       NBTTagList nbttaglist = new NBTTagList();
/* 237 */       for (Long key : this.replantTasks.keySet())
/*     */       {
/* 239 */         ReplantInfo info = (ReplantInfo)this.replantTasks.get(key);
/* 240 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 241 */         nbttagcompound1.setLong("taskloc", info.pos.toLong());
/* 242 */         nbttagcompound1.setByte("taskface", (byte)info.face.ordinal());
/* 243 */         nbttagcompound1.setBoolean("farmland", info.farmland);
/* 244 */         info.stack.writeToNBT(nbttagcompound1);
/* 245 */         nbttaglist.appendTag(nbttagcompound1);
/*     */       }
/* 247 */       nbt.setTag("replant", nbttaglist);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canPlaceAt(World world, BlockPos pos, EnumFacing side)
/*     */   {
/* 253 */     return !world.isAirBlock(pos);
/*     */   }
/*     */   
/*     */   public ResourceLocation getSealIcon()
/*     */   {
/* 258 */     return this.icon;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object returnContainer(World world, EntityPlayer player, BlockPos pos, EnumFacing side, ISealEntity seal)
/*     */   {
/* 271 */     return new SealBaseContainer(player.inventory, world, seal);
/*     */   }
/*     */   
/*     */   @net.minecraftforge.fml.relauncher.SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
/*     */   public Object returnGui(World world, EntityPlayer player, BlockPos pos, EnumFacing side, ISealEntity seal)
/*     */   {
/* 277 */     return new SealBaseGUI(player.inventory, world, seal);
/*     */   }
/*     */   
/*     */   public int[] getGuiCategories() {
/* 281 */     return new int[] { 2, 3, 0, 4 };
/*     */   }
/*     */   
/*     */   public SealHarvest()
/*     */   {
/*  58 */     this.delay = new Random(System.nanoTime()).nextInt(33);
/*  59 */     this.count = 0;
/*  60 */     this.replantTasks = new HashMap();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 261 */     this.icon = new ResourceLocation("thaumcraft", "items/seals/seal_harvest");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 283 */     this.props = new ISealConfigToggles.SealToggle[] { new ISealConfigToggles.SealToggle(true, "prep", "golem.prop.replant"), new ISealConfigToggles.SealToggle(false, "ppro", "golem.prop.provision") };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ISealConfigToggles.SealToggle[] getToggles()
/*     */   {
/* 290 */     return this.props;
/*     */   }
/*     */   
/*     */   public void setToggle(int indx, boolean value)
/*     */   {
/* 295 */     this.props[indx].setValue(value);
/*     */   }
/*     */   
/*     */   public EnumGolemTrait[] getRequiredTags()
/*     */   {
/* 300 */     return new EnumGolemTrait[] { EnumGolemTrait.DEFT, EnumGolemTrait.SMART };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 305 */   public EnumGolemTrait[] getForbiddenTags() { return null; }
/*     */   
/*     */   public void onTaskSuspension(World world, Task task) {}
/*     */   
/*     */   public void onRemoval(World world, BlockPos pos, EnumFacing side) {}
/*     */   
/*     */   public void onTaskStarted(World world, IGolemAPI golem, Task task) {}
/*     */   
/*     */   private class ReplantInfo { EnumFacing face;
/*     */     BlockPos pos;
/*     */     
/* 316 */     public ReplantInfo(BlockPos pos, EnumFacing face, int taskid, ItemStack stack, boolean farmland) { this.pos = pos;
/* 317 */       this.face = face;
/* 318 */       this.taskid = taskid;
/* 319 */       this.stack = stack;
/* 320 */       this.farmland = farmland;
/*     */     }
/*     */     
/*     */     int taskid;
/*     */     ItemStack stack;
/*     */     boolean farmland;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\seals\SealHarvest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */