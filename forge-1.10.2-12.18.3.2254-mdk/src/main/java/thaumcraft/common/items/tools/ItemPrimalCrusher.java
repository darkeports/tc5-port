/*     */ package thaumcraft.common.items.tools;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.Item.ToolMaterial;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.EnumHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.IRepairable;
/*     */ import thaumcraft.api.items.IWarpingGear;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.Thaumcraft;
/*     */ import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemPrimalCrusher
/*     */   extends ItemTool
/*     */   implements IRepairable, IWarpingGear
/*     */ {
/*  38 */   public static Item.ToolMaterial material = EnumHelper.addToolMaterial("PRIMALVOID", 5, 500, 8.0F, 4.0F, 20);
/*     */   
/*     */ 
/*  41 */   private static final Set isEffective = Sets.newHashSet(new Block[] { Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail, Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium, BlocksTC.taintBlock, BlocksTC.taintFibre, Blocks.obsidian });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemPrimalCrusher()
/*     */   {
/*  52 */     super(3.5F, material, isEffective);
/*  53 */     setCreativeTab(Thaumcraft.tabTC);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canHarvestBlock(Block p_150897_1_)
/*     */   {
/*  59 */     return (p_150897_1_.getMaterial() != Material.wood) && (p_150897_1_.getMaterial() != Material.leaves) && (p_150897_1_.getMaterial() != Material.plants);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getStrVsBlock(ItemStack p_150893_1_, Block p_150893_2_)
/*     */   {
/*  67 */     return (p_150893_2_.getMaterial() != Material.iron) && (p_150893_2_.getMaterial() != Material.anvil) && (p_150893_2_.getMaterial() != Material.rock) ? super.getStrVsBlock(p_150893_1_, p_150893_2_) : this.efficiencyOnProperMaterial;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Set<String> getToolClasses(ItemStack stack)
/*     */   {
/*  74 */     return ImmutableSet.of("shovel", "pickaxe");
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*     */   {
/*  80 */     return par2ItemStack.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 1)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*     */   }
/*     */   
/*     */   private boolean isEffectiveAgainst(Block block)
/*     */   {
/*  85 */     for (Object b : isEffective) {
/*  86 */       if (b == block) {
/*  87 */         return true;
/*     */       }
/*     */     }
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   public int getItemEnchantability()
/*     */   {
/*  95 */     return 20;
/*     */   }
/*     */   
/*     */   public int getWarp(ItemStack itemstack, EntityPlayer player)
/*     */   {
/* 100 */     return 2;
/*     */   }
/*     */   
/*     */   public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
/*     */   {
/* 105 */     super.onUpdate(stack, world, entity, p_77663_4_, p_77663_5_);
/*     */     
/* 107 */     if ((stack.isItemDamaged()) && (entity != null) && (entity.ticksExisted % 20 == 0) && ((entity instanceof EntityLivingBase))) {
/* 108 */       stack.damageItem(-1, (EntityLivingBase)entity);
/*     */     }
/*     */     
/* 111 */     if (entity.ticksExisted % 100 == 0) {
/* 112 */       List<EnumInfusionEnchantment> list = EnumInfusionEnchantment.getInfusionEnchantments(stack);
/* 113 */       if (!list.contains(EnumInfusionEnchantment.DESTRUCTIVE)) {
/* 114 */         EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.DESTRUCTIVE, 1);
/*     */       }
/* 116 */       if (!list.contains(EnumInfusionEnchantment.REFINING)) {
/* 117 */         EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.REFINING, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*     */   {
/* 125 */     ItemStack w1 = new ItemStack(this);
/* 126 */     EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.DESTRUCTIVE, 1);
/* 127 */     EnumInfusionEnchantment.addInfusionEnchantment(w1, EnumInfusionEnchantment.REFINING, 1);
/* 128 */     par3List.add(w1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemPrimalCrusher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */