/*     */ package thaumcraft.common.blocks.basic;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.blocks.BlocksTC;
/*     */ import thaumcraft.common.blocks.ItemBlockTC;
/*     */ import thaumcraft.common.tiles.misc.TileBanner;
/*     */ 
/*     */ public class BlockBannerTCItem extends ItemBlockTC
/*     */ {
/*     */   public BlockBannerTCItem(Block block)
/*     */   {
/*  24 */     super(block);
/*     */   }
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass)
/*     */   {
/*  29 */     if ((renderPass == 1) && 
/*  30 */       (stack.hasTagCompound()) && (stack.getTagCompound().hasKey("color"))) {
/*  31 */       return EnumDyeColor.byDyeDamage(stack.getTagCompound().getByte("color")).getMapColor().colorValue;
/*     */     }
/*     */     
/*  34 */     if (renderPass == 2) {
/*  35 */       if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("color")) && (stack.getTagCompound().getString("aspect") != null) && (Aspect.getAspect(stack.getTagCompound().getString("aspect")) != null))
/*     */       {
/*     */ 
/*  38 */         return Aspect.getAspect(stack.getTagCompound().getString("aspect")).getColor();
/*     */       }
/*  40 */       if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("color"))) {
/*  41 */         return EnumDyeColor.byDyeDamage(stack.getTagCompound().getByte("color")).getMapColor().colorValue;
/*     */       }
/*     */     }
/*  44 */     return 16777215;
/*     */   }
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4)
/*     */   {
/*  49 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().getString("aspect") != null) && (Aspect.getAspect(stack.getTagCompound().getString("aspect")) != null))
/*     */     {
/*  51 */       list.add(Aspect.getAspect(stack.getTagCompound().getString("aspect")).getName());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String getUnlocalizedName(ItemStack stack)
/*     */   {
/*  58 */     if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("color"))) {
/*  59 */       return super.getUnlocalizedName() + "." + EnumDyeColor.byDyeDamage(stack.getTagCompound().getByte("color")).getName();
/*     */     }
/*  61 */     return super.getUnlocalizedName() + ".cultist";
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
/*     */   {
/*  67 */     if (side == EnumFacing.DOWN)
/*     */     {
/*  69 */       return false;
/*     */     }
/*  71 */     if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid())
/*     */     {
/*  73 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  77 */     pos = pos.offset(side);
/*     */     
/*  79 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*     */     {
/*  81 */       return false;
/*     */     }
/*  83 */     if (!net.minecraft.init.Blocks.standing_banner.canPlaceBlockAt(worldIn, pos))
/*     */     {
/*  85 */       return false;
/*     */     }
/*  87 */     if (worldIn.isRemote)
/*     */     {
/*  89 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  94 */     worldIn.setBlockState(pos, BlocksTC.banner.getDefaultState(), 3);
/*     */     
/*  96 */     TileBanner tile = (TileBanner)worldIn.getTileEntity(pos);
/*  97 */     if (tile != null) {
/*  98 */       if (side == EnumFacing.UP)
/*     */       {
/* 100 */         int i = MathHelper.floor_double((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 0xF;
/* 101 */         tile.setBannerFacing((byte)i);
/*     */       }
/*     */       else
/*     */       {
/* 105 */         tile.setWall(true);
/*     */         
/* 107 */         int i = 0;
/*     */         
/* 109 */         if (side == EnumFacing.NORTH)
/*     */         {
/* 111 */           i = 8;
/*     */         }
/*     */         
/* 114 */         if (side == EnumFacing.WEST)
/*     */         {
/* 116 */           i = 4;
/*     */         }
/*     */         
/* 119 */         if (side == EnumFacing.EAST)
/*     */         {
/* 121 */           i = 12;
/*     */         }
/*     */         
/* 124 */         tile.setBannerFacing((byte)i);
/*     */       }
/*     */       
/* 127 */       if (stack.hasTagCompound()) {
/* 128 */         if (stack.getTagCompound().getString("aspect") != null) {
/* 129 */           tile.setAspect(Aspect.getAspect(stack.getTagCompound().getString("aspect")));
/*     */         }
/* 131 */         if (stack.getTagCompound().hasKey("color")) {
/* 132 */           tile.setColor(stack.getTagCompound().getByte("color"));
/*     */         }
/*     */       }
/*     */       
/* 136 */       tile.markDirty();
/* 137 */       worldIn.markBlockForUpdate(pos);
/*     */     }
/*     */     
/* 140 */     stack.stackSize -= 1;
/*     */     
/* 142 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\blocks\basic\BlockBannerTCItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */