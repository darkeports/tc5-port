/*     */ package thaumcraft.common.items.tools;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectHelper;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.research.ScanningManager;
/*     */ import thaumcraft.client.fx.FXDispatcher;
/*     */ import thaumcraft.common.CommonProxy;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.aura.AuraChunk;
/*     */ import thaumcraft.common.lib.aura.AuraHandler;
/*     */ import thaumcraft.common.lib.network.misc.PacketAuraToClient;
/*     */ import thaumcraft.common.lib.utils.EntityUtils;
/*     */ 
/*     */ public class ItemThaumometer extends Item implements thaumcraft.common.items.ISubItems
/*     */ {
/*     */   public ItemThaumometer()
/*     */   {
/*  41 */     setMaxStackSize(1);
/*  42 */     setNoRepair();
/*  43 */     setHasSubtypes(true);
/*  44 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */   public int[] getSubItems()
/*     */   {
/*  49 */     return new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*     */   {
/*  55 */     par3List.add(new ItemStack(this, 1, 0));
/*     */   }
/*     */   
/*     */ 
/*     */   public EnumRarity getRarity(ItemStack itemstack)
/*     */   {
/*  61 */     return EnumRarity.UNCOMMON;
/*     */   }
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*     */   {
/*  66 */     super.addInformation(stack, player, list, par4);
/*  67 */     String text = StatCollector.translateToLocal("tc.thaumometer");
/*     */     try {
/*  69 */       text = text.replace("$s", org.lwjgl.input.Keyboard.getKeyName(Thaumcraft.proxy.getKeyBindings().keyF.getKeyCode()));
/*  70 */       list.add(EnumChatFormatting.DARK_PURPLE + text);
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer p)
/*     */   {
/*  77 */     if (world.isRemote) {
/*  78 */       drawFX(world, p);
/*  79 */       p.worldObj.playSound(p.posX, p.posY, p.posZ, "thaumcraft:scan", 0.5F, 1.0F, false);
/*     */     } else {
/*  81 */       doScan(world, p);
/*     */     }
/*  83 */     return stack;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
/*     */   {
/*  89 */     if ((isSelected) && (!world.isRemote) && (entity.ticksExisted % 20 == 0) && ((entity instanceof EntityPlayerMP))) {
/*  90 */       updateAura(stack, world, entity);
/*     */     }
/*     */     
/*  93 */     if ((isSelected) && (world.isRemote) && (entity.ticksExisted % 5 == 0) && ((entity instanceof EntityPlayer)))
/*     */     {
/*  95 */       Entity target = EntityUtils.getPointedEntity(world, entity, 1.0D, 16.0D, 5.0F, true);
/*  96 */       if ((target != null) && (ScanningManager.isThingStillScannable((EntityPlayer)entity, target))) {
/*  97 */         for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(2); a++) {
/*  98 */           Thaumcraft.proxy.getFX().scanHighlight((float)target.posX + (world.rand.nextFloat() - world.rand.nextFloat()) * (target.width * 0.7F), (float)target.posY + world.rand.nextFloat() * target.height, (float)target.posZ + (world.rand.nextFloat() - world.rand.nextFloat()) * (target.width * 0.7F));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 103 */         MovingObjectPosition mop = getMovingObjectPositionFromPlayerWild(world, (EntityPlayer)entity, true);
/* 104 */         if ((mop != null) && (mop.getBlockPos() != null) && (ScanningManager.isThingStillScannable((EntityPlayer)entity, mop.getBlockPos())))
/*     */         {
/* 106 */           for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(2); a++) {
/* 107 */             Thaumcraft.proxy.getFX().scanHighlight(mop.getBlockPos().getX() - 0.3F + world.rand.nextFloat() * 1.6F, mop.getBlockPos().getY() - 0.3F + world.rand.nextFloat() * 1.6F, mop.getBlockPos().getZ() - 0.3F + world.rand.nextFloat() * 1.6F);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MovingObjectPosition getMovingObjectPositionFromPlayerWild(World worldIn, EntityPlayer playerIn, boolean useLiquids)
/*     */   {
/* 120 */     float f = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) + worldIn.rand.nextInt(20) - worldIn.rand.nextInt(20);
/* 121 */     float f1 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) + worldIn.rand.nextInt(20) - worldIn.rand.nextInt(20);
/* 122 */     double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX);
/* 123 */     double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) + playerIn.getEyeHeight();
/* 124 */     double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ);
/* 125 */     Vec3 vec3 = new Vec3(d0, d1, d2);
/* 126 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/* 127 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/* 128 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/* 129 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/* 130 */     float f6 = f3 * f4;
/* 131 */     float f7 = f2 * f4;
/* 132 */     double d3 = 16.0D;
/* 133 */     Vec3 vec31 = vec3.addVector(f6 * d3, f5 * d3, f7 * d3);
/* 134 */     return worldIn.rayTraceBlocks(vec3, vec31, useLiquids, !useLiquids, false);
/*     */   }
/*     */   
/*     */   private void updateAura(ItemStack stack, World world, Entity entity) {
/* 138 */     AuraChunk ac = AuraHandler.getAuraChunk(world.provider.getDimensionId(), (int)entity.posX >> 4, (int)entity.posZ >> 4);
/*     */     
/* 140 */     if (ac != null) {
/* 141 */       short b = ac.getBase();
/* 142 */       AspectList c = ac.getCurrentAspects();
/* 143 */       AspectList r = new AspectList();
/* 144 */       for (Aspect aspect : AspectHelper.getAuraAspects(ac.getCurrentAspects()).getAspects()) {
/* 145 */         if (getAspect(stack.getItemDamage()) != null) {
/* 146 */           r.add(Aspect.CRYSTAL, b);
/* 147 */           if (getAspect(stack.getItemDamage()) != aspect) {
/*     */             continue;
/*     */           }
/* 150 */           r.add(Aspect.VOID, (int)(b * 1.1F));
/*     */         }
/*     */         else {
/* 153 */           r.add(Aspect.VOID, c.getAmount(aspect));
/*     */         }
/* 155 */         r.add(aspect, c.getAmount(aspect));
/*     */       }
/*     */       
/* 158 */       thaumcraft.common.lib.network.PacketHandler.INSTANCE.sendTo(new PacketAuraToClient(r), (EntityPlayerMP)entity);
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawFX(World worldIn, EntityPlayer playerIn) {
/* 163 */     Entity target = EntityUtils.getPointedEntity(worldIn, playerIn, 1.0D, 9.0D, 0.0F, true);
/* 164 */     if (target != null) {
/* 165 */       for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(5); a++) {
/* 166 */         Thaumcraft.proxy.getFX().blockRunes(target.posX - 0.5D, target.posY + target.getEyeHeight() / 2.0F, target.posZ - 0.5D, 0.3F + worldIn.rand.nextFloat() * 0.7F, 0.0F, 0.3F + worldIn.rand.nextFloat() * 0.7F, (int)(target.height * 15.0F), 0.03F);
/*     */       }
/*     */       
/*     */     }
/*     */     else
/*     */     {
/* 172 */       MovingObjectPosition mop = getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
/* 173 */       if ((mop != null) && (mop.getBlockPos() != null)) {
/* 174 */         for (int a = 0; a < Thaumcraft.proxy.getFX().particleCount(5); a++) {
/* 175 */           Thaumcraft.proxy.getFX().blockRunes(mop.getBlockPos().getX(), mop.getBlockPos().getY() + 0.25D, mop.getBlockPos().getZ(), 0.3F + worldIn.rand.nextFloat() * 0.7F, 0.0F, 0.3F + worldIn.rand.nextFloat() * 0.7F, 15, 0.03F);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private Aspect getAspect(int b)
/*     */   {
/* 183 */     switch (b) {
/* 184 */     default:  return null;
/* 185 */     case 1:  return Aspect.AIR;
/* 186 */     case 2:  return Aspect.FIRE;
/* 187 */     case 3:  return Aspect.WATER;
/* 188 */     case 4:  return Aspect.EARTH;
/* 189 */     case 5:  return Aspect.ORDER;
/* 190 */     case 6:  return Aspect.ENTROPY; }
/* 191 */     return Aspect.FLUX;
/*     */   }
/*     */   
/*     */   public void doScan(World worldIn, EntityPlayer playerIn)
/*     */   {
/* 196 */     if (!worldIn.isRemote) {
/* 197 */       Entity target = EntityUtils.getPointedEntity(worldIn, playerIn, 1.0D, 9.0D, 0.0F, true);
/* 198 */       if (target != null) {
/* 199 */         ScanningManager.scanTheThing(playerIn, target);
/*     */       } else {
/* 201 */         MovingObjectPosition mop = getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
/* 202 */         if ((mop != null) && (mop.getBlockPos() != null)) {
/* 203 */           ScanningManager.scanTheThing(playerIn, mop.getBlockPos());
/*     */         } else {
/* 205 */           ScanningManager.scanTheThing(playerIn, null);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void changeVis(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 212 */     int d = itemStackIn.getItemDamage() + 1;
/* 213 */     if (d > 7) d = 0;
/* 214 */     itemStackIn.setItemDamage(d);
/* 215 */     if (!worldIn.isRemote) {
/* 216 */       if (d > 0) {
/* 217 */         playerIn.addChatMessage(new ChatComponentText(String.format(StatCollector.translateToLocal("tc.dioptra.1"), new Object[] { ((ItemThaumometer)itemStackIn.getItem()).getAspect(d).getName() })));
/*     */       }
/*     */       else {
/* 220 */         playerIn.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("tc.dioptra.3")));
/*     */       }
/* 222 */       ((ItemThaumometer)itemStackIn.getItem()).updateAura(itemStackIn, worldIn, playerIn);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemThaumometer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */