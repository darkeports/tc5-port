/*     */ package thaumcraft.common.lib.world.objects;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ import net.minecraft.world.gen.structure.StructureComponent;
/*     */ import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
/*     */ import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
/*     */ import thaumcraft.common.config.ConfigEntities;
/*     */ 
/*     */ public class ComponentBankerHome extends StructureVillagePieces.Village
/*     */ {
/*     */   private boolean isTallHouse;
/*     */   private int tablePosition;
/*     */   
/*     */   public ComponentBankerHome() {}
/*     */   
/*     */   public ComponentBankerHome(StructureVillagePieces.Start p_i2101_1_, int p_i2101_2_, Random p_i2101_3_, StructureBoundingBox p_i2101_4_, EnumFacing p_i2101_5_)
/*     */   {
/*  27 */     super(p_i2101_1_, p_i2101_2_);
/*  28 */     this.coordBaseMode = p_i2101_5_;
/*  29 */     this.boundingBox = p_i2101_4_;
/*  30 */     this.isTallHouse = p_i2101_3_.nextBoolean();
/*  31 */     this.tablePosition = p_i2101_3_.nextInt(3);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void writeStructureToNBT(NBTTagCompound p_143012_1_)
/*     */   {
/*  37 */     super.writeStructureToNBT(p_143012_1_);
/*  38 */     p_143012_1_.setInteger("T", this.tablePosition);
/*  39 */     p_143012_1_.setBoolean("C", this.isTallHouse);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void readStructureFromNBT(NBTTagCompound p_143011_1_)
/*     */   {
/*  45 */     super.readStructureFromNBT(p_143011_1_);
/*  46 */     this.tablePosition = p_143011_1_.getInteger("T");
/*  47 */     this.isTallHouse = p_143011_1_.getBoolean("C");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static StructureVillagePieces.Village buildComponent(StructureVillagePieces.Start p_74908_0_, List p_74908_1_, Random p_74908_2_, int p_74908_3_, int p_74908_4_, int p_74908_5_, EnumFacing p_74908_6_, int p_74908_7_)
/*     */   {
/*  54 */     StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74908_3_, p_74908_4_, p_74908_5_, 0, 0, 0, 4, 6, 5, p_74908_6_);
/*  55 */     return (canVillageGoDeeper(structureboundingbox)) && (StructureComponent.findIntersecting(p_74908_1_, structureboundingbox) == null) ? new ComponentBankerHome(p_74908_0_, p_74908_7_, p_74908_2_, structureboundingbox, p_74908_6_) : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
/*     */   {
/*  65 */     if (this.field_143015_k < 0)
/*     */     {
/*  67 */       this.field_143015_k = getAverageGroundLevel(p_74875_1_, p_74875_3_);
/*     */       
/*  69 */       if (this.field_143015_k < 0)
/*     */       {
/*  71 */         return true;
/*     */       }
/*     */       
/*  74 */       this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
/*     */     }
/*     */     
/*  77 */     fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 3, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  78 */     fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 3, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  79 */     fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 1, 2, 0, 3, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
/*     */     
/*  81 */     if (this.isTallHouse)
/*     */     {
/*  83 */       fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 1, 2, 4, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*     */     }
/*     */     else
/*     */     {
/*  87 */       fillWithBlocks(p_74875_1_, p_74875_3_, 1, 5, 1, 2, 5, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*     */     }
/*     */     
/*  90 */     setBlockState(p_74875_1_, Blocks.log.getDefaultState(), 1, 4, 0, p_74875_3_);
/*  91 */     setBlockState(p_74875_1_, Blocks.log.getDefaultState(), 2, 4, 0, p_74875_3_);
/*  92 */     setBlockState(p_74875_1_, Blocks.log.getDefaultState(), 1, 4, 4, p_74875_3_);
/*  93 */     setBlockState(p_74875_1_, Blocks.log.getDefaultState(), 2, 4, 4, p_74875_3_);
/*  94 */     setBlockState(p_74875_1_, Blocks.log.getDefaultState(), 0, 4, 1, p_74875_3_);
/*  95 */     setBlockState(p_74875_1_, Blocks.log.getDefaultState(), 0, 4, 2, p_74875_3_);
/*  96 */     setBlockState(p_74875_1_, Blocks.log.getDefaultState(), 0, 4, 3, p_74875_3_);
/*  97 */     setBlockState(p_74875_1_, Blocks.log.getDefaultState(), 3, 4, 1, p_74875_3_);
/*  98 */     setBlockState(p_74875_1_, Blocks.log.getDefaultState(), 3, 4, 2, p_74875_3_);
/*  99 */     setBlockState(p_74875_1_, Blocks.log.getDefaultState(), 3, 4, 3, p_74875_3_);
/* 100 */     fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 101 */     fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 0, 3, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 102 */     fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 4, 0, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 103 */     fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 4, 3, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 104 */     fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 105 */     fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 106 */     fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 107 */     fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 108 */     setBlockState(p_74875_1_, Blocks.iron_bars.getDefaultState(), 0, 2, 2, p_74875_3_);
/* 109 */     setBlockState(p_74875_1_, Blocks.iron_bars.getDefaultState(), 3, 2, 2, p_74875_3_);
/*     */     
/* 111 */     if (this.tablePosition > 0)
/*     */     {
/* 113 */       setBlockState(p_74875_1_, Blocks.cobblestone_wall.getDefaultState(), this.tablePosition, 1, 3, p_74875_3_);
/* 114 */       setBlockState(p_74875_1_, Blocks.stone_pressure_plate.getDefaultState(), this.tablePosition, 2, 3, p_74875_3_);
/*     */     }
/*     */     
/* 117 */     setBlockState(p_74875_1_, Blocks.air.getDefaultState(), 1, 1, 0, p_74875_3_);
/* 118 */     setBlockState(p_74875_1_, Blocks.air.getDefaultState(), 1, 2, 0, p_74875_3_);
/* 119 */     placeDoorCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 1, 1, 0, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*     */     
/*     */ 
/* 122 */     if ((getBlockStateFromPos(p_74875_1_, 1, 0, -1, p_74875_3_).getBlock().getMaterial() == Material.air) && (getBlockStateFromPos(p_74875_1_, 1, -1, -1, p_74875_3_).getBlock().getMaterial() != Material.air))
/*     */     {
/*     */ 
/* 125 */       setBlockState(p_74875_1_, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, p_74875_3_);
/*     */     }
/*     */     
/* 128 */     for (int i = 0; i < 5; i++)
/*     */     {
/* 130 */       for (int j = 0; j < 4; j++)
/*     */       {
/* 132 */         clearCurrentPositionBlocksUpwards(p_74875_1_, j, 6, i, p_74875_3_);
/* 133 */         replaceAirAndLiquidDownwards(p_74875_1_, Blocks.cobblestone.getDefaultState(), j, -1, i, p_74875_3_);
/*     */       }
/*     */     }
/*     */     
/* 137 */     spawnVillagers(p_74875_1_, p_74875_3_, 1, 1, 2, 1);
/* 138 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected int func_180779_c(int p_180779_1_, int p_180779_2_)
/*     */   {
/* 145 */     return ConfigEntities.entBankerId;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\lib\world\objects\ComponentBankerHome.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */