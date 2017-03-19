/*     */ package thaumcraft.common.entities.construct.golem;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagLong;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.golems.EnumGolemTrait;
/*     */ import thaumcraft.api.golems.IGolemProperties;
/*     */ import thaumcraft.api.golems.ISealDisplayer;
/*     */ import thaumcraft.api.golems.parts.GolemMaterial;
/*     */ 
/*     */ public class ItemGolemPlacer extends Item implements ISealDisplayer
/*     */ {
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*     */   {
/*  31 */     ItemStack is = new ItemStack(this, 1, 0);
/*  32 */     is.setTagInfo("props", new NBTTagLong(0L));
/*  33 */     par3List.add(is.copy());
/*  34 */     IGolemProperties props = new GolemProperties();
/*  35 */     props.setHead(thaumcraft.api.golems.parts.GolemHead.getHeads()[1]);
/*  36 */     props.setArms(thaumcraft.api.golems.parts.GolemArm.getArms()[1]);
/*  37 */     is.setTagInfo("props", new NBTTagLong(props.toLong()));
/*  38 */     par3List.add(is.copy());
/*     */     
/*  40 */     props = new GolemProperties();
/*  41 */     props.setMaterial(GolemMaterial.getMaterials()[1]);
/*  42 */     props.setHead(thaumcraft.api.golems.parts.GolemHead.getHeads()[1]);
/*  43 */     props.setArms(thaumcraft.api.golems.parts.GolemArm.getArms()[2]);
/*  44 */     is.setTagInfo("props", new NBTTagLong(props.toLong()));
/*  45 */     par3List.add(is.copy());
/*     */     
/*  47 */     props = new GolemProperties();
/*  48 */     props.setMaterial(GolemMaterial.getMaterials()[4]);
/*  49 */     props.setHead(thaumcraft.api.golems.parts.GolemHead.getHeads()[1]);
/*  50 */     props.setArms(thaumcraft.api.golems.parts.GolemArm.getArms()[3]);
/*  51 */     is.setTagInfo("props", new NBTTagLong(props.toLong()));
/*  52 */     par3List.add(is.copy());
/*     */   }
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass)
/*     */   {
/*  57 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("props"))) {
/*  58 */       IGolemProperties props = GolemProperties.fromLong(stack.getTagCompound().getLong("props"));
/*  59 */       return props.getMaterial().itemColor;
/*     */     }
/*  61 */     return super.getColorFromItemStack(stack, renderPass);
/*     */   }
/*     */   
/*     */ 
/*     */   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
/*     */   {
/*  67 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("props"))) {
/*  68 */       IGolemProperties props = GolemProperties.fromLong(stack.getTagCompound().getLong("props"));
/*     */       
/*  70 */       if (props.hasTrait(EnumGolemTrait.SMART)) {
/*  71 */         if (props.getRank() >= 10) {
/*  72 */           list.add("§6" + StatCollector.translateToLocal("golem.rank") + " " + props.getRank());
/*     */         } else {
/*  74 */           int rx = stack.getTagCompound().getInteger("xp");
/*  75 */           int xn = (props.getRank() + 1) * (props.getRank() + 1) * 1000;
/*  76 */           list.add("§6" + StatCollector.translateToLocal("golem.rank") + " " + props.getRank() + " §2(" + rx + "/" + xn + ")");
/*     */         }
/*     */       }
/*     */       
/*  80 */       list.add("§a" + props.getMaterial().getLocalizedName());
/*     */       
/*  82 */       for (EnumGolemTrait tag : props.getTraits()) {
/*  83 */         list.add("§9-" + tag.getLocalizedName());
/*     */       }
/*     */     }
/*  86 */     super.addInformation(stack, player, list, par4);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/*  94 */     IBlockState bs = world.getBlockState(pos);
/*     */     
/*  96 */     if (!bs.getBlock().getMaterial().isSolid()) return false;
/*  97 */     if (world.isRemote) { return false;
/*     */     }
/*  99 */     pos = pos.offset(side);
/* 100 */     bs = world.getBlockState(pos);
/*     */     
/* 102 */     if (!player.canPlayerEdit(pos, side, stack)) { return false;
/*     */     }
/* 104 */     EntityThaumcraftGolem golem = new EntityThaumcraftGolem(world);
/* 105 */     golem.setPositionAndRotation(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
/* 106 */     if ((golem != null) && (world.spawnEntityInWorld(golem))) {
/* 107 */       golem.setOwned(true);
/* 108 */       golem.setValidSpawn();
/* 109 */       golem.setOwnerId(player.getUniqueID().toString());
/* 110 */       if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("props"))) {
/* 111 */         golem.setProperties(GolemProperties.fromLong(stack.getTagCompound().getLong("props")));
/*     */       }
/* 113 */       if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("xp"))) {
/* 114 */         golem.rankXp = stack.getTagCompound().getInteger("xp");
/*     */       }
/* 116 */       golem.onInitialSpawn(world.getDifficultyForLocation(pos), (IEntityLivingData)null);
/* 117 */       if (!player.capabilities.isCreativeMode) { stack.stackSize -= 1;
/*     */       }
/*     */     }
/*     */     
/* 121 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\entities\construct\golem\ItemGolemPlacer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */