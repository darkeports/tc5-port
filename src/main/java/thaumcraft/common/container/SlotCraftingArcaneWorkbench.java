/*     */ package thaumcraft.common.container;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.item.ItemPickaxe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.api.wands.IWand;
/*     */ 
/*     */ public class SlotCraftingArcaneWorkbench extends Slot
/*     */ {
/*     */   private final InventoryCrafting craftMatrix;
/*     */   private EntityPlayer thePlayer;
/*     */   private int amountCrafted;
/*     */   
/*     */   public SlotCraftingArcaneWorkbench(EntityPlayer par1EntityPlayer, InventoryCrafting par2IInventory, IInventory par3IInventory, int par4, int par5, int par6)
/*     */   {
/*  28 */     super(par3IInventory, par4, par5, par6);
/*  29 */     this.thePlayer = par1EntityPlayer;
/*  30 */     this.craftMatrix = par2IInventory;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isItemValid(ItemStack stack)
/*     */   {
/*  36 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack decrStackSize(int amount)
/*     */   {
/*  42 */     if (getHasStack())
/*     */     {
/*  44 */       this.amountCrafted += Math.min(amount, getStack().stackSize);
/*     */     }
/*     */     
/*  47 */     return super.decrStackSize(amount);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void onCrafting(ItemStack stack, int amount)
/*     */   {
/*  53 */     this.amountCrafted += amount;
/*  54 */     onCrafting(stack);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void onCrafting(ItemStack stack)
/*     */   {
/*  60 */     if (this.amountCrafted > 0)
/*     */     {
/*  62 */       stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
/*     */     }
/*     */     
/*  65 */     this.amountCrafted = 0;
/*     */     
/*  67 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.crafting_table))
/*     */     {
/*  69 */       this.thePlayer.triggerAchievement(AchievementList.buildWorkBench);
/*     */     }
/*     */     
/*  72 */     if ((stack.getItem() instanceof ItemPickaxe))
/*     */     {
/*  74 */       this.thePlayer.triggerAchievement(AchievementList.buildPickaxe);
/*     */     }
/*     */     
/*  77 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.furnace))
/*     */     {
/*  79 */       this.thePlayer.triggerAchievement(AchievementList.buildFurnace);
/*     */     }
/*     */     
/*  82 */     if ((stack.getItem() instanceof ItemHoe))
/*     */     {
/*  84 */       this.thePlayer.triggerAchievement(AchievementList.buildHoe);
/*     */     }
/*     */     
/*  87 */     if (stack.getItem() == Items.bread)
/*     */     {
/*  89 */       this.thePlayer.triggerAchievement(AchievementList.makeBread);
/*     */     }
/*     */     
/*  92 */     if (stack.getItem() == Items.cake)
/*     */     {
/*  94 */       this.thePlayer.triggerAchievement(AchievementList.bakeCake);
/*     */     }
/*     */     
/*  97 */     if (((stack.getItem() instanceof ItemPickaxe)) && (((ItemPickaxe)stack.getItem()).getToolMaterial() != net.minecraft.item.Item.ToolMaterial.WOOD))
/*     */     {
/*  99 */       this.thePlayer.triggerAchievement(AchievementList.buildBetterPickaxe);
/*     */     }
/*     */     
/* 102 */     if ((stack.getItem() instanceof net.minecraft.item.ItemSword))
/*     */     {
/* 104 */       this.thePlayer.triggerAchievement(AchievementList.buildSword);
/*     */     }
/*     */     
/* 107 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table))
/*     */     {
/* 109 */       this.thePlayer.triggerAchievement(AchievementList.enchantments);
/*     */     }
/*     */     
/* 112 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.bookshelf))
/*     */     {
/* 114 */       this.thePlayer.triggerAchievement(AchievementList.bookcase);
/*     */     }
/*     */     
/* 117 */     if ((stack.getItem() == Items.golden_apple) && (stack.getMetadata() == 1))
/*     */     {
/* 119 */       this.thePlayer.triggerAchievement(AchievementList.overpowered);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
/*     */   {
/* 126 */     FMLCommonHandler.instance().firePlayerCraftingEvent(playerIn, stack, this.craftMatrix);
/* 127 */     onCrafting(stack);
/* 128 */     ForgeHooks.setCraftingPlayer(playerIn);
/*     */     
/* 130 */     AspectList aspects = thaumcraft.common.lib.crafting.ThaumcraftCraftingManager.findMatchingArcaneRecipeAspects(this.craftMatrix, this.thePlayer);
/* 131 */     if ((aspects.size() > 0) && (this.craftMatrix.getStackInSlot(10) != null)) {
/* 132 */       IWand wand = (IWand)this.craftMatrix.getStackInSlot(10).getItem();
/* 133 */       wand.consumeAllVis(this.craftMatrix.getStackInSlot(10), playerIn, aspects, true, true);
/*     */     }
/*     */     
/* 136 */     ItemStack[] aitemstack = CraftingManager.getInstance().func_180303_b(this.craftMatrix, playerIn.worldObj);
/* 137 */     ForgeHooks.setCraftingPlayer(null);
/*     */     
/* 139 */     for (int i = 0; i < Math.min(9, aitemstack.length); i++)
/*     */     {
/* 141 */       ItemStack itemstack1 = this.craftMatrix.getStackInSlot(i);
/* 142 */       ItemStack itemstack2 = aitemstack[i];
/*     */       
/* 144 */       if (itemstack1 != null)
/*     */       {
/* 146 */         this.craftMatrix.decrStackSize(i, 1);
/*     */       }
/*     */       
/* 149 */       if (itemstack2 != null)
/*     */       {
/* 151 */         if (this.craftMatrix.getStackInSlot(i) == null)
/*     */         {
/* 153 */           this.craftMatrix.setInventorySlotContents(i, itemstack2);
/*     */         }
/* 155 */         else if (!this.thePlayer.inventory.addItemStackToInventory(itemstack2))
/*     */         {
/* 157 */           this.thePlayer.dropPlayerItemWithRandomChoice(itemstack2, false);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\container\SlotCraftingArcaneWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */