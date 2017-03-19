/*     */ package thaumcraft.common.entities.construct.golem.seals;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.EnumPacketDirection;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.server.management.ItemInWorldManager;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.util.FakePlayer;
/*     */ import net.minecraftforge.common.util.FakePlayerFactory;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.golems.EnumGolemTrait;
/*     */ import thaumcraft.api.golems.GolemHelper;
/*     */ import thaumcraft.api.golems.IGolemAPI;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles;
/*     */ import thaumcraft.api.golems.seals.ISealConfigToggles.SealToggle;
/*     */ import thaumcraft.api.golems.seals.ISealEntity;
/*     */ import thaumcraft.api.golems.seals.SealPos;
/*     */ import thaumcraft.api.golems.tasks.Task;
/*     */ import thaumcraft.common.entities.construct.golem.gui.SealBaseContainer;
/*     */ import thaumcraft.common.entities.construct.golem.gui.SealBaseGUI;
/*     */ import thaumcraft.common.entities.construct.golem.tasks.TaskHandler;
/*     */ import thaumcraft.common.lib.network.FakeNetHandlerPlayServer;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ 
/*     */ public class SealUse
/*     */   extends SealFiltered implements ISealConfigToggles
/*     */ {
/*     */   public String getKey()
/*     */   {
/*  46 */     return "Thaumcraft:use";
/*     */   }
/*     */   
/*  49 */   int delay = new Random(System.nanoTime()).nextInt(49);
/*     */   FakePlayer fp;
/*  51 */   int watchedTask = Integer.MIN_VALUE;
/*     */   
/*     */ 
/*     */   public void tickSeal(World world, ISealEntity seal)
/*     */   {
/*  56 */     if (this.delay++ % 5 != 0) { return;
/*     */     }
/*  58 */     Task oldTask = TaskHandler.getTask(world.provider.getDimensionId(), this.watchedTask);
/*  59 */     if ((oldTask == null) || (oldTask.isSuspended()) || (oldTask.isCompleted()))
/*     */     {
/*  61 */       if (getToggles()[5].value != world.isAirBlock(seal.getSealPos().pos)) return;
/*  62 */       Task task = new Task(seal.getSealPos(), seal.getSealPos().pos);
/*  63 */       task.setPriority(seal.getPriority());
/*  64 */       TaskHandler.addTask(world.provider.getDimensionId(), task);
/*     */       
/*  66 */       this.watchedTask = task.getId();
/*     */     }
/*     */   }
/*     */   
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean canBlockBePlaced(World world, Block blockIn, BlockPos pos, EnumFacing side)
/*     */   {
/*  86 */     Block block = world.getBlockState(pos).getBlock();
/*  87 */     AxisAlignedBB axisalignedbb = blockIn.getCollisionBoundingBox(world, pos, blockIn.getDefaultState());
/*  88 */     if ((axisalignedbb != null) && (!world.checkNoEntityCollision(axisalignedbb, null))) return false;
/*  89 */     return true;
/*     */   }
/*     */   
/*     */   public boolean onTaskCompletion(World world, IGolemAPI golem, Task task)
/*     */   {
/*  94 */     if (getToggles()[5].value == world.isAirBlock(task.getPos())) {
/*  95 */       if (this.fp == null) {
/*  96 */         this.fp = FakePlayerFactory.get((WorldServer)world, new GameProfile((UUID)null, "FakeThaumcraftGolem"));
/*  97 */         this.fp.playerNetServerHandler = new FakeNetHandlerPlayServer(this.fp.mcServer, new NetworkManager(EnumPacketDirection.CLIENTBOUND), this.fp);
/*     */       }
/*  99 */       this.fp.setPositionAndRotation(golem.getGolemEntity().posX, golem.getGolemEntity().posY, golem.getGolemEntity().posZ, golem.getGolemEntity().rotationYaw, golem.getGolemEntity().rotationPitch);
/*     */       
/*     */ 
/* 102 */       IBlockState bs = world.getBlockState(task.getPos());
/* 103 */       ItemStack clickStack = golem.getCarrying()[0];
/* 104 */       if (this.filter[0] != null) {
/* 105 */         clickStack = InventoryUtils.findFirstMatchFromFilter(this.filter, this.blacklist, golem.getCarrying(), !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value);
/*     */       }
/*     */       
/* 108 */       if ((clickStack != null) || (this.props[6].value)) {
/* 109 */         ItemStack ss = null;
/* 110 */         if (clickStack != null) {
/* 111 */           ss = clickStack.copy();
/* 112 */           golem.dropItem(clickStack.copy());
/*     */         }
/*     */         
/* 115 */         this.fp.setCurrentItemOrArmor(0, ss);
/* 116 */         this.fp.setSneaking(this.props[6].value);
/*     */         
/* 118 */         if (getToggles()[4].value)
/*     */         {
/* 120 */           this.fp.theItemInWorldManager.onBlockClicked(task.getPos(), task.getSealPos().face);
/*     */         }
/*     */         else {
/* 123 */           if ((this.fp.getCurrentEquippedItem() != null) && ((this.fp.getCurrentEquippedItem().getItem() instanceof ItemBlock)) && (!canBlockBePlaced(world, ((ItemBlock)this.fp.getCurrentEquippedItem().getItem()).block, task.getPos(), task.getSealPos().face)))
/*     */           {
/* 125 */             golem.getGolemEntity().setPosition(golem.getGolemEntity().posX + task.getSealPos().face.getFrontOffsetX(), golem.getGolemEntity().posY + task.getSealPos().face.getFrontOffsetY(), golem.getGolemEntity().posZ + task.getSealPos().face.getFrontOffsetZ());
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 130 */           this.fp.theItemInWorldManager.activateBlockOrUseItem(this.fp, world, this.fp.getCurrentEquippedItem(), task.getPos(), task.getSealPos().face, 0.5F, 0.5F, 0.5F);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 135 */         golem.addRankXp(1);
/*     */         
/* 137 */         if ((this.fp.getCurrentEquippedItem() != null) && (this.fp.getCurrentEquippedItem().stackSize <= 0)) {
/* 138 */           this.fp.setCurrentItemOrArmor(0, null);
/*     */         }
/* 140 */         dropSomeItems(this.fp, golem);
/*     */         
/* 142 */         golem.swingArm();
/*     */       }
/*     */     }
/*     */     
/* 146 */     task.setSuspended(true);
/* 147 */     return true;
/*     */   }
/*     */   
/*     */   private void dropSomeItems(FakePlayer fp2, IGolemAPI golem)
/*     */   {
/* 152 */     for (int i = 0; i < fp2.inventory.mainInventory.length; i++)
/*     */     {
/* 154 */       if (fp2.inventory.mainInventory[i] != null)
/*     */       {
/* 156 */         if (golem.canCarry(fp2.inventory.mainInventory[i], true)) {
/* 157 */           fp2.inventory.mainInventory[i] = golem.holdItem(fp2.inventory.mainInventory[i]);
/*     */         }
/*     */         
/* 160 */         if ((fp2.inventory.mainInventory[i] != null) && (fp2.inventory.mainInventory[i].stackSize > 0)) {
/* 161 */           InventoryUtils.dropItemAtEntity(golem.getGolemWorld(), fp2.inventory.mainInventory[i], golem.getGolemEntity());
/*     */         }
/* 163 */         fp2.inventory.mainInventory[i] = null;
/*     */       }
/*     */     }
/* 166 */     for (i = 0; i < fp2.inventory.armorInventory.length; i++)
/*     */     {
/* 168 */       if (fp2.inventory.armorInventory[i] != null)
/*     */       {
/* 170 */         if (golem.canCarry(fp2.inventory.armorInventory[i], true)) {
/* 171 */           fp2.inventory.armorInventory[i] = golem.holdItem(fp2.inventory.armorInventory[i]);
/*     */         }
/*     */         
/* 174 */         if ((fp2.inventory.mainInventory[i] != null) && (fp2.inventory.armorInventory[i].stackSize > 0)) {
/* 175 */           InventoryUtils.dropItemAtEntity(golem.getGolemWorld(), fp2.inventory.armorInventory[i], golem.getGolemEntity());
/*     */         }
/* 177 */         fp2.inventory.armorInventory[i] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canGolemPerformTask(IGolemAPI golem, Task task)
/*     */   {
/* 184 */     if (!this.props[6].value) {
/* 185 */       boolean found = InventoryUtils.findFirstMatchFromFilter(this.filter, this.blacklist, golem.getCarrying(), !this.props[0].value, !this.props[1].value, this.props[2].value, this.props[3].value) != null;
/*     */       
/* 187 */       if ((!found) && (getToggles()[8].value) && (!this.blacklist) && (getInv()[0] != null)) {
/* 188 */         ISealEntity se = SealHandler.getSealEntity(golem.getGolemWorld().provider.getDimensionId(), task.getSealPos());
/* 189 */         if (se != null) {
/* 190 */           ItemStack stack = getInv()[0].copy();
/* 191 */           if (!this.props[0].value) stack.setItemDamage(32767);
/* 192 */           GolemHelper.requestProvisioning(golem.getGolemWorld(), se, stack);
/*     */         }
/*     */       }
/* 195 */       return found;
/*     */     }
/* 197 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onTaskSuspension(World world, Task task) {}
/*     */   
/*     */   public boolean canPlaceAt(World world, BlockPos pos, EnumFacing side)
/*     */   {
/* 205 */     return true;
/*     */   }
/*     */   
/*     */   public ResourceLocation getSealIcon()
/*     */   {
/* 210 */     return this.icon;
/*     */   }
/*     */   
/* 213 */   ResourceLocation icon = new ResourceLocation("thaumcraft", "items/seals/seal_use");
/*     */   
/*     */ 
/*     */   public void onRemoval(World world, BlockPos pos, EnumFacing side) {}
/*     */   
/*     */   public Object returnContainer(World world, EntityPlayer player, BlockPos pos, EnumFacing side, ISealEntity seal)
/*     */   {
/* 220 */     return new SealBaseContainer(player.inventory, world, seal);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public Object returnGui(World world, EntityPlayer player, BlockPos pos, EnumFacing side, ISealEntity seal)
/*     */   {
/* 226 */     return new SealBaseGUI(player.inventory, world, seal);
/*     */   }
/*     */   
/*     */   public int[] getGuiCategories() {
/* 230 */     return new int[] { 1, 3, 0, 4 };
/*     */   }
/*     */   
/*     */   public EnumGolemTrait[] getRequiredTags() {
/* 234 */     return new EnumGolemTrait[] { EnumGolemTrait.DEFT, EnumGolemTrait.SMART };
/*     */   }
/*     */   
/*     */   public EnumGolemTrait[] getForbiddenTags()
/*     */   {
/* 239 */     return null;
/*     */   }
/*     */   
/*     */   public ISealConfigToggles.SealToggle[] getToggles()
/*     */   {
/* 244 */     return this.props;
/*     */   }
/*     */   
/* 247 */   protected ISealConfigToggles.SealToggle[] props = { new ISealConfigToggles.SealToggle(true, "pmeta", "golem.prop.meta"), new ISealConfigToggles.SealToggle(true, "pnbt", "golem.prop.nbt"), new ISealConfigToggles.SealToggle(false, "pore", "golem.prop.ore"), new ISealConfigToggles.SealToggle(false, "pmod", "golem.prop.mod"), new ISealConfigToggles.SealToggle(false, "pleft", "golem.prop.left"), new ISealConfigToggles.SealToggle(false, "pempty", "golem.prop.empty"), new ISealConfigToggles.SealToggle(false, "pemptyhand", "golem.prop.emptyhand"), new ISealConfigToggles.SealToggle(false, "psneak", "golem.prop.sneak"), new ISealConfigToggles.SealToggle(false, "ppro", "golem.prop.provision.wl") };
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
/*     */   public void setToggle(int indx, boolean value)
/*     */   {
/* 261 */     this.props[indx].setValue(value);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\seals\SealUse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */