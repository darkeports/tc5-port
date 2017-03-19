/*     */ package thaumcraft.common.entities.construct.golem.seals;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.Block.SoundType;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.util.FakePlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ import thaumcraft.api.golems.EnumGolemTrait;
/*     */ import thaumcraft.api.golems.GolemHelper;
/*     */ import thaumcraft.api.golems.IGolemAPI;
/*     */ import thaumcraft.api.golems.seals.ISealConfigArea;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles.SealToggle;
/*     */ import thaumcraft.api.golems.seals.ISealEntity;
/*     */ import thaumcraft.api.golems.tasks.Task;
/*     */ import thaumcraft.common.entities.construct.golem.gui.SealBaseGUI;
/*     */ import thaumcraft.common.entities.construct.golem.tasks.TaskHandler;
/*     */ import thaumcraft.common.lib.utils.BlockUtils;
/*     */ 
/*     */ public class SealBreaker extends SealFiltered implements ISealConfigArea, ISealConfigToggles
/*     */ {
/*     */   public String getKey()
/*     */   {
/*  40 */     return "Thaumcraft:breaker";
/*     */   }
/*     */   
/*  43 */   int delay = new Random(System.nanoTime()).nextInt(42);
/*     */   
/*  45 */   HashMap<Integer, Long> cache = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */   public void tickSeal(World world, ISealEntity seal)
/*     */   {
/*  51 */     if (this.delay % 100 == 0) {
/*  52 */       Iterator<Integer> it = this.cache.keySet().iterator();
/*  53 */       while (it.hasNext()) {
/*  54 */         Task t = TaskHandler.getTask(world.provider.getDimensionId(), ((Integer)it.next()).intValue());
/*  55 */         if (t == null) {
/*  56 */           it.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  61 */     this.delay += 1;
/*     */     
/*  63 */     BlockPos p = GolemHelper.getPosInArea(seal, this.delay);
/*     */     
/*  65 */     if ((!this.cache.containsValue(Long.valueOf(p.toLong()))) && (isValidBlock(world, p))) {
/*  66 */       Task task = new Task(seal.getSealPos(), p);
/*  67 */       task.setPriority(seal.getPriority());
/*  68 */       task.setData((int)(world.getBlockState(p).getBlock().getBlockHardness(world, p) / 3.0F));
/*  69 */       TaskHandler.addTask(world.provider.getDimensionId(), task);
/*  70 */       this.cache.put(Integer.valueOf(task.getId()), Long.valueOf(p.toLong()));
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isValidBlock(World world, BlockPos p) {
/*  75 */     IBlockState bs = world.getBlockState(p);
/*  76 */     if ((!world.isAirBlock(p)) && (bs.getBlock().getBlockHardness(world, p) >= 0.0F)) {
/*  77 */       ItemStack ts = getFilterSlot(0);
/*  78 */       if (ts != null) {
/*  79 */         ItemStack fs = BlockUtils.createStackedBlock(bs);
/*  80 */         if (fs == null) {
/*  81 */           fs = new ItemStack(bs.getBlock(), 1, !getToggles()[0].value ? 32767 : bs.getBlock().getMetaFromState(bs));
/*     */         }
/*  83 */         if (!getToggles()[0].value) { fs.setItemDamage(32767);
/*     */         }
/*  85 */         if (isBlacklist()) {
/*  86 */           if (OreDictionary.itemMatches(fs, getFilterSlot(0), getToggles()[0].value)) return false;
/*     */         }
/*  88 */         else if (!OreDictionary.itemMatches(fs, getFilterSlot(0), getToggles()[0].value)) { return false;
/*     */         }
/*     */       }
/*  91 */       return true;
/*     */     }
/*  93 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onTaskCompletion(World world, IGolemAPI golem, Task task)
/*     */   {
/*  99 */     IBlockState bs = world.getBlockState(task.getPos());
/* 100 */     if ((this.cache.containsKey(Integer.valueOf(task.getId()))) && (isValidBlock(world, task.getPos()))) {
/* 101 */       FakePlayer fp = net.minecraftforge.common.util.FakePlayerFactory.get((WorldServer)world, new GameProfile((java.util.UUID)null, "FakeThaumcraftGolem"));
/* 102 */       fp.setPosition(golem.getGolemEntity().posX, golem.getGolemEntity().posY, golem.getGolemEntity().posZ);
/*     */       
/* 104 */       golem.swingArm();
/* 105 */       if (task.getData() > 0) {
/* 106 */         float bh = bs.getBlock().getBlockHardness(world, task.getPos()) / 3.0F;
/* 107 */         world.playSoundEffect(task.getPos().getX() + 0.5F, task.getPos().getY() + 0.5F, task.getPos().getZ() + 0.5F, bs.getBlock().stepSound.getBreakSound(), (bs.getBlock().stepSound.getVolume() + 0.7F) / 8.0F, bs.getBlock().stepSound.getFrequency() * 0.5F);
/*     */         
/* 109 */         BlockUtils.destroyBlockPartially(world, golem.getGolemEntity().getEntityId(), task.getPos(), (int)(9.0F * (1.0F - task.getData() / bh)));
/* 110 */         task.setLifespan((short)(int)Math.max(task.getLifespan(), 10L));
/* 111 */         task.setData(task.getData() - 1);
/* 112 */         return false;
/*     */       }
/*     */       
/* 115 */       BlockUtils.harvestBlock(world, fp, task.getPos(), true);
/* 116 */       golem.addRankXp(1);
/*     */       
/* 118 */       this.cache.remove(Integer.valueOf(task.getId()));
/*     */     }
/* 120 */     task.setSuspended(true);
/* 121 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canGolemPerformTask(IGolemAPI golem, Task task)
/*     */   {
/* 126 */     if ((this.cache.containsKey(Integer.valueOf(task.getId()))) && (isValidBlock(golem.getGolemWorld(), task.getPos()))) return true;
/* 127 */     task.setSuspended(true);
/* 128 */     return false;
/*     */   }
/*     */   
/*     */   public void onTaskSuspension(World world, Task task)
/*     */   {
/* 133 */     this.cache.remove(Integer.valueOf(task.getId()));
/*     */   }
/*     */   
/*     */   public boolean canPlaceAt(World world, BlockPos pos, EnumFacing side)
/*     */   {
/* 138 */     return !world.isAirBlock(pos);
/*     */   }
/*     */   
/*     */   public ResourceLocation getSealIcon()
/*     */   {
/* 143 */     return this.icon;
/*     */   }
/*     */   
/* 146 */   ResourceLocation icon = new ResourceLocation("thaumcraft", "items/seals/seal_breaker");
/*     */   
/*     */ 
/*     */   public void onRemoval(World world, BlockPos pos, EnumFacing side) {}
/*     */   
/*     */   public Object returnContainer(World world, EntityPlayer player, BlockPos pos, EnumFacing side, ISealEntity seal)
/*     */   {
/* 153 */     return new thaumcraft.common.entities.construct.golem.gui.SealBaseContainer(player.inventory, world, seal);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public Object returnGui(World world, EntityPlayer player, BlockPos pos, EnumFacing side, ISealEntity seal)
/*     */   {
/* 159 */     return new SealBaseGUI(player.inventory, world, seal);
/*     */   }
/*     */   
/*     */   public int[] getGuiCategories() {
/* 163 */     return new int[] { 2, 1, 3, 0, 4 };
/*     */   }
/*     */   
/*     */   public EnumGolemTrait[] getRequiredTags() {
/* 167 */     return new EnumGolemTrait[] { EnumGolemTrait.BREAKER };
/*     */   }
/*     */   
/*     */   public EnumGolemTrait[] getForbiddenTags()
/*     */   {
/* 172 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onTaskStarted(World world, IGolemAPI golem, Task task) {}
/*     */   
/*     */ 
/*     */   public ISealConfigToggles.SealToggle[] getToggles()
/*     */   {
/* 181 */     return this.props;
/*     */   }
/*     */   
/* 184 */   protected ISealConfigToggles.SealToggle[] props = { new ISealConfigToggles.SealToggle(true, "pmeta", "golem.prop.meta") };
/*     */   
/*     */ 
/*     */ 
/*     */   public void setToggle(int indx, boolean value)
/*     */   {
/* 190 */     this.props[indx].setValue(value);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\seals\SealBreaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */