/*     */ package thaumcraft.common.tiles.devices;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
/*     */ import net.minecraftforge.fml.relauncher.ReflectionHelper;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import thaumcraft.api.blocks.TileThaumcraft;
/*     */ import thaumcraft.common.lib.utils.BlockStateUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileBellows
/*     */   extends TileThaumcraft
/*     */   implements ITickable
/*     */ {
/*     */   @SideOnly(Side.CLIENT)
/*     */   public AxisAlignedBB getRenderBoundingBox()
/*     */   {
/*  28 */     return AxisAlignedBB.fromBounds(getPos().getX() - 0.3D, getPos().getY() - 0.3D, getPos().getZ() - 0.3D, getPos().getX() + 1.3D, getPos().getY() + 1.3D, getPos().getZ() + 1.3D);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  33 */   public float inflation = 1.0F;
/*  34 */   boolean direction = false;
/*  35 */   boolean firstrun = true;
/*  36 */   public int delay = 0;
/*     */   
/*     */   public void update()
/*     */   {
/*  40 */     if (this.worldObj.isRemote) {
/*  41 */       if (BlockStateUtils.isEnabled(getBlockMetadata())) {
/*  42 */         if (this.firstrun)
/*  43 */           this.inflation = (0.35F + this.worldObj.rand.nextFloat() * 0.55F);
/*  44 */         this.firstrun = false;
/*     */         
/*  46 */         if ((this.inflation > 0.35F) && (!this.direction)) this.inflation -= 0.075F;
/*  47 */         if ((this.inflation <= 0.35F) && (!this.direction))
/*     */         {
/*  49 */           this.direction = true;
/*     */         }
/*     */         
/*  52 */         if ((this.inflation < 1.0F) && (this.direction)) this.inflation += 0.025F;
/*  53 */         if ((this.inflation >= 1.0F) && (this.direction)) {
/*  54 */           this.direction = false;
/*  55 */           this.worldObj.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), "mob.ghast.fireball", 0.01F, 0.5F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, false);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*  60 */     else if (BlockStateUtils.isEnabled(getBlockMetadata())) {
/*  61 */       this.delay += 1;
/*  62 */       if (this.delay >= 2) {
/*  63 */         this.delay = 0;
/*  64 */         TileEntity tile = this.worldObj.getTileEntity(this.pos.offset(BlockStateUtils.getFacing(getBlockMetadata())));
/*  65 */         if ((tile != null) && ((tile instanceof TileEntityFurnace))) {
/*  66 */           TileEntityFurnace tf = (TileEntityFurnace)tile;
/*  67 */           int ct = getCooktime(tf);
/*  68 */           if ((ct > 0) && (ct < 199)) setCooktime(tf, ct + 1);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setCooktime(TileEntityFurnace ent, int hit)
/*     */   {
/*     */     try {
/*  77 */       ObfuscationReflectionHelper.setPrivateValue(TileEntityFurnace.class, ent, Integer.valueOf(hit), new String[] { "cookTime", "field_174906_k", "k" });
/*     */     } catch (Exception e) {}
/*     */   }
/*     */   
/*     */   public int getCooktime(TileEntityFurnace ent) {
/*     */     try {
/*  83 */       return ((Integer)ReflectionHelper.getPrivateValue(TileEntityFurnace.class, ent, new String[] { "cookTime", "field_174906_k", "k" })).intValue();
/*     */     } catch (Exception e) {}
/*  85 */     return 0;
/*     */   }
/*     */   
/*     */   public static int getBellows(World world, BlockPos pos, EnumFacing[] directions)
/*     */   {
/*  90 */     int bellows = 0;
/*  91 */     for (EnumFacing dir : directions) {
/*  92 */       TileEntity tile = world.getTileEntity(pos.offset(dir));
/*     */       try {
/*  94 */         if ((tile != null) && ((tile instanceof TileBellows)) && (BlockStateUtils.getFacing(tile.getBlockMetadata()) == dir.getOpposite()) && (BlockStateUtils.isEnabled(tile.getBlockMetadata())))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*  99 */           bellows++;
/*     */         }
/*     */       } catch (Exception e) {}
/*     */     }
/* 103 */     return bellows;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\tiles\devices\TileBellows.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */