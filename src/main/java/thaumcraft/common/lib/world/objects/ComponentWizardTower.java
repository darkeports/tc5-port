/*     */ package thaumcraft.common.lib.world.objects;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ import net.minecraft.world.gen.structure.StructureComponent;
/*     */ import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
/*     */ import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
/*     */ import net.minecraftforge.common.ChestGenHooks;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.api.items.ItemsTC;
/*     */ import thaumcraft.common.config.ConfigEntities;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComponentWizardTower
/*     */   extends StructureVillagePieces.Village
/*     */ {
/*     */   public ComponentWizardTower(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, EnumFacing par5)
/*     */   {
/*  31 */     super(par1ComponentVillageStartPiece, par2);
/*  32 */     this.coordBaseMode = par5;
/*  33 */     this.boundingBox = par4StructureBoundingBox;
/*     */   }
/*     */   
/*  36 */   private int averageGroundLevel = -1;
/*     */   
/*  38 */   public static final List towerChestContents = Lists.newArrayList(new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.glowstone_dust, 0, 1, 3, 3), new WeightedRandomChestContent(Items.glass_bottle, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_nugget, 0, 1, 3, 5), new WeightedRandomChestContent(Items.fire_charge, 0, 1, 1, 5), new WeightedRandomChestContent(Items.skull, 0, 1, 1, 3), new WeightedRandomChestContent(ItemsTC.knowledgeFragment, 0, 1, 3, 1), new WeightedRandomChestContent(ItemsTC.alumentum, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(BlocksTC.nitor), 1, 1, 1, 5), new WeightedRandomChestContent(ItemsTC.thaumonomicon, 0, 1, 1, 20) });
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
/*     */   static
/*     */   {
/*  51 */     ChestGenHooks.init("towerChestContents", towerChestContents, 4, 9);
/*     */   }
/*     */   
/*     */   public static StructureVillagePieces.Village buildComponent(StructureVillagePieces.Start startPiece, List pieces, Random random, int par3, int par4, int par5, EnumFacing par6, int par7)
/*     */   {
/*  56 */     StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 5, 12, 5, par6);
/*  57 */     return (!canVillageGoDeeper(var8)) || (StructureComponent.findIntersecting(pieces, var8) != null) ? null : new ComponentWizardTower(startPiece, par7, random, var8, par6);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean addComponentParts(World world, Random par2Random, StructureBoundingBox bb)
/*     */   {
/*  68 */     if (this.averageGroundLevel < 0)
/*     */     {
/*  70 */       this.averageGroundLevel = getAverageGroundLevel(world, bb);
/*     */       
/*  72 */       if (this.averageGroundLevel < 0)
/*     */       {
/*  74 */         return true;
/*     */       }
/*     */       
/*  77 */       this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 12 - 1, 0);
/*     */     }
/*     */     
/*  80 */     fillWithBlocks(world, bb, 2, 1, 2, 4, 11, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  81 */     fillWithBlocks(world, bb, 2, 0, 2, 4, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  82 */     fillWithBlocks(world, bb, 2, 5, 2, 4, 5, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  83 */     fillWithBlocks(world, bb, 2, 10, 2, 4, 10, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  84 */     fillWithBlocks(world, bb, 1, 0, 2, 1, 11, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  85 */     fillWithBlocks(world, bb, 2, 0, 1, 4, 11, 1, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  86 */     fillWithBlocks(world, bb, 5, 0, 2, 5, 11, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  87 */     fillWithBlocks(world, bb, 2, 0, 5, 4, 11, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  88 */     fillWithBlocks(world, bb, 2, 0, 5, 4, 11, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  89 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 1, 0, 1, bb);
/*  90 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 1, 0, 5, bb);
/*  91 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 5, 0, 1, bb);
/*  92 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 5, 0, 5, bb);
/*     */     
/*  94 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 1, 5, 1, bb);
/*  95 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 1, 5, 5, bb);
/*  96 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 5, 5, 1, bb);
/*  97 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 5, 5, 5, bb);
/*     */     
/*  99 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 1, 10, 1, bb);
/* 100 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 1, 10, 5, bb);
/* 101 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 5, 10, 1, bb);
/* 102 */     setBlockState(world, Blocks.cobblestone.getStateFromMeta(3), 5, 10, 5, bb);
/*     */     
/* 104 */     setBlockState(world, Blocks.glass_pane.getDefaultState(), 3, 7, 1, bb);
/* 105 */     setBlockState(world, Blocks.glass_pane.getDefaultState(), 3, 8, 1, bb);
/* 106 */     setBlockState(world, Blocks.glass_pane.getDefaultState(), 3, 7, 5, bb);
/* 107 */     setBlockState(world, Blocks.glass_pane.getDefaultState(), 3, 8, 5, bb);
/* 108 */     setBlockState(world, Blocks.glass_pane.getDefaultState(), 3, 2, 5, bb);
/* 109 */     setBlockState(world, Blocks.glass_pane.getDefaultState(), 3, 3, 5, bb);
/*     */     
/* 111 */     int var4 = getMetadataWithOffset(Blocks.ladder, 4);
/* 112 */     for (int var5 = 1; var5 <= 9; var5++)
/*     */     {
/* 114 */       setBlockState(world, Blocks.ladder.getStateFromMeta(var4), 4, var5, 3, bb);
/*     */     }
/* 116 */     setBlockState(world, Blocks.trapdoor.getDefaultState(), 4, 10, 3, bb);
/* 117 */     setBlockState(world, Blocks.glowstone.getDefaultState(), 3, 5, 3, bb);
/*     */     
/* 119 */     generateChestContents(world, bb, par2Random, 2, 6, 2, ChestGenHooks.getItems("towerChestContents", par2Random), ChestGenHooks.getCount("towerChestContents", par2Random));
/*     */     
/*     */ 
/*     */ 
/* 123 */     setBlockState(world, Blocks.air.getDefaultState(), 3, 1, 1, bb);
/* 124 */     setBlockState(world, Blocks.air.getDefaultState(), 3, 2, 1, bb);
/* 125 */     placeDoorCurrentPosition(world, bb, par2Random, 3, 1, 1, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*     */     
/*     */ 
/* 128 */     if ((getBlockStateFromPos(world, 3, 0, 0, bb).getBlock().getMaterial() == Material.air) && (getBlockStateFromPos(world, 3, -1, 0, bb).getBlock().getMaterial() != Material.air))
/*     */     {
/*     */ 
/* 131 */       setBlockState(world, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 3, 0, 0, bb);
/*     */     }
/*     */     
/*     */ 
/* 135 */     for (int var5 = 0; var5 < 12; var5++)
/*     */     {
/* 137 */       for (int var6 = 0; var6 < 5; var6++)
/*     */       {
/* 139 */         clearCurrentPositionBlocksUpwards(world, var6, 12, var5, bb);
/* 140 */         replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), var6, -1, var5, bb);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 145 */     spawnVillagers(world, bb, 7, 1, 1, 1);
/* 146 */     return true;
/*     */   }
/*     */   
/*     */   protected int func_180779_c(int p_180779_1_, int p_180779_2_)
/*     */   {
/* 151 */     return ConfigEntities.entWizardId;
/*     */   }
/*     */   
/*     */   public ComponentWizardTower() {}
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\objects\ComponentWizardTower.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */