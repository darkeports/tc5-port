/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.items.CapabilityItemHandler;
/*     */ import net.minecraftforge.items.IItemHandler;
/*     */ import net.minecraftforge.items.ItemHandlerHelper;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
import thaumcraft.codechicken.libold.raytracer.IndexedCuboid6;
import thaumcraft.codechicken.libold.vec.Cuboid6;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ import thaumcraft.common.lib.utils.InventoryUtils;
/*     */ 
/*     */ public class TilePatternCrafter
/*     */   extends TileThaumcraft implements ITickable
/*     */ {
/*  30 */   public byte type = 0;
/*  31 */   public int count = new Random(System.currentTimeMillis()).nextInt(20);
/*     */   
/*  33 */   private final InventoryCrafting craftMatrix = new InventoryCrafting(new Container()
/*     */   {
/*     */     public boolean canInteractWith(EntityPlayer playerIn)
/*     */     {
/*  37 */       return false;
/*     */     }
/*  33 */   }, 3, 3);
/*     */   
/*     */ 
/*     */ 
/*     */   public float rot;
/*     */   
/*     */ 
/*     */ 
/*     */   public float rp;
/*     */   
/*     */ 
/*     */ 
/*     */   public void readCustomNBT(NBTTagCompound nbt)
/*     */   {
/*  47 */     super.readCustomNBT(nbt);
/*  48 */     this.type = nbt.getByte("type");
/*     */   }
/*     */   
/*     */   public void writeCustomNBT(NBTTagCompound nbt)
/*     */   {
/*  53 */     super.writeCustomNBT(nbt);
/*  54 */     nbt.setByte("type", this.type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  59 */   public int rotTicks = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   public void update()
/*     */   {
/*  65 */     if (this.worldObj.isRemote) {
/*  66 */       if (this.rotTicks > 0) {
/*  67 */         this.rotTicks -= 1;
/*  68 */         this.rp += 1.0F;
/*     */       } else {
/*  70 */         this.rp *= 0.8F;
/*     */       }
/*  72 */       this.rot += this.rp;
/*     */     }
/*  74 */     if ((!this.worldObj.isRemote) && (this.count++ % 20 == 0) && (BlockStateUtils.isEnabled(getBlockMetadata())))
/*     */     {
/*  76 */       TileEntity above = this.worldObj.getTileEntity(getPos().up());
/*  77 */       TileEntity below = this.worldObj.getTileEntity(getPos().down());
/*     */       
/*  79 */       if ((above != null) && (above.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)) && (below != null) && (below.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)))
/*     */       {
/*     */ 
/*  82 */         IItemHandler ca = (IItemHandler)above.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
/*  83 */         IItemHandler cb = (IItemHandler)below.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
/*     */         
/*  85 */         for (int a = 0; a < ca.getSlots(); a++) {
/*  86 */           ItemStack as = ca.getStackInSlot(a);
/*  87 */           int amt = 9;
/*  88 */           switch (this.type) {
/*  89 */           case 0:  amt = 9; break;
/*  90 */           case 1:  amt = 1; break;
/*  91 */           case 2: case 3:  amt = 2; break;
/*  92 */           case 4:  amt = 4; break;
/*  93 */           case 5: case 6:  amt = 3; break;
/*  94 */           case 7: case 8:  amt = 6; break;
/*  95 */           case 9:  amt = 8;
/*     */           }
/*  97 */           if ((as != null) && (InventoryUtils.consumeAmount(ca, as, amt, false, false, true, false, true)) && 
/*  98 */             (craft(as)) && 
/*  99 */             (ItemHandlerHelper.insertItem(cb, this.outStack.copy(), true) == null)) {
/* 100 */             boolean b = true;
/* 101 */             for (int i = 0; i < 9; i++) {
/* 102 */               if ((this.craftMatrix.getStackInSlot(i) != null) && (ItemHandlerHelper.insertItem(cb, this.craftMatrix.getStackInSlot(i).copy(), true) != null))
/*     */               {
/*     */ 
/* 105 */                 b = false;
/* 106 */                 break;
/*     */               }
/*     */             }
/* 109 */             if (b) {
/* 110 */               ItemHandlerHelper.insertItem(cb, this.outStack.copy(), false);
/* 111 */               for (int i = 0; i < 9; i++) {
/* 112 */                 if (this.craftMatrix.getStackInSlot(i) != null)
/* 113 */                   ItemHandlerHelper.insertItem(cb, this.craftMatrix.getStackInSlot(i).copy(), false);
/*     */               }
/* 115 */               InventoryUtils.consumeAmount(ca, as, amt, false, false, true, false, false);
/* 116 */               this.worldObj.addBlockEvent(getPos(), getBlockType(), 1, 0);
/* 117 */               break;
/*     */             }
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
/* 129 */   ItemStack outStack = null;
/*     */   
/*     */   private boolean craft(ItemStack inStack)
/*     */   {
/* 133 */     this.outStack = null;
/* 134 */     this.craftMatrix.clear();
/* 135 */     switch (this.type) {
/*     */     case 0: 
/* 137 */       for (int a = 0; a < 9; a++)
/* 138 */         this.craftMatrix.setInventorySlotContents(a, ItemHandlerHelper.copyStackWithSize(inStack, 1));
/* 139 */       break;
/*     */     case 1: 
/* 141 */       this.craftMatrix.setInventorySlotContents(0, ItemHandlerHelper.copyStackWithSize(inStack, 1));
/* 142 */       break;
/*     */     case 2: 
/* 144 */       for (int a = 0; a < 2; a++)
/* 145 */         this.craftMatrix.setInventorySlotContents(a, ItemHandlerHelper.copyStackWithSize(inStack, 1));
/* 146 */       break;
/*     */     case 3: 
/* 148 */       for (int a = 0; a < 2; a++)
/* 149 */         this.craftMatrix.setInventorySlotContents(a * 3, ItemHandlerHelper.copyStackWithSize(inStack, 1));
/* 150 */       break;
/*     */     case 4: 
/* 152 */       for (int a = 0; a < 2; a++)
/* 153 */         for (int b = 0; b < 2; b++)
/* 154 */           this.craftMatrix.setInventorySlotContents(a + b * 3, ItemHandlerHelper.copyStackWithSize(inStack, 1));
/* 155 */       break;
/*     */     case 5: 
/* 157 */       for (int a = 0; a < 3; a++)
/* 158 */         this.craftMatrix.setInventorySlotContents(a, ItemHandlerHelper.copyStackWithSize(inStack, 1));
/* 159 */       break;
/*     */     case 6: 
/* 161 */       for (int a = 0; a < 3; a++)
/* 162 */         this.craftMatrix.setInventorySlotContents(a * 3, ItemHandlerHelper.copyStackWithSize(inStack, 1));
/* 163 */       break;
/*     */     case 7: 
/* 165 */       for (int a = 0; a < 6; a++)
/* 166 */         this.craftMatrix.setInventorySlotContents(a, ItemHandlerHelper.copyStackWithSize(inStack, 1));
/* 167 */       break;
/*     */     case 8: 
/* 169 */       for (int a = 0; a < 2; a++)
/* 170 */         for (int b = 0; b < 3; b++)
/* 171 */           this.craftMatrix.setInventorySlotContents(a + b * 3, ItemHandlerHelper.copyStackWithSize(inStack, 1));
/* 172 */       break;
/*     */     case 9: 
/* 174 */       for (int a = 0; a < 9; a++) {
/* 175 */         if (a != 4) {
/* 176 */           this.craftMatrix.setInventorySlotContents(a, ItemHandlerHelper.copyStackWithSize(inStack, 1));
/*     */         }
/*     */       }
/*     */     }
/* 180 */     this.outStack = CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj);
/*     */     
/* 182 */     ItemStack[] aitemstack = CraftingManager.getInstance().func_180303_b(this.craftMatrix, this.worldObj);
/* 183 */     for (int i = 0; i < Math.min(9, aitemstack.length); i++)
/*     */     {
/* 185 */       ItemStack itemstack1 = this.craftMatrix.getStackInSlot(i);
/* 186 */       ItemStack itemstack2 = aitemstack[i];
/*     */       
/* 188 */       if (itemstack1 != null)
/*     */       {
/* 190 */         this.craftMatrix.setInventorySlotContents(i, null);
/*     */       }
/*     */       
/* 193 */       if ((itemstack2 != null) && (this.craftMatrix.getStackInSlot(i) == null)) {
/* 194 */         this.craftMatrix.setInventorySlotContents(i, itemstack2);
/*     */       }
/*     */     }
/*     */     
/* 198 */     return this.outStack != null;
/*     */   }
/*     */   
/*     */   public void cycle() {
/* 202 */     this.type = ((byte)(this.type + 1));
/* 203 */     if (this.type > 9) this.type = 0;
/* 204 */     markDirty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean receiveClientEvent(int i, int j)
/*     */   {
/* 211 */     if (i == 1)
/*     */     {
/* 213 */       if (this.worldObj.isRemote) {
/* 214 */         this.rotTicks = 10;
/* 215 */         this.worldObj.playSound(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, "thaumcraft:pump", 0.2F, 1.7F, false);
/*     */       }
/*     */       
/* 218 */       return true;
/*     */     }
/* 220 */     return super.receiveClientEvent(i, j);
/*     */   }
/*     */   
/*     */ 
/*     */   public MovingObjectPosition rayTrace(World world, Vec3 vec3d, Vec3 vec3d1, MovingObjectPosition fullblock)
/*     */   {
/* 226 */     return fullblock;
/*     */   }
/*     */   
/*     */ 
/*     */   public void addTraceableCuboids(List<IndexedCuboid6> cuboids)
/*     */   {
/* 232 */     EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata());
/* 233 */     cuboids.add(new IndexedCuboid6(Integer.valueOf(0), getCuboidByFacing(facing)));
/*     */   }
/*     */   
/*     */   public Cuboid6 getCuboidByFacing(EnumFacing facing) {
/* 237 */     switch (facing) {
/* 238 */     default:  return new Cuboid6(getPos().getX() + 0.75D, getPos().getY() + 0.125D, getPos().getZ() + 0.375D, getPos().getX() + 0.875D, getPos().getY() + 0.375D, getPos().getZ() + 0.625D);
/*     */     case EAST: 
/* 240 */       return new Cuboid6(getPos().getX() + 0.125D, getPos().getY() + 0.125D, getPos().getZ() + 0.375D, getPos().getX() + 0.25D, getPos().getY() + 0.375D, getPos().getZ() + 0.625D);
/*     */     case NORTH: 
/* 242 */       return new Cuboid6(getPos().getX() + 0.375D, getPos().getY() + 0.125D, getPos().getZ() + 0.75D, getPos().getX() + 0.625D, getPos().getY() + 0.375D, getPos().getZ() + 0.875D);
/*     */     }
/* 244 */     return new Cuboid6(getPos().getX() + 0.375D, getPos().getY() + 0.125D, getPos().getZ() + 0.125D, getPos().getX() + 0.625D, getPos().getY() + 0.375D, getPos().getZ() + 0.25D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TilePatternCrafter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */